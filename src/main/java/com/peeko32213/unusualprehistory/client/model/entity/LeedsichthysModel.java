package com.peeko32213.unusualprehistory.client.model.entity;


import com.peeko32213.unusualprehistory.MathHelpers;
import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.aquatic.LeedsichthysEntity;
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
    public void setCustomAnimations(LeedsichthysEntity entity, long instanceId, AnimationState<LeedsichthysEntity> animationState) {
        super.setCustomAnimations(entity, instanceId, animationState);
        if (animationState == null) return;

<<<<<<< Updated upstream
<<<<<<< Updated upstream
//        CoreGeoBone backBody = this.getAnimationProcessor().getBone("BackBody");
=======
=======
>>>>>>> Stashed changes
        CoreGeoBone tail = this.getAnimationProcessor().getBone("BackBody");
        CoreGeoBone tailfin = this.getAnimationProcessor().getBone("Tail");
>>>>>>> Stashed changes
        EntityModelData extraDataOfType = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

        CoreGeoBone root = this.getAnimationProcessor().getBone("root");

<<<<<<< Updated upstream
<<<<<<< Updated upstream
//        backBody.setRotY(backBody.getRotY() + extraDataOfType.netHeadYaw() * ((float) Math.PI / 180F));
=======
>>>>>>> Stashed changes
=======
>>>>>>> Stashed changes

        if (entity.isBaby()) {
            root.setScaleX(0.5F);
            root.setScaleY(0.5F);
            root.setScaleZ(0.5F);
        } else {
            root.setScaleX(1.0F);
            root.setScaleY(1.0F);
            root.setScaleZ(1.0F);
        }

        if (entity.isInWaterOrBubble()) {
            tail.setRotY((float) (Mth.PI - (MathHelpers.LerpDegrees((float) entity.currentTail1Yaw, (float) entity.tail1Yaw, 0.01))));
            tailfin.setRotY((float) (Mth.PI - (MathHelpers.LerpDegrees((float) entity.currentTail2Yaw, (float) entity.tail2Yaw, 0.01))));
            entity.currentTail1Yaw = (float) MathHelpers.LerpDegrees((float) entity.currentTail1Yaw, (float) entity.tail1Yaw, 0.01);
            entity.currentTail2Yaw = (float) MathHelpers.LerpDegrees((float) entity.currentTail2Yaw, (float) entity.tail2Yaw, 0.01);
            //this runs BETWEEN TICKS
            //0.25 means it interpolates to a quarter of the way to the target

            //No deg to rad because the arccos function used to return the angle
            //gotta set up UNIQUE NODES FOR EACH BONE

            tail.setRotX((float) (MathHelpers.LerpDegrees((float) entity.currentTail1Pitch, (float) entity.tail1Pitch, 0.01)));
            tailfin.setRotX((float) (tailfin.getRotX() + MathHelpers.LerpDegrees((float) entity.currentTail2Pitch, (float) entity.tail2Pitch, 0.01)));
            entity.currentTail1Pitch = (float) MathHelpers.LerpDegrees((float) entity.currentTail1Pitch, (float) entity.currentTail1Pitch, 0.01);
            entity.currentTail2Pitch = (float) MathHelpers.LerpDegrees((float) entity.currentTail2Pitch, (float) entity.tail2Pitch, 0.01);

            //positive RotX is DOWNWARDS, and increasing angle swings it forwards towards the head


        }

    }

}

