package com.peeko32213.unusualprehistory.common.world.structure.processor;

import com.mojang.serialization.Codec;
import com.peeko32213.unusualprehistory.core.registry.UPBlocks;
import com.peeko32213.unusualprehistory.core.registry.world.UPStructureProcessors;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.jetbrains.annotations.Nullable;

public class UndergroundDigSiteProcessor extends StructureProcessor {

    private static final UndergroundDigSiteProcessor INSTANCE = new UndergroundDigSiteProcessor();

    public static final Codec<UndergroundDigSiteProcessor> CODEC = Codec.unit(() -> UndergroundDigSiteProcessor.INSTANCE);

    public UndergroundDigSiteProcessor() {}

    @Nullable
    public StructureTemplate.StructureBlockInfo processBlock(LevelReader levelReader, BlockPos blockPosUnused, BlockPos pos, StructureTemplate.StructureBlockInfo relativeInfo, StructureTemplate.StructureBlockInfo info, StructurePlaceSettings settings) {
        BlockState in = info.state();
        if(blockPosUnused.getY() < 0){
            if(in.is(Blocks.STONE)){
                return new StructureTemplate.StructureBlockInfo(info.pos(), Blocks.DEEPSLATE.defaultBlockState(), info.nbt());
            }
            if(in.is(Blocks.STONE_BRICK_WALL)){
                return new StructureTemplate.StructureBlockInfo(info.pos(), copyBlockStateProperties(in, Blocks.DEEPSLATE_BRICK_WALL.defaultBlockState()), info.nbt());
            }
            if(in.is(UPBlocks.STONE_FOSSIL.get())){
                return new StructureTemplate.StructureBlockInfo(info.pos(), UPBlocks.DEEPSLATE_FOSSIL.get().defaultBlockState(), info.nbt());
            }
        }
        return info;
    }

    private static BlockState copyBlockStateProperties(BlockState from, BlockState to){
        for (Property prop : from.getProperties()) {
            to = to.hasProperty(prop) ? to.setValue(prop, from.getValue(prop)) : to;
        }
        return to;
    }

    @Override
    protected StructureProcessorType<?> getType() {
        return UPStructureProcessors.UNDERGROUND_DIG_SITE.get();
    }
}
