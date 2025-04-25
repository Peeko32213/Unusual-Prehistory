package com.peeko32213.unusualprehistory.common.entity.custom.prehistoric;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.peeko32213.unusualprehistory.common.entity.animation.state.EntityAction;
import com.peeko32213.unusualprehistory.common.entity.animation.state.RandomStateGoal;
import com.peeko32213.unusualprehistory.common.entity.animation.state.StateHelper;
import com.peeko32213.unusualprehistory.common.entity.animation.state.WeightedState;
import com.peeko32213.unusualprehistory.common.entity.custom.ai.goal.PrehistoricFollowOwnerGoal;
import com.peeko32213.unusualprehistory.common.entity.custom.ai.goal.megatherium.MegatheriumAttackGoal;
import com.peeko32213.unusualprehistory.common.entity.custom.base.PrehistoricEntity;
import com.peeko32213.unusualprehistory.common.entity.util.interfaces.ICustomFollower;
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
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.keyframe.event.SoundKeyframeEvent;
import software.bernie.geckolib.core.object.PlayState;

import java.util.List;
import java.util.function.Predicate;

public class MegatheriumEntity extends PrehistoricEntity implements ICustomFollower {

    // Movement animations
    private static final RawAnimation MEGATHERIUM_WALK = RawAnimation.begin().thenLoop("animation.megatherium.walk");
    private static final RawAnimation MEGATHERIUM_RUN = RawAnimation.begin().thenLoop("animation.megatherium.run");
    private static final RawAnimation MEGATHERIUM_SWIM = RawAnimation.begin().thenLoop("animation.megatherium.swim");

    // Idle animations
    private static final RawAnimation MEGATHERIUM_IDLE = RawAnimation.begin().thenLoop("animation.megatherium.idle");
    private static final RawAnimation MEGATHERIUM_SIT = RawAnimation.begin().thenLoop("animation.megatherium.sit");
    private static final RawAnimation MEGATHERIUM_SIT_START = RawAnimation.begin().thenPlay("animation.megatherium.sit_start");
    private static final RawAnimation MEGATHERIUM_SIT_END = RawAnimation.begin().thenPlay("animation.megatherium.sit_end");
    private static final RawAnimation MEGATHERIUM_MBLEM_1 = RawAnimation.begin().thenLoop("animation.megatherium.mblem_blend1");
    private static final RawAnimation MEGATHERIUM_MBLEM_2 = RawAnimation.begin().thenLoop("animation.megatherium.mblem_blend2");
    private static final RawAnimation MEGATHERIUM_SCRATCH_1 = RawAnimation.begin().thenLoop("animation.megatherium.scratch_blend1");
    private static final RawAnimation MEGATHERIUM_SCRATCH_2 = RawAnimation.begin().thenLoop("animation.megatherium.scratch_blend2");
    private static final RawAnimation MEGATHERIUM_SHAKE = RawAnimation.begin().thenLoop("animation.megatherium.shake_blend");
    private static final RawAnimation MEGATHERIUM_YAWN = RawAnimation.begin().thenLoop("animation.megatherium.yawn_blend");

    // Attack animations
    private static final RawAnimation MEGATHERIUM_ATTACK_1 = RawAnimation.begin().thenPlay("animation.megatherium.attack1");
    private static final RawAnimation MEGATHERIUM_ATTACK_2 = RawAnimation.begin().thenPlay("animation.megatherium.attack2");

    // Misc animations
    private static final RawAnimation MEGATHERIUM_BULLDOZE = RawAnimation.begin().thenLoop("animation.megatherium.bulldoze");

