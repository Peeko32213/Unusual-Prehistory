package com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.aquatic;

import com.google.common.collect.ImmutableMap;
import com.peeko32213.unusualprehistory.common.entity.animation.state.StateHelper;
import com.peeko32213.unusualprehistory.common.entity.animation.state.WeightedState;
import com.peeko32213.unusualprehistory.common.entity.custom.ai.goal.AquaticJumpGoal;
import com.peeko32213.unusualprehistory.common.entity.custom.ai.goal.PrehistoricPanicGoal;
import com.peeko32213.unusualprehistory.common.entity.custom.base.SchoolingAquaticEntity;
import com.peeko32213.unusualprehistory.common.entity.util.navigator.SmartBodyHelper;
import com.peeko32213.unusualprehistory.core.other.tags.UPEntityTypeTags;
import com.peeko32213.unusualprehistory.core.registry.entities.UPEntities;
import com.peeko32213.unusualprehistory.core.registry.items.UPItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.List;

public class StethacanthusEntity extends SchoolingAquaticEntity implements Bucketable {

    private static final EntityDataAccessor<Boolean> FROM_BUCKET = SynchedEntityData.defineId(StethacanthusEntity.class, EntityDataSerializers.BOOLEAN);

    // Movement animations
    private static final RawAnimation STETHA_SWIM = RawAnimation.begin().thenLoop("animation.stethacanthus.swim");
    private static final RawAnimation STETHA_FLOP = RawAnimation.begin().thenLoop("animation.stethacanthus.flop");

    // Idle animations
    private static final RawAnimation STETHA_IDLE = RawAnimation.begin().thenLoop("animation.stethacanthus.idle");

    // Attack animations
    private static final RawAnimation STETHA_ATTACK = RawAnimation.begin().thenLoop("animation.stethacanthus.attack");

    // States
    @Override
    public ImmutableMap<String, StateHelper> getStates() {
        return null;
    }
    @Override
    public List<WeightedState<StateHelper>> getWeightedStatesToPerform() {
        return List.of();
    }

    // Body control / navigation
    @Override
    protected @NotNull BodyRotationControl createBodyControl() {
        SmartBodyHelper helper = new SmartBodyHelper(this);
        helper.bodyLagMoving = 0.3F;
        helper.bodyLagStill = 0.25F;
        return helper;
    }

