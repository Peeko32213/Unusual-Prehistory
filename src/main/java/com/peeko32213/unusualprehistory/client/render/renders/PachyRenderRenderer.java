package com.peeko32213.unusualprehistory.client.render.renders;



import com.peeko32213.unusualprehistory.client.model.render.PachyRenderModel;
import com.peeko32213.unusualprehistory.client.model.render.RaptorRenderModel;
import com.peeko32213.unusualprehistory.common.entity.render.BaseEntityRender;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class PachyRenderRenderer extends MobRenderer<BaseEntityRender, PachyRenderModel<BaseEntityRender>> {
	protected static final ResourceLocation TEXTURE = new ResourceLocation("unusualprehistory:textures/entity/renders/pachycephalosaurus_render.png");

	public PachyRenderRenderer(EntityRendererProvider.Context renderManagerIn) {
		super(renderManagerIn, new PachyRenderModel<>(renderManagerIn.bakeLayer(PachyRenderModel.LAYER_LOCATION)), 0.2F);
	}

	@Override
	public ResourceLocation getTextureLocation(BaseEntityRender entity) {
		return TEXTURE;
	}

}
