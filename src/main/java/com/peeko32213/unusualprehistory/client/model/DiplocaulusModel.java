package com.peeko32213.unusualprehistory.client.model;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.EntityDiplocaulus;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class DiplocaulusModel extends GeoModel<EntityDiplocaulus>
{
    @Override
    public ResourceLocation getModelResource(EntityDiplocaulus object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/diplocaulus.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(EntityDiplocaulus object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/diplocaulus.png");
    }

    @Override
    public ResourceLocation getAnimationResource(EntityDiplocaulus object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/diplocaulus.animation.json");
    }

    @Override
    public void setCustomAnimations(EntityDiplocaulus animatable, long instanceId, AnimationState<EntityDiplocaulus> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);
        if (animationState == null) return;
        EntityModelData extraDataOfType = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
        CoreGeoBone head = this.getAnimationProcessor().getBone("Head");
        if (animatable.isBaby()) {
            head.setScaleX(1.15F);
            head.setScaleY(1.15F);
            head.setScaleZ(1.15F);
        } else {
            head.setScaleX(1.0F);
            head.setScaleY(1.0F);
            head.setScaleZ(1.0F);
        }
    }

}

