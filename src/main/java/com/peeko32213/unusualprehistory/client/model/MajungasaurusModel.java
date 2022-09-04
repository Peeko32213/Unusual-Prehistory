package com.peeko32213.unusualprehistory.client.model;


import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.EntityMajungasaurus;
import com.peeko32213.unusualprehistory.common.entity.EntityStethacanthus;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class MajungasaurusModel extends AnimatedGeoModel<EntityMajungasaurus>
{
    @Override
    public ResourceLocation getModelLocation(EntityMajungasaurus object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/majungasaurus.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityMajungasaurus object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/majungasaurus.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityMajungasaurus object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/majungasaurus.animation.json");
    }

}

