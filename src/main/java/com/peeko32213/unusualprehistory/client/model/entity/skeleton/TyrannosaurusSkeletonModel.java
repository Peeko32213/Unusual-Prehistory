package com.peeko32213.unusualprehistory.client.model.entity.skeleton;

import com.peeko32213.unusualprehistory.common.entity.custom.skeleton.TyrannosaurusSkeleton;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

import static com.peeko32213.unusualprehistory.UnusualPrehistory.modPrefix;

public class TyrannosaurusSkeletonModel extends GeoModel<TyrannosaurusSkeleton> {

    private static final ResourceLocation SKELETON_MODEL = modPrefix("geo/tyrannosaurus/tyrannosaurus_skeleton.geo.json");
    private static final ResourceLocation SKELETON_TEXTURE = modPrefix("textures/entity/tyrannosaurus/tyrannosaurus_skeleton.png");
    private static final ResourceLocation SKELETON_ANIMATION = modPrefix("animations/skeletons/tyrannosaurus_skeleton.animation.json");

    @Override
    public ResourceLocation getModelResource(TyrannosaurusSkeleton object) {
        return SKELETON_MODEL;
    }

    @Override
    public ResourceLocation getTextureResource(TyrannosaurusSkeleton object) {
        return SKELETON_TEXTURE;
    }

    @Override
    public ResourceLocation getAnimationResource(TyrannosaurusSkeleton object) {
        return SKELETON_ANIMATION;
    }
}
