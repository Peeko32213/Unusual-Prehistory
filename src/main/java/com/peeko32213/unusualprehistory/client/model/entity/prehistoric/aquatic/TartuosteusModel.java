 package com.peeko32213.unusualprehistory.client.model.entity.prehistoric.aquatic;

 import com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.aquatic.TartuosteusEntity;
 import net.minecraft.resources.ResourceLocation;
 import software.bernie.geckolib.constant.DataTickets;
 import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
 import software.bernie.geckolib.core.animation.AnimationState;
 import software.bernie.geckolib.model.GeoModel;
 import software.bernie.geckolib.model.data.EntityModelData;

 import static com.peeko32213.unusualprehistory.UnusualPrehistory.modPrefix;

 public class TartuosteusModel extends GeoModel<TartuosteusEntity> {

     private static final ResourceLocation TEXTURE = modPrefix("textures/entity/tartuosteus/tartuosteus.png");
     private static final ResourceLocation EVIL_TEXTURE = modPrefix("textures/entity/tartuosteus/evil_tartuosteus.png");
     private static final ResourceLocation MODEL = modPrefix("geo/tartuosteus.geo.json");
     private static final ResourceLocation ANIMATIONS = modPrefix("animations/tartuosteus.animation.json");

     @Override
     public ResourceLocation getModelResource(TartuosteusEntity animatable) {
         return MODEL;
     }


     @Override
     public ResourceLocation getTextureResource(TartuosteusEntity animatable) {
         if (animatable.getVariant() == 1) {
             return EVIL_TEXTURE;
         }
         else return TEXTURE;
     }

     @Override
     public ResourceLocation getAnimationResource(TartuosteusEntity animatable) {
         return ANIMATIONS;
     }

     @Override
     public void setCustomAnimations(TartuosteusEntity animatable, long instanceId, AnimationState<TartuosteusEntity> animationState) {
         super.setCustomAnimations(animatable, instanceId, animationState);
         CoreGeoBone swimControl = this.getAnimationProcessor().getBone("swim_control");

         EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

         swimControl.setRotX(((entityData.headPitch() * ((float) Math.PI / 180F))));
         swimControl.setRotZ(-((entityData.netHeadYaw() * ((float) Math.PI / 180F)) / 2));
     }
 }
