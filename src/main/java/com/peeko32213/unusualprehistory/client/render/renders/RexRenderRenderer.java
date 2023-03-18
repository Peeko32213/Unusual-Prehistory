package com.peeko32213.unusualprehistory.client.render.renders;


import com.peeko32213.unusualprehistory.client.model.render.RexRenderModel;
import com.peeko32213.unusualprehistory.common.entity.msc.render.BaseEntityRender;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class RexRenderRenderer extends MobRenderer<BaseEntityRender, RexRenderModel<BaseEntityRender>> {
	protected static final ResourceLocation TEXTURE = new ResourceLocation("unusualprehistory:textures/entity/tyrannosaurus_rex.png");

	public RexRenderRenderer(EntityRendererProvider.Context renderManagerIn) {
		super(renderManagerIn, new RexRenderModel<>(renderManagerIn.bakeLayer(RexRenderModel.LAYER_LOCATION)), 0.2F);
	}

	@Override
	public ResourceLocation getTextureLocation(BaseEntityRender entity) {
		return TEXTURE;
	}

}
