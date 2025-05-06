package com.peeko32213.unusualprehistory.common.block.custom.plant;

import com.peeko32213.unusualprehistory.core.registry.UPBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;

public abstract class GrowingPlantDoubleHeadBlock extends GrowingPlantHeadBlock {
    private final double growPerTickProbability;

    protected GrowingPlantDoubleHeadBlock(Properties p_53928_, Direction p_53929_, VoxelShape p_53930_, boolean p_53931_, double p_53932_) {
        super(p_53928_, p_53929_, p_53930_, p_53931_, p_53932_);
        this.growPerTickProbability = p_53932_;

    }

    @Override
    public void randomTick(BlockState state, ServerLevel serverLevel, BlockPos pos, RandomSource randomSource) {
        BlockPos blockpos = pos.relative(this.growthDirection);
        if (state.getValue(AGE) < 25 && net.minecraftforge.common.ForgeHooks.onCropsGrowPre(serverLevel, pos.relative(this.growthDirection), serverLevel.getBlockState(pos.relative(this.growthDirection)), randomSource.nextDouble() < this.growPerTickProbability)) {
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
        if (serverLevel.getBlockState(blockpos).is(UPBlocks.QUEREUXIA_TOP.get())) {
            serverLevel.setBlockAndUpdate(blockpos.below(), UPBlocks.QUEREUXIA_PLANT.get().defaultBlockState());
        }
    }

    @Override
    public void tick(BlockState state, ServerLevel serverLevel, BlockPos blockpos, RandomSource randomSource) {
        if (serverLevel.getBlockState(blockpos.above()).is(UPBlocks.QUEREUXIA_TOP.get())) {
            serverLevel.setBlockAndUpdate(blockpos, UPBlocks.QUEREUXIA_PLANT.get().defaultBlockState());
        }
        super.tick(state, serverLevel, blockpos, randomSource);
    }

    @Override
    public void performBonemeal(ServerLevel serverLevel, RandomSource randomSource, BlockPos pos, BlockState state) {
        BlockPos blockpos = pos.relative(this.growthDirection);
        if (this.canGrowInto(serverLevel.getBlockState(blockpos))) {
            if (serverLevel.getBlockState(blockpos.above()).is(Blocks.AIR) || serverLevel.getBlockState(blockpos.above()).is(UPBlocks.QUEREUXIA_TOP.get())) {
                serverLevel.setBlockAndUpdate(blockpos, UPBlocks.QUEREUXIA_PLANT.get().defaultBlockState());
                serverLevel.setBlockAndUpdate(blockpos.above(), UPBlocks.QUEREUXIA_TOP.get().defaultBlockState());
                serverLevel.scheduleTick(blockpos, this, 1);
                return;
            }
            serverLevel.setBlockAndUpdate(blockpos, this.getGrowIntoState(state, serverLevel.random));
            serverLevel.scheduleTick(blockpos, this, 1);
        }
    }

}
