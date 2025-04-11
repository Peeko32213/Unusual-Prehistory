package com.peeko32213.unusualprehistory.common.entity.custom.ai.goal.attack;

import com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.HwachavenatorEntity;
import com.peeko32213.unusualprehistory.common.entity.util.helper.HitboxAttacks;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class HwachavenatorAttackGoal extends Goal {

    protected final HwachavenatorEntity hwachavenator;
    private int animTime = 0;

    Vec3 slashOffSet = new Vec3(0, 1.5, 2.2);

    public HwachavenatorAttackGoal(HwachavenatorEntity pMob) {
        this.hwachavenator = pMob;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    public boolean canUse() {
        return hwachavenator.getTarget() != null && hwachavenator.getTarget().isAlive();
    }

    public void start() {
        hwachavenator.setRunning(!hwachavenator.isVehicle());
        hwachavenator.setAnimationState(0);
        this.animTime = 0;
    }

    public void stop() {
        hwachavenator.setRunning(false);
        hwachavenator.setAnimationState(0);
    }

    public void tick() {
        LivingEntity target = hwachavenator.getTarget();
        if (target != null) {
            hwachavenator.lookAt(EntityAnchorArgument.Anchor.EYES, target.getEyePosition());
            double distance = hwachavenator.distanceToSqr(target.getX(), target.getY(), target.getZ());
            int animState = hwachavenator.getAnimationState();

            switch (animState) {
                case 21, 22 -> {
                    tickBiteAttack();
                    this.hwachavenator.getNavigation().moveTo(target, 1.0D);
                }
                default -> {
                    this.hwachavenator.getLookControl().setLookAt(target, 30.0F, 30.0F);
                    this.hwachavenator.getNavigation().moveTo(target, 1.0D);
                    this.checkForCloseRangeAttack(distance);
                }
            }
        }
    }

    protected void checkForCloseRangeAttack (double distance){
        int meleeRange = 19;
        int r = (hwachavenator.getRandom().nextInt(100) + 1);
        if (distance <= meleeRange) {
            if (r <= 50) {
                hwachavenator.setAnimationState(21);
            }
            else {
                hwachavenator.setAnimationState(22);
            }
        }
    }

    protected void tickBiteAttack () {
        animTime++;
        hwachavenator.getNavigation().stop();
        if(animTime == 8) {
            HitboxAttacks.pivotedPolyHitCheck(hwachavenator, hwachavenator, this.slashOffSet, 0.5, 0.6, 0.5, (ServerLevel) hwachavenator.level(), (float) hwachavenator.getAttribute(Attributes.ATTACK_DAMAGE).getValue(), (hwachavenator.damageSources().mobAttack(hwachavenator)), 0.2F, false, true, false);
        }
        if(animTime >= 12) {
            animTime = 0;
            this.hwachavenator.setAnimationState(0);
        }
    }
}
