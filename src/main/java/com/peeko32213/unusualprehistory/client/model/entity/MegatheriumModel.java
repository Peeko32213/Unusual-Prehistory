package com.peeko32213.unusualprehistory.client.model.entity;


import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.MegatheriumEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class MegatheriumModel extends GeoModel<MegatheriumEntity> {

    @Override
    public ResourceLocation getModelResource(MegatheriumEntity megatherium) {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/megatherium.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(MegatheriumEntity megatherium) {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/megatherium.png");
    }

    @Override
    public ResourceLocation getAnimationResource(MegatheriumEntity megatherium) {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/megatherium.animation.json");
    }

    @Override
    public void setCustomAnimations(MegatheriumEntity animatable, long instanceId, AnimationState<MegatheriumEntity> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);
        if (animationState == null) return;
        EntityModelData extraDataOfType = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

        CoreGeoBone saddle = this.getAnimationProcessor().getBone("Saddle");

        saddle.setHidden(!animatable.isSaddled());


        CoreGeoBone head = this.getAnimationProcessor().getBone("Sloth_Head");
        head.setRotY(extraDataOfType.netHeadYaw() * Mth.DEG_TO_RAD);
    }
}

