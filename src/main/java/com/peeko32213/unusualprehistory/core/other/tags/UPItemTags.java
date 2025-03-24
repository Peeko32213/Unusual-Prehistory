package com.peeko32213.unusualprehistory.core.other.tags;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.teamabnormals.blueprint.core.util.TagUtil;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class UPItemTags {
    public static final TagKey<Item> NONE_ITEM_TAG = itemTag("none_item_tag");

    public static final TagKey<Item> EDAPHO_FOOD_ITEMS = itemTag("edaphosaurus_food_items");
    public static final TagKey<Item> ESTEMME_FOOD_ITEMS = itemTag("estemmenosuchus_food_items");

    public static final TagKey<Item> ALLOWED_FRIDGE_ITEMS = itemTag("allowed_fridge_items");
    public static final TagKey<Item> ANALYZER_ITEMS_INPUT = itemTag("analyzer_items");

    public static final TagKey<Item> ANALYZER_ITEMS_OUTPUT_MESOZOIC = itemTag("analyzer_items_output_mezo_fossil");
    public static final TagKey<Item> ANALYZER_ITEMS_OUTPUT_PALEO = itemTag("analyzer_items_output_paleo_fossil");
    public static final TagKey<Item> ANALYZER_ITEMS_OUTPUT_AMBER = itemTag("analyzer_items_output_amber_fossil");
    public static final TagKey<Item> ANALYZER_ITEMS_OUTPUT_ENCRUSTED= itemTag("analyzer_items_output_encrusted_organ");
    public static final TagKey<Item> ANALYZER_ITEMS_OUTPUT_GINKGO = itemTag("analyzer_items_output_ginkgo_fruit");
    public static final TagKey<Item> ANALYZER_ITEMS_OUTPUT_PETRIFIED = itemTag("analyzer_items_output_petrified");

    public static final TagKey<Item> ANALYZER_ITEMS_OUTPUT_PLANT = itemTag("analyzer_items_output_plant_fossil");
    public static final TagKey<Item> ANALYZER_ITEMS_OUTPUT_TAR = itemTag("analyzer_items_output_tar_fossil");
    public static final TagKey<Item> ANALYZER_ITEMS_OUTPUT_OPAL = itemTag("analyzer_items_output_opal_fossil");
    public static final TagKey<Item> ANALYZER_ITEMS_OUTPUT_FROZEN = itemTag("analyzer_items_output_frozen_fossil");

    public static final TagKey<Item> ANALYZER_ITEMS_OUTPUT_RAW_COTY = itemTag("analyzer_items_output_raw_coty");
    public static final TagKey<Item> ANALYZER_ITEMS_OUTPUT_TREE = itemTag("analyzer_items_output_tree");
    public static final TagKey<Item> DNA_FLASKS = itemTag("dna_flasks");
    public static final TagKey<Item> FILLED_FLASKS = itemTag( "filled_flasks");
    public static final TagKey<Item> FOSSILS = itemTag("fossils");
    public static final TagKey<Item> ORGANIC_OOZE = itemTag("organic_ooze");

    public static final TagKey<Item> KENTRO_FOOD = itemTag( "kentro_food");
    public static final TagKey<Item> MAJUNGA_FOOD = itemTag("majunga_food");
    public static final TagKey<Item> ULUGH_FOOD = itemTag("ulugh_food");

    public static final TagKey<Item> TRICERATOPS_FOOD = itemTag("triceratops_food");
    public static final TagKey<Item> TRICERATOPS_TAMES = itemTag("triceratops_tames");

    public static final TagKey<Item> HWACHA_FOOD = itemTag("hwacha_food");
    public static final TagKey<Item> HWACHA_TAMES = itemTag("hwacha_food");

    public static final TagKey<Item> BARINA_FOOD = itemTag("barinasuchus_food");

    public static final TagKey<Item> COTY_FOOD = itemTag("cotylorhynchus_food");
    public static final TagKey<Item> COTY_FERMENTERS = itemTag("cotylorhynchus_fermenting");

    public static final TagKey<Item> PACHY_FOOD = itemTag("pachy_food");
    public static final TagKey<Item> PETRIFIED_WOOD = itemTag("petrified_wood");
    public static final TagKey<Item> FOXXI = itemTag("foxxi");
    public static final TagKey<Item> DRYO = itemTag("dryo");
    public static final TagKey<Item> ZULOAGAE = itemTag("zuloagae");

    public static final TagKey<Item> HYNERPETON_IGNITERS = itemTag("hynerpeton_igniters");

    public static final TagKey<Item> OPAL_GEMS = itemTag("opal_gems");

    private static TagKey<Item> itemTag(String name) {
        return TagUtil.itemTag(UnusualPrehistory.MODID, name);
    }
}
