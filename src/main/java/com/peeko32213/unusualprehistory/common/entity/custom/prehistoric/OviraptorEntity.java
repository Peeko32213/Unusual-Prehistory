package com.peeko32213.unusualprehistory.common.entity.custom.prehistoric;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.peeko32213.unusualprehistory.common.entity.animation.state.EntityAction;
import com.peeko32213.unusualprehistory.common.entity.animation.state.StateHelper;
import com.peeko32213.unusualprehistory.common.entity.animation.state.WeightedState;
import com.peeko32213.unusualprehistory.common.entity.custom.base.PrehistoricEntity;
import com.peeko32213.unusualprehistory.common.entity.util.navigator.SmartBodyHelper;
import com.peeko32213.unusualprehistory.common.entity.util.navigator.SmoothGroundNavigation;
import com.peeko32213.unusualprehistory.core.registry.UPEntities;
import com.peeko32213.unusualprehistory.core.registry.UPSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
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
import java.util.Locale;
import java.util.Objects;

public class OviraptorEntity extends PrehistoricEntity {

    private static final EntityDataAccessor<Integer> SCALE = SynchedEntityData.defineId(OviraptorEntity.class, EntityDataSerializers.INT);

    public float flap;
    public float flapSpeed;
    public float oFlapSpeed;
    public float oFlap;
    public float flapping = 1.0F;
    private float nextFlap = 1.0F;

    // Movement animations
    private static final RawAnimation OVI_WALK = RawAnimation.begin().thenLoop("animation.oviraptor.walk");
    private static final RawAnimation OVI_RUN = RawAnimation.begin().thenLoop("animation.oviraptor.run");
    private static final RawAnimation OVI_SWIM = RawAnimation.begin().thenLoop("animation.oviraptor.swim");
    private static final RawAnimation OVI_FALL = RawAnimation.begin().thenLoop("animation.oviraptor.fall");

    // Idle animations
    private static final RawAnimation OVI_IDLE = RawAnimation.begin().thenLoop("animation.oviraptor.idle");
    private static final RawAnimation OVI_LOOKOUT_1 = RawAnimation.begin().thenPlay("animation.oviraptor.idle");
    private static final RawAnimation OVI_LOOKOUT_2 = RawAnimation.begin().thenPlay("animation.oviraptor.idle");
    private static final RawAnimation OVI_COCKADOO = RawAnimation.begin().thenPlay("animation.oviraptor.cockadoo");
    private static final RawAnimation OVI_PECK = RawAnimation.begin().thenPlay("animation.oviraptor.peck");
    private static final RawAnimation OVI_SIT_1 = RawAnimation.begin().thenLoop("animation.oviraptor.sit1");
    private static final RawAnimation OVI_SIT_2 = RawAnimation.begin().thenLoop("animation.oviraptor.sit2");
    private static final RawAnimation OVI_SLEEP = RawAnimation.begin().thenLoop("animation.oviraptor.sleep");

