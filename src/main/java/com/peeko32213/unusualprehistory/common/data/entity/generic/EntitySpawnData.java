package com.peeko32213.unusualprehistory.common.data.entity.generic;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.peeko32213.unusualprehistory.common.data.predicate.AndSpawnPredicate;
import com.peeko32213.unusualprehistory.common.data.predicate.IsBlockStateSpawnPredicate;
import com.peeko32213.unusualprehistory.common.data.predicate.IsBrightEnoughToSpawnPredicate;
import com.peeko32213.unusualprehistory.common.data.predicate.SpawnPredicate;
import com.peeko32213.unusualprehistory.core.registry.UPTags;
import com.scouter.goalsmith.data.PredicateCodec;

public class EntitySpawnData {
    public static final Codec<EntitySpawnData> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.BOOL.fieldOf("spawns_naturally").forGetter(EntitySpawnData::getSpawnsNaturally),
                    PredicateCodec.DIRECT_CODEC.fieldOf("spawn_predicate").forGetter(data -> data.predicateCodec)
            ).apply(instance, EntitySpawnData::new)
        );

    private final boolean spawnsNaturally;
    private final SpawnPredicate spawnPredicate;
    private final PredicateCodec predicateCodec;

    public EntitySpawnData(boolean spawnsNaturally, PredicateCodec PredicateCodec) {
        this.spawnsNaturally = spawnsNaturally;
        this.predicateCodec = PredicateCodec;
        if (!(predicateCodec instanceof SpawnPredicate)) {
            new RuntimeException("Arguments must be instances of SpawnPredicate");
        }
        this.spawnPredicate = (SpawnPredicate) predicateCodec;
    }

    public boolean getSpawnsNaturally() {
        return spawnsNaturally;
    }


    public SpawnPredicate getSpawnPredicate() {
        return spawnPredicate;
    }

    public static EntitySpawnData getDefaultInstance() {
        return new EntitySpawnData(false, new AndSpawnPredicate(new IsBlockStateSpawnPredicate(UPTags.DINO_NATURAL_SPAWNABLE), new IsBrightEnoughToSpawnPredicate()));
    }
}
