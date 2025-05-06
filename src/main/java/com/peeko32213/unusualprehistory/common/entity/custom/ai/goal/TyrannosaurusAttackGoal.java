package com.peeko32213.unusualprehistory.common.entity.custom.ai.goal;

import com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.TyrannosaurusEntity;
import com.peeko32213.unusualprehistory.common.entity.util.helper.HitboxAttacks;
import com.peeko32213.unusualprehistory.core.registry.UPSounds;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class TyrannosaurusAttackGoal extends Goal {

    protected final TyrannosaurusEntity tyrannosaurus;
    private int attackTime = 0;

    private int tackleTime = 0;
    private int tackleCooldown = 0;

    Vec3 stompOffset = new Vec3(0, 0.9, 0);

    public TyrannosaurusAttackGoal(TyrannosaurusEntity pMob) {
        this.tyrannosaurus = pMob;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    public boolean canUse() {
        return this.tyrannosaurus.getTarget() != null && this.tyrannosaurus.getTarget().isAlive() && !this.tyrannosaurus.isEepy();
    }

    public void start() {
        this.tyrannosaurus.setRunning(!this.tyrannosaurus.isVehicle());
        this.tyrannosaurus.setAnimationState(0);
        this.attackTime = 0;
        this.tackleTime = 0;
    }

    public void stop() {
        this.tyrannosaurus.setRunning(false);
        this.tyrannosaurus.setAggro(false);
        this.tyrannosaurus.setTackling(false);
        this.tyrannosaurus.setStomping(false);
        this.tyrannosaurus.setSwiping(false);
        this.tyrannosaurus.setRoaring(false);
        this.tyrannosaurus.setAnimationState(0);
    }

    public void tick() {
        LivingEntity target = this.tyrannosaurus.getTarget();
        if (target != null) {

            this.tyrannosaurus.lookAt(this.tyrannosaurus.getTarget(), 30F, 30F);
            this.tyrannosaurus.getLookControl().setLookAt(this.tyrannosaurus.getTarget(), 30F, 30F);

            double distance = this.tyrannosaurus.distanceToSqr(target.getX(), target.getY(), target.getZ());
            int animState = this.tyrannosaurus.getAnimationState();

            switch (animState) {
                case 21, 22 -> {
                    tickBiteAttack();
                    this.tyrannosaurus.getNavigation().moveTo(target, 1.5D);
                }
                case 23, 24 -> tickStompAttack();
                case 25-> tickTailSwipeAttack();
                case 26 -> tickTackleAttack();
                default -> {
                    this.tackleCooldown = Math.max(this.tackleCooldown - 1, 0);
                    this.tyrannosaurus.getNavigation().moveTo(target, 1.75D);
                    this.checkForCloseRangeAttack(distance);
                }
            }
        }
    }

    protected void checkForCloseRangeAttack (double distance){
        this.tyrannosaurus.setAggro(distance <= 60);

        int r = (this.tyrannosaurus.getRandom().nextInt(100) + 1);
        if (distance <= 30) {
            if (r <= 20) {
                this.tyrannosaurus.setAnimationState(21);
            }
            else if (r <= 40) {
                this.tyrannosaurus.setAnimationState(22);
            }
            else if (r <= 60) {
                this.tyrannosaurus.setAnimationState(23);
            }
            else if (r <= 80) {
                this.tyrannosaurus.setAnimationState(24);
            }
            else {
                this.tyrannosaurus.setAnimationState(25);
            }
        }
        if (distance > 30 && this.tackleCooldown <= 0 && !this.tyrannosaurus.isInWater()) {
            this.tyrannosaurus.setAnimationState(26);
        }
    }

    protected void tickBiteAttack () {
        this.attackTime++;

        if (this.attackTime == 11) {
            if (this.tyrannosaurus.distanceTo(this.tyrannosaurus.getTarget()) < 4.5F) {
                this.tyrannosaurus.doHurtTarget(this.tyrannosaurus.getTarget());
            }
        }
        if (this.attackTime >= 18) {
            this.attackTime = 0;
            this.tyrannosaurus.setAnimationState(0);
        }
    }

    protected void tickStompAttack () {
        this.attackTime++;
        this.tyrannosaurus.setDeltaMovement(0, this.tyrannosaurus.getDeltaMovement().y, 0);
        this.tyrannosaurus.getNavigation().stop();
        this.tyrannosaurus.setStomping(true);

        if (this.attackTime == 18) {
            HitboxAttacks.pivotedPolyHitCheck(this.tyrannosaurus, this.tyrannosaurus, this.stompOffset, 3.4, -0.1, 3.4, (ServerLevel) tyrannosaurus.level(), (float) tyrannosaurus.getAttribute(Attributes.ATTACK_DAMAGE).getValue() * 1.2F, (tyrannosaurus.damageSources().mobAttack(tyrannosaurus)), 2.25F, true, false, false);
        }
        if (this.attackTime >= 36) {
            this.attackTime = 0;
            this.tyrannosaurus.setStomping(false);
            this.tyrannosaurus.setAnimationState(0);
        }
    }

    protected void tickTailSwipeAttack () {
        attackTime++;
        tyrannosaurus.setDeltaMovement(0, tyrannosaurus.getDeltaMovement().y, 0);
        tyrannosaurus.getNavigation().stop();
        tyrannosaurus.setSwiping(true);

        if (attackTime == 11) {
            if (tyrannosaurus.distanceTo(tyrannosaurus.getTarget()) < 5.5F) {
                tyrannosaurus.doHurtTarget(tyrannosaurus.getTarget());
            }
        }
        if (attackTime >= 36) {
            attackTime = 0;
            tyrannosaurus.setSwiping(false);
            tyrannosaurus.setAnimationState(0);
        }
    }

    protected void tickTackleAttack () {
        this.tackleTime++;
        Entity target = this.tyrannosaurus.getTarget();
        double distance = this.tyrannosaurus.distanceToSqr(target.getX(), target.getY(), target.getZ());

        if (distance > 24 && !this.tyrannosaurus.isTackling()) {
            if (this.tackleTime < 3) {
                this.tyrannosaurus.setDeltaMovement(0, this.tyrannosaurus.getDeltaMovement().y, 0);
                this.tyrannosaurus.getNavigation().stop();
            }
            if (this.tackleTime == 3) this.tyrannosaurus.playSound(UPSounds.TYRANNO_AGGRO.get(), 1.5F, 0.8F + this.tyrannosaurus.getRandom().nextFloat() * 0.1F);
            if (this.tackleTime > 3 && this.tackleTime < 26) {
                this.tyrannosaurus.setAggro(true);
                this.tyrannosaurus.getNavigation().moveTo(target, 2.2D);
            }
            if (this.tackleTime >= 26) {
                this.attackTime = 0;
                this.tackleTime = 0;
                this.tyrannosaurus.setTackling(false);
                this.tyrannosaurus.setAggro(false);
                this.tyrannosaurus.setAnimationState(0);
                if (this.tyrannosaurus.isAngry()) {
                    this.tackleCooldown = this.tyrannosaurus.getRandom().nextInt(25) + 50;
                }
                else this.tackleCooldown = this.tyrannosaurus.getRandom().nextInt(100) + 50;
            }
        }
        else {
            this.attackTime++;
            this.tyrannosaurus.setTackling(true);
            if (this.attackTime == 10) {
                if (this.tyrannosaurus.distanceTo(target) < 4.7F) {
                    this.tyrannosaurus.doHurtTarget(target);
                }
            }
            if (this.attackTime >= 39) {
                this.attackTime = 0;
                this.tackleTime = 0;
                this.tyrannosaurus.setTackling(false);
                this.tyrannosaurus.setAggro(false);
                this.tyrannosaurus.setAnimationState(0);
                if (this.tyrannosaurus.isAngry()) {
                    this.tackleCooldown = this.tyrannosaurus.getRandom().nextInt(25) + 50;
                }
                else this.tackleCooldown = this.tyrannosaurus.getRandom().nextInt(100) + 50;
            }
        }
    }
}
