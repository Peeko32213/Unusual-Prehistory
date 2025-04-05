package com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.flying;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.peeko32213.unusualprehistory.common.entity.animation.state.EntityAction;
import com.peeko32213.unusualprehistory.common.entity.animation.state.StateHelper;
import com.peeko32213.unusualprehistory.common.entity.animation.state.WeightedState;
import com.peeko32213.unusualprehistory.common.entity.custom.base.PrehistoricEntity;
import com.peeko32213.unusualprehistory.common.entity.util.interfaces.IVariantEntity;
import com.peeko32213.unusualprehistory.common.entity.util.navigator.FlyingMoveController;
import com.peeko32213.unusualprehistory.common.entity.util.navigator.SmartBodyHelper;
import com.peeko32213.unusualprehistory.core.registry.UPSounds;
import com.peeko32213.unusualprehistory.core.registry.items.UPItems;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;

public class KimmeridgebrachypteraeschnidiumEntity extends PrehistoricEntity implements IVariantEntity, Bucketable {

    @Nullable
    private static final EntityDataAccessor<Boolean> FLYING = SynchedEntityData.defineId(KimmeridgebrachypteraeschnidiumEntity.class, EntityDataSerializers.BOOLEAN);

    private static final EntityDataAccessor<Integer> BASE_COLOR = SynchedEntityData.defineId(KimmeridgebrachypteraeschnidiumEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> PATTERN = SynchedEntityData.defineId(KimmeridgebrachypteraeschnidiumEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> PATTERN_COLOR = SynchedEntityData.defineId(KimmeridgebrachypteraeschnidiumEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> HAS_PATTERN = SynchedEntityData.defineId(KimmeridgebrachypteraeschnidiumEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> WING_COLOR = SynchedEntityData.defineId(KimmeridgebrachypteraeschnidiumEntity.class, EntityDataSerializers.INT);

    private static final EntityDataAccessor<Boolean> FROM_BUCKET = SynchedEntityData.defineId(KimmeridgebrachypteraeschnidiumEntity.class, EntityDataSerializers.BOOLEAN);

    public final float[] ringBuffer = new float[64];
    public float prevFlyProgress;
    public float flyProgress;
    public int ringBufferIndex = -1;
    private boolean isLandNavigator;
    private int timeFlying;
    private int flapCooldown = 0;

    public float currentRoll = 0.0F;
    public float prevTilt;
    public float tilt;

    // Movement animations
    private static final RawAnimation KIMMER_FLY = RawAnimation.begin().thenLoop("animation.kimmeridgebrachypteraeschnidium.fly");

    // Idle animations
    private static final RawAnimation KIMMER_IDLE = RawAnimation.begin().thenLoop("animation.kimmeridgebrachypteraeschnidium.idle1");
    private static final RawAnimation KIMMER_HOVER = RawAnimation.begin().thenLoop("animation.kimmeridgebrachypteraeschnidium.hover");
    private static final RawAnimation KIMMER_PREEN = RawAnimation.begin().thenPlay("animation.kimmeridgebrachypteraeschnidium.preen");

    // Idle accessors
    private static final EntityDataAccessor<Boolean> PREEN = SynchedEntityData.defineId(KimmeridgebrachypteraeschnidiumEntity.class, EntityDataSerializers.BOOLEAN);

    // Starting predicates
    private static final Predicate<LivingEntity> KIMMER_STARTING_PREDICATE = (e -> {
        if(e instanceof KimmeridgebrachypteraeschnidiumEntity entity) {
            return !entity.isAggro() && !entity.isSprinting() && !entity.isInWater() && !entity.isFlying();
        }
        return false;
    });

    // Idle actions
    private static final EntityAction KIMMER_PREEN_ACTION = new EntityAction(0, (e) -> {}, 1);

    private static final StateHelper KIMMER_PREEN_STATE =
            StateHelper.Builder.state(PREEN, "kimmeridgebrachypteraeschnidium_preen")
                    .playTime(60)
                    .stopTime(130)
                    .startingPredicate(KIMMER_STARTING_PREDICATE)
                    .affectsAI(true)
                    .affectedFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK))
                    .entityAction(KIMMER_PREEN_ACTION)
                    .build();

    // States
    @Override
    public ImmutableMap<String, StateHelper> getStates() {
        return ImmutableMap.of(
                KIMMER_PREEN_STATE.getName(), KIMMER_PREEN_STATE
        );
    }

    @Override
    public List<WeightedState<StateHelper>> getWeightedStatesToPerform() {
        return ImmutableList.of(
                WeightedState.of(KIMMER_PREEN_STATE, 10)
        );
    }

    // Body control / navigation
    @Override
    protected @NotNull BodyRotationControl createBodyControl() {
        SmartBodyHelper helper = new SmartBodyHelper(this);
        if(this.isFlying()) {
            helper.bodyLagMoving = 0.05F;
            helper.bodyLagStill = 0.1F;
        }
        else {
            helper.bodyLagMoving = 0.4F;
            helper.bodyLagStill = 0.25F;
        }
        return helper;
    }

    public KimmeridgebrachypteraeschnidiumEntity(EntityType<? extends PrehistoricEntity> entityType, Level level) {
        super(entityType, level);
        switchNavigator(true);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
            .add(Attributes.MAX_HEALTH, 6.0D)
            .add(Attributes.MOVEMENT_SPEED, 0.2F);
    }

    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new KimmerFlightGoal());
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(PREEN, false);
        this.entityData.define(FROM_BUCKET, false);
        this.entityData.define(FLYING, false);
        this.entityData.define(BASE_COLOR, 0);
        this.entityData.define(PATTERN, 0);
        this.entityData.define(PATTERN_COLOR, 0);
        this.entityData.define(HAS_PATTERN, false);
        this.entityData.define(WING_COLOR, 0);
    }

