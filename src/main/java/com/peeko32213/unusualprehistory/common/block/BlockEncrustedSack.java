package com.peeko32213.unusualprehistory.common.block;

import com.peeko32213.unusualprehistory.common.entity.EntityEncrusted;
import com.peeko32213.unusualprehistory.common.entity.baby.EntityBabyBrachi;
import com.peeko32213.unusualprehistory.core.registry.UPEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ambient.Bat;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class BlockEncrustedSack extends Block  {
    public static final IntegerProperty HATCH = BlockStateProperties.HATCH;
    public static final IntegerProperty EGGS = BlockStateProperties.EGGS;
    private static final VoxelShape SHAPE = Block.box(4, 0, 4, 12, 8, 12);

    public BlockEncrustedSack() {
        super(Properties.of(Material.EGG, MaterialColor.SAND).strength(0.5F).sound(SoundType.HONEY_BLOCK).randomTicks().noOcclusion());
        this.registerDefaultState(this.stateDefinition.any().setValue(HATCH, Integer.valueOf(0)).setValue(EGGS, Integer.valueOf(1)));
    }

    public static boolean hasProperHabitat(BlockGetter reader, BlockPos blockReader) {
        return isProperHabitat(reader, blockReader.below());
    }

    public static boolean isProperHabitat(BlockGetter reader, BlockPos pos) {
        return reader.getBlockState(pos).is(BlockTags.BAMBOO_PLANTABLE_ON);
    }

    public void stepOn(Level worldIn, BlockPos pos, BlockState state, Entity entityIn) {
        this.tryTrample(worldIn, pos, entityIn, 100);
        super.stepOn(worldIn, pos, state, entityIn);
    }

    public void fallOn(Level worldIn, BlockState state, BlockPos pos, Entity entityIn, float fallDistance) {
        if (!(entityIn instanceof Zombie)) {
            this.tryTrample(worldIn, pos, entityIn, 3);
        }

        super.fallOn(worldIn, state, pos, entityIn, fallDistance);
    }

    private void tryTrample(Level worldIn, BlockPos pos, Entity trampler, int chances) {
        if (this.canTrample(worldIn, trampler)) {
            if (!worldIn.isClientSide && worldIn.random.nextInt(chances) == 0) {
                BlockState blockstate = worldIn.getBlockState(pos);
                this.removeOneEgg(worldIn, pos, blockstate);

            }

        }
    }


    private void removeOneEgg(Level worldIn, BlockPos pos, BlockState state) {
        worldIn.playSound(null, pos, SoundEvents.TURTLE_EGG_BREAK, SoundSource.BLOCKS, 0.7F, 0.9F + worldIn.random.nextFloat() * 0.2F);
        int i = state.getValue(EGGS);
        if(i <= 1){
        worldIn.destroyBlock(pos, false);
        } else {
            worldIn.setBlock(pos, state.setValue(EGGS, Integer.valueOf(i - 1)), 1);
            worldIn.levelEvent(4001, pos, Block.getId(state));
        }
    }



    public void randomTick(BlockState state, ServerLevel worldIn, BlockPos pos, RandomSource p_222647_) {
        if (this.canGrow(worldIn) && hasProperHabitat(worldIn, pos)) {
            int i = state.getValue(HATCH);
            float f = (float) 1.0F;
            int k = 5;
            if (i < 2) {
                worldIn.playSound(null, pos, SoundEvents.TURTLE_EGG_CRACK, SoundSource.BLOCKS, 0.7F, 0.9F + p_222647_.nextFloat() * 0.2F);
                worldIn.setBlock(pos, state.setValue(HATCH, Integer.valueOf(i + 1)), 2);
            } else {
                worldIn.playSound(null, pos, SoundEvents.TURTLE_EGG_HATCH, SoundSource.BLOCKS, 0.7F, 0.9F + p_222647_.nextFloat() * 0.2F);
                worldIn.removeBlock(pos, false);
                for (int j = 0; j < state.getValue(EGGS); ++j) {
                    for(int l = 0; l < k; ++l) {
                        float f1 = ((float)(l % 2) - 0.5F) * f;
                        float f2 = ((float)(l / 2) - 0.5F) * f;
                        worldIn.levelEvent(4001, pos, Block.getId(state));
                        EntityEncrusted turtleentity = UPEntities.ENCRUSTED.get().create(worldIn);
                        turtleentity.restrictTo(pos, 20);
                        turtleentity.moveTo(pos.getX() + (double)f1, pos.getY() + 0.5D, pos.getZ() + (double)f2, 2.0F * 360.0F, 0.0F);
                        if (!worldIn.isClientSide) {
                            worldIn.addFreshEntity(turtleentity);
                        }
                    }
                    AreaEffectCloud areaeffectcloud = new AreaEffectCloud(worldIn, pos.getX(), pos.getY(), pos.getZ());
                    areaeffectcloud.setParticle(ParticleTypes.CLOUD);
                    areaeffectcloud.setRadius(0.5F);
                    areaeffectcloud.setDuration(200);
                    areaeffectcloud.setRadiusPerTick((1.0F - areaeffectcloud.getRadius()) / (float)areaeffectcloud.getDuration());
                    areaeffectcloud.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 100, 10));
                    worldIn.addFreshEntity(areaeffectcloud);
                    }

                }
            }
        }

    public void onPlace(BlockState state, Level worldIn, BlockPos pos, BlockState oldState, boolean isMoving) {
        if (hasProperHabitat(worldIn, pos) && !worldIn.isClientSide) {
            worldIn.levelEvent(1000, pos, 0);
        }

    }

    private boolean canGrow(Level worldIn) {
        return worldIn.random.nextInt(5) == 0;
    }

    public void playerDestroy(Level worldIn, Player player, BlockPos pos, BlockState state, @Nullable BlockEntity te, ItemStack stack) {
        super.playerDestroy(worldIn, player, pos, state, te, stack);
        this.removeOneEgg(worldIn, pos, state);
    }


    @Override
    public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }


    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(HATCH, EGGS);
    }

    private boolean canTrample(Level worldIn, Entity trampler) {
        if (!(trampler instanceof EntityBabyBrachi) && !(trampler instanceof Bat)) {
            if (!(trampler instanceof LivingEntity)) {
                return false;
            } else {
                return trampler instanceof Player || net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(worldIn, trampler);
            }
        } else {
            return false;
        }
    }
}
