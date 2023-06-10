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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A codec for rollable items.
 */
public class RollableItemCodec {

    /**
     * The codec for RollableItemCodec.
     */
    public static Codec<RollableItemCodec> CODEC = RecordCodecBuilder.create(inst -> inst
            .group(
                    Codec.INT.optionalFieldOf("rolls", 1).forGetter(r -> r.rolls),
                    ItemWeightedPairCodec.CODEC.listOf().fieldOf("entries").forGetter(e -> e.entries)
            ).apply(inst, RollableItemCodec::new)
    );

    private int rolls;
    private List<ItemWeightedPairCodec> entries;

    /**
     * Constructs a new RollableItemCodec with the specified rolls and entries.
     *
     * @param rolls   The amount of times the items get rolled.
     * @param entries The entries of items.
     */
    public RollableItemCodec(int rolls, List<ItemWeightedPairCodec> entries) {
        this.rolls = rolls;
        this.entries = entries;
    }

    /**
     * Gets the number of rolls for this RollableItemCodec.
     *
     * @return The number of rolls.
     */
    public int getRolls() {
        return rolls;
    }

    /**
     * Gets the entries of items for this RollableItemCodec.
     *
     * @return The entries of items.
     */
    public List<ItemWeightedPairCodec> getEntries() {
        return entries;
    }

    /**
     * Gets a random item based on the weighted entries and a random source.
     *
     * @param random  The random source.
     * @param entries The entries of items.
     * @return A map of ItemStack to its count.
     */
    public Map<ItemStack, Integer> getRandomItem(RandomSource random, List<ItemWeightedPairCodec> entries) {
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

    /**
     * Drops items from this RollableItemCodec at the specified position in the level.
     *
     * @param level             The level.
     * @param pos               The position to drop the items.
     * @param rollableItemCodec The RollableItemCodec to drop items from.
     */
    public void dropItem(Level level, BlockPos pos, RollableItemCodec rollableItemCodec) {
        for (int i = 0; i < rollableItemCodec.getRolls(); i++) {
            Map<ItemStack, Integer> itemCount = getRandomItem(level.random, rollableItemCodec.getEntries());
            if (!itemCount.isEmpty()) {
                for (Map.Entry<ItemStack, Integer> itemEntry : itemCount.entrySet()) {
                    for (int count = 0; count < itemEntry.getValue(); count++) {
                        ItemEntity itemEntity = new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), itemEntry.getKey());
                        level.addFreshEntity(itemEntity);
                    }
                }
            }
        }
    }
}
