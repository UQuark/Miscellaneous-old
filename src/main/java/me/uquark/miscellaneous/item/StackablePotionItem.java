package me.uquark.miscellaneous.item;

import com.mojang.serialization.Lifecycle;
import me.uquark.miscellaneous.Miscellaneous;
import me.uquark.quarkcore.potion.BrewingRecipeHelper;
import me.uquark.quarkcore.reflect.ReflectionHelper;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.item.PotionItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;

import java.lang.reflect.Field;

public class StackablePotionItem extends PotionItem {
    public final Identifier id = new Identifier("minecraft", "potion");

    public StackablePotionItem() {
        super(new Settings().group(ItemGroup.BREWING).maxCount(16));
    }

    public void register() {
        try {
            if (!BrewingRecipeHelper.registerPotionType(this))
                return;
            Items.POTION = this;
            Registry.ITEM.set(755, RegistryKey.of(Registry.ITEM_KEY, id), this, Lifecycle.stable());
        } catch (Exception e) {
            Miscellaneous.LOGGER.error("Failed to replace default potion item");
            e.printStackTrace();
        }
    }
}
