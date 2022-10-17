// Made with Blockbench 4.4.2
// Exported for Minecraft version 1.17 - 1.18 with Mojang mappings
// Paste this class into your mod and generate all required imports
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

public class BeelzRenderModel<T extends BaseEntityRender> extends EntityModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
			new ResourceLocation(UnusualPrehistory.MODID, "beelz_render"), "main");
	private final ModelPart Body;
	private final ModelPart Arm1;
	private final ModelPart Arm2;
	private final ModelPart Leg1;
	private final ModelPart Leg2;

	public BeelzRenderModel(ModelPart root) {
		this.Body = root.getChild("Body");
		this.Arm1 = root.getChild("Arm1");
		this.Arm2 = root.getChild("Arm2");
		this.Leg1 = root.getChild("Leg1");
		this.Leg2 = root.getChild("Leg2");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition Body = partdefinition.addOrReplaceChild("Body", CubeListBuilder.create().texOffs(0, 0).addBox(-8.5F, -12.25F, -8.5F, 17.0F, 12.0F, 17.0F, new CubeDeformation(0.0F))
		.texOffs(52, 12).addBox(-8.5F, -12.25F, -8.5F, 17.0F, 12.0F, 17.0F, new CubeDeformation(0.1F))
		.texOffs(0, 8).addBox(4.5F, -15.25F, -8.5F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(0, 15).addBox(4.5F, -17.25F, -8.5F, 6.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(8.5F, -14.25F, -4.5F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(2.5F, -14.25F, -8.5F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(0, 15).mirror().addBox(-10.5F, -17.25F, -8.5F, 6.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(0, 8).mirror().addBox(-8.5F, -15.25F, -8.5F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(0, 0).mirror().addBox(-4.5F, -14.25F, -8.5F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(0, 0).mirror().addBox(-8.5F, -14.25F, -4.5F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition Arm1 = partdefinition.addOrReplaceChild("Arm1", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, 0.0F, -4.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(23, 29).addBox(-2.0F, 3.975F, -7.0F, 4.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(6.75F, 19.0F, -6.5F));

		PartDefinition Arm2 = partdefinition.addOrReplaceChild("Arm2", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-4.0F, 0.0F, -4.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(23, 29).mirror().addBox(-2.0F, 3.975F, -7.0F, 4.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-6.75F, 19.0F, -6.5F));

		PartDefinition Leg1 = partdefinition.addOrReplaceChild("Leg1", CubeListBuilder.create().texOffs(0, 29).addBox(0.0F, 0.0F, -7.0F, 4.0F, 6.0F, 9.0F, new CubeDeformation(0.0F))
		.texOffs(13, 29).addBox(1.0F, 5.975F, -9.0F, 5.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(8.5F, 16.5F, 7.5F));

		PartDefinition Leg2 = partdefinition.addOrReplaceChild("Leg2", CubeListBuilder.create().texOffs(0, 29).mirror().addBox(-4.0F, 0.0F, -7.0F, 4.0F, 6.0F, 9.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(13, 29).mirror().addBox(-6.0F, 5.975F, -9.0F, 5.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-8.5F, 15.5F, 7.5F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		Body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		Arm1.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		Arm2.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		Leg1.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		Leg2.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}