package com.peeko32213.unusualprehistory.client.model.iceberg;

import com.peeko32213.unusualprehistory.common.entity.iceberg.IcebergMammoth;
import com.peeko32213.unusualprehistory.common.entity.msc.util.dino.EntityWorldSpawnable;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

import static com.peeko32213.unusualprehistory.UnusualPrehistory.prefix;

public class IcebergMammothModel extends AnimatedGeoModel<IcebergMammoth> {
    //MODELS
    private static final ResourceLocation MAMMOTH_MODEL_LOCATION = prefix("geo/mammoth.geo.json");
    
    //TEXTURES
    private static final ResourceLocation MAMMOTH_TEXTURE_LOCATION = prefix("textures/entity/mammoth.png");

    //ANIMATIONS
    private static final ResourceLocation MAMMOTH_ANIMATION_LOCATION = prefix("animations/mammoth.animation.json");


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
