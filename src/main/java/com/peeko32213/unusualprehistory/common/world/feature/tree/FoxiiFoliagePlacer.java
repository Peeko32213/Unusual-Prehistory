package com.peeko32213.unusualprehistory.common.world.feature.tree;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.peeko32213.unusualprehistory.core.registry.UPFeatureModifiers;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;

import java.util.function.BiConsumer;

public class FoxiiFoliagePlacer extends FoliagePlacer {

    public static final Codec<FoxiiFoliagePlacer> CODEC = RecordCodecBuilder.create((p_68664_) -> {
        return foliagePlacerParts(p_68664_).and(IntProvider.codec(0, 24).fieldOf("crown_height").forGetter((p_161484_) -> {
            return p_161484_.crownHeight;
        })).apply(p_68664_, FoxiiFoliagePlacer::new);
    });

    private final IntProvider crownHeight;

    public FoxiiFoliagePlacer(IntProvider pRadius, IntProvider pOffset, IntProvider crownHeight) {
        super(pRadius, pOffset);
        this.crownHeight = crownHeight;
    }

    @Override
    protected FoliagePlacerType<?> type() {
        return UPFeatureModifiers.FOLIAGE_FOXII.get();
    }

    @Override
    protected void createFoliage(LevelSimulatedReader pLevel, BiConsumer<BlockPos, BlockState> pBlockSetter, RandomSource pRandom, TreeConfiguration pConfig, int pMaxFreeTreeHeight, FoliageAttachment pAttachment, int pFoliageHeight, int pFoliageRadius, int pOffset) {
        BlockPos blockpos = pAttachment.pos();

        int i = 0;
        int start = blockpos.getY() - pFoliageHeight + pOffset;
        for(int j = start; j <= blockpos.getY() + pOffset; ++j) {
            int k = blockpos.getY() - j;
            int l = pFoliageRadius + pAttachment.radiusOffset() + Mth.floor((float)k / (float)pFoliageHeight * 6F);
            int i1;
            if (k > 0 && l == i && (j & 1) == 0) {
                i1 = l + 1;
            } else {
                i1 = l;
            }

            if(j == start){
                i1-=1;
            }

            this.placeLeavesRow(pLevel, pBlockSetter, pRandom, pConfig, new BlockPos(blockpos.getX(), j, blockpos.getZ()), i1, 0, pAttachment.doubleTrunk());
            this.placeLeavesRow(pLevel, pBlockSetter, pRandom, pConfig, new BlockPos(blockpos.getX()-1, j, blockpos.getZ()-1), i1, 0, pAttachment.doubleTrunk());
            this.placeLeavesRow(pLevel, pBlockSetter, pRandom, pConfig, new BlockPos(blockpos.getX()-1, j, blockpos.getZ()), i1, 0, pAttachment.doubleTrunk());
            this.placeLeavesRow(pLevel, pBlockSetter, pRandom, pConfig, new BlockPos(blockpos.getX(), j, blockpos.getZ()-1), i1, 0, pAttachment.doubleTrunk());
            i = l;
        }
        for(int k = blockpos.getY() - pFoliageHeight - 7; k < blockpos.getY()-pFoliageHeight+ pOffset; k++){
            int range = pRandom.nextInt(0,3);
            if(k % 2 == 0){
                this.placeLeavesRow(pLevel, pBlockSetter, pRandom, pConfig, new BlockPos(blockpos.getX()-1, k, blockpos.getZ()-1), range, 0, pAttachment.doubleTrunk());
            } else {
                this.placeLeavesRow(pLevel, pBlockSetter, pRandom, pConfig, new BlockPos(blockpos.getX(), k, blockpos.getZ()), range, 0, pAttachment.doubleTrunk());

            }

        }




        int startingPos =  blockpos.getY() - 6;
        int range = 1;

        boolean placeTopLeaves = pRandom.nextBoolean();

        for(int j = startingPos; j <= blockpos.getY() + 3 && placeTopLeaves; ++j) {
            int toCheck = blockpos.getY() + (startingPos / 2);
            if (j == 0) {
                range = 3;
            } else {
                if (j > toCheck && j < blockpos.getY() && range < 2) {
                    this.placeLeavesRow(pLevel, pBlockSetter, pRandom, pConfig, new BlockPos(blockpos.getX(), j, blockpos.getZ()), range, 0, pAttachment.doubleTrunk());
                    this.placeLeavesRow(pLevel, pBlockSetter, pRandom, pConfig, new BlockPos(blockpos.getX() - 1, j, blockpos.getZ() - 1), range, 0, pAttachment.doubleTrunk());
                    this.placeLeavesRow(pLevel, pBlockSetter, pRandom, pConfig, new BlockPos(blockpos.getX() - 1, j, blockpos.getZ()), range, 0, pAttachment.doubleTrunk());
                    this.placeLeavesRow(pLevel, pBlockSetter, pRandom, pConfig, new BlockPos(blockpos.getX(), j, blockpos.getZ() - 1), range, 0, pAttachment.doubleTrunk());
                    range += 1;
                } else {
                    this.placeLeavesRow(pLevel, pBlockSetter, pRandom, pConfig, new BlockPos(blockpos.getX(), j, blockpos.getZ()), range, 0, pAttachment.doubleTrunk());
                    this.placeLeavesRow(pLevel, pBlockSetter, pRandom, pConfig, new BlockPos(blockpos.getX() - 1, j, blockpos.getZ() - 1), range, 0, pAttachment.doubleTrunk());
                    this.placeLeavesRow(pLevel, pBlockSetter, pRandom, pConfig, new BlockPos(blockpos.getX() - 1, j, blockpos.getZ()), range, 0, pAttachment.doubleTrunk());
                    this.placeLeavesRow(pLevel, pBlockSetter, pRandom, pConfig, new BlockPos(blockpos.getX(), j, blockpos.getZ() - 1), range, 0, pAttachment.doubleTrunk());
                    range -= 1;
                }
            }
        }
    }

    @Override
    public int foliageHeight(RandomSource pRandom, int pHeight, TreeConfiguration pConfig) {
        return 30;
    }

    @Override
    protected boolean shouldSkipLocation(RandomSource pRandom, int pLocalX, int pLocalY, int pLocalZ, int pRange, boolean pLarge) {
        if (pLocalX + pLocalZ >= 8) {
            return true;
        } else {
            return pLocalX * pLocalX + pLocalZ * pLocalZ > pRange * pRange;
        }
    }
}
