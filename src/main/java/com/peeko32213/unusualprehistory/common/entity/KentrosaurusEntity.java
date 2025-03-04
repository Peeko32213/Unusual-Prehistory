package com.peeko32213.unusualprehistory.common.entity;

import com.peeko32213.unusualprehistory.common.entity.base.PrehistoricEntity;
import com.peeko32213.unusualprehistory.common.entity.util.goal.BabyPanicGoal;
import com.peeko32213.unusualprehistory.common.entity.util.goal.CustomRandomStrollGoal;
import com.peeko32213.unusualprehistory.common.entity.util.helper.HitboxHelper;
import com.peeko32213.unusualprehistory.core.registry.UPSounds;
import com.peeko32213.unusualprehistory.core.registry.UPTags;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.world.Difficulty;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.FollowParentGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

import javax.annotation.Nonnull;
import java.util.EnumSet;
import java.util.function.Predicate;

public class KentrosaurusEntity extends PrehistoricEntity {
    public static final double FLEE_SPEED_MOD = 1.5D;
    private static final EntityDataAccessor<Integer> COMBAT_STATE = SynchedEntityData.defineId(KentrosaurusEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> ENTITY_STATE = SynchedEntityData.defineId(KentrosaurusEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> ANIMATION_STATE = SynchedEntityData.defineId(KentrosaurusEntity.class, EntityDataSerializers.INT);
    protected static final EntityDataAccessor<Byte> DATA_FLAGS_ID = SynchedEntityData.defineId(KentrosaurusEntity.class, EntityDataSerializers.BYTE);
    private static final Predicate<LivingEntity> SCARY_MOB = (p_29634_) -> {
        if (p_29634_ instanceof Player && ((Player) p_29634_).isCreative()) {
            return false;
        } else {
            return p_29634_.getType() == EntityType.AXOLOTL || p_29634_.getMobType() != MobType.WATER;
        }
    };
    static final TargetingConditions targetingConditions = TargetingConditions.forNonCombat().ignoreInvisibilityTesting().ignoreLineOfSight().selector(SCARY_MOB);

    private boolean orderedToSit;
    protected int attackCooldown = 0;
    private static final RawAnimation KENTRO_WALK = RawAnimation.begin().thenLoop("animation.kentro.walk");
    private static final RawAnimation KENTRO_IDLE = RawAnimation.begin().thenLoop("animation.kentro.idle");
    private static final RawAnimation KENTRO_SIT = RawAnimation.begin().thenLoop("animation.kentro.laying");
    private static final RawAnimation KENTRO_SWIM = RawAnimation.begin().thenLoop("animation.kentro.swim");
    private static final RawAnimation KENTRO_SWIPE = RawAnimation.begin().thenLoop("animation.kentro.swipe");

    public KentrosaurusEntity(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
        this.setSpeedModifier(0.0D);

    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 35.0D)
                .add(Attributes.ARMOR, 5.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.2D)
                .add(Attributes.ATTACK_DAMAGE, 12.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.3D);
    }

    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(3, new BabyPanicGoal(this, 2.0D));
        this.goalSelector.addGoal(1, new KentrosaurusEntity.KentroMeleeAttackGoal(this, 1.5F, true));
        this.goalSelector.addGoal(1, new KentroSitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(3, new CustomRandomStrollGoal(this, 30, 1.0D, 100, 34));
        this.goalSelector.addGoal(5, new FollowParentGoal(this, 1.1D));
        this.targetSelector.addGoal(8, (new HurtByTargetGoal(this)));
    }


    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_FLAGS_ID, (byte) 0);
        this.entityData.define(ANIMATION_STATE, 0);
        this.entityData.define(COMBAT_STATE, 0);
        this.entityData.define(ENTITY_STATE, 0);
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

    @Override
    public boolean isAlliedTo(Entity pEntity) {
        return pEntity.is(this);
    }

    public InteractionResult mobInteract(@Nonnull Player player, @Nonnull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        Item item = itemstack.getItem();
        if(hand != InteractionHand.MAIN_HAND) return InteractionResult.FAIL;
        if (isKentroFood(itemstack)) {
            //int size = itemstack.getCount();
            this.usePlayerItem(player, hand, itemstack);
            itemstack.shrink(1);
            this.setOrderedToSit(true);
            this.playSound(this.getEatingSound(itemstack), 1.0F, 1.0F);
            return InteractionResult.SUCCESS;
        } else {
            this.setOrderedToSit(false);
            return InteractionResult.SUCCESS;
        }
    }


    public boolean isKentroFood(ItemStack stack) {
        return stack.is(UPTags.KENTRO_FOOD);
    }

