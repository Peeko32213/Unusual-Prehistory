package com.peeko32213.unusualprehistory.common.entity;

import com.peeko32213.unusualprehistory.common.entity.msc.util.goal.BabyPanicGoal;
import com.peeko32213.unusualprehistory.common.entity.msc.util.goal.CustomRandomStrollGoal;
import com.peeko32213.unusualprehistory.common.entity.msc.util.helper.HitboxHelper;
import com.peeko32213.unusualprehistory.common.entity.msc.util.dino.EntityBaseDinosaurAnimal;
import com.peeko32213.unusualprehistory.core.registry.UPItems;
import com.peeko32213.unusualprehistory.core.registry.UPTags;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.FollowParentGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.WrappedGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
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

import java.util.EnumSet;
//TODO LIST
// - Quils need their arrow capabilities, however we are still unsure on what the arrows should do to make them uniquely different
// - Rabies has been highly suggested by the dev team, however we have to figure out how to actually implement and make it unique.

public class EntityPsittacosaurus extends EntityBaseDinosaurAnimal {
    private static final EntityDataAccessor<Integer> COMBAT_STATE = SynchedEntityData.defineId(EntityPsittacosaurus.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> ENTITY_STATE = SynchedEntityData.defineId(EntityPsittacosaurus.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> ANIMATION_STATE = SynchedEntityData.defineId(EntityPsittacosaurus.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> SHEDDING_TIME = SynchedEntityData.defineId(EntityPsittacosaurus.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> SHEDDING = SynchedEntityData.defineId(EntityPsittacosaurus.class, EntityDataSerializers.BOOLEAN);
    private static final RawAnimation PSITTACO_IDLE_1 = RawAnimation.begin().thenLoop("animation.psittacosaurus.idle1");
    private static final RawAnimation PSITTACO_IDLE_2 = RawAnimation.begin().thenLoop("animation.psittacosaurus.idle2");
    private static final RawAnimation PSITTACO_SIT = RawAnimation.begin().thenLoop("animation.psittacosaurus.sit");
    private static final RawAnimation PSITTACO_SLEEP = RawAnimation.begin().thenLoop("animation.psittacosaurus.sleep");
    private static final RawAnimation PSITTACO_WALK = RawAnimation.begin().thenLoop("animation.psittacosaurus.walk");
    private static final RawAnimation PSITTACO_RUN = RawAnimation.begin().thenLoop("animation.psittacosaurus.run");
    private static final RawAnimation PSITTACO_SWIM = RawAnimation.begin().thenLoop("animation.psittacosaurus.swim");
    private static final RawAnimation PSITTACO_SHAKE = RawAnimation.begin().thenLoop("animation.psittacosaurus.shake");
    private static final RawAnimation PSITTACO_ANGER = RawAnimation.begin().thenLoop("animation.psittacosaurus.anger");
    private static final RawAnimation PSITTACO_SCRATCH_1 = RawAnimation.begin().thenLoop("animation.psittacosaurus.scratch1");
    private static final RawAnimation PSITTACO_SCRATCH_2 = RawAnimation.begin().thenLoop("animation.psittacosaurus.scratch2");
    private static final RawAnimation PSITTACO_ATTACK_1 = RawAnimation.begin().thenLoop("animation.psittacosaurus.attack1");
    private static final RawAnimation PSITTACO_ATTACK_2 = RawAnimation.begin().thenLoop("animation.psittacosaurus.attack2");
    private static final RawAnimation PSITTACO_ATTACK_3 = RawAnimation.begin().thenLoop("animation.psittacosaurus.attack3");
    private static final RawAnimation PSITTACO_CLAW_1 = RawAnimation.begin().thenLoop("animation.psittacosaurus.claw1");
    private static final RawAnimation PSITTACO_CLAW_2 = RawAnimation.begin().thenLoop("animation.psittacosaurus.claw2");
    private static final RawAnimation PSITTACO_CLAW_3 = RawAnimation.begin().thenLoop("animation.psittacosaurus.claw3");
    public int timeUntilDrops = this.random.nextInt(500) + 1000;
    public float prevPreenProgress;
    public float preenProgress;
    public EntityPsittacosaurus(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 15.0D)
                .add(Attributes.ARMOR, 0.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.23D)
                .add(Attributes.ATTACK_DAMAGE, 5.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.0D);
    }

    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(3, new BabyPanicGoal(this, 2.0D));
        this.goalSelector.addGoal(1, new EntityPsittacosaurus.PsittacoMeleeAttackGoal(this, 1.5F, true));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, LivingEntity.class, 10, false, false, entity -> entity.getType().is(UPTags.PSITTACO_TARGETS)));
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(3, new CustomRandomStrollGoal(this, 30, 1.0D, 100, 34));
        this.goalSelector.addGoal(5, new FollowParentGoal(this, 1.1D));
        this.targetSelector.addGoal(8, (new HurtByTargetGoal(this)));
    }

