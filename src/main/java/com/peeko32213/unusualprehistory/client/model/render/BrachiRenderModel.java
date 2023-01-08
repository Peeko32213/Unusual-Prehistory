package com.peeko32213.unusualprehistory.client.model.render;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.render.BaseEntityRender;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;

public class BrachiRenderModel <T extends BaseEntityRender> extends EntityModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
            new ResourceLocation(UnusualPrehistory.MODID, "brachi_render"), "main");
    private final ModelPart NeckBase;
    private final ModelPart Body;
    private final ModelPart Arm1;
    private final ModelPart Arm2;
    private final ModelPart Leg1;
    private final ModelPart Leg2;

    public BrachiRenderModel(ModelPart root) {
        this.NeckBase = root.getChild("NeckBase");
        this.Body = root.getChild("Body");
        this.Arm1 = root.getChild("Arm1");
        this.Arm2 = root.getChild("Arm2");
        this.Leg1 = root.getChild("Leg1");
        this.Leg2 = root.getChild("Leg2");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition NeckBase = partdefinition.addOrReplaceChild("NeckBase", CubeListBuilder.create().texOffs(0, 107).addBox(-13.5F, -61.0F, -16.0F, 27.0F, 60.0F, 26.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -25.0F, -26.0F));

        PartDefinition NeckMiddle = NeckBase.addOrReplaceChild("NeckMiddle", CubeListBuilder.create().texOffs(0, 193).addBox(-8.5F, -60.0F, -23.0F, 17.0F, 60.0F, 26.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -61.0F, 7.0F));

        PartDefinition Saddle = NeckMiddle.addOrReplaceChild("Saddle", CubeListBuilder.create().texOffs(235, 20).addBox(-8.5F, -60.0F, -23.0F, 17.0F, 60.0F, 26.0F, new CubeDeformation(0.1F))
                .texOffs(292, 0).addBox(-6.0F, -62.0F, -13.0F, 12.0F, 2.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition Head = NeckMiddle.addOrReplaceChild("Head", CubeListBuilder.create().texOffs(0, 29).addBox(-4.5F, -3.1F, -20.0F, 9.0F, 8.0F, 11.0F, new CubeDeformation(0.0F))
                .texOffs(86, 198).addBox(-9.0F, -11.1F, -9.0F, 18.0F, 16.0F, 15.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-1.5F, -16.1F, -15.0F, 3.0F, 13.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -50.0F, -21.0F));

        PartDefinition Body = partdefinition.addOrReplaceChild("Body", CubeListBuilder.create().texOffs(0, 0).addBox(-22.5F, -28.0F, -28.0F, 45.0F, 45.0F, 62.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -32.0F, 0.0F));

        PartDefinition Tail = Body.addOrReplaceChild("Tail", CubeListBuilder.create().texOffs(152, 0).addBox(-6.5F, 0.0F, 0.0F, 13.0F, 13.0F, 28.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -28.0F, 34.0F, -0.5672F, 0.0F, 0.0F));

        PartDefinition TailBase = Tail.addOrReplaceChild("TailBase", CubeListBuilder.create().texOffs(185, 78).addBox(-5.0F, 0.0F, 0.0F, 10.0F, 10.0F, 29.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 3.0F, 28.0F, 0.0873F, 0.0F, 0.0F));

        PartDefinition Arm1 = partdefinition.addOrReplaceChild("Arm1", CubeListBuilder.create().texOffs(106, 107).addBox(-9.0F, 0.0F, -12.0F, 24.0F, 67.0F, 24.0F, new CubeDeformation(0.0F)), PartPose.offset(20.0F, -43.0F, -23.0F));

        PartDefinition Arm2 = partdefinition.addOrReplaceChild("Arm2", CubeListBuilder.create().texOffs(106, 107).addBox(-15.0F, 0.0F, -12.0F, 24.0F, 67.0F, 24.0F, new CubeDeformation(0.0F)), PartPose.offset(-20.0F, -43.0F, -23.0F));

        PartDefinition Leg1 = partdefinition.addOrReplaceChild("Leg1", CubeListBuilder.create().texOffs(178, 174).addBox(-9.0F, -1.0F, -12.0F, 24.0F, 57.0F, 24.0F, new CubeDeformation(0.0F)), PartPose.offset(18.0F, -32.0F, 26.0F));

        PartDefinition Leg2 = partdefinition.addOrReplaceChild("Leg2", CubeListBuilder.create().texOffs(178, 174).addBox(-15.0F, -1.0F, -12.0F, 24.0F, 57.0F, 24.0F, new CubeDeformation(0.0F)), PartPose.offset(-18.0F, -32.0F, 26.0F));

        return LayerDefinition.create(meshdefinition, 512, 512);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        NeckBase.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        Body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        Arm1.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        Arm2.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        Leg1.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        Leg2.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
