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


    public static final BiMap<ResourceLocation, String> NAMED_ALGORITHMS = HashBiMap.create();
    static {
        NAMED_ALGORITHMS.put(new ResourceLocation("entity_translucent"), "translucent");
        NAMED_ALGORITHMS.put(new ResourceLocation("entity_cutout"), "cutout");
        NAMED_ALGORITHMS.put(new ResourceLocation("entity_cutout_no_cull"), "cutout_no_cull");
    }

    public static final Codec<String> CODEC = ExtraCodecs.stringResolverCodec(
            NAMED_ALGORITHMS::get,  // Convert ResourceLocation to String
            key -> String.valueOf(NAMED_ALGORITHMS.inverse().get(new ResourceLocation(key))) // Convert String back to ResourceLocation
    );






   //private static RenderType entityCutout(GeoAnimatable entity, GeoRenderer entityRenderer) {
   //    return ClientUtils.entityCutout(entity, entityRenderer);
   //}

   //private static RenderType entityCutoutNoCull(GeoAnimatable entity, GeoRenderer entityRenderer) {
   //    return ClientUtils.entityCutoutNoCull(entity, entityRenderer);
   //}

   //private static RenderType translucent(GeoAnimatable entity, GeoRenderer entityRenderer) {
   //    return ClientUtils.translucent(entity, entityRenderer);
   //}

    //public static RenderTypes getRenderType(ResourceLocation location) {
    //    return NAMED_ALGORITHMS.getOrDefault(location, ClientUtils::entityCutout);
    //}
//
    //public static ResourceLocation getRenderType(RenderTypes location) {
    //    return NAMED_ALGORITHMS.inverse().get(location);
    //}
//
    //public static void putRenderType(ResourceLocation location, UPRenderTypes.RenderTypes renderTypes) {
    //    NAMED_ALGORITHMS.put(location, renderTypes);
    //}
}