    public void tick() {
        super.tick();
        if (this.attackCooldown > 0) {
            this.attackCooldown--;
        }
    }


    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("Sitting", this.orderedToSit);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.orderedToSit = compound.getBoolean("Sitting");
        this.setInSittingPose(this.orderedToSit);
    }

    public boolean isInSittingPose() {
        return (this.entityData.get(DATA_FLAGS_ID) & 1) != 0;
    }

    public void setInSittingPose(boolean p_21838_) {
        byte b0 = this.entityData.get(DATA_FLAGS_ID);
        if (p_21838_) {
            this.entityData.set(DATA_FLAGS_ID, (byte) (b0 | 1));
        } else {
            this.entityData.set(DATA_FLAGS_ID, (byte) (b0 & -2));
        }

    }


    @Override
    public boolean canCollideWith(Entity pEntity) {

        if (pEntity instanceof LivingEntity livingEntity && this.isAlive() && this.isInSittingPose() && pEntity.isAlive()) {
            if (!(livingEntity instanceof Player)) {
                this.touch(livingEntity);
                return true;
            }
        }

        return super.canCollideWith(pEntity);
    }

    @Override
    public void playerTouch(Player entity) {
        super.playerTouch(entity);
        if (!entity.isCreative() && this.attackCooldown == 0 && entity.level().getDifficulty() != Difficulty.PEACEFUL && isOrderedToSit()) {
            entity.hurt(this.damageSources().mobAttack(this), 5.0F);
            this.attackCooldown = 80;
        }
    }

    private void touch(LivingEntity pMob) {
        pMob.hurt(this.damageSources().mobAttack(this), (float) (5.0D));
    }


    public SoundEvent getEatingSound(ItemStack p_28540_) {
        return SoundEvents.GENERIC_EAT;
    }


    public boolean canBeCollidedWith() {
        return this.isAlive() && this.isInSittingPose();
    }


    public boolean isOrderedToSit() {
        return this.orderedToSit;
    }

    public void setOrderedToSit(boolean p_21840_) {
        this.orderedToSit = p_21840_;
    }

    @Override
    protected SoundEvent getAttackSound() {
        return null;
    }

    @Override
    protected int getKillHealAmount() {
        return 0;
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
    public AgeableMob getBreedOffspring(ServerLevel p_146743_, AgeableMob p_146744_) {
        return null;
    }

    public static class KentroSitWhenOrderedToGoal extends Goal {
        private final KentrosaurusEntity mob;

        public KentroSitWhenOrderedToGoal(KentrosaurusEntity p_25898_) {
            this.mob = p_25898_;
            this.setFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
        }

        public boolean canContinueToUse() {
            return this.mob.isOrderedToSit();
        }

        public boolean canUse() {
            if (this.mob.isInWaterOrBubble()) {
                return false;
            } else if (!this.mob.onGround()) {
                return false;
            } else {
                return this.mob.isOrderedToSit();
            }
        }

        public void start() {
            this.mob.getNavigation().stop();
            this.mob.setInSittingPose(true);
        }

        public void stop() {
            this.mob.setInSittingPose(false);
        }
    }

    protected void playStepSound(BlockPos p_28301_, BlockState p_28302_) {
        this.playSound(UPSounds.MAJUNGA_STEP.get(), 0.1F, 1.0F);
    }

    static class KentroPanicGoal extends PanicGoal {
        private final KentrosaurusEntity rabbit;

        public KentroPanicGoal(KentrosaurusEntity pRabbit, double pSpeedModifier) {
            super(pRabbit, pSpeedModifier);
            this.rabbit = pRabbit;
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {
            super.tick();
            this.rabbit.setSpeedModifier(this.speedModifier);
        }
    }

    public void setSpeedModifier(double pSpeedModifier) {
        this.getNavigation().setSpeedModifier(pSpeedModifier);
        this.moveControl.setWantedPosition(this.moveControl.getWantedX(), this.moveControl.getWantedY(), this.moveControl.getWantedZ(), pSpeedModifier);
    }


    protected SoundEvent getAmbientSound() {
        return UPSounds.KENTRO_IDLE.get();
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return UPSounds.KENTRO_HURT.get();
    }

    protected SoundEvent getDeathSound() {
        return UPSounds.KENTRO_DEATH.get();
    }

    static class KentroMeleeAttackGoal extends Goal {

        protected final KentrosaurusEntity mob;
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


        public KentroMeleeAttackGoal(KentrosaurusEntity p_i1636_1_, double p_i1636_2_, boolean p_i1636_4_) {
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
            } else if (!livingentity.isAlive()) {
                return false;
            } else if (!this.followingTargetEvenIfNotSeen) {
                return !this.mob.getNavigation().isDone();
            } else if (!this.mob.isWithinRestriction(livingentity.blockPosition())) {
                return false;
            } else {
                return !(livingentity instanceof Player) || !livingentity.isSpectator() && !((Player) livingentity).isCreative();
            }


        }

        public void start() {
            this.mob.getNavigation().moveTo(this.path, this.speedModifier);
            this.ticksUntilNextPathRecalculation = 0;
            this.ticksUntilNextAttack = 0;
            this.animTime = 0;
            this.mob.setAnimationState(0);
        }

        public void stop() {
            LivingEntity livingentity = this.mob.getTarget();
            if (!EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(livingentity)) {
                this.mob.setTarget((LivingEntity) null);
            }
            this.mob.setAnimationState(0);

        }

        public void tick() {


            LivingEntity target = this.mob.getTarget();
            double distance = this.mob.distanceToSqr(target.getX(), target.getY(), target.getZ());
            double reach = this.getAttackReachSqr(target);
            int animState = this.mob.getAnimationState();
            Vec3 aim = this.mob.getLookAngle();
            Vec2 aim2d = new Vec2((float) (aim.x / (1 - Math.abs(aim.y))), (float) (aim.z / (1 - Math.abs(aim.y))));


            switch (animState) {
                case 21:
                    tickSliceAttack();
                    break;
                default:
                    this.ticksUntilNextPathRecalculation = Math.max(this.ticksUntilNextPathRecalculation - 1, 0);
                    this.ticksUntilNextAttack = Math.max(this.ticksUntilNextPathRecalculation - 1, 0);
                    this.mob.getLookControl().setLookAt(target, 30.0F, 30.0F);
                    this.doMovement(target, distance);
                    this.checkForCloseRangeAttack(distance, reach);
                    break;

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
                int r = this.mob.getRandom().nextInt(2048);
                if (r <= 1200) {
                    this.mob.setAnimationState(21);
                }
            }
        }


        protected boolean getRangeCheck() {

            return
                    this.mob.distanceToSqr(this.mob.getTarget().getX(), this.mob.getTarget().getY(), this.mob.getTarget().getZ())
                            <=
                            1.8F * this.getAttackReachSqr(this.mob.getTarget());
        }


        protected void tickSliceAttack() {
            animTime++;
            this.mob.getNavigation().stop();
            if (animTime == 5) {
                preformSlashAttack();
            }
            if (animTime >= 15) {
                animTime = 0;

                this.mob.setAnimationState(0);
                this.resetAttackCooldown();
                this.ticksUntilNextPathRecalculation = 0;
            }
        }

        protected void preformSlashAttack() {
            Vec3 pos = mob.position();
            HitboxHelper.LargeAttack(this.mob.damageSources().mobAttack(mob), 10.0f, 1.0f, mob, pos, 5.0F, -Math.PI / 2, Math.PI / 2, -1.0f, 3.0f);
            this.mob.playSound(UPSounds.TAIL_SWIPE.get(), 0.1F, 1.0F);
        }


        protected void resetAttackCooldown() {
            this.ticksUntilNextAttack = 0;
        }

        protected boolean isTimeToAttack() {
            return this.ticksUntilNextAttack <= 0;
        }

        protected int getTicksUntilNextAttack() {
            return this.ticksUntilNextAttack;
        }

        protected int getAttackInterval() {
            return 5;
        }

        protected double getAttackReachSqr(LivingEntity p_179512_1_) {
            return (double) (this.mob.getBbWidth() * 2.5F * this.mob.getBbWidth() * 1.8F + p_179512_1_.getBbWidth());
        }
    }

    @Override
    public void registerControllers(final AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "Normal", 5, this::Controller));
    }

    protected <E extends KentrosaurusEntity> PlayState Controller(final software.bernie.geckolib.core.animation.AnimationState<E> event) {
        if (this.isFromBook()) {
            event.setAndContinue(KENTRO_IDLE);
        }
        int animState = this.getAnimationState();
        {
            switch (animState) {

                case 21:
                    event.setAndContinue(KENTRO_SWIPE);
                    break;
                default:
                    if (this.isInSittingPose()) {
                        event.setAndContinue(KENTRO_SIT);
                        event.getController().setAnimationSpeed(1.0F);
                        return PlayState.CONTINUE;
                    }
                    if (this.isInWater()) {
                        event.setAndContinue(KENTRO_SWIM);
                        event.getController().setAnimationSpeed(1.0F);
                        return PlayState.CONTINUE;
                    } else if (event.isMoving() && !this.isInSittingPose()) {
                        {
                            event.setAndContinue(KENTRO_WALK);
                            event.getController().setAnimationSpeed(1.0F);
                            return PlayState.CONTINUE;
                        }
                    } else {
                        event.setAndContinue(KENTRO_IDLE);
                        event.getController().setAnimationSpeed(1.0F);
                        return PlayState.CONTINUE;
                    }

            }
        }
        return PlayState.CONTINUE;
    }


    @Override
    public double getTick(Object o) {
        return tickCount;
    }

}
