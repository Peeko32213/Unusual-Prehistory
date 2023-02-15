package com.peeko32213.unusualprehistory.client.render;


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.peeko32213.unusualprehistory.client.model.AnurognathusModel;
import com.peeko32213.unusualprehistory.client.model.EryonModel;
import com.peeko32213.unusualprehistory.common.entity.EntityAnurognathus;
import com.peeko32213.unusualprehistory.common.entity.EntityEryon;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class EryonRenderer extends GeoEntityRenderer<EntityEryon> {

    public EryonRenderer(EntityRendererProvider.Context context) {
        super(context, new EryonModel());
    }

}
