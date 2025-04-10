package com.peeko32213.unusualprehistory.common.entity.custom.ai.goal.attack;

import com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.PachycephalosaurusEntity;
import com.peeko32213.unusualprehistory.common.entity.util.helper.HitboxAttacks;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class PachycephalosaurusAttackGoal extends Goal {

    protected final PachycephalosaurusEntity pachycephalosaurus;
    private int animTime = 0;
    private int chargeCooldown = 0;
    private Vec3 chargeMotion = new Vec3(0,0,0);

    Vec3 bonkOffset = new Vec3(0, 1, 1.1);
    Vec3 chargeOffset = new Vec3(0, 1, 1.15);

    public PachycephalosaurusAttackGoal(PachycephalosaurusEntity pMob) {
        this.pachycephalosaurus = pMob;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    public boolean canUse() {
        return pachycephalosaurus.getTarget() != null && pachycephalosaurus.getTarget().isAlive();
    }

    public void start() {
        pachycephalosaurus.setRunning(!pachycephalosaurus.isVehicle());
        pachycephalosaurus.setAnimationState(0);
        this.animTime = 0;
    }

    public void stop() {
        pachycephalosaurus.setRunning(false);
        pachycephalosaurus.setCharging(false);
        pachycephalosaurus.setAnimationState(0);
    }

    public void tick() {
        LivingEntity target = pachycephalosaurus.getTarget();
        if (target != null) {
            pachycephalosaurus.lookAt(EntityAnchorArgument.Anchor.EYES, target.getEyePosition());
            double distance = pachycephalosaurus.distanceToSqr(target.getX(), target.getY(), target.getZ());
            int animState = pachycephalosaurus.getAnimationState();

            switch (animState) {
                case 21, 22, 23 -> tickBonkAttack();
                case 24 -> tickChargeAttack();
                default -> {
                    this.chargeCooldown = Math.max(this.chargeCooldown - 1, 0);
                    this.pachycephalosaurus.getLookControl().setLookAt(target, 30.0F, 30.0F);
                    this.pachycephalosaurus.getNavigation().moveTo(target, 1.0D);
                    this.checkForCloseRangeAttack(distance);
                }
            }
        }
    }

    protected void checkForCloseRangeAttack (double distance){
        int meleeRange = 6;
        int r = (pachycephalosaurus.getRandom().nextInt(100) + 1);
        if (distance <= meleeRange) {
            if (r <= 33) {
                pachycephalosaurus.setAnimationState(21);
            }
            else if (r <= 66) {
                pachycephalosaurus.setAnimationState(22);
            }
            else {
                pachycephalosaurus.setAnimationState(23);
            }
        }
        else if (distance > meleeRange && this.chargeCooldown <= 0 && !pachycephalosaurus.isInWater()) {
            pachycephalosaurus.setAnimationState(24);
        }
    }

    protected void tickBonkAttack () {
        animTime++;
        if(animTime==9) {
            HitboxAttacks.pivotedPolyHitCheck(pachycephalosaurus, pachycephalosaurus, this.bonkOffset, 0.2, 0.4, 0.2, (ServerLevel) pachycephalosaurus.level(), (float) pachycephalosaurus.getAttribute(Attributes.ATTACK_DAMAGE).getValue(), (pachycephalosaurus.damageSources().mobAttack(pachycephalosaurus)), 0.75F, false, true, false);
        }
        if(animTime>=11) {
            animTime=0;
            pachycephalosaurus.setAnimationState(0);
        }
    }

    protected void tickChargeAttack () {
        animTime++;
        pachycephalosaurus.getNavigation().stop();
        pachycephalosaurus.setCharging(true);

        Entity target = pachycephalosaurus.getTarget();
        pachycephalosaurus.lookAt(target, 360, 30);
        pachycephalosaurus.yBodyRot = pachycephalosaurus.getYRot();

        if (animTime == 28) {
            Vec3 targetPos = (target.position());
            double x = -((pachycephalosaurus.position().x - targetPos.x));
            double z = -((pachycephalosaurus.position().z - targetPos.z));
            this.chargeMotion = new Vec3(x, pachycephalosaurus.getDeltaMovement().y, z).normalize();
        }

        if (animTime >= 30 && animTime < 40) {
            pachycephalosaurus.setDeltaMovement(chargeMotion.x * 0.9, pachycephalosaurus.getDeltaMovement().y, chargeMotion.z * 0.9);
            HitboxAttacks.pivotedPolyHitCheck(pachycephalosaurus, pachycephalosaurus, this.chargeOffset, 0.2, 0.4, 0.2, (ServerLevel) pachycephalosaurus.level(), (float) pachycephalosaurus.getAttribute(Attributes.ATTACK_DAMAGE).getValue() * 1.25F, pachycephalosaurus.damageSources().mobAttack(pachycephalosaurus), 2.0F, true, false, false);
        }

        if(animTime >= 40) {
            animTime = 0;
            pachycephalosaurus.setCharging(false);
            pachycephalosaurus.setAnimationState(0);
            this.chargeCooldown = pachycephalosaurus.getRandom().nextInt(60) + 30;
        }
    }
}
