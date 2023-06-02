package com.peeko32213.unusualprehistory.common.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import java.util.Collections;
import java.util.List;

public class AnalyzerRecipeCodec {

    public static Codec<AnalyzerRecipeCodec> CODEC = RecordCodecBuilder.create(inst -> inst
                    .group(
                            Registry.ITEM.byNameCodec().fieldOf("input").forGetter(i -> i.item),
                            ItemWeightedPair.CODEC.listOf().optionalFieldOf("entries", Collections.emptyList()).forGetter(e -> e.itemWeightedPairs)
    ).apply(inst, AnalyzerRecipeCodec::new)
    );


    protected Item item;
    protected List<ItemWeightedPair> itemWeightedPairs;

    public AnalyzerRecipeCodec(Item item, List<ItemWeightedPair> weight){
        this.item = item;
        this.itemWeightedPairs = weight;
    }


    public List<ItemWeightedPair> getItemWeightedPairs() {
        return itemWeightedPairs;
    }

    public Item getItem() {
        return item;
    }

    public void addToList(List<ItemWeightedPair> itemWeightedPairs){
        this.itemWeightedPairs.addAll(itemWeightedPairs);
    }

    public int getTotalWeight(){
        int totalWeight = 0;
        for(ItemWeightedPair itemWeightedPair : getItemWeightedPairs()){
            totalWeight+=itemWeightedPair.getWeight();
        }
        return totalWeight;
    }
}
