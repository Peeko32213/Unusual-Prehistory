package com.peeko32213.unusualprehistory.client.model.entity.prehistoric.aquatic;

import com.peeko32213.unusualprehistory.common.entity.custom.part.LeedsichthysPartEntity;
import com.peeko32213.unusualprehistory.common.entity.custom.part.LeedsichthysPartIndex;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

import static com.peeko32213.unusualprehistory.UnusualPrehistory.modPrefix;

public class LeedsichthysPartModel extends GeoModel<LeedsichthysPartEntity> {

    private ResourceLocation tailModel = modPrefix("geo/leedsichthys.geo.json");
    private ResourceLocation tailTexture = modPrefix("textures/entity/leedsichthys_invis.png");

    @Override
    public ResourceLocation getModelResource(LeedsichthysPartEntity object) {
        return getModelForType(object.getPartType());
    }

    @Override
    public ResourceLocation getTextureResource(LeedsichthysPartEntity object) {
        return getTextureForType(object.getPartType());
    }

    @Override
    public ResourceLocation getAnimationResource(LeedsichthysPartEntity animatable) {
        return null;
    }

    private ResourceLocation getModelForType(LeedsichthysPartIndex partType) {
        //switch (partType){
//
        //    case TAIL: return tailModel;
        //}
        return tailModel;
    }

    private ResourceLocation getTextureForType(LeedsichthysPartIndex partType) {
        switch (partType){
            case TAIL: return tailTexture;
        }
        return tailModel;
    }

}
