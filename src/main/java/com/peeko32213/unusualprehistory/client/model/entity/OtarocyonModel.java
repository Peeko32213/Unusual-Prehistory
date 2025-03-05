package com.peeko32213.unusualprehistory.client.model.entity;


import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.OtarocyonEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class OtarocyonModel extends GeoModel<OtarocyonEntity>
{
    @Override
    public ResourceLocation getModelResource(OtarocyonEntity object) {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/otarocyon.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(OtarocyonEntity object) {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/otarocyon.png");
    }

    @Override
    public ResourceLocation getAnimationResource(OtarocyonEntity object) {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/otarocyon.animation.json");
    }

    @Override
    public void setCustomAnimations(OtarocyonEntity animatable, long instanceId, AnimationState<OtarocyonEntity> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);
        if (animationState == null) return;
        EntityModelData extraDataOfType = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
        CoreGeoBone head = this.getAnimationProcessor().getBone("Head");
        CoreGeoBone headb = this.getAnimationProcessor().getBone("Head_B");
        if (animatable.isBaby()) {
            head.setScaleX(1.35F);
            head.setScaleY(1.35F);
            head.setScaleZ(1.35F);

            headb.setScaleX(1.35F);
            headb.setScaleY(1.35F);
            headb.setScaleZ(1.35F);
        } else {
            head.setScaleX(1.0F);
            head.setScaleY(1.0F);
            head.setScaleZ(1.0F);

            headb.setScaleX(1.0F);
            headb.setScaleY(1.0F);
            headb.setScaleZ(1.0F);
        }
        if (!animatable.isSprinting()) {
            head.setRotY(extraDataOfType.netHeadYaw() * Mth.DEG_TO_RAD);
        }
    }

}

