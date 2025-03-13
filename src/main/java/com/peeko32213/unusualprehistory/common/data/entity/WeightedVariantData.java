package com.peeko32213.unusualprehistory.common.data.entity;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.random.Weight;
import net.minecraft.util.random.WeightedEntry;

public class WeightedVariantData extends WeightedEntry.IntrusiveBase{


    public static final Codec<WeightedVariantData> CODEC = RecordCodecBuilder.<WeightedVariantData>create((instance) ->
            instance.group(
                    Weight.CODEC.fieldOf("weight").forGetter(WeightedEntry.IntrusiveBase::getWeight),
                    VariantData.CODEC.fieldOf("variant").forGetter(WeightedVariantData::getVariantData)
            ).apply(instance, WeightedVariantData::new));


    private final VariantData variantData;

    public WeightedVariantData(int pWeight, VariantData variantData) {
        this(Weight.of(pWeight), variantData);
    }

    public WeightedVariantData(Weight pWeight, VariantData variantData) {
        super(pWeight);
        this.variantData = variantData;
    }


    public VariantData getVariantData() {
        return variantData;
    }

    public int getWeightAsInt() {
        return getWeight().asInt();
    }
}
