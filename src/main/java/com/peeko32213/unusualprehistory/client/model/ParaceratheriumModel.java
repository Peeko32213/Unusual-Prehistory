package com.peeko32213.unusualprehistory.client.model;


import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.EntityParaceratherium;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;


public class ParaceratheriumModel extends GeoModel<EntityParaceratherium>
{
    @Override
    public ResourceLocation getModelResource(EntityParaceratherium paraceratherium)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/paraceratherium/paraceratherium.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(EntityParaceratherium paraceratherium)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/paraceratherium/paraceratherium.png");
    }

    @Override
    public ResourceLocation getAnimationResource(EntityParaceratherium paraceratherium)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/paraceratherium/paraceratherium.animation.json");
    }

    public RenderType getRenderType(EntityParaceratherium animatable, ResourceLocation texture) {
        return RenderType.entityTranslucent(getTextureResource(animatable));
    }

    @Override
    public void setCustomAnimations(EntityParaceratherium animatable, long instanceId, AnimationState<EntityParaceratherium> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);
        if (animationState == null) return;
        EntityModelData extraDataOfType = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
        CoreGeoBone head = this.getAnimationProcessor().getBone("Head");
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

