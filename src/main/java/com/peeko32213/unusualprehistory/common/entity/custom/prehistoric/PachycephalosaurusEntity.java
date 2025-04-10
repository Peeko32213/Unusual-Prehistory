package com.peeko32213.unusualprehistory.common.entity.custom.prehistoric;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.peeko32213.unusualprehistory.common.entity.animation.state.EntityAction;
import com.peeko32213.unusualprehistory.common.entity.animation.state.RandomStateGoal;
import com.peeko32213.unusualprehistory.common.entity.animation.state.StateHelper;
import com.peeko32213.unusualprehistory.common.entity.animation.state.WeightedState;
import com.peeko32213.unusualprehistory.common.entity.custom.ai.goal.attack.PachycephalosaurusAttackGoal;
import com.peeko32213.unusualprehistory.common.entity.custom.base.PrehistoricEntity;
import com.peeko32213.unusualprehistory.common.entity.custom.ai.goal.BabyPanicGoal;
import com.peeko32213.unusualprehistory.common.entity.util.navigator.SmartBodyHelper;
import com.peeko32213.unusualprehistory.common.entity.util.navigator.SmoothGroundNavigation;
import com.peeko32213.unusualprehistory.core.other.tags.UPBlockTags;
import com.peeko32213.unusualprehistory.core.other.tags.UPEntityTypeTags;
import com.peeko32213.unusualprehistory.core.registry.UPEffects;
import com.peeko32213.unusualprehistory.core.registry.entities.UPEntities;
import com.peeko32213.unusualprehistory.core.registry.UPSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.keyframe.event.SoundKeyframeEvent;
import software.bernie.geckolib.core.object.PlayState;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Predicate;

public class PachycephalosaurusEntity extends PrehistoricEntity {

    private static final EntityDataAccessor<Boolean> CHARGING = SynchedEntityData.defineId(PachycephalosaurusEntity.class, EntityDataSerializers.BOOLEAN);

    // Movement animations
    private static final RawAnimation PACHY_WALK = RawAnimation.begin().thenLoop("animation.pachycephalosaurus.walk");
    private static final RawAnimation PACHY_RUN = RawAnimation.begin().thenLoop("animation.pachycephalosaurus.run");
    private static final RawAnimation PACHY_SWIM = RawAnimation.begin().thenLoop("animation.pachycephalosaurus.swim");

    // Idle animations
    private static final RawAnimation PACHY_IDLE = RawAnimation.begin().thenLoop("animation.pachycephalosaurus.idle");
    private static final RawAnimation PACHY_GRAZE = RawAnimation.begin().thenPlay("animation.pachycephalosaurus.graze_blend");
    private static final RawAnimation PACHY_HUFF = RawAnimation.begin().thenPlay("animation.pachycephalosaurus.huff_blend");
    private static final RawAnimation PACHY_STOMP_1 = RawAnimation.begin().thenPlay("animation.pachycephalosaurus.stomp_blend1");
    private static final RawAnimation PACHY_STOMP_2 = RawAnimation.begin().thenPlay("animation.pachycephalosaurus.stomp_blend2");
    private static final RawAnimation PACHY_SIT_START = RawAnimation.begin().thenPlay("animation.pachycephalosaurus.sit_start");
    private static final RawAnimation PACHY_SIT = RawAnimation.begin().thenLoop("animation.pachycephalosaurus.sit");
    private static final RawAnimation PACHY_SIT_END = RawAnimation.begin().thenPlay("animation.pachycephalosaurus.sit_end");
    private static final RawAnimation PACHY_SLEEP = RawAnimation.begin().thenLoop("animation.pachycephalosaurus.sleep");

    // Attack animations
    private static final RawAnimation PACHY_CHARGE = RawAnimation.begin().thenPlay("animation.pachycephalosaurus.warn_blend").thenLoop("animation.pachycephalosaurus.charge");
    private static final RawAnimation PACHY_ATTACK_1 = RawAnimation.begin().thenPlay("animation.pachycephalosaurus.attack1");
    private static final RawAnimation PACHY_ATTACK_2 = RawAnimation.begin().thenPlay("animation.pachycephalosaurus.attack2");
    private static final RawAnimation PACHY_ATTACK_3 = RawAnimation.begin().thenPlay("animation.pachycephalosaurus.attack3");

