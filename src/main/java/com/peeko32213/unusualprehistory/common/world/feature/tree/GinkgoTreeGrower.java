package com.peeko32213.unusualprehistory.common.world.feature.tree;

import com.peeko32213.unusualprehistory.common.world.feature.UPPlacedFeatures;
import com.peeko32213.unusualprehistory.core.registry.UPConfiguredFeatures;
import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

import javax.annotation.Nullable;

public class GinkgoTreeGrower extends AbstractTreeGrower {
    @Nullable
    @Override
    protected Holder<? extends ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource p_204307_, boolean p_204308_) {
        return UPConfiguredFeatures.GINKGO_TREE.getHolder().orElseThrow();
    }
}
