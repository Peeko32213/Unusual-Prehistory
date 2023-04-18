package com.peeko32213.unusualprehistory.core.registry.util;

import com.peeko32213.unusualprehistory.common.entity.EntityHwachavenator;
import com.peeko32213.unusualprehistory.common.entity.EntityTriceratops;
import com.peeko32213.unusualprehistory.common.entity.EntityUlughbegsaurus;
import com.peeko32213.unusualprehistory.core.registry.UPSounds;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.game.ClientboundStopSoundPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class KeyOutputMessage {
    public int key;

    public KeyOutputMessage(int key) {
        this.key = key;
    }

    public KeyOutputMessage(FriendlyByteBuf buf) {
        this.key = buf.readInt();
    }


    public void toBytes(FriendlyByteBuf buffer) {
        buffer.writeInt(key);
    }

    public static void handle(KeyOutputMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            Player player = context.getSender();
            Entity vehicle = player.getVehicle();
            if (vehicle instanceof EntityHwachavenator hwachavenator) {
                if (hwachavenator.isSaddled() && hwachavenator.isTame() && hwachavenator.getControllingPassenger() == player) {
                    hwachavenator.setIsShooting(false);

                    ClientboundStopSoundPacket clientboundstopsoundpacket = new ClientboundStopSoundPacket(UPSounds.HWACHA_SHOOT.getId(), SoundSource.NEUTRAL);
                    ServerPlayer serverPlayer = (ServerPlayer)hwachavenator.getControllingPassenger();
                    serverPlayer.connection.send(clientboundstopsoundpacket);

                }
            }
            if (vehicle instanceof EntityUlughbegsaurus ulughbegsaurus && !ulughbegsaurus.isInSittingPose()) {
                ulughbegsaurus.level.broadcastEntityEvent(ulughbegsaurus, (byte)4);
                for (Entity entity : ulughbegsaurus.level.getEntitiesOfClass(LivingEntity.class, ulughbegsaurus.getBoundingBox().inflate(2.0D))) {
                    if (!(entity instanceof EntityUlughbegsaurus) && !(entity instanceof Player)) {
                        if (ulughbegsaurus.isSaddled() && ulughbegsaurus.isTame() && ulughbegsaurus.getControllingPassenger() == player) {
                            ulughbegsaurus.setHasSwung(false);
                        }
                    }
                }
            }
            if (vehicle instanceof EntityTriceratops triceratops && !triceratops.isInSittingPose()) {
                triceratops.level.broadcastEntityEvent(triceratops, (byte)4);
                for (Entity entity : triceratops.level.getEntitiesOfClass(LivingEntity.class, triceratops.getBoundingBox().inflate(4.0D))) {
                    if (!(entity instanceof EntityTriceratops) && !(entity instanceof Player)) {
                        if (triceratops.isSaddled() && triceratops.isTame() && triceratops.getControllingPassenger() == player) {
                            triceratops.setHasSwung(false);
                        }
                    }
                }
            }
        });
        context.setPacketHandled(true);
    }
}
