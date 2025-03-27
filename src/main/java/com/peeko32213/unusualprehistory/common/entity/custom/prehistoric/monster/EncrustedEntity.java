package com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.monster;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.peeko32213.unusualprehistory.common.entity.animation.state.EntityAction;
import com.peeko32213.unusualprehistory.common.entity.animation.state.RandomStateGoal;
import com.peeko32213.unusualprehistory.common.entity.animation.state.StateHelper;
import com.peeko32213.unusualprehistory.common.entity.animation.state.WeightedState;
import com.peeko32213.unusualprehistory.common.entity.custom.ai.goal.attack.EncrustedAttackGoal;
import com.peeko32213.unusualprehistory.common.entity.custom.base.PrehistoricMonsterEntity;
import com.peeko32213.unusualprehistory.common.entity.projectile.AmberShotEntity;
import com.peeko32213.unusualprehistory.common.entity.util.interfaces.IVariantEntity;
import com.peeko32213.unusualprehistory.common.entity.util.navigator.SmartBodyHelper;
import com.peeko32213.unusualprehistory.common.entity.util.navigator.SmoothGroundNavigation;
import com.peeko32213.unusualprehistory.common.entity.util.ranged.CustomAbstractRangedAttack;
import com.peeko32213.unusualprehistory.core.registry.UPSounds;
import com.peeko32213.unusualprehistory.core.other.tags.UPEntityTypeTags;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.keyframe.event.SoundKeyframeEvent;
import software.bernie.geckolib.core.object.PlayState;

import java.util.List;

public class EncrustedEntity extends PrehistoricMonsterEntity implements IVariantEntity {

    // Movement animations
    private static final RawAnimation ENCRUSTED_WALK = RawAnimation.begin().thenLoop("animation.encrusted.walk");
    private static final RawAnimation ENCRUSTED_RUN = RawAnimation.begin().thenLoop("animation.encrusted.run");

    // Idle animations
    private static final RawAnimation ENCRUSTED_IDLE = RawAnimation.begin().thenLoop("animation.encrusted.idle");
    private static final RawAnimation ENCRUSTED_TWITCH_1 = RawAnimation.begin().thenPlay("animation.encrusted.twitch_blend1");
    private static final RawAnimation ENCRUSTED_TWITCH_2 = RawAnimation.begin().thenPlay("animation.encrusted.twitch_blend2");
    private static final RawAnimation ENCRUSTED_TWITCH_3 = RawAnimation.begin().thenPlay("animation.encrusted.twitch_blend3");
    private static final RawAnimation ENCRUSTED_PROBOSCIS = RawAnimation.begin().thenPlay("animation.encrusted.proboscis_blend");

    // Attack animations
    private static final RawAnimation ENCRUSTED_ATTACK = RawAnimation.begin().thenPlay("animation.encrusted.attack_blend");
    private static final RawAnimation ENCRUSTED_SHOOT = RawAnimation.begin().thenPlay("animation.encrusted.shoot_blend");

