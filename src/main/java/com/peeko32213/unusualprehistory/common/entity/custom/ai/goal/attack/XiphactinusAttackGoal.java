package com.peeko32213.unusualprehistory.common.entity.custom.ai.goal.attack;

import com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.aquatic.XiphactinusEntity;
import com.peeko32213.unusualprehistory.common.entity.util.helper.HitboxAttacks;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class XiphactinusAttackGoal extends Goal {

    protected final XiphactinusEntity xiphactinus;
    private int animTime = 0;
    private int ramTime = 0;
    private int ramCooldown = 0;
    private Vec3 ramMotion = new Vec3(0,0,0);

    Vec3 biteOffset = new Vec3(0, 0.5, 2.8);
    Vec3 ramOffset = new Vec3(0, 0.5, 2.9);

    public XiphactinusAttackGoal(XiphactinusEntity pMob) {
        this.xiphactinus = pMob;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    public boolean canUse() {
        return xiphactinus.getTarget() != null && xiphactinus.getTarget().isAlive();
    }

    public void start() {
        xiphactinus.setAnimationState(0);
        this.animTime = 0;
    }

    public void stop() {
        xiphactinus.setRamming(false);
        xiphactinus.setAnimationState(0);
    }

    public void tick() {
        LivingEntity target = xiphactinus.getTarget();
        if (target != null) {
            double distance = xiphactinus.distanceToSqr(target.getX(), target.getY(), target.getZ());
            xiphactinus.lookAt(EntityAnchorArgument.Anchor.EYES, target.getEyePosition());
            int animState = xiphactinus.getAnimationState();

            switch (animState) {
                case 21 -> {
                    tickBiteAttack();
                    xiphactinus.getNavigation().moveTo(target, 1.3F);
                }
                case 22 -> tickRamAttack();
                default -> {
                    this.ramCooldown = Math.max(this.ramCooldown - 1, 0);
                    xiphactinus.getNavigation().moveTo(target, 1.3F);
                    this.checkForCloseRangeAttack(distance);
                }
            }
        }
    }

    protected void checkForCloseRangeAttack (double distance){
        if (distance <= 16) {
            xiphactinus.setAnimationState(21);
        }
        else if (distance > 16 && this.ramCooldown <= 0) {
            xiphactinus.setAnimationState(22);
        }
    }

    protected void tickBiteAttack() {
        animTime++;
        if(animTime == 6) {
            HitboxAttacks.pivotedPolyHitCheck(xiphactinus, xiphactinus, this.biteOffset, 0.4, 0.5, 0.4, (ServerLevel) xiphactinus.level(), (float) xiphactinus.getAttribute(Attributes.ATTACK_DAMAGE).getValue(), (xiphactinus.damageSources().mobAttack(xiphactinus)), 0.5F, false, true, false);
        }
        if(animTime >= 17) {
            animTime = 0;
            xiphactinus.setAnimationState(0);
        }
    }

    protected void tickRamAttack () {
        ramTime++;
        xiphactinus.getNavigation().stop();

        Entity target = xiphactinus.getTarget();
        xiphactinus.lookAt(target, 360, 30);
        xiphactinus.yBodyRot = xiphactinus.getYRot();

        double distance = xiphactinus.distanceToSqr(target.getX(), target.getY(), target.getZ());

        if (distance > 14 && !xiphactinus.isRamming()) {
            if (ramTime == 18) {
                Vec3 targetPos = (target.position());
                double x = -((xiphactinus.position().x - targetPos.x));
                double y = -((xiphactinus.position().y - targetPos.y));
                double z = -((xiphactinus.position().z - targetPos.z));
                this.ramMotion = new Vec3(x, y, z).normalize();
            }
            if (ramTime >= 20 && ramTime < 48) {
                if (xiphactinus.isInWater()) {
                    xiphactinus.setDeltaMovement(ramMotion.x * 0.8, ramMotion.y * 0.8, ramMotion.z * 0.8);
                }
                else {
                    xiphactinus.setDeltaMovement(ramMotion.x * 0.8, xiphactinus.getDeltaMovement().y, ramMotion.z * 0.8);
                }
            }
            if (ramTime >= 48) {
                animTime = 0;
                ramTime = 0;
                xiphactinus.setRamming(false);
                xiphactinus.setAnimationState(0);
                this.ramCooldown = xiphactinus.getRandom().nextInt(40) + 40;
            }
        }
        else {
            animTime++;
            xiphactinus.setRamming(true);
            if (animTime == 6) {
                HitboxAttacks.pivotedPolyHitCheck(xiphactinus, xiphactinus, this.ramOffset, 0.5, 0.6, 0.5, (ServerLevel) xiphactinus.level(), (float) xiphactinus.getAttribute(Attributes.ATTACK_DAMAGE).getValue() * 1.2F, (xiphactinus.damageSources().mobAttack(xiphactinus)), 1F, true, true, false);
            }
            if (animTime >= 11) {
                animTime = 0;
                ramTime = 0;
                xiphactinus.setRamming(false);
                xiphactinus.setAnimationState(0);
                this.ramCooldown = xiphactinus.getRandom().nextInt(40) + 40;
            }
        }
    }
}
