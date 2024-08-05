package com.peeko32213.unusualprehistory.client.model;


import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.EntityDunkleosteus;
import com.peeko32213.unusualprehistory.common.entity.EntityScaumenacia;
import com.peeko32213.unusualprehistory.common.entity.EntityVelociraptor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class ScaumenaciaModel extends GeoModel<EntityScaumenacia>
{
    @Override
    public ResourceLocation getModelResource(EntityScaumenacia object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/scaumenacia.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(EntityScaumenacia object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/scaumenacia.png");
    }

    @Override
    public ResourceLocation getAnimationResource(EntityScaumenacia object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/scaumenacia.animation.json");
    }

    @Override
    public void setCustomAnimations(EntityScaumenacia animatable, long instanceId, AnimationState<EntityScaumenacia> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);
        if (animationState == null) return;
        if (animatable.isFromBook()) return;

        CoreGeoBone backBody = this.getAnimationProcessor().getBone("Body");
        //CoreGeoBone tailfin = this.getAnimationProcessor().getBone("Tailfin");
        EntityModelData extraDataOfType = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

        CoreGeoBone root = this.getAnimationProcessor().getBone("Body");
        //7
        root.setRotX(extraDataOfType.headPitch() * (Mth.DEG_TO_RAD / 2));
        //root.setRotZ(Mth.clamp(Mth.lerp(0.1F, Mth.cos(animatable.yBodyRot * 0.1F) * 0.1F, 1.0F), -15F, 15F));

        backBody.setRotY(backBody.getRotY() + extraDataOfType.netHeadYaw() * ((float) Math.PI / 270F));
        //tailfin.setRotZ(tailfin.getRotY() + extraDataOfType.netHeadYaw() * ((float) Math.PI / 270F));
    }
}

