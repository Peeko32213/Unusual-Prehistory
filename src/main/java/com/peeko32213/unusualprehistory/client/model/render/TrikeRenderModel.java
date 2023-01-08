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

public class TrikeRenderModel <T extends BaseEntityRender> extends EntityModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
            new ResourceLocation(UnusualPrehistory.MODID, "trike_render"), "main");
private final ModelPart Body;
private final ModelPart Arm1;
private final ModelPart Arm2;
private final ModelPart Leg1;
private final ModelPart Leg2;

public TrikeRenderModel(ModelPart root) {
        this.Body = root.getChild("Body");
        this.Arm1 = root.getChild("Arm1");
        this.Arm2 = root.getChild("Arm2");
        this.Leg1 = root.getChild("Leg1");
        this.Leg2 = root.getChild("Leg2");
        }

public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition Body = partdefinition.addOrReplaceChild("Body", CubeListBuilder.create().texOffs(0, 0).addBox(-19.5F, -26.0F, -28.0F, 39.0F, 49.0F, 55.0F, new CubeDeformation(0.0F))
        .texOffs(0, 183).addBox(-19.5F, -26.0F, -8.0F, 39.0F, 49.0F, 24.0F, new CubeDeformation(0.1F))
        .texOffs(138, 177).addBox(-19.5F, 5.1F, -28.2F, 39.0F, 18.0F, 20.0F, new CubeDeformation(0.1F))
        .texOffs(192, 23).addBox(-8.0F, -33.0F, -4.0F, 16.0F, 7.0F, 16.0F, new CubeDeformation(0.0F))
        .texOffs(192, 46).addBox(-8.0F, -36.0F, 8.0F, 16.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
        .texOffs(208, 19).addBox(-8.0F, -36.0F, -4.0F, 16.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -19.0F, 0.0F));

        PartDefinition Neck = Body.addOrReplaceChild("Neck", CubeListBuilder.create().texOffs(0, 152).addBox(-13.0F, -5.0F, -6.0F, 25.0F, 25.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, -28.0F));

        PartDefinition Head = Neck.addOrReplaceChild("Head", CubeListBuilder.create().texOffs(20, 22).addBox(-6.5F, 23.0F, -23.0F, 11.0F, 3.0F, 6.0F, new CubeDeformation(0.0F))
        .texOffs(0, 0).addBox(-6.5F, 12.0F, -23.0F, 11.0F, 11.0F, 11.0F, new CubeDeformation(0.0F))
        .texOffs(114, 234).addBox(-6.5F, 12.0F, -23.0F, 11.0F, 11.0F, 11.0F, new CubeDeformation(0.1F))
        .texOffs(144, 144).addBox(-11.0F, 3.0F, -12.0F, 20.0F, 20.0F, 13.0F, new CubeDeformation(0.0F))
        .texOffs(190, 223).addBox(-11.0F, 3.0F, -12.0F, 20.0F, 20.0F, 13.0F, new CubeDeformation(0.1F))
        .texOffs(0, 104).addBox(-20.5F, -25.2F, -5.95F, 41.0F, 41.0F, 7.0F, new CubeDeformation(0.0F))
        .texOffs(20, 31).addBox(-3.5F, 7.0F, -19.0F, 5.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.0F, -7.0F));

        PartDefinition Horn1 = Head.addOrReplaceChild("Horn1", CubeListBuilder.create().texOffs(0, 22).addBox(-2.5F, -22.0F, -4.0F, 5.0F, 22.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.0F, 10.0F, -7.0F, 0.4363F, 0.0F, 0.0F));

        PartDefinition Horn2 = Head.addOrReplaceChild("Horn2", CubeListBuilder.create().texOffs(0, 22).addBox(-2.5F, -22.0F, -4.0F, 5.0F, 22.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-8.0F, 10.0F, -7.0F, 0.4363F, 0.0F, 0.0F));

        PartDefinition Tail = Body.addOrReplaceChild("Tail", CubeListBuilder.create().texOffs(96, 104).addBox(-7.0F, -8.0F, 0.0F, 15.0F, 16.0F, 24.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 4.0F, 27.0F));

        PartDefinition Arm1 = partdefinition.addOrReplaceChild("Arm1", CubeListBuilder.create().texOffs(80, 144).addBox(-8.0F, 0.0F, -8.5F, 16.0F, 20.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(11.0F, 4.0F, -16.0F));

        PartDefinition Arm2 = partdefinition.addOrReplaceChild("Arm2", CubeListBuilder.create().texOffs(80, 144).addBox(-8.0F, 0.0F, -8.5F, 16.0F, 20.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(-11.0F, 4.0F, -16.0F));

        PartDefinition Leg1 = partdefinition.addOrReplaceChild("Leg1", CubeListBuilder.create().texOffs(174, 90).addBox(-7.0F, 22.0F, -6.5F, 14.0F, 17.0F, 14.0F, new CubeDeformation(0.0F))
        .texOffs(133, 0).addBox(-8.0F, -1.0F, -8.5F, 16.0F, 23.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(15.0F, -15.0F, 26.5F));

        PartDefinition Leg2 = partdefinition.addOrReplaceChild("Leg2", CubeListBuilder.create().texOffs(174, 90).addBox(-7.0F, 22.0F, -6.5F, 14.0F, 17.0F, 14.0F, new CubeDeformation(0.0F))
        .texOffs(133, 0).addBox(-8.0F, -1.0F, -8.5F, 16.0F, 23.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(-15.0F, -15.0F, 26.5F));

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
