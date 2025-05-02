package com.peeko32213.unusualprehistory.core.other.tags;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class UPItemTags {
    public static final TagKey<Item> NONE_ITEM_TAG = itemTag("none_item_tag");
    public static final TagKey<Item> DUNK_FOOD_PACIFY = itemTag("pacifies_dunkleosteus");
    public static final TagKey<Item> DUNK_FOOD = itemTag("dunkleosteus_food");

    public static final TagKey<Item> MEGATHERIUM_FOOD = itemTag("megatherium_food");
    public static final TagKey<Item> EDAPHO_FOOD_ITEMS = itemTag("edaphosaurus_food_items");
    public static final TagKey<Item> ESTEMME_FOOD_ITEMS = itemTag("estemmenosuchus_food_items");
    public static final TagKey<Item> UNICORN_FOOD_ITEMS = itemTag("unicorn_food_items");

    public static final TagKey<Item> ALLOWED_FRIDGE_ITEMS = itemTag("allowed_fridge_items");
    public static final TagKey<Item> ANALYZER_ITEMS_INPUT = itemTag("analyzer_items");

    public static final TagKey<Item> DNA_BOTTLES = itemTag("dna_bottles");
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

    public static final TagKey<Item> TELECREX_FOOD = itemTag("telecrex_food");

    public static final TagKey<Item> HIDDEN_ITEMS = itemTag("hidden_items");

    private static TagKey<Item> itemTag(String name) {
        return TagKey.create(Registries.ITEM, new ResourceLocation(UnusualPrehistory.MODID, name));
    }
}
