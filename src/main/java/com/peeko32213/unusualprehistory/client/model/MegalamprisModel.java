package com.peeko32213.unusualprehistory.client.model;


import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.EntityMegalampris;
import com.peeko32213.unusualprehistory.common.entity.EntityProtosphyraena;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;


public class MegalamprisModel extends GeoModel<EntityMegalampris>
{
    @Override
    public ResourceLocation getModelResource(EntityMegalampris object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/megalampris.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(EntityMegalampris object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/megalampris.png");
    }

    @Override
    public ResourceLocation getAnimationResource(EntityMegalampris object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/megalampris.animation.json");
    }

    @Override
    public void setCustomAnimations(EntityMegalampris animatable, long instanceId, AnimationState<EntityMegalampris> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);
        if (animationState == null) return;

        CoreGeoBone body = this.getAnimationProcessor().getBone("blastercore");
        EntityModelData extraDataOfType = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

        //dynamic pitch
        if (animatable.isInWater()){
            body.setRotX(extraDataOfType.headPitch() * (float)Math.PI / 180F);
            body.setRotY(extraDataOfType.netHeadYaw() * (float)Math.PI / 180F);
        }
        //end of dynamic pitch


    }

}
