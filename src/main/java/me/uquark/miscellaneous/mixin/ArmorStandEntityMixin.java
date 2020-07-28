package me.uquark.miscellaneous.mixin;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ArmorStandEntity.class)
public abstract class ArmorStandEntityMixin extends LivingEntity {
    protected ArmorStandEntityMixin(EntityType<? extends LivingEntity> type, World world) {
        super(type, world);
    }

    @Shadow
    protected abstract void onBreak(DamageSource damageSource);

    @Shadow
    public abstract boolean shouldShowArms();

    /**
     * @author UQuark
     * @reason Armor Stand with Arms
     */
    @Overwrite
    private void breakAndDropItem(DamageSource damageSource) {
        if (shouldShowArms())
            Block.dropStack(this.world, getBlockPos(), new ItemStack(me.uquark.miscellaneous.item.Items.ARMOR_STAND_WITH_ARMS_ITEM));
        else
            Block.dropStack(this.world, getBlockPos(), new ItemStack(Items.ARMOR_STAND));
        this.onBreak(damageSource);
    }
}
