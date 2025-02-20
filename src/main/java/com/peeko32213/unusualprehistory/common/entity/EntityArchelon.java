 package com.peeko32213.unusualprehistory.common.entity;

 import com.peeko32213.unusualprehistory.common.entity.msc.anim_goal.AnimationHelper;
 import com.peeko32213.unusualprehistory.common.entity.msc.util.dino.EntityTameableBaseDinosaurAnimal;
 import com.peeko32213.unusualprehistory.common.entity.msc.util.goal.CustomRideGoal;
 import com.peeko32213.unusualprehistory.common.entity.msc.util.goal.TameableFollowOwner;
 import com.peeko32213.unusualprehistory.common.entity.msc.util.interfaces.CustomFollower;
 import net.minecraft.core.BlockPos;
 import net.minecraft.nbt.CompoundTag;
 import net.minecraft.network.chat.Component;
 import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
 import net.minecraft.network.syncher.EntityDataAccessor;
 import net.minecraft.network.syncher.EntityDataSerializers;
 import net.minecraft.network.syncher.SynchedEntityData;
 import net.minecraft.server.level.ServerLevel;
 import net.minecraft.server.level.ServerPlayer;
 import net.minecraft.sounds.SoundEvent;
 import net.minecraft.tags.FluidTags;
 import net.minecraft.tags.TagKey;
 import net.minecraft.util.Mth;
 import net.minecraft.util.RandomSource;
 import net.minecraft.world.InteractionHand;
 import net.minecraft.world.InteractionResult;
 import net.minecraft.world.effect.MobEffectInstance;
 import net.minecraft.world.effect.MobEffectUtil;
 import net.minecraft.world.effect.MobEffects;
 import net.minecraft.world.entity.*;
 import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
 import net.minecraft.world.entity.ai.attributes.Attributes;
 import net.minecraft.world.entity.ai.control.MoveControl;
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
 import software.bernie.geckolib.animatable.GeoEntity;
 import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
 import software.bernie.geckolib.core.animation.AnimatableManager;
 import software.bernie.geckolib.core.animation.AnimationController;
 import software.bernie.geckolib.core.animation.RawAnimation;
 import software.bernie.geckolib.core.object.PlayState;
 import software.bernie.geckolib.util.GeckoLibUtil;

 import javax.annotation.Nonnull;
 import java.util.List;

 public class EntityArchelon extends EntityTameableBaseDinosaurAnimal implements CustomFollower, GeoEntity {
     private static final EntityDataAccessor<Integer> COMMAND = SynchedEntityData.defineId(EntityArchelon.class, EntityDataSerializers.INT);
     private static final EntityDataAccessor<Boolean> SADDLED = SynchedEntityData.defineId(EntityArchelon.class, EntityDataSerializers.BOOLEAN);
     private static final EntityDataAccessor<Integer> CHILL_TIME = SynchedEntityData.defineId(EntityArchelon.class, EntityDataSerializers.INT);
     private static final EntityDataAccessor<Boolean> TRAVELLING = SynchedEntityData.defineId(EntityArchelon.class, EntityDataSerializers.BOOLEAN);
     private static final EntityDataAccessor<BlockPos> TRAVEL_POS = SynchedEntityData.defineId(EntityArchelon.class, EntityDataSerializers.BLOCK_POS);

     private static final RawAnimation ARCHELON_IDLE = RawAnimation.begin().thenLoop("animation.archelon.idle");
     private static final RawAnimation ARCHELON_WALK = RawAnimation.begin().thenLoop("animation.archelon.walk");
     private static final RawAnimation ARCHELON_SWIM_IDLE = RawAnimation.begin().thenLoop("animation.archelon.swim_idle");
     private static final AnimationHelper ARCHELON_SWIM = AnimationHelper.loopingAnimation("swim","animation.archelon.swim");
     private static final RawAnimation ARCHELON_BITE_BLEND = RawAnimation.begin().thenLoop("animation.archelon.bite_blend");
     private static final AnimationHelper ARCHELON_RAMMING = AnimationHelper.playAnimationWController("Normal","rammin","animation.archelon.ramming").addLoop(ARCHELON_SWIM.getGetRawAnimName());
     private static final RawAnimation ARCHELON_SHAKE = RawAnimation.begin().thenLoop("animation.archelon.shake");
     //private static final RawAnimation ARCHELON_SPIN1 = RawAnimation.begin().thenLoop("animation.archelon.spin1");
     //private static final RawAnimation ARCHELON_SPIN2 = RawAnimation.begin().thenLoop("animation.archelon.spin2");

     private static final AnimationHelper ARCHELON_SPIN = AnimationHelper.playAnimationWController("Normal","spin1", "animation.archelon.spin1");
     private static final AnimationHelper ARCHELON_SPIN2 = AnimationHelper.playAnimationWController("Normal","spin2", "animation.archelon.spin2");

     private static final RawAnimation ARCHELON_HEART_SPIN = RawAnimation.begin().thenLoop("animation.archelon.heartspin");


     private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

     public float sitProgress;

     public EntityArchelon(EntityType<? extends EntityTameableBaseDinosaurAnimal> entityType, Level level) {
         super(entityType, level);
         this.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
         this.setPathfindingMalus(BlockPathTypes.WATER_BORDER, 0.0F);
         this.moveControl = new EntityArchelon.TurtleMoveControl(this);
     }

     public static AttributeSupplier.Builder createAttributes() {
         return Mob.createMobAttributes()
                 .add(Attributes.MAX_HEALTH, 60.0D)
                 .add(Attributes.MOVEMENT_SPEED, 0.2D)
                 .add(Attributes.ATTACK_DAMAGE, 1.0D)
                 .add(Attributes.ARMOR, 10D)
                 .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D)
                 ;
     }


     protected void registerGoals() {
         this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
         this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
         this.goalSelector.addGoal(1, new CustomRideGoal(this, 3D));
         this.goalSelector.addGoal(3, new EntityArchelon.TurtleGoToWaterGoal(this, 1.0D));
         this.goalSelector.addGoal(7, new EntityArchelon.TurtleTravelGoal(this, 1.0D));
         this.goalSelector.addGoal(9, new EntityArchelon.TurtleRandomStrollGoal(this, 1.0D, 100));
         //this.goalSelector.addGoal(3, new TameableFollowOwner(this, 1.2D, 5.0F, 2.0F, false));
         this.goalSelector.addGoal(0, new SitWhenOrderedToGoal(this));
         this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)));
         this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
         this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
     }

     public boolean isFood(ItemStack pStack) {
         Item item = pStack.getItem();
         return item.isEdible() && pStack.getFoodProperties(this).isMeat();
     }

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
                 if(!this.level().isClientSide) {
                     this.heal((float) itemstack.getFoodProperties(this).getNutrition());
                 }
                 this.gameEvent(GameEvent.EAT, this);
                 return InteractionResult.SUCCESS;
             }
             //else if (itemstack.getItem() == Items.SADDLE && !this.isSaddled()) {
             //    this.usePlayerItem(player, hand, itemstack);
             //    this.setSaddled(true);
             //    return InteractionResult.SUCCESS;
             //}
             //else if (itemstack.getItem() == Items.SHEARS && this.isSaddled()) {
             //    this.setSaddled(false);
             //    this.spawnAtLocation(Items.SADDLE);
             //    return InteractionResult.SUCCESS;
             //}

             else {
                 //if (!player.isShiftKeyDown() && !this.isBaby() && this.isSaddled()) {
                 //    if(!this.level().isClientSide) {
                 //        player.startRiding(this);
                 //    }
                 //    return InteractionResult.SUCCESS;
                 //} else {
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
         return InteractionResult.PASS;
     }

     public void tick() {
         super.tick();
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

     public void travel(Vec3 travelVector) {
         if (this.isAlive()) {
             LivingEntity livingentity = (LivingEntity) this.getControllingPassenger();
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

                 this.setSpeed(0.3F);
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

     @Override
     protected void defineSynchedData() {
         super.defineSynchedData();
         this.entityData.define(COMMAND, 0);
         this.entityData.define(SADDLED, Boolean.valueOf(false));
         this.entityData.define(CHILL_TIME, 0);
         this.entityData.define(TRAVELLING, false);
         this.entityData.define(TRAVEL_POS, BlockPos.ZERO);

     }

     public void addAdditionalSaveData(CompoundTag compound) {
         super.addAdditionalSaveData(compound);
         compound.putBoolean("Saddle", this.isSaddled());
         compound.putInt("Command", this.getCommand());
         compound.putInt("ChillTime", this.getChillTime());
     }

     public void readAdditionalSaveData(CompoundTag compound) {
         super.readAdditionalSaveData(compound);
         this.setSaddled(compound.getBoolean("Saddle"));
         this.setCommand(compound.getInt("TrikeCommand"));
         this.setChillTime(compound.getInt("ChillTime"));
         int l = compound.getInt("TravelPosX");
         int i1 = compound.getInt("TravelPosY");
         int j1 = compound.getInt("TravelPosZ");
         this.setTravelPos(new BlockPos(l, i1, j1));

     }

     public boolean isSaddled() {
         return this.entityData.get(SADDLED).booleanValue();
     }

     public void setSaddled(boolean saddled) {
         this.entityData.set(SADDLED, Boolean.valueOf(saddled));
     }

     public int getRandomAnimationNumber() {
         setRandomNumber(random.nextInt(100));
         return getRandomNumber();
     }


     public boolean getRandomAnimationBool() {
         setRandomBool(random.nextBoolean());
         return getRandomBool();
     }


     @javax.annotation.Nullable
     public LivingEntity getControllingPassenger() {
         for (Entity passenger : this.getPassengers()) {
             if (passenger instanceof Player) {
                 return (Player) passenger;
             }
         }
         return null;
     }

     @Override
     protected void positionRider(Entity pPassenger, MoveFunction pCallback) {
         float ySin = Mth.sin(this.yBodyRot * ((float) Math.PI / 180F));
         float yCos = Mth.cos(this.yBodyRot * ((float) Math.PI / 180F));
         pPassenger.setPos(this.getX() + (double) (0.5F * ySin), this.getY() + this.getPassengersRidingOffset() + pPassenger.getMyRidingOffset() + 0.4F, this.getZ() - (double) (0.5F * yCos));
     }

     public double getPassengersRidingOffset() {
         if (this.isInWater()) {
             return 0.535;
         }
         else {
             return 2.35;
         }
     }

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

     public int getChillTime() {
         return this.entityData.get(CHILL_TIME);
     }

     public void setChillTime(int chillTime) {
         this.entityData.set(CHILL_TIME, chillTime);
     }

     public int getCommand() {
         return this.entityData.get(COMMAND).intValue();
     }

     public void setCommand(int command) {
         this.entityData.set(COMMAND, Integer.valueOf(command));
     }

     @Override
     protected void performAttack() {

     }

     public boolean isPushedByFluid() {
         return false;
     }

     public boolean canBreatheUnderwater() {
         return true;
     }

     public MobType getMobType() {
         return MobType.WATER;
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

     @Override
     public boolean shouldFollow() {
         return this.getCommand() == 1;
     }

     @Nullable
     @Override
     public AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
         return null;
     }

     protected <E extends EntityArchelon> PlayState Controller(final software.bernie.geckolib.core.animation.AnimationState<E> event) {
         if (this.isFromBook()) {
             return PlayState.CONTINUE;
         }

         if (this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6 && !this.isInWater() && !this.isSwimming()) {
             event.setAnimation(ARCHELON_WALK);
             event.getController().setAnimationSpeed(1.0D);
             return PlayState.CONTINUE;
         }

         if(playingAnimation()) {
             return PlayState.CONTINUE;
         }

         if (event.isMoving() && this.isInWater() && getRandomNumber() == 0)  {
              boolean randomAnimationBool = getRandomAnimationBool();
             event.setAnimation(ARCHELON_SWIM.getAnimation());
//             if (randomAnimationBool) {
//                 setAnimationTimer(200);
//                 return event.setAndContinue(ARCHELON_SPIN.getAnimation());
//             }
//             if (!randomAnimationBool) {
//                 setAnimationTimer(200);
//                 return event.setAndContinue(ARCHELON_SPIN2.getAnimation());
//             }
             //}
             event.getController().setAnimationSpeed(1.0F);
             return PlayState.CONTINUE;
         }


         if (isStillEnough() && !this.isSwimming() && getRandomNumber() == 0) {
             boolean bool = getRandomAnimationBool();
             if (bool) {
                 return event.setAndContinue(ARCHELON_SHAKE);
             }
             return  event.setAndContinue(ARCHELON_SWIM_IDLE);
         }
         //if (this.isSaddled()) {
         //    event.setAnimation(ARCHELON_HEART_SPIN);
         //}
         return event.setAndContinue(ARCHELON_IDLE);
     }



     @Override
     public void registerControllers(final AnimatableManager.ControllerRegistrar controllers) {
         controllers.add(new AnimationController<>(this, "Normal", 5, this::Controller));

         //controllers.add(new AnimationController<>(this, ARCHELON_SPIN.getControllerName(), 0, event -> { return PlayState.STOP;}).triggerableAnim(ARCHELON_SPIN.getAnimName(), ARCHELON_SPIN.getAnimation()));
         //controllers.add(new AnimationController<>(this, ARCHELON_SPIN2.getControllerName(), 0, event -> { return PlayState.STOP;}).triggerableAnim(ARCHELON_SPIN2.getAnimName(), ARCHELON_SPIN2.getAnimation()));
         //controllers.add(new AnimationController<>(this, ARCHELON_RAMMING.getControllerName(), 0, event -> { return PlayState.STOP;}).triggerableAnim(ARCHELON_RAMMING.getAnimName(), ARCHELON_RAMMING.getAnimation()));
        // controllers.add(new AnimationController<>(this, ARCHELON_SWIM.getControllerName(), 0, event -> { return PlayState.STOP;}).triggerableAnim(ARCHELON_SWIM.getAnimName(), ARCHELON_SWIM.getAnimation()));
     }

     private boolean isStillEnough() {
         return this.getDeltaMovement().horizontalDistance() < 0.05;
     }

     protected void customServerAiStep() {
         super.customServerAiStep();
         if ((this.tickCount + this.getId()) % 100 == 0) {
             MobEffectInstance mobeffectinstance = new MobEffectInstance(MobEffects.CONDUIT_POWER, 6000, 0);
             List<ServerPlayer> list = MobEffectUtil.addEffectToPlayersAround((ServerLevel)this.level(), this, this.position(), 50.0D, mobeffectinstance, 1200);
             list.forEach((p_289459_) -> {
                 p_289459_.connection.send(new ClientboundGameEventPacket(ClientboundGameEventPacket.GUARDIAN_ELDER_EFFECT, this.isSilent() ? 0.0F : 1.0F));
             });
         }

     }

     static class TurtleGoToWaterGoal extends MoveToBlockGoal {
         private static final int GIVE_UP_TICKS = 1200;
         private final EntityArchelon turtle;

         TurtleGoToWaterGoal(EntityArchelon pTurtle, double pSpeedModifier) {
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

     static class TurtleTravelGoal extends Goal {
         private final EntityArchelon turtle;
         private final double speedModifier;
         private boolean stuck;

         TurtleTravelGoal(EntityArchelon pTurtle, double pSpeedModifier) {
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

     static class TurtleRandomStrollGoal extends RandomStrollGoal {
         private final EntityArchelon turtle;

         TurtleRandomStrollGoal(EntityArchelon pTurtle, double pSpeedModifier, int pInterval) {
             super(pTurtle, pSpeedModifier, pInterval);
             this.turtle = pTurtle;
         }

         public boolean canUse() {
             return !this.mob.isInWater();
         }
     }

     static class TurtleMoveControl extends MoveControl {
         private final EntityArchelon turtle;

         TurtleMoveControl(EntityArchelon pTurtle) {
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

     protected @NotNull PathNavigation createNavigation(Level pLevel) {

         return new EntityArchelon.TurtlePathNavigation(this, pLevel);
     }

     public boolean shouldSwim() {
         return getMaxFluidHeight() >= 0.1F || this.isInLava() || this.isInWaterOrBubble();
     }

     private double getMaxFluidHeight() {
         return Math.max(this.getFluidHeight(FluidTags.LAVA), this.getFluidHeight(FluidTags.WATER));
     }

     static class TurtlePathNavigation extends AmphibiousPathNavigation {
         TurtlePathNavigation(EntityArchelon pTurtle, Level pLevel) {
             super(pTurtle, pLevel);
         }

         public boolean isStableDestination(BlockPos pPos) {
             Mob mob = this.mob;
             if (mob instanceof EntityArchelon turtle) {
                 if (turtle.isTravelling()) {
                     return this.level.getBlockState(pPos).is(Blocks.WATER);
                 }
             }

             return !this.level.getBlockState(pPos.below()).isAir();
         }
     }
 }
