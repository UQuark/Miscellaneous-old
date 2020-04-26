package me.uquark.miscellaneous.potion;

import me.uquark.miscellaneous.Miscellaneous;
import me.uquark.miscellaneous.effect.Effects;
import me.uquark.quarkcore.potion.AbstractPotion;
import net.minecraft.entity.effect.StatusEffectInstance;

public class RottingAlivePotion extends AbstractPotion {
    public RottingAlivePotion() {
        super(Miscellaneous.modid, "rotting_alive", new StatusEffectInstance(Effects.ROTTING_ALIVE_EFFECT, 20, 0));
    }
}
