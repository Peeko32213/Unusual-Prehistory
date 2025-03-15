 package com.peeko32213.unusualprehistory.client.model.entity;

 import com.peeko32213.unusualprehistory.UnusualPrehistory;
 import com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.aquatic.JawlessFishEntity;
 import net.minecraft.resources.ResourceLocation;
 import net.minecraft.util.Mth;
 import software.bernie.geckolib.constant.DataTickets;
 import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
 import software.bernie.geckolib.core.animation.AnimationState;
 import software.bernie.geckolib.model.GeoModel;
 import software.bernie.geckolib.model.data.EntityModelData;

 public class JawlessFishModel extends GeoModel<JawlessFishEntity> {

     private static final ResourceLocation MODEL_CEPHALAPIS = new ResourceLocation(UnusualPrehistory.MODID, "geo/jawless_fish/cephalaspis.geo.json");
     private static final ResourceLocation MODEL_DORYASPIS = new ResourceLocation(UnusualPrehistory.MODID, "geo/jawless_fish/doryaspis.geo.json");
     private static final ResourceLocation MODEL_FURCACAUDA = new ResourceLocation(UnusualPrehistory.MODID, "geo/jawless_fish/furcacauda.geo.json");
     private static final ResourceLocation MODEL_SACAMAMBASPIS = new ResourceLocation(UnusualPrehistory.MODID, "geo/jawless_fish/sacabambaspis.geo.json");


     private static final ResourceLocation TEXTURE_CEPHALAPIS = new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/jawless_fish/cephalaspis.png");
     private static final ResourceLocation TEXTURE_DORYASPIS = new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/jawless_fish/doryaspis.png");
     private static final ResourceLocation TEXTURE_FURCACAUDA = new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/jawless_fish/furacacauda.png");
     private static final ResourceLocation TEXTURE_SACAMAMBASPIS = new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/jawless_fish/sacabambaspis.png");
     @Override
     public ResourceLocation getModelResource(JawlessFishEntity object)
     {
         return switch (object.getVariant()) {
             case 1 -> MODEL_DORYASPIS;
             case 2 -> MODEL_CEPHALAPIS;
             case 3 -> MODEL_FURCACAUDA;
             default -> MODEL_SACAMAMBASPIS;
         };
     }

     @Override
     public ResourceLocation getTextureResource(JawlessFishEntity animatable) {
         return switch (animatable.getVariant()) {
             case 1 -> TEXTURE_DORYASPIS;
             case 2 -> TEXTURE_CEPHALAPIS;
             case 3 -> TEXTURE_FURCACAUDA;
             default -> TEXTURE_SACAMAMBASPIS;
         };
     }

     @Override
     public ResourceLocation getAnimationResource(JawlessFishEntity object)
     {
         return new ResourceLocation(UnusualPrehistory.MODID, "animations/jawless_fish.animation.json");
     }

     @Override
     public void setCustomAnimations(JawlessFishEntity animatable, long instanceId, AnimationState<JawlessFishEntity> animationState) {
         super.setCustomAnimations(animatable, instanceId, animationState);
     }
 }
