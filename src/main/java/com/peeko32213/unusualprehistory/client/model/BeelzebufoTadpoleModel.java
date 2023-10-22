package com.peeko32213.unusualprehistory.client.model;


import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.msc.baby.EntityBeelzebufoTadpole;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;
public class BeelzebufoTadpoleModel extends GeoModel<EntityBeelzebufoTadpole>
{
    @Override
    public ResourceLocation getModelResource(EntityBeelzebufoTadpole object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/beelze_baby.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(EntityBeelzebufoTadpole object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/beelzebufo_baby.png");
    }

    @Override
    public ResourceLocation getAnimationResource(EntityBeelzebufoTadpole object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/beelze_tadpole.animation.json");
    }

}

