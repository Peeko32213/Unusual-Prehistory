package com.peeko32213.unusualprehistory.client.model;


import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.EntityCotylorhynchus;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

import javax.annotation.Nullable;
import java.util.List;

public class CotylorhynchusModel extends GeoModel<EntityCotylorhynchus>
{
    @Override
    public ResourceLocation getModelResource(EntityCotylorhynchus object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/cotylorhynchus.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(EntityCotylorhynchus object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/cotylorhynchus.png");
    }

    @Override
    public ResourceLocation getAnimationResource(EntityCotylorhynchus object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/coty.animation.json");
    }

    @Override
    public void setCustomAnimations(EntityCotylorhynchus dino, int uniqueID, @Nullable AnimationEvent customPredicate) {
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

