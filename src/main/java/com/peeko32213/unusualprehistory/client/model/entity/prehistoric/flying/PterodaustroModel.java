package com.peeko32213.unusualprehistory.client.model.entity.prehistoric.flying;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.flying.PterodaustroEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class PterodaustroModel extends GeoModel<PterodaustroEntity> {

    @Override
    public ResourceLocation getModelResource(PterodaustroEntity object) {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/pterodaustro.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(PterodaustroEntity object) {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/pterodaustro.png");
    }

    @Override
    public ResourceLocation getAnimationResource(PterodaustroEntity object) {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/pterodaustro.animation.json");
    }

    @Override
    public void setCustomAnimations(PterodaustroEntity animatable, long instanceId, AnimationState<PterodaustroEntity> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);
        if (animationState == null) return;
        EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
        CoreGeoBone head = this.getAnimationProcessor().getBone("head");
        CoreGeoBone flightControl = this.getAnimationProcessor().getBone("flight_control");

        if (animatable.isBaby()) {
            head.setScaleX(1.5F);
            head.setScaleY(1.5F);
            head.setScaleZ(1.5F);
        } else {
            head.setScaleX(1.0F);
            head.setScaleY(1.0F);
            head.setScaleZ(1.0F);
        }

        if (!animatable.isSprinting() && !animatable.isFlying()) {
            head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
        }

//        flightControl.setRotX(-((animatable.flightPitch * ((float) Math.PI / 180F))));
//        flightControl.setRotZ(((animatable.flightPitch * ((float) Math.PI / 180F)) / 2));
    }
}

