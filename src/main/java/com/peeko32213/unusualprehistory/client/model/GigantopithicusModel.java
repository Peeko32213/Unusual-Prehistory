package com.peeko32213.unusualprehistory.client.model;


import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.EntityGigantopithicus;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class GigantopithicusModel extends AnimatedGeoModel<EntityGigantopithicus>
{

    private static final ResourceLocation TEXTURE_NORMAL = new ResourceLocation("unusualprehistory:textures/entity/gigantopithicus.png");
    private static final ResourceLocation TEXTURE_VARIANT = new ResourceLocation("unusualprehistory:textures/entity/braypithicus.png");


    @Override
    public ResourceLocation getModelResource(EntityGigantopithicus object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/gigantopithicus.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(EntityGigantopithicus object)
    {



        if (object.hasCustomName()) {
            if(object.getCustomName().getString().toLowerCase().equals("braypithicus")){
                return TEXTURE_VARIANT;
            }

        }
        return TEXTURE_NORMAL;
    }

    @Override
    public ResourceLocation getAnimationResource(EntityGigantopithicus object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/gigantopithicus.animation.json");
    }

}

