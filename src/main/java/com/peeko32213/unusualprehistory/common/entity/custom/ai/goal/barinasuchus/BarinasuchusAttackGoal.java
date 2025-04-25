package com.peeko32213.unusualprehistory.common.entity.custom.ai.goal.barinasuchus;

import com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.BarinasuchusEntity;
import com.peeko32213.unusualprehistory.common.entity.util.helper.HitboxAttacks;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class BarinasuchusAttackGoal extends Goal {

    protected final BarinasuchusEntity barinasuchus;
    private int animTime = 0;

    Vec3 biteOffSet = new Vec3(0, 0.25, 2.2);

    public BarinasuchusAttackGoal(BarinasuchusEntity pMob) {
        this.barinasuchus = pMob;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    public boolean canUse() {
        return barinasuchus.getTarget() != null && barinasuchus.getTarget().isAlive();
    }

    public void start() {
        barinasuchus.setRunning(!barinasuchus.isVehicle());
        barinasuchus.setAnimationState(0);
        this.animTime = 0;
    }

    public void stop() {
        barinasuchus.setRunning(false);
        barinasuchus.setAnimationState(0);
    }

    public void tick() {
        LivingEntity target = barinasuchus.getTarget();
        if (target != null) {
            barinasuchus.lookAt(EntityAnchorArgument.Anchor.EYES, target.getEyePosition());
            double distance = barinasuchus.distanceToSqr(target.getX(), target.getY(), target.getZ());
            int animState = barinasuchus.getAnimationState();

            switch (animState) {
                case 21, 22 -> {
                    this.barinasuchus.getNavigation().moveTo(target, 1.0D);
                    tickBiteAttack();
                }
                default -> {
                    this.barinasuchus.getLookControl().setLookAt(target, 30.0F, 30.0F);
                    this.barinasuchus.getNavigation().moveTo(target, 1.0D);
                    this.checkForCloseRangeAttack(distance);
                }
            }
        }
    }

    protected void checkForCloseRangeAttack (double distance){
        int meleeRange = 16;
        int r = (barinasuchus.getRandom().nextInt(100) + 1);
        if (distance <= meleeRange) {
            if (r <= 50) {
                barinasuchus.setAnimationState(21);
            }
            else {
                barinasuchus.setAnimationState(22);
            }
        }
    }

    protected void tickBiteAttack () {
        animTime++;
        if(animTime==9) {
            HitboxAttacks.pivotedPolyHitCheck(barinasuchus, barinasuchus, this.biteOffSet, 0.45, 0.5, 0.25, (ServerLevel) barinasuchus.level(), (float) barinasuchus.getAttribute(Attributes.ATTACK_DAMAGE).getValue(), (barinasuchus.damageSources().mobAttack(barinasuchus)), 0.1F, false, true, false);
        }
        if(animTime>=14) {
            animTime=0;
            this.barinasuchus.setAnimationState(0);
        }
    }
}
