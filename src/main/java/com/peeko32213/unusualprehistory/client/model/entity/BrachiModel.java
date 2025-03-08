package com.peeko32213.unusualprehistory.client.model.entity;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.semi_aquatic.BrachiosaurusEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class BrachiModel extends GeoModel<BrachiosaurusEntity> {

    @Override
    public ResourceLocation getModelResource(BrachiosaurusEntity brachiosaurus) {
        if(brachiosaurus.isBaby()) {
            return new ResourceLocation(UnusualPrehistory.MODID, "geo/brachiosaurus/brachiosaurus_baby.geo.json");
        }
//        if(brachiosaurus.shouldBeTeen()) {
//            return new ResourceLocation(UnusualPrehistory.MODID, "geo/brachiosaurus/brachiosaurus_teen.geo.json");
//        }
        else {
            return new ResourceLocation(UnusualPrehistory.MODID, "geo/brachiosaurus/brachiosaurus.geo.json");
        }
    }

    @Override
    public ResourceLocation getTextureResource(BrachiosaurusEntity brachiosaurus){
        if (brachiosaurus.isBaby()) {
            return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/brachiosaurus/brachiosaurus_baby.png");
        }
//        if(brachiosaurus.shouldBeTeen()) {
//            return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/brachiosaurus/brachiosaurus_teen.png");
//        }
        if (brachiosaurus.isSaddled() && !brachiosaurus.isBaby()) {
            return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/brachiosaurus/brachiosaurus_saddled.png");
        }
        else {
            return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/brachiosaurus/brachiosaurus.png");
        }
    }

    @Override
    public ResourceLocation getAnimationResource(BrachiosaurusEntity brachiosaurus) {
        if(brachiosaurus.isBaby()) {
            return new ResourceLocation(UnusualPrehistory.MODID, "animations/brachiosaurus/brachiosaurus_baby.animation.json");
        }
//        if(brachiosaurus.shouldBeTeen()) {
//            return new ResourceLocation(UnusualPrehistory.MODID, "animations/brachiosaurus/brachiosaurus_teen.animation.json");
//        }
        else {
            return new ResourceLocation(UnusualPrehistory.MODID, "animations/brachiosaurus/brachiosaurus.animation.json");
        }
    }

    @Override
    public void setCustomAnimations(BrachiosaurusEntity animatable, long instanceId, AnimationState<BrachiosaurusEntity> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);
        if (animationState == null) return;
        EntityModelData extraDataOfType = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
        CoreGeoBone head = this.getAnimationProcessor().getBone("Head");
        head.setRotY(extraDataOfType.netHeadYaw() * Mth.DEG_TO_RAD);
    }
}

