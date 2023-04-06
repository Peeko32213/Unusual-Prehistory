package com.peeko32213.unusualprehistory.client.render.renders;


import com.peeko32213.unusualprehistory.client.model.render.HwachaRenderModel;
import com.peeko32213.unusualprehistory.common.entity.msc.render.BaseEntityRender;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class HwachaRenderRenderer extends MobRenderer<BaseEntityRender, HwachaRenderModel<BaseEntityRender>> {
	protected static final ResourceLocation TEXTURE = new ResourceLocation("unusualprehistory:textures/entity/renders/hwachavenator_render.png");

	public HwachaRenderRenderer(EntityRendererProvider.Context renderManagerIn) {
		super(renderManagerIn, new HwachaRenderModel<>(renderManagerIn.bakeLayer(HwachaRenderModel.LAYER_LOCATION)), 0.2F);
	}

	@Override
	public ResourceLocation getTextureLocation(BaseEntityRender entity) {
		return TEXTURE;
	}

}