    public void addAdditionalSaveData(@NotNull CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("Flying", this.isFlying());
        compound.putInt("BaseColor", this.getBaseColor());
        compound.putInt("Pattern", this.getPattern());
        compound.putInt("PatternColor", this.getPatternColor());
        compound.putInt("WingColor", this.getWingColor());
        compound.putBoolean("HasPattern", this.getHasPattern());
    }

    public void readAdditionalSaveData(@NotNull CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setFlying(compound.getBoolean("Flying"));
        this.setBaseColor(compound.getInt("BaseColor"));
        this.setPattern(compound.getInt("Pattern"));
        this.setPatternColor(compound.getInt("PatternColor"));
        this.setWingColor(compound.getInt("WingColor"));
        this.setHasPattern(compound.getBoolean("HasPattern"));
    }

    public boolean fromBucket() {
        return this.entityData.get(FROM_BUCKET);
    }

    public void setFromBucket(boolean pFromBucket) {
        this.entityData.set(FROM_BUCKET, pFromBucket);
    }

    @Override
    public void saveToBucketTag(ItemStack bucket) {
        CompoundTag compoundnbt = bucket.getOrCreateTag();
        Bucketable.saveDefaultDataToBucketTag(this, bucket);
        compoundnbt.putFloat("Health", this.getHealth());
        compoundnbt.putInt("BaseColor", this.getBaseColor());
        compoundnbt.putInt("Pattern", this.getPattern());
        compoundnbt.putInt("PatternColor", this.getPatternColor());
        compoundnbt.putInt("WingColor", this.getWingColor());
        compoundnbt.putBoolean("HasPattern", this.getHasPattern());
        compoundnbt.putInt("Age", this.getAge());
        if (this.hasCustomName()) {
            bucket.setHoverName(this.getCustomName());
        }
    }

    @Override
    public void loadFromBucketTag(CompoundTag pTag) {
        Bucketable.loadDefaultDataFromBucketTag(this, pTag);
    }

    @Override
    public ItemStack getBucketItemStack() {
        return new ItemStack(UPItems.CAPTURED_KIMMER_BOTTLE.get());
    }

    @Override
    public SoundEvent getPickupSound() {
        return SoundEvents.BOTTLE_FILL_DRAGONBREATH;
    }

