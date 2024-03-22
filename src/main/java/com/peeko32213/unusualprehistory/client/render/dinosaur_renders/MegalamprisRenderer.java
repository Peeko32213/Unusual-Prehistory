package com.peeko32213.unusualprehistory.client.render.dinosaur_renders;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.peeko32213.unusualprehistory.client.model.DefaultModel;
import com.peeko32213.unusualprehistory.client.model.LeedsichthysModel;
import com.peeko32213.unusualprehistory.client.model.MegalamprisModel;
import com.peeko32213.unusualprehistory.client.model.ModelLocations;
import com.peeko32213.unusualprehistory.common.entity.EntityLeedsichthys;
import com.peeko32213.unusualprehistory.common.entity.EntityMegalampris;
import com.peeko32213.unusualprehistory.common.entity.msc.projectile.EntityAmberShot;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class MegalamprisRenderer extends GeoEntityRenderer<EntityMegalampris> {

    public MegalamprisRenderer(EntityRendererProvider.Context context) {
        super(context, new MegalamprisModel());
    }
}
