package me.uquark.miscellaneous.enchantment;

import me.uquark.miscellaneous.Miscellaneous;
import me.uquark.quarkcore.enchantment.AbstractEnchantment;
import net.minecraft.enchantment.BindingCurseEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;

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

    public boolean differs(Enchantment other) {
        return other instanceof BindingCurseEnchantment ? false : super.differs(other);
    }
}
