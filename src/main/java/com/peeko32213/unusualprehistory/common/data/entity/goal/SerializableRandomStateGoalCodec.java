package com.peeko32213.unusualprehistory.common.data.entity.goal;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.peeko32213.unusualprehistory.common.entity.animation.state.IStateAction;
import com.peeko32213.unusualprehistory.common.entity.animation.state.SerializableRandomStateGoal;
import com.peeko32213.unusualprehistory.common.entity.animation.state.WeightedSerializableStateHelper;
import com.peeko32213.unusualprehistory.core.registry.entities.UPGoalRegistry;
import com.scouter.goalsmith.data.GoalCodec;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import org.slf4j.Logger;

public class SerializableRandomStateGoalCodec implements GoalCodec {
    private static final Logger LOGGER = LogUtils.getLogger();

    public static final Codec<SerializableRandomStateGoalCodec> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("goal_priority").forGetter(SerializableRandomStateGoalCodec::getGoalPriority),
            WeightedRandomList.codec(WeightedSerializableStateHelper.CODEC).fieldOf("state_helpers").forGetter(SerializableRandomStateGoalCodec::getSerializableStateHelperWeightedRandomList)
    ).apply(instance, SerializableRandomStateGoalCodec::new));
    private final int goalPriority;
    private final WeightedRandomList<WeightedSerializableStateHelper> serializableStateHelperWeightedRandomList;

    public SerializableRandomStateGoalCodec(int goalPriority, WeightedRandomList<WeightedSerializableStateHelper> serializableStateHelperWeightedRandomList) {
        this.goalPriority = goalPriority;
        this.serializableStateHelperWeightedRandomList = serializableStateHelperWeightedRandomList;

    }

    @Override
    public Goal addGoal(PathfinderMob mob) {
        if( mob instanceof IStateAction) {
            SerializableRandomStateGoal goal = new SerializableRandomStateGoal(mob, serializableStateHelperWeightedRandomList);
            mob.goalSelector.addGoal(goalPriority, goal);
            return goal;
        }
        LOGGER.error("Unsupported Operation, Tried adding SerializableRandomStateGoal to IStateAction Entity!");
        return null;
    }

    @Override
    public Codec<? extends GoalCodec> codec() {
        return UPGoalRegistry.RANDOM_STATE_GOAL_CODEC.get();
    }

    public int getGoalPriority() {
        return goalPriority;
    }

    public WeightedRandomList<WeightedSerializableStateHelper> getSerializableStateHelperWeightedRandomList() {
        return serializableStateHelperWeightedRandomList;
    }
}
