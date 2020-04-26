package me.uquark.miscellaneous.mixin;

import com.google.common.collect.ImmutableMap;
import me.uquark.miscellaneous.item.WrenchItem;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.AbstractState;
import net.minecraft.state.State;
import net.minecraft.state.property.Property;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockState.class)
public abstract class BlockStateMixin extends AbstractState<Block, BlockState> implements State<BlockState> {
    protected BlockStateMixin(Block owner, ImmutableMap<Property<?>, Comparable<?>> entries) {
        super(owner, entries);
    }

    @Inject(method = "onUse", at = @At("HEAD"), cancellable = true)
    public void onUse(World world, PlayerEntity player, Hand hand, BlockHitResult hit, CallbackInfoReturnable<ActionResult> info) {
        if (player.getMainHandStack().getItem() instanceof WrenchItem) {
            info.setReturnValue(ActionResult.PASS);
            info.cancel();
        }
    }
}