    // Idle accessors
    private static final EntityDataAccessor<Boolean> TWITCH_1 = SynchedEntityData.defineId(EncrustedEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> TWITCH_2 = SynchedEntityData.defineId(EncrustedEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> TWITCH_3 = SynchedEntityData.defineId(EncrustedEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> PROBOSCIS = SynchedEntityData.defineId(EncrustedEntity.class, EntityDataSerializers.BOOLEAN);

    // Idle actions
    private static final EntityAction ENCRUSTED_TWITCH_1_ACTION = new EntityAction(0, (e) -> {}, 1);

    private static final StateHelper ENCRUSTED_TWITCH_1_STATE =
            StateHelper.Builder.state(TWITCH_1, "encrusted_twitch_1")
                    .playTime(30)
                    .stopTime(50)
                    .entityAction(ENCRUSTED_TWITCH_1_ACTION)
                    .build();

    private static final EntityAction ENCRUSTED_TWITCH_2_ACTION = new EntityAction(0, (e) -> {}, 1);

    private static final StateHelper ENCRUSTED_TWITCH_2_STATE =
            StateHelper.Builder.state(TWITCH_2, "encrusted_twitch_2")
                    .playTime(20)
                    .stopTime(40)
                    .entityAction(ENCRUSTED_TWITCH_2_ACTION)
                    .build();

    private static final EntityAction ENCRUSTED_TWITCH_3_ACTION = new EntityAction(0, (e) -> {}, 1);

    private static final StateHelper ENCRUSTED_TWITCH_3_STATE =
            StateHelper.Builder.state(TWITCH_3, "encrusted_twitch_3")
                    .playTime(20)
                    .stopTime(40)
                    .entityAction(ENCRUSTED_TWITCH_3_ACTION)
                    .build();

    private static final EntityAction ENCRUSTED_PROBOSCIS_ACTION = new EntityAction(0, (e) -> {}, 1);

    private static final StateHelper ENCRUSTED_PROBOSCIS_STATE =
            StateHelper.Builder.state(PROBOSCIS, "encrusted_proboscis")
                    .playTime(30)
                    .stopTime(90)
                    .entityAction(ENCRUSTED_PROBOSCIS_ACTION)
                    .build();

    @Override
    public ImmutableMap<String, StateHelper> getStates() {
        return ImmutableMap.of(
                ENCRUSTED_TWITCH_1_STATE.getName(), ENCRUSTED_TWITCH_1_STATE,
                ENCRUSTED_TWITCH_2_STATE.getName(), ENCRUSTED_TWITCH_2_STATE,
                ENCRUSTED_TWITCH_3_STATE.getName(), ENCRUSTED_TWITCH_3_STATE,
                ENCRUSTED_PROBOSCIS_STATE.getName(), ENCRUSTED_PROBOSCIS_STATE
        );
    }

    @Override
    public List<WeightedState<StateHelper>> getWeightedStatesToPerform() {
        return ImmutableList.of(
                WeightedState.of(ENCRUSTED_TWITCH_1_STATE, 10),
                WeightedState.of(ENCRUSTED_TWITCH_2_STATE, 10),
                WeightedState.of(ENCRUSTED_TWITCH_3_STATE, 10),
                WeightedState.of(ENCRUSTED_PROBOSCIS_STATE, 9)
        );
    }

    // Body control / navigation
    @Override
    protected @NotNull BodyRotationControl createBodyControl() {
        SmartBodyHelper helper = new SmartBodyHelper(this);
        helper.bodyLagMoving = 0.35F;
        helper.bodyLagStill = 0.2F;
        return helper;
    }

    @Override
    protected @NotNull PathNavigation createNavigation(Level levelIn) {
        return new SmoothGroundNavigation(this, levelIn);
    }

    public EncrustedEntity(EntityType<? extends PrehistoricMonsterEntity> entityType, Level level) {
        super(entityType, level);
        this.setMaxUpStep(1.25F);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
            .add(Attributes.MAX_HEALTH, 24)
            .add(Attributes.MOVEMENT_SPEED, 0.2D)
            .add(Attributes.ATTACK_DAMAGE, 6)
            .add(Attributes.FOLLOW_RANGE, 24);
    }

    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(2, new RandomStateGoal<>(this));
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(2, new EncrustedAttackGoal(this, 1.3F, true, new RangedAttack(this).setProjectileOriginOffset(0.8, 0.4, 0.8).setDamage((float) this.getAttribute(Attributes.ATTACK_DAMAGE).getValue() / 2)));
        this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 1, 30));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 15.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, LivingEntity.class, 10, false, false, entity -> entity.getType().is(UPEntityTypeTags.ENCRUSTED_TARGETS)));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    public static class RangedAttack extends CustomAbstractRangedAttack {

        public RangedAttack(PrehistoricMonsterEntity parentEntity) {
            super(parentEntity);
        }

        @Override
        public Projectile getProjectile(Level world, double d2, double d3, double d4) {
            return new AmberShotEntity(world, this.parentEntity, d2, d3, d4, damage);
        }
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

    // Sounds
    protected SoundEvent getAmbientSound() {
        return UPSounds.ENCRUSTED_IDLE.get();
    }

    protected SoundEvent getHurtSound(@NotNull DamageSource damageSourceIn) {
        return UPSounds.ENCRUSTED_HURT.get();
    }

    protected SoundEvent getDeathSound() {
        return UPSounds.ENCRUSTED_DEATH.get();
    }

    protected void playStepSound(@NotNull BlockPos p_28301_, @NotNull BlockState p_28302_) {
        this.playSound(SoundEvents.SPIDER_STEP, 0.15F, 1.25F);
    }

    @Override
    public MobType getMobType() {
        return MobType.ARTHROPOD;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(TWITCH_1, false);
        this.entityData.define(TWITCH_2, false);
        this.entityData.define(TWITCH_3, false);
        this.entityData.define(PROBOSCIS, false);
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected boolean shouldDespawnInPeaceful() {
        return true;
    }

    // Animation sounds
    private void soundListener(SoundKeyframeEvent<EncrustedEntity> event) {
        EncrustedEntity encrusted = event.getAnimatable();
        if (event.getKeyframeData().getSound().equals("encrusted_swipe")) {
            encrusted.level().playLocalSound(encrusted.getX(), encrusted.getY(), encrusted.getZ(), UPSounds.ENCRUSTED_MELEE.get(), encrusted.getSoundSource(), 0.5F, encrusted.getVoicePitch(), false);
        }
        if (event.getKeyframeData().getSound().equals("encrusted_shoot")) {
            encrusted.level().playLocalSound(encrusted.getX(), encrusted.getY(), encrusted.getZ(), UPSounds.ENCRUSTED_SPIT.get(), encrusted.getSoundSource(), 0.5F, encrusted.getVoicePitch(), false);
        }
    }

    // Animation control
    @Override
    public void registerControllers(final AnimatableManager.ControllerRegistrar controllers) {
        AnimationController<EncrustedEntity> controller = new AnimationController<>(this, "controller", 5, this::predicate);
        controllers.add(controller);

        AnimationController<EncrustedEntity> idle = new AnimationController<>(this, "idleController", 5, this::idlePredicate);
        controllers.add(idle);

        AnimationController<EncrustedEntity> shoot = new AnimationController<>(this, "shootController", 5, this::shootPredicate);
        controllers.add(shoot);

        AnimationController<EncrustedEntity> attack = new AnimationController<>(this, "attackController", 5, this::attackPredicate);
        attack.setSoundKeyframeHandler(this::soundListener);
        controllers.add(attack);
    }

    private <E extends EncrustedEntity> PlayState predicate(final software.bernie.geckolib.core.animation.AnimationState<E> event) {

        if (this.isFromBook()) {
            return event.setAndContinue(ENCRUSTED_IDLE);
        }

        if (this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6) {
            if (this.isAggressive()) {
                event.setAndContinue(ENCRUSTED_RUN);
                event.getController().setAnimationSpeed(1.0F);
            } else {
                event.setAndContinue(ENCRUSTED_WALK);
                event.getController().setAnimationSpeed(1.0F);
            }
        }

        else {
            event.setAndContinue(ENCRUSTED_IDLE);
            event.getController().setAnimationSpeed(1.0F);
        }
        return PlayState.CONTINUE;
    }

    // Idle animations
    protected <E extends EncrustedEntity> PlayState idlePredicate(final AnimationState<E> event) {
        if (getBooleanState(TWITCH_1)) {
            event.getController().setAnimation(ENCRUSTED_TWITCH_1);
            return PlayState.CONTINUE;
        }
        if (getBooleanState(TWITCH_2)) {
            event.getController().setAnimation(ENCRUSTED_TWITCH_2);
            return PlayState.CONTINUE;
        }
        if (getBooleanState(TWITCH_3)) {
            event.getController().setAnimation(ENCRUSTED_TWITCH_3);
            return PlayState.CONTINUE;
        }
        if (getBooleanState(PROBOSCIS)) {
            event.getController().setAnimation(ENCRUSTED_PROBOSCIS);
            return PlayState.CONTINUE;
        }
        event.getController().forceAnimationReset();
        return PlayState.STOP;
    }

    // Attack animation
    protected <E extends EncrustedEntity> PlayState attackPredicate(final AnimationState<E> event) {
        int animState = this.getAnimationState();
        if (animState == 21) {
            event.getController().setAnimation(ENCRUSTED_ATTACK);
            return PlayState.CONTINUE;
        }
        event.getController().forceAnimationReset();
        return PlayState.STOP;
    }

    // Shoot animation
    protected <E extends EncrustedEntity> PlayState shootPredicate(final AnimationState<E> event) {
        int animState = this.getAnimationState();
        if (animState == 22) {
            event.getController().setAnimation(ENCRUSTED_SHOOT);
            return PlayState.CONTINUE;
        }
        event.getController().forceAnimationReset();
        return PlayState.STOP;
    }
}
