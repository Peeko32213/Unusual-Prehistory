// package com.peeko32213.unusualprehistory.common.entity;

// import com.peeko32213.unusualprehistory.common.entity.msc.util.dino.EntityTameableBaseDinosaurAnimal;
// import com.peeko32213.unusualprehistory.common.entity.msc.util.goal.BabyPanicGoal;
// import com.peeko32213.unusualprehistory.common.entity.msc.util.goal.CustomRandomStrollGoal;
// import com.peeko32213.unusualprehistory.common.entity.msc.util.helper.HitboxHelper;
// import com.peeko32213.unusualprehistory.common.entity.msc.util.interfaces.CustomFollower;
// import com.peeko32213.unusualprehistory.common.message.BalaurMountMessage;
// import com.peeko32213.unusualprehistory.core.registry.*;
// import com.peeko32213.unusualprehistory.core.registry.util.UPMath;
// import net.minecraft.core.BlockPos;
// import net.minecraft.nbt.CompoundTag;
// import net.minecraft.network.chat.Component;
// import net.minecraft.network.syncher.EntityDataAccessor;
// import net.minecraft.network.syncher.EntityDataSerializers;
// import net.minecraft.network.syncher.SynchedEntityData;
// import net.minecraft.server.level.ServerLevel;
// import net.minecraft.sounds.SoundEvent;
// import net.minecraft.sounds.SoundEvents;
// import net.minecraft.tags.TagKey;
// import net.minecraft.util.Mth;
// import net.minecraft.world.InteractionHand;
// import net.minecraft.world.InteractionResult;
// import net.minecraft.world.damagesource.DamageSource;
// import net.minecraft.world.entity.*;
// import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
// import net.minecraft.world.entity.ai.attributes.Attributes;
// import net.minecraft.world.entity.ai.goal.*;
// import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
// import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
// import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
// import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
// import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
// import net.minecraft.world.entity.player.Player;
// import net.minecraft.world.item.ItemStack;
// import net.minecraft.world.level.Level;
// import net.minecraft.world.level.block.state.BlockState;
// import net.minecraft.world.level.gameevent.GameEvent;
// import net.minecraft.world.level.pathfinder.Node;
// import net.minecraft.world.level.pathfinder.Path;
// import net.minecraft.world.phys.Vec2;
// import net.minecraft.world.phys.Vec3;
// import software.bernie.geckolib.core.animation.AnimatableManager;
// import software.bernie.geckolib.core.animation.AnimationController;
// import software.bernie.geckolib.core.animation.RawAnimation;
// import software.bernie.geckolib.core.object.PlayState;

// import javax.annotation.Nullable;
// import java.util.EnumSet;

// public class EntityBalaur extends EntityTameableBaseDinosaurAnimal implements CustomFollower {
//     private static final EntityDataAccessor<Integer> SCALE = SynchedEntityData.defineId(EntityBalaur.class, EntityDataSerializers.INT);
//     private static final EntityDataAccessor<Integer> COMMAND = SynchedEntityData.defineId(EntityBalaur.class, EntityDataSerializers.INT);
//     private static final EntityDataAccessor<Integer> COMBAT_STATE = SynchedEntityData.defineId(EntityBalaur.class, EntityDataSerializers.INT);
//     private static final EntityDataAccessor<Integer> ENTITY_STATE = SynchedEntityData.defineId(EntityBalaur.class, EntityDataSerializers.INT);
//     private static final EntityDataAccessor<Integer> ANIMATION_STATE = SynchedEntityData.defineId(EntityBalaur.class, EntityDataSerializers.INT);

