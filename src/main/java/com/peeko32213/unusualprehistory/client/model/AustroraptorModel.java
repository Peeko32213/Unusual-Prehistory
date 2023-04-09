package com.peeko32213.unusualprehistory.client.model;


import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.EntityAustroraptor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

import javax.annotation.Nullable;
import java.util.List;

public class AustroraptorModel extends AnimatedGeoModel<EntityAustroraptor>
{
    @Override
    public ResourceLocation getModelResource(EntityAustroraptor object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/austroraptor.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(EntityAustroraptor object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/austroraptor.png");
    }

    @Override
    public ResourceLocation getAnimationResource(EntityAustroraptor object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/austroraptor.animation.json");
    }

    @Override
    public void setCustomAnimations(EntityAustroraptor dino, int uniqueID, @Nullable AnimationEvent customPredicate) {
        super.setCustomAnimations(dino, uniqueID, customPredicate);
        super.setCustomAnimations(dino, uniqueID, customPredicate);

        if (customPredicate == null) return;

        List<EntityModelData> extraDataOfType = customPredicate.getExtraDataOfType(EntityModelData.class);
        IBone head = this.getAnimationProcessor().getBone("Neck");

        if (!dino.isSprinting()) {
            head.setRotationY(extraDataOfType.get(0).netHeadYaw * Mth.DEG_TO_RAD);
        }
    }

}

