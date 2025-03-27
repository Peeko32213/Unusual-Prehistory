package com.peeko32213.unusualprehistory.common.entity.custom.ai.goal.attack;

import com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.BarinasuchusEntity;
import com.peeko32213.unusualprehistory.common.entity.util.helper.HitboxAttacks;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class BarinasuchusAttackGoal extends Goal {

    protected final BarinasuchusEntity mob;
    private final int meleeRange = 20;
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

    Vec3 biteOffSet = new Vec3(0, 0.25, 1.6);

    public BarinasuchusAttackGoal(BarinasuchusEntity pMob, double pSpeedModifier, boolean pFollowingTargetEvenIfNotSeen) {
        this.mob = pMob;
        this.speedModifier = pSpeedModifier;
        this.followingTargetEvenIfNotSeen = pFollowingTargetEvenIfNotSeen;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
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
        this.mob.setAnimationState(0);
    }

    public void tick() {

        LivingEntity target = this.mob.getTarget();
        double distance = this.mob.distanceToSqr(target.getX(), target.getY(), target.getZ());
        int animState = this.mob.getAnimationState();

        switch (animState) {
            case 21, 22 -> tickBiteAttack();
            default -> {
                this.ticksUntilNextPathRecalculation = Math.max(this.ticksUntilNextPathRecalculation - 1, 0);
                this.ticksUntilNextAttack = Math.max(this.ticksUntilNextPathRecalculation - 1, 0);
                this.mob.getLookControl().setLookAt(target, 30.0F, 30.0F);
                this.doMovement(target, distance);
                this.checkForCloseRangeAttack(distance);
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

    protected void checkForCloseRangeAttack (double distance){
        if (distance <= meleeRange && this.ticksUntilNextAttack <= 0) {
            int r = (this.mob.getRandom().nextInt(100) + 1);

            if (r <= 50) {
                this.mob.setAnimationState(21);
            }
            else {
                this.mob.setAnimationState(22);
            }
        }
    }

    protected boolean getRangeCheck () {
        return this.mob.distanceToSqr(this.mob.getTarget().getX(), this.mob.getTarget().getY(), this.mob.getTarget().getZ()) <= 1.8F * this.getAttackReachSqr(this.mob.getTarget());
    }

    protected void tickBiteAttack () {
        animTime++;
        LivingEntity target = this.mob.getTarget();
        this.mob.lookAt(target, 100000, 100000);
        this.mob.yBodyRot = this.mob.yHeadRot;

        if(animTime==9) {
            //System.out.println("bite");
            preformBiteAttack();
        }
        if(animTime>=10) {
            animTime=0;
            this.mob.setAnimationState(0);
            this.resetAttackCooldown();
            this.ticksUntilNextPathRecalculation = 0;
        }
    }

    protected void preformBiteAttack () {
        this.mob.setDeltaMovement(this.mob.getDeltaMovement().scale(0));
        HitboxAttacks.pivotedPolyHitCheck(mob, this.mob, this.biteOffSet, 0.75, 1, 1.0, (ServerLevel)this.mob.level(), (float) mob.getAttribute(Attributes.ATTACK_DAMAGE).getValue(), (this.mob.damageSources().mobAttack(mob)), 0.15F, false, true, false);
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

    protected double getAttackReachSqr(LivingEntity entity) {
        return (double)(this.mob.getBbWidth() * 2.5F * this.mob.getBbWidth() * 1.8F + entity.getBbWidth());
    }
}
