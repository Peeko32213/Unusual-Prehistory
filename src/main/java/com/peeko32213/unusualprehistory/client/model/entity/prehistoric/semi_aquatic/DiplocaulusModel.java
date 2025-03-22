package com.peeko32213.unusualprehistory.client.model.entity.prehistoric.semi_aquatic;

import com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.semi_aquatic.DiplocaulusEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;

public class DiplocaulusModel extends GeoModel<DiplocaulusEntity> {

    private static final ResourceLocation TEXTURE_1 = new ResourceLocation("unusualprehistory:textures/entity/diplocaulus/diplocaulus_brevirostris.png");
    private static final ResourceLocation TEXTURE_2 = new ResourceLocation("unusualprehistory:textures/entity/diplocaulus/diplocaulus_magnicornis.png");
    private static final ResourceLocation TEXTURE_3 = new ResourceLocation("unusualprehistory:textures/entity/diplocaulus/diplocaulus_recurvatis.png");
    private static final ResourceLocation TEXTURE_4 = new ResourceLocation("unusualprehistory:textures/entity/diplocaulus/diplocaulus_salamandroides.png");

    private static final ResourceLocation MODEL_1 = new ResourceLocation("unusualprehistory:geo/diplocaulus/diplocaulus_brevirostris.geo.json");
    private static final ResourceLocation MODEL_2 = new ResourceLocation("unusualprehistory:geo/diplocaulus/diplocaulus_magnicornis.geo.json");
    private static final ResourceLocation MODEL_3 = new ResourceLocation("unusualprehistory:geo/diplocaulus/diplocaulus_recurvatis.geo.json");
    private static final ResourceLocation MODEL_4 = new ResourceLocation("unusualprehistory:geo/diplocaulus/diplocaulus_salamandroides.geo.json");

    private static final ResourceLocation ANIMATION_1 = new ResourceLocation("unusualprehistory:animations/diplocaulus/diplocaulus_brevirostris.animation.json");
    private static final ResourceLocation ANIMATION_2 = new ResourceLocation("unusualprehistory:animations/diplocaulus/diplocaulus_magnicornis.animation.json");
    private static final ResourceLocation ANIMATION_3 = new ResourceLocation("unusualprehistory:animations/diplocaulus/diplocaulus_recurvatis.animation.json");
    private static final ResourceLocation ANIMATION_4 = new ResourceLocation("unusualprehistory:animations/diplocaulus/diplocaulus_salamandroides.animation.json");


    @Override
    public ResourceLocation getModelResource(DiplocaulusEntity diplo) {
        return switch (diplo.getVariant()) {
            case 1 -> MODEL_2;
            case 2 -> MODEL_3;
            case 3 -> MODEL_4;
            default -> MODEL_1;
        };
    }

    @Override
    public ResourceLocation getTextureResource(DiplocaulusEntity diplo) {
        return switch (diplo.getVariant()) {
            case 1 -> TEXTURE_2;
            case 2 -> TEXTURE_3;
            case 3 -> TEXTURE_4;
            default -> TEXTURE_1;
        };
    }

    @Override
    public ResourceLocation getAnimationResource(DiplocaulusEntity diplo) {
        return switch (diplo.getVariant()) {
            case 1 -> ANIMATION_2;
            case 2 -> ANIMATION_3;
            case 3 -> ANIMATION_4;
            default -> ANIMATION_1;
        };
    }

    @Override
    public void setCustomAnimations(DiplocaulusEntity animatable, long instanceId, AnimationState<DiplocaulusEntity> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);
        if (animationState == null) return;

        CoreGeoBone head = this.getAnimationProcessor().getBone("head");
        if (animatable.isBaby()) {
            head.setScaleX(1.35F);
            head.setScaleY(1.35F);
            head.setScaleZ(1.35F);
        } else {
            head.setScaleX(1.0F);
            head.setScaleY(1.0F);
            head.setScaleZ(1.0F);
        }
    }
}

