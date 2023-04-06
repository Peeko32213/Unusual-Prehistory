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

public class AustroraptorRenderModel<T extends BaseEntityRender> extends EntityModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
            new ResourceLocation(UnusualPrehistory.MODID, "austro_render"), "main");
    private final ModelPart Body;
    private final ModelPart Leg1;
    private final ModelPart Leg2;

public AustroraptorRenderModel(ModelPart root) {
        this.Body = root.getChild("Body");
        this.Leg1 = root.getChild("Leg1");
        this.Leg2 = root.getChild("Leg2");
        }

public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition Body = partdefinition.addOrReplaceChild("Body", CubeListBuilder.create().texOffs(0, 54).addBox(-4.5F, -11.0F, -6.5F, 9.0F, 11.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 7.0F, -4.0F));

        PartDefinition TailFront = Body.addOrReplaceChild("TailFront", CubeListBuilder.create().texOffs(60, 13).addBox(-2.5F, 0.0F, 0.5F, 5.0F, 5.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -11.0F, 6.0F));

        PartDefinition TailFeathers1 = TailFront.addOrReplaceChild("TailFeathers1", CubeListBuilder.create().texOffs(47, 0).addBox(-2.0F, 0.0F, -7.5F, 16.0F, 0.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.7F, 2.15F, 8.1F, 0.0F, 0.0F, 0.5236F));

        PartDefinition TailFeathers2 = TailFront.addOrReplaceChild("TailFeathers2", CubeListBuilder.create().texOffs(47, 0).mirror().addBox(-14.0F, 0.0F, -7.5F, 16.0F, 0.0F, 13.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-1.7F, 2.15F, 8.1F, 0.0F, 0.0F, -0.5236F));

        PartDefinition TailFeathers3 = TailFront.addOrReplaceChild("TailFeathers3", CubeListBuilder.create().texOffs(83, 0).mirror().addBox(-14.0F, 0.2F, -4.5F, 16.0F, 0.0F, 13.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-0.7F, 2.15F, 12.1F, 0.0F, 0.0F, -0.48F));

        PartDefinition TailBack = TailFront.addOrReplaceChild("TailBack", CubeListBuilder.create().texOffs(36, 32).addBox(-1.5F, 0.0F, -0.5F, 3.0F, 3.0F, 22.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 14.0F));

        PartDefinition MiddleTailFeathers1 = TailBack.addOrReplaceChild("MiddleTailFeathers1", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, 0.0F, -22.5F, 16.0F, 0.0F, 28.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.0F, 21.0F, 0.0F, 0.0F, 0.5236F));

        PartDefinition MiddleTailFeathers2 = TailBack.addOrReplaceChild("MiddleTailFeathers2", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-16.0F, 0.0F, -22.5F, 16.0F, 0.0F, 28.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 1.0F, 21.0F, 0.0F, 0.0F, -0.5236F));

        PartDefinition MiddleTailFeathers3 = TailBack.addOrReplaceChild("MiddleTailFeathers3", CubeListBuilder.create().texOffs(-28, 95).mirror().addBox(-15.8F, 0.1F, -22.3F, 16.0F, 0.0F, 28.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 1.0F, 21.0F, 0.0F, 0.0F, -0.5236F));

        PartDefinition TailTip = TailBack.addOrReplaceChild("TailTip", CubeListBuilder.create(), PartPose.offset(0.0F, 1.0F, 27.0F));

        PartDefinition BackTailFeathers1 = TailTip.addOrReplaceChild("BackTailFeathers1", CubeListBuilder.create().texOffs(0, 28).addBox(0.0F, 0.0F, 0.0F, 16.0F, 0.0F, 26.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -0.5F, 0.0F, 0.0F, 0.5236F));

        PartDefinition BackTailFeathers2 = TailTip.addOrReplaceChild("BackTailFeathers2", CubeListBuilder.create().texOffs(0, 28).mirror().addBox(-16.0F, 0.0F, 0.0F, 16.0F, 0.0F, 26.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, -0.5F, 0.0F, 0.0F, -0.5236F));

        PartDefinition Arm1 = Body.addOrReplaceChild("Arm1", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, 12.0F, -1.0F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(85, 73).addBox(0.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(4.0F, 12.0F, -2.0F, 0.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 45).addBox(4.0F, 1.0F, 2.0F, 0.0F, 11.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, -6.0F, -3.0F));

        PartDefinition Arm2 = Body.addOrReplaceChild("Arm2", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(0.0F, 12.0F, -1.0F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(85, 73).mirror().addBox(-4.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 0).mirror().addBox(-4.0F, 12.0F, -2.0F, 0.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 45).mirror().addBox(-4.0F, 1.0F, 2.0F, 0.0F, 11.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-3.0F, -6.0F, -3.0F));

        PartDefinition Neck = Body.addOrReplaceChild("Neck", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -21.0F, 1.0F, 6.0F, 13.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(0, 19).addBox(-3.0F, -12.0F, -5.0F, 6.0F, 13.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -5.0F, -5.0F));

        PartDefinition Head = Neck.addOrReplaceChild("Head", CubeListBuilder.create().texOffs(61, 77).addBox(-3.0F, -1.0F, -6.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(64, 31).addBox(-1.5F, 2.0F, -17.0F, 3.0F, 2.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -20.0F, 1.0F));

        PartDefinition Jaw = Head.addOrReplaceChild("Jaw", CubeListBuilder.create().texOffs(33, 77).addBox(-1.5F, 0.0F, -11.0F, 3.0F, 1.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 4.0F, -6.0F));

        PartDefinition HeadCrest = Head.addOrReplaceChild("HeadCrest", CubeListBuilder.create().texOffs(44, 57).addBox(-11.0F, -20.0F, 0.0F, 22.0F, 20.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, -3.0F, -0.7418F, 0.0F, 0.0F));

        PartDefinition SideCrest1 = Head.addOrReplaceChild("SideCrest1", CubeListBuilder.create().texOffs(0, 38).addBox(-1.0F, -11.0F, 0.0F, 13.0F, 12.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.75F, 4.0F, -4.0F, -0.2197F, -1.1243F, 0.5251F));

        PartDefinition SideCrest2 = Head.addOrReplaceChild("SideCrest2", CubeListBuilder.create().texOffs(0, 38).mirror().addBox(-12.0F, -11.0F, 0.0F, 13.0F, 12.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-2.75F, 4.0F, -4.0F, -0.2197F, 1.1243F, -0.5251F));

        PartDefinition Leg1 = partdefinition.addOrReplaceChild("Leg1", CubeListBuilder.create().texOffs(22, 78).addBox(-1.5F, 7.0F, 2.0F, 2.0F, 15.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 78).addBox(-3.0F, 0.0F, -2.0F, 5.0F, 7.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, 2.0F, 2.0F));

        PartDefinition Feet1 = Leg1.addOrReplaceChild("Feet1", CubeListBuilder.create(), PartPose.offset(-0.5F, 20.0F, 2.0F));

        PartDefinition Toe1 = Feet1.addOrReplaceChild("Toe1", CubeListBuilder.create().texOffs(18, 14).addBox(0.0F, -2.0F, -5.0F, 0.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 2.0F, 0.0F, 0.0F, -0.3491F, 0.0F));

        PartDefinition Toe2 = Feet1.addOrReplaceChild("Toe2", CubeListBuilder.create().texOffs(18, 14).addBox(0.0F, -2.0F, -5.0F, 0.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 2.0F, 0.0F, 0.0F, 0.3491F, 0.0F));

        PartDefinition Toe3 = Feet1.addOrReplaceChild("Toe3", CubeListBuilder.create().texOffs(18, 14).addBox(0.0F, -2.0F, -5.0F, 0.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, 0.0F));

        PartDefinition Leg2 = partdefinition.addOrReplaceChild("Leg2", CubeListBuilder.create().texOffs(22, 78).mirror().addBox(-0.5F, 7.0F, 2.0F, 2.0F, 15.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 78).mirror().addBox(-2.0F, 0.0F, -2.0F, 5.0F, 7.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-4.0F, 2.0F, 2.0F));

        PartDefinition Feet2 = Leg2.addOrReplaceChild("Feet2", CubeListBuilder.create(), PartPose.offset(0.5F, 20.0F, 2.0F));

        PartDefinition Toe4 = Feet2.addOrReplaceChild("Toe4", CubeListBuilder.create().texOffs(18, 14).mirror().addBox(0.0F, -2.0F, -5.0F, 0.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-1.0F, 2.0F, 0.0F, 0.0F, 0.3491F, 0.0F));

        PartDefinition Toe5 = Feet2.addOrReplaceChild("Toe5", CubeListBuilder.create().texOffs(18, 14).mirror().addBox(0.0F, -2.0F, -5.0F, 0.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(1.0F, 2.0F, 0.0F, 0.0F, -0.3491F, 0.0F));

        PartDefinition Toe6 = Feet2.addOrReplaceChild("Toe6", CubeListBuilder.create().texOffs(18, 14).mirror().addBox(0.0F, -2.0F, -5.0F, 0.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 2.0F, 0.0F));

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
