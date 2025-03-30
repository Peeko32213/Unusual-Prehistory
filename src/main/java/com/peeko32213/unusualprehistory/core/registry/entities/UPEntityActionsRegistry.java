package com.peeko32213.unusualprehistory.core.registry.entities;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.mojang.serialization.Codec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.entity.LivingEntity;

import java.util.function.Consumer;

import static com.peeko32213.unusualprehistory.UnusualPrehistory.modPrefix;

public class UPEntityActionsRegistry {
    private static final BiMap<ResourceLocation, Consumer<LivingEntity>> ACTIONS = HashBiMap.create();

    public static Consumer<LivingEntity> DEFAULT = entity -> {};

    public static void register() {
        register(modPrefix("default"), DEFAULT);
    }

    public static void register(ResourceLocation id, Consumer<LivingEntity> action) {
        ACTIONS.put(id, action);
    }

    public static Consumer<LivingEntity> getAction(ResourceLocation id) {
        return ACTIONS.getOrDefault(id, entity -> {});
    }

    public static ResourceLocation getId(Consumer<LivingEntity> action) {
        return ACTIONS.inverse().get(action);
    }

    public static final Codec<Consumer<LivingEntity>> CODEC = ExtraCodecs.stringResolverCodec(
            id -> ACTIONS.inverse().get(id).toString(),
            key -> ACTIONS.get(new ResourceLocation(key))
    );
}