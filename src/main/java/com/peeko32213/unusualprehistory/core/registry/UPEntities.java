package com.peeko32213.unusualprehistory.core.registry;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.*;
//import com.peeko32213.unusualprehistory.common.entity.arrow.PsittaccoArrow;
import com.peeko32213.unusualprehistory.common.entity.iceberg.IcebergMammoth;
import com.peeko32213.unusualprehistory.common.entity.iceberg.IcebergSmilodon;
import com.peeko32213.unusualprehistory.common.entity.msc.baby.*;
import com.peeko32213.unusualprehistory.common.entity.msc.part.EntityLeedsichthysPart;
import com.peeko32213.unusualprehistory.common.entity.msc.part.EntityPalaeophisPart;
import com.peeko32213.unusualprehistory.common.entity.msc.projectile.*;
import com.peeko32213.unusualprehistory.common.entity.msc.util.dino.EntityBookSnake;
import com.peeko32213.unusualprehistory.common.entity.plants.EntityPlant;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import static com.peeko32213.unusualprehistory.UnusualPrehistory.prefix;

@Mod.EventBusSubscriber(modid = UnusualPrehistory.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class UPEntities {
    public static final List<RegistryObject<?>> dinos = new ArrayList<>();
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, UnusualPrehistory.MODID);

    // Boat.
    public static final RegistryObject<EntityType<UPBoatEntity>> BOAT = ENTITIES.register("boat", () -> EntityType.Builder.<UPBoatEntity>of(UPBoatEntity::new, MobCategory.MISC).sized(1.375F, 0.5625F).clientTrackingRange(10).build(new ResourceLocation(UnusualPrehistory.MODID, "boat").toString()));
    public static final RegistryObject<EntityType<UPChestBoatEntity>> CHEST_BOAT = ENTITIES.register("chest_boat", () -> EntityType.Builder.<UPChestBoatEntity>of(UPChestBoatEntity::new, MobCategory.MISC).sized(1.375f, 0.5625f).clientTrackingRange(10).build(new ResourceLocation(UnusualPrehistory.MODID, "chest_boat").toString()));

    // Paleo mobs
    public static final RegistryObject<EntityType<EntityAmmonite>> AMMON = ENTITIES.register("ammon",
            () -> EntityType.Builder.of(EntityAmmonite::new, MobCategory.WATER_CREATURE).sized(0.7f, 0.7f)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "ammon").toString()));

    public static final RegistryObject<EntityType<EntityCotylorhynchus>> COTY = ENTITIES.register("coty",
            () -> EntityType.Builder.of(EntityCotylorhynchus::new, MobCategory.CREATURE).sized(2.0f, 1.8f)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "coty").toString()));

    public static final RegistryObject<EntityType<EntityDiplocaulus>> DIPLOCAULUS = ENTITIES.register("diplocaulus",
            () -> EntityType.Builder.of(EntityDiplocaulus::new, MobCategory.CREATURE).sized(0.8F, 0.65F)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "diplocaulus").toString()));

    public static final RegistryObject<EntityType<EntityDunkleosteus>> DUNK = ENTITIES.register("dunk",
            () -> EntityType.Builder.of(EntityDunkleosteus::new, MobCategory.WATER_CREATURE).sized(2.2F, 1.75F)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "dunk").toString()));

    public static final RegistryObject<EntityType<EntityBabyDunk>> BABY_DUNK = ENTITIES.register("baby_dunk",
            () -> EntityType.Builder.of(EntityBabyDunk::new, MobCategory.WATER_AMBIENT).sized(1.1f, 0.6f)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "baby_dunk").toString()));

    public static final RegistryObject<EntityType<EntityHyneria>> HYNERIA = ENTITIES.register("hyneria",
            () -> EntityType.Builder.of(EntityHyneria::new, MobCategory.WATER_CREATURE).sized(1.45f, 1.0f)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "hyneria").toString()));

    public static final RegistryObject<EntityType<EntityScaumenacia>> SCAU = ENTITIES.register("scau",
            () -> EntityType.Builder.of(EntityScaumenacia::new, MobCategory.WATER_AMBIENT).sized(0.75f, 0.6f)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "scau").toString()));

    public static final RegistryObject<EntityType<EntityStethacanthus>> STETHACANTHUS = ENTITIES.register("stethacanthus",
            () -> EntityType.Builder.of(EntityStethacanthus::new, MobCategory.WATER_CREATURE).sized(0.6f, 0.6f)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "stethacanthus").toString()));

    // Meso mobs
    public static final RegistryObject<EntityType<EntityAntarctopelta>> ANTARCO = ENTITIES.register("antarctopelta",
            () -> EntityType.Builder.of(EntityAntarctopelta::new, MobCategory.CREATURE).sized(2.1F, 1.8F)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "antarctopelta").toString()));

    public static final RegistryObject<EntityType<EntityAnurognathus>> ANURO = ENTITIES.register("anuro",
            () -> EntityType.Builder.of(EntityAnurognathus::new, MobCategory.CREATURE).sized(0.8f, 0.8f)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "anuro").toString()));

    public static final RegistryObject<EntityType<EntityArchelon>> ARCHELON = ENTITIES.register("archelon",
            () -> EntityType.Builder.of(EntityArchelon::new, MobCategory.WATER_CREATURE).sized(3.5f, 1.1f)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "archelon").toString()));

    public static final RegistryObject<EntityType<EntityAustroraptor>> AUSTRO = ENTITIES.register("austroraptor",
            () -> EntityType.Builder.of(EntityAustroraptor::new, MobCategory.CREATURE).sized(1.25F, 2.4F)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "austroraptor").toString()));

    public static final RegistryObject<EntityType<EntityBalaur>> BALAUR = ENTITIES.register("balaur",
            () -> EntityType.Builder.of(EntityBalaur::new, MobCategory.CREATURE).sized(0.9F, 1.15F)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "balaur").toString()));

    public static final RegistryObject<EntityType<EntityBeelzebufo>> BEELZ = ENTITIES.register("beelz",
            () -> EntityType.Builder.of(EntityBeelzebufo::new, MobCategory.CREATURE).sized(1.2f, 0.8f)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "beelz").toString()));

    public static final RegistryObject<EntityType<EntityBeelzebufoTadpole>> BEELZE_TADPOLE = ENTITIES.register("beelz_tadpole",
            () -> EntityType.Builder.of(EntityBeelzebufoTadpole::new, MobCategory.WATER_AMBIENT).sized(0.4f, 0.4f)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "beelz_tadpole").toString()));

    public static final RegistryObject<EntityType<EntityBrachiosaurus>> BRACHI = ENTITIES.register("brachi",
            () -> EntityType.Builder.of(EntityBrachiosaurus::new, MobCategory.CREATURE).sized(5.25F, 8.0F).setShouldReceiveVelocityUpdates(true).setUpdateInterval(1)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "brachi").toString()));

    public static final RegistryObject<EntityType<EntityEryon>> ERYON = ENTITIES.register("eryon",
            () -> EntityType.Builder.of(EntityEryon::new, MobCategory.CREATURE).sized(0.8f, 0.5f)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "eryon").toString()));

    public static final RegistryObject<EntityType<EntityHwachavenator>> HWACHA = ENTITIES.register("hwachavenator",
            () -> EntityType.Builder.of(EntityHwachavenator::new, MobCategory.CREATURE).sized(1.95F, 2.8F)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "hwachavenator").toString()));

    public static final RegistryObject<EntityType<EntityKentrosaurus>> KENTRO = ENTITIES.register("kentrosaurus",
            () -> EntityType.Builder.of(EntityKentrosaurus::new, MobCategory.CREATURE).sized(1.8F, 1.9F)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "kentrosaurus").toString()));

    public static final RegistryObject<EntityType<EntityKimmeridgebrachypteraeschnidium>> KIMMER = ENTITIES.register("kimmer",
            () -> EntityType.Builder.of(EntityKimmeridgebrachypteraeschnidium::new, MobCategory.CREATURE).sized(0.65f, 0.65f)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "kimmer").toString()));

    public static final RegistryObject<EntityType<EntityLeedsichthys>> LEEDSICHTHYS = ENTITIES.register("leedsichthys",
            () -> EntityType.Builder.of(EntityLeedsichthys::new, MobCategory.WATER_CREATURE).sized(8.0F, 5.0F)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "leedsichthys").toString()));

    public static final RegistryObject<EntityType<EntityLeedsichthysPart>> LEEDS_PART = ENTITIES.register("leeds_part",
            () -> EntityType.Builder.<EntityLeedsichthysPart>of(EntityLeedsichthysPart::new, MobCategory.MISC)
                    .sized(10.0F, 10.0F).setShouldReceiveVelocityUpdates(true)
                    .setUpdateInterval(1)
                    .noSummon()
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "leeds_part").toString()));

    public static final RegistryObject<EntityType<EntityMajungasaurus>> MAJUNGA = ENTITIES.register("majunga",
            () -> EntityType.Builder.of(EntityMajungasaurus::new, MobCategory.CREATURE).sized(1.35F, 1.8F)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "majunga").toString()));

    public static final RegistryObject<EntityType<EntityPachycephalosaurus>> PACHY = ENTITIES.register("pachy",
            () -> EntityType.Builder.of(EntityPachycephalosaurus::new, MobCategory.CREATURE).sized(1.2F, 1.9F)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "pachy").toString()));

    public static final RegistryObject<EntityType<EntityProtosphyraena>> PROTOSPHYRAENA = ENTITIES.register("protosphyraena",
            () -> EntityType.Builder.of(EntityProtosphyraena::new, MobCategory.WATER_CREATURE).sized(1.35f, 0.8f)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "protosphyraena").toString()));

    public static final RegistryObject<EntityType<EntityTriceratops>> TRIKE = ENTITIES.register("trike",
            () -> EntityType.Builder.of(EntityTriceratops::new, MobCategory.CREATURE).sized(3.1F, 4.4F)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "trike").toString()));

    public static final RegistryObject<EntityType<EntityTyrannosaurusRex>> REX = ENTITIES.register("rex",
            () -> EntityType.Builder.of(EntityTyrannosaurusRex::new, MobCategory.CREATURE).sized(3.3F, 4.5F)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "rex").toString()));

    public static final RegistryObject<EntityType<EntityUlughbegsaurus>> ULUG = ENTITIES.register("ulughbegsaurus",
            () -> EntityType.Builder.of(EntityUlughbegsaurus::new, MobCategory.CREATURE).sized(1.95F, 2.8F)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "ulughbegsaurus").toString()));

    public static final RegistryObject<EntityType<EntityVelociraptor>> VELOCI = ENTITIES.register("veloci",
            () -> EntityType.Builder.of(EntityVelociraptor::new, MobCategory.CREATURE).sized(0.9F, 0.9F)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "veloci").toString()));

    // Ceno mobs
    public static final RegistryObject<EntityType<EntityBarinasuchus>> BARINASUCHUS = ENTITIES.register("barinasuchus",
            () -> EntityType.Builder.of(EntityBarinasuchus::new, MobCategory.CREATURE).sized(2.4F, 2.2F)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "barinasuchus").toString()));

    public static final RegistryObject<EntityType<EntityGigantopithicus>> GIGANTOPITHICUS = ENTITIES.register("gigantopithicus",
            () -> EntityType.Builder.of(EntityGigantopithicus::new, MobCategory.CREATURE).sized(2.55F, 3.0F)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "gigantopithicus").toString()));

    public static final RegistryObject<EntityType<EntityMammoth>> MAMMOTH = ENTITIES.register("mammoth",
            () -> EntityType.Builder.of(EntityMammoth::new, MobCategory.CREATURE).sized(3.75F, 4.5F)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "mammoth").toString()));

    public static final RegistryObject<EntityType<EntityMegalania>> MEGALANIA = ENTITIES.register("megalania",
            () -> EntityType.Builder.of(EntityMegalania::new, MobCategory.CREATURE).sized(2.5F, 2.0F)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "megalania").toString()));

    public static final RegistryObject<EntityType<EntityMegatherium>> MEGATHERIUM = ENTITIES.register("megatherium",
            () -> EntityType.Builder.of(EntityMegatherium::new, MobCategory.CREATURE).sized(2.75F, 3.0F)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "megatherium").toString()));

    public static final RegistryObject<EntityType<EntityOphiodon>> OPHIODON = ENTITIES.register("ophiodon",
            () -> EntityType.Builder.of(EntityOphiodon::new, MobCategory.WATER_CREATURE).sized(1.4f, 0.8f)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "ophiodon").toString()));

    public static final RegistryObject<EntityType<EntityOtarocyon>> OTAROCYON = ENTITIES.register("otarocyon",
            () -> EntityType.Builder.of(EntityOtarocyon::new, MobCategory.CREATURE).sized(1.0F, 1.0F)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "otarocyon").toString()));

    public static final RegistryObject<EntityType<EntityPalaeophis>> PALAEOPHIS = ENTITIES.register("palaeophis",
            () -> EntityType.Builder.of(EntityPalaeophis::new, MobCategory.WATER_CREATURE).sized(0.8F, 0.8F)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "palaeophis").toString()));

    public static final RegistryObject<EntityType<EntityPalaeophisPart>> PALAEOPHIS_PART = ENTITIES.register("palaeophis_part",
            () -> EntityType.Builder.<EntityPalaeophisPart>of(EntityPalaeophisPart::new, MobCategory.MISC)
                    .sized(0.8F, 0.8F).setShouldReceiveVelocityUpdates(true)
                    .setUpdateInterval(1)
                    .noSummon()
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "palaeophis_part").toString()));

    public static final RegistryObject<EntityType<EntityBabyPalaeolophis>> BABY_PALAEO = ENTITIES.register("palaeophis_hatchling",
            () -> EntityType.Builder.of(EntityBabyPalaeolophis::new, MobCategory.WATER_AMBIENT).sized(0.75f, 0.3f)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "palaeophis_hatchling").toString()));

    public static final RegistryObject<EntityType<EntityParaceratherium>> PARACERATHERIUM = ENTITIES.register("paraceratherium",
            () -> EntityType.Builder.of(EntityParaceratherium::new, MobCategory.CREATURE).sized(4.25F, 7.5F)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "paraceratherium").toString()));

    public static final RegistryObject<EntityType<EntitySmilodon>> SMILODON = ENTITIES.register("smilodon",
            () -> EntityType.Builder.of(EntitySmilodon::new, MobCategory.CREATURE).sized(1F, 1.6F)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "smilodon").toString()));

    public static final RegistryObject<EntityType<EntityTalpanas>> TALPANAS = ENTITIES.register("talpanas",
            () -> EntityType.Builder.of(EntityTalpanas::new, MobCategory.CREATURE).sized(0.8F, 0.8F)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "talpanas").toString()));

    // Misc mobs
    public static final RegistryObject<EntityType<EntityEncrusted>> ENCRUSTED = ENTITIES.register("encrusted",
            () -> EntityType.Builder.of(EntityEncrusted::new, MobCategory.MONSTER).sized(1.5F, 1.95F)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "encrusted").toString()));

    public static final RegistryObject<EntityType<EntitySludge>> SLUDGE = ENTITIES.register("sludge",
            () -> EntityType.Builder.of(EntitySludge::new, MobCategory.MONSTER).sized(2.0f, 2.0f)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "sludge").toString()));

    // Non-living mobs

    public static final RegistryObject<EntityType<EntityAmberShot>> AMBER_SHOT = ENTITIES.register(
            "amber_shot", () -> EntityType.Builder.<EntityAmberShot>of(EntityAmberShot::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F).clientTrackingRange(9)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "amber_shot").toString()));

    public static final RegistryObject<EntityType<EntityHwachaSpike>> HWACHA_SPIKE = ENTITIES.register(
            "hwacha_spike", () -> EntityType.Builder.<EntityHwachaSpike>of(EntityHwachaSpike::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F).clientTrackingRange(9).noSummon()
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "hwacha_spike").toString()));

    public static final RegistryObject<EntityType<IcebergMammoth>> ICEBERG_MAMMOTH = ENTITIES.register("iceberg_mammoth",
            () -> EntityType.Builder.<IcebergMammoth>of(IcebergMammoth::new, MobCategory.CREATURE)
                    .fireImmune()
                    .sized(5, 5)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "icberg_mammoth").toString()));

    public static final RegistryObject<EntityType<IcebergSmilodon>> ICEBERG_SMILODON = ENTITIES.register("iceberg_smilodon",
            () -> EntityType.Builder.<IcebergSmilodon>of(IcebergSmilodon::new, MobCategory.CREATURE)
                    .fireImmune()
                    .sized(2, 2)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "iceberg_smilodon").toString()));

    public static final RegistryObject<EntityType<EntityOpalescentPearl>> OPALESCENT_PEARL = ENTITIES.register("opalescent_pearl",
            () ->  EntityType.Builder.<EntityOpalescentPearl>of(EntityOpalescentPearl::new, MobCategory.MISC)
                    .sized(0.25F, 0.25F)
                    .clientTrackingRange(4)
                    .updateInterval(10)
                    .build(prefix("opalescent_pearl").toString()));

    public static final RegistryObject<EntityType<EntityOpalescentShuriken>> OPALESCENT_SHURIKEN = ENTITIES.register("opalescent_shuriken",
            () ->  EntityType.Builder.<EntityOpalescentShuriken>of(EntityOpalescentShuriken::new, MobCategory.MISC)
                    .sized(0.25F, 0.25F)
                    .clientTrackingRange(4)
                    .updateInterval(10)
                    .build(prefix("opalescent_shuriken").toString()));

    //    public static final RegistryObject<EntityType<EntityTyrannosaurineJarate>> T_JARATE = ENTITIES.register("t_jarate",
