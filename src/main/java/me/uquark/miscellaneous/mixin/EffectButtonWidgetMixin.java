package me.uquark.miscellaneous.mixin;

import me.uquark.miscellaneous.effect.Effects;
import net.minecraft.client.gui.screen.ingame.BeaconScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BeaconScreen.EffectButtonWidget.class)
public class EffectButtonWidgetMixin {
    @Shadow
    public StatusEffect effect;

    /**
     * @author UQuark
     * @reason Secondary beacon effect w/o II suffix
     */
    @Overwrite
    private Text method_30902(StatusEffect statusEffect, boolean bl) {
        MutableText mutableText = new TranslatableText(statusEffect.getTranslationKey());
        if (!bl && statusEffect != StatusEffects.REGENERATION && statusEffect != Effects.BOUND_INVENTORY_EFFECT) {
            mutableText.append(" II");
        }

        return mutableText;
    }
}
