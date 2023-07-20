package com.peeko32213.unusualprehistory.client.render.armor;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.peeko32213.unusualprehistory.client.model.armor.ShedscaleChestplateModel;
import com.peeko32213.unusualprehistory.client.model.armor.ShedscaleHelmetModel;
import com.peeko32213.unusualprehistory.common.item.armor.shedscale.ItemShedscaleChestplate;
import com.peeko32213.unusualprehistory.common.item.armor.shedscale.ItemShedscaleHelmet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

public class ShedscaleChestplateRenderer extends GeoArmorRenderer<ItemShedscaleChestplate> {
    public ShedscaleChestplateRenderer() {
        super(new ShedscaleChestplateModel());

        this.bodyBone = "armorBody";
        this.rightArmBone = "armorRightArm";
        this.leftArmBone = "armorLeftArm";

    }

    @Override
    public RenderType getRenderType(ItemShedscaleChestplate animatable, float partialTick, PoseStack poseStack, @Nullable MultiBufferSource bufferSource, @Nullable VertexConsumer buffer, int packedLight, ResourceLocation texture) {
        return RenderType.armorCutoutNoCull(getTextureLocation(animatable));
    }
}
