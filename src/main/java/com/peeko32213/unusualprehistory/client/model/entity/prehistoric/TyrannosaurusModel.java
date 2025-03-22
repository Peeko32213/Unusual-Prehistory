package com.peeko32213.unusualprehistory.client.model.entity.prehistoric;

import com.peeko32213.unusualprehistory.MathHelpers;
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
    public void setCustomAnimations(TyrannosaurusEntity entity, long instanceId, AnimationState<TyrannosaurusEntity> animationState) {
        super.setCustomAnimations(entity, instanceId, animationState);
        if (animationState == null) return;
        EntityModelData extraDataOfType = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

        CoreGeoBone head = this.getAnimationProcessor().getBone("head");

        CoreGeoBone eyes = this.getAnimationProcessor().getBone("eepy");

        eyes.setHidden(!entity.hasEepy());

        if (entity.isBaby()) {
            head.setScaleX(1.5F);
            head.setScaleY(1.5F);
            head.setScaleZ(1.5F);
        } else {
            head.setScaleX(1.0F);
            head.setScaleY(1.0F);
            head.setScaleZ(1.0F);
        }

        if (!entity.isSprinting()) {
            head.setRotY(extraDataOfType.netHeadYaw() * Mth.DEG_TO_RAD);
        }



        CoreGeoBone tail = this.getAnimationProcessor().getBone("tail1_overlay");
        CoreGeoBone tail2 = this.getAnimationProcessor().getBone("tail2_overlay");

        tail.setRotY((float) ((MathHelpers.LerpDegrees((float) entity.rexIK.getCurrentTailYaws()[0], (float) entity.rexIK.getTailYaws()[0], 1))));
        tail2.setRotY((float) ((MathHelpers.LerpDegrees((float) entity.rexIK.getCurrentTailYaws()[1], (float) entity.rexIK.getTailYaws()[1], 1))));
        entity.rexIK.getCurrentTailYaws()[0] = (float) MathHelpers.LerpDegrees((float) entity.rexIK.getCurrentTailYaws()[0], (float) entity.rexIK.getTailYaws()[0], 1);
        entity.rexIK.getCurrentTailYaws()[1] = (float) MathHelpers.LerpDegrees((float) entity.rexIK.getCurrentTailYaws()[1], (float) entity.rexIK.getTailYaws()[1], 1);
        //this runs BETWEEN TICKS
        //0.25 means it interpolates to a quarter of the way to the target
        //setRotY takes RADIANS

        //No deg to rad because the arccos function used to return the angle
        //gotta set up UNIQUE NODES FOR EACH BONE
        tail.setRotX((float) Mth.clamp(MathHelpers.LerpDegrees((float) entity.rexIK.getCurrentTailPitches()[0], (float) entity.rexIK.getTailPitches()[0], 0.01), -Mth.PI*0.1, Mth.PI*0.1));
        tail2.setRotX((float) Mth.clamp(MathHelpers.LerpDegrees((float) entity.rexIK.getCurrentTailPitches()[1], (float) entity.rexIK.getTailPitches()[1], 0.01), -Mth.PI*0.1, Mth.PI*0.1));
        entity.rexIK.getCurrentTailPitches()[0] = (float) MathHelpers.LerpDegrees((float) entity.rexIK.getCurrentTailPitches()[0], (float) entity.rexIK.getTailPitches()[0], 0.01);
        entity.rexIK.getCurrentTailPitches()[1] = (float) MathHelpers.LerpDegrees((float) entity.rexIK.getCurrentTailPitches()[1], (float) entity.rexIK.getTailPitches()[1], 0.01);

        //positive RotX is DOWNWARDS, and increasing angle swings it forwards towards the head

    }
}

