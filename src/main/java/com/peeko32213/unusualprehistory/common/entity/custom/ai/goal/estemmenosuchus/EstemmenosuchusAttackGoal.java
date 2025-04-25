package com.peeko32213.unusualprehistory.common.entity.custom.ai.goal.estemmenosuchus;

import com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.EstemmenosuchusEntity;
import com.peeko32213.unusualprehistory.common.entity.util.helper.HitboxAttacks;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class EstemmenosuchusAttackGoal extends Goal {

    protected final EstemmenosuchusEntity estemmenosuchus;
    private int animTime = 0;

    private int ramCooldown;
    private Vec3 ramMotion = new Vec3(0,0,0);

    Vec3 ramOffset = new Vec3(0, 1.5, 3.2);
    Vec3 biteOffSet = new Vec3(0, 1.5, 3.0);

    public EstemmenosuchusAttackGoal(EstemmenosuchusEntity pMob) {
        this.estemmenosuchus = pMob;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    public boolean canUse() {
        return estemmenosuchus.getTarget() != null && estemmenosuchus.getTarget().isAlive();
    }

    public void start() {
        estemmenosuchus.setRunning(!estemmenosuchus.isVehicle());
        estemmenosuchus.setAnimationState(0);
        this.animTime = 0;
        this.ramCooldown = 0;
    }

    public void stop() {
        estemmenosuchus.setRunning(false);
        estemmenosuchus.setRamming(false);
        estemmenosuchus.setAnimationState(0);
    }

    public void tick() {
        LivingEntity target = estemmenosuchus.getTarget();
        if (target != null) {
            estemmenosuchus.lookAt(EntityAnchorArgument.Anchor.EYES, target.getEyePosition());
            double distance = estemmenosuchus.distanceToSqr(target.getX(), target.getY(), target.getZ());
            int animState = estemmenosuchus.getAnimationState();

            switch (animState) {
                case 21, 22 -> {
                    tickBiteAttack();
                    this.estemmenosuchus.getNavigation().moveTo(target, 1.0D);
                }
                case 23 -> tickRamAttack();
                default -> {
                    this.ramCooldown = Math.max(this.ramCooldown - 1, 0);
                    this.estemmenosuchus.getLookControl().setLookAt(target, 30.0F, 30.0F);
                    this.estemmenosuchus.getNavigation().moveTo(target, 1.0D);
                    this.checkForCloseRangeAttack(distance);
                }
            }
        }
    }

    protected void checkForCloseRangeAttack (double distance){
        int r = (estemmenosuchus.getRandom().nextInt(100) + 1);
        if (distance <= 34) {
            if (r <= 50) {
                estemmenosuchus.setAnimationState(21);
            }
            else {
                estemmenosuchus.setAnimationState(22);
            }
        }
        else if ((distance > 34 && distance < 80) && this.ramCooldown <= 0 && !estemmenosuchus.isInWater()) {
            estemmenosuchus.setAnimationState(23);
        }
    }

    protected void tickBiteAttack() {
        animTime++;

        LivingEntity target = estemmenosuchus.getTarget();
        estemmenosuchus.lookAt(target, 360, 60);
        estemmenosuchus.yBodyRot = estemmenosuchus.yHeadRot;

        if(animTime==9) {
            HitboxAttacks.pivotedPolyHitCheck(estemmenosuchus, this.estemmenosuchus, this.biteOffSet, 0.55, 0.75, 0.55, (ServerLevel)this.estemmenosuchus.level(), (float) estemmenosuchus.getAttribute(Attributes.ATTACK_DAMAGE).getValue(), (this.estemmenosuchus.damageSources().mobAttack(estemmenosuchus)), 0.5F, false, true, false);
        }
        if(animTime>=15) {
            animTime=0;
            this.estemmenosuchus.setAnimationState(0);
        }
    }

    protected void tickRamAttack() {
        animTime++;
        estemmenosuchus.getNavigation().stop();
        estemmenosuchus.setRamming(true);

        Entity target = estemmenosuchus.getTarget();
        estemmenosuchus.lookAt(target, 360, 60);
        estemmenosuchus.yBodyRot = estemmenosuchus.getYRot();

        if (animTime == 17) {
            Vec3 targetPos = (target.position());
            double x = -((estemmenosuchus.position().x - targetPos.x));
            double z = -((estemmenosuchus.position().z - targetPos.z));
            this.ramMotion = new Vec3(x, estemmenosuchus.getDeltaMovement().y, z).normalize();
        }

        if (animTime >= 19 && animTime < 35) {
            estemmenosuchus.setDeltaMovement(ramMotion.x * 0.5, estemmenosuchus.getDeltaMovement().y, ramMotion.z * 0.5);
            HitboxAttacks.pivotedPolyHitCheck(estemmenosuchus, estemmenosuchus, this.ramOffset, 0.6, 0.75, 0.6, (ServerLevel) estemmenosuchus.level(), (float) estemmenosuchus.getAttribute(Attributes.ATTACK_DAMAGE).getValue() * 0.8F, estemmenosuchus.damageSources().mobAttack(estemmenosuchus), 2.25F, true, false, false);
        }

        if(animTime >= 35) {
            animTime = 0;
            estemmenosuchus.setRamming(false);
            estemmenosuchus.setAnimationState(0);
            this.ramCooldown = estemmenosuchus.getRandom().nextInt(100) + 50;
        }
    }
}