    // Idle accessors
    private static final EntityDataAccessor<Boolean> MBLEM_1 = SynchedEntityData.defineId(MegatheriumEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> MBLEM_2 = SynchedEntityData.defineId(MegatheriumEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> SCRATCH_1 = SynchedEntityData.defineId(MegatheriumEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> SCRATCH_2 = SynchedEntityData.defineId(MegatheriumEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> SHAKE = SynchedEntityData.defineId(MegatheriumEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> YAWN = SynchedEntityData.defineId(MegatheriumEntity.class, EntityDataSerializers.BOOLEAN);

    // Starting predicates
    private static final Predicate<LivingEntity> MEGATHERIUM_STARTING_PREDICATE = (e -> {
        if(e instanceof MegatheriumEntity entity) {
            return !entity.getMoveControl().hasWanted() && !entity.isSprinting() && !entity.isInWater() && !entity.isRunning();
        }
        return false;
    });

    private static final EntityAction MEGATHERIUM_MBLEM_1_ACTION = new EntityAction(0, (e) -> {}, 1);

    private static final StateHelper MEGATHERIUM_MBLEM_1_STATE =
            StateHelper.Builder.state(MBLEM_1, "megatherium_mblem_1")
                    .playTime(40)
                    .stopTime(50)
                    .startingPredicate(MEGATHERIUM_STARTING_PREDICATE)
                    .entityAction(MEGATHERIUM_MBLEM_1_ACTION)
                    .build();

    private static final EntityAction MEGATHERIUM_MBLEM_2_ACTION = new EntityAction(0, (e) -> {}, 1);

    private static final StateHelper MEGATHERIUM_MBLEM_2_STATE =
            StateHelper.Builder.state(MBLEM_2, "megatherium_mblem_2")
                    .playTime(40)
                    .stopTime(50)
                    .startingPredicate(MEGATHERIUM_STARTING_PREDICATE)
                    .entityAction(MEGATHERIUM_MBLEM_2_ACTION)
                    .build();

    private static final EntityAction MEGATHERIUM_SCRATCH_1_ACTION = new EntityAction(0, (e) -> {}, 1);

    private static final StateHelper MEGATHERIUM_SCRATCH_1_STATE =
            StateHelper.Builder.state(SCRATCH_1, "megatherium_scratch_1")
                    .playTime(80)
                    .stopTime(140)
                    .startingPredicate(MEGATHERIUM_STARTING_PREDICATE)
                    .entityAction(MEGATHERIUM_SCRATCH_1_ACTION)
                    .build();

    private static final EntityAction MEGATHERIUM_SCRATCH_2_ACTION = new EntityAction(0, (e) -> {}, 1);

    private static final StateHelper MEGATHERIUM_SCRATCH_2_STATE =
            StateHelper.Builder.state(SCRATCH_2, "megatherium_scratch_2")
                    .playTime(80)
                    .stopTime(140)
                    .startingPredicate(MEGATHERIUM_STARTING_PREDICATE)
                    .entityAction(MEGATHERIUM_SCRATCH_2_ACTION)
                    .build();

    private static final EntityAction MEGATHERIUM_SHAKE_ACTION = new EntityAction(0, (e) -> {}, 1);

    private static final StateHelper MEGATHERIUM_SHAKE_STATE =
            StateHelper.Builder.state(SHAKE, "megatherium_shake")
                    .playTime(40)
                    .stopTime(160)
                    .startingPredicate(MEGATHERIUM_STARTING_PREDICATE)
                    .entityAction(MEGATHERIUM_SHAKE_ACTION)
                    .build();

    private static final EntityAction MEGATHERIUM_YAWN_ACTION = new EntityAction(0, (e) -> {}, 1);

    private static final StateHelper MEGATHERIUM_YAWN_STATE =
            StateHelper.Builder.state(YAWN, "megatherium_yawn")
                    .playTime(40)
                    .stopTime(120)
                    .startingPredicate(MEGATHERIUM_STARTING_PREDICATE)
                    .entityAction(MEGATHERIUM_YAWN_ACTION)
                    .build();

    // Idle states
    @Override
    public ImmutableMap<String, StateHelper> getStates() {
        return ImmutableMap.of(
                MEGATHERIUM_MBLEM_1_STATE.getName(), MEGATHERIUM_MBLEM_1_STATE,
                MEGATHERIUM_MBLEM_2_STATE.getName(), MEGATHERIUM_MBLEM_2_STATE,
                MEGATHERIUM_SCRATCH_1_STATE.getName(), MEGATHERIUM_SCRATCH_1_STATE,
                MEGATHERIUM_SCRATCH_2_STATE.getName(), MEGATHERIUM_SCRATCH_2_STATE,
                MEGATHERIUM_SHAKE_STATE.getName(), MEGATHERIUM_SHAKE_STATE,
                MEGATHERIUM_YAWN_STATE.getName(), MEGATHERIUM_YAWN_STATE
        );
    }

    @Override
    public List<WeightedState<StateHelper>> getWeightedStatesToPerform() {
        return ImmutableList.of(
                WeightedState.of(MEGATHERIUM_MBLEM_1_STATE, 10),
                WeightedState.of(MEGATHERIUM_MBLEM_2_STATE, 10),
                WeightedState.of(MEGATHERIUM_SCRATCH_1_STATE, 7),
                WeightedState.of(MEGATHERIUM_SCRATCH_2_STATE, 7),
                WeightedState.of(MEGATHERIUM_SHAKE_STATE, 6),
                WeightedState.of(MEGATHERIUM_YAWN_STATE, 9)
        );
    }

    // Body control / navigation
    @Override
    protected @NotNull BodyRotationControl createBodyControl() {
        return new SmartBodyHelper(this);
    }

    @Override
    protected @NotNull PathNavigation createNavigation(Level levelIn) {
        return new SmoothGroundNavigation(this, levelIn);
    }

    public MegatheriumEntity(EntityType<? extends PrehistoricEntity> entityType, Level level) {
        super(entityType, level);
        this.setMaxUpStep(1.25F);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
            .add(Attributes.MAX_HEALTH, 80.0D).add(Attributes.ATTACK_DAMAGE, 12.0D).add(Attributes.MOVEMENT_SPEED, 0.16D).add(Attributes.KNOCKBACK_RESISTANCE, 1.5D)
        ;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new RandomStateGoal<>(this));
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(0, new SitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(1, new MegatheriumAttackGoal(this));
        this.goalSelector.addGoal(4, new TemptGoal(this, 1.2D, Ingredient.of(UPItemTags.MEGATHERIUM_FOOD), false));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)));
        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
        this.goalSelector.addGoal(3, new PrehistoricFollowOwnerGoal(this, 1.2D, 5.0F, 2.0F, false));
        this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 1.0D, 10));
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(MBLEM_1, false);
        this.entityData.define(MBLEM_2, false);
        this.entityData.define(SCRATCH_1, false);
        this.entityData.define(SCRATCH_2, false);
        this.entityData.define(SHAKE, false);
        this.entityData.define(YAWN, false);
        super.defineSynchedData();
    }

