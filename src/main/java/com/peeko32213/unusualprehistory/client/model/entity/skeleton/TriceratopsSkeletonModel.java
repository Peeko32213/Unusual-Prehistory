package com.peeko32213.unusualprehistory.client.model.entity.skeleton;

import com.peeko32213.unusualprehistory.common.entity.custom.skeleton.TriceratopsSkeleton;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

import static com.peeko32213.unusualprehistory.UnusualPrehistory.modPrefix;

public class TriceratopsSkeletonModel extends GeoModel<TriceratopsSkeleton> {

    private static final ResourceLocation SKELETON_MODEL = modPrefix("geo/triceratops/triceratops_skeleton.geo.json");
    private static final ResourceLocation SKELETON_TEXTURE = modPrefix("textures/entity/triceratops/triceratops_skeleton.png");
    private static final ResourceLocation SKELETON_ANIMATION = modPrefix("animations/skeletons/triceratops_skeleton.animation.json");

    @Override
    public ResourceLocation getModelResource(TriceratopsSkeleton object) {
        return SKELETON_MODEL;
    }

    @Override
    public ResourceLocation getTextureResource(TriceratopsSkeleton object) {
        return SKELETON_TEXTURE;
    }

    @Override
    public ResourceLocation getAnimationResource(TriceratopsSkeleton object) {
        return SKELETON_ANIMATION;
    }
}
