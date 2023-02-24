package com.peeko32213.unusualprehistory.client.model.render;

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

public class RaptorRenderModel <T extends BaseEntityRender> extends EntityModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
            new ResourceLocation(UnusualPrehistory.MODID, "raptor_render"), "main");
    private final ModelPart Body;
    private final ModelPart Leg1;
    private final ModelPart Leg2;

    public RaptorRenderModel(ModelPart root) {
        this.Body = root.getChild("Body");
        this.Leg1 = root.getChild("Leg1");
        this.Leg2 = root.getChild("Leg2");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition Body = partdefinition.addOrReplaceChild("Body", CubeListBuilder.create().texOffs(0, 38).addBox(-4.5F, -2.0F, -4.0F, 9.0F, 8.0F, 11.0F, new CubeDeformation(0.0F))
                .texOffs(14, 24).addBox(-2.0F, 0.0F, -6.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 13.0F, 0.0F));

        PartDefinition Arm1 = Body.addOrReplaceChild("Arm1", CubeListBuilder.create().texOffs(0, 26).addBox(3.0F, 0.0F, 2.0F, 0.0F, 7.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 19).addBox(0.0F, 0.0F, -2.0F, 3.0F, 7.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(3.0F, 7.0F, -2.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(3.0F, 7.0F, 1.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(0.0F, 7.0F, -1.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, 2.0F, -1.0F));

        PartDefinition Arm2 = Body.addOrReplaceChild("Arm2", CubeListBuilder.create().texOffs(0, 26).addBox(-3.0F, 0.0F, 2.0F, 0.0F, 7.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 19).addBox(-3.0F, 0.0F, -2.0F, 3.0F, 7.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-3.0F, 7.0F, -2.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-3.0F, 7.0F, 1.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(0.0F, 7.0F, -1.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, 2.0F, -1.0F));

        PartDefinition Tail = Body.addOrReplaceChild("Tail", CubeListBuilder.create().texOffs(19, 5).addBox(-1.5F, 0.0F, 0.0F, 3.0F, 3.0F, 30.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.5F, -2.0F, 7.0F));

        PartDefinition Feathers1 = Tail.addOrReplaceChild("Feathers1", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, 0.0F, -14.0F, 7.0F, 0.0F, 35.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.3F, 1.0F, 14.0F, 0.0F, 0.0F, 0.4363F));

        PartDefinition Feathers2 = Tail.addOrReplaceChild("Feathers2", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-5.5F, 0.0F, -14.0F, 7.0F, 0.0F, 35.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-1.3F, 1.0F, 14.0F, 0.0F, 0.0F, -0.4363F));

        PartDefinition Head = Body.addOrReplaceChild("Head", CubeListBuilder.create().texOffs(0, 0).addBox(-2.5F, -5.0F, -3.0F, 5.0F, 5.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(13, 5).addBox(0.0F, -8.0F, -3.0F, 0.0F, 3.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(29, 38).addBox(-2.5F, -5.0F, -5.0F, 5.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 10).addBox(-1.5F, -3.0F, -10.0F, 3.0F, 2.0F, 7.0F, new CubeDeformation(0.02F)), PartPose.offset(0.0F, 0.0F, -4.0F));

        PartDefinition LowerJaw = Head.addOrReplaceChild("LowerJaw", CubeListBuilder.create().texOffs(13, 3).addBox(-1.5F, 0.0F, -6.0F, 3.0F, 1.0F, 7.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, -1.0F, -4.0F));

        PartDefinition Leg1 = partdefinition.addOrReplaceChild("Leg1", CubeListBuilder.create().texOffs(15, 14).addBox(-1.0F, 0.0F, -2.0F, 3.0F, 5.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(8, 32).addBox(-1.0F, 5.0F, 1.0F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 7).addBox(2.0F, 6.0F, -2.0F, 0.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 7).addBox(-1.0F, 6.0F, -2.0F, 0.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 7).addBox(0.5F, 6.0F, -2.0F, 0.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, 16.0F, 6.0F));

        PartDefinition Leg2 = partdefinition.addOrReplaceChild("Leg2", CubeListBuilder.create().texOffs(15, 14).addBox(-2.0F, 0.0F, -2.0F, 3.0F, 5.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(8, 32).addBox(-2.0F, 5.0F, 1.0F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 7).addBox(-2.0F, 6.0F, -2.0F, 0.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 7).addBox(1.0F, 6.0F, -2.0F, 0.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 7).addBox(-0.5F, 6.0F, -2.0F, 0.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, 16.0F, 6.0F));

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