package com.peeko32213.unusualprehistory.common.entity;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.peeko32213.unusualprehistory.common.entity.msc.util.dino.BaseStatedDinosaurAnimalEntity;
import com.peeko32213.unusualprehistory.common.entity.msc.util.helper.HitboxHelper;
import com.peeko32213.unusualprehistory.common.entity.msc.util.state.EntityAction;
import com.peeko32213.unusualprehistory.common.entity.msc.util.state.RandomStateGoal;
import com.peeko32213.unusualprehistory.common.entity.msc.util.state.StateHelper;
import com.peeko32213.unusualprehistory.common.entity.msc.util.state.WeightedState;
import com.peeko32213.unusualprehistory.core.registry.UPEntities;
import com.peeko32213.unusualprehistory.core.registry.UPSounds;
import com.peeko32213.unusualprehistory.core.registry.UPTags;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
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
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.EnumSet;
import java.util.List;
import java.util.Objects;

public class EstemmenosuchusEntity extends BaseStatedDinosaurAnimalEntity implements GeoEntity, GeoAnimatable {

    private static final Ingredient FOOD_ITEMS = Ingredient.of(UPTags.ESTEMME_FOOD_ITEMS);

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    private static final EntityDataAccessor<Integer> CHARGE_COOLDOWN_TICKS = SynchedEntityData.defineId(EstemmenosuchusEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> HAS_TARGET = SynchedEntityData.defineId(EstemmenosuchusEntity.class, EntityDataSerializers.BOOLEAN);
    private boolean canBePushed = true;

    private static final EntityDataAccessor<Integer> ANIMATION_STATE = SynchedEntityData.defineId(EstemmenosuchusEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> COMBAT_STATE = SynchedEntityData.defineId(EstemmenosuchusEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> ENTITY_STATE = SynchedEntityData.defineId(EstemmenosuchusEntity.class, EntityDataSerializers.INT);

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
    private static final RawAnimation BITE_1 = RawAnimation.begin().thenPlay("animation.estemmenosuchus.attack1_blend");
    private static final RawAnimation BITE_2 = RawAnimation.begin().thenPlay("animation.estemmenosuchus.attack2_blend");
    private static final RawAnimation RAMMING_START = RawAnimation.begin().thenPlay("animation.estemmenosuchus.ramming_start");
    private static final RawAnimation RAMMING = RawAnimation.begin().thenLoop("animation.estemmenosuchus.ramming");

    // Misc animations
    private static final RawAnimation CRUSH = RawAnimation.begin().thenPlay("animation.estemmenosuchus.crush_blend");

    // Idle accessors
    private static final EntityDataAccessor<Boolean> IDLE_1_AC = SynchedEntityData.defineId(EstemmenosuchusEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> IDLE_2_AC = SynchedEntityData.defineId(EstemmenosuchusEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> IDLE_3_AC = SynchedEntityData.defineId(EstemmenosuchusEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> IDLE_4_AC = SynchedEntityData.defineId(EstemmenosuchusEntity.class, EntityDataSerializers.BOOLEAN);

    // Idle actions
    private static final EntityAction ESTEMME_IDLE_1_ACTION = new EntityAction(0, (e) -> {}, 1);

    private static final StateHelper ESTEMME_IDLE_1_STATE =
            StateHelper.Builder.state(IDLE_1_AC, "estemmenosuchus_bellow")
                .playTime(60)
                .stopTime(120)
                .entityAction(ESTEMME_IDLE_1_ACTION)
                .build();

    private static final EntityAction ESTEMME_IDLE_2_ACTION = new EntityAction(0, (e) -> {}, 1);

    private static final StateHelper ESTEMME_IDLE_2_STATE =
            StateHelper.Builder.state(IDLE_2_AC, "estemmenosuchus_yawn")
                .playTime(60)
                .stopTime(140)
                .entityAction(ESTEMME_IDLE_2_ACTION)
                .build();

    private static final EntityAction ESTEMME_IDLE_3_ACTION = new EntityAction(0, (e) -> {}, 1);

    private static final StateHelper ESTEMME_IDLE_3_STATE =
            StateHelper.Builder.state(IDLE_3_AC, "estemmenosuchus_sit")
                .playTime(160)
                .stopTime(200)
                .affectsAI(true)
                .affectedFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK))
                .entityAction(ESTEMME_IDLE_3_ACTION)
                .build();

    private static final EntityAction ESTEMME_IDLE_4_ACTION = new EntityAction(0, (e) -> {}, 1);

    private static final StateHelper ESTEMME_IDLE_4_STATE =
            StateHelper.Builder.state(IDLE_4_AC, "estemmenosuchus_sleep")
                .playTime(320)
                .stopTime(400)
                .affectsAI(true)
                .affectedFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK))
                .entityAction(ESTEMME_IDLE_4_ACTION)
                .build();

    @Override
    public ImmutableMap<String, StateHelper> getStates() {
        return ImmutableMap.of(
                ESTEMME_IDLE_1_STATE.getName(), ESTEMME_IDLE_1_STATE,
                ESTEMME_IDLE_2_STATE.getName(), ESTEMME_IDLE_2_STATE,
                ESTEMME_IDLE_3_STATE.getName(), ESTEMME_IDLE_3_STATE,
                ESTEMME_IDLE_4_STATE.getName(), ESTEMME_IDLE_4_STATE
        );
    }

    @Override
    public List<WeightedState<StateHelper>> getWeightedStatesToPerform() {
        return ImmutableList.of(
                WeightedState.of(ESTEMME_IDLE_1_STATE, 20),
                WeightedState.of(ESTEMME_IDLE_2_STATE, 18),
                WeightedState.of(ESTEMME_IDLE_3_STATE, 8),
                WeightedState.of(ESTEMME_IDLE_4_STATE, 4)
        );
    }

    @Override
    public boolean getAction() {
        return false;
    }

    @Override
    public void setAction(boolean action) {}

    public EstemmenosuchusEntity(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
            .add(Attributes.MAX_HEALTH, 80.0D)
            .add(Attributes.ARMOR, 8.0)
            .add(Attributes.MOVEMENT_SPEED, 0.14D)
            .add(Attributes.KNOCKBACK_RESISTANCE, 0.5D)
            .add(Attributes.FOLLOW_RANGE, 12.0D)
            .add(Attributes.ATTACK_DAMAGE, 12.0D);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(2, new RandomStateGoal<>(this));
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(4, new TemptGoal(this, 1.2D, FOOD_ITEMS, false));
        this.goalSelector.addGoal(5, new FollowParentGoal(this, 1.1D));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 1.0F, 30));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(3, new EstemmenosuchusEntity.EstemmeMeleeAttackGoal(this, 1.25F, false));
//        this.goalSelector.addGoal(1, new EstemmenosuchusEntity.EstemmePrepareChargeGoal(this));
//        this.goalSelector.addGoal(2, new EstemmenosuchusEntity.EstemmeChargeGoal(this, 2.0F));
        this.targetSelector.addGoal(7, new HurtByTargetGoal(this));
    }

