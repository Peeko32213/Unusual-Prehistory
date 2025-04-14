package com.peeko32213.unusualprehistory.common.entity.custom.ai.goal;

import com.peeko32213.unusualprehistory.common.entity.util.goals.GoalUtils;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

public class GroundseekingRandomSwimGoal extends RandomStrollGoal {

    PathfinderMob entity;
    Vec3 wantedPos;

    int radius;
    int height;
    double prox;

    public GroundseekingRandomSwimGoal(PathfinderMob entity, double spdmultiplier, int interval, int SearchRadius, int SearchHeight, double proximity) {
        super(entity, spdmultiplier, interval);
        this.entity = entity;
        this.radius = SearchRadius;
        this.height = SearchHeight;
        this.prox = proximity;
        // radius = search width
        // height = search height
        // prox = how close the entity gets before cancelling the goal
    }

    @Override
    public boolean canUse() {
        boolean canUse =super.canUse() && entity.isInWater();
        return canUse;
    }

    @Override
    public boolean canContinueToUse() {
        wantedPos = new Vec3(this.wantedX, this.wantedY, this.wantedZ);
        return super.canContinueToUse() && entity.isInWater() && !(this.wantedPos.distanceTo(this.entity.position()) <= this.entity.getBbWidth() * prox);
        // second part cancels the goal if the animal gets close enough to the target but doesn't touch it
        // the distance is a multiplier of the animal's hitbox width, so if prox = 1: the animal will stop one body width ahead of its destination, prox = 2: two body width, etc
    }

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
        Vec3 goalpos = GoalUtils.getRandomSwimmablePosWithSeabed(this.mob, radius, height);
        return goalpos;
    }
}
