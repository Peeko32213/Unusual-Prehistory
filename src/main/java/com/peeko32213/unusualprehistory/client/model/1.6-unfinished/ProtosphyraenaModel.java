//package com.peeko32213.unusualprehistory.client.model;
//
//
//import com.peeko32213.unusualprehistory.UnusualPrehistory;
//import com.peeko32213.unusualprehistory.common.entity.EntityProtosphyraena;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.util.Mth;
//import software.bernie.geckolib.constant.DataTickets;
//import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
//import software.bernie.geckolib.core.animation.AnimationState;
//import software.bernie.geckolib.model.GeoModel;
//import software.bernie.geckolib.model.data.EntityModelData;
//
//
//public class ProtosphyraenaModel extends GeoModel<EntityProtosphyraena>
//{
//    @Override
//    public ResourceLocation getModelResource(EntityProtosphyraena object)
//    {
//        return new ResourceLocation(UnusualPrehistory.MODID, "geo/protosphyraena.geo.json");
//    }
//
//    @Override
//    public ResourceLocation getTextureResource(EntityProtosphyraena object)
//    {
//        return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/protosphyraena.png");
//    }
//
//    @Override
//    public ResourceLocation getAnimationResource(EntityProtosphyraena object)
//    {
//        return new ResourceLocation(UnusualPrehistory.MODID, "animations/protosphyraena.animation.json");
//    }
//
//    @Override
//    public void setCustomAnimations(EntityProtosphyraena animatable, long instanceId, AnimationState<EntityProtosphyraena> animationState) {
//        super.setCustomAnimations(animatable, instanceId, animationState);
//        if (animationState == null) return;
//
//        CoreGeoBone backBody = this.getAnimationProcessor().getBone("MidBody");
//        CoreGeoBone tailfin = this.getAnimationProcessor().getBone("Tail");
//        EntityModelData extraDataOfType = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
//
//        CoreGeoBone root = this.getAnimationProcessor().getBone("Body");
//        root.setRotX(extraDataOfType.headPitch() * (Mth.DEG_TO_RAD / 7));
//        root.setRotZ(Mth.clamp(Mth.lerp(0.1F, Mth.cos(animatable.yBodyRot * 0.1F) * 0.1F, 1.0F), -15F, 15F));
//
//        backBody.setRotY(backBody.getRotY() + extraDataOfType.netHeadYaw() * ((float) Math.PI / 270F));
//        tailfin.setRotZ(tailfin.getRotY() + extraDataOfType.netHeadYaw() * ((float) Math.PI / 270F));
//
//    }
//
//}
//
