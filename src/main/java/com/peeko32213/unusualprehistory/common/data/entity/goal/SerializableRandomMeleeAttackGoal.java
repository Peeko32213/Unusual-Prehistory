package com.peeko32213.unusualprehistory.common.data.entity.goal;

import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.Path;

import java.util.Optional;

public class SerializableRandomMeleeAttackGoal<T extends PathfinderMob> extends Goal {

    private final T entity;
    private final Optional<WeightedSerializableMeleeAttackHelper> helper;
    private int playTicks;
    private int cooldownTicks;
    private int timesActionPerformed;
    private final double speedModifier;
    private final boolean followingTargetEvenIfNotSeen;
    private Path path;
    private double pathedTargetX;
    private double pathedTargetY;
    private double pathedTargetZ;
    private int ticksUntilNextPathRecalculation;
    //private int ticksUntilNextAttack;
    private long lastCanUseCheck;
    private int failedPathFindingPenalty = 0;
    private boolean canPenalize = false;
    private final double attackSqrReachBbWidthModifier;
    //available in newer java version, sadly we're not there yet :(
    //public RandomStateGoal(T entity) {
    //  int states = entity.getStatesToPerform().size();
    //  StateHelper stateHelper = entity.getStatesToPerform().get(entity.getRandom().nextInt(states));
    //  this(entity, stateHelper);
    //}

    public SerializableRandomMeleeAttackGoal(T entity, WeightedRandomList<WeightedSerializableMeleeAttackHelper> randomList, double speedModifier, boolean followingTargetEvenIfNotSeen, double attackSqrReachBbWidthModifier) {
        this.entity = entity;
        this.speedModifier = speedModifier;
        this.followingTargetEvenIfNotSeen = followingTargetEvenIfNotSeen;
        this.attackSqrReachBbWidthModifier = attackSqrReachBbWidthModifier;
        this.helper = randomList.getRandom(entity.getRandom());
        helper.ifPresent(entityHelper -> {
            if( entityHelper.getSerializableStateHelper().isAffectsAI()) {
                
                this.setFlags(entityHelper.getSerializableStateHelper().getAffectedFlags());
            }
        });
    }

    @Override
    public boolean canUse() {
        if(this.helper.isEmpty()) return false;
        boolean hasConditions = this.helper.get().getSerializableStateHelper().getStartingPredicate().getPredicate().test(this.entity);
        if(!hasConditions || playTicks > this.helper.get().getSerializableStateHelper().getPlayTime() && cooldownTicks-- > 0) {
            return false;
        }
        long i = this.entity.level().getGameTime();

        if (i - this.lastCanUseCheck < 20L) {
            return false;
        } else {
            this.lastCanUseCheck = i;
            LivingEntity livingentity = this.entity.getTarget();
            if (livingentity == null) {
                return false;
            } else if (!livingentity.isAlive()) {
                return false;
            } else {
                if (canPenalize) {
                    if (--this.ticksUntilNextPathRecalculation <= 0) {
                        this.path = this.entity.getNavigation().createPath(livingentity, 0);
                        this.ticksUntilNextPathRecalculation = 4 + this.entity.getRandom().nextInt(7);
                        return this.path != null;
                    } else {
                        return true;
                    }
                }
                this.path = this.entity.getNavigation().createPath(livingentity, 0);
                if (this.path != null) {
                    return true;
                } else {
                    return this.getAttackReachSqr(livingentity) >= this.entity.distanceToSqr(livingentity.getX(), livingentity.getY(), livingentity.getZ());
                }
            }
        }

    }

    @Override
    public void start() {
        if(this.helper.isPresent()){
            this.helper.get().getSerializableStateHelper().getState().setValue(this.entity,true);
            this.entity.getNavigation().moveTo(this.path, this.speedModifier);
            this.entity.setAggressive(true);
            this.ticksUntilNextPathRecalculation = 0;
            //this.ticksUntilNextAttack = 0;
        }
    }

    @Override
    public void tick() {
        super.tick();
        this.playTicks++;
        if(this.helper.isEmpty()) return;
        LivingEntity target = this.entity.getTarget();
        double distance = this.entity.distanceToSqr(target.getX(), target.getY(), target.getZ());
        this.doMovement(target, distance);
        if(this.timesActionPerformed < this.helper.get().getSerializableStateHelper().getMeleeEntityAction().getTimesToPerform() &&
                this.playTicks >= this.helper.get().getSerializableStateHelper().getMeleeEntityAction().getTimeToPerformAction()) {
            this.helper.get().getSerializableStateHelper().getMeleeEntityAction().getAttack().performAttack(entity);
            this.timesActionPerformed++;
        }
    }

    protected void doMovement(LivingEntity livingentity, Double d0) {

        this.ticksUntilNextPathRecalculation = Math.max(this.ticksUntilNextPathRecalculation - 1, 0);

        if ((this.followingTargetEvenIfNotSeen || this.entity.getSensing().hasLineOfSight(livingentity)) && this.ticksUntilNextPathRecalculation <= 0 && (this.pathedTargetX == 0.0D && this.pathedTargetY == 0.0D && this.pathedTargetZ == 0.0D || livingentity.distanceToSqr(this.pathedTargetX, this.pathedTargetY, this.pathedTargetZ) >= 1.0D || this.entity.getRandom().nextFloat() < 0.05F)) {
            this.pathedTargetX = livingentity.getX();
            this.pathedTargetY = livingentity.getY();
            this.pathedTargetZ = livingentity.getZ();
            this.ticksUntilNextPathRecalculation = 4 + this.entity.getRandom().nextInt(7);
            if (this.canPenalize) {
                this.ticksUntilNextPathRecalculation += failedPathFindingPenalty;
                if (this.entity.getNavigation().getPath() != null) {
                    Node finalPathPoint = this.entity.getNavigation().getPath().getEndNode();
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
            if (!this.entity.getNavigation().moveTo(livingentity, this.speedModifier)) {
                this.ticksUntilNextPathRecalculation += 15;
            }
        }

    }
    

    @Override
    public boolean canContinueToUse() {
        if(this.helper.isEmpty() || playTicks > this.helper.get().getSerializableStateHelper().getPlayTime()) return false;


        LivingEntity livingentity = this.entity.getTarget();

        if (livingentity == null) {
            return false;
        }
        else if (!livingentity.isAlive()) {
            return false;
        }
        else if (!this.followingTargetEvenIfNotSeen) {
            return !this.entity.getNavigation().isDone();
        }
        else if (!this.entity.isWithinRestriction(livingentity.blockPosition())) {
            return false;
        }
        else {
            return !(livingentity instanceof Player) || !livingentity.isSpectator() && !((Player) livingentity).isCreative();
        }
    }


    @Override
    public void stop() {
        if(this.helper.isEmpty()) return;
        this.helper.get().getSerializableStateHelper().getState().setValue(this.entity,false);
        this.cooldownTicks = this.helper.get().getSerializableStateHelper().getStopTime();
        this.playTicks = 0;
        this.timesActionPerformed = 0;
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    protected double getAttackReachSqr(LivingEntity pAttackTarget) {
        return this.entity.getBbWidth() * attackSqrReachBbWidthModifier * this.entity.getBbWidth() * attackSqrReachBbWidthModifier + pAttackTarget.getBbWidth();
    }
}
