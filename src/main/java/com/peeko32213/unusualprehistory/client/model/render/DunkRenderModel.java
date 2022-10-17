package com.peeko32213.unusualprehistory.client.model.render;// Made with Blockbench 4.4.2
// Exported for Minecraft version 1.17 - 1.18 with Mojang mappings
// Paste this class into your mod and generate all required imports
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.render.BaseEntityRender;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;

public class DunkRenderModel<T extends BaseEntityRender> extends EntityModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
			new ResourceLocation(UnusualPrehistory.MODID, "dunk_render"), "main");
	private final ModelPart Body;

	public DunkRenderModel(ModelPart root) {
		this.Body = root.getChild("Body");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition Body = partdefinition.addOrReplaceChild("Body", CubeListBuilder.create().texOffs(120, 30).addBox(-8.5F, 1.0F, -33.0F, 17.0F, 5.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(8.5F, 1.0F, -33.0F, 0.0F, 5.0F, 16.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).mirror().addBox(-8.5F, 1.0F, -33.0F, 0.0F, 5.0F, 16.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(0, 62).addBox(-13.5F, -18.0F, -17.0F, 27.0F, 27.0F, 33.0F, new CubeDeformation(0.15F))
		.texOffs(87, 0).addBox(-8.5F, -13.0F, -33.0F, 17.0F, 14.0F, 16.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-13.5F, -18.0F, -17.0F, 27.0F, 29.0F, 33.0F, new CubeDeformation(0.0F))
		.texOffs(0, 62).addBox(-1.0F, -33.0F, 5.2F, 2.0F, 15.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 13.0F, 1.0F));

		PartDefinition LowerJaw = Body.addOrReplaceChild("LowerJaw", CubeListBuilder.create().texOffs(120, 35).addBox(-8.5F, -2.0F, -16.999F, 17.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(0, 5).addBox(8.5F, -2.0F, -17.0F, 0.0F, 2.0F, 16.0F, new CubeDeformation(0.0F))
		.texOffs(0, 5).mirror().addBox(-8.5F, -2.0F, -17.0F, 0.0F, 2.0F, 16.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(87, 75).addBox(-8.5F, 0.0F, -17.0F, 17.0F, 4.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 7.0F, -16.0F));

		PartDefinition Fin1 = Body.addOrReplaceChild("Fin1", CubeListBuilder.create().texOffs(120, 95).addBox(1.0F, 0.0F, 0.0F, 17.0F, 2.0F, 9.0F, new CubeDeformation(0.0F))
		.texOffs(111, 113).addBox(0.0F, 0.0F, 0.0F, 18.0F, 2.0F, 9.0F, new CubeDeformation(0.15F)), PartPose.offsetAndRotation(13.5F, 8.0F, -12.0F, 0.0F, 0.0F, 0.6109F));

		PartDefinition Fin2 = Body.addOrReplaceChild("Fin2", CubeListBuilder.create().texOffs(120, 95).mirror().addBox(-18.0F, 0.0F, 0.0F, 17.0F, 2.0F, 9.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(111, 113).mirror().addBox(-18.0F, 0.0F, 0.0F, 18.0F, 2.0F, 9.0F, new CubeDeformation(0.15F)).mirror(false), PartPose.offsetAndRotation(-13.5F, 8.0F, -12.0F, 0.0F, 0.0F, -0.6109F));

		PartDefinition Backfin1 = Body.addOrReplaceChild("Backfin1", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, 0.0F, 0.0F, 10.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(13.5F, 9.0F, 13.0F, 0.0F, 0.0F, 0.6109F));

		PartDefinition Backfin2 = Body.addOrReplaceChild("Backfin2", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-10.0F, 0.0F, 0.0F, 10.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-13.5F, 9.0F, 13.0F, 0.0F, 0.0F, -0.6109F));

		PartDefinition BackBody = Body.addOrReplaceChild("BackBody", CubeListBuilder.create().texOffs(98, 40).addBox(-6.5F, 0.0F, 0.0F, 13.0F, 13.0F, 22.0F, new CubeDeformation(0.0F))
		.texOffs(0, 23).addBox(-1.0F, -5.0F, 9.0F, 2.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -12.0F, 16.0F));

		PartDefinition Tailfin = BackBody.addOrReplaceChild("Tailfin", CubeListBuilder.create(), PartPose.offset(0.0F, 7.0F, 19.0F));

		PartDefinition Topfin = Tailfin.addOrReplaceChild("Topfin", CubeListBuilder.create().texOffs(0, 122).addBox(-1.0F, -28.0F, 0.0F, 2.0F, 28.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.48F, 0.0F, 0.0F));

		PartDefinition Bottomfin = Tailfin.addOrReplaceChild("Bottomfin", CubeListBuilder.create().texOffs(87, 68).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 12.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.48F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 256, 256);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		Body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}