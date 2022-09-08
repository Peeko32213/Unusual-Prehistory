package com.peeko32213.unusualprehistory.client.model;


import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.EntityBeelzebufo;
import com.peeko32213.unusualprehistory.common.entity.EntityCotylorhynchus;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class CotylorhynchusModel extends AnimatedGeoModel<EntityCotylorhynchus>
{
    @Override
    public ResourceLocation getModelLocation(EntityCotylorhynchus object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/cotylorhynchus.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityCotylorhynchus object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/coty.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityCotylorhynchus object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/coty.animation.json");
    }

}

