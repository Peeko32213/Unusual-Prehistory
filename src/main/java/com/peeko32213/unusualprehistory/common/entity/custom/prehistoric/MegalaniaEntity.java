package com.peeko32213.unusualprehistory.common.entity.custom.prehistoric;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.peeko32213.unusualprehistory.common.entity.animation.state.EntityAction;
import com.peeko32213.unusualprehistory.common.entity.animation.state.RandomStateGoal;
import com.peeko32213.unusualprehistory.common.entity.animation.state.StateHelper;
import com.peeko32213.unusualprehistory.common.entity.animation.state.WeightedState;
import com.peeko32213.unusualprehistory.common.entity.custom.ai.goal.BabyPanicGoal;
import com.peeko32213.unusualprehistory.common.entity.custom.base.PrehistoricEntity;
import com.peeko32213.unusualprehistory.common.entity.util.navigator.SmartBodyHelper;
import com.peeko32213.unusualprehistory.common.entity.util.navigator.SmoothGroundNavigation;
import com.peeko32213.unusualprehistory.core.registry.UPEffects;
import com.peeko32213.unusualprehistory.core.registry.entities.UPEntities;
import com.peeko32213.unusualprehistory.core.registry.UPSounds;
import com.peeko32213.unusualprehistory.core.other.tags.UPEntityTypeTags;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.BiomeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.Tags;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

