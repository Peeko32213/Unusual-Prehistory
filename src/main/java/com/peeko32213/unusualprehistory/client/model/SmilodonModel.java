package com.peeko32213.unusualprehistory.client.model;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.EntitySmilodon;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class SmilodonModel extends GeoModel<EntitySmilodon> {

    @Override
    public ResourceLocation getModelResource(EntitySmilodon smilodon) {
        if(smilodon.isBaby()){
            return new ResourceLocation(UnusualPrehistory.MODID, "geo/smilodon/smilodon_baby.geo.json");
        } else {
            return new ResourceLocation(UnusualPrehistory.MODID, "geo/smilodon/smilodon.geo.json");
        }
    }

    @Override
    public ResourceLocation getTextureResource(EntitySmilodon smilodon) {
        if(smilodon.isBaby()) {
            return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/smilodon/smilodon_baby.png");
        }
        else if(smilodon.isBaby() && smilodon.getVariant() == 1) {
            return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/smilodon/smilodon_ocelot_baby.png");
        }
        else if(smilodon.getVariant() == 1){
            return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/smilodon/smilodon_ocelot.png");
        }
        else {
            return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/smilodon/smilodon.png");
        }
    }

    @Override
    public ResourceLocation getAnimationResource(EntitySmilodon smilodon) {
        if(smilodon.isBaby()){
            return new ResourceLocation(UnusualPrehistory.MODID, "animations/smilodon/smilodon_baby.animation.json");
        } else {
            return new ResourceLocation(UnusualPrehistory.MODID, "animations/smilodon/smilodon.animation.json");
        }
    }

    @Override
    public void setCustomAnimations(EntitySmilodon animatable, long instanceId, AnimationState<EntitySmilodon> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);
        if (animationState == null) return;
        EntityModelData extraDataOfType = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
        CoreGeoBone head = this.getAnimationProcessor().getBone("Head");
        head.setRotY(extraDataOfType.netHeadYaw() * Mth.DEG_TO_RAD);
    }
}

