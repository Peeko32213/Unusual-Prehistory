package com.peeko32213.unusualprehistory.client.model.entity.prehistoric.aquatic;

import com.peeko32213.unusualprehistory.common.entity.custom.part.PalaeophisPartEntity;
import com.peeko32213.unusualprehistory.common.entity.custom.part.PalaeophisPartIndex;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

import static com.peeko32213.unusualprehistory.UnusualPrehistory.modPrefix;

public class PalaeophisPartModel extends GeoModel<PalaeophisPartEntity> {

    private ResourceLocation neckModel = modPrefix("geo/palaeophis/palaeophis_neck.geo.json");
    private ResourceLocation bodyModel = modPrefix("geo/palaeophis/palaeophis_body.geo.json");
    private ResourceLocation bodyFinModel = modPrefix("geo/palaeophis/palaeophis_body_fin.geo.json");
    private ResourceLocation tailModel = modPrefix("geo/palaeophis/palaeophis_tail.geo.json");
    private ResourceLocation finModel = modPrefix("geo/palaeophis/palaeophis_fin.geo.json");

    private ResourceLocation neckTexture = modPrefix("textures/entity/palaeophis/palaeophis_neck.png");
    private ResourceLocation bodyTexture = modPrefix("textures/entity/palaeophis/palaeophis_body.png");
    private ResourceLocation bodyFinTexture = modPrefix("textures/entity/palaeophis/palaeophis_body_fin.png");
    private ResourceLocation tailTexture = modPrefix("textures/entity/palaeophis/palaeophis_tail.png");
    private ResourceLocation finTexture = modPrefix("textures/entity/palaeophis/palaeophis_fin.png");

    private ResourceLocation neckTextureShed = modPrefix("textures/entity/palaeophis/palaeophis_neck_shed.png");
    private ResourceLocation bodyTextureShed = modPrefix("textures/entity/palaeophis/palaeophis_body_shed.png");
    private ResourceLocation bodyFinTextureShed = modPrefix("textures/entity/palaeophis/palaeophis_body_fin_shed.png");
    private ResourceLocation tailTextureShed = modPrefix("textures/entity/palaeophis/palaeophis_tail_shed.png");
    private ResourceLocation finTextureShed = modPrefix("textures/entity/palaeophis/palaeophis_fin_shed.png");

    private ResourceLocation neckTextureDeep = modPrefix("textures/entity/palaeophis/palaeophis_deep_neck.png");
    private ResourceLocation bodyTextureDeep = modPrefix("textures/entity/palaeophis/palaeophis_deep_body.png");
    private ResourceLocation bodyFinTextureDeep = modPrefix("textures/entity/palaeophis/palaeophis_deep_body_fin.png");
    private ResourceLocation tailTextureDeep = modPrefix("textures/entity/palaeophis/palaeophis_deep_tail.png");
    private ResourceLocation finTextureDeep = modPrefix("textures/entity/palaeophis/palaeophis_deep_fin.png");

    private ResourceLocation neckTextureShedDeep = modPrefix("textures/entity/palaeophis/palaeophis_deep_neck_shed.png");
    private ResourceLocation bodyTextureShedDeep = modPrefix("textures/entity/palaeophis/palaeophis_deep_body_shed.png");
    private ResourceLocation bodyFinTextureShedDeep = modPrefix("textures/entity/palaeophis/palaeophis_deep_body_fin_shed.png");
    private ResourceLocation tailTextureShedDeep = modPrefix("textures/entity/palaeophis/palaeophis_deep_tail_shed.png");
    private ResourceLocation finTextureShedDeep = modPrefix("textures/entity/palaeophis/palaeophis_deep_fin_shed.png");

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
