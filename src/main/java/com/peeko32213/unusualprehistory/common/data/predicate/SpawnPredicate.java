package com.peeko32213.unusualprehistory.common.data.predicate;

import com.scouter.goalsmith.data.PredicateCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.LevelAccessor;

public interface SpawnPredicate extends PredicateCodec<Boolean> {


    boolean checkSpawn(LevelAccessor pLevel, MobSpawnType pSpawnType, BlockPos pPos, RandomSource pRandom);

}