    public void tick() {
        super.tick();
        prevPreenProgress = preenProgress;


        if (random.nextInt(5000) == 0 && !this.getIsShedding()) {
            this.setSheddingTime(100 + random.nextInt(30));
            this.setIsShedding(true);
        }

        if(this.getSheddingTime() <= 0 && this.getIsShedding()){
            this.setIsShedding(false);
            this.goalSelector.getRunningGoals().forEach(WrappedGoal::start);
        }

        if(this.getIsShedding()) {
            this.setSheddingTime(this.getSheddingTime() - 1);
            this.getNavigation().stop();
            this.goalSelector.getRunningGoals().forEach(WrappedGoal::stop);

            if (random.nextInt(90) == 0) {
                this.spawnAtLocation(UPItems.PSITTACO_QUIL.get());
            }
        }
    }

    public boolean getIsShedding() {
        return this.entityData.get(SHEDDING);
    }

    public void setIsShedding(boolean preening) {
        this.entityData.set(SHEDDING, preening);
    }

    public int getSheddingTime() {
        return this.entityData.get(SHEDDING_TIME);
    }

    public void setSheddingTime(int shaking) {
        this.entityData.set(SHEDDING_TIME, shaking);
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ANIMATION_STATE, 0);
        this.entityData.define(COMBAT_STATE, 0);
        this.entityData.define(ENTITY_STATE, 0);
        this.entityData.define(SHEDDING_TIME, 0);
        this.entityData.define(SHEDDING, false);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("DropTime", this.timeUntilDrops);
        compound.putInt("sheddingTime", this.getSheddingTime());
        compound.putBoolean("isShedding", this.getIsShedding());

    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setSheddingTime(compound.getInt("sheddingTime"));
        this.setIsShedding(compound.getBoolean("isShedding"));

        if (compound.contains("shedTime")) {
            this.timeUntilDrops = compound.getInt("DropTime");
        }

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

    static class PsittacoMeleeAttackGoal extends Goal {

        protected final EntityPsittacosaurus mob;
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


