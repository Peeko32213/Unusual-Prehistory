package com.peeko32213.unusualprehistory.client.model.entity;

import com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.NyctoraptorEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class NyctoraptorModel extends GeoModel<NyctoraptorEntity> {

    private static final ResourceLocation TEXTURE_TAIGA = new ResourceLocation("unusualprehistory:textures/entity/nyctoraptor/nyctoraptor_taiga.png");
    private static final ResourceLocation TEXTURE_FOREST = new ResourceLocation("unusualprehistory:textures/entity/nyctoraptor/nyctoraptor_forest.png");
    private static final ResourceLocation MODEL_TAIGA = new ResourceLocation("unusualprehistory:geo/nyctoraptor/nyctoraptor_taiga.geo.json");
    private static final ResourceLocation MODEL_FOREST = new ResourceLocation("unusualprehistory:geo/nyctoraptor/nyctoraptor_forest.geo.json");
    private static final ResourceLocation ANIMATION_TAIGA = new ResourceLocation("unusualprehistory:animations/nyctoraptor/nyctoraptor_taiga.animation.json");
    private static final ResourceLocation ANIMATION_FOREST = new ResourceLocation("unusualprehistory:animations/nyctoraptor/nyctoraptor_forest.animation.json");

    @Override
    public ResourceLocation getModelResource(NyctoraptorEntity object) {
        if(object.getVariant() == 1) {
            return MODEL_FOREST;
        }
        else return MODEL_TAIGA;
    }

    @Override
    public ResourceLocation getTextureResource(NyctoraptorEntity object) {
        if(object.getVariant() == 1) {
            return TEXTURE_FOREST;
        }
        else return TEXTURE_TAIGA;
    }

    @Override
    public ResourceLocation getAnimationResource(NyctoraptorEntity object) {
        return ANIMATION_TAIGA;
    }

    @Override
    public void setCustomAnimations(NyctoraptorEntity animatable, long instanceId, AnimationState<NyctoraptorEntity> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);
        if (animationState == null) return;
        EntityModelData extraDataOfType = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
        CoreGeoBone head = this.getAnimationProcessor().getBone("Neck");

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
            head.setRotY(extraDataOfType.netHeadYaw() * Mth.DEG_TO_RAD);
        }
    }
}
