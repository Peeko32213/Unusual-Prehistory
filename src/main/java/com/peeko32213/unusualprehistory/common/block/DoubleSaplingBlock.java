package com.peeko32213.unusualprehistory.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class DoubleSaplingBlock extends SaplingBlock {

    public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;
    private static final VoxelShape SHAPE_BOTTOM =  Shapes.join(Block.box(1, 8, 1, 15, 16, 15), Block.box(0, 0, 0, 16, 8, 16), BooleanOp.OR);
    private static final VoxelShape SHAPE_TOP =  Block.box(0.0, 0, 0, 16, 8, 16);
    private final AbstractTreeGrower treeGrower;

    public DoubleSaplingBlock(AbstractTreeGrower pTreeGrower, Properties pProperties) {
        super(pTreeGrower, pProperties);
        this.treeGrower = pTreeGrower;
    }

    public BlockState updateShape(BlockState state, Direction direction, BlockState state2, LevelAccessor levelAccessor, BlockPos pos1, BlockPos pos2) {
        DoubleBlockHalf doubleblockhalf = state.getValue(HALF);
        if (direction.getAxis() != Direction.Axis.Y || doubleblockhalf == DoubleBlockHalf.LOWER != (direction == Direction.UP) || state2.is(this) && state2.getValue(HALF) != doubleblockhalf) {
            return doubleblockhalf == DoubleBlockHalf.LOWER && direction == Direction.DOWN && !state.canSurvive(levelAccessor, pos1) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, direction, state2, levelAccessor, pos1, pos2);
        } else {
            return Blocks.AIR.defaultBlockState();
        }
    }
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter blockGetter, BlockPos blockPos, CollisionContext context) {
        if(state.getValue(HALF) == DoubleBlockHalf.UPPER) {
            return SHAPE_TOP;
        } else{
            return Shapes.block();
        }
    }

    public void setPlacedBy(Level level, BlockPos pos, BlockState state, LivingEntity livingEntity, ItemStack stack) {
        BlockPos blockpos = pos.above();
        level.setBlock(blockpos, this.defaultBlockState().setValue(HALF, DoubleBlockHalf.UPPER), 3);
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext state) {
        BlockPos blockpos = state.getClickedPos();
        Level level = state.getLevel();
        if (blockpos.getY() < level.getMaxBuildHeight() - 1 && level.getBlockState(blockpos.above()).canBeReplaced(state)) {
            return this.defaultBlockState().setValue(HALF, DoubleBlockHalf.LOWER);
        } else {
            return null;
        }
    }



    public boolean isValidBonemealTarget(BlockGetter pLevel, BlockPos pPos, BlockState pState, boolean pIsClient) {
        return pState.getValue(HALF) == DoubleBlockHalf.UPPER;
    }


    public void playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        if (!level.isClientSide && player.isCreative()) {
            preventCreativeDropFromBottomPart(level, pos, state, player);
        }

        super.playerWillDestroy(level, pos, state, player);
    }

    protected static void preventCreativeDropFromBottomPart(Level level, BlockPos pos, BlockState state, Player player) {
        DoubleBlockHalf doubleblockhalf = state.getValue(HALF);
        if (doubleblockhalf == DoubleBlockHalf.UPPER) {
            BlockPos blockpos = pos.below();
            BlockState blockstate = level.getBlockState(blockpos);
            if (blockstate.is(state.getBlock()) && blockstate.getValue(HALF) == DoubleBlockHalf.LOWER) {
                BlockState blockstate1 = Blocks.AIR.defaultBlockState();
                level.setBlock(blockpos, blockstate1, 35);
                level.levelEvent(player, 2001, blockpos, Block.getId(blockstate));
            }
        }
    }

    public boolean canSurvive(BlockState state, LevelReader levelReader, BlockPos pos) {
        BlockPos blockpos = pos.below();
        BlockState blockstate = levelReader.getBlockState(blockpos);
        if(state.getValue(HALF) == DoubleBlockHalf.LOWER) {
            if (state.getBlock() == this) //Forge: This function is called during world gen and placement, before this block is set, so if we are not 'here' then assume it's the pre-check.
                return levelReader.getBlockState(blockpos).canSustainPlant(levelReader, blockpos, Direction.UP, this);
        }


        return state.getValue(HALF) == DoubleBlockHalf.LOWER ? this.mayPlaceOn(levelReader.getBlockState(blockpos), levelReader, blockpos) : blockstate.is(this);
    }

    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        if (!pLevel.isAreaLoaded(pPos, 1)) return; // Forge: prevent loading unloaded chunks when checking neighbor's light
        if (pLevel.getMaxLocalRawBrightness(pPos.above()) >= 9 && pRandom.nextInt(7) == 0 && pState.getValue(HALF) == DoubleBlockHalf.UPPER) {
            this.advanceTree(pLevel, pPos, pState, pRandom);
        }

    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(HALF, STAGE);
    }
}
