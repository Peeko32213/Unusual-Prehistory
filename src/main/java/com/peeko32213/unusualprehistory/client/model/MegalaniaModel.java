package com.peeko32213.unusualprehistory.client.model;


import com.peeko32213.unusualprehistory.common.entity.EntityMegalania;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

import static com.peeko32213.unusualprehistory.UnusualPrehistory.prefix;

public class MegalaniaModel extends AnimatedGeoModel<EntityMegalania>
{
    private static final ResourceLocation NORMAL_LOCATION = prefix("textures/entity/megalania.png");
    private static final ResourceLocation HOT_LOCATION = prefix("textures/entity/megalania_hot.png");
    private static final ResourceLocation COLD_LOCATION = prefix("textures/entity/megalania_cold.png");
    private static final ResourceLocation NETHER_LOCATION = prefix("textures/entity/megalania_nether.png");
    @Override
    public ResourceLocation getModelResource(EntityMegalania object)
    {
        return prefix("geo/megalania.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(EntityMegalania object)
    {
        if(object.getVariant() == 1){
            return COLD_LOCATION;
        }
        if(object.getVariant() == 2)
        {
            return HOT_LOCATION;
        }
        if(object.getVariant() == 3)
        {
            return NETHER_LOCATION;
        }

        return NORMAL_LOCATION;
    }

    @Override
    public ResourceLocation getAnimationResource(EntityMegalania object)
    {
        return prefix("animations/megalania.animation.json");
    }

}

