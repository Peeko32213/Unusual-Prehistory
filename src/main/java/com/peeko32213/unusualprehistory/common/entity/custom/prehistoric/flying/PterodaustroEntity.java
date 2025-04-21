 package com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.flying;

 import com.google.common.collect.ImmutableList;
 import com.google.common.collect.ImmutableMap;
 import com.peeko32213.unusualprehistory.common.entity.animation.state.EntityAction;
 import com.peeko32213.unusualprehistory.common.entity.animation.state.RandomStateGoal;
 import com.peeko32213.unusualprehistory.common.entity.animation.state.StateHelper;
 import com.peeko32213.unusualprehistory.common.entity.animation.state.WeightedState;
 import com.peeko32213.unusualprehistory.common.entity.custom.ai.goal.UPBlockPos;
 import com.peeko32213.unusualprehistory.common.entity.custom.ai.goal.pterodaustro.PterodaustroFlightGoal;
 import com.peeko32213.unusualprehistory.common.entity.custom.base.PrehistoricEntity;
 import com.peeko32213.unusualprehistory.common.entity.util.navigator.*;
 import com.peeko32213.unusualprehistory.core.other.tags.UPBlockTags;
 import com.peeko32213.unusualprehistory.core.other.util.UPMath;
 import com.peeko32213.unusualprehistory.core.registry.UPSounds;
 import com.peeko32213.unusualprehistory.core.registry.entities.UPEntities;
 import net.minecraft.core.BlockPos;
 import net.minecraft.nbt.CompoundTag;
 import net.minecraft.network.syncher.EntityDataAccessor;
 import net.minecraft.network.syncher.EntityDataSerializers;
 import net.minecraft.network.syncher.SynchedEntityData;
 import net.minecraft.server.level.ServerLevel;
 import net.minecraft.sounds.SoundEvent;
 import net.minecraft.util.Mth;
 import net.minecraft.world.damagesource.DamageSource;
 import net.minecraft.world.damagesource.DamageTypes;
 import net.minecraft.world.entity.*;
 import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
 import net.minecraft.world.entity.ai.attributes.Attributes;
 import net.minecraft.world.entity.ai.control.BodyRotationControl;
 import net.minecraft.world.entity.ai.control.MoveControl;
 import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
 import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
 import net.minecraft.world.entity.ai.goal.*;
 import net.minecraft.world.entity.ai.navigation.PathNavigation;
 import net.minecraft.world.entity.monster.Monster;
 import net.minecraft.world.entity.player.Player;
 import net.minecraft.world.level.ClipContext;
 import net.minecraft.world.level.Level;
 import net.minecraft.world.level.block.Blocks;
 import net.minecraft.world.level.block.state.BlockState;
 import net.minecraft.world.level.pathfinder.BlockPathTypes;
 import net.minecraft.world.phys.AABB;
 import net.minecraft.world.phys.HitResult;
 import net.minecraft.world.phys.Vec3;
 import org.jetbrains.annotations.NotNull;
 import software.bernie.geckolib.core.animation.AnimatableManager;
 import software.bernie.geckolib.core.animation.AnimationController;
 import software.bernie.geckolib.core.animation.AnimationState;
 import software.bernie.geckolib.core.animation.RawAnimation;
 import software.bernie.geckolib.core.keyframe.event.SoundKeyframeEvent;
 import software.bernie.geckolib.core.object.PlayState;

 import javax.annotation.Nullable;
 import java.util.EnumSet;
 import java.util.List;
 import java.util.function.Predicate;

 public class PterodaustroEntity extends PrehistoricEntity {

     private static final EntityDataAccessor<Boolean> FLYING = SynchedEntityData.defineId(PterodaustroEntity.class, EntityDataSerializers.BOOLEAN);

     public float currentRoll = 0.0F;
     public float prevTilt;
     public float tilt;

     public float prevFlyProgress;
     public float flyProgress;
     private boolean isLandNavigator;
     public int timeFlying;
     public BlockPos orbitPos = null;
     public double orbitDist = 5D;
     public boolean orbitClockwise = false;
     public float prevSwoopProgress;
     public float swoopProgress;
     public float prevFlapAmount;
     public float flapAmount;
     public float flightPitch = 0;
     public float prevFlightPitch = 0;

     // Movement animations
     private static final RawAnimation PTERODAUSTRO_WALK = RawAnimation.begin().thenLoop("animation.pterodaustro.walk");
     private static final RawAnimation PTERODAUSTRO_RUN = RawAnimation.begin().thenLoop("animation.pterodaustro.run");
     private static final RawAnimation PTERODAUSTRO_SWIM = RawAnimation.begin().thenLoop("animation.pterodaustro.swim");
     private static final RawAnimation PTERODAUSTRO_FLY = RawAnimation.begin().thenLoop("animation.pterodaustro.fly");
     private static final RawAnimation PTERODAUSTRO_FLY_FAST = RawAnimation.begin().thenLoop("animation.pterodaustro.flyfast");

     // Idle animations
     private static final RawAnimation PTERODAUSTRO_IDLE = RawAnimation.begin().thenLoop("animation.pterodaustro.idle");
     private static final RawAnimation PTERODAUSTRO_HOVER = RawAnimation.begin().thenLoop("animation.pterodaustro.hover");
     private static final RawAnimation PTERODAUSTRO_DISPLAY = RawAnimation.begin().thenPlay("animation.pterodaustro.display");
     private static final RawAnimation PTERODAUSTRO_FEED = RawAnimation.begin().thenPlay("animation.pterodaustro.feed_blend");
     private static final RawAnimation PTERODAUSTRO_BROADCAST = RawAnimation.begin().thenPlay("animation.pterodaustro.broadcast_blend");

     // Idle accessors
     private static final EntityDataAccessor<Boolean> DISPLAY = SynchedEntityData.defineId(PterodaustroEntity.class, EntityDataSerializers.BOOLEAN);
     private static final EntityDataAccessor<Boolean> FEED = SynchedEntityData.defineId(PterodaustroEntity.class, EntityDataSerializers.BOOLEAN);
     private static final EntityDataAccessor<Boolean> BROADCAST = SynchedEntityData.defineId(PterodaustroEntity.class, EntityDataSerializers.BOOLEAN);

     // Starting predicates
     private static final Predicate<LivingEntity> PTERODAUSTRO_STARTING_PREDICATE = (e -> {
         if(e instanceof PterodaustroEntity entity) {
             return !entity.isFlying() && !entity.isSprinting() && !entity.isInWater() && entity.onGround();
         }
         return false;
     });

     private static final Predicate<LivingEntity> PTERODAUSTRO_EATING_PREDICATE = (e -> {
         if(e instanceof PterodaustroEntity entity) {
             return entity.isInWater() || entity.level().getBlockState(entity.blockPosition().below()).is(Blocks.WATER);
         }
         return false;
     });

     // Idle actions
     private static final EntityAction PTERODAUSTRO_DISPLAY_ACTION = new EntityAction(0, (e) -> {}, 1);
     private static final StateHelper PTERODAUSTRO_DISPLAY_STATE =
             StateHelper.Builder.state(DISPLAY, "pterodaustro_display")
                     .playTime(60)
                     .stopTime(200)
                     .startingPredicate(PTERODAUSTRO_STARTING_PREDICATE)
                     .affectedFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK))
                     .entityAction(PTERODAUSTRO_DISPLAY_ACTION)
                     .build();

     private static final EntityAction PTERODAUSTRO_FEED_ACTION = new EntityAction(0, (e) -> {}, 1);
     private static final StateHelper PTERODAUSTRO_FEED_STATE =
             StateHelper.Builder.state(FEED, "pterodaustro_feed")
                     .playTime(60)
                     .stopTime(150)
                     .startingPredicate(PTERODAUSTRO_EATING_PREDICATE)
                     .entityAction(PTERODAUSTRO_FEED_ACTION)
                     .build();

     private static final EntityAction PTERODAUSTRO_BROADCAST_ACTION = new EntityAction(0, (e) -> {}, 1);
     private static final StateHelper PTERODAUSTRO_BROADCAST_STATE =
             StateHelper.Builder.state(BROADCAST, "pterodaustro_broadcast")
                     .playTime(60)
                     .stopTime(190)
                     .startingPredicate(PTERODAUSTRO_STARTING_PREDICATE)
                     .entityAction(PTERODAUSTRO_BROADCAST_ACTION)
                     .build();

     @Override
     public ImmutableMap<String, StateHelper> getStates() {
         return ImmutableMap.of(
                 PTERODAUSTRO_DISPLAY_STATE.getName(), PTERODAUSTRO_DISPLAY_STATE,
                 PTERODAUSTRO_FEED_STATE.getName(), PTERODAUSTRO_FEED_STATE,
                 PTERODAUSTRO_BROADCAST_STATE.getName(), PTERODAUSTRO_BROADCAST_STATE
         );
     }

     @Override
     public List<WeightedState<StateHelper>> getWeightedStatesToPerform() {
         return ImmutableList.of(
                 WeightedState.of(PTERODAUSTRO_DISPLAY_STATE, 7),
                 WeightedState.of(PTERODAUSTRO_FEED_STATE, 9),
                 WeightedState.of(PTERODAUSTRO_BROADCAST_STATE, 8)
         );
     }

     // Body control / navigation
     @Override
     protected @NotNull BodyRotationControl createBodyControl() {
         SmartBodyHelper helper = new SmartBodyHelper(this);
         if (this.isFlying()) {
             helper.bodyLagMoving = 0.015F;
             helper.bodyLagStill = 0.075F;
         }
         else {
             helper.bodyLagMoving = 0.4F;
             helper.bodyLagStill = 0.3F;
         }
         return helper;
     }

     @Override
     protected @NotNull PathNavigation createNavigation(Level levelIn) {
         return new SmoothGroundNavigation(this, levelIn);
     }

     public PterodaustroEntity(EntityType<? extends PrehistoricEntity> entityType, Level level) {
         super(entityType, level);
         switchNavigator(true);
         this.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
         this.setPathfindingMalus(BlockPathTypes.WATER_BORDER, 0.0F);
     }

     public static AttributeSupplier.Builder createAttributes() {
         return Monster.createMonsterAttributes()
                 .add(Attributes.MAX_HEALTH, 12.0D)
                 .add(Attributes.MOVEMENT_SPEED, 0.2F)
                 .add(Attributes.ATTACK_DAMAGE, 2.0F);
     }

     protected void registerGoals() {
         this.goalSelector.addGoal(0, new RandomStateGoal<>(this));
         this.goalSelector.addGoal(0, new FloatGoal(this) {
             public boolean canUse() {
                 return super.canUse() && (PterodaustroEntity.this.getAirSupply() < 150);
             }
         });
         this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
         this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
         this.goalSelector.addGoal(6, new PterodaustroFlightGoal(this));
     }

     @Nullable
     @Override
     public AgeableMob getBreedOffspring(@NotNull ServerLevel serverLevel, @NotNull AgeableMob ageableMob) {
         return UPEntities.PTERODAUSTRO.get().create(serverLevel);
     }

     private void switchNavigator(boolean onLand) {
         if (onLand) {
             this.moveControl = new MoveControl(this);
             this.navigation = new SmoothGroundNavigation(this, level());
             this.isLandNavigator = true;
         } else {
             this.moveControl = new MoveHelper(this);
             this.navigation = new DirectPathNavigator(this, level());
             this.isLandNavigator = false;
         }
     }

