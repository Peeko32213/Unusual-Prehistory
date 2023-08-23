package com.peeko32213.unusualprehistory.common.world.feature;

import com.mojang.serialization.Codec;
import com.peeko32213.unusualprehistory.core.registry.UPBlocks;
import com.peeko32213.unusualprehistory.core.registry.util.FastNoiseLite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import static net.minecraft.world.level.block.MultifaceBlock.getFaceProperty;

public class TarPitFeature extends Feature<NoneFeatureConfiguration> {
    public TarPitFeature(Codec<NoneFeatureConfiguration> pCodec) {
        super(pCodec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> pContext) {
        WorldGenLevel worldGenLevel = pContext.level();
        BlockPos blockPos = pContext.origin();

        RandomSource random = pContext.random();
        FastNoiseLite noiseTar = createNoise(worldGenLevel.getSeed() + random.nextLong(), 0.06F);


        createTarPitFeature(worldGenLevel, blockPos, random, 17, 15, noiseTar, true);
        return true;
    }


    private static void createTarPitFeature(WorldGenLevel worldgenlevel, BlockPos origin, RandomSource random, int radius, int height, FastNoiseLite noise, boolean finalSection) {
        // Define additional block states for the iceberg section
        BlockState block = Blocks.STONE.defaultBlockState();
        BlockState block2 = UPBlocks.TAR.get().defaultBlockState();
        BlockState block3 = Blocks.MUD.defaultBlockState();
        BlockState block4 = UPBlocks.SPLATTERED_TAR.get().defaultBlockState().setValue(getFaceProperty(Direction.DOWN), Boolean.valueOf(true));
        BlockState block5 = UPBlocks.STONE_TAR_FOSSIL.get().defaultBlockState();
        BlockState block6 = Blocks.STONE.defaultBlockState();
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

                    if (distance < 1 ) {
                        if(f < -0.1 && f > -0.8 && distance < 0.8 && pos.getY() < (origin.getY() - 1)){
                            worldgenlevel.setBlock(pos, block2, 3);



                        } else if ( (f >= -0.1 && f < 0.1 || f <= -0.8) && distance < 0.8 && pos.getY() < (origin.getY() - 1)) {
                            worldgenlevel.setBlock(pos, block, 3);

                        } else if(f < 0) {
                            if(pos.getY() < origin.getY()) {
                                if ((f >= -0.6 && f < -0.55) || (f >= -0.85 && f < -0.8)) {
                                    worldgenlevel.setBlock(pos, block5, 3);
                                } else if (pos.getY() < (origin.getY() - 4)) {
                                    worldgenlevel.setBlock(pos, block, 3);
                                } else {
                                    worldgenlevel.setBlock(pos, block3, 3);
                                }

                                if (f < -0.1 && f > -0.8 && distance < 0.8 && pos.getY() >= (origin.getY() - 1)) {
                                    worldgenlevel.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
                                }
                            }
                            if(f < -0.1 && f > -0.8 && pos.getY() >= origin.getY() && pos.getY() < origin.getY() + 1 && random.nextFloat() < 0.5F && worldgenlevel.getBlockState(pos.below()).getMaterial().isSolid()){
                                worldgenlevel.setBlock(pos, block4, 3);
                            }
                        }
                    }

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
