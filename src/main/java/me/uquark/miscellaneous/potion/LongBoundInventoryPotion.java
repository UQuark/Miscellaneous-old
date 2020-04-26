package me.uquark.miscellaneous.potion;

import me.uquark.miscellaneous.Miscellaneous;
import me.uquark.miscellaneous.effect.Effects;
import me.uquark.quarkcore.potion.AbstractPotion;
import net.minecraft.entity.effect.StatusEffectInstance;

public class LongBoundInventoryPotion extends AbstractPotion {
    public LongBoundInventoryPotion() {
        super(Miscellaneous.modid, "long_bound_inventory", new StatusEffectInstance(Effects.BOUND_INVENTORY_EFFECT, 8*60*20, 0));
    }
}
