// package com.peeko32213.unusualprehistory.common.entity;

// import com.peeko32213.unusualprehistory.common.entity.msc.util.dino.EntityTameableBaseDinosaurAnimal;
// import com.peeko32213.unusualprehistory.common.entity.msc.util.goal.CustomRandomStrollGoal;
// import com.peeko32213.unusualprehistory.common.entity.msc.util.goal.NocturnalSleepingGoal;
// import com.peeko32213.unusualprehistory.common.entity.msc.util.goal.TameableFollowOwner;
// import com.peeko32213.unusualprehistory.common.entity.msc.util.interfaces.CustomFollower;
// import com.peeko32213.unusualprehistory.core.registry.UPItems;
// import net.minecraft.core.BlockPos;
// import net.minecraft.nbt.CompoundTag;
// import net.minecraft.network.chat.Component;
// import net.minecraft.network.syncher.EntityDataAccessor;
// import net.minecraft.network.syncher.EntityDataSerializers;
// import net.minecraft.network.syncher.SynchedEntityData;
// import net.minecraft.server.level.ServerLevel;
// import net.minecraft.sounds.SoundEvent;
// import net.minecraft.tags.TagKey;
// import net.minecraft.util.Mth;
// import net.minecraft.world.InteractionHand;
// import net.minecraft.world.InteractionResult;
// import net.minecraft.world.entity.*;
// import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
// import net.minecraft.world.entity.ai.attributes.Attributes;
// import net.minecraft.world.entity.ai.goal.*;
// import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
// import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
// import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
// import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
// import net.minecraft.world.entity.ai.util.LandRandomPos;
// import net.minecraft.world.entity.animal.Animal;
// import net.minecraft.world.entity.animal.Chicken;
// import net.minecraft.world.entity.animal.Rabbit;
// import net.minecraft.world.entity.monster.Monster;
// import net.minecraft.world.entity.player.Player;
// import net.minecraft.world.item.ItemStack;
// import net.minecraft.world.level.Level;
// import net.minecraft.world.level.block.Blocks;
// import net.minecraft.world.level.block.state.BlockState;
// import net.minecraft.world.level.gameevent.GameEvent;
// import net.minecraft.world.phys.Vec3;
// import org.jetbrains.annotations.Nullable;
// import software.bernie.geckolib.core.animation.AnimatableManager;
// import software.bernie.geckolib.core.animation.AnimationController;
// import software.bernie.geckolib.core.animation.RawAnimation;
// import software.bernie.geckolib.core.object.PlayState;

// import java.util.EnumSet;
// import java.util.List;
// //TODO LIST
// // - Sounds need to be done and tied to the howling
// // - Leaping does not work, along with the animations (Leaps like fox and has the animations for it)
// // - Walking animation sometimes play while idling
// //      - Scouters Fix, while working, caused none of the other idles to play
// public class EntityOtarocyon extends EntityTameableBaseDinosaurAnimal implements CustomFollower {

//     private static final RawAnimation OTAROCYON_IDLE = RawAnimation.begin().thenLoop("animation.otarocyon.idle");
//     private static final RawAnimation OTAROCYON_SIT = RawAnimation.begin().thenLoop("animation.otarocyon.sit");
//     private static final RawAnimation OTAROCYON_LOAF = RawAnimation.begin().thenLoop("animation.otarocyon.loaf");
//     private static final RawAnimation OTAROCYON_SLEEP = RawAnimation.begin().thenLoop("animation.otarocyon.sleep");
//     private static final RawAnimation OTAROCYON_WALK = RawAnimation.begin().thenLoop("animation.otarocyon.walk");
//     private static final RawAnimation OTAROCYON_RUN = RawAnimation.begin().thenLoop("animation.otarocyon.run");
//     private static final RawAnimation OTAROCYON_SWIM = RawAnimation.begin().thenLoop("animation.otarocyon.swim");
//     private static final RawAnimation OTAROCYON_YAWN = RawAnimation.begin().thenLoop("animation.otarocyon.yawn");
//     private static final RawAnimation OTAROCYON_DIG = RawAnimation.begin().thenLoop("animation.otarocyon.dig");
//     private static final RawAnimation OTAROCYON_SCREAM = RawAnimation.begin().thenLoop("animation.otarocyon.scream");
//     private static final RawAnimation OTAROCYON_ATTACK = RawAnimation.begin().thenLoop("animation.otarocyon.attack");
//     private static final RawAnimation OTAROCYON_LEAP_START = RawAnimation.begin().thenLoop("animation.otarocyon.leap_start");
//     private static final RawAnimation OTAROCYON_LEAP_HOLD = RawAnimation.begin().thenLoop("animation.otarocyon.leap_hold");

