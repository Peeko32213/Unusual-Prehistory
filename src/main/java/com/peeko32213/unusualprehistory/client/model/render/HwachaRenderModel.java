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

public class HwachaRenderModel<T extends BaseEntityRender> extends EntityModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
            new ResourceLocation(UnusualPrehistory.MODID, "hwacha_render"), "main");
        private final ModelPart Body;
        private final ModelPart Arm1;
        private final ModelPart Arm2;
        private final ModelPart Leg1;
        private final ModelPart Leg2;

public HwachaRenderModel(ModelPart root) {
        this.Body = root.getChild("Body");
        this.Arm1 = root.getChild("Arm1");
        this.Arm2 = root.getChild("Arm2");
        this.Leg1 = root.getChild("Leg1");
        this.Leg2 = root.getChild("Leg2");
        }

public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition Body = partdefinition.addOrReplaceChild("Body", CubeListBuilder.create().texOffs(84, 0).addBox(-7.5F, -11.0F, -6.0F, 15.0F, 18.0F, 32.0F, new CubeDeformation(0.0F))
                .texOffs(0, 149).addBox(-7.5F, -11.0F, -6.0F, 15.0F, 18.0F, 32.0F, new CubeDeformation(0.1F))
                .texOffs(0, 24).addBox(7.5F, -9.0F, 6.0F, 6.0F, 2.0F, 20.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(11.5F, -8.0F, 7.0F, 8.0F, 0.0F, 20.0F, new CubeDeformation(0.0F))
                .texOffs(0, 24).mirror().addBox(-13.5F, -9.0F, 6.0F, 6.0F, 2.0F, 20.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 0).mirror().addBox(-19.5F, -8.0F, 7.0F, 8.0F, 0.0F, 20.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, -8.0F, -9.0F));

        PartDefinition Tail = Body.addOrReplaceChild("Tail", CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, -1.0F, 1.0F, 11.0F, 9.0F, 62.0F, new CubeDeformation(0.0F))
                .texOffs(0, 17).addBox(4.0F, -15.0F, 9.0F, 0.0F, 14.0F, 54.0F, new CubeDeformation(0.0F))
                .texOffs(0, 17).mirror().addBox(-4.0F, -15.0F, 9.0F, 0.0F, 14.0F, 54.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(59, 71).addBox(5.0F, 0.0F, 1.0F, 5.0F, 2.0F, 49.0F, new CubeDeformation(0.0F))
                .texOffs(59, 71).mirror().addBox(-10.0F, 0.0F, 1.0F, 5.0F, 2.0F, 49.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 0).addBox(10.0F, 1.0F, 3.0F, 4.0F, 0.0F, 44.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).mirror().addBox(-14.0F, 1.0F, 3.0F, 4.0F, 0.0F, 44.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, -9.0F, 25.0F));

        PartDefinition Neck = Body.addOrReplaceChild("Neck", CubeListBuilder.create().texOffs(0, 85).addBox(-6.5F, -21.0F, -9.0F, 13.0F, 28.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.0F, -3.0F));

        PartDefinition Head = Neck.addOrReplaceChild("Head", CubeListBuilder.create().texOffs(126, 50).addBox(-3.5F, -6.0F, -13.0F, 7.0F, 5.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(198, 0).addBox(3.4F, -1.0F, -17.0F, 0.0F, 5.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(198, 0).mirror().addBox(-3.4F, -1.0F, -17.0F, 0.0F, 5.0F, 9.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(216, 0).addBox(-3.6F, 2.0F, -17.0F, 7.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(46, 40).addBox(-1.0F, -12.0F, -11.0F, 2.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(41, 85).addBox(-3.5F, -6.0F, -17.0F, 7.0F, 8.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(75, 122).addBox(-4.5F, -8.0F, -8.0F, 9.0F, 10.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(0, 4).addBox(2.0F, -10.0F, -17.0F, 0.0F, 4.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(0, 4).mirror().addBox(-2.0F, -10.0F, -17.0F, 0.0F, 4.0F, 16.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, -11.0F, -9.0F));

        PartDefinition Jaw = Head.addOrReplaceChild("Jaw", CubeListBuilder.create().texOffs(0, 38).addBox(0.0F, 6.0F, -13.925F, 0.0F, 4.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(118, 71).addBox(-3.0F, 0.0F, -14.925F, 6.0F, 6.0F, 17.0F, new CubeDeformation(0.0F))
                .texOffs(146, -17).addBox(2.95F, -2.0F, -15.0F, 0.0F, 2.0F, 17.0F, new CubeDeformation(0.0F))
                .texOffs(146, -17).mirror().addBox(-2.95F, -2.0F, -15.0F, 0.0F, 2.0F, 17.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 0).addBox(-3.0F, -2.0F, -14.975F, 6.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.0F, -2.0F));

        PartDefinition TurretPlate1 = Head.addOrReplaceChild("TurretPlate1", CubeListBuilder.create().texOffs(84, 50).addBox(-1.0F, -2.0F, -1.0F, 15.0F, 4.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(0, 46).addBox(0.0F, 0.5F, -3.0F, 17.0F, 0.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.0F, -5.875F, -2.0F, 1.1829F, -0.8134F, -0.9878F));

        PartDefinition TurretPlate2 = Head.addOrReplaceChild("TurretPlate2", CubeListBuilder.create().texOffs(84, 50).mirror().addBox(-14.0F, -2.0F, -1.0F, 15.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 46).mirror().addBox(-17.0F, 0.5F, -3.0F, 17.0F, 0.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-6.0F, -5.875F, -2.0F, 1.1829F, 0.8134F, 0.9878F));

        PartDefinition SmallTurretPlate1 = Head.addOrReplaceChild("SmallTurretPlate1", CubeListBuilder.create().texOffs(101, 122).addBox(-1.0F, -3.0F, -1.0F, 12.0F, 3.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(25, 54).addBox(-1.0F, -1.5F, -3.0F, 14.0F, 0.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.0F, 0.0F, -2.0F, -0.073F, -0.6949F, 0.1137F));

        PartDefinition SmallTurretPlate2 = Head.addOrReplaceChild("SmallTurretPlate2", CubeListBuilder.create().texOffs(101, 122).mirror().addBox(-11.0F, -3.0F, -1.0F, 12.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(25, 54).mirror().addBox(-13.0F, -1.5F, -3.0F, 14.0F, 0.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-5.0F, 0.0F, -2.0F, -0.073F, 0.6949F, -0.1137F));

        PartDefinition Reins1 = Head.addOrReplaceChild("Reins1", CubeListBuilder.create().texOffs(134, 163).addBox(1.5F, 0.0F, -6.0F, 0.0F, 21.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.75F, 1.5F, -7.0F, 0.2616F, -0.0113F, 0.2167F));

        PartDefinition Reins2 = Head.addOrReplaceChild("Reins2", CubeListBuilder.create().texOffs(134, 163).mirror().addBox(-1.5F, 0.0F, -6.0F, 0.0F, 21.0F, 16.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(5.75F, 1.5F, -7.0F, 0.2616F, 0.0113F, -0.2167F));

        PartDefinition Arm1 = partdefinition.addOrReplaceChild("Arm1", CubeListBuilder.create().texOffs(84, 0).addBox(2.0F, 3.0F, -6.0F, 0.0F, 17.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(49, 122).addBox(-3.0F, 0.0F, -4.0F, 6.0F, 20.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(7.0F, -6.0F, -7.0F));

        PartDefinition Fingies1 = Arm1.addOrReplaceChild("Fingies1", CubeListBuilder.create().texOffs(0, 85).addBox(-2.0F, 0.0F, -3.0F, 2.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, 20.0F, 0.0F));

        PartDefinition Arm2 = partdefinition.addOrReplaceChild("Arm2", CubeListBuilder.create().texOffs(84, 0).mirror().addBox(-2.0F, 3.0F, -6.0F, 0.0F, 17.0F, 14.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(49, 122).mirror().addBox(-3.0F, 0.0F, -4.0F, 6.0F, 20.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-7.0F, -6.0F, -7.0F));

        PartDefinition Fingies2 = Arm2.addOrReplaceChild("Fingies2", CubeListBuilder.create().texOffs(0, 85).mirror().addBox(0.0F, 0.0F, -3.0F, 2.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-3.0F, 20.0F, 0.0F));

        PartDefinition Leg1 = partdefinition.addOrReplaceChild("Leg1", CubeListBuilder.create().texOffs(84, 0).addBox(-3.0F, 32.0F, -9.0F, 8.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(118, 94).addBox(-3.0F, 19.0F, -3.0F, 8.0F, 15.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(56, 85).addBox(-3.0F, 0.0F, -8.0F, 8.0F, 19.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offset(7.0F, -10.0F, 16.0F));

        PartDefinition Leg2 = partdefinition.addOrReplaceChild("Leg2", CubeListBuilder.create().texOffs(84, 0).mirror().addBox(-5.0F, 32.0F, -9.0F, 8.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(118, 94).mirror().addBox(-5.0F, 19.0F, -3.0F, 8.0F, 15.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(56, 85).mirror().addBox(-5.0F, 0.0F, -8.0F, 8.0F, 19.0F, 13.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-7.0F, -10.0F, 16.0F));

        return LayerDefinition.create(meshdefinition, 256, 256);
        }

@Override
public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

        }

@Override
public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        Body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        Arm1.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        Arm2.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        Leg1.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        Leg2.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        }
        }
