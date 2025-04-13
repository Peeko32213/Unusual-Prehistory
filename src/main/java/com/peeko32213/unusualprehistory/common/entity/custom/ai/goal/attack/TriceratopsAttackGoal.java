package com.peeko32213.unusualprehistory.common.entity.custom.ai.goal.attack;

import com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.TriceratopsEntity;
import com.peeko32213.unusualprehistory.common.entity.util.helper.HitboxAttacks;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class TriceratopsAttackGoal extends Goal {

    protected final TriceratopsEntity triceratops;
    private int animTime = 0;

    private int chargeCooldown;
    private Vec3 chargeMotion = new Vec3(0,0,0);

    Vec3 chargeOffset = new Vec3(0, 1.4, 3.5);
    Vec3 strikeOffSet = new Vec3(0, 1.4, 3.3);

    public TriceratopsAttackGoal(TriceratopsEntity pMob) {
        this.triceratops = pMob;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    public boolean canUse() {
        return triceratops.getTarget() != null && triceratops.getTarget().isAlive();
    }

    public void start() {
        triceratops.setRunning(!triceratops.isVehicle());
        triceratops.setAnimationState(0);
        this.animTime = 0;
        this.chargeCooldown = 0;
    }

    public void stop() {
        triceratops.setRunning(false);
        triceratops.setCharging(false);
        triceratops.setAnimationState(0);
    }

    public void tick() {
        LivingEntity target = triceratops.getTarget();
        if (target != null) {
            triceratops.lookAt(EntityAnchorArgument.Anchor.EYES, target.getEyePosition());
            double distance = triceratops.distanceToSqr(target.getX(), target.getY(), target.getZ());
            int animState = triceratops.getAnimationState();

            switch (animState) {
                case 21, 22 -> {
                    tickStrikeAttack();
                    this.triceratops.getNavigation().moveTo(target, 1.0D);
                }
                case 23 -> tickChargeAttack();
                default -> {
                    this.chargeCooldown = Math.max(this.chargeCooldown - 1, 0);
                    this.triceratops.getLookControl().setLookAt(target, 30.0F, 30.0F);
                    this.triceratops.getNavigation().moveTo(target, 1.0D);
                    this.checkForCloseRangeAttack(distance);
                }
            }
        }
    }

    protected void checkForCloseRangeAttack (double distance){
        int meleeRange = 36;
        int r = (triceratops.getRandom().nextInt(100) + 1);
        if (distance <= meleeRange) {
            if (r <= 50) {
                triceratops.setAnimationState(21);
            }
            else {
                triceratops.setAnimationState(22);
            }
        }
        else if (distance > meleeRange && this.chargeCooldown <= 0 && !triceratops.isInWater()) {
            triceratops.setAnimationState(23);
        }
    }

    protected void tickStrikeAttack () {
        animTime++;
        if(animTime==10) {
            HitboxAttacks.pivotedPolyHitCheck(triceratops, this.triceratops, this.strikeOffSet, 0.6, 0.75, 0.6, (ServerLevel)this.triceratops.level(), (float) triceratops.getAttribute(Attributes.ATTACK_DAMAGE).getValue(), (this.triceratops.damageSources().mobAttack(triceratops)), 1.5F, false, true, false);
        }
        if(animTime>=11) {
            animTime=0;
            this.triceratops.setAnimationState(0);
        }
    }

    protected void tickChargeAttack () {
        animTime++;
        triceratops.getNavigation().stop();
        triceratops.setCharging(true);

        Entity target = triceratops.getTarget();
        triceratops.lookAt(target, 360, 30);
        triceratops.yBodyRot = triceratops.getYRot();

        if (animTime == 46) {
            Vec3 targetPos = (target.position());
            double x = -((triceratops.position().x - targetPos.x));
            double z = -((triceratops.position().z - targetPos.z));
            this.chargeMotion = new Vec3(x, triceratops.getDeltaMovement().y, z).normalize();
        }

        if (animTime >= 48 && animTime < 64) {
            triceratops.setDeltaMovement(chargeMotion.x * 0.88, triceratops.getDeltaMovement().y, chargeMotion.z * 0.88);
            HitboxAttacks.pivotedPolyHitCheck(triceratops, triceratops, this.chargeOffset, 0.6, 0.75, 0.6, (ServerLevel) triceratops.level(), (float) triceratops.getAttribute(Attributes.ATTACK_DAMAGE).getValue() * 1.25F, triceratops.damageSources().mobAttack(triceratops), 2.5F, true, false, false);
        }

        if(animTime >= 64) {
            animTime = 0;
            triceratops.setCharging(false);
            triceratops.setAnimationState(0);
            this.chargeCooldown = triceratops.getRandom().nextInt(100) + 50;
        }
    }
}