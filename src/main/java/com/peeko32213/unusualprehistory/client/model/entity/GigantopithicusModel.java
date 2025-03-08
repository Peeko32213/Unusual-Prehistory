package com.peeko32213.unusualprehistory.client.model.entity;


import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.GigantopithicusEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;


public class GigantopithicusModel extends GeoModel<GigantopithicusEntity> {

    @Override
    public ResourceLocation getModelResource(GigantopithicusEntity gigantopithicus) {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/gigantopithicus/gigantopithicus.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(GigantopithicusEntity gigantopithicus) {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/gigantopithicus/gigantopithicus.png");
    }

    @Override
    public ResourceLocation getAnimationResource(GigantopithicusEntity gigantopithicus) {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/gigantopithicus/gigantopithicus.animation.json");
    }

    public RenderType getRenderType(GigantopithicusEntity animatable, ResourceLocation texture) {
        return RenderType.entityTranslucent(getTextureResource(animatable));
    }

    @Override
    public void setCustomAnimations(GigantopithicusEntity animatable, long instanceId, AnimationState<GigantopithicusEntity> animationState) {
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
    }
}

