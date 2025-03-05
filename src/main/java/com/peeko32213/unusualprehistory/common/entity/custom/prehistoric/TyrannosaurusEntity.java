package com.peeko32213.unusualprehistory.common.entity.custom.prehistoric;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.peeko32213.unusualprehistory.UnusualPrehistoryConfig;
import com.peeko32213.unusualprehistory.common.entity.custom.base.StatedPrehistoricEntity;
import com.peeko32213.unusualprehistory.common.entity.util.goal.StatedSleepingGoal;
import com.peeko32213.unusualprehistory.common.entity.util.helper.HitboxHelper;
import com.peeko32213.unusualprehistory.common.entity.util.interfaces.IVariantEntity;
import com.peeko32213.unusualprehistory.common.entity.animation.state.EntityAction;
import com.peeko32213.unusualprehistory.common.entity.animation.state.RandomStateGoal;
import com.peeko32213.unusualprehistory.common.entity.animation.state.StateHelper;
import com.peeko32213.unusualprehistory.common.entity.animation.state.WeightedState;
import com.peeko32213.unusualprehistory.core.registry.*;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.keyframe.event.SoundKeyframeEvent;
import software.bernie.geckolib.core.object.PlayState;

import javax.annotation.Nonnull;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;

public class TyrannosaurusEntity extends StatedPrehistoricEntity implements GeoEntity, GeoAnimatable, IVariantEntity {

    private static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(TyrannosaurusEntity.class, EntityDataSerializers.INT);

