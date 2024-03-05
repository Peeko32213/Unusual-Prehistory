package com.peeko32213.unusualprehistory.common.block;

import com.peeko32213.unusualprehistory.common.message.ParticleSyncS2CPacket;
import com.peeko32213.unusualprehistory.core.registry.UPMessages;
import com.peeko32213.unusualprehistory.core.registry.UPParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
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
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

public class BlockElectricPillar extends Block {
    public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;

    private static final VoxelShape SHAPE_BOTTOM = Stream.of(
            Block.box(4, 0, 4, 12, 16, 12),
            Block.box(0, 0, 8, 16, 16, 8),
            Block.box(8, 0, 0, 8, 16, 16)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    private static final VoxelShape SHAPE_TOP = Shapes.join(Block.box(4, 0, 4, 12, 2, 12), Block.box(6, 2, 6, 10, 7, 10), BooleanOp.OR);

    public BlockElectricPillar(Properties pProperties) {
        super( pProperties);
    }

    public BlockState updateShape(BlockState state, Direction direction, BlockState state2, LevelAccessor levelAccessor, BlockPos pos1, BlockPos pos2) {
        DoubleBlockHalf doubleblockhalf = state.getValue(HALF);
        if (direction.getAxis() != Direction.Axis.Y || doubleblockhalf == DoubleBlockHalf.LOWER != (direction == Direction.UP) || state2.is(this) && state2.getValue(HALF) != doubleblockhalf) {
            return doubleblockhalf == DoubleBlockHalf.LOWER && direction == Direction.DOWN && !state.canSurvive(levelAccessor, pos1) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, direction, state2, levelAccessor, pos1, pos2);
        } else {
            return Blocks.AIR.defaultBlockState();
        }
    }
    public boolean isPathfindable(BlockState pState, BlockGetter pLevel, BlockPos pPos, PathComputationType pType) {
        return false;
    }


    @Override
    public VoxelShape getShape(BlockState state, BlockGetter blockGetter, BlockPos blockPos, CollisionContext context) {
        if (state.getValue(HALF) == DoubleBlockHalf.UPPER) {
            return SHAPE_TOP;
        } else {
            return SHAPE_BOTTOM;
        }
    }

    public void playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        if (!level.isClientSide && player.isCreative()) {
            BlockElectricPillar.preventCreativeDropFromBottomPart(level, pos, state, player);
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

    /**
     * Called by BlockItem after this block has been placed.
     */
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, LivingEntity pPlacer, ItemStack
            pStack) {
        pLevel.setBlock(pPos.above(), pState.setValue(HALF, DoubleBlockHalf.UPPER), 3);
    }

    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        BlockPos blockpos = pPos.below();
        BlockState blockstate = pLevel.getBlockState(blockpos);
        return pState.getValue(HALF) == DoubleBlockHalf.LOWER ? blockstate.isFaceSturdy(pLevel, blockpos, Direction.UP) : blockstate.is(this);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(HALF);
    }

    @Override
    public void entityInside(BlockState state, Level level, BlockPos pPos, Entity pEntity) {
        if(pEntity instanceof LivingEntity livingEntity) {
            Vec3 center = Vec3.atCenterOf(pPos);
            float range = 1F;
            RandomSource randomSource = ((LivingEntity) pEntity).getRandom();
            if (randomSource.nextInt(1) == 0 && state.getValue(HALF) == DoubleBlockHalf.UPPER) {
                float particleMax = 5 + level.getRandom().nextInt(3);
                for (int particles = 0; particles < particleMax; particles++) {
                    Vec3 vec3 = new Vec3((level.getRandom().nextFloat() - 0.5) * 0.3F, (level.getRandom().nextFloat() - 0.5) * 0.3F, range * 0.5F + range * 0.5F * level.getRandom().nextFloat()).yRot((float) ((particles / particleMax) * Math.PI * 2)).add(center);
                    level.addParticle(UPParticles.ELECTRIC_ATTACK.get(), center.x, center.y, center.z, vec3.x, vec3.y, vec3.z);
                    if (!(livingEntity instanceof ServerPlayer)) {
                        UPMessages.sendMSGToAll(new ParticleSyncS2CPacket(center, vec3));
                    }
                }
            }

           boolean hurt = livingEntity.hurt(livingEntity.damageSources().fall(), 0F);
            if(hurt) {
                int i = 5;
                if (i > 0) {
                    ((LivingEntity) livingEntity).knockback((double) ((float) i * 0.5F), (double) -Mth.sin(livingEntity.getYRot() * ((float) Math.PI / 180F)), (double) (-Mth.cos(livingEntity.getYRot() * ((float) Math.PI / 180F))));

                    livingEntity.setDeltaMovement(livingEntity.getDeltaMovement().multiply(0.6D, 0.80D, 0.6D));
                    livingEntity.setSprinting(false);
                }
                Vec3 vec3 = livingEntity.getDeltaMovement();
                if (livingEntity instanceof ServerPlayer && livingEntity.hurtMarked) {
                    ((ServerPlayer)livingEntity).connection.send(new ClientboundSetEntityMotionPacket(livingEntity));
                    livingEntity.hurtMarked = false;
                    livingEntity.setDeltaMovement(vec3);
                }
                }
        }
    }

    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource randomSource) {
        Vec3 center = Vec3.atCenterOf(pos);
        float range = 1F;
        if (randomSource.nextInt(1) == 0 && state.getValue(HALF) == DoubleBlockHalf.UPPER) {
            float particleMax = 5 + level.getRandom().nextInt(5);
            for (int particles = 0; particles < particleMax; particles++) {
            Vec3 vec3 = new Vec3((level.getRandom().nextFloat() - 0.5) * 0.3F, (level.getRandom().nextFloat() - 0.5) * 0.3F, range * 0.5F + range * 0.5F * level.getRandom().nextFloat()).yRot((float) ((particles / particleMax) * Math.PI * 2)).add(center);

                //level.addParticle(UPParticles.ELECTRIC_ATTACK.get(), center.x, center.y, center.z, vec3.x, vec3.y, vec3.z);
            }

            //level.addParticle(UPParticles.ELECTRIC_ORBIT.get(), center.x, center.y, center.z, center.x, center.y, center.z);
        }
    }
}
