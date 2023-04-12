package com.peeko32213.unusualprehistory.client.render.book_renders;


import com.peeko32213.unusualprehistory.client.model.render.KentroRenderModel;
import com.peeko32213.unusualprehistory.common.entity.msc.render.BaseEntityRender;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class KentroRenderRenderer extends MobRenderer<BaseEntityRender, KentroRenderModel<BaseEntityRender>> {
	protected static final ResourceLocation TEXTURE = new ResourceLocation("unusualprehistory:textures/entity/kentrosaurus.png");

	public KentroRenderRenderer(EntityRendererProvider.Context renderManagerIn) {
		super(renderManagerIn, new KentroRenderModel<>(renderManagerIn.bakeLayer(KentroRenderModel.LAYER_LOCATION)), 0.2F);
	}

	@Override
	public ResourceLocation getTextureLocation(BaseEntityRender entity) {
		return TEXTURE;
	}

}
