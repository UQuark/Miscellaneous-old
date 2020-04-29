package me.uquark.miscellaneous.mixin;

import me.uquark.miscellaneous.util.ReachDistance;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.MessageType;
import net.minecraft.network.NetworkThreadUtils;
import net.minecraft.network.listener.ServerPlayPacketListener;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.network.packet.s2c.play.BlockUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.ChatMessageS2CPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ServerPlayNetworkHandler.class)
public abstract class ServerPlayNetworkHandlerMixin implements ServerPlayPacketListener {
    @Shadow
    public ServerPlayerEntity player;
    @Shadow
    public MinecraftServer server;
    @Shadow
    public Vec3d requestedTeleportPos;

    @Shadow
    public abstract void disconnect(Text reason);

    @Overwrite
    public void onPlayerInteractBlock(PlayerInteractBlockC2SPacket packet) {
        NetworkThreadUtils.forceMainThread(packet, this, this.player.getServerWorld());
        ServerWorld serverWorld = this.server.getWorld(this.player.dimension);
        Hand hand = packet.getHand();
        ItemStack itemStack = this.player.getStackInHand(hand);
        BlockHitResult blockHitResult = packet.getHitY();
        BlockPos blockPos = blockHitResult.getBlockPos();
        Direction direction = blockHitResult.getSide();
        this.player.updateLastActionTime();
        float reachDistance = ReachDistance.getReachDistance(player);
        if (blockPos.getY() >= this.server.getWorldHeight() - 1 && (direction == Direction.UP || blockPos.getY() >= this.server.getWorldHeight())) {
            Text text = (new TranslatableText("build.tooHigh", new Object[]{this.server.getWorldHeight()})).formatted(Formatting.RED);
            this.player.networkHandler.sendPacket(new ChatMessageS2CPacket(text, MessageType.GAME_INFO));
        } else if (this.requestedTeleportPos == null && this.player.squaredDistanceTo((double)blockPos.getX() + 0.5D, (double)blockPos.getY() + 0.5D, (double)blockPos.getZ() + 0.5D) < reachDistance * reachDistance && serverWorld.canPlayerModifyAt(this.player, blockPos)) {
            ActionResult actionResult = this.player.interactionManager.interactBlock(this.player, serverWorld, itemStack, hand, blockHitResult);
            if (actionResult.shouldSwingHand()) {
                this.player.swingHand(hand, true);
            }
        }

        this.player.networkHandler.sendPacket(new BlockUpdateS2CPacket(serverWorld, blockPos));
        this.player.networkHandler.sendPacket(new BlockUpdateS2CPacket(serverWorld, blockPos.offset(direction)));
    }
}