//            () ->  EntityType.Builder.<EntityTyrannosaurineJarate>of(EntityTyrannosaurineJarate::new, MobCategory.MISC)
//                    .sized(0.25F, 0.25F)
//                    .clientTrackingRange(4)
//                    .updateInterval(10)
//                    .build(prefix("t_jarate").toString()));

    public static final RegistryObject<EntityType<EntityBookSnake>> BOOK_PALAEO = ENTITIES.register("palaeo_book_palaeo",
            () -> EntityType.Builder.<EntityBookSnake>of(EntityBookSnake::new, MobCategory.MISC)
                    .noSummon()
                    .fireImmune()
                    .sized(2, 2)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "palaeo_book_palaeo").toString()));

    //Plants

    public static final RegistryObject<EntityType<EntityPlant>> FOXXI_SAPLING = ENTITIES.register("foxxi_sapling",
            () -> EntityType.Builder.<EntityPlant>of(EntityPlant::new, MobCategory.MISC)
                    .noSummon()
                    .fireImmune()
                    .sized(2, 2)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "foxxi_sapling").toString()));

    public static final RegistryObject<EntityType<EntityPlant>> HORSETAIL = ENTITIES.register("horsetail",
            () -> EntityType.Builder.<EntityPlant>of(EntityPlant::new, MobCategory.MISC)
                    .noSummon()
                    .fireImmune()
                    .sized(1, 1)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "horsetail").toString()));

    public static final RegistryObject<EntityType<EntityPlant>> TALL_HORSETAIL = ENTITIES.register("tall_horsetail",
            () -> EntityType.Builder.<EntityPlant>of(EntityPlant::new, MobCategory.MISC)
                    .noSummon()
                    .fireImmune()
                    .sized(2, 2)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "tall_horsetail").toString()));

    public static final RegistryObject<EntityType<EntityPlant>> LEEFRUCTUS = ENTITIES.register("leefructus",
            () -> EntityType.Builder.<EntityPlant>of(EntityPlant::new, MobCategory.MISC)
                    .noSummon()
                    .fireImmune()
                    .sized(1, 1)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "leefructus").toString()));

    public static final RegistryObject<EntityType<EntityPlant>> BENNETTITALES = ENTITIES.register("bennett",
            () -> EntityType.Builder.<EntityPlant>of(EntityPlant::new, MobCategory.MISC)
                    .noSummon()
                    .fireImmune()
                    .sized(1, 1)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "bennett").toString()));

    public static final RegistryObject<EntityType<EntityPlant>> ARCHAEOSIGILARIA = ENTITIES.register("archaeos",
            () -> EntityType.Builder.<EntityPlant>of(EntityPlant::new, MobCategory.MISC)
                    .noSummon()
                    .fireImmune()
                    .sized(1, 1)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "archaeos").toString()));

    public static final RegistryObject<EntityType<EntityPlant>> SARACENIA = ENTITIES.register("sarracenia",
            () -> EntityType.Builder.<EntityPlant>of(EntityPlant::new, MobCategory.MISC)
                    .noSummon()
                    .fireImmune()
                    .sized(1, 1)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "sarracenia").toString()));

    public static final RegistryObject<EntityType<EntityPlant>> TALL_SARACENIA = ENTITIES.register("tall_sarracenia",
            () -> EntityType.Builder.<EntityPlant>of(EntityPlant::new, MobCategory.MISC)
                    .noSummon()
                    .fireImmune()
                    .sized(2, 2)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "tall_sarracenia").toString()));

    public static final RegistryObject<EntityType<EntityPlant>> GINKGO_SAPLING = ENTITIES.register("ginkgo_sapling",
            () -> EntityType.Builder.<EntityPlant>of(EntityPlant::new, MobCategory.MISC)
                    .noSummon()
                    .fireImmune()
                    .sized(1, 1)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "ginkgo_sapling").toString()));


    public static final RegistryObject<EntityType<EntityPlant>> DRYO_SAPLING = ENTITIES.register("dryo_sapling",
            () -> EntityType.Builder.<EntityPlant>of(EntityPlant::new, MobCategory.MISC)
                    .noSummon()
                    .fireImmune()
                    .sized(1, 1)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "dryo_sapling").toString()));

    public static final RegistryObject<EntityType<EntityPlant>> CLATHRODICTYON = ENTITIES.register("clathrodictyon",
            () -> EntityType.Builder.<EntityPlant>of(EntityPlant::new, MobCategory.MISC)
                    .noSummon()
                    .fireImmune()
                    .sized(1, 1)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "clathrodictyon").toString()));

    public static final RegistryObject<EntityType<EntityPlant>> ARCHAEFRUCTUS = ENTITIES.register("archaefructus",
            () -> EntityType.Builder.<EntityPlant>of(EntityPlant::new, MobCategory.MISC)
                    .fireImmune()
                    .sized(1, 1)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "archaefructus").toString()));

    public static final RegistryObject<EntityType<EntityPlant>> NELUMBITES = ENTITIES.register("nelumbites",
            () -> EntityType.Builder.<EntityPlant>of(EntityPlant::new, MobCategory.MISC)
                    .noSummon()
                    .fireImmune()
                    .sized(1, 1)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "nelumbites").toString()));

    public static final RegistryObject<EntityType<EntityPlant>> QUEREUXIA = ENTITIES.register("quereuxia",
            () -> EntityType.Builder.<EntityPlant>of(EntityPlant::new, MobCategory.MISC)
                    .noSummon()
                    .fireImmune()
                    .sized(1, 1)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "quereuxia").toString()));

    public static final RegistryObject<EntityType<EntityPlant>> RAIGUENRAYUN = ENTITIES.register("raiguenrayun",
            () -> EntityType.Builder.<EntityPlant>of(EntityPlant::new, MobCategory.MISC)
                    .noSummon()
                    .fireImmune()
                    .sized(2, 2)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "raiguenrayun").toString()));

