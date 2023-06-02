package com.peeko32213.unusualprehistory.common.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Registry;
import net.minecraft.world.item.Item;

public class ItemWeightedPair {
    public static Codec<ItemWeightedPair> CODEC = RecordCodecBuilder.create(inst -> inst
            .group(
                    Registry.ITEM.byNameCodec().fieldOf("item").forGetter(i -> i.item),
                    Codec.INT.fieldOf("weight").forGetter(w -> w.weight)
            ).apply(inst, ItemWeightedPair::new)
    );


    protected Item item;
    protected int weight;

    public ItemWeightedPair(Item item, int weight){
        this.item = item;
        this.weight = weight;
    }

    public int getWeight(){
        return this.weight;
    }

    public Item getItem(){
        return this.getItem();
    }
}
