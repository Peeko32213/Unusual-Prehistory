package com.peeko32213.unusualprehistory.client.model;


import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.EntityAustroraptor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class AustroraptorModel extends GeoModel<EntityAustroraptor>
{
    @Override
    public ResourceLocation getModelResource(EntityAustroraptor object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/austroraptor.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(EntityAustroraptor object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/austroraptor.png");
    }

    @Override
    public ResourceLocation getAnimationResource(EntityAustroraptor object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/austroraptor.animation.json");
    }

    @Override
    public void setCustomAnimations(EntityAustroraptor animatable, long instanceId, AnimationState<EntityAustroraptor> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);

        if (animationState == null) return;

        EntityModelData extraDataOfType = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
        CoreGeoBone head = this.getAnimationProcessor().getBone("Neck");

        if (!animatable.isSprinting()) {
            head.setRotY(extraDataOfType.netHeadYaw() * Mth.DEG_TO_RAD);
        }
    }

}

