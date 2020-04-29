package me.uquark.miscellaneous.util;

import net.minecraft.block.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.HashSet;
import java.util.Set;

public class TreeDefinition {
    public enum TreeType {
        Oak,
        Birch,
        Spruce,
        Jungle,
        Acacia,
        DarkOak,
        Undefined;

        public static TreeType getTreeType(Block block) {
            if (block.equals(Blocks.OAK_LOG) || block.equals(Blocks.OAK_LEAVES))
                return Oak;
            if (block.equals(Blocks.BIRCH_LOG) || block.equals(Blocks.BIRCH_LEAVES))
                return Birch;
            if (block.equals(Blocks.SPRUCE_LOG) || block.equals(Blocks.SPRUCE_LEAVES))
                return Spruce;
            if (block.equals(Blocks.JUNGLE_LOG) || block.equals(Blocks.JUNGLE_LEAVES))
                return Jungle;
            if (block.equals(Blocks.ACACIA_LOG) || block.equals(Blocks.ACACIA_LEAVES))
                return Acacia;
            if (block.equals(Blocks.DARK_OAK_LOG) || block.equals(Blocks.DARK_OAK_LEAVES))
                return DarkOak;
            return Undefined;
        }

        public Block getLog() {
            switch (this) {
                case Oak:
                    return Blocks.OAK_LOG;
                case Birch:
                    return Blocks.BIRCH_LOG;
                case Spruce:
                    return Blocks.SPRUCE_LOG;
                case Jungle:
                    return Blocks.JUNGLE_LOG;
                case Acacia:
                    return Blocks.ACACIA_LOG;
                case DarkOak:
                    return Blocks.DARK_OAK_LOG;
                default:
                    return null;
            }
        }

        public Block getLeaves() {
            switch (this) {
                case Oak:
                    return Blocks.OAK_LEAVES;
                case Birch:
                    return Blocks.BIRCH_LEAVES;
                case Spruce:
                    return Blocks.SPRUCE_LEAVES;
                case Jungle:
                    return Blocks.JUNGLE_LEAVES;
                case Acacia:
                    return Blocks.ACACIA_LEAVES;
                case DarkOak:
                    return Blocks.DARK_OAK_LEAVES;
                default:
                    return null;
            }
        }
    }

    public final Set<BlockPos> blocks = new HashSet<>();
    public final boolean isTree;
    public final TreeType type;

    private final Set<BlockPos> checked = new HashSet<>();
    private boolean hasTop = false;

    public TreeDefinition(World world, BlockPos pos) {
        type = TreeType.getTreeType(world.getBlockState(pos).getBlock());
        defineTree(world, pos);
        isTree = hasTop;
    }

    private void defineTree(World world, BlockPos pos) {
        if (checked.contains(pos))
            return;
        checked.add(pos);

        BlockState state = world.getBlockState(pos);

        if (state.getBlock() instanceof LeavesBlock && TreeType.getTreeType(state.getBlock()) == type) {
            hasTop |= !state.get(LeavesBlock.PERSISTENT);
            return;
        }

        if (state.getBlock() instanceof LogBlock && TreeType.getTreeType(state.getBlock()) == type) {
            blocks.add(pos);
            for (int x = -1; x <= 1; x++)
                for (int y = 0; y <= 1; y++)
                    for (int z = -1; z <= 1; z++)
                        defineTree(world, pos.add(x, y, z));
        }
    }
}
