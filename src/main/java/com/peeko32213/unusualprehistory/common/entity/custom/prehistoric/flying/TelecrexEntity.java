package com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.flying;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.peeko32213.unusualprehistory.common.entity.animation.state.EntityAction;
import com.peeko32213.unusualprehistory.common.entity.animation.state.RandomStateGoal;
import com.peeko32213.unusualprehistory.common.entity.animation.state.StateHelper;
import com.peeko32213.unusualprehistory.common.entity.animation.state.WeightedState;
import com.peeko32213.unusualprehistory.common.entity.custom.ai.goal.TelecrexFlightGoal;
import com.peeko32213.unusualprehistory.common.entity.custom.ai.goal.TelecrexScatterGoal;
import com.peeko32213.unusualprehistory.common.entity.custom.base.PrehistoricEntity;
import com.peeko32213.unusualprehistory.common.entity.util.navigator.DirectPathNavigator;
import com.peeko32213.unusualprehistory.common.entity.util.navigator.FlyingMoveController;
import com.peeko32213.unusualprehistory.common.entity.util.navigator.SmartBodyHelper;
import com.peeko32213.unusualprehistory.common.entity.util.navigator.SmoothGroundNavigation;
import com.peeko32213.unusualprehistory.core.other.tags.UPItemTags;
import com.peeko32213.unusualprehistory.core.other.util.UPMath;
import com.peeko32213.unusualprehistory.core.registry.UPSounds;
import com.peeko32213.unusualprehistory.core.registry.entities.UPEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.keyframe.event.SoundKeyframeEvent;
import software.bernie.geckolib.core.object.PlayState;

import javax.annotation.Nullable;
import java.util.*;

public class TelecrexEntity extends PrehistoricEntity {

