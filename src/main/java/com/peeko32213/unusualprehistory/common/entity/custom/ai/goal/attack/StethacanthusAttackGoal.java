package com.peeko32213.unusualprehistory.common.entity.custom.ai.goal.attack;

import com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.aquatic.StethacanthusEntity;
import com.peeko32213.unusualprehistory.common.entity.util.helper.HitboxAttacks;
import com.peeko32213.unusualprehistory.core.other.tags.UPEntityTypeTags;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class StethacanthusAttackGoal extends Goal {

    protected final StethacanthusEntity stethacanthus;
    private int animTime = 0;

    Vec3 biteOffSet = new Vec3(0, 0.3, 0.5);

    public StethacanthusAttackGoal(StethacanthusEntity pMob) {
        this.stethacanthus = pMob;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    public boolean canUse() {
        LivingEntity target = stethacanthus.getTarget();
        return target != null && target.isAlive() && target.isInWater() && !target.getType().is(UPEntityTypeTags.STETHA_AVOIDS) && !(target instanceof Player);
    }

    public void start() {
        stethacanthus.setAnimationState(0);
        this.animTime = 0;
    }

    public void stop() {
        stethacanthus.setAnimationState(0);
    }

    public void tick() {
        LivingEntity target = stethacanthus.getTarget();
        if (target != null && target.isInWater()) {
            stethacanthus.lookAt(stethacanthus.getTarget(), 30F, 30F);
            stethacanthus.getLookControl().setLookAt(stethacanthus.getTarget(), 30F, 30F);

            double distance = stethacanthus.distanceToSqr(target.getX(), target.getY(), target.getZ());
            int animState = stethacanthus.getAnimationState();

            if (animState == 21) {
                tickBiteAttack();
                this.stethacanthus.getNavigation().moveTo(target, 1.4D);
            } else {
                this.stethacanthus.getNavigation().moveTo(target, 1.4D);
                this.checkForCloseRangeAttack(distance);
            }
        }
    }

    protected void checkForCloseRangeAttack (double distance){
        if (distance <= 11) {
            stethacanthus.setAnimationState(21);
        }
    }

    protected void tickBiteAttack() {
        animTime++;
        stethacanthus.lookAt(stethacanthus.getTarget(), 30F, 30F);
        stethacanthus.getLookControl().setLookAt(stethacanthus.getTarget(), 30F, 30F);

        if(animTime==9) {
            HitboxAttacks.pivotedPolyHitCheck(stethacanthus, this.stethacanthus, this.biteOffSet, 0.04, 0.1, 0.04, (ServerLevel)this.stethacanthus.level(), (float) stethacanthus.getAttribute(Attributes.ATTACK_DAMAGE).getValue(), (this.stethacanthus.damageSources().mobAttack(stethacanthus)), 0.1F, false, true, false);
        }
        if(animTime>=15) {
            animTime=0;
            this.stethacanthus.setAnimationState(0);
        }
    }
}