package com.peeko32213.unusualprehistory.common.entity.custom.prehistoric;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.peeko32213.unusualprehistory.UnusualPrehistoryConfig;
import com.peeko32213.unusualprehistory.common.entity.custom.base.TamableStatedPrehistoricEntity;
import com.peeko32213.unusualprehistory.common.entity.util.navigator.SmoothGroundNavigation;
import com.peeko32213.unusualprehistory.common.entity.util.goal.CustomRideGoal;
import com.peeko32213.unusualprehistory.common.entity.util.goal.TameableStatedFollowOwner;
import com.peeko32213.unusualprehistory.common.entity.util.goal.TameableTempt;
import com.peeko32213.unusualprehistory.common.entity.util.helper.HitboxHelper;
import com.peeko32213.unusualprehistory.common.entity.util.interfaces.ICustomFollower;
import com.peeko32213.unusualprehistory.common.entity.util.interfaces.IVariantEntity;
import com.peeko32213.unusualprehistory.common.entity.animation.state.EntityAction;
import com.peeko32213.unusualprehistory.common.entity.animation.state.RandomStateGoal;
import com.peeko32213.unusualprehistory.common.entity.animation.state.StateHelper;
import com.peeko32213.unusualprehistory.common.entity.animation.state.WeightedState;
import com.peeko32213.unusualprehistory.core.registry.UPEntities;
import com.peeko32213.unusualprehistory.core.registry.UPItems;
import com.peeko32213.unusualprehistory.core.registry.UPSounds;
import com.peeko32213.unusualprehistory.core.registry.UPTags;
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
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
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
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;

public class TriceratopsEntity extends TamableStatedPrehistoricEntity implements ICustomFollower, GeoEntity, GeoAnimatable, IVariantEntity {

    private static final Ingredient TEMPTATION_ITEMS = Ingredient.of(UPTags.TRICERATOPS_FOOD);

    public float sitProgress;

    private static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(TriceratopsEntity.class, EntityDataSerializers.INT);

