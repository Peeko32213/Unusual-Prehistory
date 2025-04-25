package com.peeko32213.unusualprehistory.common.entity.custom.prehistoric;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.peeko32213.unusualprehistory.common.entity.animation.state.EntityAction;
import com.peeko32213.unusualprehistory.common.entity.animation.state.RandomStateGoal;
import com.peeko32213.unusualprehistory.common.entity.animation.state.StateHelper;
import com.peeko32213.unusualprehistory.common.entity.animation.state.WeightedState;
import com.peeko32213.unusualprehistory.common.entity.custom.ai.goal.ulughbegsaurus.UlughbegsaurusAttackGoal;
import com.peeko32213.unusualprehistory.common.entity.custom.base.PrehistoricEntity;
import com.peeko32213.unusualprehistory.common.entity.custom.ai.goal.BabyPanicGoal;
import com.peeko32213.unusualprehistory.common.entity.custom.ai.goal.PrehistoricFollowOwnerGoal;
import com.peeko32213.unusualprehistory.common.entity.util.interfaces.ICustomFollower;
import com.peeko32213.unusualprehistory.common.entity.util.kinematics.IKSolver;
import com.peeko32213.unusualprehistory.common.entity.util.navigator.SmartBodyHelper;
import com.peeko32213.unusualprehistory.common.entity.util.navigator.SmoothGroundNavigation;
import com.peeko32213.unusualprehistory.core.other.tags.UPItemTags;
import com.peeko32213.unusualprehistory.core.registry.entities.UPEntities;
import com.peeko32213.unusualprehistory.core.registry.UPSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.keyframe.event.SoundKeyframeEvent;
import software.bernie.geckolib.core.object.PlayState;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public class UlughbegsaurusEntity extends PrehistoricEntity implements ICustomFollower {

    public IKSolver TailKinematics;

    private static final EntityDataAccessor<Integer> EATING_TIME = SynchedEntityData.defineId(UlughbegsaurusEntity.class, EntityDataSerializers.INT);
    public static final Logger LOGGER = LogManager.getLogger();

    public float eatProgress;

    // Movement animations
    private static final RawAnimation ULUGH_WALK = RawAnimation.begin().thenLoop("animation.ulughbegsaurus.walk");
    private static final RawAnimation ULUGH_SPRINT = RawAnimation.begin().thenLoop("animation.ulughbegsaurus.run");
    private static final RawAnimation ULUGH_SWIM = RawAnimation.begin().thenLoop("animation.ulughbegsaurus.swim");

    // Idle animations
    private static final RawAnimation ULUGH_IDLE = RawAnimation.begin().thenLoop("animation.ulughbegsaurus.idle");
    private static final RawAnimation ULUGH_YAWN = RawAnimation.begin().thenPlay("animation.ulughbegsaurus.yawn_blend");
    private static final RawAnimation ULUGH_SHAKE = RawAnimation.begin().thenPlay("animation.ulughbegsaurus.shake_blend");
    private static final RawAnimation ULUGH_VOCAL = RawAnimation.begin().thenPlay("animation.ulughbegsaurus.vocal_blend");
    private static final RawAnimation ULUGH_SIT_START = RawAnimation.begin().thenPlay("animation.ulughbegsaurus.sit_start");
    private static final RawAnimation ULUGH_SIT = RawAnimation.begin().thenLoop("animation.ulughbegsaurus.sit");
    private static final RawAnimation ULUGH_SIT_END = RawAnimation.begin().thenPlay("animation.ulughbegsaurus.sit_end");
    private static final RawAnimation ULUGH_SLEEP = RawAnimation.begin().thenLoop("animation.ulughbegsaurus.sleep");

    // Attack animations
    private static final RawAnimation ULUGH_BITE1 = RawAnimation.begin().thenPlay("animation.ulughbegsaurus.attack_blend1");
    private static final RawAnimation ULUGH_BITE2 = RawAnimation.begin().thenPlay("animation.ulughbegsaurus.attack_blend2");

    // Misc animations
    private static final RawAnimation ULUGH_EAT = RawAnimation.begin().thenPlay("animation.ulughbegsaurus.eat_bend");

    // Idle accessors
    private static final EntityDataAccessor<Boolean> YAWN = SynchedEntityData.defineId(UlughbegsaurusEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> SHAKE = SynchedEntityData.defineId(UlughbegsaurusEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> VOCAL = SynchedEntityData.defineId(UlughbegsaurusEntity.class, EntityDataSerializers.BOOLEAN);

    // Starting predicates
    private static final Predicate<LivingEntity> ULUGHBEGSAURUS_STARTING_PREDICATE = (e -> {
        if(e instanceof UlughbegsaurusEntity entity) {
            return !entity.getMoveControl().hasWanted() && !entity.isSprinting() && !entity.isInWater() && !entity.isRunning();
        }
        return false;
    });

    // Idle actions
    private static final EntityAction ULUGH_YAWN_ACTION = new EntityAction(0, (e) -> {}, 1);

    private static final StateHelper ULUGH_YAWN_STATE =
            StateHelper.Builder.state(YAWN, "ulughbegsaurus_yawn")
                    .playTime(60)
                    .stopTime(120)
                    .startingPredicate(ULUGHBEGSAURUS_STARTING_PREDICATE)
                    .entityAction(ULUGH_YAWN_ACTION)
                    .build();

    private static final EntityAction ULUGH_SHAKE_ACTION = new EntityAction(0, (e) -> {}, 1);

    private static final StateHelper ULUGH_SHAKE_STATE =
            StateHelper.Builder.state(SHAKE, "ulughbegsaurus_shake")
                    .playTime(80)
                    .stopTime(150)
                    .startingPredicate(ULUGHBEGSAURUS_STARTING_PREDICATE)
                    .entityAction(ULUGH_SHAKE_ACTION)
                    .build();

    private static final EntityAction ULUGH_VOCAL_ACTION = new EntityAction(0, (e) -> {}, 1);

    private static final StateHelper ULUGH_VOCAL_STATE =
            StateHelper.Builder.state(VOCAL, "ulughbegsaurus_vocal")
                    .playTime(60)
                    .stopTime(140)
                    .startingPredicate(ULUGHBEGSAURUS_STARTING_PREDICATE)
                    .entityAction(ULUGH_VOCAL_ACTION)
                    .build();

    @Override
    public ImmutableMap<String, StateHelper> getStates() {
        return ImmutableMap.of(
                ULUGH_YAWN_STATE.getName(), ULUGH_YAWN_STATE,
                ULUGH_SHAKE_STATE.getName(), ULUGH_SHAKE_STATE,
                ULUGH_VOCAL_STATE.getName(), ULUGH_VOCAL_STATE
        );
    }

    @Override
    public List<WeightedState<StateHelper>> getWeightedStatesToPerform() {
        return ImmutableList.of(
                WeightedState.of(ULUGH_YAWN_STATE, 14),
                WeightedState.of(ULUGH_SHAKE_STATE, 12),
                WeightedState.of(ULUGH_VOCAL_STATE, 11)
        );
    }

    @Override
    protected @NotNull BodyRotationControl createBodyControl() {
        SmartBodyHelper helper = new SmartBodyHelper(this);
        helper.bodyLagMoving = 0.5F;
        helper.bodyLagStill = 0.2F;
        return helper;
    }

    @Override
    protected @NotNull PathNavigation createNavigation(Level levelIn) {
        return new SmoothGroundNavigation(this, levelIn);
    }

    public UlughbegsaurusEntity(EntityType<? extends PrehistoricEntity> entityType, Level level) {
        super(entityType, level);
        this.setMaxUpStep(1.25F);
        this.reassessTameGoals();
        this.TailKinematics = new IKSolver(this, 3, 3);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 60.0D).add(Attributes.MOVEMENT_SPEED, 0.2D).add(Attributes.ATTACK_DAMAGE, 8.0D).add(Attributes.KNOCKBACK_RESISTANCE, 0.2D).add(Attributes.FOLLOW_RANGE, 32.0D);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new RandomStateGoal<>(this));
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(0, new SitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(1, new UlughbegsaurusAttackGoal(this));
        this.goalSelector.addGoal(3, new BabyPanicGoal(this, 2.0D));
        this.goalSelector.addGoal(1, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 1.0F, 30));
        this.goalSelector.addGoal(3, new PrehistoricFollowOwnerGoal(this, 1.2D, 5.0F, 2.0F, false));
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)));
        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
    }

    @Override
    protected float getWaterSlowDown() {
        return 0.94F;
    }

    protected void playStepSound(@NotNull BlockPos p_28301_, @NotNull BlockState p_28302_) {
        this.playSound(UPSounds.MAJUNGA_STEP.get(), 0.2F, 1.15F);
    }

    protected SoundEvent getAmbientSound() {
        return UPSounds.ULUGH_IDLE.get();
    }

    protected SoundEvent getHurtSound(@NotNull DamageSource damageSourceIn) {
        return UPSounds.ULUGH_HURT.get();
    }

    protected SoundEvent getDeathSound() {
        return UPSounds.ULUGH_DEATH.get();
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

    public boolean isFood(ItemStack stack) {
        return stack.is(UPItemTags.ULUGH_FOOD);
    }

    public boolean isEating() {
        return this.getEatingTime() > 0;
    }

    public int getEatingTime() {
        return this.entityData.get(EATING_TIME);
    }

    public void setEatingTime(int shaking) {
        this.entityData.set(EATING_TIME, shaking);
    }

    @Override
    public int getMaxHeadYRot() {
        return 15;
    }

    public @NotNull InteractionResult mobInteract(@Nonnull Player player, @Nonnull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if(hand != InteractionHand.MAIN_HAND) return InteractionResult.FAIL;
        if (isFood(itemstack) && !isTame()) {
            if(!this.level().isClientSide) {
                this.level().broadcastEntityEvent(this, (byte) 7);
                this.tame(player);
                itemstack.shrink(1);
                this.setEatingTime(50 + random.nextInt(30));
            }
            this.playSound(this.getEatingSound(itemstack), 1.0F, 1.0F);
            return InteractionResult.SUCCESS;
        }
        if (isTame() && isOwnedBy(player)) {
            if (this.isFood(itemstack) && this.getHealth() < this.getMaxHealth()) {
                if (!player.getAbilities().instabuild) {
                    itemstack.shrink(1);
                }
                if(!this.level().isClientSide) {
                    this.heal((float) Objects.requireNonNull(itemstack.getFoodProperties(this)).getNutrition());
                }
                this.playSound(this.getEatingSound(itemstack), 1.0F, 1.0F);
                this.level().broadcastEntityEvent(this, (byte) 7);
                this.gameEvent(GameEvent.EAT, this);
                return InteractionResult.SUCCESS;
            }
            else if (!player.isShiftKeyDown() && !this.isBaby() && !this.isInSittingPose() && this.getStandingTime() == 0 && this.getSittingTime() == 0 && !this.isInWater()) {
                player.startRiding(this);
            }
            else {
                this.setCommand((this.getCommand() + 1) % 3);
                if (this.getCommand() == 3) {
                    this.setCommand(0);
                }

                int var10001 = this.getCommand();
                player.displayClientMessage(Component.translatable("entity.unusualprehistory.all.command_" + var10001, new Object[]{this.getName()}), true);
                boolean sit = this.getCommand() == 2;
                if (sit) {
                    this.setOrderedToSit(true);
                    if (!this.isInSittingPose() && this.onGround()){
                        this.setSittingTime(20);
                    }
                } else {
                    if (this.isInSittingPose() && this.onGround()){
                        this.setStandingTime(20);
                    }
                    this.setOrderedToSit(false);
                }
            }
        }
        return InteractionResult.PASS;
    }

    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
    }

    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(YAWN, false);
        this.entityData.define(SHAKE, false);
        this.entityData.define(VOCAL, false);
        this.entityData.define(EATING_TIME, 0);
    }

    @Override
    public boolean canBeLeashed(Player player) {
        return !this.isInSittingPose() && !(this.getSittingTime() > 0 || this.getStandingTime() > 0) && !this.isVehicle();
    }

    @Override
    public boolean isPushable() {
        return !this.isInSittingPose() && !(this.getSittingTime() > 0 || this.getStandingTime() > 0) && !this.isVehicle();
    }

    @Override
    public EntityDimensions getDimensions(Pose pPose) {
        if (this.isInSittingPose()) {
            return super.getDimensions(pPose).scale(1.0F, 0.625F);
        } else {
            return super.getDimensions(pPose);
        }
    }

    protected Vec3 getRiddenInput(Player player, Vec3 deltaIn) {
        if (player.zza != 0) {
            float f = player.zza < 0.0F ? 0.5F : 1.0F;
            return new Vec3(player.xxa * 0.25F, 0.0D, player.zza * 0.5F * f);
        } else {
            this.setSprinting(false);
        }
        return Vec3.ZERO;
    }

    protected void tickRidden(Player player, Vec3 vec3) {
        super.tickRidden(player, vec3);
        if (player.zza != 0 || player.xxa != 0){
            this.setRot(player.getYRot(), player.getXRot() * 0.25F);
            this.yRotO = this.yBodyRot = this.yHeadRot = this.getYRot();
            this.setMaxUpStep(1.25F);
            this.getNavigation().stop();
            this.setTarget(null);
        }
    }

    protected float getRiddenSpeed(Player pPlayer) {
        float f = 0.0F;
        if (pPlayer.isSprinting()) {
            f = 0.225F;
        }
        return (float)this.getAttributeValue(Attributes.MOVEMENT_SPEED) + f;
    }

    // Controlling passenger
    @Nullable
    public LivingEntity getControllingPassenger() {
        for (Entity passenger : this.getPassengers()) {
            if (passenger instanceof Player) {
                Player player = (Player) passenger;
                return player;
            }
        }
        return null;
    }

    @Override
    protected void positionRider(Entity pPassenger, @NotNull MoveFunction pCallback) {
        float ySin = Mth.sin(this.yBodyRot * ((float) Math.PI / 180F));
        float yCos = Mth.cos(this.yBodyRot * ((float) Math.PI / 180F));
        pPassenger.setPos(this.getX() + (double) (-0.05F * ySin), this.getY() + this.getPassengersRidingOffset() + pPassenger.getMyRidingOffset() + (-0.05F), this.getZ() - (double) (-0.05F * yCos));
    }

    public double getPassengersRidingOffset() {
        return 2.3F;
    }

    public void tick() {
        super.tick();

        if (this.isEating() && eatProgress < 5F) {
            eatProgress++;
        }
        if (!this.isEating() && eatProgress > 0F) {
            eatProgress--;
        }
        if (this.isEating()) {
            this.setEatingTime(this.getEatingTime() - 1);
            this.getNavigation().stop();
        }

        // Float while ridden
        boolean ridden = !this.getPassengers().isEmpty();
        boolean water = this.isInWater();
        if(ridden && water) {
            boolean waterBelow = this.level().isWaterAt(this.blockPosition().below());

            if(waterBelow) {
                this.move(MoverType.PLAYER, new Vec3(0, 0.08, 0));
            }
        }

        this.TailKinematics.calculateTailAngles(this);

        if (isRunning() && !hasRunningAttributes) {
            hasRunningAttributes = true;
            this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.34D);
        }
        if (!isRunning() && hasRunningAttributes) {
            hasRunningAttributes = false;
            this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.2D);
        }
    }

    @Override
    public float getStepHeight() {
        return 1.25F;
    }

    public boolean isAlliedTo(@NotNull Entity entityIn) {
        if (this.isTame()) {
            LivingEntity livingentity = this.getOwner();
            if (entityIn == livingentity) {
                return true;
            }
            if (entityIn instanceof TamableAnimal) {
                return ((TamableAnimal) entityIn).isOwnedBy(livingentity);
            }
            if (livingentity != null) {
                return livingentity.isAlliedTo(entityIn);
            }
        }
        return entityIn.is(this);
    }

    public void determineVariant(int variantChange){
        if (variantChange <= 8) {
            this.setVariant(1);
        }
        else if (variantChange <= 16) {
            this.setVariant(2);
        }
        else if (variantChange <= 24) {
            this.setVariant(3);
        }
        else if (variantChange <= 32) {
            this.setVariant(4);
        }
        else if (variantChange <= 40) {
            this.setVariant(5);
        }
        else if (variantChange <= 48) {
            this.setVariant(6);
        }
        else if (variantChange <= 56) {
            this.setVariant(7);
        }
        else if (variantChange <= 64) {
            this.setVariant(8);
        }
        else if (variantChange <= 72) {
            this.setVariant(9);
        }
        else if (variantChange <= 80) {
            this.setVariant(10);
        }
        else if (variantChange <= 98) {
            this.setVariant(0);
        }
        else {
            this.setVariant(11);
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

    public boolean hurt(@NotNull DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        } else {
            Entity entity = source.getEntity();
            this.setOrderedToSit(false);
            if (entity != null && this.isTame() && !(entity instanceof Player) && !(entity instanceof AbstractArrow)) {
                amount = (amount + 1.0F) / 3.0F;
            }
            return super.hurt(source, amount);
        }
    }

    @Override
    public boolean shouldFollow() {
        return this.getCommand() == 1;
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

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel serverLevel, @NotNull AgeableMob ageableMob) {
        UlughbegsaurusEntity ulugh = UPEntities.ULUG.get().create(serverLevel);
        ulugh.setVariant(this.getVariant());
        return ulugh;
    }

    private void soundListener(SoundKeyframeEvent<UlughbegsaurusEntity> event) {
        UlughbegsaurusEntity ulughbegsaurus = event.getAnimatable();
        if (event.getKeyframeData().getSound().equals("ulughbegsaurus_bite")) {
            ulughbegsaurus.level().playLocalSound(ulughbegsaurus.getX(), ulughbegsaurus.getY(), ulughbegsaurus.getZ(), UPSounds.ULUGH_BITE.get(), ulughbegsaurus.getSoundSource(), 1.0F, ulughbegsaurus.getVoicePitch(), false);
        }
    }

    @Override
    public void registerControllers(final AnimatableManager.ControllerRegistrar controllers) {
        AnimationController<UlughbegsaurusEntity> controller = new AnimationController<>(this, "controller", 10, this::predicate);
        controllers.add(controller);

        AnimationController<UlughbegsaurusEntity> idle = new AnimationController<>(this, "idleController", 0, this::idlePredicate);
        idle.setSoundKeyframeHandler(this::soundListener);
        controllers.add(idle);

        AnimationController<UlughbegsaurusEntity> attack = new AnimationController<>(this, "attackController", 5, this::attackPredicate);
        attack.setSoundKeyframeHandler(this::soundListener);
        controllers.add(attack);

        AnimationController<UlughbegsaurusEntity> sit = new AnimationController<>(this, "sitController", 0, this::sitPredicate);
        controllers.add(sit);
    }

    protected <E extends UlughbegsaurusEntity> PlayState predicate(final software.bernie.geckolib.core.animation.AnimationState<E> event) {

        if(this.isFromBook()){
            return event.setAndContinue(ULUGH_IDLE);
        }

        else if(this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6 && !this.isSwimming() && !this.isInWater() && !this.hasControllingPassenger() && !this.isInSittingPose()){
            if(this.isSprinting() || this.isRunning()) {
                event.setAndContinue(ULUGH_SPRINT);
                event.getController().setAnimationSpeed(1.0F);
            } else {
                event.setAndContinue(ULUGH_WALK);
                event.getController().setAnimationSpeed(1.0F);
            }
            return PlayState.CONTINUE;
        }

        else if(this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6 && !this.isSwimming() && !this.isInWater() && this.hasControllingPassenger() && !this.isInSittingPose()){

            if(Objects.requireNonNull(this.getControllingPassenger()).isSprinting()){
                event.setAndContinue(ULUGH_SPRINT);
                event.getController().setAnimationSpeed(1.15F);
            }
            else {
                event.setAndContinue(ULUGH_WALK);
                event.getController().setAnimationSpeed(1.5F);
            }
            return PlayState.CONTINUE;
        }

        if (this.isInWater()) {
            event.setAndContinue(ULUGH_SWIM);
            event.getController().setAnimationSpeed(1.0F);
            return PlayState.CONTINUE;
        }

        if (!this.isInWater()) {
            return event.setAndContinue(ULUGH_IDLE);
        }
        return PlayState.CONTINUE;
    }

    // Idle animations
    protected <E extends UlughbegsaurusEntity> PlayState idlePredicate(final AnimationState<E> event) {
        if (getBooleanState(YAWN)) {
            event.getController().setAnimation(ULUGH_YAWN);
            return PlayState.CONTINUE;
        }
        if (getBooleanState(VOCAL)) {
            event.getController().setAnimation(ULUGH_VOCAL);
            return PlayState.CONTINUE;
        }
        if (getBooleanState(SHAKE)) {
            event.getController().setAnimation(ULUGH_SHAKE);
            return PlayState.CONTINUE;
        }
        event.getController().forceAnimationReset();
        return PlayState.STOP;
    }

    // Attack animations
    protected <E extends UlughbegsaurusEntity> PlayState attackPredicate(final AnimationState<E> event) {
        int animState = this.getAnimationState();

        if (animState == 21) {
            event.setAndContinue(ULUGH_BITE1);
            return PlayState.CONTINUE;
        }
        else if (animState == 22) {
            event.setAndContinue(ULUGH_BITE2);
            return PlayState.CONTINUE;
        }
        else if (animState == 0) {
            event.getController().forceAnimationReset();
            return PlayState.STOP;
        }
        else return PlayState.CONTINUE;
    }

    // Sitting animations
    protected <E extends UlughbegsaurusEntity> PlayState sitPredicate(AnimationState<E> event) {
        if (this.isInSittingPose() || (this.getSittingLag() < 7 && this.getSittingLag() > 0)){
            event.setAndContinue(ULUGH_SIT);
            return PlayState.CONTINUE;
        }
        else if (this.getSittingTime() > 0) {
            event.setAndContinue(ULUGH_SIT_START);
            return PlayState.CONTINUE;
        }
        else if (this.getStandingTime() > 0) {
            event.setAndContinue(ULUGH_SIT_END);
            return PlayState.CONTINUE;
        }
        else {
            event.getController().forceAnimationReset();
            return PlayState.STOP;
        }
    }
}