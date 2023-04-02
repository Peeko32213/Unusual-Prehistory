package com.peeko32213.unusualprehistory.common.capabilities;
/**
 * The following code falls under GNU Lesser General Public License v3.0
 *
 * @Author TelepathicGrunt
 * Taken from https://github.com/TelepathicGrunt/Bumblezone/blob/1.19.2-Forge/src/main/java/com/telepathicgrunt/the_bumblezone/capabilities/BzCapabilities.java
 */

import net.minecraft.world.entity.Entity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public final class UPCapabilities {

    public static final Capability<UPPlayerCapability> PLAYER_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {
    });

    public static void setupCapabilities() {
        IEventBus modbus = FMLJavaModLoadingContext.get().getModEventBus();
        modbus.addListener(UPCapabilities::registerCapabilities);

        IEventBus forgeBus = MinecraftForge.EVENT_BUS;
        forgeBus.addGenericListener(Entity.class, UPAttacherPlayerCapability::attach);
    }

    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.register(UPPlayerCapability.class);
    }
}
