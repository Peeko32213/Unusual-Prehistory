package com.peeko32213.unusualprehistory.client.render.dinosaur_renders;


import com.peeko32213.unusualprehistory.common.entity.msc.util.dino.EntityTameableBaseDinosaurAnimal;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class TameableDinosaurCutoutNoCullRenderer<T extends EntityTameableBaseDinosaurAnimal> extends GeoEntityRenderer<T> {


    public TameableDinosaurCutoutNoCullRenderer(EntityRendererProvider.Context renderManager, GeoModel<T> modelProvider) {
        super(renderManager, modelProvider);
    }


    @Override
    public RenderType getRenderType(T animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityCutoutNoCull(getTextureLocation(animatable));
    }

}