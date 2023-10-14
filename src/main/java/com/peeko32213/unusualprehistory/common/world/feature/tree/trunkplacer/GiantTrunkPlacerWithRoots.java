package com.peeko32213.unusualprehistory.common.world.feature.tree.trunkplacer;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.peeko32213.unusualprehistory.core.registry.UPBlocks;
import com.peeko32213.unusualprehistory.core.registry.UPTrunkPlacerType;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class GiantTrunkPlacerWithRoots extends TrunkPlacer {

    public static final Codec<GiantTrunkPlacerWithRoots> CODEC = RecordCodecBuilder.create((p_70189_) -> {
        return trunkPlacerParts(p_70189_).apply(p_70189_, GiantTrunkPlacerWithRoots::new);
    });


    public GiantTrunkPlacerWithRoots(int pBaseHeight, int pHeightRandA, int pHeightRandB) {
        super(pBaseHeight, pHeightRandA, pHeightRandB);
    }

    @Override
    protected TrunkPlacerType<?> type() {
        return UPTrunkPlacerType.GIANT_TRUNK_PLACER_WITH_ROOTS.get();
    }

    @Override
    public List<FoliagePlacer.FoliageAttachment> placeTrunk(LevelSimulatedReader pLevel, BiConsumer<BlockPos, BlockState> pBlockSetter, RandomSource pRandom, int pFreeTreeHeight, BlockPos pPos, TreeConfiguration pConfig) {

        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

        boolean offSetPos = pLevel.isStateAtPosition(pPos.below(), (state -> {
           return state.is(Blocks.AIR) || state.is(UPBlocks.FOXII_SAPLING.get()) || !state.isSolid();
        }));

        if(offSetPos) pPos = pPos.below();


        for(int i = 0; i < pFreeTreeHeight; ++i) {
            this.placeLogIfFreeWithOffset(pLevel, pBlockSetter, pRandom, blockpos$mutableblockpos, pConfig, pPos, 0, i, 0);
            if (i < pFreeTreeHeight - 1) {
                this.placeLogIfFreeWithOffset(pLevel, pBlockSetter, pRandom, blockpos$mutableblockpos, pConfig, pPos, 1, i, 0);
                this.placeLogIfFreeWithOffset(pLevel, pBlockSetter, pRandom, blockpos$mutableblockpos, pConfig, pPos, 1, i, 1);
                this.placeLogIfFreeWithOffset(pLevel, pBlockSetter, pRandom, blockpos$mutableblockpos, pConfig, pPos, 0, i, 1);
                this.placeLogIfFreeWithOffset(pLevel, pBlockSetter, pRandom, blockpos$mutableblockpos, pConfig, pPos, -1, i, 0);
                this.placeLogIfFreeWithOffset(pLevel, pBlockSetter, pRandom, blockpos$mutableblockpos, pConfig, pPos, -1, i, -1);
                this.placeLogIfFreeWithOffset(pLevel, pBlockSetter, pRandom, blockpos$mutableblockpos, pConfig, pPos, 0, i, -1);
                this.placeLogIfFreeWithOffset(pLevel, pBlockSetter, pRandom, blockpos$mutableblockpos, pConfig, pPos, 1, i, -1);
                this.placeLogIfFreeWithOffset(pLevel, pBlockSetter, pRandom, blockpos$mutableblockpos, pConfig, pPos, -1, i, 1);
            }
        }


        int size = 5;
        double multipl = 0.3;
        int offSet = 6;
        int startX = pPos.getX() - size / 2;
        int startZ = pPos.getZ() - size / 2;
        for(int toSize = 0; toSize < 1; toSize++) {
            for (int x = startX; x < startX + size; x++) {
                for (int z = startZ; z < startZ + size; z++) {
                    BlockPos pos = new BlockPos(x, pPos.getY(), z);
                    if (x == startX || x == startX + size - 1 || z == startZ || z == startZ + size - 1) {
                        boolean placeRoots = pRandom.nextInt(0, 100) <= 65;
                        for (int i = 0; i < Mth.floor(pFreeTreeHeight * multipl) && placeRoots; i++) {
                            this.placeLogIfFreeWithOffset(pLevel, pBlockSetter, pRandom, blockpos$mutableblockpos, pConfig, pos, 0, i, 0);

                            boolean stopPlacingCurrent = pRandom.nextInt(0, 100) <= 35;
                            if (i > offSet || stopPlacingCurrent) break;
                        }
                    } else {
                        continue;
                    }
                }
            }
            multipl = multipl - 0.05;
            size++;
            offSet--;
        }


        return ImmutableList.of(new FoliagePlacer.FoliageAttachment(pPos.above(pFreeTreeHeight), 0, true));
    }


    private void placeLogIfFreeWithOffset(LevelSimulatedReader pLevel, BiConsumer<BlockPos, BlockState> pBlockSetter, RandomSource pRandom, BlockPos.MutableBlockPos pPos, TreeConfiguration pConfig, BlockPos pOffsetPos, int pOffsetX, int pOffsetY, int pOffsetZ) {
        this.placeLogIfFreeWithOffset(pLevel, pBlockSetter, pRandom, pPos , pConfig, pOffsetPos, pOffsetX ,pOffsetY , pOffsetZ, Function.identity());
    }

    private void placeLogIfFreeWithOffset(LevelSimulatedReader pLevel, BiConsumer<BlockPos, BlockState> pBlockSetter, RandomSource pRandom, BlockPos.MutableBlockPos pPos, TreeConfiguration pConfig, BlockPos pOffsetPos, int pOffsetX, int pOffsetY, int pOffsetZ, Function<BlockState, BlockState> pPropertySetter) {
        pPos.setWithOffset(pOffsetPos, pOffsetX, pOffsetY, pOffsetZ);
        pBlockSetter.accept(pPos, pPropertySetter.apply(pConfig.trunkProvider.getState(pRandom, pPos)));
        //this.placeLog(pLevel, pBlockSetter, pRandom, pPos, pConfig);
        //this.placeLogIfFree(pLevel, pBlockSetter, pRandom, pPos, pConfig);
    }
}
