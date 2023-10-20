package com.peeko32213.unusualprehistory.client.model;


import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.msc.baby.EntityBeelzebufoTadpole;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class BeelzebufoTadpoleModel extends GeoModel<EntityBeelzebufoTadpole>
{
    @Override
    public ResourceLocation getModelResource(EntityBeelzebufoTadpole object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/beelze_baby.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(EntityBeelzebufoTadpole object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/beelzebufo_baby.png");
    }

    @Override
    public ResourceLocation getAnimationResource(EntityBeelzebufoTadpole object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/beelze_tadpole.animation.json");
    }

    @Override
    public void setCustomAnimations(EntityBeelzebufoTadpole entity, int uniqueID, AnimationEvent customPredicate) {
        super.setCustomAnimations(entity, uniqueID, customPredicate);
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