    public int getBaseColor() {
        return this.entityData.get(BASE_COLOR);
    }
    public void setBaseColor(int variant) {
        this.entityData.set(BASE_COLOR, variant);
    }

    public int getPattern() {
        return this.entityData.get(PATTERN);
    }
    public void setPattern(int variant) {
        this.entityData.set(PATTERN, variant);
    }

    public int getPatternColor() {
        return this.entityData.get(PATTERN_COLOR);
    }
    public void setPatternColor(int variant) {
        this.entityData.set(PATTERN_COLOR, variant);
    }

    public int getWingColor() {
        return this.entityData.get(WING_COLOR);
    }
    public void setWingColor(int variant) {
        this.entityData.set(WING_COLOR, variant);
    }

    public Boolean getHasPattern() {
        return this.entityData.get(HAS_PATTERN);
    }
    public void setHasPattern(Boolean variant) {
        this.entityData.set(HAS_PATTERN, variant);
    }

    private void setBucketData(ItemStack bucket) {
        CompoundTag compoundnbt = bucket.getOrCreateTag();
        compoundnbt.putFloat("Health", this.getHealth());
        compoundnbt.putInt("BaseColor", this.getBaseColor());
        compoundnbt.putInt("Pattern", this.getPattern());
        compoundnbt.putInt("PatternColor", this.getPatternColor());
        compoundnbt.putBoolean("HasPattern", this.getHasPattern());
        compoundnbt.putInt("WingColor", this.getWingColor());

        if (this.hasCustomName()) {
            bucket.setHoverName(this.getCustomName());
        }
    }

    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {

        if (pReason == MobSpawnType.BUCKET && pDataTag != null && pDataTag.contains("BaseColor", 3)) {
            this.setBaseColor(pDataTag.getInt("BaseColor"));
            this.setPattern(pDataTag.getInt("Pattern"));
            this.setPatternColor(pDataTag.getInt("PatternColor"));
            this.setHasPattern(pDataTag.getBoolean("HasPattern"));
            this.setWingColor(pDataTag.getInt("WingColor"));
        }
        else {
            this.setBaseColor(this.random.nextInt(16));
            this.setPattern(this.random.nextInt(7));
            this.setPatternColor(this.random.nextInt(16));
            this.setHasPattern(this.random.nextInt(3)==0);
            this.setWingColor(this.random.nextInt(16));
        }

        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
    }

    public static String getPatternName(int pattern){
        return switch (pattern){
            case 1 -> "tailshade";
            case 2 -> "topshade";
            case 3 -> "halfshade";
            case 4 -> "large_stripe";
            case 5 -> "racing_stripe";
            case 6 -> "large_racing_stripe";
            default -> "stripe";
        };
    }

    private void switchNavigator(boolean onLand) {
        if (onLand) {
            this.moveControl = new MoveControl(this);
            this.navigation = new GroundPathNavigation(this, level());
            this.isLandNavigator = true;
        } else {
            this.moveControl = new FlyingMoveController(this, 0.6F, false, true);
            this.navigation = new FlyingPathNavigation(this, level()) {
                public boolean isStableDestination(BlockPos pos) {
                    return !this.level.getBlockState(pos.below(2)).isAir();
                }
            };
            navigation.setCanFloat(false);
            this.isLandNavigator = false;
        }
    }

