package com.peeko32213.unusualprehistory.common.world.feature;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class UPFeatures {

    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, UnusualPrehistory.MODID);
    private static final String PETRIFIED_WOOD_FOREST_FEATURE_NAME = "petrified_wood_forest_feature";

    public static final RegistryObject<Feature<NoneFeatureConfiguration>> PETRIFIED_WOOD_FOREST = FEATURES.register(PETRIFIED_WOOD_FOREST_FEATURE_NAME, () -> new PetrifiedTreeFeature(NoneFeatureConfiguration.CODEC));

}

