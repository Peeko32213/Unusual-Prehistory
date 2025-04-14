 package com.peeko32213.unusualprehistory.common.entity.custom.prehistoric;

 import com.google.common.collect.ImmutableList;
 import com.google.common.collect.ImmutableMap;
 import com.peeko32213.unusualprehistory.common.entity.animation.state.EntityAction;
 import com.peeko32213.unusualprehistory.common.entity.animation.state.StateHelper;
 import com.peeko32213.unusualprehistory.common.entity.animation.state.WeightedState;
 import com.peeko32213.unusualprehistory.common.entity.custom.ai.goal.BabyPanicGoal;
 import com.peeko32213.unusualprehistory.common.entity.custom.ai.goal.PrehistoricFollowOwnerGoal;
 import com.peeko32213.unusualprehistory.common.entity.custom.base.PrehistoricEntity;
 import com.peeko32213.unusualprehistory.common.entity.util.interfaces.ICustomFollower;
 import com.peeko32213.unusualprehistory.common.entity.util.navigator.SmartBodyHelper;
 import com.peeko32213.unusualprehistory.common.entity.util.navigator.SmoothGroundNavigation;
 import com.peeko32213.unusualprehistory.common.message.BalaurMountMessage;
 import com.peeko32213.unusualprehistory.core.registry.entities.UPEntities;
 import com.peeko32213.unusualprehistory.core.registry.items.UPItems;
 import com.peeko32213.unusualprehistory.core.registry.UPMessages;
 import com.peeko32213.unusualprehistory.core.registry.UPSounds;
 import com.peeko32213.unusualprehistory.core.other.util.UPMath;
 import net.minecraft.core.BlockPos;
 import net.minecraft.nbt.CompoundTag;
 import net.minecraft.network.chat.Component;
 import net.minecraft.network.syncher.EntityDataAccessor;
 import net.minecraft.network.syncher.EntityDataSerializers;
 import net.minecraft.network.syncher.SynchedEntityData;
 import net.minecraft.server.level.ServerLevel;
 import net.minecraft.sounds.SoundEvent;
 import net.minecraft.sounds.SoundEvents;
 import net.minecraft.util.Mth;
 import net.minecraft.world.InteractionHand;
 import net.minecraft.world.InteractionResult;
 import net.minecraft.world.damagesource.DamageSource;
 import net.minecraft.world.entity.*;
 import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
 import net.minecraft.world.entity.ai.attributes.Attributes;
 import net.minecraft.world.entity.ai.control.BodyRotationControl;
 import net.minecraft.world.entity.ai.goal.*;
 import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
 import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
 import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
 import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
 import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
 import net.minecraft.world.entity.ai.navigation.PathNavigation;
 import net.minecraft.world.entity.player.Player;
 import net.minecraft.world.item.ItemStack;
 import net.minecraft.world.item.Items;
 import net.minecraft.world.level.Level;
 import net.minecraft.world.level.block.state.BlockState;
 import net.minecraft.world.level.gameevent.GameEvent;
 import net.minecraft.world.phys.Vec3;
 import org.jetbrains.annotations.NotNull;
 import software.bernie.geckolib.core.animation.AnimatableManager;
 import software.bernie.geckolib.core.animation.AnimationController;
 import software.bernie.geckolib.core.animation.AnimationState;
 import software.bernie.geckolib.core.animation.RawAnimation;
 import software.bernie.geckolib.core.object.PlayState;

 import javax.annotation.Nullable;
 import java.util.List;
 import java.util.Objects;

 public class BalaurEntity extends PrehistoricEntity implements ICustomFollower {

     private static final EntityDataAccessor<Integer> COMMAND = SynchedEntityData.defineId(BalaurEntity.class, EntityDataSerializers.INT);
     private static final EntityDataAccessor<Integer> COMBAT_STATE = SynchedEntityData.defineId(BalaurEntity.class, EntityDataSerializers.INT);
     private static final EntityDataAccessor<Integer> ENTITY_STATE = SynchedEntityData.defineId(BalaurEntity.class, EntityDataSerializers.INT);
     private static final EntityDataAccessor<Integer> ANIMATION_STATE = SynchedEntityData.defineId(BalaurEntity.class, EntityDataSerializers.INT);

     public float sitProgress;
     private int latchTime = 0;

     public float flap;
     public float flapSpeed;
     public float oFlapSpeed;
     public float oFlap;
     public float flapping = 1.0F;
     private float nextFlap = 1.0F;

     // Movement animations
     private static final RawAnimation BALAUR_WALK = RawAnimation.begin().thenLoop("animation.balaur.walk");
     private static final RawAnimation BALAUR_RUN = RawAnimation.begin().thenLoop("animation.balaur.run");
     private static final RawAnimation BALAUR_SWIM = RawAnimation.begin().thenLoop("animation.balaur.swim");
     private static final RawAnimation BALAUR_FALL = RawAnimation.begin().thenLoop("animation.balaur.fall");

     // Idle animations
     private static final RawAnimation BALAUR_IDLE = RawAnimation.begin().thenLoop("animation.balaur.idle");
     private static final RawAnimation BALAUR_SCRATCH_1 = RawAnimation.begin().thenPlay("animation.balaur.scratch1");
     private static final RawAnimation BALAUR_SCRATCH_2 = RawAnimation.begin().thenPlay("animation.balaur.scratch2");
     private static final RawAnimation BALAUR_HISS = RawAnimation.begin().thenPlay("animation.balaur.hiss");
     private static final RawAnimation BALAUR_PREEN_1 = RawAnimation.begin().thenPlay("animation.balaur.preen1");
     private static final RawAnimation BALAUR_PREEN_2 = RawAnimation.begin().thenPlay("animation.balaur.preen2");
     private static final RawAnimation BALAUR_WARN = RawAnimation.begin().thenLoop("animation.balaur.warn");
     private static final RawAnimation BALAUR_SIT = RawAnimation.begin().thenLoop("animation.balaur.sit");
     private static final RawAnimation BALAUR_SLEEP = RawAnimation.begin().thenLoop("animation.balaur.sleep");

     // Attack animations
     private static final RawAnimation BALAUR_BITE_1 = RawAnimation.begin().thenLoop("animation.balaur.bite1");
     private static final RawAnimation BALAUR_BITE_2 = RawAnimation.begin().thenLoop("animation.balaur.bite2");
     private static final RawAnimation BALAUR_CLAW = RawAnimation.begin().thenLoop("animation.balaur.claw");
     private static final RawAnimation BALAUR_POUNCE_START = RawAnimation.begin().thenLoop("animation.balaur.pounce_start");
     private static final RawAnimation BALAUR_POUNCE_HOLD = RawAnimation.begin().thenLoop("animation.balaur.pounce_hold");

     // Idle accessors
     private static final EntityDataAccessor<Boolean> IDLE_1_AC = SynchedEntityData.defineId(BalaurEntity.class, EntityDataSerializers.BOOLEAN);
     private static final EntityDataAccessor<Boolean> IDLE_2_AC = SynchedEntityData.defineId(BalaurEntity.class, EntityDataSerializers.BOOLEAN);
     private static final EntityDataAccessor<Boolean> IDLE_3_AC = SynchedEntityData.defineId(BalaurEntity.class, EntityDataSerializers.BOOLEAN);
     private static final EntityDataAccessor<Boolean> IDLE_4_AC = SynchedEntityData.defineId(BalaurEntity.class, EntityDataSerializers.BOOLEAN);
     private static final EntityDataAccessor<Boolean> IDLE_5_AC = SynchedEntityData.defineId(BalaurEntity.class, EntityDataSerializers.BOOLEAN);

     // Idle actions
     private static final EntityAction BALAUR_IDLE_1_ACTION = new EntityAction(0, (e) -> {}, 1);

     private static final StateHelper BALAUR_IDLE_1_STATE =
             StateHelper.Builder.state(IDLE_1_AC, "balaur_scratch_1")
                     .playTime(20)
                     .stopTime(90)
                     .entityAction(BALAUR_IDLE_1_ACTION)
                     .build();

     private static final EntityAction BALAUR_IDLE_2_ACTION = new EntityAction(0, (e) -> {}, 1);

     private static final StateHelper BALAUR_IDLE_2_STATE =
             StateHelper.Builder.state(IDLE_2_AC, "balaur_scratch_2")
                     .playTime(20)
                     .stopTime(90)
                     .entityAction(BALAUR_IDLE_2_ACTION)
                     .build();

     private static final EntityAction BALAUR_IDLE_3_ACTION = new EntityAction(0, (e) -> {}, 1);

     private static final StateHelper BALAUR_IDLE_3_STATE =
             StateHelper.Builder.state(IDLE_3_AC, "balaur_hiss")
                     .playTime(40)
                     .stopTime(170)
                     .entityAction(BALAUR_IDLE_3_ACTION)
                     .build();

     private static final EntityAction BALAUR_IDLE_4_ACTION = new EntityAction(0, (e) -> {}, 1);

     private static final StateHelper BALAUR_IDLE_4_STATE =
             StateHelper.Builder.state(IDLE_4_AC, "balaur_preen_1")
                     .playTime(80)
                     .stopTime(150)
                     .entityAction(BALAUR_IDLE_4_ACTION)
                     .build();

     private static final EntityAction BALAUR_IDLE_5_ACTION = new EntityAction(0, (e) -> {}, 1);

     private static final StateHelper BALAUR_IDLE_5_STATE =
             StateHelper.Builder.state(IDLE_5_AC, "balaur_preen_2")
                     .playTime(80)
                     .stopTime(150)
                     .entityAction(BALAUR_IDLE_5_ACTION)
                     .build();

     @Override
     public ImmutableMap<String, StateHelper> getStates() {
         return ImmutableMap.of(
                 BALAUR_IDLE_1_STATE.getName(), BALAUR_IDLE_1_STATE,
                 BALAUR_IDLE_2_STATE.getName(), BALAUR_IDLE_2_STATE,
                 BALAUR_IDLE_3_STATE.getName(), BALAUR_IDLE_3_STATE,
                 BALAUR_IDLE_4_STATE.getName(), BALAUR_IDLE_4_STATE,
                 BALAUR_IDLE_5_STATE.getName(), BALAUR_IDLE_5_STATE
         );
     }

     @Override
     public List<WeightedState<StateHelper>> getWeightedStatesToPerform() {
         return ImmutableList.of(
                 WeightedState.of(BALAUR_IDLE_1_STATE, 10),
                 WeightedState.of(BALAUR_IDLE_2_STATE, 10),
                 WeightedState.of(BALAUR_IDLE_3_STATE, 6),
                 WeightedState.of(BALAUR_IDLE_4_STATE, 9),
                 WeightedState.of(BALAUR_IDLE_5_STATE, 9)
         );
     }

     @Override
     public boolean getAction() {
         return false;
     }

     @Override
     public void setAction(boolean action) {}

     @Override
     protected @NotNull BodyRotationControl createBodyControl() {
         SmartBodyHelper helper = new SmartBodyHelper(this);
         helper.bodyLagMoving = 0.75F;
         helper.bodyLagStill = 0.3F;
         return helper;
     }

     @Override
     protected @NotNull PathNavigation createNavigation(Level levelIn) {
         return new SmoothGroundNavigation(this, levelIn);
     }

     public BalaurEntity(EntityType<? extends PrehistoricEntity> entityType, Level level) {
         super(entityType, level);
         ((GroundPathNavigation) this.getNavigation()).setCanOpenDoors(true);
         this.setMaxUpStep(1.25F);
         this.reassessTameGoals();
     }

     public static AttributeSupplier.Builder createAttributes() {
         return Mob.createMobAttributes()
             .add(Attributes.MAX_HEALTH, 16.0D)
             .add(Attributes.MOVEMENT_SPEED, 0.2D)
             .add(Attributes.ATTACK_DAMAGE, 6.0D);
     }

     protected void registerGoals() {
         super.registerGoals();
         this.goalSelector.addGoal(0, new FloatGoal(this));
         this.goalSelector.addGoal(3, new BabyPanicGoal(this, 2.0D));
//         this.goalSelector.addGoal(1, new BalaurEntity.BalaurMeleeAttackGoal(this, 1.35F, true));
         this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 1.0F, 30));
         this.goalSelector.addGoal(5, new FollowParentGoal(this, 1.1D));
         this.goalSelector.addGoal(3, new OpenDoorGoal(this, true));
         this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
         this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
         this.goalSelector.addGoal(0, new SitWhenOrderedToGoal(this));
         this.goalSelector.addGoal(3, new PrehistoricFollowOwnerGoal(this, 1.2D, 5.0F, 2.0F, false));
         this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, false));
         this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)));
         this.targetSelector.addGoal(8, new OwnerHurtByTargetGoal(this));
         this.targetSelector.addGoal(8, new OwnerHurtTargetGoal(this));
     }

     @Override
     public void aiStep() {
         super.aiStep();
         this.oFlap = this.flap;
         this.oFlapSpeed = this.flapSpeed;
         this.flapSpeed += (this.onGround() ? -1.0F : 4.0F) * 0.3F;
         this.flapSpeed = Mth.clamp(this.flapSpeed, 0.0F, 1.0F);
         if (!this.onGround() && this.flapping < 1.0F) {
             this.flapping = 1.0F;
         }

         this.flapping *= 0.9F;
         Vec3 vec3 = this.getDeltaMovement();
         if (!this.onGround() && vec3.y < 0.0) {
             this.setDeltaMovement(vec3.multiply(1.0, 0.75, 1.0));
         }

         this.flap += this.flapping * 2.0F;
     }

     @Override
     public void customServerAiStep() {
         if (this.getMoveControl().hasWanted()) {
             this.setSprinting(this.getMoveControl().getSpeedModifier() >= 1.25D);
         } else {
             this.setSprinting(false);
         }
         super.customServerAiStep();
     }

     @Override
     public InteractionResult mobInteract(Player player, InteractionHand hand) {
         ItemStack itemstack = player.getItemInHand(hand);
         InteractionResult interactionresult = itemstack.interactLivingEntity(player, this, hand);
         if (itemstack.is(Items.BEEF)) {
             if (!isTame()) {
                 this.usePlayerItem(player, hand, itemstack);
                 if (getRandom().nextInt(3) == 0) {
                     this.tame(player);
                     this.level().broadcastEntityEvent(this, (byte) 7);
                     itemstack.shrink(1);
                 } else {
                     this.level().broadcastEntityEvent(this, (byte) 6);
                 }
             }
         }
         if (isTame() && isOwnedBy(player)) {
             if (this.isFood(itemstack) && this.getHealth() < this.getMaxHealth()) {
                 if (!player.getAbilities().instabuild) {
                     itemstack.shrink(1);
                 }
                 if (!this.level().isClientSide) {
                     this.heal((float) Objects.requireNonNull(itemstack.getFoodProperties(this)).getNutrition());
                 }
                 this.gameEvent(GameEvent.EAT, this);
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
         return InteractionResult.SUCCESS;

     }

     public boolean hurt(DamageSource source, float amount) {
         if (source.getEntity() != null && this.getRootVehicle() == source.getEntity().getRootVehicle()) {
             return super.hurt(source, amount * 0.333F);
         }
         return super.hurt(source, amount);
     }

     public void rideTick() {
         final Entity entity = this.getVehicle();
         if (this.isPassenger() && !entity.isAlive()) {
             this.stopRiding();
         } else {
             this.setDeltaMovement(0, 0, 0);
             this.tick();
             if (this.isPassenger()) {
                 final Entity mount = this.getVehicle();
                 if (mount instanceof final LivingEntity livingEntity) {
                     this.yBodyRot = livingEntity.yBodyRot;
                     this.setYRot(livingEntity.getYRot());
                     this.yHeadRot = livingEntity.yHeadRot;
                     this.yRotO = livingEntity.yHeadRot;
                     final float radius = 1F;
                     final float angle = (UPMath.STARTING_ANGLE * livingEntity.yBodyRot);
                     final double extraX = radius * Mth.sin(Mth.PI + angle);
                     final double extraZ = radius * Mth.cos(angle);
                     this.setPos(mount.getX() + extraX, Math.max(mount.getY() + mount.getEyeHeight() * 0.25F, mount.getY()), mount.getZ() + extraZ);
                     if (!mount.isAlive() || mount instanceof Player && ((Player) mount).isCreative()) {
                         this.removeVehicle();
                     }
                     if (!this.level().isClientSide) {
                         if (latchTime % 20 == 0 && this.isAlive()) {
                             if (mount.hurt(this.damageSources().mobAttack(this), 5.0F)) {
                                 this.gameEvent(GameEvent.EAT);
                             }
                         }
                         if (latchTime > 81) {
                             latchTime = -20 - random.nextInt(20);
                             this.removeVehicle();
                             UPMessages.sendMSGToAll(new BalaurMountMessage(this.getId(), mount.getId()));
                         }
                     }
                 }

             }
         }

     }

     @Override
     public boolean canRiderInteract() {
         return true;
     }

     @Override
     public boolean doHurtTarget(Entity target) {
         boolean shouldHurt;
         float damage = (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE);
         float knockback = (float) this.getAttributeValue(Attributes.ATTACK_KNOCKBACK);
         if (shouldHurt = target.hurt(this.damageSources().mobAttack(this), damage)) {
             if (knockback > 0.0f && target instanceof LivingEntity) {
                 ((LivingEntity) target).knockback(knockback * 0.5f, Mth.sin(this.getYRot() * ((float) Math.PI / 180)), -Mth.cos(this.getYRot() * ((float) Math.PI / 180)));
                 this.setDeltaMovement(this.getDeltaMovement().multiply(0.6, 1.0, 0.6));
             }
             this.doEnchantDamageEffects(this, target);
             this.setLastHurtMob(target);
         }
         if (shouldHurt && target instanceof LivingEntity livingEntity) {
             this.playSound(UPSounds.VELOCIRAPTOR_HURT.get(), 0.1F, 1.0F);
         }
         return shouldHurt;
     }

     @Override
     public void tick() {
         super.tick();
         if (this.isOrderedToSit() && sitProgress < 5F) {
             sitProgress++;
         }
         if (!this.isOrderedToSit() && sitProgress > 0F) {
             sitProgress--;
         }

         this.setOrderedToSit(this.getCommand() == 2 && !this.isVehicle());

         if (isPassenger()) {
             if (latchTime < 0)
                 latchTime = 0;

             latchTime++;
         } else {
             latchTime = 0;
         }
     }

     @Override
     public boolean isAlliedTo(Entity pEntity) {
         return pEntity.is(this);
     }

     protected SoundEvent getAmbientSound() {
         return UPSounds.BALAUR_IDLE.get();
     }

     protected SoundEvent getHurtSound(@NotNull DamageSource damageSourceIn) {
         return UPSounds.BALAUR_HURT.get();
     }

     protected SoundEvent getDeathSound() {
         return UPSounds.BALAUR_DEATH.get();
     }

     protected void playStepSound(BlockPos p_28301_, BlockState p_28302_) {
         this.playSound(SoundEvents.CHICKEN_STEP, 0.15F, 1.0F);
     }

     @Override
     public float getSoundVolume() {
         if(this.isBaby()){
             return 0.65F;
         }
         else{
             return 0.8F;
         }
     }

     @Override
     public void setCustomName(@Nullable Component pName) {
         super.setCustomName(pName);
     }

     @Override
     public boolean shouldFollow() {
         return this.getCommand() == 1;
     }

     @Override
     public void addAdditionalSaveData(CompoundTag compound) {
         super.addAdditionalSaveData(compound);
         compound.putInt("Command", this.getCommand());
     }

     @Override
     public void readAdditionalSaveData(CompoundTag compound) {
         super.readAdditionalSaveData(compound);
         this.setCommand(compound.getInt("Command"));
     }

     @Override
     protected void defineSynchedData() {
         super.defineSynchedData();
         this.entityData.define(IDLE_1_AC, false);
         this.entityData.define(IDLE_2_AC, false);
         this.entityData.define(IDLE_3_AC, false);
         this.entityData.define(IDLE_4_AC, false);
         this.entityData.define(IDLE_5_AC, false);
         this.entityData.define(COMMAND, 0);
         this.entityData.define(ANIMATION_STATE, 0);
         this.entityData.define(COMBAT_STATE, 0);
         this.entityData.define(ENTITY_STATE, 0);
     }

     public int getCommand() {
         return this.entityData.get(COMMAND);
     }

     public void setCommand(int command) {
         this.entityData.set(COMMAND, command);
     }

     public int getAnimationState() {
         return this.entityData.get(ANIMATION_STATE);
     }

     public void setAnimationState(int anim) {
         this.entityData.set(ANIMATION_STATE, anim);
     }

     public boolean isBittenByMosquito(Entity entity) {
         for (Entity e : entity.getPassengers()) {
             if (e instanceof BalaurEntity) {
                 return true;
             }
         }
         return false;
     }

     @Nullable
     @Override
     public AgeableMob getBreedOffspring(@NotNull ServerLevel serverLevel, @NotNull AgeableMob ageableMob) {
         return UPEntities.BALAUR.get().create(serverLevel);
     }

     protected boolean isFlapping() {
         return this.flyDist > this.nextFlap;
     }

     protected void onFlap() {
         this.nextFlap = this.flyDist + this.flapSpeed / 2.0F;
     }

     public boolean causeFallDamage(float fallDistance, float multiplier, DamageSource source) {
         return false;
     }

     @Override
     public void registerControllers(final AnimatableManager.ControllerRegistrar controllers) {
         AnimationController<BalaurEntity> controller = new AnimationController<>(this, "controller", 5, this::predicate);
         controllers.add(controller);

         AnimationController<BalaurEntity> blend = new AnimationController<>(this, "blend", 5, this::predicate)
                 .triggerableAnim("scratch_1", BALAUR_SCRATCH_1)
                 .triggerableAnim("scratch_2", BALAUR_SCRATCH_2)
                 .triggerableAnim("bite_1", BALAUR_BITE_1)
                 .triggerableAnim("bite_2", BALAUR_BITE_2)
                 .triggerableAnim("claw", BALAUR_CLAW)
             ;
         controllers.add(blend);

         AnimationController<BalaurEntity> flap = new AnimationController<>(this, "flapController", 5, this::flapPredicate);
         controllers.add(flap);
     }

     protected <E extends BalaurEntity> PlayState predicate(final software.bernie.geckolib.core.animation.AnimationState<E> event) {
         if (this.isFromBook()) {
             event.setAndContinue(BALAUR_IDLE);
         }

         if (this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6 && !this.isInSittingPose() && !this.isInWater()) {
             if (this.isSprinting()) {
                 event.setAndContinue(BALAUR_RUN);
                 event.getController().setAnimationSpeed(1.0D);
                 return PlayState.CONTINUE;
             } else if (event.isMoving()) {
                 event.setAndContinue(BALAUR_WALK);
                 event.getController().setAnimationSpeed(1.0D);
                 return PlayState.CONTINUE;
             }
         }

         if (this.isInWater()) {
             event.setAndContinue(BALAUR_SWIM);
             event.getController().setAnimationSpeed(1.0F);
             return PlayState.CONTINUE;
         }

         if (this.isInSittingPose() && !this.isInWater() && !this.isSwimming()) {
             return event.setAndContinue(BALAUR_SIT);
         }

         if (this.isPassenger()) {
             event.setAndContinue(BALAUR_POUNCE_HOLD);
             event.getController().setAnimationSpeed(1.0F);
             return PlayState.CONTINUE;
         }

         if (!this.isInSittingPose()) {
             event.setAndContinue(BALAUR_SLEEP);
             event.getController().setAnimationSpeed(1.0F);
             return PlayState.CONTINUE;
         }

         if (!this.isInWater()) {
             if (getBooleanState(IDLE_1_AC)) {
                 if (this.isStillEnough()) {
                     triggerAnim("blend", "scratch_1");
                     return event.setAndContinue(BALAUR_IDLE);
                 } else {
                     triggerAnim("blend", "scratch_1");
                     return PlayState.CONTINUE;
                 }
             }
             if (getBooleanState(IDLE_2_AC)) {
                 if (this.isStillEnough()) {
                     triggerAnim("blend", "scratch_2");
                     return event.setAndContinue(BALAUR_IDLE);
                 } else {
                     triggerAnim("blend", "scratch_2");
                     return PlayState.CONTINUE;
                 }
             }
             if (getBooleanState(IDLE_3_AC)) {
                 return event.setAndContinue(BALAUR_HISS);
             }
             if (getBooleanState(IDLE_4_AC)) {
                 return event.setAndContinue(BALAUR_PREEN_1);
             }
             if (getBooleanState(IDLE_5_AC)) {
                 return event.setAndContinue(BALAUR_PREEN_2);
             }
             return event.setAndContinue(BALAUR_IDLE);
         }
         return PlayState.CONTINUE;
     }

     protected <E extends BalaurEntity> PlayState flapPredicate(final AnimationState<E> event) {
         if (!this.onGround() && !this.isInWater()) {
             event.getController().setAnimation(BALAUR_FALL);
             event.getController().setAnimationSpeed(1.0D);
             return PlayState.CONTINUE;
         }
         event.getController().forceAnimationReset();
         return PlayState.STOP;
     }
 }
