package com.peeko32213.unusualprehistory.client.model;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.TyrannosaurusEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class TyrannosaurusModel extends GeoModel<TyrannosaurusEntity> {

    @Override
    public ResourceLocation getModelResource(TyrannosaurusEntity rex) {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/tyrannosaurus/tyrannosaurus_rex.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(TyrannosaurusEntity rex) {
        if(rex.hasEepy()) {
            return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/tyrannosaurus/tyrannosaurus_rex_eepy.png");
        } else{
            return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/tyrannosaurus/tyrannosaurus_rex.png");
        }
    }

    @Override
    public ResourceLocation getAnimationResource(TyrannosaurusEntity rex) {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/tyrannosaurus.animation.json");
    }

    @Override
    public void setCustomAnimations(TyrannosaurusEntity animatable, long instanceId, AnimationState<TyrannosaurusEntity> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);
        if (animationState == null) return;
        EntityModelData extraDataOfType = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

        CoreGeoBone tyrannosaurus = this.getAnimationProcessor().getBone("root");
        CoreGeoBone head = this.getAnimationProcessor().getBone("Rex_Head");

        if (animatable.isBaby()) {
            head.setScaleX(1.5F);
            head.setScaleY(1.5F);
            head.setScaleZ(1.5F);
            tyrannosaurus.setScaleX(0.5F);
            tyrannosaurus.setScaleY(0.5F);
            tyrannosaurus.setScaleZ(0.5F);
        } else {
            head.setScaleX(1.0F);
            head.setScaleY(1.0F);
            head.setScaleZ(1.0F);
            tyrannosaurus.setScaleX(1.15F);
            tyrannosaurus.setScaleY(1.15F);
            tyrannosaurus.setScaleZ(1.15F);
        }

        if (!animatable.isSprinting()) {
            head.setRotY(extraDataOfType.netHeadYaw() * Mth.DEG_TO_RAD);
        }
    }
}

