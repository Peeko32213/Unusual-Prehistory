package com.peeko32213.unusualprehistory.client.render.renders;


import com.peeko32213.unusualprehistory.client.model.render.AntarctoRenderModel;
import com.peeko32213.unusualprehistory.common.entity.msc.render.BaseEntityRender;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class AntarctoRenderRenderer extends MobRenderer<BaseEntityRender, AntarctoRenderModel<BaseEntityRender>> {
	protected static final ResourceLocation TEXTURE = new ResourceLocation("unusualprehistory:textures/entity/antarctopelta.png");

	public AntarctoRenderRenderer(EntityRendererProvider.Context renderManagerIn) {
		super(renderManagerIn, new AntarctoRenderModel<>(renderManagerIn.bakeLayer(AntarctoRenderModel.LAYER_LOCATION)), 0.2F);
	}

	@Override
	public ResourceLocation getTextureLocation(BaseEntityRender entity) {
		return TEXTURE;
	}

}
