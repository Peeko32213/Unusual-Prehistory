package com.peeko32213.unusualprehistory.core.registry;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class UPTags {

    public static class BlockTags {
        public static final TagKey<Block> ANURO_PERCH_BLOCKS = tag("anuro_perch_blocks");
        private static TagKey<Block> tag(String name) {
            return TagKey.create(Registry.BLOCK_REGISTRY, new ResourceLocation(UnusualPrehistory.MODID, name));
        }
    }

}
