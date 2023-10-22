package com.peeko32213.unusualprehistory.client.model;


import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.EntityCotylorhynchus;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class CotylorhynchusModel extends GeoModel<EntityCotylorhynchus>
{
    @Override
    public ResourceLocation getModelResource(EntityCotylorhynchus object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/cotylorhynchus.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(EntityCotylorhynchus object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/cotylorhynchus.png");
    }

    @Override
    public ResourceLocation getAnimationResource(EntityCotylorhynchus object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/coty.animation.json");
    }

    @Override
    public void setCustomAnimations(EntityCotylorhynchus animatable, long instanceId, AnimationState<EntityCotylorhynchus> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);
        if (animationState == null) return;
        EntityModelData extraDataOfType = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
        CoreGeoBone head = this.getAnimationProcessor().getBone("Head");

        if (animatable.isBaby()) {
            head.setScaleX(1.75F);
            head.setScaleY(1.75F);
            head.setScaleZ(1.75F);
        }
        if (!animatable.isSprinting()) {
            head.setRotY(extraDataOfType.netHeadYaw() * Mth.DEG_TO_RAD);
        }
    }

}

