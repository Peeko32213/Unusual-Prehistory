package com.peeko32213.unusualprehistory.common.entity.custom.prehistoric;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.peeko32213.unusualprehistory.common.data.entity.synced.SerializableSynchedData;
import com.peeko32213.unusualprehistory.common.entity.animation.state.EntityAction;
import com.peeko32213.unusualprehistory.common.entity.animation.state.StateHelper;
import com.peeko32213.unusualprehistory.common.entity.animation.state.WeightedState;
import com.peeko32213.unusualprehistory.common.entity.custom.ai.goal.VelociraptorPushButtonsGoal;
import com.peeko32213.unusualprehistory.common.entity.custom.ai.goal.attack.VelociraptorMeleeAttackGoal;
import com.peeko32213.unusualprehistory.common.entity.custom.base.PrehistoricEntity;
import com.peeko32213.unusualprehistory.common.entity.custom.ai.goal.BabyPanicGoal;
import com.peeko32213.unusualprehistory.common.entity.custom.ai.goal.PounceGoal;
import com.peeko32213.unusualprehistory.common.entity.util.navigator.SmartBodyHelper;
import com.peeko32213.unusualprehistory.common.entity.util.navigator.SmoothGroundNavigation;
import com.peeko32213.unusualprehistory.core.registry.*;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.keyframe.event.SoundKeyframeEvent;
import software.bernie.geckolib.core.object.PlayState;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.List;

import static com.peeko32213.unusualprehistory.UnusualPrehistory.prefix;


public class VelociraptorEntity extends PrehistoricEntity {

    private static final EntityDataAccessor<Boolean> PRESS = SynchedEntityData.defineId(VelociraptorEntity.class, EntityDataSerializers.BOOLEAN);

    public boolean pushingState = false;

    public float flap;
    public float flapSpeed;
    public float oFlapSpeed;
    public float oFlap;
    public float flapping = 1.0F;
    private float nextFlap = 1.0F;

    // Movement animations
    private static final RawAnimation VELOCI_WALK = RawAnimation.begin().thenLoop("animation.velociraptor.walk");
    private static final RawAnimation VELOCI_RUN = RawAnimation.begin().thenLoop("animation.velociraptor.run");
    private static final RawAnimation VELOCI_SWIM = RawAnimation.begin().thenLoop("animation.velociraptor.swim");
    private static final RawAnimation VELOCI_JUMP = RawAnimation.begin().thenLoop("animation.velociraptor.jump");

    // Idle animations
    private static final RawAnimation VELOCI_IDLE = RawAnimation.begin().thenLoop("animation.velociraptor.idle");
    private static final RawAnimation VELOCI_LOOKOUT_1 = RawAnimation.begin().thenPlay("animation.velociraptor.lookout_blend1");
    private static final RawAnimation VELOCI_LOOKOUT_2 = RawAnimation.begin().thenPlay("animation.velociraptor.lookout_blend2");
    private static final RawAnimation VELOCI_CHATTER = RawAnimation.begin().thenPlay("animation.velociraptor.chatter_blend");
    private static final RawAnimation VELOCI_SCRATCH_1 = RawAnimation.begin().thenPlay("animation.velociraptor.scratch_blend1");
    private static final RawAnimation VELOCI_SCRATCH_2 = RawAnimation.begin().thenPlay("animation.velociraptor.scratch_blend2");
    private static final RawAnimation VELOCI_PREEN_1 = RawAnimation.begin().thenPlay("animation.velociraptor.preen1");
    private static final RawAnimation VELOCI_PREEN_2 = RawAnimation.begin().thenPlay("animation.velociraptor.preen2");
    private static final RawAnimation VELOCI_SIT = RawAnimation.begin().thenLoop("animation.velociraptor.sit");
    private static final RawAnimation VELOCI_SLEEP = RawAnimation.begin().thenLoop("animation.velociraptor.sleep");