    public StethacanthusEntity(EntityType<? extends SchoolingAquaticEntity> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 8.0D)
                .add(Attributes.ATTACK_DAMAGE, 3.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.9F)
                .add(Attributes.FOLLOW_RANGE, 16.0F);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new TryFindWaterGoal(this));
        this.goalSelector.addGoal(3, new AvoidEntityGoal<>(this, Player.class, 6.0F, 1.5D, 1.0D, EntitySelector.NO_SPECTATORS::test));
        this.goalSelector.addGoal(3, new AvoidEntityGoal<>(this, LivingEntity.class, 8.0F, 1.5D, 1.0D, entity -> entity.getType().is(UPEntityTypeTags.STETHA_AVOIDS)));
        this.goalSelector.addGoal(1, new StethacanthusAttackGoal());
        this.goalSelector.addGoal(4, new RandomSwimmingGoal(this, 1.0D, 10));
        this.goalSelector.addGoal(4, new AquaticJumpGoal(this, 20));
        this.goalSelector.addGoal(6, new StethacanthusFleeGoal());
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, LivingEntity.class, 180, true, true, entity -> entity.getType().is(UPEntityTypeTags.STETHA_TARGETS)));
    }

    // Schooling
    public int getMaxSchoolSize() {
        return 3;
    }

    @Override
    protected float getStandingEyeHeight(Pose pPose, EntityDimensions pSize) {
        return pSize.height * 0.55F;
    }

    public void travel(Vec3 pTravelVector) {
        if (this.isEffectiveAi() && this.isInWater()) {
            this.moveRelative(this.getSpeed(), pTravelVector);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.9D));
            if (this.getTarget() == null) {
                this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.005D, 0.0D));
            }
        } else {
            super.travel(pTravelVector);
        }
    }

    // Flop
    @Override
    public void aiStep() {
        if (!this.isInWater() && this.onGround() && this.verticalCollision) {
            this.setDeltaMovement(this.getDeltaMovement().add((this.random.nextFloat() * 2.0F - 1.0F) * 0.05F, 0.4F, (this.random.nextFloat() * 2.0F - 1.0F) * 0.05F));
            this.setOnGround(false);
            this.hasImpulse = true;
            this.playSound(this.getFlopSound(), this.getSoundVolume(), this.getVoicePitch());
        }
        super.aiStep();
    }

    @Override
    public void customServerAiStep() {
        if (this.getMoveControl().hasWanted()) {
            this.setRunning(this.getMoveControl().getSpeedModifier() >= 1.38D);
        } else {
            super.customServerAiStep();
        }
    }

    // Sounds
    protected SoundEvent getAmbientSound() {
        return SoundEvents.TROPICAL_FISH_AMBIENT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.TROPICAL_FISH_DEATH;
    }

    protected SoundEvent getHurtSound(DamageSource p_28281_) {
        return SoundEvents.TROPICAL_FISH_HURT;
    }

    protected SoundEvent getFlopSound() {
        return SoundEvents.TROPICAL_FISH_FLOP;
    }

    // Synched data
    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(FROM_BUCKET, false);
    }

    // Save data
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("FromBucket", this.isFromBucket());
        compound.putBoolean("Bucketed", this.fromBucket());
    }
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setFromBucket(compound.getBoolean("FromBucket"));
        this.setFromBucket(compound.getBoolean("Bucketed"));
    }

    public boolean requiresCustomPersistence() {
        return super.requiresCustomPersistence() || this.fromBucket() || this.hasCustomName();
    }

    public boolean removeWhenFarAway(double p_213397_1_) {
        return !this.fromBucket() && !this.hasCustomName();
    }

    @Override
    public boolean fromBucket() {
        return this.entityData.get(FROM_BUCKET);
    }

    private boolean isFromBucket() {
        return this.entityData.get(FROM_BUCKET);
    }

    public void setFromBucket(boolean p_203706_1_) {
        this.entityData.set(FROM_BUCKET, p_203706_1_);
    }

    @Override
    public void saveToBucketTag(ItemStack bucket) {
        CompoundTag compoundnbt = bucket.getOrCreateTag();
        Bucketable.saveDefaultDataToBucketTag(this, bucket);
        compoundnbt.putFloat("Health", this.getHealth());
        if (this.hasCustomName()) {
            bucket.setHoverName(this.getCustomName());
        }
    }

    @Override
    public void loadFromBucketTag(CompoundTag compound) {
        Bucketable.loadDefaultDataFromBucketTag(this, compound);
    }

    @Override
    public ItemStack getBucketItemStack() {
        return new ItemStack(UPItems.STETHA_BUCKET.get());
    }

    @Override
    public SoundEvent getPickupSound() {
        return SoundEvents.BUCKET_EMPTY_FISH;
    }

    public InteractionResult mobInteract(Player p_27477_, InteractionHand p_27478_) {
        return Bucketable.bucketMobPickup(p_27477_, p_27478_, this).orElse(super.mobInteract(p_27477_, p_27478_));
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel serverLevel, @NotNull AgeableMob ageableMob) {
        return UPEntities.STETHACANTHUS.get().create(serverLevel);
    }

    // Animation control
    @Override
    public void registerControllers(final AnimatableManager.ControllerRegistrar controllers) {
        AnimationController<StethacanthusEntity> controller = new AnimationController<>(this, "controller", 5, this::predicate);
        controllers.add(controller);

        AnimationController<StethacanthusEntity> attack = new AnimationController<>(this, "attackController", 5, this::attackPredicate);
        controllers.add(attack);
    }

    protected <E extends StethacanthusEntity> PlayState predicate(final software.bernie.geckolib.core.animation.AnimationState<E> event) {
        if (this.isFromBook()) {
            return event.setAndContinue(STETHA_SWIM);
        }
        if (this.getAnimationState() != 21) {
            if (!(event.getLimbSwingAmount() > -0.06F && event.getLimbSwingAmount() < 0.06F) && this.isInWater()) {
                if (this.isRunning()) {
                    event.setAndContinue(STETHA_SWIM);
                    event.getController().setAnimationSpeed(1.6F);
                } else {
                    event.setAndContinue(STETHA_SWIM);
                    event.getController().setAnimationSpeed(1.0F);
                }
                return PlayState.CONTINUE;
            } else if (this.isInWater()) {
                event.setAndContinue(STETHA_IDLE);
            }
        }
        else event.setAndContinue(STETHA_FLOP);
        return PlayState.CONTINUE;
    }

    // Attack animations
    protected <E extends StethacanthusEntity> PlayState attackPredicate(final AnimationState<E> event) {
        int animState = this.getAnimationState();
        if (animState == 21) {
            event.setAndContinue(STETHA_ATTACK);
            return PlayState.CONTINUE;
        }
        else if (animState == 0) {
            event.getController().forceAnimationReset();
            return PlayState.STOP;
        }
        else return PlayState.CONTINUE;
    }

    // Goals
    class StethacanthusAttackGoal extends Goal {
        private int attackTime = 0;

        public StethacanthusAttackGoal() {
            this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        }

        public boolean canUse() {
            LivingEntity target = StethacanthusEntity.this.getTarget();
            return target != null && target.isAlive() && target.isInWater() && !target.getType().is(UPEntityTypeTags.STETHA_AVOIDS) && !(target instanceof Player);
        }

        public void start() {
            StethacanthusEntity.this.setAnimationState(0);
            this.attackTime = 0;
        }

        public void stop() {
            StethacanthusEntity.this.setAnimationState(0);
        }

        public void tick() {
            LivingEntity target = StethacanthusEntity.this.getTarget();
            if (target != null && target.isInWater()) {
                StethacanthusEntity.this.lookAt(StethacanthusEntity.this.getTarget(), 30F, 30F);
                StethacanthusEntity.this.getLookControl().setLookAt(StethacanthusEntity.this.getTarget(), 30F, 30F);

                double distance = StethacanthusEntity.this.distanceToSqr(target.getX(), target.getY(), target.getZ());
                int animState = StethacanthusEntity.this.getAnimationState();

                if (animState == 21) {
                    tickBiteAttack();
                    StethacanthusEntity.this.getNavigation().moveTo(target, 0.75D);
                } else {
                    StethacanthusEntity.this.getNavigation().moveTo(target, 1.4D);
                    this.checkForCloseRangeAttack(distance);
                }
            }
        }

        protected void checkForCloseRangeAttack (double distance){
            if (distance <= 4) {
                StethacanthusEntity.this.setAnimationState(21);
            }
        }

        protected void tickBiteAttack() {
            attackTime++;
            if (attackTime ==9) {
                if (StethacanthusEntity.this.distanceTo(StethacanthusEntity.this.getTarget()) < 1.5F) {
                    StethacanthusEntity.this.doHurtTarget(StethacanthusEntity.this.getTarget());
                }
            }
            if (attackTime >=15) {
                attackTime =0;
                StethacanthusEntity.this.setAnimationState(0);
            }
        }
    }

    class StethacanthusFleeGoal extends PrehistoricPanicGoal {
        public StethacanthusFleeGoal() {
            super(StethacanthusEntity.this, 1.4D);
        }

        @Override
        protected boolean findRandomPosition() {
            Vec3 vec3 = DefaultRandomPos.getPos(this.mob, 12, 10);
            if (vec3 == null) {
                return false;
            } else {
                this.posX = vec3.x;
                this.posY = vec3.y;
                this.posZ = vec3.z;
                return true;
            }
        }
    }
}