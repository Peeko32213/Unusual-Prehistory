package com.peeko32213.unusualprehistory.client.model.entity;


import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.HwachavenatorEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;


public class HwachavenatorModel extends GeoModel<HwachavenatorEntity>
{
    @Override
    public ResourceLocation getModelResource(HwachavenatorEntity object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/hwachavenator.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(HwachavenatorEntity object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/hwachavenator.png");
    }

    @Override
    public ResourceLocation getAnimationResource(HwachavenatorEntity object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/hwacha.animation.json");
    }

    @Override
    public void setCustomAnimations(HwachavenatorEntity animatable, long instanceId, AnimationState<HwachavenatorEntity> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);
        if (animationState == null) return;

        EntityModelData extraDataOfType = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
        CoreGeoBone head = this.getAnimationProcessor().getBone("Neck");
        if (animatable.isBaby()) {
            head.setScaleX(1.75F);
            head.setScaleY(1.75F);
            head.setScaleZ(1.75F);
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

