 package com.peeko32213.unusualprehistory.common.entity.custom.prehistoric;

 import com.peeko32213.unusualprehistory.common.entity.custom.base.PrehistoricEntity;
 import com.peeko32213.unusualprehistory.common.entity.util.goal.BabyPanicGoal;
 import com.peeko32213.unusualprehistory.common.entity.util.goal.JoinPackGoal;
 import com.peeko32213.unusualprehistory.common.entity.util.goal.PackHunterGoal;
 import com.peeko32213.unusualprehistory.common.entity.util.helper.HitboxHelper;
 import com.peeko32213.unusualprehistory.common.entity.util.interfaces.IPackHunter;
 import com.peeko32213.unusualprehistory.core.registry.UPSounds;
 import net.minecraft.nbt.CompoundTag;
 import net.minecraft.network.syncher.EntityDataAccessor;
 import net.minecraft.network.syncher.EntityDataSerializers;
 import net.minecraft.network.syncher.SynchedEntityData;
 import net.minecraft.server.level.ServerLevel;
 import net.minecraft.sounds.SoundEvent;
 import net.minecraft.tags.TagKey;
 import net.minecraft.world.DifficultyInstance;
 import net.minecraft.world.entity.*;
 import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
 import net.minecraft.world.entity.ai.attributes.Attributes;
 import net.minecraft.world.entity.ai.goal.*;
 import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
 import net.minecraft.world.entity.animal.Animal;
 import net.minecraft.world.entity.animal.Pig;
 import net.minecraft.world.entity.player.Player;
 import net.minecraft.world.level.Level;
 import net.minecraft.world.level.ServerLevelAccessor;
 import net.minecraft.world.level.block.state.BlockState;
 import net.minecraft.world.level.pathfinder.Node;
 import net.minecraft.world.level.pathfinder.Path;
 import net.minecraft.world.phys.Vec2;
 import net.minecraft.world.phys.Vec3;
 import org.jetbrains.annotations.Nullable;
 import software.bernie.geckolib.core.animation.AnimatableManager;
 import software.bernie.geckolib.core.animation.AnimationController;
 import software.bernie.geckolib.core.animation.RawAnimation;
 import software.bernie.geckolib.core.object.PlayState;

 import java.util.EnumSet;

 public class PsilopterusEntity extends PrehistoricEntity implements IPackHunter {

     private static final EntityDataAccessor<Integer> ANIMATION_STATE = SynchedEntityData.defineId(PsilopterusEntity.class, EntityDataSerializers.INT);
     private static final EntityDataAccessor<Integer> COMBAT_STATE = SynchedEntityData.defineId(PsilopterusEntity.class, EntityDataSerializers.INT);
     private static final EntityDataAccessor<Integer> ENTITY_STATE = SynchedEntityData.defineId(PsilopterusEntity.class, EntityDataSerializers.INT);
     private static final EntityDataAccessor<Boolean> DOMINATE = SynchedEntityData.defineId(PsilopterusEntity.class, EntityDataSerializers.BOOLEAN);

     private static final RawAnimation PSILO_IDLE = RawAnimation.begin().thenLoop("animation.psilopterus.idle");
     private static final RawAnimation PSILO_SIT = RawAnimation.begin().thenLoop("animation.psilopterus.sit");
     private static final RawAnimation PSILO_SLEEP = RawAnimation.begin().thenLoop("animation.psilopterus.sleep");
     private static final RawAnimation PSILO_WALK = RawAnimation.begin().thenLoop("animation.psilopterus.walk");
     private static final RawAnimation PSILO_RUN = RawAnimation.begin().thenLoop("animation.psilopterus.run");
     private static final RawAnimation PSILO_SWIM = RawAnimation.begin().thenLoop("animation.psilopterus.swim");
     private static final RawAnimation PSILO_DIG = RawAnimation.begin().thenLoop("animation.psilopterus.dig");
     private static final RawAnimation PSILO_PREEN_1 = RawAnimation.begin().thenLoop("animation.psilopterus.preen1");
     private static final RawAnimation PSILO_PREEN_2 = RawAnimation.begin().thenLoop("animation.psilopterus.preen2");
     private static final RawAnimation PSILO_LOOKOUT_1 = RawAnimation.begin().thenLoop("animation.psilopterus.lookout1");
     private static final RawAnimation PSILO_LOOKOUT_2 = RawAnimation.begin().thenLoop("animation.psilopterus.lookout2");
     private static final RawAnimation PSILO_ATTACK_1 = RawAnimation.begin().thenLoop("animation.psilopterus.attack1");
     private static final RawAnimation PSILO_ATTACK_2 = RawAnimation.begin().thenLoop("animation.psilopterus.attack2");
     private static final RawAnimation PSILO_KICK = RawAnimation.begin().thenLoop("animation.psilopterus.kick");

     private static final RawAnimation PSILO_IDLE_BOOK = RawAnimation.begin().thenLoop("animation.psilopterus.idle_book");

     private boolean hasDominateAttributes = false;
     private PsilopterusEntity priorPackMember;
     private PsilopterusEntity afterPackMember;
     public PsilopterusEntity(EntityType<? extends Animal> entityType, Level level) {
         super(entityType, level);
         this.setMaxUpStep(1.25F);
     }

     public static AttributeSupplier.Builder createAttributes() {
         return Mob.createMobAttributes()
                 .add(Attributes.MAX_HEALTH, 15.0D)
                 .add(Attributes.ARMOR, 0.0D)
                 .add(Attributes.MOVEMENT_SPEED, 0.25D)
                 .add(Attributes.ATTACK_DAMAGE, 8.0D)
                 .add(Attributes.KNOCKBACK_RESISTANCE, 0.2D)
                 .add(Attributes.ATTACK_KNOCKBACK, 0.1D);
     }

     protected void registerGoals() {
         super.registerGoals();
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

         if(playingAnimation()){
             super.travel(Vec3.ZERO);
         }
         super.travel(pTravelVector);
     }

     public void tick() {
         super.tick();

         LivingEntity target = this.getTarget();
         if (target != null && target.isAlive() && !(target instanceof Player player && player.isCreative())) {
             if (this.isDominate()) {
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
         if (isDominate() && !hasDominateAttributes) {
             hasDominateAttributes = true;
             this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(20.0D);
             this.getAttribute(Attributes.ARMOR).setBaseValue(5.0D);
             this.heal(25.0F);
         }
         if (!isDominate() && hasDominateAttributes) {
             hasDominateAttributes = false;
             this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(15.0D);
             this.getAttribute(Attributes.ARMOR).setBaseValue(0.0D);
             this.heal(20.0F);
         }
     }

     @Override
     protected void defineSynchedData() {
         super.defineSynchedData();
         this.entityData.define(ANIMATION_STATE, 0);
         this.entityData.define(COMBAT_STATE, 0);
         this.entityData.define(ENTITY_STATE, 0);
         this.entityData.define(DOMINATE, false);

     }

     public boolean isDominate() {
         return this.entityData.get(DOMINATE);
     }

     public void setDominate(boolean bool) {
         this.entityData.set(DOMINATE, bool);
     }

     @Override
     public void addAdditionalSaveData(CompoundTag compound) {
         super.addAdditionalSaveData(compound);
         this.setDominate(compound.getBoolean("Elder"));
     }

     @Override
     public void readAdditionalSaveData(CompoundTag compound) {
         super.readAdditionalSaveData(compound);
         compound.putBoolean("Elder", this.isDominate());
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

     @javax.annotation.Nullable
     public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficultyIn, MobSpawnType reason, @javax.annotation.Nullable SpawnGroupData spawnDataIn, @javax.annotation.Nullable CompoundTag dataTag) {
         if (spawnDataIn instanceof AgeableMob.AgeableMobGroupData) {
             AgeableMob.AgeableMobGroupData data = (AgeableMob.AgeableMobGroupData) spawnDataIn;
             if (data.getGroupSize() == 0) {
                 this.setDominate(true);
             }
         } else {
             this.setDominate(this.getRandom().nextInt(2) == 0);
         }
         return super.finalizeSpawn(level, difficultyIn, reason, spawnDataIn, dataTag);
     }

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
             HitboxHelper.LargeAttackWithTargetCheck(this.mob.damageSources().mobAttack(mob),3.0f, 0.1f, mob, pos,  2.1F, -Math.PI/5, Math.PI/3, -1.0f, 3.0f);

         }


         protected void performAttackKick () {

             Vec3 pos = mob.position();
             this.mob.playSound(UPSounds.PACHY_KICK.get(), 0.5F, 0.5F);
             HitboxHelper.LargeAttackWithTargetCheck(this.mob.damageSources().mobAttack(mob),6.0f, 1.0f, mob, pos,  2.1F, -Math.PI/5, Math.PI/3, -1.0f, 3.0f);

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
             return 3;
         }

         protected double getAttackReachSqr(LivingEntity p_25556_) {
             return (double)(this.mob.getBbWidth() * 2.0F * this.mob.getBbWidth() * 1.3F + p_25556_.getBbWidth());
         }
     }

     @Override
     protected SoundEvent getAttackSound() {
         return null;
     }

     @Override
     protected int getKillHealAmount() {
         return 4;
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

     private boolean isStillEnough() {
         return this.getDeltaMovement().horizontalDistance() < 0.05;
     }

     protected <E extends PsilopterusEntity> PlayState Controller(final software.bernie.geckolib.core.animation.AnimationState<E> event) {
         if(this.isFromBook()){
             return event.setAndContinue(PSILO_IDLE_BOOK);
         }

         int animState = this.getAnimationState();
         {
             switch (animState) {

                 case 21:
                     event.setAndContinue(PSILO_ATTACK_1);
                     break;
                 case 22:
                     event.setAndContinue(PSILO_ATTACK_2);
                     break;
                 case 23:
                     event.setAndContinue(PSILO_KICK);
                     break;
                 default:
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
                     if(playingAnimation()){
                         return PlayState.CONTINUE;
                     }

                     if (isStillEnough() && getRandomAnimationNumber() == 0 && !this.isSwimming()) {
                         int rand = getRandomAnimationNumber();
                         if (rand < 40) {
                             setAnimationTimer(150);
                             event.setAndContinue(PSILO_SIT);
                         }
                         if (rand < 60) {
                             setAnimationTimer(150);
                             event.setAndContinue(PSILO_PREEN_1);
                         }
                         if (rand < 70) {
                             setAnimationTimer(150);
                             event.setAndContinue(PSILO_PREEN_2);
                         }
                         if (rand < 80F) {
                             setAnimationTimer(150);
                             event.setAndContinue(PSILO_LOOKOUT_1);
                         }
                         if (rand < 90F) {
                             setAnimationTimer(150);
                             event.setAndContinue(PSILO_LOOKOUT_2);
                         }
                         event.setAndContinue(PSILO_IDLE);
                     }
             }
             return PlayState.CONTINUE;
         }
     }

     @Override
     public void registerControllers(final AnimatableManager.ControllerRegistrar controllers) {
         controllers.add(new AnimationController<>(this, "Normal", 5, this::Controller));
     }

     @Override
     public double getTick(Object o) {
         return tickCount;
     }
 }