//     private static final RawAnimation BALAUR_IDLE_1 = RawAnimation.begin().thenLoop("animation.balaur.idle1");
//     private static final RawAnimation BALAUR_IDLE_2 = RawAnimation.begin().thenLoop("animation.balaur.idle2");
//     private static final RawAnimation BALAUR_SIT = RawAnimation.begin().thenLoop("animation.balaur.sit");
//     private static final RawAnimation BALAUR_SLEEP = RawAnimation.begin().thenLoop("animation.balaur.sleep");
//     private static final RawAnimation BALAUR_WALK = RawAnimation.begin().thenLoop("animation.balaur.walk");
//     private static final RawAnimation BALAUR_RUN = RawAnimation.begin().thenLoop("animation.balaur.run");
//     private static final RawAnimation BALAUR_SWIM = RawAnimation.begin().thenLoop("animation.balaur.swim");
//     private static final RawAnimation BALAUR_FALL = RawAnimation.begin().thenLoop("animation.balaur.fall");
//     private static final RawAnimation BALAUR_SCRATCH_1 = RawAnimation.begin().thenLoop("animation.balaur.scratch1");
//     private static final RawAnimation BALAUR_SCRATCH_2 = RawAnimation.begin().thenLoop("animation.balaur.scratch2");
//     private static final RawAnimation BALAUR_HISS = RawAnimation.begin().thenLoop("animation.balaur.hiss");
//     private static final RawAnimation BALAUR_PREEN_1 = RawAnimation.begin().thenLoop("animation.balaur.preen1");
//     private static final RawAnimation BALAUR_PREEN_2 = RawAnimation.begin().thenLoop("animation.balaur.preen2");
//     private static final RawAnimation BALAUR_BITE_1 = RawAnimation.begin().thenLoop("animation.balaur.bite1");
//     private static final RawAnimation BALAUR_BITE_2 = RawAnimation.begin().thenLoop("animation.balaur.bite2");
//     private static final RawAnimation BALAUR_CLAW = RawAnimation.begin().thenLoop("animation.balaur.claw");
//     private static final RawAnimation BALAUR_POUNCE_START = RawAnimation.begin().thenLoop("animation.balaur.pounce_start");
//     private static final RawAnimation BALAUR_POUNCE_HOLD = RawAnimation.begin().thenLoop("animation.balaur.pounce_hold");

//     protected boolean pushingState = false;
//     public float sitProgress;
//     private int latchTime = 0;

//     public EntityBalaur(EntityType<? extends EntityTameableBaseDinosaurAnimal> entityType, Level level) {
//         super(entityType, level);
//         ((GroundPathNavigation) this.getNavigation()).setCanOpenDoors(true);
//         this.setMaxUpStep(1.0F);
//         this.refreshDimensions();
//     }


//     public static AttributeSupplier.Builder createAttributes() {
//         return Mob.createMobAttributes()
//                 .add(Attributes.MAX_HEALTH, 15.0D)
//                 .add(Attributes.ARMOR, 0.0D)
//                 .add(Attributes.MOVEMENT_SPEED, 0.24D)
//                 .add(Attributes.ATTACK_DAMAGE, 8.0D)
//                 .add(Attributes.KNOCKBACK_RESISTANCE, 0.0D);
//     }

//     protected void registerGoals() {
//         super.registerGoals();
//         //this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 2D, false));
//         //this.goalSelector.addGoal(2, new EntityBalaur.IMeleeAttackGoal());
//         this.goalSelector.addGoal(3, new BabyPanicGoal(this, 2.0D));
//         this.goalSelector.addGoal(0, new FloatGoal(this));
//         this.goalSelector.addGoal(1, new EntityBalaur.BalaurMeleeAttackGoal(this, 1.5F, true));
//         this.goalSelector.addGoal(3, new CustomRandomStrollGoal(this, 30, 1.0D, 100, 34) {
//                     @Override
//                     public boolean canUse() {
//                         if (this.mob.isVehicle()) {
//                             return false;
//                         } else {
//                             if (!this.forceTrigger) {
//                                 if (this.mob.getNoActionTime() >= 100) {
//                                     return false;
//                                 }
//                                 if (((EntityBalaur) this.mob).isHungry()) {
//                                     if (this.mob.getRandom().nextInt(60) != 0) {
//                                         return false;
//                                     }
//                                 } else {
//                                     if (this.mob.getRandom().nextInt(30) != 0) {
//                                         return false;
//                                     }
//                                 }
//                             }

//                             Vec3 vec3d = this.getPosition();
//                             if (vec3d == null) {
//                                 return false;
//                             } else {
//                                 this.wantedX = vec3d.x;
//                                 this.wantedY = vec3d.y;
//                                 this.wantedZ = vec3d.z;
//                                 this.forceTrigger = false;
//                                 return true;
//                             }
//                         }
//                     }
//                 }

//         );
//         this.goalSelector.addGoal(5, new FollowParentGoal(this, 1.1D));
//         this.goalSelector.addGoal(3, new OpenDoorGoal(this, true));
//         this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
//         this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
//         this.goalSelector.addGoal(0, new SitWhenOrderedToGoal(this));
//         this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, false));
//         this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)));
//         this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
//         this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
//     }

//     @Override
//     public void customServerAiStep() {
//         if (this.getMoveControl().hasWanted()) {
//             this.setSprinting(this.getMoveControl().getSpeedModifier() >= 1.2D);
//         } else {
//             this.setSprinting(false);
//         }
//         super.customServerAiStep();
//     }

