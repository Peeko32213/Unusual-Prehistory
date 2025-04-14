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
        return super.canUse() && entity.isInWater();
    }

    @Override
    public boolean canContinueToUse() {
        wantedPos = new Vec3(this.wantedX, this.wantedY, this.wantedZ);
        return super.canContinueToUse() && entity.isInWater() && !(this.wantedPos.distanceTo(this.entity.position()) <= this.entity.getBbWidth() * prox);
        //second part cancels the goal if the animal gets close enough
    }

    public void tick() {}

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void stop() {
        super.stop();
    }

    @Nullable
    protected Vec3 getPosition() {
        return MathHelpers.getRandomSwimmablePosThatIsntTheSameDepth(this.mob, radius, height);
    }
}