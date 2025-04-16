package com.peeko32213.unusualprehistory.client.model.entity.prehistoric.flying;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.flying.KimmeridgebrachypteraeschnidiumEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class KimmeridgebrachypteraeschnidiumModel extends GeoModel<KimmeridgebrachypteraeschnidiumEntity> {
    @Override
    public ResourceLocation getModelResource(KimmeridgebrachypteraeschnidiumEntity kimmer) {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/kimmeridgebrachypteraeschnidium.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(KimmeridgebrachypteraeschnidiumEntity kimmer) {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/kimmeridgebrachypteraeschnidium/base_"+kimmer.getBaseColor()+".png");
    }

    @Override
    public ResourceLocation getAnimationResource(KimmeridgebrachypteraeschnidiumEntity kimmer) {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/kimmeridgebrachypteraeschnidium.animation.json");
    }

    @Override
    public void setCustomAnimations(KimmeridgebrachypteraeschnidiumEntity animatable, long instanceId, AnimationState<KimmeridgebrachypteraeschnidiumEntity> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);
        EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

        if (animatable.isFlying()) {
            CoreGeoBone flightControl = this.getAnimationProcessor().getBone("flight_control");

            flightControl.setRotX(((entityData.headPitch() * ((float) Math.PI / 180F))));
            flightControl.setRotZ(-((entityData.netHeadYaw() * ((float) Math.PI / 180F)) / 2));
        }
    }
}
