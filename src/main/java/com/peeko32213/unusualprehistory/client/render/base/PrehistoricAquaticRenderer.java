package com.peeko32213.unusualprehistory.client.render.base;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.peeko32213.unusualprehistory.client.render.layer.RideLayer;
import com.peeko32213.unusualprehistory.common.entity.custom.base.PrehistoricAquaticEntity;
import com.peeko32213.unusualprehistory.common.entity.custom.base.PrehistoricEntity;
import com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.OviraptorEntity;
import com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.aquatic.PanacanthocarisEntity;
import com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.aquatic.StethacanthusEntity;
import com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.aquatic.XiphactinusEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

import java.util.Locale;

public class PrehistoricAquaticRenderer<T extends PrehistoricAquaticEntity> extends PrehistoricRenderer<T> {

    public PrehistoricAquaticRenderer(EntityRendererProvider.Context renderManager, GeoModel<T> modelProvider) {
        super(renderManager, modelProvider);
        addRenderLayer(new <PrehistoricAquaticEntity>RideLayer(this));
    }

    @Override
    protected void applyRotations(T animatable, PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTick) {
        super.applyRotations(animatable, poseStack, ageInTicks, rotationYaw, partialTick);
        if (animatable.isInWater()){
            poseStack.mulPose(Axis.ZP.rotationDegrees(animatable.currentRoll * 360 / 4));
        }
    }
}
