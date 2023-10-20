package com.peeko32213.unusualprehistory.client.model;


import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.EntityAntarctopelta;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;
import software.bernie.geckolib3.core.processor.IBone;

public class AntarctopeltaModel extends GeoModel<EntityAntarctopelta>
{
    @Override
    public ResourceLocation getModelResource(EntityAntarctopelta object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/antarctopelta.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(EntityAntarctopelta object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/antarctopelta.png");
    }

    @Override
    public ResourceLocation getAnimationResource(EntityAntarctopelta object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/antarctopelta.animation.json");
    }

    @Override
    public void setCustomAnimations(EntityAntarctopelta animatable, long instanceId, AnimationState<EntityAntarctopelta> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);

        if (animationState == null) return;

        EntityModelData extraDataOfType = animationState.getData(DataTickets.ENTITY);
        IBone head = this.getAnimationProcessor().getBone("Head");

        if (dino.isBaby()) {
            head.setScaleX(1.75F);
            head.setScaleY(1.75F);
            head.setScaleZ(1.75F);
        }

        if (!dino.isSprinting()) {
            head.setRotationY(extraDataOfType.get(0).netHeadYaw * Mth.DEG_TO_RAD);
        }
    }


}

