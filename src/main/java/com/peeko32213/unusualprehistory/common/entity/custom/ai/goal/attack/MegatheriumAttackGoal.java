package com.peeko32213.unusualprehistory.common.entity.custom.ai.goal.attack;

import com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.MegatheriumEntity;
import com.peeko32213.unusualprehistory.common.entity.util.helper.HitboxAttacks;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class MegatheriumAttackGoal extends Goal {

    protected final MegatheriumEntity megatherium;
    private int animTime = 0;

    Vec3 slashOffSet = new Vec3(0, 1.5, 2.2);

    public MegatheriumAttackGoal(MegatheriumEntity pMob) {
        this.megatherium = pMob;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    public boolean canUse() {
        return megatherium.getTarget() != null && megatherium.getTarget().isAlive();
    }

    public void start() {
        megatherium.setRunning(!megatherium.isVehicle());
        megatherium.setAnimationState(0);
        this.animTime = 0;
    }

    public void stop() {
        megatherium.setRunning(false);
        megatherium.setAnimationState(0);
    }

    public void tick() {
        LivingEntity target = megatherium.getTarget();
        if (target != null) {
            megatherium.lookAt(EntityAnchorArgument.Anchor.EYES, target.getEyePosition());
            double distance = megatherium.distanceToSqr(target.getX(), target.getY(), target.getZ());
            int animState = megatherium.getAnimationState();

            switch (animState) {
                case 21, 22 -> tickSlashAttack();
                default -> {
                    this.megatherium.getLookControl().setLookAt(target, 30.0F, 30.0F);
                    this.megatherium.getNavigation().moveTo(target, 1.0D);
                    this.checkForCloseRangeAttack(distance);
                }
            }
        }
    }

    protected void checkForCloseRangeAttack (double distance){
        int meleeRange = 20;
        int r = (megatherium.getRandom().nextInt(100) + 1);
        if (distance <= meleeRange) {
            if (r <= 50) {
                megatherium.setAnimationState(21);
            }
            else {
                megatherium.setAnimationState(22);
            }
        }
    }

    protected void tickSlashAttack () {
        animTime++;
        megatherium.getNavigation().stop();
        if(animTime==16) {
            HitboxAttacks.pivotedPolyHitCheck(megatherium, megatherium, this.slashOffSet, 1.4, 0.75, 1.4, (ServerLevel) megatherium.level(), (float) megatherium.getAttribute(Attributes.ATTACK_DAMAGE).getValue(), (megatherium.damageSources().mobAttack(megatherium)), 0.3F, true, true, false);
        }
        if(animTime>=24) {
            animTime=0;
            this.megatherium.setAnimationState(0);
        }
    }
}