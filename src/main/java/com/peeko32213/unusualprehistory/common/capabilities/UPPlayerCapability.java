package com.peeko32213.unusualprehistory.common.capabilities;

import com.peeko32213.unusualprehistory.common.networking.packet.AmberProtectionSyncS2CPacket;
import com.peeko32213.unusualprehistory.core.registry.UPMessages;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UPPlayerCapability implements INBTSerializable<CompoundTag> {
    public static final Logger LOGGER = LogManager.getLogger();
    public int amberProtection = 0;

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();

        nbt.putInt("amberProtection", this.amberProtection);

        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        this.amberProtection = nbt.getInt("amberProtection");
    }



    public static void onPlayerCloned(PlayerEvent.Clone event) {
        if(event.isWasDeath()) {
            event.getOriginal().getCapability(UPCapabilities.PLAYER_CAPABILITY).ifPresent(oldStore -> {
                event.getOriginal().getCapability(UPCapabilities.PLAYER_CAPABILITY).ifPresent(newStore -> {
                    newStore.amberProtection = oldStore.amberProtection;
                });
            });
        }
    }


    public static void onLivingDamage(LivingDamageEvent event) {
        if(event.getEntity() instanceof ServerPlayer serverPlayer){
            serverPlayer.getCapability(UPCapabilities.PLAYER_CAPABILITY).ifPresent(capability -> {
                if(capability.amberProtection ==0){
                    return;
                }

                int damage = (int)event.getAmount();
                LOGGER.info("damage " + damage);
                for(int i = 0; i < damage; i++){
                    if(capability.amberProtection >= serverPlayer.getHealth()){
                        capability.amberProtection = capability.amberProtection - 1;
                        LOGGER.info("doing damage to amber");
                    } else {
                        serverPlayer.setHealth(serverPlayer.getHealth() - 1);
                        LOGGER.info("doing damage to hp");
                    }
                    UPMessages.sendToPlayer(new AmberProtectionSyncS2CPacket(capability.amberProtection), serverPlayer);
                }
                event.setCanceled(true);
            });
        }
    }

    public static void onPlayerJoinWorld(EntityJoinLevelEvent event) {
        if(!event.getLevel().isClientSide()) {
            if(event.getEntity() instanceof ServerPlayer player) {
                player.getCapability(UPCapabilities.PLAYER_CAPABILITY).ifPresent(capability -> {
                    UPMessages.sendToPlayer(new AmberProtectionSyncS2CPacket(capability.amberProtection), player);
                });
            }
        }
    }
}
