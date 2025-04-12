package com.peeko32213.unusualprehistory.client.model.entity.prehistoric;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.client.model.ExtendedMolangQueriesModel;
import com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.BarinasuchusEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.data.EntityModelData;

public class BarinasuchusModel extends ExtendedMolangQueriesModel<BarinasuchusEntity> {

    @Override
    public ResourceLocation getModelResource(BarinasuchusEntity barinasuchus) {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/barinasuchus.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(BarinasuchusEntity barinasuchus) {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/barinasuchus.png");
    }

    @Override
    public ResourceLocation getAnimationResource(BarinasuchusEntity barinasuchus) {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/barinasuchus.animation.json");
    }

    @Override
    public void setCustomAnimations(BarinasuchusEntity animatable, long instanceId, AnimationState<BarinasuchusEntity> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);

        if (animationState == null) return;
        EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
        CoreGeoBone head = this.getAnimationProcessor().getBone("head");
        CoreGeoBone body = this.getAnimationProcessor().getBone("body");
        CoreGeoBone tail = this.getAnimationProcessor().getBone("tail");

//        if(!animatable.isVehicle()){
//            float headOffset = body.getRotX() * 1;
//            float offSetYaw = entityData.netHeadYaw() * Mth.DEG_TO_RAD + headOffset;
//            offSetYaw = (float) Mth.clamp(offSetYaw, -0.7, 0.7);
//            tail.setRotY(offSetYaw);
//        }

        if (animatable.isBaby()) {
            head.setScaleX(1.5F);
            head.setScaleY(1.5F);
            head.setScaleZ(1.5F);
        } else {
            head.setScaleX(1.0F);
            head.setScaleY(1.0F);
            head.setScaleZ(1.0F);
        }
        if (!animatable.isSprinting()) {
            head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
        }
    }
}

