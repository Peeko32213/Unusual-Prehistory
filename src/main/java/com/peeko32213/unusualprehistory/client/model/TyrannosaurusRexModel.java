package com.peeko32213.unusualprehistory.client.model;


import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.EntityTyrannosaurusRex;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

import javax.annotation.Nullable;
import java.util.List;

public class TyrannosaurusRexModel extends GeoModel<EntityTyrannosaurusRex>
{
    @Override
    public ResourceLocation getModelResource(EntityTyrannosaurusRex object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/rex.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(EntityTyrannosaurusRex object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/tyrannosaurus_rex.png");
    }

    @Override
    public ResourceLocation getAnimationResource(EntityTyrannosaurusRex object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/rex.animation.json");
    }

    @Override
    public void setCustomAnimations(EntityTyrannosaurusRex dino, int uniqueID, @Nullable AnimationEvent customPredicate) {
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

