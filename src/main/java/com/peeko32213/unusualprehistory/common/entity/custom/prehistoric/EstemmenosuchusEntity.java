package com.peeko32213.unusualprehistory.common.entity.custom.prehistoric;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.peeko32213.unusualprehistory.common.entity.animation.state.EntityAction;
import com.peeko32213.unusualprehistory.common.entity.animation.state.RandomStateGoal;
import com.peeko32213.unusualprehistory.common.entity.animation.state.StateHelper;
import com.peeko32213.unusualprehistory.common.entity.animation.state.WeightedState;
import com.peeko32213.unusualprehistory.common.entity.custom.ai.goal.PrehistoricFollowOwnerGoal;
import com.peeko32213.unusualprehistory.common.entity.custom.ai.goal.attack.EstemmenosuchusAttackGoal;
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
import java.util.Objects;
import java.util.function.Predicate;

public class EstemmenosuchusEntity extends PrehistoricEntity implements ICustomFollower {

    private static final Ingredient FOOD_ITEMS = Ingredient.of(UPItemTags.ESTEMME_FOOD_ITEMS);
    private static final EntityDataAccessor<Boolean> RAMMING = SynchedEntityData.defineId(EstemmenosuchusEntity.class, EntityDataSerializers.BOOLEAN);

    // Movement animations
    private static final RawAnimation ESTEMME_WALK = RawAnimation.begin().thenLoop("animation.estemmenosuchus.walk");
    private static final RawAnimation ESTEMME_RUN = RawAnimation.begin().thenLoop("animation.estemmenosuchus.run");
    private static final RawAnimation ESTEMME_SWIM = RawAnimation.begin().thenLoop("animation.estemmenosuchus.swim");

    // Idle animations
    private static final RawAnimation ESTEMME_IDLE = RawAnimation.begin().thenLoop("animation.estemmenosuchus.idle");
    private static final RawAnimation ESTEMME_BELLOW = RawAnimation.begin().thenPlay("animation.estemmenosuchus.bellow_blend");
    private static final RawAnimation ESTEMME_YAWN = RawAnimation.begin().thenPlay("animation.estemmenosuchus.yawn_blend");
    private static final RawAnimation ESTEMME_SIT = RawAnimation.begin().thenLoop("animation.estemmenosuchus.sit");
    private static final RawAnimation ESTEMME_SLEEP = RawAnimation.begin().thenLoop("animation.estemmenosuchus.sleep");

    // Attack animations
    private static final RawAnimation ESTEMME_BITE_1 = RawAnimation.begin().thenPlay("animation.estemmenosuchus.attack1_blend");
    private static final RawAnimation ESTEMME_BITE_2 = RawAnimation.begin().thenPlay("animation.estemmenosuchus.attack2_blend");
    private static final RawAnimation ESTEMME_RAMMING = RawAnimation.begin().thenPlay("animation.estemmenosuchus.ramming_start").thenLoop("animation.estemmenosuchus.ramming");

    // Misc animations
    private static final RawAnimation CRUSH = RawAnimation.begin().thenPlay("animation.estemmenosuchus.crush_blend");

