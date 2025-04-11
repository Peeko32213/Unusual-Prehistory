package com.peeko32213.unusualprehistory.common.entity.custom.ai.goal.attack;

import com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.aquatic.XiphactinusEntity;
import com.peeko32213.unusualprehistory.common.entity.util.helper.HitboxAttacks;
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
    private int ramCooldown = 0;
    private Vec3 ramMotion = new Vec3(0,0,0);

    Vec3 biteOffset = new Vec3(0, 1, 3.1);
    Vec3 ramOffset = new Vec3(0, 1, 3.15);

    public XiphactinusAttackGoal(XiphactinusEntity pMob) {
        this.xiphactinus = pMob;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    public boolean canUse() {
        return xiphactinus.getTarget() != null && xiphactinus.getTarget().isAlive();
    }

    public void start() {
        xiphactinus.setRunning(!xiphactinus.isVehicle());
        xiphactinus.setAnimationState(0);
        this.animTime = 0;
    }

    public void stop() {
        xiphactinus.setRunning(false);
        xiphactinus.setRamming(false);
        xiphactinus.setAnimationState(0);
    }

    public void tick() {
        LivingEntity target = xiphactinus.getTarget();
        if (target != null) {
            xiphactinus.lookAt(target, 360, 360);
            xiphactinus.yBodyRot = xiphactinus.getYRot();
            double distance = xiphactinus.distanceToSqr(target.getX(), target.getY(), target.getZ());
            int animState = xiphactinus.getAnimationState();

            switch (animState) {
                case 21 -> tickBiteAttack();
                case 22 -> tickRamAttack();
                default -> {
                    this.ramCooldown = Math.max(this.ramCooldown - 1, 0);
                    this.xiphactinus.getLookControl().setLookAt(target, 30.0F, 30.0F);
                    this.xiphactinus.getNavigation().moveTo(target, 1.0D);
                    this.checkForCloseRangeAttack(distance);
                }
            }
        }
    }

    protected void checkForCloseRangeAttack (double distance){
        int meleeRange = 16;
        if (distance <= meleeRange) {
            xiphactinus.setAnimationState(21);
        }
        else if (distance > meleeRange && this.ramCooldown <= 0) {
            xiphactinus.setAnimationState(22);
        }
    }

    protected void tickBiteAttack() {
        animTime++;
        if(animTime==9) {
            HitboxAttacks.pivotedPolyHitCheck(xiphactinus, xiphactinus, this.biteOffset, 0.2, 0.4, 0.2, (ServerLevel) xiphactinus.level(), (float) xiphactinus.getAttribute(Attributes.ATTACK_DAMAGE).getValue(), (xiphactinus.damageSources().mobAttack(xiphactinus)), 0.75F, false, true, true);
        }
        if(animTime>=11) {
            animTime=0;
            xiphactinus.setAnimationState(0);
        }
    }

    protected void tickRamAttack () {
        animTime++;
        xiphactinus.getNavigation().stop();
        xiphactinus.setRamming(true);

        Entity target = xiphactinus.getTarget();
        xiphactinus.lookAt(target, 360, 30);
        xiphactinus.yBodyRot = xiphactinus.getYRot();

        if (animTime == 28) {
            Vec3 targetPos = (target.position());
            double x = -((xiphactinus.position().x - targetPos.x));
            double y = -((xiphactinus.position().y - targetPos.y));
            double z = -((xiphactinus.position().z - targetPos.z));
            this.ramMotion = new Vec3(x, y, z).normalize();
        }

        if (animTime >= 30 && animTime < 40) {
            xiphactinus.setDeltaMovement(ramMotion.x * 0.9, ramMotion.y * 0.9, ramMotion.z * 0.9);
            HitboxAttacks.pivotedPolyHitCheck(xiphactinus, xiphactinus, this.ramOffset, 0.2, 0.4, 0.2, (ServerLevel) xiphactinus.level(), (float) xiphactinus.getAttribute(Attributes.ATTACK_DAMAGE).getValue() * 1.25F, xiphactinus.damageSources().mobAttack(xiphactinus), 2.0F, true, false, true);
        }

        if(animTime >= 40) {
            animTime = 0;
            xiphactinus.setRamming(false);
            xiphactinus.setAnimationState(0);
            this.ramCooldown = xiphactinus.getRandom().nextInt(20) + 10;
        }
    }
}
