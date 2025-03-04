 package com.peeko32213.unusualprehistory.common.entity;

 import com.peeko32213.unusualprehistory.common.entity.base.TamablePrehistoricEntity;
 import com.peeko32213.unusualprehistory.common.entity.base.TamableClimbingPrehistoricEntity;
 import com.peeko32213.unusualprehistory.common.entity.util.goal.CustomRandomStrollGoal;
 import com.peeko32213.unusualprehistory.common.entity.util.goal.TameableFollowOwner;
 import com.peeko32213.unusualprehistory.common.entity.util.interfaces.ICustomFollower;
 import com.peeko32213.unusualprehistory.core.registry.UPItems;
 import com.peeko32213.unusualprehistory.core.registry.util.UPMath;
 import net.minecraft.core.BlockPos;
 import net.minecraft.core.Direction;
 import net.minecraft.nbt.CompoundTag;
 import net.minecraft.network.chat.Component;
 import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
 import net.minecraft.network.syncher.EntityDataAccessor;
 import net.minecraft.network.syncher.EntityDataSerializers;
 import net.minecraft.network.syncher.SynchedEntityData;
 import net.minecraft.server.level.ServerLevel;
 import net.minecraft.server.level.ServerPlayer;
 import net.minecraft.sounds.SoundEvent;
 import net.minecraft.sounds.SoundEvents;
 import net.minecraft.tags.TagKey;
 import net.minecraft.util.Mth;
 import net.minecraft.world.InteractionHand;
 import net.minecraft.world.InteractionResult;
 import net.minecraft.world.damagesource.DamageSource;
 import net.minecraft.world.damagesource.DamageTypes;
 import net.minecraft.world.effect.MobEffectInstance;
 import net.minecraft.world.effect.MobEffects;
 import net.minecraft.world.entity.*;
 import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
 import net.minecraft.world.entity.ai.attributes.Attributes;
 import net.minecraft.world.entity.ai.goal.*;
 import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
 import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
 import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
 import net.minecraft.world.entity.ai.targeting.TargetingConditions;
 import net.minecraft.world.entity.player.Player;
 import net.minecraft.world.entity.vehicle.DismountHelper;
 import net.minecraft.world.item.ItemStack;
 import net.minecraft.world.item.Items;
 import net.minecraft.world.level.Level;
 import net.minecraft.world.level.block.state.BlockState;
 import net.minecraft.world.phys.AABB;
 import net.minecraft.world.phys.Vec3;
 import org.jetbrains.annotations.Nullable;
 import software.bernie.geckolib.core.animation.AnimatableManager;
 import software.bernie.geckolib.core.animation.AnimationController;
 import software.bernie.geckolib.core.animation.RawAnimation;
 import software.bernie.geckolib.core.object.PlayState;

 import java.util.function.Predicate;
 //TODO LIST
 // - They look around too much while climbing, breaking the immersion a bit
 // - Sounds + Eggs need to be done
 // - Chances in DNA loot pool need to be added (Added to Amber as amber needs more stuff in it)
 // - While ordered to sit it sometimes goes into the animation but still slides around, though it usually fixes itself after commanding it again
 // - Walking animation sometimes play while idling
 public class LongisquamaEntity extends TamableClimbingPrehistoricEntity implements ICustomFollower {
     private static final RawAnimation LONGISQUAMA_IDLE = RawAnimation.begin().thenLoop("animation.longisquama.ground_idle");
     private static final RawAnimation LONGISQUAMA_WALK = RawAnimation.begin().thenLoop("animation.longisquama.ground_walk");
     private static final RawAnimation LONGISQUAMA_SHAKING = RawAnimation.begin().thenLoop("animation.longisquama.shaking");
     private static final RawAnimation LONGISQUAMA_WATER_IDLE = RawAnimation.begin().thenLoop("animation.longisquama.water_idle");
     private static final RawAnimation LONGISQUAMA_SWIM = RawAnimation.begin().thenLoop("animation.longisquama.water_swim");
     private static final RawAnimation LONGISQUAMA_BASKING = RawAnimation.begin().thenLoop("animation.longisquama.basking");
     private static final RawAnimation LONGISQUAMA_FLAIRING = RawAnimation.begin().thenLoop("animation.longisquama.flaring");
     private static final RawAnimation LONGISQUAMA_CLIMBING = RawAnimation.begin().thenLoop("animation.longisquama.climbing");

     private static final EntityDataAccessor<Integer> COMMAND = SynchedEntityData.defineId(LongisquamaEntity.class, EntityDataSerializers.INT);
     private static final Predicate<LivingEntity> SCARY_MOB = (entity) -> {
         if (entity instanceof Player && ((Player)entity).isCreative() && entity.isAlliedTo(entity)) {
             return false;
         }
         if (entity instanceof LongisquamaEntity) {
             return false;
         }
         else {
             return entity.getType() == EntityType.AXOLOTL || entity.getMobType() != MobType.WATER;
         }
     };
     static final TargetingConditions targetingConditions = TargetingConditions.forNonCombat().ignoreInvisibilityTesting().ignoreLineOfSight().selector(SCARY_MOB);
     public float sitProgress;
     private int rideCooldown = 0;

     public LongisquamaEntity(EntityType<? extends TamablePrehistoricEntity> entityType, Level level) {
         super(entityType, level);
     }

     public static AttributeSupplier.Builder createAttributes() {
         return Mob.createMobAttributes()
                 .add(Attributes.MAX_HEALTH, 10.0D)
                 .add(Attributes.MAX_HEALTH, 5.0D)
                 .add(Attributes.MOVEMENT_SPEED, 0.26D)
                 .add(Attributes.ARMOR, 0.5D)
                 .add(Attributes.ARMOR_TOUGHNESS, 0.5D)
                 .add(Attributes.KNOCKBACK_RESISTANCE, 0.0D);
     }



     @Override
     protected void registerGoals() {
         this.goalSelector.addGoal(0, new FloatGoal(this));
         this.goalSelector.addGoal(1, new PanicGoal(this, 1.25D));
         this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
         this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
         this.goalSelector.addGoal(0, new SitWhenOrderedToGoal(this));
         this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)));
         this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
         this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
         this.goalSelector.addGoal(3, new TameableFollowOwner(this, 1.2D, 5.0F, 2.0F, false));
         this.goalSelector.addGoal(3, new CustomRandomStrollGoal(this, 60, 1.0D, 100, 34));
         this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, Player.class, 5.0F, 2.5D, 2.7D, EntitySelector.NO_SPECTATORS::test));
     }

     @Override
     public InteractionResult mobInteract(Player player, InteractionHand hand) {
         ItemStack itemstack = player.getItemInHand(hand);
         InteractionResult type = super.mobInteract(player, hand);
         if (itemstack.is(UPItems.ENCYLOPEDIA.get())) {
             InteractionResult interactionresult = itemstack.interactLivingEntity(player, this, hand);
         }
         if (itemstack.is(Items.SPIDER_EYE)) {
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
         InteractionResult interactionresult = itemstack.interactLivingEntity(player, this, hand);
         if (interactionresult != InteractionResult.SUCCESS && type != InteractionResult.SUCCESS && isTame() && isOwnedBy(player) && !isFood(itemstack)) {
             if (player.isShiftKeyDown() && player.getPassengers().isEmpty()) {
                 this.startRiding(player);
                 rideCooldown = 20;
                 return InteractionResult.SUCCESS;
             } else {
                 this.setCommand(this.getCommand() + 1);
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
         return type;
     }


     @Override
     protected boolean isImmobile() {
         return this.isPassenger();
     }

     public Vec3 getDismountLocationForPassenger(LivingEntity pLivingEntity) {
         Vec3 vec3 = getCollisionHorizontalEscapeVector((double)this.getBbWidth(), (double)pLivingEntity.getBbWidth(), this.getYRot() + (pLivingEntity.getMainArm() == HumanoidArm.RIGHT ? 90.0F : -90.0F));
         Vec3 vec31 = this.getDismountLocationInDirection(vec3, pLivingEntity);
         if (vec31 != null) {
             return vec31;
         } else {
             Vec3 vec32 = getCollisionHorizontalEscapeVector((double)this.getBbWidth(), (double)pLivingEntity.getBbWidth(), this.getYRot() + (pLivingEntity.getMainArm() == HumanoidArm.LEFT ? 90.0F : -90.0F));
             Vec3 vec33 = this.getDismountLocationInDirection(vec32, pLivingEntity);
             return vec33 != null ? vec33 : this.position();
         }
     }

     @javax.annotation.Nullable
     private Vec3 getDismountLocationInDirection(Vec3 pDirection, LivingEntity pPassenger) {
         double d0 = this.getX() + pDirection.x;
         double d1 = this.getBoundingBox().minY;
         double d2 = this.getZ() + pDirection.z;
         BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

         for(Pose pose : pPassenger.getDismountPoses()) {
             blockpos$mutableblockpos.set(d0, d1, d2);
             double d3 = this.getBoundingBox().maxY + 0.75D;

             while(true) {
                 double d4 = this.level().getBlockFloorHeight(blockpos$mutableblockpos);
                 if ((double)blockpos$mutableblockpos.getY() + d4 > d3) {
                     break;
                 }

                 if (DismountHelper.isBlockFloorValid(d4)) {
                     AABB aabb = pPassenger.getLocalBoundsForPose(pose);
                     Vec3 vec3 = new Vec3(d0, (double)blockpos$mutableblockpos.getY() + d4, d2);
                     if (DismountHelper.canDismountTo(this.level(), pPassenger, aabb.move(vec3))) {
                         pPassenger.setPose(pose);
                         return vec3;
                     }
                 }

                 blockpos$mutableblockpos.move(Direction.UP);
                 if (!((double)blockpos$mutableblockpos.getY() < d3)) {
                     break;
                 }
             }
         }

         return null;
     }

     public void aiStep() {
         super.aiStep();
         if (this.isAlive()) {
             for(Mob mob : this.level().getEntitiesOfClass(Mob.class, this.getBoundingBox().inflate(0.3D), (p_149013_) -> {
                 return !this.isAlliedTo(p_149013_);
             })) {
                 if (mob.isAlive() && !this.isAlliedTo(mob)) {
                     this.touch(mob);
                 }
             }
         }

     }



     private boolean touch(Mob pMob) {
         if (this.isAlliedTo(pMob) || pMob instanceof LongisquamaEntity ){
             return false;
         }
         if (pMob.hurt(this.damageSources().mobAttack(this), (float)(1.2))) {
             pMob.addEffect(new MobEffectInstance(MobEffects.POISON, 60 , 0), this);
             this.playSound(SoundEvents.PUFFER_FISH_STING, 1.0F, 1.0F);
         }
         return false;
     }



     /**
      * Called by a player entity when they collide with an entity
      */
     public void playerTouch(Player pEntity) {
         if (pEntity instanceof ServerPlayer && pEntity.hurt(this.damageSources().mobAttack(this), (float)(1.2)) && !isOwnedBy(pEntity)) {
             if (!this.isSilent()) {
                 ((ServerPlayer)pEntity).connection.send(new ClientboundGameEventPacket(ClientboundGameEventPacket.PUFFER_FISH_STING, 0.0F));
             }
             pEntity.addEffect(new MobEffectInstance(MobEffects.POISON, 60, 0), this);
         }

     }

     @Override
     public boolean hurt(DamageSource pSource, float pAmount) {
         if(pSource.getEntity() != null && pSource.getEntity().is(this)){
             return false;
         }
         return super.hurt(pSource, pAmount);
     }

     public void tick() {
         super.tick();

         if (attackCooldown > 0) {
             attackCooldown--;
         }
     }

     @Override
     protected void defineSynchedData() {
         super.defineSynchedData();
         this.entityData.define(COMMAND, 0);
     }

     public int getCommand() {
         return this.entityData.get(COMMAND).intValue();
     }

     public void setCommand(int command) {
         this.entityData.set(COMMAND, Integer.valueOf(command));
     }

     public void addAdditionalSaveData(CompoundTag compound) {
         super.addAdditionalSaveData(compound);
         compound.putInt("Command", this.getCommand());
     }

     public void readAdditionalSaveData(CompoundTag compound) {
         super.readAdditionalSaveData(compound);
         this.setCommand(compound.getInt("Command"));
     }

     @Override
     public void die(DamageSource pDamageSource) {
         this.stopRiding();
         super.die(pDamageSource);
     }

     public void rideTick() {
         Entity entity = this.getVehicle();
         if (this.isPassenger() && !entity.isAlive()) {
             this.stopRiding();
         } else if (isTame() && entity instanceof LivingEntity && isOwnedBy((LivingEntity) entity)) {
             this.setDeltaMovement(0, 0, 0);
             this.tick();
             if (this.isPassenger()) {
                 Entity mount = this.getVehicle();
                 if (mount instanceof Player player) {
                     this.yBodyRot = ((LivingEntity) mount).yBodyRot;
                     this.setYRot(mount.getYRot());
                     this.yHeadRot = ((LivingEntity) mount).yHeadRot;
                     this.yRotO = ((LivingEntity) mount).yHeadRot;
                     final float radius = 0F;
                     final float angle = (UPMath.STARTING_ANGLE * (((LivingEntity) mount).yBodyRot - 180F));
                     double extraX = radius * Mth.sin(Mth.PI + angle);
                     double extraZ = radius * Mth.cos(angle);
                     this.setPos(mount.getX() + extraX, Math.max(mount.getY() + mount.getBbHeight() + 0.1, mount.getY()), mount.getZ() + extraZ);
                     if (!player.isAlive() || rideCooldown == 0 || player.isShiftKeyDown() || !mount.isAlive()) {
                         this.stopRiding();
                     }
                 }

             }
         } else {
             super.rideTick();
         }

     }

     @Override
     protected float getClimbSpeedMultiplier() {
         return 0.5F;
     }

     @Override
     protected void performAttack() {

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

     @Nullable
     @Override
     public AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
         return null;
     }

     @Override
     public boolean isInvulnerableTo(DamageSource source) {
         return source.is(DamageTypes.FALL) || source.is(DamageTypes.CACTUS)  || super.isInvulnerableTo(source);
     }

     private boolean isStillEnough() {
         return this.getDeltaMovement().horizontalDistance() < 0.05;
     }

     public boolean isAlliedTo(Entity entityIn) {
         if (this.isTame()) {
             LivingEntity livingentity = this.getOwner();
             if (entityIn == livingentity) {
                 return true;
             }
             if (entityIn instanceof TamableAnimal) {
                 return ((TamableAnimal) entityIn).isOwnedBy(livingentity);
             }
             if (livingentity != null) {
                 return livingentity.isAlliedTo(entityIn);
             }
         }

         return entityIn.is(this);
     }


     protected <E extends LongisquamaEntity> PlayState Controller(final software.bernie.geckolib.core.animation.AnimationState<E> event) {

         if (this.isFromBook()) {
             return event.setAndContinue(LONGISQUAMA_IDLE);
         }

         if ((this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6 && !this.isInSittingPose() && !this.isInWater()) || this.isClimbing()) {
             return event.setAndContinue(LONGISQUAMA_WALK);
         }
         if (this.isInSittingPose() && ((!this.isInWater() && !this.isSwimming()) || this.isClimbing())) {
             return event.setAndContinue(LONGISQUAMA_BASKING);
         }
         if (this.isInWater()) {
             event.setAndContinue(LONGISQUAMA_SWIM);
             event.getController().setAnimationSpeed(1.0F);
         }
         if (this.isPassenger() && !this.isSwimming() && !this.isInSittingPose() && this.isClimbing()) {
             return event.setAndContinue(LONGISQUAMA_SHAKING);
         }
         else if (this.isClimbing() && !this.isSwimming()) {
             return event.setAndContinue(LONGISQUAMA_CLIMBING);
         }

         if(playingAnimation())
         {
             return PlayState.CONTINUE;
         }
         if (isStillEnough() && getRandomAnimationNumber(500) == 0 && !this.isInSittingPose() && !this.isSwimming() && this.isClimbing()) {
             int rand = getRandomAnimationNumber();
             if (rand < 55) {
                 setAnimationTimer(100);
                 return event.setAndContinue(LONGISQUAMA_BASKING);
             }
             if (rand < 75) {
                 setAnimationTimer(100);
                 return event.setAndContinue(LONGISQUAMA_FLAIRING);
             }
             event.setAndContinue(LONGISQUAMA_IDLE);
         }
         return PlayState.CONTINUE;
     }

     protected <E extends LongisquamaEntity> PlayState attackController(final software.bernie.geckolib.core.animation.AnimationState<E> event) {
         if (this.swinging && event.getController().getAnimationState().equals(AnimationController.State.PAUSED)) {
             return event.setAndContinue(LONGISQUAMA_SHAKING);
         }
         return PlayState.CONTINUE;
     }

     @Override
     public void registerControllers(final AnimatableManager.ControllerRegistrar controllers) {
         controllers.add(new AnimationController<>(this, "Normal", 5, this::Controller));
         controllers.add(new AnimationController<>(this, "Attack", 0, this::attackController));
     }

     @Override
     public boolean shouldFollow() {
         return this.getCommand() == 1;
     }
 }
