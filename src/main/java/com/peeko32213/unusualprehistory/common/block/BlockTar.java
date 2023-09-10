package com.peeko32213.unusualprehistory.common.block;

import com.peeko32213.unusualprehistory.core.registry.UPItems;
import com.peeko32213.unusualprehistory.core.registry.UPParticles;
import com.peeko32213.unusualprehistory.core.registry.UPTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Optional;

public class BlockTar extends Block implements BucketPickup {
    private static final float HORIZONTAL_PARTICLE_MOMENTUM_FACTOR = 0.083333336F;
    private static final float IN_BLOCK_HORIZONTAL_SPEED_MULTIPLIER = 0.9F;
    private static final float IN_BLOCK_VERTICAL_SPEED_MULTIPLIER = 1.5F;
    private static final float NUM_BLOCKS_TO_FALL_INTO_BLOCK = 2.5F;
    private static final VoxelShape FALLING_COLLISION_SHAPE = Shapes.box(0.0D, 0.0D, 0.0D, 1.0D, (double)0.9F, 1.0D);
    private static final double MINIMUM_FALL_DISTANCE_FOR_SOUND = 4.0D;
    private static final double MINIMUM_FALL_DISTANCE_FOR_BIG_SOUND = 7.0D;

    public BlockTar(BlockBehaviour.Properties pProperties) {
        super(pProperties);
    }

    public boolean skipRendering(BlockState pState, BlockState pAdjacentBlockState, Direction pDirection) {
        return pAdjacentBlockState.is(this) ? true : super.skipRendering(pState, pAdjacentBlockState, pDirection);
    }

    public VoxelShape getOcclusionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return Shapes.empty();
    }

    public void entityInside(BlockState pState, Level pLevel, BlockPos pPos, Entity pEntity) {

        if (!(pEntity instanceof LivingEntity) || pEntity.getFeetBlockState().is(this)) {
            if(!pEntity.getType().is(UPTags.TAR_WALKABLE_THROUGH_MOBS)) {
                pEntity.makeStuckInBlock(pState, new Vec3((double) 0.2F, 1.5D, (double) 0.2F));
            }
            if (pLevel.isClientSide) {
                RandomSource randomsource = pLevel.getRandom();
                boolean flag = pEntity.xOld != pEntity.getX() || pEntity.zOld != pEntity.getZ();
                if (flag && randomsource.nextBoolean()) {
                    pLevel.addParticle(ParticleTypes.SMOKE, pEntity.getX(), (double)(pPos.getY() + 1), pEntity.getZ(), (double)(Mth.randomBetween(randomsource, -1.0F, 1.0F) * 0.083333336F), (double)0.05F, (double)(Mth.randomBetween(randomsource, -1.0F, 1.0F) * 0.083333336F));
                }
            }
        }
    }


    public void fallOn(Level pLevel, BlockState pState, BlockPos pPos, Entity pEntity, float pFallDistance) {
        if (!((double)pFallDistance < 4.0D) && pEntity instanceof LivingEntity livingentity) {
            LivingEntity.Fallsounds $$7 = livingentity.getFallSounds();
            SoundEvent soundevent = (double)pFallDistance < 7.0D ? $$7.small() : $$7.big();
            pEntity.playSound(soundevent, 1.0F, 1.0F);
        }
    }

    public VoxelShape getCollisionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        if (pContext instanceof EntityCollisionContext entitycollisioncontext) {
            Entity entity = entitycollisioncontext.getEntity();
            if (entity != null) {
                if (entity.fallDistance > 2.5F) {
                    return FALLING_COLLISION_SHAPE;
                }

                boolean flag = entity instanceof FallingBlockEntity;
                if (flag || canEntityWalkOnTar(entity) && pContext.isAbove(Shapes.block(), pPos, false) && !pContext.isDescending()) {
                    return super.getCollisionShape(pState, pLevel, pPos, pContext);
                }
            }
        }

        return Shapes.empty();
    }

    public VoxelShape getVisualShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return Shapes.empty();
    }

    public static boolean canEntityWalkOnTar(Entity pEntity) {
        if (pEntity.getType().is(UPTags.TAR_WALKABLE_ON_MOBS)) {
            return true;
        } else {
            return pEntity instanceof LivingEntity ? ((LivingEntity)pEntity).getItemBySlot(EquipmentSlot.FEET).canWalkOnPowderedSnow((LivingEntity)pEntity) : false;
        }
    }

    public ItemStack pickupBlock(LevelAccessor pLevel, BlockPos pPos, BlockState pState) {
        pLevel.setBlock(pPos, Blocks.AIR.defaultBlockState(), 11);
        if (!pLevel.isClientSide()) {
            pLevel.levelEvent(2001, pPos, Block.getId(pState));
        }

        return new ItemStack(UPItems.TAR_BUCKET.get());
    }

    public Optional<SoundEvent> getPickupSound() {
        return Optional.of(SoundEvents.BUCKET_FILL_POWDER_SNOW);
    }

    public boolean isPathfindable(BlockState pState, BlockGetter pLevel, BlockPos pPos, PathComputationType pType) {
        return true;
    }

    @Override
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom) {
        BlockPos blockpos = pPos.above();
        if (pLevel.getBlockState(blockpos).isAir() && !pLevel.getBlockState(blockpos).isSolidRender(pLevel, blockpos)) {
            if (pRandom.nextInt(100) == 0) {
                double d0 = (double)pPos.getX() + pRandom.nextDouble();
                double d1 = (double)pPos.getY() + 1.0D;
                double d2 = (double)pPos.getZ() + pRandom.nextDouble();
                pLevel.addParticle(UPParticles.TAR_BUBBLE.get(), d0, d1, d2, 0.0D, 0.0D, 0.0D);
                pLevel.playLocalSound(d0, d1, d2, SoundEvents.LAVA_POP, SoundSource.BLOCKS, 0.2F + pRandom.nextFloat() * 0.2F, 0.9F + pRandom.nextFloat() * 0.15F, false);
            }

            if (pRandom.nextInt(200) == 0) {
                pLevel.playLocalSound((double)pPos.getX(), (double)pPos.getY(), (double)pPos.getZ(), SoundEvents.LAVA_AMBIENT, SoundSource.BLOCKS, 0.2F + pRandom.nextFloat() * 0.2F, 0.9F + pRandom.nextFloat() * 0.15F, false);
            }
        }
    }


}
