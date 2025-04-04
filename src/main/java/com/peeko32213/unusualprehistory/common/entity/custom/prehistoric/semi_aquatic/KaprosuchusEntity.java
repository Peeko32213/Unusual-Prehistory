 package com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.semi_aquatic;

 import com.google.common.collect.ImmutableMap;
 import com.peeko32213.unusualprehistory.common.entity.animation.state.RandomStateGoal;
 import com.peeko32213.unusualprehistory.common.entity.animation.state.StateHelper;
 import com.peeko32213.unusualprehistory.common.entity.animation.state.WeightedState;
 import com.peeko32213.unusualprehistory.common.entity.custom.ai.goal.*;
 import com.peeko32213.unusualprehistory.common.entity.custom.base.PrehistoricEntity;
 import com.peeko32213.unusualprehistory.common.entity.util.helper.HitboxAttacks;
 import com.peeko32213.unusualprehistory.common.entity.util.interfaces.ICustomFollower;
 import com.peeko32213.unusualprehistory.common.entity.util.interfaces.ISemiAquatic;
 import com.peeko32213.unusualprehistory.common.entity.util.navigator.SemiAquaticPathNavigation;
 import com.peeko32213.unusualprehistory.common.entity.util.navigator.SmartBodyHelper;
 import com.peeko32213.unusualprehistory.common.entity.util.navigator.SmoothGroundNavigation;
 import com.peeko32213.unusualprehistory.common.entity.util.navigator.WaterMoveController;
 import com.peeko32213.unusualprehistory.core.registry.items.UPItems;
 import com.peeko32213.unusualprehistory.core.registry.UPSounds;
 import com.peeko32213.unusualprehistory.core.other.tags.UPEntityTypeTags;
 import net.minecraft.nbt.CompoundTag;
 import net.minecraft.network.chat.Component;
 import net.minecraft.network.syncher.EntityDataAccessor;
 import net.minecraft.network.syncher.EntityDataSerializers;
 import net.minecraft.network.syncher.SynchedEntityData;
 import net.minecraft.server.level.ServerLevel;
 import net.minecraft.world.InteractionHand;
 import net.minecraft.world.InteractionResult;
 import net.minecraft.world.entity.*;
 import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
 import net.minecraft.world.entity.ai.attributes.Attributes;
 import net.minecraft.world.entity.ai.control.BodyRotationControl;
 import net.minecraft.world.entity.ai.control.MoveControl;
 import net.minecraft.world.entity.ai.goal.*;
 import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
 import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
 import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
 import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
 import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
 import net.minecraft.world.entity.ai.navigation.PathNavigation;
 import net.minecraft.world.entity.player.Player;
 import net.minecraft.world.item.ItemStack;
 import net.minecraft.world.level.Level;
 import net.minecraft.world.level.block.state.BlockState;
 import net.minecraft.world.level.gameevent.GameEvent;
 import net.minecraft.world.level.pathfinder.BlockPathTypes;
 import net.minecraft.world.level.pathfinder.Node;
 import net.minecraft.world.level.pathfinder.Path;
 import net.minecraft.world.phys.Vec2;
 import net.minecraft.world.phys.Vec3;
 import org.jetbrains.annotations.NotNull;
 import org.jetbrains.annotations.Nullable;
 import software.bernie.geckolib.core.animation.AnimatableManager;
 import software.bernie.geckolib.core.animation.AnimationController;
 import software.bernie.geckolib.core.animation.RawAnimation;
 import software.bernie.geckolib.core.object.PlayState;

 import java.util.EnumSet;
 import java.util.List;
 import java.util.Objects;

 public class KaprosuchusEntity extends PrehistoricEntity implements ICustomFollower, ISemiAquatic {

     private static final RawAnimation KAPROSUCHUS_WALK = RawAnimation.begin().thenLoop("animation.kaprosuchus.walking");
     private static final RawAnimation KAPROSUCHUS_RUN = RawAnimation.begin().thenLoop("animation.kaprosuchus.run");
     private static final RawAnimation KAPROSUCHUS_SIT = RawAnimation.begin().thenLoop("animation.kaprosuchus.sit");
     private static final RawAnimation KAPROSUCHUS_SWIM = RawAnimation.begin().thenLoop("animation.kaprosuchus.swim");
     private static final RawAnimation KAPROSUCHUS_SCRATCH = RawAnimation.begin().thenLoop("animation.kaprosuchus.scratch");
     private static final RawAnimation KAPROSUCHUS_IDLE = RawAnimation.begin().thenLoop("animation.kaprosuchus.idle");
     private static final RawAnimation KAPROSUCHUS_ATTACK_1 = RawAnimation.begin().thenLoop("animation.kaprosuchus.attack_bite");
     private static final RawAnimation KAPROSUCHUS_ATTACK_2 = RawAnimation.begin().thenLoop("animation.kaprosuchus.attack_headbutt");
     private static final RawAnimation KAPROSUCHUS_ATTACK_SWIM = RawAnimation.begin().thenLoop("animation.kaprosuchus.swimattack");
     private static final RawAnimation KAPROSUCHUS_ROAR = RawAnimation.begin().thenLoop("animation.kaprosuchus.roar");
     private static final RawAnimation KAPROSUCHUS_SWIM_IDLE = RawAnimation.begin().thenLoop("animation.kaprosuchus.swimidle");

     private static final EntityDataAccessor<Integer> COMMAND = SynchedEntityData.defineId(KaprosuchusEntity.class, EntityDataSerializers.INT);
     private static final EntityDataAccessor<Integer> COMBAT_STATE = SynchedEntityData.defineId(KaprosuchusEntity.class, EntityDataSerializers.INT);
     private static final EntityDataAccessor<Integer> ENTITY_STATE = SynchedEntityData.defineId(KaprosuchusEntity.class, EntityDataSerializers.INT);
     private static final EntityDataAccessor<Integer> ANIMATION_STATE = SynchedEntityData.defineId(KaprosuchusEntity.class, EntityDataSerializers.INT);
     public float sitProgress;
     public float prevSwimProgress;
     public float swimProgress;
     private int swimTimer = -1000;
     private boolean isLandNavigator;

     // Body control / navigation
     @Override
     protected @NotNull BodyRotationControl createBodyControl() {
         SmartBodyHelper helper = new SmartBodyHelper(this);
         helper.bodyLagMoving = 0.35F;
         helper.bodyLagStill = 0.25F;
         return helper;
     }

     @Override
     protected @NotNull PathNavigation createNavigation(Level levelIn) {
         return new SmoothGroundNavigation(this, levelIn);
     }

     public KaprosuchusEntity(EntityType<? extends PrehistoricEntity> entityType, Level level) {
         super(entityType, level);
         this.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
         this.setPathfindingMalus(BlockPathTypes.WATER_BORDER, 0.0F);
         switchNavigator(false);
     }

     public static AttributeSupplier.Builder createAttributes() {
         return Mob.createMobAttributes()
                 .add(Attributes.MAX_HEALTH, 25.0D)
                 .add(Attributes.ATTACK_DAMAGE, 6.0D)
                 .add(Attributes.MOVEMENT_SPEED, 0.18D)
                 .add(Attributes.ARMOR, 10.0D)
                 .add(Attributes.ARMOR_TOUGHNESS, 10.0D)
                 .add(Attributes.KNOCKBACK_RESISTANCE, 0.5D);
     }

     @Override
     protected void registerGoals() {
         this.goalSelector.addGoal(7, new FindWaterGoal(this));
         this.goalSelector.addGoal(7, new LeaveWaterGoal(this));
         this.goalSelector.addGoal(9, new SemiAquaticSwimmingGoal(this, 1.0D, 10));
         this.goalSelector.addGoal(1, new PanicGoal(this, 1.25D));
         this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
         this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
         this.goalSelector.addGoal(0, new SitWhenOrderedToGoal(this));
         this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)));
         this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
         this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
         this.goalSelector.addGoal(3, new RandomStrollGoal(this, 1, 30) {
                     @Override
                     public boolean canUse() {
                         if (this.mob.isVehicle()) {
                             return false;
                         } else {
                             if (!this.forceTrigger) {
                                 if (this.mob.getNoActionTime() >= 100) {
                                     return false;
                                 }else {
                                     if (this.mob.getRandom().nextInt(30) != 0) {
                                         return false;
                                     }
                                 }
                             }

                             Vec3 vec3d = this.getPosition();
                             if (vec3d == null) {
                                 return false;
                             } else {
                                 this.wantedX = vec3d.x;
                                 this.wantedY = vec3d.y;
                                 this.wantedZ = vec3d.z;
                                 this.forceTrigger = false;
                                 return true;
                             }
                         }
                     }
                 }
         );
         this.goalSelector.addGoal(3, new PrehistoricFollowOwnerGoal(this, 1.2D, 5.0F, 2.0F, false));
         this.goalSelector.addGoal(3, new RandomStrollGoal(this, 1, 30));
         this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, LivingEntity.class, 10, false, false, entity -> entity.getType().is(UPEntityTypeTags.PSITTACO_TARGETS)));
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

     @Override
     public InteractionResult mobInteract(Player player, InteractionHand hand) {
         ItemStack itemstack = player.getItemInHand(hand);
         if (itemstack.is(UPItems.ENCYLOPEDIA.get())) {
             InteractionResult interactionresult = itemstack.interactLivingEntity(player, this, hand);
         }
         if (itemstack.is(UPItems.RAW_COTY.get())) {
             if (!isTame()) {
                 this.usePlayerItem(player, hand, itemstack);
                 if (getRandom().nextInt(3) == 0) {
                     this.tame(player);
                     this.level().broadcastEntityEvent(this, (byte) 7);
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
                 if(!this.level().isClientSide) {
                     this.heal((float) itemstack.getFoodProperties(this).getNutrition());
                 }
                 this.gameEvent(GameEvent.EAT, this);
                 return InteractionResult.SUCCESS;
             }
             else  {
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

         if (this.isOrderedToSit() && sitProgress < 5F) {
             sitProgress++;
         }
         if (!this.isOrderedToSit() && sitProgress > 0F) {
             sitProgress--;
         }
         if (this.getCommand() == 2 && !this.isVehicle()) {
             this.setOrderedToSit(true);
         } else {
             this.setOrderedToSit(false);
         }
     }

     @Override
     public float getStepHeight() {
         return 1.25F;
     }

     @Override
     protected void defineSynchedData() {
         super.defineSynchedData();
         this.entityData.define(COMMAND, 0);
         this.entityData.define(ANIMATION_STATE, 0);
         this.entityData.define(COMBAT_STATE, 0);
         this.entityData.define(ENTITY_STATE, 0);
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
         compound.putInt("SwimTimer", this.swimTimer);
     }

     public void readAdditionalSaveData(CompoundTag compound) {
         super.readAdditionalSaveData(compound);
         this.setCommand(compound.getInt("Command"));
         this.swimTimer = compound.getInt("SwimTimer");
     }

     @Override
     public boolean shouldFollow() {
         return this.getCommand() == 1;
     }

     @Nullable
     @Override
     public AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
         return null;
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

     public int getAnimationState() {

         return this.entityData.get(ANIMATION_STATE);
     }

     public void setAnimationState(int anim) {

         this.entityData.set(ANIMATION_STATE, anim);
     }

     public int getCombatState() {

         return this.entityData.get(COMBAT_STATE);
     }

     public void setCombatState(int anim) {

         this.entityData.set(COMBAT_STATE, anim);
     }

     public int getEntityState() {

         return this.entityData.get(ENTITY_STATE);
     }

     public void setEntityState(int anim) {

         this.entityData.set(ENTITY_STATE, anim);
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
     public boolean canBreatheUnderwater() {
         return true;
     }

     @Override
     public boolean shouldStopMoving() {
         return false;
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
     public ImmutableMap<String, StateHelper> getStates() {
         return null;
     }

     @Override
     public List<WeightedState<StateHelper>> getWeightedStatesToPerform() {
         return List.of();
     }

     protected <E extends KaprosuchusEntity> PlayState Controller(final software.bernie.geckolib.core.animation.AnimationState<E> event) {
         if(this.isFromBook()){
             return event.setAndContinue(KAPROSUCHUS_IDLE);
         }

         int animState = this.getAnimationState();
         {
             switch (animState) {

                 case 21:
                     event.setAndContinue(KAPROSUCHUS_ATTACK_1);
                     event.getController().setAnimationSpeed(1.25D);
                     break;
                 case 22:
                     event.setAndContinue(KAPROSUCHUS_ATTACK_SWIM);
                     event.getController().setAnimationSpeed(1.25F);
                     break;

                 default:
                     if (this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6 && !this.isInSittingPose() && !this.isInWater() ) {
                         if (this.isSprinting()) {
                             event.setAndContinue(KAPROSUCHUS_RUN);
                             event.getController().setAnimationSpeed(2.0D);
                             return PlayState.CONTINUE;
                         } else if (event.isMoving()) {
                             event.setAndContinue(KAPROSUCHUS_WALK);
                             event.getController().setAnimationSpeed(1.0D);
                             return PlayState.CONTINUE;
                         }
                     }
                     if (this.isInSittingPose() && !this.isInWater() && !this.isSwimming()) {
                         return event.setAndContinue(KAPROSUCHUS_SIT);
                     }
                     if (this.isInSittingPose() && this.isInWater() && this.isSwimming()) {
                         return event.setAndContinue(KAPROSUCHUS_SWIM_IDLE);
                     }
                     if (this.isInWater()) {
                         event.setAndContinue(KAPROSUCHUS_SWIM);
                         event.getController().setAnimationSpeed(1.0F);
                         return PlayState.CONTINUE;
                     }

                     if(playingAnimation())
                     {
                         return PlayState.CONTINUE;
                     }

                      else if (isStillEnough() && !this.isInSittingPose() && !this.isSwimming()) {
                         event.setAndContinue(KAPROSUCHUS_IDLE);
                     }
             }
         }
         return PlayState.CONTINUE;
     }


     @Override
     public void registerControllers(final AnimatableManager.ControllerRegistrar controllers) {
         controllers.add(new AnimationController<>(this, "Normal", 10, this::Controller));
     }

 }
