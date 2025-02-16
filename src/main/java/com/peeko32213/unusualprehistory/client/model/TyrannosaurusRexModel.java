package com.peeko32213.unusualprehistory.client.model;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.EntityTyrannosaurusRex;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class TyrannosaurusRexModel extends GeoModel<EntityTyrannosaurusRex> {

    @Override
    public ResourceLocation getModelResource(EntityTyrannosaurusRex rex) {
        if(rex.isBaby()) {
            return new ResourceLocation(UnusualPrehistory.MODID, "geo/tyrannosaurus_rex/tyrannosaurus_rex_baby.geo.json");
        } else {
            return new ResourceLocation(UnusualPrehistory.MODID, "geo/tyrannosaurus_rex/tyrannosaurus_rex.geo.json");
        }
    }

    @Override
    public ResourceLocation getTextureResource(EntityTyrannosaurusRex rex) {
        if(rex.isBaby()) {
            return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/tyrannosaurus_rex/tyrannosaurus_rex_baby.png");
        } else if(rex.hasEepy() && !rex.isBaby()) {
            return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/tyrannosaurus_rex/tyrannosaurus_rex_eepy.png");
        } else{
            return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/tyrannosaurus_rex/tyrannosaurus_rex.png");
        }
    }

    @Override
    public ResourceLocation getAnimationResource(EntityTyrannosaurusRex rex) {
        if(rex.isBaby()) {
            return new ResourceLocation(UnusualPrehistory.MODID, "animations/tyrannosaurus_rex/tyrannosaurus_rex_baby.animation.json");
        } else {
            return new ResourceLocation(UnusualPrehistory.MODID, "animations/tyrannosaurus_rex/tyrannosaurus_rex.animation.json");
        }
    }

    @Override
    public void setCustomAnimations(EntityTyrannosaurusRex animatable, long instanceId, AnimationState<EntityTyrannosaurusRex> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);
        if (animationState == null) return;
        EntityModelData extraDataOfType = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
        CoreGeoBone head = this.getAnimationProcessor().getBone("Head");

        if (!animatable.isSprinting()) {
            head.setRotY(extraDataOfType.netHeadYaw() * Mth.DEG_TO_RAD);
        }
    }
}

