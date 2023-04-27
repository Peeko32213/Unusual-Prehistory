package com.peeko32213.unusualprehistory.core.registry;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.world.feature.tree.GinkgoFoliagePlacer;
import net.minecraft.core.Registry;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class UPConfiguredFeatures {

    public static List<String> configuredFeatureList = new ArrayList<>();

    public static final DeferredRegister<ConfiguredFeature<?,?>> CONFIGURED_FEATURES = DeferredRegister.create(Registry.CONFIGURED_FEATURE_REGISTRY, UnusualPrehistory.MODID);

    private static final String PETRIFIED_WOOD_FOREST_FEATURE_NAME = "petrified_wood_forest_feature_name";
    public static final RegistryObject<ConfiguredFeature<?, ?>> STONE_FOSSIL_ORE = registerConfiguredFeature("stone_fossil_ore", () -> new ConfiguredFeature<>( Feature.ORE, new OreConfiguration(List.of(OreConfiguration.target(new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES), UPBlocks.STONE_FOSSIL.get().defaultBlockState()), OreConfiguration.target(new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES), UPBlocks.STONE_FOSSIL.get().defaultBlockState())), 9)));
    public static final RegistryObject<ConfiguredFeature<?, ?>> AMBER_FOSSIL_ORE = registerConfiguredFeature("amber_fossil_ore",  () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(List.of(OreConfiguration.target(new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES), UPBlocks.STONE_AMBER_FOSSIL.get().defaultBlockState()), OreConfiguration.target(new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES), UPBlocks.STONE_AMBER_FOSSIL.get().defaultBlockState())), 5)));
    //public static final RegistryObject<ConfiguredFeature<?, ?>> DEEPSLATE_AMBER_FOSSIL_ORE = registerConfiguredFeature("deepslate_amber_fossil_ore", () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(List.of(OreConfiguration.target(new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES), UPBlocks.DEEPSLATE_AMBER_FOSSIL.get().defaultBlockState()), OreConfiguration.target(new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES), UPBlocks.DEEPSLATE_AMBER_FOSSIL.get().defaultBlockState())), 4)));

    public static final RegistryObject<ConfiguredFeature<?, ?>> DEEPSLATE_FOSSIL_ORE = registerConfiguredFeature("deepslate_fossil_ore", () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(List.of(OreConfiguration.target(new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES), UPBlocks.DEEPSLATE_FOSSIL.get().defaultBlockState()), OreConfiguration.target(new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES), UPBlocks.DEEPSLATE_FOSSIL.get().defaultBlockState())), 4)));
    public static final RegistryObject<ConfiguredFeature<?, ?>> PLANT_FOSSIL_ORE = registerConfiguredFeature("plant_fossil_ore",() -> new ConfiguredFeature<>( Feature.ORE, new OreConfiguration(List.of(OreConfiguration.target(new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES), UPBlocks.PLANT_FOSSIL.get().defaultBlockState()), OreConfiguration.target(new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES), UPBlocks.PLANT_FOSSIL.get().defaultBlockState())), 11)));
    public static final RegistryObject<ConfiguredFeature<?, ?>> DEEPSLATE_PLANT_FOSSIL_ORE = registerConfiguredFeature("deepslate_plant_fossil_ore", () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(List.of(OreConfiguration.target(new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES), UPBlocks.DEEPSLATE_PLANT_FOSSIL.get().defaultBlockState()), OreConfiguration.target(new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES), UPBlocks.DEEPSLATE_PLANT_FOSSIL.get().defaultBlockState())), 4)));
    public static final RegistryObject<ConfiguredFeature<?, ?>> GINKGO_TREE =
            registerConfiguredFeature("ginkgo", () -> new ConfiguredFeature<>(Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                    BlockStateProvider.simple(UPBlocks.GINKGO_LOG.get()),
                    new StraightTrunkPlacer(9, 5, 0),
                    BlockStateProvider.simple(UPBlocks.GINKGO_LEAVES.get()),
                    new GinkgoFoliagePlacer( 2F, 4.5F, ConstantInt.of(0), 1, 0, 0.5f, 2),
                    new TwoLayersFeatureSize(6, 3, 5)).build()));

    public static final RegistryObject<ConfiguredFeature<?, ?>> CONFIGURED_PETRIFIED_WOOD_FOREST= registerConfiguredFeature(PETRIFIED_WOOD_FOREST_FEATURE_NAME, () -> new ConfiguredFeature<>(UPFeatures.PETRIFIED_WOOD_FOREST.get(), new NoneFeatureConfiguration()));



    public static RegistryObject<ConfiguredFeature<?, ?>> registerConfiguredFeature(String name, Supplier<ConfiguredFeature<?, ?>> feature) {
        configuredFeatureList.add(name);
        return CONFIGURED_FEATURES.register(name, feature);
    }

}
