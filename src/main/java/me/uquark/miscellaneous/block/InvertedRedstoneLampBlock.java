package me.uquark.miscellaneous.block;

import me.uquark.miscellaneous.Miscellaneous;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.RedstoneLampBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class InvertedRedstoneLampBlock extends RedstoneLampBlock {
    public static final String name = "inverted_redstone_lamp";
    public BlockItem blockItem;
    public Identifier id;

    public InvertedRedstoneLampBlock() {
        super(Settings.copy(Blocks.REDSTONE_LAMP));
        blockItem = new BlockItem(this, new Item.Settings().group(ItemGroup.REDSTONE));
        setDefaultState(getDefaultState().with(LIT, true));
        id = new Identifier(Miscellaneous.modid, "inverted_redstone_lamp");
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(LIT, !ctx.getWorld().isReceivingRedstonePower(ctx.getBlockPos()));
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos neighborPos, boolean moved) {
        if (!world.isClient) {
            boolean isLit = state.get(LIT);
            if (isLit == world.isReceivingRedstonePower(pos)) {
                world.setBlockState(pos, state.cycle(LIT), 2);
            }
        }
    }

    public void register() {
        Registry.register(Registry.BLOCK, id, this);
        Registry.register(Registry.ITEM, id, blockItem);
    }
}