    // Idle accessors
    private static final EntityDataAccessor<Boolean> BELLOW = SynchedEntityData.defineId(EstemmenosuchusEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> YAWN = SynchedEntityData.defineId(EstemmenosuchusEntity.class, EntityDataSerializers.BOOLEAN);

    // Starting predicates
    private static final Predicate<LivingEntity> ESTEMME_STARTING_PREDICATE = (e -> {
        if(e instanceof EstemmenosuchusEntity entity) {
            return !entity.isRunning() && !entity.isSprinting() && !entity.isInWater();
        }
        return false;
    });

    // Idle actions
    private static final EntityAction ESTEMME_BELLOW_ACTION = new EntityAction(0, (e) -> {}, 1);

    private static final StateHelper ESTEMME_BELLOW_STATE =
            StateHelper.Builder.state(BELLOW, "estemmenosuchus_bellow")
                .playTime(60)
                .stopTime(120)
                .startingPredicate(ESTEMME_STARTING_PREDICATE)
                .entityAction(ESTEMME_BELLOW_ACTION)
                .build();

    private static final EntityAction ESTEMME_YAWN_ACTION = new EntityAction(0, (e) -> {}, 1);

    private static final StateHelper ESTEMME_YAWN_STATE =
            StateHelper.Builder.state(YAWN, "estemmenosuchus_yawn")
                .playTime(60)
                .stopTime(140)
                .startingPredicate(ESTEMME_STARTING_PREDICATE)
                .entityAction(ESTEMME_YAWN_ACTION)
                .build();

    @Override
    public ImmutableMap<String, StateHelper> getStates() {
        return ImmutableMap.of(
                ESTEMME_BELLOW_STATE.getName(), ESTEMME_BELLOW_STATE,
                ESTEMME_YAWN_STATE.getName(), ESTEMME_YAWN_STATE
        );
    }

    @Override
    public List<WeightedState<StateHelper>> getWeightedStatesToPerform() {
        return ImmutableList.of(
                WeightedState.of(ESTEMME_BELLOW_STATE, 8),
                WeightedState.of(ESTEMME_YAWN_STATE, 10)
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

    public EstemmenosuchusEntity(EntityType<? extends PrehistoricEntity> entityType, Level level) {
        super(entityType, level);
        this.setMaxUpStep(1.25F);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
            .add(Attributes.MAX_HEALTH, 80.0D)
            .add(Attributes.ARMOR, 8.0)
            .add(Attributes.MOVEMENT_SPEED, 0.14D)
            .add(Attributes.KNOCKBACK_RESISTANCE, 0.5D)
            .add(Attributes.FOLLOW_RANGE, 24.0D)
            .add(Attributes.ATTACK_DAMAGE, 12.0D);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new RandomStateGoal<>(this));
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(0, new SitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(1, new EstemmenosuchusAttackGoal(this));
        this.goalSelector.addGoal(4, new TemptGoal(this, 1.2D, FOOD_ITEMS, false));
        this.goalSelector.addGoal(5, new FollowParentGoal(this, 1.1D));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 1.0F));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(3, new PrehistoricFollowOwnerGoal(this, 1.1D, 5.0F, 2.0F, false));
        this.targetSelector.addGoal(7, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(7, new OwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(8, (new HurtByTargetGoal(this)));
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel serverLevel, @NotNull AgeableMob p_146744_) {
        return UPEntities.ESTEMMENOSUCHUS.get().create(serverLevel);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(BELLOW, false);
        this.entityData.define(YAWN, false);
        this.entityData.define(RAMMING, false);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("Ramming", this.isRamming());
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setRamming(compound.getBoolean("Ramming"));
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
            return super.getDimensions(pPose).scale(1.0F, 0.85F);
        } else {
            return super.getDimensions(pPose);
        }
    }

    // Ramming
    public void setRamming(boolean ramming) {
        this.entityData.set(RAMMING, ramming);
    }
    public boolean isRamming() {
        return this.entityData.get(RAMMING);
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
            this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.35D);
        }
        if (!isRunning() && hasRunningAttributes) {
            hasRunningAttributes = false;
            this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.14D);
        }
    }

    public boolean isFood(ItemStack stack) {
        return stack.is(UPItemTags.ESTEMME_FOOD_ITEMS);
    }

    // Mob interactions
    public @NotNull InteractionResult mobInteract(Player player, @NotNull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if(hand != InteractionHand.MAIN_HAND) return InteractionResult.FAIL;
        if (isFood(itemstack) && !isTame()) {
            if(!this.level().isClientSide) {
                this.level().broadcastEntityEvent(this, (byte) 7);
                this.tame(player);
                itemstack.shrink(1);
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
            f = 0.27F;
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
        pPassenger.setPos(this.getX() + (double) (0.21F * ySin), this.getY() + this.getPassengersRidingOffset() + pPassenger.getMyRidingOffset() + (0.21F), this.getZ() - (double) (0.21F * yCos));
    }

    public double getPassengersRidingOffset() {
        return 2.8F;
    }

    // Follow owner
    @Override
    public boolean shouldFollow() {
        return this.getCommand() == 1;
    }

    // Command
    @Override
    public boolean canOwnerCommand(Player ownerPlayer) {
        return true;
    }

    protected SoundEvent getAmbientSound() {
        return UPSounds.ESTEMME_IDLE.get();
    }

    protected SoundEvent getHurtSound(@NotNull DamageSource damageSourceIn) {
        return UPSounds.ESTEMME_HURT.get();
    }

    protected SoundEvent getDeathSound() {
        return UPSounds.ESTEMME_DEATH.get();
    }

    protected void playStepSound(@NotNull BlockPos p_28301_, @NotNull BlockState p_28302_) {
        this.playSound(UPSounds.MAJUNGA_STEP.get(), 0.3F, 0.85F);
    }

    @Override
    public float getSoundVolume() {
        return 1.0F;
    }

    // TODO: make sounds.json change pitch instead, and make sounds datagen so its less messy
    @Override
    public float getVoicePitch() {
        return 0.75F;
    }

    @Override
    public int getAmbientSoundInterval() {
        return 135;
    }

    // Animation sounds
    private void soundListener(SoundKeyframeEvent<EstemmenosuchusEntity> event) {
        EstemmenosuchusEntity estemmenosuchus = event.getAnimatable();
        if (event.getKeyframeData().getSound().equals("estemmenosuchus_bellow")) {
            estemmenosuchus.level().playLocalSound(estemmenosuchus.getX(), estemmenosuchus.getY(), estemmenosuchus.getZ(), UPSounds.ESTEMME_BELLOW.get(), estemmenosuchus.getSoundSource(), 1.25F, estemmenosuchus.getVoicePitch() * 0.85F, false);
        }
        if (event.getKeyframeData().getSound().equals("estemmenosuchus_warn")) {
            estemmenosuchus.level().playLocalSound(estemmenosuchus.getX(), estemmenosuchus.getY(), estemmenosuchus.getZ(), UPSounds.ESTEMME_WARN.get(), estemmenosuchus.getSoundSource(), 1.5F, estemmenosuchus.getVoicePitch() * 1.2F, false);
        }
        if (event.getKeyframeData().getSound().equals("estemmenosuchus_bite")) {
            estemmenosuchus.level().playLocalSound(estemmenosuchus.getX(), estemmenosuchus.getY(), estemmenosuchus.getZ(), UPSounds.BARINA_BITE.get(), estemmenosuchus.getSoundSource(), 1.0F, estemmenosuchus.getVoicePitch() * 0.85F, false);
        }
    }

    // Animation controllers
    @Override
    public void registerControllers(final AnimatableManager.ControllerRegistrar controllers) {
        AnimationController<EstemmenosuchusEntity> controller = new AnimationController<>(this, "controller", 10, this::predicate);
        controllers.add(controller);

        AnimationController<EstemmenosuchusEntity> idle = new AnimationController<>(this, "idleController", 0, this::idlePredicate);
        idle.setSoundKeyframeHandler(this::soundListener);
        controllers.add(idle);

        AnimationController<EstemmenosuchusEntity> attack = new AnimationController<>(this, "attackController", 5, this::attackPredicate);
        attack.setSoundKeyframeHandler(this::soundListener);
        controllers.add(attack);

        AnimationController<EstemmenosuchusEntity> sit = new AnimationController<>(this, "sitController", 0, this::sitPredicate);
        controllers.add(sit);
    }

    protected <E extends EstemmenosuchusEntity> PlayState predicate(final software.bernie.geckolib.core.animation.AnimationState<E> event) {
        if (this.isFromBook()) {
            return event.setAndContinue(ESTEMME_IDLE);
        }

        if (this.isInWater() || this.isSwimming()) {
            event.setAndContinue(ESTEMME_SWIM);
            event.getController().setAnimationSpeed(1.0F);
            return PlayState.CONTINUE;
        }

        else if(this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6 && !this.isSwimming() && !this.isInWater() && !this.isInSittingPose() && !this.isRamming()) {
            if(this.hasControllingPassenger()) {
                if (this.getControllingPassenger().isSprinting()) {
                    event.setAndContinue(ESTEMME_RUN);
                    event.getController().setAnimationSpeed(1.0F);
                } else {
                    event.setAndContinue(ESTEMME_WALK);
                    event.getController().setAnimationSpeed(1.15F);
                }
            }
            else {
                if (this.isSprinting() || this.isRunning()) {
                    event.setAndContinue(ESTEMME_RUN);
                    event.getController().setAnimationSpeed(1.0F);
                } else {
                    event.setAndContinue(ESTEMME_WALK);
                    event.getController().setAnimationSpeed(1.0F);
                }
            }
            return PlayState.CONTINUE;
        }

        else if (!this.isInWater()) {
            return event.setAndContinue(ESTEMME_IDLE);
        }

        return PlayState.CONTINUE;
    }
    // Idle animations
    protected <E extends EstemmenosuchusEntity> PlayState idlePredicate(final software.bernie.geckolib.core.animation.AnimationState<E> event) {
        if (getBooleanState(BELLOW)) {
            event.getController().setAnimation(ESTEMME_BELLOW);
            return PlayState.CONTINUE;
        }
        if (getBooleanState(YAWN)) {
            event.getController().setAnimation(ESTEMME_YAWN);
            return PlayState.CONTINUE;
        }
        event.getController().forceAnimationReset();
        return PlayState.STOP;
    }

    // Attack animations
    protected <E extends EstemmenosuchusEntity> PlayState attackPredicate(final software.bernie.geckolib.core.animation.AnimationState<E> event) {
        int animState = this.getAnimationState();
        if (animState == 21) {
            event.setAndContinue(ESTEMME_BITE_1);
            return PlayState.CONTINUE;
        }
        else if (animState == 22) {
            event.setAndContinue(ESTEMME_BITE_2);
            return PlayState.CONTINUE;
        }
        else if (this.isRamming() && animState == 23) {
            event.setAndContinue(ESTEMME_RAMMING);
            event.getController().setAnimationSpeed(1.0F);
            return PlayState.CONTINUE;
        }
        else if (animState == 0) {
            event.getController().forceAnimationReset();
            return PlayState.STOP;
        }
        else return PlayState.CONTINUE;
    }

    // Sitting animations
    protected <E extends EstemmenosuchusEntity> PlayState sitPredicate(AnimationState<E> event) {
        if (this.isInSittingPose() || (this.getSittingLag() < 7 && this.getSittingLag() > 0)){
            event.setAndContinue(ESTEMME_SIT);
            return PlayState.CONTINUE;
        }
        else if (this.getSittingTime() > 0) {
            event.setAndContinue(ESTEMME_IDLE);
            return PlayState.CONTINUE;
        }
        else if (this.getStandingTime() > 0) {
            event.setAndContinue(ESTEMME_IDLE);
            return PlayState.CONTINUE;
        }
        else {
            event.getController().forceAnimationReset();
            return PlayState.STOP;
        }
    }
}
