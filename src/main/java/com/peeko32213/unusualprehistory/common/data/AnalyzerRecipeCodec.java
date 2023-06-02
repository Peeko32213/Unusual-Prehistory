package com.peeko32213.unusualprehistory.common.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Registry;
import net.minecraft.world.item.Item;

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
                    Registry.ITEM.byNameCodec().listOf().fieldOf("input").forGetter(i -> i.item),
                    ItemWeightedPair.CODEC.listOf().fieldOf("entries").forGetter(e -> e.itemWeightedPairs)
            ).apply(inst, AnalyzerRecipeCodec::new)
    );

    private List<Item> item;
    private List<ItemWeightedPair> itemWeightedPairs;

    /**
     * Constructs a new AnalyzerRecipeCodec with the specified input items and weighted pairs.
     *
     * @param item              The list of input items.
     * @param itemWeightedPairs The list of weighted pairs.
     */
    public AnalyzerRecipeCodec(List<Item> item, List<ItemWeightedPair> itemWeightedPairs) {
        this.item = item;
        this.itemWeightedPairs = itemWeightedPairs;
    }

    /**
     * Gets the list of item-weighted pairs.
     *
     * @return The list of item-weighted pairs.
     */
    public List<ItemWeightedPair> getItemWeightedPairs() {
        return itemWeightedPairs;
    }

    /**
     * Gets the list of input items.
     *
     * @return The list of input items.
     */
    public List<Item> getItem() {
        return item;
    }

    /**
     * Adds a list of item-weighted pairs to the existing list.
     *
     * @param itemWeightedPairs The list of item-weighted pairs to add.
     */
    public void addToList(List<ItemWeightedPair> itemWeightedPairs) {
        this.itemWeightedPairs.addAll(itemWeightedPairs);
    }

    /**
     * Calculates and returns the total weight of all item-weighted pairs.
     *
     * @return The total weight.
     */
    public int getTotalWeight() {
        int totalWeight = 0;
        for (ItemWeightedPair itemWeightedPair : getItemWeightedPairs()) {
            totalWeight += itemWeightedPair.getWeight();
        }
        return totalWeight;
    }
}

