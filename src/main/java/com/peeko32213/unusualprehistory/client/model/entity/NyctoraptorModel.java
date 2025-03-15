package com.peeko32213.unusualprehistory.client.model.entity;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.NyctoraptorEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class NyctoraptorModel extends GeoModel<NyctoraptorEntity> {

    @Override
    public ResourceLocation getModelResource(NyctoraptorEntity object) {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/nyctoraptor.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(NyctoraptorEntity object) {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/nyctoraptor.png");
    }

    @Override
    public ResourceLocation getAnimationResource(NyctoraptorEntity object) {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/nyctoraptor.animation.json");
    }

    @Override
    public void setCustomAnimations(NyctoraptorEntity animatable, long instanceId, AnimationState<NyctoraptorEntity> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);
        if (animationState == null) return;
        EntityModelData extraDataOfType = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
        CoreGeoBone head = this.getAnimationProcessor().getBone("Neck");

        if (animatable.isBaby()) {
            head.setScaleX(1.5F);
            head.setScaleY(1.5F);
            head.setScaleZ(1.5F);
        } else {
            head.setScaleX(1.0F);
            head.setScaleY(1.0F);
            head.setScaleZ(1.0F);
        }

        if (!animatable.isSprinting()) {
            head.setRotY(extraDataOfType.netHeadYaw() * Mth.DEG_TO_RAD);
        }
    }
}