    public void tick() {
        super.tick();
        this.prevFlyProgress = flyProgress;
        if (this.isFlying() && flyProgress < 5F) {
            flyProgress++;
        }
        if (!this.isFlying() && flyProgress > 0F) {
            flyProgress--;
        }
        if (this.ringBufferIndex < 0) {
            for (int i = 0; i < this.ringBuffer.length; ++i) {
                this.ringBuffer[i] = 15;
            }
        }
        this.ringBufferIndex++;
        if (this.ringBufferIndex == this.ringBuffer.length) {
            this.ringBufferIndex = 0;
        }
        if (!level().isClientSide) {
            if (isFlying() && this.isLandNavigator) {
                switchNavigator(false);
            }
            if (!isFlying() && !this.isLandNavigator) {
                switchNavigator(true);
            }
            if (this.isFlying()) {
                if (this.isFlying() && !this.onGround()) {
                    if (!this.isInWaterOrBubble()) {
                        this.setDeltaMovement(this.getDeltaMovement().multiply(1F, 0.6F, 1F));
                    }
                }
                if (this.onGround() && timeFlying > 20) {
                    this.setFlying(false);
                }
                this.timeFlying++;
            } else {
                this.timeFlying = 0;
            }
        }

//        if (flapCooldown == 0 && this.isFlying()) {
//            flapCooldown = 1;
//            this.playSound(UPSounds.KIMMER_FLAP.get(), 0.025F * this.getSoundVolume(), 2.0F);
//        }
//        if(flapCooldown > 0){
//            flapCooldown--;
//        }

        prevTilt = tilt;
        if (this.isFlying() && !this.onGround()) {
            final float v = Mth.degreesDifference(this.getYRot(), yRotO);
            if (Math.abs(v) > 1) {
                if (Math.abs(tilt) < 25) {
                    tilt -= Math.signum(v);
                }
            } else {
                if (Math.abs(tilt) > 0) {
                    final float tiltSign = Math.signum(tilt);
                    tilt -= tiltSign * 0.85F;
                    if (tilt * tiltSign < 0) {
                        tilt = 0;
                    }
                }
            }
        } else {
            tilt = 0;
        }

        float prevRoll = this.currentRoll;
        float targetRoll = Math.max(-0.45F, Math.min(0.45F, (this.getYRot() - this.yRotO) * 0.1F));
        targetRoll = -targetRoll;
        this.currentRoll = prevRoll + (targetRoll - prevRoll) * 0.05F;
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        return source.is(DamageTypes.IN_WALL)  || source.is(DamageTypes.FALL) || source.is(DamageTypes.CACTUS) || super.isInvulnerableTo(source);
    }

    @Override
    @NotNull
    public MobType getMobType() {
        return MobType.ARTHROPOD;
    }

    // Flight
    public boolean isFlying() {
        return this.entityData.get(FLYING);
    }

    public void setFlying(boolean flying) {
        if (flying && isBaby()) {
            return;
        }
        this.entityData.set(FLYING, flying);
    }

    public boolean canBlockBeSeen(BlockPos pos) {
        double x = pos.getX() + 0.5F;
        double y = pos.getY() + 0.5F;
        double z = pos.getZ() + 0.5F;
        HitResult result = this.level().clip(new ClipContext(new Vec3(this.getX(), this.getY() + (double) this.getEyeHeight(), this.getZ()), new Vec3(x, y, z), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this));
        double dist = result.getLocation().distanceToSqr(x, y, z);
        return dist <= 1.0D || result.getType() == HitResult.Type.MISS;
    }

    public Vec3 getBlockGrounding(Vec3 fleePos) {
        final float radius = 3.15F * -3 - this.getRandom().nextInt(24);
        float neg = this.getRandom().nextBoolean() ? 1 : -1;
        float renderYawOffset = this.yBodyRot;
        float angle = (0.01745329251F * renderYawOffset) + 3.15F + (this.getRandom().nextFloat() * neg);
        final double extraX = radius * Mth.sin(Mth.PI + angle);
        final double extraZ = radius * Mth.cos(angle);
        final BlockPos radialPos = new BlockPos((int) (fleePos.x() + extraX), (int) getY(), (int) (fleePos.z() + extraZ));
        BlockPos ground = this.getGround(radialPos);
        if (ground.getY() == -64) {
            return this.position();
        } else {
            ground = this.blockPosition();
            while (ground.getY() > -64 && !level().getBlockState(ground).isSolid()) {
                ground = ground.below();
            }
        }
        if (!this.isTargetBlocked(Vec3.atCenterOf(ground.above()))) {
            return Vec3.atCenterOf(ground);
        }
        return null;
    }

    public boolean isTargetBlocked(Vec3 target) {
        Vec3 Vector3d = new Vec3(this.getX(), this.getEyeY(), this.getZ());

        return this.level().clip(new ClipContext(Vector3d, target, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this)).getType() != HitResult.Type.MISS;
    }

