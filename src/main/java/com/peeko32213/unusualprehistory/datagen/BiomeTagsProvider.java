package com.peeko32213.unusualprehistory.datagen;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.core.registry.UPTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

public class BiomeTagsProvider extends net.minecraft.data.tags.BiomeTagsProvider {
    public BiomeTagsProvider(DataGenerator pGenerator, ExistingFileHelper existingFileHelper) {
        super(pGenerator, UnusualPrehistory.MODID, existingFileHelper);
    }

    protected void addTags() {
        tag(UPTags.IS_PETRIFIED_WOOD_FOREST_BIOME).add(Biomes.BADLANDS).add(Biomes.WOODED_BADLANDS).add(Biomes.ERODED_BADLANDS).add(Biomes.DESERT);
        tag(UPTags.IS_STETHA_BIOME).add(Biomes.LUKEWARM_OCEAN);
        tag(UPTags.IS_MAJUNGA_BIOME).addTag(BiomeTags.IS_SAVANNA).addTag(BiomeTags.IS_JUNGLE);
        tag(UPTags.IS_ANURO_BIOME).add(Biomes.FLOWER_FOREST).addTag(BiomeTags.IS_SAVANNA);
        tag(UPTags.IS_BEELZ_BIOME).addTag(Tags.Biomes.IS_SWAMP);
        tag(UPTags.IS_AMMON_BIOME).addTag(BiomeTags.IS_OCEAN);
        tag(UPTags.IS_DUNK_BIOME).add(Biomes.LUKEWARM_OCEAN);
        tag(UPTags.IS_COTY_BIOME).addTag(BiomeTags.IS_BADLANDS);
        tag(UPTags.IS_SCAU_BIOME).add(Biomes.LUKEWARM_OCEAN);
        tag(UPTags.IS_TRIKE_BIOME).addTag(BiomeTags.IS_TAIGA);
        tag(UPTags.IS_PACHY_BIOME).addTag(BiomeTags.IS_TAIGA);
        tag(UPTags.IS_BRACHI_BIOME).addTag(BiomeTags.IS_SAVANNA);
        tag(UPTags.IS_VELOCI_BIOME).add(Biomes.DESERT).addTag(BiomeTags.IS_BADLANDS);
        tag(UPTags.IS_REX_BIOME).addTag(BiomeTags.IS_TAIGA);
        tag(UPTags.IS_ERYON_BIOME).add(Biomes.MANGROVE_SWAMP);
        tag(UPTags.IS_AUSTRO_BIOME).addTag(Tags.Biomes.IS_SWAMP).addTags(BiomeTags.IS_RIVER);
        tag(UPTags.IS_ANTARCTO_BIOME).add(Biomes.OLD_GROWTH_BIRCH_FOREST).add(Biomes.OLD_GROWTH_PINE_TAIGA).add(Biomes.OLD_GROWTH_SPRUCE_TAIGA);
        tag(UPTags.IS_ULUG_BIOME).addTags(BiomeTags.IS_BEACH).addTags(BiomeTags.IS_RIVER);
        tag(UPTags.IS_KENTRO_BIOME).addTag(BiomeTags.IS_JUNGLE).addTag(Tags.Biomes.IS_SWAMP).addTags(BiomeTags.IS_FOREST);
        tag(UPTags.IS_HWACHA_BIOME).addTag(Tags.Biomes.IS_SWAMP);
        tag(UPTags.IS_ENCRUSTED_BIOME).addTags(Tags.Biomes.IS_UNDERGROUND);
    }
}
