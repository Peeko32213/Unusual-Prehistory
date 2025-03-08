package com.peeko32213.unusualprehistory.datagen.tags;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.core.registry.UPTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biomes;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class BiomeTagsProvider extends net.minecraft.data.tags.BiomeTagsProvider {
    public BiomeTagsProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pPro,ExistingFileHelper existingFileHelper) {
        super(pOutput,pPro ,UnusualPrehistory.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider pProvider) {
        this.addTags();
    }

    protected void addTags() {

        tag(UPTags.IS_FOSSIL_STRUCTURES_BIOME)
                .addTag(BiomeTags.IS_BADLANDS)
                .addTag(Tags.Biomes.IS_SWAMP)
                .addTag(Tags.Biomes.IS_DESERT)
        ;

        tag(UPTags.IS_PETRIFIED_WOOD_FOREST_BIOME)
                .addTag(BiomeTags.IS_BADLANDS)
                .addTag(Tags.Biomes.IS_DESERT)
        ;

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
        tag(UPTags.IS_ENCRUSTED_BIOME).addTag(Tags.Biomes.IS_UNDERGROUND);
        tag(UPTags.IS_HWACHA_BIOME).addTag(Tags.Biomes.IS_SWAMP);
        tag(UPTags.IS_MAMMOTH_BIOME).addTags(Tags.Biomes.IS_PLAINS).addTag(Tags.Biomes.IS_COLD);
        tag(UPTags.IS_PALAEO_BIOME).add(Biomes.WARM_OCEAN);
        tag(UPTags.IS_MEGATHERIUM_BIOME).add(Biomes.SAVANNA);
        tag(UPTags.IS_SMILODON_BIOME).addTag(Tags.Biomes.IS_COLD).addTag(Tags.Biomes.IS_MOUNTAIN);
        tag(UPTags.IS_TALPANAS_BIOME).add(Biomes.LUSH_CAVES);
        tag(UPTags.IS_PARACER_BIOME).add(Biomes.SAVANNA);
        tag(UPTags.IS_MEGALANIA_BIOME).addTags(Tags.Biomes.IS_HOT_OVERWORLD);
        tag(UPTags.IS_BARINA_BIOME).add(Biomes.SPARSE_JUNGLE);
        tag(UPTags.IS_GIGANTO_BIOME).add(Biomes.BAMBOO_JUNGLE);


        tag(UPTags.IS_ICE_FOSSIL_ICEBERG_BIOME)
                .add(Biomes.FROZEN_OCEAN)
                .add(Biomes.DEEP_FROZEN_OCEAN)
                .add(Biomes.FROZEN_RIVER);

        tag(UPTags.IS_TAR_BIOME)
                .add(Biomes.SWAMP)
                .add(Biomes.SAVANNA)
                .add(Biomes.SAVANNA_PLATEAU)
                .add(Biomes.PLAINS)
                .add(Biomes.SNOWY_PLAINS)
                .add(Biomes.DESERT);
    }
}
