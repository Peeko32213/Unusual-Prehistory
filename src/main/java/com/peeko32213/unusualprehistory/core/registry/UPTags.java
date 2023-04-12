package com.peeko32213.unusualprehistory.core.registry;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;

public class UPTags {


    public static final TagKey<EntityType<?>> ANURO_EGG_TRAMPLERS = registerEntityTag("anuro_egg_tramplers");
    public static final TagKey<EntityType<?>> BRACHI_EGG_TRAMPLERS = registerEntityTag("brachi_egg_tramplers");
    //public static final TagKey<EntityType<?>> ANURO_EGG_TRAMPLERS = registerEntityTag("anuro_egg_tramplers");
    //public static final TagKey<EntityType<?>> ANURO_EGG_TRAMPLERS = registerEntityTag("anuro_egg_tramplers");
    //public static final TagKey<EntityType<?>> ANURO_EGG_TRAMPLERS = registerEntityTag("anuro_egg_tramplers");
    //Target tags
    public static final TagKey<EntityType<?>> ANURO_TARGETS = registerEntityTag("anuro_targets");

    public static final TagKey<EntityType<?>> MAJUNGA_TARGETS = registerEntityTag("majunga_targets");
    public static final TagKey<EntityType<?>> BEELZE_TARGETS = registerEntityTag("beelze_targets");
    public static final TagKey<EntityType<?>> REX_TARGETS = registerEntityTag("rex_targets");
    public static final TagKey<EntityType<?>> RAPTOR_TARGETS = registerEntityTag("raptor_targets");
    public static final TagKey<EntityType<?>> ANTARCTO_TARGETS = registerEntityTag("antarcto_targets");
    public static final TagKey<EntityType<?>> ENCRUSTED_TARGETS = registerEntityTag("encrusted_targets");
    public static final TagKey<EntityType<?>> DUNK_TARGETS = registerEntityTag("dunk_targets");
    public static final TagKey<EntityType<?>> LAND_MOBS = registerEntityTag("land_mobs");
    //Misc Tags
    public static final TagKey<Item> ALLOWED_FRIDGE_ITEMS = registerItemTag("allowed_fridge_items");
    public static final TagKey<Item> ANALYZER_ITEMS_INPUT = registerItemTag("analyzer_items");
    public static final TagKey<Item> DNA_FLASKS = registerItemTag("dna_flasks");
    public static final TagKey<Item> FILLED_FLASKS = registerItemTag( "filled_flasks");
    public static final TagKey<Item> FOSSILS = registerItemTag("fossils");
    public static final TagKey<Item> ORGANIC_OOZE = registerItemTag("organic_ooze");

    //Food Tags
    public static final TagKey<Item> KENTRO_FOOD = registerItemTag( "kentro_food");
    public static final TagKey<Item> MAJUNGA_FOOD = registerItemTag("majunga_food");
    public static final TagKey<Item> YELLOW_ULUGH_FOOD = registerItemTag("yellow_ulugh_food");
    public static final TagKey<Item> BLUE_ULUGH_FOOD = registerItemTag( "blue_ulugh_food");
    public static final TagKey<Item> ORANGE_ULUGH_FOOD = registerItemTag( "orange_ulugh_food");
    public static final TagKey<Item> WHITE_ULUGH_FOOD = registerItemTag("white_ulugh_food");
    public static final TagKey<Item> TRIKE_FOOD = registerItemTag("trike_food");
    public static final TagKey<Item> HWACHA_FOOD = registerItemTag("hwacha_food");

    public static final TagKey<Item> PACHY_FOOD = registerItemTag("pachy_food");
    public static final TagKey<Item> PETRIFIED_WOOD = registerItemTag("petrified_wood");


    //Breakables Tags
    public static final TagKey<Block> TRIKE_BREAKABLES = registerBlockTag("trike_breakables");
    public static final TagKey<Block> PASSIVE_BRACHI_BREAKABLES = registerBlockTag("passive_brachi_breakables");
    public static final TagKey<Block> REX_BREAKABLES = registerBlockTag("rex_breakables");
    public static final TagKey<Block> ANGRY_BRACHI_BREAKABLES = registerBlockTag("angry_brachi_breakables");
    public static final TagKey<Block> ERYON_DIGGABLES = registerBlockTag("eryon_diggables");



    //BIOME Tags
    public static final TagKey<Biome> IS_PETRIFIED_WOOD_FOREST_BIOME = registerBiomeTag("is_petrified_wood_forest_biome");



    private static TagKey<Item> registerItemTag(String name) {
        return TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(UnusualPrehistory.MODID, name));
    }

    private static TagKey<Block> registerBlockTag(String name) {
        return TagKey.create(Registry.BLOCK_REGISTRY, new ResourceLocation(UnusualPrehistory.MODID, name));
    }

    private static TagKey<EntityType<?>> registerEntityTag(String name) {
        return TagKey.create(Registry.ENTITY_TYPE_REGISTRY, new ResourceLocation(UnusualPrehistory.MODID, name));
    }

    private static TagKey<Biome> registerBiomeTag(String name){
        return TagKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(UnusualPrehistory.MODID, name));

    }
}
