package com.peeko32213.unusualprehistory.common.entity.custom.prehistoric;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.peeko32213.unusualprehistory.UnusualPrehistoryConfig;
import com.peeko32213.unusualprehistory.common.entity.animation.state.*;
import com.peeko32213.unusualprehistory.common.entity.custom.ai.goal.CustomRideGoal;
import com.peeko32213.unusualprehistory.common.entity.custom.ai.goal.PrehistoricFollowOwnerGoal;
import com.peeko32213.unusualprehistory.common.entity.custom.ai.goal.TameableTempt;
import com.peeko32213.unusualprehistory.common.entity.custom.ai.goal.attack.TriceratopsMeleeAttackGoal;
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
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.SitWhenOrderedToGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
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
import net.minecraft.world.phys.AABB;
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

public class TriceratopsEntity extends PrehistoricEntity implements ICustomFollower, IStateAction {

    private static final EntityDataAccessor<Boolean> SKELETAL = SynchedEntityData.defineId(TriceratopsEntity.class, EntityDataSerializers.BOOLEAN);
    private UUID lastLightningBoltUUID;

    private static final Ingredient TEMPTATION_ITEMS = Ingredient.of(UPItemTags.TRICERATOPS_FOOD);

    public static final Logger LOGGER = LogManager.getLogger();

    // Movement animations
    private static final RawAnimation TRIKE_SWIM = RawAnimation.begin().thenLoop("animation.triceratops.swim");
    private static final RawAnimation TRIKE_WALK = RawAnimation.begin().thenLoop("animation.triceratops.walk");
    private static final RawAnimation TRIKE_SPRINT = RawAnimation.begin().thenLoop("animation.triceratops.charge");

    // Idle animations
    private static final RawAnimation TRIKE_IDLE = RawAnimation.begin().thenLoop("animation.triceratops.idle");
    private static final RawAnimation TRIKE_GRAZE = RawAnimation.begin().thenPlay("animation.triceratops.graze_blend");
    private static final RawAnimation TRIKE_HEAD_SHAKE = RawAnimation.begin().thenPlay("animation.triceratops.shake_blend");
    private static final RawAnimation TRIKE_CHATTER = RawAnimation.begin().thenPlay("animation.triceratops.chatter_blend");
    private static final RawAnimation TRIKE_SIT = RawAnimation.begin().thenPlay("animation.triceratops.sit_start").thenPlay("animation.triceratops.sit");
    private static final RawAnimation TRIKE_SIT_END = RawAnimation.begin().thenPlay("animation.triceratops.sit_end");

    // Attack animations
    private static final RawAnimation TRIKE_ATTACK_1 = RawAnimation.begin().thenPlay("animation.triceratops.attack_blend1");
    private static final RawAnimation TRIKE_ATTACK_2 = RawAnimation.begin().thenPlay("animation.triceratops.attack_blend2");
    private static final RawAnimation TRIKE_CHARGE = RawAnimation.begin().thenPlay("animation.triceratops.warning_blend").thenLoop("animation.triceratops.charge");

    // Idle accessors
    private static final EntityDataAccessor<Boolean> IDLE_1_AC = SynchedEntityData.defineId(TriceratopsEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> IDLE_2_AC = SynchedEntityData.defineId(TriceratopsEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> IDLE_3_AC = SynchedEntityData.defineId(TriceratopsEntity.class, EntityDataSerializers.BOOLEAN);

    // Idle actions
    private static final EntityAction TRIKE_IDLE_1_ACTION = new EntityAction(0, (e) -> {}, 1);

    private static final StateHelper TRIKE_IDLE_1_STATE =
            StateHelper.Builder.state(IDLE_1_AC, "triceratops_graze")
                    .playTime(60)
                    .stopTime(150)
                    .affectsAI(true)
                    .affectedFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK))
                    .entityAction(TRIKE_IDLE_1_ACTION)
                    .build();

    private static final EntityAction TRIKE_IDLE_2_ACTION = new EntityAction(0, (e) -> {}, 1);

    private static final StateHelper TRIKE_IDLE_2_STATE =
            StateHelper.Builder.state(IDLE_2_AC, "triceratops_head_shake")
                    .playTime(40)
                    .stopTime(120)
                    .entityAction(TRIKE_IDLE_2_ACTION)
                    .build();

    private static final EntityAction TRIKE_IDLE_3_ACTION = new EntityAction(0, (e) -> {}, 1);

    private static final StateHelper TRIKE_IDLE_3_STATE =
            StateHelper.Builder.state(IDLE_3_AC, "triceratops_chatter")
                    .playTime(60)
                    .stopTime(100)
                    .entityAction(TRIKE_IDLE_3_ACTION)
                    .build();

