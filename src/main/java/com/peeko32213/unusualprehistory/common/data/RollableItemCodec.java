package com.peeko32213.unusualprehistory.common.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class RollableItemCodec {

    public static Codec<RollableItemCodec> CODEC = RecordCodecBuilder.create(inst -> inst
            .group(
                    Codec.INT.optionalFieldOf("rolls", 1).forGetter(r -> r.rolls),
                    ItemWeightedPairCodec.CODEC.listOf().fieldOf("entries").forGetter(e -> e.entries)
            ).apply(inst, RollableItemCodec::new)
    );

    private int rolls;
    private List<ItemWeightedPairCodec> entries;

    /**
     * Constructs a new ItemWeightedPair with the specified item and weight.
     *
     * @param rolls   The amount of times the items get rolled.
     * @param entries The entries of items.
     */
    public RollableItemCodec(int rolls, List<ItemWeightedPairCodec> entries) {
        this.rolls = rolls;
        this.entries = entries;

    }

    public int getRolls() {
        return rolls;
    }

    public List<ItemWeightedPairCodec> getEntries() {
        return entries;
    }

    public Map<ItemStack, Integer> getRandomItem(RandomSource random, List<ItemWeightedPairCodec> entries){
        List<ItemWeightedPairCodec> outputs = entries;
        Item outputItem = null;
        int totalWeight = 0;
        for (ItemWeightedPairCodec itemWeightedPair : outputs) {
            totalWeight += itemWeightedPair.getWeight();
        }

        int randomNr = random.nextInt(totalWeight);
        int cumulativeWeight = 0;
        int outputCount = 0;
        for (ItemWeightedPairCodec itemWeightedPair : outputs) {
            cumulativeWeight += itemWeightedPair.getWeight();
            if (randomNr < cumulativeWeight) {
                outputItem = itemWeightedPair.getItem();
                outputCount = itemWeightedPair.getAmount();
                break;
            }
        }
        HashMap outPut = new HashMap();

        ItemStack outputStack = new ItemStack(outputItem);
        if (outputStack == null) {
            HashMap failMap = new HashMap();
            failMap.put(ItemStack.EMPTY, 1);
            return failMap;
        }

        outPut.put(outputStack, outputCount);

        return outPut;
    }

    public void dropItem(Level level, BlockPos pos, RollableItemCodec rollableItemCodec){
        for(int i = 0; i < rollableItemCodec.getRolls(); i++){
            Map<ItemStack, Integer> itemCount = getRandomItem(level.random, rollableItemCodec.getEntries());
            if(!itemCount.isEmpty())
            {
             for(Map.Entry<ItemStack, Integer> itemEntry : itemCount.entrySet()){
                 for(int count = 0; count < itemEntry.getValue(); count++){
                     ItemEntity itemEntity = new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), itemEntry.getKey());
                     level.addFreshEntity(itemEntity);
                 }
             }
            }
        }
    }
}
