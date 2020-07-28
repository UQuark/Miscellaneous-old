package me.uquark.miscellaneous.effect;

import me.uquark.miscellaneous.Miscellaneous;
import me.uquark.quarkcore.effect.AbstractStatusEffect;
import me.uquark.quarkcore.reflect.ReflectionHelper;
import net.minecraft.block.entity.BeaconBlockEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.entity.effect.StatusEffects;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Collectors;

public class BoundInventoryEffect extends AbstractStatusEffect {
    protected BoundInventoryEffect() {
        super(Miscellaneous.modid, "bound_inventory", StatusEffectType.BENEFICIAL, 0xf2147d);
    }

    @Override
    public void register() {
        super.register();
        try {
            BeaconBlockEntity.EFFECTS_BY_LEVEL = new StatusEffect[][]{{StatusEffects.SPEED, StatusEffects.HASTE}, {StatusEffects.RESISTANCE, StatusEffects.JUMP_BOOST}, {StatusEffects.STRENGTH}, {StatusEffects.REGENERATION, Effects.BOUND_INVENTORY_EFFECT}};
            BeaconBlockEntity.EFFECTS = Arrays.stream(BeaconBlockEntity.EFFECTS_BY_LEVEL).flatMap(Arrays::stream).collect(Collectors.toSet());
        } catch (Exception e) {
            Miscellaneous.LOGGER.error("Failed to add beacon effects");
            e.printStackTrace();
        }
    }
}
