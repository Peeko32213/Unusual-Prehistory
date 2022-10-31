package com.peeko32213.unusualprehistory.common.entity.util;

import com.peeko32213.unusualprehistory.common.entity.EntityAnurognathus;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class AnuroPolinateGoal extends MoveToBlockGoal {

    private EntityAnurognathus anuro;
    private int idleAtFlowerTime = 0;
    private boolean isAboveDestination;

    public AnuroPolinateGoal(EntityAnurognathus anuro) {
        super(anuro, 1D, 32, 8);
        this.anuro = anuro;
    }

    public boolean canUse() {
        return !anuro.isBaby() && anuro.pollinateCooldown == 0 && super.canUse();
    }

    public void stop() {
        idleAtFlowerTime = 0;
    }

    public double acceptedDistance() {
        return 3D;
    }

    public void tick() {
        super.tick();
        BlockPos blockpos = this.getMoveToTarget();
        if (!isWithinXZDist(blockpos, this.mob.position(), this.acceptedDistance())) {
            this.isAboveDestination = false;
            ++this.tryTicks;
            double speedLoc = speedModifier;
            if(this.mob.distanceToSqr(blockpos.getX() + 0.5D, blockpos.getY() + 0.5D, blockpos.getZ() + 0.5D) >= 3){
                speedLoc = speedModifier * 0.8D;
            }
            this.mob.getMoveControl().setWantedPosition((double) ((float) blockpos.getX()) + 0.5D, blockpos.getY(), (double) ((float) blockpos.getZ()) + 0.5D, speedLoc);

        } else {
            this.isAboveDestination = true;
            --this.tryTicks;
        }

        if (this.isReachedTarget() && Math.abs(anuro.getY() - blockPos.getY()) <= 2) {
            anuro.lookAt(EntityAnchorArgument.Anchor.EYES, new Vec3(blockPos.getX() + 0.5D, blockPos.getY(), blockPos.getZ() + 0.5));
            if (this.idleAtFlowerTime >= 20) {
                this.pollinate();
                this.stop();
            } else {
                ++this.idleAtFlowerTime;
            }
        }

    }



    private boolean isGrowable(BlockPos pos, ServerLevel world) {
        BlockState blockstate = world.getBlockState(pos);
        Block block = blockstate.getBlock();
        return block instanceof CropBlock && !((CropBlock)block).isMaxAge(blockstate);
    }

    private boolean isWithinXZDist(BlockPos blockpos, Vec3 positionVec, double distance) {
        return blockpos.distSqr(new BlockPos(positionVec.x(), blockpos.getY(), positionVec.z())) < distance * distance;
    }

    protected boolean isReachedTarget() {
        return this.isAboveDestination;
    }

    private void pollinate() {
        anuro.level.levelEvent(2005, blockPos, 0);
        anuro.setCropsPollinated(anuro.getCropsPollinated() + 1);
        anuro.pollinateCooldown = 200;
        if(anuro.getCropsPollinated() > 3){
            if(isGrowable(blockPos, (ServerLevel) anuro.level)){
                BoneMealItem.growCrop(new ItemStack(Items.BONE_MEAL), anuro.level, blockPos);
            }
            anuro.setCropsPollinated(0);
        }
    }

    @Override
    protected boolean isValidTarget(LevelReader worldIn, BlockPos pos) {
        if (worldIn.getBlockState(pos).is(BlockTags.BEE_GROWABLES) || worldIn.getBlockState(pos).is(BlockTags.FLOWERS)) {
            return anuro.pollinateCooldown == 0 && anuro.canBlockBeSeen(pos);
        }
        return false;
    }
}
