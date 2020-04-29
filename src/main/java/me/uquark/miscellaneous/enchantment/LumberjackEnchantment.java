package me.uquark.miscellaneous.enchantment;

import me.uquark.miscellaneous.Miscellaneous;
import me.uquark.miscellaneous.util.Tree;
import me.uquark.quarkcore.enchantment.AbstractEnchantment;
import net.minecraft.block.Block;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class LumberjackEnchantment extends AbstractEnchantment {
    protected LumberjackEnchantment() {
        super(Miscellaneous.modid, "lumberjack", Weight.COMMON, EnchantmentTarget.DIGGER, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
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
        return stack.getItem() instanceof AxeItem;
    }

    public void capitate(PlayerEntity player, World world, BlockPos pos) {
        if (world.isClient)
            return;
        Tree tree = new Tree(world, pos);
        if (!tree.isTree)
            return;
        for (BlockPos treeBlock : tree.blocks)
            world.breakBlock(treeBlock, false, player);
        if (!player.isCreative())
            Block.dropStack(world, pos, new ItemStack(tree.type.getLog(), tree.blocks.size()));
    }
}
