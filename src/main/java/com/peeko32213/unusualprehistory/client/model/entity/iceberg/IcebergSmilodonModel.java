package com.peeko32213.unusualprehistory.client.model.entity.iceberg;

import com.peeko32213.unusualprehistory.common.entity.custom.iceberg.IcebergSmilodon;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

import static com.peeko32213.unusualprehistory.UnusualPrehistory.modPrefix;

public class IcebergSmilodonModel extends GeoModel<IcebergSmilodon> {
    //MODELS
    private static final ResourceLocation SMILODON_MODEL_LOCATION = modPrefix("geo/smilodon.geo.json");

    //TEXTURES
    private static final ResourceLocation SMILODON_TEXTURE_LOCATION = modPrefix("textures/entity/smilodon/smilodon_frozen.png");
    //ANIMATIONS

    private static final ResourceLocation SMILODON_ANIMATION_LOCATION = modPrefix("animations/smilodon.animation.json");

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