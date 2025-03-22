package com.peeko32213.unusualprehistory.common.data.predicate;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.peeko32213.unusualprehistory.core.registry.entities.UPSpawnPredicateRegistry;
import com.scouter.goalsmith.data.PredicateCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.LevelAccessor;

import java.util.function.Predicate;

public class OrSpawnPredicate implements SpawnPredicate {
    public static final Codec<OrSpawnPredicate> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    PredicateCodec.DIRECT_CODEC.fieldOf("first").forGetter(predicate -> predicate.first),
                    PredicateCodec.DIRECT_CODEC.fieldOf("second").forGetter(predicate -> predicate.second)
            ).apply(instance, OrSpawnPredicate::new)
    );

    private final PredicateCodec first;
    private final PredicateCodec second;
    private final SpawnPredicate firstSpawn;
    private final SpawnPredicate secondSpawn;

    public OrSpawnPredicate(PredicateCodec first, PredicateCodec second) {
        if (!(first instanceof SpawnPredicate) || !(second instanceof SpawnPredicate)) {
            throw new IllegalArgumentException("Arguments must be instances of SpawnPredicate");
        }

        this.first = first;
        this.second = second;

        this.firstSpawn = (SpawnPredicate) first;
        this.secondSpawn = (SpawnPredicate) second;
    }

    @Override
    public boolean checkSpawn(LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        return firstSpawn.checkSpawn(level, spawnType, pos, random) || secondSpawn.checkSpawn(level, spawnType, pos, random);
    }

    @Override
    public Predicate<Boolean> getPredicate() {
        // Implementation as required
        return aBoolean -> false;
    }

    @Override
    public Codec<? extends PredicateCodec<Boolean>> codec() {
        return UPSpawnPredicateRegistry.OR_PREDICATE.get();
    }
}
