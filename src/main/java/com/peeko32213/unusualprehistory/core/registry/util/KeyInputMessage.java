package com.peeko32213.unusualprehistory.core.registry.util;

import com.peeko32213.unusualprehistory.common.entity.EntityHwachavenator;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class KeyInputMessage  {
    public int key;

    public KeyInputMessage(int key) {
        this.key = key;
    }

    public static void encode(KeyInputMessage message, FriendlyByteBuf buffer) {
        buffer.writeInt(message.key);
    }

    public static KeyInputMessage decode(FriendlyByteBuf buffer) {
        return new KeyInputMessage(buffer.readInt());
    }

    public static void handle(KeyInputMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            Player player = context.getSender();
            Entity vehicle = player.getVehicle();
            if (vehicle instanceof EntityHwachavenator hwachavenator) {
                if (hwachavenator.isSaddled() && hwachavenator.isTame() && hwachavenator.getControllingPassenger() == player) {
                    hwachavenator.setIsShooting(true);
                }
            }
        });
        context.setPacketHandled(true);
    }
}
