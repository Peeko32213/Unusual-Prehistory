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

public class UlughRenderModel<T extends BaseEntityRender> extends EntityModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
            new ResourceLocation(UnusualPrehistory.MODID, "ulugh_render"), "main");
        private final ModelPart Leg1;
        private final ModelPart Leg2;
        private final ModelPart OuterBody;

public UlughRenderModel(ModelPart root) {
        this.Leg1 = root.getChild("Leg1");
        this.Leg2 = root.getChild("Leg2");
        this.OuterBody = root.getChild("OuterBody");
        }

public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition Leg1 = partdefinition.addOrReplaceChild("Leg1", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -1.0F, -8.0F, 9.0F, 13.0F, 13.0F, new CubeDeformation(0.0F))
                .texOffs(0, 26).addBox(-4.5F, 12.0F, -3.0F, 8.0F, 18.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(24, 26).addBox(-4.5F, 28.0F, -8.0F, 8.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(8.0F, -6.0F, 12.0F));

        PartDefinition Leg2 = partdefinition.addOrReplaceChild("Leg2", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-4.0F, -1.0F, -8.0F, 9.0F, 13.0F, 13.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 26).mirror().addBox(-3.5F, 12.0F, -3.0F, 8.0F, 18.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(24, 26).mirror().addBox(-3.5F, 28.0F, -8.0F, 8.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-8.0F, -6.0F, 12.0F));

        PartDefinition OuterBody = partdefinition.addOrReplaceChild("OuterBody", CubeListBuilder.create(), PartPose.offset(0.0F, -10.0F, -4.0F));

        PartDefinition Arm1 = OuterBody.addOrReplaceChild("Arm1", CubeListBuilder.create().texOffs(31, 0).addBox(0.0F, 14.0F, -3.0F, 2.0F, 4.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(0, 64).addBox(-3.0F, -2.0F, -3.0F, 5.0F, 16.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(9.0F, 3.0F, -5.0F));

        PartDefinition Arm2 = OuterBody.addOrReplaceChild("Arm2", CubeListBuilder.create().texOffs(31, 0).mirror().addBox(-2.0F, 14.0F, -3.0F, 2.0F, 4.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 64).mirror().addBox(-2.0F, -2.0F, -3.0F, 5.0F, 16.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-9.0F, 3.0F, -5.0F));

        PartDefinition Body = OuterBody.addOrReplaceChild("Body", CubeListBuilder.create().texOffs(0, 64).addBox(-9.5F, -10.0F, -14.0F, 17.0F, 19.0F, 32.0F, new CubeDeformation(0.0F))
                .texOffs(133, 65).addBox(-9.5F, -10.0F, -14.0F, 17.0F, 19.0F, 32.0F, new CubeDeformation(0.1F))
                .texOffs(148, 48).addBox(-7.5F, -14.0F, -2.0F, 13.0F, 4.0F, 13.0F, new CubeDeformation(0.1F))
                .texOffs(161, 44).addBox(-7.5F, -18.0F, -2.0F, 13.0F, 4.0F, 0.0F, new CubeDeformation(0.1F)), PartPose.offset(1.0F, 0.0F, 1.0F));

        PartDefinition Neck = Body.addOrReplaceChild("Neck", CubeListBuilder.create().texOffs(71, 0).addBox(-8.0F, -18.0F, -10.0F, 14.0F, 23.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -4.0F, -10.0F));

        PartDefinition Head = Neck.addOrReplaceChild("Head", CubeListBuilder.create().texOffs(98, 90).addBox(-6.0F, -10.0F, -7.0F, 10.0F, 6.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(66, 64).addBox(-4.0F, -10.0F, -19.0F, 6.0F, 8.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(98, 103).addBox(1.0F, -12.0F, -10.0F, 2.0F, 4.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(98, 103).mirror().addBox(-5.0F, -12.0F, -10.0F, 2.0F, 4.0F, 10.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, -7.0F, -10.0F));

        PartDefinition Jaw = Head.addOrReplaceChild("Jaw", CubeListBuilder.create().texOffs(95, 77).addBox(-6.0F, 0.0F, -7.0F, 10.0F, 6.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(71, 37).addBox(-4.0F, 2.0F, -19.0F, 6.0F, 4.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(124, 0).addBox(2.8F, -5.0F, -4.0F, 1.0F, 5.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(124, 0).mirror().addBox(-5.8F, -5.0F, -4.0F, 1.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, -4.0F, 0.0F));

        PartDefinition Reins1 = Head.addOrReplaceChild("Reins1", CubeListBuilder.create().texOffs(161, 6).addBox(-1.0F, 1.0F, -7.0F, 0.0F, 14.0F, 24.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.3491F));

        PartDefinition Reins2 = Head.addOrReplaceChild("Reins2", CubeListBuilder.create().texOffs(161, 6).mirror().addBox(1.0F, 1.0F, -7.0F, 0.0F, 14.0F, 24.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-7.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.3491F));

        PartDefinition Tail = Body.addOrReplaceChild("Tail", CubeListBuilder.create().texOffs(0, 0).addBox(-4.5F, 0.0F, 0.0F, 9.0F, 11.0F, 53.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, -10.0F, 18.0F));

        return LayerDefinition.create(meshdefinition, 256, 256);
        }

@Override
public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

        }

@Override
public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        OuterBody.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        Leg1.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        Leg2.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        }
        }