//     private static final EntityDataAccessor<Integer> COMMAND = SynchedEntityData.defineId(EntityOtarocyon.class, EntityDataSerializers.INT);
//     private static final EntityDataAccessor<Integer> SPOOKED = SynchedEntityData.defineId(EntityOtarocyon.class, EntityDataSerializers.INT);
//     private Goal landTargetGoal;

//     public float sitProgress;
//     private int spookMobsTime = 0;
//     float crouchAmount;
//     float crouchAmountO;
//     public static AttributeSupplier.Builder createAttributes() {
//         return Mob.createMobAttributes()
//                 .add(Attributes.MAX_HEALTH, 15.0D)
//                 .add(Attributes.ATTACK_DAMAGE, 3.0D)
//                 .add(Attributes.MOVEMENT_SPEED, 0.2D)
//                 .add(Attributes.ARMOR, 0.0D)
//                 .add(Attributes.ARMOR_TOUGHNESS, 0.0D)
//                 .add(Attributes.KNOCKBACK_RESISTANCE, 0.0D);
//     }

//     public EntityOtarocyon(EntityType<? extends EntityTameableBaseDinosaurAnimal> entityType, Level level) {
//         super(entityType, level);
//     }

//     @Override
//     protected void registerGoals() {
//         this.goalSelector.addGoal(0, new FloatGoal(this));
//         this.goalSelector.addGoal(1, new NocturnalSleepingGoal(this));
//         this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 2D, false));
//         this.goalSelector.addGoal(1, new PanicGoal(this, 1.25D));
//         this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
//         this.goalSelector.addGoal(6, new EntityOtarocyon.FoxPounceGoal());
//         this.goalSelector.addGoal(1, new EntityOtarocyon.FaceplantGoal());
//         this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
//         this.goalSelector.addGoal(0, new SitWhenOrderedToGoal(this));
//         this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)));
//         this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
//         this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
//         this.goalSelector.addGoal(3, new TameableFollowOwner(this, 1.2D, 5.0F, 2.0F, false));
//         this.goalSelector.addGoal(3, new CustomRandomStrollGoal(this, 60, 1.0D, 100, 34));
//         this.landTargetGoal = new NearestAttackableTargetGoal<>(this, Animal.class, 10, false, false, (entity) -> {
//             return entity instanceof Chicken || entity instanceof Rabbit;
//         });
//     }

//     @Override
//     public InteractionResult mobInteract(Player player, InteractionHand hand) {
//         ItemStack itemstack = player.getItemInHand(hand);
//         if (itemstack.is(UPItems.ENCYLOPEDIA.get())) {
//             InteractionResult interactionresult = itemstack.interactLivingEntity(player, this, hand);
//         }
//         if (itemstack.is(UPItems.RAW_AUSTRO.get())) {
//             if (!isTame()) {
//                 this.usePlayerItem(player, hand, itemstack);
//                 if (getRandom().nextInt(3) == 0) {
//                     this.tame(player);
//                     this.level().broadcastEntityEvent(this, (byte) 7);
//                     itemstack.shrink(1);
//                 } else {
//                     this.level().broadcastEntityEvent(this, (byte) 6);
//                 }
//             }
//         }
//         if (isTame() && isOwnedBy(player)) {
//             if (this.isFood(itemstack) && this.getHealth() < this.getMaxHealth()) {
//                 if (!player.getAbilities().instabuild) {
//                     itemstack.shrink(1);
//                 }
//                 if(!this.level().isClientSide) {
//                     this.heal((float) itemstack.getFoodProperties(this).getNutrition());
//                 }
//                 this.gameEvent(GameEvent.EAT, this);
//                 return InteractionResult.SUCCESS;
//             }
//             else  {
//                     this.setCommand((this.getCommand() + 1) % 3);

