 package com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.aquatic;

 import com.google.common.collect.ImmutableList;
 import com.google.common.collect.ImmutableMap;
 import com.peeko32213.unusualprehistory.common.entity.animation.state.EntityAction;
 import com.peeko32213.unusualprehistory.common.entity.animation.state.StateHelper;
 import com.peeko32213.unusualprehistory.common.entity.animation.state.WeightedState;
 import com.peeko32213.unusualprehistory.common.entity.custom.ai.goal.CustomRideGoal;
 import com.peeko32213.unusualprehistory.common.entity.custom.base.PrehistoricEntity;
 import com.peeko32213.unusualprehistory.common.entity.util.interfaces.ICustomFollower;
 import com.peeko32213.unusualprehistory.common.entity.util.navigator.SmartBodyHelper;
 import com.peeko32213.unusualprehistory.core.registry.entities.UPEntities;
 import net.minecraft.core.BlockPos;
 import net.minecraft.nbt.CompoundTag;
 import net.minecraft.network.chat.Component;
 import net.minecraft.network.syncher.EntityDataAccessor;
 import net.minecraft.network.syncher.EntityDataSerializers;
 import net.minecraft.network.syncher.SynchedEntityData;
 import net.minecraft.server.level.ServerLevel;
 import net.minecraft.sounds.SoundEvent;
 import net.minecraft.sounds.SoundEvents;
 import net.minecraft.tags.FluidTags;
 import net.minecraft.util.Mth;
 import net.minecraft.util.RandomSource;
 import net.minecraft.world.InteractionHand;
 import net.minecraft.world.InteractionResult;
 import net.minecraft.world.damagesource.DamageSource;
 import net.minecraft.world.entity.*;
 import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
 import net.minecraft.world.entity.ai.attributes.Attributes;
 import net.minecraft.world.entity.ai.control.BodyRotationControl;
 import net.minecraft.world.entity.ai.control.MoveControl;
 import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
 import net.minecraft.world.entity.ai.goal.*;
 import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
 import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
 import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
 import net.minecraft.world.entity.ai.navigation.AmphibiousPathNavigation;
 import net.minecraft.world.entity.ai.navigation.PathNavigation;
 import net.minecraft.world.entity.ai.util.DefaultRandomPos;
 import net.minecraft.world.entity.player.Player;
 import net.minecraft.world.item.Item;
 import net.minecraft.world.item.ItemStack;
 import net.minecraft.world.item.Items;
 import net.minecraft.world.level.Level;
 import net.minecraft.world.level.LevelReader;
 import net.minecraft.world.level.block.Blocks;
 import net.minecraft.world.level.block.state.BlockState;
 import net.minecraft.world.level.gameevent.GameEvent;
 import net.minecraft.world.level.pathfinder.BlockPathTypes;
 import net.minecraft.world.phys.Vec3;
 import org.jetbrains.annotations.NotNull;
 import org.jetbrains.annotations.Nullable;
 import software.bernie.geckolib.core.animation.AnimatableManager;
 import software.bernie.geckolib.core.animation.AnimationController;
 import software.bernie.geckolib.core.animation.RawAnimation;
 import software.bernie.geckolib.core.keyframe.event.SoundKeyframeEvent;
 import software.bernie.geckolib.core.object.PlayState;

 import javax.annotation.Nonnull;
 import java.util.List;

 public class ArchelonEntity extends PrehistoricEntity implements ICustomFollower {

     private static final EntityDataAccessor<Integer> CHILL_TIME = SynchedEntityData.defineId(ArchelonEntity.class, EntityDataSerializers.INT);
     private static final EntityDataAccessor<Boolean> TRAVELLING = SynchedEntityData.defineId(ArchelonEntity.class, EntityDataSerializers.BOOLEAN);
     private static final EntityDataAccessor<BlockPos> TRAVEL_POS = SynchedEntityData.defineId(ArchelonEntity.class, EntityDataSerializers.BLOCK_POS);

     // Movement animations
     private static final RawAnimation ARCHELON_WALK = RawAnimation.begin().thenLoop("animation.archelon.walk");
     private static final RawAnimation ARCHELON_SWIM = RawAnimation.begin().thenLoop("animation.archelon.swim");
     private static final RawAnimation ARCHELON_SHAKE = RawAnimation.begin().thenPlay("animation.archelon.shake");
     private static final RawAnimation ARCHELON_SPIN_1 = RawAnimation.begin().thenPlay("animation.archelon.spin1");
     private static final RawAnimation ARCHELON_SPIN_2 = RawAnimation.begin().thenPlay("animation.archelon.spin2");

     // Idle animations
     private static final RawAnimation ARCHELON_IDLE = RawAnimation.begin().thenLoop("animation.archelon.idle");
     private static final RawAnimation ARCHELON_SWIM_IDLE = RawAnimation.begin().thenLoop("animation.archelon.swim_idle");

     // Attack animations
     private static final RawAnimation ARCHELON_BITE_BLEND = RawAnimation.begin().thenPlay("animation.archelon.bite_blend");
     private static final RawAnimation ARCHELON_RAMMING = RawAnimation.begin().thenPlay("animation.archelon.ramming");

     // Idle accessors
     private static final EntityDataAccessor<Boolean> IDLE_1_AC = SynchedEntityData.defineId(ArchelonEntity.class, EntityDataSerializers.BOOLEAN);
     private static final EntityDataAccessor<Boolean> IDLE_2_AC = SynchedEntityData.defineId(ArchelonEntity.class, EntityDataSerializers.BOOLEAN);
     private static final EntityDataAccessor<Boolean> IDLE_3_AC = SynchedEntityData.defineId(ArchelonEntity.class, EntityDataSerializers.BOOLEAN);

     // Idle actions
     private static final EntityAction ARCHELON_IDLE_1_ACTION = new EntityAction(0, (e) -> {}, 1);

     private static final StateHelper ARCHELON_IDLE_1_STATE =
             StateHelper.Builder.state(IDLE_1_AC, "archelon_shake")
                     .playTime(60)
                     .stopTime(100)
                     .entityAction(ARCHELON_IDLE_1_ACTION)
                     .build();

     private static final EntityAction ARCHELON_IDLE_2_ACTION = new EntityAction(0, (e) -> {}, 1);

     private static final StateHelper ARCHELON_IDLE_2_STATE =
             StateHelper.Builder.state(IDLE_2_AC, "archelon_spin_1")
                     .playTime(100)
                     .stopTime(200)
                     .entityAction(ARCHELON_IDLE_2_ACTION)
                     .build();

     private static final EntityAction ARCHELON_IDLE_3_ACTION = new EntityAction(0, (e) -> {}, 1);

     private static final StateHelper ARCHELON_IDLE_3_STATE =
             StateHelper.Builder.state(IDLE_3_AC, "archelon_spin_2")
                     .playTime(100)
                     .stopTime(200)
                     .entityAction(ARCHELON_IDLE_3_ACTION)
                     .build();

     // States
     @Override
     public ImmutableMap<String, StateHelper> getStates() {
         return ImmutableMap.of(
                 ARCHELON_IDLE_1_STATE.getName(), ARCHELON_IDLE_1_STATE,
                 ARCHELON_IDLE_2_STATE.getName(), ARCHELON_IDLE_2_STATE,
                 ARCHELON_IDLE_3_STATE.getName(), ARCHELON_IDLE_3_STATE
         );
     }

     @Override
     public List<WeightedState<StateHelper>> getWeightedStatesToPerform() {
         return ImmutableList.of(
                 WeightedState.of(ARCHELON_IDLE_1_STATE, 10),
                 WeightedState.of(ARCHELON_IDLE_2_STATE, 9),
                 WeightedState.of(ARCHELON_IDLE_3_STATE, 9)
         );
     }

     // Actions
     @Override
     public boolean getAction() {
         return false;
     }

     @Override
     public void setAction(boolean action) {}

     // Animation sounds
     private void soundListener(SoundKeyframeEvent<ArchelonEntity> event) {
         ArchelonEntity archelon = event.getAnimatable();
         archelon.level();
     }

     // Animation control
     @Override
     public void registerControllers(final AnimatableManager.ControllerRegistrar controllers) {
         AnimationController<ArchelonEntity> controller = new AnimationController<>(this, "controller", 5, this::predicate);
         controllers.add(controller);
         AnimationController<ArchelonEntity> blend = new AnimationController<>(this, "blend", 5, this::predicate)
                 .triggerableAnim("shake", ARCHELON_SHAKE)
                 .triggerableAnim("spin_1", ARCHELON_SPIN_1)
                 .triggerableAnim("spin_2", ARCHELON_SPIN_2)
                 .triggerableAnim("bite", ARCHELON_BITE_BLEND)
             ;
         blend.setSoundKeyframeHandler(this::soundListener);
         controllers.add(blend);
     }

     protected <E extends ArchelonEntity> PlayState predicate(final software.bernie.geckolib.core.animation.AnimationState<E> event) {

         if (this.isFromBook()) {
             return event.setAndContinue(ARCHELON_SWIM_IDLE);
         }

         if (this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6 && !this.isInWater() && !this.isSwimming()) {
             event.setAnimation(ARCHELON_WALK);
             return PlayState.CONTINUE;
         }

         if (!(event.getLimbSwingAmount() > -0.06F && event.getLimbSwingAmount() < 0.06F) && this.isInWater()) {
             event.setAnimation(ARCHELON_SWIM);
             event.getController().setAnimationSpeed(1.0F);
             return PlayState.CONTINUE;
         }
         else if(!(event.getLimbSwingAmount() > -0.06F && event.getLimbSwingAmount() < 0.06F) && this.isInWater() && this.hasControllingPassenger() && !this.isInSittingPose()){

             if(this.getControllingPassenger().isSprinting()){
                 event.setAndContinue(ARCHELON_SWIM);
                 event.getController().setAnimationSpeed(2.0F);
             }
             else {
                 event.setAndContinue(ARCHELON_SWIM);
                 event.getController().setAnimationSpeed(1.2F);
             }

             return PlayState.CONTINUE;
         }

         if(this.isInWater()) {
//             if (getBooleanState(IDLE_2_AC)) {
//                 if(this.isStillEnough()) {
//                     triggerAnim("blend", "spin_1");
//                     return event.setAndContinue(ARCHELON_SWIM_IDLE);
//                 }
//                 else {
//                     triggerAnim("blend", "spin_1");
//                     return PlayState.CONTINUE;
//                 }
//             }
//             else if (getBooleanState(IDLE_3_AC)) {
//                 if(this.isStillEnough()) {
//                     triggerAnim("blend", "spin_2");
//                     return event.setAndContinue(ARCHELON_SWIM_IDLE);
//                 }
//                 else {
//                     triggerAnim("blend", "spin_2");
//                     return PlayState.CONTINUE;
//                 }
//             }
             return event.setAndContinue(ARCHELON_SWIM_IDLE);
         } else {
//             if (getBooleanState(IDLE_1_AC)) {
//                 if(this.isStillEnough()) {
//                     triggerAnim("blend", "shake");
//                     return event.setAndContinue(ARCHELON_IDLE);
//                 }
//                 else {
//                     triggerAnim("blend", "shake");
//                     return PlayState.CONTINUE;
//                 }
//             }
             return event.setAndContinue(ARCHELON_IDLE);
         }
     }

     // Body control / navigation
     @Override
     protected @NotNull BodyRotationControl createBodyControl() {
         SmartBodyHelper helper = new SmartBodyHelper(this);
         helper.bodyLagMoving = 0.15F;
         helper.bodyLagStill = 0.1F;
         return helper;
     }

     protected @NotNull PathNavigation createNavigation(Level pLevel) {
         return new ArchelonEntity.TurtlePathNavigation(this, pLevel);
     }

     public ArchelonEntity(EntityType<? extends PrehistoricEntity> entityType, Level level) {
         super(entityType, level);
         this.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
         this.setPathfindingMalus(BlockPathTypes.WATER_BORDER, 0.0F);
         this.lookControl = new SmoothSwimmingLookControl(this, 4);
         this.moveControl = new ArchelonEntity.TurtleMoveControl(this);
     }

     // Attributes
     public static AttributeSupplier.Builder createAttributes() {
         return Mob.createMobAttributes()
                 .add(Attributes.MAX_HEALTH, 60.0D)
                 .add(Attributes.MOVEMENT_SPEED, 0.2D)
                 .add(Attributes.ATTACK_DAMAGE, 5.0D)
                 .add(Attributes.ARMOR, 10D)
                 .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D)
             ;
     }

     // Goals
     protected void registerGoals() {
         super.registerGoals();
         this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
         this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
         this.goalSelector.addGoal(1, new CustomRideGoal(this, 3D));
         if(!this.hasControllingPassenger()) {
             this.goalSelector.addGoal(3, new ArchelonEntity.TurtleGoToWaterGoal(this, 1.25D));
             this.goalSelector.addGoal(7, new ArchelonEntity.TurtleTravelGoal(this, 1.0D));
             this.goalSelector.addGoal(9, new ArchelonEntity.TurtleRandomStrollGoal(this, 1.25D, 100));
         }
//         this.goalSelector.addGoal(8, new PrehistoricFollowOwnerGoal(this, 1.2D, 5.0F, 2.0F, false));
         this.goalSelector.addGoal(0, new SitWhenOrderedToGoal(this));
         this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
         this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
         this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
     }

     @Override
     public boolean canBreatheUnderwater() {
         return true;
     }

     @Override
     public boolean isPushedByFluid() {
         return false;
     }

     // Foods
     public boolean isFood(ItemStack pStack) {
         Item item = pStack.getItem();
         return item.isEdible() && pStack.getFoodProperties(this).isMeat();
     }

     // Mob interactions
     public InteractionResult mobInteract(@Nonnull Player player, @Nonnull InteractionHand hand) {
         ItemStack itemstack = player.getItemInHand(hand);
         Item item = itemstack.getItem();
         if(hand != InteractionHand.MAIN_HAND) return InteractionResult.FAIL;
         if (isFood(itemstack)) {
             if (!isTame()) {
                 if(!this.level().isClientSide) {
                     int size = itemstack.getCount();
                     this.tame(player);
                     itemstack.shrink(size);
                 }
                 this.playSound(this.getEatingSound(itemstack), 1.0F, 1.0F);
                 return InteractionResult.SUCCESS;
             }
         }
         if (isTame() && isOwnedBy(player)) {
             if (this.isFood(itemstack) && this.getHealth() < this.getMaxHealth()) {
                 if (!player.getAbilities().instabuild) {
                     itemstack.shrink(1);
                 }
                 if (!this.level().isClientSide) {
                     this.heal((float) itemstack.getFoodProperties(this).getNutrition());
                 }
                 this.gameEvent(GameEvent.EAT, this);
                 return InteractionResult.SUCCESS;
             } else if (itemstack.getItem() == Items.SADDLE && !this.isSaddled()) {
                 this.usePlayerItem(player, hand, itemstack);
                 this.setSaddled(true);
                 return InteractionResult.SUCCESS;
             } else if (itemstack.getItem() == Items.SHEARS && this.isSaddled()) {
                 this.setSaddled(false);
                 this.spawnAtLocation(Items.SADDLE);
                 return InteractionResult.SUCCESS;
             } else {
                 if (!player.isShiftKeyDown() && !this.isBaby() && this.isSaddled()) {
                     if (!this.level().isClientSide) {
                         player.startRiding(this);
                     }
                     return InteractionResult.SUCCESS;
                 } else {
                     this.setCommand((this.getCommand() + 1) % 3);

                     if (this.getCommand() == 3) {
                         this.setCommand(0);
                     }
                     player.displayClientMessage(Component.translatable("entity.unusualprehistory.all.command_" + this.getCommand(), this.getName()), true);
                     boolean sit = this.getCommand() == 2;
                     if (sit) {
                         this.setOrderedToSit(true);
                         return InteractionResult.SUCCESS;
                     } else {
                         this.setOrderedToSit(false);
                         return InteractionResult.SUCCESS;
                     }
                 }
             }
         }
         return InteractionResult.PASS;
     }

     // Passenger offset
     @Override
     protected void positionRider(Entity pPassenger, @NotNull MoveFunction pCallback) {
         float ySin = Mth.sin(this.yBodyRot * ((float) Math.PI / 180F));
         float yCos = Mth.cos(this.yBodyRot * ((float) Math.PI / 180F));
         pPassenger.setPos(this.getX() + (double) (0.15F * ySin), this.getY() + this.getPassengersRidingOffset() + pPassenger.getMyRidingOffset() + 0.4F, this.getZ() - (double) (0.15F * yCos));
     }

     public double getPassengersRidingOffset() {
         return 0.8F;
     }

     public void tick() {
         super.tick();

         if (!this.level().isClientSide) {
             if (this.getChillTime() > 0) {
                 this.setChillTime(this.getChillTime() - 1);
             } else if (this.shouldSwim()) {
                 if (random.nextInt(this.isVehicle() ? 200 : 2000) == 0) {
                     this.setChillTime(100 + random.nextInt(500));
                 }
             }
         }
     }

     // Travel
     public void travel(Vec3 travelVector) {
         if (this.isAlive()) {
             LivingEntity livingentity = this.getControllingPassenger();
             if (this.isVehicle() && livingentity != null) {
                 this.setYRot(livingentity.getYRot());
                 this.yRotO = this.getYRot();
                 this.setXRot(livingentity.getXRot() * 0.5F);
                 this.setRot(this.getYRot(), this.getXRot());
                 this.yBodyRot = this.getYRot();
                 this.yHeadRot = this.yBodyRot;
                 float f = livingentity.xxa * 0.5F;
                 float f1 = livingentity.zza;
                 if (f1 <= 0.0F) {
                     f1 *= 0.25F;
                 }

                 this.setSpeed(0.01F);
                 if(this.isInWater()) {
                     if (!this.isInSittingPose()) {
                         if (this.getControllingPassenger().isSprinting()) {
                             this.setSpeed(((float) this.getAttributeValue(Attributes.MOVEMENT_SPEED) * 1.1F));
                         } else {
                             this.setSpeed(((float) this.getAttributeValue(Attributes.MOVEMENT_SPEED) * 0.6F));
                         }
                     }
                 }
                 super.travel(new Vec3(f, travelVector.y, f1));
             }
             if (this.isEffectiveAi() && this.isInWater()) {
                 this.moveRelative(this.getSpeed(), travelVector);
                 this.move(MoverType.SELF, this.getDeltaMovement());
                 this.setDeltaMovement(this.getDeltaMovement().scale(0.7D));
                 if (this.getTarget() == null) {
                     this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.005D, 0.0D));
                 }
             }
             else {
                 super.travel(travelVector);
             }
         }
     }

     // Sounds
     protected SoundEvent getSwimSound() {
         return SoundEvents.TURTLE_SWIM;
     }

     @Nullable
     protected SoundEvent getHurtSound(DamageSource pDamageSource) {
         return SoundEvents.TURTLE_HURT;
     }

     protected SoundEvent getDeathSound() {
         return SoundEvents.TURTLE_DEATH;
     }

     @Nullable
     protected SoundEvent getAmbientSound() {
         return !this.isInWater() && this.onGround() ? SoundEvents.TURTLE_AMBIENT_LAND : super.getAmbientSound();
     }

     protected void playSwimSound(float pVolume) {
         super.playSwimSound(pVolume * 1.5F);
     }

     protected void playStepSound(BlockPos pPos, BlockState pBlock) {
         SoundEvent soundevent = SoundEvents.TURTLE_SHAMBLE;
         this.playSound(soundevent, 0.25F, 0.85F);
     }

     public float getVoicePitch() {
         return super.getVoicePitch() * 0.75F;
     }

     // Synched data
     @Override
     protected void defineSynchedData() {
         super.defineSynchedData();
         this.entityData.define(IDLE_1_AC, false);
         this.entityData.define(IDLE_2_AC, false);
         this.entityData.define(IDLE_3_AC, false);
         this.entityData.define(CHILL_TIME, 0);
         this.entityData.define(TRAVELLING, false);
         this.entityData.define(TRAVEL_POS, BlockPos.ZERO);
     }

     // Save data
     public void addAdditionalSaveData(CompoundTag compound) {
         super.addAdditionalSaveData(compound);
         compound.putInt("ChillTime", this.getChillTime());
     }

     public void readAdditionalSaveData(CompoundTag compound) {
         super.readAdditionalSaveData(compound);
         this.setChillTime(compound.getInt("ChillTime"));
         int l = compound.getInt("TravelPosX");
         int i1 = compound.getInt("TravelPosY");
         int j1 = compound.getInt("TravelPosZ");
         this.setTravelPos(new BlockPos(l, i1, j1));
     }

     @Nullable
     public LivingEntity getControllingPassenger() {
         for (Entity passenger : this.getPassengers()) {
             if (passenger instanceof Player) {
                 return (Player) passenger;
             }
         }
         return null;
     }

     // Travel position
     void setTravelPos(BlockPos pTravelPos) {
         this.entityData.set(TRAVEL_POS, pTravelPos);
     }
     BlockPos getTravelPos() {
         return this.entityData.get(TRAVEL_POS);
     }
     boolean isTravelling() {
         return this.entityData.get(TRAVELLING);
     }
     void setTravelling(boolean pIsTravelling) {
         this.entityData.set(TRAVELLING, pIsTravelling);
     }

     // Chill time
     public int getChillTime() {
         return this.entityData.get(CHILL_TIME);
     }
     public void setChillTime(int chillTime) {
         this.entityData.set(CHILL_TIME, chillTime);
     }

     @Override
     protected int getKillHealAmount() {
         return 6;
     }

     @Override
     public boolean shouldFollow() {
         return this.getCommand() == 1;
     }

     @Nullable
     @Override
     public AgeableMob getBreedOffspring(@NotNull ServerLevel serverLevel, @NotNull AgeableMob ageableMob) {
         return UPEntities.ARCHELON.get().create(serverLevel);
     }

     protected void customServerAiStep() {
         super.customServerAiStep();
     }

     // Find water
     static class TurtleGoToWaterGoal extends MoveToBlockGoal {
         private static final int GIVE_UP_TICKS = 1200;
         private final ArchelonEntity turtle;

         TurtleGoToWaterGoal(ArchelonEntity pTurtle, double pSpeedModifier) {
             super(pTurtle, pTurtle.isBaby() ? 2.0D : pSpeedModifier, 24);
             this.turtle = pTurtle;
             this.verticalSearchStart = -1;
         }

         public boolean canContinueToUse() {
             return !this.turtle.isInWater() && this.tryTicks <= 1200 && this.isValidTarget(this.turtle.level(), this.blockPos);
         }


         public boolean canUse() {
             if (this.turtle.isBaby() && !this.turtle.isInWater()) {
                 return super.canUse();
             } else {
                 return !this.turtle.isInWater();
             }
         }

         public boolean shouldRecalculatePath() {
             return this.tryTicks % 160 == 0;
         }


         protected boolean isValidTarget(LevelReader pLevel, BlockPos pPos) {
             return pLevel.getBlockState(pPos).is(Blocks.WATER);
         }
     }

     // Travel
     static class TurtleTravelGoal extends Goal {
         private final ArchelonEntity turtle;
         private final double speedModifier;
         private boolean stuck;

         TurtleTravelGoal(ArchelonEntity pTurtle, double pSpeedModifier) {
             this.turtle = pTurtle;
             this.speedModifier = pSpeedModifier;
         }

         /**
          * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
          * method as well.
          */
         public boolean canUse() {
             return this.turtle.isInWater();
         }

         /**
          * Execute a one shot task or start executing a continuous task
          */
         public void start() {
             int i = 512;
             int j = 4;
             RandomSource randomsource = this.turtle.random;
             int k = randomsource.nextInt(1025) - 512;
             int l = randomsource.nextInt(9) - 4;
             int i1 = randomsource.nextInt(1025) - 512;
             if ((double)l + this.turtle.getY() > (double)(this.turtle.level().getSeaLevel() - 1)) {
                 l = 0;
             }

             BlockPos blockpos = BlockPos.containing((double)k + this.turtle.getX(), (double)l + this.turtle.getY(), (double)i1 + this.turtle.getZ());
             this.turtle.setTravelPos(blockpos);
             this.turtle.setTravelling(true);
             this.stuck = false;
         }

         /**
          * Keep ticking a continuous task that has already been started
          */
         public void tick() {
             if (this.turtle.getNavigation().isDone()) {
                 Vec3 vec3 = Vec3.atBottomCenterOf(this.turtle.getTravelPos());
                 Vec3 vec31 = DefaultRandomPos.getPosTowards(this.turtle, 16, 3, vec3, (double)((float)Math.PI / 10F));
                 if (vec31 == null) {
                     vec31 = DefaultRandomPos.getPosTowards(this.turtle, 8, 7, vec3, (double)((float)Math.PI / 2F));
                 }

                 if (vec31 != null) {
                     int i = Mth.floor(vec31.x);
                     int j = Mth.floor(vec31.z);
                     int k = 34;
                     if (!this.turtle.level().hasChunksAt(i - 34, j - 34, i + 34, j + 34)) {
                         vec31 = null;
                     }
                 }

                 if (vec31 == null) {
                     this.stuck = true;
                     return;
                 }

                 this.turtle.getNavigation().moveTo(vec31.x, vec31.y, vec31.z, this.speedModifier);
             }

         }

         /**
          * Returns whether an in-progress EntityAIBase should continue executing
          */
         public boolean canContinueToUse() {
             return !this.turtle.getNavigation().isDone() && !this.stuck && !this.turtle.isInLove();
         }

         /**
          * Reset the task's internal state. Called when this task is interrupted by another one
          */
         public void stop() {
             this.turtle.setTravelling(false);
             super.stop();
         }
     }

     // Land stroll
     static class TurtleRandomStrollGoal extends RandomStrollGoal {
         private final ArchelonEntity turtle;

         TurtleRandomStrollGoal(ArchelonEntity pTurtle, double pSpeedModifier, int pInterval) {
             super(pTurtle, pSpeedModifier, pInterval);
             this.turtle = pTurtle;
         }

         public boolean canUse() {
             return !this.mob.isInWater();
         }
     }

     // Move control
     static class TurtleMoveControl extends MoveControl {
         private final ArchelonEntity turtle;

         TurtleMoveControl(ArchelonEntity pTurtle) {
             super(pTurtle);
             this.turtle = pTurtle;
         }

         private void updateSpeed() {
             if (this.turtle.isInWater()) {
                 this.turtle.setDeltaMovement(this.turtle.getDeltaMovement().add(0.0D, 0.0D, 0.0D));

                 if (this.turtle.isBaby()) {
                     this.turtle.setSpeed(Math.max(this.turtle.getSpeed() / 3.0F, 0.06F));
                 }
             } else if (this.turtle.onGround()) {
                 this.turtle.setSpeed(Math.max(this.turtle.getSpeed() / 2.0F, 0.06F));
             }

         }

         public void tick() {
             this.updateSpeed();
             if (this.operation == MoveControl.Operation.MOVE_TO && !this.turtle.getNavigation().isDone()) {
                 double d0 = this.wantedX - this.turtle.getX();
                 double d1 = this.wantedY - this.turtle.getY();
                 double d2 = this.wantedZ - this.turtle.getZ();
                 double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
                 if (d3 < (double)1.0E-5F) {
                     this.mob.setSpeed(0.0F);
                 } else {
                     d1 /= d3;
                     float f = (float)(Mth.atan2(d2, d0) * (double)(180F / (float)Math.PI)) - 90.0F;
                     this.turtle.setYRot(this.rotlerp(this.turtle.getYRot(), f, 90.0F));
                     this.turtle.yBodyRot = this.turtle.getYRot();
                     float f1 = (float)(this.speedModifier * this.turtle.getAttributeValue(Attributes.MOVEMENT_SPEED));
                     this.turtle.setSpeed(Mth.lerp(0.125F, this.turtle.getSpeed(), f1));
                     this.turtle.setDeltaMovement(this.turtle.getDeltaMovement().add(0.0D, (double)this.turtle.getSpeed() * d1 * 0.1D, 0.0D));
                 }
             } else {
                 this.turtle.setSpeed(0.0F);
             }
         }
     }

     public boolean shouldSwim() {
         return getMaxFluidHeight() >= 0.1F || this.isInLava() || this.isInWaterOrBubble();
     }

     private double getMaxFluidHeight() {
         return Math.max(this.getFluidHeight(FluidTags.LAVA), this.getFluidHeight(FluidTags.WATER));
     }

     // Amphibious navigation
     static class TurtlePathNavigation extends AmphibiousPathNavigation {
         TurtlePathNavigation(ArchelonEntity pTurtle, Level pLevel) {
             super(pTurtle, pLevel);
         }

         public boolean isStableDestination(BlockPos pPos) {
             Mob mob = this.mob;
             if (mob instanceof ArchelonEntity turtle) {
                 if (turtle.isTravelling()) {
                     return this.level.getBlockState(pPos).is(Blocks.WATER);
                 }
             }

             return !this.level.getBlockState(pPos.below()).isAir();
         }
     }
 }
