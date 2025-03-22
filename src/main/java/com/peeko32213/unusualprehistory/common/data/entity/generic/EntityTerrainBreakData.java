package com.peeko32213.unusualprehistory.common.data.entity.generic;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.peeko32213.unusualprehistory.core.other.UPTags;
import com.scouter.goalsmith.data.PredicateCodec;
import com.scouter.goalsmith.data.predicates.TruePredicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;


public class EntityTerrainBreakData {

    public static final Codec<EntityTerrainBreakData> CODEC = RecordCodecBuilder.<EntityTerrainBreakData>create(instance -> instance
            .group(
                    Codec.BOOL.fieldOf("can_break_terrain").forGetter(EntityTerrainBreakData::isCanBreakTerrain),
                    PredicateCodec.DIRECT_CODEC.fieldOf("starting_predicate").forGetter(EntityTerrainBreakData::getStartingPredicate),
                    TagKey.codec(Registries.BLOCK).fieldOf("blocks_to_break_tag").forGetter(EntityTerrainBreakData::getBlocksToBreak),
                    Codec.DOUBLE.fieldOf("inflation_factor").forGetter(EntityTerrainBreakData::getInflationFactor)
            ).apply(instance, (aBoolean, predicateCodec, blockTagKey, aDouble) -> new EntityTerrainBreakData(aBoolean, (PredicateCodec<Entity>) predicateCodec, blockTagKey, aDouble))
    );

    private final boolean canBreakTerrain;
    private final PredicateCodec<Entity> startingPredicate;
    private final TagKey<Block> blocksToBreak;
    private final double inflationFactor;

    public EntityTerrainBreakData(boolean canBreakTerrain, PredicateCodec<Entity> startingPredicate, TagKey<Block> blocksToBreak, double inflationFactor) {
        this.canBreakTerrain = canBreakTerrain;
        this.startingPredicate = startingPredicate;
        this.blocksToBreak = blocksToBreak;
        this.inflationFactor = inflationFactor;
    }


    public boolean breakBlocks(Mob entity) {
        if(!canBreakTerrain) return false;
        if (entity.horizontalCollision && net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(entity.level(), entity) && startingPredicate.getPredicate().test(entity)) {
            boolean brokeBlocks = false;
            AABB axisalignedbb = entity.getBoundingBox().inflate(inflationFactor);
            for (BlockPos blockpos : BlockPos.betweenClosed(Mth.floor(axisalignedbb.minX), Mth.floor(axisalignedbb.minY), Mth.floor(axisalignedbb.minZ), Mth.floor(axisalignedbb.maxX), Mth.floor(axisalignedbb.maxY), Mth.floor(axisalignedbb.maxZ))) {
                BlockState blockstate = entity.level().getBlockState(blockpos);
                if (blockstate.is(blocksToBreak)) {
                    brokeBlocks = true;
                    entity.level().destroyBlock(blockpos, true, entity);
                }
            }
            return brokeBlocks;
        }
        return false;
    }

    public double getInflationFactor() {
        return inflationFactor;
    }

    public PredicateCodec<Entity> getStartingPredicate() {
        return startingPredicate;
    }

    public TagKey<Block> getBlocksToBreak() {
        return blocksToBreak;
    }

    public boolean isCanBreakTerrain() {
        return canBreakTerrain;
    }

    public static EntityTerrainBreakData getDefaultInstance() {
        return new EntityTerrainBreakData(false, new TruePredicate<>(),UPTags.NONE_BLOCK_TAG,0D);
    }
}
