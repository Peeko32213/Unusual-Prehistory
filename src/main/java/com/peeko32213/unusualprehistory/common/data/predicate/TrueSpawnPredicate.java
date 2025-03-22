package com.peeko32213.unusualprehistory.common.data.predicate;

import com.mojang.serialization.Codec;
import com.peeko32213.unusualprehistory.core.registry.entities.UPSpawnPredicateRegistry;
import com.scouter.goalsmith.data.PredicateCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.LevelAccessor;

import java.util.function.Predicate;

public class TrueSpawnPredicate implements SpawnPredicate {
    public static final Codec<TrueSpawnPredicate> CODEC = Codec.unit(new TrueSpawnPredicate());

    @Override
    public Predicate<Boolean> getPredicate() {
        return aBoolean -> false;
    }

    @Override
    public Codec<? extends PredicateCodec<Boolean>> codec() {
        return UPSpawnPredicateRegistry.TRUE_SPAWN_PREDICATE.get();
    }


    @Override
    public boolean checkSpawn(LevelAccessor pLevel, MobSpawnType pSpawnType, BlockPos pPos, RandomSource pRandom) {
        return true;
    }
}
