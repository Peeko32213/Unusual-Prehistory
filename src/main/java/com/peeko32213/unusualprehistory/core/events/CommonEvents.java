package com.peeko32213.unusualprehistory.core.events;

import com.google.common.base.Predicates;
import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.*;
import com.peeko32213.unusualprehistory.common.entity.msc.baby.*;
import com.peeko32213.unusualprehistory.common.entity.msc.util.dino.EntityWorldSpawnable;
import com.peeko32213.unusualprehistory.core.registry.UPEntities;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.function.Predicate;

@Mod.EventBusSubscriber(modid = UnusualPrehistory.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonEvents {


    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(UPEntities.STETHACANTHUS.get(), EntityStethacanthus.createAttributes().build());
        event.put(UPEntities.MAJUNGA.get(), EntityMajungasaurus.createAttributes().build());
        event.put(UPEntities.ANURO.get(), EntityAnurognathus.createAttributes().build());
        event.put(UPEntities.BEELZ.get(), EntityBeelzebufo.createAttributes().build());
        event.put(UPEntities.AMMON.get(), EntityAmmonite.createAttributes().build());
        event.put(UPEntities.DUNK.get(), EntityDunkleosteus.createAttributes().build());
        event.put(UPEntities.COTY.get(), EntityCotylorhynchus.createAttributes().build());
        event.put(UPEntities.BEELZE_TADPOLE.get(), EntityBeelzebufoTadpole.createAttributes().build());
        event.put(UPEntities.BABY_DUNK.get(), EntityBabyDunk.createAttributes().build());
        event.put(UPEntities.SCAU.get(), EntityScaumenacia.createAttributes().build());
        event.put(UPEntities.TRIKE.get(), EntityTriceratops.createAttributes().build());
        event.put(UPEntities.PACHY.get(), EntityPachycephalosaurus.createAttributes().build());
        event.put(UPEntities.BRACHI_TEEN.get(), EntityBrachiosaurusTeen.createAttributes().build());
        event.put(UPEntities.BRACHI.get(), EntityBrachiosaurus.createAttributes().build());
        event.put(UPEntities.VELOCI.get(), EntityVelociraptor.createAttributes().build());
        event.put(UPEntities.REX.get(), EntityTyrannosaurusRex.createAttributes().build());
        event.put(UPEntities.ENCRUSTED.get(), EntityEncrusted.createAttributes().build());
        event.put(UPEntities.BABY_REX.get(), EntityBabyRex.createAttributes().build());
        event.put(UPEntities.BABY_BRACHI.get(), EntityBabyBrachi.createAttributes().build());
        event.put(UPEntities.ERYON.get(), EntityEryon.createAttributes().build());
        event.put(UPEntities.AUSTRO.get(), EntityAustroraptor.createAttributes().build());
        event.put(UPEntities.ANTARCO.get(), EntityAntarctopelta.createAttributes().build());
        event.put(UPEntities.ULUG.get(), EntityUlughbegsaurus.createAttributes().build());
        event.put(UPEntities.KENTRO.get(), EntityKentrosaurus.createAttributes().build());
        event.put(UPEntities.HWACHA.get(), EntityHwachavenator.createAttributes().build());
        event.put(UPEntities.TALAPANAS.get(), EntityTalapanas.createAttributes().build());
        event.put(UPEntities.GIGANTOPITHICUS.get(), EntityGigantopithicus.createAttributes().build());
        event.put(UPEntities.BARINASUCHUS.get(), EntityBarinasuchus.createAttributes().build());
        event.put(UPEntities.MEGATHERIUM.get(), EntityMegatherium.createAttributes().build());
        event.put(UPEntities.PARACERATHERIUM.get(), EntityParaceratherium.createAttributes().build());
        event.put(UPEntities.SMILODON.get(), EntitySmilodon.createAttributes().build());
        event.put(UPEntities.MEGALANIA.get(), EntityMegalania.createAttributes().build());
        event.put(UPEntities.MAMMOTH.get(), EntityMammoth.createAttributes().build());
        event.put(UPEntities.PALAEOPHIS.get(), EntityPalaeophis.createAttributes().build());




        event.put(UPEntities.ICEBERG_SMILODON.get(), EntityWorldSpawnable.bakeAttributes().build());
        event.put(UPEntities.ICEBERG_MAMMOTH.get(), EntityWorldSpawnable.bakeAttributes().build());

    }

    public static Predicate<LivingEntity> buildPredicateFromTag(TagKey<EntityType<?>> entityTag) {
        if (entityTag == null) {
            return Predicates.alwaysFalse();
        } else {
            return (com.google.common.base.Predicate<LivingEntity>) e -> e.isAlive() && e.getType().is(entityTag);
        }
    }
}