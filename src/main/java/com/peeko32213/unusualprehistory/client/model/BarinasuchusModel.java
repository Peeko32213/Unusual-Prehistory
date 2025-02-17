package com.peeko32213.unusualprehistory.client.model;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.EntityBarinasuchus;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class BarinasuchusModel extends GeoModel<EntityBarinasuchus> {

    @Override
    public ResourceLocation getModelResource(EntityBarinasuchus barinasuchus)
    {
        if(barinasuchus.isBaby()){
            return new ResourceLocation(UnusualPrehistory.MODID, "geo/barinasuchus/barinasuchus_baby.geo.json");
        } else {
            return new ResourceLocation(UnusualPrehistory.MODID, "geo/barinasuchus/barinasuchus.geo.json");
        }
    }

    @Override
    public ResourceLocation getTextureResource(EntityBarinasuchus barinasuchus)
    {
        if(barinasuchus.isBaby()) {
            return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/barinasuchus/barinasuchus_baby.png");
        } else{
            return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/barinasuchus/barinasuchus.png");
        }
    }

    @Override
    public ResourceLocation getAnimationResource(EntityBarinasuchus barinasuchus) {
        if(barinasuchus.isBaby()){
            return new ResourceLocation(UnusualPrehistory.MODID, "animations/barinasuchus/barinasuchus_baby.animation.json");
        } else {
            return new ResourceLocation(UnusualPrehistory.MODID, "animations/barinasuchus/barinasuchus.animation.json");
        }
    }

    @Override
    public void setCustomAnimations(EntityBarinasuchus animatable, long instanceId, AnimationState<EntityBarinasuchus> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);
        if (animationState == null) return;
        EntityModelData extraDataOfType = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
        CoreGeoBone head = this.getAnimationProcessor().getBone("Head");
        head.setRotY(extraDataOfType.netHeadYaw() * Mth.DEG_TO_RAD);
    }
}

