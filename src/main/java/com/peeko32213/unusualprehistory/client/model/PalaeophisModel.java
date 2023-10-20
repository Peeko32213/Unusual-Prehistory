package com.peeko32213.unusualprehistory.client.model;


import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.EntityPalaeophis;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

import static com.peeko32213.unusualprehistory.UnusualPrehistory.prefix;

public class PalaeophisModel extends GeoModel<EntityPalaeophis>
{
    private ResourceLocation headModel = prefix("geo/palaeophis_head.geo.json");
    private ResourceLocation DEEP_ONE_SHED = prefix("textures/entity/palaeophis_deep_head_shed.png");
    private ResourceLocation DEEP_ONE = prefix("textures/entity/palaeophis_deep_head.png");
    private ResourceLocation NORMAL = prefix("textures/entity/palaeophis_head.png");
    private ResourceLocation NORMAL_SHED = prefix("textures/entity/palaeophis_head_shed.png");
    @Override
    public ResourceLocation getModelResource(EntityPalaeophis object)
    {
        return headModel;

    }

    @Override
    public ResourceLocation getTextureResource(EntityPalaeophis object)
    {
        if(object.getVariant() == 1){
            if(object.isShedding()){
                return DEEP_ONE_SHED;

            }

            return DEEP_ONE;
        }

        if(object.isShedding()){
            return NORMAL_SHED;

        }
        return NORMAL;
    }

    @Override
    public ResourceLocation getAnimationResource(EntityPalaeophis object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/palaeophis_head.animation.json");
    }

}

