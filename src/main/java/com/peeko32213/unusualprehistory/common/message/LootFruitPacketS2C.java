package com.peeko32213.unusualprehistory.common.message;

import com.mojang.serialization.Codec;
import com.peeko32213.unusualprehistory.UnusualPrehistory;
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

public class LootFruitPacketS2C {
    private static final Codec<Map<Item, List<LootFruitCodec>>> MAPPER = Codec.unboundedMap(
                    Registry.ITEM.byNameCodec(),
                    LootFruitCodec.CODEC.listOf())
            .xmap(LootFruitCodec::convertToMap, LootFruitCodec::convertFromMap)
            .orElse(e -> {UnusualPrehistory.LOGGER.error("Failed to parse lootfruits can't send packet! Due to " + e);},
                    new HashMap<>());
    public static Map<Item, List<LootFruitCodec>> SYNCED_DATA = new HashMap<>();

    private final Map<Item, List<LootFruitCodec>> map;

    public LootFruitPacketS2C(Map<Item, List<LootFruitCodec>> map) {
        this.map = map;
    }

    public void encode(FriendlyByteBuf buffer) {
        CompoundTag result = (CompoundTag) (MAPPER.encodeStart(NbtOps.INSTANCE, this.map).result().orElse(new CompoundTag()));
        buffer.writeNbt(result);
    }

    public static LootFruitPacketS2C decode(FriendlyByteBuf buffer) {
        CompoundTag receivedTag = buffer.readNbt();
        Map<Item, List<LootFruitCodec>> decodedMap = MAPPER.parse(NbtOps.INSTANCE, receivedTag).result().orElse(new HashMap<>());
        return new LootFruitPacketS2C(decodedMap);
    }

    public void onPacketReceived(Supplier<NetworkEvent.Context> contextGetter) {
        NetworkEvent.Context context = contextGetter.get();
        context.enqueueWork(this::handlePacketOnMainThread);
        context.setPacketHandled(true);
    }

    private void handlePacketOnMainThread() {
        SYNCED_DATA = this.map;
        LootFruitJsonManager.setTrades(SYNCED_DATA);
    }
}


