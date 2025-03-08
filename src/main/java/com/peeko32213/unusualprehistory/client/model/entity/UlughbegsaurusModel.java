package com.peeko32213.unusualprehistory.client.model.entity;


import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.UlughbegsaurusEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class UlughbegsaurusModel extends GeoModel<UlughbegsaurusEntity>
{
    private static final ResourceLocation TEXTURE_BLUE = new ResourceLocation("unusualprehistory:textures/entity/ulughbegsaurus/ulughbegsaurus_blue.png");
    private static final ResourceLocation TEXTURE_YELLOW = new ResourceLocation("unusualprehistory:textures/entity/ulughbegsaurus/ulughbegsaurus_yellow.png");
    private static final ResourceLocation TEXTURE_ORANGE = new ResourceLocation("unusualprehistory:textures/entity/ulughbegsaurus/ulughbegsaurus_orange.png");
    private static final ResourceLocation TEXTURE_CYAN = new ResourceLocation("unusualprehistory:textures/entity/ulughbegsaurus/ulughbegsaurus_cyan.png");
    private static final ResourceLocation TEXTURE_GREEN = new ResourceLocation("unusualprehistory:textures/entity/ulughbegsaurus/ulughbegsaurus_green.png");
    private static final ResourceLocation TEXTURE_PINK = new ResourceLocation("unusualprehistory:textures/entity/ulughbegsaurus/ulughbegsaurus_pink.png");
    private static final ResourceLocation TEXTURE_TURQUOISE = new ResourceLocation("unusualprehistory:textures/entity/ulughbegsaurus/ulughbegsaurus_turquoise.png");
    private static final ResourceLocation TEXTURE_FURCHISA = new ResourceLocation("unusualprehistory:textures/entity/ulughbegsaurus/ulughbegsaurus_fuchisa.png");
    private static final ResourceLocation TEXTURE_LIME = new ResourceLocation("unusualprehistory:textures/entity/ulughbegsaurus/ulughbegsaurus_lime.png");
    private static final ResourceLocation TEXTURE_PURPLE = new ResourceLocation("unusualprehistory:textures/entity/ulughbegsaurus/ulughbegsaurus_purple.png");
    private static final ResourceLocation TEXTURE_RED = new ResourceLocation("unusualprehistory:textures/entity/ulughbegsaurus/ulughbegsaurus_red.png");
    private static final ResourceLocation TEXTURE_JEB = new ResourceLocation("unusualprehistory:textures/entity/ulughbegsaurus/ulughbegsaurus_jeb.png");

    @Override
    public ResourceLocation getModelResource(UlughbegsaurusEntity object) {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/ulughbegsaurus.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(UlughbegsaurusEntity object) {
        return switch (object.getVariant()) {
            case 1 -> TEXTURE_CYAN;
            case 2 -> TEXTURE_YELLOW;
            case 3 -> TEXTURE_ORANGE;
            case 4 -> TEXTURE_GREEN;
            case 5 -> TEXTURE_PINK;
            case 6 -> TEXTURE_TURQUOISE;
            case 7 -> TEXTURE_FURCHISA;
            case 8 -> TEXTURE_LIME;
            case 9 -> TEXTURE_PURPLE;
            case 10 -> TEXTURE_RED;
            case 11 -> TEXTURE_JEB;
            default -> TEXTURE_BLUE;
        };
    }

    @Override
    public ResourceLocation getAnimationResource(UlughbegsaurusEntity object) {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/ulughbegsaurus.animation.json");
    }

    @Override
    public void setCustomAnimations(UlughbegsaurusEntity animatable, long instanceId, AnimationState<UlughbegsaurusEntity> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);
        if (animationState == null) return;
        EntityModelData extraDataOfType = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

        CoreGeoBone neck = this.getAnimationProcessor().getBone("Ulugh_Neck");

        CoreGeoBone saddle = this.getAnimationProcessor().getBone("Ulugh_Saddle");

        CoreGeoBone head = this.getAnimationProcessor().getBone("Ulugh_Head");

        saddle.setHidden(!animatable.isSaddled());

        if (animatable.isBaby()) {
            head.setScaleX(1.5F);
            head.setScaleY(1.5F);
            head.setScaleZ(1.5F);
        } else {
            head.setScaleX(1.0F);
            head.setScaleY(1.0F);
            head.setScaleZ(1.0F);
        }

        if (!animatable.isSprinting()) {
            neck.setRotY(extraDataOfType.netHeadYaw() * Mth.DEG_TO_RAD);
        }
    }
}

