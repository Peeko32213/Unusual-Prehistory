package com.peeko32213.unusualprehistory.client.model.entity;


import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.semi_aquatic.HyneriaEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;


public class HyneriaModel extends GeoModel<HyneriaEntity>
{
    @Override
    public ResourceLocation getModelResource(HyneriaEntity object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/hyneria.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(HyneriaEntity object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/hyneria.png");
    }

    @Override
    public ResourceLocation getAnimationResource(HyneriaEntity object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/hyneria.animation.json");
    }

    @Override
    public void setCustomAnimations(HyneriaEntity animatable, long instanceId, AnimationState<HyneriaEntity> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);
        if (animationState == null) return;
        if (animatable.isInWaterOrBubble()) {
            CoreGeoBone backBody = this.getAnimationProcessor().getBone("TailBody");
            CoreGeoBone tailfin = this.getAnimationProcessor().getBone("Tail");
            EntityModelData extraDataOfType = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

            CoreGeoBone root = this.getAnimationProcessor().getBone("Body");
            root.setRotX(extraDataOfType.headPitch() * (Mth.DEG_TO_RAD / 7));
            root.setRotZ(Mth.clamp(Mth.lerp(0.1F, Mth.cos(animatable.yBodyRot * 0.1F) * 0.1F, 1.0F), -15F, 15F));

            backBody.setRotY(backBody.getRotY() + extraDataOfType.netHeadYaw() * ((float) Math.PI / 270F));
            tailfin.setRotZ(tailfin.getRotY() + extraDataOfType.netHeadYaw() * ((float) Math.PI / 270F));

//            CoreGeoBone head = this.getAnimationProcessor().getBone("Head");
//
//            CoreGeoBone jawtop = this.getAnimationProcessor().getBone("TopJaw");
//            CoreGeoBone jawlower = this.getAnimationProcessor().getBone("LowerJaw");
//
//            CoreGeoBone jawtopb = this.getAnimationProcessor().getBone("TopJaw_B");
//            CoreGeoBone jawlowerb = this.getAnimationProcessor().getBone("LowerJaw_B");
//
//            if (animatable.isBaby()) {
//                head.setScaleX(1.5F);
//                head.setScaleY(1.5F);
//                head.setScaleZ(1.5F);
//
//                jawtop.setScaleX(1.5F);
//                jawtop.setScaleY(1.5F);
//                jawtop.setScaleZ(1.5F);
//
//                jawlower.setScaleX(1.5F);
//                jawlower.setScaleY(1.5F);
//                jawlower.setScaleZ(1.5F);
//
//                jawtopb.setScaleX(1.5F);
//                jawtopb.setScaleY(1.5F);
//                jawtopb.setScaleZ(1.5F);
//
//                jawlowerb.setScaleX(1.5F);
//                jawlowerb.setScaleY(1.5F);
//                jawlowerb.setScaleZ(1.5F);
//            } else {
//                head.setScaleX(1.0F);
//                head.setScaleY(1.0F);
//                head.setScaleZ(1.0F);
//
//                jawtop.setScaleX(1.0F);
//                jawtop.setScaleY(1.0F);
//                jawtop.setScaleZ(1.0F);
//
//                jawlower.setScaleX(1.0F);
//                jawlower.setScaleY(1.0F);
//                jawlower.setScaleZ(1.0F);
//
//                jawtopb.setScaleX(1.0F);
//                jawtopb.setScaleY(1.0F);
//                jawtopb.setScaleZ(1.0F);
//
//                jawlowerb.setScaleX(1.0F);
//                jawlowerb.setScaleY(1.0F);
//                jawlowerb.setScaleZ(1.0F);
//            }

        }
    }

}

