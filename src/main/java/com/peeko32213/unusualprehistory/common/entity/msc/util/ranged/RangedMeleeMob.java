package com.peeko32213.unusualprehistory.common.entity.msc.util.ranged;

import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;

import java.util.UUID;

public class RangedMeleeMob extends PathfinderMob implements NeutralMob {

    private static final EntityDataAccessor<Integer> ANGER_TIME = SynchedEntityData.defineId(RangedMeleeMob.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> STATE = SynchedEntityData.defineId(RangedMeleeMob.class, EntityDataSerializers.INT);
    private static final UniformInt ANGER_TIME_RANGE = TimeUtil.rangeOfSeconds(20, 39);
    private UUID targetUuid;
    private BlockPos lightBlockPos = null;

    protected RangedMeleeMob(EntityType<? extends PathfinderMob> type, Level worldIn) {
        super(type, worldIn);
        this.xpReward = (int) (this.getMaxHealth());
    }

    public int getAttckingState() {
        return this.entityData.get(STATE);
    }

    public void setAttackingState(int time) {
        this.entityData.set(STATE, time);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ANGER_TIME, 0);
        this.entityData.define(STATE, 0);
    }

    @Override
    public int getRemainingPersistentAngerTime() {
        return this.entityData.get(ANGER_TIME);
    }

    @Override
    public void setRemainingPersistentAngerTime(int time) {
        this.entityData.set(ANGER_TIME, time);
    }

    @Override
    public UUID getPersistentAngerTarget() {
        return this.targetUuid;
    }

    @Override
    public void setPersistentAngerTarget(UUID target) {
        this.targetUuid = target;
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public void startPersistentAngerTimer() {
        this.setRemainingPersistentAngerTime(ANGER_TIME_RANGE.sample(this.random));
    }

    @Override
    protected boolean shouldDespawnInPeaceful() {
        return true;
    }

    public void performRangedAttack(LivingEntity target, float pullProgress) {
    }

    @Override
    protected float getSoundVolume() {
        return 0.4F;
    }

    private boolean checkDistance(BlockPos blockPosA, BlockPos blockPosB, int distance) {
        return Math.abs(blockPosA.getX() - blockPosB.getX()) <= distance
                && Math.abs(blockPosA.getY() - blockPosB.getY()) <= distance
                && Math.abs(blockPosA.getZ() - blockPosB.getZ()) <= distance;
    }


    @Override
    protected void tickDeath() {
        ++this.deathTime;
        if (this.deathTime == 35) {
            this.remove(Entity.RemovalReason.KILLED);
            this.dropExperience();
        }
    }

}
