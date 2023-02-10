package com.peeko32213.unusualprehistory.client.model;


import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.baby.EntityBabyDunk;
import com.peeko32213.unusualprehistory.common.entity.baby.EntityBeelzebufoTadpole;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class BabyDunkModel extends AnimatedGeoModel<EntityBabyDunk>
{
    @Override
    public ResourceLocation getModelResource(EntityBabyDunk object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/babydunk.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(EntityBabyDunk object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/baby_dunkleosteus.png");
    }

    @Override
    public ResourceLocation getAnimationResource(EntityBabyDunk object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/babydunk.animation.json");
    }

    @Override
    public void setLivingAnimations(EntityBabyDunk entity, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        IBone body = this.getAnimationProcessor().getBone("Body");
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

