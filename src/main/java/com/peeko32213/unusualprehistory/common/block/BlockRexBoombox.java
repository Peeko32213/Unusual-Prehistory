package com.peeko32213.unusualprehistory.common.block;

import com.google.common.collect.Lists;
import com.peeko32213.unusualprehistory.core.registry.UPEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.ConduitBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class BlockRexBoombox extends Block {
    public static final DirectionProperty FACING = DirectionalBlock.FACING;
    public static final BooleanProperty TRIGGERED = BlockStateProperties.TRIGGERED;
    private final List<BlockPos> effectBlocks = Lists.newArrayList();

    public BlockRexBoombox() {
        super(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(1.5F));
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(TRIGGERED, Boolean.valueOf(false)));
    }

    public void neighborChanged(BlockState state, Level worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
        tickRexBoombox(state, worldIn, pos, false);
    }

    public void tick(BlockState state, ServerLevel worldIn, BlockPos pos, RandomSource rand) {
        tickRexBoombox(state, worldIn, pos, true);
    }

    public void tickRexBoombox(BlockState state, Level worldIn, BlockPos pos, boolean tickOff) {
        boolean flag = worldIn.hasNeighborSignal(pos) || worldIn.hasNeighborSignal(pos.below()) || worldIn.hasNeighborSignal(pos.above());
        boolean flag1 = state.getValue(TRIGGERED);
        List<BlockPos> list = this.effectBlocks;
        if (flag && !flag1) {
            applyEffects(worldIn, pos, list);
            worldIn.setBlock(pos, state.setValue(TRIGGERED, Boolean.valueOf(true)), 2);
            worldIn.scheduleTick(pos, this, 20);
        } else if (flag1) {
            if (tickOff) {
                worldIn.scheduleTick(pos, this, 20);
                worldIn.setBlock(pos, state.setValue(TRIGGERED, Boolean.valueOf(false)), 2);
            }
        }
    }

    private static void applyEffects(Level p_155444_, BlockPos p_155445_, List<BlockPos> p_155446_) {
        int i = p_155446_.size();
        int j = i / 7 * 16;
        int k = p_155445_.getX();
        int l = p_155445_.getY();
        int i1 = p_155445_.getZ();
        AABB aabb = (new AABB((double)k, (double)l, (double)i1, (double)(k + 1), (double)(l + 1), (double)(i1 + 1))).inflate((double)j).expandTowards(0.0D, (double)p_155444_.getHeight(), 0.0D);
        List<Player> list = p_155444_.getEntitiesOfClass(Player.class, aabb);
        if (!list.isEmpty()) {
            for(Player player : list) {
                if (p_155445_.closerThan(player.blockPosition(), (double)j) && player.isInWaterOrRain()) {
                    player.addEffect(new MobEffectInstance(UPEffects.SCREEN_SHAKE.get(), 260, 0, true, true));
                }
            }

        }
    }

    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getNearestLookingDirection().getOpposite());
    }

    public BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
    }

    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, TRIGGERED);
    }
}
