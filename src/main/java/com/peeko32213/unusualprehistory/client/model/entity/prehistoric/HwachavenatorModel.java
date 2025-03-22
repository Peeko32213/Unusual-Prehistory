package com.peeko32213.unusualprehistory.client.model.entity.prehistoric;

import com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.HwachavenatorEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

import static com.peeko32213.unusualprehistory.core.UnusualPrehistory.prefix;

public class HwachavenatorModel extends GeoModel<HwachavenatorEntity> {

    private static final ResourceLocation HWACHA_ACUTI_MODEL = prefix("geo/hwachavenator/hwachavenator_acuti.geo.json");
    private static final ResourceLocation HWACHA_TRUCULENTUS_MODEL = prefix("geo/hwachavenator/hwachavenator_truculentus.geo.json");
    private static final ResourceLocation HWACHA_VENENUM_MODEL = prefix("geo/hwachavenator/hwachavenator_venenum.geo.json");

    private static final ResourceLocation HWACHA_ACUTI_TEXTURE = prefix("textures/entity/hwachavenator/hwachavenator_acuti.png");
    private static final ResourceLocation HWACHA_TRUCULENTUS_TEXTURE = prefix("textures/entity/hwachavenator/hwachavenator_truculentus.png");
    private static final ResourceLocation HWACHA_VENENUM_TEXTURE = prefix("textures/entity/hwachavenator/hwachavenator_venenum.png");

    private static final ResourceLocation HWACHA_ACUTI_ANIMATION = prefix("animations/hwachavenator/hwachavenator_acuti.animation.json");
    private static final ResourceLocation HWACHA_TRUCULENTUS_ANIMATION = prefix("animations/hwachavenator/hwachavenator_truculentus.animation.json");
    private static final ResourceLocation HWACHA_VENENUM_ANIMATION = prefix("animations/hwachavenator/hwachavenator_venenum.animation.json");

    @Override
    public ResourceLocation getModelResource(HwachavenatorEntity object) {
        return switch (object.getVariant()) {
            case 1 -> HWACHA_TRUCULENTUS_MODEL;
            case 2 -> HWACHA_VENENUM_MODEL;
            default -> HWACHA_ACUTI_MODEL;
        };
    }

    @Override
    public ResourceLocation getTextureResource(HwachavenatorEntity object) {
        return switch (object.getVariant()) {
            case 1 -> HWACHA_TRUCULENTUS_TEXTURE;
            case 2 -> HWACHA_VENENUM_TEXTURE;
            default -> HWACHA_ACUTI_TEXTURE;
        };
    }

    @Override
    public ResourceLocation getAnimationResource(HwachavenatorEntity object) {
        return switch (object.getVariant()) {
            case 1 -> HWACHA_TRUCULENTUS_ANIMATION;
            case 2 -> HWACHA_VENENUM_ANIMATION;
            default -> HWACHA_ACUTI_ANIMATION;
        };
    }

    @Override
    public void setCustomAnimations(HwachavenatorEntity animatable, long instanceId, AnimationState<HwachavenatorEntity> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);
        if (animationState == null) return;

        EntityModelData extraDataOfType = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
        CoreGeoBone neck = this.getAnimationProcessor().getBone("Hwacha_Neck");
        CoreGeoBone head = this.getAnimationProcessor().getBone("Hwacha_Head");

        if (animatable.isBaby()) {
            head.setScaleX(1.5F);
            head.setScaleY(1.5F);
            head.setScaleZ(1.5F);
        } else {
            head.setScaleX(1.0F);
            head.setScaleY(1.0F);
            head.setScaleZ(1.0F);
        }

        if (!animatable.isSprinting() && !animatable.hasControllingPassenger()) {
            neck.setRotY(extraDataOfType.netHeadYaw() * Mth.DEG_TO_RAD);
        }
    }
}