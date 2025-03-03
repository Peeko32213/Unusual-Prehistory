package com.peeko32213.unusualprehistory.core.registry;

import com.google.common.collect.ImmutableList;
import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.world.feature.tree.FoxiiFoliagePlacer;
import com.peeko32213.unusualprehistory.common.world.feature.tree.GinkgoFoliagePlacer;
import com.peeko32213.unusualprehistory.common.world.feature.tree.trunkplacer.GiantTrunkPlacerWithRoots;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.DiskConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.RuleBasedBlockStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.AlterGroundDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.LeaveVineDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TrunkVineDecorator;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class UPConfiguredFeatures {

    public static List<String> configuredFeatureList = new ArrayList<>();

    public static final DeferredRegister<ConfiguredFeature<?,?>> CONFIGURED_FEATURES = DeferredRegister.create(Registries.CONFIGURED_FEATURE, UnusualPrehistory.MODID);

    private static final String PETRIFIED_WOOD_FOREST_FEATURE_NAME = "petrified_wood_forest";

    private static final String TAR_PIT_FEATURE_NAME = "tar_pit";
    private static final String ICE_FOSSIL_ICEBERG_FEATURE_NAME = "fossil_iceberg";

    public static final RegistryObject<ConfiguredFeature<?, ?>> STONE_FOSSIL_ORE = registerConfiguredFeature("stone_fossil_ore", () -> new ConfiguredFeature<>( Feature.ORE, new OreConfiguration(List.of(OreConfiguration.target(new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES), UPBlocks.STONE_FOSSIL.get().defaultBlockState()), OreConfiguration.target(new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES), UPBlocks.STONE_FOSSIL.get().defaultBlockState())), 9)));
    public static final RegistryObject<ConfiguredFeature<?, ?>> DEEPSLATE_FOSSIL_ORE = registerConfiguredFeature("deepslate_fossil_ore", () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(List.of(OreConfiguration.target(new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES), UPBlocks.DEEPSLATE_FOSSIL.get().defaultBlockState()), OreConfiguration.target(new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES), UPBlocks.DEEPSLATE_FOSSIL.get().defaultBlockState())), 4)));

    public static final RegistryObject<ConfiguredFeature<?, ?>> AMBER_FOSSIL_ORE = registerConfiguredFeature("amber_fossil_ore",  () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(List.of(OreConfiguration.target(new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES), UPBlocks.STONE_AMBER_FOSSIL.get().defaultBlockState()), OreConfiguration.target(new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES), UPBlocks.STONE_AMBER_FOSSIL.get().defaultBlockState())), 5)));

    public static final RegistryObject<ConfiguredFeature<?, ?>> PLANT_FOSSIL_ORE = registerConfiguredFeature("plant_fossil_ore",() -> new ConfiguredFeature<>( Feature.ORE, new OreConfiguration(List.of(OreConfiguration.target(new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES), UPBlocks.PLANT_FOSSIL.get().defaultBlockState()), OreConfiguration.target(new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES), UPBlocks.PLANT_FOSSIL.get().defaultBlockState())), 11)));
    public static final RegistryObject<ConfiguredFeature<?, ?>> DEEPSLATE_PLANT_FOSSIL_ORE = registerConfiguredFeature("deepslate_plant_fossil_ore", () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(List.of(OreConfiguration.target(new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES), UPBlocks.DEEPSLATE_PLANT_FOSSIL.get().defaultBlockState()), OreConfiguration.target(new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES), UPBlocks.DEEPSLATE_PLANT_FOSSIL.get().defaultBlockState())), 4)));

    public static final RegistryObject<ConfiguredFeature<?, ?>> GINKGO_TREE =
            registerConfiguredFeature("ginkgo", () -> new ConfiguredFeature<>(Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                    BlockStateProvider.simple(UPBlocks.GINKGO_LOG.get()),
                    new StraightTrunkPlacer(9, 5, 0),
                    BlockStateProvider.simple(UPBlocks.GINKGO_LEAVES.get()),
                    new GinkgoFoliagePlacer( 2F, 4.5F, ConstantInt.of(0), 1, 0, 0.5f, 2),
                    new TwoLayersFeatureSize(1, 1, 2))
                    .dirt(BlockStateProvider.simple(Blocks.DIAMOND_BLOCK))
                    .build()));


    public static final RegistryObject<ConfiguredFeature<?, ?>> FOXII_TREE =
            registerConfiguredFeature("foxii", () -> new ConfiguredFeature<>(Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                    BlockStateProvider.simple(UPBlocks.FOXXI_LOG.get()),
                    new GiantTrunkPlacerWithRoots(32, 13, 10),
                    BlockStateProvider.simple(UPBlocks.FOXXI_LEAVES.get()),
                    new FoxiiFoliagePlacer( ConstantInt.of(0), ConstantInt.of(0), UniformInt.of(2, 3)),
                    new TwoLayersFeatureSize(1, 1, 2))
                    .decorators(ImmutableList.of(new AlterGroundDecorator(BlockStateProvider.simple(Blocks.PODZOL)))).build())
            );

    public static final RegistryObject<ConfiguredFeature<?, ?>> DRYOPHYLLUM =
            registerConfiguredFeature("dryo", () -> new ConfiguredFeature<>(Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                    BlockStateProvider.simple(UPBlocks.DRYO_LOG.get()),
                    new StraightTrunkPlacer(3, 2, 1),
                    BlockStateProvider.simple(UPBlocks.DRYO_LEAVES.get()),
                    new BlobFoliagePlacer(ConstantInt.of(3), ConstantInt.of(0), 3),
                    new TwoLayersFeatureSize(1, 1, 2))
                    .decorators(ImmutableList.of(TrunkVineDecorator.INSTANCE, new LeaveVineDecorator(0.25F))).build()));

    public static final RegistryObject<ConfiguredFeature<?, ?>> CONFIGURED_PETRIFIED_WOOD_FOREST= registerConfiguredFeature(PETRIFIED_WOOD_FOREST_FEATURE_NAME, () -> new ConfiguredFeature<>(UPFeatures.PETRIFIED_WOOD_FOREST.get(), new NoneFeatureConfiguration()));
    public static final RegistryObject<ConfiguredFeature<?, ?>> CONFIGURED_TAR_PIT = registerConfiguredFeature(TAR_PIT_FEATURE_NAME, () -> new ConfiguredFeature<>(UPFeatures.TAR_PIT.get(), new NoneFeatureConfiguration()));
    public static final RegistryObject<ConfiguredFeature<?, ?>> CONFIGURED_ICE_FOSSIL_ICEBERG = registerConfiguredFeature(ICE_FOSSIL_ICEBERG_FEATURE_NAME, () -> new ConfiguredFeature<>(UPFeatures.ICE_FOSSIL_ICEBERG.get(), new NoneFeatureConfiguration()));

    public static final RegistryObject<ConfiguredFeature<?, ?>> STONE_OPAL_ORE = registerConfiguredFeature("stone_opal_ore",  () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(List.of(OreConfiguration.target(new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES), UPBlocks.STONE_OPAL_FOSSIL.get().defaultBlockState()), OreConfiguration.target(new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES), UPBlocks.STONE_AMBER_FOSSIL.get().defaultBlockState())), 3)));

    public static final RegistryObject<ConfiguredFeature<?, ?>> DEEPSLATE_OPAL_ORE = registerConfiguredFeature("deepslate_opal_ore", () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(List.of(OreConfiguration.target(new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES), UPBlocks.DEEPSLATE_OPAL_FOSSIL.get().defaultBlockState()), OreConfiguration.target(new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES), UPBlocks.DEEPSLATE_OPAL_FOSSIL.get().defaultBlockState())), 4)));
    public static final RegistryObject<ConfiguredFeature<?, ?>> PERMAFROST_PATCH = registerConfiguredFeature("permafrost_patch",  () -> new ConfiguredFeature<>(Feature.DISK, new DiskConfiguration(RuleBasedBlockStateProvider.simple(UPBlocks.PERMAFROST.get()), BlockPredicate.matchesBlocks(List.of(Blocks.STONE, Blocks.DEEPSLATE)), UniformInt.of(2, 3), 1)));
    public static final RegistryObject<ConfiguredFeature<?, ?>> PERMAFROST_FOSSIL_PATCH = registerConfiguredFeature("permafrost_fossil_patch",  () -> new ConfiguredFeature<>(Feature.DISK, new DiskConfiguration(RuleBasedBlockStateProvider.simple(UPBlocks.PERMAFROST_FOSSIL.get()), BlockPredicate.matchesBlocks(List.of(UPBlocks.PERMAFROST.get())), UniformInt.of(2, 3), 1)));

    public static RegistryObject<ConfiguredFeature<?, ?>> registerConfiguredFeature(String name, Supplier<ConfiguredFeature<?, ?>> feature) {
        configuredFeatureList.add(name);
        return CONFIGURED_FEATURES.register(name, feature);
    }

}
