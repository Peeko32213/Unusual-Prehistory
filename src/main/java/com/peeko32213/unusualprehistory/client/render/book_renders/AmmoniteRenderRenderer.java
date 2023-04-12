package com.peeko32213.unusualprehistory.client.render.book_renders;



import com.peeko32213.unusualprehistory.client.model.render.AmmoniteRenderModel;
import com.peeko32213.unusualprehistory.common.entity.msc.render.BaseEntityRender;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class AmmoniteRenderRenderer extends MobRenderer<BaseEntityRender, AmmoniteRenderModel<BaseEntityRender>> {
	protected static final ResourceLocation TEXTURE = new ResourceLocation("unusualprehistory:textures/entity/renders/ammonite_render.png");

	public AmmoniteRenderRenderer(EntityRendererProvider.Context renderManagerIn) {
		super(renderManagerIn, new AmmoniteRenderModel<>(renderManagerIn.bakeLayer(AmmoniteRenderModel.LAYER_LOCATION)), 0.2F);
	}

	@Override
	public ResourceLocation getTextureLocation(BaseEntityRender entity) {
		return TEXTURE;
	}

}
