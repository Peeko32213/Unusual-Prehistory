package com.peeko32213.unusualprehistory.client.model;

import com.peeko32213.unusualprehistory.common.entity.msc.part.EntityLeedsichthysPart;
import com.peeko32213.unusualprehistory.common.entity.msc.part.LeedsichthysPartIndex;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

import static com.peeko32213.unusualprehistory.UnusualPrehistory.prefix;

public class LeedsichthysPartModel extends GeoModel<EntityLeedsichthysPart> {

    private ResourceLocation tailModel = prefix("geo/leedsichthys.geo.json");
    private ResourceLocation tailTexture = prefix("textures/entity/leedsichthys_invis.png");


    @Override
    public ResourceLocation getModelResource(EntityLeedsichthysPart object) {
        return getModelForType(object.getPartType());
    }

    @Override
    public ResourceLocation getTextureResource(EntityLeedsichthysPart object) {
        return getTextureForType(object.getPartType());
    }

    @Override
    public ResourceLocation getAnimationResource(EntityLeedsichthysPart animatable) {
        return null;
    }

    private ResourceLocation getModelForType(LeedsichthysPartIndex partType) {
        switch (partType){

            case TAIL: return tailModel;
        }
        return tailModel;
    }

    private ResourceLocation getTextureForType(LeedsichthysPartIndex partType) {
        switch (partType){
            case TAIL: return tailTexture;
        }
        return tailModel;
    }

}
