package com.peeko32213.unusualprehistory.client.model;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.EntityHynerpeton;
import com.peeko32213.unusualprehistory.common.entity.EntityJawlessFish;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class JawlessFishModel extends GeoModel<EntityJawlessFish> {
    private static final ResourceLocation TEXTURE_CEPHALAPIS = new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/cephalaspis.png");
    private static final ResourceLocation TEXTURE_DORYASPIS = new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/doryaspis.png");
    private static final ResourceLocation TEXTURE_FURCACAUDA = new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/furcacauda.png");
    private static final ResourceLocation TEXTURE_SACAMAMBASPIS = new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/sacabambaspis.png");

    private static final ResourceLocation MODEL_CEPHALAPIS = new ResourceLocation(UnusualPrehistory.MODID, "geo/jawless_fish/cephalaspis.geo.json");
    private static final ResourceLocation MODEL_DORYASPIS = new ResourceLocation(UnusualPrehistory.MODID, "geo/jawless_fish/doryaspis.geo.json");
    private static final ResourceLocation MODEL_FURCACAUDA = new ResourceLocation(UnusualPrehistory.MODID, "geo/jawless_fish/furcacauda.geo.json");
    private static final ResourceLocation MODEL_SACAMAMBASPIS = new ResourceLocation(UnusualPrehistory.MODID, "geo/jawless_fish/sacabambaspis.geo.json");

    @Override
    public ResourceLocation getModelResource(EntityJawlessFish object)
    {
        return switch (EntityJawlessFish.getVariant()) {
            case 1 -> MODEL_DORYASPIS;
            case 2 -> MODEL_CEPHALAPIS;
            case 3 -> MODEL_FURCACAUDA;
            default -> MODEL_SACAMAMBASPIS;
        };
    }

    @Override
    public ResourceLocation getTextureResource(EntityJawlessFish animatable) {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/jawless_fish/cephalaspis.png");
    }

    @Override
    public ResourceLocation getAnimationResource(EntityJawlessFish object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/jawless_fish.animation.json");
    }



}
