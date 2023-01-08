package com.peeko32213.unusualprehistory.core.registry;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import net.minecraft.core.Holder;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import java.util.List;

public class UPConfiguredFeatures {

    public static void init() {
    }

    public static final Holder<ConfiguredFeature<OreConfiguration, ?>> STONE_FOSSIL_ORE = registerConfiguredFeature("stone_fossil_ore", Feature.ORE, new OreConfiguration(List.of(OreConfiguration.target(new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES), UPBlocks.STONE_FOSSIL.get().defaultBlockState()), OreConfiguration.target(new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES), UPBlocks.STONE_FOSSIL.get().defaultBlockState())), 9));
    public static final Holder<ConfiguredFeature<OreConfiguration, ?>> AMBER_FOSSIL_ORE = registerConfiguredFeature("amber_fossil_ore", Feature.ORE, new OreConfiguration(List.of(OreConfiguration.target(new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES), UPBlocks.STONE_AMBER_FOSSIL.get().defaultBlockState()), OreConfiguration.target(new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES), UPBlocks.STONE_AMBER_FOSSIL.get().defaultBlockState())), 2));
    public static final Holder<ConfiguredFeature<OreConfiguration, ?>> DEEPSLATE_FOSSIL_ORE = registerConfiguredFeature("deepslate_fossil_ore", Feature.ORE, new OreConfiguration(List.of(OreConfiguration.target(new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES), UPBlocks.DEEPSLATE_FOSSIL.get().defaultBlockState()), OreConfiguration.target(new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES), UPBlocks.DEEPSLATE_FOSSIL.get().defaultBlockState())), 4));
    public static final Holder<ConfiguredFeature<OreConfiguration, ?>> PLANT_FOSSIL_ORE = registerConfiguredFeature("plant_fossil_ore", Feature.ORE, new OreConfiguration(List.of(OreConfiguration.target(new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES), UPBlocks.PLANT_FOSSIL.get().defaultBlockState()), OreConfiguration.target(new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES), UPBlocks.PLANT_FOSSIL.get().defaultBlockState())), 11));

    public static <FC extends FeatureConfiguration, F extends Feature<FC>> Holder<ConfiguredFeature<FC, ?>> registerConfiguredFeature(String id, F feature, FC featureConfiguration) {
        ResourceLocation resourceLocation = new ResourceLocation(UnusualPrehistory.MODID, id);

        if (BuiltinRegistries.CONFIGURED_FEATURE.keySet().contains(resourceLocation))
            throw new IllegalStateException("Placed Feature ID: \"" + resourceLocation + "\" already exists in the Placed Features registry!");

        return BuiltinRegistries.registerExact(BuiltinRegistries.CONFIGURED_FEATURE, resourceLocation.toString(), new ConfiguredFeature<>(feature, featureConfiguration));
    }

}
