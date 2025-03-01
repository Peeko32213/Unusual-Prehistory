package com.peeko32213.unusualprehistory.client.model;


import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.XiphactinusEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class XiphactinusModel extends GeoModel<XiphactinusEntity> {

    @Override
    public ResourceLocation getModelResource(XiphactinusEntity object) {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/xiphactinus.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(XiphactinusEntity object) {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/xiphactinus.png");
    }

    @Override
    public ResourceLocation getAnimationResource(XiphactinusEntity object) {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/xiphactinus.animation.json");
    }

    @Override
    public void setCustomAnimations(XiphactinusEntity animatable, long instanceId, AnimationState<XiphactinusEntity> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);
        if (animationState == null) return;
        if(animatable.isFromBook()) return;

        if (animatable.isInWaterOrBubble()) {
            CoreGeoBone body = this.getAnimationProcessor().getBone("Body");
            CoreGeoBone tail = this.getAnimationProcessor().getBone("TailBody");
            EntityModelData extraDataOfType = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

            CoreGeoBone root = this.getAnimationProcessor().getBone("root");
            root.setRotX(extraDataOfType.headPitch() * (Mth.DEG_TO_RAD / 2));

            body.setRotZ(body.getRotY() + extraDataOfType.netHeadYaw() * ((float) Math.PI / 270F));
            tail.setRotY(tail.getRotY() + extraDataOfType.netHeadYaw() * ((float) Math.PI / 270F));
        }
    }
}

