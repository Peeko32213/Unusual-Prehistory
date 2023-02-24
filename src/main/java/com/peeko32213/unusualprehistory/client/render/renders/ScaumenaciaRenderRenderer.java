package com.peeko32213.unusualprehistory.client.render.renders;


import com.peeko32213.unusualprehistory.client.model.render.ScaumenaciaRenderModel;
import com.peeko32213.unusualprehistory.common.entity.render.BaseEntityRender;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class ScaumenaciaRenderRenderer extends MobRenderer<BaseEntityRender, ScaumenaciaRenderModel<BaseEntityRender>> {
	protected static final ResourceLocation TEXTURE = new ResourceLocation("unusualprehistory:textures/entity/renders/scaumenacia_render.png");

	public ScaumenaciaRenderRenderer(EntityRendererProvider.Context renderManagerIn) {
		super(renderManagerIn, new ScaumenaciaRenderModel<>(renderManagerIn.bakeLayer(ScaumenaciaRenderModel.LAYER_LOCATION)), 0.2F);
	}

	@Override
	public ResourceLocation getTextureLocation(BaseEntityRender entity) {
		return TEXTURE;
	}

}
