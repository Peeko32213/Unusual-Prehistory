package com.peeko32213.unusualprehistory.common.world.feature.configs;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;

import java.util.List;

public class FossilSkeletonConfig implements FeatureConfiguration {

    public static final Codec<FossilSkeletonConfig> CODEC = RecordCodecBuilder.create((p_159816_) -> p_159816_.group(ResourceLocation.CODEC.listOf().fieldOf("fossil_structures").forGetter((p_159830_) -> p_159830_.fossilStructures), StructureProcessorType.LIST_CODEC.fieldOf("fossil_processors").forGetter((p_204759_) -> p_204759_.fossilProcessors), Codec.intRange(0, 7).fieldOf("max_empty_corners_allowed").forGetter((p_159818_) -> p_159818_.maxEmptyCornersAllowed)).apply(p_159816_, FossilSkeletonConfig::new));
    public final List<ResourceLocation> fossilStructures;
    public final Holder<StructureProcessorList> fossilProcessors;
    public final int maxEmptyCornersAllowed;

    public FossilSkeletonConfig(List<ResourceLocation> structure, Holder<StructureProcessorList> processor, int p_204755_) {
        this.fossilStructures = structure;
        this.fossilProcessors = processor;
        this.maxEmptyCornersAllowed = p_204755_;
    }
}
