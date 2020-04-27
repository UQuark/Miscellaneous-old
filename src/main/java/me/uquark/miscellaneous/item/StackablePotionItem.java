package me.uquark.miscellaneous.item;

import me.uquark.miscellaneous.Miscellaneous;
import me.uquark.quarkcore.potion.BrewingRecipeHelper;
import me.uquark.quarkcore.reflect.ReflectionHelper;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.item.PotionItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

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
            Field potion = ReflectionHelper.resolveField(Items.class, "POTION", "field_8574");
            ReflectionHelper.setFinal(null, potion, this);
            Registry.ITEM.set(687, id, this);
        } catch (Exception e) {
            Miscellaneous.LOGGER.error("Failed to replace default potion item");
            e.printStackTrace();
        }
    }
}
