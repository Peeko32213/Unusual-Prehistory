package com.peeko32213.unusualprehistory.common.entity.msc.util;

import com.peeko32213.unusualprehistory.common.entity.msc.util.dino.EntityBaseDinosaurAnimal;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class PounceGoal extends Goal {

    protected EntityBaseDinosaurAnimal entity;
    protected LivingEntity target;
    protected int ticks;
    private int chargeTime;
    protected int timer;
    public PounceGoal(EntityBaseDinosaurAnimal entity, int chargeTime) {
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        this.entity = entity;
        this.chargeTime = chargeTime;
    }

    @Override
    public boolean canUse() {
        LivingEntity target = this.entity.getTarget();
        if (target == null)
            return false;
        return this.entity.hasLineOfSight(target) && this.entity.getY() >= this.entity.getTarget().getY() && !(timer-- >0);
    }

    @Override
    public void start() {
        this.target = this.entity.getTarget();
        this.ticks = 0;
    }

    @Override
    public boolean canContinueToUse() {
        return this.ticks < this.chargeTime + 1 && this.entity.isOnGround() && this.target.isAlive() && !(timer >0);
    }

    @Override
    public void tick() {
        super.tick();
        this.entity.getMoveControl().setWantedPosition(this.target.getX(), this.target.getY(), this.target.getZ(), 0.01D);
        this.entity.getLookControl().setLookAt(this.target);
        if(this.entity.distanceToSqr(this.target) < 100 && this.ticks == this.chargeTime){
            this.pounce();
        }
        this.ticks++;
    }

    public void pounce(){

        Vec3 diff = new Vec3(this.target.getX() - this.entity.getX(), (this.target.getY() - this.entity.getY()) + 1, this.target.getZ() -this.entity.getZ());
        Vec3 vel = diff.multiply(0.5D,0.4D, 0.5D).add(0,0.3,0).normalize();
        this.entity.setDeltaMovement(vel);
        this.entity.getNavigation().stop();
        this.stop();
    }

    @Override
    public void stop() {
        this.timer = 1200;
        super.stop();
    }
}
