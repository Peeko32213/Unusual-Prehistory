// todo: finish this later
//package com.peeko32213.unusualprehistory.common.world.feature;
//
//import com.mojang.serialization.Codec;
//import com.peeko32213.unusualprehistory.common.world.feature.configs.FossilSkeletonConfig;
//import net.minecraft.core.BlockPos;
//import net.minecraft.core.Vec3i;
//import net.minecraft.util.RandomSource;
//import net.minecraft.world.level.ChunkPos;
//import net.minecraft.world.level.WorldGenLevel;
//import net.minecraft.world.level.block.Blocks;
//import net.minecraft.world.level.block.Mirror;
//import net.minecraft.world.level.block.Rotation;
//import net.minecraft.world.level.block.state.BlockState;
//import net.minecraft.world.level.levelgen.Heightmap;
//import net.minecraft.world.level.levelgen.feature.Feature;
//import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
//import net.minecraft.world.level.levelgen.structure.BoundingBox;
//import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
//import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
//import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
//import org.apache.commons.lang3.mutable.MutableInt;
//
//import java.util.List;
//import java.util.Objects;
//
//public class FossilSkeletonFeature extends Feature<FossilSkeletonConfig> {
//
//    public FossilSkeletonFeature(Codec<FossilSkeletonConfig> pCodec) {
//        super(pCodec);
//    }
//
//    public boolean place(FeaturePlaceContext<FossilSkeletonConfig> context) {
//        RandomSource random = context.random();
//        WorldGenLevel level = context.level();
//        BlockPos pos = context.origin();
//        Rotation rotation = Rotation.getRandom(random);
//        FossilSkeletonConfig config = context.config();
//        int size = random.nextInt(config.fossilStructures.size());
//        StructureTemplateManager manager = level.getLevel().getServer().getStructureManager();
//        StructureTemplate structure = manager.getOrCreate(config.fossilStructures.get(size));
//        ChunkPos chunkPos = new ChunkPos(pos);
//        BoundingBox boundingBox = new BoundingBox(chunkPos.getMinBlockX() - 16, level.getMinBuildHeight(), chunkPos.getMinBlockZ() - 16, chunkPos.getMaxBlockX() + 16, level.getMaxBuildHeight(), chunkPos.getMaxBlockZ() + 16);
//        StructurePlaceSettings settings = (new StructurePlaceSettings()).setRotation(rotation).setBoundingBox(boundingBox).setRandom(random);
//        Vec3i vec3i = structure.getSize(rotation);
//        BlockPos blockPos = pos.offset(-vec3i.getX() / 2, 0, -vec3i.getZ() / 2);
//        int posY = pos.getY();
//
//        for(int $$16 = 0; $$16 < vec3i.getX(); ++$$16) {
//            for(int $$17 = 0; $$17 < vec3i.getZ(); ++$$17) {
//                posY = Math.min(posY, level.getHeight(Heightmap.Types.OCEAN_FLOOR_WG, blockPos.getX() + $$16, blockPos.getZ() + $$17));
//            }
//        }
//
//        int $$18 = Math.max(posY - 15 - random.nextInt(10), level.getMinBuildHeight() + 10);
//        BlockPos $$19 = structure.getZeroPositionWithTransform(blockPos.atY($$18), Mirror.NONE, rotation);
//        if (countEmptyCorners(level, structure.getBoundingBox(settings, $$19)) > config.maxEmptyCornersAllowed) {
//            return false;
//        } else {
//            settings.clearProcessors();
//            List var10000 = config.fossilProcessors.value().list();
//            Objects.requireNonNull(settings);
//            var10000.forEach(settings::addProcessor);
//            structure.placeInWorld(level, $$19, $$19, settings, random, 4);
//            settings.clearProcessors();
//            return true;
//        }
//    }
//
//    private static int countEmptyCorners(WorldGenLevel pLevel, BoundingBox pBoundingBox) {
//        MutableInt $$2 = new MutableInt(0);
//        pBoundingBox.forAllCorners((p_284921_) -> {
//            BlockState $$3 = pLevel.getBlockState(p_284921_);
//            if ($$3.isAir() || $$3.is(Blocks.LAVA) || $$3.is(Blocks.WATER)) {
//                $$2.add(1);
//            }
//
//        });
//        return $$2.getValue();
//    }
//}
