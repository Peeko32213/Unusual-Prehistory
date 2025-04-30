 package com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.aquatic;

 import com.google.common.collect.ImmutableList;
 import com.google.common.collect.ImmutableMap;
 import com.peeko32213.unusualprehistory.common.entity.animation.state.EntityAction;
 import com.peeko32213.unusualprehistory.common.entity.animation.state.RandomStateGoal;
 import com.peeko32213.unusualprehistory.common.entity.animation.state.StateHelper;
 import com.peeko32213.unusualprehistory.common.entity.animation.state.WeightedState;
 import com.peeko32213.unusualprehistory.common.entity.custom.ai.goal.GroundseekingRandomSwimGoal;
 import com.peeko32213.unusualprehistory.common.entity.custom.ai.goal.ophiodon.OphiodonAttackGoal;
 import com.peeko32213.unusualprehistory.common.entity.custom.base.PrehistoricAquaticEntity;
 import com.peeko32213.unusualprehistory.common.entity.util.navigator.SmartBodyHelper;
 import com.peeko32213.unusualprehistory.core.registry.UPSounds;
 import com.peeko32213.unusualprehistory.core.other.tags.UPEntityTypeTags;
 import com.peeko32213.unusualprehistory.core.registry.entities.UPEntities;
 import net.minecraft.nbt.CompoundTag;
 import net.minecraft.network.syncher.EntityDataAccessor;
 import net.minecraft.network.syncher.EntityDataSerializers;
 import net.minecraft.network.syncher.SynchedEntityData;
 import net.minecraft.server.level.ServerLevel;
 import net.minecraft.sounds.SoundEvent;
 import net.minecraft.sounds.SoundEvents;
 import net.minecraft.world.DifficultyInstance;
 import net.minecraft.world.damagesource.DamageSource;
 import net.minecraft.world.entity.*;
 import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
 import net.minecraft.world.entity.ai.attributes.Attributes;
 import net.minecraft.world.entity.ai.control.BodyRotationControl;
 import net.minecraft.world.entity.ai.goal.*;
 import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
 import net.minecraft.world.level.Level;
 import net.minecraft.world.level.ServerLevelAccessor;
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
 import java.util.function.Predicate;

 public class OphiodonEntity extends PrehistoricAquaticEntity {

     // Movement animations
     private static final RawAnimation OPHIODON_SWIM = RawAnimation.begin().thenLoop("animation.ophiodon.swim");
     private static final RawAnimation OPHIODON_FLOP = RawAnimation.begin().thenLoop("animation.ophiodon.flop");

     // Idle animations
     private static final RawAnimation OPHIODON_IDLE = RawAnimation.begin().thenLoop("animation.ophiodon.idle");
     private static final RawAnimation OPHIODON_REST = RawAnimation.begin().thenLoop("animation.ophiodon.rest");
     private static final RawAnimation OPHIODON_PATROL = RawAnimation.begin().thenPlay("animation.ophiodon.patrol");
     private static final RawAnimation OPHIODON_SIEVE = RawAnimation.begin().thenPlay("animation.ophiodon.sieve");

     // Attack animations
     private static final RawAnimation OPHIODON_ATTACK = RawAnimation.begin().thenPlay("animation.ophiodon.attack");

     // Idle accesors
     public static final EntityDataAccessor<Boolean> PATROL = SynchedEntityData.defineId(OphiodonEntity.class, EntityDataSerializers.BOOLEAN);
     public static final EntityDataAccessor<Boolean> SIEVE = SynchedEntityData.defineId(OphiodonEntity.class, EntityDataSerializers.BOOLEAN);

     // Starting predicates
     private static final Predicate<LivingEntity> OPHIODON_STARTING_PREDICATE = (e -> {
         if(e instanceof OphiodonEntity entity) {
             return !entity.isRunning() && !entity.isSprinting() && entity.isInWater() && !entity.onGround();
         }
         return false;
     });

     private static final Predicate<LivingEntity> OPHIODON_SIEVE_PREDICATE = (e -> {
         if(e instanceof OphiodonEntity entity) {
             return !entity.isRunning() && !entity.isSprinting() && entity.isInWater() && entity.onGround();
         }
         return false;
     });

     // Idle actions
     private static final EntityAction OPHIODON_PATROL_ACTION = new EntityAction(0, (e) -> {}, 1);
     private static final StateHelper OPHIODON_PATROL_STATE =
             StateHelper.Builder.state(PATROL, "ophiodon_patrol")
                     .playTime(80)
                     .stopTime(250)
                     .startingPredicate(OPHIODON_STARTING_PREDICATE)
                     .affectsAI(true)
                     .affectedFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK))
                     .entityAction(OPHIODON_PATROL_ACTION)
                     .build();

     private static final EntityAction OPHIODON_SIEVE_ACTION = new EntityAction(0, (e) -> {}, 1);
     private static final StateHelper OPHIODON_SIEVE_STATE =
             StateHelper.Builder.state(SIEVE, "ophiodon_patrol")
                     .playTime(80)
                     .stopTime(300)
                     .startingPredicate(OPHIODON_SIEVE_PREDICATE)
                     .affectsAI(true)
                     .affectedFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK))
                     .entityAction(OPHIODON_SIEVE_ACTION)
                     .build();

     @Override
     public ImmutableMap<String, StateHelper> getStates() {
         return ImmutableMap.of(
                 OPHIODON_PATROL_STATE.getName(), OPHIODON_PATROL_STATE,
                 OPHIODON_SIEVE_STATE.getName(), OPHIODON_SIEVE_STATE
         );
     }

     @Override
     public List<WeightedState<StateHelper>> getWeightedStatesToPerform() {
         return ImmutableList.of(
                 WeightedState.of(OPHIODON_PATROL_STATE, 10),
                 WeightedState.of(OPHIODON_SIEVE_STATE, 10)
         );
     }

     // Body control / navigation
     @Override
     protected @NotNull BodyRotationControl createBodyControl() {
         SmartBodyHelper helper = new SmartBodyHelper(this);
         helper.bodyLagMoving = 0.3F;
         helper.bodyLagStill = 0.2F;
         return helper;
     }

     public OphiodonEntity(EntityType<? extends PrehistoricAquaticEntity> entityType, Level level) {
         super(entityType, level);
     }

     public static AttributeSupplier.Builder createAttributes() {
         return Mob.createMobAttributes()
                 .add(Attributes.MAX_HEALTH, 24.0D)
                 .add(Attributes.ATTACK_DAMAGE, 5.0D)
                 .add(Attributes.MOVEMENT_SPEED, 1.0F)
                 .add(Attributes.FOLLOW_RANGE, 16.0D);
     }

     protected void registerGoals() {
         this.goalSelector.addGoal(0, new RandomStateGoal<>(this));
         this.goalSelector.addGoal(0, new TryFindWaterGoal(this));
         this.goalSelector.addGoal(1, new OphiodonAttackGoal(this));
         this.goalSelector.addGoal(1, new GroundseekingRandomSwimGoal(this, 1.0D, 100, 12, 12, 0.01));
         this.targetSelector.addGoal(6, new NearestAttackableTargetGoal<>(this, LivingEntity.class, 50, true, true, entity -> entity.getType().is(UPEntityTypeTags.OPHIODON_TARGETS)));
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

     protected SoundEvent getAmbientSound() {
         return SoundEvents.COD_AMBIENT;
     }

     protected SoundEvent getHurtSound(@NotNull DamageSource damageSourceIn) {
         return UPSounds.DUNK_HURT.get();
     }

     protected SoundEvent getDeathSound() {
         return UPSounds.DUNK_DEATH.get();
     }

     protected SoundEvent getFlopSound() {
         return SoundEvents.COD_FLOP;
     }

     protected void defineSynchedData() {
         super.defineSynchedData();
         this.entityData.define(PATROL, false);
         this.entityData.define(SIEVE, false);
     }

     public void addAdditionalSaveData(CompoundTag compound) {
         super.addAdditionalSaveData(compound);
     }

     public void readAdditionalSaveData(CompoundTag compound) {
         super.readAdditionalSaveData(compound);
     }

     // Animation control
     @Override
     public void registerControllers(final AnimatableManager.ControllerRegistrar controllers) {
         AnimationController<OphiodonEntity> controller = new AnimationController<>(this, "controller", 5, this::predicate);
         controllers.add(controller);

         AnimationController<OphiodonEntity> attack = new AnimationController<>(this, "attackController", 5, this::attackPredicate);
         controllers.add(attack);
     }

     protected <E extends OphiodonEntity> PlayState predicate(final software.bernie.geckolib.core.animation.AnimationState<E> event) {
         if (this.isInWater() && !(this.getAnimationState() > 0)) {
             if (getBooleanState(PATROL)) {
                 event.getController().setAnimation(OPHIODON_PATROL);
                 return PlayState.CONTINUE;
             }
             if (getBooleanState(SIEVE)) {
                 event.getController().setAnimation(OPHIODON_SIEVE);
                 return PlayState.CONTINUE;
             }
         }
         if (!(this.getAnimationState() > 0) && !getBooleanState(PATROL) && !getBooleanState(SIEVE)) {
             if (!(event.getLimbSwingAmount() > -0.06F && event.getLimbSwingAmount() < 0.06F) && this.isInWater()) {
                 event.setAndContinue(OPHIODON_SWIM);
                 event.getController().setAnimationSpeed(1.0F);
                 return PlayState.CONTINUE;
             }
             if (!this.isInWater()) {
                 event.setAndContinue(OPHIODON_FLOP);
                 event.getController().setAnimationSpeed(1.0F);
                 return PlayState.CONTINUE;
             }
             else {
                 event.setAndContinue(OPHIODON_IDLE);
             }
         }
         return PlayState.CONTINUE;
     }

     // Attack animations
     protected <E extends OphiodonEntity> PlayState attackPredicate(final AnimationState<E> event) {
         int animState = this.getAnimationState();
         if (animState == 21) {
             event.setAndContinue(OPHIODON_ATTACK);
             return PlayState.CONTINUE;
         }
         else if (animState == 0) {
             event.getController().forceAnimationReset();
             return PlayState.STOP;
         }
         else return PlayState.CONTINUE;
     }

     @Nullable
     public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {
         return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
     }

     // Variants
     public void determineVariant(int variantChange){
         if (variantChange <= 50) {
             this.setVariant(1);
         }
         else {
             this.setVariant(0);
         }
     }

     @Nullable
     @Override
     public AgeableMob getBreedOffspring(@NotNull ServerLevel serverLevel, @NotNull AgeableMob ageableMob) {
         OphiodonEntity ophiodon = UPEntities.OPHIODON.get().create(serverLevel);
         ophiodon.setVariant(this.getVariant());
         return ophiodon;
     }
 }
