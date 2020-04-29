package me.uquark.miscellaneous.mixin;

import me.uquark.miscellaneous.enchantment.Enchantments;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket.Action;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.network.ServerPlayerInteractionManager;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerInteractionManager.class)
public class ServerPlayerInteractionManagerMixin {
    @Shadow
    public ServerPlayerEntity player;

    @Inject(method = "finishMining", at = @At("HEAD"))
    public void finishMining(BlockPos blockPos, Action action, String reason, CallbackInfo info) {
        if (Enchantments.LUMBERJACK_ENCHANTMENT.isEnchanted(player.getMainHandStack()))
            Enchantments.LUMBERJACK_ENCHANTMENT.capitate(player, player.world, blockPos);
    }
}
