package com.peeko32213.unusualprehistory.common.entity.msc.util.goal.boids;

import com.peeko32213.unusualprehistory.common.entity.msc.util.dino.BoidFishEntity;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.world.entity.ai.goal.Goal;

public class LimitSpeedAndLookInVelocityDirectionGoal extends Goal {
    private final BoidFishEntity mob;
    private final float minSpeed;
    private final float maxSpeed;

    public LimitSpeedAndLookInVelocityDirectionGoal(BoidFishEntity mob, float minSpeed, float maxSpeed) {
        this.mob = mob;
        this.minSpeed = minSpeed;
        this.maxSpeed = maxSpeed;
    }

    @Override
    public boolean canUse() {
        return  mob.isInWaterOrBubble() && (mob.isFollower() || mob.hasFollowers());
    }

    @Override
    public void tick() {
        var velocity = mob.getDeltaMovement();
        var speed = velocity.length();

        if (speed < minSpeed)
            velocity = velocity.normalize().scale(minSpeed);
        if (speed > maxSpeed)
            velocity = velocity.normalize().scale(maxSpeed);

        mob.setDeltaMovement(velocity);
        mob.lookAt(EntityAnchorArgument.Anchor.EYES, mob.position().add(velocity.scale(3)));
    }
}
