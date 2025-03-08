package com.peeko32213.unusualprehistory.client.model.iceberg;

import com.peeko32213.unusualprehistory.common.entity.custom.iceberg.IcebergSmilodon;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

import static com.peeko32213.unusualprehistory.UnusualPrehistory.prefix;

public class IcebergSmilodonModel extends GeoModel<IcebergSmilodon> {
    //MODELS
    private static final ResourceLocation SMILODON_MODEL_LOCATION = prefix("geo/smilodon.geo.json");

    //TEXTURES
    private static final ResourceLocation SMILODON_TEXTURE_LOCATION = prefix("textures/entity/smilodon_frozen.png");
    //ANIMATIONS

    private static final ResourceLocation SMILODON_ANIMATION_LOCATION = prefix("animations/smilodon.animation.json");

    @Override
    public ResourceLocation getModelResource(IcebergSmilodon object) {
        return SMILODON_MODEL_LOCATION;
    }

    @Override
    public ResourceLocation getTextureResource(IcebergSmilodon object) {
        return SMILODON_TEXTURE_LOCATION;
    }

    @Override
    public ResourceLocation getAnimationResource(IcebergSmilodon object) {
        return SMILODON_ANIMATION_LOCATION;
    }
}