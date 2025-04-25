package com.peeko32213.unusualprehistory.common.entity.custom.ai.goal.dunkleosteus;

import com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.aquatic.DunkleosteusEntity;
import com.peeko32213.unusualprehistory.common.entity.util.helper.HitboxAttacks;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class DunkleosteusAttackGoal extends Goal {

    protected final DunkleosteusEntity dunkleosteus;
    private int animTime = 0;

    Vec3 largeBiteOffSet = new Vec3(0, 0.3, 1.25);
    Vec3 mediumBiteOffSet = new Vec3(0, 0.25, 1.0);
    Vec3 smallBiteOffSet = new Vec3(0, 0.2, 0.75);

    public DunkleosteusAttackGoal(DunkleosteusEntity pMob) {
        this.dunkleosteus = pMob;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    public boolean canUse() {
        return dunkleosteus.getTarget() != null && dunkleosteus.getTarget().isAlive();
    }

    public void start() {
        dunkleosteus.setRunning(!dunkleosteus.isVehicle());
        dunkleosteus.setAnimationState(0);
        this.animTime = 0;
    }

    public void stop() {
        dunkleosteus.setRunning(false);
        dunkleosteus.setAnimationState(0);
    }

    public void tick() {
        LivingEntity target = dunkleosteus.getTarget();
        if (target != null) {
            dunkleosteus.lookAt(EntityAnchorArgument.Anchor.EYES, target.getEyePosition());
            double distance = dunkleosteus.distanceToSqr(target.getX(), target.getY(), target.getZ());
            int animState = dunkleosteus.getAnimationState();

            if (animState == 21) {
                tickBiteAttack();
            } else {
                this.dunkleosteus.getLookControl().setLookAt(target, 30.0F, 30.0F);
                this.dunkleosteus.getNavigation().moveTo(target, 1.0D);
                this.checkForCloseRangeAttack(distance);
            }
        }
    }

    protected void checkForCloseRangeAttack (double distance){
        int meleeRange = 75;
        if (distance <= meleeRange) {
            dunkleosteus.setAnimationState(21);
        }
    }

    protected void tickBiteAttack () {
        animTime++;
        if(animTime==9) {
            preformBiteAttack();
        }

        if(animTime>=10) {
            animTime=0;
            this.dunkleosteus.setAnimationState(0);
        }
    }

    protected void preformBiteAttack () {
        HitboxAttacks.pivotedPolyHitCheck(dunkleosteus, dunkleosteus, this.largeBiteOffSet, 0.5, 1, 0.8, (ServerLevel)dunkleosteus.level(), (float) dunkleosteus.getAttribute(Attributes.ATTACK_DAMAGE).getValue(), (dunkleosteus.damageSources().mobAttack(dunkleosteus)), 0.1F, false, true, true);
    }
}