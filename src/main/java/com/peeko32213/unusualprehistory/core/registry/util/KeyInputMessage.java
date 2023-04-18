package com.peeko32213.unusualprehistory.core.registry.util;

import com.peeko32213.unusualprehistory.common.entity.EntityHwachavenator;
import com.peeko32213.unusualprehistory.common.entity.EntityTriceratops;
import com.peeko32213.unusualprehistory.common.entity.EntityUlughbegsaurus;
import com.peeko32213.unusualprehistory.core.registry.UPSounds;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class KeyInputMessage  {
    public int key;

    public KeyInputMessage(int key) {
        this.key = key;
    }

    public KeyInputMessage(FriendlyByteBuf buf) {
        this.key = buf.readInt();
    }


    public void toBytes(FriendlyByteBuf buffer) {
        buffer.writeInt(key);
    }

    public static void handle(KeyInputMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            Player player = context.getSender();
            Entity vehicle = player.getVehicle();
            if (vehicle instanceof EntityHwachavenator hwachavenator && !hwachavenator.isInSittingPose()) {
                if (hwachavenator.isSaddled() && hwachavenator.isTame() && hwachavenator.getControllingPassenger() == player) {
                    hwachavenator.setIsShooting(true);
                }
            }
            if (vehicle instanceof EntityUlughbegsaurus ulughbegsaurus && !ulughbegsaurus.isInSittingPose() && !ulughbegsaurus.hasSwung()) {
                ulughbegsaurus.level.broadcastEntityEvent(ulughbegsaurus, (byte)4);
                for (Entity entity : ulughbegsaurus.level.getEntitiesOfClass(LivingEntity.class, ulughbegsaurus.getBoundingBox().inflate(2.0D))) {
                    if (!(entity instanceof EntityUlughbegsaurus) && !(entity instanceof Player)) {
                        if (ulughbegsaurus.isSaddled() && ulughbegsaurus.isTame() && ulughbegsaurus.getControllingPassenger() == player) {
                            ulughbegsaurus.setSwinging(true);
                            ulughbegsaurus.setHasSwung(true);
                            ulughbegsaurus.swinging = true;
                            if (ulughbegsaurus.isOrange()) {
                                entity.hurt(DamageSource.mobAttack(ulughbegsaurus), 12.0F);
                            } else
                                entity.hurt(DamageSource.mobAttack(ulughbegsaurus), 8.0F);

                        }
                    }
                }
            }
            if (vehicle instanceof EntityTriceratops triceratops && !triceratops.isInSittingPose() && !triceratops.hasSwung()) {
                //triceratops.level.broadcastEntityEvent(triceratops, (byte)4);
                for (Entity entity : triceratops.level.getEntitiesOfClass(LivingEntity.class, triceratops.getBoundingBox().inflate(4.0D))) {
                    if (!(entity instanceof EntityTriceratops) && !(entity instanceof Player)) {
                        if (triceratops.isSaddled() && triceratops.isTame() && triceratops.getControllingPassenger() == player) {
                            triceratops.setSwinging(true);
                            triceratops.setHasSwung(true);
                            triceratops.swinging = true;
                            entity.hurt(DamageSource.mobAttack(triceratops), 10.0F);
                        }
                    }
                }
            }
        });
        context.setPacketHandled(true);
    }
}