    private static final EntityDataAccessor<Integer> CHARGE_COOLDOWN_TICKS = SynchedEntityData.defineId(TriceratopsEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> HAS_TARGET = SynchedEntityData.defineId(TriceratopsEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> COMMAND = SynchedEntityData.defineId(TriceratopsEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> SADDLED = SynchedEntityData.defineId(TriceratopsEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> ANIMATION_STATE = SynchedEntityData.defineId(TriceratopsEntity.class, EntityDataSerializers.INT);

    public static final Logger LOGGER = LogManager.getLogger();

    // Movement animations
    private static final RawAnimation TRIKE_SWIM = RawAnimation.begin().thenLoop("animation.triceratops.swim");
    private static final RawAnimation TRIKE_WALK = RawAnimation.begin().thenLoop("animation.triceratops.walk");

    // Idle animations
    private static final RawAnimation TRIKE_IDLE = RawAnimation.begin().thenLoop("animation.triceratops.idle");
    private static final RawAnimation TRIKE_GRAZE = RawAnimation.begin().thenPlay("animation.triceratops.graze");
    private static final RawAnimation TRIKE_HEAD_SHAKE = RawAnimation.begin().thenPlay("animation.triceratops.head_shake");
    private static final RawAnimation TRIKE_CHATTER = RawAnimation.begin().thenPlay("animation.triceratops.chatter");
    private static final RawAnimation TRIKE_SIT = RawAnimation.begin().thenLoop("animation.triceratops.sit");

    // Attack animations
    private static final RawAnimation TRIKE_ATTACK = RawAnimation.begin().thenPlay("animation.triceratops.attack");
    private static final RawAnimation TRIKE_CHARGE_START = RawAnimation.begin().thenLoop("animation.triceratops.charge_start");
    private static final RawAnimation TRIKE_CHARGE = RawAnimation.begin().thenLoop("animation.triceratops.charge");

    // Idle accessors
    private static final EntityDataAccessor<Boolean> IDLE_1_AC = SynchedEntityData.defineId(TriceratopsEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> IDLE_2_AC = SynchedEntityData.defineId(TriceratopsEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> IDLE_3_AC = SynchedEntityData.defineId(TriceratopsEntity.class, EntityDataSerializers.BOOLEAN);

    // Idle actions
    private static final EntityAction TRIKE_IDLE_1_ACTION = new EntityAction(0, (e) -> {}, 1);

    private static final StateHelper TRIKE_IDLE_1_STATE =
            StateHelper.Builder.state(IDLE_1_AC, "triceratops_graze")
                    .playTime(60)
                    .stopTime(100)
                    .affectsAI(true)
                    .affectedFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK))
                    .entityAction(TRIKE_IDLE_1_ACTION)
                    .build();

    private static final EntityAction TRIKE_IDLE_2_ACTION = new EntityAction(0, (e) -> {}, 1);

    private static final StateHelper TRIKE_IDLE_2_STATE =
            StateHelper.Builder.state(IDLE_2_AC, "triceratops_head_shake")
                    .playTime(40)
                    .stopTime(90)
                    .entityAction(TRIKE_IDLE_2_ACTION)
                    .build();

    private static final EntityAction TRIKE_IDLE_3_ACTION = new EntityAction(0, (e) -> {}, 1);

    private static final StateHelper TRIKE_IDLE_3_STATE =
            StateHelper.Builder.state(IDLE_3_AC, "triceratops_chatter")
                    .playTime(40)
                    .stopTime(80)
                    .entityAction(TRIKE_IDLE_3_ACTION)
                    .build();

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

    @Override
    public boolean getAction() {
        return false;
    }

    @Override
    public void setAction(boolean action) {}

    public TriceratopsEntity(EntityType<? extends TamableStatedPrehistoricEntity> entityType, Level level) {
        super(entityType, level);
        this.setMaxUpStep(1.25F);
    }

    @Override
    protected @NotNull PathNavigation createNavigation(Level levelIn) {
        return new SmoothGroundNavigation(this, levelIn);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
            .add(Attributes.MAX_HEALTH, 120.0D)
            .add(Attributes.ARMOR, 12.0D)
            .add(Attributes.MOVEMENT_SPEED, 0.145D)
            .add(Attributes.ATTACK_DAMAGE, 12.0D)
            .add(Attributes.KNOCKBACK_RESISTANCE, 1.5D);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(2, new RandomStateGoal<>(this));
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new TrikeMeleeAttackGoal(this, 1.75D, false));
        this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 1.0D, 28));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(8, (new HurtByTargetGoal(this)));
        this.targetSelector.addGoal(2, new TrikeNearestAttackablePlayerTargetGoal(this));
        this.goalSelector.addGoal(0, new SitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(1, new CustomRideGoal(this, 3D));
        this.goalSelector.addGoal(3, new TameableStatedFollowOwner(this, 1.2D, 5.0F, 2.0F, false));
        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
        this.goalSelector.addGoal(5, new TameableTempt(this, 1.1D, TEMPTATION_ITEMS, false));
    }

    @Override
    public int getMaxHeadYRot() {
        return 15;
    }

    @Override
    public boolean doHurtTarget(Entity entityIn) {
        this.level().broadcastEntityEvent(this, (byte) 4);
        return super.doHurtTarget(entityIn);
    }

    public boolean canBeCollidedWith() {
        return UnusualPrehistoryConfig.TRIKE_COLLISON.get();
    }

    protected SoundEvent getAmbientSound() {
        return UPSounds.TRIKE_IDLE.get();
    }

    protected SoundEvent getHurtSound(@NotNull DamageSource damageSourceIn) {
        return UPSounds.TRIKE_HURT.get();
    }

    protected SoundEvent getDeathSound() {
        return UPSounds.TRIKE_DEATH.get();
    }

    @Override
    public float getSoundVolume() {
        return 0.75F;
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel serverLevel, @NotNull AgeableMob ageableMob) {
        TriceratopsEntity trike = UPEntities.TRICERATOPS.get().create(serverLevel);
        assert trike != null;
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

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(IDLE_1_AC, false);
        this.entityData.define(IDLE_2_AC, false);
        this.entityData.define(IDLE_3_AC, false);
        this.entityData.define(VARIANT, 0);
        this.entityData.define(CHARGE_COOLDOWN_TICKS, 0);
        this.entityData.define(HAS_TARGET, false);
        this.entityData.define(COMMAND, 0);
        this.entityData.define(SADDLED, Boolean.FALSE);
        this.entityData.define(ANIMATION_STATE, 0);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("Variant", this.getVariant());
        compound.putBoolean("Saddle", this.isSaddled());
        compound.putInt("TrikeCommand", this.getCommand());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setVariant(compound.getInt("Variant"));
        this.setSaddled(compound.getBoolean("Saddle"));
        this.setCommand(compound.getInt("TrikeCommand"));
    }

    public int getCommand() {
        return this.entityData.get(COMMAND);
    }

    public void setCommand(int command) {
        this.entityData.set(COMMAND, command);
    }

    @Override
    protected SoundEvent getAttackSound() {
        return null;
    }

    @Override
    protected int getKillHealAmount() {
        return 10;
    }

    @Override
    protected boolean canGetHungry() {
        return false;
    }

    @Override
    protected boolean hasTargets() {
        return false;
    }

    @Override
    protected boolean hasAvoidEntity() {
        return false;
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
        return null;
    }

    public void tick() {
        super.tick();

        if (this.isOrderedToSit() && sitProgress < 5F) {
            sitProgress++;
        }
        if (!this.isOrderedToSit() && sitProgress > 0F) {
            sitProgress--;
        }
        this.setOrderedToSit(this.getCommand() == 2 && !this.isVehicle());
    }

    @Override
    protected void performAttack() {}

    public boolean isAlliedTo(@NotNull Entity entityIn) {
        if (this.isTame()) {
            LivingEntity livingentity = this.getOwner();
            if (entityIn == livingentity) {
                return true;
            }
            if (entityIn instanceof TamableAnimal) {
                assert livingentity != null;
                return ((TamableAnimal) entityIn).isOwnedBy(livingentity);
            }
            if (livingentity != null) {
                return livingentity.isAlliedTo(entityIn);
            }
        }

        return entityIn.is(this);
    }

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
                if(Objects.requireNonNull(this.getControllingPassenger()).isSprinting()) {
                    this.setSpeed(((float) this.getAttributeValue(Attributes.MOVEMENT_SPEED) * 1.4F));
                } else {
                    this.setSpeed(((float) this.getAttributeValue(Attributes.MOVEMENT_SPEED) * 0.5F));
                }
                super.travel(new Vec3(f, pos.y, f1));
            } else {
                super.travel(pos);
            }
        }
    }

    public boolean canSprint() {
        return true;
    }

    @Override
    public float getStepHeight() {
        return 1.25F;
    }

    @Override
    protected void positionRider(Entity pPassenger, @NotNull MoveFunction pCallback) {
        float ySin = Mth.sin(this.yBodyRot * ((float) Math.PI / 180F));
        float yCos = Mth.cos(this.yBodyRot * ((float) Math.PI / 180F));
        pPassenger.setPos(this.getX() + (double) (0.15F * ySin), this.getY() + this.getPassengersRidingOffset() + pPassenger.getMyRidingOffset() + 0.4F, this.getZ() - (double) (0.15F * yCos));
    }

    public double getPassengersRidingOffset() {
        return 2.85;
    }

    public boolean isFood(ItemStack stack) {
        return stack.is(UPTags.TRICERATOPS_FOOD);
    }

    public boolean isTameFood(ItemStack stack) {
        return stack.is(UPTags.TRICERATOPS_TAMES);
    }

    public @NotNull InteractionResult mobInteract(Player player, @NotNull InteractionHand hand) {

        ItemStack itemstack = player.getItemInHand(hand);

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
        } else if (this.isTame() && this.isOwnedBy(player)) {
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

            } else if (itemstack.getItem() == Items.SADDLE && !this.isSaddled() && this.isTame() && this.isOwnedBy(player)) {

                this.usePlayerItem(player, hand, itemstack);
                this.playSound(SoundEvents.HORSE_SADDLE, 1.0F, 1.0F);
                this.setSaddled(true);

                return InteractionResult.SUCCESS;
            } else if (itemstack.getItem() == Items.SHEARS && this.isSaddled() && this.isTame() && this.isOwnedBy(player)) {

                this.playSound(SoundEvents.SHEEP_SHEAR, 1.0F, 1.0F);
                this.setSaddled(false);
                this.spawnAtLocation(Items.SADDLE);

                return InteractionResult.SUCCESS;
            } else if(this.isTame() && this.isOwnedBy(player)) {
                if (!player.isShiftKeyDown() && !this.isBaby() && this.isSaddled() && this.isTame() && this.isOwnedBy(player)) {
                    if(!this.level().isClientSide) {
                        player.startRiding(this);
                    }
                } else if(this.isTame() && this.isOwnedBy(player)){
                    this.setCommand((this.getCommand() + 1) % 3);
                    player.displayClientMessage(Component.translatable("entity.unusualprehistory.all.command_" + this.getCommand(), this.getName()), true);
                    boolean sit = this.getCommand() == 2;
                    this.setOrderedToSit(sit);

                    if (this.getCommand() == 3) {
                        this.setCommand(0);
                    }
                }
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }

    public boolean isSaddled() {
        return this.entityData.get(SADDLED);
    }

    public void setSaddled(boolean saddled) {
        this.entityData.set(SADDLED, saddled);
    }

    private boolean isWithinYRange(LivingEntity target) {
        if (target == null) {
            return false;
        }
        return Math.abs(target.getY() - this.getY()) < 3;
    }

    @Override
    public ResourceLocation getVariantTexture() {
        return null;
    }

    public int getVariant() {
        return this.entityData.get(VARIANT);
    }

    public void setVariant(int variant) {
        this.entityData.set(VARIANT, variant);
    }

    public int getAnimationState() {
        return this.entityData.get(ANIMATION_STATE);
    }

    public void setAnimationState(int anim) {
        this.entityData.set(ANIMATION_STATE, anim);
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

    class TrikeMeleeAttackGoal extends Goal {

        protected final TriceratopsEntity mob;
        private final double speedModifier;
        private final boolean followingTargetEvenIfNotSeen;
        private Path path;
        private double pathedTargetX;
        private double pathedTargetY;
        private double pathedTargetZ;
        private int ticksUntilNextPathRecalculation;
        private int ticksUntilNextAttack;
        private long lastCanUseCheck;
        private int failedPathFindingPenalty = 0;
        private boolean canPenalize = false;
        private int animTime = 0;

        public TrikeMeleeAttackGoal(TriceratopsEntity p_i1636_1_, double p_i1636_2_, boolean p_i1636_4_) {
            this.mob = p_i1636_1_;
            this.speedModifier = p_i1636_2_;
            this.followingTargetEvenIfNotSeen = p_i1636_4_;
        }

        public boolean canUse() {
            long i = this.mob.level().getGameTime();

            if (i - this.lastCanUseCheck < 20L) {
                return false;
            } else {
                this.lastCanUseCheck = i;
                LivingEntity livingentity = this.mob.getTarget();
                if (livingentity == null) {
                    return false;
                } else if (!livingentity.isAlive()) {
                    return false;
                } else {
                    if (canPenalize) {
                        if (--this.ticksUntilNextPathRecalculation <= 0) {
                            this.path = this.mob.getNavigation().createPath(livingentity, 0);
                            this.ticksUntilNextPathRecalculation = 4 + this.mob.getRandom().nextInt(7);
                            return this.path != null;
                        } else {
                            return true;
                        }
                    }
                    this.path = this.mob.getNavigation().createPath(livingentity, 0);
                    if (this.path != null) {
                        return true;
                    } else {
                        return this.getAttackReachSqr(livingentity) >= this.mob.distanceToSqr(livingentity.getX(), livingentity.getY(), livingentity.getZ());
                    }
                }
            }
        }

        public boolean canContinueToUse() {

            LivingEntity livingentity = this.mob.getTarget();

            if (livingentity == null) {
                return false;
            }
            else if (!livingentity.isAlive()) {
                return false;
            }
            else if (!this.followingTargetEvenIfNotSeen) {
                return !this.mob.getNavigation().isDone();
            }
            else if (!this.mob.isWithinRestriction(livingentity.blockPosition())) {
                return false;
            }
            else {
                return !(livingentity instanceof Player) || !livingentity.isSpectator() && !((Player) livingentity).isCreative();
            }
        }

        public void start() {
            this.mob.getNavigation().moveTo(this.path, this.speedModifier);
            this.mob.setAggressive(true);
            this.ticksUntilNextPathRecalculation = 0;
            this.ticksUntilNextAttack = 0;
            this.animTime = 0;
            this.mob.setAnimationState(0);
        }

        public void stop() {
            LivingEntity livingentity = this.mob.getTarget();
            if (!EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(livingentity)) {
                this.mob.setTarget(null);
            }
            this.mob.setAggressive(false);
            this.mob.getNavigation().stop();
            this.mob.setAnimationState(0);
        }

        public void tick() {

            LivingEntity target = this.mob.getTarget();
            assert target != null;
            double distance = this.mob.distanceToSqr(target.getX(), target.getY(), target.getZ());
            double reach = this.getAttackReachSqr(target);
            int animState = this.mob.getAnimationState();

            if (animState == 1) {
                tickSlashAttack();
            }

            else {
                this.ticksUntilNextPathRecalculation = Math.max(this.ticksUntilNextPathRecalculation - 1, 0);
                this.ticksUntilNextAttack = Math.max(this.ticksUntilNextPathRecalculation - 1, 0);
                this.mob.getLookControl().setLookAt(target, 30.0F, 30.0F);
                this.doMovement(target, distance);
                this.checkForCloseRangeAttack(distance, reach);
            }
        }

        protected void doMovement(LivingEntity livingentity, Double d0) {

            this.ticksUntilNextPathRecalculation = Math.max(this.ticksUntilNextPathRecalculation - 1, 0);

            if ((this.followingTargetEvenIfNotSeen || this.mob.getSensing().hasLineOfSight(livingentity)) && this.ticksUntilNextPathRecalculation <= 0 && (this.pathedTargetX == 0.0D && this.pathedTargetY == 0.0D && this.pathedTargetZ == 0.0D || livingentity.distanceToSqr(this.pathedTargetX, this.pathedTargetY, this.pathedTargetZ) >= 1.0D || this.mob.getRandom().nextFloat() < 0.05F)) {
                this.pathedTargetX = livingentity.getX();
                this.pathedTargetY = livingentity.getY();
                this.pathedTargetZ = livingentity.getZ();
                this.ticksUntilNextPathRecalculation = 4 + this.mob.getRandom().nextInt(7);
                if (this.canPenalize) {
                    this.ticksUntilNextPathRecalculation += failedPathFindingPenalty;
                    if (this.mob.getNavigation().getPath() != null) {
                        Node finalPathPoint = this.mob.getNavigation().getPath().getEndNode();
                        if (finalPathPoint != null && livingentity.distanceToSqr(finalPathPoint.x, finalPathPoint.y, finalPathPoint.z) < 1)
                            failedPathFindingPenalty = 0;
                        else
                            failedPathFindingPenalty += 10;
                    } else {
                        failedPathFindingPenalty += 10;
                    }
                }
                if (d0 > 1024.0D) {
                    this.ticksUntilNextPathRecalculation += 10;
                } else if (d0 > 256.0D) {
                    this.ticksUntilNextPathRecalculation += 5;
                }
                if (!this.mob.getNavigation().moveTo(livingentity, this.speedModifier)) {
                    this.ticksUntilNextPathRecalculation += 15;
                }
            }

        }

        protected void checkForCloseRangeAttack(double distance, double reach) {
            if (distance <= reach && this.ticksUntilNextAttack <= 0) {
                this.mob.setAnimationState(1);
            }
        }

        protected void tickSlashAttack () {

            triggerAnim("blend", "attack");

            animTime++;

            if (animTime <= 3) {
                this.mob.lookAt(Objects.requireNonNull(this.mob.getTarget()), 100000, 100000);
                this.mob.yBodyRot = this.mob.yHeadRot;
            }

            if(animTime==5) {
                preformSlashAttack();
            }

            if(animTime>=8) {
                animTime=0;
                this.mob.setAnimationState(0);
                this.resetAttackCooldown();
                this.ticksUntilNextPathRecalculation = 0;
            }
        }

        protected void preformSlashAttack () {
            Vec3 pos = mob.position();
            this.mob.playSound(UPSounds.PACHY_HEADBUTT.get(), 1.0F, 1.0F);
            this.mob.swing(InteractionHand.MAIN_HAND);
            HitboxHelper.LargeAttackWithTargetCheck(this.mob.damageSources().mobAttack(mob), (float) Objects.requireNonNull(mob.getAttribute(Attributes.ATTACK_DAMAGE)).getValue(), 1.5f, mob, pos,  5.5F, -Math.PI/2, Math.PI/2, -1.0f, 3.0f);
        }

        protected void resetAttackCooldown() {
            this.ticksUntilNextAttack = this.adjustedTickDelay(20);
        }

        protected boolean isTimeToAttack() {
            return this.ticksUntilNextAttack <= 0;
        }

        protected int getTicksUntilNextAttack() {
            return this.ticksUntilNextAttack;
        }

        protected int getAttackInterval() {
            return this.adjustedTickDelay(20);
        }

        protected double getAttackReachSqr(LivingEntity pAttackTarget) {
            return this.mob.getBbWidth() * 2.0F * this.mob.getBbWidth() * 2.0F + pAttackTarget.getBbWidth();
        }
    }

    protected void ageBoundaryReached() {
        super.ageBoundaryReached();
        if (!this.isBaby() && this.level().getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT)) {
            this.spawnAtLocation(UPItems.TRIKE_HORN.get(), 1);
        }

    }

    @Override
    public void killed() {
        super.killed();
    }

    @Override
    public float getVoicePitch() {
        return (this.random.nextFloat() - this.random.nextFloat()) * 0.2f + 1.0f;
    }

    protected void playStepSound(@NotNull BlockPos p_28301_, @NotNull BlockState p_28302_) {
        this.playSound(UPSounds.MAJUNGA_STEP.get(), 0.1F, 1.0F);
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

        if (this.horizontalCollision && net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.level(), this) && this.isAggressive() && !this.isTame()) {
            boolean flag = false;
            AABB axisalignedbb = this.getBoundingBox().inflate(0.2D);
            for (BlockPos blockpos : BlockPos.betweenClosed(Mth.floor(axisalignedbb.minX), Mth.floor(axisalignedbb.minY), Mth.floor(axisalignedbb.minZ), Mth.floor(axisalignedbb.maxX), Mth.floor(axisalignedbb.maxY), Mth.floor(axisalignedbb.maxZ))) {
                BlockState blockstate = this.level().getBlockState(blockpos);
                if (blockstate.is(UPTags.TRIKE_BREAKABLES)) {
                    flag = this.level().destroyBlock(blockpos, true, this) || flag;
                }
            }
        }
    }

    @Override
    public void handleEntityEvent(byte pId) {
        super.handleEntityEvent(pId);
    }

    @Override
    public void ate() {
        if (this.isBaby()) {
            this.ageUp(60);
        }
    }

    @Override
    public void registerControllers(final AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", 5, this::Controller));
        controllers.add(new AnimationController<>(this, "blend", 5, this::Controller)
                .triggerableAnim("shake", TRIKE_HEAD_SHAKE)
                .triggerableAnim("chatter", TRIKE_CHATTER)
                .triggerableAnim("attack", TRIKE_ATTACK));
    }

    protected <E extends TriceratopsEntity> PlayState Controller(final software.bernie.geckolib.core.animation.AnimationState<E> event) {

        if (this.isFromBook()) {
            return event.setAndContinue(TRIKE_IDLE);
        }

        if (this.isInWater() || this.isSwimming()) {
            event.setAndContinue(TRIKE_SWIM);
            event.getController().setAnimationSpeed(1.0F);
            return PlayState.CONTINUE;
        }

        else if(this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6 && !this.isSwimming() && !this.isInWater() && !this.hasControllingPassenger() && !this.isInSittingPose()){
            if(this.isSprinting() && !this.isBaby()) {
                event.setAndContinue(TRIKE_CHARGE);
                event.getController().setAnimationSpeed(1.75F);
            } else {
                event.setAndContinue(TRIKE_WALK);
                event.getController().setAnimationSpeed(1.0F);
            }
            return PlayState.CONTINUE;
        }

        else if(this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6 && !this.isSwimming() && !this.isInWater() && this.hasControllingPassenger() && !this.isInSittingPose()){

            if(Objects.requireNonNull(this.getControllingPassenger()).isSprinting()){
                event.setAndContinue(TRIKE_CHARGE);
                event.getController().setAnimationSpeed(1.8F);
            }
            else {
                event.setAndContinue(TRIKE_WALK);
                event.getController().setAnimationSpeed(1.5F);
            }

            return PlayState.CONTINUE;
        }

        if (this.isInSittingPose()) {
            event.setAndContinue(TRIKE_SIT);
            event.getController().setAnimationSpeed(1.0F);
            return PlayState.CONTINUE;
        }

        if (!this.isInWater()) {
            if (getBooleanState(IDLE_1_AC)) {
                return event.setAndContinue(TRIKE_GRAZE);
            }
            if (getBooleanState(IDLE_2_AC)) {
                triggerAnim("blend", "shake");
                return PlayState.CONTINUE;
            }
            if (getBooleanState(IDLE_3_AC)) {
                triggerAnim("blend", "chatter");
                return PlayState.CONTINUE;
            }
            return event.setAndContinue(TRIKE_IDLE);
        }

        event.getController().setAnimationSpeed(1.0F);
        return PlayState.CONTINUE;
    }

    static class TrikeNearestAttackablePlayerTargetGoal extends NearestAttackableTargetGoal<Player> {
        private final TriceratopsEntity trike;

        public TrikeNearestAttackablePlayerTargetGoal(TriceratopsEntity mob) {
            super(mob, Player.class, 10, true, true, EntitySelector.NO_CREATIVE_OR_SPECTATOR::test);
            this.trike = mob;
        }

        @Override
        public boolean canUse() {
            if (this.trike.isBaby()) {
                return false;
            }
            if (super.canUse()) {
                if (!trike.isWithinYRange(target)) {
                    return false;
                }
                List<TriceratopsEntity> nearbyEntities = this.trike.level().getEntitiesOfClass(TriceratopsEntity.class, this.trike.getBoundingBox().inflate(8.0, 4.0, 8.0));
                for (TriceratopsEntity mob : nearbyEntities) {
                    if (!mob.isBaby()) continue;
                    return true;
                }
            }
            return false;
        }
    }

    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_28134_, DifficultyInstance p_28135_, MobSpawnType p_28136_, @Nullable SpawnGroupData p_28137_, @Nullable CompoundTag p_28138_) {
        p_28137_ = super.finalizeSpawn(p_28134_, p_28135_, p_28136_, p_28137_, p_28138_);
        Level level = p_28134_.getLevel();
        if (level instanceof ServerLevel) {
            this.setPersistenceRequired();
        }
        if (random.nextBoolean()) {
            this.setVariant(1);
        }
        else {
            this.setVariant(0);
        }
        return p_28137_;
    }

    @Override
    public boolean shouldFollow() {
        return this.getCommand() == 1;
    }
}
