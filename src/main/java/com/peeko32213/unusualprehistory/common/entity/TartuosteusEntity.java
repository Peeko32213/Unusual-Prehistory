 package com.peeko32213.unusualprehistory.common.entity;

 import com.peeko32213.unusualprehistory.common.entity.msc.util.interfaces.IBookEntity;
 import com.peeko32213.unusualprehistory.common.entity.msc.util.dino.BaseAquaticAnimalEntity;
 import net.minecraft.nbt.CompoundTag;
 import net.minecraft.network.syncher.EntityDataAccessor;
 import net.minecraft.network.syncher.EntityDataSerializers;
 import net.minecraft.network.syncher.SynchedEntityData;
 import net.minecraft.sounds.SoundEvent;
 import net.minecraft.sounds.SoundEvents;
 import net.minecraft.tags.TagKey;
 import net.minecraft.util.Mth;
 import net.minecraft.world.entity.EntityType;
 import net.minecraft.world.entity.Mob;
 import net.minecraft.world.entity.MoverType;
 import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
 import net.minecraft.world.entity.ai.attributes.Attributes;
 import net.minecraft.world.entity.ai.control.MoveControl;
 import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
 import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
 import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
 import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
 import net.minecraft.world.entity.ai.goal.TryFindWaterGoal;
 import net.minecraft.world.entity.ai.navigation.PathNavigation;
 import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
 import net.minecraft.world.entity.animal.WaterAnimal;
 import net.minecraft.world.entity.player.Player;
 import net.minecraft.world.level.Level;
 import net.minecraft.world.level.block.state.BlockState;
 import net.minecraft.world.level.pathfinder.BlockPathTypes;
 import net.minecraft.world.phys.Vec3;
 import software.bernie.geckolib.core.animatable.GeoAnimatable;
 import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
 import software.bernie.geckolib.core.animation.AnimatableManager;
 import software.bernie.geckolib.core.animation.AnimationController;
 import software.bernie.geckolib.core.animation.RawAnimation;
 import software.bernie.geckolib.core.object.PlayState;
 import software.bernie.geckolib.util.GeckoLibUtil;

 //TODO LIST
 // - Burrowing At Night
 // - Electric Goop + Electric Fence, though waiting on crydigo to make textures

 public class TartuosteusEntity extends BaseAquaticAnimalEntity implements GeoAnimatable, IBookEntity {
     private static final EntityDataAccessor<Boolean> FROM_BOOK = SynchedEntityData.defineId(TartuosteusEntity.class, EntityDataSerializers.BOOLEAN);
     private static final RawAnimation TARTUO_SWIM = RawAnimation.begin().thenLoop("animation.tartuosteus.swim");
     private static final RawAnimation TARTUO_IDLE = RawAnimation.begin().thenLoop("animation.tartuosteus.idle");
     private static final RawAnimation TARTUO_REST = RawAnimation.begin().thenLoop("animation.tartuosteus.rest");
     private static final RawAnimation TARTUO_BURROW_START = RawAnimation.begin().thenLoop("animation.tartuosteus.burrow_start");
     private static final RawAnimation TARTUO_BURROW_HOLD = RawAnimation.begin().thenLoop("animation.tartuosteus.burrow_hold");
     private static final RawAnimation TARTUO_BURROW_END = RawAnimation.begin().thenLoop("animation.tartuosteus.burrow_end");
     private static final RawAnimation TARTUO_FLOP = RawAnimation.begin().thenLoop("animation.tartuosteus.flop");
     private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

     public TartuosteusEntity(EntityType<? extends WaterAnimal> pEntityType, Level pLevel) {
         super(pEntityType, pLevel);
         this.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
         this.lookControl = new SmoothSwimmingLookControl(this, 10);
         this.moveControl = new TartuosteusEntity.MoveHelperController(this);
     }

     public static AttributeSupplier.Builder createAttributes() {
         return Mob.createMobAttributes()
                 .add(Attributes.MAX_HEALTH, 20.0D)
                 .add(Attributes.ARMOR, 20.0)
                 .add(Attributes.KNOCKBACK_RESISTANCE, 0.15D);
     }

     protected void registerGoals() {
         this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
         this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 6.0F));
         this.goalSelector.addGoal(0, new TryFindWaterGoal(this));
         this.goalSelector.addGoal(4, new RandomSwimmingGoal(this, 0.7D, 10));
     }

     public void travel(Vec3 travelVector) {
         if (this.isEffectiveAi() && this.isInWater()) {
             this.moveRelative(this.getSpeed(), travelVector);
             this.move(MoverType.SELF, this.getDeltaMovement());
             this.setDeltaMovement(this.getDeltaMovement().scale(0.9D));
             if (this.getTarget() == null) {
                 this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.005D, 0.0D));
             }
         } else {
             super.travel(travelVector);
         }

     }

     protected PathNavigation createNavigation(Level p_27480_) {
         return new WaterBoundPathNavigation(this, p_27480_);
     }

     protected SoundEvent getAmbientSound() {
         return SoundEvents.COD_AMBIENT;
     }

     protected void defineSynchedData() {
         super.defineSynchedData();
         this.entityData.define(FROM_BOOK, false);
     }

     public void addAdditionalSaveData(CompoundTag compound) {
         super.addAdditionalSaveData(compound);

     }

     public void readAdditionalSaveData(CompoundTag compound) {
         super.readAdditionalSaveData(compound);
     }

     public void aiStep() {
         super.aiStep();
     }

     public boolean isFromBook() {
         return this.entityData.get(FROM_BOOK).booleanValue();
     }
     public void setIsFromBook(boolean fromBook) {
         this.entityData.set(FROM_BOOK, fromBook);
     }


     @Override
     public void setFromBook(boolean fromBook) {
         this.entityData.set(FROM_BOOK, fromBook);
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

     private boolean isStillEnough() {
         return this.getDeltaMovement().horizontalDistance() < 0.05;
     }

     protected <E extends TartuosteusEntity> PlayState Controller(final software.bernie.geckolib.core.animation.AnimationState<E> event) {

         if (this.isFromBook()) {
             return PlayState.CONTINUE;
         }

         if (!(event.getLimbSwingAmount() > -0.06F && event.getLimbSwingAmount() < 0.06F) && this.isInWater()) {
             event.setAndContinue(TARTUO_SWIM);
             return PlayState.CONTINUE;
         }
         if (!this.isInWater()) {
             event.setAndContinue(TARTUO_FLOP);
             event.getController().setAnimationSpeed(2.0F);
             return PlayState.CONTINUE;
         }

         if(playingAnimation())
         {
             return PlayState.CONTINUE;
         }

         if (isStillEnough() && getRandomAnimationNumber(500) == 0 && this.isInWater()) {
             int rand = getRandomAnimationNumber();
             if (rand < 50) {
                 setAnimationTimer(150);
                 event.setAndContinue(TARTUO_REST);
             }
             else {
                 event.setAndContinue(TARTUO_IDLE);
                 return PlayState.CONTINUE;
             }
         }
         return PlayState.CONTINUE;
     }

     @Override
     public void registerControllers(final AnimatableManager.ControllerRegistrar controllers) {
         controllers.add(new AnimationController<>(this, "Normal", 5, this::Controller));
     }

     public boolean requiresCustomPersistence() {
         return super.requiresCustomPersistence() || this.hasCustomName();
     }

     public boolean removeWhenFarAway(double d) {
         return !this.hasCustomName();
     }

     @Override
     public AnimatableInstanceCache getAnimatableInstanceCache() {
         return this.cache;
     }

     @Override
     public double getTick(Object o) {
         return tickCount;
     }

     static class MoveHelperController extends MoveControl {
         private final TartuosteusEntity dolphin;

         public MoveHelperController(TartuosteusEntity dolphinIn) {
             super(dolphinIn);
             this.dolphin = dolphinIn;
         }

         public void tick() {
             if (this.dolphin.isInWater()) {
                 this.dolphin.setDeltaMovement(this.dolphin.getDeltaMovement().add(0.0D, 0.005D, 0.0D));
             }

             if (this.operation == MoveControl.Operation.MOVE_TO && !this.dolphin.getNavigation().isDone()) {
                 double d0 = this.wantedX - this.dolphin.getX();
                 double d1 = this.wantedY - this.dolphin.getY();
                 double d2 = this.wantedZ - this.dolphin.getZ();
                 double d3 = d0 * d0 + d1 * d1 + d2 * d2;
                 if (d3 < (double) 2.5000003E-7F) {
                     this.mob.setZza(0.0F);
                 } else {
                     float f = (float) (Mth.atan2(d2, d0) * (double) (180F / (float) Math.PI)) - 90.0F;
                     this.dolphin.setYRot(this.rotlerp(this.dolphin.getYRot(), f, 10.0F));
                     this.dolphin.yBodyRot = this.dolphin.getYRot();
                     this.dolphin.yHeadRot = this.dolphin.getYRot();
                     float f1 = (float) (this.speedModifier * this.dolphin.getAttributeValue(Attributes.MOVEMENT_SPEED));
                     if (this.dolphin.isInWater()) {
                         this.dolphin.setSpeed(f1 * 0.02F);
                         float f2 = -((float) (Mth.atan2(d1, Mth.sqrt((float) (d0 * d0 + d2 * d2))) * (double) (180F / (float) Math.PI)));
                         f2 = Mth.clamp(Mth.wrapDegrees(f2), -85.0F, 85.0F);
                         this.dolphin.setXRot(this.rotlerp(this.dolphin.getXRot(), f2, 5.0F));
                         float f3 = Mth.cos(this.dolphin.getXRot() * ((float) Math.PI / 180F));
                         float f4 = Mth.sin(this.dolphin.getXRot() * ((float) Math.PI / 180F));
                         this.dolphin.zza = f3 * f1;
                         this.dolphin.yya = -f4 * f1;
                     } else {
                         this.dolphin.setSpeed(f1 * 0.1F);
                     }

                 }
             } else {
                 this.dolphin.setSpeed(0.0F);
                 this.dolphin.setXxa(0.0F);
                 this.dolphin.setYya(0.0F);
                 this.dolphin.setZza(0.0F);
             }
         }
     }
 }
