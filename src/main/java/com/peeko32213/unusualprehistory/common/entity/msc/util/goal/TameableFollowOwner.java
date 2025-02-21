package com.peeko32213.unusualprehistory.common.entity.msc.util.goal;

import com.peeko32213.unusualprehistory.common.entity.msc.util.dino.TameableBaseDinosaurAnimalEntity;
import com.peeko32213.unusualprehistory.common.entity.msc.util.interfaces.CustomFollower;
import net.minecraft.world.entity.ai.goal.FollowOwnerGoal;

public class TameableFollowOwner extends FollowOwnerGoal {

    private CustomFollower follower;

    public TameableFollowOwner(TameableBaseDinosaurAnimalEntity tameable, double speed, float minDist, float maxDist, boolean teleportToLeaves) {
        super(tameable, speed, minDist, maxDist, teleportToLeaves);
        this.follower = (CustomFollower)tameable;
    }



    public boolean canUse(){

        return super.canUse() && follower.shouldFollow();
    }

}
