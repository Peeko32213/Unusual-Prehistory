package com.peeko32213.unusualprehistory.common.entity.custom.base;

import com.peeko32213.unusualprehistory.common.entity.animation.state.IStateAction;
import com.peeko32213.unusualprehistory.common.entity.util.interfaces.IBookEntity;
import com.peeko32213.unusualprehistory.common.entity.util.interfaces.IHatchableEntity;
import com.peeko32213.unusualprehistory.common.entity.util.interfaces.IVariantEntity;
import com.peeko32213.unusualprehistory.core.other.tags.UPBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.WrappedGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.util.GeckoLibUtil;

import javax.annotation.Nullable;

public abstract class PrehistoricEntity extends TamableAnimal implements GeoEntity, GeoAnimatable, IHatchableEntity, IBookEntity, IStateAction, IVariantEntity {

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    private static final EntityDataAccessor<Boolean> RUNNING = SynchedEntityData.defineId(PrehistoricEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> PERFORMING_ACTION = SynchedEntityData.defineId(PrehistoricEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> COMMAND = SynchedEntityData.defineId(PrehistoricEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> SADDLED = SynchedEntityData.defineId(PrehistoricEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> IS_FROM_EGG = SynchedEntityData.defineId(PrehistoricEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> FROM_BOOK = SynchedEntityData.defineId(PrehistoricEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(PrehistoricEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> RANDOM_BOOL = SynchedEntityData.defineId(PrehistoricEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> ANIM_TIMER = SynchedEntityData.defineId(PrehistoricEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> ANIMATION_STATE = SynchedEntityData.defineId(PrehistoricEntity.class, EntityDataSerializers.INT);

    private static final EntityDataAccessor<Integer> SITTING_TIME = SynchedEntityData.defineId(PrehistoricEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> STANDING_TIME = SynchedEntityData.defineId(PrehistoricEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> SITTING_LAG = SynchedEntityData.defineId(PrehistoricEntity.class, EntityDataSerializers.INT);

    public boolean hasRunningAttributes = false;
    private float tailYaw;

    private float prevTailYaw;
    private float headLook;
    private float prevHeadLook;

    protected PrehistoricEntity(EntityType<? extends TamableAnimal> entityType, Level level) {
        super(entityType, level);
        tailYaw = this.yBodyRot;
        prevTailYaw = this.yBodyRot;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
    }

    @Override
    public void aiStep() {
        super.aiStep();
    }

    @Override
    public boolean canSprint() {
        return true;
    }

    @Override
    public void tick() {
        super.tick();
        prevTailYaw = tailYaw;
        prevHeadLook = headLook;

        if(playingAnimation()) {
            setAnimationTimer(getAnimationTimer() - 1);
        }

        // Sit control
        if (this.isTame() && this.getSittingTime()==0 && this.getStandingTime()==0){
            refreshDimensions();
        }
        if (this.getSittingTime() > 0){
            if (!this.getNavigation().isDone()){
                this.getNavigation().stop();
            }
            this.goalSelector.getRunningGoals().forEach(WrappedGoal::stop);
            int prev = this.getSittingTime();

            if (this.getSittingTime() < 6)
                this.setSittingLag(getSittingTime() + 5);

            this.setSittingTime(prev - 1);
        }
        else if (this.getSittingLag() > 0) {
            if(this.getSittingLag() == 1){
                this.goalSelector.getRunningGoals().forEach(WrappedGoal::start);
            }
            int prev = this.getSittingLag();
            this.setSittingLag(prev - 1);
        }
        if (this.isInSittingPose()){
            this.getNavigation().stop();
        }
        if (this.getStandingTime() > 0){
            if (!this.getNavigation().isDone()){
                this.getNavigation().stop();
            }
            this.goalSelector.getRunningGoals().forEach(WrappedGoal::stop);
            int prev = this.getStandingTime();
            this.setStandingTime(prev - 1);
        }

        // Heal over time
        if (this.tickCount % 100 == 0 && this.getHealth() < this.getMaxHealth()) {
            this.heal(2);
        }

        // Queries
        float yMov = (float) this.getDeltaMovement().y;
        tickRotation(Mth.clamp(yMov, -1.0F, 1.0F) * -(float) (180F / (float) Math.PI));
    }

    public void checkDespawn() {
        if (this.level().getDifficulty() == Difficulty.PEACEFUL && this.shouldDespawnInPeaceful()) {
            this.discard();
        } else {
            this.noActionTime = 0;
        }
    }

    @Override
    public boolean canAttack(@NotNull LivingEntity entity) {
        boolean prev = super.canAttack(entity);
        if(prev && isBaby()){
            return false;
        }
        return prev;
    }

    public boolean doHurtTarget(Entity entityIn) {
        return super.doHurtTarget(entityIn);
    }

    public boolean causeFallDamage(float pFallDistance, float pMultiplier, DamageSource pSource) {
        int i = this.calculateFallDamage(pFallDistance, pMultiplier);
        if (i <= 0) {
            return false;
        } else {
            this.hurt(pSource, (float)i);
            if (this.isVehicle()) {
                for(Entity entity : this.getIndirectPassengers()) {
                    entity.hurt(pSource, (float)i);
                }
            }
            this.playBlockFallSound();
            return true;
        }
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(RUNNING, false);
        this.entityData.define(PERFORMING_ACTION, false);
        this.entityData.define(SADDLED, false);
        this.entityData.define(IS_FROM_EGG, false);
        this.entityData.define(FROM_BOOK, false);
        this.entityData.define(VARIANT, 0);
        this.entityData.define(RANDOM_BOOL, false);
        this.entityData.define(ANIM_TIMER, 0);
        this.entityData.define(COMMAND, 0);
        this.entityData.define(ANIMATION_STATE, 0);
        this.entityData.define(SITTING_TIME, 0);
        this.entityData.define(SITTING_LAG, 0);
        this.entityData.define(STANDING_TIME, 0);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("Saddle", this.isSaddled());
        compound.putBoolean("fromEgg", this.isFromEgg());
        compound.putInt("variant", this.getVariant());
        compound.putInt("animTimer", this.getAnimationTimer());
        compound.putBoolean("randomBool", this.getRandomBool());
        compound.putInt("command", this.getCommand());
        compound.putInt("StandingTime", this.getStandingTime());
        compound.putInt("SittingTime", this.getSittingTime());
        compound.putInt("SittingLag", this.getSittingLag());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setSaddled(compound.getBoolean("Saddle"));
        this.setIsFromEgg(compound.getBoolean("fromEgg"));
        this.setVariant(compound.getInt("variant"));
        this.setRandomBool(compound.getBoolean("randomBool"));
        this.setAnimationTimer(compound.getInt("animTimer"));
        this.setCommand(compound.getInt("command"));
        this.setStandingTime(compound.getInt("StandingTime"));
        this.setSittingTime(compound.getInt("SittingTime"));
        this.setSittingLag(compound.getInt("SittingLag"));
    }

    // Set aggressive
    public boolean isRunning() {
        return this.entityData.get(RUNNING);
    }
    public void setRunning(boolean bool) {
        this.entityData.set(RUNNING, bool);
    }

    // Animation states
    public boolean getBooleanState(EntityDataAccessor<Boolean> pKey) {
        return this.entityData.get(pKey);
    }
    public void setBooleanState(EntityDataAccessor<Boolean> pKey, boolean state) {
        this.entityData.set(pKey, state);
    }

    // Actions
    public boolean getPerformingAction() {
        return this.entityData.get(PERFORMING_ACTION);
    }
    public void setPerformingAction(boolean action) {
        this.entityData.set(PERFORMING_ACTION, action);
    }

    @Override
    public boolean getAction() {
        return getPerformingAction();
    }

    @Override
    public void setAction(boolean action) {
        setPerformingAction(action);
    }

    // Tame command
    public int getCommand() {
        return this.entityData.get(COMMAND);
    }
    public void setCommand(int command) {
        this.entityData.set(COMMAND, command);
    }

    public boolean canOwnerCommand(Player ownerPlayer) {
        return false;
    }

    // Sitting
    public boolean isInSittingPose() {
        return super.isInSittingPose() && !(this.isVehicle() || this.isPassenger());
    }

    public int getStandingTime() {
        return this.entityData.get(STANDING_TIME);
    }

    public void setStandingTime(int command) {
        this.entityData.set(STANDING_TIME, command);
    }

    public int getSittingTime() {
        return this.entityData.get(SITTING_TIME);
    }

    public void setSittingTime(int command) {
        this.entityData.set(SITTING_TIME, command);
    }

    public int getSittingLag() {
        return this.entityData.get(SITTING_LAG);
    }

    public void setSittingLag(int command) {
        this.entityData.set(SITTING_LAG, command);
    }

    // Saddled
    public boolean isSaddled() {
        return this.entityData.get(SADDLED);
    }
    public void setSaddled(boolean saddled) {
        this.entityData.set(SADDLED, saddled);
    }

    // From egg
    public boolean isFromEgg() {
        return this.entityData.get(IS_FROM_EGG);
    }
    public void setIsFromEgg(boolean fromEgg) {
        this.entityData.set(IS_FROM_EGG, fromEgg);
    }

    // From book
    public boolean isFromBook() {
        return this.entityData.get(FROM_BOOK);
    }
    public void setIsFromBook(boolean fromBook) {
        this.entityData.set(FROM_BOOK, fromBook);
    }

    @Override
    public void setFromBook(boolean fromBook) {
        this.entityData.set(FROM_BOOK, fromBook);
    }

    // Persistence
    public boolean requiresCustomPersistence() {
        return this.isFromEgg();
    }
    public boolean removeWhenFarAway(double d) {
        return !this.isFromEgg();
    }

    // Variant
    public int getVariant() {
        return this.entityData.get(VARIANT);
    }
    public void setVariant(int variant) {
        this.entityData.set(VARIANT, variant);
    }
    public void determineVariant(int variantChange) {}

    public boolean getRandomBool() {
        return this.entityData.get(RANDOM_BOOL);
    }
    public void setRandomBool(boolean bool) {
        this.entityData.set(RANDOM_BOOL,bool);
    }

    public boolean playingAnimation() {
        return getAnimationTimer() > 0;
    }
    public int getAnimationTimer() {
        return this.entityData.get(ANIM_TIMER);
    }
    public void setAnimationTimer(int time) {
        this.entityData.set(ANIM_TIMER,time);
    }

    public int getAnimationState() {
        return this.entityData.get(ANIMATION_STATE);
    }
    public void setAnimationState(int anim) {
        this.entityData.set(ANIMATION_STATE, anim);
    }

    public boolean isStillEnough() {
        return this.getDeltaMovement().horizontalDistance() < 0.05;
    }

    private void tickRotation(float yMov) {
        float threshold = 1F;
        float lookThreshold = 0.5F;
        boolean flag2 = false;
        boolean flag3 = false;

        if (this.yRotO - this.getYRot() > threshold) {
            tailYaw += 10;
            flag2 = true;
        }
        if (this.yRotO - this.getYRot() < -threshold) {
            tailYaw -= 10;
            flag2 = true;
        }
        if (!flag2) {
            if (tailYaw > 0) {
                tailYaw = Math.max(tailYaw - 5, 0);
            }
            if (tailYaw < 0) {
                tailYaw = Math.min(tailYaw + 5, 0);
            }
        }

        if (this.yRotO - this.getYRot() > lookThreshold) {
            headLook += 10;
            flag3 = true;
        }
        if (this.yRotO - this.getYRot() < -lookThreshold) {
            headLook -= 10;
            flag3 = true;
        }
        if (!flag3) {
            if (headLook > 0) {
                headLook = Math.max(headLook - 5, 0);
            }
            if (headLook < 0) {
                headLook = Math.min(headLook + 5, 0);
            }
        }

        tailYaw = Mth.approachDegrees(this.tailYaw, yBodyRot, 8);
        tailYaw = Mth.clamp(tailYaw, -60, 60);
        headLook = Mth.clamp(headLook, -60, 60);
    }

    // Queries
    public float getYaw(float partialTick) {
        return (yBodyRotO + (yBodyRot - yBodyRotO) * partialTick);
    }
    public float getTailYaw(float partialTick) {
        return (prevTailYaw + (tailYaw - prevTailYaw) * partialTick);
    }
    public float getHeadLook(float partialTick) {
        return (prevHeadLook + (headLook - prevHeadLook) * partialTick);
    }

    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficultyIn, MobSpawnType reason, @Nullable SpawnGroupData spawnDataIn, @Nullable CompoundTag dataTag) {
        spawnDataIn = super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
        int variantChange = this.random.nextInt(0, 100);
        this.determineVariant(variantChange);
        return super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    public double getTick(Object o) {
        return tickCount;
    }

    // Riding offset
    public double getPassengersRidingOffset() {
        return 0;
    }

    // Allied to self or owner
    public boolean isAlliedTo(@NotNull Entity entityIn) {
        if (this.isTame()) {
            LivingEntity livingentity = this.getOwner();
            if (entityIn == livingentity) {
                return true;
            }
            if (entityIn instanceof TamableAnimal) {
                return ((TamableAnimal) entityIn).isOwnedBy(livingentity);
            }
            if (livingentity != null) {
                return livingentity.isAlliedTo(entityIn);
            }
        }
        return entityIn.is(this);
    }

    // Spawn rules
    public static boolean checkSurfaceDinoSpawnRules(EntityType<? extends PrehistoricEntity> dino, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource p_186242_) {
        boolean canSpawn = level.getBlockState(pos.below()).is(UPBlockTags.DINO_NATURAL_SPAWNABLE) && isBrightEnoughToSpawn(level, pos);
        return canSpawn;
    }
}
