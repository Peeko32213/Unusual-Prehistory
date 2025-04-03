 package com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.semi_aquatic;

 import com.google.common.collect.ImmutableMap;
 import com.peeko32213.unusualprehistory.common.entity.animation.state.StateHelper;
 import com.peeko32213.unusualprehistory.common.entity.animation.state.WeightedState;
 import com.peeko32213.unusualprehistory.common.entity.custom.ai.goal.FindWaterGoal;
 import com.peeko32213.unusualprehistory.common.entity.custom.ai.goal.LeaveWaterGoal;
 import com.peeko32213.unusualprehistory.common.entity.custom.ai.goal.SemiAquaticSwimmingGoal;
 import com.peeko32213.unusualprehistory.common.entity.custom.base.PrehistoricEntity;
 import com.peeko32213.unusualprehistory.common.entity.util.interfaces.ISemiAquatic;
 import com.peeko32213.unusualprehistory.common.entity.util.navigator.SemiAquaticPathNavigation;
 import com.peeko32213.unusualprehistory.common.entity.util.navigator.SmartBodyHelper;
 import com.peeko32213.unusualprehistory.common.entity.util.navigator.SmoothGroundNavigation;
 import com.peeko32213.unusualprehistory.common.entity.util.navigator.WaterMoveController;
 import com.peeko32213.unusualprehistory.core.registry.entities.UPEntities;
 import net.minecraft.core.BlockPos;
 import net.minecraft.nbt.CompoundTag;
 import net.minecraft.server.level.ServerLevel;
 import net.minecraft.sounds.SoundEvent;
 import net.minecraft.sounds.SoundEvents;
 import net.minecraft.world.damagesource.DamageSource;
 import net.minecraft.world.entity.*;
 import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
 import net.minecraft.world.entity.ai.attributes.Attributes;
 import net.minecraft.world.entity.ai.control.BodyRotationControl;
 import net.minecraft.world.entity.ai.control.MoveControl;
 import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
 import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
 import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
 import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
 import net.minecraft.world.entity.ai.navigation.PathNavigation;
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

 import java.util.List;

 public class PterygotusEntity extends PrehistoricEntity implements ISemiAquatic {

     private static final RawAnimation PTERY_IDLE = RawAnimation.begin().thenLoop("animation.pterygotus.idle");
     private static final RawAnimation PTERY_WALK = RawAnimation.begin().thenLoop("animation.pterygotus.walk");
     private static final RawAnimation PTERY_SWIM_IDLE = RawAnimation.begin().thenLoop("animation.pterygotus.float");
     private static final RawAnimation PTERY_SWIM = RawAnimation.begin().thenLoop("animation.pterygotus.swim");

     public float prevSwimProgress;
     public float swimProgress;
     private int swimTimer = -1000;
     private boolean isLandNavigator;

     // Body control / navigation
     @Override
     protected @NotNull BodyRotationControl createBodyControl() {
         SmartBodyHelper helper = new SmartBodyHelper(this);
         helper.bodyLagMoving = 0.25F;
         helper.bodyLagStill = 0.15F;
         return helper;
     }

     @Override
     protected @NotNull PathNavigation createNavigation(Level levelIn) {
         return new SmoothGroundNavigation(this, levelIn);
     }

     public PterygotusEntity(EntityType<? extends PrehistoricEntity> entityType, Level level) {
         super(entityType, level);
         this.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
         this.setPathfindingMalus(BlockPathTypes.WATER_BORDER, 0.0F);
         switchNavigator(false);
     }

     public static AttributeSupplier.Builder createAttributes() {
         return Mob.createMobAttributes()
             .add(Attributes.MAX_HEALTH, 60.0D)
             .add(Attributes.ATTACK_DAMAGE, 8.0D)
             .add(Attributes.ARMOR, 10.0D)
             .add(Attributes.MOVEMENT_SPEED, 0.16D)
             .add(Attributes.KNOCKBACK_RESISTANCE, 0.5D);
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
             this.moveControl = new WaterMoveController(this, 1.0F);
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

     public void travel(@NotNull Vec3 travelVector) {
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
         super.defineSynchedData();
     }

     public void addAdditionalSaveData(CompoundTag compound) {
         super.addAdditionalSaveData(compound);
         compound.putInt("SwimTimer", this.swimTimer);
     }

     public void readAdditionalSaveData(CompoundTag compound) {
         super.readAdditionalSaveData(compound);
         this.swimTimer = compound.getInt("SwimTimer");
     }

     public boolean canBreatheUnderwater() {
         return true;
     }

     protected SoundEvent getAmbientSound() {
         return SoundEvents.SPIDER_AMBIENT;
     }

     protected SoundEvent getHurtSound(@NotNull DamageSource damageSourceIn) {
         return SoundEvents.SPIDER_HURT;
     }

     protected SoundEvent getDeathSound() {
         return SoundEvents.SPIDER_DEATH;
     }

     protected void playStepSound(@NotNull BlockPos p_28301_, @NotNull BlockState p_28302_) {
         this.playSound(SoundEvents.SPIDER_STEP, 0.1F, 0.8F);
     }

     @Override
     public float getSoundVolume() {
         return 0.8F;
     }

     @Nullable
     @Override
     public AgeableMob getBreedOffspring(@NotNull ServerLevel serverLevel, @NotNull AgeableMob ageableMob) {
         return UPEntities.PTERYGOTUS.get().create(serverLevel);
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

     protected <E extends PterygotusEntity> PlayState Controller(final software.bernie.geckolib.core.animation.AnimationState<E> event) {

         if (this.isFromBook()) {
             return event.setAndContinue(PTERY_SWIM_IDLE);
         }

         if (event.isMoving() && !this.isInWater()) {
             event.setAndContinue(PTERY_WALK);
             event.getController().setAnimationSpeed(1.0D);
             return PlayState.CONTINUE;
         }

         if (this.isInWater()) {
             event.setAndContinue(PTERY_SWIM);
             event.getController().setAnimationSpeed(1.0F);
             return PlayState.CONTINUE;
         }

         if (isStillEnough() && this.isInWater()) {
             event.setAndContinue(PTERY_SWIM_IDLE);
             event.getController().setAnimationSpeed(1.0F);
             return PlayState.CONTINUE;
         }


         if(playingAnimation())
         {
             return PlayState.CONTINUE;
         }

         if (isStillEnough() && !this.isSwimming()) {
             event.setAndContinue(PTERY_IDLE);
             event.getController().setAnimationSpeed(1.0F);
             return PlayState.CONTINUE;
         }

         return PlayState.CONTINUE;
     }

     @Override
     public void registerControllers(final AnimatableManager.ControllerRegistrar controllers) {
         controllers.add(new AnimationController<>(this, "Normal", 5, this::Controller));
     }

     @Override
     public ImmutableMap<String, StateHelper> getStates() {
         return null;
     }

     @Override
     public List<WeightedState<StateHelper>> getWeightedStatesToPerform() {
         return List.of();
     }
 }
