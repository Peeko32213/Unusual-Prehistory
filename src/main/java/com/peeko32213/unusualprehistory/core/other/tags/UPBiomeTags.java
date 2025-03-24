package com.peeko32213.unusualprehistory.core.other.tags;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.teamabnormals.blueprint.core.util.TagUtil;
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

    // Mob
    // Empty by default, filled by Natural Prehistoric Mob Spawning built-in datapack
    public static final TagKey<Biome> IS_STETHA_BIOME = biomeTag("is_stetha_biome");
    public static final TagKey<Biome> IS_MAJUNGA_BIOME = biomeTag("is_majunga_biome");
    public static final TagKey<Biome> IS_ANURO_BIOME = biomeTag("is_anuro_biome");
    public static final TagKey<Biome> IS_BEELZ_BIOME = biomeTag("is_beelz_biome");
    public static final TagKey<Biome> IS_AMMON_BIOME = biomeTag("is_ammon_biome");
    public static final TagKey<Biome> IS_DUNK_BIOME = biomeTag("is_dunk_biome");
    public static final TagKey<Biome> IS_COTY_BIOME = biomeTag("is_coty_biome");
    public static final TagKey<Biome> IS_SCAU_BIOME = biomeTag("is_scau_biome");
    public static final TagKey<Biome> IS_TRIKE_BIOME = biomeTag("is_trike_biome");
    public static final TagKey<Biome> IS_PACHY_BIOME = biomeTag("is_pachy_biome");
    public static final TagKey<Biome> IS_BRACHI_BIOME = biomeTag("is_brachi_biome");
    public static final TagKey<Biome> IS_VELOCI_BIOME = biomeTag("is_veloci_biome");
    public static final TagKey<Biome> IS_REX_BIOME = biomeTag("is_rex_biome");
    public static final TagKey<Biome> IS_ERYON_BIOME = biomeTag("is_eryon_biome");
    public static final TagKey<Biome> IS_AUSTRO_BIOME = biomeTag("is_austro_biome");
    public static final TagKey<Biome> IS_ANTARCTO_BIOME = biomeTag("is_antarcto_biome");
    public static final TagKey<Biome> IS_ULUG_BIOME = biomeTag("is_ulug_biome");
    public static final TagKey<Biome> IS_KENTRO_BIOME = biomeTag("is_kentro_biome");
    public static final TagKey<Biome> IS_HWACHA_BIOME = biomeTag("is_hwacha_biome");
    public static final TagKey<Biome> IS_ENCRUSTED_BIOME = biomeTag("is_encrusted_biome");
    public static final TagKey<Biome> IS_MAMMOTH_BIOME = biomeTag("is_mammoth_biome");
    public static final TagKey<Biome> IS_PALAEO_BIOME = biomeTag("is_palaeo_biome");
    public static final TagKey<Biome> IS_MEGATHERIUM_BIOME = biomeTag("is_megatherium_biome");
    public static final TagKey<Biome> IS_SMILODON_BIOME = biomeTag("is_smilodon_biome");
    public static final TagKey<Biome> IS_TALPANAS_BIOME = biomeTag("is_talpanas_biome");
    public static final TagKey<Biome> IS_PARACER_BIOME = biomeTag("is_paracer_biome");
    public static final TagKey<Biome> IS_MEGALANIA_BIOME = biomeTag("is_megalania_biome");
    public static final TagKey<Biome> IS_BARINA_BIOME = biomeTag("is_barina_biome");
    public static final TagKey<Biome> IS_GIGANTO_BIOME = biomeTag("is_giganto_biome");

    private static TagKey<Biome> biomeTag(String name) {
        return TagUtil.biomeTag(UnusualPrehistory.MODID, name);
    }
}
