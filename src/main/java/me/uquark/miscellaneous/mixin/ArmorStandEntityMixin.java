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
    public abstract void method_6908(DamageSource damageSource);

    @Shadow
    public abstract boolean shouldShowArms();

    @Overwrite
    private void method_6924(DamageSource damageSource) {
        if (shouldShowArms())
            Block.dropStack(this.world, new BlockPos(this), new ItemStack(me.uquark.miscellaneous.item.Items.ARMOR_STAND_WITH_ARMS_ITEM));
        else
            Block.dropStack(this.world, new BlockPos(this), new ItemStack(Items.ARMOR_STAND));
        this.method_6908(damageSource);
    }
}
