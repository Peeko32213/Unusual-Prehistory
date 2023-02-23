package com.peeko32213.unusualprehistory.common.block.plant;

import com.peeko32213.unusualprehistory.core.registry.UPBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.VoxelShape;

public abstract class GrowingPlantDoubleHeadBlock extends GrowingPlantHeadBlock {
    private final double growPerTickProbability;

    protected GrowingPlantDoubleHeadBlock(Properties p_53928_, Direction p_53929_, VoxelShape p_53930_, boolean p_53931_, double p_53932_) {
        super(p_53928_, p_53929_, p_53930_, p_53931_, p_53932_);
        this.growPerTickProbability = p_53932_;

    }

    @Override
    public void randomTick(BlockState state, ServerLevel serverLevel, BlockPos pos, RandomSource randomSource) {
        if (state.getValue(AGE) < 25 && net.minecraftforge.common.ForgeHooks.onCropsGrowPre(serverLevel, pos.relative(this.growthDirection), serverLevel.getBlockState(pos.relative(this.growthDirection)), randomSource.nextDouble() < this.growPerTickProbability)) {
            BlockPos blockpos = pos.relative(this.growthDirection);
            if (this.canGrowInto(serverLevel.getBlockState(blockpos))) {
                if (serverLevel.getBlockState(blockpos.above()).is(Blocks.AIR) || serverLevel.getBlockState(blockpos.above()).is(UPBlocks.QUEREUXIA_TOP.get())) {
                    serverLevel.setBlockAndUpdate(blockpos.above(), UPBlocks.QUEREUXIA_TOP.get().defaultBlockState());
                    serverLevel.setBlockAndUpdate(blockpos, UPBlocks.QUEREUXIA_PLANT.get().defaultBlockState());
                    net.minecraftforge.common.ForgeHooks.onCropsGrowPost(serverLevel, blockpos, serverLevel.getBlockState(blockpos));
                    return;
                }
                serverLevel.setBlockAndUpdate(blockpos, this.getGrowIntoState(state, serverLevel.random));
                net.minecraftforge.common.ForgeHooks.onCropsGrowPost(serverLevel, blockpos, serverLevel.getBlockState(blockpos));
            }
        }

    }

    @Override
    public void performBonemeal(ServerLevel serverLevel, RandomSource p_221338_, BlockPos p_221339_, BlockState p_221340_) {
        BlockPos blockpos = p_221339_.relative(this.growthDirection);
        int i = Math.min(p_221340_.getValue(AGE) + 1, 25);
        int j = this.getBlocksToGrowWhenBonemealed(p_221338_);

        for (int k = 0; k < j && this.canGrowInto(serverLevel.getBlockState(blockpos)); ++k) {
            if (serverLevel.getBlockState(blockpos.above()).is(Blocks.AIR)) {
                serverLevel.setBlock(blockpos, UPBlocks.QUEREUXIA_PLANT.get().defaultBlockState(), 3);
                serverLevel.setBlock(blockpos.above(), UPBlocks.QUEREUXIA_TOP.get().defaultBlockState(), 3);
                return;
            }
            if (serverLevel.getBlockState(blockpos.above()).is(UPBlocks.QUEREUXIA_TOP.get())) {
                serverLevel.setBlockAndUpdate(blockpos, UPBlocks.QUEREUXIA_PLANT.get().defaultBlockState());
                return;
            }

            serverLevel.setBlockAndUpdate(blockpos, p_221340_.setValue(AGE, Integer.valueOf(i)));
            blockpos = blockpos.relative(this.growthDirection);
            i = Math.min(i + 1, 25);

        }

    }
}
