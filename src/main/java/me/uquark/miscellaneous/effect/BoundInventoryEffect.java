package me.uquark.miscellaneous.effect;

import me.uquark.miscellaneous.Miscellaneous;
import me.uquark.quarkcore.effect.AbstractStatusEffect;
import net.minecraft.entity.effect.StatusEffectType;

public class BoundInventoryEffect extends AbstractStatusEffect {
    protected BoundInventoryEffect() {
        super(Miscellaneous.modid, "bound_inventory", StatusEffectType.BENEFICIAL, 0xf2147d);
    }
}