    // Attack animations
    private static final RawAnimation VELOCI_BITE = RawAnimation.begin().thenPlay("animation.velociraptor.bite_blend");
    private static final RawAnimation VELOCI_KICK = RawAnimation.begin().thenPlay("animation.velociraptor.kick");

    // Idle accessors
    private static final EntityDataAccessor<Boolean> IDLE_1_AC = SynchedEntityData.defineId(VelociraptorEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> IDLE_2_AC = SynchedEntityData.defineId(VelociraptorEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> IDLE_3_AC = SynchedEntityData.defineId(VelociraptorEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> IDLE_4_AC = SynchedEntityData.defineId(VelociraptorEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> IDLE_5_AC = SynchedEntityData.defineId(VelociraptorEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> IDLE_6_AC = SynchedEntityData.defineId(VelociraptorEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> IDLE_7_AC = SynchedEntityData.defineId(VelociraptorEntity.class, EntityDataSerializers.BOOLEAN);
    public static final SerializableSynchedData<Boolean> VELOCIRAPTOR_BITE = new SerializableSynchedData<>(prefix("velociraptor_bite"), VelociraptorEntity.class, EntityDataSerializers.BOOLEAN, false, Object::toString, Boolean::parseBoolean);

    // Idle actions
    private static final EntityAction VELOCI_IDLE_1_ACTION = new EntityAction(0, (e) -> {}, 1);

    private static final StateHelper VELOCI_IDLE_1_STATE =
            StateHelper.Builder.state(IDLE_1_AC, "velociraptor_lookout_1")
                    .playTime(60)
                    .stopTime(150)
                    .affectsAI(true)
                    .affectedFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK))
                    .entityAction(VELOCI_IDLE_1_ACTION)
                    .build();

    private static final EntityAction VELOCI_IDLE_2_ACTION = new EntityAction(0, (e) -> {}, 1);

    private static final StateHelper VELOCI_IDLE_2_STATE =
            StateHelper.Builder.state(IDLE_2_AC, "velociraptor_lookout_2")
                    .playTime(60)
                    .stopTime(150)
                    .affectsAI(true)
                    .affectedFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK))
                    .entityAction(VELOCI_IDLE_2_ACTION)
                    .build();

    private static final EntityAction VELOCI_IDLE_3_ACTION = new EntityAction(0, (e) -> {}, 1);

    private static final StateHelper VELOCI_IDLE_3_STATE =
            StateHelper.Builder.state(IDLE_3_AC, "velociraptor_chatter")
                    .playTime(60)
                    .stopTime(150)
                    .entityAction(VELOCI_IDLE_3_ACTION)
                    .build();

    private static final EntityAction VELOCI_IDLE_4_ACTION = new EntityAction(0, (e) -> {}, 1);

    private static final StateHelper VELOCI_IDLE_4_STATE =
            StateHelper.Builder.state(IDLE_4_AC, "velociraptor_scratch_1")
                    .playTime(60)
                    .stopTime(150)
                    .entityAction(VELOCI_IDLE_4_ACTION)
                    .build();

    private static final EntityAction VELOCI_IDLE_5_ACTION = new EntityAction(0, (e) -> {}, 1);

    private static final StateHelper VELOCI_IDLE_5_STATE =
            StateHelper.Builder.state(IDLE_5_AC, "velociraptor_scratch_2")
                    .playTime(60)
                    .stopTime(150)
                    .entityAction(VELOCI_IDLE_5_ACTION)
                    .build();

    private static final EntityAction VELOCI_IDLE_6_ACTION = new EntityAction(0, (e) -> {}, 1);

    private static final StateHelper VELOCI_IDLE_6_STATE =
            StateHelper.Builder.state(IDLE_6_AC, "velociraptor_preen_1")
                    .playTime(60)
                    .stopTime(150)
                    .affectsAI(true)
                    .affectedFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK))
                    .entityAction(VELOCI_IDLE_6_ACTION)
                    .build();

