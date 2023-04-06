package com.peeko32213.unusualprehistory.common.world.feature;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.peeko32213.unusualprehistory.core.registry.UPBlocks;
import com.peeko32213.unusualprehistory.core.registry.util.FastNoiseLite;
import net.minecraft.core.BlockPos;
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
        double middleBlockZ = worldgenlevel.getChunk(blockpos.getX() >> 4, blockpos.getZ() >> 4).getPos().getMiddleBlockZ();
        double middleBlockX = worldgenlevel.getChunk(blockpos.getX() >> 4, blockpos.getZ() >> 4).getPos().getMiddleBlockX();
        double cornerBlockZ = worldgenlevel.getChunk(blockpos.getX() >> 4, blockpos.getZ() >> 4).getPos().getMinBlockZ();
        double cornerBlockX = worldgenlevel.getChunk(blockpos.getX() >> 4, blockpos.getZ() >> 4).getPos().getMinBlockX();


        BlockPos blockPosMid = new BlockPos(middleBlockX, worldgenlevel.getHeight(Heightmap.Types.OCEAN_FLOOR_WG, (int) middleBlockX, (int) middleBlockZ), middleBlockZ);
        BlockPos blockPosCorner = new BlockPos(cornerBlockX, worldgenlevel.getHeight(Heightmap.Types.OCEAN_FLOOR_WG, (int) cornerBlockX, (int) cornerBlockZ), cornerBlockZ);

        int radius = random.nextInt(3, 6);
        int radius2 = radius;

        changeTerrain(worldgenlevel, random, blockPosCorner, noise);
        createSphere(worldgenlevel, random, blockPosMid, noise);
        populateWithTrees(worldgenlevel, random, blockPosCorner, noise);
        return true;
    }


    public static void createSphere(WorldGenLevel worldgenlevel, RandomSource rand, BlockPos origin, FastNoiseLite noise) {
        int height = rand.nextInt(2, 4);
        int radius = height;
        BlockState block = Blocks.STONE.defaultBlockState();
        for (int x = -radius; x < radius; x++) {
            for (int y = 0; y < height; y++) {
                for (int z = -radius; z < radius; z++) {
                    BlockPos pos = origin.offset(x, y, z);
                    BlockPos pos2 = origin.offset(x, -y, z);
                    double distance = distance(x, y, z, radius, height, radius);
                    float f = noise.GetNoise(x, y, z);
                    if (distance < 1) {
                        if (f < 1) {
                            worldgenlevel.setBlock(pos, block, 3);
                            worldgenlevel.setBlock(pos2, block, 3);
                        } else {

                            if (!worldgenlevel.getBlockState(pos).is(Blocks.AIR)) {
                                worldgenlevel.setBlock(pos, block, 3);
                                worldgenlevel.setBlock(pos2, block, 3);
                            }
                        }
                    }
                }
            }
        }
    }

    public static void populateWithTrees(WorldGenLevel worldgenlevel, RandomSource rand, BlockPos origin, FastNoiseLite noise)
    {
        int height = 8;
        int radius = height;

        BlockState block = UPBlocks.PETRIFIED_WOOD_LOG.get().defaultBlockState();
        for (int x = 0; x < 16; x++) {
                for (int z = 0; z < 16; z++) {
                    BlockPos pos = origin.offset(x, 0, z);
                    double yHeight = worldgenlevel.getHeight(Heightmap.Types.OCEAN_FLOOR_WG, (int) pos.getX(), (int) pos.getZ());

                    BlockPos pos2 = new BlockPos(pos.getX(), yHeight, pos.getZ());
                    float f = noise.GetNoise(x, (float) yHeight, z);
                        if (f > 0.55) {
                            int randomTreeHeight = rand.nextInt(1,5);
                            for(int treeHeight = 0; treeHeight < randomTreeHeight; treeHeight++){
                                worldgenlevel.setBlock(pos2.offset(0,treeHeight,0), block, 3);
                            }
                        }


                }
        }
    }

    public static void changeTerrain(WorldGenLevel worldgenlevel, RandomSource rand, BlockPos origin, FastNoiseLite noise)
    {
        int height = 8;
        int radius = height;
        BlockState block = Blocks.SAND.defaultBlockState();
        BlockState block2 = Blocks.DIRT.defaultBlockState();
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                BlockPos pos = origin.offset(x, 0, z);
                double yHeight = worldgenlevel.getHeight(Heightmap.Types.OCEAN_FLOOR_WG, (int) pos.getX(), (int) pos.getZ()) -1;
                BlockPos pos2 = new BlockPos(pos.getX(), yHeight, pos.getZ());
                float f = noise.GetNoise(x, (float) yHeight, z);
                if (f < 0 && worldgenlevel.getBlockState(pos2).is(BlockTags.DIRT)) {
                        worldgenlevel.setBlock(pos2, block, 3);
                } else if(worldgenlevel.getBlockState(pos2).is(BlockTags.DIRT)) {
                        worldgenlevel.setBlock(pos2, block2, 3);

                }

            }
        }
    }


    public static double distance(double x, double y, double z, double xRadius, double yRadius, double zRadius) {
        return Mth.square((double) x / (xRadius)) + Mth.square((double) y / (yRadius)) + Mth.square((double) z / (zRadius));
    }

    private static FastNoiseLite createNoise(long seed, float frequency) {
        FastNoiseLite noise = new FastNoiseLite((int) seed);
        noise.SetNoiseType(FastNoiseLite.NoiseType.OpenSimplex2S);
        noise.SetFrequency(frequency);
        return noise;
    }
}