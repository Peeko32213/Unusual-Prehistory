package com.peeko32213.unusualprehistory.common.data.predicate;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.peeko32213.unusualprehistory.common.data.codec.NullableFieldCodec;
import com.peeko32213.unusualprehistory.core.registry.entities.UPSpawnPredicateRegistry;
import com.scouter.goalsmith.data.PredicateCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.LevelAccessor;

import java.util.function.Predicate;

public class CanSeeSkySpawnPredicate implements SpawnPredicate {
    private final BlockPos offset;

    public CanSeeSkySpawnPredicate(BlockPos offset) {
        this.offset = offset;
    }

    @Override
    public Predicate<Boolean> getPredicate() {
        return aBoolean -> false;
    }

    @Override
    public Codec<? extends PredicateCodec<Boolean>> codec() {
        return UPSpawnPredicateRegistry.CAN_SEE_SKY_PREDICATE.get();
    }

    // Codec for CanSeeSkyPredicate with NullableFieldCodec for offset
    public static final Codec<CanSeeSkySpawnPredicate> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    NullableFieldCodec.makeDefaultableField("offset", BlockPos.CODEC, new BlockPos(0, 0, 0)).forGetter(CanSeeSkySpawnPredicate::getOffset)
            ).apply(instance, CanSeeSkySpawnPredicate::new)
    );

    public BlockPos getOffset() {
        return offset;
    }

    @Override
    public boolean checkSpawn(LevelAccessor pLevel, MobSpawnType pSpawnType, BlockPos pPos, RandomSource pRandom) {
        BlockPos checkPos = pPos.offset(offset);
        return pLevel.canSeeSky(checkPos);
    }
}
