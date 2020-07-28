package me.uquark.miscellaneous.mixin;

import me.uquark.miscellaneous.effect.Effects;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
    protected PlayerEntityMixin(EntityType<? extends LivingEntity> type, World world) {
        super(type, world);
    }

    /**
     * @author UQuark
     * @reason Bound Inventory
     */
    @Overwrite
    public boolean shouldAlwaysDropXp() {
        return getStatusEffect(Effects.BOUND_INVENTORY_EFFECT) == null;
    }
}
