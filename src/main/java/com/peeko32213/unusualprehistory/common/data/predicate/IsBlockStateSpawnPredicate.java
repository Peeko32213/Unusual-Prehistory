package com.peeko32213.unusualprehistory.common.data.predicate;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.peeko32213.unusualprehistory.common.data.codec.NullableFieldCodec;
import com.peeko32213.unusualprehistory.core.registry.entities.UPSpawnPredicateRegistry;
import com.scouter.goalsmith.data.PredicateCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Predicate;

public class IsBlockStateSpawnPredicate implements SpawnPredicate {

    public static final Codec<IsBlockStateSpawnPredicate> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.either(BuiltInRegistries.BLOCK.byNameCodec().fieldOf("block").codec(), TagKey.codec(Registries.BLOCK).fieldOf("tag").codec()).fieldOf("block_type").forGetter(predicate ->
                            predicate.block != null ? Either.left(predicate.block) : Either.right(predicate.blockTagKey)
                    ),
                    NullableFieldCodec.makeDefaultableField("offset",BlockPos.CODEC, new BlockPos(0,0,0)).forGetter(e -> e.offSet)
            ).apply(instance, IsBlockStateSpawnPredicate::new)
    );


    private Block block;
    private TagKey<Block> blockTagKey;
    private BlockPos offSet;

    public IsBlockStateSpawnPredicate(Either<Block, TagKey<Block>> blockOrTag, BlockPos offSet) {
        this.block = blockOrTag.left().orElse(null);
        this.blockTagKey = blockOrTag.right().orElse(null);
        this.offSet = offSet;
    }

    public IsBlockStateSpawnPredicate() {
        this.block = null;
        this.blockTagKey = null;
        this.offSet = new BlockPos(0, 0, 0);
    }

    public IsBlockStateSpawnPredicate(Block block) {
        this.block = block;
        this.blockTagKey = null;
        this.offSet = new BlockPos(0, 0, 0);
    }

    public IsBlockStateSpawnPredicate(TagKey<Block> block) {
        this.block = null;
        this.blockTagKey = block;
        this.offSet = new BlockPos(0, 0, 0);
    }

    public IsBlockStateSpawnPredicate(Block block, BlockPos offSet) {
        this.block = block;
        this.blockTagKey = null;
        this.offSet = offSet;
    }

    public IsBlockStateSpawnPredicate(TagKey<Block> block, BlockPos offSet) {
        this.block = null;
        this.blockTagKey = block;
        this.offSet = offSet;
    }

    @Override
    public boolean checkSpawn(LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        BlockPos checkPos = pos.offset(this.offSet);
        BlockState state = level.getBlockState(checkPos);

        if (this.block != null) {
            return state.is(this.block);
        } else if (this.blockTagKey != null) {
            return state.is(this.blockTagKey);
        }

        return false;
    }
    @Override
    public Predicate<Boolean> getPredicate() {
        return aBoolean -> false;
    }

    @Override
    public Codec<? extends PredicateCodec<Boolean>> codec() {
        return UPSpawnPredicateRegistry.BLOCK_STATE_SPAWN_PREDICATE.get();
    }
}
