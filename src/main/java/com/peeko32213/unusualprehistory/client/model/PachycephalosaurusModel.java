package com.peeko32213.unusualprehistory.client.model;


import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.EntityKentrosaurus;
import com.peeko32213.unusualprehistory.common.entity.EntityPachycephalosaurus;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;


public class PachycephalosaurusModel extends GeoModel<EntityPachycephalosaurus>
{
    @Override
    public ResourceLocation getModelResource(EntityPachycephalosaurus object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/pachy.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(EntityPachycephalosaurus object)
    {
        if (object.isUlti()) {
            return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/pachycephalosaurus_ulti.png");
        }
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/pachycephalosaurus.png");
    }

    @Override
    public ResourceLocation getAnimationResource(EntityPachycephalosaurus object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/pachy.animation.json");
    }

    @Override
    public void setCustomAnimations(EntityPachycephalosaurus animatable, long instanceId, AnimationState<EntityPachycephalosaurus> animationState) {
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

