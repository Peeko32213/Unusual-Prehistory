package com.peeko32213.unusualprehistory.client.render.book_renders;


import com.peeko32213.unusualprehistory.client.model.render.StethaRenderModel;
import com.peeko32213.unusualprehistory.common.entity.msc.render.BaseEntityRender;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class StethaRenderRenderer extends MobRenderer<BaseEntityRender, StethaRenderModel<BaseEntityRender>> {
	protected static final ResourceLocation TEXTURE = new ResourceLocation("unusualprehistory:textures/entity/renders/stethacanthus_render.png");

	public StethaRenderRenderer(EntityRendererProvider.Context renderManagerIn) {
		super(renderManagerIn, new StethaRenderModel<>(renderManagerIn.bakeLayer(StethaRenderModel.LAYER_LOCATION)), 0.2F);
	}

	@Override
	public ResourceLocation getTextureLocation(BaseEntityRender entity) {
		return TEXTURE;
	}

}
