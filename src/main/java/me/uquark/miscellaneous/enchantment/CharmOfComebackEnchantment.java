package me.uquark.miscellaneous.enchantment;

import me.uquark.miscellaneous.Miscellaneous;
import me.uquark.quarkcore.enchantment.AbstractEnchantment;
import net.minecraft.enchantment.BindingCurseEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;

public class CharmOfComebackEnchantment extends AbstractEnchantment {
    protected CharmOfComebackEnchantment() {
        super(Miscellaneous.modid, "charm_of_comeback", Rarity.COMMON, EnchantmentTarget.BREAKABLE, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    public int getMinPower(int level) {
        return 20;
    }

    @Override
    public int getMaxPower(int level) {
        return 30;
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    public boolean isAcceptableItem(ItemStack stack) {
        return true;
    }

    @Override
    protected boolean canAccept(Enchantment other) {
        return other != Enchantments.BINDING_CURSE && other != Enchantments.VANISHING_CURSE && super.canAccept(other);
    }
}
