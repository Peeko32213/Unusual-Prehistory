package com.peeko32213.unusualprehistory.client.model;


import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.EntityTyrannosaurusRex;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;
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

}

