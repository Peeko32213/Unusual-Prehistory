package com.peeko32213.unusualprehistory.common.data.predicate;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.peeko32213.unusualprehistory.common.data.codec.NullableFieldCodec;
import com.peeko32213.unusualprehistory.core.registry.UPSpawnPredicateRegistry;
import com.scouter.goalsmith.data.PredicateCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LightLayer;

import java.util.function.Predicate;

public class IsBrightEnoughToSpawnPredicate implements SpawnPredicate {
    private final int requiredLight;
    private final BlockPos offset;

    public IsBrightEnoughToSpawnPredicate() {
       this(8, new BlockPos(0,0,0));
    }

    public IsBrightEnoughToSpawnPredicate(int requiredLight, BlockPos offset) {
        this.requiredLight = requiredLight;
        this.offset = offset;
    }

    @Override
    public boolean checkSpawn(LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        BlockPos checkPos = pos.offset(offset);
        return level.getBrightness(LightLayer.SKY, checkPos) >= requiredLight;
    }

    @Override
    public Predicate<Boolean> getPredicate() {
        return aBoolean -> false;
    }

    @Override
    public Codec<? extends PredicateCodec<Boolean>> codec() {
        return UPSpawnPredicateRegistry.IS_BRIGHT_ENOUGH_TO_SPAWN.get();
    }

    public static final Codec<IsBrightEnoughToSpawnPredicate> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    NullableFieldCodec.makeDefaultableField("required_light",Codec.intRange(0, 15), 8).forGetter(IsBrightEnoughToSpawnPredicate::getRequiredLight),
                    NullableFieldCodec.makeDefaultableField("offset", BlockPos.CODEC, new BlockPos(0, 0, 0)).forGetter(IsBrightEnoughToSpawnPredicate::getOffset)
            ).apply(instance, IsBrightEnoughToSpawnPredicate::new)
    );

    public int getRequiredLight() {
        return requiredLight;
    }

    public BlockPos getOffset() {
        return offset;
    }
}
