package com.peeko32213.unusualprehistory.common.entity.util.goal;

import com.peeko32213.unusualprehistory.common.entity.SmilodonEntity;
import com.peeko32213.unusualprehistory.core.registry.UPItems;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.List;

public class GroomGoal extends Goal {

    private static final TargetingConditions PARTNER_TARGETING = TargetingConditions.forNonCombat().range(8.0D).ignoreLineOfSight();
    protected final SmilodonEntity smilodon;
    private final Class<? extends SmilodonEntity> partnerClass;
    protected final Level level;
    @Nullable
    protected SmilodonEntity partner;
    private int groomTime;
    private final double speedModifier;
    private boolean groom = false;
    private boolean groom2 = false;

    public GroomGoal(SmilodonEntity pAnimal, double pSpeedModifier) {
        this(pAnimal, pSpeedModifier, pAnimal.getClass());
    }

    public GroomGoal(SmilodonEntity pAnimal, double pSpeedModifier, Class<? extends SmilodonEntity> pPartnerClass) {
        this.smilodon = pAnimal;
        this.level = pAnimal.level();
        this.partnerClass = pPartnerClass;
        this.speedModifier = pSpeedModifier;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    /**
     * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
     * method as well.
     */
    public boolean canUse() {
        if (!this.smilodon.canGroom() || this.smilodon.getTarget() != null) {
            return false;
        } else {
            this.partner = this.getFreePartner();
            return this.partner != null && this.partner.getTarget() == null;
        }
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean canContinueToUse() {
        return this.partner.isAlive() && this.groomTime < 80;
    }

    /**
     * Reset the task's internal state. Called when this task is interrupted by another one
     */
    public void stop() {
        this.partner.setGroom1(false);
        this.partner.setGroom2(false);
        this.smilodon.setGroom1(false);
        this.smilodon.setGroom2(false);
        this.partner.setCanGroom(false);
        this.smilodon.setCanGroom(false);
        this.smilodon.groomTimer = 6000;
        this.partner.groomTimer = 6000;
        this.smilodon.spawnAtLocation(UPItems.SMILO_FUR.get());
        this.partner = null;
        this.groomTime = 0;
    }

    @Override
    public void start() {
        groom = this.level.random.nextBoolean();
        groom2 = this.level.random.nextBoolean();
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    public void tick() {
        this.groomTime = groomTime + 1;
        double d0 = this.smilodon.distanceToSqr(this.partner);
        if (this.groomTime >= 2 && d0 < 3.5D) {
            this.smilodon.getLookControl().setLookAt(this.partner, 10.0F, (float) this.smilodon.getMaxHeadXRot());
            this.partner.getLookControl().setLookAt(this.smilodon, 10.0F, (float) this.partner.getMaxHeadXRot());
            this.smilodon.setGroom1(true);
        } else {
            this.smilodon.getLookControl().setLookAt(this.partner, 10.0F, (float) this.smilodon.getMaxHeadXRot());
            this.partner.getLookControl().setLookAt(this.smilodon, 10.0F, (float) this.partner.getMaxHeadXRot());
            this.smilodon.getNavigation().moveTo(this.partner, this.speedModifier);
        }

    }

    /**
     * Loops through nearby smilos and finds another smilo of the same type that can be groomed with. Returns the first
     * valid groomer found.
     */
    @Nullable
    private SmilodonEntity getFreePartner() {
        List<? extends SmilodonEntity> list = this.level.getNearbyEntities(this.partnerClass, PARTNER_TARGETING, this.smilodon, this.smilodon.getBoundingBox().inflate(8.0D));
        double d0 = 13D;
        SmilodonEntity animal = null;

        for (SmilodonEntity animal1 : list) {
            double d1 = this.smilodon.distanceToSqr(animal1);
            if (this.smilodon.canGroom(animal1) && d1 < d0) {
                animal = animal1;
                d0 = this.smilodon.distanceToSqr(animal1);
            }
        }

        return animal;
    }
}
