package com.peeko32213.unusualprehistory.client.render.renders;



import com.peeko32213.unusualprehistory.client.model.render.BeelzRenderModel;
import com.peeko32213.unusualprehistory.client.model.render.CotyRenderModel;
import com.peeko32213.unusualprehistory.common.entity.render.BaseEntityRender;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class CotyRenderRenderer extends MobRenderer<BaseEntityRender, CotyRenderModel<BaseEntityRender>> {
	protected static final ResourceLocation TEXTURE = new ResourceLocation("unusualprehistory:textures/entity/cotylorhynchus.png");

	public CotyRenderRenderer(EntityRendererProvider.Context renderManagerIn) {
		super(renderManagerIn, new CotyRenderModel<>(renderManagerIn.bakeLayer(CotyRenderModel.LAYER_LOCATION)), 0.2F);
	}

	@Override
	public ResourceLocation getTextureLocation(BaseEntityRender entity) {
		return TEXTURE;
	}

}
