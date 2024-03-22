package com.peeko32213.unusualprehistory.client.model;


import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.EntityTanystropheus;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class TanystropheusModel extends GeoModel<EntityTanystropheus>
{
    @Override
    public ResourceLocation getModelResource(EntityTanystropheus object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/tanystropheus.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(EntityTanystropheus object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/tanystropheus.png");
    }

    @Override
    public ResourceLocation getAnimationResource(EntityTanystropheus object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/tanystropheus.animation.json");
    }

    @Override
    public void setCustomAnimations(EntityTanystropheus animatable, long instanceId, AnimationState<EntityTanystropheus> animationState) {
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
        CoreGeoBone tail = this.getAnimationProcessor().getBone("Tail");
        CoreGeoBone tailfin = this.getAnimationProcessor().getBone("TailFin");
        CoreGeoBone neck = this.getAnimationProcessor().getBone("Neck");
        CoreGeoBone neckfront = this.getAnimationProcessor().getBone("NeckFront");
        CoreGeoBone root = this.getAnimationProcessor().getBone("Body");

        if (animatable.isInWater()) {
            root.setRotX(extraDataOfType.headPitch() * (Mth.DEG_TO_RAD / 7));
            root.setRotZ(Mth.clamp(Mth.lerp(0.1F, Mth.cos(animatable.yBodyRot * 0.1F) * 0.1F, 1.0F), -15F, 15F));
            tail.setRotY(tail.getRotY() + extraDataOfType.netHeadYaw() * ((float) Math.PI / 270F));
            tailfin.setRotZ(tailfin.getRotY() + extraDataOfType.netHeadYaw() * ((float) Math.PI / 270F));
            neck.setRotY(neck.getRotY() + extraDataOfType.netHeadYaw() * ((float) Math.PI / 270F));
            neckfront.setRotZ(neckfront.getRotY() + extraDataOfType.netHeadYaw() * ((float) Math.PI / 270F));

            //dynamic pitch
            CoreGeoBone trueroot = this.getAnimationProcessor().getBone("root");
            trueroot.setRotX(extraDataOfType.headPitch() * (float)Math.PI / 180F);
            //end of dynamic pitch
        }
    }

}

