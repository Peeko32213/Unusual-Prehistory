package com.peeko32213.unusualprehistory.client.model.entity;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.TyrannosaurusEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class TyrannosaurusModel extends GeoModel<TyrannosaurusEntity> {

    @Override
    public ResourceLocation getModelResource(TyrannosaurusEntity tyranno) {
        if(tyranno.getVariant() == 1) {
            return new ResourceLocation(UnusualPrehistory.MODID, "geo/tyrannosaurus/tyrannosaurus_mcraeensis.geo.json");
        } else {
            return new ResourceLocation(UnusualPrehistory.MODID, "geo/tyrannosaurus/tyrannosaurus_rex.geo.json");
        }
    }

    @Override
    public ResourceLocation getTextureResource(TyrannosaurusEntity tyranno) {
        if(tyranno.getVariant() == 1) {
            return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/tyrannosaurus/tyrannosaurus_mcraeensis.png");
        } else {
            return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/tyrannosaurus/tyrannosaurus_rex.png");
        }
    }

    @Override
    public ResourceLocation getAnimationResource(TyrannosaurusEntity tyranno) {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/tyrannosaurus.animation.json");
    }

    @Override
    public void setCustomAnimations(TyrannosaurusEntity animatable, long instanceId, AnimationState<TyrannosaurusEntity> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);
        if (animationState == null) return;
        EntityModelData extraDataOfType = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

        CoreGeoBone head = this.getAnimationProcessor().getBone("head");

        CoreGeoBone eyes = this.getAnimationProcessor().getBone("eepy");

        eyes.setHidden(!animatable.hasEepy());
        eyes.setHidden(!animatable.isAsleep());

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

