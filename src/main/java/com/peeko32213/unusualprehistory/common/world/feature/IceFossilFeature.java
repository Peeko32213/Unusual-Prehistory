package com.peeko32213.unusualprehistory.common.world.feature;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.peeko32213.unusualprehistory.common.entity.msc.util.dino.EntityWorldSpawnable;
import com.peeko32213.unusualprehistory.core.registry.UPBlocks;
import com.peeko32213.unusualprehistory.core.registry.UPEntities;
import com.peeko32213.unusualprehistory.core.registry.util.FastNoiseLite;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.phys.Vec3;
import org.slf4j.Logger;

public class IceFossilFeature extends Feature<NoneFeatureConfiguration> {
    public IceFossilFeature(Codec<NoneFeatureConfiguration> pCodec) {
        super(pCodec);
    }

    private static final Logger LOGGER = LogUtils.getLogger();
    private static final BlockState END_STONE = Blocks.END_STONE.defaultBlockState();
    private static final BlockState STONE = Blocks.STONE.defaultBlockState();
    private static final BlockState DIRT = Blocks.DIRT.defaultBlockState();
    private static final BlockState COARSE_DIRT = Blocks.COARSE_DIRT.defaultBlockState();
    private static final BlockState SANDSTONE = Blocks.SANDSTONE.defaultBlockState();
    private static final BlockState SAND = Blocks.SAND.defaultBlockState();
    private static final BlockState TAR = UPBlocks.TAR.get().defaultBlockState();
    private static final BlockState AIR = Blocks.AIR.defaultBlockState();
    private static final BlockState WATER = Blocks.WATER.defaultBlockState();
    private static final BlockState WW = Blocks.WHITE_WOOL.defaultBlockState();
    private static final BlockState BW = Blocks.BROWN_WOOL.defaultBlockState();
    private static final BlockState RW = Blocks.RED_WOOL.defaultBlockState();
    private static final BlockState GW = Blocks.GREEN_WOOL.defaultBlockState();
    private static final BlockState BBW = Blocks.BLUE_WOOL.defaultBlockState();
    private static final BlockState YW = Blocks.YELLOW_WOOL.defaultBlockState();
    private static final BlockState OW = Blocks.ORANGE_WOOL.defaultBlockState();

