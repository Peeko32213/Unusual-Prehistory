package com.peeko32213.unusualprehistory.client.model;


import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.EntityKentrosaurus;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

import javax.annotation.Nullable;
import java.util.List;

public class KentrosaurusModel extends GeoModel<EntityKentrosaurus>
{
    @Override
    public ResourceLocation getModelResource(EntityKentrosaurus object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/kentrosaurus.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(EntityKentrosaurus object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/kentrosaurus.png");
    }

    @Override
    public ResourceLocation getAnimationResource(EntityKentrosaurus object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/kentro.animation.json");
    }

    @Override
    public void setCustomAnimations(EntityKentrosaurus dino, int uniqueID, @Nullable AnimationEvent customPredicate) {
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

