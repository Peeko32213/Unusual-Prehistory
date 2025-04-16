package com.peeko32213.unusualprehistory.client.model.entity.prehistoric.aquatic;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.aquatic.OphiodonEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class OphiodonModel extends GeoModel<OphiodonEntity> {

    @Override
    public ResourceLocation getModelResource(OphiodonEntity object) {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/ophiodon.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(OphiodonEntity object) {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/ophiodon.png");
    }

    @Override
    public ResourceLocation getAnimationResource(OphiodonEntity object) {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/ophiodon.animation.json");
    }

    @Override
    public void setCustomAnimations(OphiodonEntity animatable, long instanceId, AnimationState<OphiodonEntity> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);
        EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
        CoreGeoBone swimControl = this.getAnimationProcessor().getBone("swim_control");
        CoreGeoBone head = this.getAnimationProcessor().getBone("Head");

        if (animatable.isBaby()) {
            head.setScaleX(1.4F);
            head.setScaleY(1.4F);
            head.setScaleZ(1.4F);
        } else {
            head.setScaleX(1.0F);
            head.setScaleY(1.0F);
            head.setScaleZ(1.0F);
        }

        swimControl.setRotX(((entityData.headPitch() * ((float) Math.PI / 180F))));
        swimControl.setRotZ(-((entityData.netHeadYaw() * ((float) Math.PI / 180F)) / 2));
    }
}

