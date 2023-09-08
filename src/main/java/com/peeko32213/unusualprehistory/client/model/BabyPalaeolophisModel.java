package com.peeko32213.unusualprehistory.client.model;


import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.msc.baby.EntityBabyDunk;
import com.peeko32213.unusualprehistory.common.entity.msc.baby.EntityBabyPalaeolophis;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class BabyPalaeolophisModel extends AnimatedGeoModel<EntityBabyPalaeolophis>
{
    @Override
    public ResourceLocation getModelResource(EntityBabyPalaeolophis object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/baby_palaeophis.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(EntityBabyPalaeolophis object)
    {
        if(object.getVariant() == 1){
            return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/baby_deep_palaeophis.png");
        }
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/baby_palaeophis.png");
    }

    @Override
    public ResourceLocation getAnimationResource(EntityBabyPalaeolophis object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/baby_palaeophis.animation.json");
    }

    @Override
    public void setCustomAnimations(EntityBabyPalaeolophis entity, int uniqueID, AnimationEvent customPredicate) {
        super.setCustomAnimations(entity, uniqueID, customPredicate);
        IBone body = this.getAnimationProcessor().getBone("Snake");
        EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);

        if (!entity.isInWater()) {
            body.setRotationZ(1.5708f);
        }
        else {
            body.setRotationX(extraData.headPitch * (float)Math.PI / 250F);
            body.setRotationY(extraData.netHeadYaw * (float)Math.PI / 250F);
        }
    }

}

