//package com.peeko32213.unusualprehistory.client.model;
//
//
//import com.peeko32213.unusualprehistory.UnusualPrehistory;
//import com.peeko32213.unusualprehistory.common.entity.EntityLeedsichthys;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.util.Mth;
//import software.bernie.geckolib.constant.DataTickets;
//import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
//import software.bernie.geckolib.core.animation.AnimationState;
//import software.bernie.geckolib.model.GeoModel;
//import software.bernie.geckolib.model.data.EntityModelData;
//
//
//public class LeedsichthysModel extends GeoModel<EntityLeedsichthys>
//{
//    @Override
//    public ResourceLocation getModelResource(EntityLeedsichthys object)
//    {
//        return new ResourceLocation(UnusualPrehistory.MODID, "geo/leedsichthys.geo.json");
//    }
//
//    @Override
//    public ResourceLocation getTextureResource(EntityLeedsichthys object)
//    {
//        return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/leedsichthys.png");
//    }
//
//    @Override
//    public ResourceLocation getAnimationResource(EntityLeedsichthys object)
//    {
//        return new ResourceLocation(UnusualPrehistory.MODID, "animations/leedsichthys.animation.json");
//    }
//
//    @Override
//    public void setCustomAnimations(EntityLeedsichthys animatable, long instanceId, AnimationState<EntityLeedsichthys> animationState) {
//        super.setCustomAnimations(animatable, instanceId, animationState);
//        if (animationState == null) return;
//
//        CoreGeoBone backBody = this.getAnimationProcessor().getBone("BackBody");
//        CoreGeoBone tailfin = this.getAnimationProcessor().getBone("Tail");
//        EntityModelData extraDataOfType = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
//
//        CoreGeoBone root = this.getAnimationProcessor().getBone("FrontBody");
//        root.setRotX(extraDataOfType.headPitch() * (Mth.DEG_TO_RAD / 7));
//        root.setRotZ(Mth.clamp(Mth.lerp(0.1F, Mth.cos(animatable.yBodyRot * 0.1F) * 0.1F, 1.0F), -15F, 15F));
//
//        backBody.setRotY(backBody.getRotY() + extraDataOfType.netHeadYaw() * ((float) Math.PI / 180F));
//        tailfin.setRotZ(tailfin.getRotY() + extraDataOfType.netHeadYaw() * ((float) Math.PI / 180F));
//
//    }
//
//}
//
