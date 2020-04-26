package me.uquark.miscellaneous;

import me.uquark.miscellaneous.effect.Effects;
import me.uquark.miscellaneous.enchantment.Enchantments;
import me.uquark.miscellaneous.item.Items;
import me.uquark.miscellaneous.potion.Potions;
import net.fabricmc.api.ModInitializer;

public class Miscellaneous implements ModInitializer {
    public final static String modid = "miscellaneous";

    @Override
    public void onInitialize() {
        Items.WRENCH_ITEM.register();
        Enchantments.CHARM_OF_COMEBACK_ENCHANTMENT.register();
        Effects.BOUND_INVENTORY_EFFECT.register();
        Potions.BOUND_INVENTORY_POTION.register();
        Potions.LONG_BOUND_INVENTORY_POTION.register();
    }
}
