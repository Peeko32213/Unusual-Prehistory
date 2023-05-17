package com.peeko32213.unusualprehistory.core.registry.util;

import com.peeko32213.unusualprehistory.common.entity.EntityHwachavenator;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.Supplier;

public class HwachaKeyInputMessage {
    public int key;
    public static final Logger LOGGER = LogManager.getLogger();
    public HwachaKeyInputMessage(int key) {
        this.key = key;
    }

    public HwachaKeyInputMessage(FriendlyByteBuf buf) {
        this.key = buf.readInt();
    }


    public void toBytes(FriendlyByteBuf buffer) {
        buffer.writeInt(key);
    }

    public static void handle(HwachaKeyInputMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            Player player = context.getSender();
            Entity vehicle = player.getVehicle();
            if (vehicle instanceof EntityHwachavenator hwachavenator && !hwachavenator.isInSittingPose() && !hwachavenator.isInWater()) {
                if (hwachavenator.isSaddled() && hwachavenator.isTame() && hwachavenator.getControllingPassenger() == player) {
                    hwachavenator.setIsShooting(true);
                }
            }
        });
        context.setPacketHandled(true);
    }
}
