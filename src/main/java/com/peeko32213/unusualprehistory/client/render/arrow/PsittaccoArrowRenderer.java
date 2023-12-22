package com.peeko32213.unusualprehistory.client.render.arrow;

import com.peeko32213.unusualprehistory.common.entity.arrow.PsittaccoArrow;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

import static com.peeko32213.unusualprehistory.UnusualPrehistory.prefix;

public class PsittaccoArrowRenderer extends ArrowRenderer<PsittaccoArrow> {
    public static final ResourceLocation PSITTACCO_ARROW_LOCATION = prefix("textures/entity/projectile/psittacco_arrow.png");

    public PsittaccoArrowRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
    }

    @Override
    public ResourceLocation getTextureLocation(PsittaccoArrow pEntity) {
        return PSITTACCO_ARROW_LOCATION;
    }

}
