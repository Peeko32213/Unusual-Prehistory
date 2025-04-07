package com.peeko32213.unusualprehistory.common.entity.custom.prehistoric;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.peeko32213.unusualprehistory.UnusualPrehistoryConfig;
import com.peeko32213.unusualprehistory.common.entity.animation.state.*;
import com.peeko32213.unusualprehistory.common.entity.custom.ai.goal.PrehistoricFollowOwnerGoal;
import com.peeko32213.unusualprehistory.common.entity.custom.ai.goal.TameableTempt;
import com.peeko32213.unusualprehistory.common.entity.custom.ai.goal.attack.TriceratopsAttackGoal;
import com.peeko32213.unusualprehistory.common.entity.custom.base.PrehistoricEntity;
import com.peeko32213.unusualprehistory.common.entity.util.interfaces.ICustomFollower;
import com.peeko32213.unusualprehistory.common.entity.util.navigator.SmartBodyHelper;
import com.peeko32213.unusualprehistory.common.entity.util.navigator.SmoothGroundNavigation;
import com.peeko32213.unusualprehistory.core.other.tags.UPBlockTags;
import com.peeko32213.unusualprehistory.core.other.tags.UPItemTags;
import com.peeko32213.unusualprehistory.core.registry.entities.UPEntities;
import com.peeko32213.unusualprehistory.core.registry.items.UPItems;
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
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
import java.util.UUID;
import java.util.function.Predicate;

public class TriceratopsEntity extends PrehistoricEntity implements ICustomFollower {

    private static final EntityDataAccessor<Boolean> SKELETAL = SynchedEntityData.defineId(TriceratopsEntity.class, EntityDataSerializers.BOOLEAN);
    private UUID lastLightningBoltUUID;

    public static final Logger LOGGER = LogManager.getLogger();

    // Movement animations
    private static final RawAnimation TRIKE_SWIM = RawAnimation.begin().thenLoop("animation.triceratops.swim");
    private static final RawAnimation TRIKE_WALK = RawAnimation.begin().thenLoop("animation.triceratops.walk");
    private static final RawAnimation TRIKE_SPRINT = RawAnimation.begin().thenLoop("animation.triceratops.run");

    // Idle animations
    private static final RawAnimation TRIKE_IDLE = RawAnimation.begin().thenLoop("animation.triceratops.idle");
    private static final RawAnimation TRIKE_GRAZE = RawAnimation.begin().thenPlay("animation.triceratops.graze_blend");
    private static final RawAnimation TRIKE_HEAD_SHAKE = RawAnimation.begin().thenPlay("animation.triceratops.shake_blend");
    private static final RawAnimation TRIKE_CHATTER = RawAnimation.begin().thenPlay("animation.triceratops.chatter_blend");
    private static final RawAnimation TRIKE_SIT_START = RawAnimation.begin().thenPlay("animation.triceratops.sit_start");
    private static final RawAnimation TRIKE_SIT = RawAnimation.begin().thenLoop("animation.triceratops.sit");
    private static final RawAnimation TRIKE_SIT_END = RawAnimation.begin().thenPlay("animation.triceratops.sit_end");

    // Attack animations
    private static final RawAnimation TRIKE_ATTACK_1 = RawAnimation.begin().thenPlay("animation.triceratops.attack_blend1");
    private static final RawAnimation TRIKE_ATTACK_2 = RawAnimation.begin().thenPlay("animation.triceratops.attack_blend2");
    private static final RawAnimation TRIKE_CHARGE = RawAnimation.begin().thenPlay("animation.triceratops.warning_blend").thenLoop("animation.triceratops.charge");