    // Idle accessors
    private static final EntityDataAccessor<Boolean> GRAZE = SynchedEntityData.defineId(PachycephalosaurusEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> HUFF = SynchedEntityData.defineId(PachycephalosaurusEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> STOMP_1 = SynchedEntityData.defineId(PachycephalosaurusEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> STOMP_2 = SynchedEntityData.defineId(PachycephalosaurusEntity.class, EntityDataSerializers.BOOLEAN);

    // Starting predicates
    private static final Predicate<LivingEntity> PACHY_STARTING_PREDICATE = (e -> {
        if(e instanceof PachycephalosaurusEntity entity) {
            return !entity.getMoveControl().hasWanted() && !entity.isSprinting() && !entity.isInWater() && !entity.isRunning() && !entity.isCharging();
        }
        return false;
    });

    private static final Predicate<LivingEntity> PACHY_GRAZING_PREDICATE = (e -> {
        if(e instanceof PachycephalosaurusEntity entity) {
            return !entity.getMoveControl().hasWanted() && !entity.isSprinting() && !entity.isInWater() && !entity.isRunning() && !entity.isCharging() && entity.level().getBlockState(entity.blockPosition().below()).is(UPBlockTags.PACHY_GRAZING_BLOCKS);
        }
        return false;
    });

    private static final EntityAction PACHY_GRAZE_ACTION = new EntityAction(0, (e) -> {}, 1);
    private static final StateHelper PACHY_GRAZE_STATE =
            StateHelper.Builder.state(GRAZE, "pachycephalosaurus_graze")
                    .playTime(60)
                    .stopTime(140)
                    .startingPredicate(PACHY_GRAZING_PREDICATE)
                    .entityAction(PACHY_GRAZE_ACTION)
                    .build();

    private static final EntityAction PACHY_HUFF_ACTION = new EntityAction(0, (e) -> {}, 1);
    private static final StateHelper PACHY_HUFF_STATE =
            StateHelper.Builder.state(HUFF, "pachycephalosaurus_huff")
                    .playTime(30)
                    .stopTime(100)
                    .startingPredicate(PACHY_STARTING_PREDICATE)
                    .entityAction(PACHY_HUFF_ACTION)
                    .build();

    private static final EntityAction PACHY_STOMP_1_ACTION = new EntityAction(0, (e) -> {}, 1);
    private static final StateHelper PACHY_STOMP_1_STATE =
            StateHelper.Builder.state(STOMP_1, "pachycephalosaurus_stomp_1")
                    .playTime(10)
                    .stopTime(60)
                    .startingPredicate(PACHY_STARTING_PREDICATE)
                    .entityAction(PACHY_STOMP_1_ACTION)
                    .build();

    private static final EntityAction PACHY_STOMP_2_ACTION = new EntityAction(0, (e) -> {}, 1);
    private static final StateHelper PACHY_STOMP_2_STATE =
            StateHelper.Builder.state(STOMP_2, "pachycephalosaurus_stomp_2")
                    .playTime(10)
                    .stopTime(60)
                    .startingPredicate(PACHY_STARTING_PREDICATE)
                    .entityAction(PACHY_STOMP_2_ACTION)
                    .build();

    // Idle states
    @Override
    public ImmutableMap<String, StateHelper> getStates() {
        return ImmutableMap.of(
                PACHY_GRAZE_STATE.getName(), PACHY_GRAZE_STATE,
                PACHY_HUFF_STATE.getName(), PACHY_HUFF_STATE,
                PACHY_STOMP_1_STATE.getName(), PACHY_STOMP_1_STATE,
                PACHY_STOMP_2_STATE.getName(), PACHY_STOMP_2_STATE
        );
    }

    @Override
    public List<WeightedState<StateHelper>> getWeightedStatesToPerform() {
        return ImmutableList.of(
                WeightedState.of(PACHY_GRAZE_STATE, 11),
                WeightedState.of(PACHY_HUFF_STATE, 10),
                WeightedState.of(PACHY_STOMP_1_STATE, 7),
                WeightedState.of(PACHY_STOMP_2_STATE, 7)
        );
    }

    // Body control / navigation
    @Override
    protected @NotNull BodyRotationControl createBodyControl() {
        SmartBodyHelper helper = new SmartBodyHelper(this);
        helper.bodyLagMoving = 0.45F;
        helper.bodyLagStill = 0.3F;
        return helper;
    }

    @Override
    protected @NotNull PathNavigation createNavigation(Level levelIn) {
        return new SmoothGroundNavigation(this, levelIn);
    }

    public PachycephalosaurusEntity(EntityType<? extends PrehistoricEntity> entityType, Level level) {
        super(entityType, level);
        this.setMaxUpStep(1.25F);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 24.0D).add(Attributes.ARMOR, 5.0D).add(Attributes.MOVEMENT_SPEED, 0.23D).add(Attributes.ATTACK_DAMAGE, 6.0D).add(Attributes.FOLLOW_RANGE, 24.0D);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new RandomStateGoal<>(this));
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PachycephalosaurusAttackGoal(this));
        this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, LivingEntity.class, 8.0F, 1.5D, 1.5D, entity -> entity.getType().is(UPEntityTypeTags.PACHY_AVOIDS)));
        this.goalSelector.addGoal(3, new BabyPanicGoal(this, 2.0D));
        this.goalSelector.addGoal(4, new TemptGoal(this, 1.0D, Ingredient.of(Items.WHEAT), false));
        this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 1, 30));
        this.goalSelector.addGoal(5, new FollowParentGoal(this, 1.1D));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(8, (new HurtByTargetGoal(this)));
    }

    protected SoundEvent getAmbientSound() {
        return UPSounds.PACHY_IDLE.get();
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return UPSounds.PACHY_HURT.get();
    }

    protected SoundEvent getDeathSound() {
        return UPSounds.PACHY_DEATH.get();
    }

    protected void playStepSound(BlockPos p_28301_, BlockState p_28302_) {
        if (this.isCharging()) {
            this.playSound(SoundEvents.GOAT_STEP, 0.25F, 0.8F);
        }
        this.playSound(SoundEvents.GOAT_STEP, 0.15F, 0.8F);
    }

    @Override
    public float getSoundVolume() {
        if(this.isBaby()){
            return 0.6F;
        }
        else{
            return 0.8F;
        }
    }

    // Charging
    public void setCharging(boolean charge) {
        this.entityData.set(CHARGING, charge);
    }
    public boolean isCharging() {
        return this.entityData.get(CHARGING);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(GRAZE, false);
        this.entityData.define(HUFF, false);
        this.entityData.define(STOMP_1, false);
        this.entityData.define(STOMP_2, false);
        this.entityData.define(CHARGING, false);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("Charging", this.isCharging());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setCharging(compound.getBoolean("Charging"));
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        return UPEntities.PACHY.get().create(serverLevel);
    }

    public void tick() {
        super.tick();
        if (this.getTarget() != null && this.getTarget().hasEffect(UPEffects.PACHYS_MIGHT.get())) {
            this.setTarget(null);
            this.setLastHurtByMob(null);
        }

        if (isRunning() && !hasRunningAttributes) {
            hasRunningAttributes = true;
            this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.38D);
        }
        if (!isRunning() && hasRunningAttributes) {
            hasRunningAttributes = false;
            this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.23D);
        }
    }

    // Sprinting
    @Override
    public void customServerAiStep() {
        if (this.getMoveControl().hasWanted()) {
            this.setSprinting(this.getMoveControl().getSpeedModifier() >= 1.4D);
        } else {
            this.setSprinting(false);
        }
        super.customServerAiStep();
    }

    // Animation sounds
    private void soundListener(SoundKeyframeEvent<PachycephalosaurusEntity> event) {
        PachycephalosaurusEntity pachycephalosaurus = event.getAnimatable();
        if (pachycephalosaurus.level().isClientSide) {
            if (event.getKeyframeData().getSound().equals("pachycephalosaurus_bonk")) {
                pachycephalosaurus.level().playLocalSound(pachycephalosaurus.getX(), pachycephalosaurus.getY(), pachycephalosaurus.getZ(), UPSounds.PACHY_HEADBUTT.get(), pachycephalosaurus.getSoundSource(), 0.75F, pachycephalosaurus.getVoicePitch(), false);
            }
            if (event.getKeyframeData().getSound().equals("pachycephalosaurus_warn")) {
                pachycephalosaurus.level().playLocalSound(pachycephalosaurus.getX(), pachycephalosaurus.getY(), pachycephalosaurus.getZ(), UPSounds.PACHY_WARN.get(), pachycephalosaurus.getSoundSource(), 0.5F, pachycephalosaurus.getVoicePitch(), false);
            }
            if (event.getKeyframeData().getSound().equals("pachycephalosaurus_warn_stomp")) {
                pachycephalosaurus.level().playLocalSound(pachycephalosaurus.getX(), pachycephalosaurus.getY(), pachycephalosaurus.getZ(), SoundEvents.GOAT_STEP, pachycephalosaurus.getSoundSource(), 0.5F, 0.8F, false);
            }
            if (event.getKeyframeData().getSound().equals("pachycephalosaurus_stomp")) {
                pachycephalosaurus.level().playLocalSound(pachycephalosaurus.getX(), pachycephalosaurus.getY(), pachycephalosaurus.getZ(), SoundEvents.GOAT_STEP, pachycephalosaurus.getSoundSource(), 0.25F, 0.8F, false);
            }
        }
    }

    // Animation control
    @Override
    public void registerControllers(final AnimatableManager.ControllerRegistrar controllers) {
        AnimationController<PachycephalosaurusEntity> controller = new AnimationController<>(this, "controller", 5, this::predicate);
        controllers.add(controller);

        AnimationController<PachycephalosaurusEntity> idle = new AnimationController<>(this, "idleController", 0, this::idlePredicate);
        idle.setSoundKeyframeHandler(this::soundListener);
        controllers.add(idle);

        AnimationController<PachycephalosaurusEntity> attack = new AnimationController<>(this, "attackController", 5, this::attackPredicate);
        attack.setSoundKeyframeHandler(this::soundListener);
        controllers.add(attack);
    }

    protected <E extends PachycephalosaurusEntity> PlayState predicate(final software.bernie.geckolib.core.animation.AnimationState<E> event) {
        if (this.isFromBook()) {
            return event.setAndContinue(PACHY_IDLE);
        }

        if (this.isInWater()) {
            event.setAndContinue(PACHY_SWIM);
            event.getController().setAnimationSpeed(1.0D);
        }
        if (!(event.getLimbSwingAmount() > -0.06F && event.getLimbSwingAmount() < 0.06F) && !this.isCharging()) {
            if (this.isSprinting() || this.isRunning()) {
                event.getController().setAnimationSpeed(1.0F);
                event.setAndContinue(PACHY_RUN);
            }
            else {
                event.getController().setAnimationSpeed(1.0F);
                event.setAndContinue(PACHY_WALK);
            }
        }
        else if (!this.isInWater()) {
            event.setAndContinue(PACHY_IDLE);
            event.getController().setAnimationSpeed(1.0F);
        }
        return PlayState.CONTINUE;
    }

    // Idle animations
    protected <E extends PachycephalosaurusEntity> PlayState idlePredicate(final AnimationState<E> event) {
        if (getBooleanState(GRAZE)) {
            event.getController().setAnimation(PACHY_GRAZE);
            return PlayState.CONTINUE;
        }
        if (getBooleanState(HUFF)) {
            event.getController().setAnimation(PACHY_HUFF);
            return PlayState.CONTINUE;
        }
        if (getBooleanState(STOMP_1)) {
            event.getController().setAnimation(PACHY_STOMP_1);
            return PlayState.CONTINUE;
        }
        if (getBooleanState(STOMP_2)) {
            event.getController().setAnimation(PACHY_STOMP_2);
            return PlayState.CONTINUE;
        }
        event.getController().forceAnimationReset();
        return PlayState.STOP;
    }

    // Attack animations
    protected <E extends PachycephalosaurusEntity> PlayState attackPredicate(final AnimationState<E> event) {
        int animState = this.getAnimationState();
        if (animState == 21) {
            event.setAndContinue(PACHY_ATTACK_1);
            return PlayState.CONTINUE;
        }
        else if (animState == 22) {
            event.setAndContinue(PACHY_ATTACK_2);
            return PlayState.CONTINUE;
        }
        else if (animState == 23) {
            event.setAndContinue(PACHY_ATTACK_3);
            return PlayState.CONTINUE;
        }
        else if (this.isCharging() && animState == 24) {
            event.getController().setAnimationSpeed(1.0F);
            event.setAndContinue(PACHY_CHARGE);
            return PlayState.CONTINUE;
        }
        else if (animState == 0) {
            event.getController().forceAnimationReset();
            return PlayState.STOP;
        }
        else return PlayState.CONTINUE;
    }
}

