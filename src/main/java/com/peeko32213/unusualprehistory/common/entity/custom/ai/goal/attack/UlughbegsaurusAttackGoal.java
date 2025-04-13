package com.peeko32213.unusualprehistory.common.entity.custom.ai.goal.attack;

import com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.UlughbegsaurusEntity;
import com.peeko32213.unusualprehistory.common.entity.util.helper.HitboxAttacks;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class UlughbegsaurusAttackGoal extends Goal {

    protected final UlughbegsaurusEntity ulughbegsaurus;
    private int animTime = 0;

    Vec3 biteOffSet = new Vec3(0, 1, 2.3);

    public UlughbegsaurusAttackGoal(UlughbegsaurusEntity pMob) {
        this.ulughbegsaurus = pMob;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    public boolean canUse() {
        return ulughbegsaurus.getTarget() != null && ulughbegsaurus.getTarget().isAlive();
    }

    public void start() {
        ulughbegsaurus.setRunning(!ulughbegsaurus.isVehicle());
        ulughbegsaurus.setAnimationState(0);
        this.animTime = 0;
    }

    public void stop() {
        ulughbegsaurus.setRunning(false);
        ulughbegsaurus.setAnimationState(0);
    }

    public void tick() {
        LivingEntity target = ulughbegsaurus.getTarget();
        if (target != null) {
            ulughbegsaurus.lookAt(EntityAnchorArgument.Anchor.EYES, target.getEyePosition());
            double distance = ulughbegsaurus.distanceToSqr(target.getX(), target.getY(), target.getZ());
            int animState = ulughbegsaurus.getAnimationState();

            switch (animState) {
                case 21, 22 -> {
                    this.ulughbegsaurus.getNavigation().moveTo(target, 1.0D);
                    tickBiteAttack();
                }
                default -> {
                    this.ulughbegsaurus.getLookControl().setLookAt(target, 30.0F, 30.0F);
                    this.ulughbegsaurus.getNavigation().moveTo(target, 1.0D);
                    this.checkForCloseRangeAttack(distance);
                }
            }
        }
    }

    protected void checkForCloseRangeAttack (double distance){
        int r = (ulughbegsaurus.getRandom().nextInt(100) + 1);
        if (distance <= 17) {
            if (r <= 50) {
                ulughbegsaurus.setAnimationState(21);
            }
            else {
                ulughbegsaurus.setAnimationState(22);
            }
        }
    }

    protected void tickBiteAttack () {
        animTime++;
        if(animTime==9) {
            HitboxAttacks.pivotedPolyHitCheck(ulughbegsaurus, ulughbegsaurus, this.biteOffSet, 0.4, 0.65, 0.4, (ServerLevel) ulughbegsaurus.level(), (float) ulughbegsaurus.getAttribute(Attributes.ATTACK_DAMAGE).getValue(), (ulughbegsaurus.damageSources().mobAttack(ulughbegsaurus)), 0.15F, false, true, false);
        }
        if(animTime>=12) {
            animTime=0;
            this.ulughbegsaurus.setAnimationState(0);
        }
    }
}
