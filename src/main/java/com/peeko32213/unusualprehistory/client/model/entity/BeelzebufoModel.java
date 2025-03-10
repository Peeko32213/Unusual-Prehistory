package com.peeko32213.unusualprehistory.client.model.entity;


import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.CotylorhynchusEntity;
import com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.semi_aquatic.BeelzebufoEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

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

        CoreGeoBone saddle = this.getAnimationProcessor().getBone("Saddle");

        saddle.setHidden(!animatable.isSaddled());
    }
}

