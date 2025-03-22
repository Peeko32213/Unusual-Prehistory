package com.peeko32213.unusualprehistory.common.entity.custom.ai.goal;

import com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.VelociraptorEntity;
import com.peeko32213.unusualprehistory.core.registry.UPItems;
import com.peeko32213.unusualprehistory.core.registry.UPSounds;
import com.peeko32213.unusualprehistory.core.other.UPTags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.ForgeEventFactory;
import org.jetbrains.annotations.NotNull;

public class VelociraptorPushButtonsGoal extends MoveToBlockGoal {

    private final VelociraptorEntity entity;

    private static final int COOLDOWN = 5000;
    protected int ticksWaited = 0;

    private static int cooldownTicks = COOLDOWN;

    public VelociraptorPushButtonsGoal(VelociraptorEntity pMob, double pSpeedModifier, int pSearchRange, int pVerticalSearchRange) {
        super(pMob, pSpeedModifier, pSearchRange, pVerticalSearchRange);
        this.entity = pMob;
    }

    public double getDesiredSquaredDistanceToTarget() {
        return 1.5D;
    }

    public boolean canUse() {
        return cooldownTicks-- <= 0 && super.canUse();
    }

    public boolean canContinueToUse() {
        return super.canContinueToUse();
    }

    public void tick() {
        ++this.ticksWaited;
        float r = entity.getRandom().nextFloat();

        if (this.isReachedTarget()) {
            if (this.ticksWaited >= 60) {
                this.onReachedTarget();
                if (r <= 0.05F) {
                    entity.spawnAtLocation(UPItems.VELOCI_FEATHERS.get());
                }
            } else {
                ++this.ticksWaited;
            }
        }
        else if (!this.isReachedTarget() && r < 0.05F) {
            entity.playSound(UPSounds.VELOCIRAPTOR_SEARCH.get(), 0.15F, 1.0F);
        }
        super.tick();
    }

    protected void onReachedTarget() {
        if (ForgeEventFactory.getMobGriefingEvent(entity.level(), entity)) {
            BlockState blockstate = entity.level().getBlockState(this.blockPos);
            if (blockstate.is(UPTags.VELOCI_BUTTONS)) {
                this.pushButton(blockstate);
                this.stop();
            }
        }
        this.ticksWaited = 0;
    }

    @Override
    public void stop() {
        super.stop();
        cooldownTicks = COOLDOWN;
    }

    @Override
    protected @NotNull BlockPos getMoveToTarget() {
        return this.blockPos;
    }

    @Override
    protected boolean isValidTarget(LevelReader world, @NotNull BlockPos pos) {
        BlockState blockState = world.getBlockState(pos);
        return (blockState.is(UPTags.VELOCI_BUTTONS));
    }

    private void pushButton(BlockState p_148929_) {
        ((VelociraptorEntity) this.mob).pushingState = true;
        this.nextStartTick = this.nextStartTick(this.mob);
        BlockState state = this.mob.level().getBlockState(this.blockPos);
        ((ButtonBlock) state.getBlock()).use(state, this.mob.level(), this.blockPos, null, null, null);
    }

    public void start() {
        this.ticksWaited = 0;
        super.start();
    }
}
