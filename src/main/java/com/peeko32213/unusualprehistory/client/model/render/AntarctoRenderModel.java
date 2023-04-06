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

public class AntarctoRenderModel<T extends BaseEntityRender> extends EntityModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
            new ResourceLocation(UnusualPrehistory.MODID, "antarcto_render"), "main");
        private final ModelPart Body;
        private final ModelPart Legs;

public AntarctoRenderModel(ModelPart root) {
        this.Body = root.getChild("Body");
        this.Legs = root.getChild("Legs");
        }

public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition Body = partdefinition.addOrReplaceChild("Body", CubeListBuilder.create().texOffs(0, 48).mirror().addBox(-16.5F, -3.0F, 3.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 48).mirror().addBox(-16.5F, -3.0F, 10.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 67).mirror().addBox(-17.5F, -3.0F, -5.0F, 4.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 67).mirror().addBox(-17.5F, -3.0F, -13.0F, 4.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 67).addBox(13.5F, -3.0F, -13.0F, 4.0F, 2.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(0, 67).addBox(13.5F, -3.0F, -5.0F, 4.0F, 2.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(0, 48).addBox(12.5F, -3.0F, 10.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 48).addBox(12.5F, -3.0F, 3.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-13.5F, -11.0F, -16.0F, 27.0F, 16.0F, 32.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 10.0F, 0.0F));

        PartDefinition Tail = Body.addOrReplaceChild("Tail", CubeListBuilder.create().texOffs(0, 67).addBox(-7.0F, 0.0F, 0.0F, 13.0F, 8.0F, 21.0F, new CubeDeformation(0.0F))
                .texOffs(0, 17).addBox(6.0F, 3.0F, 2.0F, 7.0F, 2.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(0, 17).addBox(6.0F, 3.0F, 12.0F, 7.0F, 2.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(0, 17).mirror().addBox(-13.0F, 3.0F, 2.0F, 7.0F, 2.0F, 9.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 17).mirror().addBox(-13.0F, 3.0F, 12.0F, 7.0F, 2.0F, 9.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, -6.0F, 16.0F));

        PartDefinition BackTail = Tail.addOrReplaceChild("BackTail", CubeListBuilder.create().texOffs(0, 17).mirror().addBox(-11.0F, 2.1F, 1.0F, 7.0F, 2.0F, 9.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 17).addBox(4.0F, 2.1F, 1.0F, 7.0F, 2.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(68, 67).addBox(-5.0F, -1.0F, 0.0F, 9.0F, 7.0F, 19.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.0F, 21.0F));

        PartDefinition Slice = BackTail.addOrReplaceChild("Slice", CubeListBuilder.create().texOffs(0, 48).addBox(-15.5F, -1.0F, 0.0F, 30.0F, 2.0F, 17.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, 13.0F));

        PartDefinition Neck = Body.addOrReplaceChild("Neck", CubeListBuilder.create().texOffs(77, 48).addBox(-7.5F, -3.0F, -9.0F, 15.0F, 9.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(0, 48).mirror().addBox(-11.0F, 2.0F, -5.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 48).addBox(7.0F, 2.0F, -5.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -5.0F, -15.0F));

        PartDefinition Head = Neck.addOrReplaceChild("Head", CubeListBuilder.create().texOffs(47, 67).addBox(-3.5F, -2.0F, -12.0F, 7.0F, 7.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(0, 28).addBox(-3.5F, 5.0F, -12.0F, 7.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -9.0F));

        PartDefinition Legs = partdefinition.addOrReplaceChild("Legs", CubeListBuilder.create(), PartPose.offset(0.0F, 15.0F, 0.0F));

        PartDefinition Leg1 = Legs.addOrReplaceChild("Leg1", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -1.0F, -2.0F, 7.0F, 10.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(0, 54).addBox(-3.0F, 7.0F, -3.0F, 7.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(7.0F, 0.0F, 10.0F));

        PartDefinition Leg2 = Legs.addOrReplaceChild("Leg2", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-4.0F, -1.0F, -2.0F, 7.0F, 10.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 54).mirror().addBox(-4.0F, 7.0F, -3.0F, 7.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-7.0F, 0.0F, 10.0F));

        PartDefinition Arm1 = Legs.addOrReplaceChild("Arm1", CubeListBuilder.create().texOffs(86, 0).addBox(-4.0F, -2.0F, -4.0F, 7.0F, 10.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(0, 57).addBox(-4.0F, 6.0F, -5.0F, 7.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(8.0F, 1.0F, -9.0F));

        PartDefinition Arm2 = Legs.addOrReplaceChild("Arm2", CubeListBuilder.create().texOffs(86, 0).mirror().addBox(-3.0F, -2.0F, -4.0F, 7.0F, 10.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 57).mirror().addBox(-3.0F, 6.0F, -5.0F, 7.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-8.0F, 1.0F, -9.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
        }

@Override
public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

        }

@Override
public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        Body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        Legs.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        }
        }