    public void tick() {
        super.tick();

        // Float while being ridden
        boolean ridden = !this.getPassengers().isEmpty();
        boolean water = this.isInWater();
        if(ridden && water) {
            boolean waterBelow = this.level().isWaterAt(this.blockPosition().below());

            if(waterBelow) {
                this.move(MoverType.PLAYER, new Vec3(0, 0.08, 0));
            }
        }

        if (isRunning() && !hasRunningAttributes) {
            hasRunningAttributes = true;
            this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.28D);
        }
        if (!isRunning() && hasRunningAttributes) {
            hasRunningAttributes = false;
            this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.16D);
        }
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
            return super.getDimensions(pPose).scale(1.0F, 0.8F);
        } else {
            return super.getDimensions(pPose);
        }
    }

    protected void doPlayerRide(@NotNull Player player) {
        if (!this.level().isClientSide) {
            player.setYRot(this.getYRot());
            player.setXRot(this.getXRot());
            player.startRiding(this);
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
        if(player.zza != 0 || player.xxa != 0){
            this.setRot(player.getYRot(), player.getXRot() * 0.25F);
            this.yRotO = this.yBodyRot = this.yHeadRot = this.getYRot();
            this.setMaxUpStep(1.25F);
            this.getNavigation().stop();
            this.setTarget(null);
        }
    }

    protected float getRiddenSpeed(Player pPlayer) {
        float f = 0.0F;
        if(pPlayer.isSprinting()) {
            f = 0.1F;
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
        pPassenger.setPos(this.getX() + (double) (0.5F * ySin), this.getY() + this.getPassengersRidingOffset() + pPassenger.getMyRidingOffset() + 0.4F, this.getZ() - (double) (0.5F * yCos));
    }

    public double getPassengersRidingOffset() {
        return 3.65;
    }

    public @NotNull InteractionResult mobInteract(Player player, @NotNull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (hand != InteractionHand.MAIN_HAND) return InteractionResult.FAIL;
        if (itemstack.is(UPItemTags.MEGATHERIUM_FOOD)) {
            if(!this.isTame()) {
                this.gameEvent(GameEvent.EAT, this);
                this.playSound(SoundEvents.HORSE_EAT, 0.5F, 1.0F);
                this.level().broadcastEntityEvent(this, (byte) 6);

                int size = itemstack.getCount();
                int tameAmount = 60 + random.nextInt(32);
                if(!player.isCreative()) {
                    if (size >= tameAmount) {
                        this.tame(player);
                        this.level().broadcastEntityEvent(this, (byte) 7);
                    }
                    itemstack.shrink(size);
                }
                else {
                    this.tame(player);
                    this.level().broadcastEntityEvent(this, (byte) 7);
                }
                return InteractionResult.SUCCESS;
            }
        }
        if(this.isTame() && this.isOwnedBy(player)) {
            if (this.isFood(itemstack) && this.getHealth() < this.getMaxHealth()) {
                if (!player.getAbilities().instabuild) {
                    itemstack.shrink(1);
                }
                this.heal((float) itemstack.getFoodProperties(this).getNutrition());
                this.gameEvent(GameEvent.EAT, this);
            }
            else if (itemstack.getItem() == Items.SADDLE && !this.isSaddled()) {
                this.usePlayerItem(player, hand, itemstack);
                this.playSound(SoundEvents.HORSE_SADDLE);
                this.setSaddled(true);
            }
            else if (itemstack.getItem() == Items.SHEARS && this.isSaddled()) {
                this.setSaddled(false);
                this.playSound(SoundEvents.SHEEP_SHEAR, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
                this.spawnAtLocation(Items.SADDLE);
            }
        }
        if (!this.level().isClientSide && this.isTame() && this.isOwnedBy(player) && this.getStandingTime() == 0 && this.getSittingTime() == 0) {
            if (!player.isShiftKeyDown() && !this.isBaby() && this.isSaddled() && !this.isInSittingPose() &&
                    this.getStandingTime() == 0 && this.getSittingTime() == 0 && !this.isInWater()) {
                this.doPlayerRide(player);
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
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.FAIL;
    }

    @Override
    public boolean shouldFollow() {
        return this.getCommand() == 1;
    }

    protected void playStepSound(BlockPos p_28301_, BlockState p_28302_) {
        this.playSound(SoundEvents.POLAR_BEAR_STEP, 0.25F, 1.0F);
    }

    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
    }

    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
    }

    protected SoundEvent getAmbientSound() {
        return UPSounds.MEGATHER_IDLE.get();
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return UPSounds.MEGATHER_HURT.get();
    }

    protected SoundEvent getDeathSound() {
        return UPSounds.MEGATHER_DEATH.get();
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

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        return UPEntities.MEGATHERIUM.get().create(serverLevel);
    }

    // Animation sounds
    private void soundListener(SoundKeyframeEvent<MegatheriumEntity> event) {
        MegatheriumEntity megatherium = event.getAnimatable();
        if (megatherium.level().isClientSide) {
            if (event.getKeyframeData().getSound().equals("megatherium_swipe")) {
                megatherium.level().playLocalSound(megatherium.getX(), megatherium.getY(), megatherium.getZ(), UPSounds.TAIL_SWIPE.get(), megatherium.getSoundSource(), 0.75F, megatherium.getVoicePitch(), false);
            }
        }
    }

    // Attack controller
    @Override
    public void registerControllers(final AnimatableManager.ControllerRegistrar controllers) {
        AnimationController<MegatheriumEntity> controller = new AnimationController<>(this, "controller", 5, this::predicate);
        controllers.add(controller);

        AnimationController<MegatheriumEntity> idle = new AnimationController<>(this, "idleController", 0, this::idlePredicate);
        idle.setSoundKeyframeHandler(this::soundListener);
        controllers.add(idle);

        AnimationController<MegatheriumEntity> attack = new AnimationController<>(this, "attackController", 5, this::attackPredicate);
        attack.setSoundKeyframeHandler(this::soundListener);
        controllers.add(attack);

        AnimationController<MegatheriumEntity> sit = new AnimationController<>(this, "sitController", 0, this::sitPredicate);
        controllers.add(sit);
    }

    protected <E extends MegatheriumEntity> PlayState predicate(final software.bernie.geckolib.core.animation.AnimationState<E> event) {
        if (this.isFromBook()) {
            return event.setAndContinue(MEGATHERIUM_IDLE);
        }
        if (this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6 && !this.isInWater() && !this.isInSittingPose() && !this.isSwimming()) {
            if(this.hasControllingPassenger()) {
                if (this.getControllingPassenger().isSprinting()) {
                    event.setAndContinue(MEGATHERIUM_RUN);
                    event.getController().setAnimationSpeed(1.0F);
                } else {
                    event.setAndContinue(MEGATHERIUM_WALK);
                    event.getController().setAnimationSpeed(1.25F);
                }
            }
            else {
                if (this.isSprinting() || this.isRunning()) {
                    event.getController().setAnimationSpeed(1.0F);
                    event.setAndContinue(MEGATHERIUM_RUN);
                } else {
                    event.getController().setAnimationSpeed(1.0F);
                    event.setAndContinue(MEGATHERIUM_WALK);
                }
            }
            return PlayState.CONTINUE;
        }
        if (this.isInWater() || this.isSwimming()) {
            event.getController().setAnimationSpeed(1.0F);
            event.setAndContinue(MEGATHERIUM_SWIM);
            return PlayState.CONTINUE;
        }

        if (this.isInSittingPose()) {
            event.getController().setAnimationSpeed(1.0F);
            event.setAndContinue(MEGATHERIUM_SIT);
            return PlayState.CONTINUE;
        }
        event.setAndContinue(MEGATHERIUM_IDLE);
        event.getController().setAnimationSpeed(1.0F);
        return PlayState.CONTINUE;
    }

    // Idle animations
    protected <E extends MegatheriumEntity> PlayState idlePredicate(final AnimationState<E> event) {
        if (getBooleanState(MBLEM_1)) {
            event.getController().setAnimation(MEGATHERIUM_MBLEM_1);
            return PlayState.CONTINUE;
        }
        if (getBooleanState(MBLEM_2)) {
            event.getController().setAnimation(MEGATHERIUM_MBLEM_2);
            return PlayState.CONTINUE;
        }
        if (getBooleanState(SCRATCH_1)) {
            event.getController().setAnimation(MEGATHERIUM_SCRATCH_1);
            return PlayState.CONTINUE;
        }
        if (getBooleanState(SCRATCH_2)) {
            event.getController().setAnimation(MEGATHERIUM_SCRATCH_2);
            return PlayState.CONTINUE;
        }
        if (getBooleanState(SHAKE)) {
            event.getController().setAnimation(MEGATHERIUM_SHAKE);
            return PlayState.CONTINUE;
        }
        if (getBooleanState(YAWN)) {
            event.getController().setAnimation(MEGATHERIUM_YAWN);
            return PlayState.CONTINUE;
        }
        event.getController().forceAnimationReset();
        return PlayState.STOP;
    }

    // Attack animations
    protected <E extends MegatheriumEntity> PlayState attackPredicate(final AnimationState<E> event) {
        int animState = this.getAnimationState();
        if (animState == 21) {
            event.setAndContinue(MEGATHERIUM_ATTACK_1);
            return PlayState.CONTINUE;
        }
        else if (animState == 22) {
            event.setAndContinue(MEGATHERIUM_ATTACK_2);
            return PlayState.CONTINUE;
        }
        else if (animState == 0) {
            event.getController().forceAnimationReset();
            return PlayState.STOP;
        }
        else return PlayState.CONTINUE;
    }

    // Sitting animations
    protected <E extends MegatheriumEntity> PlayState sitPredicate(AnimationState<E> event) {
        if (this.isInSittingPose() || (this.getSittingLag() < 7 && this.getSittingLag() > 0)){
            event.setAndContinue(MEGATHERIUM_SIT);
            return PlayState.CONTINUE;
        }
        else if (this.getSittingTime() > 0) {
            event.setAndContinue(MEGATHERIUM_SIT_START);
            return PlayState.CONTINUE;
        }
        else if (this.getStandingTime() > 0) {
            event.setAndContinue(MEGATHERIUM_SIT_END);
            return PlayState.CONTINUE;
        }
        else {
            event.getController().forceAnimationReset();
            return PlayState.STOP;
        }
    }
}
