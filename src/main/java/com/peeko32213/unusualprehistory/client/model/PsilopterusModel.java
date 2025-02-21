package com.peeko32213.unusualprehistory.client.model;


import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.PsilopterusEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

import static com.peeko32213.unusualprehistory.UnusualPrehistory.prefix;

public class PsilopterusModel extends GeoModel<PsilopterusEntity> {

    private static final ResourceLocation NORMAL = prefix("textures/entity/psilopterus.png");
    private static final ResourceLocation DOMINATE = prefix("textures/entity/psilopterus_dominate.png");

    @Override
    public ResourceLocation getModelResource(PsilopterusEntity object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/psilopterus.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(PsilopterusEntity object)
    {
        if(object.isDominate()){
            return DOMINATE;
        }
        return NORMAL;
    }

    @Override
    public ResourceLocation getAnimationResource(PsilopterusEntity object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/psilopterus.animation.json");
    }

    @Override
    public void setCustomAnimations(PsilopterusEntity animatable, long instanceId, AnimationState<PsilopterusEntity> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);
        if (animationState == null) return;
        EntityModelData extraDataOfType = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
        CoreGeoBone head = this.getAnimationProcessor().getBone("Head");
        if (animatable.isBaby()) {
            //head.setScaleX(1.25F);
            //head.setScaleY(1.25F);
            //head.setScaleZ(1.25F);
        } else {
            head.setScaleX(1.0F);
            head.setScaleY(1.0F);
            head.setScaleZ(1.0F);
        }
        if (!animatable.isSprinting()) {
            head.setRotY(extraDataOfType.netHeadYaw() * Mth.DEG_TO_RAD);
        }
    }

}