    public Vec3 getBlockInViewAway(Vec3 fleePos, float radiusAdd) {
        float radius = 5 + radiusAdd + this.getRandom().nextInt(5);
        float neg = this.getRandom().nextBoolean() ? 1 : -1;
        float renderYawOffset = this.yBodyRot;
        float angle = (0.01745329251F * renderYawOffset) + 3.15F + (this.getRandom().nextFloat() * neg);
        double extraX = radius * Mth.sin((float) (Math.PI + angle));
        double extraZ = radius * Mth.cos(angle);
        final BlockPos radialPos = new BlockPos((int) (fleePos.x() + extraX), (int) getY(), (int) (fleePos.z() + extraZ));
        BlockPos ground = getGround(radialPos);
        int distFromGround = (int) this.getY() - ground.getY();
        int flightHeight = 5 + this.getRandom().nextInt(5);
        int j = this.getRandom().nextInt(5) + 5;

        BlockPos newPos = ground.above(distFromGround > 5 ? flightHeight : j);
        if (!this.isTargetBlocked(Vec3.atCenterOf(newPos)) && this.distanceToSqr(Vec3.atCenterOf(newPos)) > 1) {
            return Vec3.atCenterOf(newPos);
        }
        return null;
    }

    private boolean isOverWaterOrVoid() {
        BlockPos position = this.blockPosition();
        while (position.getY() > -65 && level().isEmptyBlock(position)) {
            position = position.below();
        }
        return !level().getFluidState(position).isEmpty() || level().getBlockState(position).is(Blocks.VINE) || position.getY() <= -65;
    }

    public BlockPos getGround(BlockPos in) {
        BlockPos position = new BlockPos(in.getX(), (int) this.getY(), in.getZ());
        while (position.getY() > -64 && !level().getBlockState(position).isSolid() && level().getFluidState(position).isEmpty()) {
            position = position.below();
        }
        return position;
    }

    private class KimmerFlightGoal extends Goal {
        protected double x;
        protected double y;
        protected double z;

        public KimmerFlightGoal() {
            super();
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            if (KimmeridgebrachypteraeschnidiumEntity.this.isVehicle() || (KimmeridgebrachypteraeschnidiumEntity.this.getTarget() != null && KimmeridgebrachypteraeschnidiumEntity.this.getTarget().isAlive()) || KimmeridgebrachypteraeschnidiumEntity.this.isPassenger()) {
                return false;
            } else {
                if (KimmeridgebrachypteraeschnidiumEntity.this.getRandom().nextInt(45) != 0 && !KimmeridgebrachypteraeschnidiumEntity.this.isFlying()) {
                    return false;
                }

                Vec3 lvt_1_1_ = this.getPosition();
                if (lvt_1_1_ == null) {
                    return false;
                } else {
                    this.x = lvt_1_1_.x;
                    this.y = lvt_1_1_.y;
                    this.z = lvt_1_1_.z;
                    return true;
                }
            }
        }

        public void tick() {
            KimmeridgebrachypteraeschnidiumEntity.this.getMoveControl().setWantedPosition(this.x, this.y, this.z, 1F);
            if (isFlying() && KimmeridgebrachypteraeschnidiumEntity.this.onGround() && KimmeridgebrachypteraeschnidiumEntity.this.timeFlying > 10) {
                KimmeridgebrachypteraeschnidiumEntity.this.setFlying(false);
            }
        }

        @Nullable
        protected Vec3 getPosition() {
            Vec3 vector3d = KimmeridgebrachypteraeschnidiumEntity.this.position();
            if (KimmeridgebrachypteraeschnidiumEntity.this.timeFlying < 200 || KimmeridgebrachypteraeschnidiumEntity.this.isOverWaterOrVoid()) {
                return KimmeridgebrachypteraeschnidiumEntity.this.getBlockInViewAway(vector3d, 0);
            } else {
                return KimmeridgebrachypteraeschnidiumEntity.this.getBlockGrounding(vector3d);
            }
        }

