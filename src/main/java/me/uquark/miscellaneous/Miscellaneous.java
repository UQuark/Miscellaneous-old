package me.uquark.miscellaneous;

import me.uquark.miscellaneous.block.Blocks;
import me.uquark.miscellaneous.effect.Effects;
import me.uquark.miscellaneous.enchantment.Enchantments;
import me.uquark.miscellaneous.item.Items;
import me.uquark.miscellaneous.potion.Potions;
import me.uquark.quarkcore.potion.BrewingRecipeHelper;
import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Miscellaneous implements ModInitializer {
    public final static Logger LOGGER = LogManager.getLogger();
    public final static String modid = "miscellaneous";

    @Override
    public void onInitialize() {
        Items.WRENCH_ITEM.register();
        Items.NECROMASS_ITEM.register();
        Items.STACKABLE_POTION_ITEM.register();
        Items.STACKABLE_SPLASH_POTION_ITEM.register();
        Items.STACKABLE_LINGERING_POTION_ITEM.register();
        Items.ARMOR_STAND_WITH_ARMS_ITEM.register();

        Enchantments.CHARM_OF_COMEBACK_ENCHANTMENT.register();
        Enchantments.LUMBERJACK_ENCHANTMENT.register();

        Effects.BOUND_INVENTORY_EFFECT.register();
        Effects.ROTTING_ALIVE_EFFECT.register();

        Potions.BOUND_INVENTORY_POTION.register();
        Potions.LONG_BOUND_INVENTORY_POTION.register();
        Potions.ROTTING_ALIVE_POTION.register();

        Blocks.INVERTED_REDSTONE_LAMP_BLOCK.register();

        if (!BrewingRecipeHelper.registerPotionRecipe(net.minecraft.potion.Potions.AWKWARD, net.minecraft.item.Items.ENDER_PEARL, Potions.BOUND_INVENTORY_POTION))
            LOGGER.warn(String.format("Failed to register recipe of potion %s", Potions.BOUND_INVENTORY_POTION.id.getPath()));
        if (!BrewingRecipeHelper.registerPotionRecipe(Potions.BOUND_INVENTORY_POTION, net.minecraft.item.Items.REDSTONE, Potions.LONG_BOUND_INVENTORY_POTION))
            LOGGER.warn(String.format("Failed to register recipe of potion %s", Potions.LONG_BOUND_INVENTORY_POTION.id.getPath()));
        if (!BrewingRecipeHelper.registerPotionRecipe(net.minecraft.potion.Potions.AWKWARD, Items.NECROMASS_ITEM, Potions.ROTTING_ALIVE_POTION))
            LOGGER.warn(String.format("Failed to register recipe of potion %s", Potions.ROTTING_ALIVE_POTION.id.getPath()));
    }
}
