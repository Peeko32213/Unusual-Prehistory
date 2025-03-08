package com.peeko32213.unusualprehistory.common.world.feature;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.peeko32213.unusualprehistory.core.registry.UPBlocks;
import com.peeko32213.unusualprehistory.core.registry.util.FastNoiseLite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import org.slf4j.Logger;

public class PetrifiedTreeFeature extends Feature<NoneFeatureConfiguration> {
    private static final Logger LOGGER = LogUtils.getLogger();

    public PetrifiedTreeFeature(Codec<NoneFeatureConfiguration> config) {
        super(config);
    }

    /**
     * Places the given feature at the given location.
     * During world generation, features are provided with a 3x3 region of chunks, centered on the chunk being generated,
     * that they can safely generate into.
     *
     * @param pContext A context object with a reference to the level and the position the feature is being placed at
     */
    public boolean place(FeaturePlaceContext<net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration> context) {
        WorldGenLevel worldgenlevel = context.level();
        BlockPos blockpos = context.origin();
        RandomSource random = context.random();
        FastNoiseLite noise = createNoise(worldgenlevel.getSeed() + random.nextLong(), random.nextFloat());
        int randomNr = random.nextInt(0, 50);
        boolean placeFeature = randomNr < 5;

        if(!placeFeature){
            return false;
        }

        double middleBlockZ = worldgenlevel.getChunk(blockpos.getX() >> 4, blockpos.getZ() >> 4).getPos().getMiddleBlockZ();
        double middleBlockX = worldgenlevel.getChunk(blockpos.getX() >> 4, blockpos.getZ() >> 4).getPos().getMiddleBlockX();
        double cornerBlockZ = worldgenlevel.getChunk(blockpos.getX() >> 4, blockpos.getZ() >> 4).getPos().getMinBlockZ();
        double cornerBlockX = worldgenlevel.getChunk(blockpos.getX() >> 4, blockpos.getZ() >> 4).getPos().getMinBlockX();

        BlockPos blockPosMid =  BlockPos.containing(middleBlockX, worldgenlevel.getHeight(Heightmap.Types.OCEAN_FLOOR_WG, (int) middleBlockX, (int) middleBlockZ), middleBlockZ);
        BlockPos blockPosCorner =  BlockPos.containing(cornerBlockX, worldgenlevel.getHeight(Heightmap.Types.OCEAN_FLOOR_WG, (int) cornerBlockX, (int) cornerBlockZ), cornerBlockZ);

        int radius = random.nextInt(5, 30);

        addBushes(worldgenlevel, random, blockPosMid, noise, radius);
        populateWithTrees(worldgenlevel, random, blockPosMid, noise, radius);
        return true;
    }

    public static void populateWithTrees(WorldGenLevel worldgenlevel, RandomSource rand, BlockPos origin, FastNoiseLite noise, int radius) {
        BlockState block = UPBlocks.PETRIFIED_WOOD_LOG.get().defaultBlockState();
        for (int x = -radius; x < radius; x++) {
            for (int z = -radius; z < radius; z++) {
                BlockPos pos = origin.offset(x, 0, z);
                double yHeight = worldgenlevel.getHeight(Heightmap.Types.OCEAN_FLOOR_WG, (int) pos.getX(), (int) pos.getZ()) - 1;
                BlockPos pos2 =  BlockPos.containing(pos.getX(), yHeight, pos.getZ());
                double distance = distance(x, 0, z, radius, 1, radius);
                float f = noise.GetNoise(x, (float) yHeight, z);
                if (distance < 1) {
                    if (f > 0.33 && f < 0.4) {
                        int randomTreeHeight = rand.nextInt(2, 7);
                        for (int treeHeight = 1; treeHeight < randomTreeHeight && !hasPetrifiedWoodNextToIt(worldgenlevel, pos2, rand); treeHeight++) {
                            worldgenlevel.setBlock(pos2.offset(0, treeHeight, 0), block, 3);
                        }
                    }
                }
            }
        }
    }

    public static void addBushes(WorldGenLevel worldgenlevel, RandomSource rand, BlockPos origin, FastNoiseLite noise, int radius) {
        BlockState block = UPBlocks.PETRIFIED_BUSH.get().defaultBlockState();
        for (int x = -radius; x < radius; x++) {
            for (int z = -radius; z < radius; z++) {
                BlockPos pos = origin.offset(x, 0, z);
                double yHeight = worldgenlevel.getHeight(Heightmap.Types.OCEAN_FLOOR_WG, (int) pos.getX(), (int) pos.getZ());
                BlockPos pos2 =  BlockPos.containing(pos.getX(), yHeight, pos.getZ());
                double distance = distance(x, 0, z, radius, 1, radius);
                float f = noise.GetNoise(x, (float) yHeight, z);
                if (distance < 1) {
                    boolean isCorrectBlock = worldgenlevel.getBlockState(pos2).is(Blocks.AIR);
                    if (f > 0 && f < 0.015 && isCorrectBlock) {
                        worldgenlevel.setBlock(pos2, block, 3);
                    }
                }
            }
        }
    }


    public static double distance(double x, double y, double z, double xRadius, double yRadius, double zRadius) {
        return Mth.square((double) x / (xRadius)) + Mth.square((double) y / (yRadius)) + Mth.square((double) z / (zRadius));
    }

    public static boolean hasPetrifiedWoodNextToIt(WorldGenLevel worldGenLevel, BlockPos pos, RandomSource randomSource) {
        int randomNr = randomSource.nextInt(0, 100);

        boolean checkForWood = randomNr > 10;

        if (!checkForWood) {
            return false;
        }

        for (Direction direction : Direction.Plane.HORIZONTAL) {
            BlockState blockstate1 = worldGenLevel.getBlockState(pos.relative(direction));
            if (blockstate1.is(UPBlocks.PETRIFIED_WOOD_LOG.get())) {
                return true;
            }
        }
        return false;
    }


    private static FastNoiseLite createNoise(long seed, float frequency) {
        FastNoiseLite noise = new FastNoiseLite((int) seed);
        noise.SetNoiseType(FastNoiseLite.NoiseType.OpenSimplex2S);
        noise.SetFrequency(frequency);
        return noise;
    }
}