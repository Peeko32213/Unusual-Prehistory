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

public class RexRenderModel <T extends BaseEntityRender> extends EntityModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
            new ResourceLocation(UnusualPrehistory.MODID, "rex_render"), "main");
    private final ModelPart Body;
    private final ModelPart Leg1;
    private final ModelPart Leg2;

    public RexRenderModel(ModelPart root) {
        this.Body = root.getChild("Body");
        this.Leg1 = root.getChild("Leg1");
        this.Leg2 = root.getChild("Leg2");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition Body = partdefinition.addOrReplaceChild("Body", CubeListBuilder.create().texOffs(0, 102).addBox(-15.5F, -14.0F, -30.0F, 29.0F, 29.0F, 53.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, -21.0F, -3.0F));

        PartDefinition Neck = Body.addOrReplaceChild("Neck", CubeListBuilder.create().texOffs(117, 48).addBox(-7.0F, -5.0F, -11.0F, 17.0F, 25.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.5F, -9.0F, -30.0F, 0.0873F, 0.0F, 0.0F));

        PartDefinition Head = Neck.addOrReplaceChild("Head", CubeListBuilder.create().texOffs(111, 102).addBox(-9.0F, -16.0F, -17.0F, 21.0F, 16.0F, 17.0F, new CubeDeformation(0.0F))
                .texOffs(0, 50).addBox(-7.0F, -13.0F, -37.0F, 17.0F, 16.0F, 20.0F, new CubeDeformation(0.0F))
                .texOffs(0, 138).addBox(-7.0F, 3.0F, -36.95F, 17.0F, 3.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(111, 115).addBox(9.95F, 3.0F, -37.0F, 0.0F, 3.0F, 20.0F, new CubeDeformation(0.0F))
                .texOffs(111, 115).addBox(-6.95F, 3.0F, -37.0F, 0.0F, 3.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 8.0F, -7.0F, -0.0873F, 0.0F, 0.0F));

        PartDefinition Jaw = Head.addOrReplaceChild("Jaw", CubeListBuilder.create().texOffs(117, 64).addBox(9.95F, -1.0F, -30.0F, 0.0F, 3.0F, 20.0F, new CubeDeformation(0.0F))
                .texOffs(117, 64).addBox(-6.95F, -1.0F, -30.0F, 0.0F, 3.0F, 20.0F, new CubeDeformation(0.0F))
                .texOffs(50, 16).addBox(-7.0F, -1.0F, -29.925F, 17.0F, 3.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(77, 184).addBox(-7.0F, 2.0F, -30.0F, 17.0F, 8.0F, 20.0F, new CubeDeformation(0.0F))
                .texOffs(0, 184).addBox(-9.0F, -1.0F, -10.0F, 21.0F, 11.0F, 17.0F, new CubeDeformation(0.01F))
                .texOffs(165, 164).addBox(-6.975F, -11.0F, -9.0F, 1.0F, 10.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(165, 164).addBox(8.975F, -11.0F, -9.0F, 1.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.0F, -7.0F));

        PartDefinition TailPart = Body.addOrReplaceChild("TailPart", CubeListBuilder.create().texOffs(43, 43).addBox(-9.0F, 0.0F, 0.0F, 15.0F, 15.0F, 44.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, -14.0F, 23.0F, -0.0436F, 0.0F, 0.0F));

        PartDefinition TailTip = TailPart.addOrReplaceChild("TailTip", CubeListBuilder.create().texOffs(136, 95).addBox(-6.0F, 0.0F, 0.0F, 10.0F, 10.0F, 50.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.5F, 0.0F, 44.0F));

        PartDefinition Arm1 = Body.addOrReplaceChild("Arm1", CubeListBuilder.create().texOffs(50, 0).addBox(0.0F, 0.0F, -3.0F, 5.0F, 10.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(151, 201).addBox(3.0F, 10.0F, -3.0F, 2.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(13.0F, 9.0F, -18.0F));

        PartDefinition Arm2 = Body.addOrReplaceChild("Arm2", CubeListBuilder.create().texOffs(50, 0).addBox(-5.0F, 0.0F, -3.0F, 5.0F, 10.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(151, 201).addBox(-5.0F, 10.0F, -3.0F, 2.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(-13.0F, 9.0F, -18.0F));

        PartDefinition Leg1 = partdefinition.addOrReplaceChild("Leg1", CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, 0.0F, -11.0F, 15.0F, 30.0F, 20.0F, new CubeDeformation(0.0F))
                .texOffs(0, 102).addBox(-5.0F, 30.0F, -4.0F, 13.0F, 23.0F, 13.0F, new CubeDeformation(0.0F))
                .texOffs(165, 204).addBox(-6.0F, 51.0F, -9.0F, 15.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(13.0F, -29.0F, 16.0F));

        PartDefinition Leg2 = partdefinition.addOrReplaceChild("Leg2", CubeListBuilder.create().texOffs(0, 0).addBox(-9.0F, 0.0F, -11.0F, 15.0F, 30.0F, 20.0F, new CubeDeformation(0.0F))
                .texOffs(0, 102).addBox(-8.0F, 30.0F, -4.0F, 13.0F, 23.0F, 13.0F, new CubeDeformation(0.0F))
                .texOffs(165, 204).addBox(-9.0F, 51.0F, -9.0F, 15.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(-13.0F, -29.0F, 16.0F));

        return LayerDefinition.create(meshdefinition, 256, 256);
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
