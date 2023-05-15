package com.peeko32213.unusualprehistory.core.registry.util;

import com.peeko32213.unusualprehistory.common.entity.EntityHwachavenator;
import com.peeko32213.unusualprehistory.core.registry.UPSounds;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.game.ClientboundStopSoundPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class HwachaKeyOutputMessage {
    public int key;

    public HwachaKeyOutputMessage(int key) {
        this.key = key;
    }

    public HwachaKeyOutputMessage(FriendlyByteBuf buf) {
        this.key = buf.readInt();
    }


    public void toBytes(FriendlyByteBuf buffer) {
        buffer.writeInt(key);
    }

    public static void handle(HwachaKeyOutputMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            Player player = context.getSender();
            Entity vehicle = player.getVehicle();
            if (vehicle instanceof EntityHwachavenator hwachavenator) {
                if (hwachavenator.isSaddled() && hwachavenator.isTame() && hwachavenator.getControllingPassenger() == player) {
                    hwachavenator.setIsShooting(false);
                    hwachavenator.soundTimer = 0;
                    ClientboundStopSoundPacket clientboundstopsoundpacket = new ClientboundStopSoundPacket(UPSounds.HWACHA_SHOOT.getId(), SoundSource.NEUTRAL);
                    ServerPlayer serverPlayer = (ServerPlayer) hwachavenator.getControllingPassenger();
                    serverPlayer.connection.send(clientboundstopsoundpacket);

                }
            }
        });
        context.setPacketHandled(true);
    }
}
