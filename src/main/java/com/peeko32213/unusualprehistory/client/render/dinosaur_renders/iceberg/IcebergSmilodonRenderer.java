package com.peeko32213.unusualprehistory.client.render.dinosaur_renders.iceberg;

import com.peeko32213.unusualprehistory.client.model.iceberg.IcebergMammothModel;
import com.peeko32213.unusualprehistory.client.model.iceberg.IcebergSmilodonModel;
import com.peeko32213.unusualprehistory.common.entity.iceberg.IcebergMammoth;
import com.peeko32213.unusualprehistory.common.entity.iceberg.IcebergSmilodon;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class IcebergSmilodonRenderer extends GeoEntityRenderer<IcebergSmilodon> {

    public IcebergSmilodonRenderer(EntityRendererProvider.Context context) {
        super(context, new IcebergSmilodonModel());
    }
}