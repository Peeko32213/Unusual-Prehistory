package com.peeko32213.unusualprehistory.core.event;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.*;
import com.peeko32213.unusualprehistory.core.registry.UPEntities;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(modid = UnusualPrehistory.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonModEvents {

    @SubscribeEvent
    public static void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
        });
    }

    @SubscribeEvent
    public static void registerAttributes (EntityAttributeCreationEvent event) {
        event.put(UPEntities.STETHACANTHUS.get(), EntityStethacanthus.createAttributes().build());
        event.put(UPEntities.MAJUNGA.get(), EntityMajungasaurus.createAttributes().build());
        event.put(UPEntities.ANURO.get(), EntityAnurognathus.createAttributes().build());
        event.put(UPEntities.BEELZ.get(), EntityBeelzebufo.createAttributes().build());
        event.put(UPEntities.AMMON.get(), EntityAmmonite.createAttributes().build());
        event.put(UPEntities.DUNK.get(), EntityDunkleosteus.createAttributes().build());
        event.put(UPEntities.COTY.get(), EntityCotylorhynchus.createAttributes().build());

    }

}