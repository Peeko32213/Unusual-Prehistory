package com.peeko32213.unusualprehistory.client.model.entity;


import com.peeko32213.unusualprehistory.MathHelpers;
import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.aquatic.GlobidensEntity;
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
    public void setCustomAnimations(GlobidensEntity entity, long instanceId, AnimationState<GlobidensEntity> animationState) {
        super.setCustomAnimations(entity, instanceId, animationState);
        if (animationState == null) return;
        EntityModelData extraDataOfType = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
        CoreGeoBone head = this.getAnimationProcessor().getBone("Head");
        if (entity.isBaby()) {
            head.setScaleX(1.75F);
            head.setScaleY(1.75F);
            head.setScaleZ(1.75F);
        } else {
            head.setScaleX(1.0F);
            head.setScaleY(1.0F);
            head.setScaleZ(1.0F);
        }
//        if (!entity.isSprinting()) {
//            head.setRotY(extraDataOfType.netHeadYaw() * Mth.DEG_TO_RAD);
//        }

        if (entity.isInWaterOrBubble()) {
            CoreGeoBone tail = this.getAnimationProcessor().getBone("MidBody");
            CoreGeoBone tailfin = this.getAnimationProcessor().getBone("Tail");

            CoreGeoBone root = this.getAnimationProcessor().getBone("root");
            root.setRotZ(Mth.clamp(Mth.lerp(0.1F, Mth.cos(entity.yBodyRot * 0.1F) * 0.1F, 1.0F), -15F, 15F));

            head.setRotY((float) (Mth.PI + (MathHelpers.LerpDegrees((float) entity.currentTail1Yaw, (float) entity.tail1Angle, 0.05))));
            tail.setRotY((float) (Mth.PI - (MathHelpers.LerpDegrees((float) entity.currentTail1Yaw, (float) entity.tail1Angle, 0.05))));
            tailfin.setRotY((float) (Mth.PI - (MathHelpers.LerpDegrees((float) entity.currentTail2Yaw, (float) entity.tail2Angle, 0.05))));
            entity.currentTail1Yaw = (float) MathHelpers.LerpDegrees((float) entity.currentTail1Yaw, (float) entity.tail1Angle, 0.05);
            entity.currentTail2Yaw = (float) MathHelpers.LerpDegrees((float) entity.currentTail2Yaw, (float) entity.tail2Angle, 0.05);
            //System.out.println(currentTail1Yaw);
            //this runs BETWEEN TICKS
            //0.25 means it interpolates to a quarter of the way to the target

            //No deg to rad because the arccos function used to return the angle
            //gotta set up UNIQUE NODES FOR EACH BONE


            root.setRotX((float) (MathHelpers.LerpDegrees((float) entity.currentBodyPitch, (float) entity.bodyPitch, 0.005)));
            entity.currentBodyPitch = (float) MathHelpers.LerpDegrees((float) entity.currentBodyPitch, (float) entity.bodyPitch, 0.005);

            double offset = 0;
            if ((!entity.isSprinting())) {
                offset = Mth.HALF_PI/2;
            } else {
                offset = 0;
            }
            tail.setRotX((float) (tail.getRotX() - offset + MathHelpers.LerpDegrees((float) entity.currentTail1Pitch, (float) entity.tail1Pitch, 0.005)));
            tailfin.setRotX((float) (tailfin.getRotX() + MathHelpers.LerpDegrees((float) entity.currentTail2Pitch, (float) entity.tail2Pitch, 0.005)));
            entity.currentTail1Pitch = (float) MathHelpers.LerpDegrees((float) entity.currentTail1Pitch, (float) entity.tail1Pitch, 0.005);
            entity.currentTail2Pitch = (float) MathHelpers.LerpDegrees((float) entity.currentTail2Pitch, (float) entity.tail2Pitch, 0.005);

            //positive RotX is DOWNWARDS, and increasing angle swings it forwards towards the head


        }
    }
}

