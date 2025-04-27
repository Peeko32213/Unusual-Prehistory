package com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.aquatic;

import com.google.common.collect.ImmutableMap;
import com.peeko32213.unusualprehistory.common.entity.animation.state.StateHelper;
import com.peeko32213.unusualprehistory.common.entity.animation.state.WeightedState;
import com.peeko32213.unusualprehistory.common.entity.custom.ai.goal.FollowVariantLeaderGoal;
import com.peeko32213.unusualprehistory.common.entity.custom.base.SchoolingAquaticEntity;
import com.peeko32213.unusualprehistory.common.entity.util.navigator.SmartBodyHelper;
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
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

import javax.annotation.Nullable;
import java.util.List;
import java.util.stream.Stream;

public class ScaumenaciaEntity extends SchoolingAquaticEntity implements Bucketable {

    private static final EntityDataAccessor<Boolean> FROM_BUCKET = SynchedEntityData.defineId(ScaumenaciaEntity.class, EntityDataSerializers.BOOLEAN);

    private static final RawAnimation SCAU_SWIM = RawAnimation.begin().thenLoop("animation.scaumenacia.swim");
    private static final RawAnimation SCAU_IDLE = RawAnimation.begin().thenLoop("animation.scaumenacia.idle");
    private static final RawAnimation SCAU_FLOP = RawAnimation.begin().thenLoop("animation.scaumenacia.flop");

    @Override
    public ImmutableMap<String, StateHelper> getStates() {
        return null;
    }

    @Override
    public List<WeightedState<StateHelper>> getWeightedStatesToPerform() {
        return List.of();
    }

    @Override
    protected @NotNull BodyRotationControl createBodyControl() {
        SmartBodyHelper helper = new SmartBodyHelper(this);
        helper.bodyLagMoving = 0.35F;
        helper.bodyLagStill = 0.2F;
        return helper;
    }

    public ScaumenaciaEntity(EntityType<? extends SchoolingAquaticEntity> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 6.0D).add(Attributes.MOVEMENT_SPEED, 0.8F);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new TryFindWaterGoal(this));
        this.goalSelector.addGoal(4, new RandomSwimmingGoal(this, 1.0D, 10));
        this.goalSelector.addGoal(1, new FollowVariantLeaderGoal(this));
    }

    // Schooling
    public int getMaxSchoolSize() {
        return 6;
    }

    @Override
    public void addFollowers(Stream<? extends SchoolingAquaticEntity> entity) {
        entity.limit(this.getMaxSchoolSize() - this.schoolSize).filter((entity1) -> entity1 != this).forEach((entity2) -> entity2.startFollowing(this));
    }

    // Flop
    @Override
    public void aiStep() {
        super.aiStep();
        if (!this.isInWater() && this.onGround() && this.verticalCollision) {
            this.setDeltaMovement(this.getDeltaMovement().add((this.random.nextFloat() * 2.0F - 1.0F) * 0.05F, 0.4F, (this.random.nextFloat() * 2.0F - 1.0F) * 0.05F));
            this.setOnGround(false);
            this.hasImpulse = true;
            this.playSound(this.getFlopSound(), this.getSoundVolume(), this.getVoicePitch());
        }
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

    @Override
    protected float getStandingEyeHeight(Pose pPose, EntityDimensions pSize) {
        return pSize.height * 0.6F;
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.COD_AMBIENT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.COD_DEATH;
    }

    protected SoundEvent getHurtSound(DamageSource p_28281_) {
        return SoundEvents.COD_HURT;
    }

    protected SoundEvent getFlopSound() {
        return SoundEvents.COD_FLOP;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(FROM_BUCKET, false);
    }

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

    @Override
    public boolean fromBucket() {
        return this.entityData.get(FROM_BUCKET);
    }

    @Override
    public void saveToBucketTag(ItemStack bucket) {
        CompoundTag compoundnbt = bucket.getOrCreateTag();
        compoundnbt.putFloat("Health", this.getHealth());
        if (this.hasCustomName()) {
            bucket.setHoverName(this.getCustomName());
        }
    }

    public boolean requiresCustomPersistence() {
        return super.requiresCustomPersistence() || this.fromBucket();
    }
    public boolean removeWhenFarAway(double p_213397_1_) {
        return !this.fromBucket() && !this.hasCustomName();
    }

    private boolean isFromBucket() {
        return this.entityData.get(FROM_BUCKET);
    }
    public void setFromBucket(boolean p_203706_1_) {
        this.entityData.set(FROM_BUCKET, p_203706_1_);
    }

    @Override
    public void loadFromBucketTag(CompoundTag p_148832_) {

    }

    @Override
    public SoundEvent getPickupSound() {
        return SoundEvents.BUCKET_EMPTY_FISH;
    }

    public InteractionResult mobInteract(Player p_27477_, InteractionHand p_27478_) {
        return Bucketable.bucketMobPickup(p_27477_, p_27478_, this).orElse(super.mobInteract(p_27477_, p_27478_));
    }

    @Override
    public ItemStack getBucketItemStack() {
        ItemStack stack = new ItemStack(UPItems.SCAU_BUCKET.get());
        if (this.hasCustomName()) {
            stack.setHoverName(this.getCustomName());
        }
        return stack;
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel serverLevel, @NotNull AgeableMob ageableMob) {
        ScaumenaciaEntity scaumenacia = UPEntities.SCAU.get().create(serverLevel);
        scaumenacia.setVariant(this.getVariant());
        return scaumenacia;
    }

    // Variants
    public void determineVariant(int variantChange){
        if (variantChange <= 1) {
            this.setVariant(1);
        }
        else {
            this.setVariant(0);
        }
    }

    @Override
    public void registerControllers(final AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "Normal", 5, this::Controller));
    }

    protected <E extends ScaumenaciaEntity> PlayState Controller(final software.bernie.geckolib.core.animation.AnimationState<E> event) {
        if(this.isInWater()) {
            if (!(event.getLimbSwingAmount() > -0.06F && event.getLimbSwingAmount() < 0.06F)) {
                event.setAndContinue(SCAU_SWIM);
            }
            else event.setAndContinue(SCAU_IDLE);
        }
        else event.setAndContinue(SCAU_FLOP);
        return PlayState.CONTINUE;
    }
}
