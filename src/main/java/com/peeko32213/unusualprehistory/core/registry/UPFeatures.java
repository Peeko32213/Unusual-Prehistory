package com.peeko32213.unusualprehistory.core.registry;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.world.feature.PetrifiedTreeFeature;
import com.peeko32213.unusualprehistory.common.world.feature.TarPitFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class UPFeatures {

    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, UnusualPrehistory.MODID);
    private static final String PETRIFIED_WOOD_FOREST_FEATURE_NAME = "petrified_wood_forest_feature";
    private static final String TAR_PIT_FEATURE_NAME = "tar_pit_feature";
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> PETRIFIED_WOOD_FOREST = FEATURES.register(PETRIFIED_WOOD_FOREST_FEATURE_NAME, () -> new PetrifiedTreeFeature(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> TAR_PIT = FEATURES.register(TAR_PIT_FEATURE_NAME, () -> new TarPitFeature(NoneFeatureConfiguration.CODEC));

}

