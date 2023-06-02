package com.peeko32213.unusualprehistory.common.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Registry;
import net.minecraft.world.item.Item;

/**
 * Represents a pair consisting of an item and its associated weight.
 */
public class ItemWeightedPair {
    /**
     * The Codec instance for serializing/deserializing ItemWeightedPair objects.
     */
    public static Codec<ItemWeightedPair> CODEC = RecordCodecBuilder.create(inst -> inst
            .group(
                    Registry.ITEM.byNameCodec().fieldOf("item").forGetter(ItemWeightedPair::getItem),
                    Codec.INT.fieldOf("weight").forGetter(ItemWeightedPair::getWeight)
            ).apply(inst, ItemWeightedPair::new)
    );

    private Item item;
    private int weight;

    /**
     * Constructs a new ItemWeightedPair with the specified item and weight.
     *
     * @param item   The item.
     * @param weight The weight associated with the item.
     */
    public ItemWeightedPair(Item item, int weight) {
        this.item = item;
        this.weight = weight;
    }

    /**
     * Gets the weight associated with the item.
     *
     * @return The weight.
     */
    public int getWeight() {
        return weight;
    }

    /**
     * Gets the item.
     *
     * @return The item.
     */
    public Item getItem() {
        return item;
    }
}

