package com.peeko32213.unusualprehistory.common.message;

import com.peeko32213.unusualprehistory.common.entity.IAttackEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.Supplier;

public class AttackInputMessage {
    public int key;
    public static final Logger LOGGER = LogManager.getLogger();

    public AttackInputMessage(int key) {
        this.key = key;
    }

    public AttackInputMessage(FriendlyByteBuf buf) {
        this.key = buf.readInt();
    }


    public void toBytes(FriendlyByteBuf buffer) {
        buffer.writeInt(key);
    }

    public static void handle(AttackInputMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            Player player = context.getSender();
            Entity vehicle = player.getVehicle();
            if (vehicle instanceof IAttackEntity attackEntity && vehicle instanceof TamableAnimal animal) {
                if (animal.isTame() && animal.getControllingPassenger() == player) {
                    if (attackEntity.getAttackCooldown() <= 0) {
                        attackEntity.performAttack();
                        attackEntity.setAttackCooldown(attackEntity.getMaxAttackCooldown());
                    }
                }
            }
        });
        context.setPacketHandled(true);
    }
}
