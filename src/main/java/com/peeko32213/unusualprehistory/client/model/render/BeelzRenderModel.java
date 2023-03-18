// Made with Blockbench 4.4.2
// Exported for Minecraft version 1.17 - 1.18 with Mojang mappings
// Paste this class into your mod and generate all required imports
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

public class BeelzRenderModel<T extends BaseEntityRender> extends EntityModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
			new ResourceLocation(UnusualPrehistory.MODID, "beelz_render"), "main");
	private final ModelPart Leg1;
	private final ModelPart Leg2;
	private final ModelPart Arm1;
	private final ModelPart Arm2;
	private final ModelPart Body;

	public BeelzRenderModel(ModelPart root) {
		this.Leg1 = root.getChild("Leg1");
		this.Leg2 = root.getChild("Leg2");
		this.Arm1 = root.getChild("Arm1");
		this.Arm2 = root.getChild("Arm2");
		this.Body = root.getChild("Body");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition Leg1 = partdefinition.addOrReplaceChild("Leg1", CubeListBuilder.create().texOffs(24, 44).addBox(-4.5F, 0.0F, -6.5F, 8.0F, 6.0F, 10.0F, new CubeDeformation(0.0F))
				.texOffs(0, 7).addBox(-6.5F, 5.9F, -8.5F, 5.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-8.0F, 16.5F, 6.0F));

		PartDefinition Leg2 = partdefinition.addOrReplaceChild("Leg2", CubeListBuilder.create().texOffs(24, 44).mirror().addBox(-3.5F, 0.0F, -6.5F, 8.0F, 6.0F, 10.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(0, 7).mirror().addBox(1.5F, 5.9F, -8.5F, 5.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(8.0F, 16.5F, 6.0F));

		PartDefinition Arm1 = partdefinition.addOrReplaceChild("Arm1", CubeListBuilder.create().texOffs(0, 48).addBox(-3.75F, 0.0F, -4.5F, 5.0F, 5.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(0, 11).addBox(-0.75F, 4.4F, -7.5F, 4.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-8.0F, 19.0F, -6.0F));

		PartDefinition Arm2 = partdefinition.addOrReplaceChild("Arm2", CubeListBuilder.create().texOffs(0, 48).mirror().addBox(-1.25F, 0.0F, -4.5F, 5.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(0, 11).mirror().addBox(-3.25F, 4.4F, -7.5F, 4.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(8.0F, 19.0F, -6.0F));

		PartDefinition Body = partdefinition.addOrReplaceChild("Body", CubeListBuilder.create().texOffs(0, 0).addBox(-8.5F, -4.25F, -8.5F, 17.0F, 5.0F, 17.0F, new CubeDeformation(0.0F))
				.texOffs(68, 0).addBox(7.5F, -6.25F, -8.5F, 1.0F, 2.0F, 17.0F, new CubeDeformation(0.0F))
				.texOffs(68, 0).mirror().addBox(-8.5F, -6.25F, -8.5F, 1.0F, 2.0F, 17.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(68, 20).addBox(-7.5F, -6.25F, 7.5F, 15.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(68, 28).addBox(3.5F, -6.25F, -8.5F, 4.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(68, 28).mirror().addBox(-7.5F, -6.25F, -8.5F, 4.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(68, 23).addBox(2.5F, -5.25F, -8.5F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(68, 23).mirror().addBox(-3.5F, -5.25F, -8.5F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 21.5F, 0.0F));

		PartDefinition TopJaw = Body.addOrReplaceChild("TopJaw", CubeListBuilder.create().texOffs(67, 24).addBox(-4.5F, 0.0F, -17.0F, 9.0F, 1.0F, 1.0F, new CubeDeformation(0.01F))
				.texOffs(67, 27).addBox(-3.5F, 1.0F, -17.0F, 7.0F, 1.0F, 0.0F, new CubeDeformation(0.01F))
				.texOffs(0, 22).addBox(-8.5F, -5.0F, -17.0F, 17.0F, 5.0F, 17.0F, new CubeDeformation(0.0F))
				.texOffs(60, 55).addBox(-8.5F, -5.0F, -17.0F, 17.0F, 12.0F, 17.0F, new CubeDeformation(0.1F)), PartPose.offset(0.0F, -6.25F, 8.5F));

		PartDefinition Eye1 = TopJaw.addOrReplaceChild("Eye1", CubeListBuilder.create().texOffs(0, 0).addBox(-3.5F, -2.25F, -0.5F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-3.5F, -3.25F, -4.5F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(0, 15).addBox(-5.5F, -5.25F, -4.5F, 6.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(0.5F, -2.25F, -4.5F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, -4.75F, -10.5F));

		PartDefinition Eye2 = TopJaw.addOrReplaceChild("Eye2", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(3.5F, -2.25F, -0.5F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(0, 0).mirror().addBox(-0.5F, -3.25F, -4.5F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(0, 15).mirror().addBox(-0.5F, -5.25F, -4.5F, 6.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(0, 0).mirror().addBox(-2.5F, -2.25F, -4.5F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(5.0F, -4.75F, -10.5F));

		PartDefinition Tounge = Body.addOrReplaceChild("Tounge", CubeListBuilder.create().texOffs(50, 44).addBox(-5.5F, -2.0F, -8.0F, 10.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, -4.0F, 1.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		Leg1.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		Leg2.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		Arm1.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		Arm2.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		Body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}