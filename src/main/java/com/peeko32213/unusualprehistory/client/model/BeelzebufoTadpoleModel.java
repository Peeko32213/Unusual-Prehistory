package com.peeko32213.unusualprehistory.client.model;


import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.baby.EntityBeelzebufoTadpole;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class BeelzebufoTadpoleModel extends AnimatedGeoModel<EntityBeelzebufoTadpole>
{
    @Override
    public ResourceLocation getModelLocation(EntityBeelzebufoTadpole object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/beelze_baby.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityBeelzebufoTadpole object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/beelzebufo_baby.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityBeelzebufoTadpole object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/beelze_tadpole.animation.json");
    }

    @Override
    public void setLivingAnimations(EntityBeelzebufoTadpole entity, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        IBone body = this.getAnimationProcessor().getBone("Tadpole");
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

