package me.uquark.miscellaneous.mixin;

import me.uquark.miscellaneous.effect.Effects;
import net.minecraft.client.gui.screen.ingame.BeaconScreen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.text.MutableText;
import net.minecraft.text.TranslatableText;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.lang.reflect.Field;

@Mixin(BeaconScreen.EffectButtonWidget.class)
public abstract class EffectButtonWidgetMixin extends BeaconScreen.BaseButtonWidget {
    protected EffectButtonWidgetMixin(int x, int y) {
        super(x, y);
    }

    @Final
    @Shadow
    private StatusEffect effect;
    @Final
    @Shadow
    private boolean primary;
    
    /**
     * @author UQuark
     * @reason Bound Inventory
     */
    @Overwrite
    public void renderToolTip(MatrixStack matrices, int mouseX, int mouseY) {
        MutableText mutableText = new TranslatableText(this.effect.getTranslationKey());
        if (!this.primary && this.effect != StatusEffects.REGENERATION && this.effect != Effects.BOUND_INVENTORY_EFFECT) {
            mutableText.append(" II");
        }

        try {
            Field field = BeaconScreen.EffectButtonWidget.class.getDeclaredField("this$0");
            field.setAccessible(true);
            BeaconScreen bs = (BeaconScreen) field.get(this);
            bs.renderTooltip(matrices, mutableText, mouseX, mouseY);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
