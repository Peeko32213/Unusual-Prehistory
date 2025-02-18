package com.peeko32213.unusualprehistory.client.model;


import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.EntityMammoth;
import com.peeko32213.unusualprehistory.common.entity.EntityPalaeophis;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;


public class PalaeophisModel extends GeoModel<EntityPalaeophis> {

    @Override
    public ResourceLocation getModelResource(EntityPalaeophis object) {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/palaeophis/palaeophis_head.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(EntityPalaeophis object) {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/palaeophis/palaeophis_head.png");
    }

    @Override
    public ResourceLocation getAnimationResource(EntityPalaeophis object) {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/palaeophis/palaeophis_head.animation.json");
    }

    @Override
    public void setCustomAnimations(EntityPalaeophis animatable, long instanceId, AnimationState<EntityPalaeophis> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);
        if (animationState == null) return;

        CoreGeoBone head = this.getAnimationProcessor().getBone("Head");

        if (animatable.isBaby()) {
            head.setScaleX(1.25F);
            head.setScaleY(1.25F);
            head.setScaleZ(1.25F);
        } else {
            head.setScaleX(1.0F);
            head.setScaleY(1.0F);
            head.setScaleZ(1.0F);
        }
    }
}

