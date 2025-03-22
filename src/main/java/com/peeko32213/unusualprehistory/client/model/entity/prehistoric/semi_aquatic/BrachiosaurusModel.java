package com.peeko32213.unusualprehistory.client.model.entity.prehistoric.semi_aquatic;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.semi_aquatic.BrachiosaurusEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class BrachiosaurusModel extends GeoModel<BrachiosaurusEntity> {

    @Override
    public ResourceLocation getModelResource(BrachiosaurusEntity brachiosaurus) {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/brachiosaurus.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(BrachiosaurusEntity brachiosaurus){
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/brachiosaurus.png");
    }

    @Override
    public ResourceLocation getAnimationResource(BrachiosaurusEntity brachiosaurus) {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/brachiosaurus.animation.json");
    }

    @Override
    public void setCustomAnimations(BrachiosaurusEntity animatable, long instanceId, AnimationState<BrachiosaurusEntity> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);
        if (animationState == null) return;

        EntityModelData extraDataOfType = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

        CoreGeoBone neck = this.getAnimationProcessor().getBone("neck");
        CoreGeoBone head = this.getAnimationProcessor().getBone("head");

        if (animatable.isBaby()) {
            head.setScaleX(1.5F);
            head.setScaleY(1.5F);
            head.setScaleZ(1.5F);
        } else {
            head.setScaleX(1.0F);
            head.setScaleY(1.0F);
            head.setScaleZ(1.0F);
        }

        neck.setRotY(extraDataOfType.netHeadYaw() * Mth.DEG_TO_RAD);
    }
}