    private static final EntityDataAccessor<Boolean> FLYING = SynchedEntityData.defineId(TelecrexEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> TAKING_OFF = SynchedEntityData.defineId(TelecrexEntity.class, EntityDataSerializers.BOOLEAN);

    private static final Ingredient TEMPTATION_ITEMS = Ingredient.of(UPItemTags.TELECREX_FOOD);

    public float currentRoll = 0.0F;
    public float prevTilt;
    public float tilt;

    public float prevFlyProgress;
    public float flyProgress;
    private boolean isLandNavigator;
    public int timeFlying;
    protected int takeoffTime;

    // Movement animations
    private static final RawAnimation TELECREX_WALK = RawAnimation.begin().thenLoop("animation.telecrex.walk");
    private static final RawAnimation TELECREX_FLY = RawAnimation.begin().thenLoop("animation.telecrex.fly");
    private static final RawAnimation TELECREX_FLY_FAST = RawAnimation.begin().thenLoop("animation.telecrex.flyfast");

    // Idle animations
    private static final RawAnimation TELECREX_IDLE = RawAnimation.begin().thenLoop("animation.telecrex.idle");
    private static final RawAnimation TELECREX_HOVER = RawAnimation.begin().thenLoop("animation.telecrex.hover");
    private static final RawAnimation TELECREX_PECK = RawAnimation.begin().thenPlay("animation.telecrex.peck");
    private static final RawAnimation TELECREX_PREEN_1 = RawAnimation.begin().thenPlay("animation.telecrex.preen1");
    private static final RawAnimation TELECREX_PREEN_2 = RawAnimation.begin().thenPlay("animation.telecrex.preen2");
    private static final RawAnimation TELECREX_LOOKOUT_1 = RawAnimation.begin().thenPlay("animation.telecrex.lookout_blend1");
    private static final RawAnimation TELECREX_LOOKOUT_2 = RawAnimation.begin().thenPlay("animation.telecrex.lookout_blend2");

    // Misc animations
    // todo: add takeoff animation when starting flight
    private static final RawAnimation TELECREX_TAKEOFF = RawAnimation.begin().thenPlay("animation.telecrex.takeoff");

    // Idle accessors
    private static final EntityDataAccessor<Boolean> PECK = SynchedEntityData.defineId(TelecrexEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> PREEN1 = SynchedEntityData.defineId(TelecrexEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> PREEN2 = SynchedEntityData.defineId(TelecrexEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> LOOKOUT1 = SynchedEntityData.defineId(TelecrexEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> LOOKOUT2 = SynchedEntityData.defineId(TelecrexEntity.class, EntityDataSerializers.BOOLEAN);

    // Idle actions
    private static final EntityAction TELECREX_PECK_ACTION = new EntityAction(0, (e) -> {}, 1);

    private final StateHelper TELECREX_PECK_STATE =
            StateHelper.Builder.state(PECK, "telecrex_peck")
                    .playTime(40)
                    .stopTime(100)
                    .affectedFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK))
                    .entityAction(TELECREX_PECK_ACTION)
                    .build();

    private static final EntityAction TELECREX_PREEN1_ACTION = new EntityAction(0, (e) -> {}, 1);

    private static final StateHelper TELECREX_PREEN1_STATE =
            StateHelper.Builder.state(PREEN1, "telecrex_preen1")
                    .playTime(60)
                    .stopTime(130)
                    .affectedFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK))
                    .entityAction(TELECREX_PREEN1_ACTION)
                    .build();

    private static final EntityAction TELECREX_PREEN2_ACTION = new EntityAction(0, (e) -> {}, 1);

    private static final StateHelper TELECREX_PREEN2_STATE =
            StateHelper.Builder.state(PREEN2, "telecrex_preen2")
                    .playTime(60)
                    .stopTime(130)
                    .affectedFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK))
                    .entityAction(TELECREX_PREEN2_ACTION)
                    .build();

    private static final EntityAction TELECREX_LOOKOUT1_ACTION = new EntityAction(0, (e) -> {}, 1);

    private static final StateHelper TELECREX_LOOKOUT1_STATE =
            StateHelper.Builder.state(LOOKOUT1, "telecrex_lookout1")
                    .playTime(40)
                    .stopTime(120)
                    .entityAction(TELECREX_LOOKOUT1_ACTION)
                    .build();

    private static final EntityAction TELECREX_LOOKOUT2_ACTION = new EntityAction(0, (e) -> {}, 1);

    private static final StateHelper TELECREX_LOOKOUT2_STATE =
            StateHelper.Builder.state(LOOKOUT2, "telecrex_lookout2")
                    .playTime(40)
                    .stopTime(120)
                    .entityAction(TELECREX_LOOKOUT2_ACTION)
                    .build();

    @Override
    public ImmutableMap<String, StateHelper> getStates() {
        return ImmutableMap.of(
                TELECREX_PECK_STATE.getName(), TELECREX_PECK_STATE,
                TELECREX_PREEN1_STATE.getName(), TELECREX_PREEN1_STATE,
                TELECREX_PREEN2_STATE.getName(), TELECREX_PREEN2_STATE,
                TELECREX_LOOKOUT1_STATE.getName(), TELECREX_LOOKOUT1_STATE,
                TELECREX_LOOKOUT2_STATE.getName(), TELECREX_LOOKOUT2_STATE
        );
    }

    @Override
    public List<WeightedState<StateHelper>> getWeightedStatesToPerform() {
        return ImmutableList.of(
                WeightedState.of(TELECREX_PECK_STATE, 8),
                WeightedState.of(TELECREX_PREEN1_STATE, 7),
                WeightedState.of(TELECREX_PREEN2_STATE, 7),
                WeightedState.of(TELECREX_LOOKOUT1_STATE, 10),
                WeightedState.of(TELECREX_LOOKOUT2_STATE, 10)
        );
    }

    // Body control / navigation
    @Override
    protected @NotNull BodyRotationControl createBodyControl() {
        SmartBodyHelper helper = new SmartBodyHelper(this);
        if (this.isFlying()) {
            helper.bodyLagMoving = 0.02F;
            helper.bodyLagStill = 0.08F;
        } else {
            helper.bodyLagMoving = 0.5F;
            helper.bodyLagStill = 0.25F;
        }
        return helper;
    }

    @Override
    protected @NotNull PathNavigation createNavigation(Level levelIn) {
        return new SmoothGroundNavigation(this, levelIn);
    }

    public TelecrexEntity(EntityType<? extends PrehistoricEntity> entityType, Level level) {
        super(entityType, level);
        this.setPathfindingMalus(BlockPathTypes.DANGER_FIRE, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.WATER, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.WATER_BORDER, 16.0F);
        this.setPathfindingMalus(BlockPathTypes.COCOA, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.FENCE, -1.0F);
        switchNavigator(false);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
            .add(Attributes.MAX_HEALTH, 8.0D)
            .add(Attributes.MOVEMENT_SPEED, 0.16D);
    }

    private void switchNavigator(boolean onLand) {
        if (onLand) {
            this.moveControl = new MoveControl(this);
            this.navigation = new SmoothGroundNavigation(this, level());
            this.isLandNavigator = true;
        } else {
            this.moveControl = new FlyingMoveController(this, 0.6F, false);
            this.navigation = new DirectPathNavigator(this, level());
            this.isLandNavigator = false;
        }
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new RandomStateGoal<>(this) {
        public boolean canUse() {
                return !isFlying() && !getMoveControl().hasWanted() && onGround() && super.canUse();
            }
        });
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(2, new PanicGoal(this, 1.5D));
        this.goalSelector.addGoal(5, new FollowParentGoal(this, 1.1D));
        this.goalSelector.addGoal(6, new TelecrexFlightGoal(this));
        this.goalSelector.addGoal(4, new TelecrexScatterGoal(this));
        this.goalSelector.addGoal(7, new TemptGoal(this, 1.2D, TEMPTATION_ITEMS, false));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(5, (new HurtByTargetGoal(this, Player.class)).setAlertOthers());
    }

    // Data
    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(PECK, false);
        this.entityData.define(PREEN1, false);
        this.entityData.define(PREEN2, false);
        this.entityData.define(LOOKOUT1, false);
        this.entityData.define(LOOKOUT2, false);
        this.entityData.define(FLYING, false);
    }

    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("Flying", this.isFlying());
    }

    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setFlying(compound.getBoolean("Flying"));
    }

    public boolean isFlying() {
        return this.entityData.get(FLYING);
    }
    public void setFlying(boolean flying) {
        this.entityData.set(FLYING, flying);
    }

    public boolean isFood(ItemStack stack) {
        return stack.is(UPItemTags.TELECREX_FOOD);
    }

    // Mob interactions
    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if(hand != InteractionHand.MAIN_HAND) return InteractionResult.FAIL;
        if (this.isFood(itemstack) && this.getHealth() < this.getMaxHealth()) {
            if (!player.getAbilities().instabuild) {
                itemstack.shrink(1);
            }
            if(!this.level().isClientSide) {
                this.level().broadcastEntityEvent(this, (byte) 7);
            }
            this.playSound(SoundEvents.PARROT_EAT, 0.25F, this.getVoicePitch());
            this.gameEvent(GameEvent.EAT, this);
            this.heal(2);
            return InteractionResult.SUCCESS;
        }
        return super.mobInteract(player, hand);
    }

    public void tick() {
        super.tick();

        this.prevFlyProgress = flyProgress;
        if (isFlying()) {
            if (flyProgress < 5F)
                flyProgress++;
        } else {
            if (flyProgress > 0F)
                flyProgress--;
        }
        if (!this.level().isClientSide) {
            final boolean isFlying = isFlying();
            if (isFlying && this.isLandNavigator) {
                switchNavigator(false);
            }
            if (!isFlying && !this.isLandNavigator) {
                switchNavigator(true);
            }
            if (isFlying) {
                timeFlying++;
                this.setNoGravity(true);
                if (this.onGround()) {
                    this.setFlying(false);
                }
            }
            else {
                timeFlying = 0;
                this.setNoGravity(false);
            }
        }

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

    public void travel(Vec3 vec3d) {
        if (this.isInWater() && this.getDeltaMovement().y > 0F) {
            this.setDeltaMovement(this.getDeltaMovement().multiply(1.0D, 0.5D, 1.0D));
        }
        super.travel(vec3d);
    }

    public boolean isTargetBlocked(Vec3 target) {
        Vec3 Vector3d = new Vec3(this.getX(), this.getEyeY(), this.getZ());
        return this.level().clip(new ClipContext(Vector3d, target, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this)).getType() != HitResult.Type.MISS;
    }

    public Vec3 getBlockInViewAway(Vec3 fleePos, float radiusAdd) {
        final float radius = 3.15F * -3 - this.getRandom().nextInt(24) - radiusAdd;
        final float angle = getAngle1();
        final double extraX = radius * Mth.sin(Mth.PI + angle);
        final double extraZ = radius * Mth.cos(angle);
        final BlockPos radialPos = new BlockPos((int) (fleePos.x() + extraX), 0, (int) (fleePos.z() + extraZ));
        final BlockPos ground = getGround(radialPos);
        final int distFromGround = (int) this.getY() - ground.getY();
        final BlockPos newPos;
        if (distFromGround > 8) {
            final int flightHeight = 4 + this.getRandom().nextInt(10);
            newPos = ground.above(flightHeight);
        } else {
            newPos = ground.above(this.getRandom().nextInt(6) + 1);
        }

        if (!this.isTargetBlocked(Vec3.atCenterOf(newPos)) && this.distanceToSqr(Vec3.atCenterOf(newPos)) > 1) {
            return Vec3.atCenterOf(newPos);
        }
        return null;
    }

    private BlockPos getGround(BlockPos in) {
        BlockPos position = new BlockPos(in.getX(), (int) this.getY(), in.getZ());
        while (position.getY() > -64 && !level().getBlockState(position).isSolid() && level().getFluidState(position).isEmpty()) {
            position = position.below();
        }
        return position;
    }

    public Vec3 getBlockGrounding(Vec3 fleePos) {
        final float radius = 3.15F * -3 - this.getRandom().nextInt(24);
        final float angle = getAngle1();
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

    private float getAngle1() {
        final float neg = this.getRandom().nextBoolean() ? 1 : -1;
        final float renderYawOffset = this.yBodyRot;
        return (UPMath.STARTING_ANGLE * renderYawOffset) + 3.15F + (this.getRandom().nextFloat() * neg);
    }

    public boolean isOverWater() {
        BlockPos position = this.blockPosition();
        while (position.getY() > -64 && level().isEmptyBlock(position)) {
            position = position.below();
        }
        return !level().getFluidState(position).isEmpty();
    }

    // Set sprinting
    @Override
    public void customServerAiStep() {
        if (this.getMoveControl().hasWanted()) {
            this.setSprinting(this.getMoveControl().getSpeedModifier() >= 1.25D);
        } else {
            this.setSprinting(false);
        }
        super.customServerAiStep();
    }

    // Sounds
    protected SoundEvent getAmbientSound() {
        return UPSounds.TELECREX_IDLE.get();
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return UPSounds.TELECREX_HURT.get();
    }

    protected SoundEvent getDeathSound() {
        return UPSounds.TELECREX_DEATH.get();
    }

    protected void playStepSound(BlockPos p_28301_, BlockState p_28302_) {
        this.playSound(SoundEvents.CHICKEN_STEP, 0.1F, 1.0F);
    }

    @Override
    public float getSoundVolume() {
        if(this.isBaby()) {
            return 0.6F;
        }
        else return 0.8F;
    }

    @Override
    protected int getKillHealAmount() {
        return 0;
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel serverLevel, @NotNull AgeableMob ageableMob) {
        return UPEntities.TELECREX.get().create(serverLevel);
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        return source.is(DamageTypes.FALL) || source.is(DamageTypes.IN_WALL);
    }

    // Animation sounds
    private void soundListener(SoundKeyframeEvent<TelecrexEntity> event) {
        TelecrexEntity telecrex = event.getAnimatable();
        if (telecrex.level().isClientSide) {
            if (event.getKeyframeData().getSound().equals("telecrex_flap")) {
                telecrex.level().playLocalSound(telecrex.getX(), telecrex.getY(), telecrex.getZ(), UPSounds.TELECREX_FLAP.get(), telecrex.getSoundSource(), 0.1F, telecrex.getVoicePitch(), false);
            }
        }
    }

    // Animation control
    @Override
    public void registerControllers(final AnimatableManager.ControllerRegistrar controllers) {
        AnimationController<TelecrexEntity> controller = new AnimationController<>(this, "controller", 5, this::predicate);
        controllers.add(controller);
        controller.setSoundKeyframeHandler(this::soundListener);

        AnimationController<TelecrexEntity> idle = new AnimationController<>(this, "idleController", 5, this::idlePredicate);
        controllers.add(idle);
    }

    protected <E extends TelecrexEntity> PlayState predicate(final AnimationState<E> event) {
        if (this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6 && this.onGround()) {
            if(this.isSprinting()) {
                event.setAndContinue(TELECREX_WALK);
                event.getController().setAnimationSpeed(1.25F);
            }
            else event.setAndContinue(TELECREX_WALK);
            event.getController().setAnimationSpeed(1.0F);
            return PlayState.CONTINUE;
        }
        if (this.isFlying() && this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6) {
            if(this.isSprinting() || this.getMoveControl().hasWanted()) {
                event.setAndContinue(TELECREX_FLY_FAST);
            }
            else event.setAndContinue(TELECREX_FLY);
            return PlayState.CONTINUE;
        }
        else if (this.isFlying() && this.isStillEnough()) {
            event.setAndContinue(TELECREX_HOVER);
            return PlayState.CONTINUE;
        }
        return event.setAndContinue(TELECREX_IDLE);
    }

    protected <E extends TelecrexEntity> PlayState idlePredicate(final AnimationState<E> event) {
        if(this.onGround() && !this.isFlying() && !getMoveControl().hasWanted()) {
            if (getBooleanState(PECK)) {
                event.getController().setAnimation(TELECREX_PECK);
                return PlayState.CONTINUE;
            }
            if (getBooleanState(PREEN1)) {
                event.getController().setAnimation(TELECREX_PREEN_1);
                return PlayState.CONTINUE;
            }
            if (getBooleanState(PREEN2)) {
                event.getController().setAnimation(TELECREX_PREEN_2);
                return PlayState.CONTINUE;
            }
            if (getBooleanState(LOOKOUT1)) {
                event.getController().setAnimation(TELECREX_LOOKOUT_1);
                return PlayState.CONTINUE;
            }
            if (getBooleanState(LOOKOUT2)) {
                event.getController().setAnimation(TELECREX_LOOKOUT_2);
                return PlayState.CONTINUE;
            }
            event.getController().forceAnimationReset();
        }
        return PlayState.STOP;
    }

    public boolean canTrample(BlockState state, BlockPos pos, float fallDistance) {
        return false;
    }

    public boolean causeFallDamage(float distance, float damageMultiplier) {
        return false;
    }

    protected void checkFallDamage(double y, boolean onGroundIn, BlockState state, BlockPos pos) {}
}