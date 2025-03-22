package com.peeko32213.unusualprehistory.common.entity.animation.state;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.peeko32213.unusualprehistory.core.registry.EntityActionsRegistry;
import net.minecraft.world.entity.LivingEntity;

import java.util.function.Consumer;

public class EntityAction {
    public static final Codec<EntityAction> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("time_to_perform_action").forGetter(EntityAction::getTimeToPerformAction),
            Codec.INT.fieldOf("times_to_perform").forGetter(EntityAction::getTimesToPerform),
            EntityActionsRegistry.CODEC.fieldOf("action_id").forGetter(EntityAction::getAction)
    ).apply(instance, EntityAction::new));

    private final int timeToPerformAction;
    private final int timesToPerform;
    private final Consumer<LivingEntity> actionId;



    public EntityAction(int timeToPerformAction, Consumer<LivingEntity> consumer, int timesToPerform) {
        this.timeToPerformAction = timeToPerformAction;
        this.timesToPerform = timesToPerform;
        this.actionId = consumer;
    }

    public EntityAction(int timeToPerformAction, int timesToPerform, Consumer<LivingEntity> actionId) {
        this.timeToPerformAction = timeToPerformAction;
        this.timesToPerform = timesToPerform;
        this.actionId = actionId;
    }

    public int getTimeToPerformAction() {
        return timeToPerformAction;
    }

    public int getTimesToPerform() {
        return timesToPerform;
    }

    public Consumer<LivingEntity> getAction() {
        return actionId;
    }

    public static EntityAction getDefaultInstance() {
        return new EntityAction(0,1, EntityActionsRegistry.DEFAULT);
    }
}
