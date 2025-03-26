package com.peeko32213.unusualprehistory.core.registry.world;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.Music;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@Mod.EventBusSubscriber(modid = UnusualPrehistory.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class UPBiomes {
    public static final ResourceKey<Biome> PETRIFIED_FOREST = createKey("petrified_forest");

    public static final List<ResourceKey<Biome>> NATURAL_BIOMES = List.of(
            PETRIFIED_FOREST
    );

    public static void bootstrap(BootstapContext<Biome> context) {
        HolderGetter<PlacedFeature> features = context.lookup(Registries.PLACED_FEATURE);
        HolderGetter<ConfiguredWorldCarver<?>> carvers = context.lookup(Registries.CONFIGURED_CARVER);

        context.register(PETRIFIED_FOREST, petrifiedForest(features, carvers));
    }

    public static ResourceKey<Biome> createKey(String name) {
        return ResourceKey.create(Registries.BIOME, new ResourceLocation(UnusualPrehistory.MODID, name));
    }

    private static Biome petrifiedForest(HolderGetter<PlacedFeature> features, HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        BiomeGenerationSettings.Builder generation = new BiomeGenerationSettings.Builder(features, carvers);
        UPBiomeGeneration.petrifiedForest(generation);

        MobSpawnSettings.Builder spawns = new MobSpawnSettings.Builder();
        BiomeDefaultFeatures.commonSpawns(spawns);
        spawns.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.COW, 4, 2, 3));

        return biome(true, 0.4F, 0.3F, 4159204, 329011, spawns, generation, null);
    }

    private static Biome biome(boolean precipitation, float temperature, float downfall, int waterColor, int waterFogColor, MobSpawnSettings.Builder spawns, BiomeGenerationSettings.Builder generation, @Nullable Music music) {
        return (new Biome.BiomeBuilder()).hasPrecipitation(precipitation).temperature(temperature).downfall(downfall).specialEffects((new BiomeSpecialEffects.Builder()).waterColor(waterColor).waterFogColor(waterFogColor).fogColor(12638463).skyColor(calculateSkyColor(temperature)).ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS).backgroundMusic(music).build()).mobSpawnSettings(spawns.build()).generationSettings(generation.build()).build();
    }

    private static Biome biome(boolean precipitation, float temperature, float downfall, int foliageColor, int waterColor, int waterFogColor, MobSpawnSettings.Builder spawns, BiomeGenerationSettings.Builder generation, @Nullable Music music) {
        return (new Biome.BiomeBuilder()).hasPrecipitation(precipitation).temperature(temperature).downfall(downfall).specialEffects((new BiomeSpecialEffects.Builder()).foliageColorOverride(foliageColor).waterColor(waterColor).waterFogColor(waterFogColor).fogColor(12638463).skyColor(calculateSkyColor(temperature)).ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS).backgroundMusic(music).build()).mobSpawnSettings(spawns.build()).generationSettings(generation.build()).build();
    }

    private static Biome biome(boolean precipitation, float temperature, float downfall, int grassColor, int foliageColor, int waterColor, int waterFogColor, int fogColor, MobSpawnSettings.Builder spawns, BiomeGenerationSettings.Builder generation, @Nullable Music music) {
        return (new Biome.BiomeBuilder()).hasPrecipitation(precipitation).temperature(temperature).downfall(downfall).specialEffects((new BiomeSpecialEffects.Builder()).grassColorOverride(grassColor).foliageColorOverride(foliageColor).waterColor(waterColor).waterFogColor(waterFogColor).fogColor(fogColor).skyColor(calculateSkyColor(temperature)).ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS).backgroundMusic(music).build()).mobSpawnSettings(spawns.build()).generationSettings(generation.build()).build();
    }

    private static int calculateSkyColor(float temperature) {
        float clampedTemp = Mth.clamp(temperature / 3.0F, -1.0F, 1.0F);
        return Mth.hsvToRgb(0.62222224F - clampedTemp * 0.05F, 0.5F + clampedTemp * 0.1F, 1.0F);
    }
}
