package com.peeko32213.unusualprehistory.client.model;


import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.EntitySmilodon;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class SmilodonModel extends GeoModel<EntitySmilodon>
{
    @Override
    public ResourceLocation getModelResource(EntitySmilodon object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/smilodon.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(EntitySmilodon object)
    {
        if(object.getVariant() == 1){
            return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/smilodon_ocelot.png");
        }

        return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/smilodon.png");
    }

    @Override
    public ResourceLocation getAnimationResource(EntitySmilodon object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/smilodon.animation.json");
    }

}

