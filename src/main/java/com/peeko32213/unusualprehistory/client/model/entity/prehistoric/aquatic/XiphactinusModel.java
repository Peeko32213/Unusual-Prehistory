package com.peeko32213.unusualprehistory.client.model.entity.prehistoric.aquatic;


import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.aquatic.XiphactinusEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class XiphactinusModel extends GeoModel<XiphactinusEntity> {

    @Override
    public ResourceLocation getModelResource(XiphactinusEntity object) {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/xiphactinus.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(XiphactinusEntity object) {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/xiphactinus.png");
    }

    @Override
    public ResourceLocation getAnimationResource(XiphactinusEntity object) {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/xiphactinus.animation.json");
    }

    @Override
    public void setCustomAnimations(XiphactinusEntity animatable, long instanceId, AnimationState<XiphactinusEntity> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);
        if (animationState == null) return;

        EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
        CoreGeoBone swimControl = this.getAnimationProcessor().getBone("swim_control");
        CoreGeoBone head = this.getAnimationProcessor().getBone("head_rot");
        CoreGeoBone body = this.getAnimationProcessor().getBone("body_rot");
        CoreGeoBone tailBody = this.getAnimationProcessor().getBone("tail_rot");

        swimControl.setRotX(((entityData.headPitch() * ((float) Math.PI / 180F))));
        head.setRotX(((entityData.headPitch() * ((float) Math.PI / 180F)) / 4));
        body.setRotX(-((entityData.headPitch() * ((float) Math.PI / 180F))));
        tailBody.setRotX(-((entityData.headPitch() * ((float) Math.PI / 180F))));

        body.setRotY(-animatable.currentRoll / 2);
        tailBody.setRotY(-animatable.currentRoll);
        tailBody.setRotY(-animatable.currentRoll);
    }
}

