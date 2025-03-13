package com.peeko32213.unusualprehistory.common.data.entity;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.mojang.serialization.Codec;
import com.peeko32213.unusualprehistory.client.ClientUtils;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.renderer.GeoRenderer;

public class UPRenderTypes {

    public interface RenderTypes {
        RenderType getRenderType(GeoAnimatable entity, GeoRenderer entityRenderer);
    }

    public static final BiMap<ResourceLocation, RenderTypes> NAMED_ALGORITHMS = HashBiMap.create();
    static {
        NAMED_ALGORITHMS.put( ResourceLocation.tryParse("entity_translucent"), UPRenderTypes::translucent );
        NAMED_ALGORITHMS.put( ResourceLocation.tryParse("entity_cutout"), UPRenderTypes::entityCutout);
        NAMED_ALGORITHMS.put( ResourceLocation.tryParse("entity_cutout_no_cull"), UPRenderTypes::entityCutoutNoCull);

    }

    public static final Codec<RenderTypes> CODEC = ExtraCodecs.stringResolverCodec(
            sa -> NAMED_ALGORITHMS.inverse().get(sa).toString(),
            key -> NAMED_ALGORITHMS.get(new ResourceLocation(key))
    );





    private static RenderType entityCutout(GeoAnimatable entity, GeoRenderer entityRenderer) {
        return ClientUtils.entityCutout(entity, entityRenderer);
    }

    private static RenderType entityCutoutNoCull(GeoAnimatable entity, GeoRenderer entityRenderer) {
        return ClientUtils.entityCutoutNoCull(entity, entityRenderer);
    }

    private static RenderType translucent(GeoAnimatable entity, GeoRenderer entityRenderer) {
        return ClientUtils.translucent(entity, entityRenderer);
    }

    public static RenderTypes getRenderType(ResourceLocation location) {
        return NAMED_ALGORITHMS.getOrDefault(location, UPRenderTypes::entityCutout);
    }

    public static ResourceLocation getRenderType(RenderTypes location) {
        return NAMED_ALGORITHMS.inverse().get(location);
    }

    public static void putRenderType(ResourceLocation location, UPRenderTypes.RenderTypes renderTypes) {
        NAMED_ALGORITHMS.put(location, renderTypes);
    }
}