package com.peeko32213.unusualprehistory.core.registry;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Instrument;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;

public class UPTags {


    public static final TagKey<Instrument> OCARINA_WHISTLE = registerInstrument("ocarina_whistle");

    public static final TagKey<EntityType<?>> ANURO_EGG_TRAMPLERS = registerEntityTag("anuro_egg_tramplers");
    public static final TagKey<EntityType<?>> BRACHI_EGG_TRAMPLERS = registerEntityTag("brachi_egg_tramplers");
    //public static final TagKey<EntityType<?>> ANURO_EGG_TRAMPLERS = registerEntityTag("anuro_egg_tramplers");
    //public static final TagKey<EntityType<?>> ANURO_EGG_TRAMPLERS = registerEntityTag("anuro_egg_tramplers");
    //public static final TagKey<EntityType<?>> ANURO_EGG_TRAMPLERS = registerEntityTag("anuro_egg_tramplers");
    //Target tags
    public static final TagKey<EntityType<?>> ANURO_TARGETS = registerEntityTag("anuro_targets");
    public static final TagKey<EntityType<?>> MAJUNGA_TARGETS = registerEntityTag("majunga_targets");

    public static final TagKey<EntityType<?>> MEGALANIA_TARGETS = registerEntityTag("megalania_targets");

    public static final TagKey<EntityType<?>> BEELZE_TARGETS = registerEntityTag("beelze_targets");
    public static final TagKey<EntityType<?>> REX_TARGETS = registerEntityTag("rex_targets");
    public static final TagKey<EntityType<?>> RAPTOR_TARGETS = registerEntityTag("raptor_targets");
    public static final TagKey<EntityType<?>> ANTARCTO_TARGETS = registerEntityTag("antarcto_targets");
    public static final TagKey<EntityType<?>> ENCRUSTED_TARGETS = registerEntityTag("encrusted_targets");
    public static final TagKey<EntityType<?>> DUNK_TARGETS = registerEntityTag("dunk_targets");
    public static final TagKey<EntityType<?>> LAND_MOBS = registerEntityTag("land_mobs");
    public static final TagKey<EntityType<?>> PSITTACO_TARGETS = registerEntityTag("psittaco_targets");

    public static final TagKey<EntityType<?>> SMILODON_TARGETS = registerEntityTag("smilodon_targets");
    public static final TagKey<EntityType<?>> HERBIVORES = registerEntityTag("herbivores");
    public static final TagKey<EntityType<?>> CARNIVORES = registerEntityTag("carnivores");
    public static final TagKey<EntityType<?>> OMNIVORES = registerEntityTag("omnivores");
    public static final TagKey<EntityType<?>> PISCIVORE_DIET = registerEntityTag("piscivore_diet");

    public static final TagKey<EntityType<?>> SMILODON_EMBRYO_ATTACH_TO= registerEntityTag("smilodon_embryo_attach_to");
    public static final TagKey<EntityType<?>> MAMMOTH_EMBRYO_ATTACH_TO= registerEntityTag("mammoth_embryo_attach_to");
    public static final TagKey<EntityType<?>> MEGATH_EMBRYO_ATTACH_TO= registerEntityTag("megath_embryo_attach_to");
    public static final TagKey<EntityType<?>> GIGANTO_EMBRYO_ATTACH_TO= registerEntityTag("giganto_embryo_attach_to");
    public static final TagKey<EntityType<?>> PARACER_EMBRYO_ATTACH_TO= registerEntityTag("paracer_embryo_attach_to");
    public static final TagKey<EntityType<?>> PALAEO_EMBRYO_ATTACH_TO= registerEntityTag("palaeo_embryo_attach_to");

    public static final TagKey<EntityType<?>> TAR_WALKABLE_ON_MOBS= registerEntityTag("tar_walkable_on_mobs");
    public static final TagKey<EntityType<?>> TAR_WALKABLE_THROUGH_MOBS= registerEntityTag("tar_walkable_through_mobs");

    //Misc Tags
    public static final TagKey<Item> ALLOWED_FRIDGE_ITEMS = registerItemTag("allowed_fridge_items");
    public static final TagKey<Item> ANALYZER_ITEMS_INPUT = registerItemTag("analyzer_items");

    public static final TagKey<Item> ANALYZER_ITEMS_OUTPUT_MESOZOIC = registerItemTag("analyzer_items_output_mezo_fossil");
    public static final TagKey<Item> ANALYZER_ITEMS_OUTPUT_PALEO = registerItemTag("analyzer_items_output_paleo_fossil");
    public static final TagKey<Item> ANALYZER_ITEMS_OUTPUT_AMBER = registerItemTag("analyzer_items_output_amber_fossil");
    public static final TagKey<Item> ANALYZER_ITEMS_OUTPUT_ENCRUSTED= registerItemTag("analyzer_items_output_encrusted_organ");
    public static final TagKey<Item> ANALYZER_ITEMS_OUTPUT_GINKGO = registerItemTag("analyzer_items_output_ginkgo_fruit");
    public static final TagKey<Item> ANALYZER_ITEMS_OUTPUT_PETRIFIED = registerItemTag("analyzer_items_output_petrified");

