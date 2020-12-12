package me.uquark.miscellaneous.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(SheepEntity.class)
public abstract class SheepEntityMixin extends AnimalEntity {
    protected SheepEntityMixin(EntityType<? extends AnimalEntity> type, World world) {
        super(type, world);
    }

    @Shadow
    public abstract void setSheared(boolean bl);

    @Override
    protected void eat(PlayerEntity player, ItemStack stack) {
        if (!player.getAbilities().creativeMode) {
            stack.decrement(1);
        }

        setSheared(false);
    }
}
