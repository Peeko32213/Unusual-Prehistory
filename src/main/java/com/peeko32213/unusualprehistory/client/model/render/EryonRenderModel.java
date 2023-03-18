package com.peeko32213.unusualprehistory.client.model.render;

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

public class EryonRenderModel <T extends BaseEntityRender> extends EntityModel<T> {

    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
            new ResourceLocation(UnusualPrehistory.MODID, "eryon_render"), "main");
    private final ModelPart Body;
    private final ModelPart Leg1;
    private final ModelPart Leg5;
    private final ModelPart Leg2;
    private final ModelPart Leg6;
    private final ModelPart Leg3;
    private final ModelPart Leg7;
    private final ModelPart Leg4;
    private final ModelPart Leg8;

    public EryonRenderModel(ModelPart root) {
        this.Body = root.getChild("Body");
        this.Leg1 = root.getChild("Leg1");
        this.Leg5 = root.getChild("Leg5");
        this.Leg2 = root.getChild("Leg2");
        this.Leg6 = root.getChild("Leg6");
        this.Leg3 = root.getChild("Leg3");
        this.Leg7 = root.getChild("Leg7");
        this.Leg4 = root.getChild("Leg4");
        this.Leg8 = root.getChild("Leg8");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition Body = partdefinition.addOrReplaceChild("Body", CubeListBuilder.create().texOffs(23, 21).addBox(-3.0F, -3.0F, -6.0F, 6.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-5.0F, -3.0F, -5.0F, 10.0F, 3.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 23.0F, 0.0F));

        PartDefinition Spikes1 = Body.addOrReplaceChild("Spikes1", CubeListBuilder.create().texOffs(22, 16).addBox(-3.0F, 0.0F, -4.0F, 3.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.25F, -1.75F, -4.0F, 0.0F, 0.0F, 0.6109F));

        PartDefinition Spikes2 = Body.addOrReplaceChild("Spikes2", CubeListBuilder.create().texOffs(22, 16).mirror().addBox(0.0F, 0.0F, -4.0F, 3.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(3.25F, -1.75F, -4.0F, 0.0F, 0.0F, -0.6109F));

        PartDefinition Tail = Body.addOrReplaceChild("Tail", CubeListBuilder.create().texOffs(13, 21).addBox(-2.0F, 0.0F, 0.0F, 4.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.9F, 4.0F, -0.7854F, 0.0F, 0.0F));

        PartDefinition TailFin = Tail.addOrReplaceChild("TailFin", CubeListBuilder.create().texOffs(10, 12).addBox(-4.0F, 0.0F, 0.0F, 8.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.0F, 2.0F, -1.8326F, 0.0F, 0.0F));

        PartDefinition SmallerAntennae1 = Body.addOrReplaceChild("SmallerAntennae1", CubeListBuilder.create().texOffs(11, 16).addBox(-5.0F, 0.0F, -5.0F, 5.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, -1.0F, -5.0F, 0.0F, 0.0F, 0.2182F));

        PartDefinition SmallerAntennae2 = Body.addOrReplaceChild("SmallerAntennae2", CubeListBuilder.create().texOffs(11, 16).mirror().addBox(0.0F, 0.0F, -5.0F, 5.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(1.0F, -1.0F, -5.0F, 0.0F, 0.0F, -0.2182F));

        PartDefinition LargerAntennae1 = Body.addOrReplaceChild("LargerAntennae1", CubeListBuilder.create().texOffs(0, 12).addBox(0.0F, -4.0F, -8.0F, 0.0F, 4.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.5F, -2.0F, -5.0F, -0.4363F, 0.0F, 0.0F));

        PartDefinition LargerAntennae2 = Body.addOrReplaceChild("LargerAntennae2", CubeListBuilder.create().texOffs(0, 12).mirror().addBox(0.0F, -4.0F, -8.0F, 0.0F, 4.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(1.5F, -2.0F, -5.0F, -0.4363F, 0.0F, 0.0F));

        PartDefinition Arms = Body.addOrReplaceChild("Arms", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, -3.0F));

        PartDefinition Arm1 = Arms.addOrReplaceChild("Arm1", CubeListBuilder.create().texOffs(0, 12).addBox(-3.0F, 0.0F, -8.0F, 3.0F, 0.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.0F, -0.5F, 0.0F, 0.0F, 0.0F, -0.3054F));

        PartDefinition Claw1 = Arm1.addOrReplaceChild("Claw1", CubeListBuilder.create().texOffs(0, 3).addBox(0.0F, 0.0F, -1.0F, 1.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, 0.025F, -6.0F));

        PartDefinition Arm2 = Arms.addOrReplaceChild("Arm2", CubeListBuilder.create().texOffs(0, 12).mirror().addBox(0.0F, 0.0F, -8.0F, 3.0F, 0.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(5.0F, -0.5F, 0.0F, 0.0F, 0.0F, 0.3054F));

        PartDefinition Claw2 = Arm2.addOrReplaceChild("Claw2", CubeListBuilder.create().texOffs(0, 3).mirror().addBox(-1.0F, 0.0F, -1.0F, 1.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(1.0F, 0.025F, -6.0F));

        PartDefinition Leg1 = partdefinition.addOrReplaceChild("Leg1", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, 0.0F, 0.0F, 4.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.0F, 22.0F, -2.0F, -0.0636F, -0.2684F, 0.3462F));

        PartDefinition Leg5 = partdefinition.addOrReplaceChild("Leg5", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(0.0F, 0.0F, 0.0F, 4.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(5.0F, 22.0F, -2.0F, -0.0636F, 0.2684F, -0.3462F));

        PartDefinition Leg2 = partdefinition.addOrReplaceChild("Leg2", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, 0.0F, 0.0F, 4.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.0F, 22.0F, -1.0F, 0.1045F, 0.2709F, 0.3183F));

        PartDefinition Leg6 = partdefinition.addOrReplaceChild("Leg6", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(0.0F, 0.0F, 0.0F, 4.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(5.0F, 22.0F, -1.0F, 0.1045F, -0.2709F, -0.3183F));

        PartDefinition Leg3 = partdefinition.addOrReplaceChild("Leg3", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, 0.0F, 0.0F, 4.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.0F, 22.0F, 0.0F, 0.2135F, 0.5369F, 0.3181F));

        PartDefinition Leg7 = partdefinition.addOrReplaceChild("Leg7", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(0.0F, 0.0F, 0.0F, 4.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(5.0F, 22.0F, 0.0F, 0.2135F, -0.5369F, -0.3181F));

        PartDefinition Leg4 = partdefinition.addOrReplaceChild("Leg4", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, 0.0F, 0.0F, 4.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.0F, 22.0F, 2.0F, 0.4191F, 0.8755F, 0.4315F));

        PartDefinition Leg8 = partdefinition.addOrReplaceChild("Leg8", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(0.0F, 0.0F, 0.0F, 4.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(5.0F, 22.0F, 2.0F, 0.4191F, -0.8755F, -0.4315F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        Body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        Leg1.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        Leg5.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        Leg2.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        Leg6.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        Leg3.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        Leg7.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        Leg4.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        Leg8.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
