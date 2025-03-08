package com.peeko32213.unusualprehistory.common.entity.util.goal;

import com.peeko32213.unusualprehistory.common.entity.custom.base.PrehistoricAquaticEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.goal.JumpGoal;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;

public class AquaticJumpGoal extends JumpGoal {

    private static final int[] STEPS_TO_CHECK = new int[]{0, 1, 4, 5, 6, 7};
    private final PrehistoricAquaticEntity entity;
    private final int interval;
    private boolean breached;

    public AquaticJumpGoal(PrehistoricAquaticEntity pEntity, int pInterval) {
        this.entity = pEntity;
        this.interval = reducedTickDelay(pInterval);
    }

    /**
     * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
     * method as well.
     */
    public boolean canUse() {
        if (this.entity.getRandom().nextInt(this.interval) != 0) {
            return false;
        } else {
            Direction direction = this.entity.getMotionDirection();
            int i = direction.getStepX();
            int j = direction.getStepZ();
            BlockPos blockpos = this.entity.blockPosition();

            for(int k : STEPS_TO_CHECK) {
                if (!this.waterIsClear(blockpos, i, j, k) || !this.surfaceIsClear(blockpos, i, j, k)) {
                    return false;
                }
            }

            return true;
        }
    }

    private boolean waterIsClear(BlockPos pPos, int pDx, int pDz, int pScale) {
        BlockPos blockpos = pPos.offset(pDx * pScale, 0, pDz * pScale);
        return this.entity.level().getFluidState(blockpos).is(FluidTags.WATER) && !this.entity.level().getBlockState(blockpos).blocksMotion();
    }

    private boolean surfaceIsClear(BlockPos pPos, int pDx, int pDz, int pScale) {
        return this.entity.level().getBlockState(pPos.offset(pDx * pScale, 1, pDz * pScale)).isAir() && this.entity.level().getBlockState(pPos.offset(pDx * pScale, 2, pDz * pScale)).isAir();
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean canContinueToUse() {
        double d0 = this.entity.getDeltaMovement().y;
        return (!(d0 * d0 < (double)0.03F) || this.entity.getXRot() == 0.0F || !(Math.abs(this.entity.getXRot()) < 10.0F) || !this.entity.isInWater()) && !this.entity.onGround();
    }

    public boolean isInterruptable() {
        return false;
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void start() {
        Direction direction = this.entity.getMotionDirection();
        float scale = (float) 1.2;
        this.entity.setDeltaMovement(this.entity.getDeltaMovement().add((double)direction.getStepX() * scale, 0.75D, (double)direction.getStepZ() * 0.6D));
        this.entity.getNavigation().stop();
    }

    /**
     * Reset the task's internal state. Called when this task is interrupted by another one
     */
    public void stop() {
        this.entity.setXRot(0.0F);
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    public void tick() {
        boolean flag = this.breached;
        if (!flag) {
            FluidState fluidstate = this.entity.level().getFluidState(this.entity.blockPosition());
            this.breached = fluidstate.is(FluidTags.WATER);
        }

        if (this.breached && !flag) {
            this.entity.playSound(SoundEvents.DOLPHIN_JUMP, 1.0F, 1.0F);
        }

        Vec3 vec3 = this.entity.getDeltaMovement();
        if (vec3.y * vec3.y < (double)0.03F && this.entity.getXRot() != 0.0F) {
            this.entity.setXRot(Mth.rotLerp(0.2F, this.entity.getXRot(), 0.0F));
        } else if (vec3.length() > (double)1.0E-5F) {
            double d0 = vec3.horizontalDistance();
            double d1 = Math.atan2(-vec3.y, d0) * (double)(180F / (float)Math.PI);
            this.entity.setXRot((float)d1);
        }

    }
}
