package com.peeko32213.unusualprehistory.core.events;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.*;
import com.peeko32213.unusualprehistory.common.entity.msc.baby.*;
import com.peeko32213.unusualprehistory.common.entity.msc.part.EntityLeedsichthysPart;
import com.peeko32213.unusualprehistory.common.entity.msc.part.EntityPalaeophisPart;
import com.peeko32213.unusualprehistory.common.entity.msc.util.dino.EntityBookSnake;
import com.peeko32213.unusualprehistory.common.entity.msc.util.dino.EntityWorldSpawnable;
import com.peeko32213.unusualprehistory.common.entity.plants.EntityPlant;
import com.peeko32213.unusualprehistory.core.registry.UPEntities;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

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
        event.put(UPEntities.TALPANAS.get(), EntityTalpanas.createAttributes().build());
        event.put(UPEntities.GIGANTOPITHICUS.get(), EntityGigantopithicus.createAttributes().build());
        event.put(UPEntities.BARINASUCHUS.get(), EntityBarinasuchus.createAttributes().build());
        event.put(UPEntities.MEGATHERIUM.get(), EntityMegatherium.createAttributes().build());
        event.put(UPEntities.PARACERATHERIUM.get(), EntityParaceratherium.createAttributes().build());
        event.put(UPEntities.SMILODON.get(), EntitySmilodon.createAttributes().build());
        event.put(UPEntities.MEGALANIA.get(), EntityMegalania.createAttributes().build());
        event.put(UPEntities.MAMMOTH.get(), EntityMammoth.createAttributes().build());
        event.put(UPEntities.PALAEOPHIS.get(), EntityPalaeophis.createAttributes().build());
        event.put(UPEntities.PALAEOPHIS_PART.get(), EntityPalaeophisPart.bakeAttributes().build());
        event.put(UPEntities.BABY_MEGATHERIUM.get(), EntityBabyMegatherium.createAttributes().build());
        event.put(UPEntities.BABY_PARACER.get(), EntityBabyParaceratherium.createAttributes().build());
        event.put(UPEntities.BABY_GIGANTO.get(), EntityBabyGigantopithicus.createAttributes().build());
        event.put(UPEntities.BABY_MEGALANIA.get(), EntityBabyMegalania.createAttributes().build());
        event.put(UPEntities.BABY_PALAEO.get(), EntityBabyPalaeolophis.createAttributes().build());
        event.put(UPEntities.BABY_SMILODON.get(), EntityBabySmilodon.createAttributes().build());
        event.put(UPEntities.BABY_MAMMOTH.get(), EntityBabyMammoth.createAttributes().build());
        event.put(UPEntities.BABY_BARINA.get(), EntityBabyBarinasuchus.createAttributes().build());
        event.put(UPEntities.SLUDGE.get(), EntitySludge.createAttributes().build());
        event.put(UPEntities.OTAROCYON.get(), EntityOtarocyon.createAttributes().build());  //80% Done
        event.put(UPEntities.LONGISQUAMA.get(), EntityLongisquama.createAttributes().build()); //80% Done
        event.put(UPEntities.JAWLESS_FISH.get(), EntityJawlessFish.createAttributes().build()); //50% Done
        event.put(UPEntities.TARTUOSTEUS.get(), EntityTartuosteus.createAttributes().build()); //80% Done
        event.put(UPEntities.PSITTACO.get(), EntityPsittacosaurus.createAttributes().build()); //70% Done
        event.put(UPEntities.TANY.get(), EntityTanystropheus.createAttributes().build()); //70% Done
        event.put(UPEntities.KAPROSUCHUS.get(), EntityKaprosuchus.createAttributes().build()); //80% Done
        event.put(UPEntities.PSILOPTERUS.get(), EntityPsilopterus.createAttributes().build()); //80% Done
        event.put(UPEntities.DIPLOCAULUS.get(), EntityDiplocaulus.createAttributes().build()); //80% Done
        event.put(UPEntities.HYNERPETON.get(), EntityHynerpeton.createAttributes().build()); //80% Done
        event.put(UPEntities.BALAUR.get(), EntityBalaur.createAttributes().build()); //70% Done
        event.put(UPEntities.OPHIODON.get(), EntityOphiodon.createAttributes().build()); //80% Done
        event.put(UPEntities.PROTOSPHYRAENA.get(), EntityProtosphyraena.createAttributes().build());
        event.put(UPEntities.KIMMER.get(), EntityKimmeridgebrachypteraeschnidium.createAttributes().build());
        event.put(UPEntities.ARCHELON.get(), EntityArchelon.createAttributes().build());
        event.put(UPEntities.LEEDSICHTHYS.get(), EntityLeedsichthys.createAttributes().build());
        event.put(UPEntities.LEEDS_PART.get(), EntityLeedsichthysPart.createAttributes().build());
        event.put(UPEntities.PTERODAUSTRO.get(), EntityPterodaustro.createAttributes().build());


        event.put(UPEntities.ICEBERG_SMILODON.get(), EntityWorldSpawnable.bakeAttributes().build());
        event.put(UPEntities.ICEBERG_MAMMOTH.get(), EntityWorldSpawnable.bakeAttributes().build());
        event.put(UPEntities.BOOK_PALAEO.get(), EntityBookSnake.createAttributes().build());

        //Plants
        event.put(UPEntities.FOXXI_SAPLING.get(), EntityPlant.bakeAttributes().build());
        event.put(UPEntities.HORSETAIL.get(), EntityPlant.bakeAttributes().build());
        event.put(UPEntities.TALL_HORSETAIL.get(), EntityPlant.bakeAttributes().build());
        event.put(UPEntities.LEEFRUCTUS.get(), EntityPlant.bakeAttributes().build());
        event.put(UPEntities.BENNETTITALES.get(), EntityPlant.bakeAttributes().build());
        event.put(UPEntities.ARCHAEOSIGILARIA.get(), EntityPlant.bakeAttributes().build());
        event.put(UPEntities.SARACENIA.get(), EntityPlant.bakeAttributes().build());
        event.put(UPEntities.TALL_SARACENIA.get(), EntityPlant.bakeAttributes().build());
        event.put(UPEntities.GINKGO_SAPLING.get(), EntityPlant.bakeAttributes().build());
        event.put(UPEntities.DRYO_SAPLING.get(), EntityPlant.bakeAttributes().build());
        event.put(UPEntities.CLATHRODICTYON.get(), EntityPlant.bakeAttributes().build());
        event.put(UPEntities.ARCHAEFRUCTUS.get(), EntityPlant.bakeAttributes().build());
        event.put(UPEntities.NELUMBITES.get(), EntityPlant.bakeAttributes().build());
        event.put(UPEntities.QUEREUXIA.get(), EntityPlant.bakeAttributes().build());
        event.put(UPEntities.RAIGUENRAYUN.get(), EntityPlant.bakeAttributes().build());

        event.put(UPEntities.DINO_LAND_EGG.get(), LivingEntity.createLivingAttributes().build());

    }
}