    @Override
    public float getStepHeight() {
        return 1.25F;
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

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel serverLevel, @NotNull AgeableMob p_146744_) {
        return UPEntities.ESTEMMENOSUCHUS.get().create(serverLevel);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(IDLE_1_AC, false);
        this.entityData.define(IDLE_2_AC, false);
        this.entityData.define(IDLE_3_AC, false);
        this.entityData.define(IDLE_4_AC, false);
        this.entityData.define(ANIMATION_STATE, 0);
        this.entityData.define(COMBAT_STATE, 0);
        this.entityData.define(ENTITY_STATE, 0);
        this.entityData.define(CHARGE_COOLDOWN_TICKS, 0);
        this.entityData.define(HAS_TARGET, false);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compound) {
        super.addAdditionalSaveData(compound);
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compound) {
        super.readAdditionalSaveData(compound);
    }

    public void tick() {
        super.tick();
    }

    public int getAnimationState() {
        return this.entityData.get(ANIMATION_STATE);
    }

    public void setAnimationState(int anim) {
        this.entityData.set(ANIMATION_STATE, anim);
    }

    public int getCombatState() {
        return this.entityData.get(COMBAT_STATE);
    }

    public void setCombatState(int anim) {
        this.entityData.set(COMBAT_STATE, anim);
    }

    public int getEntityState() {
        return this.entityData.get(ENTITY_STATE);
    }

    public void setEntityState(int anim) {
        this.entityData.set(ENTITY_STATE, anim);
    }

    public boolean isFood(ItemStack stack) {
        return stack.is(UPTags.ESTEMME_FOOD_ITEMS);
    }

