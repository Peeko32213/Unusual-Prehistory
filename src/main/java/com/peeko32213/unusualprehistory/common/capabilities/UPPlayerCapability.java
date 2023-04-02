package com.peeko32213.unusualprehistory.common.capabilities;

import com.peeko32213.unusualprehistory.common.networking.UPMessages;
import com.peeko32213.unusualprehistory.common.networking.packet.AmberProtectionSyncS2CPacket;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class UPPlayerCapability implements INBTSerializable<CompoundTag> {

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
                for(int i = 1; i < damage; i++){
                    if(capability.amberProtection * 2 >= serverPlayer.getHealth()){
                        capability.amberProtection = capability.amberProtection - i;
                    } else {
                        serverPlayer.setHealth(serverPlayer.getHealth() - i);
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
