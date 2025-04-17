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

    @Override
    public ResourceLocation getModelResource(TyrannosaurusEntity tyranno) {
        if (!tyranno.isSkeletal()) {
            if (tyranno.getVariant() == 1) {
                return new ResourceLocation(UnusualPrehistory.MODID, "geo/tyrannosaurus/tyrannosaurus_mcraeensis.geo.json");
            }
            else return new ResourceLocation(UnusualPrehistory.MODID, "geo/tyrannosaurus/tyrannosaurus_rex.geo.json");
        }
        else return new ResourceLocation(UnusualPrehistory.MODID, "geo/tyrannosaurus/tyrannosaurus_skeleton.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(TyrannosaurusEntity tyranno) {
        if (!tyranno.isEepy()) {
            if (!tyranno.isAngry()) {
                if (!tyranno.isSkeletal()) {
                    if (tyranno.getVariant() == 1) {
                        return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/tyrannosaurus/tyrannosaurus_mcraeensis.png");
                    } else
                        return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/tyrannosaurus/tyrannosaurus_rex.png");
                }
                return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/tyrannosaurus/tyrannosaurus_skeleton.png");
            } else {
                if (!tyranno.isSkeletal()) {
                    if (tyranno.getVariant() == 1) {
                        return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/tyrannosaurus/tyrannosaurus_mcraeensis_enraged.png");
                    } else
                        return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/tyrannosaurus/tyrannosaurus_rex_enraged.png");
                }
                return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/tyrannosaurus/tyrannosaurus_skeleton_enraged.png");
            }
        }
        else {
            if (!tyranno.isSkeletal()) {
                if (tyranno.getVariant() == 1) {
                    return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/tyrannosaurus/tyrannosaurus_mcraeensis_eepy.png");
                } else
                    return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/tyrannosaurus/tyrannosaurus_rex_eepy.png");
            }
            return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/tyrannosaurus/tyrannosaurus_skeleton_eepy.png");
        }
    }

    @Override
    public ResourceLocation getAnimationResource(TyrannosaurusEntity tyranno) {
        if (tyranno.getVariant() == 1) {
            return new ResourceLocation(UnusualPrehistory.MODID, "animations/tyrannosaurus/tyrannosaurus_mcraeensis.animation.json");
        }
        else return new ResourceLocation(UnusualPrehistory.MODID, "animations/tyrannosaurus/tyrannosaurus_rex.animation.json");
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