    // Idle accessors
    private static final EntityDataAccessor<Boolean> GRAZE = SynchedEntityData.defineId(TriceratopsEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> HEAD_SHAKE = SynchedEntityData.defineId(TriceratopsEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> CHATTER = SynchedEntityData.defineId(TriceratopsEntity.class, EntityDataSerializers.BOOLEAN);

    // Starting predicates
    private static final Predicate<LivingEntity> TRICERATOPS_STARTING_PREDICATE = (e -> {
        if(e instanceof TriceratopsEntity entity) {
            return !entity.isRunning() && !entity.isSprinting() && !entity.isInWater();
        }
        return false;
    });

    private static final Predicate<LivingEntity> TRICERATOPS_GRAZING_PREDICATE = (e -> {
        if(e instanceof TriceratopsEntity entity) {
            return !entity.isRunning() && !entity.isSprinting() && !entity.isInWater() && entity.level().getBlockState(entity.blockPosition().below()).is(UPBlockTags.TRIKE_GRAZING_BLOCKS);
        }
        return false;
    });

    // Idle actions
    private static final EntityAction TRIKE_GRAZE_ACTION = new EntityAction(0, (e) -> {}, 1);

    private static final StateHelper TRIKE_GRAZE_STATE =
            StateHelper.Builder.state(GRAZE, "triceratops_graze")
                    .playTime(60)
                    .stopTime(150)
                    .startingPredicate(TRICERATOPS_GRAZING_PREDICATE)
                    .affectsAI(true)
                    .affectedFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK))
                    .entityAction(TRIKE_GRAZE_ACTION)
                    .build();

    private static final EntityAction TRIKE_HEAD_SHAKE_ACTION = new EntityAction(0, (e) -> {}, 1);

    private static final StateHelper TRIKE_HEAD_SHAKE_STATE =
            StateHelper.Builder.state(HEAD_SHAKE, "triceratops_head_shake")
                    .playTime(40)
                    .stopTime(120)
                    .startingPredicate(TRICERATOPS_STARTING_PREDICATE)
                    .entityAction(TRIKE_HEAD_SHAKE_ACTION)
                    .build();

    private static final EntityAction TRIKE_CHATTER_ACTION = new EntityAction(0, (e) -> {}, 1);

    private static final StateHelper TRIKE_CHATTER_STATE =
            StateHelper.Builder.state(CHATTER, "triceratops_chatter")
                    .playTime(60)
                    .stopTime(100)
                    .startingPredicate(TRICERATOPS_STARTING_PREDICATE)
                    .entityAction(TRIKE_CHATTER_ACTION)
                    .build();

    // States
    @Override
    public ImmutableMap<String, StateHelper> getStates() {
        return ImmutableMap.of(
                TRIKE_GRAZE_STATE.getName(), TRIKE_GRAZE_STATE,
                TRIKE_HEAD_SHAKE_STATE.getName(), TRIKE_HEAD_SHAKE_STATE,
                TRIKE_CHATTER_STATE.getName(), TRIKE_CHATTER_STATE
        );
    }

