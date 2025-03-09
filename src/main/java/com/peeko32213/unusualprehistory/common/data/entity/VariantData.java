package com.peeko32213.unusualprehistory.common.data.entity;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.peeko32213.unusualprehistory.common.data.codec.NullableFieldCodec;
import com.peeko32213.unusualprehistory.common.data.entity.attribute.AttributesModifier;
import com.scouter.goalsmith.data.AttributesAdditions;
import net.minecraft.util.ExtraCodecs;

import java.util.Collections;
import java.util.List;
import java.util.jar.Attributes;

public class VariantData {

    public static final Codec<VariantData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ExtraCodecs.POSITIVE_INT.fieldOf("variant_id").forGetter(VariantData::getVariantId),
            NullableFieldCodec.makeDefaultableField("variant_resource_locations", EntityResourceLocationData.CODEC, EntityResourceLocationData.getDefaultInstance()).forGetter(VariantData::getEntityResourceLocationData),
            NullableFieldCodec.makeDefaultableField("attributes", AttributesModifier.CODEC, AttributesModifier.getDefaultInstance()).forGetter(VariantData::getAttributesModifiers)
    ).apply(instance, VariantData::new));


    private final int variantId;
    private final EntityResourceLocationData entityResourceLocationData;
    private final AttributesModifier attributesModifiers;

    public VariantData(int variantId, EntityResourceLocationData entityResourceLocationData, AttributesModifier attributesModifiers) {
        this.variantId = variantId;
        this.entityResourceLocationData = entityResourceLocationData;
        this.attributesModifiers = attributesModifiers;
    }

    public int getVariantId() {
        return variantId;
    }

    public EntityResourceLocationData getEntityResourceLocationData() {
        return entityResourceLocationData;
    }

    public AttributesModifier getAttributesModifiers() {
        return attributesModifiers;
    }
}

