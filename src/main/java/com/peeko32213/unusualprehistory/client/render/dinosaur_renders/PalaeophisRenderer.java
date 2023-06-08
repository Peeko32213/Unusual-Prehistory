package com.peeko32213.unusualprehistory.client.render.dinosaur_renders;


import com.peeko32213.unusualprehistory.client.model.DunkleosteusModel;
import com.peeko32213.unusualprehistory.client.model.PalaeophisModel;
import com.peeko32213.unusualprehistory.common.entity.EntityBrachiosaurus;
import com.peeko32213.unusualprehistory.common.entity.EntityDunkleosteus;
import com.peeko32213.unusualprehistory.common.entity.EntityPalaeophis;
import com.peeko32213.unusualprehistory.common.entity.msc.part.EntityBrachiosaurusPart;
import com.peeko32213.unusualprehistory.common.entity.msc.part.EntityPalaeophisPart;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class PalaeophisRenderer extends GeoEntityRenderer<EntityPalaeophis> {

    public PalaeophisRenderer(EntityRendererProvider.Context context) {
        super(context, new PalaeophisModel());
    }

}