import java.util.EnumSet;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public class MegalaniaEntity extends PrehistoricEntity {

    private static final EntityDataAccessor<Boolean> ASLEEP = SynchedEntityData.defineId(MegalaniaEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> AGGRO = SynchedEntityData.defineId(MegalaniaEntity.class, EntityDataSerializers.BOOLEAN);

    private float sleepProgress = 0.0F;
    private float prevSleepProgress = 0.0F;
    private int stunnedTick;
    private boolean whipAttack = false;

    // Body control / navigation
    @Override
    protected @NotNull BodyRotationControl createBodyControl() {
        return new SmartBodyHelper(this);
    }

    @Override
    protected @NotNull PathNavigation createNavigation(Level levelIn) {
        return new SmoothGroundNavigation(this, levelIn);
    }

    public MegalaniaEntity(EntityType<? extends PrehistoricEntity> entityType, Level level) {
        super(entityType, level);
        this.setMaxUpStep(1.25F);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
            .add(Attributes.MAX_HEALTH, 60.0D)
            .add(Attributes.MOVEMENT_SPEED, 0.16D)
            .add(Attributes.ATTACK_DAMAGE, 7.0D)
            .add(Attributes.KNOCKBACK_RESISTANCE, 0.25D)
            .add(Attributes.FOLLOW_RANGE, 16.0D);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new RandomStateGoal<>(this));
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new BabyPanicGoal(this, 1.25D));
        this.goalSelector.addGoal(2, new MegalaniaAttackGoal());
        this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 1.0D, 50));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, LivingEntity.class, 100, true, false, entity -> entity.getType().is(UPEntityTypeTags.MEGALANIA_TARGETS)));
        this.targetSelector.addGoal(6, new NearestAttackableTargetGoal<>(this, Player.class, 100, true, false, this::isAngryAt));
        this.targetSelector.addGoal(3, (new HurtByTargetGoal(this)));
    }

    protected float getWaterSlowDown() {
        return 0.98F;
    }

    public boolean isAngryAt(LivingEntity entity) {
        return this.canAttack(entity);
    }

    @Override
    public boolean canAttack(LivingEntity entity) {
        boolean prev = super.canAttack(entity);
        if (prev && isBaby() || this.isAsleep()){
            return false;
        }
        if ( entity.is(this)) {
            return false;
        }
        return prev;
    }

    public void travel(Vec3 vec3d) {
        if (this.isAsleep()) {
            if (this.getNavigation().getPath() != null) {
                this.getNavigation().stop();
            }
            this.getLookControl().setLookAt(this.position().add(2,0,2));
            vec3d = Vec3.ZERO;
        }
        super.travel(vec3d);
    }

    public boolean hurt(DamageSource source, float amount) {
        this.setAsleep(false);
        return super.hurt(source, amount);
    }

    public boolean doHurtTarget(Entity entityIn) {
        if (super.doHurtTarget(entityIn)) {
            if (entityIn instanceof LivingEntity) {
                int i = 5;
                if (this.level().getDifficulty() == Difficulty.NORMAL) {
                    i = 10;
                } else if (this.level().getDifficulty() == Difficulty.HARD) {
                    i = 20;
                }
                if (!this.whipAttack) {
                    ((LivingEntity) entityIn).addEffect(new MobEffectInstance(UPEffects.DRAINING_VENOM.get(), i * 20, 0));
                }
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
        ItemStack itemStack = pPlayer.getItemInHand(pHand);
        if(pHand != InteractionHand.MAIN_HAND) return InteractionResult.FAIL;
        if(itemStack.is(Tags.Items.TOOLS)) {
            CompoundTag compoundTag = itemStack.getTag();
            compoundTag.putInt("megalania_damage", 30);
            itemStack.setTag(compoundTag);
        }
        return super.mobInteract(pPlayer, pHand);
    }

    public boolean isHotBiome() {
        if (this.level().dimension() == Level.NETHER) {
            return true;
        } else {
            return this.level().getBiome(this.blockPosition()).is(BiomeTags.SNOW_GOLEM_MELTS);
        }
    }

    public boolean isColdBiome() {
        if (this.level().dimension() == Level.NETHER) {
            return false;
        } else {
            int i = Mth.floor(this.getX());
            int j = Mth.floor(this.getY());
            int k = Mth.floor(this.getZ());
            return this.level().getBiome(new BlockPos(i, 0, k)).value().coldEnoughToSnow(new BlockPos(i, j, k));
        }
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel serverLevel, @NotNull AgeableMob ageableMob) {
        MegalaniaEntity megalania = UPEntities.MEGALANIA.get().create(serverLevel);
        megalania.setVariant(this.getVariant());
        return megalania;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ASLEEP, false);
        this.entityData.define(AGGRO, false);
        this.entityData.define(FLICK1, false);
        this.entityData.define(FLICK2, false);
        this.entityData.define(YAWN, false);
        this.entityData.define(TONGUE, false);
        this.entityData.define(ROAR, false);
    }

    public boolean isAsleep() {
        return this.entityData.get(ASLEEP);
    }

    public void setAsleep(boolean isAsleep) {
        this.entityData.set(ASLEEP, isAsleep);
    }

    public boolean isAggro() {
        return this.entityData.get(AGGRO);
    }

    public void setAggro(boolean isAggro) {
        this.entityData.set(AGGRO, isAggro);
    }

    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("Asleep", this.isAsleep());
        compound.putBoolean("Aggro", this.isAggro());
        compound.putInt("StunTick", this.stunnedTick);
    }

    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setAsleep(compound.getBoolean("Asleep"));
        this.setAggro(compound.getBoolean("Aggro"));
        this.stunnedTick = compound.getInt("StunTick");
    }

    @Override
    public void handleEntityEvent(byte pId) {
        if (pId == 39) {
            this.stunnedTick = 60;
        }
        else {
            super.handleEntityEvent(pId);
        }
    }

    private void setColdVariant(){
        this.setVariant(1);
    }

    private void setHotVariant(){
        this.setVariant(2);
    }

    private void setNetherVariant(){
        this.setVariant(3);
    }

    private void setNormalVariant(){
        this.setVariant(0);
    }

    public void tick() {
        super.tick();

        if (this.isAsleep() && sleepProgress < 1.0F) {
            sleepProgress = Math.min(sleepProgress + 0.2F, 1.0F);
            this.stunnedTick = 60;
        }
        if (!this.isAsleep() && sleepProgress > 0.0F) {
            sleepProgress = Math.max(sleepProgress - 0.2F, 0.0F);
        }

        if(this.isAsleep()) {
            this.getLookControl().setLookAt(this.position().add(2,0,2));
        }

        if(!this.level().isClientSide){
            if (this.isHotBiome() && !isInWaterRainOrBubble()) {
                Objects.requireNonNull(this.getAttribute(Attributes.MOVEMENT_SPEED)).setBaseValue(0.2);
                this.setAsleep(false);
            }
            if (this.isColdBiome() && !isInWaterRainOrBubble()) {
                Objects.requireNonNull(this.getAttribute(Attributes.MOVEMENT_SPEED)).setBaseValue(0.13);
                this.setAsleep(true);
                this.stunnedTick = 60;
            }
            else {
                Objects.requireNonNull(this.getAttribute(Attributes.MOVEMENT_SPEED)).setBaseValue(0.16);
            }
        }
    }

    public float getSleepProgress(float partialTick) {
        return prevSleepProgress + (sleepProgress - prevSleepProgress) * partialTick;
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor levelAccessor, DifficultyInstance difficultyInstance, MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag tag) {
        SpawnGroupData data = super.finalizeSpawn(levelAccessor, difficultyInstance, spawnType, spawnGroupData, tag);

        if (levelAccessor.getLevel().dimension() == Level.NETHER) {
            setNetherVariant();
            return data;
        } else if (this.isHotBiome()) {
            setHotVariant();
        } else if (this.isColdBiome()) {
            setColdVariant();
        } else {
            setNormalVariant();
        }
        return data;
    }

    // Sounds
    protected SoundEvent getAmbientSound() { return UPSounds.MEGALANIA_IDLE.get(); }

    protected SoundEvent getHurtSound(@NotNull DamageSource damageSourceIn) {
        return UPSounds.MEGALANIA_HURT.get();
    }

    protected SoundEvent getDeathSound() {
        return UPSounds.MEGALANIA_DEATH.get();
    }

    protected void playStepSound(BlockPos p_28301_, BlockState p_28302_) {
        if(!this.isBaby()) {
            this.playSound(UPSounds.MAJUNGA_STEP.get(), 0.15F, 1.0F);
        }
    }

    @Override
    public float getSoundVolume() {
        if(this.isBaby()){
            return 0.75F;
        }
        else{
            return 1.0F;
        }
    }

    @Override
    public int getMaxHeadYRot() {
        return 20;
    }

    @Override
    public int getMaxHeadXRot() {
        return 20;
    }

    // Goals
    private class MegalaniaAttackGoal extends Goal {
        private int attackTime = 0;

        public MegalaniaAttackGoal() {
            this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        }

        public boolean canUse() {
            LivingEntity target = MegalaniaEntity.this.getTarget();
            return target != null && target.isAlive();
        }

        public void start() {
            MegalaniaEntity.this.setAnimationState(0);
            MegalaniaEntity.this.setRunning(true);
            this.attackTime = 0;
        }

        public void stop() {
            MegalaniaEntity.this.setAnimationState(0);
            MegalaniaEntity.this.setRunning(false);
            MegalaniaEntity.this.setAggro(false);
        }

        public void tick() {
            LivingEntity target = MegalaniaEntity.this.getTarget();
            if (target != null) {
                MegalaniaEntity.this.lookAt(MegalaniaEntity.this.getTarget(), 30F, 30F);
                MegalaniaEntity.this.getLookControl().setLookAt(MegalaniaEntity.this.getTarget(), 30F, 30F);

                double distance = MegalaniaEntity.this.distanceToSqr(target.getX(), target.getY(), target.getZ());
                int animState = MegalaniaEntity.this.getAnimationState();

                switch (animState) {
                    case 21, 22 -> {
                        MegalaniaEntity.this.getNavigation().moveTo(target, 2.0D);
                        tickBiteAttack();
                    }
                    case 23 -> tickWhipAttack();
                    default -> {
                        MegalaniaEntity.this.getNavigation().moveTo(target, 2.25D);
                        this.checkForCloseRangeAttack(distance);
                    }
                }
            }
        }

        protected void checkForCloseRangeAttack (double distance){
            MegalaniaEntity.this.setAggro(distance <= 20);

            int r = random.nextInt(100);
            if (distance <= 7) {
                if (r <= 33) {
                    MegalaniaEntity.this.setAnimationState(21);
                } else if (r <= 66) {
                    MegalaniaEntity.this.setAnimationState(22);
                } else {
                    MegalaniaEntity.this.setAnimationState(23);
                }
            }
        }

        protected void tickBiteAttack() {
            attackTime++;
            if (attackTime == 9) {
                if (MegalaniaEntity.this.distanceTo(MegalaniaEntity.this.getTarget()) < 3.3F) {
                    MegalaniaEntity.this.doHurtTarget(MegalaniaEntity.this.getTarget());
                }
            }
            if (attackTime >= 14) {
                attackTime = 0;
                MegalaniaEntity.this.setAnimationState(0);
            }
        }

        protected void tickWhipAttack() {
            attackTime++;
            MegalaniaEntity.this.whipAttack = true;
            MegalaniaEntity.this.getNavigation().stop();
            MegalaniaEntity.this.setDeltaMovement(0, MegalaniaEntity.this.getDeltaMovement().y, 0);

            if (attackTime == 10) {
                if (MegalaniaEntity.this.distanceTo(MegalaniaEntity.this.getTarget()) < 4.7F) {
                    MegalaniaEntity.this.doHurtTarget(MegalaniaEntity.this.getTarget());
                }
            }
            if (attackTime >= 21) {
                attackTime = 0;
                MegalaniaEntity.this.whipAttack = false;
                MegalaniaEntity.this.setAnimationState(0);
            }
        }
    }

    // Movement animations
    private static final RawAnimation MEGALANIA_WALK = RawAnimation.begin().thenLoop("animation.megalania.walk");
    private static final RawAnimation MEGALANIA_RUN = RawAnimation.begin().thenLoop("animation.megalania.run");
    private static final RawAnimation MEGALANIA_SWIM = RawAnimation.begin().thenLoop("animation.megalania.swim");

    // Idle animations
    private static final RawAnimation MEGALANIA_IDLE = RawAnimation.begin().thenLoop("animation.megalania.idle");
    private static final RawAnimation MEGALANIA_SLEEP = RawAnimation.begin().thenLoop("animation.megalania.sleep");
    private static final RawAnimation MEGALANIA_FLICK1 = RawAnimation.begin().thenPlay("animation.megalania.flick_blend1");
    private static final RawAnimation MEGALANIA_FLICK2 = RawAnimation.begin().thenPlay("animation.megalania.flick_blend2");
    private static final RawAnimation MEGALANIA_YAWN = RawAnimation.begin().thenPlay("animation.megalania.yawn_blend");
    private static final RawAnimation MEGALANIA_TONGUE = RawAnimation.begin().thenPlay("animation.megalania.tongue_blend");
    private static final RawAnimation MEGALANIA_ROAR = RawAnimation.begin().thenPlay("animation.megalania.roar");

    // Attack animations
    private static final RawAnimation MEGALANIA_BITE1 = RawAnimation.begin().thenPlay("animation.megalania.bite_blend1");
    private static final RawAnimation MEGALANIA_BITE2 = RawAnimation.begin().thenPlay("animation.megalania.bite_blend2");
    private static final RawAnimation MEGALANIA_TAIL_WHIP = RawAnimation.begin().thenPlay("animation.megalania.tailwhip");

    // Misc animations
    private static final RawAnimation MEGALANIA_AGGRO = RawAnimation.begin().thenLoop("animation.megalania.aggro_blend");
    private static final RawAnimation MEGALANIA_LEAP = RawAnimation.begin().thenLoop("animation.megalania.leap");

    // Idle accessors
    private static final EntityDataAccessor<Boolean> FLICK1 = SynchedEntityData.defineId(MegalaniaEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> FLICK2 = SynchedEntityData.defineId(MegalaniaEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> YAWN = SynchedEntityData.defineId(MegalaniaEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> TONGUE = SynchedEntityData.defineId(MegalaniaEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> ROAR = SynchedEntityData.defineId(MegalaniaEntity.class, EntityDataSerializers.BOOLEAN);

    // Starting predicates
    private static final Predicate<LivingEntity> MEGALANIA_IDLE_PREDICATE = (e -> {
        if(e instanceof MegalaniaEntity entity) {
            return !entity.isRunning() && !entity.isSprinting() && !entity.isInWater() && entity.onGround();
        }
        return false;
    });

    // Idle actions
    private static final EntityAction MEGALANIA_FLICK1_ACTION = new EntityAction(0, (e) -> {}, 1);
    private static final StateHelper MEGALANIA_FLICK1_STATE =
            StateHelper.Builder.state(FLICK1, "megalania_flick1")
                    .playTime(20)
                    .stopTime(140)
                    .startingPredicate(MEGALANIA_IDLE_PREDICATE)
                    .entityAction(MEGALANIA_FLICK1_ACTION)
                    .build();

    private static final EntityAction MEGALANIA_FLICK2_ACTION = new EntityAction(0, (e) -> {}, 1);
    private static final StateHelper MEGALANIA_FLICK2_STATE =
            StateHelper.Builder.state(FLICK2, "megalania_flick2")
                    .playTime(20)
                    .stopTime(140)
                    .startingPredicate(MEGALANIA_IDLE_PREDICATE)
                    .entityAction(MEGALANIA_FLICK2_ACTION)
                    .build();

    private static final EntityAction MEGALANIA_YAWN_ACTION = new EntityAction(0, (e) -> {}, 1);
    private static final StateHelper MEGALANIA_YAWN_STATE =
            StateHelper.Builder.state(YAWN, "megalania_yawn")
                    .playTime(80)
                    .stopTime(250)
                    .startingPredicate(MEGALANIA_IDLE_PREDICATE)
                    .entityAction(MEGALANIA_YAWN_ACTION)
                    .build();

    private static final EntityAction MEGALANIA_TONGUE_ACTION = new EntityAction(0, (e) -> {}, 1);
    private static final StateHelper MEGALANIA_TONGUE_STATE =
            StateHelper.Builder.state(TONGUE, "megalania_tongue")
                    .playTime(20)
                    .stopTime(80)
                    .startingPredicate(MEGALANIA_IDLE_PREDICATE)
                    .entityAction(MEGALANIA_TONGUE_ACTION)
                    .build();

    private static final EntityAction MEGALANIA_ROAR_ACTION = new EntityAction(0, (e) -> {}, 1);
    private static final StateHelper MEGALANIA_ROAR_STATE =
            StateHelper.Builder.state(ROAR, "megalania_roar")
                    .playTime(80)
                    .stopTime(300)
                    .startingPredicate(MEGALANIA_IDLE_PREDICATE)
                    .affectsAI(true)
                    .affectedFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK))
                    .entityAction(MEGALANIA_ROAR_ACTION)
                    .build();

    @Override
    public ImmutableMap<String, StateHelper> getStates() {
        return ImmutableMap.of(
                MEGALANIA_FLICK1_STATE.getName(), MEGALANIA_FLICK1_STATE,
                MEGALANIA_FLICK2_STATE.getName(), MEGALANIA_FLICK2_STATE,
                MEGALANIA_YAWN_STATE.getName(), MEGALANIA_YAWN_STATE,
                MEGALANIA_TONGUE_STATE.getName(), MEGALANIA_TONGUE_STATE,
                MEGALANIA_ROAR_STATE.getName(), MEGALANIA_ROAR_STATE
        );
    }

    @Override
    public List<WeightedState<StateHelper>> getWeightedStatesToPerform() {
        return ImmutableList.of(
                WeightedState.of(MEGALANIA_FLICK1_STATE, 7),
                WeightedState.of(MEGALANIA_FLICK2_STATE, 7),
                WeightedState.of(MEGALANIA_YAWN_STATE, 8),
                WeightedState.of(MEGALANIA_TONGUE_STATE, 11),
                WeightedState.of(MEGALANIA_ROAR_STATE, 6)
        );
    }

    // Animation control
    @Override
    public void registerControllers(final AnimatableManager.ControllerRegistrar controllers) {
        AnimationController<MegalaniaEntity> controller = new AnimationController<>(this, "controller", 5, this::predicate);
        controllers.add(controller);

        AnimationController<MegalaniaEntity> idle = new AnimationController<>(this, "idleController", 5, this::idlePredicate);
        controllers.add(idle);

        AnimationController<MegalaniaEntity> attack = new AnimationController<>(this, "attackController", 5, this::attackPredicate);
        controllers.add(attack);
    }

    protected <E extends MegalaniaEntity> PlayState predicate(final AnimationState<E> event) {
        if (!(this.getAnimationState() == 23)) {
            if (this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6 && !this.isAsleep() && !this.isInWater()) {
                if (this.isRunning()) {
                    event.setAndContinue(MEGALANIA_RUN);
                } else {
                    event.setAndContinue(MEGALANIA_WALK);
                }
                return PlayState.CONTINUE;
            }
            if (this.isInWater()) {
                event.setAndContinue(MEGALANIA_SWIM);
                return PlayState.CONTINUE;
            }
            if (isAsleep() && !this.isInWater()) {
                event.setAndContinue(MEGALANIA_SLEEP);
                return PlayState.CONTINUE;

            }
            if (!this.isInWater()) {
                event.setAndContinue(MEGALANIA_IDLE);
                return PlayState.CONTINUE;
            }
            if (getBooleanState(ROAR) && !this.isInWater()) {
                event.getController().setAnimation(MEGALANIA_ROAR);
                return PlayState.CONTINUE;
            }
        }
        return PlayState.CONTINUE;
    }

    // Idle animations
    protected <E extends MegalaniaEntity> PlayState idlePredicate(final AnimationState<E> event) {
        if (!this.isRunning() || !this.onGround()) {
            if (getBooleanState(FLICK1)) {
                event.getController().setAnimation(MEGALANIA_FLICK1);
                return PlayState.CONTINUE;
            }
            if (getBooleanState(FLICK2)) {
                event.getController().setAnimation(MEGALANIA_FLICK2);
                return PlayState.CONTINUE;
            }
            if (getBooleanState(YAWN)) {
                event.getController().setAnimation(MEGALANIA_YAWN);
                return PlayState.CONTINUE;
            }
            if (getBooleanState(TONGUE)) {
                event.getController().setAnimation(MEGALANIA_TONGUE);
                return PlayState.CONTINUE;
            }
        }
        event.getController().forceAnimationReset();
        return PlayState.STOP;
    }

    // Attack animations
    protected <E extends MegalaniaEntity> PlayState attackPredicate(final AnimationState<E> event) {
        int animState = this.getAnimationState();
        if (animState == 21) {
            event.setAndContinue(MEGALANIA_BITE1);
            return PlayState.CONTINUE;
        }
        else if (animState == 22) {
            event.setAndContinue(MEGALANIA_BITE2);
            return PlayState.CONTINUE;
        }
        else if (animState == 23) {
            event.setAndContinue(MEGALANIA_TAIL_WHIP);
            return PlayState.CONTINUE;
        }
        else if (this.isAggro()) {
            event.setAndContinue(MEGALANIA_AGGRO);
            return PlayState.CONTINUE;
        }
        else if (animState == 0) {
            event.getController().forceAnimationReset();
            return PlayState.STOP;
        }
        else return PlayState.CONTINUE;
    }
}
