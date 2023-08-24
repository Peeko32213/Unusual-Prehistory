package com.peeko32213.unusualprehistory.client.model;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.msc.part.EntityPalaeophisPart;
import com.peeko32213.unusualprehistory.common.entity.msc.part.PalaeophisPartIndex;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

import static com.peeko32213.unusualprehistory.UnusualPrehistory.prefix;

public class PalaeophisPartModel extends AnimatedGeoModel<EntityPalaeophisPart>  {
    private ResourceLocation neckModel = prefix("geo/palaeophis_neck.geo.json");
    private ResourceLocation bodyModel = prefix("geo/palaeophis_body.geo.json");
    private ResourceLocation bodyFinModel = prefix("geo/palaeophis_body_fin.geo.json");
    private ResourceLocation tailModel = prefix("geo/palaeophis_tail.geo.json");
    private ResourceLocation finModel = prefix("geo/palaeophis_fin.geo.json");

    private ResourceLocation neckTexture = prefix("textures/entity/palaeophis_neck.png");
    private ResourceLocation bodyTexture = prefix("textures/entity/palaeophis_body.png");
    private ResourceLocation bodyFinTexture = prefix("textures/entity/palaeophis_body_fin.png");
    private ResourceLocation tailTexture = prefix("textures/entity/palaeophis_tail.png");
    private ResourceLocation finTexture = prefix("textures/entity/palaeophis_fin.png");

    private ResourceLocation neckTextureShed = prefix("textures/entity/palaeophis_neck.png");
    private ResourceLocation bodyTextureShed = prefix("textures/entity/palaeophis_body.png");
    private ResourceLocation bodyFinTextureShed = prefix("textures/entity/palaeophis_body_fin.png");
    private ResourceLocation tailTextureShed = prefix("textures/entity/palaeophis_tail.png");
    private ResourceLocation finTextureShed = prefix("textures/entity/palaeophis_fin.png");
    @Override
    public ResourceLocation getModelResource(EntityPalaeophisPart object) {
        return getModelForType(object.getPartType());
    }

    @Override
    public ResourceLocation getTextureResource(EntityPalaeophisPart object) {
        if(object.isShedding()){
            return getTextureForShedType(object.getPartType());
        }
        return getTextureForType(object.getPartType());
    }

    @Override
    public ResourceLocation getAnimationResource(EntityPalaeophisPart animatable) {
        return null;
    }

    private ResourceLocation getModelForType(PalaeophisPartIndex partType) {
        switch (partType){
            case BODY: return bodyModel;
            case NECK: return neckModel;
            case TAIL: return tailModel;
            case FIN:return finModel;
            case BODY_FIN:return bodyFinModel;
        }
        return bodyModel;
    }

    private ResourceLocation getTextureForType(PalaeophisPartIndex partType) {
        switch (partType){
            case BODY: return bodyTexture;
            case NECK: return neckTexture;
            case TAIL: return tailTexture;
            case FIN:return finTexture;
            case BODY_FIN:return bodyFinTexture;
        }
        return bodyTexture;
    }

    private ResourceLocation getTextureForShedType(PalaeophisPartIndex partType) {
        switch (partType){
            case BODY: return bodyTextureShed;
            case NECK: return neckTextureShed;
            case TAIL: return tailTextureShed;
            case FIN:return finTextureShed;
            case BODY_FIN:return bodyFinTextureShed;
        }
        return bodyTextureShed;
    }
}
