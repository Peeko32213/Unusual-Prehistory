package com.peeko32213.unusualprehistory.client.model;


import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.EntityDunkleosteus;
import com.peeko32213.unusualprehistory.common.entity.EntityPalaeophis;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class PalaeophisModel extends AnimatedGeoModel<EntityPalaeophis>
{
    @Override
    public ResourceLocation getModelResource(EntityPalaeophis object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/palaeophis.geo.json");

    }

    @Override
    public ResourceLocation getTextureResource(EntityPalaeophis object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/palaeophis.png");
    }

    @Override
    public ResourceLocation getAnimationResource(EntityPalaeophis object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/palaeophis.animation.json");
    }

    @Override
    public void setLivingAnimations(EntityPalaeophis entity, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        IBone head = this.getAnimationProcessor().getBone("Head");
        IBone body1 = this.getAnimationProcessor().getBone("Body1");
        IBone body2 = this.getAnimationProcessor().getBone("Body2");
        IBone body3 = this.getAnimationProcessor().getBone("Body3");
        IBone body4 = this.getAnimationProcessor().getBone("Body4");
        IBone body5 = this.getAnimationProcessor().getBone("Body5");
        IBone body6 = this.getAnimationProcessor().getBone("Body6");
        IBone body7 = this.getAnimationProcessor().getBone("Body7");
        IBone body8 = this.getAnimationProcessor().getBone("Body8");
        IBone body9 = this.getAnimationProcessor().getBone("Body9");
        IBone body10 = this.getAnimationProcessor().getBone("Body10");
        IBone body11 = this.getAnimationProcessor().getBone("Body11");
        IBone body12 = this.getAnimationProcessor().getBone("Body12");
        IBone body13 = this.getAnimationProcessor().getBone("Body13");
        IBone body14 = this.getAnimationProcessor().getBone("Body14");
        IBone body15 = this.getAnimationProcessor().getBone("Body15");

        EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);

        AnimationData manager = entity.getFactory().getOrCreateAnimationData(uniqueID);
        int unpausedMultiplier = !Minecraft.getInstance().isPaused() || manager.shouldPlayWhilePaused ? 1 : 0;

        head.setRotationY(head.getRotationY() + extraData.netHeadYaw * ((float) Math.PI / 270F) * unpausedMultiplier);

        body1.setRotationY(body1.getRotationY() + extraData.netHeadYaw * ((float) Math.PI / 270F) * unpausedMultiplier);

        body2.setRotationY(body2.getRotationY() + extraData.netHeadYaw * ((float) Math.PI / 270F) * unpausedMultiplier);

        body3.setRotationY(body3.getRotationY() + extraData.netHeadYaw * ((float) Math.PI / 270F) * unpausedMultiplier);

        body4.setRotationY(body4.getRotationY() + extraData.netHeadYaw * ((float) Math.PI / 270F) * unpausedMultiplier);

        body5.setRotationY(body5.getRotationY() + extraData.netHeadYaw * ((float) Math.PI / 270F) * unpausedMultiplier);

        body6.setRotationY(body6.getRotationY() + extraData.netHeadYaw * ((float) Math.PI / 270F) * unpausedMultiplier);

        body7.setRotationY(body7.getRotationY() + extraData.netHeadYaw * ((float) Math.PI / 270F) * unpausedMultiplier);

        body8.setRotationY(body8.getRotationY() + extraData.netHeadYaw * ((float) Math.PI / 270F) * unpausedMultiplier);

        body9.setRotationY(body9.getRotationY() + extraData.netHeadYaw * ((float) Math.PI / 270F) * unpausedMultiplier);

        body10.setRotationY(body10.getRotationY() + extraData.netHeadYaw * ((float) Math.PI / 270F) * unpausedMultiplier);

        body11.setRotationY(body11.getRotationY() + extraData.netHeadYaw * ((float) Math.PI / 270F) * unpausedMultiplier);

        body12.setRotationY(body12.getRotationY() + extraData.netHeadYaw * ((float) Math.PI / 270F) * unpausedMultiplier);

        body13.setRotationY(body13.getRotationY() + extraData.netHeadYaw * ((float) Math.PI / 270F) * unpausedMultiplier);

        body14.setRotationY(body14.getRotationY() + extraData.netHeadYaw * ((float) Math.PI / 270F) * unpausedMultiplier);

        body15.setRotationY(body15.getRotationY() + extraData.netHeadYaw * ((float) Math.PI / 270F) * unpausedMultiplier);
    }

}

