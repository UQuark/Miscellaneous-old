package me.uquark.miscellaneous.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.entity.Entity;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Iterator;

@Mixin(FarmlandBlock.class)
public abstract class FarmlandBlockMixin extends Block {
    public FarmlandBlockMixin(Settings settings) {
        super(settings);
    }

    private static boolean isWaterNearby(WorldView worldView, BlockPos pos) {
        Iterator<BlockPos> var2 = BlockPos.iterate(pos.add(-4, 0, -4), pos.add(4, 1, 4)).iterator();

        BlockPos blockPos;
        do {
            if (!var2.hasNext()) {
                return false;
            }

            blockPos = (BlockPos)var2.next();
        } while(!worldView.getFluidState(blockPos).matches(FluidTags.WATER));

        return true;
    }

    @Inject(method = "onLandedUpon", at = @At("HEAD"), cancellable = true)
    public void onLandedUpon(World world, BlockPos pos, Entity entity, float distance, CallbackInfo info) {
        if (isWaterNearby(world, pos)) {
            super.onLandedUpon(world, pos, entity, distance);
            info.cancel();
        }
    }
}
