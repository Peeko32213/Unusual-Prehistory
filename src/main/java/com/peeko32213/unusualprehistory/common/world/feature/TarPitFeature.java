package com.peeko32213.unusualprehistory.common.world.feature;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.peeko32213.unusualprehistory.core.registry.UPBlocks;
import com.peeko32213.unusualprehistory.core.registry.UPTags;
import com.peeko32213.unusualprehistory.core.registry.util.FastNoiseLite;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Material;
import org.slf4j.Logger;

public class TarPitFeature extends Feature<NoneFeatureConfiguration> {
    public TarPitFeature(Codec<NoneFeatureConfiguration> pCodec) {
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
        if(firstState.getFluidState().is(FluidTags.WATER) || firstState.getFluidState().is(FluidTags.LAVA) || firstState.is(Blocks.WATER) || firstState.is(Blocks.LAVA)){
            return false;
        }

        RandomSource random = pContext.random();
        FastNoiseLite noise = createNoise(worldGenLevel.getSeed() + random.nextLong(), random.nextFloat());
        double radius = random.nextInt(8, 12);
        double depth = radius * 0.5 * randRange(1, 2.4, random);
        int dist = Mth.floor(radius);
        int dist2 = Mth.floor(radius * 1.5);
        int bott = Mth.floor(depth);

        blockPos = getPosOnSurfaceWG(worldGenLevel, blockPos);
        if (blockPos.getY() < 10) return false;

        int waterLevel = blockPos.getY();

        BlockPos pos = getPosOnSurfaceRaycast(worldGenLevel, blockPos.north(dist).above(10), 20);
        if (Math.abs(blockPos.getY() - pos.getY()) > 10) return false;
        waterLevel = Math.min(pos.getY(), waterLevel);

        pos = getPosOnSurfaceRaycast(worldGenLevel, blockPos.south(dist).above(10), 20);
        if (Math.abs(blockPos.getY() - pos.getY()) > 10) return false;
        waterLevel = Math.min(pos.getY(), waterLevel);

        pos = getPosOnSurfaceRaycast(worldGenLevel, blockPos.east(dist).above(10), 20);
        if (Math.abs(blockPos.getY() - pos.getY()) > 10) return false;
        waterLevel = Math.min(pos.getY(), waterLevel);

        pos = getPosOnSurfaceRaycast(worldGenLevel, blockPos.west(dist).above(10), 20);
        if (Math.abs(blockPos.getY() - pos.getY()) > 10) return false;
        waterLevel = Math.min(pos.getY(), waterLevel);
        BlockState state;

        waterLevel = waterLevel + 15;
        int minX = blockPos.getX() - dist2;
        int maxX = blockPos.getX() + dist2;
        int minZ = blockPos.getZ() - dist2;
        int maxZ = blockPos.getZ() + dist2;
        int maskMinX = minX - 1;
        int maskMinZ = minZ - 1;

        boolean[][] mask = new boolean[maxX - minX + 3][maxZ - minZ + 3];
        for (int x = minX; x <= maxX; x++) {
            POS.setX(x);
            int mx = x - maskMinX;
            for (int z = minZ; z <= maxZ; z++) {
                POS.setZ(z);
                int mz = z - maskMinZ;
                if (!mask[mx][mz]) {
                    for (int y = waterLevel + 1; y <= waterLevel + 20; y++) {
                        POS.setY(y);
                        FluidState fluid = worldGenLevel.getFluidState(POS);
                        if (!fluid.isEmpty()) {
                            for (int i = -1; i < 2; i++) {
                                int px = mx + i;
                                for (int j = -1; j < 2; j++) {
                                    int pz = mz + j;
                                    mask[px][pz] = true;
                                }
                            }
                            break;
                        }
                    }
                }
            }
        }

        for (int x = minX; x <= maxX; x++) {
            POS.setX(x);
            int x2 = x - blockPos.getX();
            x2 *= x2;
            int mx = x - maskMinX;
            for (int z = minZ; z <= maxZ; z++) {
                POS.setZ(z);
                int z2 = z - blockPos.getZ();
                z2 *= z2;
                int mz = z - maskMinZ;
                if (!mask[mx][mz]) {
                    double size = 1;
                    for (int y = blockPos.getY(); y <= blockPos.getY() + 20; y++) {
                        POS.setY(y);
                        double add = y - blockPos.getY();
                        if (add > 5) {
                            size *= 0.8;
                            add = 5;
                        }
                        double r = (add * 1.8 + radius * (noise.GetNoise((float) (x * 0.2), (float) (y * 0.2), (float) (z * 0.2)) * 0.25 + 0.75)) - 1.0 / size;
                        if (r > 0) {
                            r *= r;
                            if (x2 + z2 <= r) {
                                state = worldGenLevel.getBlockState(POS);
                                //Some area outside
                                if (state.is(UPTags.TAR_PIT_REPLACEABLE)) {
                                    setWithoutUpdate(worldGenLevel, POS, Blocks.DIRT.defaultBlockState());
                                }
                                pos = POS.below();
                                if (worldGenLevel.getBlockState(pos).is(UPTags.TAR_PIT_REPLACEABLE)) {
                                    state = AIR;
                                    if (y > waterLevel + 1)
                                        setWithoutUpdate(worldGenLevel, pos, state);
                                    else if (y > waterLevel)
                                        setWithoutUpdate(worldGenLevel, pos, state);
                                    else
                                        setWithoutUpdate(worldGenLevel, pos, state);
                                }
                            }
                        } else {
                            break;
                        }
                    }
                }
            }
        }

        double aspect = ((double) radius / (double) depth);

        for (int x = blockPos.getX() - dist; x <= blockPos.getX() + dist; x++) {
            POS.setX(x);
            int x2 = x - blockPos.getX();
            x2 *= x2;
            int mx = x - maskMinX;
            for (int z = blockPos.getZ() - dist; z <= blockPos.getZ() + dist; z++) {
                POS.setZ(z);
                int z2 = z - blockPos.getZ();
                z2 *= z2;
                int mz = z - maskMinZ;
                if (!mask[mx][mz]) {
                    for (int y = blockPos.getY() - bott; y < blockPos.getY(); y++) {
                        POS.setY(y);
                        double y2 = (double) (y - blockPos.getY()) * aspect;
                        y2 *= y2;
                        double r = radius * (noise.GetNoise((float) (x * 0.2), (float) (y * 0.2), (float) (z * 0.2)) * 0.25 + 0.75);
                        double rb = r * 1.2;
                        r *= r;
                        rb *= rb;
                        if (y2 + x2 + z2 <= r) {
                            state = worldGenLevel.getBlockState(POS);
                            if (canReplace(state)) {
                                state = worldGenLevel.getBlockState(POS.above());
                                state = canReplace(state) ? (y < waterLevel ? TAR : Blocks.SANDSTONE.defaultBlockState()) : state;
                                setWithoutUpdate(worldGenLevel, POS, state);
                            }
                            pos = POS.below();
                            if (worldGenLevel.getBlockState(pos).is(UPTags.TAR_PIT_REPLACEABLE)) {
                                setWithoutUpdate(worldGenLevel, pos, TAR);
                            }
                            pos = POS.above();
                            while (canReplace(state = worldGenLevel.getBlockState(pos)) && !state.isAir() && state.getFluidState().isEmpty()) {
                                setWithoutUpdate(worldGenLevel, pos, pos.getY() < waterLevel ? Blocks.COARSE_DIRT.defaultBlockState() : Blocks.GRAVEL.defaultBlockState());
                                pos = pos.above();
                            }
                        }
                        // Make border
                        else if (y < waterLevel && y2 + x2 + z2 <= rb) {
                            if (isAirBlock(POS.above(), worldGenLevel)) {
                                state = COARSE_DIRT;
                                setWithoutUpdate(worldGenLevel, POS, random.nextBoolean() ? state : DIRT);
                                setWithoutUpdate(worldGenLevel, POS.below(), SANDSTONE);
                            } else {


                                setWithoutUpdate(worldGenLevel, POS, SANDSTONE);

                                setWithoutUpdate(worldGenLevel, POS.below(), SANDSTONE);
                            }
                        }
                    }
                }
            }
        }

        // BlockHelper.fixBlocks(world, new BlockPos(minX - 2, waterLevel - 2, minZ - 2), new BlockPos(maxX + 2, blockPos.getY() + 20, maxZ + 2));


        return true;
    }