    // Heal mob
    public @NotNull InteractionResult mobInteract(Player player, @NotNull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if(hand != InteractionHand.MAIN_HAND) return InteractionResult.FAIL;

        if (isFood(itemstack) && this.getHealth() < this.getMaxHealth()) {

            if (!player.getAbilities().instabuild) {
                itemstack.shrink(1);
            }
            if(!this.level().isClientSide) {
                this.heal(10);
            }

            this.level().broadcastEntityEvent(this, (byte) 18);
            this.playSound(this.getEatingSound(itemstack), 1.0F, 1.0F);
            this.gameEvent(GameEvent.EAT, this);

            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    // Bite attack goal
    class EstemmeMeleeAttackGoal extends Goal {

        protected final EstemmenosuchusEntity mob;
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

        public EstemmeMeleeAttackGoal(EstemmenosuchusEntity p_i1636_1_, double p_i1636_2_, boolean p_i1636_4_) {
            this.mob = p_i1636_1_;
            this.speedModifier = p_i1636_2_;
            this.followingTargetEvenIfNotSeen = p_i1636_4_;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
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
                tickBiteAttack();
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

        protected boolean getRangeCheck() {
            return this.mob.distanceToSqr(Objects.requireNonNull(this.mob.getTarget()).getX(), this.mob.getTarget().getY(), this.mob.getTarget().getZ()) <= 1.42F * this.getAttackReachSqr(this.mob.getTarget());
        }

        protected void tickBiteAttack () {

            triggerAnim("attack", "bite_1");

            animTime++;

            if (animTime <= 3) {
                this.mob.lookAt(Objects.requireNonNull(this.mob.getTarget()), 100000, 100000);
                this.mob.yBodyRot = this.mob.yHeadRot;
            }

            if(animTime==9) {
                preformBiteAttack();
            }

            if(animTime>=12) {
                animTime=0;
                this.mob.setAnimationState(0);
                this.resetAttackCooldown();
                this.ticksUntilNextPathRecalculation = 0;
            }
        }

        protected void preformBiteAttack () {
            Vec3 pos = mob.position();
            this.mob.playSound(UPSounds.MEGALANIA_BITE.get(), 1.0F, 1.0F);
            this.mob.swing(InteractionHand.MAIN_HAND);
            HitboxHelper.LargeAttackWithTargetCheck(this.mob.damageSources().mobAttack(mob), (float) Objects.requireNonNull(mob.getAttribute(Attributes.ATTACK_DAMAGE)).getValue(), 0.15f, mob, pos,  5.0F, -Math.PI/2, Math.PI/2, -1.0f, 3.0f);
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
            return this.mob.getBbWidth() * 2.5F * this.mob.getBbWidth() * 1.8F + p_179512_1_.getBbWidth();
        }
    }

//    // Prepare charge attack
//    static class EstemmePrepareChargeGoal extends Goal {
//        protected final EstemmenosuchusEntity estemme;
//
//        public EstemmePrepareChargeGoal(EstemmenosuchusEntity estemme) {
//            this.estemme = estemme;
//            this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
//        }
//
//        @Override
//        public boolean canUse() {
//            LivingEntity target = this.estemme.getTarget();
//            if (target == null || !target.isAlive() || !this.estemme.isWithinYRange(target)) {
//                this.estemme.resetChargeCooldownTicks();
//                return false;
//            }
//            return target instanceof Player && estemme.hasChargeCooldown();
//        }
//
//        @Override
//        public void start() {
//            LivingEntity target = this.estemme.getTarget();
//            if (target == null) {
//                return;
//            }
//            this.estemme.setHasTarget(true);
//            this.estemme.resetChargeCooldownTicks();
//            this.estemme.canBePushed = false;
//        }
//
//        @Override
//        public void stop() {
//            this.estemme.setHasTarget(false);
//            this.estemme.canBePushed = true;
//        }
//
//        @Override
//        public void tick() {
//            LivingEntity target = this.estemme.getTarget();
//            if (target == null) {
//                return;
//            }
//            this.estemme.getLookControl().setLookAt(target);
//            this.estemme.setChargeCooldownTicks(Math.max(0, this.estemme.getChargeCooldownTicks() - 1));
//        }
//    }
//
//    // Charge attack
//    static class EstemmeChargeGoal extends Goal {
//        protected final EstemmenosuchusEntity mob;
//        private final double speedModifier;
//        private Path path;
//        private Vec3 chargeDirection;
//
//        public EstemmeChargeGoal(EstemmenosuchusEntity pathfinderMob, double speedModifier) {
//            this.mob = pathfinderMob;
//            this.speedModifier = speedModifier;
//            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
//            this.chargeDirection = Vec3.ZERO;
//        }
//
//        @Override
//        public boolean canUse() {
//            LivingEntity target = this.mob.getTarget();
//            if (target == null || !target.isAlive() || this.mob.hasChargeCooldown()) {
//                return false;
//            }
//            this.path = this.mob.getNavigation().createPath(target, 0);
//            return target instanceof Player && this.path != null;
//        }
//
//        @Override
//        public boolean canContinueToUse() {
//            LivingEntity target = this.mob.getTarget();
//            if (target == null || !target.isAlive() || this.mob.hasChargeCooldown()) {
//                return false;
//            }
//            return !this.mob.getNavigation().isDone();
//        }
//
//        @Override
//        public void start() {
//            BlockPos blockPosition = this.mob.blockPosition();
//            BlockPos target = this.path.getTarget();
//            this.chargeDirection = new Vec3(blockPosition.getX() - target.getX(), 0.0, blockPosition.getZ() - target.getZ()).normalize();
//            this.mob.getNavigation().moveTo(this.path, this.speedModifier);
//            this.mob.setAggressive(true);
//        }
//
//        @Override
//        public void stop() {
//            this.mob.resetChargeCooldownTicks();
//            this.mob.getNavigation().stop();
//        }
//
//        @Override
//        public void tick() {
//            this.mob.getLookControl().setLookAt(Vec3.atCenterOf(this.path.getTarget()));
//            if (this.mob.horizontalCollision && this.mob.onGround()) {
//                this.mob.jumpFromGround();
//            }
//            if (this.mob.level().getGameTime() % 2L == 0L) {
//                this.mob.playSound(UPSounds.MAJUNGA_STEP.get(), 0.5F, this.mob.getVoicePitch());
//            }
//            this.tryToHurt();
//        }
//
//        protected void tryToHurt() {
//            List<LivingEntity> nearbyEntities = this.mob.level().getNearbyEntities(LivingEntity.class, TargetingConditions.forCombat(), this.mob, this.mob.getBoundingBox());
//            if (!nearbyEntities.isEmpty()) {
//                LivingEntity livingEntity = nearbyEntities.get(0);
//                if (!(livingEntity instanceof MajungasaurusEntity)) {
//                    livingEntity.hurt(this.mob.damageSources().mobAttack(this.mob), (float) this.mob.getAttributeValue(Attributes.ATTACK_DAMAGE));
//                    float speed = Mth.clamp(this.mob.getSpeed() * 1.65f, 0.2f, 3.0f);
//
//                    float shieldBlockModifier = livingEntity.isDamageSourceBlocked(this.mob.damageSources().mobAttack(this.mob)) ? 0.5f : 1.0f;
//
//                    livingEntity.knockback(shieldBlockModifier * speed * 2.0D, this.chargeDirection.x(), this.chargeDirection.z());
//
//                    double knockbackResistance = Math.max(0.0, 1.0 - livingEntity.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));
//
//                    livingEntity.setDeltaMovement(livingEntity.getDeltaMovement().add(0.0, 0.4f * knockbackResistance, 0.0));
//
//                    this.mob.swing(InteractionHand.MAIN_HAND);
//                    if (livingEntity.equals(this.mob.getTarget())) {
//                        this.stop();
//                    }
//
//                }
//            }
//        }
//    }
//
//    @Override
//    public boolean isPushable() {
//        return this.canBePushed;
//    }
//
//    public void setChargeCooldownTicks(int ticks) {
//        this.entityData.set(CHARGE_COOLDOWN_TICKS, ticks);
//    }
//
//    public int getChargeCooldownTicks() {
//        return this.entityData.get(CHARGE_COOLDOWN_TICKS);
//    }
//
//    public boolean hasChargeCooldown() {
//        return this.entityData.get(CHARGE_COOLDOWN_TICKS) > 0;
//    }
//
//    public void resetChargeCooldownTicks() {
//        this.entityData.set(CHARGE_COOLDOWN_TICKS, 5);
//    }
//
//    public void setHasTarget(boolean hasTarget) {
//        this.entityData.set(HAS_TARGET, hasTarget);
//    }
//
//    public boolean hasTarget() {
//        return this.entityData.get(HAS_TARGET);
//    }
//
//    private boolean isWithinYRange(LivingEntity target) {
//        if (target == null) {
//            return false;
//        }
//        return Math.abs(target.getY() - this.getY()) < 3;
//    }

    @Override
    public void customServerAiStep() {
        if (this.getMoveControl().hasWanted()) {
            this.setSprinting(this.getMoveControl().getSpeedModifier() >= 1.5D);
        } else {
            this.setSprinting(false);
        }
        super.customServerAiStep();
    }

    // Animation controllers
    @Override
    public void registerControllers(final AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "normal", 5, this::controller));
        controllers.add(new AnimationController<>(this, "blend", 5, this::controller)
            .triggerableAnim("bellow", ESTEMME_BELLOW)
            .triggerableAnim("yawn", ESTEMME_YAWN));
        controllers.add(new AnimationController<>(this, "attack", 5, this::controller)
            .triggerableAnim("bite_1", BITE_1)
            .triggerableAnim("bite_2", BITE_2));
    }

    protected <E extends EstemmenosuchusEntity> PlayState controller(final software.bernie.geckolib.core.animation.AnimationState<E> event) {

        if(this.isFromBook()) {
            return event.setAndContinue(ESTEMME_IDLE);
        }

        if (this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6 && !this.isInWater() && !getBooleanState(IDLE_3_AC) && !getBooleanState(IDLE_4_AC)) {

            event.setAndContinue(ESTEMME_WALK);

            if(this.isSprinting()){
                event.setAndContinue(RAMMING);
                event.getController().setAnimationSpeed(1.0F);
            }
//            else if (this.hasChargeCooldown() && this.hasTarget()) {
//                event.setAndContinue(RAMMING_START);
//                event.getController().setAnimationSpeed(1.0F);
//                return PlayState.CONTINUE;
//            }
            return PlayState.CONTINUE;
        }

        if (this.isInWater() && !getBooleanState(IDLE_3_AC) && !getBooleanState(IDLE_4_AC)) {
            event.setAndContinue(ESTEMME_SWIM);
            event.getController().setAnimationSpeed(1.0F);
            return PlayState.CONTINUE;
        }

        if (!this.isInWater()) {
            if (getBooleanState(IDLE_1_AC)) {
                event.setControllerSpeed(1.0F);
                triggerAnim("blend", "bellow");
                return PlayState.CONTINUE;
            }
            if (getBooleanState(IDLE_2_AC)) {
                event.setControllerSpeed(1.0F);
                triggerAnim("blend", "yawn");
                return PlayState.CONTINUE;
            }
            if (getBooleanState(IDLE_3_AC)) {
                event.setControllerSpeed(1.0F);
//                event.setControllerSpeed(0.35F);
                return event.setAndContinue(ESTEMME_SIT);
            }
            if (getBooleanState(IDLE_4_AC)) {
                event.setControllerSpeed(1.0F);
//                event.setControllerSpeed(0.35F);
                return event.setAndContinue(ESTEMME_SLEEP);
            }
            event.setControllerSpeed(1.0F);
            return event.setAndContinue(ESTEMME_IDLE);
        }

        event.setControllerSpeed(1.0F);
        return PlayState.CONTINUE;
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
        this.playSound(UPSounds.MAJUNGA_STEP.get(), 0.3F, 1.0F);
    }

    @Override
    public float getSoundVolume() {
        return 1.0F;
    }

    @Override
    public int getAmbientSoundInterval() {
        return 135;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    public boolean requiresCustomPersistence() {
        return super.requiresCustomPersistence() || this.hasCustomName();
    }

    public boolean removeWhenFarAway(double d) {
        return !this.hasCustomName();
    }

    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_28134_, DifficultyInstance p_28135_, MobSpawnType p_28136_, @Nullable SpawnGroupData p_28137_, @Nullable CompoundTag p_28138_) {
        p_28137_ = super.finalizeSpawn(p_28134_, p_28135_, p_28136_, p_28137_, p_28138_);
        Level level = p_28134_.getLevel();
        if (level instanceof ServerLevel) {
            {
                this.setPersistenceRequired();
            }
        }
        return p_28137_;
    }
}
