package com.peeko32213.unusualprehistory.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BlockRexHead extends Block {
    public static final DirectionProperty FACING = DirectionalBlock.FACING;
    private static final VoxelShape UP_SHAPE = Shapes.or(Block.box(0, 0, 0, 16, 32, 24),
            Block.box(0, 0, 0, 16, 32, 24));
    private static final VoxelShape DOWN_SHAPE = Shapes.or(Block.box(0, -16, 0, 16, 16, 24),
            Block.box(0, -16, 0, 16, 16, 24));
    private static final VoxelShape SOUTH_SHAPE = Shapes.or(Block.box(0, 0, 0, 16, 24, 32),
            Block.box(0, 0, 0, 16, 24, 32));
    private static final VoxelShape NORTH_SHAPE = Shapes.or(Block.box(0, 0, -16, 16, 24, 16),
            Block.box(0, 0, -16, 16, 24, 16));
    private static final VoxelShape EAST_SHAPE = Shapes.or(Block.box(-16, 0, 0, 16, 24, 16),
            Block.box(-16, 0, 0, 16, 24, 16));
    private static final VoxelShape WEST_SHAPE = Shapes.or(Block.box(0, 0, 0, 32, 24, 16),
            Block.box(0, 0, 0, 32, 24, 16));

    public BlockRexHead() {
        super(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(1.5F).noOcclusion());
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getClickedFace());
    }

    public BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
    }

    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    public VoxelShape getShape(BlockState p_54561_, BlockGetter p_54562_, BlockPos p_54563_, CollisionContext p_54564_) {
        return switch (p_54561_.getValue(FACING)) {
            case NORTH -> NORTH_SHAPE;
            case SOUTH -> SOUTH_SHAPE;
            case EAST -> EAST_SHAPE;
            case WEST -> WEST_SHAPE;
            case UP -> UP_SHAPE;
            default -> DOWN_SHAPE;
        };
    }
}