//     @Override
//     public double getFluidJumpThreshold() {
//         if (this.isBaby()) {
//             return 0.15F;
//         } else {
//             return 0.25F;
//         }
//     }

     @Override
     protected void defineSynchedData() {
         super.defineSynchedData();
         this.entityData.define(FLYING, false);
         this.entityData.define(DISPLAY, false);
         this.entityData.define(FEED, false);
         this.entityData.define(BROADCAST, false);
     }

     public void addAdditionalSaveData(CompoundTag compound) {
         super.addAdditionalSaveData(compound);
         compound.putBoolean("Flying", this.isFlying());
     }

     public void readAdditionalSaveData(CompoundTag compound) {
         super.readAdditionalSaveData(compound);
         this.setFlying(compound.getBoolean("Flying"));
     }

     public void tick() {
         super.tick();

         this.prevFlyProgress = flyProgress;
         this.prevFlapAmount = flapAmount;
         this.prevSwoopProgress = swoopProgress;
         this.prevFlightPitch = flightPitch;
         float yMot = (float) -((float) this.getDeltaMovement().y * Mth.RAD_TO_DEG);
         this.flightPitch = yMot;

         if (yMot < 0.1F) {
             flapAmount = Math.min(-yMot * 0.2F, 1F);
             if (swoopProgress > 0) {
                 swoopProgress--;
             }
         } else {
             if (flapAmount > 0.0F) {
                 flapAmount -= Math.min(flapAmount, 0.1F);
             } else {
                 flapAmount = 0;
             }
             if (swoopProgress < yMot * 0.2F) {
                 swoopProgress = Math.min(yMot * 0.2F, swoopProgress + 1);
             }
         }

         if (isFlying()) {
             if (flyProgress < 5F)
                 flyProgress++;
         } else {
             if (flyProgress > 0F)
                 flyProgress--;
         }
         if (!this.level().isClientSide) {
             final boolean isFlying = isFlying();
             if (isFlying && this.isLandNavigator) {
                 switchNavigator(false);
             }
             if (!isFlying && !this.isLandNavigator) {
                 switchNavigator(true);
             }
             if (isFlying) {
                 timeFlying++;
                 this.setNoGravity(true);
                 if (this.onGround()) {
                     this.setFlying(false);
                 }
             }
             else {
                 timeFlying = 0;
                 this.setNoGravity(false);
             }
         }

         prevTilt = tilt;
         if (this.isFlying() && !this.onGround()) {
             final float v = Mth.degreesDifference(this.getYRot(), yRotO);
             if (Math.abs(v) > 1) {
                 if (Math.abs(tilt) < 25) {
                     tilt -= Math.signum(v);
                 }
             } else {
                 if (Math.abs(tilt) > 0) {
                     final float tiltSign = Math.signum(tilt);
                     tilt -= tiltSign * 0.85F;
                     if (tilt * tiltSign < 0) {
                         tilt = 0;
                     }
                 }
             }
         } else {
             tilt = 0;
         }

         float prevRoll = this.currentRoll;
         float targetRoll = Math.max(-0.45F, Math.min(0.45F, (this.getYRot() - this.yRotO) * 0.1F));
         targetRoll = -targetRoll;
         this.currentRoll = prevRoll + (targetRoll - prevRoll) * 0.05F;
     }

     @Override
     public boolean isInvulnerableTo(DamageSource source) {
         return source.is(DamageTypes.IN_WALL) || source.is(DamageTypes.FALL) || source.is(DamageTypes.CACTUS) || super.isInvulnerableTo(source);
     }

     public boolean isFlying() {
         return this.entityData.get(FLYING);
     }
     public void setFlying(boolean flying) {
         this.entityData.set(FLYING, flying);
     }

     // Sounds
     protected SoundEvent getAmbientSound() {
         return UPSounds.ANURO_IDLE.get();
     }

     protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
         return UPSounds.ANURO_HURT.get();
     }

     protected SoundEvent getDeathSound() {
         return UPSounds.ANURO_DEATH.get();
     }

     public boolean causeFallDamage(float distance, float damageMultiplier) {
         return false;
     }

     protected void checkFallDamage(double y, boolean onGroundIn, BlockState state, BlockPos pos) {}

     public boolean isTargetBlocked(Vec3 target) {
         Vec3 Vector3d = new Vec3(this.getX(), this.getEyeY(), this.getZ());
         return this.level().clip(new ClipContext(Vector3d, target, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this)).getType() != HitResult.Type.MISS;
     }

     @Override
     public BlockPos getBlockPosBelowThatAffectsMyMovement() {
         return this.getOnPos(0.500001F);
     }

     public Vec3 getBlockInViewAway(Vec3 fleePos, float radiusAdd) {
         final float radius = 0.75F * (0.7F * 6) * -3 - this.getRandom().nextInt(24) - radiusAdd;
         final float neg = this.getRandom().nextBoolean() ? 1 : -1;
         final float renderYawOffset = this.yBodyRot;
         final float angle = (UPMath.STARTING_ANGLE * renderYawOffset) + 3.15F + (this.getRandom().nextFloat() * neg);
         final double extraX = radius * Mth.sin(Mth.PI + angle);
         final double extraZ = radius * Mth.cos(angle);
         final BlockPos radialPos = new BlockPos((int) (fleePos.x() + extraX), 0, (int) (fleePos.z() + extraZ));
         final BlockPos ground = getGround(radialPos);
         final int distFromGround = (int) this.getY() - ground.getY();
         final int flightHeight = 7 + this.getRandom().nextInt(10);
         final BlockPos newPos = ground.above(distFromGround > 8 ? flightHeight : this.getRandom().nextInt(7) + 4);
         if (!this.isTargetBlocked(Vec3.atCenterOf(newPos)) && this.distanceToSqr(Vec3.atCenterOf(newPos)) > 1) {
             return Vec3.atCenterOf(newPos);
         }
         return null;
     }

     private BlockPos getGround(BlockPos in) {
         BlockPos position = new BlockPos(in.getX(), (int) this.getY(), in.getZ());
         while (position.getY() < 320 && !level().getFluidState(position).isEmpty()) {
             position = position.above();
         }
         while (position.getY() > -64 && !level().getBlockState(position).isSolid()) {
             position = position.below();
         }
         return position;
     }

     public Vec3 getBlockGrounding(Vec3 fleePos) {
         final float radius = 0.75F * (0.7F * 6) * -3 - this.getRandom().nextInt(24);
         final float neg = this.getRandom().nextBoolean() ? 1 : -1;
         final float renderYawOffset = this.yBodyRot;
         final float angle = (UPMath.STARTING_ANGLE * renderYawOffset) + 3.15F + (this.getRandom().nextFloat() * neg);
         final double extraX = radius * Mth.sin(Mth.PI + angle);
         final double extraZ = radius * Mth.cos(angle);
         final BlockPos radialPos = UPBlockPos.fromCoords(fleePos.x() + extraX, getY(), fleePos.z() + extraZ);
         BlockPos ground = this.getGround(radialPos);
         if (ground.getY() == -64) {
             return this.position();
         } else {
             ground = this.blockPosition();
             while (ground.getY() > -64 && !level().getBlockState(ground).isSolid()) {
                 ground = ground.below();
             }
         }
         if (!this.isTargetBlocked(Vec3.atCenterOf(ground.above()))) {
             return Vec3.atCenterOf(ground);
         }
         return null;
     }

     public Vec3 getOrbitVec(Vec3 vector3d, float gatheringCircleDist) {
         final float angle = (UPMath.STARTING_ANGLE * (float) this.orbitDist * (orbitClockwise ? -tickCount : tickCount));
         final double extraX = gatheringCircleDist * Mth.sin((angle));
         final double extraZ = gatheringCircleDist * Mth.cos(angle);
         if (this.orbitPos != null) {
             final Vec3 pos = new Vec3(orbitPos.getX() + extraX, orbitPos.getY() + random.nextInt(2) - 2, orbitPos.getZ() + extraZ);
             if (this.level().isEmptyBlock(UPBlockPos.fromVec3(pos))) {
                 return pos;
             }
         }
         return null;
     }

     public boolean isOverWaterOrVoid() {
         BlockPos position = this.blockPosition();
         while (position.getY() > -64 && level().isEmptyBlock(position)) {
             position = position.below();
         }
         return !level().getFluidState(position).isEmpty() || position.getY() <= -64;
     }

     static class MoveHelper extends MoveControl {
         private final PterodaustroEntity pterodaustro;

         public MoveHelper(PterodaustroEntity pterodaustro) {
             super(pterodaustro);
             this.pterodaustro = pterodaustro;
         }

         public void tick() {
             if (this.operation == MoveControl.Operation.MOVE_TO) {
                 final Vec3 vector3d = new Vec3(this.wantedX - pterodaustro.getX(), this.wantedY - pterodaustro.getY(), this.wantedZ - pterodaustro.getZ());
                 final double d5 = vector3d.length();
                 if (d5 < 0.3) {
                     this.operation = MoveControl.Operation.WAIT;
                     pterodaustro.setDeltaMovement(pterodaustro.getDeltaMovement().scale(0.5D));
                 } else {
                     pterodaustro.setDeltaMovement(pterodaustro.getDeltaMovement().add(vector3d.scale(this.speedModifier * 0.05D / d5)));
                     final Vec3 vector3d1 = pterodaustro.getDeltaMovement();
                     pterodaustro.setYRot(-((float) Mth.atan2(vector3d1.x, vector3d1.z)) * Mth.RAD_TO_DEG);
                     pterodaustro.yBodyRot = pterodaustro.getYRot();
                 }
             }
         }

         private boolean canReach(Vec3 p_220673_1_, int p_220673_2_) {
             AABB axisalignedbb = this.pterodaustro.getBoundingBox();

             for (int i = 1; i < p_220673_2_; ++i) {
                 axisalignedbb = axisalignedbb.move(p_220673_1_);
                 if (!this.pterodaustro.level().noCollision(this.pterodaustro, axisalignedbb)) {
                     return false;
                 }
             }
             return true;
         }
     }

     // Animation sounds
     private void soundListener(SoundKeyframeEvent<PterodaustroEntity> event) {
         PterodaustroEntity pterodaustro = event.getAnimatable();
         if (pterodaustro.level().isClientSide) {
//             if (event.getKeyframeData().getSound().equals("pterodaustro_flap")) {
//                 pterodaustro.level().playLocalSound(pterodaustro.getX(), pterodaustro.getY(), pterodaustro.getZ(), UPSounds.TELECREX_FLAP.get(), pterodaustro.getSoundSource(), 0.1F, pterodaustro.getVoicePitch(), false);
//             }
         }
     }

     // Animation control
     @Override
     public void registerControllers(final AnimatableManager.ControllerRegistrar controllers) {
         AnimationController<PterodaustroEntity> controller = new AnimationController<>(this, "controller", 5, this::predicate);
         controllers.add(controller);
         controller.setSoundKeyframeHandler(this::soundListener);

         AnimationController<PterodaustroEntity> idle = new AnimationController<>(this, "idleController", 5, this::idlePredicate);
         controllers.add(idle);
     }

     protected <E extends PterodaustroEntity> PlayState predicate(final AnimationState<E> event) {
         if (!this.isInWater()) {
             if (this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6 && this.onGround()) {
                 if (this.isSprinting()) {
                     event.setAndContinue(PTERODAUSTRO_RUN);
                 }
                 else event.setAndContinue(PTERODAUSTRO_WALK);
                 return PlayState.CONTINUE;
             }
             else if (!this.isFlying()) {
                 return event.setAndContinue(PTERODAUSTRO_IDLE);
             }

             if (this.isFlying() && this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6) {
                 if (this.isSprinting() || this.getMoveControl().hasWanted()) {
                     event.setAndContinue(PTERODAUSTRO_FLY_FAST);
                 }
                 else event.setAndContinue(PTERODAUSTRO_FLY);
                 return PlayState.CONTINUE;
             }
             else if (this.isFlying() && this.isStillEnough()) {
                 event.setAndContinue(PTERODAUSTRO_HOVER);
                 return PlayState.CONTINUE;
             }
         }
         if (this.isInWater()) {
             event.setAndContinue(PTERODAUSTRO_SWIM);
         }
         return PlayState.CONTINUE;
     }

     protected <E extends PterodaustroEntity> PlayState idlePredicate(final AnimationState<E> event) {
         if(this.onGround() && !this.isFlying() && !getMoveControl().hasWanted()) {
             if (getBooleanState(DISPLAY)) {
                 event.getController().setAnimation(PTERODAUSTRO_DISPLAY);
                 return PlayState.CONTINUE;
             }
             if (getBooleanState(FEED)) {
                 event.getController().setAnimation(PTERODAUSTRO_FEED);
                 return PlayState.CONTINUE;
             }
             if (getBooleanState(BROADCAST)) {
                 event.getController().setAnimation(PTERODAUSTRO_BROADCAST);
                 return PlayState.CONTINUE;
             }
             event.getController().forceAnimationReset();
         }
         return PlayState.STOP;
     }
 }
