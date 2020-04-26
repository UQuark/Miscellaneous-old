package me.uquark.miscellaneous.potion;

import me.uquark.miscellaneous.Miscellaneous;
import me.uquark.miscellaneous.effect.Effects;
import me.uquark.quarkcore.potion.AbstractPotion;
import net.minecraft.entity.effect.StatusEffectInstance;

public class BoundInventoryPotion extends AbstractPotion {
    public BoundInventoryPotion() {
        super(Miscellaneous.modid, "bound_inventory", new StatusEffectInstance(Effects.BOUND_INVENTORY_EFFECT, 3*60*20, 0));
    }
}
