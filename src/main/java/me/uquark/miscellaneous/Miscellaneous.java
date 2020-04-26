package me.uquark.miscellaneous;

import me.uquark.miscellaneous.enchantment.Enchantments;
import me.uquark.miscellaneous.item.Items;
import net.fabricmc.api.ModInitializer;

public class Miscellaneous implements ModInitializer {
    public final static String modid = "miscellaneous";

    @Override
    public void onInitialize() {
        Items.WRENCH_ITEM.register();
        Enchantments.CHARM_OF_COMEBACK.register();
    }
}
