package com.peeko32213.unusualprehistory.common.data.attack;

import com.mojang.serialization.Codec;
import com.peeko32213.unusualprehistory.core.registry.UPRegistry;
import com.scouter.goalsmith.data.GSRegistries;
import com.scouter.goalsmith.data.PredicateCodec;
import net.minecraft.core.Holder;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;

import java.util.function.Function;
import java.util.function.Predicate;

public interface EntityAttack {
    Codec<EntityAttack> DIRECT_CODEC = ExtraCodecs.lazyInitializedCodec(() -> UPRegistry.ENTITY_ATTACK_TYPE_SERIALIZER_SUPPLIER.get().getCodec()).dispatch(EntityAttack::codec, Function.identity());

    Codec<Holder<EntityAttack>> REFERENCE_CODEC = RegistryFileCodec.create(UPRegistry.Keys.ENTITY_ATTACKS, DIRECT_CODEC);
    void performAttack(PathfinderMob entity);
    Codec<? extends EntityAttack> codec();
}
