package com.peeko32213.unusualprehistory.data.server.world;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.core.registry.world.UPConfiguredFeatures;
import com.peeko32213.unusualprehistory.core.registry.world.UPPlacedFeatures;
import com.peeko32213.unusualprehistory.data.UPDatagenUtils;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.function.Supplier;

public class UPBiomeModifiersProvider {

    private static ForgeBiomeModifiers.AddFeaturesBiomeModifier addFeatureModifier(BootstapContext<BiomeModifier> context, ResourceKey<PlacedFeature> placedSet, TagKey<Biome> biomeTag, GenerationStep.Decoration decoration) {
        return new ForgeBiomeModifiers.AddFeaturesBiomeModifier (context.lookup(Registries.BIOME).getOrThrow(biomeTag),  UPDatagenUtils.getPlacedHolderSet(context, placedSet), decoration);
    }

    private static ForgeBiomeModifiers.AddSpawnsBiomeModifier addSingleSpawnModifier(BootstapContext<BiomeModifier> context, TagKey<Biome> biomeTag, EntityType<?> entity, int weight, int minCount, int maxCount) {
        return ForgeBiomeModifiers.AddSpawnsBiomeModifier.singleSpawn(context.lookup(Registries.BIOME).getOrThrow(biomeTag), new MobSpawnSettings.SpawnerData(entity, weight, minCount, maxCount));
    }

    private static void register(BootstapContext<BiomeModifier> context, String name, Supplier<? extends BiomeModifier> modifier) {
        context.register(ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, UnusualPrehistory.modPrefix(name)), modifier.get());
    }
}
