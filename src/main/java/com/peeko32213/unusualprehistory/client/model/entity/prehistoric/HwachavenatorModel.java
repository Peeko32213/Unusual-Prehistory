package com.peeko32213.unusualprehistory.client.model.entity.prehistoric;

import com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.HwachavenatorEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

import static com.peeko32213.unusualprehistory.UnusualPrehistory.modPrefix;

public class HwachavenatorModel extends GeoModel<HwachavenatorEntity> {

    private static final ResourceLocation HWACHA_ACUTI_MODEL = modPrefix("geo/hwachavenator/hwachavenator_acuti.geo.json");
    private static final ResourceLocation HWACHA_TRUCULENTUS_MODEL = modPrefix("geo/hwachavenator/hwachavenator_truculentus.geo.json");
    private static final ResourceLocation HWACHA_VENENUM_MODEL = modPrefix("geo/hwachavenator/hwachavenator_venenum.geo.json");
    private static final ResourceLocation HWACHA_FABULOSA_MODEL = modPrefix("geo/hwachavenator/hwachavenator_fabulosa.geo.json");

    private static final ResourceLocation HWACHA_ACUTI_TEXTURE = modPrefix("textures/entity/hwachavenator/hwachavenator_acuti.png");
    private static final ResourceLocation HWACHA_TRUCULENTUS_TEXTURE = modPrefix("textures/entity/hwachavenator/hwachavenator_truculentus.png");
    private static final ResourceLocation HWACHA_VENENUM_TEXTURE = modPrefix("textures/entity/hwachavenator/hwachavenator_venenum.png");
    private static final ResourceLocation HWACHA_FABULOSA_WANING_TEXTURE = modPrefix("textures/entity/hwachavenator/hwachavenator_fabulosa_waning.png");
    private static final ResourceLocation HWACHA_FABULOSA_WAXING_TEXTURE = modPrefix("textures/entity/hwachavenator/hwachavenator_fabulosa_waxing.png");

    private static final ResourceLocation HWACHA_ACUTI_ANIMATION = modPrefix("animations/hwachavenator/hwachavenator_acuti.animation.json");
    private static final ResourceLocation HWACHA_TRUCULENTUS_ANIMATION = modPrefix("animations/hwachavenator/hwachavenator_truculentus.animation.json");
    private static final ResourceLocation HWACHA_VENENUM_ANIMATION = modPrefix("animations/hwachavenator/hwachavenator_venenum.animation.json");
    private static final ResourceLocation HWACHA_FABULOSA_ANIMATION = modPrefix("animations/hwachavenator/hwachavenator_fabulosa.animation.json");

    @Override
    public ResourceLocation getModelResource(HwachavenatorEntity object) {
        return switch (object.getVariant()) {
            case 1 -> HWACHA_TRUCULENTUS_MODEL;
            case 2 -> HWACHA_VENENUM_MODEL;
            case 3 -> HWACHA_FABULOSA_MODEL;
            default -> HWACHA_ACUTI_MODEL;
        };
    }

    @Override
    public ResourceLocation getTextureResource(HwachavenatorEntity object) {
        return switch (object.getVariant()) {
            case 1 -> HWACHA_TRUCULENTUS_TEXTURE;
            case 2 -> HWACHA_VENENUM_TEXTURE;
            case 3 -> HWACHA_FABULOSA_WANING_TEXTURE;
            case 4 -> HWACHA_FABULOSA_WAXING_TEXTURE;
            default -> HWACHA_ACUTI_TEXTURE;
        };
    }

    @Override
    public ResourceLocation getAnimationResource(HwachavenatorEntity object) {
        return switch (object.getVariant()) {
            case 1 -> HWACHA_TRUCULENTUS_ANIMATION;
            case 2 -> HWACHA_VENENUM_ANIMATION;
            case 3 -> HWACHA_FABULOSA_ANIMATION;
            default -> HWACHA_ACUTI_ANIMATION;
        };
    }

    @Override
    public void setCustomAnimations(HwachavenatorEntity animatable, long instanceId, AnimationState<HwachavenatorEntity> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);
        if (animationState == null) return;

        EntityModelData extraDataOfType = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
        CoreGeoBone neck = this.getAnimationProcessor().getBone("neck");
        CoreGeoBone head = this.getAnimationProcessor().getBone("head");

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