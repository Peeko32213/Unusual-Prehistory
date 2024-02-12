package com.peeko32213.unusualprehistory.client.render.dinosaur_renders;


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.peeko32213.unusualprehistory.client.model.KimmeridgebrachypteraeschnidiumModel;
import com.peeko32213.unusualprehistory.client.model.TyrannosaurusRexModel;
import com.peeko32213.unusualprehistory.client.render.layer.KimmeridgebrachypteraeschnidiumPatternLayer;
import com.peeko32213.unusualprehistory.client.render.layer.TyrannosaurusRexEepyLayer;
import com.peeko32213.unusualprehistory.common.entity.EntityKimmeridgebrachypteraeschnidium;
import com.peeko32213.unusualprehistory.common.entity.EntityTyrannosaurusRex;
import net.minecraft.client.model.ColorableHierarchicalModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.TropicalFishRenderer;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class KimmeridgebrachypteraeschnidiumRenderer extends GeoEntityRenderer<EntityKimmeridgebrachypteraeschnidium> {

    public KimmeridgebrachypteraeschnidiumRenderer(EntityRendererProvider.Context context) {
        super(context, new KimmeridgebrachypteraeschnidiumModel());
        this.addRenderLayer(new KimmeridgebrachypteraeschnidiumPatternLayer(this));
    }


    @Override
    public void render(EntityKimmeridgebrachypteraeschnidium kimmer, float f, float g, PoseStack poseStack, MultiBufferSource multiBufferSource, int i) {

        KimmeridgebrachypteraeschnidiumModel model = new KimmeridgebrachypteraeschnidiumModel();


        float[] fs = kimmer.getBaseColor().getTextureDiffuseColors();
        model.setColor(fs[0], fs[1], fs[2]);

        super.render(kimmer, f, g, poseStack, multiBufferSource, i);

        model.setColor(1.0F, 1.0F, 1.0F);
    }



}
