package com.peeko32213.unusualprehistory.common.data.entity;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.scouter.goalsmith.codec.NullableFieldCodec;
import com.scouter.goalsmith.data.*;

import java.util.Collections;
import java.util.List;

public class EntityGoals {
    public static final Codec<EntityGoals> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    NullableFieldCodec.makeDefaultableField("goal_operations", GoalOperation.DIRECT_CODEC.listOf(), Collections.emptyList()).forGetter(EntityGoals::getGoalOperation),
                    NullableFieldCodec.makeDefaultableField("target_goal_operations", TargetGoalOperation.DIRECT_CODEC.listOf(), Collections.emptyList()).forGetter(EntityGoals::getTargetGoalOperation)
            ).apply(instance, EntityGoals::new)
    );
    

    private final List<GoalOperation> goalOperation;
    private final List<TargetGoalOperation> targetGoalOperation;

    public EntityGoals(List<GoalOperation> goalOperation, List<TargetGoalOperation> targetGoalOperation) {
        this.goalOperation = goalOperation;
        this.targetGoalOperation = targetGoalOperation;
    }

    public List<GoalOperation> getGoalOperation() {
        return goalOperation;
    }

    public List<TargetGoalOperation> getTargetGoalOperation() {
        return targetGoalOperation;
    }
}
