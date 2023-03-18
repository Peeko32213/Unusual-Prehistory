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

public class EncrustedRenderModel <T extends BaseEntityRender> extends EntityModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
            new ResourceLocation(UnusualPrehistory.MODID, "encrusted_render"), "main");
    private final ModelPart Body;
    private final ModelPart Leg1;
    private final ModelPart Leg2;
    private final ModelPart Leg3;
    private final ModelPart Leg4;

    public EncrustedRenderModel(ModelPart root) {
        this.Body = root.getChild("Body");
        this.Leg1 = root.getChild("Leg1");
        this.Leg2 = root.getChild("Leg2");
        this.Leg3 = root.getChild("Leg3");
        this.Leg4 = root.getChild("Leg4");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition Body = partdefinition.addOrReplaceChild("Body", CubeListBuilder.create().texOffs(32, 40).addBox(-3.5F, -3.0F, -3.0F, 7.0F, 7.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -4.0F, 1.0F));

        PartDefinition Back = Body.addOrReplaceChild("Back", CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, -1.0F, 0.0F, 12.0F, 12.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.0F, 3.0F, -0.2182F, 0.0F, 0.0F));

        PartDefinition Head = Body.addOrReplaceChild("Head", CubeListBuilder.create().texOffs(0, 24).addBox(-1.0F, 0.0F, -21.0F, 2.0F, 2.0F, 17.0F, new CubeDeformation(0.0F))
                .texOffs(21, 32).addBox(-6.5F, -2.0F, -4.0F, 13.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 32).addBox(0.0F, 2.0F, -21.0F, 0.0F, 4.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.0F, -3.0F, 0.1309F, 0.0F, 0.0F));

        PartDefinition Antenae1 = Head.addOrReplaceChild("Antenae1", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, -5.0F, -6.0F, 0.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, -2.0F, -2.0F, 0.0F, 0.0F, 0.6545F));

        PartDefinition Antenae2 = Head.addOrReplaceChild("Antenae2", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(0.0F, -5.0F, -6.0F, 0.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-1.0F, -2.0F, -2.0F, 0.0F, 0.0F, -0.6545F));

        PartDefinition Wing1 = Body.addOrReplaceChild("Wing1", CubeListBuilder.create().texOffs(30, 0).addBox(0.0F, 0.0F, 0.0F, 7.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.0F, -1.0F, 0.0F, 0.3927F, -0.6545F));

        PartDefinition Wing2 = Body.addOrReplaceChild("Wing2", CubeListBuilder.create().texOffs(30, 0).mirror().addBox(-7.0F, 0.0F, 0.0F, 7.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, -2.0F, -1.0F, 0.0F, -0.3927F, 0.6545F));

        PartDefinition Arm1 = Body.addOrReplaceChild("Arm1", CubeListBuilder.create().texOffs(0, 26).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(8, 26).addBox(-1.0F, 6.0F, -9.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(42, 0).addBox(-1.0F, 4.0F, -9.0F, 2.0F, 2.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(0, 17).addBox(0.0F, 9.0F, -7.0F, 0.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, 3.0F, -3.0F, 0.0F, -0.3054F, 0.0F));

        PartDefinition Arm2 = Body.addOrReplaceChild("Arm2", CubeListBuilder.create().texOffs(0, 26).mirror().addBox(-1.0F, 0.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(8, 26).mirror().addBox(-1.0F, 6.0F, -9.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(42, 0).mirror().addBox(-1.0F, 4.0F, -9.0F, 2.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 17).mirror().addBox(0.0F, 9.0F, -7.0F, 0.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-2.0F, 3.0F, -3.0F, 0.0F, 0.2618F, 0.0F));

        PartDefinition Leg1 = partdefinition.addOrReplaceChild("Leg1", CubeListBuilder.create().texOffs(21, 28).addBox(0.0F, -2.0F, -1.0F, 22.0F, 2.0F, 2.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(3.0F, -1.0F, 0.0F, 0.1137F, 0.6154F, 0.1723F));

        PartDefinition LegPart1 = Leg1.addOrReplaceChild("LegPart1", CubeListBuilder.create().texOffs(21, 24).addBox(0.0F, 0.0F, -1.0F, 22.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(22.0F, -2.0F, 0.0F, 0.0061F, 0.0721F, 1.147F));

        PartDefinition Foot1 = LegPart1.addOrReplaceChild("Foot1", CubeListBuilder.create().texOffs(22, 43).addBox(0.0F, -10.0F, 0.0F, 2.0F, 10.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(20.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.3054F));

        PartDefinition Leg2 = partdefinition.addOrReplaceChild("Leg2", CubeListBuilder.create().texOffs(21, 28).mirror().addBox(-22.0F, -2.0F, -1.0F, 22.0F, 2.0F, 2.0F, new CubeDeformation(0.01F)).mirror(false), PartPose.offsetAndRotation(-3.0F, -1.0F, 0.0F, 0.1137F, -0.6154F, -0.1723F));

        PartDefinition LegPart2 = Leg2.addOrReplaceChild("LegPart2", CubeListBuilder.create().texOffs(21, 24).mirror().addBox(-22.0F, 0.0F, -1.0F, 22.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-22.0F, -2.0F, 0.0F, 0.0061F, -0.0721F, -1.147F));

        PartDefinition Foot2 = LegPart2.addOrReplaceChild("Foot2", CubeListBuilder.create().texOffs(22, 43).mirror().addBox(-2.0F, -10.0F, 0.0F, 2.0F, 10.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-20.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.3054F));

        PartDefinition Leg3 = partdefinition.addOrReplaceChild("Leg3", CubeListBuilder.create().texOffs(21, 28).addBox(0.0F, -2.0F, -1.0F, 22.0F, 2.0F, 2.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(3.0F, -1.0F, 0.0F, -0.1137F, -0.6154F, 0.1723F));

        PartDefinition LegPart3 = Leg3.addOrReplaceChild("LegPart3", CubeListBuilder.create().texOffs(21, 24).addBox(0.0F, 0.0F, -1.0F, 22.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(22.0F, -2.0F, 0.0F, -0.0061F, -0.0721F, 1.147F));

        PartDefinition Foot3 = LegPart3.addOrReplaceChild("Foot3", CubeListBuilder.create().texOffs(22, 43).addBox(0.0F, -10.0F, 0.0F, 2.0F, 10.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(20.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.3054F));

        PartDefinition Leg4 = partdefinition.addOrReplaceChild("Leg4", CubeListBuilder.create().texOffs(21, 28).mirror().addBox(-22.0F, -2.0F, -1.0F, 22.0F, 2.0F, 2.0F, new CubeDeformation(0.01F)).mirror(false), PartPose.offsetAndRotation(-3.0F, -1.0F, 0.0F, -0.1137F, 0.6154F, -0.1723F));

        PartDefinition LegPart4 = Leg4.addOrReplaceChild("LegPart4", CubeListBuilder.create().texOffs(21, 24).mirror().addBox(-22.0F, 0.0F, -1.0F, 22.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-22.0F, -2.0F, 0.0F, -0.0061F, 0.0721F, -1.147F));

        PartDefinition Foot4 = LegPart4.addOrReplaceChild("Foot4", CubeListBuilder.create().texOffs(22, 43).mirror().addBox(-2.0F, -10.0F, 0.0F, 2.0F, 10.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-20.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.3054F));

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
        Leg3.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        Leg4.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
