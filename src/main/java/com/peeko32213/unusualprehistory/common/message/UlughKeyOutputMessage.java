package com.peeko32213.unusualprehistory.common.message;

import com.peeko32213.unusualprehistory.common.entity.EntityUlughbegsaurus;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class UlughKeyOutputMessage {
    public int key;

    public UlughKeyOutputMessage(int key) {
        this.key = key;
    }

    public UlughKeyOutputMessage(FriendlyByteBuf buf) {
        this.key = buf.readInt();
    }


    public void toBytes(FriendlyByteBuf buffer) {
        buffer.writeInt(key);
    }

    public static void handle(UlughKeyOutputMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            Player player = context.getSender();
            Entity vehicle = player.getVehicle();
            if (vehicle instanceof EntityUlughbegsaurus ulughbegsaurus) {
                for (Entity entity : ulughbegsaurus.level.getEntitiesOfClass(LivingEntity.class, ulughbegsaurus.getBoundingBox().inflate(2.0D))) {
                    if (ulughbegsaurus.isSaddled() && ulughbegsaurus.isTame() && ulughbegsaurus.getControllingPassenger() == player) {
                        //This one is only for client side stuff
                        ulughbegsaurus.level.broadcastEntityEvent(ulughbegsaurus, (byte) 5);

                        //Here we set it server side
                        ulughbegsaurus.setSwinging(false);
                    }
                }
            }
        });
        context.setPacketHandled(true);
    }
}
