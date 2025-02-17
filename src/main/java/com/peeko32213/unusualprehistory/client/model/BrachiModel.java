package com.peeko32213.unusualprehistory.client.model;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.EntityBrachiosaurus;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class BrachiModel extends GeoModel<EntityBrachiosaurus> {

    @Override
    public ResourceLocation getModelResource(EntityBrachiosaurus brachiosaurus) {
        if(brachiosaurus.getAge() < -12000 && brachiosaurus.isBaby()) {
            return new ResourceLocation(UnusualPrehistory.MODID, "geo/brachiosaurus/brachiosaurus_baby.geo.json");
        }
        if(brachiosaurus.getAge() < 0 && brachiosaurus.isBaby()) {
            return new ResourceLocation(UnusualPrehistory.MODID, "geo/brachiosaurus/brachiosaurus_teen.geo.json");
        }

        else if(!brachiosaurus.isBaby()) {
            return new ResourceLocation(UnusualPrehistory.MODID, "geo/brachiosaurus/brachiosaurus.geo.json");
        }
        else {
            return new ResourceLocation(UnusualPrehistory.MODID, "geo/brachiosaurus/brachiosaurus.geo.json");
        }
    }

    @Override
    public ResourceLocation getTextureResource(EntityBrachiosaurus brachiosaurus){
        if(brachiosaurus.getAge() < -12000 && brachiosaurus.isBaby()) {
            return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/brachiosaurus/brachiosaurus_baby.png");
        }
        if(brachiosaurus.getAge() < 0 && brachiosaurus.isBaby()) {
            return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/brachiosaurus/brachiosaurus_teen.png");
        }

        else if(!brachiosaurus.isBaby()) {
            return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/brachiosaurus/brachiosaurus.png");
        }
        else {
            return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/brachiosaurus/brachiosaurus.png");
        }
    }

    @Override
    public ResourceLocation getAnimationResource(EntityBrachiosaurus brachiosaurus) {
        if(brachiosaurus.getAge() < -12000 && brachiosaurus.isBaby()) {
            return new ResourceLocation(UnusualPrehistory.MODID, "animations/brachiosaurus/brachiosaurus_baby.animation.json");
        }
        if(brachiosaurus.getAge() < 0 && brachiosaurus.isBaby()) {
            return new ResourceLocation(UnusualPrehistory.MODID, "animations/brachiosaurus/brachiosaurus_teen.animation.json");
        }

        else if(!brachiosaurus.isBaby()) {
            return new ResourceLocation(UnusualPrehistory.MODID, "animations/brachiosaurus/brachiosaurus.animation.json");
        }
        else {
            return new ResourceLocation(UnusualPrehistory.MODID, "animations/brachiosaurus/brachiosaurus.animation.json");
        }
    }

    @Override
    public void setCustomAnimations(EntityBrachiosaurus animatable, long instanceId, AnimationState<EntityBrachiosaurus> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);
        if (animationState == null) return;
        EntityModelData extraDataOfType = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
        CoreGeoBone head = this.getAnimationProcessor().getBone("Head");
        head.setRotY(extraDataOfType.netHeadYaw() * Mth.DEG_TO_RAD);
    }
}

