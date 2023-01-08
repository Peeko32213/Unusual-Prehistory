package com.peeko32213.unusualprehistory.client.render.renders;



import com.peeko32213.unusualprehistory.client.model.render.BrachiRenderModel;
import com.peeko32213.unusualprehistory.client.model.render.PachyRenderModel;
import com.peeko32213.unusualprehistory.common.entity.render.BaseEntityRender;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class BrachiRenderRenderer extends MobRenderer<BaseEntityRender, BrachiRenderModel<BaseEntityRender>> {
	protected static final ResourceLocation TEXTURE = new ResourceLocation("unusualprehistory:textures/entity/brachiosaurus.png");

	public BrachiRenderRenderer(EntityRendererProvider.Context renderManagerIn) {
		super(renderManagerIn, new BrachiRenderModel<>(renderManagerIn.bakeLayer(BrachiRenderModel.LAYER_LOCATION)), 0.2F);
	}

	@Override
	public ResourceLocation getTextureLocation(BaseEntityRender entity) {
		return TEXTURE;
	}

}