    private static final EntityAction VELOCI_IDLE_7_ACTION = new EntityAction(0, (e) -> {}, 1);

    private static final StateHelper VELOCI_IDLE_7_STATE =
            StateHelper.Builder.state(IDLE_7_AC, "velociraptor_preen_2")
                    .playTime(60)
                    .stopTime(150)
                    .affectsAI(true)
                    .affectedFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK))
                    .entityAction(VELOCI_IDLE_7_ACTION)
                    .build();

    @Override
    public ImmutableMap<String, StateHelper> getStates() {
        return ImmutableMap.of(
                VELOCI_IDLE_1_STATE.getName(), VELOCI_IDLE_1_STATE,
                VELOCI_IDLE_2_STATE.getName(), VELOCI_IDLE_2_STATE,
                VELOCI_IDLE_3_STATE.getName(), VELOCI_IDLE_3_STATE,
                VELOCI_IDLE_4_STATE.getName(), VELOCI_IDLE_4_STATE,
                VELOCI_IDLE_5_STATE.getName(), VELOCI_IDLE_5_STATE,
                VELOCI_IDLE_6_STATE.getName(), VELOCI_IDLE_6_STATE,
                VELOCI_IDLE_7_STATE.getName(), VELOCI_IDLE_7_STATE
        );
    }

    @Override
    public List<WeightedState<StateHelper>> getWeightedStatesToPerform() {
        return ImmutableList.of(
                WeightedState.of(VELOCI_IDLE_1_STATE, 10),
                WeightedState.of(VELOCI_IDLE_2_STATE, 10),
                WeightedState.of(VELOCI_IDLE_3_STATE, 12),
                WeightedState.of(VELOCI_IDLE_4_STATE, 11),
                WeightedState.of(VELOCI_IDLE_5_STATE, 11),
                WeightedState.of(VELOCI_IDLE_6_STATE, 9),
                WeightedState.of(VELOCI_IDLE_7_STATE, 9)
        );
    }

    private void soundListener(SoundKeyframeEvent<VelociraptorEntity> event) {
        VelociraptorEntity velociraptor = event.getAnimatable();
        if (event.getKeyframeData().getSound().equals("velociraptor_attack")) {
            velociraptor.level().playLocalSound(velociraptor.getX(), velociraptor.getY(), velociraptor.getZ(), UPSounds.VELOCIRAPTOR_ATTACK.get(), velociraptor.getSoundSource(), 0.5F, velociraptor.getVoicePitch(), false);
        }
    }

    @Override
    public void registerControllers(final AnimatableManager.ControllerRegistrar controllers) {
        AnimationController<VelociraptorEntity> controller = new AnimationController<>(this, "controller", 5, this::predicate);
        controllers.add(controller);

        AnimationController<VelociraptorEntity> blend = new AnimationController<>(this, "blend", 5, this::predicate)
                .triggerableAnim("lookout_1", VELOCI_LOOKOUT_1)
                .triggerableAnim("lookout_2", VELOCI_LOOKOUT_2)
                .triggerableAnim("scratch_1", VELOCI_SCRATCH_1)
                .triggerableAnim("scratch_2", VELOCI_SCRATCH_2)
                .triggerableAnim("chatter", VELOCI_CHATTER);
        blend.setSoundKeyframeHandler(this::soundListener);
        controllers.add(blend);

        AnimationController<VelociraptorEntity> flap = new AnimationController<>(this, "flapController", 5, this::flapPredicate);
        controllers.add(flap);

        AnimationController<VelociraptorEntity> attack = new AnimationController<>(this, "attackController", 5, this::attackPredicate);
        attack.setSoundKeyframeHandler(this::soundListener);
        controllers.add(attack);
    }

    protected <E extends VelociraptorEntity> PlayState predicate(final AnimationState<E> event) {

        if (this.isFromBook()) {
            return event.setAndContinue(VELOCI_IDLE);
        }

//        if(isSDataTrue(VELOCIRAPTOR_BITE)) {
//            triggerAnim("blend", "bite");
//            return PlayState.CONTINUE;
//        }

        if (this.isInWater()) {
            event.setAndContinue(VELOCI_SWIM);
            event.getController().setAnimationSpeed(1.0D);
            return PlayState.CONTINUE;
        }
        else if(this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6 && !this.isSwimming() && !this.isInWater()){
            if(this.isSprinting()) {
                event.setAndContinue(VELOCI_RUN);
                event.getController().setAnimationSpeed(1.0D);
            } else {
                event.setAndContinue(VELOCI_WALK);
                event.getController().setAnimationSpeed(1.0D);
            }
            return PlayState.CONTINUE;
        }

        if(this.isFlapping()){
            event.setAndContinue(VELOCI_JUMP);
            event.getController().setAnimationSpeed(1.0D);
        }

        if (!this.isInWater()) {
            if (getBooleanState(IDLE_1_AC)) {
                if (this.isStillEnough()) {
                    triggerAnim("blend", "lookout_1");
                    return event.setAndContinue(VELOCI_IDLE);
                } else {
                    triggerAnim("blend", "lookout_1");
                    return PlayState.CONTINUE;
                }
            }
            if (getBooleanState(IDLE_2_AC)) {
                if (this.isStillEnough()) {
                    triggerAnim("blend", "lookout_2");
                    return event.setAndContinue(VELOCI_IDLE);
                } else {
                    triggerAnim("blend", "lookout_2");
                    return PlayState.CONTINUE;
                }
            }
            if (getBooleanState(IDLE_3_AC)) {
                if (this.isStillEnough()) {
                    triggerAnim("blend", "scratch_1");
                    return event.setAndContinue(VELOCI_IDLE);
                } else {
                    triggerAnim("blend", "scratch_1");
                    return PlayState.CONTINUE;
                }
            }
            if (getBooleanState(IDLE_4_AC)) {
                if (this.isStillEnough()) {
                    triggerAnim("blend", "scratch_2");
                    return event.setAndContinue(VELOCI_IDLE);
                } else {
                    triggerAnim("blend", "scratch_2");
                    return PlayState.CONTINUE;
                }
            }
            if (getBooleanState(IDLE_5_AC)) {
                if (this.isStillEnough()) {
                    triggerAnim("blend", "chatter");
                    return event.setAndContinue(VELOCI_IDLE);
                } else {
                    triggerAnim("blend", "chatter");
                    return PlayState.CONTINUE;
                }
            }
            if (getBooleanState(IDLE_6_AC)) {
                return event.setAndContinue(VELOCI_PREEN_1);
            }
            if (getBooleanState(IDLE_7_AC)) {
                return event.setAndContinue(VELOCI_PREEN_2);
            }
            return event.setAndContinue(VELOCI_IDLE);
        }
        return PlayState.CONTINUE;
    }

    // Falling animation
    protected <E extends VelociraptorEntity> PlayState flapPredicate(final AnimationState<E> event) {
        if (!this.onGround() && !this.isInWater()) {
            event.getController().setAnimation(VELOCI_JUMP);
            event.getController().setAnimationSpeed(1.0F);
            return PlayState.CONTINUE;
        }
        event.getController().forceAnimationReset();

        return PlayState.STOP;
    }

    // Attack animations
    protected <E extends VelociraptorEntity> PlayState attackPredicate(final AnimationState<E> event) {
        int animState = this.getAnimationState();

        if (animState == 21) {
            event.setAndContinue(VELOCI_BITE);
            return PlayState.CONTINUE;
        }
        else if (animState == 22) {
            event.setAndContinue(VELOCI_KICK);
            return PlayState.CONTINUE;
        }
        else if (animState == 0) {
            event.getController().forceAnimationReset();
            return PlayState.STOP;
        }
        else return PlayState.CONTINUE;
    }

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

    public VelociraptorEntity(EntityType<? extends PrehistoricEntity> entityType, Level level) {
        super(entityType, level);
        this.setMaxUpStep(1.25F);
        ((GroundPathNavigation) this.getNavigation()).setCanOpenDoors(true);
        this.refreshDimensions();
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
            .add(Attributes.MAX_HEALTH, 14.0D)
            .add(Attributes.MOVEMENT_SPEED, 0.22D)
            .add(Attributes.ATTACK_DAMAGE, 5.0D);
    }

    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PounceGoal(this, 0));
        this.goalSelector.addGoal(1, new VelociraptorMeleeAttackGoal(this, 1.5F, true));
        this.goalSelector.addGoal(4, new VelociraptorPushButtonsGoal(this, 0.5F, 5, 2));
        this.goalSelector.addGoal(3, new BabyPanicGoal(this, 2.0D));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 1.0F, 30));
        this.goalSelector.addGoal(5, new FollowParentGoal(this, 1.1D));
        this.targetSelector.addGoal(8, (new HurtByTargetGoal(this)));
        this.goalSelector.addGoal(3, new OpenDoorGoal(this, true));