//                     if (this.getCommand() == 3) {
//                         this.setCommand(0);
//                     }
//                     player.displayClientMessage(Component.translatable("entity.unusualprehistory.all.command_" + this.getCommand(), this.getName()), true);
//                     boolean sit = this.getCommand() == 2;
//                     if (sit) {
//                         this.setOrderedToSit(true);
//                         return InteractionResult.SUCCESS;
//                     } else {
//                         this.setOrderedToSit(false);
//                         return InteractionResult.SUCCESS;
//                     }
//                 }
//             }
//         return InteractionResult.SUCCESS;

//     }



//     public void tick() {
//         super.tick();

//         if(attackCooldown > 0){
//             attackCooldown--;
//         }

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
//         this.crouchAmountO = this.crouchAmount;
//         if (this.isCrouching()) {
//             this.crouchAmount += 0.2F;
//             if (this.crouchAmount > 3.0F) {
//                 this.crouchAmount = 3.0F;
//             }
//         } else {
//             this.crouchAmount = 0.0F;
//         }
//         if(isStillEnough() && random.nextInt(500) == 0 && !this.isInSittingPose() && !this.isSwimming() && !this.isAsleep()){
//             float rand = random.nextFloat();
//             if (rand < 0.2F) {
//                 spookMobsTime = 40;
//                 this.navigation.stop();
//             }
//         }
//         if(spookMobsTime > 0) {
//             this.navigation.stop();
//             List<Monster> list = this.level().getEntitiesOfClass(Monster.class, this.getBoundingBox().inflate(16, 8, 16));
//             for (Monster e : list) {
//                 e.setTarget(null);
//                 e.setLastHurtByMob(null);
//                 if(spookMobsTime % 5 == 0){
//                     Vec3 vec = LandRandomPos.getPosAway(e, 20, 7, this.position());
//                     if(vec != null){
//                         e.getNavigation().moveTo(vec.x, vec.y, vec.z, 1.5D);
//                     }
//                 }

//             }
//             spookMobsTime--;
//         }
//     }

//     @Override
//     protected void defineSynchedData() {
//         super.defineSynchedData();
//         this.entityData.define(COMMAND, 0);
//         this.entityData.define(SPOOKED, 0);
//     }

//     public int getCommand() {
//         return this.entityData.get(COMMAND).intValue();
//     }

//     public void setCommand(int command) {
//         this.entityData.set(COMMAND, Integer.valueOf(command));
//     }

//     public void addAdditionalSaveData(CompoundTag compound) {
//         super.addAdditionalSaveData(compound);
//         compound.putInt("Command", this.getCommand());
//         compound.putInt("spooked", this.spookMobsTime);
//     }

//     public void readAdditionalSaveData(CompoundTag compound) {
//         super.readAdditionalSaveData(compound);
//         this.setCommand(compound.getInt("Command"));
//         this.setSpooked(compound.getInt("spooked"));
//     }

//     @Override
//     public boolean shouldFollow() {
//         return this.getCommand() == 1;
//     }

//     @Override
//     protected void performAttack() {

//     }

//     @Override
//     protected SoundEvent getAttackSound() {
//         return null;
//     }

//     @Override
//     protected int getKillHealAmount() {
//         return 0;
//     }

//     @Override
//     protected boolean canGetHungry() {
//         return false;
//     }

//     @Override
//     protected boolean hasTargets() {
//         return false;
//     }

//     @Override
//     protected boolean hasAvoidEntity() {
//         return false;
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
//         return null;
//     }

