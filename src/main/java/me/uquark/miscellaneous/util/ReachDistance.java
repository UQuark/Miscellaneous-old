package me.uquark.miscellaneous.util;

import me.uquark.miscellaneous.effect.Effects;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;

public class ReachDistance {
    public static float getReachDistance(PlayerEntity player) {
        StatusEffectInstance effect = player.getStatusEffect(Effects.HANDY_EFFECT);
        return (effect == null) ?
                Effects.HANDY_EFFECT.getReachDistance(-1, player.isCreative())
                :
                Effects.HANDY_EFFECT.getReachDistance(effect.getAmplifier(), player.isCreative());
    }
}
