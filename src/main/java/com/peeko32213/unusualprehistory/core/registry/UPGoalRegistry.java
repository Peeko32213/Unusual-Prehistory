package com.peeko32213.unusualprehistory.core.registry;

import com.mojang.serialization.Codec;
import com.peeko32213.unusualprehistory.core.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.data.entity.goal.RabiesHuntGoalCodec;
import com.peeko32213.unusualprehistory.common.data.entity.goal.SerializableRandomMeleeAttackGoalCodec;
import com.peeko32213.unusualprehistory.common.data.entity.goal.SerializableRandomStateGoalCodec;
import com.scouter.goalsmith.data.GSRegistries;
import com.scouter.goalsmith.data.GoalCodec;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class UPGoalRegistry {
    public static final DeferredRegister<Codec<? extends GoalCodec>> GOAL_TYPE_SERIALIZER = DeferredRegister.create(GSRegistries.Keys.GOAL_TYPE_SERIALIZERS, UnusualPrehistory.MODID);
    public static final RegistryObject<Codec<SerializableRandomStateGoalCodec>> RANDOM_STATE_GOAL_CODEC = GOAL_TYPE_SERIALIZER.register("random_state_goal", () -> SerializableRandomStateGoalCodec.CODEC);
    public static final RegistryObject<Codec<SerializableRandomMeleeAttackGoalCodec>> RANDOM_MELEE_ATTACK_GOAL_CODEC = GOAL_TYPE_SERIALIZER.register("random_melee_attack_goal", () -> SerializableRandomMeleeAttackGoalCodec.CODEC);
    public static final RegistryObject<Codec<RabiesHuntGoalCodec>> RABIES_HUNT_GOAL_CODEC = GOAL_TYPE_SERIALIZER.register("rabies_hunt_goal", () -> RabiesHuntGoalCodec.CODEC);


}
