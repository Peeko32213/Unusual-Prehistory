package com.peeko32213.unusualprehistory.client.model;


import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.msc.baby.PalaeolophisHatchlingEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class BabyPalaeophisModel extends GeoModel<PalaeolophisHatchlingEntity> {

    @Override
    public ResourceLocation getModelResource(PalaeolophisHatchlingEntity object) {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/palaeophis/palaeophis_baby.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(PalaeolophisHatchlingEntity object) {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/palaeophis/palaeophis_baby.png");
    }

    @Override
    public ResourceLocation getAnimationResource(PalaeolophisHatchlingEntity object) {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/palaeophis/palaeophis_baby.animation.json");
    }

    @Override
    public void setCustomAnimations(PalaeolophisHatchlingEntity animatable, long instanceId, AnimationState<PalaeolophisHatchlingEntity> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);
        if (animationState == null) return;
        if(animatable.isFromBook()) return;
        CoreGeoBone body = this.getAnimationProcessor().getBone("Body");
        CoreGeoBone tailfin = this.getAnimationProcessor().getBone("TailBody");
        EntityModelData extraDataOfType = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

        CoreGeoBone root = this.getAnimationProcessor().getBone("Snake");
        root.setRotX(extraDataOfType.headPitch() * (Mth.DEG_TO_RAD / 7));
        root.setRotZ(Mth.clamp(Mth.lerp(0.1F, Mth.cos(animatable.yBodyRot * 0.1F) * 0.1F, 1.0F), -15F, 15F));

        body.setRotY(body.getRotY() + extraDataOfType.netHeadYaw() * ((float) Math.PI / 270F));
        tailfin.setRotZ(tailfin.getRotY() + extraDataOfType.netHeadYaw() * ((float) Math.PI / 270F));

    }
}

