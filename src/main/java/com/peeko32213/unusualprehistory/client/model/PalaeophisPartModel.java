package com.peeko32213.unusualprehistory.client.model;

import com.peeko32213.unusualprehistory.common.entity.msc.part.EntityPalaeophisPart;
import com.peeko32213.unusualprehistory.common.entity.msc.part.PalaeophisPartIndex;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

import static com.peeko32213.unusualprehistory.UnusualPrehistory.prefix;

public class PalaeophisPartModel extends GeoModel<EntityPalaeophisPart> {
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

    private ResourceLocation neckTextureShed = prefix("textures/entity/palaeophis_neck_shed.png");
    private ResourceLocation bodyTextureShed = prefix("textures/entity/palaeophis_body_shed.png");
    private ResourceLocation bodyFinTextureShed = prefix("textures/entity/palaeophis_body_fin_shed.png");
    private ResourceLocation tailTextureShed = prefix("textures/entity/palaeophis_tail_shed.png");
    private ResourceLocation finTextureShed = prefix("textures/entity/palaeophis_fin_shed.png");



    private ResourceLocation neckTextureDeep = prefix("textures/entity/palaeophis_deep_neck.png");
    private ResourceLocation bodyTextureDeep = prefix("textures/entity/palaeophis_deep_body.png");
    private ResourceLocation bodyFinTextureDeep = prefix("textures/entity/palaeophis_deep_body_fin.png");
    private ResourceLocation tailTextureDeep = prefix("textures/entity/palaeophis_deep_tail.png");
    private ResourceLocation finTextureDeep = prefix("textures/entity/palaeophis_deep_fin.png");

    private ResourceLocation neckTextureShedDeep = prefix("textures/entity/palaeophis_deep_neck_shed.png");
    private ResourceLocation bodyTextureShedDeep = prefix("textures/entity/palaeophis_deep_body_shed.png");
    private ResourceLocation bodyFinTextureShedDeep = prefix("textures/entity/palaeophis_deep_body_fin_shed.png");
    private ResourceLocation tailTextureShedDeep = prefix("textures/entity/palaeophis_deep_tail_shed.png");
    private ResourceLocation finTextureShedDeep = prefix("textures/entity/palaeophis_deep_fin_shed.png");

    @Override
    public ResourceLocation getModelResource(EntityPalaeophisPart object) {
        return getModelForType(object.getPartType());
    }

    @Override
    public ResourceLocation getTextureResource(EntityPalaeophisPart object) {
        if(object.getVariant() == 1){

            if(object.isShedding()){
                return getTextureForShedTypeDeep(object.getPartType());
            }

            return getTextureForTypeDeep(object.getPartType());
        }




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

    private ResourceLocation getTextureForTypeDeep(PalaeophisPartIndex partType) {
        switch (partType){
            case BODY: return bodyTextureDeep;
            case NECK: return neckTextureDeep;
            case TAIL: return tailTextureDeep;
            case FIN:return finTextureDeep;
            case BODY_FIN:return bodyFinTextureDeep;
        }
        return bodyTextureDeep;
    }


    private ResourceLocation getTextureForShedTypeDeep(PalaeophisPartIndex partType) {
        switch (partType){
            case BODY: return bodyTextureShedDeep;
            case NECK: return neckTextureShedDeep;
            case TAIL: return tailTextureShedDeep;
            case FIN:return finTextureShedDeep;
            case BODY_FIN:return bodyFinTextureShedDeep;
        }
        return bodyTextureShedDeep;
    }

}
