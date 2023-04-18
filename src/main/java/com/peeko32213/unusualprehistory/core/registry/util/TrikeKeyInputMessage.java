package com.peeko32213.unusualprehistory.core.registry.util;

import com.peeko32213.unusualprehistory.common.entity.EntityHwachavenator;
import com.peeko32213.unusualprehistory.common.entity.EntityTriceratops;
import com.peeko32213.unusualprehistory.common.entity.EntityUlughbegsaurus;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.Supplier;

public class TrikeKeyInputMessage {
    public int key;
    public static final Logger LOGGER = LogManager.getLogger();
    public TrikeKeyInputMessage(int key) {
        this.key = key;
    }

    public TrikeKeyInputMessage(FriendlyByteBuf buf) {
        this.key = buf.readInt();
    }


    public void toBytes(FriendlyByteBuf buffer) {
        buffer.writeInt(key);
    }

    public static void handle(TrikeKeyInputMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            Player player = context.getSender();
            Entity vehicle = player.getVehicle();

            if (vehicle instanceof EntityTriceratops triceratops && triceratops.getControllingPassenger() == player) {
                if(!(triceratops.attackCooldown > 0)) {
                    triceratops.setSwinging(true);
                }
            }
        });
        context.setPacketHandled(true);
    }
}
