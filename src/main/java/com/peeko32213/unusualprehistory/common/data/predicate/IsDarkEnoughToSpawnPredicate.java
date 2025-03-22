package com.peeko32213.unusualprehistory.common.data.predicate;

import com.mojang.serialization.Codec;
import com.peeko32213.unusualprehistory.core.registry.entities.UPSpawnPredicateRegistry;
import com.scouter.goalsmith.data.PredicateCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;

import java.util.function.Predicate;

public class IsDarkEnoughToSpawnPredicate implements SpawnPredicate {
    public static final Codec<IsDarkEnoughToSpawnPredicate> CODEC = Codec.unit(new IsDarkEnoughToSpawnPredicate());

    @Override
    public Predicate<Boolean> getPredicate() {
        return aBoolean -> false;
    }

    @Override
    public Codec<? extends PredicateCodec<Boolean>> codec() {
        return UPSpawnPredicateRegistry.IS_DARK_ENOUGH_TO_SPAWN.get();
    }


    @Override
    public boolean checkSpawn(LevelAccessor pLevel, MobSpawnType pSpawnType, BlockPos pPos, RandomSource pRandom) {
        return Monster.isDarkEnoughToSpawn((ServerLevelAccessor) pLevel, pPos, pRandom);
    }
}
