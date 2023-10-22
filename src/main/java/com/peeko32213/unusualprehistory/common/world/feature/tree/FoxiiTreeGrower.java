package com.peeko32213.unusualprehistory.common.world.feature.tree;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.AbstractMegaTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.jetbrains.annotations.Nullable;

import static com.peeko32213.unusualprehistory.UnusualPrehistory.prefix;

public class FoxiiTreeGrower extends AbstractMegaTreeGrower {



    @org.jetbrains.annotations.Nullable
    @Override
    protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource pRandom, boolean pHasFlowers) {
        return null;
    }

    @Nullable
    @Override
    protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredMegaFeature(RandomSource pRandom) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, prefix("foxii"));
    }
}

