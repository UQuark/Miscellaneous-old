package me.uquark.miscellaneous.item;

import me.uquark.miscellaneous.Miscellaneous;
import me.uquark.quarkcore.reflect.ReflectionHelper;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.item.PotionItem;
import net.minecraft.item.SplashPotionItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.lang.reflect.Field;

public class StackableSplashPotionItem extends SplashPotionItem {
    public final Identifier id = new Identifier("minecraft", "splash_potion");

    public StackableSplashPotionItem() {
        super(new Settings().group(ItemGroup.BREWING).maxCount(16));
    }

    public void register() {
        try {
            Field potion = ReflectionHelper.resolveField(Items.class, "SPLASH_POTION", "field_8436");
            ReflectionHelper.setFinal(null, potion, this);
            Registry.ITEM.set(825, id, this);
        } catch (Exception e) {
            Miscellaneous.LOGGER.error("Failed to replace default potion item");
            e.printStackTrace();
        }
    }
}