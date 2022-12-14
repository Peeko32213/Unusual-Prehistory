package com.peeko32213.unusualprehistory.client.render.renders;



import com.peeko32213.unusualprehistory.client.model.render.CotyRenderModel;
import com.peeko32213.unusualprehistory.client.model.render.DunkRenderModel;
import com.peeko32213.unusualprehistory.common.entity.render.BaseEntityRender;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class DunkRenderRenderer extends MobRenderer<BaseEntityRender, DunkRenderModel<BaseEntityRender>> {
	protected static final ResourceLocation TEXTURE = new ResourceLocation("unusualprehistory:textures/entity/dunk_pane.png");

	public DunkRenderRenderer(EntityRendererProvider.Context renderManagerIn) {
		super(renderManagerIn, new DunkRenderModel<>(renderManagerIn.bakeLayer(DunkRenderModel.LAYER_LOCATION)), 0.2F);
	}

	@Override
	public ResourceLocation getTextureLocation(BaseEntityRender entity) {
		return TEXTURE;
	}

}
