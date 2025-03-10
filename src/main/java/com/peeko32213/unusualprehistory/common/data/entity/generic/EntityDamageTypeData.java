package com.peeko32213.unusualprehistory.common.data.entity.generic;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EntityDamageTypeData {
    public static final Codec<EntityDamageTypeData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ResourceKey.codec(Registries.DAMAGE_TYPE).listOf().fieldOf("is_invulnerable_to").forGetter(EntityDamageTypeData::getIsInvulnerableToRk)
    ).apply(instance, EntityDamageTypeData::new));

    private final List<ResourceKey<DamageType>> isInvulnerableToRk;
    private final List<DamageType> isInvulnerableTo = new ArrayList<>();

    public EntityDamageTypeData(List<ResourceKey<DamageType>> isInvulnerableToRk) {
        this.isInvulnerableToRk = isInvulnerableToRk;
    }

    public List<ResourceKey<DamageType>> getIsInvulnerableToRk() {
        return isInvulnerableToRk;
    }

    public List<DamageType> getIsInvulnerableTo(Level level) {
        if (isInvulnerableTo.isEmpty()) {
            Optional<HolderLookup.RegistryLookup<DamageType>> registryOpt =
                    level.registryAccess().lookup(Registries.DAMAGE_TYPE);

            registryOpt.ifPresent(registry -> {
                for (ResourceKey<DamageType> rk : isInvulnerableToRk) {
                    registry.get(rk).ifPresent(damageTypeReference -> isInvulnerableTo.add(damageTypeReference.get()));
                }
            });
        }
        return isInvulnerableTo;
    }

    public static EntityDamageTypeData getDefaultInstance () {
        return new EntityDamageTypeData(List.of(DamageTypes.GENERIC));
    }
}
