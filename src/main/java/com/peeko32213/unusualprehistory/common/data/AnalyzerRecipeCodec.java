package com.peeko32213.unusualprehistory.common.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an Analyzer Recipe Codec, which associates a list of input items with a list of weighted pairs.
 */
public class AnalyzerRecipeCodec {
    /**
     * The Codec instance for serializing/deserializing AnalyzerRecipeCodec objects.
     */
    public static Codec<AnalyzerRecipeCodec> CODEC = RecordCodecBuilder.create(inst -> inst
            .group(
                    ExtraCodecs.TAG_OR_ELEMENT_ID.listOf().fieldOf("input").forGetter(i -> i.input),
                    ItemWeightedPairCodec.CODEC.listOf().fieldOf("entries").forGetter(e -> e.itemWeightedPairs)
            ).apply(inst, AnalyzerRecipeCodec::new)
    );

    private List<ItemWeightedPairCodec> itemWeightedPairs;
    private List<ExtraCodecs.TagOrElementLocation> input;
    protected final List<ResourceLocation> spawnItems;
    protected final List<ResourceLocation> spawnItemTags;

    /**
     * Constructs a new AnalyzerRecipeCodec with the specified input items and weighted pairs.
     *
     * @param item              The list of input items.
     * @param itemWeightedPairs The list of weighted pairs.
     */
    public AnalyzerRecipeCodec(List<ExtraCodecs.TagOrElementLocation> inputItems, List<ItemWeightedPairCodec> itemWeightedPairs) {
        List<ResourceLocation> spawnItemTags = new ArrayList<>();
        List<ResourceLocation> spawnItems = new ArrayList<>();
        for(ExtraCodecs.TagOrElementLocation tagOrElementLocation : inputItems){
            if(tagOrElementLocation.tag()){
                spawnItemTags.add(tagOrElementLocation.id());
            } else {
                spawnItems.add(tagOrElementLocation.id());
            }
        }
        this.spawnItems = spawnItems;
        this.spawnItemTags = spawnItemTags;
        this.itemWeightedPairs = itemWeightedPairs;
    }

    /**
     * Gets the list of item-weighted pairs.
     *
     * @return The list of item-weighted pairs.
     */
    public List<ItemWeightedPairCodec> getItemWeightedPairs() {
        return itemWeightedPairs;
    }


    public List<ResourceLocation> getInputItemTags() {
        return spawnItemTags;
    }

    public List<ResourceLocation> getInputItems() {
        return spawnItems;
    }


    /**
     * Adds a list of item-weighted pairs to the existing list.
     *
     * @param itemWeightedPairs The list of item-weighted pairs to add.
     */
    public void addToList(List<ItemWeightedPairCodec> itemWeightedPairs) {
        this.itemWeightedPairs.addAll(itemWeightedPairs);
    }

    /**
     * Calculates and returns the total weight of all item-weighted pairs.
     *
     * @return The total weight.
     */
    public int getTotalWeight() {
        int totalWeight = 0;
        for (ItemWeightedPairCodec itemWeightedPair : getItemWeightedPairs()) {
            totalWeight += itemWeightedPair.getWeight();
        }
        return totalWeight;
    }
}

