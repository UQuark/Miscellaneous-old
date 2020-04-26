package me.uquark.miscellaneous.mixin;

import me.uquark.miscellaneous.effect.Effects;
import me.uquark.miscellaneous.enchantment.CharmOfComebackEnchantment;
import net.minecraft.block.entity.BeaconBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DefaultedList;
import net.minecraft.util.Nameable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.tools.obfuscation.ObfuscationData;

import java.util.Iterator;
import java.util.List;

@Mixin(PlayerInventory.class)
public abstract class PlayerInventoryMixin implements Inventory, Nameable {
    @Shadow
    private List<DefaultedList<ItemStack>> combinedInventory;
    @Shadow
    public PlayerEntity player;

    @Overwrite
    public void dropAll() {
        if (player.getStatusEffect(Effects.BOUND_INVENTORY_EFFECT) != null)
            return;

        Iterator var1 = this.combinedInventory.iterator();

        while(var1.hasNext()) {
            List<ItemStack> list = (List)var1.next();

            for(int i = 0; i < list.size(); ++i) {
                ItemStack itemStack = (ItemStack)list.get(i);
                if (!itemStack.isEmpty() && !CharmOfComebackEnchantment.isEnchanted(itemStack)) {
                    this.player.dropItem(itemStack, true, false);
                    list.set(i, ItemStack.EMPTY);
                }
            }
        }
    }

}
