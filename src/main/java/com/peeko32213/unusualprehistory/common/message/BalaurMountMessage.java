package com.peeko32213.unusualprehistory.common.message;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.BalaurEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class BalaurMountMessage {
    public int rider;
    public int mount;

    public BalaurMountMessage(int rider, int mount) {
        this.rider = rider;
        this.mount = mount;
    }

    public BalaurMountMessage() {
    }

    public static BalaurMountMessage read(FriendlyByteBuf buf) {
        return new BalaurMountMessage(buf.readInt(), buf.readInt());
    }

    public static void write(BalaurMountMessage message, FriendlyByteBuf buf) {
        buf.writeInt(message.rider);
        buf.writeInt(message.mount);
    }

    public static class Handler {
        public Handler() {
        }

        public static void handle(BalaurMountMessage message, Supplier<NetworkEvent.Context> context) {
            context.get().setPacketHandled(true);
            Player player = context.get().getSender();
            if(context.get().getDirection().getReceptionSide() == LogicalSide.CLIENT){
                player = UnusualPrehistory.PROXY.getClientSidePlayer();
            }

            if (player != null) {
                if (player.level() != null) {
                    Entity entity = player.level().getEntity(message.rider);
                    Entity mountEntity = player.level().getEntity(message.mount);
                    if ((entity instanceof BalaurEntity) && mountEntity instanceof Player && entity.distanceTo(mountEntity) < 16D) {
                        entity.startRiding(mountEntity, true);
                    }
                }
            }
        }
    }
}
