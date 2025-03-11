package com.peeko32213.unusualprehistory.common.entity.custom.prehistoric;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.peeko32213.unusualprehistory.common.entity.animation.state.EntityAction;
import com.peeko32213.unusualprehistory.common.entity.animation.state.StateHelper;
import com.peeko32213.unusualprehistory.common.entity.animation.state.WeightedState;
import com.peeko32213.unusualprehistory.common.entity.custom.base.PrehistoricEntity;
import com.peeko32213.unusualprehistory.common.entity.util.goal.BabyPanicGoal;
import com.peeko32213.unusualprehistory.common.entity.util.goal.PounceGoal;
import com.peeko32213.unusualprehistory.common.entity.util.interfaces.IVariantEntity;
import com.peeko32213.unusualprehistory.core.registry.*;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.ForgeEventFactory;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.keyframe.event.SoundKeyframeEvent;
import software.bernie.geckolib.core.object.PlayState;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class VelociraptorEntity extends PrehistoricEntity implements GeoEntity, GeoAnimatable, IVariantEntity {

    private static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(VelociraptorEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> PRESS = SynchedEntityData.defineId(VelociraptorEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> SCALE = SynchedEntityData.defineId(VelociraptorEntity.class, EntityDataSerializers.INT);

    private static final EntityDataAccessor<Boolean> HAS_TARGET = SynchedEntityData.defineId(VelociraptorEntity.class, EntityDataSerializers.BOOLEAN);

    protected boolean pushingState = false;

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
    private static final RawAnimation VELOCI_ATTACK_1 = RawAnimation.begin().thenPlay("animation.velociraptor.bite_blend");
    private static final RawAnimation VELOCI_ATTACK_2 = RawAnimation.begin().thenPlay("animation.velociraptor.kick");

    // Idle accessors
    private static final EntityDataAccessor<Boolean> IDLE_1_AC = SynchedEntityData.defineId(VelociraptorEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> IDLE_2_AC = SynchedEntityData.defineId(VelociraptorEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> IDLE_3_AC = SynchedEntityData.defineId(VelociraptorEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> IDLE_4_AC = SynchedEntityData.defineId(VelociraptorEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> IDLE_5_AC = SynchedEntityData.defineId(VelociraptorEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> IDLE_6_AC = SynchedEntityData.defineId(VelociraptorEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> IDLE_7_AC = SynchedEntityData.defineId(VelociraptorEntity.class, EntityDataSerializers.BOOLEAN);

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

    // Attack actions

//    private static final EntityAction VELOCI_ATTACK_1_ACTION = new EntityAction(0, (e) -> {}, 1);
//
//    private static final StateHelper VELOCI_ATTACK_1_STATE =
//            StateHelper.Builder.state(ATTACK_1_AC, "velociraptor_attack_1")
//                    .playTime(60)
//                    .stopTime(150)
//                    .affectsAI(true)
//                    .affectedFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK))
//                    .entityAction(VELOCI_ATTACK_1_ACTION)
//                    .build();

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

    @Override
    public boolean getAction() {
        return false;
    }

    @Override
    public void setAction(boolean action) {}

//    public List<WeightedRandomList<WeightedSerializableMeleeAttackHelper>> getAttack() {
//        return WeightedRandomList.create(
//            new WeightedSerializableMeleeAttackHelper(10,
//            SerializableRandomMeleeAttackHelper.Builder.state(ATTACK_1_AC, "velociraptor_attack_1")
//                .meleeEntityAction(new MeleeEntityAction(8,1,
//                    new LargeHitBoxAttackWithTargetCheck(1.0F, 1.0F, 1.0F, 30, 30, false, true))).build()
//            )
//        );
//    }

    public VelociraptorEntity(EntityType<? extends PrehistoricEntity> entityType, Level level) {
        super(entityType, level);
        ((GroundPathNavigation) this.getNavigation()).setCanOpenDoors(true);
        this.setMaxUpStep(1.0F);
        this.refreshDimensions();
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
            .add(Attributes.MAX_HEALTH, 16.0D)
            .add(Attributes.MOVEMENT_SPEED, 0.2D)
            .add(Attributes.ATTACK_DAMAGE, 5.0D);
    }

    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PounceGoal(this, 0));
        this.goalSelector.addGoal(4, new PushButtonsGoal(this, 0.5F, 5, 2));
        this.goalSelector.addGoal(3, new BabyPanicGoal(this, 2.0D));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 1.0F, 30) {
                    @Override
                    public boolean canUse() {
                        if (this.mob.isVehicle()) {
                            return false;
                        } else {
                            if (!this.forceTrigger) {
                                if (this.mob.getNoActionTime() >= 100) {
                                    return false;
                                }
                                if (((VelociraptorEntity) this.mob).isHungry()) {
                                    if (this.mob.getRandom().nextInt(60) != 0) {
                                        return false;
                                    }
                                } else {
                                    if (this.mob.getRandom().nextInt(30) != 0) {
                                        return false;
                                    }
                                }
                            }

                            Vec3 vec3d = this.getPosition();
                            if (vec3d == null) {
                                return false;
                            } else {
                                this.wantedX = vec3d.x;
                                this.wantedY = vec3d.y;
                                this.wantedZ = vec3d.z;
                                this.forceTrigger = false;
                                return true;
                            }
                        }
                    }
                }
        );
        this.goalSelector.addGoal(5, new FollowParentGoal(this, 1.1D));
        this.targetSelector.addGoal(8, (new HurtByTargetGoal(this)));
        this.goalSelector.addGoal(3, new OpenDoorGoal(this, true));
//        this.goalSelector.addGoal(1, new VelociraptorEntity.MeleeAttackGoal());
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
        if (this.getMoveControl().hasWanted() && !this.isBaby()) {
            this.setSprinting(this.getMoveControl().getSpeedModifier() >= 1.25D);
        } else {
            this.setSprinting(false);
        }
        super.customServerAiStep();
    }

    @Override
    public boolean doHurtTarget(Entity entityIn) {
        this.level().broadcastEntityEvent(this, (byte) 4);
        return super.doHurtTarget(entityIn);
    }

//    class MeleeAttackGoal extends SerializableRandomMeleeAttackGoal {
//        public MeleeAttackGoal() {
//            super(VelociraptorEntity.this, getAttack(), 1.75F, false, 2.0F);
//        }
//    }

//    class MeleeAttackGoal extends DelayedAttackGoal {
//
//        public MeleeAttackGoal() {
//            super(VelociraptorEntity.this, 1.75D, false);
//        }
//
//        protected void tickAttack () {
//
//            triggerAnim("blend", "bite");
//
//            animTime++;
//
//            if (animTime <= 3) {
//                this.mob.lookAt(Objects.requireNonNull(this.mob.getTarget()), 100000, 100000);
//                this.mob.yBodyRot = this.mob.yHeadRot;
//            }
//
//            if(animTime==5) {
//                preformAttack();
//            }
//
//            if(animTime>=8) {
//                animTime=0;
//                this.mob.setAnimationState(0);
//                this.resetAttackCooldown();
//                this.ticksUntilNextPathRecalculation = 0;
//            }
//        }
//
//        protected void preformAttack () {
//            Vec3 pos = mob.position();
//            this.mob.playSound(UPSounds.PACHY_HEADBUTT.get(), 1.0F, this.mob.getVoicePitch());
//            HitboxAttacks.largeAttackWithTargetCheck(this.mob.damageSources().mobAttack(mob), (float) Objects.requireNonNull(mob.getAttribute(Attributes.ATTACK_DAMAGE)).getValue(), 0.15f, mob, pos,  5.5F, -Math.PI/2, Math.PI/2, -1.0f, 3.0f, false);
//        }
//    }

    @Override
    public boolean isAlliedTo(Entity pEntity) {
        return pEntity.is(this);
    }

    protected SoundEvent getAmbientSound() {
        return UPSounds.RAPTOR_IDLE.get();
    }

    protected SoundEvent getHurtSound(@NotNull DamageSource damageSourceIn) {
        return UPSounds.RAPTOR_HURT.get();
    }

    protected SoundEvent getDeathSound() {
        return UPSounds.RAPTOR_DEATH.get();
    }

    protected void playStepSound(@NotNull BlockPos p_28301_, @NotNull BlockState p_28302_) {
        this.playSound(SoundEvents.CHICKEN_STEP, 0.15F, 1.0F);
    }

    @Override
    protected SoundEvent getAttackSound() {
        return UPSounds.RAPTOR_ATTACK.get();
    }

    @Override
    protected int getKillHealAmount() {
        return 4;
    }

    @Override
    protected boolean canGetHungry() {
        return true;
    }

    @Override
    protected boolean hasTargets() {
        return true;
    }

    @Override
    protected boolean hasAvoidEntity() {
        return true;
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
        return UPTags.RAPTOR_TARGETS;
    }

    @Override
    public void setCustomName(@Nullable Component pName) {
        super.setCustomName(pName);
    }

    @Override
    protected float getJumpPower() {
        return 1.25F;
    }

    @Override
    public ResourceLocation getVariantTexture() {
        return null;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("Press", this.hasPressed());
        compound.putInt("scale", this.getModelScale());
        compound.putInt("Variant", this.getVariant());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setPress(compound.getBoolean("Press"));
        this.setScale(Math.min(compound.getInt("scale"), 0));
        this.setVariant(compound.getInt("Variant"));
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
        this.entityData.define(SCALE, 0);
        this.entityData.define(VARIANT, 0);
        this.entityData.define(HAS_TARGET, false);
    }

    public void onSyncedDataUpdated(@NotNull EntityDataAccessor<?> pKey) {
        if (SCALE.equals(pKey)) {
            this.refreshDimensions();
        }

        super.onSyncedDataUpdated(pKey);
    }

    public @NotNull EntityDimensions getDimensions(@NotNull Pose pPose) {
        return super.getDimensions(pPose).scale(getScale(this.getModelScale()));
    }

    private static float getScale(int scale) {
        if (scale == 1) {
            return 1.8F;
        }
        return 0.9F;
    }

    public int getModelScale() {
        return this.entityData.get(SCALE);
    }

    public void setScale(int scale) {
        this.entityData.set(SCALE, scale);
    }

    public void setPress(boolean eepy) {
        this.entityData.set(PRESS, eepy);
    }

    public boolean hasPressed() {
        return this.entityData.get(PRESS);
    }

    private static class PushButtonsGoal extends MoveToBlockGoal {
        private final VelociraptorEntity raptor;

        private static final int COOLDOWN = 5000;
        protected int ticksWaited;

        private static int cooldownTicks = COOLDOWN;

        public PushButtonsGoal(VelociraptorEntity pMob, double pSpeedModifier, int pSearchRange, int pVerticalSearchRange) {
            super(pMob, pSpeedModifier, pSearchRange, pVerticalSearchRange);
            this.raptor = pMob;
        }

        public double getDesiredSquaredDistanceToTarget() {
            return 1.5D;
        }

        public boolean canUse() {
            return cooldownTicks-- <= 0 && super.canUse();
        }

        public boolean canContinueToUse() {
            return super.canContinueToUse();
        }

        public void tick() {
            if (this.isReachedTarget()) {
                if (this.ticksWaited >= 40) {
                    this.onReachedTarget();
                    if (raptor.random.nextFloat() <= 0.05F) {
                        if (raptor.random.nextFloat() < 0.1F) {
                            raptor.spawnAtLocation(UPItems.RAPTOR_FEATHERS.get());
                        }
                    }
                } else {
                    ++this.ticksWaited;
                }
            } else if (!this.isReachedTarget() && raptor.random.nextFloat() < 0.05F) {
                raptor.playSound(UPSounds.RAPTOR_SEARCH.get(), 0.1F, 1.0F);
            }

            super.tick();
        }

        protected void onReachedTarget() {
            if (ForgeEventFactory.getMobGriefingEvent(raptor.level(), raptor)) {
                BlockState blockstate = raptor.level().getBlockState(this.blockPos);
                if (blockstate.is(UPBlocks.AMBER_BUTTON.get())) {
                    this.pushButton(blockstate);
                    this.stop();
                }

            }
        }

        @Override
        public void stop() {
            super.stop();
            cooldownTicks = COOLDOWN;
        }

        @Override
        protected @NotNull BlockPos getMoveToTarget() {
            return this.blockPos;
        }

        @Override
        protected boolean isValidTarget(LevelReader world, @NotNull BlockPos pos) {
            BlockState blockState = world.getBlockState(pos);
            return (blockState.is(UPBlocks.AMBER_BUTTON.get()));
        }

        private void pushButton(BlockState p_148929_) {
            ((VelociraptorEntity) this.mob).pushingState = true;
            this.nextStartTick = this.nextStartTick(this.mob);
            BlockState state = this.mob.level().getBlockState(this.blockPos);
            ((ButtonBlock) state.getBlock()).use(state, this.mob.level(), this.blockPos, null, null, null);
        }

        public void start() {
            this.ticksWaited = 0;
            super.start();
        }
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

    private void soundListener(SoundKeyframeEvent<VelociraptorEntity> event) {
        VelociraptorEntity velociraptor = event.getAnimatable();
        if (velociraptor.level().isClientSide) {}
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
                    .triggerableAnim("chatter", VELOCI_CHATTER)
                    .triggerableAnim("bite", VELOCI_ATTACK_1)
                ;
        blend.setSoundKeyframeHandler(this::soundListener);
        controllers.add(blend);

        AnimationController<VelociraptorEntity> flap = new AnimationController<>(this, "flapController", 5, this::flapPredicate);
        controllers.add(flap);
    }

    protected <E extends VelociraptorEntity> PlayState predicate(final AnimationState<E> event) {

        if (this.isFromBook()) {
            return event.setAndContinue(VELOCI_IDLE);
        }

        int animState = this.getAnimationState();

//        if (animState == 1) {
//            triggerAnim("blend", "bite");
//            return PlayState.CONTINUE;
//        } else if (animState == 2) {
//            return event.setAndContinue(VELOCI_ATTACK_2);
//        }

        if (this.isInWater()) {
            event.setAndContinue(VELOCI_SWIM);
            event.getController().setAnimationSpeed(1.0D);
            return PlayState.CONTINUE;
        }
        else if(this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6 && !this.isSwimming() && !this.isInWater()){
            if(this.isSprinting() && !this.isBaby()) {
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
            if (getBooleanState(IDLE_1_AC) && !this.isAsleep()) {
                if (this.isStillEnough()) {
                    triggerAnim("blend", "lookout_1");
                    return event.setAndContinue(VELOCI_IDLE);
                } else {
                    triggerAnim("blend", "lookout_1");
                    return PlayState.CONTINUE;
                }
            }
            if (getBooleanState(IDLE_2_AC) && !this.isAsleep()) {
                if (this.isStillEnough()) {
                    triggerAnim("blend", "lookout_2");
                    return event.setAndContinue(VELOCI_IDLE);
                } else {
                    triggerAnim("blend", "lookout_2");
                    return PlayState.CONTINUE;
                }
            }
            if (getBooleanState(IDLE_3_AC) && !this.isAsleep()) {
                if (this.isStillEnough()) {
                    triggerAnim("blend", "scratch_1");
                    return event.setAndContinue(VELOCI_IDLE);
                } else {
                    triggerAnim("blend", "scratch_1");
                    return PlayState.CONTINUE;
                }
            }
            if (getBooleanState(IDLE_4_AC) && !this.isAsleep()) {
                if (this.isStillEnough()) {
                    triggerAnim("blend", "scratch_2");
                    return event.setAndContinue(VELOCI_IDLE);
                } else {
                    triggerAnim("blend", "scratch_2");
                    return PlayState.CONTINUE;
                }
            }
            if (getBooleanState(IDLE_5_AC) && !this.isAsleep()) {
                if (this.isStillEnough()) {
                    triggerAnim("blend", "chatter");
                    return event.setAndContinue(VELOCI_IDLE);
                } else {
                    triggerAnim("blend", "chatter");
                    return PlayState.CONTINUE;
                }
            }
            if (getBooleanState(IDLE_6_AC) && !this.isAsleep()) {
                return event.setAndContinue(VELOCI_PREEN_1);
            }
            if (getBooleanState(IDLE_7_AC) && !this.isAsleep()) {
                return event.setAndContinue(VELOCI_PREEN_2);
            }
            return event.setAndContinue(VELOCI_IDLE);

        }
        return PlayState.CONTINUE;
    }

    protected <E extends VelociraptorEntity> PlayState flapPredicate(final AnimationState<E> event) {
        if (!this.onGround() && !this.isInWater()) {
            event.getController().setAnimation(VELOCI_JUMP);
            event.getController().setAnimationSpeed(1.0D);
            return PlayState.CONTINUE;
        }
        event.getController().forceAnimationReset();

        return PlayState.STOP;
    }

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

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficultyIn, MobSpawnType reason, @Nullable SpawnGroupData spawnDataIn, @Nullable CompoundTag dataTag) {
        spawnDataIn = super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
        int variantChange = this.random.nextInt(0, 100);
        this.determineVariant(variantChange);
        return super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    public boolean shouldFollow() {
        return false;
    }

}