        public boolean canContinueToUse() {
            return KimmeridgebrachypteraeschnidiumEntity.this.isFlying() && KimmeridgebrachypteraeschnidiumEntity.this.distanceToSqr(x, y, z) > 5F;
        }

        public void start() {
            KimmeridgebrachypteraeschnidiumEntity.this.setFlying(true);
            KimmeridgebrachypteraeschnidiumEntity.this.getMoveControl().setWantedPosition(this.x, this.y, this.z, 1F);
        }

        public void stop() {
            KimmeridgebrachypteraeschnidiumEntity.this.getNavigation().stop();
            x = 0;
            y = 0;
            z = 0;
            super.stop();
        }
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel p_146743_, @NotNull AgeableMob p_146744_) {
        return null;
    }

    // Mob interactions
    @Override
    public @NotNull InteractionResult mobInteract(Player player, @NotNull InteractionHand hand) {
        ItemStack heldItem = player.getItemInHand(hand);

        if (heldItem.getItem() == Items.GLASS_BOTTLE && this.isAlive()) {
            playSound(SoundEvents.BOTTLE_FILL_DRAGONBREATH, 0.5F, 1.0F);
            heldItem.shrink(1);
            ItemStack itemstack1 = new ItemStack(UPItems.CAPTURED_KIMMER_BOTTLE.get());
            this.setBucketData(itemstack1);
            if (!this.level().isClientSide) {
                CriteriaTriggers.FILLED_BUCKET.trigger((ServerPlayer) player, itemstack1);
            }
            if (heldItem.isEmpty() && !player.isCreative()) {
                player.setItemInHand(hand, itemstack1);
            } else if (!player.getInventory().add(itemstack1)) {
                player.drop(itemstack1, false);
            }
            this.discard();
            return InteractionResult.SUCCESS;
        }
        return super.mobInteract(player, hand);
    }

    // Animation control
    @Override
    public void registerControllers(final AnimatableManager.ControllerRegistrar controllers) {
        AnimationController<KimmeridgebrachypteraeschnidiumEntity> controller = new AnimationController<>(this, "controller", 5, this::predicate);
        controllers.add(controller);

        AnimationController<KimmeridgebrachypteraeschnidiumEntity> idle = new AnimationController<>(this, "idleController", 5, this::idlePredicate);
        controllers.add(idle);
    }

    protected <E extends KimmeridgebrachypteraeschnidiumEntity> PlayState predicate(final software.bernie.geckolib.core.animation.AnimationState<E> event) {
        if(this.isFromBook()){
            return event.setAndContinue(KIMMER_HOVER);
        }
        if (event.isMoving() && this.onGround()) {
            return event.setAndContinue(KIMMER_HOVER);
        }
        if (!event.isMoving() && this.onGround() && !this.isFlying()) {
            return event.setAndContinue(KIMMER_IDLE);
        }
        return event.setAndContinue(KIMMER_FLY);
    }

    // Idle animations
    protected <E extends KimmeridgebrachypteraeschnidiumEntity> PlayState idlePredicate(final AnimationState<E> event) {
        if (getBooleanState(PREEN)) {
            event.getController().setAnimation(KIMMER_PREEN);
            return PlayState.CONTINUE;
        }
        event.getController().forceAnimationReset();
        return PlayState.STOP;
    }

    protected void playStepSound(BlockPos pPos, BlockState pBlock) {
    }

    protected float getSoundVolume() {
        return 0.25F;
    }

    @Override
    protected void doPush(Entity entity) {
    }

    @Override
    protected void pushEntities() {
    }

    @Override
    protected Entity.@NotNull MovementEmission getMovementEmission() {
        return Entity.MovementEmission.EVENTS;
    }

    @Override
    protected void doWaterSplashEffect() {
    }

    @Override
    public boolean causeFallDamage(float pFallDistance, float pMultiplier, DamageSource pSource) {
        return false;
    }

    protected void checkFallDamage(double y, boolean onGroundIn, @NotNull BlockState state, @NotNull BlockPos pos) {
    }
}
