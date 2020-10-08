package me.uquark.miscellaneous.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.Npc;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.village.Merchant;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(MerchantEntity.class)
public abstract class AbstractTraderEntityMixin extends PassiveEntity implements Npc, Merchant {
    protected AbstractTraderEntityMixin(EntityType<? extends PassiveEntity> type, World world) {
        super(type, world);
    }

    /**
     * @author UQuark
     * @reason Villagers should be leashable. SLAVERYYYYY
     */
    @Overwrite
    public boolean canBeLeashedBy(PlayerEntity player) {
        return true;
    }
}
