package com.peeko32213.unusualprehistory.client.model.entity;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.BarinasuchusEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class BarinasuchusModel extends GeoModel<BarinasuchusEntity> {

    @Override
    public ResourceLocation getModelResource(BarinasuchusEntity barinasuchus) {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/barinasuchus/barinasuchus.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(BarinasuchusEntity barinasuchus) {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/barinasuchus/barinasuchus.png");
    }

    @Override
    public ResourceLocation getAnimationResource(BarinasuchusEntity barinasuchus) {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/barinasuchus/barinasuchus.animation.json");
    }

    @Override
    public void setCustomAnimations(BarinasuchusEntity animatable, long instanceId, AnimationState<BarinasuchusEntity> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);
        if (animationState == null) return;
        EntityModelData extraDataOfType = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
        CoreGeoBone head = this.getAnimationProcessor().getBone("Head");
        if (animatable.isBaby()) {
            head.setScaleX(1.35F);
            head.setScaleY(1.35F);
            head.setScaleZ(1.35F);
        } else {
            head.setScaleX(1.0F);
            head.setScaleY(1.0F);
            head.setScaleZ(1.0F);
        }
        if (!animatable.isSprinting()) {
            head.setRotY(extraDataOfType.netHeadYaw() * Mth.DEG_TO_RAD);
        }
    }
}

