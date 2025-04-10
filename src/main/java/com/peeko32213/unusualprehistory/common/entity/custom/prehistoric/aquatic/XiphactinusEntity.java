package com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.aquatic;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.peeko32213.unusualprehistory.common.entity.animation.state.EntityAction;
import com.peeko32213.unusualprehistory.common.entity.animation.state.StateHelper;
import com.peeko32213.unusualprehistory.common.entity.animation.state.WeightedState;
import com.peeko32213.unusualprehistory.common.entity.custom.ai.goal.attack.XiphactinusAttackGoal;
import com.peeko32213.unusualprehistory.common.entity.custom.base.PrehistoricAquaticEntity;
import com.peeko32213.unusualprehistory.common.entity.custom.part.XiphactinusPartEntity;
import com.peeko32213.unusualprehistory.common.entity.util.navigator.SmartBodyHelper;
import com.peeko32213.unusualprehistory.core.registry.UPSounds;
import com.peeko32213.unusualprehistory.core.other.tags.UPEntityTypeTags;
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
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.keyframe.event.SoundKeyframeEvent;
import software.bernie.geckolib.core.object.PlayState;

import javax.annotation.Nullable;
import java.util.List;

public class XiphactinusEntity extends PrehistoricAquaticEntity {

    private static final EntityDataAccessor<Boolean> RAMMING = SynchedEntityData.defineId(XiphactinusEntity.class, EntityDataSerializers.BOOLEAN);

    public final XiphactinusPartEntity head;
    public final XiphactinusPartEntity tail;
    public final XiphactinusPartEntity[] allParts;
    public int ringBufferIndex = -1;
    public final float[][] ringBuffer = new float[64][3];

    private int passiveFor = 0;

    public float prevTilt;
    public float tilt;
    public float currentRoll = 0.0F;

    // Idle animations
    private static final RawAnimation XIPH_IDLE = RawAnimation.begin().thenLoop("animation.xiphactinus.idle");
    private static final RawAnimation XIPH_INSPECT = RawAnimation.begin().thenPlay("animation.xiphactinus.inspect_blend");
    private static final RawAnimation XIPH_HICCUP = RawAnimation.begin().thenPlay("animation.xiphactinus.hiccup_blend");
    private static final RawAnimation XIPH_OPEN_JAW = RawAnimation.begin().thenPlay("animation.xiphactinus.holdingprey_blend");

    // Movement animations
    private static final RawAnimation XIPH_SWIM = RawAnimation.begin().thenLoop("animation.xiphactinus.swim");
    private static final RawAnimation XIPH_SWIM_FAST = RawAnimation.begin().thenLoop("animation.xiphactinus.swimfast");
    private static final RawAnimation XIPH_FLOP = RawAnimation.begin().thenLoop("animation.xiphactinus.flop");

    // Attack animations
    private static final RawAnimation XIPH_RAM = RawAnimation.begin().thenLoop("animation.xiphactinus.attack_charge");
    private static final RawAnimation XIPH_BITE = RawAnimation.begin().thenPlay("animation.xiphactinus.attack_impact");

