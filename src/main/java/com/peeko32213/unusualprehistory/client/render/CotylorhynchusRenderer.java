package com.peeko32213.unusualprehistory.client.render;


import com.peeko32213.unusualprehistory.client.model.AnurognathusModel;
import com.peeko32213.unusualprehistory.client.model.CotylorhynchusModel;
import com.peeko32213.unusualprehistory.common.entity.EntityAnurognathus;
import com.peeko32213.unusualprehistory.common.entity.EntityCotylorhynchus;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class CotylorhynchusRenderer extends GeoEntityRenderer<EntityCotylorhynchus> {

    public CotylorhynchusRenderer(EntityRendererProvider.Context context) {
        super(context, new CotylorhynchusModel());
    }
}
