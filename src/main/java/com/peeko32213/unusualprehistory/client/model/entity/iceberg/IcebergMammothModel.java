package com.peeko32213.unusualprehistory.client.model.entity.iceberg;

import com.peeko32213.unusualprehistory.common.entity.custom.iceberg.IcebergMammoth;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

import static com.peeko32213.unusualprehistory.UnusualPrehistory.modPrefix;

public class IcebergMammothModel extends GeoModel<IcebergMammoth> {
    //MODELS
    private static final ResourceLocation MAMMOTH_MODEL_LOCATION = modPrefix("geo/mammoth/mammoth.geo.json");
    
    //TEXTURES
    private static final ResourceLocation MAMMOTH_TEXTURE_LOCATION = modPrefix("textures/entity/mammoth/mammoth_frozen.png");

    //ANIMATIONS
    private static final ResourceLocation MAMMOTH_ANIMATION_LOCATION = modPrefix("animations/mammoth/mammoth.animation.json");


    @Override
    public ResourceLocation getModelResource(IcebergMammoth object) {
        return MAMMOTH_MODEL_LOCATION;
    }

    @Override
    public ResourceLocation getTextureResource(IcebergMammoth object) {
        return MAMMOTH_TEXTURE_LOCATION;
    }

    @Override
    public ResourceLocation getAnimationResource(IcebergMammoth object) {
        return MAMMOTH_ANIMATION_LOCATION;
    }
}
