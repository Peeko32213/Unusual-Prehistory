package com.peeko32213.unusualprehistory.core.registry;

import com.mojang.serialization.Codec;
import com.peeko32213.unusualprehistory.core.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.data.predicate.*;
import com.scouter.goalsmith.data.GSRegistries;
import com.scouter.goalsmith.data.PredicateCodec;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class UPSpawnPredicateRegistry {
    public static final DeferredRegister<Codec<? extends PredicateCodec<?>>> PREDICATE_SERIALIZER = DeferredRegister.create(GSRegistries.Keys.PREDICATE_TYPE_SERIALIZERS, UnusualPrehistory.MODID);
    public static final RegistryObject<Codec<? extends OrSpawnPredicate>> OR_PREDICATE = PREDICATE_SERIALIZER.register("or_spawn", () -> OrSpawnPredicate.CODEC);
    public static final RegistryObject<Codec<? extends AndSpawnPredicate>> AND_PREDICATE = PREDICATE_SERIALIZER.register("and_spawn", () -> AndSpawnPredicate.CODEC);
    public static final RegistryObject<Codec<? extends NotSpawnPredicate>> NOT_PREDICATE = PREDICATE_SERIALIZER.register("not_spawn", () -> NotSpawnPredicate.CODEC);
    public static final RegistryObject<Codec<? extends BiomeSpawnPredicate>> BIOME_SPAWN_PREDICATE = PREDICATE_SERIALIZER.register("is_biome", () -> BiomeSpawnPredicate.CODEC);
    public static final RegistryObject<Codec<? extends CanSeeSkyFromBelowWaterSpawnPredicate>> CAN_SEE_SKY_FROM_BELOW_WATER_PREDICATE = PREDICATE_SERIALIZER.register("can_see_sky_from_below_water", () -> CanSeeSkyFromBelowWaterSpawnPredicate.CODEC);
    public static final RegistryObject<Codec<? extends CanSeeSkySpawnPredicate>> CAN_SEE_SKY_PREDICATE = PREDICATE_SERIALIZER.register("can_see_sky", () -> CanSeeSkySpawnPredicate.CODEC);
    public static final RegistryObject<Codec<? extends DifficultySpawnPredicate>> DIFFICULTY_PREDICATE = PREDICATE_SERIALIZER.register("is_difficulty_spawn", () -> DifficultySpawnPredicate.CODEC);
    public static final RegistryObject<Codec<? extends IsBlockStateSpawnPredicate>> BLOCK_STATE_SPAWN_PREDICATE = PREDICATE_SERIALIZER.register("is_block_state_spawn", () -> IsBlockStateSpawnPredicate.CODEC);
    public static final RegistryObject<Codec<? extends IsFluidStateSpawnPredicate>> FLUID_STATE_SPAWN_PREDICATE = PREDICATE_SERIALIZER.register("is_fluid_state_spawn", () -> IsFluidStateSpawnPredicate.CODEC);
    public static final RegistryObject<Codec<? extends IsBrightEnoughToSpawnPredicate>> IS_BRIGHT_ENOUGH_TO_SPAWN = PREDICATE_SERIALIZER.register("is_bright_enough_to_spawn", () -> IsBrightEnoughToSpawnPredicate.CODEC);
    public static final RegistryObject<Codec<? extends IsDarkEnoughToSpawnPredicate>> IS_DARK_ENOUGH_TO_SPAWN = PREDICATE_SERIALIZER.register("is_dark_enough_to_spawn", () -> IsDarkEnoughToSpawnPredicate.CODEC);
    public static final RegistryObject<Codec<? extends YLevelSpawnPredicate>> Y_LEVEL_SPAWN_PREDICATE = PREDICATE_SERIALIZER.register("y_level_check_spawn", () -> YLevelSpawnPredicate.CODEC);
    public static final RegistryObject<Codec<? extends TrueSpawnPredicate>> TRUE_SPAWN_PREDICATE = PREDICATE_SERIALIZER.register("true_spawn", () -> TrueSpawnPredicate.CODEC);

}
