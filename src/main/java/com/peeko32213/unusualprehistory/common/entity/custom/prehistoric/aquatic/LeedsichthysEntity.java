 package com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.aquatic;

 import com.google.common.collect.ImmutableList;
 import com.google.common.collect.ImmutableMap;
 import com.peeko32213.unusualprehistory.MathHelpers;
 import com.peeko32213.unusualprehistory.UnusualPrehistoryConfig;
 import com.peeko32213.unusualprehistory.common.entity.animation.state.EntityAction;
 import com.peeko32213.unusualprehistory.common.entity.animation.state.StateHelper;
 import com.peeko32213.unusualprehistory.common.entity.animation.state.WeightedState;
 import com.peeko32213.unusualprehistory.common.entity.custom.ai.goal.AquaticJumpGoal;
 import com.peeko32213.unusualprehistory.common.entity.custom.ai.goal.CustomizableRandomSwimGoal;
 import com.peeko32213.unusualprehistory.common.entity.custom.base.PrehistoricAquaticEntity;
 import com.peeko32213.unusualprehistory.common.entity.util.navigator.SmartBodyHelper;
 import com.peeko32213.unusualprehistory.core.registry.entities.UPEntities;
 import com.peeko32213.unusualprehistory.core.registry.items.UPItems;
 import com.peeko32213.unusualprehistory.core.registry.UPSounds;
 import net.minecraft.core.BlockPos;
 import net.minecraft.core.particles.ParticleTypes;
 import net.minecraft.nbt.CompoundTag;
 import net.minecraft.network.syncher.EntityDataAccessor;
 import net.minecraft.network.syncher.EntityDataSerializers;
 import net.minecraft.network.syncher.SynchedEntityData;
 import net.minecraft.server.level.ServerLevel;
 import net.minecraft.sounds.SoundEvent;
 import net.minecraft.tags.FluidTags;
 import net.minecraft.util.Mth;
 import net.minecraft.util.RandomSource;
 import net.minecraft.world.DifficultyInstance;
 import net.minecraft.world.InteractionHand;
 import net.minecraft.world.InteractionResult;
 import net.minecraft.world.damagesource.DamageSource;
 import net.minecraft.world.entity.*;
 import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
 import net.minecraft.world.entity.ai.attributes.Attributes;
 import net.minecraft.world.entity.ai.control.BodyRotationControl;
 import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
 import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
 import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
 import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
 import net.minecraft.world.entity.ai.navigation.PathNavigation;
 import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
 import net.minecraft.world.entity.animal.WaterAnimal;
 import net.minecraft.world.entity.player.Player;
 import net.minecraft.world.item.ItemStack;
 import net.minecraft.world.item.ItemUtils;
 import net.minecraft.world.item.Items;
 import net.minecraft.world.level.Level;
 import net.minecraft.world.level.LevelAccessor;
 import net.minecraft.world.level.ServerLevelAccessor;
 import net.minecraft.world.level.block.Blocks;
 import net.minecraft.world.level.pathfinder.BlockPathTypes;
 import net.minecraft.world.phys.Vec3;
 import org.jetbrains.annotations.NotNull;
 import software.bernie.geckolib.animatable.GeoEntity;
 import software.bernie.geckolib.core.animatable.GeoAnimatable;
 import software.bernie.geckolib.core.animation.AnimatableManager;
 import software.bernie.geckolib.core.animation.AnimationController;
 import software.bernie.geckolib.core.animation.AnimationState;
 import software.bernie.geckolib.core.animation.RawAnimation;
 import software.bernie.geckolib.core.keyframe.event.SoundKeyframeEvent;
 import software.bernie.geckolib.core.object.PlayState;

 import javax.annotation.Nonnull;
 import javax.annotation.Nullable;
 import java.util.List;

 public class LeedsichthysEntity extends PrehistoricAquaticEntity implements GeoEntity, GeoAnimatable {
     //START of necessary IK shit

     public double prevYHeadRot;
     public double deltaYHeadRot;

     public Vec3 rightRefPoint;
     public Vec3 rightRefOffset = new Vec3(1, 0, 0);

     public Vec3 leftRefPoint;
     public Vec3 leftRefOffset = new Vec3(-1, 0, 0);

     public Vec3 upRefPoint;
     public Vec3 upRefOffset = new Vec3(0, -1, 0);

     public Vec3 downRefPoint;
     public Vec3 downRefOffset = new Vec3(0, 1, 0);

     public Vec3 nosePoint;
     public Vec3 tail0Point;
     public Vec3 tail1Point;
     public Vec3 tail2Point;
     public Vec3 tail3Point;

     //Offset to the points relative to their parent point
     public Vec3 noseOffset = new Vec3(0.0, -1, -1);
     public Vec3 tail0Offset = new Vec3(0.0, -1, 1);
     public Vec3 tail1Offset = new Vec3(0.0, -1, 1);
     //technically the second segment's bone position offset, but affects the segment before it
     public Vec3 tail2Offset = new Vec3(0.0, -1, 1);
     public Vec3 tail3Offset = new Vec3(0.0, -1, 1);

//x = side to side offset
//y = vert offset
//z = fore to back offset(pos is back)

     public double bodyPitch = 0;
     public double currentBodyPitch = 0;

     public double tail1Yaw;
     public double tail2Yaw;
     public double currentTail1Yaw = Mth.PI;
     public double currentTail2Yaw = Mth.PI;

     //Yaw starts at pi
     public double currentTail1Pitch = 0;
     public double currentTail2Pitch = 0;
     public double tail1Pitch;
     public double tail2Pitch;
     //END of necessary IK shit
     private static final EntityDataAccessor<Integer> ANIMATION_STATE = SynchedEntityData.defineId(LeedsichthysEntity.class, EntityDataSerializers.INT);
     private static final EntityDataAccessor<Boolean> FROM_BOOK = SynchedEntityData.defineId(LeedsichthysEntity.class, EntityDataSerializers.BOOLEAN);

     // Movement animations
     private static final RawAnimation LEEDS_SWIM = RawAnimation.begin().thenLoop("animation.leedsichthys.swim");
     private static final RawAnimation LEEDS_SWIM_FAST = RawAnimation.begin().thenLoop("animation.leedsichthys.swimfast");
     private static final RawAnimation LEEDS_FREEFALL_1 = RawAnimation.begin().thenLoop("animation.leedsichthys.freefall1");
     private static final RawAnimation LEEDS_FREEFALL_2 = RawAnimation.begin().thenLoop("animation.leedsichthys.freefall2");
     private static final RawAnimation LEEDS_BEACHED_1 = RawAnimation.begin().thenLoop("animation.leedsichthys.beached1");
     private static final RawAnimation LEEDS_BEACHED_2 = RawAnimation.begin().thenLoop("animation.leedsichthys.beached2");

     // Idle animations
     private static final RawAnimation LEEDS_GULP = RawAnimation.begin().thenPlay("animation.leedsichthys.biggulp_blend");
     private static final RawAnimation LEEDS_ROLL_1 = RawAnimation.begin().thenPlay("animation.leedsichthys.roll_blend1");
     private static final RawAnimation LEEDS_ROLL_2 = RawAnimation.begin().thenPlay("animation.leedsichthys.roll_blend2");
     private static final RawAnimation LEEDS_YAWN = RawAnimation.begin().thenPlay("animation.leedsichthys.yawn_blend");

     // Attack animations
     private static final RawAnimation LEEDS_BUMP = RawAnimation.begin().thenLoop("animation.leedsichthys.bump_blend");

     // Idle accessors
     private static final EntityDataAccessor<Boolean> IDLE_1_AC = SynchedEntityData.defineId(LeedsichthysEntity.class, EntityDataSerializers.BOOLEAN);
     private static final EntityDataAccessor<Boolean> IDLE_2_AC = SynchedEntityData.defineId(LeedsichthysEntity.class, EntityDataSerializers.BOOLEAN);
     private static final EntityDataAccessor<Boolean> IDLE_3_AC = SynchedEntityData.defineId(LeedsichthysEntity.class, EntityDataSerializers.BOOLEAN);
     private static final EntityDataAccessor<Boolean> IDLE_4_AC = SynchedEntityData.defineId(LeedsichthysEntity.class, EntityDataSerializers.BOOLEAN);

     // Idle actions
     private static final EntityAction LEEDS_IDLE_1_ACTION = new EntityAction(0, (e) -> {}, 1);

     private static final StateHelper LEEDS_IDLE_1_STATE =
             StateHelper.Builder.state(IDLE_1_AC, "leeds_gulp")
                     .playTime(160)
                     .stopTime(400)
                     .entityAction(LEEDS_IDLE_1_ACTION)
                     .build();

     private static final EntityAction LEEDS_IDLE_2_ACTION = new EntityAction(0, (e) -> {}, 1);

     private static final StateHelper LEEDS_IDLE_2_STATE =
             StateHelper.Builder.state(IDLE_2_AC, "leeds_roll_1")
                     .playTime(160)
                     .stopTime(350)
                     .entityAction(LEEDS_IDLE_2_ACTION)
                     .build();

     private static final EntityAction LEEDS_IDLE_3_ACTION = new EntityAction(0, (e) -> {}, 1);

     private static final StateHelper LEEDS_IDLE_3_STATE =
             StateHelper.Builder.state(IDLE_3_AC, "leeds_roll_2")
                     .playTime(160)
                     .stopTime(350)
                     .entityAction(LEEDS_IDLE_3_ACTION)
                     .build();

     private static final EntityAction LEEDS_IDLE_4_ACTION = new EntityAction(0, (e) -> {}, 1);

     private static final StateHelper LEEDS_IDLE_4_STATE =
             StateHelper.Builder.state(IDLE_4_AC, "leeds_yawn")
                     .playTime(160)
                     .stopTime(250)
                     .entityAction(LEEDS_IDLE_4_ACTION)
                     .build();

     // States
     @Override
     public ImmutableMap<String, StateHelper> getStates() {
         return ImmutableMap.of(
                 LEEDS_IDLE_1_STATE.getName(), LEEDS_IDLE_1_STATE,
                 LEEDS_IDLE_2_STATE.getName(), LEEDS_IDLE_2_STATE,
                 LEEDS_IDLE_3_STATE.getName(), LEEDS_IDLE_3_STATE,
                 LEEDS_IDLE_4_STATE.getName(), LEEDS_IDLE_4_STATE
         );
     }

     @Override
     public List<WeightedState<StateHelper>> getWeightedStatesToPerform() {
         return ImmutableList.of(
                 WeightedState.of(LEEDS_IDLE_1_STATE, 10),
                 WeightedState.of(LEEDS_IDLE_2_STATE, 8),
                 WeightedState.of(LEEDS_IDLE_3_STATE, 8),
                 WeightedState.of(LEEDS_IDLE_4_STATE, 11)
         );
     }

     // Actions
     @Override
     public boolean getAction() {return false;}

     @Override
     public void setAction(boolean action) {}

     @Override
     protected @NotNull BodyRotationControl createBodyControl() {
         SmartBodyHelper helper = new SmartBodyHelper(this);
         helper.bodyLagMoving = 0.09F;
         helper.bodyLagStill = 0.06F;
         return helper;
     }

     public LeedsichthysEntity(EntityType<? extends PrehistoricAquaticEntity> entityType, Level level) {
         super(entityType, level);
         this.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
         this.lookControl = new SmoothSwimmingLookControl(this, 10);
         this.moveControl = new SmoothSwimmingMoveControl(this, 3600, 2, 0.02F, 0.1F, false);


         leftRefPoint = MathHelpers.rotateAroundCenterFlatDeg(this.position(), this.position().subtract(leftRefOffset), (double) -this.getYRot());
         rightRefPoint = MathHelpers.rotateAroundCenterFlatDeg(this.position(), this.position().subtract(rightRefOffset), (double) -this.getYRot());
         upRefPoint = MathHelpers.rotateAroundCenterFlatDeg(this.position(), this.position().subtract(upRefOffset), (double) -this.getYRot());
         downRefPoint = MathHelpers.rotateAroundCenterFlatDeg(this.position(), this.position().subtract(downRefOffset), (double) -this.getYRot());

         nosePoint = MathHelpers.rotateAroundCenterFlatDeg(this.position(), this.position().subtract(noseOffset), (double) -this.getYRot());
         tail0Point = MathHelpers.rotateAroundCenterFlatDeg(this.position(), this.position().subtract(tail0Offset), (double) -this.getYRot());
         tail1Point = MathHelpers.rotateAroundCenterFlatDeg(tail0Point, tail0Point.subtract(tail1Offset), (double) -this.getYRot());
         tail2Point = MathHelpers.rotateAroundCenterFlatDeg(tail1Point, tail1Point.subtract(tail2Offset), (double) -this.getYRot());
         tail3Point = MathHelpers.rotateAroundCenterFlatDeg(tail2Point, tail2Point.subtract(tail3Offset), (double) -this.getYRot());

     }

     // Attributes
     public static AttributeSupplier.Builder createAttributes() {
         return Mob.createMobAttributes()
             .add(Attributes.MAX_HEALTH, 500.0D)
             .add(Attributes.ATTACK_DAMAGE, 10.0D)
             .add(Attributes.KNOCKBACK_RESISTANCE, 4.0D)
             .add(Attributes.MOVEMENT_SPEED, 0.5D)
             .add(Attributes.FOLLOW_RANGE, 12.0D);
     }

     // Goals
     @Override
     protected void registerGoals() {
         super.registerGoals();
         this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 6.0F));
         this.goalSelector.addGoal(1, new RandomSwimmingGoal(this, 1.8D, 10));
         this.goalSelector.addGoal(4, new AquaticJumpGoal(this, 50));
         this.goalSelector.addGoal(1, new CustomizableRandomSwimGoal(this, 1.25, 1, 70, 70, 2));
     }

     @Override
     @Nonnull
     public InteractionResult mobInteract(Player player, @NotNull InteractionHand hand) {
         ItemStack itemstack = player.getItemInHand(hand);
         if (itemstack.is(Items.BOWL) && !this.isBaby()) {
             ItemStack itemstack1 = ItemUtils.createFilledResult(itemstack, player, UPItems.LEEDS_CAVIAR.get().getDefaultInstance());
             player.setItemInHand(hand, itemstack1);
             return InteractionResult.sidedSuccess(this.level().isClientSide);
         }
         return super.mobInteract(player, hand);
     }

     public void travel(@NotNull Vec3 travelVector) {
         super.travel(travelVector);
     }

     protected @NotNull PathNavigation createNavigation(@NotNull Level p_27480_) {
         return new WaterBoundPathNavigation(this, p_27480_);
     }

     // Sounds
     protected SoundEvent getAmbientSound() {
         return UPSounds.LEEDS_IDLE.get();
     }

     protected SoundEvent getHurtSound(@NotNull DamageSource damageSourceIn) {
         return UPSounds.LEEDS_HURT.get();
     }

     protected SoundEvent getDeathSound() {
         return UPSounds.LEEDS_DEATH.get();
     }

     protected SoundEvent getFlopSound() {
         return UPSounds.LEEDS_FLOP.get();
     }

     @Override
     public float getSoundVolume() {
         if(this.isBaby()){
             return 1.0F;
         }
         else{
             return 1.75F;
         }
     }

     @Override
     public int getAmbientSoundInterval() {
         return 180;
     }

     protected void defineSynchedData() {
         super.defineSynchedData();
         this.entityData.define(IDLE_1_AC, false);
         this.entityData.define(IDLE_2_AC, false);
         this.entityData.define(IDLE_3_AC, false);
         this.entityData.define(IDLE_4_AC, false);
         this.entityData.define(ANIMATION_STATE, 0);
         this.entityData.define(FROM_BOOK, false);
     }

     public void addAdditionalSaveData(CompoundTag compound) {
         super.addAdditionalSaveData(compound);
     }

     public void readAdditionalSaveData(CompoundTag compound) {
         super.readAdditionalSaveData(compound);
     }

     public void tick() {
         super.tick();

         if (this.isInWater()) {
             //START of IK
             //the entity rotations must be negativized because we want the points to be transformed relative to the entity

             tail1Yaw = (MathHelpers.angleClamp(MathHelpers.getAngleForLinkTopDownFlat(this.tail1Point, this.tail0Point, this.tail2Point, this.leftRefPoint, this.rightRefPoint), Mth.PI * 0.75));
             tail2Yaw = (MathHelpers.angleClamp(MathHelpers.getAngleForLinkTopDownFlat(this.tail2Point, this.tail1Point, this.tail3Point, this.leftRefPoint, this.rightRefPoint), Mth.PI * 0.75));

             bodyPitch = ((float) (Mth.PI * MathHelpers.angleFromYdiff(this.nosePoint, this.position(), this.tail0Point)));

             tail1Pitch = ((float) (Mth.PI * MathHelpers.angleFromYdiff(this.position(), this.tail0Point, this.tail1Point)));
             tail2Pitch = ((float) (Mth.PI * MathHelpers.angleFromYdiff(this.tail0Point, this.tail1Point, this.tail2Point)));

             nosePoint = MathHelpers.rotateAroundCenter3dDeg(this.position(), this.position().subtract(noseOffset), -this.getYRot(), -this.getXRot());
             tail0Point = MathHelpers.rotateAroundCenter3dDeg(this.position(), this.position().subtract(tail0Offset), -this.getYRot(), -this.getXRot());
             tail1Point = MathHelpers.rotateAroundCenter3dDeg(tail0Point, tail0Point.subtract(tail1Offset), (float) (-MathHelpers.angleTo(tail0Point, tail1Point).y - deltaYHeadRot*Mth.DEG_TO_RAD), -MathHelpers.angleTo(tail0Point, tail1Point).x);
             tail2Point = MathHelpers.rotateAroundCenter3dDeg(tail1Point, tail1Point.subtract(tail2Offset), -MathHelpers.angleTo(tail1Point, tail2Point).y, -MathHelpers.angleTo(tail1Point, tail2Point).x);
             tail3Point = MathHelpers.rotateAroundCenter3dDeg(tail2Point, tail2Point.subtract(tail3Offset), -MathHelpers.angleTo(tail2Point, tail3Point).y, -MathHelpers.angleTo(tail2Point, tail3Point).x);

             deltaYHeadRot = prevYHeadRot-this.getYHeadRot();
             prevYHeadRot = this.getYHeadRot();
             //this value is in degrees

            if (!this.level().isClientSide()) {
                ServerLevel llel = (ServerLevel) this.level();
                llel.sendParticles(ParticleTypes.BUBBLE_POP, (nosePoint.x), (nosePoint.y), (nosePoint.z), 1, 0.0D, 0.0D, 0.0D, 0.0D);
                llel.sendParticles(ParticleTypes.BUBBLE_POP, (tail0Point.x), (tail0Point.y), (tail0Point.z), 1, 0.0D, 0.0D, 0.0D, 0.0D);
                llel.sendParticles(ParticleTypes.BUBBLE_POP, (tail1Point.x), (tail1Point.y), (tail1Point.z), 1, 0.0D, 0.0D, 0.0D, 0.0D);
                llel.sendParticles(ParticleTypes.BUBBLE_POP, (tail2Point.x), (tail2Point.y), (tail2Point.z), 1, 0.0D, 0.0D, 0.0D, 0.0D);
                llel.sendParticles(ParticleTypes.BUBBLE_POP, (tail3Point.x), (tail3Point.y), (tail3Point.z), 1, 0.0D, 0.0D, 0.0D, 0.0D);
            }

             //side refs don't move vertically
             leftRefPoint = MathHelpers.rotateAroundCenterFlatDeg(this.position(), this.position().subtract(leftRefOffset), (double) -this.getYRot());
             rightRefPoint = MathHelpers.rotateAroundCenterFlatDeg(this.position(), this.position().subtract(rightRefOffset), (double) -this.getYRot());
             upRefPoint = MathHelpers.rotateAroundCenterFlatDeg(this.position(), this.position().subtract(upRefOffset), (double) -this.getYRot());
             downRefPoint = MathHelpers.rotateAroundCenterFlatDeg(this.position(), this.position().subtract(downRefOffset), (double) -this.getYRot());
             //END of IK
         }
     }

     @Override
     public void aiStep() {
         super.aiStep();
     }

     private void soundListener(SoundKeyframeEvent<LeedsichthysEntity> event) {
         LeedsichthysEntity leedsichthys = event.getAnimatable();
         leedsichthys.level();
     }

     @Override
     public void registerControllers(final AnimatableManager.ControllerRegistrar controllers) {
         AnimationController<LeedsichthysEntity> controller = new AnimationController<>(this, "controller", 5, this::predicate);
            controllers.add(controller);
         AnimationController<LeedsichthysEntity> blend = new AnimationController<>(this, "blend", 5, this::predicate)
                 .triggerableAnim("gulp", LEEDS_GULP)
                 .triggerableAnim("roll_1", LEEDS_ROLL_1)
                 .triggerableAnim("roll_2", LEEDS_ROLL_2)
                 .triggerableAnim("yawn", LEEDS_YAWN)
                 .triggerableAnim("bump", LEEDS_BUMP);
         blend.setSoundKeyframeHandler(this::soundListener);
            controllers.add(blend);

         AnimationController<LeedsichthysEntity> jump = new AnimationController<>(this, "jump", 5, this::jumpPredicate);
            controllers.add(jump);
     }

     protected <E extends LeedsichthysEntity> PlayState predicate(final software.bernie.geckolib.core.animation.AnimationState<E> event) {

         if (this.isFromBook()) {
             return event.setAndContinue(LEEDS_SWIM);
         }

         int animState = this.getAnimationState();

         if (!this.isFromBook()) {

             if (animState == 21) {
                 return event.setAndContinue(LEEDS_BUMP);
             }
             else {
                 if (!(event.getLimbSwingAmount() > -0.06F && event.getLimbSwingAmount() < 0.06F) && this.isInWater()) {
                     event.setAndContinue(LEEDS_SWIM);
                     return PlayState.CONTINUE;
                 }
                 if (this.onGround() && !this.isUnderWater()) {
                     event.setAndContinue(LEEDS_BEACHED_1);
                     return PlayState.CONTINUE;
                 }

                 // Idle states
                 if (this.isInWater()) {
                     if (getBooleanState(IDLE_1_AC)) {
                         triggerAnim("blend", "gulp");
                         return PlayState.CONTINUE;
                     }
                     if (getBooleanState(IDLE_2_AC)) {
                         triggerAnim("blend", "roll_1");
                         return PlayState.CONTINUE;
                     }
                     if (getBooleanState(IDLE_3_AC)) {
                         triggerAnim("blend", "roll_2");
                         return PlayState.CONTINUE;
                     }
                     if (getBooleanState(IDLE_4_AC)) {
                         triggerAnim("blend", "yawn");
                         return PlayState.CONTINUE;
                     }
                     return event.setAndContinue(LEEDS_SWIM);
                 }
                 return PlayState.CONTINUE;
             }
         }
         return PlayState.CONTINUE;
     }

     protected <E extends LeedsichthysEntity> PlayState jumpPredicate(final AnimationState<E> event) {
         if (!this.onGround() && !this.isInWater()) {
             event.getController().setAnimation(LEEDS_FREEFALL_1);
             event.getController().setAnimationSpeed(1.0D);
             return PlayState.CONTINUE;
         }
         event.getController().forceAnimationReset();

         return PlayState.STOP;
     }

     @Nullable
     public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_28134_, DifficultyInstance p_28135_, MobSpawnType p_28136_, @Nullable SpawnGroupData p_28137_, @Nullable CompoundTag p_28138_) {
         p_28137_ = super.finalizeSpawn(p_28134_, p_28135_, p_28136_, p_28137_, p_28138_);

         Level level = p_28134_.getLevel();
         if (level instanceof ServerLevel) {
             this.setPersistenceRequired();
         }
         return p_28137_;
     }

     @Nullable
     @Override
     public AgeableMob getBreedOffspring(@NotNull ServerLevel serverLevel, @NotNull AgeableMob ageableMob) {
         return UPEntities.LEEDSICHTHYS.get().create(serverLevel);
     }

     @Override
     protected int getKillHealAmount() {
         return 10;
     }

     @Override
     public boolean isPushable() {
         return false;
     }

     public static boolean checkSurfaceWaterDinoSpawnRules(EntityType<? extends WaterAnimal> pWaterAnimal, LevelAccessor pLevel, MobSpawnType pSpawnType, BlockPos pPos, RandomSource pRandom) {
         int i = pLevel.getSeaLevel();
         int j = i - 13;
         return pPos.getY() >= j && pPos.getY() <= i && pLevel.getFluidState(pPos.below()).is(FluidTags.WATER) && pLevel.getBlockState(pPos.above()).is(Blocks.WATER);
     }
 }
