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

public class YLevelSpawnPredicate implements SpawnPredicate {
    public enum CheckMode {
        SEA_LEVEL("sea_level"),
        BLOCK_POSITION("block_position");
       // CUSTOM_OFFSET("custom_offset");

        private final String name;

        CheckMode(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public static CheckMode fromName(String name) {
            for (CheckMode mode : values()) {
                if (mode.getName().equalsIgnoreCase(name)) {
                    return mode;
                }
            }
            throw new IllegalArgumentException("Unknown CheckMode: " + name);
        }

        public static Codec<CheckMode> CODEC = Codec.STRING.xmap(
                CheckMode::fromName,
                CheckMode::getName
        );
    }

    public static final Codec<YLevelSpawnPredicate> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    ComparisonPredicate.CODEC.fieldOf("y_check").forGetter(YLevelSpawnPredicate::getYComparison),
                    CheckMode.CODEC.fieldOf("check_mode").orElse(CheckMode.SEA_LEVEL).forGetter(YLevelSpawnPredicate::getCheckMode),
                    BlockPos.CODEC.fieldOf("offset").orElse(BlockPos.ZERO).forGetter(YLevelSpawnPredicate::getOffset)
            ).apply(instance, YLevelSpawnPredicate::new)
    );

    private final ComparisonPredicate yComparison;
    private final CheckMode checkMode;
    private final BlockPos offset;

    public YLevelSpawnPredicate(ComparisonPredicate yComparison, CheckMode checkMode, BlockPos offset) {
        this.yComparison = yComparison;
        this.checkMode = checkMode;
        this.offset = offset;
    }

    @Override
    public boolean checkSpawn(LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        int yLevelToCheck;

        switch (checkMode) {
            case SEA_LEVEL:
                yLevelToCheck = level.getSeaLevel() + offset.getY();
                break;
            case BLOCK_POSITION:
                yLevelToCheck = pos.getY() + offset.getY();
                break;
            default:
                throw new IllegalArgumentException("Unknown check mode: " + checkMode);
        }

        return yComparison.check(yLevelToCheck);
    }

    @Override
    public Predicate<Boolean> getPredicate() {
        return aBoolean -> false;
    }

    @Override
    public Codec<? extends PredicateCodec<Boolean>> codec() {
        return UPSpawnPredicateRegistry.Y_LEVEL_SPAWN_PREDICATE.get();
    }

    public ComparisonPredicate getYComparison() {
        return yComparison;
    }

    public CheckMode getCheckMode() {
        return checkMode;
    }

    public BlockPos getOffset() {
        return offset;
    }
}
