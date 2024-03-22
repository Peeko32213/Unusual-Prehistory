package com.peeko32213.unusualprehistory.client.model;


import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.EntityArchelon;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;


public class ArchelonModel extends GeoModel<EntityArchelon>
{
    @Override
    public ResourceLocation getModelResource(EntityArchelon object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/archelon.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(EntityArchelon object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/archelon.png");
    }

    @Override
    public ResourceLocation getAnimationResource(EntityArchelon object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/archelon.animation.json");
    }

    @Override
    public void setCustomAnimations(EntityArchelon animatable, long instanceId, AnimationState<EntityArchelon> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);
        if (animationState == null) return;
        CoreGeoBone root = this.getAnimationProcessor().getBone("Body");
        CoreGeoBone tailfin = this.getAnimationProcessor().getBone("Tail");
        EntityModelData extraDataOfType = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

        if (animatable.isInWater()) {
            root.setRotX(extraDataOfType.headPitch() * (Mth.DEG_TO_RAD / 7));
            root.setRotZ(Mth.clamp(Mth.lerp(0.1F, Mth.cos(animatable.yBodyRot * 0.1F) * 0.1F, 1.0F), -15F, 15F));

            tailfin.setRotZ(tailfin.getRotY() + extraDataOfType.netHeadYaw() * ((float) Math.PI / 270F));

        }
    }

}