//     @Override
//     public InteractionResult mobInteract(Player player, InteractionHand hand) {
//         ItemStack itemstack = player.getItemInHand(hand);
//         InteractionResult interactionresult = itemstack.interactLivingEntity(player, this, hand);
//         if (
//                 player.getItemBySlot(EquipmentSlot.HEAD).is(UPTags.IS_SHINY_HEAD) &&
//                         player.getItemBySlot(EquipmentSlot.CHEST).is(UPTags.IS_SHINY_CHEST) &&
//                         player.getItemBySlot(EquipmentSlot.LEGS).is(UPTags.IS_SHINY_LEGS) &&
//                         player.getItemBySlot(EquipmentSlot.FEET).is(UPTags.IS_SHINY_BOOTS)

//         ) {
//             if (itemstack.is(UPItems.RAW_AUSTRO.get())) {
//                 if (!isTame()) {
//                     this.usePlayerItem(player, hand, itemstack);
//                     if (getRandom().nextInt(3) == 0) {
//                         this.tame(player);
//                         this.level().broadcastEntityEvent(this, (byte) 7);
//                         itemstack.shrink(1);
//                     } else {
//                         this.level().broadcastEntityEvent(this, (byte) 6);
//                     }
//                 }
//             }
//         }
//         if (isTame() && isOwnedBy(player)) {
//             if (this.isFood(itemstack) && this.getHealth() < this.getMaxHealth()) {
//                 if (!player.getAbilities().instabuild) {
//                     itemstack.shrink(1);
//                 }
//                 if (!this.level().isClientSide) {
//                     this.heal((float) itemstack.getFoodProperties(this).getNutrition());
//                 }
//                 this.gameEvent(GameEvent.EAT, this);
//                 return InteractionResult.SUCCESS;
//             } else {
//                 this.setCommand((this.getCommand() + 1) % 3);

//                 if (this.getCommand() == 3) {
//                     this.setCommand(0);
//                 }
//                 player.displayClientMessage(Component.translatable("entity.unusualprehistory.all.command_" + this.getCommand(), this.getName()), true);
//                 boolean sit = this.getCommand() == 2;
//                 if (sit) {
//                     this.setOrderedToSit(true);
//                     return InteractionResult.SUCCESS;
//                 } else {
//                     this.setOrderedToSit(false);
//                     return InteractionResult.SUCCESS;
//                 }
//             }
//         }
//         return InteractionResult.SUCCESS;

//     }

//     public boolean hurt(DamageSource source, float amount) {
//         if (source.getEntity() != null && this.getRootVehicle() == source.getEntity().getRootVehicle()) {
//             return super.hurt(source, amount * 0.333F);
//         }
//         return super.hurt(source, amount);
//     }

//     public void rideTick() {
//         final Entity entity = this.getVehicle();
//         if (this.isPassenger() && !entity.isAlive()) {
//             this.stopRiding();
//         } else {
//             this.setDeltaMovement(0, 0, 0);
//             this.tick();
//             if (this.isPassenger()) {
//                 final Entity mount = this.getVehicle();
//                 if (mount instanceof final LivingEntity livingEntity) {
//                     this.yBodyRot = livingEntity.yBodyRot;
//                     this.setYRot(livingEntity.getYRot());
//                     this.yHeadRot = livingEntity.yHeadRot;
//                     this.yRotO = livingEntity.yHeadRot;
//                     final float radius = 1F;
//                     final float angle = (UPMath.STARTING_ANGLE * livingEntity.yBodyRot);
//                     final double extraX = radius * Mth.sin(Mth.PI + angle);
//                     final double extraZ = radius * Mth.cos(angle);
//                     this.setPos(mount.getX() + extraX, Math.max(mount.getY() + mount.getEyeHeight() * 0.25F, mount.getY()), mount.getZ() + extraZ);
//                     if (!mount.isAlive() || mount instanceof Player && ((Player) mount).isCreative()) {
//                         this.removeVehicle();
//                     }
//                     if (!this.level().isClientSide) {
//                         if (latchTime % 20 == 0 && this.isAlive()) {
//                             if (mount.hurt(this.damageSources().mobAttack(this), 5.0F)) {
//                                 this.gameEvent(GameEvent.EAT);
//                             }
//                         }
//                         if (latchTime > 81) {
//                             latchTime = -20 - random.nextInt(20);
//                             this.removeVehicle();
//                             UPMessages.sendMSGToAll(new BalaurMountMessage(this.getId(), mount.getId()));
//                         }
//                     }
//                 }

//             }
//         }

//     }

