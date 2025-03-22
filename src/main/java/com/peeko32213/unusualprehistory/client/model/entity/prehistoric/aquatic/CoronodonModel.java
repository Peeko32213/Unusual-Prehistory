package com.peeko32213.unusualprehistory.client.model.entity.prehistoric.aquatic;

import com.peeko32213.unusualprehistory.core.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.aquatic.CoronodonEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class CoronodonModel extends GeoModel<CoronodonEntity> {

    @Override
    public ResourceLocation getModelResource(CoronodonEntity object) {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/coronodon.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(CoronodonEntity object) {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/coronodon.png");
    }

    @Override
    public ResourceLocation getAnimationResource(CoronodonEntity object) {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/coronodon.animation.json");
    }

    @Override
    public void setCustomAnimations(CoronodonEntity animatable, long instanceId, AnimationState<CoronodonEntity> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);

        CoreGeoBone core = this.getAnimationProcessor().getBone("root");
        EntityModelData extraData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

        core.setRotX(extraData.headPitch() * (Mth.DEG_TO_RAD / 2));
    }
}
