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

public class KentroRenderModel<T extends BaseEntityRender> extends EntityModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
            new ResourceLocation(UnusualPrehistory.MODID, "kentro_render"), "main");
        private final ModelPart Body;
        private final ModelPart Leg1;
        private final ModelPart Arm1;
        private final ModelPart Leg2;
        private final ModelPart Arm2;

public KentroRenderModel(ModelPart root) {
        this.Body = root.getChild("Body");
        this.Leg1 = root.getChild("Leg1");
        this.Arm1 = root.getChild("Arm1");
        this.Leg2 = root.getChild("Leg2");
        this.Arm2 = root.getChild("Arm2");
        }

public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition Body = partdefinition.addOrReplaceChild("Body", CubeListBuilder.create().texOffs(0, 0).addBox(-10.5F, -14.0F, -15.0F, 21.0F, 23.0F, 29.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 4.0F, 0.0F));

        PartDefinition Tail = Body.addOrReplaceChild("Tail", CubeListBuilder.create().texOffs(0, 52).addBox(-4.5F, 0.0F, 0.0F, 9.0F, 9.0F, 38.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -13.0F, 14.0F));

        PartDefinition Spikes = Tail.addOrReplaceChild("Spikes", CubeListBuilder.create(), PartPose.offset(0.0F, 3.0F, 18.0F));

        PartDefinition LeftSpikes = Spikes.addOrReplaceChild("LeftSpikes", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 1.0F));

        PartDefinition SmallSpike1 = LeftSpikes.addOrReplaceChild("SmallSpike1", CubeListBuilder.create().texOffs(56, 52).addBox(-1.0F, -11.0F, -1.0F, 2.0F, 11.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, -1.0F, -16.0F, 0.0F, 0.0F, 0.5672F));

        PartDefinition MediumSpike1 = LeftSpikes.addOrReplaceChild("MediumSpike1", CubeListBuilder.create().texOffs(98, 50).addBox(-1.0F, -14.0F, -2.0F, 2.0F, 14.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, -1.0F, -2.0F, 0.0F, 0.0F, 0.5236F));

        PartDefinition MediumSpike2 = LeftSpikes.addOrReplaceChild("MediumSpike2", CubeListBuilder.create().texOffs(98, 50).addBox(-1.0F, -14.0F, -2.0F, 2.0F, 14.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, -1.0F, 10.0F, 0.0F, 0.0F, 0.5236F));

        PartDefinition LargeSpike1 = LeftSpikes.addOrReplaceChild("LargeSpike1", CubeListBuilder.create().texOffs(94, 89).addBox(-1.0F, -15.0F, -1.0F, 2.0F, 15.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, -1.0F, -9.0F, 0.0F, 0.0F, 0.829F));

        PartDefinition LargeSpike2 = LeftSpikes.addOrReplaceChild("LargeSpike2", CubeListBuilder.create().texOffs(94, 89).addBox(-1.0F, -15.0F, -1.0F, 2.0F, 15.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, -1.0F, 3.0F, 0.0F, 0.0F, 0.829F));

        PartDefinition LargeSpike3 = LeftSpikes.addOrReplaceChild("LargeSpike3", CubeListBuilder.create().texOffs(94, 89).addBox(-1.0F, -15.0F, -1.0F, 2.0F, 15.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, -1.0F, 15.0F, 0.0F, 0.0F, 0.829F));

        PartDefinition RightSpikes = Spikes.addOrReplaceChild("RightSpikes", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 1.0F));

        PartDefinition SmallSpike2 = RightSpikes.addOrReplaceChild("SmallSpike2", CubeListBuilder.create().texOffs(56, 52).mirror().addBox(-1.0F, -11.0F, -1.0F, 2.0F, 11.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-4.0F, -1.0F, -16.0F, 0.0F, 0.0F, -0.5672F));

        PartDefinition MediumSpike3 = RightSpikes.addOrReplaceChild("MediumSpike3", CubeListBuilder.create().texOffs(98, 50).mirror().addBox(-1.0F, -14.0F, -2.0F, 2.0F, 14.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-4.0F, -1.0F, -2.0F, 0.0F, 0.0F, -0.5236F));

        PartDefinition MediumSpike4 = RightSpikes.addOrReplaceChild("MediumSpike4", CubeListBuilder.create().texOffs(98, 50).mirror().addBox(-1.0F, -14.0F, -2.0F, 2.0F, 14.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-4.0F, -1.0F, 10.0F, 0.0F, 0.0F, -0.5236F));

        PartDefinition LargeSpike4 = RightSpikes.addOrReplaceChild("LargeSpike4", CubeListBuilder.create().texOffs(94, 89).mirror().addBox(-1.0F, -15.0F, -1.0F, 2.0F, 15.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-4.0F, -1.0F, -9.0F, 0.0F, 0.0F, -0.829F));

        PartDefinition LargeSpike5 = RightSpikes.addOrReplaceChild("LargeSpike5", CubeListBuilder.create().texOffs(94, 89).mirror().addBox(-1.0F, -15.0F, -1.0F, 2.0F, 15.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-4.0F, -1.0F, 3.0F, 0.0F, 0.0F, -0.829F));

        PartDefinition LargeSpike6 = RightSpikes.addOrReplaceChild("LargeSpike6", CubeListBuilder.create().texOffs(94, 89).mirror().addBox(-1.0F, -15.0F, -1.0F, 2.0F, 15.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-4.0F, -1.0F, 15.0F, 0.0F, 0.0F, -0.829F));

        PartDefinition SideSpike1 = Body.addOrReplaceChild("SideSpike1", CubeListBuilder.create().texOffs(71, 0).addBox(0.5F, -2.0F, -1.0F, 25.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(7.0F, 5.0F, -7.0F, 0.2932F, -0.5386F, -0.5673F));

        PartDefinition SideSpike2 = Body.addOrReplaceChild("SideSpike2", CubeListBuilder.create().texOffs(71, 0).mirror().addBox(-25.5F, -2.0F, -1.0F, 25.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-7.0F, 5.0F, -7.0F, 0.2932F, 0.5386F, 0.5673F));

        PartDefinition Neck = Body.addOrReplaceChild("Neck", CubeListBuilder.create().texOffs(56, 52).addBox(-4.0F, -5.0F, -15.0F, 7.0F, 8.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, 9.0F, -10.0F));

        PartDefinition HeadPlate1 = Neck.addOrReplaceChild("HeadPlate1", CubeListBuilder.create().texOffs(0, 14).addBox(1.0F, -11.0F, -21.0F, 0.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, 0.0F, 10.0F));

        PartDefinition HeadPlate2 = Neck.addOrReplaceChild("HeadPlate2", CubeListBuilder.create().texOffs(0, 14).mirror().addBox(-1.0F, -11.0F, -21.0F, 0.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-1.0F, 0.0F, 10.0F));

        PartDefinition Head = Neck.addOrReplaceChild("Head", CubeListBuilder.create().texOffs(81, 75).addBox(-4.0F, -2.0F, -9.0F, 7.0F, 5.0F, 9.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, -3.0F, -11.0F));

        PartDefinition BackLargerPlates1 = Body.addOrReplaceChild("BackLargerPlates1", CubeListBuilder.create().texOffs(71, 0).addBox(0.0F, -9.0F, -12.0F, 0.0F, 9.0F, 17.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, -14.0F, 0.0F));

        PartDefinition BackLargerPlates2 = Body.addOrReplaceChild("BackLargerPlates2", CubeListBuilder.create().texOffs(71, 0).mirror().addBox(0.0F, -9.0F, -12.0F, 0.0F, 9.0F, 17.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-2.0F, -14.0F, 0.0F));

        PartDefinition BackSmallerPlates1 = Body.addOrReplaceChild("BackSmallerPlates1", CubeListBuilder.create().texOffs(56, 58).addBox(0.0F, -9.0F, -6.0F, 0.0F, 9.0F, 17.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, -14.0F, 0.0F));

        PartDefinition BackSmallerPlates2 = Body.addOrReplaceChild("BackSmallerPlates2", CubeListBuilder.create().texOffs(56, 58).mirror().addBox(0.0F, -9.0F, -6.0F, 0.0F, 9.0F, 17.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-4.0F, -14.0F, 0.0F));

        PartDefinition Leg1 = partdefinition.addOrReplaceChild("Leg1", CubeListBuilder.create().texOffs(0, 52).addBox(-5.5F, -4.0F, -5.0F, 9.0F, 23.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(10.0F, 5.0F, 14.0F));

        PartDefinition Arm1 = partdefinition.addOrReplaceChild("Arm1", CubeListBuilder.create().texOffs(0, 0).addBox(-4.5F, 0.0F, -3.0F, 7.0F, 11.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(7.0F, 13.0F, -6.0F));

        PartDefinition Leg2 = partdefinition.addOrReplaceChild("Leg2", CubeListBuilder.create().texOffs(0, 52).mirror().addBox(-3.5F, -4.0F, -5.0F, 9.0F, 23.0F, 10.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-10.0F, 5.0F, 14.0F));

        PartDefinition Arm2 = partdefinition.addOrReplaceChild("Arm2", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-2.5F, 0.0F, -3.0F, 7.0F, 11.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-7.0F, 13.0F, -6.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
        }

@Override
public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

        }

@Override
public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        Body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        Leg1.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        Arm1.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        Leg2.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        Arm2.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        }
        }
