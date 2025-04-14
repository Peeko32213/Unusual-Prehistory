package com.peeko32213.unusualprehistory.client.model.entity.prehistoric.aquatic;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.aquatic.StethacanthusEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class StethacanthusModel extends GeoModel<StethacanthusEntity> {

    @Override
    public ResourceLocation getModelResource(StethacanthusEntity object) {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/stethacanthus.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(StethacanthusEntity object) {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/stethacanthus.png");
    }

    @Override
    public ResourceLocation getAnimationResource(StethacanthusEntity object) {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/stethacanthus.animation.json");
    }

    @Override
    public void setCustomAnimations(StethacanthusEntity animatable, long instanceId, AnimationState<StethacanthusEntity> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);
        EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
        CoreGeoBone swimControl = this.getAnimationProcessor().getBone("swim_control");
        CoreGeoBone head = this.getAnimationProcessor().getBone("head");

        if (animatable.isBaby()) {
            head.setScaleX(1.3F);
            head.setScaleY(1.3F);
            head.setScaleZ(1.3F);
        } else {
            head.setScaleX(1.0F);
            head.setScaleY(1.0F);
            head.setScaleZ(1.0F);
        }

        swimControl.setRotX(((entityData.headPitch() * ((float) Math.PI / 180F))));
        swimControl.setRotZ(-((entityData.netHeadYaw() * ((float) Math.PI / 180F)) / 2));
    }
}

