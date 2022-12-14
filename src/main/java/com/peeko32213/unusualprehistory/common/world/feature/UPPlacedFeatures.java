package com.peeko32213.unusualprehistory.common.world.feature;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.world.feature.tree.GinkgoFoliagePlacer;
import com.peeko32213.unusualprehistory.core.registry.UPBlocks;
import com.peeko32213.unusualprehistory.core.registry.UPConfiguredFeatures;
import net.minecraft.core.Holder;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FancyFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.SpruceFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.FancyTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

public class UPPlacedFeatures {
    private final static int LEAF_SHAG_FACTOR = 10;

    public static void init() {
    }

    public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> GINKGO_TREE =
            FeatureUtils.register("ginkgo", Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                    BlockStateProvider.simple(UPBlocks.GINKGO_LOG.get()),
                    new StraightTrunkPlacer(10, 5, 5),
                    BlockStateProvider.simple(UPBlocks.GINKGO_LEAVES.get()),
                    new GinkgoFoliagePlacer(2.25f, 6.25f, ConstantInt.of(0), 0, 1, 0.0f, 1),
                    new TwoLayersFeatureSize(2, 1, 2)).build());



    public static final Holder<PlacedFeature> GINKGO_CHECKED = PlacementUtils.register("ginkgo_checked", GINKGO_TREE,
            PlacementUtils.filteredByBlockSurvival(UPBlocks.GINKGO_SAPLING.get()));

    public static final Holder<PlacedFeature> STONE_FOSSIL_ORE = registerPlacedFeature("stone_fossil_ore", UPConfiguredFeatures.STONE_FOSSIL_ORE, commonOrePlacement(10,
            HeightRangePlacement.triangle(VerticalAnchor.absolute(-48), VerticalAnchor.absolute(58))));
    public static final Holder<PlacedFeature> AMBER_FOSSIL_ORE = registerPlacedFeature("amber_fossil_ore", UPConfiguredFeatures.STONE_FOSSIL_ORE, commonOrePlacement(2,
            HeightRangePlacement.triangle(VerticalAnchor.absolute(-48), VerticalAnchor.absolute(58))));
    public static final Holder<PlacedFeature> DEEPSLATE_FOSSIL_ORE = registerPlacedFeature("deepslate_fossil_ore", UPConfiguredFeatures.DEEPSLATE_FOSSIL_ORE,  commonOrePlacement(15, // VeinsPerChunk
            HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-80), VerticalAnchor.aboveBottom(80))));

    public static final Holder<PlacedFeature> PLANT_FOSSIL_ORE = registerPlacedFeature("plant_fossil_ore", UPConfiguredFeatures.PLANT_FOSSIL_ORE, commonOrePlacement(10,
            HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(48))));


    public static <FC extends FeatureConfiguration> Holder<PlacedFeature> registerPlacedFeature(String id, Holder<ConfiguredFeature<FC, ?>> feature, PlacementModifier... placementModifiers) {
        return registerPlacedFeature(id, feature, List.of(placementModifiers));
    }

    public static <FC extends FeatureConfiguration> Holder<PlacedFeature> registerPlacedFeature(String id, Holder<ConfiguredFeature<FC, ?>> feature, List<PlacementModifier> placementModifiers) {
        ResourceLocation resourceLocation = new ResourceLocation(UnusualPrehistory.MODID, id);
        if (BuiltinRegistries.PLACED_FEATURE.keySet().contains(resourceLocation))
            throw new IllegalStateException("Placed Feature ID: \"" + resourceLocation + "\" already exists in the Placed Features registry!");

        PlacedFeature placedFeature = new PlacedFeature(Holder.hackyErase(feature), List.copyOf(placementModifiers));

        return BuiltinRegistries.register(BuiltinRegistries.PLACED_FEATURE, resourceLocation, placedFeature);
    }

    private static List<PlacementModifier> orePlacement(PlacementModifier modifier, PlacementModifier modifier2) {
        return List.of(modifier, InSquarePlacement.spread(), modifier2, BiomeFilter.biome());
    }

    private static List<PlacementModifier> commonOrePlacement(int count, PlacementModifier modifier) {
        return orePlacement(CountPlacement.of(count), modifier);
    }

}

