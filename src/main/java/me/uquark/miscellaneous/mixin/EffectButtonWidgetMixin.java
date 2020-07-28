package me.uquark.miscellaneous.mixin;

import me.uquark.miscellaneous.effect.Effects;
import net.minecraft.client.gui.screen.ingame.BeaconScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BeaconScreen.EffectButtonWidget.class)
public class EffectButtonWidgetMixin {
    @Shadow
    public boolean primary;
    @Shadow
    public StatusEffect effect;

    @Inject(method = "renderToolTip", at=@At("HEAD"))
    public void renderToolTip(MatrixStack matrices, int mouseX, int mouseY, CallbackInfo callbackInfo) {
        primary |= (effect == Effects.BOUND_INVENTORY_EFFECT);
    }
}
