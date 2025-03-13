package com.peeko32213.unusualprehistory.common.data.entity;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.peeko32213.unusualprehistory.common.data.codec.NullableFieldCodec;
import com.peeko32213.unusualprehistory.common.data.entity.attribute.AttributesModifier;
import com.peeko32213.unusualprehistory.common.data.entity.generic.EntitySpawnData;
import com.peeko32213.unusualprehistory.common.data.entity.generic.GenericEntityData;
import net.minecraft.util.ExtraCodecs;

public class VariantData {

    public static final Codec<VariantData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ExtraCodecs.POSITIVE_INT.fieldOf("variant_id").forGetter(VariantData::getVariantId),
            NullableFieldCodec.makeDefaultableField("variant_resource_locations", EntityResourceLocationData.CODEC, EntityResourceLocationData.getDefaultInstance()).forGetter(VariantData::getEntityResourceLocationData),
            NullableFieldCodec.makeDefaultableField("attributes", AttributesModifier.CODEC, AttributesModifier.getDefaultInstance()).forGetter(VariantData::getAttributesModifiers),
            EntityGoals.CODEC.fieldOf("goals").forGetter(VariantData::getEntityGoals),
            NullableFieldCodec.makeDefaultableField("generic_entity_definitions", GenericEntityData.CODEC, GenericEntityData.getDefaulInstance()).forGetter(VariantData::getGenericEntityData)
    ).apply(instance, VariantData::new));


    private final int variantId;
    private final EntityResourceLocationData entityResourceLocationData;
    private final AttributesModifier attributesModifiers;
    private final EntityGoals entityGoals;
    private final GenericEntityData genericEntityData;

    public VariantData(int variantId, EntityResourceLocationData entityResourceLocationData, AttributesModifier attributesModifiers, EntityGoals entityGoals, GenericEntityData genericEntityData) {
        this.variantId = variantId;
        this.entityResourceLocationData = entityResourceLocationData;
        this.attributesModifiers = attributesModifiers;
        this.entityGoals = entityGoals;
        this.genericEntityData = genericEntityData;
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

    public EntityGoals getEntityGoals() {
        return entityGoals;
    }

    public GenericEntityData getGenericEntityData() {
        return genericEntityData;
    }


    public static VariantData getDefaultInstance() {
        return new VariantData(0, EntityResourceLocationData.getDefaultInstance(), AttributesModifier.getDefaultInstance(), EntityGoals.getDefaultInstance(), GenericEntityData.getDefaulInstance());
    }
}

