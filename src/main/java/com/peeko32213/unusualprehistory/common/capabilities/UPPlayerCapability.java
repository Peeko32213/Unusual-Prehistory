package com.peeko32213.unusualprehistory.common.capabilities;

import com.peeko32213.unusualprehistory.common.message.AmberProtectionSyncS2CPacket;
import com.peeko32213.unusualprehistory.core.registry.UPEffects;
import com.peeko32213.unusualprehistory.core.registry.UPMessages;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

public class UPPlayerCapability implements INBTSerializable<CompoundTag> {
    public static final Logger LOGGER = LogManager.getLogger();
    public int amberProtection = 0;
    public int playerVaccinationTime = 0;
    public int playersRabiesHadTime = 0;

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();

        nbt.putInt("amberProtection", this.amberProtection);
        nbt.putInt("playerVaccinationTime", this.playerVaccinationTime);
        nbt.putInt("playersRabiesHadTime", this.playersRabiesHadTime);

        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {

        this.amberProtection = nbt.getInt("amberProtection");
        this.playerVaccinationTime = nbt.getInt("playerVaccinationTime");
        this.playersRabiesHadTime = nbt.getInt("playersRabiesHadTime");
    }



    public static void onPlayerCloned(PlayerEvent.Clone event) {
        if(event.isWasDeath()) {
            event.getOriginal().getCapability(UPCapabilities.PLAYER_CAPABILITY).ifPresent(oldStore -> {
                event.getOriginal().getCapability(UPCapabilities.PLAYER_CAPABILITY).ifPresent(newStore -> {
                    newStore.amberProtection = oldStore.amberProtection;
                    newStore.playerVaccinationTime = oldStore.playerVaccinationTime;
                    newStore.playersRabiesHadTime = oldStore.playersRabiesHadTime;
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
                    } else {
                        serverPlayer.setHealth(serverPlayer.getHealth() - 1);
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
