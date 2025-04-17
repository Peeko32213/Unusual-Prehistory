package com.peeko32213.unusualprehistory.client.model.entity.prehistoric;

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
    private static final ResourceLocation REX = new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/tyrannosaurus/tyrannosaurus_rex.png");
    private static final ResourceLocation REX_ENRAGED = new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/tyrannosaurus/tyrannosaurus_rex_enraged.png");
    private static final ResourceLocation REX_EEPY = new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/tyrannosaurus/tyrannosaurus_rex_eepy.png");

    private static final ResourceLocation MCRAEENSIS = new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/tyrannosaurus/tyrannosaurus_mcraeensis.png");
    private static final ResourceLocation MCRAEENSIS_ENRAGED = new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/tyrannosaurus/tyrannosaurus_mcraeensis_enraged.png");
    private static final ResourceLocation MCRAEENSIS_EEPY = new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/tyrannosaurus/tyrannosaurus_mcraeensis_eepy.png");

    private static final ResourceLocation SKELETON = new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/tyrannosaurus/tyrannosaurus_skeleton.png");
    private static final ResourceLocation SKELETON_ENRAGED = new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/tyrannosaurus/tyrannosaurus_skeleton_enraged.png");
    private static final ResourceLocation SKELETON_EEPY = new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/tyrannosaurus/tyrannosaurus_skeleton_eepy.png");

    private static final ResourceLocation MODEL_REX = new ResourceLocation(UnusualPrehistory.MODID, "geo/tyrannosaurus/tyrannosaurus_rex.geo.json");
    private static final ResourceLocation MODEL_MCRAEENSIS = new ResourceLocation(UnusualPrehistory.MODID, "geo/tyrannosaurus/tyrannosaurus_mcraeensis.geo.json");
    private static final ResourceLocation MODEL_SKELETON = new ResourceLocation(UnusualPrehistory.MODID, "geo/tyrannosaurus/tyrannosaurus_skeleton.geo.json");


    private static final ResourceLocation ANIM_REX = new ResourceLocation(UnusualPrehistory.MODID, "animations/tyrannosaurus/tyrannosaurus_rex.animation.json");
    private static final ResourceLocation ANIM_MCRAEENSIS = new ResourceLocation(UnusualPrehistory.MODID, "animations/tyrannosaurus/tyrannosaurus_mcraeensis.animation.json");


    @Override
    public ResourceLocation getModelResource(TyrannosaurusEntity tyranno) {
        if (tyranno.isSkeletal()) {
            return MODEL_SKELETON;
        }
        return tyranno.getVariant() == 1 ? MODEL_MCRAEENSIS : MODEL_REX;
    }

    @Override
    public ResourceLocation getAnimationResource(TyrannosaurusEntity tyranno) {
        return tyranno.getVariant() == 1 ? ANIM_MCRAEENSIS : ANIM_REX;
    }



    @Override
    public ResourceLocation getTextureResource(TyrannosaurusEntity tyranno) {
        if (tyranno.isSkeletal()) {
            if (tyranno.isEepy()) return SKELETON_EEPY;
            if (tyranno.isAngry()) return SKELETON_ENRAGED;
            return SKELETON;
        }

        boolean isMcraeensis = tyranno.getVariant() == 1;

        if (tyranno.isEepy()) return isMcraeensis ? MCRAEENSIS_EEPY : REX_EEPY;
        if (tyranno.isAngry()) return isMcraeensis ? MCRAEENSIS_ENRAGED : REX_ENRAGED;
        return isMcraeensis ? MCRAEENSIS : REX;
    }

    @Override
    public void setCustomAnimations(TyrannosaurusEntity entity, long instanceId, AnimationState<TyrannosaurusEntity> animationState) {
        super.setCustomAnimations(entity, instanceId, animationState);
        if (animationState == null) return;
        EntityModelData extraDataOfType = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

        CoreGeoBone head = this.getAnimationProcessor().getBone("head");

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
    }
}

