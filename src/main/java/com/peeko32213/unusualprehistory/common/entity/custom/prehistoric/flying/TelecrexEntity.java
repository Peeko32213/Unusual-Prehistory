package com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.flying;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.peeko32213.unusualprehistory.common.entity.animation.state.StateHelper;
import com.peeko32213.unusualprehistory.common.entity.animation.state.WeightedState;
import com.peeko32213.unusualprehistory.common.entity.custom.base.PrehistoricEntity;
import com.peeko32213.unusualprehistory.common.entity.util.navigator.FlyingMoveController;
import com.peeko32213.unusualprehistory.common.entity.util.navigator.SmartBodyHelper;
import com.peeko32213.unusualprehistory.common.entity.util.navigator.SmoothGroundNavigation;
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
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

import java.util.EnumSet;
import java.util.List;

public class TelecrexEntity extends PrehistoricEntity {

    private static final EntityDataAccessor<Boolean> FLYING = SynchedEntityData.defineId(TelecrexEntity.class, EntityDataSerializers.BOOLEAN);

    public float currentRoll = 0.0F;
    public float prevTilt;
    public float tilt;

    private Ingredient temptationItems;

    public final float[] ringBuffer = new float[64];
    public float prevFlyProgress;
    public float flyProgress;
    public int ringBufferIndex = -1;
    private boolean isLandNavigator;
    private int timeFlying;

    // Movement animations
    private static final RawAnimation TELECREX_WALK = RawAnimation.begin().thenLoop("animation.telecrex.walk");
    private static final RawAnimation TELECREX_FLY = RawAnimation.begin().thenLoop("animation.telecrex.fly");
    private static final RawAnimation TELECREX_FLY_FAST = RawAnimation.begin().thenLoop("animation.telecrex.flyfast");

    // Idle animations
    private static final RawAnimation TELECREX_IDLE = RawAnimation.begin().thenLoop("animation.telecrex.idle");
    private static final RawAnimation TELECREX_HOVER = RawAnimation.begin().thenLoop("animation.telecrex.hover");
    private static final RawAnimation TELECREX_PECK = RawAnimation.begin().thenLoop("animation.telecrex.peck");
    private static final RawAnimation TELECREX_PREEN_1 = RawAnimation.begin().thenLoop("animation.telecrex.preen1");
    private static final RawAnimation TELECREX_PREEN_2 = RawAnimation.begin().thenLoop("animation.telecrex.preen2");
    private static final RawAnimation TELECREX_LOOKOUT_1 = RawAnimation.begin().thenLoop("animation.telecrex.lookout_blend1");
    private static final RawAnimation TELECREX_LOOKOUT_2 = RawAnimation.begin().thenLoop("animation.telecrex.lookout_blend2");

    // Misc animations
    private static final RawAnimation TELECREX_TAKEOFF = RawAnimation.begin().thenLoop("animation.telecrex.idle");

    // Body control / navigation
    @Override
    protected @NotNull BodyRotationControl createBodyControl() {
        SmartBodyHelper helper = new SmartBodyHelper(this);
        helper.bodyLagMoving = 0.5F;
        helper.bodyLagStill = 0.3F;
        return helper;
    }

    @Override
    protected @NotNull PathNavigation createNavigation(Level levelIn) {
        return new SmoothGroundNavigation(this, levelIn);
    }

    public TelecrexEntity(EntityType<? extends PrehistoricEntity> entityType, Level level) {
        super(entityType, level);
        switchNavigator(true);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
            .add(Attributes.MAX_HEALTH, 10.0D)
            .add(Attributes.MOVEMENT_SPEED, 0.16D);
    }

