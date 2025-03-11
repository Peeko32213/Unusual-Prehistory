package com.peeko32213.unusualprehistory.common.data.entity.goal;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.peeko32213.unusualprehistory.common.data.attack.EntityAttack;
import com.peeko32213.unusualprehistory.common.data.attack.NoneAttack;
import com.peeko32213.unusualprehistory.core.registry.EntityActionsRegistry;
import net.minecraft.world.entity.LivingEntity;

import java.util.function.Consumer;

public class MeleeEntityAction {
    public static final Codec<MeleeEntityAction> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("time_to_perform_attack").forGetter(MeleeEntityAction::getTimeToPerformAction),
            Codec.INT.fieldOf("times_to_perform").forGetter(MeleeEntityAction::getTimesToPerform),
            EntityAttack.DIRECT_CODEC.fieldOf("entity_attack").forGetter(MeleeEntityAction::getAttack)
    ).apply(instance, MeleeEntityAction::new));

    private final int timeToPerformAction;
    private final int timesToPerform;
    private final EntityAttack actionId;



    public MeleeEntityAction(int timeToPerformAction, EntityAttack consumer, int timesToPerform) {
        this.timeToPerformAction = timeToPerformAction;
        this.timesToPerform = timesToPerform;
        this.actionId = consumer;
    }
    public MeleeEntityAction(int timeToPerformAction, EntityAttack actionId) {
        this.timeToPerformAction = timeToPerformAction;
        this.timesToPerform = 1;
        this.actionId = actionId;
    }

    public MeleeEntityAction(int timeToPerformAction, int timesToPerform, EntityAttack actionId) {
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

    public EntityAttack getAttack() {
        return actionId;
    }

    public static MeleeEntityAction getDefaultInstance() {
        return new MeleeEntityAction(0,1, new NoneAttack());
    }
}
