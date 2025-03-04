package com.peeko32213.unusualprehistory.client.model.entity;


import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.LeedsichthysEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;


public class LeedsichthysModel extends GeoModel<LeedsichthysEntity> {
    @Override
    public ResourceLocation getModelResource(LeedsichthysEntity object) {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/leedsichthys.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(LeedsichthysEntity object) {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/leedsichthys.png");
    }

    @Override
    public ResourceLocation getAnimationResource(LeedsichthysEntity object) {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/leedsichthys.animation.json");
    }

    @Override
    public void setCustomAnimations(LeedsichthysEntity animatable, long instanceId, AnimationState<LeedsichthysEntity> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);
        if (animationState == null) return;

        CoreGeoBone backBody = this.getAnimationProcessor().getBone("BackBody");
        CoreGeoBone tailfin = this.getAnimationProcessor().getBone("Tail");
        EntityModelData extraDataOfType = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

        CoreGeoBone root = this.getAnimationProcessor().getBone("FrontBody");
        root.setRotX(extraDataOfType.headPitch() * (Mth.DEG_TO_RAD / 7));
        root.setRotZ(Mth.clamp(Mth.lerp(0.1F, Mth.cos(animatable.yBodyRot * 0.1F) * 0.1F, 1.0F), -15F, 15F));

        backBody.setRotY(backBody.getRotY() + extraDataOfType.netHeadYaw() * ((float) Math.PI / 180F));
        tailfin.setRotZ(tailfin.getRotY() + extraDataOfType.netHeadYaw() * ((float) Math.PI / 180F));

        if (animatable.isBaby()) {
            root.setScaleX(0.5F);
            root.setScaleY(0.5F);
            root.setScaleZ(0.5F);
        } else {
            root.setScaleX(1.0F);
            root.setScaleY(1.0F);
            root.setScaleZ(1.0F);
        }

    }

}

