package com.peeko32213.unusualprehistory.common.data.predicate;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.peeko32213.unusualprehistory.core.registry.entities.UPSpawnPredicateRegistry;
import com.scouter.goalsmith.data.PredicateCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.LevelAccessor;

import javax.annotation.Nullable;
import java.util.function.Predicate;

public class DifficultySpawnPredicate implements SpawnPredicate {
    private final Difficulty difficulty;
    private final Comparison comparison;

    public DifficultySpawnPredicate(Difficulty difficulty, Comparison comparison) {
        this.difficulty = difficulty;
        this.comparison = comparison;
    }

    @Override
    public Predicate<Boolean> getPredicate() {
        return aBoolean -> false;
    }

    @Override
    public Codec<? extends PredicateCodec<Boolean>> codec() {
        return UPSpawnPredicateRegistry.DIFFICULTY_PREDICATE.get();
    }

    // Custom codec for DifficultySpawnPredicate using Difficulty.CODEC and Comparison.CODEC
    public static final Codec<DifficultySpawnPredicate> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Difficulty.CODEC.fieldOf("difficulty").forGetter(DifficultySpawnPredicate::getDifficulty),
                    Comparison.CODEC.fieldOf("comparison").forGetter(DifficultySpawnPredicate::getComparison)
            ).apply(instance, DifficultySpawnPredicate::new)
    );

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public Comparison getComparison() {
        return comparison;
    }

    @Override
    public boolean checkSpawn(LevelAccessor pLevel, MobSpawnType pSpawnType, BlockPos pPos, RandomSource pRandom) {
        Difficulty currentDifficulty = pLevel.getDifficulty();
        switch (comparison) {
            case EQUAL:
                return currentDifficulty == difficulty;
            case GREATER_THAN:
                return currentDifficulty.ordinal() > difficulty.ordinal();
            case LESS_THAN:
                return currentDifficulty.ordinal() < difficulty.ordinal();
            case NOT_EQUAL:
                return currentDifficulty != difficulty;
            default:
                return false; // Handle unsupported comparisons gracefully
        }
    }

    // Comparison Enum with Custom Codec
    public enum Comparison implements StringRepresentable {
        EQUAL("equal"),
        NOT_EQUAL("equal"),
        GREATER_THAN("greater_than"),
        LESS_THAN("less_than");

        private final String key;

        Comparison(String key) {
            this.key = key;
        }

        @Override
        public String getSerializedName() {
            return key;
        }

        // Custom codec for Comparison enum
        public static final EnumCodec<Comparison> CODEC = StringRepresentable.fromEnum(Comparison::values);

        @Nullable
        public static Comparison byName(String name) {
            return CODEC.byName(name);
        }
    }
}
