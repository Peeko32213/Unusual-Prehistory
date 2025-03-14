package com.peeko32213.unusualprehistory.common.data.entity.goal;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.peeko32213.unusualprehistory.common.entity.util.goal.JarateFindWaterGoal;
import com.peeko32213.unusualprehistory.core.registry.UPGoalRegistry;
import com.scouter.goalsmith.data.GoalCodec;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;

public class JarateFindWaterGoalCodec implements GoalCodec {


    public static final Codec<JarateFindWaterGoalCodec> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("goal_priority").forGetter(JarateFindWaterGoalCodec::getGoalPriority)
            ).apply(instance, JarateFindWaterGoalCodec::new));
    private final int goalPriority;


    public JarateFindWaterGoalCodec(int goalPriority) {
        this.goalPriority = goalPriority;

    }

    public int getGoalPriority() {
        return goalPriority;
    }


    @Override
    public Goal addGoal(PathfinderMob pathfinderMob) {
        Goal goal = new JarateFindWaterGoal(pathfinderMob);
        pathfinderMob.goalSelector.addGoal(goalPriority, goal);
        return goal;
    }

    @Override
    public Codec<? extends GoalCodec> codec() {
        return UPGoalRegistry.JARATE_FIND_WATER_GOAL_CODEC.get();
    }
}
