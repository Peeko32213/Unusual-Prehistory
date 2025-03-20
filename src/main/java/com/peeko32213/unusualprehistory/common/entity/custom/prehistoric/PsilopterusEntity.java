 package com.peeko32213.unusualprehistory.common.entity.custom.prehistoric;

 import com.google.common.collect.ImmutableList;
 import com.google.common.collect.ImmutableMap;
 import com.peeko32213.unusualprehistory.common.entity.animation.state.EntityAction;
 import com.peeko32213.unusualprehistory.common.entity.animation.state.RandomStateGoal;
 import com.peeko32213.unusualprehistory.common.entity.animation.state.StateHelper;
 import com.peeko32213.unusualprehistory.common.entity.animation.state.WeightedState;
 import com.peeko32213.unusualprehistory.common.entity.custom.ai.goal.BabyPanicGoal;
 import com.peeko32213.unusualprehistory.common.entity.custom.ai.goal.JoinPackGoal;
 import com.peeko32213.unusualprehistory.common.entity.custom.ai.goal.PackHunterGoal;
 import com.peeko32213.unusualprehistory.common.entity.custom.base.PrehistoricEntity;
 import com.peeko32213.unusualprehistory.common.entity.util.helper.HitboxAttacks;
 import com.peeko32213.unusualprehistory.common.entity.util.interfaces.IPackHunter;
 import com.peeko32213.unusualprehistory.common.entity.util.navigator.SmartBodyHelper;
 import com.peeko32213.unusualprehistory.common.entity.util.navigator.SmoothGroundNavigation;
 import com.peeko32213.unusualprehistory.core.registry.UPEntities;
 import com.peeko32213.unusualprehistory.core.registry.UPSounds;
 import net.minecraft.nbt.CompoundTag;
 import net.minecraft.network.syncher.EntityDataAccessor;
 import net.minecraft.network.syncher.EntityDataSerializers;
 import net.minecraft.network.syncher.SynchedEntityData;
 import net.minecraft.server.level.ServerLevel;
 import net.minecraft.world.DifficultyInstance;
 import net.minecraft.world.damagesource.DamageSource;
 import net.minecraft.world.entity.*;
 import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
 import net.minecraft.world.entity.ai.attributes.Attributes;
 import net.minecraft.world.entity.ai.control.BodyRotationControl;
 import net.minecraft.world.entity.ai.goal.*;
 import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
 import net.minecraft.world.entity.ai.navigation.PathNavigation;
 import net.minecraft.world.entity.animal.Pig;
 import net.minecraft.world.entity.player.Player;
 import net.minecraft.world.level.Level;
 import net.minecraft.world.level.ServerLevelAccessor;
 import net.minecraft.world.level.pathfinder.Node;
 import net.minecraft.world.level.pathfinder.Path;
 import net.minecraft.world.phys.Vec2;
 import net.minecraft.world.phys.Vec3;
 import org.jetbrains.annotations.NotNull;
 import org.jetbrains.annotations.Nullable;
 import software.bernie.geckolib.core.animation.AnimatableManager;
 import software.bernie.geckolib.core.animation.AnimationController;
 import software.bernie.geckolib.core.animation.AnimationState;
 import software.bernie.geckolib.core.animation.RawAnimation;
 import software.bernie.geckolib.core.keyframe.event.SoundKeyframeEvent;
 import software.bernie.geckolib.core.object.PlayState;

 import java.util.EnumSet;
 import java.util.List;

 public class PsilopterusEntity extends PrehistoricEntity implements IPackHunter {

     private static final EntityDataAccessor<Boolean> DOMINANT = SynchedEntityData.defineId(PsilopterusEntity.class, EntityDataSerializers.BOOLEAN);

     // Movement animations
     private static final RawAnimation PSILO_WALK = RawAnimation.begin().thenLoop("animation.psilopterus.walk");
     private static final RawAnimation PSILO_RUN = RawAnimation.begin().thenLoop("animation.psilopterus.run");
     private static final RawAnimation PSILO_SWIM = RawAnimation.begin().thenLoop("animation.psilopterus.swim");

     // Idle animations
     private static final RawAnimation PSILO_IDLE = RawAnimation.begin().thenLoop("animation.psilopterus.idle");
     private static final RawAnimation PSILO_SIT = RawAnimation.begin().thenLoop("animation.psilopterus.sit");
     private static final RawAnimation PSILO_SLEEP = RawAnimation.begin().thenLoop("animation.psilopterus.sleep");
     private static final RawAnimation PSILO_DIG = RawAnimation.begin().thenPlay("animation.psilopterus.dig");
     private static final RawAnimation PSILO_PREEN_1 = RawAnimation.begin().thenPlay("animation.psilopterus.preen1");
     private static final RawAnimation PSILO_PREEN_2 = RawAnimation.begin().thenPlay("animation.psilopterus.preen2");
     private static final RawAnimation PSILO_LOOKOUT_1 = RawAnimation.begin().thenPlay("animation.psilopterus.lookout1");
     private static final RawAnimation PSILO_LOOKOUT_2 = RawAnimation.begin().thenPlay("animation.psilopterus.lookout2");
     private static final RawAnimation PSILO_IDLE_BOOK = RawAnimation.begin().thenLoop("animation.psilopterus.idle_book");

     // Attack animations
     private static final RawAnimation PSILO_ATTACK_1 = RawAnimation.begin().thenPlay("animation.psilopterus.attack1");
     private static final RawAnimation PSILO_ATTACK_2 = RawAnimation.begin().thenPlay("animation.psilopterus.attack2");
     private static final RawAnimation PSILO_KICK = RawAnimation.begin().thenPlay("animation.psilopterus.kick");

     // Idle accessors
     private static final EntityDataAccessor<Boolean> IDLE_1_AC = SynchedEntityData.defineId(PsilopterusEntity.class, EntityDataSerializers.BOOLEAN);
     private static final EntityDataAccessor<Boolean> IDLE_2_AC = SynchedEntityData.defineId(PsilopterusEntity.class, EntityDataSerializers.BOOLEAN);
     private static final EntityDataAccessor<Boolean> IDLE_3_AC = SynchedEntityData.defineId(PsilopterusEntity.class, EntityDataSerializers.BOOLEAN);
     private static final EntityDataAccessor<Boolean> IDLE_4_AC = SynchedEntityData.defineId(PsilopterusEntity.class, EntityDataSerializers.BOOLEAN);
     private static final EntityDataAccessor<Boolean> IDLE_5_AC = SynchedEntityData.defineId(PsilopterusEntity.class, EntityDataSerializers.BOOLEAN);

     // Idle actions
     private static final EntityAction PSILO_IDLE_1_ACTION = new EntityAction(0, (e) -> {}, 1);

     private static final StateHelper PSILO_IDLE_1_STATE =
             StateHelper.Builder.state(IDLE_1_AC, "psilopterus_dig")
                     .playTime(60)
                     .stopTime(200)
                     .affectsAI(true)
                     .affectedFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK))
                     .entityAction(PSILO_IDLE_1_ACTION)
                     .build();

     private static final EntityAction PSILO_IDLE_2_ACTION = new EntityAction(0, (e) -> {}, 1);

     private static final StateHelper PSILO_IDLE_2_STATE =
             StateHelper.Builder.state(IDLE_2_AC, "psilopterus_preen_1")
                     .playTime(80)
                     .stopTime(160)
                     .entityAction(PSILO_IDLE_2_ACTION)
                     .build();

     private static final EntityAction PSILO_IDLE_3_ACTION = new EntityAction(0, (e) -> {}, 1);

     private static final StateHelper PSILO_IDLE_3_STATE =
             StateHelper.Builder.state(IDLE_3_AC, "psilopterus_preen_2")
                     .playTime(80)
                     .stopTime(160)
                     .entityAction(PSILO_IDLE_3_ACTION)
                     .build();

     private static final EntityAction PSILO_IDLE_4_ACTION = new EntityAction(0, (e) -> {}, 1);

     private static final StateHelper PSILO_IDLE_4_STATE =
             StateHelper.Builder.state(IDLE_4_AC, "psilopterus_lookout_1")
                     .playTime(60)
                     .stopTime(150)
                     .entityAction(PSILO_IDLE_4_ACTION)
                     .build();

     private static final EntityAction PSILO_IDLE_5_ACTION = new EntityAction(0, (e) -> {}, 1);

     private static final StateHelper PSILO_IDLE_5_STATE =
             StateHelper.Builder.state(IDLE_5_AC, "psilopterus_lookout_2")
                     .playTime(60)
                     .stopTime(150)
                     .entityAction(PSILO_IDLE_5_ACTION)
                     .build();

     private boolean hasDominantAttributes = false;
     private PsilopterusEntity priorPackMember;
     private PsilopterusEntity afterPackMember;

     // States
     @Override
     public ImmutableMap<String, StateHelper> getStates() {
         return ImmutableMap.of(
                 PSILO_IDLE_1_STATE.getName(), PSILO_IDLE_1_STATE,
                 PSILO_IDLE_2_STATE.getName(), PSILO_IDLE_2_STATE,
                 PSILO_IDLE_3_STATE.getName(), PSILO_IDLE_3_STATE,
                 PSILO_IDLE_4_STATE.getName(), PSILO_IDLE_4_STATE,
                 PSILO_IDLE_5_STATE.getName(), PSILO_IDLE_5_STATE
         );
     }

     @Override
     public List<WeightedState<StateHelper>> getWeightedStatesToPerform() {
         return ImmutableList.of(
                 WeightedState.of(PSILO_IDLE_1_STATE, 10),
                 WeightedState.of(PSILO_IDLE_2_STATE, 10),
                 WeightedState.of(PSILO_IDLE_3_STATE, 12),
                 WeightedState.of(PSILO_IDLE_4_STATE, 11),
                 WeightedState.of(PSILO_IDLE_5_STATE, 11)
         );
     }

     // Animation sounds
     private void soundListener(SoundKeyframeEvent<PsilopterusEntity> event) {
         PsilopterusEntity psilopterus = event.getAnimatable();
         if (event.getKeyframeData().getSound().equals("psilopterus_attack")) {
         }
     }

     // Animation control
     @Override
     public void registerControllers(final AnimatableManager.ControllerRegistrar controllers) {
         AnimationController<PsilopterusEntity> controller = new AnimationController<>(this, "controller", 5, this::predicate);
         controllers.add(controller);

         AnimationController<PsilopterusEntity> blend = new AnimationController<>(this, "blend", 5, this::predicate)
                 .triggerableAnim("preen_1", PSILO_PREEN_1)
                 .triggerableAnim("preen_2", PSILO_PREEN_2)
                 .triggerableAnim("lookout_1", PSILO_LOOKOUT_1)
                 .triggerableAnim("lookout_2", PSILO_LOOKOUT_2);
         blend.setSoundKeyframeHandler(this::soundListener);
         controllers.add(blend);

         AnimationController<PsilopterusEntity> attack = new AnimationController<>(this, "attackController", 5, this::attackPredicate);
         attack.setSoundKeyframeHandler(this::soundListener);
         controllers.add(attack);
     }

     protected <E extends PsilopterusEntity> PlayState predicate(final software.bernie.geckolib.core.animation.AnimationState<E> event) {

         if(this.isFromBook()){
             return event.setAndContinue(PSILO_IDLE_BOOK);
         }

         if (this.isInWater()) {
             event.setAndContinue(PSILO_SWIM);
             event.getController().setAnimationSpeed(1.0F);
             return PlayState.CONTINUE;
         }

         if(this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6 && !this.isSwimming() && ! this.isInWater() && !isStillEnough()){
             if(this.isSprinting()) {
                 event.setAndContinue(PSILO_RUN);
             } else {
                 event.setAndContinue(PSILO_WALK);
             }
             event.getController().setAnimationSpeed(1.0F);
             return PlayState.CONTINUE;
         }

         if (!this.isInWater()) {
             if (getBooleanState(IDLE_1_AC)) {
                 return event.setAndContinue(PSILO_DIG);
             }
             if (getBooleanState(IDLE_2_AC)) {
                 if (this.isStillEnough()) {
                     triggerAnim("blend", "preen_1");
                     return event.setAndContinue(PSILO_IDLE);
                 } else {
                     triggerAnim("blend", "preen_1");
                     return PlayState.CONTINUE;
                 }
             }
             if (getBooleanState(IDLE_3_AC)) {
                 if (this.isStillEnough()) {
                     triggerAnim("blend", "preen_2");
                     return event.setAndContinue(PSILO_IDLE);
                 } else {
                     triggerAnim("blend", "preen_2");
                     return PlayState.CONTINUE;
                 }
             }
             if (getBooleanState(IDLE_4_AC)) {
                 if (this.isStillEnough()) {
                     triggerAnim("blend", "lookout_1");
                     return event.setAndContinue(PSILO_IDLE);
                 } else {
                     triggerAnim("blend", "lookout_1");
                     return PlayState.CONTINUE;
                 }
             }
             if (getBooleanState(IDLE_5_AC)) {
                 if (this.isStillEnough()) {
                     triggerAnim("blend", "lookout_1");
                     return event.setAndContinue(PSILO_IDLE);
                 } else {
                     triggerAnim("blend", "lookout_2");
                     return PlayState.CONTINUE;
                 }
             }
             return event.setAndContinue(PSILO_IDLE);
         }
         return PlayState.CONTINUE;
     }

     // Attack animations
     protected <E extends PsilopterusEntity> PlayState attackPredicate(final AnimationState<E> event) {
         int animState = this.getAnimationState();

         if (animState == 21) {
             event.setAndContinue(PSILO_ATTACK_1);
             return PlayState.CONTINUE;
         }
         else if (animState == 22) {
             event.setAndContinue(PSILO_ATTACK_2);
             return PlayState.CONTINUE;
         }
         else if (animState == 23) {
             event.setAndContinue(PSILO_KICK);
             return PlayState.CONTINUE;
         }
         else if (animState == 0) {
             event.getController().forceAnimationReset();
             return PlayState.STOP;
         }
         else return PlayState.CONTINUE;
     }

     // Body control / navigation
     @Override
     protected @NotNull BodyRotationControl createBodyControl() {
         SmartBodyHelper helper = new SmartBodyHelper(this);
         helper.bodyLagMoving = 0.4F;
         helper.bodyLagStill = 0.25F;
         return helper;
     }

     @Override
     protected @NotNull PathNavigation createNavigation(Level levelIn) {
         return new SmoothGroundNavigation(this, levelIn);
     }

     public PsilopterusEntity(EntityType<? extends PrehistoricEntity> entityType, Level level) {
         super(entityType, level);
         this.setMaxUpStep(1.25F);
     }

     // Attributes
     public static AttributeSupplier.Builder createAttributes() {
         return Mob.createMobAttributes()
             .add(Attributes.MAX_HEALTH, 16.0D)
             .add(Attributes.MOVEMENT_SPEED, 0.25D)
             .add(Attributes.ATTACK_DAMAGE, 6.0D);
     }

     // Goals
     protected void registerGoals() {
         this.goalSelector.addGoal(2, new RandomStateGoal<>(this));
         this.goalSelector.addGoal(0, new FloatGoal(this));
         this.goalSelector.addGoal(1, new PsilopterusEntity.PsiloMeleeAttackGoal(this,  1.3F, true));
         this.targetSelector.addGoal(5, new PackHunterGoal(this, Player.class, 30, false, 5));
         this.targetSelector.addGoal(5, new PackHunterGoal(this, Pig.class, 30, false, 3));
         this.goalSelector.addGoal(5, new JoinPackGoal(this, 60, 8));
         this.goalSelector.addGoal(3, new BabyPanicGoal(this, 2.0D));
         this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 1.0F, 30));
         this.goalSelector.addGoal(5, new FollowParentGoal(this, 1.1D));
         this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 15.0F));
         this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
         this.targetSelector.addGoal(4, (new HurtByTargetGoal(this, PsilopterusEntity.class)).setAlertOthers());
     }

     @Override
     public void travel(Vec3 pTravelVector) {
         super.travel(pTravelVector);
     }

     public void tick() {
         super.tick();

         LivingEntity target = this.getTarget();
         if (target != null && target.isAlive() && !(target instanceof Player player && player.isCreative())) {
             if (this.isDominant()) {
                 IPackHunter leader = this;
                 while (leader.getAfterPackMember() != null) {
                     leader = leader.getAfterPackMember();
                     if(!((PsilopterusEntity) leader).isAlliedTo(target)){
                         ((PsilopterusEntity) leader).setTarget(target);
                     }
                 }
             }
             if (this.getHealth() < this.getMaxHealth() * 0.45F) {
                 int i = 80 + random.nextInt(40);
                 if (target instanceof Mob mob) {
                     mob.setTarget(null);
                     mob.setLastHurtByMob(null);
                     mob.setLastHurtMob(null);
                 }
             }
             if (target instanceof Player && (tickCount + this.getId()) % 20 == 0 && getPackSize() < 4) {
                 this.setTarget(null);
                 this.setLastHurtByMob(null);
             }
         }
         if (isDominant() && !hasDominantAttributes) {
             hasDominantAttributes = true;
             this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(20.0D);
             this.getAttribute(Attributes.ARMOR).setBaseValue(5.0D);
             this.heal(25.0F);
         }
         if (!isDominant() && hasDominantAttributes) {
             hasDominantAttributes = false;
             this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(15.0D);
             this.getAttribute(Attributes.ARMOR).setBaseValue(0.0D);
             this.heal(20.0F);
         }
     }

     @Override
     public void aiStep() {
         super.aiStep();

         Vec3 vec3 = this.getDeltaMovement();
         if (!this.onGround() && vec3.y < 0.0) {
             this.setDeltaMovement(vec3.multiply(1.0, 0.75, 1.0));
         }
     }

     @Override
     public void customServerAiStep() {
         if (this.getMoveControl().hasWanted() && !this.isBaby()) {
             this.setSprinting(this.getMoveControl().getSpeedModifier() >= 1.25D);
         } else {
             this.setSprinting(false);
         }
         super.customServerAiStep();
     }

     // Save data
     @Override
     public void addAdditionalSaveData(CompoundTag compound) {
         super.addAdditionalSaveData(compound);
         this.setDominant(compound.getBoolean("Elder"));
     }

     @Override
     public void readAdditionalSaveData(CompoundTag compound) {
         super.readAdditionalSaveData(compound);
         compound.putBoolean("Elder", this.isDominant());
     }

     // Synched data
     @Override
     protected void defineSynchedData() {
         super.defineSynchedData();
         this.entityData.define(IDLE_1_AC, false);
         this.entityData.define(IDLE_2_AC, false);
         this.entityData.define(IDLE_3_AC, false);
         this.entityData.define(IDLE_4_AC, false);
         this.entityData.define(IDLE_5_AC, false);
         this.entityData.define(DOMINANT, false);
     }

     // Dominant
     public boolean isDominant() {
         return this.entityData.get(DOMINANT);
     }

     public void setDominant(boolean bool) {
         this.entityData.set(DOMINANT, bool);
     }

     // Pack members
     @Override
     public IPackHunter getPriorPackMember() {
         return this.priorPackMember;
     }

     @Override
     public IPackHunter getAfterPackMember() {
         return afterPackMember;
     }

     @Override
     public void setPriorPackMember(IPackHunter animal) {
         this.priorPackMember = (PsilopterusEntity) animal;
     }

     @Override
     public void setAfterPackMember(IPackHunter animal) {
         this.afterPackMember = (PsilopterusEntity) animal;
     }

     @Nullable
     public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficultyIn, MobSpawnType reason, @Nullable SpawnGroupData spawnDataIn, @Nullable CompoundTag dataTag) {
         if (spawnDataIn instanceof AgeableMob.AgeableMobGroupData) {
             AgeableMob.AgeableMobGroupData data = (AgeableMob.AgeableMobGroupData) spawnDataIn;
             if (data.getGroupSize() == 0) {
                 this.setDominant(true);
             }
         } else {
             this.setDominant(this.getRandom().nextInt(2) == 0);
         }
         return super.finalizeSpawn(level, difficultyIn, reason, spawnDataIn, dataTag);
     }

     // todo: move goal to ai/goals/attack/
     static class PsiloMeleeAttackGoal extends Goal {

         protected final PsilopterusEntity mob;
         private final double speedModifier;
         private final boolean followingTargetEvenIfNotSeen;
         private Path path;
         private double pathedTargetX;
         private double pathedTargetY;
         private double pathedTargetZ;
         private int ticksUntilNextPathRecalculation;
         private int ticksUntilNextAttack;
         private long lastCanUseCheck;
         private int failedPathFindingPenalty = 0;
         private boolean canPenalize = false;
         private int animTime = 0;


         public PsiloMeleeAttackGoal(PsilopterusEntity p_i1636_1_, double p_i1636_2_, boolean p_i1636_4_) {
             this.mob = p_i1636_1_;
             this.speedModifier = p_i1636_2_;
             this.followingTargetEvenIfNotSeen = p_i1636_4_;
             this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
         }

         public boolean canUse() {
             long i = this.mob.level().getGameTime();

             if (i - this.lastCanUseCheck < 20L) {
                 return false;
             } else {
                 this.lastCanUseCheck = i;
                 LivingEntity livingentity = this.mob.getTarget();
                 if (livingentity == null) {
                     return false;
                 } else if (!livingentity.isAlive()) {
                     return false;
                 } else {
                     if (canPenalize) {
                         if (--this.ticksUntilNextPathRecalculation <= 0) {
                             this.path = this.mob.getNavigation().createPath(livingentity, 0);
                             this.ticksUntilNextPathRecalculation = 4 + this.mob.getRandom().nextInt(7);
                             return this.path != null;
                         } else {
                             return true;
                         }
                     }
                     this.path = this.mob.getNavigation().createPath(livingentity, 0);
                     if (this.path != null) {
                         return true;
                     } else {
                         return this.getAttackReachSqr(livingentity) >= this.mob.distanceToSqr(livingentity.getX(), livingentity.getY(), livingentity.getZ());
                     }
                 }
             }

         }

         public boolean canContinueToUse() {

             LivingEntity livingentity = this.mob.getTarget();

             if (livingentity == null) {
                 return false;
             } else if (!livingentity.isAlive()) {
                 return false;
             } else if (!this.followingTargetEvenIfNotSeen) {
                 return !this.mob.getNavigation().isDone();
             } else if (!this.mob.isWithinRestriction(livingentity.blockPosition())) {
                 return false;
             } else {
                 return !(livingentity instanceof Player) || !livingentity.isSpectator() && !((Player) livingentity).isCreative();
             }

         }

         public void start() {
             this.mob.getNavigation().moveTo(this.path, this.speedModifier);
             this.mob.setAggressive(true);
             this.ticksUntilNextPathRecalculation = 0;
             this.ticksUntilNextAttack = 0;
             this.animTime = 0;
             this.mob.setAnimationState(0);

         }

         public void stop() {
             LivingEntity livingentity = this.mob.getTarget();
             if (!EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(livingentity)) {
                 this.mob.setTarget(null);
             }
             this.mob.setAnimationState(0);
             this.mob.setAggressive(false);
         }

         public void tick() {


             LivingEntity target = this.mob.getTarget();
             double distance = this.mob.distanceToSqr(target.getX(), target.getY(), target.getZ());
             double reach = this.getAttackReachSqr(target);
             int animState = this.mob.getAnimationState();
             Vec3 aim = this.mob.getLookAngle();
             Vec2 aim2d = new Vec2((float) (aim.x / (1 - Math.abs(aim.y))), (float) (aim.z / (1 - Math.abs(aim.y))));


             switch (animState) {
                 case 21 -> tickLightAttack1();
                 case 22 -> tickLightAttack2();
                 case 23 -> tickKickAttack();
                 default -> {
                     this.ticksUntilNextPathRecalculation = Math.max(this.ticksUntilNextPathRecalculation - 1, 0);
                     this.ticksUntilNextAttack = Math.max(this.ticksUntilNextPathRecalculation - 1, 0);
                     this.mob.getLookControl().setLookAt(target, 30.0F, 30.0F);
                     this.doMovement(target, distance);
                     this.checkForCloseRangeAttack(distance, reach);
                 }
             }

         }

         protected void doMovement (LivingEntity livingentity, Double d0){


             this.ticksUntilNextPathRecalculation = Math.max(this.ticksUntilNextPathRecalculation - 1, 0);


             if ((this.followingTargetEvenIfNotSeen || this.mob.getSensing().hasLineOfSight(livingentity)) && this.ticksUntilNextPathRecalculation <= 0 && (this.pathedTargetX == 0.0D && this.pathedTargetY == 0.0D && this.pathedTargetZ == 0.0D || livingentity.distanceToSqr(this.pathedTargetX, this.pathedTargetY, this.pathedTargetZ) >= 1.0D || this.mob.getRandom().nextFloat() < 0.05F)) {
                 this.pathedTargetX = livingentity.getX();
                 this.pathedTargetY = livingentity.getY();
                 this.pathedTargetZ = livingentity.getZ();
                 this.ticksUntilNextPathRecalculation = 4 + this.mob.getRandom().nextInt(7);
                 if (this.canPenalize) {
                     this.ticksUntilNextPathRecalculation += failedPathFindingPenalty;
                     if (this.mob.getNavigation().getPath() != null) {
                         Node finalPathPoint = this.mob.getNavigation().getPath().getEndNode();
                         if (finalPathPoint != null && livingentity.distanceToSqr(finalPathPoint.x, finalPathPoint.y, finalPathPoint.z) < 1)
                             failedPathFindingPenalty = 0;
                         else
                             failedPathFindingPenalty += 10;
                     } else {
                         failedPathFindingPenalty += 10;
                     }
                 }
                 if (d0 > 1024.0D) {
                     this.ticksUntilNextPathRecalculation += 10;
                 } else if (d0 > 256.0D) {
                     this.ticksUntilNextPathRecalculation += 5;
                 }

                 if (!this.mob.getNavigation().moveTo(livingentity, this.speedModifier)) {
                     this.ticksUntilNextPathRecalculation += 15;
                 }
             }

         }


         protected void checkForCloseRangeAttack ( double distance, double reach){
             if (distance <= reach && this.ticksUntilNextAttack <= 0) {


                 int r = this.mob.getRandom().nextInt(2048);
                 if (r <= 800) {
                     this.mob.setAnimationState(21);
                 } else if (r <= 1300) {
                     this.mob.setAnimationState(22);
                 } else {
                     this.mob.setAnimationState(23);
                 }

             }
         }


         protected boolean getRangeCheck () {

             return
                     this.mob.distanceToSqr(this.mob.getTarget().getX(), this.mob.getTarget().getY(), this.mob.getTarget().getZ())
                             <=
                             1.3F * this.getAttackReachSqr(this.mob.getTarget());

         }



         protected void tickLightAttack1 () {
             animTime++;
             if(animTime==4) {
                 performLightAttack();
             }
             if(animTime>=8) {
                 animTime=0;
                 if (this.getRangeCheck()) {
                     this.mob.setAnimationState(22);
                 }else {
                     this.mob.setAnimationState(0);
                     this.resetAttackCooldown();
                     this.ticksUntilNextPathRecalculation = 0;
                 }
             }
         }

         protected void tickLightAttack2 () {
             animTime++;

             if(animTime==4) {
                 performLightAttack();
             }
             if(animTime>=7) {
                 animTime=0;

                 this.mob.setAnimationState(0);
                 this.resetAttackCooldown();
                 this.ticksUntilNextPathRecalculation = 0;

             }

         }

         protected void tickKickAttack () {
             animTime++;
             if(animTime==7) {
                 performAttackKick();
             }
             if(animTime>=12) {
                 animTime=0;
                 this.mob.setAnimationState(0);
                 this.resetAttackCooldown();
                 this.ticksUntilNextPathRecalculation = 0;
             }
         }


         protected void performLightAttack () {
             Vec3 pos = mob.position();
             this.mob.playSound(UPSounds.PACHY_HEADBUTT.get(), 2.0f, 0.2f);
             HitboxAttacks.largeAttackWithTargetCheck(this.mob.damageSources().mobAttack(mob),3.0f, 0.1f, mob, pos,  2.1F, -Math.PI/5, Math.PI/3, -1.0f, 3.0f, false);
         }

         protected void performAttackKick () {
             Vec3 pos = mob.position();
             this.mob.playSound(UPSounds.PACHY_KICK.get(), 0.5F, 0.5F);
             HitboxAttacks.largeAttackWithTargetCheck(this.mob.damageSources().mobAttack(mob),6.0f, 1.0f, mob, pos,  2.1F, -Math.PI/5, Math.PI/3, -1.0f, 3.0f, false);
         }

         protected void resetAttackCooldown () {
             this.ticksUntilNextAttack = 0;
         }

         protected boolean isTimeToAttack () {
             return this.ticksUntilNextAttack <= 0;
         }

         protected int getTicksUntilNextAttack () {
             return this.ticksUntilNextAttack;
         }

         protected int getAttackInterval () {
             return 5;
         }

         protected double getAttackReachSqr(LivingEntity p_25556_) {
             return this.mob.getBbWidth() * 2.0F * this.mob.getBbWidth() * 1.3F + p_25556_.getBbWidth();
         }
     }

     @Override
     protected int getKillHealAmount() {
         return 4;
     }

     public boolean causeFallDamage(float fallDistance, float multiplier, DamageSource source) {
         return false;
     }

     @Nullable
     @Override
     public AgeableMob getBreedOffspring(@NotNull ServerLevel serverLevel, @NotNull AgeableMob ageableMob) {
         PsilopterusEntity psilopterus = UPEntities.PSILOPTERUS.get().create(serverLevel);
         psilopterus.setDominant(this.isDominant());
         return psilopterus;
     }
 }
