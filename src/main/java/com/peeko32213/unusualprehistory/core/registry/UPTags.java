package com.peeko32213.unusualprehistory.core.registry;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class UPTags {


    public static final TagKey<EntityType<?>> ANURO_TARGETS = registerEntityTag("anuro_targets");
    public static final TagKey<EntityType<?>> MAJUNGA_TARGETS = registerEntityTag("majunga_targets");
    public static final TagKey<EntityType<?>> DUNK_TARGETS = registerEntityTag("dunk_targets");
    public static final TagKey<EntityType<?>> BEELZE_TARGETS = registerEntityTag("beelze_targets");
    public static final TagKey<EntityType<?>> REX_TARGETS = registerEntityTag("rex_targets");
    public static final TagKey<EntityType<?>> RAPTOR_TARGETS = registerEntityTag("raptor_targets");
    public static final TagKey<EntityType<?>> ENCRUSTED_TARGETS = registerEntityTag("encrusted_targets");

    public static final TagKey<Block> TRIKE_BREAKABLES = registerBlockTag("trike_breakables");
    public static final TagKey<Block> PASSIVE_BRACHI_BREAKABLES = registerBlockTag("passive_brachi_breakables");
    public static final TagKey<Block> ANGRY_BRACHI_BREAKABLES = registerBlockTag("angry_brachi_breakables");

    private static TagKey<Item> registerItemTag(String name) {
        return TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(UnusualPrehistory.MODID, name));
    }

    private static TagKey<Block> registerBlockTag(String name) {
        return TagKey.create(Registry.BLOCK_REGISTRY, new ResourceLocation(UnusualPrehistory.MODID, name));
    }

    private static TagKey<EntityType<?>> registerEntityTag(String name) {
        return TagKey.create(Registry.ENTITY_TYPE_REGISTRY, new ResourceLocation(UnusualPrehistory.MODID, name));
    }

}
