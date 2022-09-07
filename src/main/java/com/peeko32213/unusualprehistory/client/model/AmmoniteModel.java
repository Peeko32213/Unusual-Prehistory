package com.peeko32213.unusualprehistory.client.model;


import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.EntityAmmonite;
import com.peeko32213.unusualprehistory.common.entity.EntityAnurognathus;
import com.peeko32213.unusualprehistory.common.entity.EntityStethacanthus;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class AmmoniteModel extends AnimatedGeoModel<EntityAmmonite>
{
    @Override
    public ResourceLocation getModelLocation(EntityAmmonite object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/ammonite.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityAmmonite object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/ammonite.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityAmmonite object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/ammonite.animation.json");
    }

    @Override
    public void setLivingAnimations(EntityAmmonite entity, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        IBone body = this.getAnimationProcessor().getBone("Body");
        EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);

        if (!entity.isInWater()) {
            body.setRotationZ(1.5708f);
        }
        else {
            body.setRotationX(extraData.headPitch * (float)Math.PI / 220F);
            body.setRotationY(extraData.netHeadYaw * (float)Math.PI / 180F);
        }
    }

}

