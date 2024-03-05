package com.peeko32213.unusualprehistory.client.render.dinosaur_renders;


import com.peeko32213.unusualprehistory.client.model.LeedsichthysModel;
import com.peeko32213.unusualprehistory.common.entity.EntityLeedsichthys;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class LeedsichthysRenderer extends GeoEntityRenderer<EntityLeedsichthys> {

    public LeedsichthysRenderer(EntityRendererProvider.Context context) {
        super(context, new LeedsichthysModel());
    }
}
