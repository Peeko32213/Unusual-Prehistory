package com.peeko32213.unusualprehistory.common.entity.custom.ai.goal.attack;

import com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.TyrannosaurusEntity;
import com.peeko32213.unusualprehistory.common.entity.util.helper.HitboxAttacks;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class TyrannosaurusAttackGoal extends Goal {

    protected final TyrannosaurusEntity tyrannosaurus;
    private final int meleeRange = 25;
    private int animTime = 0;

    private int tackleTime = 0;
    private int tackleCooldown = 0;
    private Vec3 tackleMotion = new Vec3(0,0,0);

    Vec3 biteOffSet = new Vec3(0, 1.5, 3.1);
    Vec3 stompOffset = new Vec3(0, 0.9, 0);
    Vec3 swipeOffset = new Vec3(0, 1.5, 3.25);
    Vec3 tackleOffSet = new Vec3(0, 1.5, 3.2);

    public TyrannosaurusAttackGoal(TyrannosaurusEntity pMob) {
        this.tyrannosaurus = pMob;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    public boolean canUse() {
        return tyrannosaurus.getTarget() != null && tyrannosaurus.getTarget().isAlive() && !tyrannosaurus.hasEepy();
    }

    public void start() {
        tyrannosaurus.setRunning(!tyrannosaurus.isVehicle());
        tyrannosaurus.setAnimationState(0);
        this.animTime = 0;
        this.tackleTime = 0;
    }

    public void stop() {
        tyrannosaurus.setRunning(false);
        tyrannosaurus.setTackling(false);
        tyrannosaurus.setStomping(false);
        tyrannosaurus.setSwiping(false);
//        tyrannosaurus.setAnimationState(0);
    }

    public void tick() {
        LivingEntity target = tyrannosaurus.getTarget();
        if (target != null) {
            tyrannosaurus.lookAt(EntityAnchorArgument.Anchor.EYES, target.getEyePosition());
            double distance = tyrannosaurus.distanceToSqr(target.getX(), target.getY(), target.getZ());
            int animState = tyrannosaurus.getAnimationState();

            switch (animState) {
                case 21, 22 -> {
                    tickBiteAttack();
                    this.tyrannosaurus.getNavigation().moveTo(target, 1.0D);
                }
                case 23, 24 -> tickStompAttack();
                case 25-> tickTailSwipeAttack();
                case 26 -> tickTackleAttack();
                default -> {
                    this.tackleCooldown = Math.max(this.tackleCooldown - 1, 0);
                    this.tyrannosaurus.getLookControl().setLookAt(target, 30.0F, 30.0F);
                    this.tyrannosaurus.getNavigation().moveTo(target, 1.0D);
                    this.checkForCloseRangeAttack(distance);
                }
            }
        }
    }

    protected void checkForCloseRangeAttack (double distance){
        int r = (tyrannosaurus.getRandom().nextInt(100) + 1);
        if (distance <= this.meleeRange) {
            if (r <= 10) {
                tyrannosaurus.setAnimationState(23);
            }
            else if (r <= 20) {
                tyrannosaurus.setAnimationState(24);
            }
            else if (r <= 30) {
                tyrannosaurus.setAnimationState(25);
            }
            else if (r <= 80) {
                tyrannosaurus.setAnimationState(21);
            }
            else {
                tyrannosaurus.setAnimationState(22);
            }
        }
        else if (distance <= 28) {
            if (r <= 10) {
                tyrannosaurus.setAnimationState(21);
            }
            else if (r <= 20) {
                tyrannosaurus.setAnimationState(22);
            }
            else if (r <= 30) {
                tyrannosaurus.setAnimationState(23);
            }
            else if (r <= 60) {
                tyrannosaurus.setAnimationState(24);
            }
            else {
                tyrannosaurus.setAnimationState(25);
            }
        }
        else if (distance > this.meleeRange && this.tackleCooldown <= 0 && !tyrannosaurus.isInWater()) {
            tyrannosaurus.setAnimationState(26);
        }
    }

    protected void tickBiteAttack () {
        animTime++;
        if (animTime == 11) {
            HitboxAttacks.pivotedPolyHitCheck(tyrannosaurus, tyrannosaurus, this.biteOffSet, 0.7, 0.8, 0.7, (ServerLevel) tyrannosaurus.level(), (float) tyrannosaurus.getAttribute(Attributes.ATTACK_DAMAGE).getValue(), (tyrannosaurus.damageSources().mobAttack(tyrannosaurus)), 0.75F, false, true, false);
        }
        if (animTime >= 18) {
            animTime = 0;
            this.tyrannosaurus.setAnimationState(0);
        }
    }

    protected void tickStompAttack () {
        animTime++;
        tyrannosaurus.getNavigation().stop();
        tyrannosaurus.setStomping(true);
        if (animTime == 18) {
            HitboxAttacks.pivotedPolyHitCheck(tyrannosaurus, tyrannosaurus, this.stompOffset, 3.4, -0.1, 3.4, (ServerLevel) tyrannosaurus.level(), (float) tyrannosaurus.getAttribute(Attributes.ATTACK_DAMAGE).getValue() * 1.2F, (tyrannosaurus.damageSources().mobAttack(tyrannosaurus)), 2.25F, true, false, false);
        }
        if (animTime >= 36) {
            animTime = 0;
            tyrannosaurus.setStomping(false);
            this.tyrannosaurus.setAnimationState(0);
        }
    }

    protected void tickTailSwipeAttack () {
        animTime++;
        tyrannosaurus.getNavigation().stop();
        tyrannosaurus.setSwiping(true);
        if (animTime == 11) {
            HitboxAttacks.pivotedPolyHitCheck(tyrannosaurus, tyrannosaurus, this.swipeOffset, 1.6, 0.8, 1.6, (ServerLevel) tyrannosaurus.level(), (float) tyrannosaurus.getAttribute(Attributes.ATTACK_DAMAGE).getValue(), (tyrannosaurus.damageSources().mobAttack(tyrannosaurus)), 2.5F, true, true, false);
        }
        if (animTime >= 38) {
            animTime = 0;
            tyrannosaurus.setSwiping(false);
            this.tyrannosaurus.setAnimationState(0);
        }
    }

    protected void tickTackleAttack () {
        tackleTime++;
        tyrannosaurus.getNavigation().stop();

        Entity target = tyrannosaurus.getTarget();
        tyrannosaurus.lookAt(target, 360, 30);
        tyrannosaurus.yBodyRot = tyrannosaurus.getYRot();

        double distance = tyrannosaurus.distanceToSqr(target.getX(), target.getY(), target.getZ());

        if (distance > 24 && !tyrannosaurus.isTackling()) {
            if (tackleTime == 2) {
                Vec3 targetPos = (target.position());
                double x = -((tyrannosaurus.position().x - targetPos.x));
                double z = -((tyrannosaurus.position().z - targetPos.z));
                this.tackleMotion = new Vec3(x, tyrannosaurus.getDeltaMovement().y, z).normalize();
            }
            if (tackleTime >= 3 && tackleTime < 26) {
                tyrannosaurus.setDeltaMovement(tackleMotion.x * 0.6, tyrannosaurus.getDeltaMovement().y, tackleMotion.z * 0.6);
            }
            if (tackleTime >= 26) {
                animTime = 0;
                tackleTime = 0;
                tyrannosaurus.setTackling(false);
                tyrannosaurus.setAnimationState(0);
                this.tackleCooldown = tyrannosaurus.getRandom().nextInt(100) + 50;
            }
        }
        else {
            animTime++;
            tyrannosaurus.setTackling(true);
            if (animTime == 10) {
                HitboxAttacks.pivotedPolyHitCheck(tyrannosaurus, tyrannosaurus, this.tackleOffSet, 0.9, 0.8, 0.9, (ServerLevel) tyrannosaurus.level(), (float) tyrannosaurus.getAttribute(Attributes.ATTACK_DAMAGE).getValue() * 0.8F, tyrannosaurus.damageSources().mobAttack(tyrannosaurus), 3.0F, true, true, false);
            }
            if (animTime >= 39) {
                animTime = 0;
                tackleTime = 0;
                tyrannosaurus.setTackling(false);
                tyrannosaurus.setAnimationState(0);
                this.tackleCooldown = tyrannosaurus.getRandom().nextInt(100) + 50;
            }
        }
    }
}
