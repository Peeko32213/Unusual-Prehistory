package com.peeko32213.unusualprehistory.client.model;


import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.EntityHwachavenator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;


public class HwachavenatorModel extends GeoModel<EntityHwachavenator>
{
    @Override
    public ResourceLocation getModelResource(EntityHwachavenator object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/hwachavenator.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(EntityHwachavenator object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/hwachavenator.png");
    }

    @Override
    public ResourceLocation getAnimationResource(EntityHwachavenator object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/hwacha.animation.json");
    }

    @Override
    public void setCustomAnimations(EntityHwachavenator animatable, long instanceId, AnimationState<EntityHwachavenator> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);
        if (animationState == null) return;
        EntityModelData extraDataOfType = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
        CoreGeoBone head = this.getAnimationProcessor().getBone("Neck");
        if (!animatable.isSprinting()) {
            head.setRotY(extraDataOfType.netHeadYaw() * Mth.DEG_TO_RAD);
        }
    }

}