//     @Nullable
//     @Override
//     public AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
//         return null;
//     }

//     public boolean isAlliedTo(Entity entityIn) {
//         if (this.isTame()) {
//             LivingEntity livingentity = this.getOwner();
//             if (entityIn == livingentity) {
//                 return true;
//             }
//             if (entityIn instanceof TamableAnimal) {
//                 return ((TamableAnimal) entityIn).isOwnedBy(livingentity);
//             }
//             if (livingentity != null) {
//                 return livingentity.isAlliedTo(entityIn);
//             }
//         }

//         return entityIn.is(this);
//     }

//     private boolean isStillEnough() {
//         return this.getDeltaMovement().horizontalDistance() < 0.05;
//     }

//     protected <E extends EntityOtarocyon> PlayState Controller(final software.bernie.geckolib.core.animation.AnimationState<E> event) {

//         if (this.isFromBook()) {
//             return PlayState.CONTINUE;
//         }


//         if (this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6 && !this.isInSittingPose() && !this.isInWater()) {
//             if (this.isSprinting()) {
//                 event.setAndContinue(OTAROCYON_RUN);
//                 event.getController().setAnimationSpeed(2.0D);
//                 return PlayState.CONTINUE;
//             } else if (event.isMoving()) {
//                 event.setAndContinue(OTAROCYON_WALK);
//                 event.getController().setAnimationSpeed(1.0D);
//                 return PlayState.CONTINUE;
//             }
//         }
//         if (this.isInSittingPose() && !this.isInWater() && !this.isSwimming()) {
//             return event.setAndContinue(OTAROCYON_SIT);
//         }
//         if (this.isInWater()) {
//             event.setAndContinue(OTAROCYON_SWIM);
//             event.getController().setAnimationSpeed(1.0F);
//             return PlayState.CONTINUE;
//         }
//         if (getSpooked() > 0 && !this.isInSittingPose()) {
//             event.setAndContinue(OTAROCYON_SCREAM);
//             event.getController().setAnimationSpeed(1.0F);
//             return PlayState.CONTINUE;
//         }
//         if (this.isAsleep() && !this.isInSittingPose()){
//             event.setAndContinue(OTAROCYON_SLEEP);
//             event.getController().setAnimationSpeed(1.0F);
//             return PlayState.CONTINUE;
//         }
//         else if (this.isJumping()) {
//             return event.setAndContinue(OTAROCYON_LEAP_HOLD);
//         }


//         if(playingAnimation()) {
//             return PlayState.CONTINUE;
//         }

//         else if (isStillEnough() && getRandomAnimationNumber() == 0 && !this.isInSittingPose() && !this.isSwimming()) {
//             int rand = getRandomAnimationNumber();
//             if (rand < 15) {
//                 setAnimationTimer(200);
//                 return event.setAndContinue(OTAROCYON_LOAF);
//             }
//             if (rand < 66) {
//                 setAnimationTimer(300);
//                 return event.setAndContinue(OTAROCYON_DIG);
//             }
//             if (rand < 77) {
//                 setAnimationTimer(300);
//                 return event.setAndContinue(OTAROCYON_YAWN);
//             }

//         }
//         return event.setAndContinue(OTAROCYON_IDLE);
//     }

//     protected <E extends EntityOtarocyon> PlayState attackController(final software.bernie.geckolib.core.animation.AnimationState<E> event) {
//         if (this.swinging && event.getController().getAnimationState().equals(AnimationController.State.PAUSED)) {
//             return event.setAndContinue(OTAROCYON_ATTACK);
//         }
//         return PlayState.CONTINUE;
//     }

//     @Override
//     public void registerControllers(final AnimatableManager.ControllerRegistrar controllers) {
//         controllers.add(new AnimationController<>(this, "Normal", 10, this::Controller));
//         controllers.add(new AnimationController<>(this, "Attack", 0, this::attackController));
//     }

//     public class FoxPounceGoal extends JumpGoal {

