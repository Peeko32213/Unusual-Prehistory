package com.peeko32213.unusualprehistory.client.model;


import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.EntityAmmonite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class AmmoniteModel extends GeoModel<EntityAmmonite> {

    @Override
    public ResourceLocation getModelResource(EntityAmmonite ammonite) {
        if(ammonite.getVariant() == 1) {
            return new ResourceLocation(UnusualPrehistory.MODID, "geo/ammonite/ammonite_pinacoceras.geo.json");
        } else {
            return new ResourceLocation(UnusualPrehistory.MODID, "geo/ammonite/ammonite.geo.json");
        }
    }

    @Override
    public ResourceLocation getTextureResource(EntityAmmonite ammonite) {
        if(ammonite.getVariant() == 1) {
            return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/ammonite/ammonite_pinacoceras.png");
        } else {
            return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/ammonite/ammonite.png");
        }
    }

    @Override
    public ResourceLocation getAnimationResource(EntityAmmonite ammonite) {
        if(ammonite.getVariant() == 1) {
            return new ResourceLocation(UnusualPrehistory.MODID, "animations/ammonite/ammonite_pinacoceras.animation.json");
        } else {
            return new ResourceLocation(UnusualPrehistory.MODID, "animations/ammonite/ammonite.animation.json");
        }
    }

    @Override
    public void setCustomAnimations(EntityAmmonite animatable, long instanceId, AnimationState<EntityAmmonite> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);
        if (animationState == null) return;
        if (animatable.isFromBook()) return;
    }
}

