package com.peeko32213.unusualprehistory.common.entity.msc.util;

import com.peeko32213.unusualprehistory.common.entity.EntityMammoth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;

import java.util.EnumSet;

public class LeaderHurtTargetGoal extends TargetGoal {

    private final EntityMammoth mammoth;
    private LivingEntity ownerLastHurt;
    private int timestamp;


    public LeaderHurtTargetGoal(EntityMammoth pMob) {
        super(pMob, false);
        this.mammoth = pMob;
        this.setFlags(EnumSet.of(Goal.Flag.TARGET));
    }

    @Override
    public boolean canUse() {
        if (this.mammoth.hasLeader() && this.mammoth.inRangeOfLeader()) {
            LivingEntity livingentity = this.mammoth.getLeader();
            if (livingentity == null) {
                return false;
            } else {
                this.ownerLastHurt = livingentity.getLastHurtMob();
                int i = livingentity.getLastHurtMobTimestamp();
                return i != this.timestamp && this.canAttack(this.ownerLastHurt, TargetingConditions.DEFAULT) && this.mammoth.wantsToAttack(this.ownerLastHurt, livingentity);
            }
        } else {
            return false;
        }
    }

    public void start() {
        this.mob.setTarget(this.ownerLastHurt);
        LivingEntity livingentity = this.mammoth.getLeader();
        if (livingentity != null) {
            this.timestamp = livingentity.getLastHurtMobTimestamp();
        }

        super.start();
    }
}
