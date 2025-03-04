 package com.peeko32213.unusualprehistory.common.entity;

 import com.peeko32213.unusualprehistory.common.entity.base.PrehistoricEntity;
 import com.peeko32213.unusualprehistory.common.entity.util.interfaces.IBookEntity;
 import com.peeko32213.unusualprehistory.common.entity.util.navigator.FlyingMoveController;
 import com.peeko32213.unusualprehistory.core.registry.UPSounds;
 import net.minecraft.core.BlockPos;
 import net.minecraft.nbt.CompoundTag;
 import net.minecraft.network.syncher.EntityDataAccessor;
 import net.minecraft.network.syncher.EntityDataSerializers;
 import net.minecraft.network.syncher.SynchedEntityData;
 import net.minecraft.server.level.ServerLevel;
 import net.minecraft.sounds.SoundEvent;
 import net.minecraft.tags.TagKey;
 import net.minecraft.util.Mth;
 import net.minecraft.world.Difficulty;
 import net.minecraft.world.DifficultyInstance;
 import net.minecraft.world.damagesource.DamageSource;
 import net.minecraft.world.damagesource.DamageTypes;
 import net.minecraft.world.entity.*;
 import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
 import net.minecraft.world.entity.ai.attributes.Attributes;
 import net.minecraft.world.entity.ai.control.MoveControl;
 import net.minecraft.world.entity.ai.goal.*;
 import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
 import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
 import net.minecraft.world.entity.monster.Monster;
 import net.minecraft.world.level.ClipContext;
 import net.minecraft.world.level.Level;
 import net.minecraft.world.level.ServerLevelAccessor;
 import net.minecraft.world.level.block.Blocks;
 import net.minecraft.world.level.block.state.BlockState;
 import net.minecraft.world.phys.HitResult;
 import net.minecraft.world.phys.Vec3;
 import software.bernie.geckolib.animatable.GeoEntity;
 import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
 import software.bernie.geckolib.core.animation.AnimatableManager;
 import software.bernie.geckolib.core.animation.AnimationController;
 import software.bernie.geckolib.core.animation.RawAnimation;
 import software.bernie.geckolib.core.object.PlayState;
 import software.bernie.geckolib.util.GeckoLibUtil;

 import javax.annotation.Nullable;
 import java.util.EnumSet;

 public class PterodaustroEntity extends PrehistoricEntity implements GeoEntity, IBookEntity {
     private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
     @Nullable
     private static final EntityDataAccessor<Boolean> FLYING = SynchedEntityData.defineId(PterodaustroEntity.class, EntityDataSerializers.BOOLEAN);
     private static final EntityDataAccessor<Boolean> FROM_BOOK = SynchedEntityData.defineId(PterodaustroEntity.class, EntityDataSerializers.BOOLEAN);
     public final float[] ringBuffer = new float[64];
     public float prevFlyProgress;
     public float flyProgress;
     public int ringBufferIndex = -1;
     private boolean isLandNavigator;
     private int timeFlying;

     private static final RawAnimation PTERODAUSTRO_IDLE = RawAnimation.begin().thenPlay("animation.pterodaustro.idle");
     private static final RawAnimation PTERODAUSTRO_SIT = RawAnimation.begin().thenPlay("animation.pterodaustro.sit");
     private static final RawAnimation PTERODAUSTRO_SLEEP = RawAnimation.begin().thenPlay("animation.pterodaustro.sleep");
     private static final RawAnimation PTERODAUSTRO_WALK = RawAnimation.begin().thenPlay("animation.pterodaustro.walk");
     private static final RawAnimation PTERODAUSTRO_SWIM = RawAnimation.begin().thenPlay("animation.pterodaustro.swim");
     private static final RawAnimation PTERODAUSTRO_FLY = RawAnimation.begin().thenPlay("animation.pterodaustro.fly");
     private static final RawAnimation PTERODAUSTRO_HOVER = RawAnimation.begin().thenPlay("animation.pterodaustro.hover");
     private static final RawAnimation PTERODAUSTRO_FEED = RawAnimation.begin().thenPlay("animation.pterodaustro.feed");
     private static final RawAnimation PTERODAUSTRO_DISPLAY = RawAnimation.begin().thenPlay("animation.pterodaustro.display");
     private static final RawAnimation PTERODAUSTRO_FLAP = RawAnimation.begin().thenPlay("animation.pterodaustro.flap");
     private static final RawAnimation PTERODAUSTRO_NESTING = RawAnimation.begin().thenPlay("animation.pterodaustro.nesting");


     public PterodaustroEntity(EntityType<? extends PrehistoricEntity> entityType, Level level) {
         super(entityType, level);
         switchNavigator(true);
     }

     @org.jetbrains.annotations.Nullable
     @Override
     public AgeableMob getBreedOffspring(ServerLevel p_146743_, AgeableMob p_146744_) {
         return null;
     }

     public static AttributeSupplier.Builder createAttributes() {
         return Monster.createMonsterAttributes()
                 .add(Attributes.MAX_HEALTH, 12.0D)
                 .add(Attributes.MOVEMENT_SPEED, 0.2F)
                 .add(Attributes.ATTACK_DAMAGE, 2.0F);

     }

     protected void registerGoals() {
         super.registerGoals();
         this.goalSelector.addGoal(0, new FloatGoal(this));
         this.goalSelector.addGoal(3, new PanicGoal(this, 1D));
         this.goalSelector.addGoal(1, new AIFlyIdle());
         this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, MajungasaurusEntity.class, 8.0F, 1.6D, 1.4D, EntitySelector.NO_SPECTATORS::test));
         this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, TyrannosaurusEntity.class, 8.0F, 1.6D, 1.4D, EntitySelector.NO_SPECTATORS::test));
     }

     public void checkDespawn() {
         if (this.level().getDifficulty() == Difficulty.PEACEFUL && this.shouldDespawnInPeaceful()) {
             this.discard();
         } else {
             this.noActionTime = 0;
         }
     }

     @Override
     protected SoundEvent getAttackSound() {
         return null;
     }

     @Override
     protected int getKillHealAmount() {
         return 0;
     }

     @Override
     protected boolean canGetHungry() {
         return false;
     }

     @Override
     protected boolean hasTargets() {
         return false;
     }

     @Override
     protected boolean hasAvoidEntity() {
         return false;
     }

     @Override
     protected boolean hasCustomNavigation() {
         return false;
     }

     @Override
     protected boolean hasMakeStuckInBlock() {
         return false;
     }

     @Override
     protected boolean customMakeStuckInBlockCheck(BlockState blockState) {
         return false;
     }

     @Override
     protected TagKey<EntityType<?>> getTargetTag() {
         return null;
     }


     private void switchNavigator(boolean onLand) {
         if (onLand) {
             this.moveControl = new MoveControl(this);
             this.navigation = new GroundPathNavigation(this, level());
             this.isLandNavigator = true;
         } else {
             this.moveControl = new FlyingMoveController(this, 0.6F, false, true);
             this.navigation = new FlyingPathNavigation(this, level()) {
                 public boolean isStableDestination(BlockPos pos) {
                     return !this.level.getBlockState(pos.below(2)).isAir();
                 }
             };
             navigation.setCanFloat(false);
             this.isLandNavigator = false;
         }
     }

     @Override
     protected void defineSynchedData() {
         super.defineSynchedData();
         this.entityData.define(FLYING, false);
         this.entityData.define(FROM_BOOK, false);

     }


     public void tick() {
         super.tick();
         this.prevFlyProgress = flyProgress;
         if (this.isFlying() && flyProgress < 5F) {
             flyProgress++;
         }
         if (!this.isFlying() && flyProgress > 0F) {
             flyProgress--;
         }
         if (this.ringBufferIndex < 0) {
             //initial population of buffer
             for (int i = 0; i < this.ringBuffer.length; ++i) {
                 this.ringBuffer[i] = 15;
             }
         }
         this.ringBufferIndex++;
         if (this.ringBufferIndex == this.ringBuffer.length) {
             this.ringBufferIndex = 0;
         }
         if (!level().isClientSide) {
             if (isFlying() && this.isLandNavigator) {
                 switchNavigator(false);
             }
             if (!isFlying() && !this.isLandNavigator) {
                 switchNavigator(true);
             }
             if (this.isFlying()) {
                 if (this.isFlying() && !this.onGround()) {
                     if (!this.isInWaterOrBubble()) {
                         this.setDeltaMovement(this.getDeltaMovement().multiply(1F, 0.6F, 1F));
                     }
                 }
                 if (this.onGround() && timeFlying > 20) {
                     this.setFlying(false);
                 }
                 this.timeFlying++;
             } else {
                 this.timeFlying = 0;
             }
         }
     }

     public boolean hurt(DamageSource source, float amount) {
         boolean prev = super.hurt(source, amount);
         return prev;
     }

     @Override
     public boolean isInvulnerableTo(DamageSource source) {
         return source.is(DamageTypes.IN_WALL) || source.is(DamageTypes.FALL) || source.is(DamageTypes.CACTUS) || super.isInvulnerableTo(source);
     }

     public boolean isFlying() {
         return this.entityData.get(FLYING);
     }

     public void setFlying(boolean flying) {
         if (flying && isBaby()) {
             return;
         }
         this.entityData.set(FLYING, flying);
     }

     public boolean canBlockBeSeen(BlockPos pos) {
         double x = pos.getX() + 0.5F;
         double y = pos.getY() + 0.5F;
         double z = pos.getZ() + 0.5F;
         HitResult result = this.level().clip(new ClipContext(new Vec3(this.getX(), this.getY() + (double) this.getEyeHeight(), this.getZ()), new Vec3(x, y, z), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this));
         double dist = result.getLocation().distanceToSqr(x, y, z);
         return dist <= 1.0D || result.getType() == HitResult.Type.MISS;
     }

     public void addAdditionalSaveData(CompoundTag compound) {
         super.addAdditionalSaveData(compound);
         compound.putBoolean("Flying", this.isFlying());
     }

     public void readAdditionalSaveData(CompoundTag compound) {
         super.readAdditionalSaveData(compound);
         this.setFlying(compound.getBoolean("Flying"));
     }

     protected SoundEvent getAmbientSound() {
         return UPSounds.ANURO_IDLE.get();
     }

     protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
         return UPSounds.ANURO_HURT.get();
     }

     protected SoundEvent getDeathSound() {
         return UPSounds.ANURO_DEATH.get();
     }

     public void killed(ServerLevel world, LivingEntity entity) {
         this.heal(10);
     }


     public Vec3 getBlockGrounding(Vec3 fleePos) {
         final float radius = 3.15F * -3 - this.getRandom().nextInt(24);
         float neg = this.getRandom().nextBoolean() ? 1 : -1;
         float renderYawOffset = this.yBodyRot;
         float angle = (0.01745329251F * renderYawOffset) + 3.15F + (this.getRandom().nextFloat() * neg);
         final double extraX = radius * Mth.sin(Mth.PI + angle);
         final double extraZ = radius * Mth.cos(angle);
         final BlockPos radialPos = new BlockPos((int) (fleePos.x() + extraX), (int) getY(), (int) (fleePos.z() + extraZ));
         BlockPos ground = this.getAnuroGround(radialPos);
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

     public boolean isTargetBlocked(Vec3 target) {
         Vec3 Vector3d = new Vec3(this.getX(), this.getEyeY(), this.getZ());

         return this.level().clip(new ClipContext(Vector3d, target, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this)).getType() != HitResult.Type.MISS;
     }

     public Vec3 getBlockInViewAway(Vec3 fleePos, float radiusAdd) {
         float radius = 5 + radiusAdd + this.getRandom().nextInt(5);
         float neg = this.getRandom().nextBoolean() ? 1 : -1;
         float renderYawOffset = this.yBodyRot;
         float angle = (0.01745329251F * renderYawOffset) + 3.15F + (this.getRandom().nextFloat() * neg);
         double extraX = radius * Mth.sin((float) (Math.PI + angle));
         double extraZ = radius * Mth.cos(angle);
         final BlockPos radialPos = new BlockPos((int) (fleePos.x() + extraX), (int) getY(), (int) (fleePos.z() + extraZ));
         BlockPos ground = getAnuroGround(radialPos);
         int distFromGround = (int) this.getY() - ground.getY();
         int flightHeight = 5 + this.getRandom().nextInt(5);
         int j = this.getRandom().nextInt(5) + 5;

         BlockPos newPos = ground.above(distFromGround > 5 ? flightHeight : j);
         if (!this.isTargetBlocked(Vec3.atCenterOf(newPos)) && this.distanceToSqr(Vec3.atCenterOf(newPos)) > 1) {
             return Vec3.atCenterOf(newPos);
         }
         return null;
     }

     public boolean causeFallDamage(float distance, float damageMultiplier) {
         return false;
     }

     protected void checkFallDamage(double y, boolean onGroundIn, BlockState state, BlockPos pos) {
     }

     private boolean isOverWaterOrVoid() {
         BlockPos position = this.blockPosition();
         while (position.getY() > -65 && level().isEmptyBlock(position)) {
             position = position.below();
         }
         return !level().getFluidState(position).isEmpty() || level().getBlockState(position).is(Blocks.VINE) || position.getY() <= -65;
     }

     public BlockPos getAnuroGround(BlockPos in) {
         BlockPos position = new BlockPos(in.getX(), (int) this.getY(), in.getZ());
         while (position.getY() > -64 && !level().getBlockState(position).isSolid() && level().getFluidState(position).isEmpty()) {
             position = position.below();
         }
         return position;
     }


     @Override
     public void registerControllers(final AnimatableManager.ControllerRegistrar controllers) {
         controllers.add(new AnimationController<>(this, "Normal", 5, this::Controller));
     }

     protected <E extends PterodaustroEntity> PlayState Controller(final software.bernie.geckolib.core.animation.AnimationState<E> event) {
         if (this.isFromBook()) {
             return event.setAndContinue(PTERODAUSTRO_IDLE);
         }
         if (this.isFlying()) {
             return event.setAndContinue(PTERODAUSTRO_FLY);
         }

         if (event.isMoving() && this.onGround() && this.onGround()) {
             return event.setAndContinue(PTERODAUSTRO_WALK);

         }

         if (this.isInWaterOrBubble()) {
             return event.setAndContinue(PTERODAUSTRO_SWIM);
         }

         if (playingAnimation()) {
             return PlayState.CONTINUE;
         }

         if (!event.isMoving() && this.onGround() && this.onGround() && getRandomAnimationNumber() == 0) {
             int rand = getRandomAnimationNumber();
             if (rand < 15) {
                 setAnimationTimer(100);
                 return event.setAndContinue(PTERODAUSTRO_FLAP);
             }
             if (rand < 66) {
                 setAnimationTimer(100);
                 return event.setAndContinue(PTERODAUSTRO_SIT);
             }
             if (rand < 77) {
                 setAnimationTimer(100);
                 return event.setAndContinue(PTERODAUSTRO_DISPLAY);
             }
             if (rand < 90) {
                 setAnimationTimer(100);
                 return event.setAndContinue(PTERODAUSTRO_IDLE);
             }
         }


         return event.setAndContinue(PTERODAUSTRO_IDLE);
     }

     private boolean isStillEnough() {
         return this.getDeltaMovement().horizontalDistance() < 0.05;
     }


     @Override
     public AnimatableInstanceCache getAnimatableInstanceCache() {
         return this.cache;
     }

     @Override
     public double getTick(Object o) {
         return tickCount;
     }

     @Override
     public void setFromBook(boolean fromBook) {
         this.entityData.set(FROM_BOOK, fromBook);
     }

     private class AIFlyIdle extends Goal {
         protected double x;
         protected double y;
         protected double z;

         public AIFlyIdle() {
             super();
             this.setFlags(EnumSet.of(Flag.MOVE));
         }

         @Override
         public boolean canUse() {
             if (PterodaustroEntity.this.isVehicle() || (PterodaustroEntity.this.getTarget() != null && PterodaustroEntity.this.getTarget().isAlive()) || PterodaustroEntity.this.isPassenger()) {
                 return false;
             } else {
                 if (PterodaustroEntity.this.getRandom().nextInt(45) != 0 && !PterodaustroEntity.this.isFlying()) {
                     return false;
                 }

                 Vec3 lvt_1_1_ = this.getPosition();
                 if (lvt_1_1_ == null) {
                     return false;
                 } else {
                     this.x = lvt_1_1_.x;
                     this.y = lvt_1_1_.y;
                     this.z = lvt_1_1_.z;
                     return true;
                 }
             }
         }

         public void tick() {
             PterodaustroEntity.this.getMoveControl().setWantedPosition(this.x, this.y, this.z, 3F);
             if (isFlying() && PterodaustroEntity.this.onGround() && PterodaustroEntity.this.timeFlying > 10) {
                 PterodaustroEntity.this.setFlying(false);
             }
         }

         @Nullable
         protected Vec3 getPosition() {
             Vec3 vector3d = PterodaustroEntity.this.position();
             if (PterodaustroEntity.this.timeFlying < 200 || PterodaustroEntity.this.isOverWaterOrVoid()) {
                 return PterodaustroEntity.this.getBlockInViewAway(vector3d, 0);
             } else {
                 return PterodaustroEntity.this.getBlockGrounding(vector3d);
             }
         }

         public boolean canContinueToUse() {
             return PterodaustroEntity.this.isFlying() && PterodaustroEntity.this.distanceToSqr(x, y, z) > 5F;
         }

         public void start() {
             PterodaustroEntity.this.setFlying(true);
             PterodaustroEntity.this.getMoveControl().setWantedPosition(this.x, this.y, this.z, 1F);
         }

         public void stop() {
             PterodaustroEntity.this.getNavigation().stop();
             x = 0;
             y = 0;
             z = 0;
             super.stop();
         }

     }

     public boolean isFromBook() {
         return this.entityData.get(FROM_BOOK).booleanValue();
     }

     public void setIsFromBook(boolean fromBook) {
         this.entityData.set(FROM_BOOK, fromBook);
     }

     public boolean requiresCustomPersistence() {
         return super.requiresCustomPersistence() || this.hasCustomName();
     }

     public boolean removeWhenFarAway(double d) {
         return !this.hasCustomName();
     }

     @Nullable
     public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_28134_, DifficultyInstance p_28135_, MobSpawnType p_28136_, @Nullable SpawnGroupData p_28137_, @Nullable CompoundTag p_28138_) {
         p_28137_ = super.finalizeSpawn(p_28134_, p_28135_, p_28136_, p_28137_, p_28138_);
         Level level = p_28134_.getLevel();
         if (level instanceof ServerLevel) {
             {
                 this.setPersistenceRequired();
             }
         }
         return p_28137_;
     }

     //public static boolean checkSurfaceDinoSpawnRules(EntityType<? extends AgeableMob> p_186238_, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource p_186242_) {
     //    return level.getBlockState(pos.below()).is(UPTags.DINO_NATURAL_SPAWNABLE)  && UnusualPrehistoryConfig.DINO_NATURAL_SPAWNING.get();
     //}

 }
