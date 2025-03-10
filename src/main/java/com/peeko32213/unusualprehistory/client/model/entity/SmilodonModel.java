package com.peeko32213.unusualprehistory.client.model.entity;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.SmilodonEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class SmilodonModel extends GeoModel<SmilodonEntity> {

    private static final ResourceLocation TEXTURE_COLD = new ResourceLocation("unusualprehistory:textures/entity/smilodon/smilodon_cold.png");
    private static final ResourceLocation TEXTURE_WARM = new ResourceLocation("unusualprehistory:textures/entity/smilodon/smilodon_warm.png");
    private static final ResourceLocation TEXTURE_REGAL = new ResourceLocation("unusualprehistory:textures/entity/smilodon/smilodon_regal.png");

    @Override
    public ResourceLocation getModelResource(SmilodonEntity smilodon) {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/smilodon.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(SmilodonEntity smilodon) {
        return switch (smilodon.getVariant()) {
            case 1 -> TEXTURE_WARM;
            case 2 -> TEXTURE_REGAL;
            default -> TEXTURE_COLD;
        };
    }

    @Override
    public ResourceLocation getAnimationResource(SmilodonEntity smilodon) {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/smilodon.animation.json");
    }

    @Override
    public void setCustomAnimations(SmilodonEntity animatable, long instanceId, AnimationState<SmilodonEntity> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);
        if (animationState == null) return;
        EntityModelData extraDataOfType = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
        CoreGeoBone head = this.getAnimationProcessor().getBone("Head");
        head.setRotY(extraDataOfType.netHeadYaw() * Mth.DEG_TO_RAD);
    }
}