//     @Override
//     public boolean canRiderInteract() {
//         return true;
//     }


//     @Override
//     public boolean doHurtTarget(Entity target) {
//         boolean shouldHurt;
//         float damage = (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE);
//         float knockback = (float) this.getAttributeValue(Attributes.ATTACK_KNOCKBACK);
//         if (shouldHurt = target.hurt(this.damageSources().mobAttack(this), damage)) {
//             if (knockback > 0.0f && target instanceof LivingEntity) {
//                 ((LivingEntity) target).knockback(knockback * 0.5f, Mth.sin(this.getYRot() * ((float) Math.PI / 180)), -Mth.cos(this.getYRot() * ((float) Math.PI / 180)));
//                 this.setDeltaMovement(this.getDeltaMovement().multiply(0.6, 1.0, 0.6));
//             }
//             this.doEnchantDamageEffects(this, target);
//             this.setLastHurtMob(target);
//         }
//         if (shouldHurt && target instanceof LivingEntity livingEntity) {
//             this.playSound(UPSounds.RAPTOR_HURT.get(), 0.1F, 1.0F);
//         }
//         return shouldHurt;
//     }

//     @Override
//     public void tick() {
//         super.tick();
//         if (this.isOrderedToSit() && sitProgress < 5F) {
//             sitProgress++;
//         }
//         if (!this.isOrderedToSit() && sitProgress > 0F) {
//             sitProgress--;
//         }
//         if (this.getCommand() == 2 && !this.isVehicle()) {
//             this.setOrderedToSit(true);
//         } else {
//             this.setOrderedToSit(false);
//         }
//         if (isPassenger()) {
//             if (latchTime < 0)
//                 latchTime = 0;

//             latchTime++;
//         } else {
//             latchTime = 0;
//         }
//     }

//     @Override
//     public boolean isAlliedTo(Entity pEntity) {
//         return pEntity.is(this);
//     }

//     protected SoundEvent getAmbientSound() {
//         return UPSounds.RAPTOR_IDLE.get();
//     }

//     protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
//         return UPSounds.RAPTOR_HURT.get();
//     }

//     protected SoundEvent getDeathSound() {
//         return UPSounds.RAPTOR_DEATH.get();
//     }

//     protected void playStepSound(BlockPos p_28301_, BlockState p_28302_) {
//         this.playSound(SoundEvents.CHICKEN_STEP, 0.15F, 1.0F);
//     }

//     @Override
//     protected SoundEvent getAttackSound() {
//         return UPSounds.RAPTOR_ATTACK.get();
//     }

//     @Override
//     protected int getKillHealAmount() {
//         return 5;
//     }

//     @Override
//     protected boolean canGetHungry() {
//         return true;
//     }

//     @Override
//     protected boolean hasTargets() {
//         return true;
//     }

//     @Override
//     protected boolean hasAvoidEntity() {
//         return true;
//     }

//     @Override
//     protected boolean hasCustomNavigation() {
//         return false;
//     }

//     @Override
//     protected boolean hasMakeStuckInBlock() {
//         return false;
//     }

//     @Override
//     protected boolean customMakeStuckInBlockCheck(BlockState blockState) {
//         return false;
//     }

//     @Override
//     protected TagKey<EntityType<?>> getTargetTag() {
//         return UPTags.RAPTOR_TARGETS;
//     }

//     @Override
//     public void setCustomName(@org.jetbrains.annotations.Nullable Component pName) {
//         super.setCustomName(pName);
//     }

//     private void attack(LivingEntity entity) {
//         entity.hurt(this.damageSources().mobAttack(this), 5.0F);
//     }

//     @Override
//     public boolean shouldFollow() {
//         return this.getCommand() == 1;
//     }

//     @Override
//     protected void performAttack() {

//     }

//     class IMeleeAttackGoal extends MeleeAttackGoal {
//         public IMeleeAttackGoal() {
//             super(EntityBalaur.this, 1.6D, true);
//         }

//         protected double getAttackReachSqr(LivingEntity p_25556_) {
//             return (double) (this.mob.getBbWidth() * 2.0F * this.mob.getBbWidth() * 0.66F + p_25556_.getBbWidth());
//         }

//         @Override
//         protected void checkAndPerformAttack(LivingEntity enemy, double distToEnemySqr) {
//             double d0 = this.getAttackReachSqr(enemy);
//             if (distToEnemySqr <= d0 && this.getTicksUntilNextAttack() <= 0) {
//                 this.resetAttackCooldown();
//                 ((EntityBalaur) this.mob).setHungry(false);
//                 ((EntityBalaur) this.mob).attack(enemy);
//                 ((EntityBalaur) this.mob).setTimeTillHungry(mob.getRandom().nextInt(300) + 300);
//             }
//         }

