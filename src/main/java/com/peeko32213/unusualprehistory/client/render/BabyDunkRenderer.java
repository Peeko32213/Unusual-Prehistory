package com.peeko32213.unusualprehistory.client.render;


import com.peeko32213.unusualprehistory.client.model.BabyDunkModel;
import com.peeko32213.unusualprehistory.client.model.BeelzebufoTadpoleModel;
import com.peeko32213.unusualprehistory.common.entity.baby.EntityBabyDunk;
import com.peeko32213.unusualprehistory.common.entity.baby.EntityBeelzebufoTadpole;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class BabyDunkRenderer extends GeoEntityRenderer<EntityBabyDunk> {

    public BabyDunkRenderer(EntityRendererProvider.Context context) {
        super(context, new BabyDunkModel());
    }
}
