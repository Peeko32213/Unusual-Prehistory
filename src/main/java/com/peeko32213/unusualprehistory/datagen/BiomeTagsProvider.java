package com.peeko32213.unusualprehistory.datagen;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.core.registry.UPTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.level.biome.Biomes;
import net.minecraftforge.common.data.ExistingFileHelper;

public class BiomeTagsProvider extends net.minecraft.data.tags.BiomeTagsProvider {
    public BiomeTagsProvider(DataGenerator pGenerator, ExistingFileHelper existingFileHelper) {
        super(pGenerator, UnusualPrehistory.MODID, existingFileHelper);
    }

    protected void addTags() {
        tag(UPTags.IS_PETRIFIED_WOOD_FOREST_BIOME).add(Biomes.BADLANDS).add(Biomes.WOODED_BADLANDS).add(Biomes.ERODED_BADLANDS).add(Biomes.DESERT);
    }
}
