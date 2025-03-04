package com.peeko32213.unusualprehistory.client.model.entity;


import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.OphiodonEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;


public class OphiodonModel extends GeoModel<OphiodonEntity>
{
    @Override
    public ResourceLocation getModelResource(OphiodonEntity object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/ophiodon.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(OphiodonEntity object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/ophiodon.png");
    }

    @Override
    public ResourceLocation getAnimationResource(OphiodonEntity object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/ophiodon.animation.json");
    }

    @Override
    public void setCustomAnimations(OphiodonEntity animatable, long instanceId, AnimationState<OphiodonEntity> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);
        if (animationState == null) return;
        if(animatable.isFromBook()) return;
        CoreGeoBone backBody = this.getAnimationProcessor().getBone("BackBody");
        CoreGeoBone tailfin = this.getAnimationProcessor().getBone("Tail");
        EntityModelData extraDataOfType = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

        CoreGeoBone root = this.getAnimationProcessor().getBone("root");
        root.setRotX(extraDataOfType.headPitch() * (Mth.DEG_TO_RAD / 7));
        root.setRotZ(Mth.clamp(Mth.lerp(0.1F, Mth.cos(animatable.yBodyRot * 0.1F) * 0.1F, 1.0F), -15F, 15F));

        backBody.setRotY(backBody.getRotY() + extraDataOfType.netHeadYaw() * ((float) Math.PI / 270F));
        tailfin.setRotZ(tailfin.getRotY() + extraDataOfType.netHeadYaw() * ((float) Math.PI / 270F));

    }

}

