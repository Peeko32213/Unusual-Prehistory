package com.peeko32213.unusualprehistory.common.message;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Codec;
import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.data.AnalyzerRecipeJsonManager;
import com.peeko32213.unusualprehistory.common.data.ItemWeightedPairCodec;
import com.peeko32213.unusualprehistory.common.data.LootFruitCodec;
import com.peeko32213.unusualprehistory.common.data.LootFruitJsonManager;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.Item;
import net.minecraftforge.network.NetworkEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class AnalyzerRecipeS2C {
    private static final Codec<Map<Item, List<ItemWeightedPairCodec>>> MAPPER =
            Codec.unboundedMap(
                    Registry.ITEM.byNameCodec(),
                    ItemWeightedPairCodec.CODEC.listOf()
            ).xmap(ItemWeightedPairCodec::convertToMap, ItemWeightedPairCodec::convertFromMap)
                    .orElse(e -> {UnusualPrehistory.LOGGER.error("Failed to parse Analyzer Entries can't send packet! Due to " + e);},
                            new HashMap<>());;
    public static Map<Item, List<ItemWeightedPairCodec>> SYNCED_DATA = new HashMap<>();
    private final Map<Item, List<ItemWeightedPairCodec>> map;

    public AnalyzerRecipeS2C(Map<Item, List<ItemWeightedPairCodec>> map) {
        this.map = map;
    }

    public void encode(FriendlyByteBuf buffer) {
        CompoundTag encodedTag = (CompoundTag) (MAPPER.encodeStart(NbtOps.INSTANCE, this.map).result().orElse(new CompoundTag()));
        buffer.writeNbt(encodedTag);
    }

    public static AnalyzerRecipeS2C decode(FriendlyByteBuf buffer) {
        CompoundTag receivedTag = buffer.readNbt();
        Map<Item, List<ItemWeightedPairCodec>> decodedMap = MAPPER.parse(NbtOps.INSTANCE, receivedTag).result().orElse(new HashMap<>());
        return new AnalyzerRecipeS2C(decodedMap);
    }

    public void onPacketReceived(Supplier<NetworkEvent.Context> contextGetter) {
        NetworkEvent.Context context = contextGetter.get();
        context.enqueueWork(this::handlePacketOnMainThread);
        context.setPacketHandled(true);
    }

    private void handlePacketOnMainThread() {
        SYNCED_DATA = this.map;
        AnalyzerRecipeJsonManager.setRecipeList(SYNCED_DATA);
    }
}


