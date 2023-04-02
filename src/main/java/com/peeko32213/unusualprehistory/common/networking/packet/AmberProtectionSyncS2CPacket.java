package com.peeko32213.unusualprehistory.common.networking.packet;

import com.peeko32213.unusualprehistory.client.ClientAmberProtectionData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class AmberProtectionSyncS2CPacket {
    private final int amber_protection;

    public AmberProtectionSyncS2CPacket(int amber_protection) {
        this.amber_protection = amber_protection;
    }

    public AmberProtectionSyncS2CPacket(FriendlyByteBuf buf) {
        this.amber_protection = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf)
    {
        buf.writeInt(amber_protection);
    }


    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ClientAmberProtectionData.set(amber_protection);
        });
        return true;
    }
}
