package me.uquark.miscellaneous.mixin;

import net.minecraft.client.gui.screen.ingame.BeaconScreen;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BeaconScreen.EffectButtonWidget.class)
public abstract class EffectButtonWidgetMixin extends BeaconScreen.BaseButtonWidget {
    protected EffectButtonWidgetMixin(int x, int y) {
        super(x, y);
    }
}
