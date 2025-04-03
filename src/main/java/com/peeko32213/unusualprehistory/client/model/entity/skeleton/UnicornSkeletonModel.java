package com.peeko32213.unusualprehistory.client.model.entity.skeleton;

import com.peeko32213.unusualprehistory.common.entity.custom.skeleton.UnicornSkeleton;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

import static com.peeko32213.unusualprehistory.UnusualPrehistory.modPrefix;

public class UnicornSkeletonModel extends GeoModel<UnicornSkeleton> {

    private static final ResourceLocation SKELETON_MODEL = modPrefix("geo/unicorn/unicorn_skeleton.geo.json");
    private static final ResourceLocation SKELETON_TEXTURE = modPrefix("textures/entity/unicorn/unicorn_skeleton.png");
    private static final ResourceLocation SKELETON_ANIMATION = modPrefix("animations/skeletons/unicorn_skeleton.animation.json");

    @Override
    public ResourceLocation getModelResource(UnicornSkeleton object) {
        return SKELETON_MODEL;
    }

    @Override
    public ResourceLocation getTextureResource(UnicornSkeleton object) {
        return SKELETON_TEXTURE;
    }

    @Override
    public ResourceLocation getAnimationResource(UnicornSkeleton object) {
        return SKELETON_ANIMATION;
    }
}
