package me.uquark.miscellaneous.mixin;

import me.uquark.miscellaneous.effect.Effects;
import me.uquark.miscellaneous.util.ReachDistance;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.entity.effect.StatusEffectInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ClientPlayerInteractionManager.class)
public class ClientPlayerInteractionManagerMixin {
    @Shadow
    public MinecraftClient client;

    @Overwrite
    public float getReachDistance() {
        return ReachDistance.getReachDistance(client.player);
    }
}