    private void switchNavigator(boolean onLand) {
        if (onLand) {
            this.moveControl = new MoveControl(this);
            this.navigation = new SmoothGroundNavigation(this, level());
            this.isLandNavigator = true;
        }
        else {
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

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new TelecrexEntity.AIFlyIdle());
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.25D));
        this.goalSelector.addGoal(4, new TemptGoal(this, 1.2D, getTemptationItems(), false));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.targetSelector.addGoal(8, (new HurtByTargetGoal(this)));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
    }

    private Ingredient getTemptationItems() {
        if (temptationItems == null)
            temptationItems = Ingredient.merge(Lists.newArrayList(
                    Ingredient.of(ItemTags.LEAVES)
            ));
        return temptationItems;
    }

    // Save data
    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
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
        if (flying && isBaby()) {
            return;
        }
        this.entityData.set(FLYING, flying);
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
                        this.setDeltaMovement(this.getDeltaMovement().multiply(0.5F, 0.25F, 0.5F));
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

        float prevRoll =  this.currentRoll;
        float targetRoll = Math.max(-0.45F, Math.min(0.45F, (this.getYRot() - this.yRotO) * 0.1F));
        targetRoll = -targetRoll;
        this.currentRoll = prevRoll + (targetRoll - prevRoll) * 0.05F;
    }

    // Sounds
    protected SoundEvent getAmbientSound() {
        return UPSounds.TALPANAS_IDLE.get();
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return UPSounds.TALPANAS_HURT.get();
    }

    protected SoundEvent getDeathSound() {
        return UPSounds.TALPANAS_DEATH.get();
    }

    protected void playStepSound(BlockPos p_28301_, BlockState p_28302_) {
        this.playSound(SoundEvents.CHICKEN_STEP, 0.1F, 1.0F);
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

    @Override
    public ImmutableMap<String, StateHelper> getStates() {
        return null;
    }

    @Override
    public List<WeightedState<StateHelper>> getWeightedStatesToPerform() {
        return List.of();
    }

    // Animation control
    @Override
    public void registerControllers(final AnimatableManager.ControllerRegistrar controllers) {
        AnimationController<TelecrexEntity> controller = new AnimationController<>(this, "controller", 5, this::predicate);
        controllers.add(controller);

        AnimationController<TelecrexEntity> idle = new AnimationController<>(this, "idleController", 5, this::idlePredicate);
        controllers.add(idle);
    }

    protected <E extends TelecrexEntity> PlayState predicate(final AnimationState<E> event) {
        if (this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6 && this.onGround()) {
            event.setAndContinue(TELECREX_WALK);
            event.getController().setAnimationSpeed(1.0D);
            return PlayState.CONTINUE;
        }
        if (this.isFlying()) {
            return event.setAndContinue(TELECREX_FLY);
        }
        return event.setAndContinue(TELECREX_IDLE);
    }

    protected <E extends TelecrexEntity> PlayState idlePredicate(final AnimationState<E> event) {
        return PlayState.CONTINUE;
    }

    public boolean causeFallDamage(float distance, float damageMultiplier) {
        return false;
    }

    protected void checkFallDamage(double y, boolean onGroundIn, BlockState state, BlockPos pos) {}

    public Vec3 getBlockGrounding(Vec3 fleePos) {
        final float radius = 3.15F * -3 - this.getRandom().nextInt(24);
        float neg = this.getRandom().nextBoolean() ? 1 : -1;
        float renderYawOffset = this.yBodyRot;
        float angle = (0.01745329251F * renderYawOffset) + 3.15F + (this.getRandom().nextFloat() * neg);
        final double extraX = radius * Mth.sin(Mth.PI + angle);
        final double extraZ = radius * Mth.cos(angle);
        final BlockPos radialPos = new BlockPos((int) (fleePos.x() + extraX), (int) getY(), (int) (fleePos.z() + extraZ));
        BlockPos ground = this.getTelecrexGround(radialPos);
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
        BlockPos ground = getTelecrexGround(radialPos);
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

    public BlockPos getTelecrexGround(BlockPos in) {
        BlockPos position = new BlockPos(in.getX(), (int) this.getY(), in.getZ());
        while (position.getY() > -64 && !level().getBlockState(position).isSolid() && level().getFluidState(position).isEmpty()) {
            position = position.below();
        }
        return position;
    }

    private class AIFlyIdle extends Goal {
        protected double x;
        protected double y;
        protected double z;

        public AIFlyIdle() {
            super();
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            if (TelecrexEntity.this.isVehicle() || (TelecrexEntity.this.getTarget() != null && TelecrexEntity.this.getTarget().isAlive()) || TelecrexEntity.this.isPassenger()) {
                return false;
            } else {
                if (TelecrexEntity.this.getRandom().nextInt(45) != 0 && !TelecrexEntity.this.isFlying()) {
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
            TelecrexEntity.this.getMoveControl().setWantedPosition(this.x, this.y, this.z, 3F);
            if (isFlying() && TelecrexEntity.this.onGround() && TelecrexEntity.this.timeFlying > 10) {
                TelecrexEntity.this.setFlying(false);
            }
        }

        @Nullable
        protected Vec3 getPosition() {
            Vec3 vector3d = TelecrexEntity.this.position();
            if (TelecrexEntity.this.timeFlying < 200 || TelecrexEntity.this.isOverWaterOrVoid()) {
                return TelecrexEntity.this.getBlockInViewAway(vector3d, 0);
            } else {
                return TelecrexEntity.this.getBlockGrounding(vector3d);
            }
        }

        public boolean canContinueToUse() {
            return TelecrexEntity.this.isFlying() && TelecrexEntity.this.distanceToSqr(x, y, z) > 5F;
        }

        public void start() {
            TelecrexEntity.this.setFlying(true);
            TelecrexEntity.this.getMoveControl().setWantedPosition(this.x, this.y, this.z, 1F);
        }

        public void stop() {
            TelecrexEntity.this.getNavigation().stop();
            x = 0;
            y = 0;
            z = 0;
            super.stop();
        }
    }
}