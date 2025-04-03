 package com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.semi_aquatic;

 import com.google.common.collect.ImmutableList;
 import com.google.common.collect.ImmutableMap;
 import com.peeko32213.unusualprehistory.common.entity.animation.state.EntityAction;
 import com.peeko32213.unusualprehistory.common.entity.animation.state.StateHelper;
 import com.peeko32213.unusualprehistory.common.entity.animation.state.WeightedState;
 import com.peeko32213.unusualprehistory.common.entity.custom.ai.goal.FindWaterGoal;
 import com.peeko32213.unusualprehistory.common.entity.custom.ai.goal.LeaveWaterGoal;
 import com.peeko32213.unusualprehistory.common.entity.custom.ai.goal.SemiAquaticSwimmingGoal;
 import com.peeko32213.unusualprehistory.common.entity.custom.base.PrehistoricEntity;
 import com.peeko32213.unusualprehistory.common.entity.util.interfaces.ISemiAquatic;
 import com.peeko32213.unusualprehistory.common.entity.util.navigator.SemiAquaticPathNavigation;
 import com.peeko32213.unusualprehistory.common.entity.util.navigator.WaterMoveController;
 import com.peeko32213.unusualprehistory.core.other.tags.UPBlockTags;
 import com.peeko32213.unusualprehistory.core.registry.entities.UPEntities;
 import com.peeko32213.unusualprehistory.core.registry.UPSounds;
 import net.minecraft.core.BlockPos;
 import net.minecraft.nbt.CompoundTag;
 import net.minecraft.network.syncher.EntityDataAccessor;
 import net.minecraft.network.syncher.EntityDataSerializers;
 import net.minecraft.network.syncher.SynchedEntityData;
 import net.minecraft.server.level.ServerLevel;
 import net.minecraft.sounds.SoundEvent;
 import net.minecraft.sounds.SoundEvents;
 import net.minecraft.world.damagesource.DamageSource;
 import net.minecraft.world.entity.*;
 import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
 import net.minecraft.world.entity.ai.attributes.Attributes;
 import net.minecraft.world.entity.ai.control.MoveControl;
 import net.minecraft.world.entity.ai.goal.Goal;
 import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
 import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
 import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
 import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
 import net.minecraft.world.entity.player.Player;
 import net.minecraft.world.level.Level;
 import net.minecraft.world.level.block.state.BlockState;
 import net.minecraft.world.level.pathfinder.BlockPathTypes;
 import net.minecraft.world.phys.Vec3;
 import org.jetbrains.annotations.NotNull;
 import org.jetbrains.annotations.Nullable;
 import software.bernie.geckolib.core.animation.AnimatableManager;
 import software.bernie.geckolib.core.animation.AnimationController;
 import software.bernie.geckolib.core.animation.RawAnimation;
 import software.bernie.geckolib.core.object.PlayState;

 import java.util.EnumSet;
 import java.util.List;

 public class DiplocaulusEntity extends PrehistoricEntity implements ISemiAquatic {

     private static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(DiplocaulusEntity.class, EntityDataSerializers.INT);

     // Movement
     private static final RawAnimation DIPLOCAULUS_WALK = RawAnimation.begin().thenLoop("animation.diplocaulus.walk");
     private static final RawAnimation DIPLOCAULUS_SWIM = RawAnimation.begin().thenLoop("animation.diplocaulus.swim");

     // Idle animations
     private static final RawAnimation DIPLOCAULUS_IDLE = RawAnimation.begin().thenLoop("animation.diplocaulus.idle");
     private static final RawAnimation DIPLOCAULUS_SWIM_IDLE = RawAnimation.begin().thenLoop("animation.diplocaulus.swim_idle");
     private static final RawAnimation DIPLOCAULUS_BURROW_HOLD = RawAnimation.begin().thenLoop("animation.diplocaulus.burrow_hold");
     private static final RawAnimation DIPLOCAULUS_BURROW_START = RawAnimation.begin().thenLoop("animation.diplocaulus.burrow_start");
     private static final RawAnimation DIPLOCAULUS_SLIDE = RawAnimation.begin().thenLoop("animation.diplocaulus.slide");
     private static final RawAnimation DIPLOCAULUS_ACROBAT = RawAnimation.begin().thenLoop("animation.diplocaulus.acrobat");

     // Idle accessors
     private static final EntityDataAccessor<Boolean> IDLE_1_AC = SynchedEntityData.defineId(DiplocaulusEntity.class, EntityDataSerializers.BOOLEAN);

     // Idle actions
     private static final EntityAction DIPLOCAULUS_IDLE_1_ACTION = new EntityAction(0, (e) -> {}, 1);

     private final StateHelper DIPLOCAULUS_IDLE_1_STATE =
             StateHelper.Builder.state(IDLE_1_AC, "diplocaulus_burrow")
                     .playTime(200)
                     .stopTime(300)
                     .affectsAI(true)
                     .affectedFlags(EnumSet.of(Goal.Flag.MOVE))
                     .entityAction(DIPLOCAULUS_IDLE_1_ACTION)
                     .build();

     @Override
     public ImmutableMap<String, StateHelper> getStates() {
         return ImmutableMap.of(
                 DIPLOCAULUS_IDLE_1_STATE.getName(), DIPLOCAULUS_IDLE_1_STATE
         );
     }

     @Override
     public List<WeightedState<StateHelper>> getWeightedStatesToPerform() {
         return ImmutableList.of(
                 WeightedState.of(DIPLOCAULUS_IDLE_1_STATE, 10)
         );
     }

     @Override
     public boolean getAction() {
         return false;
     }

     @Override
     public void setAction(boolean action) {}

     public float prevSwimProgress;
     public float swimProgress;
     private int swimTimer = -1000;
     private boolean isLandNavigator;

     public DiplocaulusEntity(EntityType<? extends PrehistoricEntity> entityType, Level level) {
         super(entityType, level);
         this.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
         this.setPathfindingMalus(BlockPathTypes.WATER_BORDER, 0.0F);
         switchNavigator(false);
     }

     public static AttributeSupplier.Builder createAttributes() {
         return Mob.createMobAttributes()
             .add(Attributes.MAX_HEALTH, 10.0D)
             .add(Attributes.MOVEMENT_SPEED, 0.16D);
     }

     protected void registerGoals() {
         this.goalSelector.addGoal(7, new FindWaterGoal(this));
         this.goalSelector.addGoal(7, new LeaveWaterGoal(this));
         this.goalSelector.addGoal(9, new SemiAquaticSwimmingGoal(this, 1.0D, 10));
         this.goalSelector.addGoal(3, new RandomStrollGoal(this, 1, 30));
         this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
         this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 6.0F));
     }

     private void switchNavigator(boolean onLand) {
         if (onLand) {
             this.moveControl = new MoveControl(this);
             this.navigation = new GroundPathNavigation(this, level());
             this.isLandNavigator = true;
         } else {
             this.moveControl = new WaterMoveController(this, 1.1F);
             this.navigation = new SemiAquaticPathNavigation(this, level());
             this.isLandNavigator = false;
         }
     }

     public void tick() {
         super.tick();
         this.prevSwimProgress = swimProgress;

         final boolean ground = !this.isInWaterOrBubble();

         if (!ground && this.isLandNavigator) {
             switchNavigator(false);
         }
         if (ground && !this.isLandNavigator) {
             switchNavigator(true);
         }
         if (ground && swimProgress > 0) {
             swimProgress--;
         }
         if (!ground && swimProgress < 5F) {
             swimProgress++;
         }
         if (!this.level().isClientSide) {
             if (isInWater()) {
                 swimTimer++;
             } else {
                 swimTimer--;
             }
         }
     }

     public void travel(Vec3 travelVector) {
          if (this.isEffectiveAi() && this.isInWater()) {
             this.moveRelative(this.getSpeed(), travelVector);
             this.move(MoverType.SELF, this.getDeltaMovement());
             this.setDeltaMovement(this.getDeltaMovement().scale(0.9D));
             if (this.getTarget() == null) {
                 this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.005D, 0.0D));
             }
         }
          else {
             super.travel(travelVector);
         }
     }

     @Override
     protected void defineSynchedData() {
         this.entityData.define(IDLE_1_AC, false);
         this.entityData.define(VARIANT, 0);
         super.defineSynchedData();
     }

     public void addAdditionalSaveData(CompoundTag compound) {
         super.addAdditionalSaveData(compound);
         compound.putInt("Variant", this.getVariant());
         compound.putInt("SwimTimer", this.swimTimer);
     }

     public void readAdditionalSaveData(CompoundTag compound) {
         super.readAdditionalSaveData(compound);
         this.setVariant(compound.getInt("Variant"));
         this.swimTimer = compound.getInt("SwimTimer");
     }

     public boolean canBreatheUnderwater() {
         return true;
     }

     protected SoundEvent getAmbientSound() {
         return UPSounds.DIPLO_IDLE.get();
     }

     protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
         return UPSounds.DIPLO_HURT.get();
     }

     protected SoundEvent getDeathSound() {
         return UPSounds.DIPLO_DEATH.get();
     }

     protected void playStepSound(BlockPos p_28301_, BlockState p_28302_) {
         this.playSound(SoundEvents.FROG_STEP, 0.1F, 1.5F);
     }

     @Override
     public float getSoundVolume() {
         return 0.5F;
     }

     @Nullable
     @Override
     public AgeableMob getBreedOffspring(@NotNull ServerLevel serverLevel, @NotNull AgeableMob ageableMob) {
         DiplocaulusEntity diplo = UPEntities.DIPLOCAULUS.get().create(serverLevel);
         diplo.setVariant(this.getVariant());
         return diplo;
     }

     @Override
     public boolean shouldEnterWater() {
         return !shouldLeaveWater() && swimTimer <= -1000;
     }

     public boolean shouldLeaveWater() {
         LivingEntity target = this.getTarget();
         if (target != null && !target.isInWater()) {
             return true;
         }
         return swimTimer > 600;
     }

     @Override
     public int getWaterSearchRange() {
         return 12;
     }

     @Override
     public boolean shouldStopMoving() {
         return false;
     }

     @Override
     public void registerControllers(final AnimatableManager.ControllerRegistrar controllers) {
         controllers.add(new AnimationController<>(this, "Normal", 5, this::Controller));
     }

     protected <E extends DiplocaulusEntity> PlayState Controller(final software.bernie.geckolib.core.animation.AnimationState<E> event) {

         if (this.isFromBook()) {
             return event.setAndContinue(DIPLOCAULUS_SWIM_IDLE);
         }

         if (event.isMoving() && !this.isInWater() && !this.isSwimming()) {
             event.setAnimation(DIPLOCAULUS_WALK);
             return PlayState.CONTINUE;
         }

         if (!(event.getLimbSwingAmount() > -0.06F && event.getLimbSwingAmount() < 0.06F) && this.isInWater()) {
             event.setAnimation(DIPLOCAULUS_SWIM);
             return PlayState.CONTINUE;
         }

         if (isStillEnough()) {
             return event.setAndContinue(DIPLOCAULUS_IDLE);
         }
         else if (isStillEnough() && this.isInWater()) {
             return event.setAndContinue(DIPLOCAULUS_SWIM_IDLE);
         }

         if (!this.isInWater()) {
             if (getBooleanState(IDLE_1_AC) && level().getBlockState(this.blockPosition().below()).is(UPBlockTags.DIPLO_BURROWS)) {
                 return event.setAndContinue(DIPLOCAULUS_BURROW_HOLD);
             } else {
                 return PlayState.CONTINUE;
             }
         }
         return PlayState.CONTINUE;
     }

     public void determineVariant(int variantChange){
         if (variantChange <= 25) {
             this.setVariant(1);
         }
         else if (variantChange <= 50) {
             this.setVariant(2);
         }
         else if (variantChange <= 75) {
             this.setVariant(3);
         }
         else {
             this.setVariant(0);
         }
     }
 }
