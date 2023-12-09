package com.peeko32213.unusualprehistory.common.entity.msc.util.goal;

import com.peeko32213.unusualprehistory.common.entity.msc.util.interfaces.PackHunter;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.List;

public class JoinPackGoal extends Goal {
    public final LivingEntity entity;
    public final PackHunter packAnimal;
    private int distCheckCounter;

    private int rate;
    private int packSize;

    public JoinPackGoal(LivingEntity animal, int rate, int packSize) {
        this.entity = animal;
        this.packAnimal = (PackHunter) animal;
        this.rate = rate;
        this.packSize = packSize;
    }

    public boolean canUse() {
        long worldTime = entity.level().getGameTime() % 10;
        if (worldTime != 0 && entity.getRandom().nextInt(reducedTickDelay(rate)) != 0) {
            return false;
        }
        if (!this.packAnimal.isPackFollower() && !this.packAnimal.hasPackFollower()) {
            double dist = 30D;
            List<? extends LivingEntity> list = entity.level().getEntitiesOfClass(entity.getClass(), entity.getBoundingBox().inflate(dist, dist, dist));
            LivingEntity closestTail = null;
            double d0 = Double.MAX_VALUE;
            for (LivingEntity animal : list) {
                if (!((PackHunter) animal).hasPackFollower() && ((PackHunter) animal).isValidLeader(((PackHunter) animal).getPackLeader()) && !animal.getUUID().equals(entity.getUUID()) && !((PackHunter) animal).isInPack(packAnimal) && ((PackHunter) animal).getPackSize() < packSize) {
                    double d1 = this.entity.distanceToSqr(animal);
                    if (!(d1 > d0)) {
                        d0 = d1;
                        closestTail = animal;
                    }
                }
            }
            if (closestTail == null) {
                return false;
            } else if (d0 < 1.0D) {
                return false;
            } else if (!packAnimal.isValidLeader(((PackHunter) closestTail).getPackLeader())) {
                return false;
            } else {
                this.packAnimal.joinPackOf((PackHunter) closestTail);
                return true;
            }
        } else {
            return false;
        }
    }

    public boolean canContinueToUse() {
        if (this.packAnimal.isPackFollower() && packAnimal.isValidLeader(packAnimal.getPackLeader())) {
            double d0 = this.entity.distanceToSqr((LivingEntity) this.packAnimal.getPriorPackMember());
            if (d0 > 676.0D) {
                if (this.distCheckCounter == 0) {
                    return false;
                }
            }
            if (this.distCheckCounter > 0) {
                --this.distCheckCounter;
            }

            return true;
        } else {
            return false;
        }
    }

    public void stop() {
        this.packAnimal.leavePack();
    }

    public void tick() {
    }
}
