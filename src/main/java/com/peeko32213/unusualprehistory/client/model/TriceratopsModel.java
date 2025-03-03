package com.peeko32213.unusualprehistory.client.model;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.TriceratopsEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class TriceratopsModel extends GeoModel<TriceratopsEntity> {

    @Override
    public ResourceLocation getModelResource(TriceratopsEntity trike) {
        if(trike.getVariant() == 1) {
            return new ResourceLocation(UnusualPrehistory.MODID, "geo/triceratops/triceratops_horridus.geo.json");
        } else {
            return new ResourceLocation(UnusualPrehistory.MODID, "geo/triceratops/triceratops_prorsus.geo.json");
        }
    }

    @Override
    public ResourceLocation getTextureResource(TriceratopsEntity trike) {
        if(trike.getVariant() == 1) {
            return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/triceratops/triceratops_horridus.png");
        } else {
            return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/triceratops/triceratops_prorsus.png");
        }
    }

    @Override
    public ResourceLocation getAnimationResource(TriceratopsEntity object) {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/triceratops.animation.json");
    }

    @Override
    public void setCustomAnimations(TriceratopsEntity animatable, long instanceId, AnimationState<TriceratopsEntity> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);
        if (animationState == null) return;
        EntityModelData extraDataOfType = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

        CoreGeoBone neck = this.getAnimationProcessor().getBone("Trike_Neck");
        CoreGeoBone head = this.getAnimationProcessor().getBone("Trike_Head");

        CoreGeoBone saddle = this.getAnimationProcessor().getBone("Trike_Saddle");

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

