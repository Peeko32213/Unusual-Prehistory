package com.peeko32213.unusualprehistory.common.world.feature.tree;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import java.util.Random;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public final class FeaturePlacers {
    public static final BiFunction<LevelSimulatedReader, BlockPos, Boolean> VALID_TREE_POS = TreeFeature::validTreePos;


    public static void placeProvidedBlock(LevelSimulatedReader world, BiConsumer<BlockPos, BlockState> worldPlacer, BiFunction<LevelSimulatedReader, BlockPos, Boolean> predicate, BlockPos pos, BlockStateProvider config, RandomSource random) {
        if (predicate.apply(world, pos)) worldPlacer.accept(pos, config.getState(random, pos));
    }

    public static void placeSpheroid(LevelSimulatedReader world, BiConsumer<BlockPos, BlockState> placer, BiFunction<LevelSimulatedReader, BlockPos, Boolean> predicate, RandomSource random, BlockPos centerPos, float xzRadius, float yRadius, float verticalBias, BlockStateProvider config) {
        float xzRadiusSquared = xzRadius * xzRadius;
        float yRadiusSquared = yRadius * yRadius;
        float superRadiusSquared = xzRadiusSquared * yRadiusSquared;
        FeaturePlacers.placeProvidedBlock(world, placer, predicate, centerPos, config, random);

        for (int y = 0; y <= yRadius; y++) {
            if (y > yRadius) continue;

            FeaturePlacers.placeProvidedBlock(world, placer, predicate, centerPos.offset( 0,  y, 0), config, random);
            FeaturePlacers.placeProvidedBlock(world, placer, predicate, centerPos.offset( 0, -y, 0), config, random);
        }

        for (int x = 0; x <= xzRadius; x++) {
            for (int z = 1; z <= xzRadius; z++) {
                if (x * x + z * z > xzRadiusSquared) continue;

                FeaturePlacers.placeProvidedBlock(world, placer, predicate, centerPos.offset(  x, 0,  z), config, random);
                FeaturePlacers.placeProvidedBlock(world, placer, predicate, centerPos.offset( -x, 0, -z), config, random);
                FeaturePlacers.placeProvidedBlock(world, placer, predicate, centerPos.offset( -z, 0,  x), config, random);
                FeaturePlacers.placeProvidedBlock(world, placer, predicate, centerPos.offset(  z, 0, -x), config, random);

                for (int y = 1; y <= yRadius; y++) {
                    float xzSquare = ((x * x + z * z) * yRadiusSquared);

                    if (xzSquare + (((y - verticalBias) * (y - verticalBias)) * xzRadiusSquared) <= superRadiusSquared) {
                        FeaturePlacers.placeProvidedBlock(world, placer, predicate, centerPos.offset(  x,  y,  z), config, random);
                        FeaturePlacers.placeProvidedBlock(world, placer, predicate, centerPos.offset( -x,  y, -z), config, random);
                        FeaturePlacers.placeProvidedBlock(world, placer, predicate, centerPos.offset( -z,  y,  x), config, random);
                        FeaturePlacers.placeProvidedBlock(world, placer, predicate, centerPos.offset(  z,  y, -x), config, random);
                    }

                    if (xzSquare + (((y + verticalBias) * (y + verticalBias)) * xzRadiusSquared) <= superRadiusSquared) {
                        FeaturePlacers.placeProvidedBlock(world, placer, predicate, centerPos.offset(  x, -y,  z), config, random);
                        FeaturePlacers.placeProvidedBlock(world, placer, predicate, centerPos.offset( -x, -y, -z), config, random);
                        FeaturePlacers.placeProvidedBlock(world, placer, predicate, centerPos.offset( -z, -y,  x), config, random);
                        FeaturePlacers.placeProvidedBlock(world, placer, predicate, centerPos.offset(  z, -y, -x), config, random);
                    }
                }
            }
        }
    }


    public static <T extends Comparable<T>> BlockState transferStateKey(BlockState stateIn, BlockState stateOut, Property<T> property) {
        if(!stateIn.hasProperty(property) || !stateOut.hasProperty(property)) return stateOut;
        return stateOut.setValue(property, stateIn.getValue(property));
    }
}
