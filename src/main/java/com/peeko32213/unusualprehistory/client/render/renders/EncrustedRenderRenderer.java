package com.peeko32213.unusualprehistory.client.render.renders;



import com.peeko32213.unusualprehistory.client.model.render.EncrustedRenderModel;
import com.peeko32213.unusualprehistory.client.model.render.RexRenderModel;
import com.peeko32213.unusualprehistory.common.entity.render.BaseEntityRender;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class EncrustedRenderRenderer extends MobRenderer<BaseEntityRender, EncrustedRenderModel<BaseEntityRender>> {
	protected static final ResourceLocation TEXTURE = new ResourceLocation("unusualprehistory:textures/entity/encrusted_pane.png");

	public EncrustedRenderRenderer(EntityRendererProvider.Context renderManagerIn) {
		super(renderManagerIn, new EncrustedRenderModel<>(renderManagerIn.bakeLayer(EncrustedRenderModel.LAYER_LOCATION)), 0.2F);
	}

	@Override
	public ResourceLocation getTextureLocation(BaseEntityRender entity) {
		return TEXTURE;
	}

}
