package com.peeko32213.unusualprehistory.client.model;


import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.EntityDunkleosteus;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class DunkleosteusModel extends GeoModel<EntityDunkleosteus>
{
    @Override
    public ResourceLocation getModelResource(EntityDunkleosteus object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/dunk.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(EntityDunkleosteus object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/dunkleosteus.png");
    }

    @Override
    public ResourceLocation getAnimationResource(EntityDunkleosteus object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/dunk.animation.json");
    }

    @Override
    public void setCustomAnimations(EntityDunkleosteus entity, int uniqueID, AnimationEvent customPredicate) {
        super.setCustomAnimations(entity, uniqueID, customPredicate);
        IBone body = this.getAnimationProcessor().getBone("Body");
        IBone backBody = this.getAnimationProcessor().getBone("BackBody");
        IBone tailfin = this.getAnimationProcessor().getBone("Tailfin");
        EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);

        if (!entity.isInWater() && !entity.isFromBook()) {
            body.setRotationZ(1.5708f);
        }
        AnimationData manager = entity.getFactory().getOrCreateAnimationData(uniqueID);
        int unpausedMultiplier = !Minecraft.getInstance().isPaused() || manager.shouldPlayWhilePaused ? 1 : 0;
        backBody.setRotationY(backBody.getRotationY() + extraData.netHeadYaw * ((float) Math.PI / 270F) * unpausedMultiplier);
        tailfin.setRotationY(tailfin.getRotationY() + extraData.netHeadYaw * ((float) Math.PI / 270F) * unpausedMultiplier);

    }

}

