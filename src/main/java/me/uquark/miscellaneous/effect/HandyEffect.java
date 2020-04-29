package me.uquark.miscellaneous.effect;

import me.uquark.miscellaneous.Miscellaneous;
import me.uquark.quarkcore.effect.AbstractStatusEffect;
import net.minecraft.entity.effect.StatusEffectType;

public class HandyEffect extends AbstractStatusEffect {
    public static final float EXTENTION_PER_LEVEL = 2;

    protected HandyEffect() {
        super(Miscellaneous.modid, "handy", StatusEffectType.BENEFICIAL, 0xffcd82);
    }

    public float getReachDistance(int amplifier, boolean creative) {
        final float BASE_CREATIVE_DISTANCE = 5;
        final float BASE_SURVIVAL_DISTANCE = 4.5f;
        return (creative ? BASE_CREATIVE_DISTANCE : BASE_SURVIVAL_DISTANCE) + (amplifier + 1) * EXTENTION_PER_LEVEL;
    }
}
