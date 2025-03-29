package com.peeko32213.unusualprehistory.common.world.structure;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.peeko32213.unusualprehistory.core.registry.world.UPStructures;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pools.JigsawPlacement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class UndergroundDigSiteStructure extends Structure {

    public static final Codec<UndergroundDigSiteStructure> CODEC = ExtraCodecs.validate(RecordCodecBuilder.mapCodec((instance) -> instance.group(settingsCodec(instance),
        StructureTemplatePool.CODEC.fieldOf("start_pool").forGetter((pool) -> pool.startPool),
        ResourceLocation.CODEC.optionalFieldOf("start_jigsaw_name").forGetter((structure) -> structure.startJigsawName),
        Codec.INT.fieldOf("size").forGetter(size -> size.size),
        Codec.INT.fieldOf("min_y").forGetter(min_y -> min_y.min),
        Codec.INT.fieldOf("max_y").forGetter(max_y -> max_y.max),
        Codec.INT.fieldOf("offset_in_ground").forGetter(offset -> offset.offsetInGround),
        Codec.BOOL.fieldOf("use_expansion_hack").forGetter((hack) -> hack.useExpansionHack),
        Heightmap.Types.CODEC.optionalFieldOf("project_start_to_heightmap").forGetter((heightmap) -> heightmap.projectStartToHeightmap),
        Codec.intRange(1, 128).fieldOf("max_distance_from_center").forGetter((distance) -> distance.maxDistanceFromCenter)).apply(instance, UndergroundDigSiteStructure::new)),
        UndergroundDigSiteStructure::verifyRange).codec();

    private final Holder<StructureTemplatePool> startPool;
    private final Optional<ResourceLocation> startJigsawName;
    private final int size;
    private final int min;
    private final int max;
    private final int offsetInGround;
    private final boolean useExpansionHack;
    private final Optional<Heightmap.Types> projectStartToHeightmap;
    private final int maxDistanceFromCenter;

    private static DataResult<UndergroundDigSiteStructure> verifyRange(UndergroundDigSiteStructure range) {
        byte var10000;
        switch (range.terrainAdaptation()) {
            case NONE:
                var10000 = 0;
                break;
            case BURY:
            case BEARD_THIN:
            case BEARD_BOX:
                var10000 = 12;
                break;
            default:
                throw new IncompatibleClassChangeError();
        }

        int $$1 = var10000;
        return range.maxDistanceFromCenter + $$1 > 128 ? DataResult.error(() -> "Structure size including terrain adaptation must not exceed 128") : DataResult.success(range);
    }

    public UndergroundDigSiteStructure(Structure.StructureSettings settings, Holder<StructureTemplatePool> pool, Optional<ResourceLocation> start, int size, int min_y, int max_y, int offsetInGround, boolean hack, Optional<Heightmap.Types> heightmap, int distance) {
        super(settings);
        this.startPool = pool;
        this.startJigsawName = start;
        this.size = size;
        this.min = min_y;
        this.max = max_y;
        this.offsetInGround = offsetInGround;
        this.useExpansionHack = hack;
        this.projectStartToHeightmap = heightmap;
        this.maxDistanceFromCenter = distance;
    }

    @Override
    protected Optional<GenerationStub> findGenerationPoint(GenerationContext context) {
        BlockPos blockPos = new BlockPos(context.chunkPos().getMinBlockX(), 0, context.chunkPos().getMinBlockZ());
        BlockPos validPos = new BlockPos(blockPos.getX(), getValidY(context.chunkGenerator().getBaseColumn(blockPos.getX(), blockPos.getZ(), context.heightAccessor(), context.randomState())), blockPos.getZ());
        if (validPos.getY() != min - 1 && isSufficientlyFlat(context, validPos, 10)) {
            return JigsawPlacement.addPieces(context, this.startPool, this.startJigsawName, this.size, validPos.below(-offsetInGround), false, Optional.empty(), this.maxDistanceFromCenter);
        }
        return Optional.empty();
    }

    public boolean isSufficientlyFlat(GenerationContext context, BlockPos origin, int check) {
        List<BlockPos> blockPosList = new ArrayList<>();
        for (int x = -check; x < check; x++) {
            for (int z = -check; z < check; z++) {
                blockPosList.add(origin.offset(x, 0, z));
            }
        }
        int count = 0;
        for (BlockPos pos : blockPosList) {
            NoiseColumn blockView = context.chunkGenerator().getBaseColumn(pos.getX(), pos.getZ(), context.heightAccessor(), context.randomState());
            if (blockView.getBlock(pos.getY()).isAir() && !blockView.getBlock(pos.below().getY()).isAir()) {
                count++;
            }
        }
        return count >= check * check * 2;
    }

    public int getValidY(NoiseColumn sample) {
        int maxLength = 0;
        int currentLength = 0;
        int maxIndex = min - 1;
        for (int i = min; i < max; i += size) {
            if (sample.getBlock(i).isAir()) {
                int j = i + 1;
                while (j < max && sample.getBlock(j).isAir()) {
                    j++;
                }
                int sequenceLength = j - i;
                if (sequenceLength >= size) {
                    currentLength += sequenceLength;
                    if (currentLength > maxLength) {
                        maxLength = currentLength;
                        maxIndex = i;
                    }
                    i = j - 1;
                }
            } else {
                currentLength = 0;
            }
        }
        return maxIndex;
    }

    @Override
    public GenerationStep.Decoration step() {
        return GenerationStep.Decoration.UNDERGROUND_STRUCTURES;
    }

    @Override
    public StructureType<?> type() {
        return UPStructures.UNDERGROUND_DIG_SITE.get();
    }
}