    @Override
    public List<WeightedState<StateHelper>> getWeightedStatesToPerform() {
        return ImmutableList.of(
                WeightedState.of(TRIKE_GRAZE_STATE, 10),
                WeightedState.of(TRIKE_HEAD_SHAKE_STATE, 12),
                WeightedState.of(TRIKE_CHATTER_STATE, 13)
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

    public TriceratopsEntity(EntityType<? extends PrehistoricEntity> entityType, Level level) {
        super(entityType, level);
        this.setMaxUpStep(1.25F);
        this.reassessTameGoals();
    }

    // Attributes
    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
            .add(Attributes.MAX_HEALTH, 120.0D).add(Attributes.ARMOR, 12.0D).add(Attributes.MOVEMENT_SPEED, 0.15D).add(Attributes.ATTACK_DAMAGE, 12.0D).add(Attributes.KNOCKBACK_RESISTANCE, 1.5D).add(Attributes.FOLLOW_RANGE, 32D)
        ;
    }

    // Goals
    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new RandomStateGoal<>(this));
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(0, new SitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(2, new TriceratopsAttackGoal(this));
        this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new PrehistoricFollowOwnerGoal(this, 1.2D, 5.0F, 2.0F, false));
        this.targetSelector.addGoal(8, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(8, new OwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(8, (new HurtByTargetGoal(this)));
        this.goalSelector.addGoal(6, new TameableTempt(this, 1.1D, Ingredient.of(UPItemTags.TRICERATOPS_FOOD), false));
    }

    @Override
    public int getMaxHeadYRot() {
        return 15;
    }

    // Collision config
    public boolean canBeCollidedWith() {
        return UnusualPrehistoryConfig.TRIKE_COLLISON.get();
    }

    @Override
    public boolean isPushable() {
        return !this.isInSittingPose() && !(this.getSittingTime() > 0 || this.getStandingTime() > 0) && !this.isVehicle();
    }

    // Sounds
    protected SoundEvent getAmbientSound() {
        return UPSounds.TRIKE_IDLE.get();
    }
    protected SoundEvent getHurtSound(@NotNull DamageSource damageSourceIn) {
        if(this.isSkeletal()) {
            return SoundEvents.SKELETON_HURT;
        }
        else return UPSounds.TRIKE_HURT.get();
    }
    protected SoundEvent getDeathSound() {
        if(this.isSkeletal()) {
            return SoundEvents.SKELETON_DEATH;
        }
        else return UPSounds.TRIKE_DEATH.get();
    }

    @Override
    public float getSoundVolume() {
        return 0.9F;
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel serverLevel, @NotNull AgeableMob ageableMob) {
        TriceratopsEntity trike = UPEntities.TRICERATOPS.get().create(serverLevel);
        trike.setVariant(this.getVariant());
        return trike;
    }

    public boolean hurt(@NotNull DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        } else {
            Entity entity = source.getEntity();
            if (entity != null && this.isTame() && !(entity instanceof Player) && !(entity instanceof AbstractArrow)) {
                amount = (amount + 1.0F) / 3.0F;
            }
            return super.hurt(source, amount);
        }
    }

