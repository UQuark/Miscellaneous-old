package me.uquark.miscellaneous.util;

import net.minecraft.block.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import sun.reflect.generics.tree.Tree;

import java.util.HashSet;
import java.util.Set;

public class TreeDefinition {
    public enum TreeType {
        Oak(Blocks.OAK_LOG, Blocks.OAK_LEAVES),
        Birch(Blocks.BIRCH_LOG, Blocks.BIRCH_LEAVES),
        Spruce(Blocks.SPRUCE_LOG, Blocks.SPRUCE_LEAVES),
        Jungle(Blocks.JUNGLE_LOG, Blocks.JUNGLE_LEAVES),
        Acacia(Blocks.ACACIA_LOG, Blocks.ACACIA_LEAVES),
        DarkOak(Blocks.DARK_OAK_LOG, Blocks.DARK_OAK_LEAVES),
        Crimson(Blocks.CRIMSON_STEM, Blocks.NETHER_WART_BLOCK),
        Warped(Blocks.WARPED_STEM, Blocks.WARPED_WART_BLOCK),
        Undefined(null, null);

        private final Block log, leaves;

        TreeType(Block log, Block leaves) {
            this.log = log;
            this.leaves = leaves;
        }

        public static TreeType getTreeType(Block block) {
            for (TreeType t : TreeType.values()) {
                if (block == t.log || block == t.leaves)
                    return t;
            }
            return Undefined;
        }

        public Block getLog() {
            return log;
        }

        public Block getLeaves() {
            return leaves;
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
        TreeType treeType = TreeType.getTreeType(state.getBlock());
        if (treeType == TreeType.Undefined)
            return;

        if (state.getBlock() == treeType.getLeaves() && treeType == type) {
            if (treeType == TreeType.Warped || treeType == TreeType.Crimson)
                hasTop = true;
            else
                hasTop |= !state.get(LeavesBlock.PERSISTENT);
            return;
        }

        if (state.getBlock() == treeType.getLog() && treeType == type) {
            blocks.add(pos);
            for (int x = -1; x <= 1; x++)
                for (int y = 0; y <= 1; y++)
                    for (int z = -1; z <= 1; z++)
                        defineTree(world, pos.add(x, y, z));
        }
    }
}
