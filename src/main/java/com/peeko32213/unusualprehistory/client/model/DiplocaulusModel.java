package com.peeko32213.unusualprehistory.client.model;


import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.EntityDiplocaulus;
import com.peeko32213.unusualprehistory.common.entity.EntityTanystropheus;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class DiplocaulusModel extends GeoModel<EntityDiplocaulus>
{
    @Override
    public ResourceLocation getModelResource(EntityDiplocaulus object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/diplocaulus.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(EntityDiplocaulus object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/diplocaulus.png");
    }

    @Override
    public ResourceLocation getAnimationResource(EntityDiplocaulus object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/diplocaulus.animation.json");
    }

}

