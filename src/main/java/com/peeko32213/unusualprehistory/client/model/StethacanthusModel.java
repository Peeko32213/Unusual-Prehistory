package com.peeko32213.unusualprehistory.client.model;


import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.EntityStethacanthus;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class StethacanthusModel extends AnimatedGeoModel<EntityStethacanthus>
{
    @Override
    public ResourceLocation getModelResource(EntityStethacanthus object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/stethacanthus.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(EntityStethacanthus object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/stethacanthus.png");
    }

    @Override
    public ResourceLocation getAnimationResource(EntityStethacanthus object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/stethacanthus.animation.json");
    }

    @Override
    public void setCustomAnimations(EntityStethacanthus entity, int uniqueID, AnimationEvent customPredicate) {
        super.setCustomAnimations(entity, uniqueID, customPredicate);
        IBone body = this.getAnimationProcessor().getBone("body");
        EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);

        if (!entity.isInWater() && !entity.isFromBook()) {
            body.setRotationZ(1.5708f);
        }
    }

}

