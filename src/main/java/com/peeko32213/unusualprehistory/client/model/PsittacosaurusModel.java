package com.peeko32213.unusualprehistory.client.model;


import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.PsittacosaurusEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class PsittacosaurusModel extends GeoModel<PsittacosaurusEntity>
{
    @Override
    public ResourceLocation getModelResource(PsittacosaurusEntity object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/psittacosaurus.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(PsittacosaurusEntity object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/psittacosaurus.png");
    }

    @Override
    public ResourceLocation getAnimationResource(PsittacosaurusEntity object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/psittacosaurus.animation.json");
    }

    @Override
    public void setCustomAnimations(PsittacosaurusEntity animatable, long instanceId, AnimationState<PsittacosaurusEntity> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);
        if (animationState == null) return;
        EntityModelData extraDataOfType = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
        CoreGeoBone head = this.getAnimationProcessor().getBone("Head");
        CoreGeoBone root = this.getAnimationProcessor().getBone("root");
        if (animatable.isBaby()) {
            head.setScaleX(1.75F);
            head.setScaleY(1.75F);
            head.setScaleZ(1.75F);

            root.setScaleX(0.5F);
            root.setScaleY(0.5F);
            root.setScaleZ(0.5F);

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