//    public static final RegistryObject<EntityType<PsittaccoArrow>> PSITTACCO_ARROW = ENTITIES.register("psittacco_arrow",
//            () -> EntityType.Builder.<PsittaccoArrow>of(PsittaccoArrow::new, MobCategory.MISC)
//                    .sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(20).setCustomClientFactory(PsittaccoArrow::new)
//                    .build(prefix("psittacco_arrow").toString()));

    // Unfinished 1.6 stuff
//    public static final RegistryObject<EntityType<EntityJawlessFish>> JAWLESS_FISH = ENTITIES.register("jawless_fish",
//            () -> EntityType.Builder.of(EntityJawlessFish::new, MobCategory.WATER_AMBIENT).sized(0.6f, 0.6f)
//                    .build(new ResourceLocation(UnusualPrehistory.MODID, "jawless_fish").toString()));
//    public static final RegistryObject<EntityType<DinosaurLandEgg>> DINO_LAND_EGG = ENTITIES.register("dino_land_egg",
//            () -> EntityType.Builder.<DinosaurLandEgg>of(DinosaurLandEgg::new, MobCategory.MISC)
//                    .sized(1, 1)
//                    .build(prefix("dino_land_egg").toString()));

//    public static final RegistryObject<EntityType<EntityLongisquama>> LONGISQUAMA = registerLandDinoWithEggs("longisquama",
//            () -> EntityType.Builder.of(EntityLongisquama::new, MobCategory.CREATURE).sized(1.0F, 1.0F)
//                    .build(new ResourceLocation(UnusualPrehistory.MODID, "longisquama").toString()),
//            EggSize.SMALL, 1200, 0x5e4fa7, 0x7edbdd);
//    public static final RegistryObject<EntityType<EntityTartuosteus>> TARTUOSTEUS = ENTITIES.register("tartuosteus",
//            () -> EntityType.Builder.of(EntityTartuosteus::new, MobCategory.WATER_CREATURE).sized(2.0f, 0.8f)
//                    .build(new ResourceLocation(UnusualPrehistory.MODID, "tartuosteus").toString()));
//    public static final RegistryObject<EntityType<EntityPsittacosaurus>> PSITTACO = registerLandDinoWithEggs("psittaco",
//            () -> EntityType.Builder.of(EntityPsittacosaurus::new, MobCategory.CREATURE).sized(1.0F, 1.0F)
//                    .build(new ResourceLocation(UnusualPrehistory.MODID, "psittaco").toString()),
//            EggSize.SMALL, 1200 ,0xa04f2a, 0xd6b560);
//    public static final RegistryObject<EntityType<EntityTanystropheus>> TANY = registerLandDinoWithEggs("tany",
//            () -> EntityType.Builder.of(EntityTanystropheus::new, MobCategory.CREATURE).sized(1.8F, 0.7F)
//                    .build(new ResourceLocation(UnusualPrehistory.MODID, "tany").toString()),
//            EggSize.SMALL, 1200, 0x08090d, 0xf2f6f8);
//    public static final RegistryObject<EntityType<EntityKaprosuchus>> KAPROSUCHUS = registerLandDinoWithEggs("kaprosuchus",
//            () -> EntityType.Builder.of(EntityKaprosuchus::new, MobCategory.CREATURE).sized(1.8F, 1.1F)
//                    .build(new ResourceLocation(UnusualPrehistory.MODID, "kaprosuchus").toString()),
//            EggSize.SMALL, 1200, 0x322f2c, 0xf6f050);
//    public static final RegistryObject<EntityType<EntityPsilopterus>> PSILOPTERUS = registerLandDinoWithEggs("psilopterus",
//            () -> EntityType.Builder.of(EntityPsilopterus::new, MobCategory.CREATURE).sized(1.1F, 1.5F)
//                    .build(new ResourceLocation(UnusualPrehistory.MODID, "psilopterus").toString()),
//            EggSize.SMALL, 1200, 0xc59d7d, 0xc27d28);
//    public static final RegistryObject<EntityType<EntityHynerpeton>> HYNERPETON = registerLandDinoWithEggs("hynerpteon",
//            () -> EntityType.Builder.of(EntityHynerpeton::new, MobCategory.CREATURE).sized(1.1F, 1.5F)
//                    .build(new ResourceLocation(UnusualPrehistory.MODID, "hynerpteon").toString()),
//            EggSize.SMALL, 1200, 0x1c1614, 0xb6a339);


