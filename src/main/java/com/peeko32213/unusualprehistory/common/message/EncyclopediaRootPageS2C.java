package com.peeko32213.unusualprehistory.common.message;

import com.mojang.serialization.Codec;
import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.data.EncyclopediaCodec;
import com.peeko32213.unusualprehistory.common.data.EncyclopediaJsonManager;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class EncyclopediaRootPageS2C {

    private static final Codec<EncyclopediaCodec> MAPPER = EncyclopediaCodec.CODEC.orElse(e -> {UnusualPrehistory.LOGGER.error("Failed to parse Encyclopedia Entries can't send packet! Due to " + e);},
            null);;
    protected static EncyclopediaCodec SYNCED_DATA;
    private final EncyclopediaCodec map;

    public EncyclopediaRootPageS2C(EncyclopediaCodec map) {
        this.map = map;
    }

    public void encode(FriendlyByteBuf buffer) {
        CompoundTag encodedTag = (CompoundTag) (MAPPER.encodeStart(NbtOps.INSTANCE, this.map).result().orElse(new CompoundTag()));
        buffer.writeNbt(encodedTag);
    }

    public static EncyclopediaRootPageS2C decode(FriendlyByteBuf buffer) {
        CompoundTag receivedTag = buffer.readNbt();
        EncyclopediaCodec decodedMap = MAPPER.parse(NbtOps.INSTANCE, receivedTag).result().orElse(null);
        return new EncyclopediaRootPageS2C(decodedMap);
    }

    public void onPacketReceived(Supplier<NetworkEvent.Context> contextGetter) {
        NetworkEvent.Context context = contextGetter.get();
        context.enqueueWork(this::handlePacketOnMainThread);
        context.setPacketHandled(true);
    }

    private void handlePacketOnMainThread() {
        SYNCED_DATA = this.map;
        if(SYNCED_DATA != null) {
            EncyclopediaJsonManager.setRootPage(SYNCED_DATA);
        }
    }
}


