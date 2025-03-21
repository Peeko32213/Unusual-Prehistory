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

public class CanSeeSkyFromBelowWaterSpawnPredicate implements SpawnPredicate {
    private final BlockPos offset;

    public CanSeeSkyFromBelowWaterSpawnPredicate(BlockPos offset) {
        this.offset = offset;
    }


    @Override
    public Predicate<Boolean> getPredicate() {
        return aBoolean -> false;
    }

    @Override
    public Codec<? extends PredicateCodec<Boolean>> codec() {
        return UPSpawnPredicateRegistry.CAN_SEE_SKY_FROM_BELOW_WATER_PREDICATE.get();
    }

    // Codec for CanSeeSkyFromBelowWaterPredicate with NullableFieldCodec for offset
    public static final Codec<CanSeeSkyFromBelowWaterSpawnPredicate> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    NullableFieldCodec.makeDefaultableField("offset", BlockPos.CODEC, new BlockPos(0, 0, 0)).forGetter(CanSeeSkyFromBelowWaterSpawnPredicate::getOffset)
            ).apply(instance, CanSeeSkyFromBelowWaterSpawnPredicate::new)
    );

    public BlockPos getOffset() {
        return offset;
    }

    @Override
    public boolean checkSpawn(LevelAccessor pLevel, MobSpawnType pSpawnType, BlockPos pPos, RandomSource pRandom) {
        // Apply offset to position
        BlockPos checkPos = pPos.offset(offset);

        // Check if the position can see the sky from below water level
        return pLevel.canSeeSkyFromBelowWater(checkPos);
    }
}
