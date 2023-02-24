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

public class MajungaRenderModel<T extends BaseEntityRender> extends EntityModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
			new ResourceLocation(UnusualPrehistory.MODID, "majunga_render"), "main");
	private final ModelPart Body;
	private final ModelPart Leg1;
	private final ModelPart Leg2;

	public MajungaRenderModel(ModelPart root) {
		this.Body = root.getChild("Body");
		this.Leg1 = root.getChild("Leg1");
		this.Leg2 = root.getChild("Leg2");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition Body = partdefinition.addOrReplaceChild("Body", CubeListBuilder.create().texOffs(0, 41).addBox(-6.5F, -13.0F, -12.0F, 13.0F, 13.0F, 22.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(2.5F, -14.0F, -11.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).mirror().addBox(-4.5F, -14.0F, -7.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(0, 0).addBox(2.5F, -14.0F, -7.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).mirror().addBox(-4.5F, -14.0F, -3.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(0, 0).addBox(2.5F, -14.0F, -3.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).mirror().addBox(-4.5F, -14.0F, 1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(0, 0).addBox(2.5F, -14.0F, 1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).mirror().addBox(-4.5F, -14.0F, 5.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(0, 0).addBox(2.5F, -14.0F, 5.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).mirror().addBox(-4.5F, -14.0F, -11.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 10.0F, 0.0F, 0.0436F, 0.0F, 0.0F));

		PartDefinition Arm1 = Body.addOrReplaceChild("Arm1", CubeListBuilder.create().texOffs(0, 2).addBox(1.0F, 0.0F, 5.0F, 0.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(18, 22).addBox(0.0F, 0.0F, -1.0F, 2.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.5F, -6.0F, -9.0F, -0.1745F, 0.0F, 0.0F));

		PartDefinition Arm2 = Body.addOrReplaceChild("Arm2", CubeListBuilder.create().texOffs(0, 2).mirror().addBox(-1.0F, 0.0F, 5.0F, 0.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(18, 22).mirror().addBox(-2.0F, 0.0F, -1.0F, 2.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-6.5F, -6.0F, -9.0F, -0.1745F, 0.0F, 0.0F));

		PartDefinition Neck = Body.addOrReplaceChild("Neck", CubeListBuilder.create().texOffs(70, 58).addBox(-4.5F, -1.0F, -5.0F, 9.0F, 9.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(0, 41).addBox(-1.0F, 8.0F, -9.0F, 2.0F, 3.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(0, 19).addBox(0.0F, 11.0F, -9.0F, 0.0F, 1.0F, 7.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(1.5F, -2.0F, -3.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).mirror().addBox(-3.5F, -2.0F, -3.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, -12.0F, -12.0F, 0.1309F, 0.0F, 0.0F));

		PartDefinition Head = Neck.addOrReplaceChild("Head", CubeListBuilder.create().texOffs(23, 0).addBox(1.5F, -9.0F, -9.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(23, 0).mirror().addBox(-3.5F, -9.0F, -9.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(0, 52).addBox(4.5F, -7.0F, -10.0F, 2.0F, 5.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(0, 52).mirror().addBox(-6.5F, -7.0F, -10.0F, 2.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(0, 10).addBox(4.5F, 0.0F, -12.0F, 0.0F, 2.0F, 12.0F, new CubeDeformation(0.0F))
		.texOffs(0, 10).mirror().addBox(-4.5F, 0.0F, -12.0F, 0.0F, 2.0F, 12.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(0, 27).addBox(-4.5F, 0.0F, -11.975F, 9.0F, 2.0F, 0.0F, new CubeDeformation(0.01F))
		.texOffs(47, 0).addBox(-4.5F, -7.0F, -12.0F, 9.0F, 7.0F, 12.0F, new CubeDeformation(0.02F))
		.texOffs(81, 2).addBox(-1.0F, -10.0F, -4.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 6.0F, -5.0F, -0.1309F, 0.0F, 0.0F));

		PartDefinition LowerJaw = Head.addOrReplaceChild("LowerJaw", CubeListBuilder.create().texOffs(47, 19).addBox(-4.5F, 0.0F, -12.0F, 9.0F, 3.0F, 12.0F, new CubeDeformation(0.01F))
		.texOffs(0, 29).addBox(-4.5F, -2.0F, -12.0F, 9.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(0, 12).addBox(4.5F, -2.0F, -12.0F, 0.0F, 2.0F, 12.0F, new CubeDeformation(0.0F))
		.texOffs(0, 12).mirror().addBox(-4.5F, -2.0F, -12.0F, 0.0F, 2.0F, 12.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition Tail = Body.addOrReplaceChild("Tail", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, 0.0F, 0.0F, 6.0F, 6.0F, 35.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(0.5F, -1.0F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).mirror().addBox(-2.5F, -1.0F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(0, 0).addBox(0.5F, -1.0F, 3.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).mirror().addBox(-2.5F, -1.0F, 3.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(0, 0).addBox(0.5F, -1.0F, 7.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).mirror().addBox(-2.5F, -1.0F, 7.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(0, 0).addBox(0.5F, -1.0F, 11.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).mirror().addBox(-2.5F, -1.0F, 11.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(0, 0).addBox(0.5F, -1.0F, 15.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).mirror().addBox(-2.5F, -1.0F, 15.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(0, 0).addBox(0.5F, -1.0F, 19.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).mirror().addBox(-2.5F, -1.0F, 19.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(0, 0).addBox(0.5F, -1.0F, 23.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).mirror().addBox(-2.5F, -1.0F, 23.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(0, 0).addBox(0.5F, -1.0F, 27.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).mirror().addBox(-2.5F, -1.0F, 27.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(0, 0).addBox(0.5F, -1.0F, 31.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).mirror().addBox(-2.5F, -1.0F, 31.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, -12.95F, 10.0F, -0.0873F, 0.0F, 0.0F));

		PartDefinition Leg1 = partdefinition.addOrReplaceChild("Leg1", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, 0.0F, -6.0F, 7.0F, 13.0F, 9.0F, new CubeDeformation(0.0F))
		.texOffs(48, 41).addBox(-2.0F, 13.0F, -4.0F, 7.0F, 10.0F, 7.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(4.5F, 22.0F, -5.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(2.0F, 22.0F, -5.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-0.5F, 22.0F, -5.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(6.0F, 1.0F, 10.0F));

		PartDefinition Leg2 = partdefinition.addOrReplaceChild("Leg2", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-5.0F, 0.0F, -4.0F, 7.0F, 13.0F, 9.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(48, 41).mirror().addBox(-5.0F, 13.0F, -2.0F, 7.0F, 10.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(0, 0).mirror().addBox(-4.5F, 22.0F, -3.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(0, 0).mirror().addBox(-2.0F, 22.0F, -3.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(0, 0).mirror().addBox(0.5F, 22.0F, -3.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-6.0F, 1.0F, 8.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		Body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		Leg1.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		Leg2.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}