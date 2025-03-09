package com.peeko32213.unusualprehistory.common.data.entity;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.peeko32213.unusualprehistory.common.data.codec.NullableFieldCodec;
import com.peeko32213.unusualprehistory.common.data.entity.attribute.AttributeModifier;
import com.peeko32213.unusualprehistory.common.data.entity.attribute.AttributesModifier;
import net.minecraft.util.random.WeightedRandomList;

import java.util.Collections;
import java.util.List;

public class PrehistoricEntityData {

    public static final Codec<PrehistoricEntityData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            //NullableFieldCodec.makeDefaultableField("attributes", AttributesModifier.CODEC, AttributesModifier.getDefaultInstance()).forGetter(PrehistoricEntityData::getAttributeModifiers),
            WeightedRandomList.codec(WeightedVariantData.CODEC).fieldOf("variants").forGetter(PrehistoricEntityData::getVariantDataWeightedRandomList)
    ).apply(instance, PrehistoricEntityData::new));



    private final WeightedRandomList<WeightedVariantData> variantDataWeightedRandomList;

    public PrehistoricEntityData(WeightedRandomList<WeightedVariantData> variantDataWeightedRandomList) {
        this.variantDataWeightedRandomList = variantDataWeightedRandomList;
    }


    public WeightedRandomList<WeightedVariantData> getVariantDataWeightedRandomList() {
        return variantDataWeightedRandomList;
    }
}
