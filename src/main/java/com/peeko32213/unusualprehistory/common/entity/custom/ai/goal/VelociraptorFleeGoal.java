package com.peeko32213.unusualprehistory.common.entity.custom.ai.goal;

import com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.VelociraptorEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class VelociraptorFleeGoal extends Goal {

    protected final VelociraptorEntity velociraptor;
    protected double posX;
    protected double posY;
    protected double posZ;

    public VelociraptorFleeGoal(VelociraptorEntity velociraptor) {
        this.velociraptor = velociraptor;
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    public boolean canUse() {
        if (!this.shouldPanic()) {
            return false;
        } else {
            return this.findRandomPosition();
        }
    }

    protected boolean shouldPanic() {
        return velociraptor.getLastHurtByMob() != null && velociraptor.getHealth() <= velociraptor.getMaxHealth() * 0.25F;
    }

    protected boolean findRandomPosition() {
        Vec3 vec3 = LandRandomPos.getPos(velociraptor, 24, 8);
        if (vec3 == null) {
            return false;
        } else {
            this.posX = vec3.x;
            this.posY = vec3.y;
            this.posZ = vec3.z;
            return true;
        }
    }

    public void start() {
        velociraptor.getNavigation().moveTo(this.posX, this.posY, this.posZ, 1.0F);
        velociraptor.setRunning(true);
    }

    public void stop() {
        velociraptor.setRunning(false);
    }

    public boolean canContinueToUse() {
        return !velociraptor.getNavigation().isDone();
    }
}

