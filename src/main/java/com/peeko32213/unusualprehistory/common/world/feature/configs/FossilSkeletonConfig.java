package com.peeko32213.unusualprehistory.common.world.feature.configs;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

import java.util.List;

public class FossilSkeletonConfig implements FeatureConfiguration {

    public static final Codec<FossilSkeletonConfig> CODEC = RecordCodecBuilder.create((configurationInstance) -> configurationInstance.group(ResourceLocation.CODEC.listOf().fieldOf("structures").forGetter((p_159830_) -> p_159830_.structures), Codec.INT.fieldOf("sink_by").forGetter((otherConfig) -> otherConfig.sinkBy)).apply(configurationInstance, FossilSkeletonConfig::new));
    public final List<ResourceLocation> structures;
    public final int sinkBy;

    public FossilSkeletonConfig(List<ResourceLocation> structures, int sinkBy) {
        if (structures.isEmpty()) {
            throw new IllegalArgumentException("structure lists need at least one entry");
        } else {
            this.structures = structures;
        }
        this.sinkBy = sinkBy;
    }
}
