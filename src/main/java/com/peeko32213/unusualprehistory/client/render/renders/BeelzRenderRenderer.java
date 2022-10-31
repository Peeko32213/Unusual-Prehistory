package com.peeko32213.unusualprehistory.client.render.renders;



import com.peeko32213.unusualprehistory.client.model.render.AnuroRenderModel;
import com.peeko32213.unusualprehistory.client.model.render.BeelzRenderModel;
import com.peeko32213.unusualprehistory.common.entity.render.BaseEntityRender;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class BeelzRenderRenderer extends MobRenderer<BaseEntityRender, BeelzRenderModel<BaseEntityRender>> {
	protected static final ResourceLocation TEXTURE = new ResourceLocation("unusualprehistory:textures/entity/beelze2.png");

	public BeelzRenderRenderer(EntityRendererProvider.Context renderManagerIn) {
		super(renderManagerIn, new BeelzRenderModel<>(renderManagerIn.bakeLayer(BeelzRenderModel.LAYER_LOCATION)), 0.2F);
	}

	@Override
	public ResourceLocation getTextureLocation(BaseEntityRender entity) {
		return TEXTURE;
	}

}