//    public static final RegistryObject<EntityType<EntityPterodaustro>> PTERODAUSTRO = registerLandDinoWithEggs("pterodaustro",
//            () -> EntityType.Builder.of(EntityPterodaustro::new, MobCategory.CREATURE).sized(0.8f, 0.8f)
//                    .build(new ResourceLocation(UnusualPrehistory.MODID, "pterodaustro").toString()),
//            EggSize.SMALL, 1200,0xc93660, 0xfff3fd );
//    private static <T extends EntityType<?>> RegistryObject<T> registerLandDinoWithEggs(String name, Supplier<? extends T> dinosaur, EggSize eggSize,  int hatchTime ,int eggBaseColor, int eggSpotColor) {
//        return registerLandDinoWithEggs(name, dinosaur, eggSize, EggVariant.SPOTS, hatchTime, eggBaseColor, eggSpotColor, true);
//    }
//    private static <T extends EntityType<?>> RegistryObject<T> registerLandDinoWithEggs(String name, Supplier<? extends T> dinosaur, EggSize eggSize,  int hatchTime ,int eggBaseColor, int eggSpotColor, boolean registerSpawnEgg) {
//        return registerLandDinoWithEggs(name, dinosaur, eggSize, EggVariant.SPOTS, hatchTime, eggBaseColor, eggSpotColor, registerSpawnEgg);
//    }
//    private static <T extends EntityType<?>> RegistryObject<T> registerLandDinoWithEggs(String name, Supplier<? extends T> dinosaur, EggSize eggSize, EggVariant variant,  int hatchTime ,int eggBaseColor, int eggSpotColor) {
//        return registerLandDinoWithEggs(name, dinosaur, eggSize, variant, hatchTime, eggBaseColor, eggSpotColor, true);
//    }
//    private static <T extends EntityType<?>> RegistryObject<T> registerLandDinoWithEggs(String name, Supplier<? extends T> dinosaur, EggSize eggSize, EggVariant variant,  int hatchTime ,int eggBaseColor, int eggSpotColor, boolean registerSpawnEgg) {
//        RegistryObject<T> dino = ENTITIES.register(name, dinosaur);
//        UPItems.ITEMS.register(name+"_entity_egg", () -> new DinosaurLandEggItem(dino, eggSize, variant, hatchTime, eggBaseColor, eggSpotColor));
//        if(registerSpawnEgg) {
//            UPItems.ITEMS.register(name + "_spawn_egg", () -> new ForgeSpawnEggItem(() -> (EntityType<? extends Mob>) dino.get(), eggBaseColor, eggSpotColor, new Item.Properties()));
//        }
//        dinos.add(dino);
//        return dino;
//    }
//    private static <T extends EntityType<?>> RegistryObject<T> registerLandDinoWithEggsAndBaby(String name, Supplier<? extends T> dinosaur, Supplier<? extends EntityType<?>> baby, EggSize eggSize,  int hatchTime ,int eggBaseColor, int eggSpotColor, boolean registerSpawnEgg) {
//        return registerLandDinoWithEggsAndBaby(name, dinosaur, baby,eggSize, EggVariant.SPOTS, hatchTime, eggBaseColor, eggSpotColor, registerSpawnEgg);
//    }
//    private static <T extends EntityType<?>> RegistryObject<T> registerLandDinoWithEggsAndBaby(String name, Supplier<? extends T> dinosaur, Supplier<? extends EntityType<?>> baby, EggSize eggSize, EggVariant variant,  int hatchTime ,int eggBaseColor, int eggSpotColor) {
//        return registerLandDinoWithEggsAndBaby(name, dinosaur, baby,eggSize, variant, hatchTime, eggBaseColor, eggSpotColor, true);
//    }
//    private static <T extends EntityType<?>> RegistryObject<T> registerLandDinoWithEggsAndBaby(String name, Supplier<? extends T> dinosaur, Supplier<? extends EntityType<?>> baby, EggSize eggSize,  int hatchTime ,int eggBaseColor, int eggSpotColor) {
//        return registerLandDinoWithEggsAndBaby(name, dinosaur, baby,eggSize, EggVariant.SPOTS, hatchTime, eggBaseColor, eggSpotColor, true);
//    }
//    private static <T extends EntityType<?>> RegistryObject<T> registerLandDinoWithEggsAndBaby(String name, Supplier<? extends T> dinosaur, Supplier<? extends EntityType<?>> baby, EggSize eggSize, EggVariant variant, int hatchTime , int eggBaseColor, int eggSpotColor, boolean registerSpawnEgg) {
//        RegistryObject<T> dino = ENTITIES.register(name, dinosaur);
//        UPItems.ITEMS.register(name+"_entity_egg", () -> new DinosaurLandEggItem(baby, eggSize, variant, hatchTime, eggBaseColor, eggSpotColor));
//        if(registerSpawnEgg) {
//            UPItems.ITEMS.register(name + "_spawn_egg", () -> new ForgeSpawnEggItem(() -> (EntityType<? extends Mob>) dino.get(), eggBaseColor, eggSpotColor, new Item.Properties()));
//        }
//        dinos.add(dino);
//        return dino;
//    }
}
