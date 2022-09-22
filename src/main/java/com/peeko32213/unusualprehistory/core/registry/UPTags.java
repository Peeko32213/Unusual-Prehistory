package com.peeko32213.unusualprehistory.core.registry;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Block;

public class UPTags {


        public static final TagKey<Block> ANURO_PERCH_BLOCKS = tag("anuro_perch_blocks");
        public static final TagKey<EntityType<?>> ANURO_TARGETS = registerEntityTag("anuro_heal_targets");

    private static TagKey<Block> tag(String name) {
        return TagKey.create(Registry.BLOCK_REGISTRY, new ResourceLocation(UnusualPrehistory.MODID, name));
    }
        private static TagKey<EntityType<?>> registerEntityTag(String name) {
            return TagKey.create(Registry.ENTITY_TYPE_REGISTRY, new ResourceLocation(UnusualPrehistory.MODID, name));
        }

}
