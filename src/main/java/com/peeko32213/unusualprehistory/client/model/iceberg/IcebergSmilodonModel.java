package com.peeko32213.unusualprehistory.client.model.iceberg;

import com.peeko32213.unusualprehistory.common.entity.iceberg.IcebergSmilodon;
import com.peeko32213.unusualprehistory.common.entity.msc.util.dino.EntityWorldSpawnable;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

import static com.peeko32213.unusualprehistory.UnusualPrehistory.prefix;

public class IcebergSmilodonModel extends AnimatedGeoModel<IcebergSmilodon> {
    //MODELS
    private static final ResourceLocation MAMMOTH_MODEL_LOCATION = prefix("geo/mammoth.geo.json");
    private static final ResourceLocation SMILODON_MODEL_LOCATION = prefix("geo/smilodon.geo.json");
    private static final ResourceLocation ERYON_MODEL_LOCATION = prefix("geo/eryon.geo.json");
    //TEXTURES
    private static final ResourceLocation MAMMOTH_TEXTURE_LOCATION = prefix("textures/entity/mammoth.png");
    private static final ResourceLocation SMILODON_TEXTURE_LOCATION = prefix("textures/entity/smilodon.png");
    private static final ResourceLocation ERYON_TEXTURE_LOCATION = prefix("textures/entity/eryon.png");
    //ANIMATIONS
    private static final ResourceLocation MAMMOTH_ANIMATION_LOCATION = prefix("animations/mammoth.animation.json");
    private static final ResourceLocation SMILODON_ANIMATION_LOCATION = prefix("animations/smilodon.animation.json");
    private static final ResourceLocation ERYON_ANIMATION_LOCATION = prefix("tanimations/eryon.animation.json");

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