    // Idle accessors
    private static final EntityDataAccessor<Boolean> IDLE_1_AC = SynchedEntityData.defineId(OviraptorEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> IDLE_2_AC = SynchedEntityData.defineId(OviraptorEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> IDLE_3_AC = SynchedEntityData.defineId(OviraptorEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> IDLE_4_AC = SynchedEntityData.defineId(OviraptorEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> IDLE_5_AC = SynchedEntityData.defineId(OviraptorEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> IDLE_6_AC = SynchedEntityData.defineId(OviraptorEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> IDLE_7_AC = SynchedEntityData.defineId(OviraptorEntity.class, EntityDataSerializers.BOOLEAN);

    // Idle actions
    private static final EntityAction OVI_IDLE_1_ACTION = new EntityAction(0, (e) -> {}, 1);

    private static final StateHelper OVI_IDLE_1_STATE =
            StateHelper.Builder.state(IDLE_1_AC, "oviraptor_lookout_1")
                    .playTime(60)
                    .stopTime(150)
                    .entityAction(OVI_IDLE_1_ACTION)
                    .build();

    private static final EntityAction OVI_IDLE_2_ACTION = new EntityAction(0, (e) -> {}, 1);

    private static final StateHelper OVI_IDLE_2_STATE =
            StateHelper.Builder.state(IDLE_2_AC, "oviraptor_lookout_2")
                    .playTime(60)
                    .stopTime(150)
                    .entityAction(OVI_IDLE_2_ACTION)
                    .build();

    private static final EntityAction OVI_IDLE_3_ACTION = new EntityAction(0, (e) -> {}, 1);

    private static final StateHelper OVI_IDLE_3_STATE =
            StateHelper.Builder.state(IDLE_3_AC, "velociraptor_cockadoo")
                    .playTime(60)
                    .stopTime(150)
                    .affectsAI(true)
                    .affectedFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK))
                    .entityAction(OVI_IDLE_3_ACTION)
                    .build();

    private static final EntityAction OVI_IDLE_4_ACTION = new EntityAction(0, (e) -> {}, 1);

    private static final StateHelper OVI_IDLE_4_STATE =
            StateHelper.Builder.state(IDLE_4_AC, "oviraptor_peck")
                    .playTime(60)
                    .stopTime(150)
                    .affectsAI(true)
                    .affectedFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK))
                    .entityAction(OVI_IDLE_4_ACTION)
                    .build();

    @Override
    public ImmutableMap<String, StateHelper> getStates() {
        return ImmutableMap.of(
                OVI_IDLE_1_STATE.getName(), OVI_IDLE_1_STATE,
                OVI_IDLE_2_STATE.getName(), OVI_IDLE_2_STATE,
                OVI_IDLE_3_STATE.getName(), OVI_IDLE_3_STATE,
                OVI_IDLE_4_STATE.getName(), OVI_IDLE_4_STATE
        );
    }

    @Override
    public List<WeightedState<StateHelper>> getWeightedStatesToPerform() {
        return ImmutableList.of(
                WeightedState.of(OVI_IDLE_1_STATE, 10),
                WeightedState.of(OVI_IDLE_2_STATE, 10),
                WeightedState.of(OVI_IDLE_3_STATE, 12),
                WeightedState.of(OVI_IDLE_4_STATE, 11)
        );
    }

    @Override
    public boolean getAction() {
        return false;
    }

    @Override
    public void setAction(boolean action) {}

    @Override
    protected @NotNull BodyRotationControl createBodyControl() {
        SmartBodyHelper helper = new SmartBodyHelper(this);
        helper.bodyLagMoving = 0.75F;
        helper.bodyLagStill = 0.3F;
        return helper;
    }

    @Override
    protected @NotNull PathNavigation createNavigation(Level levelIn) {
        return new SmoothGroundNavigation(this, levelIn);
    }

    public OviraptorEntity(EntityType<? extends PrehistoricEntity> entityType, Level level) {
        super(entityType, level);
        this.setMaxUpStep(1.25F);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
            .add(Attributes.MAX_HEALTH, 6.0D)
            .add(Attributes.MOVEMENT_SPEED, 0.18D);
    }

    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(7, new PanicGoal(this, 2.0D));
        this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 1.0F, 30));
        this.goalSelector.addGoal(5, new FollowParentGoal(this, 1.1D));
    }

    @Override
    public void tick() {
        super.tick();

        if (!this.hasCustomName()) {
            this.setScale(0);
        } else {
            if(!Objects.requireNonNull(this.getCustomName()).getString().equalsIgnoreCase("gigantoraptor")){
                this.setScale(0);
            } else {
                if("gigantoraptor".equals(this.getName().getString().toLowerCase(Locale.ROOT)) && !this.isBaby()){
                    this.setScale(1);
                }
            }
        }
    }

    @Override
    public void aiStep() {
        super.aiStep();
        this.oFlap = this.flap;
        this.oFlapSpeed = this.flapSpeed;
        this.flapSpeed += (this.onGround() ? -1.0F : 4.0F) * 0.3F;
        this.flapSpeed = Mth.clamp(this.flapSpeed, 0.0F, 1.0F);
        if (!this.onGround() && this.flapping < 1.0F) {
            this.flapping = 1.0F;
        }

        this.flapping *= 0.9F;
        Vec3 vec3 = this.getDeltaMovement();
        if (!this.onGround() && vec3.y < 0.0) {
            this.setDeltaMovement(vec3.multiply(1.0, 0.75, 1.0));
        }

        this.flap += this.flapping * 2.0F;
    }

    @Override
    public void customServerAiStep() {
        if (this.getMoveControl().hasWanted()) {
            this.setSprinting(this.getMoveControl().getSpeedModifier() >= 1.25D);
        } else {
            this.setSprinting(false);
        }
        super.customServerAiStep();
    }

    @Override
    public boolean isAlliedTo(Entity pEntity) {
        return pEntity.is(this);
    }

    protected SoundEvent getAmbientSound() {
        return UPSounds.OVIRAPTOR_IDLE.get();
    }

    protected SoundEvent getHurtSound(@NotNull DamageSource damageSourceIn) {
        return UPSounds.OVIRAPTOR_HURT.get();
    }

    protected SoundEvent getDeathSound() {
        return UPSounds.OVIRAPTOR_DEATH.get();
    }

    protected void playStepSound(@NotNull BlockPos p_28301_, @NotNull BlockState p_28302_) {
        this.playSound(SoundEvents.CHICKEN_STEP, 0.15F, 1.0F);
    }

    @Override
    public float getSoundVolume() {
        if(this.isBaby()){
            return 0.65F;
        }
        else{
            return 0.8F;
        }
    }

    @Override
    protected int getKillHealAmount() {
        return 2;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("scale", this.getModelScale());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setScale(Math.min(compound.getInt("scale"), 0));
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(IDLE_1_AC, false);
        this.entityData.define(IDLE_2_AC, false);
        this.entityData.define(IDLE_3_AC, false);
        this.entityData.define(IDLE_4_AC, false);
        this.entityData.define(SCALE, 0);
        super.defineSynchedData();
    }

    public @NotNull EntityDimensions getDimensions(@NotNull Pose pPose) {
        return super.getDimensions(pPose).scale(getScale(this.getModelScale()));
    }

    public void onSyncedDataUpdated(@NotNull EntityDataAccessor<?> pKey) {
        if (SCALE.equals(pKey)) {
            this.refreshDimensions();
        }
        super.onSyncedDataUpdated(pKey);
    }

    private static float getScale(int scale) {
        if (scale == 1) {
            return 1.8F;
        }
        return 1.0F;
    }

    public int getModelScale() {
        return this.entityData.get(SCALE);
    }

    public void setScale(int scale) {
        this.entityData.set(SCALE, scale);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel serverLevel, @NotNull AgeableMob ageableMob) {
        return UPEntities.OVIRAPTOR.get().create(serverLevel);
    }

    protected boolean isFlapping() {
        return this.flyDist > this.nextFlap;
    }

    protected void onFlap() {
        this.nextFlap = this.flyDist + this.flapSpeed / 2.0F;
    }

    public boolean causeFallDamage(float fallDistance, float multiplier, DamageSource source) {
        return false;
    }

    @Override
    public void registerControllers(final AnimatableManager.ControllerRegistrar controllers) {
        AnimationController<OviraptorEntity> controller = new AnimationController<>(this, "controller", 5, this::predicate);
        controllers.add(controller);

        AnimationController<OviraptorEntity> blend = new AnimationController<>(this, "blend", 5, this::predicate)
                .triggerableAnim("lookout_1", OVI_LOOKOUT_1)
                .triggerableAnim("lookout_2", OVI_LOOKOUT_2)
                ;
        controllers.add(blend);

        AnimationController<OviraptorEntity> flap = new AnimationController<>(this, "flapController", 5, this::flapPredicate);
        controllers.add(flap);
    }

    protected <E extends OviraptorEntity> PlayState predicate(final software.bernie.geckolib.core.animation.AnimationState<E> event) {
        if (this.isFromBook()) {
            return event.setAndContinue(OVI_IDLE);
        }
        if (this.isInWater()) {
            event.setAndContinue(OVI_SWIM);
            event.getController().setAnimationSpeed(1.0F);
            return PlayState.CONTINUE;
        }
        if (this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6 && !this.isInWater()) {
            if(this.isSprinting()) {
                event.setAndContinue(OVI_RUN);
                event.getController().setAnimationSpeed(1.0D);
            } else {
                event.setAndContinue(OVI_WALK);
                event.getController().setAnimationSpeed(1.0D);
            }
            return PlayState.CONTINUE;
        }
        if (!this.isInWater()) {
            if (getBooleanState(IDLE_1_AC)) {
                if (this.isStillEnough()) {
                    triggerAnim("blend", "lookout_1");
                    return event.setAndContinue(OVI_IDLE);
                } else {
                    triggerAnim("blend", "lookout_1");
                    return PlayState.CONTINUE;
                }
            }
            if (getBooleanState(IDLE_2_AC)) {
                if (this.isStillEnough()) {
                    triggerAnim("blend", "lookout_2");
                    return event.setAndContinue(OVI_IDLE);
                } else {
                    triggerAnim("blend", "lookout_2");
                    return PlayState.CONTINUE;
                }
            }
            if (getBooleanState(IDLE_3_AC)) {
                return event.setAndContinue(OVI_COCKADOO);
            }
            if (getBooleanState(IDLE_4_AC)) {
                return event.setAndContinue(OVI_PECK);
            }
            return event.setAndContinue(OVI_IDLE);
        }
        return PlayState.CONTINUE;
    }

    protected <E extends OviraptorEntity> PlayState flapPredicate(final AnimationState<E> event) {
        if (!this.onGround() && !this.isInWater()) {
            event.getController().setAnimation(OVI_FALL);
            event.getController().setAnimationSpeed(1.0D);
            return PlayState.CONTINUE;
        }
        event.getController().forceAnimationReset();
        return PlayState.STOP;
    }
}
