package com.peeko32213.unusualprehistory.client.render.egg;

import com.mojang.blaze3d.vertex.PoseStack;
import com.peeko32213.unusualprehistory.client.model.DefaultModel;
import com.peeko32213.unusualprehistory.client.model.DinosaurLandEggModel;
import com.peeko32213.unusualprehistory.client.model.ModelLocations;
import com.peeko32213.unusualprehistory.client.render.layer.*;
import com.peeko32213.unusualprehistory.common.entity.EntityKimmeridgebrachypteraeschnidium;
import com.peeko32213.unusualprehistory.common.entity.eggs.DinosaurLandEgg;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class DinosaurLandEggRenderer extends GeoEntityRenderer<DinosaurLandEgg> {

    public DinosaurLandEggRenderer(EntityRendererProvider.Context context) {
        super(context, new DinosaurLandEggModel());
        this.addRenderLayer(new DinosaurLandEggBaseColorLayer(this));
        this.addRenderLayer(new DinosaurLandEggSpotColorLayer(this));
        this.addRenderLayer(new DinosaurLandEggCrackLayer(this));
    }

    @Override
    public RenderType getRenderType(DinosaurLandEgg animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityCutout(getTextureLocation(animatable));
    }


    @Override
    public boolean shouldShowName(DinosaurLandEgg animatable) {
        return super.shouldShowName(animatable);
    }
}