//        SerializableRandomMeleeAttackGoal<VelociraptorEntity> goals = new SerializableRandomMeleeAttackGoal<>(this,
//                WeightedRandomList.create(
//                new WeightedSerializableMeleeAttackHelper(
//                    10,
//                    SerializableRandomMeleeAttackHelper.Builder
//                    .state(VELOCIRAPTOR_BITE, "velociraptor_bite")
//                    .playTime(16)
//                    .meleeEntityAction(
//                    new MeleeEntityAction(9,1,
//                    new LargeHitBoxAttackWithTargetCheck(1F, 0.1F, 5.5F, -Math.PI/4, Math.PI/4, false, true))).build())),
//                    1.75D,false,2.0F);
//
//        this.goalSelector.addGoal(1,goals);
    }

    @Override
    public void aiStep() {
        super.aiStep();

        // Flap
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
        if (this.getMoveControl().hasWanted() && !this.isBaby()) {
            this.setSprinting(this.getMoveControl().getSpeedModifier() >= 1.25D);
        } else {
            this.setSprinting(false);
        }
        super.customServerAiStep();
    }

    // Sounds
    protected SoundEvent getAmbientSound() {
        return UPSounds.VELOCIRAPTOR_IDLE.get();
    }

    protected SoundEvent getHurtSound(@NotNull DamageSource damageSourceIn) {
        return UPSounds.VELOCIRAPTOR_HURT.get();
    }

    protected SoundEvent getDeathSound() {
        return UPSounds.VELOCIRAPTOR_DEATH.get();
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

    // Heal on kill
    @Override
    protected int getKillHealAmount() {
        return 4;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("Press", this.hasPressed());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setPress(compound.getBoolean("Press"));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(IDLE_1_AC, false);
        this.entityData.define(IDLE_2_AC, false);
        this.entityData.define(IDLE_3_AC, false);
        this.entityData.define(IDLE_4_AC, false);
        this.entityData.define(IDLE_5_AC, false);
        this.entityData.define(IDLE_6_AC, false);
        this.entityData.define(IDLE_7_AC, false);
        this.entityData.define(PRESS, false);
        VELOCIRAPTOR_BITE.defineData(this);
    }

    public void setPress(boolean eepy) {
        this.entityData.set(PRESS, eepy);
    }

    public boolean hasPressed() {
        return this.entityData.get(PRESS);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel serverLevel, @NotNull AgeableMob ageableMob) {
        VelociraptorEntity velociraptor = UPEntities.VELOCIRAPTOR.get().create(serverLevel);
        velociraptor.setVariant(this.getVariant());
        return velociraptor;
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

    // Variants
    public void determineVariant(int variantChange){
        if (variantChange <= 30) {
            this.setVariant(1);
        }
        else if (variantChange <= 60) {
            this.setVariant(2);
        }
        else {
            this.setVariant(0);
        }
    }

}
