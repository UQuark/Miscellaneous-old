package me.uquark.miscellaneous.enchantment;

import me.uquark.miscellaneous.Miscellaneous;
import me.uquark.miscellaneous.util.TreeDefinition;
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
        TreeDefinition treeDefinition = new TreeDefinition(world, pos);
        if (!treeDefinition.isTree)
            return;
        ItemStack toolStack = player.getMainHandStack();
        for (BlockPos treeBlock : treeDefinition.blocks) {
            world.breakBlock(treeBlock, false, player);
            toolStack.getItem().postMine(toolStack, world, world.getBlockState(pos), pos, player);
        }
        if (!player.isCreative())
            Block.dropStack(world, pos, new ItemStack(treeDefinition.type.getLog(), treeDefinition.blocks.size()));
    }
}