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
    public ResourceLocation getModelLocation(EntityStethacanthus object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/stethacanthus.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityStethacanthus object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/stethacanthus.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityStethacanthus object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/stethacanthus.animation.json");
    }

    @Override
    public void setLivingAnimations(EntityStethacanthus entity, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        IBone body = this.getAnimationProcessor().getBone("body");
        EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);

        if (!entity.isInWater()) {
            body.setRotationZ(1.5708f);
        }
        else {
            body.setRotationX(extraData.headPitch * (float)Math.PI / 180F);
            body.setRotationY(extraData.netHeadYaw * (float)Math.PI / 180F);
        }
    }

}