    private static final BlockPos.MutableBlockPos POS = new BlockPos.MutableBlockPos();

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> pContext) {

        WorldGenLevel worldGenLevel = pContext.level();
        BlockPos blockPos = pContext.origin();
        BlockState firstState = worldGenLevel.getBlockState(blockPos);
        //if(firstState.getFluidState().is(FluidTags.WATER) || firstState.getFluidState().is(FluidTags.LAVA) || firstState.is(Blocks.WATER) || firstState.is(Blocks.LAVA)){
        //    return false;
        //}


        RandomSource random = pContext.random();
        FastNoiseLite noiseIceBerg = createNoise(worldGenLevel.getSeed() + random.nextLong(), 0.11F);
        FastNoiseLite noiseBlueIce = createNoise(worldGenLevel.getSeed() + random.nextLong(), 0.11F);
        FastNoiseLite noiseFossil = createNoise(worldGenLevel.getSeed() + random.nextLong(), 0.1F);

        createIceBerg(worldGenLevel, random, blockPos, noiseBlueIce);
        addFossil(worldGenLevel, random, blockPos, noiseFossil);
        return true;
    }


    public static void createIceBerg(WorldGenLevel worldgenlevel, RandomSource rand, BlockPos origin, FastNoiseLite noise) {
        int radius;
        int height;

        BlockState block = Blocks.PACKED_ICE.defaultBlockState();
        boolean changeDenseIce = rand.nextBoolean();

        if(changeDenseIce)
        {
            block = UPBlocks.PERMAFROST.get().defaultBlockState();
        }


        BlockState block2 = Blocks.ICE.defaultBlockState();
        BlockState block3 = Blocks.AIR.defaultBlockState();
        origin = origin.offset(0, -4, 0); // Offset the origin once instead of multiple times

        // Define the ice berg configurations with corresponding radius and height
        int[][] icebergConfigs = {
                {8, 35},
                {5, 40},
                {12, 25},
                {14, 15},
                {15, 15}
        };

        for (int i = 0; i < icebergConfigs.length; i++) {
            radius = icebergConfigs[i][0];
            height = icebergConfigs[i][1];
            boolean finalSection = false;
            if(i + 1 >= icebergConfigs.length){
                finalSection = true;
            }

            createIceBergSection(worldgenlevel, origin, radius, height, block, block2, block3, noise, finalSection);
        }

    }

    private static void createIceBergSection(WorldGenLevel worldgenlevel, BlockPos origin, int radius, int height, BlockState block, BlockState block2, BlockState block3, FastNoiseLite noise, boolean finalSection) {
        // Define additional block states for the iceberg section
        BlockState block4 = UPBlocks.PERMAFROST_FOSSIL.get().defaultBlockState();
        BlockState block5 = Blocks.SNOW_BLOCK.defaultBlockState();
        BlockState block6 = Blocks.WATER.defaultBlockState();
        int heightLower = 0;

        // If this is the final section of the iceberg, adjust the heightLower to a negative value to begin building from below the origin.
        if(finalSection) {
            heightLower = -height;
        }

        for (int x = -radius; x < radius; x++) {
            for (int y = heightLower; y < height; y++) {
                for (int z = -radius; z < radius; z++) {
                    BlockPos pos = origin.offset(x, y, z);

                    double distance = distance(x, y, z, radius, height, radius);
                    float f = noise.GetNoise(x, (float) y, z);

                    if (distance < 1) {

                        // Depending on the noise value, set different blocks at this position
                        if (f < 0 && f > -0.3) {
                            // If the noise value is within a specific range, place block4 (UPBlocks.STONE_FOSSIL) at this position
                            // The range is from -0.3 to 0, with a small sub-range from -0.25 to -0.248 where block4 is placed.
                            if(f > -0.25 && f < -0.245) {
                                worldgenlevel.setBlock(pos, block4, 3);
                            } else {
                                // Otherwise, place the specified block at this position (block parameter).
                                worldgenlevel.setBlock(pos, block, 3);
                            }
                        } else if (f <= 0.4 && f > 0) {
                            // If the noise value is between 0 and 0.4, place block2 at this position.
                            worldgenlevel.setBlock(pos, block2, 3);
                        } else if(f > 0.4 && f < 0.9 && (pos.getY() > origin.getY() + 15)) {
                            // If the noise value is between 0.4 and 0.9 and the Y-coordinate is higher than 15 blocks above the origin, place block5 (Blocks.SNOW_BLOCK) at this position.
                            worldgenlevel.setBlock(pos, block5, 3);
                        } else if(pos.getY() < 63) {
                            // If the Y-coordinate is below 63, place block6 (Blocks.WATER) at this position.
                            worldgenlevel.setBlock(pos, block6, 3);
                        } else {
                            // For any other cases, place block3 at this position (default block).
                            worldgenlevel.setBlock(pos, block3, 3);
                        }
                    }
                }
            }
        }
    }



    public static void addFossil(WorldGenLevel worldgenlevel, RandomSource rand, BlockPos origin, FastNoiseLite noise){
        EntityWorldSpawnable entityWorldSpawnable = UPEntities.ICEBERG_SMILODON.get().create(worldgenlevel.getLevel());

        if(rand.nextBoolean())
        {
            entityWorldSpawnable = UPEntities.ICEBERG_MAMMOTH.get().create(worldgenlevel.getLevel());
        }


        int height = (int) ((int) entityWorldSpawnable.getBbHeight() * 3);
        int radius = (int) ((int) entityWorldSpawnable.getBbWidth()) * 2;


        BlockState block = Blocks.ICE.defaultBlockState();
        BlockState block2 = Blocks.AIR.defaultBlockState();
        origin = origin.offset(0,(-radius/2)-1,0);


        for (int x = -radius; x < radius; x++) {
            for (int y = -height; y < height; y++) {
                for (int z = -radius; z < radius; z++) {
                    BlockPos pos = origin.offset(x, y, z);
                    double distance = distance(x, y, z, radius, height, radius);
                    float f = noise.GetNoise(x, (float) y, z);
                    if (distance < 1) {
                        if (f < 0.5) {
                            worldgenlevel.setBlock(pos, block, 3);
                        }
                        else {
                            worldgenlevel.setBlock(pos, block2, 3);
                        }
                    }
                }
            }
        }

        float randomRot = rand.nextInt(360);
        entityWorldSpawnable.setYRot(randomRot);
        entityWorldSpawnable.setPos(new Vec3(origin.getX(), origin.getY(), origin.getZ()));
        worldgenlevel.addFreshEntity(entityWorldSpawnable);
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
