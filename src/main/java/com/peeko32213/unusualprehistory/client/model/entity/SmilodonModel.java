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

    private static final ResourceLocation TEXTURE_NORMAL = new ResourceLocation("unusualprehistory:textures/entity/smilodon/smilodon.png");
    private static final ResourceLocation TEXTURE_OCELOT = new ResourceLocation("unusualprehistory:textures/entity/smilodon/smilodon_ocelot.png");

    private static final ResourceLocation TEXTURE_NORMAL_BABY = new ResourceLocation("unusualprehistory:textures/entity/smilodon/smilodon_baby.png");
    private static final ResourceLocation TEXTURE_OCELOT_BABY = new ResourceLocation("unusualprehistory:textures/entity/smilodon/smilodon_ocelot_baby.png");

    @Override
    public ResourceLocation getModelResource(SmilodonEntity smilodon) {
        if(smilodon.isBaby()){
            return new ResourceLocation(UnusualPrehistory.MODID, "geo/smilodon/smilodon_baby.geo.json");
        } else {
            return new ResourceLocation(UnusualPrehistory.MODID, "geo/smilodon/smilodon.geo.json");
        }
    }

    @Override
    public ResourceLocation getTextureResource(SmilodonEntity smilodon)
    {
        if (smilodon.isBaby()) {
            return switch (smilodon.getVariant()) {
                case 1 -> TEXTURE_OCELOT_BABY;
                default -> TEXTURE_NORMAL_BABY;
            };
        } else {
            return switch (smilodon.getVariant()) {
                case 1 -> TEXTURE_OCELOT;
                default -> TEXTURE_NORMAL;
            };
        }
    }

    @Override
    public ResourceLocation getAnimationResource(SmilodonEntity smilodon) {
        if(smilodon.isBaby()){
            return new ResourceLocation(UnusualPrehistory.MODID, "animations/smilodon/smilodon_baby.animation.json");
        } else {
            return new ResourceLocation(UnusualPrehistory.MODID, "animations/smilodon/smilodon.animation.json");
        }
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

