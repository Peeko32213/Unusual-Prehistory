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

public class AnuroRenderModel<T extends BaseEntityRender> extends EntityModel<T> {

	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
			new ResourceLocation(UnusualPrehistory.MODID, "anuro_render"), "main");
	private final ModelPart Body;
	private final ModelPart Wing1;
	private final ModelPart Wing2;
	private final ModelPart Leg1;
	private final ModelPart Leg2;

	public AnuroRenderModel(ModelPart root) {
		this.Body = root.getChild("Body");
		this.Wing1 = root.getChild("Wing1");
		this.Wing2 = root.getChild("Wing2");
		this.Leg1 = root.getChild("Leg1");
		this.Leg2 = root.getChild("Leg2");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition Body = partdefinition.addOrReplaceChild("Body", CubeListBuilder.create().texOffs(4, 7).addBox(-1.5F, -1.0F, -4.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.01F))
				.texOffs(0, 0).addBox(-4.5F, -4.0F, -4.0F, 9.0F, 3.0F, 11.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(0.0F, 20.0F, -1.0F, -0.3927F, 0.0F, 0.0F));

		PartDefinition LowerJaw = Body.addOrReplaceChild("LowerJaw", CubeListBuilder.create().texOffs(0, 0).addBox(4.5F, -2.0F, -6.0F, 0.0F, 2.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).mirror().addBox(-4.5F, -2.0F, -6.0F, 0.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(0, 14).addBox(-4.5F, 0.0F, -6.0F, 9.0F, 1.0F, 11.0F, new CubeDeformation(0.0F))
				.texOffs(0, 2).addBox(1.5F, -2.0F, -6.0F, 3.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(0, 2).mirror().addBox(-4.5F, -2.0F, -6.0F, 3.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(0, 4).addBox(-1.5F, -1.0F, -6.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, 2.0F));

		PartDefinition Tail = Body.addOrReplaceChild("Tail", CubeListBuilder.create().texOffs(1, 26).addBox(-2.0F, 0.0F, 0.0F, 5.0F, 0.0F, 4.0F, new CubeDeformation(0.01F)), PartPose.offset(-0.5F, -2.0F, 7.0F));

		PartDefinition Wing1 = partdefinition.addOrReplaceChild("Wing1", CubeListBuilder.create().texOffs(20, 14).addBox(-8.5F, 0.0F, 0.0F, 9.0F, 0.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.5F, 18.5F, -3.0F, 0.0F, 0.0F, -0.6981F));

		PartDefinition Wingtip1 = Wing1.addOrReplaceChild("Wingtip1", CubeListBuilder.create().texOffs(20, 0).addBox(-9.0F, 0.0F, -1.0F, 9.0F, 0.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-8.5F, 0.0F, 1.0F, 0.0F, 0.0F, 1.7453F));

		PartDefinition Claw1 = Wing1.addOrReplaceChild("Claw1", CubeListBuilder.create().texOffs(47, 0).addBox(-1.0F, 0.0F, -1.0F, 3.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-8.75F, -0.5F, -1.0F, 0.0F, 0.0F, 0.7854F));

		PartDefinition Wing2 = partdefinition.addOrReplaceChild("Wing2", CubeListBuilder.create().texOffs(20, 14).mirror().addBox(-0.5F, 0.0F, 0.0F, 9.0F, 0.0F, 9.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(4.5F, 18.5F, -3.0F, 0.0F, 0.0F, 0.6981F));

		PartDefinition Wingtip2 = Wing2.addOrReplaceChild("Wingtip2", CubeListBuilder.create().texOffs(20, 0).mirror().addBox(0.0F, 0.0F, -1.0F, 9.0F, 0.0F, 9.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(8.5F, 0.0F, 1.0F, 0.0F, 0.0F, -1.7453F));

		PartDefinition Claw2 = Wing2.addOrReplaceChild("Claw2", CubeListBuilder.create().texOffs(47, 0).mirror().addBox(-2.0F, 0.0F, -1.0F, 3.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(8.75F, -0.5F, -1.0F, 0.0F, 0.0F, -0.7854F));

		PartDefinition Leg1 = partdefinition.addOrReplaceChild("Leg1", CubeListBuilder.create().texOffs(0, 7).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.5F, 21.0F, 4.0F));

		PartDefinition Toes1 = Leg1.addOrReplaceChild("Toes1", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, 0.0F, -2.0F, 3.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.5F, 3.0F, 0.0F));

		PartDefinition Leg2 = partdefinition.addOrReplaceChild("Leg2", CubeListBuilder.create().texOffs(0, 7).mirror().addBox(-1.0F, -1.0F, 0.0F, 2.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(4.5F, 21.0F, 4.0F));

		PartDefinition Toes2 = Leg2.addOrReplaceChild("Toes2", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-1.5F, 0.0F, -2.0F, 3.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.5F, 3.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		Body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		Wing1.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		Wing2.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		Leg1.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		Leg2.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}