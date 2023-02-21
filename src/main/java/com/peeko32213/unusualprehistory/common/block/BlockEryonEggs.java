package com.peeko32213.unusualprehistory.common.block;

import com.peeko32213.unusualprehistory.common.entity.EntityAmmonite;
import com.peeko32213.unusualprehistory.common.entity.EntityEryon;
import com.peeko32213.unusualprehistory.core.registry.UPEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Random;

public class BlockEryonEggs extends Block {
    protected static final VoxelShape SHAPE = Block.box(0, 0, 0, 16, 1.5, 16);

    public BlockEryonEggs() {
        super(Properties.of(Material.EGG, MaterialColor.SAND).strength(0.5F).sound(SoundType.HONEY_BLOCK).randomTicks().noOcclusion());
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }


    @Override
    public boolean canSurvive(BlockState state, LevelReader reader, BlockPos pos) {
        return isProperHabitat(reader, pos.below());
    }

    public static boolean isProperHabitat(BlockGetter reader, BlockPos pos) {
        return reader.getBlockState(pos).is(BlockTags.BAMBOO_PLANTABLE_ON);
    }


    public void onPlace(BlockState p_221227_, Level p_221228_, BlockPos p_221229_, BlockState p_221230_, boolean p_221231_) {
        p_221228_.scheduleTick(p_221229_, this, getFrogspawnHatchDelay(p_221228_.getRandom()));
    }

    private static int getFrogspawnHatchDelay(RandomSource p_221186_) {
        return p_221186_.nextInt(3600, 12000);
    }



    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor access, BlockPos pos, BlockPos neighborPos) {
        return !this.canSurvive(state, access, pos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, direction, neighborState, access, pos, neighborPos);
    }


    public void tick(BlockState state, ServerLevel level, BlockPos pos, Random random) {
        if (!this.canSurvive(state, level, pos)) {
            this.hatch(level, pos);
        } else {
            this.onHatch(level, pos, random);
        }
    }

    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (entity.getType().equals(EntityType.FALLING_BLOCK)) {
            this.hatch(level, pos);
        }
    }

    private void onHatch(ServerLevel level, BlockPos pos, Random random) {
        this.hatch(level, pos);
        level.playSound(null, pos, SoundEvents.TURTLE_EGG_HATCH, SoundSource.BLOCKS, 1.0F, 1.0F);
        this.createTadpole(level, pos, random);
    }

    private void hatch(Level level, BlockPos blockPos) {
        level.destroyBlock(blockPos, false);
    }

    private void createTadpole(ServerLevel level, BlockPos pos, Random random) {
        int i = random.nextInt(1, 2);

        for (int index = 1; index <= i; ++index) {
            EntityEryon tadpole = UPEntities.ERYON.get().create(level);
            if (tadpole != null) {
                double x = (double)pos.getX() + this.getSpawnOffset(random);
                double z = (double)pos.getZ() + this.getSpawnOffset(random);
                int yaw = random.nextInt(1, 361);
                tadpole.moveTo(x, (double)pos.getY() - 0.5, z, yaw, 0.0F);
                tadpole.setPersistenceRequired();
                level.addFreshEntity(tadpole);
            }
        }
    }

    private double getSpawnOffset(Random random) {
        return Mth.clamp(random.nextDouble(), 0.2F, 0.8F);
    }
}

