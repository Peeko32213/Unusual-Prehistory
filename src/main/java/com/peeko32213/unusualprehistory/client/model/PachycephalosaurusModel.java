package com.peeko32213.unusualprehistory.client.model;


import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.EntityPachycephalosaurus;
import com.peeko32213.unusualprehistory.common.entity.EntityTriceratops;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

import javax.annotation.Nullable;
import java.util.List;

public class PachycephalosaurusModel extends AnimatedGeoModel<EntityPachycephalosaurus>
{
    @Override
    public ResourceLocation getModelResource(EntityPachycephalosaurus object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/pachy.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(EntityPachycephalosaurus object)
    {
        if (object.isUlti()) {
            return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/pachycephalosaurus_ulti.png");
        }
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/pachycephalosaurus.png");
    }

    @Override
    public ResourceLocation getAnimationResource(EntityPachycephalosaurus object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/pachy.animation.json");
    }

    public void setCustomAnimations(EntityPachycephalosaurus dino, Integer uniqueID, @Nullable AnimationEvent customPredicate) {
        super.setCustomAnimations(dino, uniqueID, customPredicate);

        if (customPredicate == null) return;

        List<EntityModelData> extraDataOfType = customPredicate.getExtraDataOfType(EntityModelData.class);
        IBone head = this.getAnimationProcessor().getBone("Head");

        if (dino.isBaby()) {
            head.setScaleX(1.75F);
            head.setScaleY(1.75F);
            head.setScaleZ(1.75F);
        }

        if (!dino.isSprinting()) {
            head.setRotationY(extraDataOfType.get(0).netHeadYaw * Mth.DEG_TO_RAD);
        }
    }

}

