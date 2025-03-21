package com.peeko32213.unusualprehistory.common.data.entity.goal;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.peeko32213.unusualprehistory.core.registry.entities.UPGoalRegistry;
import com.scouter.goalsmith.data.GoalCodec;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import org.slf4j.Logger;

public class SerializableRandomMeleeAttackGoalCodec implements GoalCodec {
    private static final Logger LOGGER = LogUtils.getLogger();

    public static final Codec<SerializableRandomMeleeAttackGoalCodec> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("goal_priority").forGetter(SerializableRandomMeleeAttackGoalCodec::getGoalPriority),
            WeightedRandomList.codec(WeightedSerializableMeleeAttackHelper.CODEC).fieldOf("state_helpers").forGetter(SerializableRandomMeleeAttackGoalCodec::getSerializableStateHelperWeightedRandomList),
            Codec.DOUBLE.fieldOf("speed_modifier").forGetter(SerializableRandomMeleeAttackGoalCodec::getSpeedModifier),
            Codec.BOOL.fieldOf("following_target_even_if_not_seen").forGetter(SerializableRandomMeleeAttackGoalCodec::isFollowingTargetEvenIfNotSeen),
            Codec.DOUBLE.fieldOf("attack_sqr_reach_bb_width_modifier").forGetter(SerializableRandomMeleeAttackGoalCodec::getAttackSqrReachBbWidthModifier)
    ).apply(instance, SerializableRandomMeleeAttackGoalCodec::new));
    private final int goalPriority;
    private final WeightedRandomList<WeightedSerializableMeleeAttackHelper> serializableStateHelperWeightedRandomList;
    private final double speedModifier;
    private final boolean followingTargetEvenIfNotSeen;
    private final double attackSqrReachBbWidthModifier;

    public SerializableRandomMeleeAttackGoalCodec(int goalPriority, WeightedRandomList<WeightedSerializableMeleeAttackHelper> serializableStateHelperWeightedRandomList, double speedModifier, boolean followingTargetEvenIfNotSeen, double attackSqrReachBbWidthModifier) {
        this.goalPriority = goalPriority;
        this.serializableStateHelperWeightedRandomList = serializableStateHelperWeightedRandomList;
        this.speedModifier = speedModifier;
        this.followingTargetEvenIfNotSeen = followingTargetEvenIfNotSeen;
        this.attackSqrReachBbWidthModifier = attackSqrReachBbWidthModifier;

    }

    @Override
    public Goal addGoal(PathfinderMob mob) {
        SerializableRandomMeleeAttackGoal goal = new SerializableRandomMeleeAttackGoal(mob, serializableStateHelperWeightedRandomList, speedModifier, followingTargetEvenIfNotSeen, attackSqrReachBbWidthModifier);
        mob.goalSelector.addGoal(goalPriority, goal);
        return goal;
    }

    @Override
    public Codec<? extends GoalCodec> codec() {
        return UPGoalRegistry.RANDOM_MELEE_ATTACK_GOAL_CODEC.get();
    }

    public int getGoalPriority() {
        return goalPriority;
    }

    public WeightedRandomList<WeightedSerializableMeleeAttackHelper> getSerializableStateHelperWeightedRandomList() {
        return serializableStateHelperWeightedRandomList;
    }

    public double getAttackSqrReachBbWidthModifier() {
        return attackSqrReachBbWidthModifier;
    }

    public double getSpeedModifier() {
        return speedModifier;
    }

    public boolean isFollowingTargetEvenIfNotSeen() {
        return followingTargetEvenIfNotSeen;
    }
}
