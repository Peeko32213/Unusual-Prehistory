package com.peeko32213.unusualprehistory.common.message;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.data.LootFruitCodec;
import com.peeko32213.unusualprehistory.common.data.LootFruitJsonManager;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class LootFruitTierPacketS2C {


    public static final Codec<Integer> INT_CODEC  =Codec.STRING.comapFlatMap(str -> parseInt(str), intVal ->  Integer.toString(intVal));

    private static final DataResult<Integer> parseInt(String instantString) {
        try {
            return DataResult.success(Integer.valueOf(instantString));
        } catch (NumberFormatException e) {
            return DataResult.error(() -> e.getMessage());
        }
    }

    private static final Codec<Map<Integer, List<LootFruitCodec>>> MAPPER = Codec.unboundedMap(INT_CODEC, LootFruitCodec.CODEC.listOf())
            .xmap(LootFruitCodec::convertToMap, LootFruitCodec::convertFromMap)
            .orElse(e -> {UnusualPrehistory.LOGGER.error("Failed to parse lootfruitTier can't send packet! Due to " + e);},
                    new HashMap<>());


    public static Map<Integer, List<LootFruitCodec>> SYNCED_DATA = new HashMap<>();

    private final Map<Integer, List<LootFruitCodec>> map;

    public LootFruitTierPacketS2C(Map<Integer, List<LootFruitCodec>> map) {
        this.map = map;
    }

    public void encode(FriendlyByteBuf buffer) {
        CompoundTag result = (CompoundTag) (MAPPER.encodeStart(NbtOps.INSTANCE, this.map).result().orElse(new CompoundTag()));
        buffer.writeNbt(result);
    }

    public static LootFruitTierPacketS2C decode(FriendlyByteBuf buffer) {
        CompoundTag receivedTag = buffer.readNbt();
        Map<Integer, List<LootFruitCodec>> decodedMap = MAPPER.parse(NbtOps.INSTANCE, receivedTag).result().orElse(new HashMap<>());
        return new LootFruitTierPacketS2C(decodedMap);
    }

    public void onPacketReceived(Supplier<NetworkEvent.Context> contextGetter) {
        NetworkEvent.Context context = contextGetter.get();
        context.enqueueWork(this::handlePacketOnMainThread);
        context.setPacketHandled(true);
    }

    private void handlePacketOnMainThread() {
        SYNCED_DATA = this.map;
        LootFruitJsonManager.setTierTrades(SYNCED_DATA);
    }
}


