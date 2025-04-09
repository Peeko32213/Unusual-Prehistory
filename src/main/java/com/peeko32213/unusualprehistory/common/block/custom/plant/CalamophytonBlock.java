package com.peeko32213.unusualprehistory.common.block.custom.plant;

import javax.annotation.Nullable;

import com.peeko32213.unusualprehistory.core.other.tags.UPBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CalamophytonBlock extends UPPlantBlock implements BonemealableBlock {

    public static final IntegerProperty LAYER = IntegerProperty.create("layer", 0, 2);
    protected static final VoxelShape BOTTOM_SHAPE = Block.box(2.0D, 0.0D, 2.0D, 12.0D, 16.0D, 12.0D);
    protected static final VoxelShape TOP_SHAPE = Block.box(2.0D, 0.0D, 2.0D, 12.0D, 14.0D, 12.0D);

    public CalamophytonBlock(Block.Properties properties) {
        super(properties);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
        if (state.getValue(LAYER) == 0) {
            return super.canSurvive(state, worldIn, pos);
        } else {
            BlockState blockstate = worldIn.getBlockState(pos.below());
            if (state.getBlock() != this) return super.canSurvive(state, worldIn, pos);
            return blockstate.getBlock() == this;
        }
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos blockpos = context.getClickedPos();
        return blockpos.getY() < context.getLevel().getMaxBuildHeight() - 1 && context.getLevel().getBlockState(blockpos.above()).canBeReplaced(context) && context.getLevel().getBlockState(blockpos.above(2)).canBeReplaced(context) ? super.getStateForPlacement(context) : null;
    }

    @Override
    public void setPlacedBy(Level worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        worldIn.setBlock(pos, this.defaultBlockState().setValue(LAYER, 0), 2);
        worldIn.setBlock(pos.above(), this.defaultBlockState().setValue(LAYER, 1), 2);
        worldIn.setBlock(pos.above(2), this.defaultBlockState().setValue(LAYER, 2), 2);
    }

    @Override
    public void playerWillDestroy(Level worldIn, BlockPos pos, BlockState state, Player player) {
        if (state.getValue(LAYER) == 0) {
            if(!player.isCreative()) {
                worldIn.destroyBlock(pos, true);
                worldIn.destroyBlock(pos.above(), false);
                worldIn.destroyBlock(pos.above(2), false);
            } else {
                worldIn.destroyBlock(pos, false);
                worldIn.destroyBlock(pos.above(), false);
                worldIn.destroyBlock(pos.above(2), false);
            }
        } else if (state.getValue(LAYER) == 1) {
            if(!player.isCreative()) {
                worldIn.destroyBlock(pos.below(), true);
                worldIn.destroyBlock(pos, false);
                worldIn.destroyBlock(pos.above(), false);
            } else {
                worldIn.destroyBlock(pos.below(), false);
                worldIn.destroyBlock(pos, false);
                worldIn.destroyBlock(pos.above(), false);
            }
        } else if (state.getValue(LAYER) == 2) {
            if(!player.isCreative()) {
                worldIn.destroyBlock(pos.below(2), true);
                worldIn.destroyBlock(pos.below(), false);
                worldIn.destroyBlock(pos, false);
            } else {
                worldIn.destroyBlock(pos.below(2), false);
                worldIn.destroyBlock(pos.below(), false);
                worldIn.destroyBlock(pos, false);
            }
        }
        super.playerWillDestroy(worldIn, pos, state, player);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LAYER);
    }

    public boolean isValidBonemealTarget(LevelReader worldIn, BlockPos pos, BlockState state, boolean isClient) {
        return true;
    }

    public boolean isBonemealSuccess(Level worldIn, RandomSource rand, BlockPos pos, BlockState state) {
        return true;
    }

    public void performBonemeal(ServerLevel worldIn, RandomSource rand, BlockPos pos, BlockState state) {
        popResource(worldIn, pos, new ItemStack(this));
    }

//    @Override
//    public float getMaxHorizontalOffset() {
//        return 0.1F;
//    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        Vec3 offset = state.getOffset(worldIn, pos);
        if (state.getValue(LAYER) == 2) {
            return TOP_SHAPE.move(offset.x, offset.y, offset.z);
        }
        return BOTTOM_SHAPE.move(offset.x, offset.y, offset.z);
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter worldIn, BlockPos pos) {
        return state.is(UPBlockTags.CALAMOPHYTON_PLACEABLE);
    }
}