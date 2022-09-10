package com.peeko32213.unusualprehistory.common.block;

import com.google.common.annotations.VisibleForTesting;
import com.peeko32213.unusualprehistory.common.entity.EntityStethacanthus;
import com.peeko32213.unusualprehistory.core.registry.UPEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ambient.Bat;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

public class BlockStethaEgg extends Block {
    private static final int MIN_STETHA_SPAWN = 1;
    private static final int MAX_STETHA_SPAWN = 4;
    private static final int DEFAULT_MIN_HATCH_TICK_DELAY = 3600;
    private static final int DEFAULT_MAX_HATCH_TICK_DELAY = 12000;
    protected static final VoxelShape SHAPE = Block.box(0, 0, 0, 16, 0, 16);
    private static int minHatchTickDelay = 3600;
    private static int maxHatchTickDelay = 12000;

    public BlockStethaEgg(BlockBehaviour.Properties p_221177_) {
        super(p_221177_);
    }

    public VoxelShape getShape(BlockState p_221199_, BlockGetter p_221200_, BlockPos p_221201_, CollisionContext p_221202_) {
        return SHAPE;
    }

    public boolean canSurvive(BlockState p_221209_, LevelReader p_221210_, BlockPos p_221211_) {
        return mayPlaceOn(p_221210_, p_221211_.below());
    }

    public void onPlace(BlockState p_221227_, Level p_221228_, BlockPos p_221229_, BlockState p_221230_, boolean p_221231_) {
        p_221228_.scheduleTick(p_221229_, this, getFrogspawnHatchDelay(p_221228_.getRandom()));
    }

    private static int getFrogspawnHatchDelay(Random p_221186_) {
        return p_221186_.nextInt(minHatchTickDelay, maxHatchTickDelay);
    }

    public BlockState updateShape(BlockState p_221213_, Direction p_221214_, BlockState p_221215_, LevelAccessor p_221216_, BlockPos p_221217_, BlockPos p_221218_) {
        return !this.canSurvive(p_221213_, p_221216_, p_221217_) ? Blocks.AIR.defaultBlockState() : super.updateShape(p_221213_, p_221214_, p_221215_, p_221216_, p_221217_, p_221218_);
    }

    public void tick(BlockState p_221194_, ServerLevel p_221195_, BlockPos p_221196_, Random p_221197_) {
        if (!this.canSurvive(p_221194_, p_221195_, p_221196_)) {
            this.destroyBlock(p_221195_, p_221196_);
        } else {
            this.hatchFrogspawn(p_221195_, p_221196_, p_221197_);
        }
    }

    public void entityInside(BlockState p_221204_, Level p_221205_, BlockPos p_221206_, Entity p_221207_) {
        if (p_221207_.getType().equals(EntityType.FALLING_BLOCK)) {
            this.destroyBlock(p_221205_, p_221206_);
        }

    }

    private static boolean mayPlaceOn(BlockGetter p_221188_, BlockPos p_221189_) {
        FluidState fluidstate = p_221188_.getFluidState(p_221189_);
        FluidState fluidstate1 = p_221188_.getFluidState(p_221189_.above());
        return fluidstate.getType() == Fluids.WATER && fluidstate1.getType() == Fluids.EMPTY;
    }

    private void hatchFrogspawn(ServerLevel p_221182_, BlockPos p_221183_, Random p_221184_) {
        this.destroyBlock(p_221182_, p_221183_);
        p_221182_.playSound((Player)null, p_221183_, SoundEvents.TURTLE_EGG_HATCH, SoundSource.BLOCKS, 1.0F, 1.0F);
        this.spawnTadpoles(p_221182_, p_221183_, p_221184_);
    }

    private void destroyBlock(Level p_221191_, BlockPos p_221192_) {
        p_221191_.destroyBlock(p_221192_, false);
    }

    private void spawnTadpoles(ServerLevel serverLevel, BlockPos blockPos, Random random) {
        int i = random.nextInt(2, 6);

        for(int j = 1; j <= i; ++j) {
            EntityStethacanthus tadpole = UPEntities.STETHACANTHUS.get().create(serverLevel);
            double d0 = (double)blockPos.getX() + this.getRandomTadpolePositionOffset(random);
            double d1 = (double)blockPos.getZ() + this.getRandomTadpolePositionOffset(random);
            int k = random.nextInt(1, 361);
            tadpole.moveTo(d0, (double)blockPos.getY() - 0.5D, d1, (float)k, 0.0F);
            tadpole.setPersistenceRequired();
            serverLevel.addFreshEntity(tadpole);
        }

    }

    private double getRandomTadpolePositionOffset(Random p_221225_) {
        double d0 = (double)(EntityStethacanthus.HITBOX_WIDTH / 2.0F);
        return Mth.clamp(p_221225_.nextDouble(), d0, 1.0D - d0);
    }

    @VisibleForTesting
    public static void setHatchDelay(int p_221179_, int p_221180_) {
        minHatchTickDelay = p_221179_;
        maxHatchTickDelay = p_221180_;
    }

    @VisibleForTesting
    public static void setDefaultHatchDelay() {
        minHatchTickDelay = 3600;
        maxHatchTickDelay = 12000;
    }
}

