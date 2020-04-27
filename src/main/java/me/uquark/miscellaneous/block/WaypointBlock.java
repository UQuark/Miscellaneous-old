package me.uquark.miscellaneous.block;

import me.uquark.miscellaneous.Miscellaneous;
import me.uquark.quarkcore.block.AbstractBlock;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WaypointBlock extends AbstractBlock {
    protected WaypointBlock() {
        super(Miscellaneous.modid, "waypoint_block", FabricBlockSettings.copy(Blocks.BEACON), new Item.Settings().group(ItemGroup.TRANSPORTATION));
    }

    @Override
    public void register() {
        super.register();
    }

    public void registerClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(this, RenderLayer.getTranslucent());
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient)
            return ActionResult.FAIL;
        ((ServerWorld) world).addLightning(new LightningEntity(world, pos.getX(), pos.getY(), pos.getZ(), false));
        return ActionResult.SUCCESS;
    }
}