    // Idle accessors
    private static final EntityDataAccessor<Boolean> IDLE_1_AC = SynchedEntityData.defineId(XiphactinusEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> IDLE_2_AC = SynchedEntityData.defineId(XiphactinusEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> IDLE_3_AC = SynchedEntityData.defineId(XiphactinusEntity.class, EntityDataSerializers.BOOLEAN);

    // Idle actions
    private static final EntityAction XIPH_IDLE_1_ACTION = new EntityAction(0, (e) -> {}, 1);

    private static final StateHelper XIPH_IDLE_1_STATE =
            StateHelper.Builder.state(IDLE_1_AC, "xiphactinus_inspect")
                    .playTime(60)
                    .stopTime(120)
                    .entityAction(XIPH_IDLE_1_ACTION)
                    .build();

    private static final EntityAction XIPH_IDLE_2_ACTION = new EntityAction(0, (e) -> {}, 1);

    private static final StateHelper XIPH_IDLE_2_STATE =
            StateHelper.Builder.state(IDLE_2_AC, "xiphactinus_yawn")
                    .playTime(20)
                    .stopTime(60)
                    .entityAction(XIPH_IDLE_2_ACTION)
                    .build();

    private static final EntityAction XIPH_IDLE_3_ACTION = new EntityAction(0, (e) -> {}, 1);

    private static final StateHelper XIPH_IDLE_3_STATE =
            StateHelper.Builder.state(IDLE_2_AC, "xiphactinus_open_mouth")
                    .playTime(160)
                    .stopTime(200)
                    .entityAction(XIPH_IDLE_3_ACTION)
                    .build();

    @Override
    public ImmutableMap<String, StateHelper> getStates() {
        return ImmutableMap.of(
                XIPH_IDLE_1_STATE.getName(), XIPH_IDLE_1_STATE,
                XIPH_IDLE_2_STATE.getName(), XIPH_IDLE_2_STATE,
                XIPH_IDLE_3_STATE.getName(), XIPH_IDLE_3_STATE
        );
    }

    @Override
    public List<WeightedState<StateHelper>> getWeightedStatesToPerform() {
        return ImmutableList.of(
                WeightedState.of(XIPH_IDLE_1_STATE, 0),
                WeightedState.of(XIPH_IDLE_2_STATE, 15),
                WeightedState.of(XIPH_IDLE_3_STATE, 18)
        );
    }

    @Override
    protected @NotNull BodyRotationControl createBodyControl() {
        SmartBodyHelper helper = new SmartBodyHelper(this);
        helper.bodyLagMoving = 0.2F;
        helper.bodyLagStill = 0.1F;
        return helper;
    }

    public XiphactinusEntity(EntityType<? extends PrehistoricAquaticEntity> entityType, Level level) {
        super(entityType, level);
        this.head = new XiphactinusPartEntity(this, 1.25F,1.1F );
        this.tail = new XiphactinusPartEntity(this, 1.25F, 1.1F);
        this.allParts = new XiphactinusPartEntity[]{this.head, this.tail};
    }

    public static AttributeSupplier.@NotNull Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 30.0D)
                .add(Attributes.MOVEMENT_SPEED, 1.0D)
                .add(Attributes.ATTACK_DAMAGE, 8.0D)
                .add(Attributes.FOLLOW_RANGE, 24.0D);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new TryFindWaterGoal(this));
        this.goalSelector.addGoal(1, new XiphactinusAttackGoal(this));
        this.goalSelector.addGoal(4, new RandomSwimmingGoal(this, 1.0D, 10));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, LivingEntity.class, 50, true, true, entity -> entity.getType().is(UPEntityTypeTags.XIPH_TARGETS)));
        this.targetSelector.addGoal(7, new HurtByTargetGoal(this));
    }
 
    protected @NotNull PathNavigation createNavigation(@NotNull Level p_27480_) {
        return new WaterBoundPathNavigation(this, p_27480_);
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.COD_AMBIENT;
    }

    protected SoundEvent getHurtSound(@NotNull DamageSource damageSourceIn) {
        return UPSounds.DUNK_HURT.get();
    }

    protected SoundEvent getDeathSound() {
        return UPSounds.DUNK_DEATH.get();
    }

    protected @NotNull SoundEvent getFlopSound() {
        return SoundEvents.COD_FLOP;
    }

    // Synched data
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(RAMMING, false);
        this.entityData.define(IDLE_1_AC, false);
        this.entityData.define(IDLE_2_AC, false);
        this.entityData.define(IDLE_3_AC, false);
    }

    // Save data
    public void addAdditionalSaveData(@NotNull CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("Ramming", this.isRamming());
        compound.putInt("PassiveFor", passiveFor);
    }

    public void readAdditionalSaveData(@NotNull CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setRamming(compound.getBoolean("Ramming"));
        passiveFor = compound.getInt("PassiveFor");
    }

    // Ramming
    public void setRamming(boolean ramming) {
        this.entityData.set(RAMMING, ramming);
    }
    public boolean isRamming() {
        return this.entityData.get(RAMMING);
    }

    @Override
    public void aiStep() {
        super.aiStep();

        // Tilt
        prevTilt = tilt;
        if (this.isInWater() && !this.onGround()) {
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

        // Roll
        float prevRoll =  this.currentRoll;
        float targetRoll = Math.max(-0.45F, Math.min(0.45F, (this.getYRot() - this.yRotO) * 0.1F));
        targetRoll = -targetRoll;
        this.currentRoll = prevRoll + (targetRoll - prevRoll) * 0.05F;

        if (!this.isInWater() && this.onGround() && this.verticalCollision) {
            this.setDeltaMovement(this.getDeltaMovement().add((this.random.nextFloat() * 2.0F - 1.0F) * 0.05F, 0.4F, (this.random.nextFloat() * 2.0F - 1.0F) * 0.05F));
            this.setOnGround(false);
            this.hasImpulse = true;
            this.playSound(this.getFlopSound(), this.getSoundVolume(), this.getVoicePitch());
        }

        // Multipart
        if (!this.isNoAi()) {
            if (this.ringBufferIndex < 0) {
                for (int i = 0; i < this.ringBuffer.length; ++i) {
                    this.ringBuffer[i][0] = this.getYRot();
                    this.ringBuffer[i][1] = (float) this.getY();
                }
            }
            this.ringBufferIndex++;
            if (this.ringBufferIndex == this.ringBuffer.length) {
                this.ringBufferIndex = 0;
            }
            this.ringBuffer[this.ringBufferIndex][0] = this.getYRot();
            this.ringBuffer[ringBufferIndex][1] = (float) this.getY();
            Vec3[] avector3d = new Vec3[this.allParts.length];

            for (int j = 0; j < this.allParts.length; ++j) {
                this.allParts[j].collideWithNearbyEntities();
                avector3d[j] = new Vec3(this.allParts[j].getX(), this.allParts[j].getY(), this.allParts[j].getZ());
            }
            final float f17 = this.getYRot() * Mth.DEG_TO_RAD;
            final float pitch = this.getXRot() * Mth.DEG_TO_RAD;
            final float xRotDiv90 = Math.abs(this.getXRot() / 90F);
            final float f3 = Mth.sin(f17) * (1 - xRotDiv90);
            final float f18 = Mth.cos(f17) * (1 - xRotDiv90);

            this.setPartPosition(this.head, f3 * -1.8F, -pitch * 0.8F, -f18 * -1.8F);
            this.setPartPosition(this.tail, f3 * 1.8F, pitch * 0.3F, f18 * -1.8F);

            for (int l = 0; l < this.allParts.length; ++l) {
                this.allParts[l].xo = avector3d[l].x;
                this.allParts[l].yo = avector3d[l].y;
                this.allParts[l].zo = avector3d[l].z;
                this.allParts[l].xOld = avector3d[l].x;
                this.allParts[l].yOld = avector3d[l].y;
                this.allParts[l].zOld = avector3d[l].z;
            }
        }
    }

    @Override
    public boolean isMultipartEntity() {
        return true;
    }

    @Override
    public net.minecraftforge.entity.PartEntity<?>[] getParts() {
        return this.allParts;
    }

    private void setPartPosition(XiphactinusPartEntity part, double offsetX, double offsetY, double offsetZ) {
        part.setPos(this.getX() + offsetX * part.scale, this.getY() + offsetY * part.scale, this.getZ() + offsetZ * part.scale);
    }

    public boolean attackEntityPartFrom(XiphactinusPartEntity xiphactinusPart, DamageSource source, float amount) {
        return this.hurt(source, amount);
    }

    public InteractionResult interactEntityPartFrom(XiphactinusPartEntity xiphactinusPart, Player player, InteractionHand hand) {
        return this.mobInteract(player, hand);
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

    public void tick() {
        super.tick();

        if (isRunning() && !hasRunningAttributes) {
            hasRunningAttributes = true;
            this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(2.0D);
        }
        if (!isRunning() && hasRunningAttributes) {
            hasRunningAttributes = false;
            this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(1.0D);
        }
    }

    @Override
    public void travel(Vec3 pTravelVector) {
        if (this.isEffectiveAi() && this.isInWater()) {
            this.moveRelative(0.01F, pTravelVector);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.9));
            if (this.getTarget() == null) {
                this.setDeltaMovement(this.getDeltaMovement().add(0.0F, -0.005, 0.0F));
            }
        } else {
            super.travel(pTravelVector);
        }
    }

    @Override
    protected float getStandingEyeHeight(Pose pPose, EntityDimensions pSize) {
        return pSize.height * 0.65F;
    }

    public void killed() {
        passiveFor = 2400 + random.nextInt(100, 1200);
        this.heal(15);
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        return null;
    }

    // Animation sounds
    private void soundListener(SoundKeyframeEvent<XiphactinusEntity> event) {
        XiphactinusEntity xiphactinus = event.getAnimatable();
        if (xiphactinus.level().isClientSide) {
        }
    }

    // Attack controller
    @Override
    public void registerControllers(final AnimatableManager.ControllerRegistrar controllers) {
        AnimationController<XiphactinusEntity> controller = new AnimationController<>(this, "controller", 5, this::predicate);
        controllers.add(controller);

        AnimationController<XiphactinusEntity> idle = new AnimationController<>(this, "idleController", 0, this::idlePredicate);
        idle.setSoundKeyframeHandler(this::soundListener);
        controllers.add(idle);

        AnimationController<XiphactinusEntity> attack = new AnimationController<>(this, "attackController", 5, this::attackPredicate);
        attack.setSoundKeyframeHandler(this::soundListener);
        controllers.add(attack);
    }

    protected <E extends XiphactinusEntity> PlayState predicate(final software.bernie.geckolib.core.animation.AnimationState<E> event) {

        if (this.isFromBook()) {
            return event.setAndContinue(XIPH_IDLE);
        }

        int animState = this.getAnimationState();

        if(!this.isFromBook()) {
            if (animState == 1) {
                return event.setAndContinue(XIPH_BITE);
            }
            else {
                if (!(event.getLimbSwingAmount() > -0.06F && event.getLimbSwingAmount() < 0.06F) && this.isInWater()) {
                    if(this.isSprinting() || this.isRunning()){
                        event.setAndContinue(XIPH_SWIM_FAST);
                        event.getController().setAnimationSpeed(1.0F);
                    } else {
                        event.setAndContinue(XIPH_SWIM);
                        event.getController().setAnimationSpeed(1.0F);
                    }
                    return PlayState.CONTINUE;
                }
                if (!this.isInWater()) {
                    event.setAndContinue(XIPH_FLOP);
                    event.getController().setAnimationSpeed(2.0F);
                    return PlayState.CONTINUE;
                }
                else if (this.isInWater()) {
                    return PlayState.CONTINUE;
                }
            }
        }
        return PlayState.CONTINUE;
    }

    // Idle animations
    protected <E extends XiphactinusEntity> PlayState idlePredicate(final AnimationState<E> event) {
        return PlayState.CONTINUE;
    }

    // Attack animations
    protected <E extends XiphactinusEntity> PlayState attackPredicate(final AnimationState<E> event) {
        int animState = this.getAnimationState();
        if (animState == 21) {
            event.setAndContinue(XIPH_BITE);
            return PlayState.CONTINUE;
        }
        if (animState == 22 && this.isRamming()) {
            event.setAndContinue(XIPH_RAM);
            return PlayState.CONTINUE;
        }
        else if (animState == 0) {
            event.getController().forceAnimationReset();
            return PlayState.STOP;
        }
        else return PlayState.CONTINUE;
    }
}
