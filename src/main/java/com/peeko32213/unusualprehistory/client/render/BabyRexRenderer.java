package com.peeko32213.unusualprehistory.client.render;


import com.peeko32213.unusualprehistory.client.model.BabyRexModel;
import com.peeko32213.unusualprehistory.common.entity.baby.EntityBabyRex;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class BabyRexRenderer extends GeoEntityRenderer<EntityBabyRex> {

    public BabyRexRenderer(EntityRendererProvider.Context context) {
        super(context, new BabyRexModel());
    }
}