//         public boolean canUse() {
//             if (!EntityOtarocyon.this.isFullyCrouched()) {
//                 return false;
//             } else {
//                 LivingEntity livingentity = EntityOtarocyon.this.getTarget();
//                 if (livingentity != null && livingentity.isAlive()) {
//                     if (livingentity.getMotionDirection() != livingentity.getDirection()) {
//                         return false;
//                     } else {
//                         boolean flag = EntityOtarocyon.isPathClear(EntityOtarocyon.this, livingentity);
//                         if (!flag) {
//                             EntityOtarocyon.this.getNavigation().createPath(livingentity, 0);
//                             EntityOtarocyon.this.setIsCrouching(false);
//                             EntityOtarocyon.this.setIsInterested(false);
//                         }

//                         return flag;
//                     }
//                 } else {
//                     return false;
//                 }
//             }
//         }


//         public boolean canContinueToUse() {
//             LivingEntity livingentity = EntityOtarocyon.this.getTarget();
//             if (livingentity != null && livingentity.isAlive()) {
//                 double d0 = EntityOtarocyon.this.getDeltaMovement().y;
//                 return (!(d0 * d0 < (double)0.05F) || !(Math.abs(EntityOtarocyon.this.getXRot()) < 15.0F) || !EntityOtarocyon.this.onGround()) && !EntityOtarocyon.this.isFaceplanted();
//             } else {
//                 return false;
//             }
//         }

//         public boolean isInterruptable() {
//             return false;
//         }


//         public void start() {
//             EntityOtarocyon.this.setJumping(true);
//             EntityOtarocyon.this.setIsPouncing(true);
//             EntityOtarocyon.this.setIsInterested(false);
//             LivingEntity livingentity = EntityOtarocyon.this.getTarget();
//             if (livingentity != null) {
//                 EntityOtarocyon.this.getLookControl().setLookAt(livingentity, 60.0F, 30.0F);
//                 Vec3 vec3 = (new Vec3(livingentity.getX() - EntityOtarocyon.this.getX(), livingentity.getY() - EntityOtarocyon.this.getY(), livingentity.getZ() - EntityOtarocyon.this.getZ())).normalize();
//                 EntityOtarocyon.this.setDeltaMovement(EntityOtarocyon.this.getDeltaMovement().add(vec3.x * 0.8D, 0.9D, vec3.z * 0.8D));
//             }

//             EntityOtarocyon.this.getNavigation().stop();
//         }


//         public void stop() {
//             EntityOtarocyon.this.setIsCrouching(false);
//             EntityOtarocyon.this.crouchAmount = 0.0F;
//             EntityOtarocyon.this.crouchAmountO = 0.0F;
//             EntityOtarocyon.this.setIsInterested(false);
//             EntityOtarocyon.this.setIsPouncing(false);
//         }


//         public void tick() {
//             LivingEntity livingentity = EntityOtarocyon.this.getTarget();
//             if (livingentity != null) {
//                 EntityOtarocyon.this.getLookControl().setLookAt(livingentity, 60.0F, 30.0F);
//             }

//             if (!EntityOtarocyon.this.isFaceplanted()) {
//                 Vec3 vec3 = EntityOtarocyon.this.getDeltaMovement();
//                 if (vec3.y * vec3.y < (double)0.03F && EntityOtarocyon.this.getXRot() != 0.0F) {
//                     EntityOtarocyon.this.setXRot(Mth.rotLerp(0.2F, EntityOtarocyon.this.getXRot(), 0.0F));
//                 } else {
//                     double d0 = vec3.horizontalDistance();
//                     double d1 = Math.signum(-vec3.y) * Math.acos(d0 / vec3.length()) * (double)(180F / (float)Math.PI);
//                     EntityOtarocyon.this.setXRot((float)d1);
//                 }
//             }

