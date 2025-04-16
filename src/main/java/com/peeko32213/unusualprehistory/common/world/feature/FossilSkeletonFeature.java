package com.peeko32213.unusualprehistory.common.world.feature;

import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.peeko32213.unusualprehistory.common.world.feature.configs.FossilSkeletonConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

public class FossilSkeletonFeature extends Feature<FossilSkeletonConfig> {

    public FossilSkeletonFeature(Codec<FossilSkeletonConfig> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<FossilSkeletonConfig> context) {
        RandomSource randomsource = context.random();
        WorldGenLevel level = context.level();
        BlockPos chunkCenter = context.origin().atY(level.getMinBuildHeight() + 3);
        List<BlockPos> genPos = new ArrayList<>();
        int surface = level.getHeight(Heightmap.Types.OCEAN_FLOOR_WG, chunkCenter.getX(), chunkCenter.getZ()) - 5;
        int j = 0;
        while (chunkCenter.getY() < surface) {
            BlockPos next = chunkCenter.above();
            BlockState currentState = level.getBlockState(chunkCenter);
            BlockState nextState = level.getBlockState(next);
            if (!canReplace(currentState, j) && canReplace(nextState, j + 1)) {
                genPos.add(chunkCenter);
            }
            j++;
            chunkCenter = next;
        }
        if (genPos.isEmpty()) {
            return false;
        }
        BlockPos blockpos = genPos.size() <= 1 ? genPos.get(0) : genPos.get(randomsource.nextInt(genPos.size() - 1));
        if (!canGenerateAt(level, blockpos)) {
            return false;
        }
        Rotation rotation = Rotation.getRandom(randomsource);
        FossilSkeletonConfig config = context.config();
        int i = randomsource.nextInt(config.structures.size());
        StructureTemplateManager structuretemplatemanager = level.getLevel().getServer().getStructureManager();
        StructureTemplate structuretemplate = structuretemplatemanager.getOrCreate(config.structures.get(i));
        ChunkPos chunkpos = new ChunkPos(blockpos);
        BoundingBox boundingbox = new BoundingBox(chunkpos.getMinBlockX() - 16, level.getMinBuildHeight(), chunkpos.getMinBlockZ() - 16, chunkpos.getMaxBlockX() + 16, level.getMaxBuildHeight(), chunkpos.getMaxBlockZ() + 16);
        StructurePlaceSettings structureplacesettings = (new StructurePlaceSettings()).setRotation(rotation).setBoundingBox(boundingbox).setRandom(randomsource);
        structureplacesettings = modifyPlacementSettings(structureplacesettings);
        Vec3i vec3i = structuretemplate.getSize(rotation);
        BlockPos blockpos1 = blockpos.offset(-vec3i.getX() / 2, 0, -vec3i.getZ() / 2);
        int replaceDown = 0;
        while (skipsOver(level.getBlockState(blockpos1), replaceDown) && blockpos1.getY() < level.getMinBuildHeight()) {
            blockpos1 = blockpos1.below();
            replaceDown++;
        }
        blockpos1 = blockpos1.below(calculateSinkBy(level, blockpos1, structuretemplate, config.sinkBy));
        BlockPos blockpos2 = structuretemplate.getZeroPositionWithTransform(blockpos1, Mirror.NONE, rotation);
        if (structuretemplate.placeInWorld(level, blockpos2, blockpos2, structureplacesettings, randomsource, 18)) {
            processBoundingBox(level, structuretemplate.getBoundingBox(structureplacesettings, blockpos2), randomsource);
        }
        return true;
    }

    protected int calculateSinkBy(WorldGenLevel level, BlockPos blockpos1, StructureTemplate structuretemplate, int sinkByIn) {
        return sinkByIn;
    }

    public void processBoundingBox(WorldGenLevel level, BoundingBox boundingBox, RandomSource randomsource) {
        BoundingBox box = new BoundingBox(boundingBox.minX(), boundingBox.minY(), boundingBox.minZ(), boundingBox.maxX(), boundingBox.minY() + 1, boundingBox.maxZ());
        Set<BlockPos> supportsNeededBelow = Sets.newHashSet();
        BlockPos.betweenClosedStream(box).forEach((pos) -> {
            if (!level.getBlockState(pos).isAir()) {
                if (!level.getFluidState(pos.below()).isEmpty() || !level.getBlockState(pos.below()).isCollisionShapeFullBlock(level, pos.below())) {
                    supportsNeededBelow.add(pos.immutable());
                }
            }
        });
        BlockPos.MutableBlockPos grounded = new BlockPos.MutableBlockPos();
        for (BlockPos pos : supportsNeededBelow) {
            grounded.set(pos.getX(), pos.getY() - 1, pos.getZ());
            while ((!level.getBlockState(grounded).getFluidState().isEmpty() || !level.getBlockState(grounded).isCollisionShapeFullBlock(level, grounded)) && grounded.getY() > level.getMinBuildHeight()) {
                level.setBlock(grounded, Blocks.STONE.defaultBlockState(), 3);
                grounded.move(0, -1, 0);
            }
        }
    }

    public StructurePlaceSettings modifyPlacementSettings(StructurePlaceSettings structureplacesettings) {
        return structureplacesettings.setKeepLiquids(false);
    }

    protected boolean canGenerateAt(WorldGenLevel level, BlockPos blockpos) {
        return true;
    }

    protected boolean canReplace(BlockState state, int already) {
        return (state.isAir() || state.canBeReplaced()) || already < 3;
    }

    protected boolean skipsOver(BlockState state, int already) {
        return canReplace(state, already);
    }
}