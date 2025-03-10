package com.peeko32213.unusualprehistory.common.data.predicate;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.peeko32213.unusualprehistory.common.data.codec.NullableFieldCodec;
import com.peeko32213.unusualprehistory.core.registry.UPSpawnPredicateRegistry;
import com.scouter.goalsmith.data.PredicateCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.Biome;

import java.util.function.Predicate;

public class BiomeSpawnPredicate implements SpawnPredicate {
    private final TagKey<Biome> biomeTag;
    private final BlockPos offset;

    public BiomeSpawnPredicate(TagKey<Biome> biomeTag, BlockPos offset) {
        this.biomeTag = biomeTag;
        this.offset = offset;
    }

    @Override
    public boolean checkSpawn(LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        BlockPos checkPos = pos.offset(offset);
        Holder<Biome> biome = level.getBiome(checkPos);
        return biome.is(biomeTag);
    }

    @Override
    public Predicate<Boolean> getPredicate() {
        return aBoolean -> false;
    }

    @Override
    public Codec<? extends PredicateCodec<Boolean>> codec() {
        return UPSpawnPredicateRegistry.BIOME_SPAWN_PREDICATE.get();
    }

    public static final Codec<BiomeSpawnPredicate> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    TagKey.codec(Registries.BIOME).fieldOf("biome_tag").forGetter(BiomeSpawnPredicate::getBiomeTag),
                    NullableFieldCodec.makeDefaultableField("offset", BlockPos.CODEC, new BlockPos(0, 0, 0)).forGetter(BiomeSpawnPredicate::getOffset)
            ).apply(instance, BiomeSpawnPredicate::new)
    );

    public TagKey<Biome> getBiomeTag() {
        return biomeTag;
    }

    public BlockPos getOffset() {
        return offset;
    }
}
