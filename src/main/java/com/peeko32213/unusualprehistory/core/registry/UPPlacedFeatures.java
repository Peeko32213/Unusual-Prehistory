package com.peeko32213.unusualprehistory.core.registry;

import com.google.common.collect.ImmutableList;
import com.peeko32213.unusualprehistory.UnusualPrehistory;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class UPPlacedFeatures {
    public static List<String> placedFeatureList = new ArrayList<>();

    public static final DeferredRegister<PlacedFeature> PLACED_FEATURES = DeferredRegister.create(Registries.PLACED_FEATURE, UnusualPrehistory.MODID);

    private static final String PETRIFIED_WOOD_FOREST_FEATURE_NAME_PLACED = "petrified_wood_forest_feature_placed";

    private static final String TAR_PIT_FEATIRE_PLACED = "tar_pit_feature_placed";

    private static final String ICE_FOSSIL_ICEBERG_FEATURE_PLACED = "ice_fossil_iceberg_feature_placed";
    public static final RegistryObject<PlacedFeature> GINKGO_CHECKED = registerPlacedFeature("ginkgo_checked", () -> new PlacedFeature(UPConfiguredFeatures.GINKGO_TREE.getHolder().orElseThrow(), ImmutableList.of(
            PlacementUtils.filteredByBlockSurvival(UPBlocks.GINKGO_SAPLING.get()))));

    public static final RegistryObject<PlacedFeature> STONE_FOSSIL_ORE = registerPlacedFeature("stone_fossil_ore_placed", () -> new PlacedFeature(UPConfiguredFeatures.STONE_FOSSIL_ORE.getHolder().orElseThrow(),
            commonOrePlacement(10,
                    HeightRangePlacement.triangle(VerticalAnchor.absolute(-48), VerticalAnchor.absolute(58)))));
    public static final RegistryObject<PlacedFeature> AMBER_FOSSIL_ORE = registerPlacedFeature("amber_fossil_ore_placed", () -> new PlacedFeature(UPConfiguredFeatures.AMBER_FOSSIL_ORE.getHolder().orElseThrow(), commonOrePlacement(4,
            HeightRangePlacement.triangle(VerticalAnchor.absolute(-48), VerticalAnchor.absolute(58)))));

    public static final RegistryObject<PlacedFeature> JUNGLE_AMBER_FOSSIL_ORE = registerPlacedFeature("jungle_amber_fossil_ore_placed", () -> new PlacedFeature(UPConfiguredFeatures.AMBER_FOSSIL_ORE.getHolder().orElseThrow(), commonOrePlacement(12,
            HeightRangePlacement.uniform(VerticalAnchor.absolute(11), VerticalAnchor.absolute(256)))));
    public static final RegistryObject<PlacedFeature> DEEPSLATE_FOSSIL_ORE = registerPlacedFeature("deepslate_fossil_ore_placed", () -> new PlacedFeature(UPConfiguredFeatures.DEEPSLATE_FOSSIL_ORE.getHolder().orElseThrow(), commonOrePlacement(17, // VeinsPerChunk
            HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(1)))));
