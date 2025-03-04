package com.peeko32213.unusualprehistory.client.model.entity;


import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.EstemmenosuchusEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class EstemmenosuchusModel extends GeoModel<EstemmenosuchusEntity>
{
    @Override
    public ResourceLocation getModelResource(EstemmenosuchusEntity object) {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/estemmenosuchus.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(EstemmenosuchusEntity object) {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/estemmenosuchus.png");
    }

    @Override
    public ResourceLocation getAnimationResource(EstemmenosuchusEntity object) {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/estemmenosuchus.animation.json");
    }

    @Override
    public void setCustomAnimations(EstemmenosuchusEntity animatable, long instanceId, AnimationState<EstemmenosuchusEntity> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);
        if (animationState == null) return;
        EntityModelData extraDataOfType = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

        CoreGeoBone head = this.getAnimationProcessor().getBone("Head");
        CoreGeoBone headReal = this.getAnimationProcessor().getBone("RealHead");

        if (animatable.isBaby()) {
            headReal.setScaleX(1.5F);
            headReal.setScaleY(1.5F);
            headReal.setScaleZ(1.5F);
        } else {
            headReal.setScaleX(1.0F);
            headReal.setScaleY(1.0F);
            headReal.setScaleZ(1.0F);
        }

        if (!animatable.isSprinting()) {
            head.setRotY(extraDataOfType.netHeadYaw() * Mth.DEG_TO_RAD);
        }
    }

}

