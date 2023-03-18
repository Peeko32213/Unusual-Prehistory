package com.peeko32213.unusualprehistory.client.render.renders;


import com.peeko32213.unusualprehistory.client.model.render.MajungaRenderModel;
import com.peeko32213.unusualprehistory.common.entity.msc.render.BaseEntityRender;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class MajungaRenderRenderer extends MobRenderer<BaseEntityRender, MajungaRenderModel<BaseEntityRender>> {
	protected static final ResourceLocation TEXTURE = new ResourceLocation("unusualprehistory:textures/entity/renders/majungasaurus_render.png");

	public MajungaRenderRenderer(EntityRendererProvider.Context renderManagerIn) {
		super(renderManagerIn, new MajungaRenderModel<>(renderManagerIn.bakeLayer(MajungaRenderModel.LAYER_LOCATION)), 0.2F);
	}

	@Override
	public ResourceLocation getTextureLocation(BaseEntityRender entity) {
		return TEXTURE;
	}

}
