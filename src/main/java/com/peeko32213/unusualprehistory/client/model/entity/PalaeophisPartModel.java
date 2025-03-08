package com.peeko32213.unusualprehistory.client.model.entity;

import com.peeko32213.unusualprehistory.common.entity.custom.part.PalaeophisPartEntity;
import com.peeko32213.unusualprehistory.common.entity.custom.part.PalaeophisPartIndex;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

import static com.peeko32213.unusualprehistory.UnusualPrehistory.prefix;

public class PalaeophisPartModel extends GeoModel<PalaeophisPartEntity> {

    private ResourceLocation neckModel = prefix("geo/palaeophis/palaeophis_neck.geo.json");
    private ResourceLocation bodyModel = prefix("geo/palaeophis/palaeophis_body.geo.json");
    private ResourceLocation bodyFinModel = prefix("geo/palaeophis/palaeophis_body_fin.geo.json");
    private ResourceLocation tailModel = prefix("geo/palaeophis/palaeophis_tail.geo.json");
    private ResourceLocation finModel = prefix("geo/palaeophis/palaeophis_fin.geo.json");

    private ResourceLocation neckTexture = prefix("textures/entity/palaeophis/palaeophis_neck.png");
    private ResourceLocation bodyTexture = prefix("textures/entity/palaeophis/palaeophis_body.png");
    private ResourceLocation bodyFinTexture = prefix("textures/entity/palaeophis/palaeophis_body_fin.png");
    private ResourceLocation tailTexture = prefix("textures/entity/palaeophis/palaeophis_tail.png");
    private ResourceLocation finTexture = prefix("textures/entity/palaeophis/palaeophis_fin.png");

    private ResourceLocation neckTextureShed = prefix("textures/entity/palaeophis/palaeophis_neck_shed.png");
    private ResourceLocation bodyTextureShed = prefix("textures/entity/palaeophis/palaeophis_body_shed.png");
    private ResourceLocation bodyFinTextureShed = prefix("textures/entity/palaeophis/palaeophis_body_fin_shed.png");
    private ResourceLocation tailTextureShed = prefix("textures/entity/palaeophis/palaeophis_tail_shed.png");
    private ResourceLocation finTextureShed = prefix("textures/entity/palaeophis/palaeophis_fin_shed.png");

    private ResourceLocation neckTextureDeep = prefix("textures/entity/palaeophis/palaeophis_deep_neck.png");
    private ResourceLocation bodyTextureDeep = prefix("textures/entity/palaeophis/palaeophis_deep_body.png");
    private ResourceLocation bodyFinTextureDeep = prefix("textures/entity/palaeophis/palaeophis_deep_body_fin.png");
    private ResourceLocation tailTextureDeep = prefix("textures/entity/palaeophis/palaeophis_deep_tail.png");
    private ResourceLocation finTextureDeep = prefix("textures/entity/palaeophis/palaeophis_deep_fin.png");

    private ResourceLocation neckTextureShedDeep = prefix("textures/entity/palaeophis/palaeophis_deep_neck_shed.png");
    private ResourceLocation bodyTextureShedDeep = prefix("textures/entity/palaeophis/palaeophis_deep_body_shed.png");
    private ResourceLocation bodyFinTextureShedDeep = prefix("textures/entity/palaeophis/palaeophis_deep_body_fin_shed.png");
    private ResourceLocation tailTextureShedDeep = prefix("textures/entity/palaeophis/palaeophis_deep_tail_shed.png");
    private ResourceLocation finTextureShedDeep = prefix("textures/entity/palaeophis/palaeophis_deep_fin_shed.png");

    @Override
    public ResourceLocation getModelResource(PalaeophisPartEntity object) {
        return getModelForType(object.getPartType());
    }

    @Override
    public ResourceLocation getTextureResource(PalaeophisPartEntity object) {
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
    public ResourceLocation getAnimationResource(PalaeophisPartEntity animatable) {
        return null;
    }

    private ResourceLocation getModelForType(PalaeophisPartIndex partType) {
        return switch (partType) {
            case BODY -> bodyModel;
            case NECK -> neckModel;
            case TAIL -> tailModel;
            case FIN -> finModel;
            case BODY_FIN -> bodyFinModel;
            default -> bodyModel;
        };
    }

    private ResourceLocation getTextureForType(PalaeophisPartIndex partType) {
        return switch (partType) {
            case BODY -> bodyTexture;
            case NECK -> neckTexture;
            case TAIL -> tailTexture;
            case FIN -> finTexture;
            case BODY_FIN -> bodyFinTexture;
            default -> bodyTexture;
        };
    }

    private ResourceLocation getTextureForShedType(PalaeophisPartIndex partType) {
        return switch (partType) {
            case BODY -> bodyTextureShed;
            case NECK -> neckTextureShed;
            case TAIL -> tailTextureShed;
            case FIN -> finTextureShed;
            case BODY_FIN -> bodyFinTextureShed;
            default -> bodyTextureShed;
        };
    }

    private ResourceLocation getTextureForTypeDeep(PalaeophisPartIndex partType) {
        return switch (partType) {
            case BODY -> bodyTextureDeep;
            case NECK -> neckTextureDeep;
            case TAIL -> tailTextureDeep;
            case FIN -> finTextureDeep;
            case BODY_FIN -> bodyFinTextureDeep;
            default -> bodyTextureDeep;
        };
    }


    private ResourceLocation getTextureForShedTypeDeep(PalaeophisPartIndex partType) {
        return switch (partType) {
            case BODY -> bodyTextureShedDeep;
            case NECK -> neckTextureShedDeep;
            case TAIL -> tailTextureShedDeep;
            case FIN -> finTextureShedDeep;
            case BODY_FIN -> bodyFinTextureShedDeep;
            default -> bodyTextureShedDeep;
        };
    }
}
