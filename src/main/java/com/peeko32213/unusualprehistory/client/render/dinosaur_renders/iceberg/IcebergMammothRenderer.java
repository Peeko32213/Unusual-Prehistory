package com.peeko32213.unusualprehistory.client.render.dinosaur_renders.iceberg;

import com.peeko32213.unusualprehistory.client.model.iceberg.IcebergMammothModel;
import com.peeko32213.unusualprehistory.common.entity.iceberg.IcebergMammoth;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class IcebergMammothRenderer extends GeoEntityRenderer<IcebergMammoth> {

    public IcebergMammothRenderer(EntityRendererProvider.Context context) {
        super(context, new IcebergMammothModel());
    }
}