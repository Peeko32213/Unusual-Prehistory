package com.peeko32213.unusualprehistory.client.model;


import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.EntityTriceratops;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class TriceratopsModel extends GeoModel<EntityTriceratops>
{
    @Override
    public ResourceLocation getModelResource(EntityTriceratops object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/trike.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(EntityTriceratops object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/triceratops.png");
    }

    @Override
    public ResourceLocation getAnimationResource(EntityTriceratops object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/trike.animation.json");
    }

    @Override
    public void setCustomAnimations(EntityTriceratops animatable, long instanceId, AnimationState<EntityTriceratops> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);
        if (animationState == null) return;
        EntityModelData extraDataOfType = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
        CoreGeoBone head = this.getAnimationProcessor().getBone("Head");

        if (animatable.isBaby()) {
            head.setScaleX(1.75F);
            head.setScaleY(1.75F);
            head.setScaleZ(1.75F);
        }
        if (!animatable.isSprinting()) {
            head.setRotY(extraDataOfType.netHeadYaw() * Mth.DEG_TO_RAD);
        }
    }

}

