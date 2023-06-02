package com.peeko32213.unusualprehistory.common.entity.msc.util;

import com.peeko32213.unusualprehistory.common.entity.EntityMegatherium;
import com.peeko32213.unusualprehistory.core.registry.UPTags;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class EatLeavesGoal extends MoveToBlockGoal {

    private EntityMegatherium sloth;
    private int idleAtLeavesTime = 0;
    private boolean isAboveDestinationBear;

    public EatLeavesGoal(EntityMegatherium sloth) {
        super(sloth, 1D, 32, 3);
        this.sloth = sloth;
    }

    public boolean canUse() {
        return !sloth.isBaby() && sloth.getMainHandItem().isEmpty() && super.canUse();
    }

    public void stop() {
        idleAtLeavesTime = 0;
    }

    public double acceptedDistance() {
        return 2D;
    }

    public void tick() {
        super.tick();
        BlockPos blockpos = this.getMoveToTarget();
        if (!isWithinXZDist(blockpos, this.mob.position(), this.acceptedDistance())) {
            this.isAboveDestinationBear = false;
            ++this.tryTicks;
            if (this.shouldRecalculatePath()) {
                this.mob.getNavigation().moveTo((double) ((float) blockpos.getX()) + 0.5D, blockpos.getY(), (double) ((float) blockpos.getZ()) + 0.5D, this.speedModifier);
            }
        } else {
            this.isAboveDestinationBear = true;
            --this.tryTicks;
        }

        if (this.isReachedTarget() && Math.abs(sloth.getY() - blockPos.getY()) <= 3) {
            sloth.lookAt(EntityAnchorArgument.Anchor.EYES, new Vec3(blockPos.getX() + 0.5D, blockPos.getY(), blockPos.getZ() + 0.5));
            if (this.idleAtLeavesTime >= 20) {
                this.breakLeaves();
            } else {
                ++this.idleAtLeavesTime;
            }
        }

    }

    private boolean isWithinXZDist(BlockPos blockpos, Vec3 positionVec, double distance) {
        return blockpos.distSqr(new BlockPos(positionVec.x(), blockpos.getY(), positionVec.z())) < distance * distance;
    }

    protected boolean isReachedTarget() {
        return this.isAboveDestinationBear;
    }

    private void breakLeaves() {
        if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(sloth.level, sloth)) {
            BlockState blockstate = sloth.level.getBlockState(this.blockPos);
            if (blockstate.is(UPTags.MEGATHERIUM_EATABLES)) {
                sloth.level.destroyBlock(blockPos, false);
                final RandomSource rand = this.sloth.getRandom();
                ItemStack stack = new ItemStack(blockstate.getBlock().asItem());
                ItemEntity itementity = new ItemEntity(sloth.level, blockPos.getX() + rand.nextFloat(), blockPos.getY() + rand.nextFloat(), blockPos.getZ() + rand.nextFloat(), stack);
                itementity.setDefaultPickUpDelay();
                sloth.level.addFreshEntity(itementity);
                stop();
            }
        }
    }

    @Override
    protected boolean isValidTarget(LevelReader worldIn, BlockPos pos) {
        return worldIn.getBlockState(pos).is(UPTags.MEGATHERIUM_EATABLES);
    }
}
