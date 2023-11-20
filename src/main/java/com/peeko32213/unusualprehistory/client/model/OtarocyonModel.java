package com.peeko32213.unusualprehistory.client.model;


import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.EntityAustroraptor;
import com.peeko32213.unusualprehistory.common.entity.EntityMajungasaurus;
import com.peeko32213.unusualprehistory.common.entity.EntityOtarocyon;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class OtarocyonModel extends GeoModel<EntityOtarocyon>
{
    @Override
    public ResourceLocation getModelResource(EntityOtarocyon object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/otarocyon.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(EntityOtarocyon object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/otarocyon.png");
    }

    @Override
    public ResourceLocation getAnimationResource(EntityOtarocyon object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/otarocyon.animation.json");
    }

}

