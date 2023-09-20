package com.peeko32213.unusualprehistory.client.model;


import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.msc.baby.EntityBabyMegalania;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

import static com.peeko32213.unusualprehistory.UnusualPrehistory.prefix;

public class BabyMegalaniaModel extends AnimatedGeoModel<EntityBabyMegalania>
{

    private static final ResourceLocation NORMAL_LOCATION = prefix("textures/entity/baby_megalania.png");
    private static final ResourceLocation HOT_LOCATION = prefix("textures/entity/baby_megalania_hot.png");
    private static final ResourceLocation COLD_LOCATION = prefix("textures/entity/baby_megalania_cold.png");
    private static final ResourceLocation NETHER_LOCATION = prefix("textures/entity/baby_megalania_nether.png");



    @Override
    public ResourceLocation getModelResource(EntityBabyMegalania object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/baby_megalania.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(EntityBabyMegalania object)
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
    public ResourceLocation getAnimationResource(EntityBabyMegalania object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/baby_megalania.animation.json");
    }

}

