package me.uquark.miscellaneous.mixin;

import com.mojang.authlib.GameProfile;
import me.uquark.miscellaneous.effect.Effects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin extends PlayerEntity {
    public ServerPlayerEntityMixin(World world, BlockPos pos, float yaw, GameProfile profile) {
        super(world, pos, yaw, profile);
    }

    @Inject(method = "copyFrom", at = @At("HEAD"))
    public void copyFrom(ServerPlayerEntity oldPlayer, boolean alive, CallbackInfo info) {
        this.getInventory().clone(oldPlayer.getInventory());
        if (oldPlayer.getStatusEffect(Effects.BOUND_INVENTORY_EFFECT) != null) {
            this.experienceLevel = oldPlayer.experienceLevel;
            this.totalExperience = oldPlayer.totalExperience;
            this.experienceProgress = oldPlayer.experienceProgress;
        }
    }
}
