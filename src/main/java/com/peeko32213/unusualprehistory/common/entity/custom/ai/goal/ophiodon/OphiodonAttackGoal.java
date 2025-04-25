package com.peeko32213.unusualprehistory.common.entity.custom.ai.goal.ophiodon;

import com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.aquatic.OphiodonEntity;
import com.peeko32213.unusualprehistory.common.entity.util.helper.HitboxAttacks;
import com.peeko32213.unusualprehistory.core.other.tags.UPEntityTypeTags;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class OphiodonAttackGoal extends Goal {

    protected final OphiodonEntity ophiodon;
    private int animTime = 0;

    Vec3 biteOffSet = new Vec3(0, 0.3, 1.2);

    public OphiodonAttackGoal(OphiodonEntity pMob) {
        this.ophiodon = pMob;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    public boolean canUse() {
        LivingEntity target = ophiodon.getTarget();
        return target != null && target.isAlive() && target.isInWater() && !target.getType().is(UPEntityTypeTags.STETHA_AVOIDS) && !(target instanceof Player);
    }

    public void start() {
        ophiodon.setAnimationState(0);
        this.animTime = 0;
    }

    public void stop() {
        ophiodon.setAnimationState(0);
    }

    public void tick() {
        LivingEntity target = ophiodon.getTarget();
        if (target != null && target.isInWater()) {
            ophiodon.lookAt(ophiodon.getTarget(), 30F, 30F);
            ophiodon.getLookControl().setLookAt(ophiodon.getTarget(), 30F, 30F);

            double distance = ophiodon.distanceToSqr(target.getX(), target.getY(), target.getZ());
            int animState = ophiodon.getAnimationState();

            if (animState == 21) {
                tickBiteAttack();
                this.ophiodon.getNavigation().moveTo(target, 0.75D);
            } else {
                this.ophiodon.getNavigation().moveTo(target, 1.4D);
                this.checkForCloseRangeAttack(distance);
            }
        }
    }

    protected void checkForCloseRangeAttack (double distance){
        if (distance <= 6) {
            ophiodon.setAnimationState(21);
        }
    }

    protected void tickBiteAttack() {
        animTime++;
        ophiodon.lookAt(ophiodon.getTarget(), 30F, 30F);
        ophiodon.getLookControl().setLookAt(ophiodon.getTarget(), 30F, 30F);

        if(animTime == 5) {
            HitboxAttacks.pivotedPolyHitCheck(ophiodon, this.ophiodon, this.biteOffSet, 0.05, 0.125, 0.05, (ServerLevel)this.ophiodon.level(), (float) ophiodon.getAttribute(Attributes.ATTACK_DAMAGE).getValue(), (this.ophiodon.damageSources().mobAttack(ophiodon)), 0.1F, false, true, false);
        }
        if(animTime >= 15) {
            animTime=0;
            this.ophiodon.setAnimationState(0);
        }
    }
}