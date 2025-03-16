package com.peeko32213.unusualprehistory.common.data.entity.goal;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.peeko32213.unusualprehistory.common.entity.util.goal.RabiesHuntGoal;
import com.peeko32213.unusualprehistory.core.registry.UPGoalRegistry;
import com.scouter.goalsmith.data.GoalCodec;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;

public class RabiesHuntGoalCodec implements GoalCodec {
    public static final Codec<RabiesHuntGoalCodec> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("goal_priority").forGetter(RabiesHuntGoalCodec::getGoalPriority)
    ).apply(instance, RabiesHuntGoalCodec::new));
    private final int goalPriority;


    public RabiesHuntGoalCodec(int goalPriority) {
        this.goalPriority = goalPriority;

    }

    public int getGoalPriority() {
        return goalPriority;
    }

    @Override
    public Goal addGoal(PathfinderMob pathfinderMob) {
        Goal goal = new RabiesHuntGoal(pathfinderMob);
        pathfinderMob.goalSelector.addGoal(goalPriority, goal);
        return goal;
    }

    @Override
    public Codec<? extends GoalCodec> codec() {
        return UPGoalRegistry.RABIES_HUNT_GOAL_CODEC.get();
    }
}