//             if (livingentity != null && EntityOtarocyon.this.distanceTo(livingentity) <= 2.0F) {
//                 EntityOtarocyon.this.doHurtTarget(livingentity);
//             } else if (EntityOtarocyon.this.getXRot() > 0.0F && EntityOtarocyon.this.onGround() && (float)EntityOtarocyon.this.getDeltaMovement().y != 0.0F && EntityOtarocyon.this.level().getBlockState(EntityOtarocyon.this.blockPosition()).is(Blocks.SNOW)) {
//                 EntityOtarocyon.this.setXRot(60.0F);
//                 EntityOtarocyon.this.setTarget((LivingEntity)null);
//                 EntityOtarocyon.this.setFaceplanted(true);
//             }

//         }
//     }

//     class FaceplantGoal extends Goal {
//         int countdown;

//         public FaceplantGoal() {
//             this.setFlags(EnumSet.of(Goal.Flag.LOOK, Goal.Flag.JUMP, Goal.Flag.MOVE));
//         }

//         public boolean canUse() {
//             return EntityOtarocyon.this.isFaceplanted();
//         }


//         public boolean canContinueToUse() {
//             return this.canUse() && this.countdown > 0;
//         }


//         public void start() {
//             this.countdown = this.adjustedTickDelay(40);
//         }


//         public void stop() {
//             EntityOtarocyon.this.setFaceplanted(false);
//         }

//         public void tick() {
//             --this.countdown;
//         }
//     }

//     public static boolean isPathClear(EntityOtarocyon pFox, LivingEntity pLivingEntity) {
//         double d0 = pLivingEntity.getZ() - pFox.getZ();
//         double d1 = pLivingEntity.getX() - pFox.getX();
//         double d2 = d0 / d1;
//         int i = 6;

//         for(int j = 0; j < 6; ++j) {
//             double d3 = d2 == 0.0D ? 0.0D : d0 * (double)((float)j / 6.0F);
//             double d4 = d2 == 0.0D ? d1 * (double)((float)j / 6.0F) : d3 / d2;

//             for(int k = 1; k < 4; ++k) {
//                 if (!pFox.level().getBlockState(BlockPos.containing(pFox.getX() + d4, pFox.getY() + (double)k, pFox.getZ() + d3)).canBeReplaced()) {
//                     return false;
//                 }
//             }
//         }

//         return true;
//     }

//     private boolean getFlag(int pFlagId) {
//         return (this.entityData.get(DATA_FLAGS_ID) & pFlagId) != 0;
//     }

//     private void setFlag(int pFlagId, boolean pValue) {
//         if (pValue) {
//             this.entityData.set(DATA_FLAGS_ID, (byte)(this.entityData.get(DATA_FLAGS_ID) | pFlagId));
//         } else {
//             this.entityData.set(DATA_FLAGS_ID, (byte)(this.entityData.get(DATA_FLAGS_ID) & ~pFlagId));
//         }

//     }

//     public boolean isPouncing() {
//         return this.getFlag(16);
//     }

//     public void setIsPouncing(boolean pIsPouncing) {
//         this.setFlag(16, pIsPouncing);
//     }

//     public boolean isJumping() {
//         return this.jumping;
//     }

//     public boolean isFullyCrouched() {
//         return this.crouchAmount == 3.0F;
//     }

//     public void setIsCrouching(boolean pIsCrouching) {
//         this.setFlag(4, pIsCrouching);
//     }

//     public boolean isCrouching() {
//         return this.getFlag(4);
//     }

//     public void setIsInterested(boolean pIsInterested) {
//         this.setFlag(8, pIsInterested);
//     }

//     public boolean isInterested() {
//         return this.getFlag(8);
//     }

//     public boolean isFaceplanted() {
//         return this.getFlag(64);
//     }

//     void setFaceplanted(boolean pFaceplanted) {
//         this.setFlag(64, pFaceplanted);
//     }
//     public int getSpooked() {
//         setSpooked(spookMobsTime);
//         return this.entityData.get(SPOOKED);
//     }

//     public void setSpooked(int nr) {
//         this.entityData.set(SPOOKED,nr);
//     }
// }
