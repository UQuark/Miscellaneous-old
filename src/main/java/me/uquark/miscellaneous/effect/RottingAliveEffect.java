package me.uquark.miscellaneous.effect;

import me.uquark.miscellaneous.Miscellaneous;
import me.uquark.quarkcore.effect.AbstractStatusEffect;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.server.world.ServerWorld;

public class RottingAliveEffect extends AbstractStatusEffect {
    protected RottingAliveEffect() {
        super(Miscellaneous.modid, "rotting_alive", StatusEffectType.HARMFUL, 0x653b3b);
    }

    @Override
    public boolean isInstant() {
        return true;
    }

    @Override
    public void onApplied(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        applyInstantEffect(null, null, entity, 0, 0);
    }

    @Override
    public void applyInstantEffect(Entity source, Entity attacker, LivingEntity target, int amplifier, double proximity) {
        if (target instanceof ZombieEntity || target instanceof AbstractSkeletonEntity || target instanceof WitherEntity || target instanceof ZombieHorseEntity || target instanceof SkeletonHorseEntity)
            return;
        if (!(target instanceof VillagerEntity)) {
            target.addStatusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 60*20, 0));
            target.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 30*20, 0));
            return;
        }
        VillagerEntity villagerEntity = (VillagerEntity)target;
        ZombieVillagerEntity zombieVillagerEntity = (ZombieVillagerEntity) EntityType.ZOMBIE_VILLAGER.create(villagerEntity.world);
        zombieVillagerEntity.copyPositionAndRotation(villagerEntity);
        villagerEntity.remove(Entity.RemovalReason.DISCARDED);
        zombieVillagerEntity.initialize((ServerWorld) villagerEntity.world, villagerEntity.world.getLocalDifficulty(zombieVillagerEntity.getBlockPos()), SpawnReason.CONVERSION, null, null);
        zombieVillagerEntity.setVillagerData(villagerEntity.getVillagerData());
//        zombieVillagerEntity.method_21649((Tag)villagerEntity.method_21651().serialize(NbtOps.INSTANCE).getValue());
        zombieVillagerEntity.setOfferData(villagerEntity.getOffers().toTag());
        zombieVillagerEntity.setXp(villagerEntity.getExperience());
        zombieVillagerEntity.setBaby(villagerEntity.isBaby());
        zombieVillagerEntity.setAiDisabled(villagerEntity.isAiDisabled());
        if (villagerEntity.hasCustomName()) {
            zombieVillagerEntity.setCustomName(villagerEntity.getCustomName());
            zombieVillagerEntity.setCustomNameVisible(villagerEntity.isCustomNameVisible());
        }

        if (villagerEntity.isPersistent()) {
            zombieVillagerEntity.setPersistent();
        }

        zombieVillagerEntity.setInvulnerable(villagerEntity.isInvulnerable());
        villagerEntity.world.spawnEntity(zombieVillagerEntity);
//        villagerEntity.world.playLevelEvent((PlayerEntity)null, 1026, new BlockPos(villagerEntity), 0);
    }
}
