package com.peeko32213.unusualprehistory.common.entity.custom.ai.goal;

import com.peeko32213.unusualprehistory.common.entity.util.helper.MathHelpers;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

public class CustomizableRandomSwimGoal extends RandomStrollGoal {

    PathfinderMob entity;
    Vec3 wantedPos;

    int radius;
    int height;
    int prox;

    public CustomizableRandomSwimGoal(PathfinderMob entity, double spdmultiplier, int interval, int radius, int height, int proximity) {
        super(entity, spdmultiplier, interval);
        this.entity = entity;
        this.radius = radius;
        this.height = height;
        this.prox = proximity;
    }

    @Override
    public boolean canUse() {
        return super.canUse();
    }

    @Override
    public boolean canContinueToUse() {
        wantedPos = new Vec3(this.wantedX, this.wantedY, this.wantedZ);
        return super.canContinueToUse() && !(this.wantedPos.distanceTo(this.entity.position()) <= this.entity.getBbWidth() * prox);
    }

    @Nullable
    protected Vec3 getPosition() {
        return MathHelpers.getRandomSwimmablePosThatIsntTheSameDepth(this.mob, radius, height);
    }
}