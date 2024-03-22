package com.peeko32213.unusualprehistory.client.model;


import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.EntityDunkleosteus;
import com.peeko32213.unusualprehistory.common.entity.EntityHynerpeton;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class HynerpetonModel extends GeoModel<EntityHynerpeton>
{
    @Override
    public ResourceLocation getModelResource(EntityHynerpeton object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/hynerpeton.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(EntityHynerpeton object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/hynerpeton.png");
    }

    @Override
    public ResourceLocation getAnimationResource(EntityHynerpeton object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/hynerpeton.animation.json");
    }

    @Override
    public void setCustomAnimations(EntityHynerpeton animatable, long instanceId, AnimationState<EntityHynerpeton> animationState) {
        //dynamic pitch
        CoreGeoBone body = this.getAnimationProcessor().getBone("root");
        EntityModelData extraDataOfType = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

        if (animatable.isInWater()) {
            body.setRotX(extraDataOfType.headPitch() * (float)Math.PI / 180F);
            body.setRotY(extraDataOfType.netHeadYaw() * (float)Math.PI / 180F);
        }
        //end of dynamic pitch
    }


}