        public PsittacoMeleeAttackGoal(EntityPsittacosaurus p_i1636_1_, double p_i1636_2_, boolean p_i1636_4_) {
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
                case 21 -> tickSpikeAttack();
                case 22 -> tickSpikeAttack();
                case 23 -> tickSpikeAttack();
                case 24 -> tickClawAttack();
                case 25 -> tickClawAttack();
                case 26 -> tickClawAttack();
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




        protected void tickSpikeAttack () {
            animTime++;
            if(animTime==4) {
                preformSpikeAttack();
            }
            if(animTime>=8) {
                animTime=0;
                if (this.getRangeCheck()) {
                    this.mob.setAnimationState(22);
                }else {
                    this.mob.setAnimationState(0);
                    this.resetAttackCooldown();
                    this.ticksUntilNextPathRecalculation = 0;
                }
            }
        }


        protected void tickClawAttack () {
            animTime++;
            if(animTime==4) {
                preformClawAttack();
            }
            if(animTime>=8) {
                animTime=0;
                if (this.getRangeCheck()) {
                    this.mob.setAnimationState(22);
                }else {
                    this.mob.setAnimationState(0);
                    this.resetAttackCooldown();
                    this.ticksUntilNextPathRecalculation = 0;
                }
            }
        }



        protected void preformSpikeAttack() {
            Vec3 pos = mob.position();
            HitboxHelper.LargeAttack(this.mob.damageSources().mobAttack(mob), 10.0f, 0.5f, mob, pos, 3.0F, -Math.PI / 2, Math.PI / 2, -1.0f, 3.0f);
        }

        protected void preformClawAttack() {
            Vec3 pos = mob.position();
            HitboxHelper.LargeAttack(this.mob.damageSources().mobAttack(mob), 5.0f, 0.2f, mob, pos, 2.0F, -Math.PI / 2, Math.PI / 2, -1.0f, 3.0f);
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
    public AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        return null;
    }

    private boolean isStillEnough() {
        return this.getDeltaMovement().horizontalDistance() < 0.05;
    }

    protected <E extends EntityPsittacosaurus> PlayState Controller(final software.bernie.geckolib.core.animation.AnimationState<E> event) {
        if(this.isFromBook()){
            return PlayState.CONTINUE;
        }
        int animState = this.getAnimationState();
        {
            switch (animState) {

                case 21:
                    event.setAndContinue(PSITTACO_ATTACK_1);
                    break;
                case 22:
                    event.setAndContinue(PSITTACO_ATTACK_2);
                    break;
                case 23:
                    event.setAndContinue(PSITTACO_ATTACK_3);
                    break;
                case 24:
                    event.setAndContinue(PSITTACO_CLAW_1);
                    break;
                case 25:
                    event.setAndContinue(PSITTACO_CLAW_2);
                    break;
                case 26:
                    event.setAndContinue(PSITTACO_CLAW_3);
                    break;
                default:
                    if (this.isInWater()) {
                        event.setAndContinue(PSITTACO_SWIM);
                        event.getController().setAnimationSpeed(1.0F);
                        return PlayState.CONTINUE;
                    }
                    if(this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6 && !this.isSwimming() && ! this.isInWater() && !isStillEnough()){
                        if(this.isSprinting()) {
                            event.setAndContinue(PSITTACO_RUN);
                        } else {
                            event.setAndContinue(PSITTACO_WALK);
                        }
                        event.getController().setAnimationSpeed(1.0F);
                        return PlayState.CONTINUE;
                    } else if (isStillEnough() && random.nextInt(100) == 0 && !this.isSwimming()) {
                        float rand = random.nextFloat();
                        if (rand < 0.85F) {
                            event.setAndContinue(PSITTACO_SIT);
                        }
                        if (rand < 0.85F) {
                            event.setAndContinue(PSITTACO_SCRATCH_2);
                        }
                        if (rand < 0.85F) {
                            event.setAndContinue(PSITTACO_SCRATCH_1);
                        }
                        if (rand < 0.85F) {
                            event.setAndContinue(PSITTACO_IDLE_2);
                        }
                        event.setAndContinue(PSITTACO_IDLE_1);
                    }
            }
        }
        return PlayState.CONTINUE;
    }

    protected <E extends EntityPsittacosaurus> PlayState shakeController(final software.bernie.geckolib.core.animation.AnimationState<E> event) {
        if (this.getIsShedding() && !this.isInWater()) {
            event.setAndContinue(PSITTACO_SHAKE);
            return PlayState.CONTINUE;
        }
        event.getController().forceAnimationReset();
        return PlayState.STOP;
    }

    @Override
    public void registerControllers(final AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "Normal", 5, this::Controller));
        controllers.add(new AnimationController<>(this, "Shake", 5, this::shakeController));
    }

    @Override
    public double getTick(Object o) {
        return tickCount;
    }
}