    // States
    @Override
    public ImmutableMap<String, StateHelper> getStates() {
        return ImmutableMap.of(
                TRIKE_IDLE_1_STATE.getName(), TRIKE_IDLE_1_STATE,
                TRIKE_IDLE_2_STATE.getName(), TRIKE_IDLE_2_STATE,
                TRIKE_IDLE_3_STATE.getName(), TRIKE_IDLE_3_STATE
        );
    }

    @Override
    public List<WeightedState<StateHelper>> getWeightedStatesToPerform() {
        return ImmutableList.of(
                WeightedState.of(TRIKE_IDLE_1_STATE, 10),
                WeightedState.of(TRIKE_IDLE_2_STATE, 12),
                WeightedState.of(TRIKE_IDLE_3_STATE, 13)
        );
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

        AnimationController<TriceratopsEntity> blend = new AnimationController<>(this, "blend", 5, this::predicate)
                .triggerableAnim("shake", TRIKE_HEAD_SHAKE)
                .triggerableAnim("chatter", TRIKE_CHATTER)
                .triggerableAnim("graze", TRIKE_GRAZE);
        blend.setSoundKeyframeHandler(this::soundListener);

        AnimationController<TriceratopsEntity> attack = new AnimationController<>(this, "attackController", 5, this::attackPredicate);
        attack.setSoundKeyframeHandler(this::soundListener);
        controllers.add(attack);
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
                if (this.isSprinting() && !this.isBaby()) {
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
            if (getBooleanState(IDLE_1_AC) && level().getBlockState(this.blockPosition().below()).is(UPBlockTags.TRIKE_GRAZING_BLOCKS)) {
                if (this.isStillEnough()) {
                    triggerAnim("blend", "graze");
                    return event.setAndContinue(TRIKE_IDLE);
                } else {
                    triggerAnim("blend", "graze");
                    return PlayState.CONTINUE;
                }
            }
            if (getBooleanState(IDLE_2_AC)) {
                if (this.isStillEnough()) {
                    triggerAnim("blend", "shake");
                    return event.setAndContinue(TRIKE_IDLE);
                } else {
                    triggerAnim("blend", "shake");
                    return PlayState.CONTINUE;
                }
            }
            if (getBooleanState(IDLE_3_AC)) {
                if (this.isStillEnough()) {
                    triggerAnim("blend", "chatter");
                    return event.setAndContinue(TRIKE_IDLE);
                } else {
                    triggerAnim("blend", "chatter");
                    return PlayState.CONTINUE;
                }
            }
            return event.setAndContinue(TRIKE_IDLE);
        }

        if (this.isInSittingPose()) {
            if(this.isStandingUp()) {
                return event.setAndContinue(TRIKE_SIT_END);
            }
            event.setAndContinue(TRIKE_SIT);
        }
        return PlayState.CONTINUE;
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
            event.getController().setAnimationSpeed(1.25F);
            return PlayState.CONTINUE;
        }
        else if (animState == 0) {
            event.getController().forceAnimationReset();
            return PlayState.STOP;
        }
        else return PlayState.CONTINUE;
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
            .add(Attributes.MAX_HEALTH, 120.0D)
            .add(Attributes.ARMOR, 12.0D)
            .add(Attributes.MOVEMENT_SPEED, 0.15D)
            .add(Attributes.ATTACK_DAMAGE, 12.0D)
            .add(Attributes.KNOCKBACK_RESISTANCE, 1.5D)
            .add(Attributes.FOLLOW_RANGE, 32D)
        ;
    }

    // Goals
    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(2, new RandomStateGoal<>(this));
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new SitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(2, new TriceratopsMeleeAttackGoal(this, 1.7F, true));
        this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 1.0D, 30));
        this.goalSelector.addGoal(1, new CustomRideGoal(this, 3D));
        this.goalSelector.addGoal(3, new PrehistoricFollowOwnerGoal(this, 1.2D, 5.0F, 2.0F, false));
        this.targetSelector.addGoal(8, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(8, new OwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(8, (new HurtByTargetGoal(this)));
        this.goalSelector.addGoal(6, new TameableTempt(this, 1.1D, TEMPTATION_ITEMS, false));
    }

    @Override
    public int getMaxHeadYRot() {
        return 15;
    }

    // Collision config
    public boolean canBeCollidedWith() {
        return UnusualPrehistoryConfig.TRIKE_COLLISON.get();
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
            this.setOrderedToSit(false);
            if (entity != null && this.isTame() && !(entity instanceof Player) && !(entity instanceof AbstractArrow)) {
                amount = (amount + 1.0F) / 3.0F;
            }
            return super.hurt(source, amount);
        }
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

    // Synched data
    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(IDLE_1_AC, false);
        this.entityData.define(IDLE_2_AC, false);
        this.entityData.define(IDLE_3_AC, false);
        this.entityData.define(SKELETAL, false);
    }

    // Heal on kill
    @Override
    protected int getKillHealAmount() {
        return 10;
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
    }

    // Travel
    @Override
    public void travel(@NotNull Vec3 pos) {
        if (this.isAlive()) {
            LivingEntity livingentity = this.getControllingPassenger();
            if (this.isVehicle() && livingentity != null) {
                this.setYRot(livingentity.getYRot());
                this.yRotO = this.getYRot();
                this.setXRot(livingentity.getXRot() * 0.5F);
                this.setRot(this.getYRot(), this.getXRot());
                this.yBodyRot = this.getYRot();
                this.yHeadRot = this.yBodyRot;
                float f = livingentity.xxa;
                float f1 = livingentity.zza;
                if (f1 <= 0.0F) {
                    f1 *= 0.25F;
                }
                if(!this.isInSittingPose()) {
                    if (this.getControllingPassenger().isSprinting()) {
                        this.setSpeed(((float) this.getAttributeValue(Attributes.MOVEMENT_SPEED) * 1.4F));
                    } else {
                        this.setSpeed(((float) this.getAttributeValue(Attributes.MOVEMENT_SPEED) * 0.5F));
                    }
                }
                super.travel(new Vec3(f, pos.y, f1));
            } else {
                super.travel(pos);
            }
        }
    }

    // Controlling passenger
    @Nullable
    public LivingEntity getControllingPassenger() {
        for (Entity passenger : this.getPassengers()) {
            if (passenger instanceof Player) {
                return (Player) passenger;
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

    // Foods
    public boolean isFood(ItemStack stack) {
        return stack.is(UPItemTags.TRICERATOPS_FOOD);
    }
    public boolean isTameFood(ItemStack stack) {
        return stack.is(UPItemTags.TRICERATOPS_TAMES);
    }

    // Mob interactions
    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {

        ItemStack itemstack = player.getItemInHand(hand);
        InteractionResult interactionresult = itemstack.interactLivingEntity(player, this, hand);
        InteractionResult type = super.mobInteract(player, hand);

        if(hand != InteractionHand.MAIN_HAND) return InteractionResult.FAIL;

        if (isTameFood(itemstack) && !this.isTame()) {

            if(random.nextBoolean() && !this.isTame()) {
                this.tame(player);
                this.level().broadcastEntityEvent(this, (byte) 7);
                this.setCommand(2);
            }
            else {
                this.level().broadcastEntityEvent(this, (byte) 6);
            }

            itemstack.shrink(1);
            this.gameEvent(GameEvent.EAT, this);
            this.playSound(this.getEatingSound(itemstack), 1.0F, 1.0F);

            return InteractionResult.SUCCESS;
        }
        else if (this.isTame() && this.isOwnedBy(player)) {
            if (this.isFood(itemstack) && this.getHealth() < this.getMaxHealth()) {

                if (!player.getAbilities().instabuild) {
                    itemstack.shrink(1);
                }
                if(!this.level().isClientSide) {
                    this.heal(6);
                }

                this.playSound(this.getEatingSound(itemstack), 1.0F, 1.0F);
                this.level().broadcastEntityEvent(this, (byte) 7);
                this.gameEvent(GameEvent.EAT, this);
                return InteractionResult.SUCCESS;

            }
            else if (itemstack.getItem() == Items.SADDLE && !this.isSaddled() && this.isTame() && this.isOwnedBy(player)) {

                this.usePlayerItem(player, hand, itemstack);
                this.playSound(SoundEvents.HORSE_SADDLE, 1.0F, 1.0F);
                this.setSaddled(true);

                return InteractionResult.SUCCESS;
            }
            else if (itemstack.getItem() == Items.SHEARS && this.isSaddled() && this.isTame() && this.isOwnedBy(player)) {

                this.playSound(SoundEvents.SHEEP_SHEAR, 1.0F, 1.0F);
                this.setSaddled(false);
                this.spawnAtLocation(Items.SADDLE);

                return InteractionResult.SUCCESS;
            }
        }
        return super.mobInteract(player, hand);
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
        this.playSound(UPSounds.MAJUNGA_STEP.get(), 0.2F, 1.0F);
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

        // Break blocks while angry
        if (this.horizontalCollision && net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.level(), this) && this.isAggressive() && !this.isTame()) {
            AABB axisalignedbb = this.getBoundingBox().inflate(0.2D);
            for (BlockPos blockpos : BlockPos.betweenClosed(Mth.floor(axisalignedbb.minX), Mth.floor(axisalignedbb.minY), Mth.floor(axisalignedbb.minZ), Mth.floor(axisalignedbb.maxX), Mth.floor(axisalignedbb.maxY), Mth.floor(axisalignedbb.maxZ))) {
                BlockState blockstate = this.level().getBlockState(blockpos);
                if (blockstate.is(UPBlockTags.TRIKE_BREAKABLES)) {
                     this.level().destroyBlock(blockpos, true, this);
                }
            }
        }
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
}
