//package com.peeko32213.unusualprehistory.client.render.prehistoric.data;
//
//import com.peeko32213.unusualprehistory.client.model.data.PrehistoricEntityDatafiedModel;
//import com.peeko32213.unusualprehistory.common.data.entity.UPRenderTypes;
//import com.peeko32213.unusualprehistory.common.entity.custom.base.data.PrehistoricEntityDatafied;
//import com.peeko32213.unusualprehistory.common.entity.custom.part.PalaeophisPartEntity;
//import net.minecraft.client.renderer.MultiBufferSource;
//import net.minecraft.client.renderer.RenderType;
//import net.minecraft.client.renderer.entity.EntityRendererProvider;
//import net.minecraft.resources.ResourceLocation;
//import org.jetbrains.annotations.Nullable;
//import software.bernie.geckolib.model.GeoModel;
//import software.bernie.geckolib.renderer.GeoEntityRenderer;
//
//public class PrehistoricEntityDatafiedRenderer extends GeoEntityRenderer<PrehistoricEntityDatafied> {
//    public PrehistoricEntityDatafiedRenderer(EntityRendererProvider.Context renderManager) {
//        super(renderManager, new PrehistoricEntityDatafiedModel());
//    }
//
//    @Override
//    public RenderType getRenderType(PrehistoricEntityDatafied animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
//        UPRenderTypes.RenderTypes renderTypes = animatable.getRenderType();
//        if(renderTypes == null) {
//            return RenderType.entityCutoutNoCull(getTextureLocation(animatable));
//        }
//        RenderType renderType = renderTypes.getRenderType(animatable, this);
//        if(renderType == null) {
//            return RenderType.entityCutoutNoCull(getTextureLocation(animatable));
//        }
//        return renderType;
//    }
//}
