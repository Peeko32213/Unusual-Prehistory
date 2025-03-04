package com.peeko32213.unusualprehistory.client.model.entity;


import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.GlobidensEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class GlobidensModel extends GeoModel<GlobidensEntity>
{
    @Override
    public ResourceLocation getModelResource(GlobidensEntity object) {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/globidens.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(GlobidensEntity object) {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/globidens.png");
    }

    @Override
    public ResourceLocation getAnimationResource(GlobidensEntity object) {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/globidens.animation.json");
    }

    @Override
    public void setCustomAnimations(GlobidensEntity animatable, long instanceId, AnimationState<GlobidensEntity> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);
        if (animationState == null) return;
        EntityModelData extraDataOfType = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
        CoreGeoBone head = this.getAnimationProcessor().getBone("Head");
        if (animatable.isBaby()) {
            head.setScaleX(1.75F);
            head.setScaleY(1.75F);
            head.setScaleZ(1.75F);
        } else {
            head.setScaleX(1.0F);
            head.setScaleY(1.0F);
            head.setScaleZ(1.0F);
        }
        if (!animatable.isSprinting()) {
            head.setRotY(extraDataOfType.netHeadYaw() * Mth.DEG_TO_RAD);
        }

        if (animatable.isInWaterOrBubble()) {
            CoreGeoBone backBody = this.getAnimationProcessor().getBone("MidBody");
            CoreGeoBone tailfin = this.getAnimationProcessor().getBone("Tail");

            CoreGeoBone root = this.getAnimationProcessor().getBone("root");
            root.setRotX(extraDataOfType.headPitch() * (Mth.DEG_TO_RAD / 7));
            root.setRotZ(Mth.clamp(Mth.lerp(0.1F, Mth.cos(animatable.yBodyRot * 0.1F) * 0.1F, 1.0F), -15F, 15F));

            backBody.setRotY(backBody.getRotY() + extraDataOfType.netHeadYaw() * ((float) Math.PI / 270F));
            tailfin.setRotZ(tailfin.getRotY() + extraDataOfType.netHeadYaw() * ((float) Math.PI / 270F));
        }
    }
}

