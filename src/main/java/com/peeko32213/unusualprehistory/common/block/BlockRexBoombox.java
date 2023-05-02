package com.peeko32213.unusualprehistory.common.block;

import com.google.common.collect.Lists;
import com.peeko32213.unusualprehistory.core.registry.UPEffects;
import com.peeko32213.unusualprehistory.core.registry.UPSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.DropperBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.AABB;

import java.util.ArrayList;
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
        List<BlockPos> list = new ArrayList<>();
        if (flag && !flag1) {
            applyEffects(worldIn, pos, list);
            SoundEvent sound = UPSounds.REX_BOOMBOX.get();
            worldIn.playSound((Player)null, pos, sound, SoundSource.BLOCKS, 4F, worldIn.random.nextFloat() * 0.2F + 0.9F);
            if(worldIn.isClientSide) {
                worldIn.addAlwaysVisibleParticle(ParticleTypes.SONIC_BOOM, true, pos.getX() + 0.5, pos.getY() + 2, pos.getZ() + 0.5, 0, 0, 0);
            }
            worldIn.setBlock(pos, state.setValue(TRIGGERED, Boolean.valueOf(true)), 2);
            worldIn.scheduleTick(pos, this, 20);
        } else if (flag1) {
            if (tickOff) {
                worldIn.scheduleTick(pos, this, 20);
                worldIn.setBlock(pos, state.setValue(TRIGGERED, Boolean.valueOf(false)), 2);
            }
        }
    }



    private static void applyEffects(Level level, BlockPos pos, List<BlockPos> listPos) {
        int i = listPos.size();
        int j = i / 7 * 16;
        int k = pos.getX();
        int l = pos.getY();
        int i1 = pos.getZ();
        AABB aabb = (new AABB(pos)).inflate(16);
        List<Player> list = level.getEntitiesOfClass(Player.class, aabb);
        if (!list.isEmpty()) {
            for(Player player : list) {
                if (pos.closerThan(player.blockPosition(), 16)) {
                    player.addEffect(new MobEffectInstance(UPEffects.SCREEN_SHAKE.get(), 20, 0, true, true));
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
