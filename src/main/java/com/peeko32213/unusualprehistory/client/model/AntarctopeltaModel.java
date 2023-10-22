package com.peeko32213.unusualprehistory.client.model;


import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.EntityAntarctopelta;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

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

