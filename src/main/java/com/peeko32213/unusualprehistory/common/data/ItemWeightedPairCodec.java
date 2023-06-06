package com.peeko32213.unusualprehistory.common.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Registry;
import net.minecraft.world.item.Item;

/**
 * Represents a pair consisting of an item and its associated weight.
 */
public class ItemWeightedPairCodec {
    /**
     * The Codec instance for serializing/deserializing ItemWeightedPair objects.
     */
    public static Codec<ItemWeightedPairCodec> CODEC = RecordCodecBuilder.create(inst -> inst
            .group(
                    Registry.ITEM.byNameCodec().fieldOf("item").forGetter(i -> i.item),
                    Codec.INT.fieldOf("weight").forGetter(w -> w.weight),
                    Codec.INT.optionalFieldOf("amount", 1).forGetter(a -> a.amount)

            ).apply(inst, ItemWeightedPairCodec::new)
    );

    private Item item;
    private int weight;
    private int amount;

    /**
     * Constructs a new ItemWeightedPair with the specified item and weight.
     *
     * @param item   The item.
     * @param weight The weight associated with the item.
     */
    public ItemWeightedPairCodec(Item item, int weight, int amount) {
        this.item = item;
        this.weight = weight;
        this.amount = amount;
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

    public int getAmount() {
        return amount;
    }


}

