package me.uquark.miscellaneous.enchantment;

import me.uquark.miscellaneous.Miscellaneous;
import me.uquark.quarkcore.enchantment.AbstractEnchantment;
import net.minecraft.enchantment.*;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.PotionItem;

public class CharmOfComebackEnchantment extends AbstractEnchantment {
    protected CharmOfComebackEnchantment() {
        super(Miscellaneous.modid, "charm_of_comeback", Weight.COMMON, EnchantmentTarget.ALL, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    public int getMinimumPower(int level) {
        return 30;
    }

    @Override
    public int getMaximumLevel() {
        return 1;
    }

    @Override
    public boolean isAcceptableItem(ItemStack stack) {
        return true;
    }

    public static boolean isEnchanted(ItemStack stack) {
        return EnchantmentHelper.getLevel(Enchantments.CHARM_OF_COMEBACK_ENCHANTMENT, stack) > 0;
    }

    public boolean differs(Enchantment other) {
        return other instanceof BindingCurseEnchantment ? false : super.differs(other);
    }
}
