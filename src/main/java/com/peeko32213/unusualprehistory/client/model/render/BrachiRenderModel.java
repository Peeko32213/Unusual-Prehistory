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

        PartDefinition NeckBase = partdefinition.addOrReplaceChild("NeckBase", CubeListBuilder.create().texOffs(142, 170).addBox(-16.0F, -69.0F, -27.0F, 35.0F, 97.0F, 47.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, -65.0F, -27.0F));

        PartDefinition NeckMiddle = NeckBase.addOrReplaceChild("NeckMiddle", CubeListBuilder.create().texOffs(265, 274).addBox(-11.0F, -88.0F, -39.0F, 24.0F, 87.0F, 41.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, -68.0F, 12.0F));

        PartDefinition Saddle = NeckMiddle.addOrReplaceChild("Saddle", CubeListBuilder.create().texOffs(95, 170).addBox(-5.0F, -90.0F, -17.0F, 12.0F, 2.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(0, 170).addBox(-11.0F, -88.0F, -39.0F, 24.0F, 125.0F, 41.0F, new CubeDeformation(0.1F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition Head = NeckMiddle.addOrReplaceChild("Head", CubeListBuilder.create().texOffs(40, 81).addBox(-6.5F, -8.0F, -36.0F, 15.0F, 9.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(0, 48).addBox(-0.5F, -31.0F, -29.0F, 3.0F, 23.0F, 24.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-11.5F, -23.0F, -22.0F, 25.0F, 24.0F, 24.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -66.0F, -27.0F));

        PartDefinition Reins1 = Head.addOrReplaceChild("Reins1", CubeListBuilder.create().texOffs(30, 24).addBox(-0.5F, 1.0F, -10.0F, 0.0F, 24.0F, 24.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(14.0F, -1.0F, -12.0F, 0.0F, 0.0F, -0.0436F));

        PartDefinition Reins2 = Head.addOrReplaceChild("Reins2", CubeListBuilder.create().texOffs(30, 24).mirror().addBox(0.5F, 1.0F, -10.0F, 0.0F, 24.0F, 24.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-13.0F, -1.0F, -12.0F, 0.0F, 0.0F, 0.0436F));

        PartDefinition Body = partdefinition.addOrReplaceChild("Body", CubeListBuilder.create().texOffs(0, 0).addBox(-28.0F, -49.0F, -24.0F, 55.0F, 66.0F, 104.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -47.0F, -19.0F, -0.2182F, 0.0F, 0.0F));

        PartDefinition Tail = Body.addOrReplaceChild("Tail", CubeListBuilder.create().texOffs(214, 0).addBox(-9.0F, 0.0F, 0.0F, 19.0F, 26.0F, 51.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, -32.0F, 80.0F, -0.3491F, 0.0F, 0.0F));

        PartDefinition TailBase = Tail.addOrReplaceChild("TailBase", CubeListBuilder.create().texOffs(306, 29).addBox(-8.0F, -1.0F, 0.0F, 17.0F, 19.0F, 48.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 8.0F, 51.0F, 0.1745F, 0.0F, 0.0F));

        PartDefinition Arm1 = partdefinition.addOrReplaceChild("Arm1", CubeListBuilder.create().texOffs(306, 139).addBox(-6.0F, 0.0F, -20.0F, 27.0F, 82.0F, 31.0F, new CubeDeformation(0.0F)), PartPose.offset(27.0F, -58.0F, -31.0F));

        PartDefinition Arm2 = partdefinition.addOrReplaceChild("Arm2", CubeListBuilder.create().texOffs(306, 139).mirror().addBox(-21.0F, 0.0F, -20.0F, 27.0F, 82.0F, 31.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-27.0F, -58.0F, -31.0F));

        PartDefinition Leg1 = partdefinition.addOrReplaceChild("Leg1", CubeListBuilder.create().texOffs(111, 314).addBox(-13.0F, 1.0F, -17.0F, 27.0F, 64.0F, 31.0F, new CubeDeformation(0.0F)), PartPose.offset(27.0F, -41.0F, 53.0F));

        PartDefinition Leg2 = partdefinition.addOrReplaceChild("Leg2", CubeListBuilder.create().texOffs(111, 314).mirror().addBox(-14.0F, 1.0F, -17.0F, 27.0F, 64.0F, 31.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-27.0F, -41.0F, 53.0F));

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
