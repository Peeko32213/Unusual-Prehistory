package com.peeko32213.unusualprehistory.common.message;

import com.peeko32213.unusualprehistory.common.entity.EntityTriceratops;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class TrikeKeyOutputMessage {
    public int key;

    public TrikeKeyOutputMessage(int key) {
        this.key = key;
    }

    public TrikeKeyOutputMessage(FriendlyByteBuf buf) {
        this.key = buf.readInt();
    }


    public void toBytes(FriendlyByteBuf buffer) {
        buffer.writeInt(key);
    }

    public static void handle(TrikeKeyOutputMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            Player player = context.getSender();
            Entity vehicle = player.getVehicle();
            if (vehicle instanceof EntityTriceratops triceratops) {
                if (triceratops.isSaddled() && triceratops.isTame() && triceratops.getControllingPassenger() == player) {
                    triceratops.level.broadcastEntityEvent(triceratops, (byte)5);
                    triceratops.setSwinging(false);
                }

              // Player rider = (Player)triceratops.getControllingPassenger();
              // if(rider.getLastHurtMob() != null && triceratops.distanceTo(rider.getLastHurtMob()) < triceratops.getBbWidth() + 3F && !triceratops.isAlliedTo(rider.getLastHurtMob())){
              //     UUID preyUUID = rider.getLastHurtMob().getUUID();
              //     if (!triceratops.getUUID().equals(preyUUID) && triceratops.riderAttackCooldown == 0) {
              //         triceratops.doHurtTarget(rider.getLastHurtMob());
              //         triceratops.setSwinging(true);
              //         triceratops.riderAttackCooldown = 20;
              //     }
              // }
            }
        });
        context.setPacketHandled(true);
    }
}
