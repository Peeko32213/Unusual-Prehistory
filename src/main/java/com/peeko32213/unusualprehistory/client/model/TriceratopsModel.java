package com.peeko32213.unusualprehistory.client.model;


import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.EntityMajungasaurus;
import com.peeko32213.unusualprehistory.common.entity.EntityTriceratops;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

import javax.annotation.Nullable;
import java.util.List;

public class TriceratopsModel extends AnimatedGeoModel<EntityTriceratops>
{
    @Override
    public ResourceLocation getModelLocation(EntityTriceratops object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/trike.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityTriceratops object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/trike2.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityTriceratops object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/trike.animation.json");
    }

    @Override
    public void setLivingAnimations(EntityTriceratops dino, Integer uniqueID, @Nullable AnimationEvent customPredicate) {
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

