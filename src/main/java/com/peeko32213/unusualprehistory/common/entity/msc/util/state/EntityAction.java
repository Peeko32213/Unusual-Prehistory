package com.peeko32213.unusualprehistory.common.entity.msc.util.state;

import net.minecraft.world.entity.LivingEntity;

import java.util.function.Consumer;

public class EntityAction {
    private final int timeToPerformAction;
    private final int timesToPerform;
    private final Consumer<LivingEntity> action;

    public EntityAction(int timeToPerformAction, Consumer<LivingEntity> action, int timesToPerform) {
        this.timeToPerformAction = timeToPerformAction;
        this.action = action;
        this.timesToPerform = timesToPerform;
    }

    public int getTimeToPerformAction() {
        return timeToPerformAction;
    }

    public int getTimesToPerform() {
        return timesToPerform;
    }

    public Consumer<LivingEntity> getAction() {
        return action;
    }
}