package com.peeko32213.unusualprehistory.client.model.entity;


import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.PterygotusEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;

public class PterygotusModel extends GeoModel<PterygotusEntity> {

    @Override
    public ResourceLocation getModelResource(PterygotusEntity object) {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/pterygotus.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(PterygotusEntity object) {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/pterygotus.png");
    }

    @Override
    public ResourceLocation getAnimationResource(PterygotusEntity object) {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/pterygotus.animation.json");
    }

    @Override
    public void setCustomAnimations(PterygotusEntity animatable, long instanceId, AnimationState<PterygotusEntity> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);
        if (animationState == null) return;

        CoreGeoBone armLeft = this.getAnimationProcessor().getBone("ArmBaseLeft");
        CoreGeoBone armRight = this.getAnimationProcessor().getBone("ArmBaseRight");
        if (animatable.isBaby()) {
            armLeft.setScaleX(1.75F);
            armLeft.setScaleY(1.75F);
            armLeft.setScaleZ(1.75F);

            armRight.setScaleX(1.75F);
            armRight.setScaleY(1.75F);
            armRight.setScaleZ(1.75F);
        } else {
            armLeft.setScaleX(1.0F);
            armLeft.setScaleY(1.0F);
            armLeft.setScaleZ(1.0F);

            armRight.setScaleX(1.0F);
            armRight.setScaleY(1.0F);
            armRight.setScaleZ(1.0F);
        }
    }

}