//     }

//     @Override
//     public void addAdditionalSaveData(CompoundTag compound) {
//         super.addAdditionalSaveData(compound);
//         compound.putInt("scale", this.getModelScale());
//         compound.putInt("Command", this.getCommand());
//     }

//     @Override
//     public void readAdditionalSaveData(CompoundTag compound) {
//         super.readAdditionalSaveData(compound);
//         this.setScale(Math.min(compound.getInt("scale"), 0));
//         this.setCommand(compound.getInt("Command"));
//     }

//     @Override
//     protected void defineSynchedData() {
//         super.defineSynchedData();
//         this.entityData.define(SCALE, 0);
//         this.entityData.define(COMMAND, 0);
//         this.entityData.define(ANIMATION_STATE, 0);
//         this.entityData.define(COMBAT_STATE, 0);
//         this.entityData.define(ENTITY_STATE, 0);
//     }

//     public int getCommand() {
//         return this.entityData.get(COMMAND).intValue();
//     }

//     public void setCommand(int command) {
//         this.entityData.set(COMMAND, Integer.valueOf(command));
//     }

//     public int getAnimationState() {

//         return this.entityData.get(ANIMATION_STATE);
//     }

//     public void setAnimationState(int anim) {

//         this.entityData.set(ANIMATION_STATE, anim);
//     }

//     public int getCombatState() {

//         return this.entityData.get(COMBAT_STATE);
//     }

//     public void setCombatState(int anim) {

//         this.entityData.set(COMBAT_STATE, anim);
//     }

//     public int getEntityState() {

//         return this.entityData.get(ENTITY_STATE);
//     }

//     public void setEntityState(int anim) {

//         this.entityData.set(ENTITY_STATE, anim);
//     }


//     public void onSyncedDataUpdated(EntityDataAccessor<?> pKey) {
//         if (SCALE.equals(pKey)) {
//             this.refreshDimensions();
//         }

//         super.onSyncedDataUpdated(pKey);
//     }

//     public EntityDimensions getDimensions(Pose pPose) {
//         return super.getDimensions(pPose).scale(getScale(this.getModelScale()));
//     }

//     private static float getScale(int scale) {
//         switch (scale) {
//             case 1:
//                 return 1.8F;
//             default:
//                 return 0.9F;
//         }
//     }

//     public int getModelScale() {
//         return this.entityData.get(SCALE);
//     }

//     public void setScale(int scale) {
//         this.entityData.set(SCALE, scale);
//     }


//     @Nullable
//     @Override
//     public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
//         return UPEntities.BALAUR.get().create(serverLevel);
//     }

//     static class BalaurMeleeAttackGoal extends Goal {

//         protected final EntityBalaur mob;
//         private final double speedModifier;
//         private final boolean followingTargetEvenIfNotSeen;
//         private Path path;
//         private double pathedTargetX;
//         private double pathedTargetY;
//         private double pathedTargetZ;
//         private int ticksUntilNextPathRecalculation;
//         private int ticksUntilNextAttack;
//         private long lastCanUseCheck;
//         private int failedPathFindingPenalty = 0;
//         private boolean canPenalize = false;
//         private int animTime = 0;


//         public BalaurMeleeAttackGoal(EntityBalaur p_i1636_1_, double p_i1636_2_, boolean p_i1636_4_) {
//             this.mob = p_i1636_1_;
//             this.speedModifier = p_i1636_2_;
//             this.followingTargetEvenIfNotSeen = p_i1636_4_;
//             this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
//         }

//         public boolean canUse() {
//             long i = this.mob.level().getGameTime();

//             if (i - this.lastCanUseCheck < 20L) {
//                 return false;
//             } else {
//                 this.lastCanUseCheck = i;
//                 LivingEntity livingentity = this.mob.getTarget();
//                 if (livingentity == null) {
//                     return false;
//                 } else if (!livingentity.isAlive()) {
//                     return false;
//                 } else {
//                     if (canPenalize) {
//                         if (--this.ticksUntilNextPathRecalculation <= 0) {
//                             this.path = this.mob.getNavigation().createPath(livingentity, 0);
//                             this.ticksUntilNextPathRecalculation = 4 + this.mob.getRandom().nextInt(7);
//                             return this.path != null;
//                         } else {
//                             return true;
//                         }
//                     }
//                     this.path = this.mob.getNavigation().createPath(livingentity, 0);
//                     if (this.path != null) {
//                         return true;
//                     } else {
//                         return this.getAttackReachSqr(livingentity) >= this.mob.distanceToSqr(livingentity.getX(), livingentity.getY(), livingentity.getZ());
//                     }
//                 }
//             }


