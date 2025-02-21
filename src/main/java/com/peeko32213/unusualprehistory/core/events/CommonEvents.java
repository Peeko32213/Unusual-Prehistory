package com.peeko32213.unusualprehistory.core.events;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.*;
import com.peeko32213.unusualprehistory.common.entity.msc.baby.*;
import com.peeko32213.unusualprehistory.common.entity.msc.part.LeedsichthysPartEntity;
import com.peeko32213.unusualprehistory.common.entity.msc.part.PalaeophisPartEntity;
import com.peeko32213.unusualprehistory.common.entity.msc.util.dino.BookPalaeophisEntity;
import com.peeko32213.unusualprehistory.common.entity.msc.util.dino.WorldSpawnableEntity;
import com.peeko32213.unusualprehistory.common.entity.plants.EntityPlant;
import com.peeko32213.unusualprehistory.core.registry.UPEntities;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = UnusualPrehistory.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonEvents {


    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {

        // Paleo mobs
        event.put(UPEntities.AMMON.get(), AmmoniteEntity.createAttributes().build());
        event.put(UPEntities.COTY.get(), CotylorhynchusEntity.createAttributes().build());
        event.put(UPEntities.DUNK.get(), DunkleosteusEntity.createAttributes().build());
        event.put(UPEntities.BABY_DUNK.get(), EntityBabyDunk.createAttributes().build());
        event.put(UPEntities.DIPLOCAULUS.get(), DiplocaulusEntity.createAttributes().build());
        event.put(UPEntities.HYNERIA.get(), HyneriaEntity.createAttributes().build());
        event.put(UPEntities.HYNERPETON.get(), HynerpetonEntity.createAttributes().build());
        event.put(UPEntities.JAWLESS_FISH.get(), JawlessFishEntity.createAttributes().build());
        event.put(UPEntities.SCAU.get(), ScaumenaciaEntity.createAttributes().build());
        event.put(UPEntities.STETHACANTHUS.get(), StethacanthusEntity.createAttributes().build());
        event.put(UPEntities.TARTUOSTEUS.get(), TartuosteusEntity.createAttributes().build());

        // Meso mobs
        event.put(UPEntities.ANTARCO.get(), AntarctopeltaEntity.createAttributes().build());
        event.put(UPEntities.ANURO.get(), AnurognathusEntity.createAttributes().build());
        event.put(UPEntities.ARCHELON.get(), ArchelonEntity.createAttributes().build());
        event.put(UPEntities.AUSTRO.get(), AustroraptorEntity.createAttributes().build());
        event.put(UPEntities.BALAUR.get(), BalaurEntity.createAttributes().build());
        event.put(UPEntities.BEELZ.get(), BeelzebufoEntity.createAttributes().build());
        event.put(UPEntities.BEELZE_TADPOLE.get(), BeelzebufoTadpoleEntity.createAttributes().build());
        event.put(UPEntities.BRACHI.get(), BrachiosaurusEntity.createAttributes().build());
        event.put(UPEntities.ERYON.get(), EryonEntity.createAttributes().build());
        event.put(UPEntities.HWACHA.get(), HwachavenatorEntity.createAttributes().build());
        event.put(UPEntities.KAPROSUCHUS.get(), KaprosuchusEntity.createAttributes().build());
        event.put(UPEntities.KENTRO.get(), KentrosaurusEntity.createAttributes().build());
        event.put(UPEntities.KIMMER.get(), KimmeridgebrachypteraeschnidiumEntity.createAttributes().build());
        event.put(UPEntities.LEEDSICHTHYS.get(), LeedsichthysEntity.createAttributes().build());
        event.put(UPEntities.LEEDS_PART.get(), LeedsichthysPartEntity.createAttributes().build());
        event.put(UPEntities.LONGISQUAMA.get(), LongisquamaEntity.createAttributes().build());
        event.put(UPEntities.MAJUNGA.get(), MajungasaurusEntity.createAttributes().build());
        event.put(UPEntities.PACHY.get(), PachycephalosaurusEntity.createAttributes().build());
        event.put(UPEntities.PROTOSPHYRAENA.get(), ProtosphyraenaEntity.createAttributes().build());
        event.put(UPEntities.PSITTACO.get(), PsittacosaurusEntity.createAttributes().build());
        event.put(UPEntities.PTERODAUSTRO.get(), PterodaustroEntity.createAttributes().build());
        event.put(UPEntities.TANY.get(), TanystropheusEntity.createAttributes().build());
        event.put(UPEntities.TRIKE.get(), TriceratopsEntity.createAttributes().build());
        event.put(UPEntities.REX.get(), TyrannosaurusEntity.createAttributes().build());
        event.put(UPEntities.ULUG.get(), UlughbegsaurusEntity.createAttributes().build());
        event.put(UPEntities.VELOCI.get(), VelociraptorEntity.createAttributes().build());

        // Ceno mobs
        event.put(UPEntities.BARINASUCHUS.get(), BarinasuchusEntity.createAttributes().build());
        event.put(UPEntities.GIGANTOPITHICUS.get(), GigantopithicusEntity.createAttributes().build());
        event.put(UPEntities.MAMMOTH.get(), MammothEntity.createAttributes().build());
        event.put(UPEntities.MEGALANIA.get(), MegalaniaEntity.createAttributes().build());
        event.put(UPEntities.MEGATHERIUM.get(), MegatheriumEntity.createAttributes().build());
        event.put(UPEntities.OPHIODON.get(), OphiodonEntity.createAttributes().build());
        event.put(UPEntities.OTAROCYON.get(), OtarocyonEntity.createAttributes().build());
        event.put(UPEntities.PALAEOPHIS.get(), PalaeophisEntity.createAttributes().build());
        event.put(UPEntities.PALAEOPHIS_PART.get(), PalaeophisPartEntity.bakeAttributes().build());
        event.put(UPEntities.BABY_PALAEO.get(), PalaeolophisHatchlingEntity.createAttributes().build());
        event.put(UPEntities.PARACERATHERIUM.get(), ParaceratheriumEntity.createAttributes().build());
        event.put(UPEntities.PSILOPTERUS.get(), PsilopterusEntity.createAttributes().build());
        event.put(UPEntities.SMILODON.get(), SmilodonEntity.createAttributes().build());
        event.put(UPEntities.TALPANAS.get(), TalpanasEntity.createAttributes().build());

        // Misc mobs
        event.put(UPEntities.ENCRUSTED.get(), EncrustedEntity.createAttributes().build());
        event.put(UPEntities.SLUDGE.get(), SludgeEntity.createAttributes().build());

        // Non-living mobs
        event.put(UPEntities.BOOK_PALAEO.get(), BookPalaeophisEntity.createAttributes().build());
        event.put(UPEntities.ICEBERG_SMILODON.get(), WorldSpawnableEntity.bakeAttributes().build());
        event.put(UPEntities.ICEBERG_MAMMOTH.get(), WorldSpawnableEntity.bakeAttributes().build());

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

    }
}