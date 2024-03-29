package com.peeko32213.unusualprehistory.common.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.item.Item;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LootFruitCodec {

    public static Codec<LootFruitCodec> CODEC = RecordCodecBuilder.create(inst -> inst
            .group(
                    Codec.INT.fieldOf("tier").forGetter(t -> t.tier),
                    Codec.STRING.fieldOf("translation_key").forGetter(t -> t.translationKey),
                    Registry.ITEM.byNameCodec().fieldOf("trade_item").forGetter(t -> t.tradeItem),
                    RollableItemCodec.CODEC.listOf().fieldOf("items").forGetter(i -> i.items),
                    TextColor.CODEC.fieldOf("color").forGetter(c -> c.color),
                    Codec.INT.fieldOf("CustomModelData").forGetter(m -> m.customModelData)
            ).apply(inst, LootFruitCodec::new)
    );

    private final int tier;
    private final String translationKey;
    private final List<RollableItemCodec> items;
    private final Item tradeItem;
    private final TextColor color;
    private final int customModelData;
    public LootFruitCodec(int tier, String translationKey, Item tradeItem, List<RollableItemCodec> items, TextColor color, int customModelData) {
        this.tier = tier;
        this.translationKey = translationKey;
        this.items = items;
        this.color = color;
        this.tradeItem = tradeItem;
        this.customModelData = customModelData;
    }


    public int getTier() {
        return tier;
    }

    public Item getTradeItem() {
        return tradeItem;
    }

    public List<RollableItemCodec> getItems() {
        return items;
    }

    public TextColor getColor() {
        return color;
    }

    public String getTranslationKey() {
        return translationKey;
    }

    public int getCustomModelData() {
        return customModelData;
    }

    public static <T> Map<T, List<LootFruitCodec>> convertToMap(Map<T, List<LootFruitCodec>> map) {
        return map.entrySet().stream().collect(Collectors.toMap(
                Map.Entry::getKey,
                entry -> entry.getValue().stream()
                        .map(lootFruitCodec -> new LootFruitCodec(
                                lootFruitCodec.getTier(),
                                lootFruitCodec.getTranslationKey(),
                                lootFruitCodec.getTradeItem(),
                                lootFruitCodec.getItems(),
                                lootFruitCodec.getColor(),
                                lootFruitCodec.getCustomModelData()
                        ))
                        .collect(Collectors.toList())
        ));
    }

    public static <T> Map<T, List<LootFruitCodec>> convertFromMap(Map<T, List<LootFruitCodec>> map) {
        return map.entrySet().stream().collect(Collectors.toMap(
                Map.Entry::getKey,
                entry -> entry.getValue().stream()
                        .map(lootFruit -> new LootFruitCodec(
                                lootFruit.getTier(),
                                lootFruit.getTranslationKey(),
                                lootFruit.getTradeItem(),
                                lootFruit.getItems(),
                                lootFruit.getColor(),
                                lootFruit.getCustomModelData()
                        ))
                        .collect(Collectors.toList())
        ));
    }
}


