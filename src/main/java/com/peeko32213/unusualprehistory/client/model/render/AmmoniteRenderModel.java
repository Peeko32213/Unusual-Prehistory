package com.peeko32213.unusualprehistory.client.model.render;// Made with Blockbench 4.4.2
// Exported for Minecraft version 1.17 - 1.18 with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.msc.render.BaseEntityRender;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;

public class AmmoniteRenderModel<T extends BaseEntityRender> extends EntityModel<T> {

	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
			new ResourceLocation(UnusualPrehistory.MODID, "ammon_render"), "main");
	private final ModelPart Body;

	public AmmoniteRenderModel(ModelPart root) {
		this.Body = root.getChild("Body");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition Body = partdefinition.addOrReplaceChild("Body", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -12.0F, -9.0F, 6.0F, 12.0F, 12.0F, new CubeDeformation(0.1F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition Head = Body.addOrReplaceChild("Head", CubeListBuilder.create().texOffs(0, 24).addBox(-3.0F, 0.0F, 0.0F, 6.0F, 5.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(24, 0).addBox(-3.0F, 0.0F, 0.0F, 6.0F, 3.0F, 3.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, -5.0F, 3.0F));

		PartDefinition LowerArms = Head.addOrReplaceChild("LowerArms", CubeListBuilder.create().texOffs(20, 6).addBox(-2.5F, 0.0F, 0.0F, 5.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 5.0F, 3.0F));

		PartDefinition UpperArms = Head.addOrReplaceChild("UpperArms", CubeListBuilder.create().texOffs(20, 6).addBox(-2.5F, 0.0F, 0.0F, 5.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.0F, 3.0F));

		PartDefinition SideArms1 = Head.addOrReplaceChild("SideArms1", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, -2.5F, 0.0F, 0.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.5F, 3.0F, 3.0F));

		PartDefinition SideArms2 = Head.addOrReplaceChild("SideArms2", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(0.0F, -2.5F, 0.0F, 0.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(2.5F, 3.0F, 3.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		Body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}


}