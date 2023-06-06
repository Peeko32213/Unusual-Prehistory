package com.peeko32213.unusualprehistory.common.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.levelgen.structure.TemplateStructurePiece;

import java.util.List;

public class LootFruitCodec {

    public static Codec<LootFruitCodec> CODEC = RecordCodecBuilder.create(inst -> inst
            .group(
                    Codec.INT.fieldOf("tier").forGetter(t -> t.tier),
                    Registry.ITEM.byNameCodec().fieldOf("trade_item").forGetter(t -> t.tradeItem),
                    RollableItemCodec.CODEC.listOf().fieldOf("items").forGetter(i -> i.items),
                    TextColor.CODEC.fieldOf("color").forGetter(c -> c.color)
            ).apply(inst, LootFruitCodec::new)
    );

    private final int tier;
    private final List<RollableItemCodec> items;
    private final Item tradeItem;
    private final TextColor color;
    public LootFruitCodec(int tier, Item tradeItem, List<RollableItemCodec> items, TextColor color) {
        this.tier = tier;
        this.items = items;
        this.color = color;
        this.tradeItem = tradeItem;
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
}
