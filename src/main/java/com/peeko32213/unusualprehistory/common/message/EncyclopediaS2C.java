package com.peeko32213.unusualprehistory.common.message;

import com.mojang.serialization.Codec;
import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.data.EncyclopediaCodec;
import com.peeko32213.unusualprehistory.common.data.EncyclopediaJsonManager;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class EncyclopediaS2C {

    private static final Codec<Map<ResourceLocation, EncyclopediaCodec>> MAPPER = Codec.unboundedMap(ResourceLocation.CODEC, EncyclopediaCodec.CODEC)
            .xmap(EncyclopediaCodec::convertToMap, EncyclopediaCodec::convertFromMap)
            .orElse(e -> {UnusualPrehistory.LOGGER.error("Failed to parse Encyclopedia Entries can't send packet! Due to " + e);},
            new HashMap<>());
    protected static Map<ResourceLocation, EncyclopediaCodec> SYNCED_DATA = new HashMap<>();
    private final Map<ResourceLocation, EncyclopediaCodec> map;

    public EncyclopediaS2C(Map<ResourceLocation, EncyclopediaCodec> map) {
        this.map = map;
    }

    public void encode(FriendlyByteBuf buffer) {
        CompoundTag encodedTag = (CompoundTag) (MAPPER.encodeStart(NbtOps.INSTANCE, this.map).result().orElse(new CompoundTag()));
        buffer.writeNbt(encodedTag);
    }

    public static EncyclopediaS2C decode(FriendlyByteBuf buffer) {
        CompoundTag receivedTag = buffer.readNbt();
        Map<ResourceLocation, EncyclopediaCodec> decodedMap = MAPPER.parse(NbtOps.INSTANCE, receivedTag).result().orElse(new HashMap<>());
        return new EncyclopediaS2C(decodedMap);
    }

    public void onPacketReceived(Supplier<NetworkEvent.Context> contextGetter) {
        NetworkEvent.Context context = contextGetter.get();
        context.enqueueWork(this::handlePacketOnMainThread);
        context.setPacketHandled(true);
    }

    private void handlePacketOnMainThread() {
        SYNCED_DATA = this.map;
        EncyclopediaJsonManager.setEncyclopediaEntries(SYNCED_DATA);
    }
}


