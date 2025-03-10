package com.peeko32213.unusualprehistory.client.model.entity;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.semi_aquatic.DiplocaulusEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class DiplocaulusModel extends GeoModel<DiplocaulusEntity> {

    private static final ResourceLocation TEXTURE_1 = new ResourceLocation("unusualprehistory:textures/entity/diplocaulus/diplocaulus_brevirostris.png");
    private static final ResourceLocation TEXTURE_2 = new ResourceLocation("unusualprehistory:textures/entity/diplocaulus/diplocaulus_magnicornis.png");
    private static final ResourceLocation TEXTURE_3 = new ResourceLocation("unusualprehistory:textures/entity/diplocaulus/diplocaulus_recurvatis.png");
    private static final ResourceLocation TEXTURE_4 = new ResourceLocation("unusualprehistory:textures/entity/diplocaulus/diplocaulus_salamandroides.png");

    private static final ResourceLocation MODEL_1 = new ResourceLocation("unusualprehistory:geo/diplocaulus/diplocaulus_brevirostris.geo.json");
    private static final ResourceLocation MODEL_2 = new ResourceLocation("unusualprehistory:geo/diplocaulus/diplocaulus_magnicornis.geo.json");
    private static final ResourceLocation MODEL_3 = new ResourceLocation("unusualprehistory:geo/diplocaulus/diplocaulus_recurvatis.geo.json");
    private static final ResourceLocation MODEL_4 = new ResourceLocation("unusualprehistory:geo/diplocaulus/diplocaulus_salamandroides.geo.json");

    @Override
    public ResourceLocation getModelResource(DiplocaulusEntity object) {
        return switch (object.getVariant()) {
            case 1 -> MODEL_2;
            case 2 -> MODEL_3;
            case 3 -> MODEL_4;
            default -> MODEL_1;
        };
    }

    @Override
    public ResourceLocation getTextureResource(DiplocaulusEntity animatable) {
        return switch (animatable.getVariant()) {
            case 1 -> TEXTURE_2;
            case 2 -> TEXTURE_3;
            case 3 -> TEXTURE_4;
            default -> TEXTURE_1;
        };
    }

    @Override
    public ResourceLocation getAnimationResource(DiplocaulusEntity object) {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/diplocaulus.animation.json");
    }

    @Override
    public void setCustomAnimations(DiplocaulusEntity animatable, long instanceId, AnimationState<DiplocaulusEntity> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);
        if (animationState == null) return;

        CoreGeoBone head = this.getAnimationProcessor().getBone("Head");
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

