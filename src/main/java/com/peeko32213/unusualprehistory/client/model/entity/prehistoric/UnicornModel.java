package com.peeko32213.unusualprehistory.client.model.entity.prehistoric;

import com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.UnicornEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

import static com.peeko32213.unusualprehistory.UnusualPrehistory.modPrefix;

public class UnicornModel extends GeoModel<UnicornEntity> {

    private static final ResourceLocation UNICORN_MODEL = modPrefix("geo/unicorn/unicorn.geo.json");
    private static final ResourceLocation UNICORN_TEXTURE = modPrefix("textures/entity/unicorn/unicorn.png");
    private static final ResourceLocation UNICORN_ANIMATION = modPrefix("animations/unicorn.animation.json");
    private static final ResourceLocation UNICORN_SKELETON_MODEL = modPrefix("geo/unicorn/unicorn_skeleton.geo.json");
    private static final ResourceLocation UNICORN_SKELETON_TEXTURE = modPrefix("textures/entity/unicorn/unicorn_skeleton.png");

    @Override
    public ResourceLocation getModelResource(UnicornEntity unicorn) {
        if (unicorn.isSkeletal()) {
            return UNICORN_SKELETON_MODEL;
        }
        else return UNICORN_MODEL;
    }

    @Override
    public ResourceLocation getTextureResource(UnicornEntity unicorn) {
        if (unicorn.isSkeletal()) {
            return UNICORN_SKELETON_TEXTURE;
        }
        else return UNICORN_TEXTURE;
    }

    @Override
    public ResourceLocation getAnimationResource(UnicornEntity unicorn) {
        return UNICORN_ANIMATION;
    }

    @Override
    public void setCustomAnimations(UnicornEntity animatable, long instanceId, AnimationState<UnicornEntity> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);
        if (animationState == null) return;

        EntityModelData extraDataOfType = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
        CoreGeoBone head = this.getAnimationProcessor().getBone("Neck");

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
