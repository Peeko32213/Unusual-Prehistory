package com.peeko32213.unusualprehistory.core.registry.util;

import com.peeko32213.unusualprehistory.common.entity.EntityMegatherium;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class MegatheriumKeyOutputMessage {
    public int key;

    public MegatheriumKeyOutputMessage(int key) {
        this.key = key;
    }

    public MegatheriumKeyOutputMessage(FriendlyByteBuf buf) {
        this.key = buf.readInt();
    }


    public void toBytes(FriendlyByteBuf buffer) {
        buffer.writeInt(key);
    }

    public static void handle(MegatheriumKeyOutputMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            Player player = context.getSender();
            Entity vehicle = player.getVehicle();
            if (vehicle instanceof EntityMegatherium megatherium) {
                if (megatherium.isSaddled() && megatherium.isTame() && megatherium.getControllingPassenger() == player) {
                    megatherium.level.broadcastEntityEvent(megatherium, (byte)5);
                    megatherium.setSwinging(false);
                }

              // Player rider = (Player)megatherium.getControllingPassenger();
              // if(rider.getLastHurtMob() != null && megatherium.distanceTo(rider.getLastHurtMob()) < megatherium.getBbWidth() + 3F && !megatherium.isAlliedTo(rider.getLastHurtMob())){
              //     UUID preyUUID = rider.getLastHurtMob().getUUID();
              //     if (!megatherium.getUUID().equals(preyUUID) && megatherium.riderAttackCooldown == 0) {
              //         megatherium.doHurtTarget(rider.getLastHurtMob());
              //         megatherium.setSwinging(true);
              //         megatherium.riderAttackCooldown = 20;
              //     }
              // }
            }
        });
        context.setPacketHandled(true);
    }
}
