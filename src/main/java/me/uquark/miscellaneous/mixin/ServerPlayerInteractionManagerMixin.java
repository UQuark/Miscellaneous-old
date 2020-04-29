package me.uquark.miscellaneous.mixin;

import me.uquark.miscellaneous.effect.Effects;
import me.uquark.miscellaneous.enchantment.Enchantments;
import net.minecraft.block.BlockState;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket.Action;
import net.minecraft.network.packet.s2c.play.PlayerActionResponseS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.network.ServerPlayerInteractionManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.GameMode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(ServerPlayerInteractionManager.class)
public abstract class ServerPlayerInteractionManagerMixin {
    @Shadow
    public ServerPlayerEntity player;
    @Shadow
    public ServerWorld world;
    @Shadow
    public GameMode gameMode;
    @Shadow
    public int startMiningTime;
    @Shadow
    public int tickCounter;
    @Shadow
    public boolean mining;
    @Shadow
    public BlockPos miningPos;
    @Shadow
    public int blockBreakingProgress;
    @Shadow
    public boolean failedToMine;
    @Shadow
    public BlockPos failedMiningPos;
    @Shadow
    public int failedStartMiningTime;
    @Shadow
    private static final Logger LOGGER = LogManager.getLogger();

    @Shadow
    public abstract boolean isCreative();
    @Shadow
    public abstract void finishMining(BlockPos blockPos, net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket.Action action, String reason);

    private float manhattanDistance(float x1, float y1, float z1, float x2, float y2, float z2) {
        return Math.abs(x1 - x2) + Math.abs(y1 - y2) + Math.abs(z1 - z2);
    }

    private float getReachDistance() {
        StatusEffectInstance effect = player.getStatusEffect(Effects.HANDY_EFFECT);
        return (effect == null) ?
                Effects.HANDY_EFFECT.getReachDistance(-1, player.isCreative())
                :
                Effects.HANDY_EFFECT.getReachDistance(effect.getAmplifier(), player.isCreative());
    }

    @Inject(method = "finishMining", at = @At("HEAD"))
    public void finishMining(BlockPos blockPos, Action action, String reason, CallbackInfo info) {
        if (Enchantments.LUMBERJACK_ENCHANTMENT.isEnchanted(player.getMainHandStack()))
            Enchantments.LUMBERJACK_ENCHANTMENT.capitate(player, player.world, blockPos);
    }

    @Overwrite
    public void processBlockBreakingAction(BlockPos pos, net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket.Action action, Direction direction, int worldHeight) {
        double g = manhattanDistance(
            (float) this.player.getX(),
            (float) this.player.getY(),
            (float) this.player.getZ(),
            pos.getX() + 0.5f,
            pos.getY() + 0.5f + 1.5f,
            pos.getZ() + 0.5f
        );
        float reachDistance = getReachDistance();
        if (g > reachDistance * reachDistance) {
            this.player.networkHandler.sendPacket(new PlayerActionResponseS2CPacket(pos, this.world.getBlockState(pos), action, false, "too far"));
        } else if (pos.getY() >= worldHeight) {
            this.player.networkHandler.sendPacket(new PlayerActionResponseS2CPacket(pos, this.world.getBlockState(pos), action, false, "too high"));
        } else {
            BlockState blockState;
            if (action == net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket.Action.START_DESTROY_BLOCK) {
                if (!this.world.canPlayerModifyAt(this.player, pos)) {
                    this.player.networkHandler.sendPacket(new PlayerActionResponseS2CPacket(pos, this.world.getBlockState(pos), action, false, "may not interact"));
                    return;
                }

                if (this.isCreative()) {
                    if (!this.world.extinguishFire((PlayerEntity)null, pos, direction)) {
                        this.finishMining(pos, action, "creative destroy");
                    } else {
                        this.player.networkHandler.sendPacket(new PlayerActionResponseS2CPacket(pos, this.world.getBlockState(pos), action, true, "fire put out"));
                    }

                    return;
                }

                if (this.player.canMine(this.world, pos, this.gameMode)) {
                    this.player.networkHandler.sendPacket(new PlayerActionResponseS2CPacket(pos, this.world.getBlockState(pos), action, false, "block action restricted"));
                    return;
                }

                this.world.extinguishFire((PlayerEntity)null, pos, direction);
                this.startMiningTime = this.tickCounter;
                float h = 1.0F;
                blockState = this.world.getBlockState(pos);
                if (!blockState.isAir()) {
                    blockState.onBlockBreakStart(this.world, pos, this.player);
                    h = blockState.calcBlockBreakingDelta(this.player, this.player.world, pos);
                }

                if (!blockState.isAir() && h >= 1.0F) {
                    this.finishMining(pos, action, "insta mine");
                } else {
                    if (this.mining) {
                        this.player.networkHandler.sendPacket(new PlayerActionResponseS2CPacket(this.miningPos, this.world.getBlockState(this.miningPos), net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket.Action.START_DESTROY_BLOCK, false, "abort destroying since another started (client insta mine, server disagreed)"));
                    }

                    this.mining = true;
                    this.miningPos = pos.toImmutable();
                    int i = (int)(h * 10.0F);
                    this.world.setBlockBreakingInfo(this.player.getEntityId(), pos, i);
                    this.player.networkHandler.sendPacket(new PlayerActionResponseS2CPacket(pos, this.world.getBlockState(pos), action, true, "actual start of destroying"));
                    this.blockBreakingProgress = i;
                }
            } else if (action == net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket.Action.STOP_DESTROY_BLOCK) {
                if (pos.equals(this.miningPos)) {
                    int j = this.tickCounter - this.startMiningTime;
                    blockState = this.world.getBlockState(pos);
                    if (!blockState.isAir()) {
                        float k = blockState.calcBlockBreakingDelta(this.player, this.player.world, pos) * (float)(j + 1);
                        if (k >= 0.7F) {
                            this.mining = false;
                            this.world.setBlockBreakingInfo(this.player.getEntityId(), pos, -1);
                            this.finishMining(pos, action, "destroyed");
                            return;
                        }

                        if (!this.failedToMine) {
                            this.mining = false;
                            this.failedToMine = true;
                            this.failedMiningPos = pos;
                            this.failedStartMiningTime = this.startMiningTime;
                        }
                    }
                }

                this.player.networkHandler.sendPacket(new PlayerActionResponseS2CPacket(pos, this.world.getBlockState(pos), action, true, "stopped destroying"));
            } else if (action == net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket.Action.ABORT_DESTROY_BLOCK) {
                this.mining = false;
                if (!Objects.equals(this.miningPos, pos)) {
                    LOGGER.warn("Mismatch in destroy block pos: " + this.miningPos + " " + pos);
                    this.world.setBlockBreakingInfo(this.player.getEntityId(), this.miningPos, -1);
                    this.player.networkHandler.sendPacket(new PlayerActionResponseS2CPacket(this.miningPos, this.world.getBlockState(this.miningPos), action, true, "aborted mismatched destroying"));
                }

                this.world.setBlockBreakingInfo(this.player.getEntityId(), pos, -1);
                this.player.networkHandler.sendPacket(new PlayerActionResponseS2CPacket(pos, this.world.getBlockState(pos), action, true, "aborted destroying"));
            }

        }
    }
}