    private static final EntityDataAccessor<Integer> ANIMATION_STATE = SynchedEntityData.defineId(TyrannosaurusEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> EEPY = SynchedEntityData.defineId(TyrannosaurusEntity.class, EntityDataSerializers.BOOLEAN);

    private static final EntityDataAccessor<Boolean> PASSIVE = SynchedEntityData.defineId(TyrannosaurusEntity.class, EntityDataSerializers.BOOLEAN);

    private int shakeCooldown = 0;

    // Movement animations
    private static final RawAnimation TYRANNO_SWIM = RawAnimation.begin().thenLoop("animation.tyrannosaurus.swim");
    private static final RawAnimation TYRANNO_CHARGE = RawAnimation.begin().thenLoop("animation.tyrannosaurus.charge");
    private static final RawAnimation TYRANNO_WALK = RawAnimation.begin().thenLoop("animation.tyrannosaurus.walk");

    // Attack animations
    private static final RawAnimation TYRANNO_BITE_0 = RawAnimation.begin().thenPlay("animation.tyrannosaurus.bite_0");
    private static final RawAnimation TYRANNO_BITE_1 = RawAnimation.begin().thenPlay("animation.tyrannosaurus.bite_1");
    private static final RawAnimation TYRANNO_WHIP = RawAnimation.begin().thenPlay("animation.tyrannosaurus.whip");
    private static final RawAnimation TYRANNO_STOMP_L = RawAnimation.begin().thenPlay("animation.tyrannosaurus.stompl");
    private static final RawAnimation TYRANNO_STOMP_R = RawAnimation.begin().thenPlay("animation.tyrannosaurus.stompr");
    private static final RawAnimation TYRANNO_TACKLE = RawAnimation.begin().thenPlay("animation.tyrannosaurus.tackle");

    // Idle animations
    private static final RawAnimation TYRANNO_IDLE = RawAnimation.begin().thenLoop("animation.tyrannosaurus.idle");
    private static final RawAnimation TYRANNO_SIT = RawAnimation.begin().thenLoop("animation.tyrannosaurus.sit");
    private static final RawAnimation TYRANNO_SLEEP = RawAnimation.begin().thenLoop("animation.tyrannosaurus.sleep");
    private static final RawAnimation TYRANNO_SHAKE = RawAnimation.begin().thenPlay("animation.tyrannosaurus.shake");
    private static final RawAnimation TYRANNO_SNIFF = RawAnimation.begin().thenPlay("animation.tyrannosaurus.sniff");
    private static final RawAnimation TYRANNO_ROAR = RawAnimation.begin().thenPlay("animation.tyrannosaurus.roar");
    private static final RawAnimation TYRANNO_EEPY = RawAnimation.begin().thenLoop("animation.tyrannosaurus.knockout");

    // Idle accessors
    private static final EntityDataAccessor<Boolean> IDLE_1_AC = SynchedEntityData.defineId(TyrannosaurusEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> IDLE_2_AC = SynchedEntityData.defineId(TyrannosaurusEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> IDLE_3_AC = SynchedEntityData.defineId(TyrannosaurusEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> IDLE_4_AC = SynchedEntityData.defineId(TyrannosaurusEntity.class, EntityDataSerializers.BOOLEAN);

    // Idle actions
    private static final EntityAction TYRANNO_IDLE_1_ACTION = new EntityAction(0, (e) -> {}, 1);

    private static final StateHelper TYRANNO_IDLE_1_STATE =
            StateHelper.Builder.state(IDLE_1_AC, "tyrannosaurus_shake")
                    .playTime(90)
                    .stopTime(200)
                    .entityAction(TYRANNO_IDLE_1_ACTION)
                    .build();

    private static final EntityAction TYRANNO_IDLE_2_ACTION = new EntityAction(0, (e) -> {}, 1);

    private static final StateHelper TYRANNO_IDLE_2_STATE =
            StateHelper.Builder.state(IDLE_2_AC, "tyrannosaurus_sniff")
                    .playTime(60)
                    .stopTime(160)
                    .entityAction(TYRANNO_IDLE_2_ACTION)
                    .build();

    private static final EntityAction TYRANNO_IDLE_3_ACTION = new EntityAction(0, (e) -> {}, 1);

    private static final StateHelper TYRANNO_IDLE_3_STATE =
            StateHelper.Builder.state(IDLE_3_AC, "tyrannosaurus_roar")
                    .playTime(80)
                    .stopTime(220)
                    .entityAction(TYRANNO_IDLE_3_ACTION)
                    .build();

    private static final EntityAction TYRANNO_IDLE_4_ACTION = new EntityAction(0, (e) -> {}, 1);

    private static final StateHelper TYRANNO_IDLE_4_STATE =
            StateHelper.Builder.state(IDLE_4_AC, "tyrannosaurus_sit")
                .playTime(240)
                .stopTime(300)
                .affectsAI(true)
                .affectedFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK))
                .entityAction(TYRANNO_IDLE_4_ACTION)
                .build();

    @Override
    public ImmutableMap<String, StateHelper> getStates() {
        return ImmutableMap.of(
                TYRANNO_IDLE_1_STATE.getName(), TYRANNO_IDLE_1_STATE,
                TYRANNO_IDLE_2_STATE.getName(), TYRANNO_IDLE_2_STATE,
                TYRANNO_IDLE_3_STATE.getName(), TYRANNO_IDLE_3_STATE,
                TYRANNO_IDLE_4_STATE.getName(), TYRANNO_IDLE_4_STATE
        );
    }

    @Override
    public List<WeightedState<StateHelper>> getWeightedStatesToPerform() {
        return ImmutableList.of(
                WeightedState.of(TYRANNO_IDLE_1_STATE, 11),
                WeightedState.of(TYRANNO_IDLE_2_STATE, 12),
                WeightedState.of(TYRANNO_IDLE_3_STATE, 9),
                WeightedState.of(TYRANNO_IDLE_4_STATE, 5)
        );
    }

    @Override
    public boolean getAction() {
        return false;
    }

    @Override
    public void setAction(boolean action) {
    }

    public TyrannosaurusEntity(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
        this.setMaxUpStep(1.25F);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
            .add(Attributes.MAX_HEALTH, 300.0D)
            .add(Attributes.MOVEMENT_SPEED, 0.2D)
            .add(Attributes.ATTACK_DAMAGE, 16.0D)
            .add(Attributes.KNOCKBACK_RESISTANCE, 1.5D)
            .add(Attributes.FOLLOW_RANGE, 32D);
    }

    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new StatedSleepingGoal(this));
        if(!this.isAsleep() && !this.hasEepy() && !this.hasTargets()) {
            this.goalSelector.addGoal(2, new RandomStateGoal<>(this));
        }
        this.goalSelector.addGoal(0, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(3, new TyrannosaurusEntity.TyrannosaurusMeleeAttackGoal(this, 1.5F, true) {
                public boolean canUse() {
                    return !isBaby() && level().getDifficulty() != Difficulty.PEACEFUL && !hasEepy() && !isPassive() && super.canUse();
                }
            });
        this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 1.0D, 20));
        this.targetSelector.addGoal(9, (new HurtByTargetGoal(this) {
                public boolean canUse() {
                    return !hasEepy() && !isBaby() && !hasEepy() && !isPassive() && super.canUse();
                }
            }));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, LivingEntity.class, 10, false, false, entity -> entity.getType().is(UPTags.TYRANNOSAURUS_TARGETS)) {
                public boolean canUse() {
                    return !hasEepy() && !isBaby() && !isPassive() && !hasEepy() && !isSleeping() && super.canUse();
                }
            });
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0f));
        this.goalSelector.addGoal(4, new AvoidEntityGoal<>(this, Chicken.class, 12.0F, 2.0D, 2.0D, EntitySelector.NO_SPECTATORS::test));
    }

    public @NotNull InteractionResult mobInteract(@Nonnull Player player, @Nonnull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        Item item = itemstack.getItem();
        if(hand != InteractionHand.MAIN_HAND) return InteractionResult.FAIL;
        if(item == UPItems.ADORNED_STAFF.get() && this.hasEepy()) {
            itemstack.hurtAndBreak(1, player, (p_29822_) -> {
                p_29822_.broadcastBreakEvent(hand);
            });
            if(!this.level().isClientSide) {
                if(!this.isPassive()) {
                    this.spawnAtLocation(new ItemStack(UPItems.REX_SCALE.get(), random.nextInt(10)), 2);
                    this.spawnAtLocation(new ItemStack(UPItems.REX_TOOTH.get(), random.nextInt(5)), 2);
                }
                this.heal(300);
                this.level().broadcastEntityEvent(this, (byte) 18);
                this.playSound(UPSounds.TYRANNO_REVIVE.get(), 1.0F, 1.0F);
                this.setTarget(null);
                this.setEepy(false);
                this.setPassive(true);
                player.displayClientMessage(Component.translatable("entity.tyrannosaurus.revive.message" + this.getName()).withStyle(ChatFormatting.GOLD), true);
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.FAIL;
    }

    @Override
    public boolean isNoAi() {
        return this.hasEepy() || super.isNoAi();
    }

    @Override
    public boolean canBeLeashed(@NotNull Player p_21418_) {
        return this.isBaby();
    }

    @Override
    protected SoundEvent getAttackSound() {
        return null;
    }

    @Override
    protected int getKillHealAmount() {
        return 30;
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
        return true;
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

    public void setEepy(boolean eepy) {
        this.entityData.set(EEPY, eepy);
    }

    public boolean hasEepy() {
        return this.entityData.get(EEPY);
    }

    public boolean shouldBeEepy() {
        if(!this.isBaby() && !this.isPassive()) {
            return this.getHealth() <= this.getMaxHealth() / 12.0F;
        }
        else {
            return false;
        }
    }

    public void setPassive(boolean passive) {
        this.entityData.set(PASSIVE, passive);
    }

    public boolean isPassive() {
        return this.entityData.get(PASSIVE);
    }

    public void travel(@NotNull Vec3 vec3d) {
        super.travel(vec3d);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("Variant", this.getVariant());
        compound.putBoolean("Eepy", this.hasEepy());
        compound.putBoolean("Passive", this.isPassive());
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setVariant(compound.getInt("Variant"));
        this.setEepy(compound.getBoolean("Eepy"));
        this.setPassive(compound.getBoolean("Passive"));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(IDLE_1_AC, false);
        this.entityData.define(IDLE_2_AC, false);
        this.entityData.define(IDLE_3_AC, false);
        this.entityData.define(IDLE_4_AC, false);
        this.entityData.define(VARIANT, 0);
        this.entityData.define(ANIMATION_STATE, 0);
        this.entityData.define(EEPY, false);
        this.entityData.define(PASSIVE, false);
    }

    public void tick() {
        super.tick();

        if (this.shouldBeEepy()) {
            this.setEepy(true);
            this.setTarget(null);
        }

        if(this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6 && !this.isSwimming() && !this.isInWater() && !this.hasEepy() && !this.isBaby() && this.onGround()) {

            if(this.shakeCooldown <= 0 && UnusualPrehistoryConfig.SCREEN_SHAKE_REX.get()) {

                double rexShakeRange = UnusualPrehistoryConfig.SCREEN_SHAKE_REX_RANGE.get();
                int rexShakeAmp= UnusualPrehistoryConfig.SCREEN_SHAKE_REX_AMPLIFIER.get();

                List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(rexShakeRange));
                for (LivingEntity e : list) {
                    if (e instanceof Player) {
                        e.addEffect(new MobEffectInstance(UPEffects.SCREEN_SHAKE.get(), 6, rexShakeAmp, false, false, false));
                        this.playSound(UPSounds.TYRANNO_STEP.get(), 1.0F, 1.0F);
                    }
                }
                if(!this.isSprinting()) {
                    shakeCooldown = 23;
                }
                else shakeCooldown = 11;
            }
        }
        shakeCooldown--;
    }

    @Override
    public void customServerAiStep() {
        if (this.getMoveControl().hasWanted() && !this.isBaby()) {
            this.setSprinting(this.getMoveControl().getSpeedModifier() >= 1.5D);
        } else {
            this.setSprinting(false);
        }

        if (this.isAsleep()) {
            this.navigation.stop();
            this.navigation.setSpeedModifier(0);
        }
        super.customServerAiStep();
    }

    @Override
    public void aiStep() {
        super.aiStep();

        if (this.shouldBeEepy()) {
            this.setEepy(true);
            this.setAggressive(false);
            this.setTarget(null);
        }

        if (this.horizontalCollision && net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.level(), this) && this.isSprinting() && !this.isBaby()) {
            boolean flag = false;
            AABB axisalignedbb = this.getBoundingBox().inflate(0.2D);
            for (BlockPos blockpos : BlockPos.betweenClosed(Mth.floor(axisalignedbb.minX), Mth.floor(axisalignedbb.minY), Mth.floor(axisalignedbb.minZ), Mth.floor(axisalignedbb.maxX), Mth.floor(axisalignedbb.maxY), Mth.floor(axisalignedbb.maxZ))) {
                BlockState blockstate = this.level().getBlockState(blockpos);
                if (blockstate.is(UPTags.TYRANNO_BREAKABLES)) {
                    flag = this.level().destroyBlock(blockpos, true, this) || flag;
                }
            }
        }

        if (this.isAsleep()) {
            this.navigation.stop();
            this.navigation.setSpeedModifier(0);
        }
    }

    @Override
    public int getMaxHeadYRot() {
        return 15;
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel serverLevel, @NotNull AgeableMob ageableMob) {
        TyrannosaurusEntity tyrannosaurus = UPEntities.TYRANNOSAURUS.get().create(serverLevel);
        assert tyrannosaurus != null;
        tyrannosaurus.setVariant(this.getVariant());
        return tyrannosaurus;
    }

    protected void ageBoundaryReached() {
        super.ageBoundaryReached();
        if (!this.isBaby() && this.level().getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT)) {
            this.spawnAtLocation(UPItems.REX_SCALE.get(), 1);
        }
    }

    public boolean requiresCustomPersistence() {
        return super.requiresCustomPersistence() || this.hasCustomName();
    }

    public boolean removeWhenFarAway(double d) {
        return !this.hasCustomName();
    }

    public int getAnimationState() {
        return this.entityData.get(ANIMATION_STATE);
    }

    public void setAnimationState(int anim) {
        this.entityData.set(ANIMATION_STATE, anim);
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

    // TODO: fight revamp with better attack ai / new attack ai for most things in the mod
    static class TyrannosaurusMeleeAttackGoal extends Goal {

        protected final TyrannosaurusEntity mob;
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

        public TyrannosaurusMeleeAttackGoal(TyrannosaurusEntity p_i1636_1_, double p_i1636_2_, boolean p_i1636_4_) {
            this.mob = p_i1636_1_;
            this.speedModifier = p_i1636_2_;
            this.followingTargetEvenIfNotSeen = p_i1636_4_;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
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

            switch (animState) {
                case 1 -> tickChargeAttack();
                case 2 -> tickWhipAttack();
                case 3, 4 -> tickStompAttack();
                default -> {
                    this.ticksUntilNextPathRecalculation = Math.max(this.ticksUntilNextPathRecalculation - 1, 0);
                    this.ticksUntilNextAttack = Math.max(this.ticksUntilNextPathRecalculation - 1, 0);
                    this.mob.getLookControl().setLookAt(target, 30.0F, 30.0F);
                    this.doMovement(target, distance);
                    this.checkForCloseRangeAttack(distance, reach);
                }
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

        protected void checkForCloseRangeAttack ( double distance, double reach){
            if (distance <= reach && this.ticksUntilNextAttack <= 0) {
                int r = this.mob.getRandom().nextInt(2048);
                if (r <= 800) {
                    this.mob.setAnimationState(1);
                }
                else if (r <= 1200) {
                    this.mob.setAnimationState(2);
                }
                else if (r <= 1700) {
                    this.mob.setAnimationState(3);
                }
                else {
                    this.mob.setAnimationState(4);
                }
            }
        }

        protected boolean getRangeCheck() {
            return this.mob.distanceToSqr(Objects.requireNonNull(this.mob.getTarget()).getX(), this.mob.getTarget().getY(), this.mob.getTarget().getZ()) <= 2.0F * this.getAttackReachSqr(this.mob.getTarget());
        }

        protected void tickChargeAttack () {

            animTime++;

            if (animTime <= 3) {
                this.mob.lookAt(Objects.requireNonNull(this.mob.getTarget()), 100000, 100000);
                this.mob.yBodyRot = this.mob.yHeadRot;
            }

            if(animTime==8) {
                preformChargeAttack();
            }

            if(animTime>=11) {
                animTime=0;
                this.mob.setAnimationState(0);
                this.resetAttackCooldown();
                this.ticksUntilNextPathRecalculation = 0;
            }
        }

        protected void tickWhipAttack () {
            this.mob.getNavigation().stop();
            animTime++;

            if (animTime <= 3) {
                this.mob.lookAt(Objects.requireNonNull(this.mob.getTarget()), 100000, 100000);
                this.mob.yBodyRot = this.mob.yHeadRot;
            }

            if(animTime==10) {
                preformWhipAttack();
            }

            if(animTime>=13) {
                animTime=0;
                this.mob.setAnimationState(0);
                this.resetAttackCooldown();
                this.ticksUntilNextPathRecalculation = 0;
            }
        }

        protected void tickStompAttack () {
            this.mob.getNavigation().stop();
            animTime++;

            if (animTime <= 3) {
                this.mob.lookAt(Objects.requireNonNull(this.mob.getTarget()), 100000, 100000);
                this.mob.yBodyRot = this.mob.yHeadRot;
            }

            if(animTime==13) {
                preformStompAttack();
            }

            if(animTime>=16) {
                animTime=0;
                this.mob.setAnimationState(0);
                this.resetAttackCooldown();
                this.ticksUntilNextPathRecalculation = 0;
            }
        }

        protected void preformChargeAttack () {
            Vec3 pos = mob.position();
            this.mob.playSound(UPSounds.TYRANNO_TAIL_SWIPE.get(), 1.0F, 1.0F);
            this.mob.swing(InteractionHand.MAIN_HAND);
            HitboxHelper.LargeAttackWithTargetCheck(this.mob.damageSources().mobAttack(mob), (float) Objects.requireNonNull(mob.getAttribute(Attributes.ATTACK_DAMAGE)).getValue(), 1.5f, mob, pos,  5.5F, -Math.PI/2, Math.PI/2, -1.0f, 3.0f);
        }

        protected void preformWhipAttack () {
            Vec3 pos = mob.position();
            this.mob.playSound(UPSounds.TYRANNO_TAIL_SWIPE.get(), 1.0F, 1.0F);
            HitboxHelper.LargeAttackWithTargetCheck(this.mob.damageSources().mobAttack(mob), (float) Objects.requireNonNull(mob.getAttribute(Attributes.ATTACK_DAMAGE)).getValue() - 5, 2.0f, mob, pos,  5.5F, -Math.PI/2, Math.PI/2, -1.0f, 3.0f);
        }

        protected void preformStompAttack () {
            Vec3 pos = mob.position();
            this.mob.playSound(UPSounds.TYRANNO_STOMP_ATTACK.get(), 1.0F, 1.0F);
            HitboxHelper.LargeAttack(this.mob.damageSources().mobAttack(mob), (float) Objects.requireNonNull(mob.getAttribute(Attributes.ATTACK_DAMAGE)).getValue() - 1, 1.75f, mob, pos,  6.0F, -Math.PI/2, Math.PI/2, -1.0f, 3.0f);
            if(this.mob.shakeCooldown <= 0 && UnusualPrehistoryConfig.SCREEN_SHAKE_REX.get()) {
                double rexShakeRange = UnusualPrehistoryConfig.SCREEN_SHAKE_BRACHI_RANGE.get();
                List<LivingEntity> list = this.mob.level().getEntitiesOfClass(LivingEntity.class, this.mob.getBoundingBox().inflate(rexShakeRange));
                for (LivingEntity e : list) {
                    if (!(e instanceof TyrannosaurusEntity) && e.isAlive()) {
                        e.addEffect(new MobEffectInstance(UPEffects.SCREEN_SHAKE.get(), 10, 4, false, false, false));
                    }
                }
                mob.shakeCooldown = 100;
            }
            mob.shakeCooldown--;
        }

        protected void resetAttackCooldown () {
            this.ticksUntilNextAttack = 0;
        }

        protected boolean isTimeToAttack () {
            return this.ticksUntilNextAttack <= 0;
        }

        protected int getTicksUntilNextAttack () {
            return this.ticksUntilNextAttack;
        }

        protected int getAttackInterval () {
            return 5;
        }

        protected double getAttackReachSqr(LivingEntity p_179512_1_) {
            return this.mob.getBbWidth() * 2.5F * this.mob.getBbWidth() * 2.0F + p_179512_1_.getBbWidth();
        }
    }

    public boolean canBeCollidedWith() {
        return UnusualPrehistoryConfig.REX_COLLISON.get();
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        return source.is(DamageTypes.IN_WALL) || source.is(DamageTypes.CACTUS) || super.isInvulnerableTo(source);
    }

    protected SoundEvent getAmbientSound() {
        return UPSounds.TYRANNO_IDLE.get();
    }

    protected SoundEvent getHurtSound(@NotNull DamageSource damageSourceIn) {
        return UPSounds.TYRANNO_HURT.get();
    }

    protected SoundEvent getDeathSound() {
        return UPSounds.TYRANNO_DEATH.get();
    }

    @Override
    public float getSoundVolume() {
        if(this.isBaby()){
            return 0.75F;
        }
        else{
            return 1.25F;
        }
    }

    @Override
    public float getVoicePitch() {
        return (this.random.nextFloat() - this.random.nextFloat()) * 0.2f + 1.0f;
    }

    private void soundListener(SoundKeyframeEvent<TyrannosaurusEntity> event) {
        TyrannosaurusEntity tyrannosaurus = event.getAnimatable();
        if (tyrannosaurus.level().isClientSide) {
            if (event.getKeyframeData().getSound().equals("tyrannosaurus_sniff")) {
                tyrannosaurus.level().playLocalSound(tyrannosaurus.getX(), tyrannosaurus.getY(), tyrannosaurus.getZ(), UPSounds.TYRANNO_SNIFF.get(), tyrannosaurus.getSoundSource(), 1.0F, tyrannosaurus.getVoicePitch(), false);
            }
            if (event.getKeyframeData().getSound().equals("tyrannosaurus_roar_1")) {
                tyrannosaurus.level().playLocalSound(tyrannosaurus.getX(), tyrannosaurus.getY(), tyrannosaurus.getZ(), UPSounds.TYRANNO_ROAR.get(), tyrannosaurus.getSoundSource(), 2.5F, tyrannosaurus.getVoicePitch(), false);
            }
        }
    }

    @Override
    public void registerControllers(final AnimatableManager.ControllerRegistrar controllers) {
        AnimationController<TyrannosaurusEntity> controller = new AnimationController<>(this, "controller", 5, this::predicate);
            controllers.add(controller);
        AnimationController<TyrannosaurusEntity> blend = new AnimationController<>(this, "blend", 5, this::predicate)
                .triggerableAnim("shake", TYRANNO_SHAKE)
                .triggerableAnim("sniff", TYRANNO_SNIFF)
                .triggerableAnim("roar", TYRANNO_ROAR)
                .triggerableAnim("bite_0", TYRANNO_BITE_0)
                .triggerableAnim("bite_1", TYRANNO_BITE_1);
                blend.setSoundKeyframeHandler(this::soundListener);
            controllers.add(blend);
    }

    private boolean isStillEnough() {
        return this.getDeltaMovement().horizontalDistance() < 0.05;
    }

    protected <E extends TyrannosaurusEntity> PlayState predicate(final software.bernie.geckolib.core.animation.AnimationState<E> event) {

        if(this.isFromBook()){
            return event.setAndContinue(TYRANNO_IDLE);
        }

        int animState = this.getAnimationState(); {

        switch (animState) {

            case 1:
                if (!this.hasEepy() && !this.isAsleep()) {
                    event.setAndContinue(TYRANNO_TACKLE);
                    event.getController().setAnimationSpeed(0.85F);
                    break;
                }
            case 2:
                if (!this.hasEepy() && !this.isAsleep()) {
                    event.setAndContinue(TYRANNO_WHIP);
                    event.getController().setAnimationSpeed(1.0F);
                    break;
                }
            case 3:
                if (!this.hasEepy() && !this.isAsleep()) {
                    event.setAndContinue(TYRANNO_STOMP_L);
                    event.getController().setAnimationSpeed(1.35F);
                    break;
                }
            case 4:
                if (!this.hasEepy() && !this.isAsleep()) {
                    event.setAndContinue(TYRANNO_STOMP_R);
                    event.getController().setAnimationSpeed(1.35F);
                    break;
                }

            default:

                if (this.hasEepy() && !this.isAsleep()) {
                    event.setAndContinue(TYRANNO_EEPY);
                    event.getController().setAnimationSpeed(1.0F);
                    return PlayState.CONTINUE;
                }

                if (this.isInWater() && !this.hasEepy()  ) {
                    event.setAndContinue(TYRANNO_SWIM);
                    event.getController().setAnimationSpeed(1.0F);
                    return PlayState.CONTINUE;
                }

                else if (this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6 && !this.isSwimming() && !this.isInWater() && !this.hasEepy() && !this.isAsleep()) {
                    if (this.isSprinting() && !this.isBaby()) {
                        event.setAndContinue(TYRANNO_CHARGE);
                    } else {
                        event.setAndContinue(TYRANNO_WALK);
                    }
                    event.getController().setAnimationSpeed(1.0F);
                    return PlayState.CONTINUE;
                }

                if (!this.isInWater() && !this.hasEepy() && !this.hasTargets() && this.isAsleep() && !getBooleanState(IDLE_1_AC) && !getBooleanState(IDLE_2_AC) && !getBooleanState(IDLE_3_AC) && !getBooleanState(IDLE_4_AC)) {
                    event.setAndContinue(TYRANNO_SLEEP);
                    event.getController().setAnimationSpeed(1.0F);
                    return PlayState.CONTINUE;
                }

                if (!this.isInWater() && !this.hasEepy() && !this.isAsleep()) {
                    if (getBooleanState(IDLE_1_AC) && !this.isAsleep()) {
                        if (this.isStillEnough()) {
                            triggerAnim("blend", "shake");
                            return event.setAndContinue(TYRANNO_IDLE);
                        }
                        else {
                            triggerAnim("blend", "shake");
                            return PlayState.CONTINUE;
                        }
                    }
                    if (getBooleanState(IDLE_2_AC) && !this.isAsleep()) {
                        if (this.isStillEnough()) {
                            triggerAnim("blend", "sniff");
                            return event.setAndContinue(TYRANNO_IDLE);
                        }
                        else {
                            triggerAnim("blend", "sniff");
                            return PlayState.CONTINUE;
                        }
                    }
                    if (getBooleanState(IDLE_3_AC) && !this.isAsleep()) {
                        if (this.isStillEnough()) {
                            triggerAnim("blend", "roar");
                            return event.setAndContinue(TYRANNO_IDLE);
                        }
                        else {
                            triggerAnim("blend", "roar");
                            return PlayState.CONTINUE;
                        }
                    }
                    if (getBooleanState(IDLE_4_AC) && !this.isAsleep()) {
                        return event.setAndContinue(TYRANNO_SIT);
                    }
                    return event.setAndContinue(TYRANNO_IDLE);
                }
            }
        }
        return PlayState.CONTINUE;
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
}