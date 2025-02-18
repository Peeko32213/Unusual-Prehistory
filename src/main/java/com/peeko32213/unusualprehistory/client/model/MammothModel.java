package com.peeko32213.unusualprehistory.client.model;


import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.EntityMammoth;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;


public class MammothModel extends GeoModel<EntityMammoth> {
    @Override
    public ResourceLocation getModelResource(EntityMammoth object) {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/mammoth/mammoth.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(EntityMammoth object) {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/mammoth/mammoth.png");
    }

    @Override
    public ResourceLocation getAnimationResource(EntityMammoth object) {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/mammoth/mammoth.animation.json");
    }

    @Override
    public void setCustomAnimations(EntityMammoth animatable, long instanceId, AnimationState<EntityMammoth> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);
        if (animationState == null) return;

        EntityModelData extraDataOfType = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
        CoreGeoBone head = this.getAnimationProcessor().getBone("Head");

        CoreGeoBone tusk1 = this.getAnimationProcessor().getBone("Tusk1");
        CoreGeoBone tusk2 = this.getAnimationProcessor().getBone("Tusk2");

//        CoreGeoBone trunk = this.getAnimationProcessor().getBone("Trunk");

        if (animatable.isBaby()) {
            head.setScaleX(1.25F);
            head.setScaleY(1.25F);
            head.setScaleZ(1.25F);
        } else {
            head.setScaleX(1.0F);
            head.setScaleY(1.0F);
            head.setScaleZ(1.0F);
        }

        tusk1.setHidden(animatable.isBaby());
        tusk2.setHidden(animatable.isBaby());

//        trunk.setHidden(animatable.isBaby());

        head.setRotY(extraDataOfType.netHeadYaw() * Mth.DEG_TO_RAD);
    }
}

