//package com.peeko32213.unusualprehistory.client.model.data;
//
//import com.peeko32213.unusualprehistory.common.entity.custom.base.data.PrehistoricEntityDatafied;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.util.Mth;
//import software.bernie.geckolib.constant.DataTickets;
//import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
//import software.bernie.geckolib.core.animation.AnimationState;
//import software.bernie.geckolib.model.GeoModel;
//import software.bernie.geckolib.model.data.EntityModelData;
//
//public class PrehistoricEntityDatafiedModel extends GeoModel<PrehistoricEntityDatafied> {
//    @Override
//    public ResourceLocation getModelResource(PrehistoricEntityDatafied animatable) {
//        return animatable.getModelLocation();
//    }
//
//    @Override
//    public ResourceLocation getTextureResource(PrehistoricEntityDatafied animatable) {
//        return animatable.getTextureLocation();
//    }
//
//    @Override
//    public ResourceLocation getAnimationResource(PrehistoricEntityDatafied animatable) {
//        return animatable.getAnimationLocation();
//    }
//
//    @Override
//    public void setCustomAnimations(PrehistoricEntityDatafied animatable, long instanceId, AnimationState<PrehistoricEntityDatafied> animationState) {
//        if (!animatable.getTurnsHead())
//            return;
//
//        CoreGeoBone head = getAnimationProcessor().getBone("head");
//
//        if (head != null) {
//            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
//
//            head.setRotX(entityData.headPitch() * Mth.DEG_TO_RAD);
//            head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
//        }
//    }
//}
