package com.peeko32213.unusualprehistory.client.model.entity.prehistoric.aquatic;

import com.peeko32213.unusualprehistory.MathHelpers;
import com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.aquatic.DunkleosteusEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class DunkleosteusModel extends GeoModel<DunkleosteusEntity> {

    private static final ResourceLocation TEXTURE_TERRELLI = new ResourceLocation("unusualprehistory:textures/entity/dunkleosteus/dunkleosteus_terrelli.png");
    private static final ResourceLocation TEXTURE_MARSAISI = new ResourceLocation("unusualprehistory:textures/entity/dunkleosteus/dunkleosteus_marsaisi.png");
    private static final ResourceLocation TEXTURE_RAVERI = new ResourceLocation("unusualprehistory:textures/entity/dunkleosteus/dunkleosteus_raveri.png");

    private static final ResourceLocation MODEL_TERRELLI = new ResourceLocation("unusualprehistory:geo/dunkleosteus/dunkleosteus_terrelli.geo.json");
    private static final ResourceLocation MODEL_MARSAISI = new ResourceLocation("unusualprehistory:geo/dunkleosteus/dunkleosteus_marsaisi.geo.json");
    private static final ResourceLocation MODEL_RAVERI = new ResourceLocation("unusualprehistory:geo/dunkleosteus/dunkleosteus_raveri.geo.json");

    private static final ResourceLocation ANIMATION_TERRELLI = new ResourceLocation("unusualprehistory:animations/dunkleosteus/dunkleosteus_terrelli.animation.json");
    private static final ResourceLocation ANIMATION_MARSAISI = new ResourceLocation("unusualprehistory:animations/dunkleosteus/dunkleosteus_marsaisi.animation.json");
    private static final ResourceLocation ANIMATION_RAVERI = new ResourceLocation("unusualprehistory:animations/dunkleosteus/dunkleosteus_raveri.animation.json");

    @Override
    public ResourceLocation getModelResource(DunkleosteusEntity object) {
        return switch (object.getDunkSize()) {
            case 1 -> MODEL_MARSAISI;
            case 2 -> MODEL_TERRELLI;
            default -> MODEL_RAVERI;
        };
    }

    @Override
    public ResourceLocation getTextureResource(DunkleosteusEntity object) {
        return switch (object.getDunkSize()) {
            case 1 -> TEXTURE_MARSAISI;
            case 2 -> TEXTURE_TERRELLI;
            default -> TEXTURE_RAVERI;
        };
    }

    @Override
    public ResourceLocation getAnimationResource(DunkleosteusEntity object) {
        return switch (object.getDunkSize()) {
            case 1 -> ANIMATION_MARSAISI;
            case 2 -> ANIMATION_TERRELLI;
            default -> ANIMATION_RAVERI;
        };
    }

    @Override
    public void setCustomAnimations(DunkleosteusEntity entity, long instanceId, AnimationState<DunkleosteusEntity> animationState) {
        super.setCustomAnimations(entity, instanceId, animationState);
        if (animationState == null) return;
        if(entity.isFromBook()) return;

        CoreGeoBone backBody = this.getAnimationProcessor().getBone("Dunk_Tail");
        EntityModelData extraDataOfType = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

        CoreGeoBone root = this.getAnimationProcessor().getBone("Dunkleosteus");

        root.setRotX(extraDataOfType.headPitch() * (Mth.DEG_TO_RAD / 2));

        backBody.setRotY(backBody.getRotY() + extraDataOfType.netHeadYaw() * ((float) Math.PI / 270F));
        
        if (entity.isUnderWater()) {
            if (entity.getDunkSize() == 2) {
                CoreGeoBone tail = this.getAnimationProcessor().getBone("Dunk_Tail");
                CoreGeoBone tail2 = this.getAnimationProcessor().getBone("Dunk_Fluke");

                tail.setRotY(tail.getRotY() + (float) ((MathHelpers.LerpDegrees((float) entity.TailKinematics.getCurrentTailYaws()[0], (float) entity.TailKinematics.getTailYaws()[0], 0.01))));
                tail2.setRotY(tail2.getRotY() + (float) ((MathHelpers.LerpDegrees((float) entity.TailKinematics.getCurrentTailYaws()[1], (float) entity.TailKinematics.getTailYaws()[1], 0.01))));
                entity.TailKinematics.getCurrentTailYaws()[0] = (float) MathHelpers.LerpDegrees((float) entity.TailKinematics.getCurrentTailYaws()[0], (float) entity.TailKinematics.getTailYaws()[0], 0.01);
                entity.TailKinematics.getCurrentTailYaws()[1] = (float) MathHelpers.LerpDegrees((float) entity.TailKinematics.getCurrentTailYaws()[1], (float) entity.TailKinematics.getTailYaws()[1], 0.01);
                //this runs BETWEEN TICKS
                //0.25 means it interpolates to a quarter of the way to the target
                //setRotY takes RADIANS

                //No deg to rad because the arccos function used to return the angle
                //gotta set up UNIQUE NODES FOR EACH BONE
                tail.setRotX((float) Mth.clamp(MathHelpers.LerpDegrees((float) entity.TailKinematics.getCurrentTailPitches()[0], (float) entity.TailKinematics.getTailPitches()[0], 0.01), -Mth.PI*0.25, Mth.PI*0.25));
                entity.TailKinematics.getCurrentTailPitches()[0] = (float) MathHelpers.LerpDegrees((float) entity.TailKinematics.getCurrentTailPitches()[0], (float) entity.TailKinematics.getTailPitches()[0], 0.01);
                //positive RotX is DOWNWARDS, and increasing angle swings it forwards towards the head
                
            } else if (entity.getDunkSize() == 1) {
                CoreGeoBone tail = this.getAnimationProcessor().getBone("Dunk_Tail");
                CoreGeoBone tail2 = this.getAnimationProcessor().getBone("Dunk_Fluke");

                tail.setRotY(tail.getRotY() + (float) (Mth.clamp(MathHelpers.LerpDegrees((float) entity.TailKinematics.getCurrentTailYaws()[0], (float) entity.TailKinematics.getTailYaws()[0], 0.01), -Mth.PI*0.25, Mth.PI*0.25)));
                tail2.setRotY(tail2.getRotY() + (float) (Mth.clamp(MathHelpers.LerpDegrees((float) entity.TailKinematics.getCurrentTailYaws()[1], (float) entity.TailKinematics.getTailYaws()[1], 0.01), -Mth.PI*0.25, Mth.PI*0.25)));
                entity.TailKinematics.getCurrentTailYaws()[0] = (float) MathHelpers.LerpDegrees((float) entity.TailKinematics.getCurrentTailYaws()[0], (float) entity.TailKinematics.getTailYaws()[0], 0.01);
                entity.TailKinematics.getCurrentTailYaws()[1] = (float) MathHelpers.LerpDegrees((float) entity.TailKinematics.getCurrentTailYaws()[1], (float) entity.TailKinematics.getTailYaws()[1], 0.01);
                //this runs BETWEEN TICKS
                //0.25 means it interpolates to a quarter of the way to the target
                //setRotY takes RADIANS

                //No deg to rad because the arccos function used to return the angle
                //gotta set up UNIQUE NODES FOR EACH BONE
                tail.setRotX((float) Mth.clamp(MathHelpers.LerpDegrees((float) entity.TailKinematics.getCurrentTailPitches()[0], (float) entity.TailKinematics.getTailPitches()[0], 0.01), -Mth.PI*0.25, Mth.PI*0.25));
                entity.TailKinematics.getCurrentTailPitches()[0] = (float) MathHelpers.LerpDegrees((float) entity.TailKinematics.getCurrentTailPitches()[0], (float) entity.TailKinematics.getTailPitches()[0], 0.01);
                //positive RotX is DOWNWARDS, and increasing angle swings it forwards towards the head
                
            } else if (entity.getDunkSize() == 0) {
                CoreGeoBone tail = this.getAnimationProcessor().getBone("Dunk_Tail");
                CoreGeoBone tail2 = this.getAnimationProcessor().getBone("Dunk_Fluke");

                tail.setRotY(tail.getRotY() + (float) ((MathHelpers.LerpDegrees((float) entity.TailKinematics.getCurrentTailYaws()[0], (float) entity.TailKinematics.getTailYaws()[0], 0.01))));
                tail2.setRotY(tail2.getRotY() + (float) ((MathHelpers.LerpDegrees((float) entity.TailKinematics.getCurrentTailYaws()[1], (float) entity.TailKinematics.getTailYaws()[1], 0.01))));
                entity.TailKinematics.getCurrentTailYaws()[0] = (float) MathHelpers.LerpDegrees((float) entity.TailKinematics.getCurrentTailYaws()[0], (float) entity.TailKinematics.getTailYaws()[0], 0.01);
                entity.TailKinematics.getCurrentTailYaws()[1] = (float) MathHelpers.LerpDegrees((float) entity.TailKinematics.getCurrentTailYaws()[1], (float) entity.TailKinematics.getTailYaws()[1], 0.01);
                //this runs BETWEEN TICKS
                //0.25 means it interpolates to a quarter of the way to the target
                //setRotY takes RADIANS

                //No deg to rad because the arccos function used to return the angle
                //gotta set up UNIQUE NODES FOR EACH BONE
                tail.setRotX((float) Mth.clamp(MathHelpers.LerpDegrees((float) entity.TailKinematics.getCurrentTailPitches()[0], (float) entity.TailKinematics.getTailPitches()[0], 0.01), -Mth.PI*0.25, Mth.PI*0.25));
                entity.TailKinematics.getCurrentTailPitches()[0] = (float) MathHelpers.LerpDegrees((float) entity.TailKinematics.getCurrentTailPitches()[0], (float) entity.TailKinematics.getTailPitches()[0], 0.01);
                //positive RotX is DOWNWARDS, and increasing angle swings it forwards towards the head
                
            }
        }

    }
}