    public static void setWithoutUpdate(WorldGenLevel world, BlockPos pos, BlockState state) {
        world.setBlock(pos, state, 3);
    }


    public static double randRange(double min, double max, RandomSource random) {
        return min + random.nextDouble() * (max - min);
    }


    public static BlockPos getPosOnSurfaceWG(WorldGenLevel world, BlockPos pos) {
        return world.getHeightmapPos(Heightmap.Types.WORLD_SURFACE_WG, pos);
    }


    public static BlockPos getPosOnSurfaceRaycast(WorldGenLevel world, BlockPos pos, int dist) {
        int h = downRay(world, pos, dist);
        return pos.below(h);
    }


    public static int downRay(WorldGenLevel world, BlockPos pos, int maxDist) {
        int length = 0;
        for (int j = 1; j < maxDist && isAirBlock(pos, world); j++)
            length++;
        return length;
    }

    public static boolean isAirBlock(BlockPos pos, WorldGenLevel world) {
        return world.getBlockState(pos).isAir();
    }

    private boolean canReplace(BlockState state) {
        return state.getMaterial().isReplaceable()
                || state.getMaterial().equals(Material.PLANT)
                || state.getMaterial().equals(Material.WATER_PLANT)
                || state.is(Blocks.END_STONE);
    }

    private static FastNoiseLite createNoise(long seed, float frequency) {
        FastNoiseLite noise = new FastNoiseLite((int) seed);
        noise.SetNoiseType(FastNoiseLite.NoiseType.OpenSimplex2S);
        noise.SetFrequency(frequency);
        return noise;
    }

}
