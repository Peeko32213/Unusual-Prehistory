package com.peeko32213.unusualprehistory.client.model.entity.prehistoric.aquatic;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.client.model.ExtendedMolangQueriesModel;
import com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.aquatic.ScaumenaciaEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.data.EntityModelData;

public class ScaumenaciaModel extends ExtendedMolangQueriesModel<ScaumenaciaEntity> {
    @Override
    public ResourceLocation getModelResource(ScaumenaciaEntity object) {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/scaumenacia.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(ScaumenaciaEntity object) {
        if (object.getVariant() == 1) {
            return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/scaumenacia/scaumenacia_buddah.png");
        }
        else return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/scaumenacia/scaumenacia.png");
    }

    @Override
    public ResourceLocation getAnimationResource(ScaumenaciaEntity object) {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/scaumenacia.animation.json");
    }

    @Override
    public void setCustomAnimations(ScaumenaciaEntity animatable, long instanceId, AnimationState<ScaumenaciaEntity> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);
        CoreGeoBone swimControl = this.getAnimationProcessor().getBone("swim_control");

        EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

        swimControl.setRotX(((entityData.headPitch() * ((float) Math.PI / 180F))));
        swimControl.setRotZ(-((entityData.netHeadYaw() * ((float) Math.PI / 180F)) / 2));
    }
}

