package com.peeko32213.unusualprehistory.core.registry.util;

import com.peeko32213.unusualprehistory.common.entity.EntityUlughbegsaurus;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.Supplier;

public class UlughKeyInputMessage {
    public int key;
    public static final Logger LOGGER = LogManager.getLogger();

    public UlughKeyInputMessage(int key) {
        this.key = key;
    }

    public UlughKeyInputMessage(FriendlyByteBuf buf) {
        this.key = buf.readInt();
    }


    public void toBytes(FriendlyByteBuf buffer) {
        buffer.writeInt(key);
    }

    public static void handle(UlughKeyInputMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            Player player = context.getSender();
            Entity vehicle = player.getVehicle();
            if (vehicle instanceof EntityUlughbegsaurus ulughbegsaurus) {
                if (ulughbegsaurus.isSaddled() && ulughbegsaurus.isTame() && ulughbegsaurus.getControllingPassenger() == player) {
                    if (!(ulughbegsaurus.attackCooldown > 0)) {
                        ulughbegsaurus.setSwinging(true);
                        ulughbegsaurus.performAttack();
                        ulughbegsaurus.attackCooldown = EntityUlughbegsaurus.ATTACK_COOLDOWN;

                    }
                }
            }
        });
        context.setPacketHandled(true);
    }
}
