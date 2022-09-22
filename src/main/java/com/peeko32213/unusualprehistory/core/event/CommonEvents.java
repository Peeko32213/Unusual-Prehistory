package com.peeko32213.unusualprehistory.core.event;

import com.google.common.base.Predicates;
import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.*;
import com.peeko32213.unusualprehistory.common.entity.baby.EntityBeelzebufoTadpole;
import com.peeko32213.unusualprehistory.common.entity.EntityDunkleosteus;
import com.peeko32213.unusualprehistory.core.registry.UPEntities;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import java.util.function.Predicate;

@Mod.EventBusSubscriber(modid = UnusualPrehistory.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonEvents {

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
        event.put(UPEntities.BEELZE_TADPOLE.get(), EntityBeelzebufoTadpole.createAttributes().build());
        event.put(UPEntities.BABY_DUNK.get(), EntityDunkleosteus.createAttributes().build());

    }

    public static Predicate<LivingEntity> buildPredicateFromTag(TagKey<EntityType<?>> entityTag){
        if(entityTag == null){
            return Predicates.alwaysFalse();
        }else{
            return (com.google.common.base.Predicate<LivingEntity>) e -> e.isAlive() && e.getType().is(entityTag);
        }
    }

}