//         }

//         public boolean canContinueToUse() {

//             LivingEntity livingentity = this.mob.getTarget();

//             if (livingentity == null) {
//                 return false;
//             } else if (!livingentity.isAlive()) {
//                 return false;
//             } else if (!this.followingTargetEvenIfNotSeen) {
//                 return !this.mob.getNavigation().isDone();
//             } else if (!this.mob.isWithinRestriction(livingentity.blockPosition())) {
//                 return false;
//             } else {
//                 return !(livingentity instanceof Player) || !livingentity.isSpectator() && !((Player) livingentity).isCreative();
//             }


//         }

//         public void start() {
//             this.mob.getNavigation().moveTo(this.path, this.speedModifier);
//             this.ticksUntilNextPathRecalculation = 0;
//             this.ticksUntilNextAttack = 0;
//             this.animTime = 0;
//             this.mob.setAnimationState(0);
//         }

//         public void stop() {
//             LivingEntity livingentity = this.mob.getTarget();
//             if (!EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(livingentity)) {
//                 //this.mob.setTarget((LivingEntity) null);
//             }
//             this.mob.setAnimationState(0);

//         }

//         public void tick() {


//             LivingEntity target = this.mob.getTarget();
//             double distance = this.mob.distanceToSqr(target.getX(), target.getY(), target.getZ());
//             double reach = this.getAttackReachSqr(target);
//             int animState = this.mob.getAnimationState();
//             Vec3 aim = this.mob.getLookAngle();
//             Vec2 aim2d = new Vec2((float) (aim.x / (1 - Math.abs(aim.y))), (float) (aim.z / (1 - Math.abs(aim.y))));
//             animState = 25;

//             switch (animState) {
//                 case 21 -> tickClawAttack();
//                 case 22, 23 -> tickScratchAttack();
//                 case 24 -> tickBiteAttack();
//                 case 25 -> tickLatchAttack();

//                 default -> {
//                     this.ticksUntilNextPathRecalculation = Math.max(this.ticksUntilNextPathRecalculation - 1, 0);
//                     this.ticksUntilNextAttack = Math.max(this.ticksUntilNextPathRecalculation - 1, 0);
//                     this.mob.getLookControl().setLookAt(target, 30.0F, 30.0F);
//                     this.doMovement(target, distance);
//                     this.checkForCloseRangeAttack(distance, reach);
//                 }
//             }
//         }

//         protected void doMovement(LivingEntity livingentity, Double d0) {


//             this.ticksUntilNextPathRecalculation = Math.max(this.ticksUntilNextPathRecalculation - 1, 0);


//             if ((this.followingTargetEvenIfNotSeen || this.mob.getSensing().hasLineOfSight(livingentity)) && this.ticksUntilNextPathRecalculation <= 0 && (this.pathedTargetX == 0.0D && this.pathedTargetY == 0.0D && this.pathedTargetZ == 0.0D || livingentity.distanceToSqr(this.pathedTargetX, this.pathedTargetY, this.pathedTargetZ) >= 1.0D || this.mob.getRandom().nextFloat() < 0.05F)) {
//                 this.pathedTargetX = livingentity.getX();
//                 this.pathedTargetY = livingentity.getY();
//                 this.pathedTargetZ = livingentity.getZ();
//                 this.ticksUntilNextPathRecalculation = 4 + this.mob.getRandom().nextInt(7);
//                 if (this.canPenalize) {
//                     this.ticksUntilNextPathRecalculation += failedPathFindingPenalty;
//                     if (this.mob.getNavigation().getPath() != null) {
//                         Node finalPathPoint = this.mob.getNavigation().getPath().getEndNode();
//                         if (finalPathPoint != null && livingentity.distanceToSqr(finalPathPoint.x, finalPathPoint.y, finalPathPoint.z) < 1)
//                             failedPathFindingPenalty = 0;
//                         else
//                             failedPathFindingPenalty += 10;
//                     } else {
//                         failedPathFindingPenalty += 10;
//                     }
//                 }
//                 if (d0 > 1024.0D) {
//                     this.ticksUntilNextPathRecalculation += 10;
//                 } else if (d0 > 256.0D) {
//                     this.ticksUntilNextPathRecalculation += 5;
//                 }

