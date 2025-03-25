package com.peeko32213.unusualprehistory.client.model.entity.prehistoric.semi_aquatic;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.semi_aquatic.BeelzebufoEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;

public class BeelzebufoModel extends GeoModel<BeelzebufoEntity> {

    @Override
    public ResourceLocation getModelResource(BeelzebufoEntity object) {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/beelzebufo.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(BeelzebufoEntity object) {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/beelzebufo.png");
    }

    @Override
    public ResourceLocation getAnimationResource(BeelzebufoEntity object) {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/beelzebufo.animation.json");
    }

    @Override
    public void setCustomAnimations(BeelzebufoEntity animatable, long instanceId, AnimationState<BeelzebufoEntity> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);

        CoreGeoBone saddle = this.getAnimationProcessor().getBone("saddle");

        saddle.setHidden(!animatable.isSaddled());
    }
}

