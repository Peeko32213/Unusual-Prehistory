package com.peeko32213.unusualprehistory.client.model.entity;

import com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.aquatic.DunkleosteusEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class DunkleosteusModel extends GeoModel<DunkleosteusEntity> {

    private static final ResourceLocation TEXTURE_TERRELLI = new ResourceLocation("unusualprehistory:textures/entity/dunkleosteus/dunkleosteus_terrelli.png");
    private static final ResourceLocation TEXTURE_MARSAISI = new ResourceLocation("unusualprehistory:textures/entity/dunkleosteus/dunkleosteus_marsaisi.png");
    private static final ResourceLocation TEXTURE_RAVERI = new ResourceLocation("unusualprehistory:textures/entity/dunkleosteus/dunkleosteus_raveri.png");

    private static final ResourceLocation MODEL_TERRELLI = new ResourceLocation("unusualprehistory:geo/dunkleosteus/dunkleosteus_terrelli.geo.json");
    private static final ResourceLocation MODEL_MARSAISI = new ResourceLocation("unusualprehistory:geo/dunkleosteus/dunkleosteus_marsaisi.geo.json");
    private static final ResourceLocation MODEL_RAVERI = new ResourceLocation("unusualprehistory:geo/dunkleosteus/dunkleosteus_raveri.geo.json");

    private static final ResourceLocation ANIMATION_TERRELLI = new ResourceLocation("unusualprehistory:animations/dunkleosteus/dunkleosteus_terrelli.animation.json");
    private static final ResourceLocation ANIMATION_MARSAISI = new ResourceLocation("unusualprehistory:animations/dunkleosteus/dunkleosteus_marsaisi.animation.json");
    private static final ResourceLocation ANIMATION_RAVERI = new ResourceLocation("unusualprehistory:animations/dunkleosteus/dunkleosteus_raveri.animation.json");

    @Override
    public ResourceLocation getModelResource(DunkleosteusEntity object) {
        return switch (object.getVariant()) {
            case 1 -> MODEL_MARSAISI;
            case 2 -> MODEL_RAVERI;
            default -> MODEL_TERRELLI;
        };
    }

    @Override
    public ResourceLocation getTextureResource(DunkleosteusEntity object) {
        return switch (object.getVariant()) {
            case 1 -> TEXTURE_MARSAISI;
            case 2 -> TEXTURE_RAVERI;
            default -> TEXTURE_TERRELLI;
        };
    }

    @Override
    public ResourceLocation getAnimationResource(DunkleosteusEntity object) {
        return switch (object.getVariant()) {
            case 1 -> ANIMATION_MARSAISI;
            case 2 -> ANIMATION_RAVERI;
            default -> ANIMATION_TERRELLI;
        };
    }

    @Override
    public void setCustomAnimations(DunkleosteusEntity animatable, long instanceId, AnimationState<DunkleosteusEntity> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);
        if (animationState == null) return;
        if(animatable.isFromBook()) return;

        CoreGeoBone backBody = this.getAnimationProcessor().getBone("Dunk_Tail");
        EntityModelData extraDataOfType = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

        CoreGeoBone root = this.getAnimationProcessor().getBone("Dunkleosteus");

        root.setRotX(extraDataOfType.headPitch() * (Mth.DEG_TO_RAD / 2));

        backBody.setRotY(backBody.getRotY() + extraDataOfType.netHeadYaw() * ((float) Math.PI / 270F));

    }
}

