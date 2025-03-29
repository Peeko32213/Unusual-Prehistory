package com.peeko32213.unusualprehistory.data.server.tags;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.core.other.tags.UPBiomeTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biomes;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class UPBiomeTagsProvider extends net.minecraft.data.tags.BiomeTagsProvider {

    public UPBiomeTagsProvider(PackOutput output, CompletableFuture<Provider> provider, ExistingFileHelper helper) {
        super(output, provider, UnusualPrehistory.MODID, helper);
    }

    @Override
    public void addTags(HolderLookup.Provider provider) {

        // Feature
        tag(UPBiomeTags.HAS_GIANT_FOSSILS).addTag(BiomeTags.IS_OVERWORLD);
        tag(UPBiomeTags.IS_PETRIFIED_WOOD_FOREST_BIOME).addTag(BiomeTags.IS_BADLANDS).addTag(Tags.Biomes.IS_DESERT);
        tag(UPBiomeTags.IS_ICE_FOSSIL_ICEBERG_BIOME).add(Biomes.FROZEN_OCEAN).add(Biomes.DEEP_FROZEN_OCEAN);
        tag(UPBiomeTags.IS_TAR_BIOME).addTag(BiomeTags.IS_BADLANDS);

        // Structure
        tag(UPBiomeTags.HAS_FOSSIL_SKELETONS).addTag(BiomeTags.IS_OVERWORLD);
        tag(UPBiomeTags.HAS_UNDERGROUND_DIG_SITES).addTag(BiomeTags.IS_OVERWORLD);

    }
}
