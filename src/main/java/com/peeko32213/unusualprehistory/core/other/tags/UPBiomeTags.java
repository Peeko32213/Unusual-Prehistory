package com.peeko32213.unusualprehistory.core.other.tags;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

public class UPBiomeTags {

    // Feature
    public static final TagKey<Biome> HAS_GIANT_FOSSILS = biomeTag("has_feature/giant_fossil");
    public static final TagKey<Biome> IS_PETRIFIED_WOOD_FOREST_BIOME = biomeTag("is_petrified_wood_forest_biome");
    public static final TagKey<Biome> IS_ICE_FOSSIL_ICEBERG_BIOME = biomeTag("is_ice_fossil_iceberg_biome");
    public static final TagKey<Biome> IS_TAR_BIOME = biomeTag("is_tar_biome");

    // Structure
    public static final TagKey<Biome> HAS_FOSSIL_SKELETONS = biomeTag("has_structure/fossil_skeleton");
    public static final TagKey<Biome> HAS_UNDERGROUND_DIG_SITES = biomeTag("has_structure/underground_dig_sites");

    private static TagKey<Biome> biomeTag(String name) {
        return TagKey.create(Registries.BIOME, new ResourceLocation(UnusualPrehistory.MODID, name));
    }
}