    // Synched data
    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(GRAZE, false);
        this.entityData.define(HEAD_SHAKE, false);
        this.entityData.define(CHATTER, false);
        this.entityData.define(SKELETAL, false);
    }

    // Save data
    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("skeletal", this.isSkeletal());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setSkeletal(compound.getBoolean("skeletal"));
    }

    @Override
    public boolean canBeLeashed(Player player) {
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
            this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.34D);
        }
        if (!isRunning() && hasRunningAttributes) {
            hasRunningAttributes = false;
            this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.15D);
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
            f = 0.25F;
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

    // Rider hitbox position
    @Override
    protected void positionRider(Entity pPassenger, @NotNull MoveFunction pCallback) {
        float ySin = Mth.sin(this.yBodyRot * ((float) Math.PI / 180F));
        float yCos = Mth.cos(this.yBodyRot * ((float) Math.PI / 180F));
        pPassenger.setPos(this.getX() + (double) (0.15F * ySin), this.getY() + this.getPassengersRidingOffset() + pPassenger.getMyRidingOffset() + 0.4F, this.getZ() - (double) (0.15F * yCos));
    }

    public double getPassengersRidingOffset() {
        return 2.85;
    }

    // Travel
    @Override
    public void travel(Vec3 travelVector) {
        super.travel(travelVector);
        this.calculateEntityAnimation(false);
        this.tryCheckInsideBlocks();
    }

    @Override
    protected boolean isImmobile() {
        return super.isImmobile() && this.isVehicle();
    }

    // Foods
    public boolean isFood(ItemStack stack) {
        return stack.is(UPItemTags.TRICERATOPS_FOOD);
    }
    public boolean isTameFood(ItemStack stack) {
        return stack.is(UPItemTags.TRICERATOPS_TAMES);
    }

    // Mob interactions
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (itemstack.is(UPItems.ENCYLOPEDIA.get())) {
            return super.mobInteract(player, hand);
        }
        else if (hand == InteractionHand.MAIN_HAND && this.isTameFood(itemstack) && !this.isTame() && !this.isBaby()) {

            if (!player.getAbilities().instabuild) {
                itemstack.shrink(1);
            }

            this.playSound(SoundEvents.HORSE_EAT);

            if (this.random.nextInt(5) == 0) {
                this.tame(player);
                this.navigation.stop();
                this.level().broadcastEntityEvent(this, (byte)7);
            }
            else{
                this.level().broadcastEntityEvent(this, (byte)6);
            }

            return InteractionResult.SUCCESS;

        }
        else if (hand == InteractionHand.MAIN_HAND && !this.level().isClientSide && this.isTame() && this.isOwnedBy(player) && this.getStandingTime()==0 && this.getSittingTime()==0) {
            if (this.isFood(itemstack) && this.getHealth() < this.getMaxHealth()) {
                if (!player.getAbilities().instabuild) {
                    itemstack.shrink(1);
                }
                this.heal((float)itemstack.getFoodProperties(this).getNutrition());
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
            else if (!player.isShiftKeyDown() && !this.isBaby() && this.isSaddled() && !this.isInSittingPose() &&
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

    // Set sprinting
    @Override
    public void customServerAiStep() {
        if (this.getMoveControl().hasWanted() && !this.isBaby()) {
            this.setSprinting(this.getMoveControl().getSpeedModifier() >= 1.25D);
        } else {
            this.setSprinting(false);
        }
        super.customServerAiStep();
    }

    // Drop horn on grow up
    protected void ageBoundaryReached() {
        super.ageBoundaryReached();
        if (!this.isBaby() && this.level().getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT)) {
            this.spawnAtLocation(UPItems.TRIKE_HORN.get(), 1);
        }
    }

    protected void playStepSound(@NotNull BlockPos p_28301_, @NotNull BlockState p_28302_) {
        this.playSound(UPSounds.MAJUNGA_STEP.get(), 0.25F, 1.0F);
    }

    protected void dropEquipment() {
        super.dropEquipment();
        if (this.isSaddled()) {
            if (!this.level().isClientSide) {
                this.spawnAtLocation(Items.SADDLE);
            }
        }
        this.setSaddled(false);
    }

    @Override
    public void aiStep() {
        super.aiStep();
    }

    // Variants
    public void determineVariant(int variantChange){
        if (variantChange <= 60) {
            this.setVariant(0);
        }
        else {
            this.setVariant(1);
        }
    }

    public boolean isSkeletal() {
        return this.entityData.get(SKELETAL);
    }

    private void setSkeletal(boolean isSkeletal) {
        this.entityData.set(SKELETAL, isSkeletal);
    }

    public void thunderHit(ServerLevel pLevel, LightningBolt pLightning) {
        UUID uuid = pLightning.getUUID();
        if (!uuid.equals(this.lastLightningBoltUUID)) {
            this.setSkeletal((!this.isSkeletal() || this.isSkeletal()) != this.isSkeletal());
            this.lastLightningBoltUUID = uuid;
            this.playSound(SoundEvents.SKELETON_DEATH, 2.0F, 1.0F);
        }
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

    // Animation sounds
    private void soundListener(SoundKeyframeEvent<TriceratopsEntity> event) {
        TriceratopsEntity triceratops = event.getAnimatable();
        if (triceratops.level().isClientSide) {
            if (event.getKeyframeData().getSound().equals("triceratops_chatter")) {
                triceratops.level().playLocalSound(triceratops.getX(), triceratops.getY(), triceratops.getZ(), UPSounds.TRIKE_CHATTER.get(), triceratops.getSoundSource(), 1.5F, triceratops.getVoicePitch(), false);
            }
            if (event.getKeyframeData().getSound().equals("triceratops_warn")) {
                triceratops.level().playLocalSound(triceratops.getX(), triceratops.getY(), triceratops.getZ(), UPSounds.TRIKE_WARN.get(), triceratops.getSoundSource(), 1.75F, triceratops.getVoicePitch(), false);
            }
            if (event.getKeyframeData().getSound().equals("triceratops_swipe")) {
                triceratops.level().playLocalSound(triceratops.getX(), triceratops.getY(), triceratops.getZ(), UPSounds.TAIL_SWIPE.get(), triceratops.getSoundSource(), 0.75F, triceratops.getVoicePitch(), false);
            }
        }
    }

    // Animation control
    @Override
    public void registerControllers(final AnimatableManager.ControllerRegistrar controllers) {
        AnimationController<TriceratopsEntity> controller = new AnimationController<>(this, "controller", 5, this::predicate);
        controllers.add(controller);

        AnimationController<TriceratopsEntity> idle = new AnimationController<>(this, "idleController", 0, this::idlePredicate);
        idle.setSoundKeyframeHandler(this::soundListener);
        controllers.add(idle);

        AnimationController<TriceratopsEntity> attack = new AnimationController<>(this, "attackController", 5, this::attackPredicate);
        attack.setSoundKeyframeHandler(this::soundListener);
        controllers.add(attack);

        AnimationController<TriceratopsEntity> sit = new AnimationController<>(this, "sitController", 0, this::sitPredicate);
        controllers.add(sit);
    }

    protected <E extends TriceratopsEntity> PlayState predicate(final software.bernie.geckolib.core.animation.AnimationState<E> event) {

        if (this.isFromBook()) {
            return event.setAndContinue(TRIKE_IDLE);
        }

        if (this.isInWater() || this.isSwimming()) {
            event.setAndContinue(TRIKE_SWIM);
            event.getController().setAnimationSpeed(1.0F);
            return PlayState.CONTINUE;
        }

        else if(this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6 && !this.isSwimming() && !this.isInWater() && !this.isInSittingPose()) {
            if(this.hasControllingPassenger()) {
                if (this.getControllingPassenger().isSprinting()) {
                    event.setAndContinue(TRIKE_SPRINT);
                    event.getController().setAnimationSpeed(1.0F);
                } else {
                    event.setAndContinue(TRIKE_WALK);
                    event.getController().setAnimationSpeed(1.5F);
                }
            }
            else {
                if (this.isSprinting() || this.isRunning()) {
                    event.setAndContinue(TRIKE_SPRINT);
                    event.getController().setAnimationSpeed(1.0F);
                } else {
                    event.setAndContinue(TRIKE_WALK);
                    event.getController().setAnimationSpeed(1.0F);
                }
            }
            return PlayState.CONTINUE;
        }

        else if (!this.isInWater()) {
            return event.setAndContinue(TRIKE_IDLE);
        }

        return PlayState.CONTINUE;
    }

    // Idle animations
    protected <E extends TriceratopsEntity> PlayState idlePredicate(final AnimationState<E> event) {
        if (getBooleanState(GRAZE)) {
            event.getController().setAnimation(TRIKE_GRAZE);
            return PlayState.CONTINUE;
        }
        if (getBooleanState(HEAD_SHAKE)) {
            event.getController().setAnimation(TRIKE_HEAD_SHAKE);
            return PlayState.CONTINUE;
        }
        if (getBooleanState(CHATTER)) {
            event.getController().setAnimation(TRIKE_CHATTER);
            return PlayState.CONTINUE;
        }
        event.getController().forceAnimationReset();
        return PlayState.STOP;
    }

    // Attack animations
    protected <E extends TriceratopsEntity> PlayState attackPredicate(final AnimationState<E> event) {
        int animState = this.getAnimationState();

        if (animState == 21) {
            event.setAndContinue(TRIKE_ATTACK_1);
            return PlayState.CONTINUE;
        }
        else if (animState == 22) {
            event.setAndContinue(TRIKE_ATTACK_2);
            return PlayState.CONTINUE;
        }
        else if (animState == 23) {
            event.setAndContinue(TRIKE_CHARGE);
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
    protected <E extends TriceratopsEntity> PlayState sitPredicate(AnimationState<E> event) {
        if (this.isInSittingPose() || (this.getSittingLag() < 7 && this.getSittingLag() > 0)){
            event.setAndContinue(TRIKE_SIT);
            return PlayState.CONTINUE;
        }
        else if (this.getSittingTime() > 0) {
            event.setAndContinue(TRIKE_SIT_START);
            return PlayState.CONTINUE;
        }
        else if (this.getStandingTime() > 0) {
            event.setAndContinue(TRIKE_SIT_END);
            return PlayState.CONTINUE;
        }
        else {
            event.getController().forceAnimationReset();
            return PlayState.STOP;
        }
    }
}
