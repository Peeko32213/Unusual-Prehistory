package com.peeko32213.unusualprehistory.common.data.entity.generic;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class EntityDimensionData {
    private final float width;
    private final float height;

    public EntityDimensionData(float width, float height) {
        this.width = width;
        this.height = height;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public static final Codec<EntityDimensionData> CODEC = RecordCodecBuilder.create(instance -> instance
        .group(
            Codec.FLOAT.fieldOf("width").forGetter(EntityDimensionData::getWidth),
            Codec.FLOAT.fieldOf("height").forGetter(EntityDimensionData::getHeight)
        ).apply(instance, EntityDimensionData::new)
    );

    public static EntityDimensionData getDefaultInstance () {
        return new EntityDimensionData(1,1);
    }
}