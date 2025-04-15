 package com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.aquatic;

 import com.google.common.collect.ImmutableMap;
 import com.peeko32213.unusualprehistory.common.entity.animation.state.StateHelper;
 import com.peeko32213.unusualprehistory.common.entity.animation.state.WeightedState;
 import com.peeko32213.unusualprehistory.common.entity.custom.ai.goal.GroundseekingRandomSwimGoal;
 import com.peeko32213.unusualprehistory.common.entity.custom.base.PrehistoricAquaticEntity;
 import com.peeko32213.unusualprehistory.common.entity.util.navigator.SmartBodyHelper;
 import com.peeko32213.unusualprehistory.core.registry.entities.UPEntities;
 import net.minecraft.nbt.CompoundTag;
 import net.minecraft.server.level.ServerLevel;
 import net.minecraft.sounds.SoundEvent;
 import net.minecraft.sounds.SoundEvents;
 import net.minecraft.world.damagesource.DamageSource;
 import net.minecraft.world.entity.*;
 import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
 import net.minecraft.world.entity.ai.attributes.Attributes;
 import net.minecraft.world.entity.ai.control.BodyRotationControl;
 import net.minecraft.world.entity.ai.goal.TryFindWaterGoal;
 import net.minecraft.world.level.Level;
 import net.minecraft.world.phys.Vec3;
 import org.jetbrains.annotations.NotNull;
 import org.jetbrains.annotations.Nullable;
 import software.bernie.geckolib.core.animation.AnimatableManager;
 import software.bernie.geckolib.core.animation.AnimationController;
 import software.bernie.geckolib.core.animation.RawAnimation;
 import software.bernie.geckolib.core.object.PlayState;

 import java.util.List;

 //TODO LIST
 // - Burrowing At Night

 public class TartuosteusEntity extends PrehistoricAquaticEntity {

     // Movement animations
     private static final RawAnimation TARTUO_SWIM = RawAnimation.begin().thenLoop("animation.tartuosteus.swim");
     private static final RawAnimation TARTUO_FLOP = RawAnimation.begin().thenLoop("animation.tartuosteus.flop");

     // Idle animations
     private static final RawAnimation TARTUO_IDLE = RawAnimation.begin().thenLoop("animation.tartuosteus.idle");
     private static final RawAnimation TARTUO_REST = RawAnimation.begin().thenLoop("animation.tartuosteus.rest");
     private static final RawAnimation TARTUO_BURROW_START = RawAnimation.begin().thenLoop("animation.tartuosteus.burrow_start");
     private static final RawAnimation TARTUO_BURROW_HOLD = RawAnimation.begin().thenLoop("animation.tartuosteus.burrow_hold");
     private static final RawAnimation TARTUO_BURROW_END = RawAnimation.begin().thenLoop("animation.tartuosteus.burrow_end");

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
         helper.bodyLagStill = 0.2F;
         return helper;
     }

     public TartuosteusEntity(EntityType<? extends PrehistoricAquaticEntity> entityType, Level level) {
         super(entityType, level);
     }

     public static AttributeSupplier.Builder createAttributes() {
         return Mob.createMobAttributes()
                 .add(Attributes.MAX_HEALTH, 16.0D)
                 .add(Attributes.ARMOR, 10.0D)
                 .add(Attributes.MOVEMENT_SPEED, 0.5F);
     }

     protected void registerGoals() {
         this.goalSelector.addGoal(0, new TryFindWaterGoal(this));
         this.goalSelector.addGoal(1, new GroundseekingRandomSwimGoal(this, 1.0D, 75, 12, 12, 0.05));
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
         return pSize.height * 0.55F;
     }

     @Override
     protected SoundEvent getAmbientSound() {
         return SoundEvents.COD_AMBIENT;
     }

     @Override
     protected SoundEvent getDeathSound() {
         return SoundEvents.COD_DEATH;
     }

     @Override
     protected SoundEvent getHurtSound(@NotNull DamageSource damageSource) {
         return SoundEvents.COD_HURT;
     }

     @Override
     protected SoundEvent getFlopSound() {
         return SoundEvents.COD_FLOP;
     }

     @Nullable
     @Override
     public AgeableMob getBreedOffspring(@NotNull ServerLevel serverLevel, @NotNull AgeableMob ageableMob) {
         TartuosteusEntity tartuosteus = UPEntities.TARTUOSTEUS.get().create(serverLevel);
         tartuosteus.setVariant(this.getVariant());
         return tartuosteus;
     }

     protected void defineSynchedData() {
         super.defineSynchedData();
     }

     public void addAdditionalSaveData(CompoundTag compound) {
         super.addAdditionalSaveData(compound);
     }

     public void readAdditionalSaveData(CompoundTag compound) {
         super.readAdditionalSaveData(compound);
     }

     // Variants
     public void determineVariant(int variantChange){
         if (variantChange <= 25) {
             this.setVariant(1);
         }
         else {
             this.setVariant(0);
         }
     }

     // Animation control
     @Override
     public void registerControllers(final AnimatableManager.ControllerRegistrar controllers) {
         AnimationController<TartuosteusEntity> controller = new AnimationController<>(this, "controller", 5, this::predicate);
         controllers.add(controller);
     }

     protected <E extends TartuosteusEntity> PlayState predicate(final software.bernie.geckolib.core.animation.AnimationState<E> event) {

         if (this.isFromBook()) {
             return event.setAndContinue(TARTUO_IDLE);
         }

         if (!(event.getLimbSwingAmount() > -0.06F && event.getLimbSwingAmount() < 0.06F) && this.isInWater()) {
             event.setAndContinue(TARTUO_SWIM);
             return PlayState.CONTINUE;
         }
         if (!this.isInWater()) {
             event.setAndContinue(TARTUO_FLOP);
             event.getController().setAnimationSpeed(2.0F);
             return PlayState.CONTINUE;
         }

         if(playingAnimation())
         {
             return PlayState.CONTINUE;
         }

         if (isStillEnough() && this.isInWater()) {
             event.setAndContinue(TARTUO_IDLE);
             return PlayState.CONTINUE;
         }
         return PlayState.CONTINUE;
     }
 }