//                 if (!this.mob.getNavigation().moveTo(livingentity, this.speedModifier)) {
//                     this.ticksUntilNextPathRecalculation += 15;
//                 }
//             }

//         }


//         protected void checkForCloseRangeAttack(double distance, double reach) {
//             if (distance <= reach && this.ticksUntilNextAttack <= 0) {
//                 int r = this.mob.getRandom().nextInt(2048);
//                 if (r <= 1200) {
//                     this.mob.setAnimationState(21);
//                 }
//             }
//         }


//         protected boolean getRangeCheck() {

//             return
//                     this.mob.distanceToSqr(this.mob.getTarget().getX(), this.mob.getTarget().getY(), this.mob.getTarget().getZ())
//                             <=
//                             1.8F * this.getAttackReachSqr(this.mob.getTarget());
//         }


//         protected void tickScratchAttack() {
//             animTime++;
//             if (animTime == 4) {
//                 this.mob.lookAt(this.mob.getTarget(), 100000, 100000);
//                 preformScratchAttack();
//             }
//             if (animTime >= 8) {
//                 animTime = 0;
//                 if (this.getRangeCheck()) {
//                     this.mob.setAnimationState(22);
//                 } else {
//                     this.mob.setAnimationState(0);
//                     this.resetAttackCooldown();
//                     this.ticksUntilNextPathRecalculation = 0;
//                 }
//             }
//         }


//         protected void tickClawAttack() {
//             animTime++;
//             if (animTime == 4) {
//                 this.mob.lookAt(this.mob.getTarget(), 100000, 100000);
//                 preformClawAttack();
//             }
//             if (animTime >= 8) {
//                 animTime = 0;
//                 if (this.getRangeCheck()) {
//                     this.mob.setAnimationState(22);
//                 } else {
//                     this.mob.setAnimationState(0);
//                     this.resetAttackCooldown();
//                     this.ticksUntilNextPathRecalculation = 0;
//                 }
//             }
//         }

//         protected void tickBiteAttack() {
//             animTime++;
//             if (animTime == 4) {
//                 this.mob.lookAt(this.mob.getTarget(), 100000, 100000);
//                 preformBiteAttack();
//             }
//             if (animTime >= 8) {
//                 animTime = 0;
//                 if (this.getRangeCheck()) {
//                     this.mob.setAnimationState(22);
//                 } else {
//                     this.mob.setAnimationState(0);
//                     this.resetAttackCooldown();
//                     this.ticksUntilNextPathRecalculation = 0;
//                 }
//             }
//         }

//         protected void tickLatchAttack() {
//             this.mob.lookAt(this.mob.getTarget(), 100000, 100000);
//             this.mob.yBodyRot = this.mob.yHeadRot;
//             animTime++;

//             if (animTime == 16) {
//                 preformLatchAttack();
//                 this.mob.getNavigation().stop();
//             }

//             if (animTime >= 24) {
//                 animTime = 0;

//                 this.mob.setAnimationState(0);
//                 this.resetAttackCooldown();
//                 this.ticksUntilNextPathRecalculation = 0;

//             }

//         }

//         protected void preformClawAttack() {
//             Vec3 pos = mob.position();
//             HitboxHelper.LargeAttack(this.mob.damageSources().mobAttack(mob), 8.0f, 0.2f, mob, pos, 1.5F, -Math.PI / 2, Math.PI / 2, -1.0f, 3.0f);
//         }

//         protected void preformScratchAttack() {
//             Vec3 pos = mob.position();
//             HitboxHelper.LargeAttack(this.mob.damageSources().mobAttack(mob), 8.0f, 0.2f, mob, pos, 1.5F, -Math.PI / 2, Math.PI / 2, -1.0f, 3.0f);
//         }

//         protected void preformBiteAttack() {
//             Vec3 pos = mob.position();
//             HitboxHelper.LargeAttack(this.mob.damageSources().mobAttack(mob), 3.0f, 0.2f, mob, pos, 1.5F, -Math.PI / 2, Math.PI / 2, -1.0f, 3.0f);
//         }

//         protected void preformLatchAttack() {
//             if (mob.getTarget() != null) {
//                 this.mob.getMoveControl().setWantedPosition(mob.getTarget().getX(), mob.getTarget().getY(), mob.getTarget().getZ(), 1.0D);
//                 if (mob.getBoundingBox().inflate(3F, 3F, 3F).intersects(mob.getTarget().getBoundingBox()) && !mob.isBittenByMosquito(mob.getTarget()) && mob.latchTime == 0) {
//                     mob.startRiding(mob.getTarget(), true);
//                     if (!mob.level().isClientSide) {
//                         UPMessages.sendMSGToAll(new BalaurMountMessage(mob.getId(), mob.getTarget().getId()));
//                     }
//                 }
//             }
//         }