    public static final TagKey<Item> ANALYZER_ITEMS_OUTPUT_PLANT = registerItemTag("analyzer_items_output_plant_fossil");
    public static final TagKey<Item> ANALYZER_ITEMS_OUTPUT_TAR = registerItemTag("analyzer_items_output_tar_fossil");
    public static final TagKey<Item> ANALYZER_ITEMS_OUTPUT_OPAL = registerItemTag("analyzer_items_output_opal_fossil");
    public static final TagKey<Item> ANALYZER_ITEMS_OUTPUT_FROZEN = registerItemTag("analyzer_items_output_frozen_fossil");

    public static final TagKey<Item> ANALYZER_ITEMS_OUTPUT_RAW_COTY = registerItemTag("analyzer_items_output_raw_coty");
    public static final TagKey<Item> ANALYZER_ITEMS_OUTPUT_RAW_SCAU = registerItemTag("analyzer_items_output_raw_scau");
    public static final TagKey<Item> ANALYZER_ITEMS_OUTPUT_RAW_STETHA = registerItemTag("analyzer_items_output_raw_stetha");
    public static final TagKey<Item> ANALYZER_ITEMS_OUTPUT_TREE = registerItemTag("analyzer_items_output_tree");
    public static final TagKey<Item> DNA_FLASKS = registerItemTag("dna_flasks");
    public static final TagKey<Item> FILLED_FLASKS = registerItemTag( "filled_flasks");
    public static final TagKey<Item> FOSSILS = registerItemTag("fossils");
    public static final TagKey<Item> ORGANIC_OOZE = registerItemTag("organic_ooze");
    public static final TagKey<Item> MAMMOTH_WEAPONS = registerItemTag("mammoth_weapons");
    //Food Tags
    public static final TagKey<Item> KENTRO_FOOD = registerItemTag( "kentro_food");
    public static final TagKey<Item> MAJUNGA_FOOD = registerItemTag("majunga_food");
    public static final TagKey<Item> YELLOW_ULUGH_FOOD = registerItemTag("yellow_ulugh_food");
    public static final TagKey<Item> BLUE_ULUGH_FOOD = registerItemTag( "blue_ulugh_food");
    public static final TagKey<Item> ORANGE_ULUGH_FOOD = registerItemTag( "orange_ulugh_food");
    public static final TagKey<Item> WHITE_ULUGH_FOOD = registerItemTag("white_ulugh_food");
    public static final TagKey<Item> BROWN_ULUGH_FOOD = registerItemTag("brown_ulugh_food");

    public static final TagKey<Item> TRIKE_FOOD = registerItemTag("trike_food");
    public static final TagKey<Item> HWACHA_FOOD = registerItemTag("hwacha_food");

    public static final TagKey<Item> PACHY_FOOD = registerItemTag("pachy_food");
    public static final TagKey<Item> PETRIFIED_WOOD = registerItemTag("petrified_wood");
    public static final TagKey<Item> FOXXI = registerItemTag("foxxi");
    public static final TagKey<Item> DRYO = registerItemTag("dryo");


    //Breakables Tags
    public static final TagKey<Block> CLUB_WHITELIST_BLOCKS = registerBlockTag("club_whitelist_blocks");
    public static final TagKey<Block> ZULOAGAE_PLANTABLE_ON = registerBlockTag("zuloagae_plantable_on");
    public static final TagKey<Block> TRIKE_BREAKABLES = registerBlockTag("trike_breakables");
    public static final TagKey<Block> TAR_PIT_REPLACEABLE = registerBlockTag("tar_pit_replaceable");
    public static final TagKey<Block> PASSIVE_BRACHI_BREAKABLES = registerBlockTag("passive_brachi_breakables");
    public static final TagKey<Block> REX_BREAKABLES = registerBlockTag("rex_breakables");
    public static final TagKey<Block> ANGRY_BRACHI_BREAKABLES = registerBlockTag("angry_brachi_breakables");
    public static final TagKey<Block> ERYON_DIGGABLES = registerBlockTag("eryon_diggables");
    public static final TagKey<Block> TALPANAS_DIGGABLES = registerBlockTag("talpanas_diggables");

    public static final TagKey<Block> DINO_HATCHABLE_BLOCKS = registerBlockTag("dino_hatchable_blocks");

    public static final TagKey<Block> MEGATHERIUM_EATABLES = registerBlockTag("megatherium_breakables");
    public static final TagKey<Block> MEGATHERIUM_MINEABLES = registerBlockTag("megatherium_mineables");

    //DINO SPAWNABLE TAGS

