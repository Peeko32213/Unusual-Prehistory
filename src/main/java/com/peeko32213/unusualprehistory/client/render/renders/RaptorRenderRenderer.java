package com.peeko32213.unusualprehistory.client.render.renders;



import com.peeko32213.unusualprehistory.client.model.render.RaptorRenderModel;
import com.peeko32213.unusualprehistory.client.model.render.TrikeRenderModel;
import com.peeko32213.unusualprehistory.common.entity.render.BaseEntityRender;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class RaptorRenderRenderer extends MobRenderer<BaseEntityRender, RaptorRenderModel<BaseEntityRender>> {
	protected static final ResourceLocation TEXTURE = new ResourceLocation("unusualprehistory:textures/entity/renders/velociraptor_render.png");

	public RaptorRenderRenderer(EntityRendererProvider.Context renderManagerIn) {
		super(renderManagerIn, new RaptorRenderModel<>(renderManagerIn.bakeLayer(RaptorRenderModel.LAYER_LOCATION)), 0.2F);
	}

	@Override
	public ResourceLocation getTextureLocation(BaseEntityRender entity) {
		return TEXTURE;
	}

}
