package com.peeko32213.unusualprehistory.client.model;


import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.EntityBrachiosaurus;
import com.peeko32213.unusualprehistory.common.entity.baby.EntityBrachiosaurusTeen;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

import javax.annotation.Nullable;
import java.util.List;

public class BrachiosaurusTeenModel extends AnimatedGeoModel<EntityBrachiosaurusTeen>
{
    @Override
    public ResourceLocation getModelResource(EntityBrachiosaurusTeen object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/brachi_teen.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(EntityBrachiosaurusTeen object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/brachiosaurus_teen.png");
    }

    @Override
    public ResourceLocation getAnimationResource(EntityBrachiosaurusTeen object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/brachi.animation.json");
    }

}

