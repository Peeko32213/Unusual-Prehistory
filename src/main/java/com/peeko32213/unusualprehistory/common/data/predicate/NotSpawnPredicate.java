package com.peeko32213.unusualprehistory.common.data.predicate;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.peeko32213.unusualprehistory.core.registry.UPSpawnPredicateRegistry;
import com.scouter.goalsmith.data.PredicateCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.LevelAccessor;

import java.util.function.Predicate;

public class NotSpawnPredicate implements SpawnPredicate {
    public static final Codec<NotSpawnPredicate> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    PredicateCodec.DIRECT_CODEC.fieldOf("predicate").forGetter(predicate -> predicate.predicate)
            ).apply(instance, NotSpawnPredicate::new)
    );

    private final PredicateCodec predicate;
    private final SpawnPredicate firstPredicate;
    public NotSpawnPredicate(PredicateCodec predicate) {
        if (!(predicate instanceof SpawnPredicate)) {
            throw new IllegalArgumentException("Argument must be an instance of SpawnPredicate");
        }
        this.predicate = predicate;
        this.firstPredicate = (SpawnPredicate) predicate;
    }

    @Override
    public boolean checkSpawn(LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        return !firstPredicate.checkSpawn(level, spawnType, pos, random);
    }

    @Override
    public Predicate<Boolean> getPredicate() {
        return aBoolean -> false;
    }

    @Override
    public Codec<? extends PredicateCodec<Boolean>> codec() {
        return UPSpawnPredicateRegistry.NOT_PREDICATE.get();
    }
}