    public static final TagKey<Block> DINO_NATURAL_SPAWNABLE = registerBlockTag("dino_natural_spawnable");


    //BIOME Tags
    public static final TagKey<Biome> IS_PETRIFIED_WOOD_FOREST_BIOME = registerBiomeTag("is_petrified_wood_forest_biome");
    public static final TagKey<Biome> IS_ICE_FOSSIL_ICEBERG_BIOME = registerBiomeTag("is_ice_fossil_iceberg_biome");
    public static final TagKey<Biome> IS_TAR_BIOME = registerBiomeTag("is_tar_biome");
    public static final TagKey<Biome> IS_STETHA_BIOME = registerBiomeTag("is_stetha_biome");
    public static final TagKey<Biome> IS_MAJUNGA_BIOME = registerBiomeTag("is_majunga_biome");
    public static final TagKey<Biome> IS_ANURO_BIOME = registerBiomeTag("is_anuro_biome");
    public static final TagKey<Biome> IS_BEELZ_BIOME = registerBiomeTag("is_beelz_biome");
    public static final TagKey<Biome> IS_AMMON_BIOME = registerBiomeTag("is_ammon_biome");
    public static final TagKey<Biome> IS_DUNK_BIOME = registerBiomeTag("is_dunk_biome");
    public static final TagKey<Biome> IS_COTY_BIOME = registerBiomeTag("is_coty_biome");
    public static final TagKey<Biome> IS_SCAU_BIOME = registerBiomeTag("is_scau_biome");
    public static final TagKey<Biome> IS_TRIKE_BIOME = registerBiomeTag("is_trike_biome");
    public static final TagKey<Biome> IS_PACHY_BIOME = registerBiomeTag("is_pachy_biome");
    public static final TagKey<Biome> IS_BRACHI_BIOME = registerBiomeTag("is_brachi_biome");
    public static final TagKey<Biome> IS_VELOCI_BIOME = registerBiomeTag("is_veloci_biome");
    public static final TagKey<Biome> IS_REX_BIOME = registerBiomeTag("is_rex_biome");
    public static final TagKey<Biome> IS_ERYON_BIOME = registerBiomeTag("is_eryon_biome");
    public static final TagKey<Biome> IS_AUSTRO_BIOME = registerBiomeTag("is_austro_biome");
    public static final TagKey<Biome> IS_ANTARCTO_BIOME = registerBiomeTag("is_antarcto_biome");
    public static final TagKey<Biome> IS_ULUG_BIOME = registerBiomeTag("is_ulug_biome");
    public static final TagKey<Biome> IS_KENTRO_BIOME = registerBiomeTag("is_kentro_biome");
    public static final TagKey<Biome> IS_HWACHA_BIOME = registerBiomeTag("is_hwacha_biome");
    public static final TagKey<Biome> IS_ENCRUSTED_BIOME = registerBiomeTag("is_encrusted_biome");
    public static final TagKey<Biome> IS_MAMMOTH_BIOME = registerBiomeTag("is_mammoth_biome");
    public static final TagKey<Biome> IS_PALAEO_BIOME = registerBiomeTag("is_palaeo_biome");
    public static final TagKey<Biome> IS_MEGATHERIUM_BIOME = registerBiomeTag("is_megatherium_biome");
    public static final TagKey<Biome> IS_SMILODON_BIOME = registerBiomeTag("is_smilodon_biome");
    public static final TagKey<Biome> IS_TALPANAS_BIOME = registerBiomeTag("is_talpanas_biome");
    public static final TagKey<Biome> IS_PARACER_BIOME = registerBiomeTag("is_paracer_biome");
    public static final TagKey<Biome> IS_MEGALANIA_BIOME = registerBiomeTag("is_megalania_biome");
    public static final TagKey<Biome> IS_BARINA_BIOME = registerBiomeTag("is_barina_biome");
    public static final TagKey<Biome> IS_GIGANTO_BIOME = registerBiomeTag("is_giganto_biome");

    private static TagKey<Item> registerItemTag(String name) {
        return TagKey.create(Registries.ITEM, new ResourceLocation(UnusualPrehistory.MODID, name));
    }

    private static TagKey<Block> registerBlockTag(String name) {
        return TagKey.create(Registries.BLOCK, new ResourceLocation(UnusualPrehistory.MODID, name));
    }

    private static TagKey<EntityType<?>> registerEntityTag(String name) {
        return TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation(UnusualPrehistory.MODID, name));
    }

    private static TagKey<Instrument> registerInstrument(String name) {
        return TagKey.create(Registries.INSTRUMENT, new ResourceLocation(UnusualPrehistory.MODID, name));
    }

    private static TagKey<Biome> registerBiomeTag(String name){
        return TagKey.create(Registries.BIOME, new ResourceLocation(UnusualPrehistory.MODID, name));

    }
}