//         protected void resetAttackCooldown() {
//             this.ticksUntilNextAttack = 0;
//         }

//         protected boolean isTimeToAttack() {
//             return this.ticksUntilNextAttack <= 0;
//         }

//         protected int getTicksUntilNextAttack() {
//             return this.ticksUntilNextAttack;
//         }

//         protected int getAttackInterval() {
//             return 5;
//         }

//         protected double getAttackReachSqr(LivingEntity p_179512_1_) {
//             return (double) (this.mob.getBbWidth() * 2.5F * this.mob.getBbWidth() * 1.8F + p_179512_1_.getBbWidth());
//         }
//     }

//     private boolean isStillEnough() {
//         return this.getDeltaMovement().horizontalDistance() < 0.05;
//     }

//     protected <E extends EntityBalaur> PlayState Controller(final software.bernie.geckolib.core.animation.AnimationState<E> event) {
//         if (this.isFromBook()) {
//             return PlayState.CONTINUE;
//         }

//         int animState = this.getAnimationState();
//         {
//             switch (animState) {

//                 case 21:
//                     event.setAndContinue(BALAUR_CLAW);
//                     break;
//                 case 22:
//                     event.setAndContinue(BALAUR_SCRATCH_1);
//                     break;
//                 case 23:
//                     event.setAndContinue(BALAUR_SCRATCH_2);
//                     break;
//                 case 24:
//                     event.setAndContinue(BALAUR_BITE_1);
//                     break;
//                 case 25:
//                     event.setAndContinue(BALAUR_POUNCE_HOLD);
//                     break;
//                 default:

//                     if (this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6 && !this.isInSittingPose() && !this.isInWater()) {
//                         if (this.isSprinting()) {
//                             event.setAndContinue(BALAUR_RUN);
//                             event.getController().setAnimationSpeed(2.0D);
//                             return PlayState.CONTINUE;
//                         } else if (event.isMoving()) {
//                             event.setAndContinue(BALAUR_WALK);
//                             event.getController().setAnimationSpeed(1.0D);
//                             return PlayState.CONTINUE;
//                         }
//                     }
//                     if (this.isInSittingPose() && !this.isInWater() && !this.isSwimming()) {
//                         return event.setAndContinue(BALAUR_SIT);
//                     }
//                     if (this.isInWater()) {
//                         event.setAndContinue(BALAUR_SWIM);
//                         event.getController().setAnimationSpeed(1.0F);
//                         return PlayState.CONTINUE;
//                     }
//                     if (this.isPassenger()) {
//                         event.setAndContinue(BALAUR_POUNCE_HOLD);
//                         event.getController().setAnimationSpeed(1.0F);
//                         return PlayState.CONTINUE;
//                     }
//                     if (this.isAsleep() && !this.isInSittingPose()) {
//                         event.setAndContinue(BALAUR_SLEEP);
//                         event.getController().setAnimationSpeed(1.0F);
//                         return PlayState.CONTINUE;
//                     } else if (this.isFallFlying()) {
//                         return event.setAndContinue(BALAUR_FALL);
//                     }
//                     if(playingAnimation())
//                     {
//                         return PlayState.CONTINUE;
//                     }

//                     if (isStillEnough() && getRandomAnimationNumber() == 0 && !this.isInSittingPose() && !this.isSwimming()) {
//                         int rand = getRandomAnimationNumber();
//                         if (rand < 15) {
//                             return event.setAndContinue(BALAUR_PREEN_1);
//                         }
//                         if (rand < 66) {
//                             return event.setAndContinue(BALAUR_PREEN_2);
//                         }
//                         if (rand < 77) {
//                             return event.setAndContinue(BALAUR_IDLE_2);
//                         }
//                         event.setAndContinue(BALAUR_IDLE_1);
//                     }
//             }
//             return PlayState.CONTINUE;
//         }
//     }


//     @Override
//     public void registerControllers(final AnimatableManager.ControllerRegistrar controllers) {
//         controllers.add(new AnimationController<>(this, "Normal", 10, this::Controller));
//     }

//     public boolean isBittenByMosquito(Entity entity) {
//         for (Entity e : entity.getPassengers()) {
//             if (e instanceof EntityBalaur) {
//                 return true;
//             }
//         }
//         return false;
//     }

//     @Override
//     public double getTick(Object o) {
//         return tickCount;
//     }

// }
