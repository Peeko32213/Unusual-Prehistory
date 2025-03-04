package com.peeko32213.unusualprehistory.common.entity.util.goal;

import com.peeko32213.unusualprehistory.common.entity.base.TamablePrehistoricEntity;
import com.peeko32213.unusualprehistory.common.entity.util.interfaces.ICustomFollower;
import net.minecraft.world.entity.ai.goal.FollowOwnerGoal;

public class TameableFollowOwner extends FollowOwnerGoal {

    private ICustomFollower follower;

    public TameableFollowOwner(TamablePrehistoricEntity tameable, double speed, float minDist, float maxDist, boolean teleportToLeaves) {
        super(tameable, speed, minDist, maxDist, teleportToLeaves);
        this.follower = (ICustomFollower)tameable;
    }

    public boolean canUse(){

        return super.canUse() && follower.shouldFollow();
    }

}
