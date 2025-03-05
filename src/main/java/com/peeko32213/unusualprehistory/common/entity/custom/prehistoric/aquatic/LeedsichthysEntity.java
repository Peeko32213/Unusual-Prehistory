 package com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.aquatic;

 import com.google.common.collect.ImmutableList;
 import com.google.common.collect.ImmutableMap;
 import com.peeko32213.unusualprehistory.UnusualPrehistoryConfig;
 import com.peeko32213.unusualprehistory.common.entity.animation.state.EntityAction;
 import com.peeko32213.unusualprehistory.common.entity.animation.state.StateHelper;
 import com.peeko32213.unusualprehistory.common.entity.animation.state.WeightedState;
 import com.peeko32213.unusualprehistory.common.entity.custom.base.PrehistoricAquaticEntity;
 import com.peeko32213.unusualprehistory.common.entity.custom.part.LeedsichthysPartEntity;
 import com.peeko32213.unusualprehistory.common.entity.util.goal.AquaticJumpGoal;
 import com.peeko32213.unusualprehistory.core.registry.UPEntities;
 import com.peeko32213.unusualprehistory.core.registry.UPItems;
 import com.peeko32213.unusualprehistory.core.registry.UPSounds;
 import net.minecraft.core.BlockPos;
 import net.minecraft.nbt.CompoundTag;
 import net.minecraft.network.syncher.EntityDataAccessor;
 import net.minecraft.network.syncher.EntityDataSerializers;
 import net.minecraft.network.syncher.SynchedEntityData;
 import net.minecraft.server.level.ServerLevel;
 import net.minecraft.sounds.SoundEvent;
 import net.minecraft.tags.FluidTags;
 import net.minecraft.tags.TagKey;
 import net.minecraft.util.Mth;
 import net.minecraft.util.RandomSource;
 import net.minecraft.world.DifficultyInstance;
 import net.minecraft.world.InteractionHand;
 import net.minecraft.world.InteractionResult;
 import net.minecraft.world.damagesource.DamageSource;
 import net.minecraft.world.entity.*;
 import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
 import net.minecraft.world.entity.ai.attributes.Attributes;
 import net.minecraft.world.entity.ai.control.MoveControl;
 import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
 import net.minecraft.world.entity.ai.goal.*;
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
 import net.minecraft.world.level.block.state.BlockState;
 import net.minecraft.world.level.pathfinder.BlockPathTypes;
 import net.minecraft.world.phys.Vec3;
 import org.jetbrains.annotations.NotNull;
 import software.bernie.geckolib.animatable.GeoEntity;
 import software.bernie.geckolib.core.animatable.GeoAnimatable;
 import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
 import software.bernie.geckolib.core.animation.AnimatableManager;
 import software.bernie.geckolib.core.animation.AnimationController;
 import software.bernie.geckolib.core.animation.RawAnimation;
 import software.bernie.geckolib.core.keyframe.event.SoundKeyframeEvent;
 import software.bernie.geckolib.core.object.PlayState;
 import software.bernie.geckolib.util.GeckoLibUtil;

 import javax.annotation.Nonnull;
 import javax.annotation.Nullable;
 import java.util.List;
 import java.util.Optional;
 import java.util.UUID;

 public class LeedsichthysEntity extends PrehistoricAquaticEntity implements GeoEntity, GeoAnimatable {

     private static final EntityDataAccessor<Integer> ANIMATION_STATE = SynchedEntityData.defineId(LeedsichthysEntity.class, EntityDataSerializers.INT);
     private static final EntityDataAccessor<Boolean> FROM_BOOK = SynchedEntityData.defineId(LeedsichthysEntity.class, EntityDataSerializers.BOOLEAN);
     private static final EntityDataAccessor<Optional<UUID>> CHILD_UUID = SynchedEntityData.defineId(LeedsichthysEntity.class, EntityDataSerializers.OPTIONAL_UUID);
     private static final EntityDataAccessor<Integer> CHILD_ID = SynchedEntityData.defineId(LeedsichthysEntity.class, EntityDataSerializers.INT);

     private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

     // Movement animations
     private static final RawAnimation LEEDS_SWIM = RawAnimation.begin().thenLoop("animation.leedsichthys.swim");
     private static final RawAnimation LEEDS_SWIM_FAST = RawAnimation.begin().thenLoop("animation.leedsichthys.swimfast");
     private static final RawAnimation LEEDS_FREEFALL_1 = RawAnimation.begin().thenLoop("animation.leedsichthys.freefall1");
     private static final RawAnimation LEEDS_FREEFALL_2 = RawAnimation.begin().thenLoop("animation.leedsichthys.freefall2");
     private static final RawAnimation LEEDS_BEACHED_1 = RawAnimation.begin().thenLoop("animation.leedsichthys.beached1");
     private static final RawAnimation LEEDS_BEACHED_2 = RawAnimation.begin().thenLoop("animation.leedsichthys.beached2");

     // Idle animations
     private static final RawAnimation LEEDS_IDLE = RawAnimation.begin().thenLoop("animation.leedsichthys.idle");
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

     @Override
     public boolean getAction() {return false;}

     @Override
     public void setAction(boolean action) {}

     private LeedsichthysPartEntity[] parts;
     public final float[] ringBuffer = new float[64];
     public int ringBufferIndex = -1;

     public LeedsichthysEntity(EntityType<? extends PrehistoricAquaticEntity> entityType, Level level) {
         super(entityType, level);
         this.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
         this.lookControl = new SmoothSwimmingLookControl(this, 10);
         this.moveControl = new LeedsichthysEntity.MoveHelperController(this);
     }

     public static AttributeSupplier.Builder createAttributes() {
         return Mob.createMobAttributes()
             .add(Attributes.MAX_HEALTH, 500.0D)
             .add(Attributes.ATTACK_DAMAGE, 10.0D)
             .add(Attributes.KNOCKBACK_RESISTANCE, 4.0D)
             .add(Attributes.MOVEMENT_SPEED, 2.3D)
             .add(Attributes.FOLLOW_RANGE, 12.0D);
     }

     @Override
     protected void registerGoals() {
         super.registerGoals();
         this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
         this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 6.0F));
         this.goalSelector.addGoal(5, new AquaticJumpGoal(this, 50));
         this.goalSelector.addGoal(1, new RandomSwimmingGoal(this, 1.8D, 10));
     }

     protected void doPush(@NotNull Entity pEntity) {
         super.doPush(pEntity);
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

     public void pushEntities() {
         final List<Entity> entities = this.level().getEntities(this, this.getBoundingBox().expandTowards(0.2D, 0.0D, 0.2D));
         entities.stream().filter(entity -> !(entity instanceof LeedsichthysPartEntity) && entity.isPushable()).forEach(entity -> entity.push(this));
     }

     public void travel(@NotNull Vec3 travelVector) {
         super.travel(travelVector);
     }

     protected @NotNull PathNavigation createNavigation(@NotNull Level p_27480_) {
         return new WaterBoundPathNavigation(this, p_27480_);
     }

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
         this.entityData.define(CHILD_UUID, Optional.empty());
         this.entityData.define(FROM_BOOK, false);
         this.entityData.define(CHILD_ID, -1);
     }

     public void addAdditionalSaveData(CompoundTag compound) {
         super.addAdditionalSaveData(compound);
         if (this.getChildId() != null) {
             compound.putUUID("ChildUUID", this.getChildId());
         }
     }

     public void readAdditionalSaveData(CompoundTag compound) {
         super.readAdditionalSaveData(compound);
         if (compound.hasUUID("ChildUUID")) {
             this.setChildId(compound.getUUID("ChildUUID"));
         }
     }

     @Nullable
     public UUID getChildId() {
         return this.entityData.get(CHILD_UUID).orElse(null);
     }

     public void setChildId(@Nullable UUID uniqueId) {
         this.entityData.set(CHILD_UUID, Optional.ofNullable(uniqueId));
     }

     public void tick() {
         super.tick();
     }

     private float getYawForPart(int i) {
         return this.getRingBuffer(4 + i * 2, 1.0F);
     }

     public float getRingBuffer(int bufferOffset, float partialTicks) {
         if (this.isDeadOrDying()) {
             partialTicks = 0.0F;
         }

         partialTicks = 1.0F - partialTicks;
         final int i = this.ringBufferIndex - bufferOffset & 63;
         final int j = this.ringBufferIndex - bufferOffset - 1 & 63;
         final float d0 = this.ringBuffer[i];
         final float d1 = this.ringBuffer[j] - d0;
         return Mth.wrapDegrees(d0 + d1 * partialTicks);
     }

     private float calcPartRotation(int i) {
         float snakeSway = 25;

         return (float) (snakeSway * -Math.sin(this.walkDist * 3 - (i))) * 0.2F * i;
     }

     public Entity getChild() {
         UUID id = getChildId();
         if (id != null && !level().isClientSide) {
             return ((ServerLevel) level()).getEntity(id);
         }
         return null;
     }

     @Override
     public void aiStep() {
         if (!this.isInWater() && this.onGround() && this.verticalCollision) {
             this.setDeltaMovement(this.getDeltaMovement().add((this.random.nextFloat() * 2.0F - 1.0F) * 0.05F, 0.4F, (this.random.nextFloat() * 2.0F - 1.0F) * 0.05F));
             this.setOnGround(false);
             this.hasImpulse = true;
             this.playSound(this.getFlopSound(), this.getSoundVolume(), this.getVoicePitch());
         }
         super.aiStep();
     }

     private boolean shouldReplaceParts() {
         if (parts == null || parts[0] == null)
             return true;

         for (int i = 0; i < 2; i++) {
             if (parts[i] == null) {
                 return true;
             }
         }
         return false;
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
     }

     protected <E extends LeedsichthysEntity> PlayState predicate(final software.bernie.geckolib.core.animation.AnimationState<E> event) {

         if(this.isFromBook()){
             return event.setAndContinue(LEEDS_SWIM);
         }

         int animState = this.getAnimationState();

         if(!this.isFromBook()) {

             if (animState == 21) {
                 return event.setAndContinue(LEEDS_BUMP);
             }

             else {
                 if (!(event.getLimbSwingAmount() > -0.06F && event.getLimbSwingAmount() < 0.06F) && this.isInWater()) {
                     event.setAndContinue(LEEDS_SWIM);
                     return PlayState.CONTINUE;
                 }
                 if (this.onGround()) {
                     event.setAndContinue(LEEDS_BEACHED_1);
                     return PlayState.CONTINUE;
                 }
                 else if (this.isFallFlying()) {
                     return event.setAndContinue(LEEDS_FREEFALL_1);
                 }
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
             }
         }
         return PlayState.CONTINUE;
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
         private final LeedsichthysEntity dolphin;

         public MoveHelperController(LeedsichthysEntity dolphinIn) {
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

     public boolean canDisableShield() {
         return true;
     }

     public int getAnimationState() {
         return this.entityData.get(ANIMATION_STATE);
     }

     public void setAnimationState(int anim) {
         this.entityData.set(ANIMATION_STATE, anim);
     }

     public boolean isFromBook() {
         return this.entityData.get(FROM_BOOK);
     }

     public void setIsFromBook(boolean fromBook) {
         this.entityData.set(FROM_BOOK, fromBook);
     }

     @Override
     public void setFromBook(boolean fromBook) {
         this.entityData.set(FROM_BOOK, fromBook);
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
     protected SoundEvent getAttackSound() {
         return null;
     }

     @Override
     protected int getKillHealAmount() {
         return 10;
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

     public static boolean checkSurfaceWaterDinoSpawnRules(EntityType<? extends WaterAnimal> pWaterAnimal, LevelAccessor pLevel, MobSpawnType pSpawnType, BlockPos pPos, RandomSource pRandom) {
         int i = pLevel.getSeaLevel();
         int j = i - 13;
         return pPos.getY() >= j && pPos.getY() <= i && pLevel.getFluidState(pPos.below()).is(FluidTags.WATER) && pLevel.getBlockState(pPos.above()).is(Blocks.WATER) && UnusualPrehistoryConfig.DINO_NATURAL_SPAWNING.get();
     }
 }
