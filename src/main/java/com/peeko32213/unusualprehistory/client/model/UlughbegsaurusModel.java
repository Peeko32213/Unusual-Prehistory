package com.peeko32213.unusualprehistory.client.model;


import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.UlughbegsaurusEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class UlughbegsaurusModel extends GeoModel<UlughbegsaurusEntity>
{
    private static final ResourceLocation TEXTURE_BLUE = new ResourceLocation("unusualprehistory:textures/entity/ulughbegsaurus.png");
    private static final ResourceLocation TEXTURE_YELLOW = new ResourceLocation("unusualprehistory:textures/entity/ulughbegsaurus_yellow.png");
    private static final ResourceLocation TEXTURE_ORANGE = new ResourceLocation("unusualprehistory:textures/entity/ulughbegsaurus_orange.png");
    private static final ResourceLocation TEXTURE_WHITE = new ResourceLocation("unusualprehistory:textures/entity/ulughbegsaurus_white.png");
    private static final ResourceLocation TEXTURE_BROWN = new ResourceLocation("unusualprehistory:textures/entity/ulughbegsaurus_brown.png");

    @Override
    public ResourceLocation getModelResource(UlughbegsaurusEntity object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/ulughbegsaurus.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(UlughbegsaurusEntity object)
    {
        return switch (object.getVariant()) {
            case 1 -> TEXTURE_WHITE;
            case 2 -> TEXTURE_YELLOW;
            case 3 -> TEXTURE_ORANGE;
            case 4 -> TEXTURE_BROWN;
            default -> TEXTURE_BLUE;
        };
    }

    @Override
    public ResourceLocation getAnimationResource(UlughbegsaurusEntity object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/ulughbegsaurus.animation.json");
    }

    @Override
    public void setCustomAnimations(UlughbegsaurusEntity animatable, long instanceId, AnimationState<UlughbegsaurusEntity> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);
        if (animationState == null) return;
        EntityModelData extraDataOfType = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
        CoreGeoBone head = this.getAnimationProcessor().getBone("Head");

        if (!animatable.isSprinting()) {
            head.setRotY(extraDataOfType.netHeadYaw() * Mth.DEG_TO_RAD);
        }
    }
}