//    public static final RegistryObject<PlacedFeature> PLANT_FOSSIL_ORE = registerPlacedFeature("plant_fossil_ore_placed", () -> new PlacedFeature(UPConfiguredFeatures.PLANT_FOSSIL_ORE.getHolder().orElseThrow(), commonOrePlacement(10,
//            HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(48)))));
//    public static final RegistryObject<PlacedFeature> DEEPSLATE_PLANT_FOSSIL_ORE = registerPlacedFeature("deepslate_plant_fossil_ore_placed", () -> new PlacedFeature(UPConfiguredFeatures.DEEPSLATE_PLANT_FOSSIL_ORE.getHolder().orElseThrow(), commonOrePlacement(17, // VeinsPerChunk
//            HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(1)))));

    public static final RegistryObject<PlacedFeature> OPAL_FOSSIL_ORE = registerPlacedFeature("opal_fossil_ore_placed", () -> new PlacedFeature(UPConfiguredFeatures.STONE_OPAL_ORE.getHolder().orElseThrow(), commonOrePlacement(4,
            HeightRangePlacement.triangle(VerticalAnchor.absolute(-48), VerticalAnchor.absolute(58)))));

    public static final RegistryObject<PlacedFeature> OCEAN_OPAL_FOSSIL_ORE = registerPlacedFeature("ocean_stone_opal_fossil_ore_placed", () -> new PlacedFeature(UPConfiguredFeatures.STONE_OPAL_ORE.getHolder().orElseThrow(), commonOrePlacement(12,
            HeightRangePlacement.uniform(VerticalAnchor.absolute(11), VerticalAnchor.absolute(256)))));
    public static final RegistryObject<PlacedFeature> DEEPSLATE_OPAL_ORE = registerPlacedFeature("deepslate_opal_ore_placed", () -> new PlacedFeature(UPConfiguredFeatures.DEEPSLATE_OPAL_ORE.getHolder().orElseThrow(), commonOrePlacement(4, // VeinsPerChunk
            HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(1)))));
    public static final RegistryObject<PlacedFeature> OCEAN_DEEPSLATE_OPAL_ORE = registerPlacedFeature("ocean_deepslate_opal_ore_placed", () -> new PlacedFeature(UPConfiguredFeatures.DEEPSLATE_OPAL_ORE.getHolder().orElseThrow(), commonOrePlacement(12, // VeinsPerChunk
            HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(1)))));

    public static final RegistryObject<PlacedFeature> PETRIFIED_WOOD_FOREST_PLACED = registerPlacedFeature(PETRIFIED_WOOD_FOREST_FEATURE_NAME_PLACED, () -> new PlacedFeature(UPConfiguredFeatures.CONFIGURED_PETRIFIED_WOOD_FOREST.getHolder().orElseThrow(), ImmutableList.of(
            RarityFilter.onAverageOnceEvery(25),
            InSquarePlacement.spread(),
            PlacementUtils.HEIGHTMAP_TOP_SOLID,
            CountPlacement.of(1),
            BiomeFilter.biome())));


    public static final RegistryObject<PlacedFeature> TAR_PIT_PLACED = registerPlacedFeature(TAR_PIT_FEATIRE_PLACED, () -> new PlacedFeature(UPConfiguredFeatures.CONFIGURED_TAR_PIT.getHolder().orElseThrow(), ImmutableList.of(
            RarityFilter.onAverageOnceEvery(600),
            InSquarePlacement.spread(),
            PlacementUtils.HEIGHTMAP_TOP_SOLID,
            //CountPlacement.of(80),
            BiomeFilter.biome())));

    public static final RegistryObject<PlacedFeature> ICE_FOSSIL_ICEBERG_PLACED = registerPlacedFeature(ICE_FOSSIL_ICEBERG_FEATURE_PLACED, () -> new PlacedFeature(UPConfiguredFeatures.CONFIGURED_ICE_FOSSIL_ICEBERG.getHolder().orElseThrow(), ImmutableList.of(
            RarityFilter.onAverageOnceEvery(600),
//            InSquarePlacement.spread(),
            PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
            //CountPlacement.of(80),
            BiomeFilter.biome())));

    public static final RegistryObject<PlacedFeature> PERMAFROST_PATCH = registerPlacedFeature("permafrost_patch", () -> new PlacedFeature(UPConfiguredFeatures.PERMAFROST_PATCH.getHolder().get(), ImmutableList.of(
            RarityFilter.onAverageOnceEvery(1),
            CountPlacement.of(60),
            InSquarePlacement.spread(),
            HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(32)),
            RandomOffsetPlacement.vertical(ConstantInt.of(-1)),
            BlockPredicateFilter.forPredicate(
                    BlockPredicate.allOf(
                            BlockPredicate.anyOf(
                                    BlockPredicate.matchesBlocks(BlockPos.ZERO, Blocks.STONE),
                                    BlockPredicate.matchesBlocks(BlockPos.ZERO, Blocks.DEEPSLATE)),
                            BlockPredicate.matchesBlocks(Direction.UP.getNormal(), Blocks.WATER))),
            BiomeFilter.biome())));

    public static final RegistryObject<PlacedFeature> PERMAFROST_FOSSIL_PATCH = registerPlacedFeature("permafrost_fossil_patch", () -> new PlacedFeature(UPConfiguredFeatures.PERMAFROST_FOSSIL_PATCH.getHolder().get(), ImmutableList.of(
            RarityFilter.onAverageOnceEvery(1),
            CountPlacement.of(15),
            InSquarePlacement.spread(),
            HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(32)),
            RandomOffsetPlacement.vertical(ConstantInt.of(-1)),
            BlockPredicateFilter.forPredicate(
                    BlockPredicate.allOf(
                            BlockPredicate.anyOf(
                                    BlockPredicate.matchesBlocks(BlockPos.ZERO, Blocks.STONE),
                                    BlockPredicate.matchesBlocks(BlockPos.ZERO, UPBlocks.PERMAFROST.get()),
                                    BlockPredicate.matchesBlocks(BlockPos.ZERO, Blocks.DEEPSLATE)),
                            BlockPredicate.matchesBlocks(Direction.UP.getNormal(), Blocks.WATER))),
            BiomeFilter.biome())));

    private static List<PlacementModifier> orePlacement(PlacementModifier modifier, PlacementModifier modifier2) {
        return List.of(modifier, InSquarePlacement.spread(), modifier2, BiomeFilter.biome());
    }

    private static List<PlacementModifier> commonOrePlacement(int count, PlacementModifier modifier) {
        return orePlacement(CountPlacement.of(count), modifier);
    }


    private static RegistryObject<PlacedFeature> registerPlacedFeature(String name, Supplier<PlacedFeature> feature) {
        placedFeatureList.add(name);
        return PLACED_FEATURES.register(name, feature);
    }

}

