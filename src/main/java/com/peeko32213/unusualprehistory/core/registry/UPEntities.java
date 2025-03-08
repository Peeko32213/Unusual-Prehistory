package com.peeko32213.unusualprehistory.core.registry;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.*;
import com.peeko32213.unusualprehistory.common.entity.custom.eggs.PrehistoricEggEntity;
import com.peeko32213.unusualprehistory.common.entity.custom.eggs.EggSize;
import com.peeko32213.unusualprehistory.common.entity.custom.eggs.EggVariant;
import com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.monster.SludgeEntity;
import com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.*;
import com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.monster.EncrustedEntity;
import com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.aquatic.*;
import com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.flying.AnurognathusEntity;
import com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.flying.KimmeridgebrachypteraeschnidiumEntity;
import com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.flying.PterodaustroEntity;
import com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.semi_aquatic.*;
import com.peeko32213.unusualprehistory.common.entity.projectile.*;
import com.peeko32213.unusualprehistory.common.entity.custom.iceberg.IcebergMammoth;
import com.peeko32213.unusualprehistory.common.entity.custom.iceberg.IcebergSmilodon;
import com.peeko32213.unusualprehistory.common.entity.custom.part.LeedsichthysPartEntity;
import com.peeko32213.unusualprehistory.common.entity.custom.part.PalaeophisPartEntity;
import com.peeko32213.unusualprehistory.common.entity.custom.base.PalaeophisBookEntity;
import com.peeko32213.unusualprehistory.common.entity.plants.EntityPlant;
import com.peeko32213.unusualprehistory.common.item.PrehistoricEggItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
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

    public static final List<RegistryObject<?>> prehistorics = new ArrayList<>();
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, UnusualPrehistory.MODID);

    // Boat.
    public static final RegistryObject<EntityType<UPBoatEntity>> BOAT = ENTITIES.register("boat", () -> EntityType.Builder.<UPBoatEntity>of(UPBoatEntity::new, MobCategory.MISC).sized(1.375F, 0.5625F).clientTrackingRange(10).build(new ResourceLocation(UnusualPrehistory.MODID, "boat").toString()));
    public static final RegistryObject<EntityType<UPChestBoatEntity>> CHEST_BOAT = ENTITIES.register("chest_boat", () -> EntityType.Builder.<UPChestBoatEntity>of(UPChestBoatEntity::new, MobCategory.MISC).sized(1.375f, 0.5625f).clientTrackingRange(10).build(new ResourceLocation(UnusualPrehistory.MODID, "chest_boat").toString()));

    // Paleo mobs
    public static final RegistryObject<EntityType<AmmoniteEntity>> AMMON = ENTITIES.register("ammon",
            () -> EntityType.Builder.of(AmmoniteEntity::new, MobCategory.WATER_CREATURE).sized(0.7f, 0.7f)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "ammon").toString()));

    public static final RegistryObject<EntityType<CotylorhynchusEntity>> COTY = registerPrehistoricCreatureWithEgg("coty",
            () -> EntityType.Builder.of(CotylorhynchusEntity::new, MobCategory.CREATURE).sized(2.0F, 1.8F)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "coty").toString()),
            EggSize.SMALL, EggVariant.SPOTS, 1200, 0x843222, 0xdfd7ad);

    public static final RegistryObject<EntityType<DiplocaulusEntity>> DIPLOCAULUS = ENTITIES.register("diplocaulus",
            () -> EntityType.Builder.of(DiplocaulusEntity::new, MobCategory.CREATURE).sized(0.8F, 0.65F)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "diplocaulus").toString()));

    public static final RegistryObject<EntityType<DunkleosteusEntity>> DUNK = ENTITIES.register("dunk",
            () -> EntityType.Builder.of(DunkleosteusEntity::new, MobCategory.WATER_CREATURE).sized(2.2F, 1.75F)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "dunk").toString()));

    public static final RegistryObject<EntityType<EntityBabyDunk>> BABY_DUNK = ENTITIES.register("baby_dunk",
            () -> EntityType.Builder.of(EntityBabyDunk::new, MobCategory.WATER_AMBIENT).sized(1.1f, 0.6f)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "baby_dunk").toString()));

    public static final RegistryObject<EntityType<EdaphosaurusEntity>> EDAPHOSAURUS = registerPrehistoricCreatureWithEgg("edaphosaurus",
            () -> EntityType.Builder.of(EdaphosaurusEntity::new, MobCategory.CREATURE).sized(1.4F, 1.75F)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "edaphosaurus").toString()),
            EggSize.SMALL, EggVariant.SPOTS, 1200, 0x632623, 0xf6e8e8);

    public static final RegistryObject<EntityType<EstemmenosuchusEntity>> ESTEMMENOSUCHUS = registerPrehistoricCreatureWithEgg("estemmenosuchus",
            () -> EntityType.Builder.of(EstemmenosuchusEntity::new, MobCategory.CREATURE).sized(3.0F, 3.25F)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "estemmenosuchus").toString()),
            EggSize.SMALL, EggVariant.SPOTS, 1200, 0x151521, 0xff6cba);

    public static final RegistryObject<EntityType<HyneriaEntity>> HYNERIA = ENTITIES.register("hyneria",
            () -> EntityType.Builder.of(HyneriaEntity::new, MobCategory.WATER_CREATURE).sized(1.45f, 1.0f)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "hyneria").toString()));

    public static final RegistryObject<EntityType<HynerpetonEntity>> HYNERPETON = registerPrehistoricCreatureWithEgg("hynerpteon",
            () -> EntityType.Builder.of(HynerpetonEntity::new, MobCategory.CREATURE).sized(3.0F, 3.25F)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "hynerpteon").toString()),
            EggSize.SMALL, EggVariant.SPOTS, 1200, 0x140f0e, 0xc8b523);

    public static final RegistryObject<EntityType<JawlessFishEntity>> JAWLESS_FISH = ENTITIES.register("jawless_fish",
            () -> EntityType.Builder.of(JawlessFishEntity::new, MobCategory.WATER_AMBIENT).sized(0.6f, 0.6f)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "jawless_fish").toString()));

    public static final RegistryObject<EntityType<PterygotusEntity>> PTERYGOTUS = ENTITIES.register("pterygotus",
            () -> EntityType.Builder.of(PterygotusEntity::new, MobCategory.CREATURE).sized(2.75F, 1.0F)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "pterygotus").toString()));

    public static final RegistryObject<EntityType<ScaumenaciaEntity>> SCAU = ENTITIES.register("scau",
            () -> EntityType.Builder.of(ScaumenaciaEntity::new, MobCategory.WATER_AMBIENT).sized(0.75f, 0.6f)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "scau").toString()));

    public static final RegistryObject<EntityType<StethacanthusEntity>> STETHACANTHUS = ENTITIES.register("stethacanthus",
            () -> EntityType.Builder.of(StethacanthusEntity::new, MobCategory.WATER_CREATURE).sized(0.6f, 0.6f)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "stethacanthus").toString()));

    public static final RegistryObject<EntityType<TartuosteusEntity>> TARTUOSTEUS = ENTITIES.register("tartuosteus",
            () -> EntityType.Builder.of(TartuosteusEntity::new, MobCategory.WATER_CREATURE).sized(2.0f, 0.8f)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "tartuosteus").toString()));

    // Meso mobs

    public static final RegistryObject<EntityType<AntarctopeltaEntity>> ANTARCO = registerPrehistoricCreatureWithEgg("antarctopelta",
            () -> EntityType.Builder.of(AntarctopeltaEntity::new, MobCategory.CREATURE).sized(2.1F, 1.8F)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "antarctopelta").toString()),
            EggSize.SMALL, EggVariant.SPOTS, 1200, 0x221a19, 0xe6e1d4);

    public static final RegistryObject<EntityType<AnurognathusEntity>> ANURO = registerPrehistoricCreatureWithEgg("anuro",
            () -> EntityType.Builder.of(AnurognathusEntity::new, MobCategory.CREATURE).sized(0.8F, 0.8F)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "anuro").toString()),
            EggSize.SMALL, EggVariant.SPOTS, 1200, 0x4f4941, 0xfaf15f);

    public static final RegistryObject<EntityType<ArchelonEntity>> ARCHELON = registerPrehistoricCreatureWithEgg("archelon",
            () -> EntityType.Builder.of(ArchelonEntity::new, MobCategory.CREATURE).sized(3.5F, 1.1F)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "archelon").toString()),
            EggSize.SMALL, EggVariant.SPOTS, 1200, 0x1a1f29, 0x7ca9a5);

    public static final RegistryObject<EntityType<AustroraptorEntity>> AUSTRO = registerPrehistoricCreatureWithEgg("austroraptor",
            () -> EntityType.Builder.of(AustroraptorEntity::new, MobCategory.CREATURE).sized(1.25F, 1.25F)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "austroraptor").toString()),
            EggSize.SMALL, EggVariant.SPOTS, 1200, 0xefebea, 0xc72727);

    public static final RegistryObject<EntityType<BalaurEntity>> BALAUR = registerPrehistoricCreatureWithEgg("balaur",
            () -> EntityType.Builder.of(BalaurEntity::new, MobCategory.CREATURE).sized(0.9F, 1.15F)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "balaur").toString()),
            EggSize.SMALL, EggVariant.SPOTS, 1200, 0x1f6731, 0xe5cb36);

    public static final RegistryObject<EntityType<BeelzebufoEntity>> BEELZ = ENTITIES.register("beelz",
            () -> EntityType.Builder.of(BeelzebufoEntity::new, MobCategory.CREATURE).sized(1.2f, 0.8f)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "beelz").toString()));

    public static final RegistryObject<EntityType<BeelzebufoTadpoleEntity>> BEELZE_TADPOLE = ENTITIES.register("beelz_tadpole",
            () -> EntityType.Builder.of(BeelzebufoTadpoleEntity::new, MobCategory.WATER_AMBIENT).sized(0.4f, 0.4f)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "beelz_tadpole").toString()));

    public static final RegistryObject<EntityType<BrachiosaurusEntity>> BRACHI = registerPrehistoricCreatureWithEgg("brachi",
            () -> EntityType.Builder.of(BrachiosaurusEntity::new, MobCategory.CREATURE).sized(5.25F, 8.0F).setShouldReceiveVelocityUpdates(true).setUpdateInterval(1)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "brachi").toString()),
            EggSize.SMALL, EggVariant.SPOTS, 1200, 0x425860, 0x0a0d18);

    public static final RegistryObject<EntityType<EryonEntity>> ERYON = ENTITIES.register("eryon",
            () -> EntityType.Builder.of(EryonEntity::new, MobCategory.CREATURE).sized(0.8f, 0.5f)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "eryon").toString()));

    public static final RegistryObject<EntityType<GlobidensEntity>> GLOBIDENS = registerPrehistoricCreatureWithEgg("globidens",
            () -> EntityType.Builder.of(GlobidensEntity::new, MobCategory.CREATURE).sized(2.5F, 1.5F)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "globidens").toString()),
            EggSize.SMALL, EggVariant.SPOTS, 1200, 0x1a2525, 0x867e68);

    public static final RegistryObject<EntityType<HwachavenatorEntity>> HWACHA = registerPrehistoricCreatureWithEgg("hwachavenator",
            () -> EntityType.Builder.of(HwachavenatorEntity::new, MobCategory.CREATURE).sized(1.95F, 2.8F)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "hwachavenator").toString()),
            EggSize.SMALL, EggVariant.SPOTS, 1200, 0x1a2122, 0xf4f435);

    public static final RegistryObject<EntityType<KaprosuchusEntity>> KAPROSUCHUS = registerPrehistoricCreatureWithEgg("kaprosuchus",
            () -> EntityType.Builder.of(KaprosuchusEntity::new, MobCategory.CREATURE).sized(1.3F, 1.1F)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "kaprosuchus").toString()),
            EggSize.SMALL, EggVariant.SPOTS, 1200, 0x0e0c0b, 0xe78f3c);

    public static final RegistryObject<EntityType<KentrosaurusEntity>> KENTRO = registerPrehistoricCreatureWithEgg("kentrosaurus",
            () -> EntityType.Builder.of(KentrosaurusEntity::new, MobCategory.CREATURE).sized(1.8F, 1.9F)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "kentrosaurus").toString()),
            EggSize.SMALL, EggVariant.SPOTS, 1200, 0x273817, 0xdee1c9);

    public static final RegistryObject<EntityType<KimmeridgebrachypteraeschnidiumEntity>> KIMMER = ENTITIES.register("kimmeridgebrachypteraeschnidium",
            () -> EntityType.Builder.of(KimmeridgebrachypteraeschnidiumEntity::new, MobCategory.CREATURE).sized(0.65f, 0.65f)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "kimmeridgebrachypteraeschnidium").toString()));

    public static final RegistryObject<EntityType<LeedsichthysEntity>> LEEDSICHTHYS = ENTITIES.register("leedsichthys",
            () -> EntityType.Builder.of(LeedsichthysEntity::new, MobCategory.WATER_CREATURE).sized(7.75F, 5.25F)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "leedsichthys").toString()));

    public static final RegistryObject<EntityType<LeedsichthysPartEntity>> LEEDS_PART = ENTITIES.register("leeds_part",
            () -> EntityType.Builder.<LeedsichthysPartEntity>of(LeedsichthysPartEntity::new, MobCategory.MISC)
                    .sized(10.0F, 10.0F).setShouldReceiveVelocityUpdates(true)
                    .setUpdateInterval(1)
                    .noSummon()
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "leeds_part").toString()));

    public static final RegistryObject<EntityType<LongisquamaEntity>> LONGISQUAMA = registerPrehistoricCreatureWithEgg("longisquama",
            () -> EntityType.Builder.of(LongisquamaEntity::new, MobCategory.CREATURE).sized(1.0F, 1.0F)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "longisquama").toString()),
            EggSize.SMALL, EggVariant.SPOTS, 1200, 0x5e4fa7, 0x7edbdd);

    public static final RegistryObject<EntityType<MajungasaurusEntity>> MAJUNGA = registerPrehistoricCreatureWithEgg("majunga",
            () -> EntityType.Builder.of(MajungasaurusEntity::new, MobCategory.CREATURE).sized(1.35F, 1.8F)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "majunga").toString()),
            EggSize.SMALL, EggVariant.SPOTS, 1200, 0x1d600e, 0xcad9b0);

    public static final RegistryObject<EntityType<OviraptorEntity>> OVIRAPTOR = registerPrehistoricCreatureWithEgg("oviraptor",
            () -> EntityType.Builder.of(OviraptorEntity::new, MobCategory.CREATURE).sized(0.8F, 1.2F)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "oviraptor").toString()),
            EggSize.SMALL, EggVariant.SPOTS, 1200, 0x111e27, 0x3e9e67);

    public static final RegistryObject<EntityType<PachycephalosaurusEntity>> PACHY = registerPrehistoricCreatureWithEgg("pachy",
            () -> EntityType.Builder.of(PachycephalosaurusEntity::new, MobCategory.CREATURE).sized(1.2F, 1.9F)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "pachy").toString()),
            EggSize.SMALL, EggVariant.SPOTS, 1200, 0x70837f, 0x191a2a);

    public static final RegistryObject<EntityType<ProtosphyraenaEntity>> PROTOSPHYRAENA = ENTITIES.register("protosphyraena",
            () -> EntityType.Builder.of(ProtosphyraenaEntity::new, MobCategory.WATER_CREATURE).sized(1.35f, 0.8f)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "protosphyraena").toString()));

    public static final RegistryObject<EntityType<PsittacosaurusEntity>> PSITTACO = registerPrehistoricCreatureWithEgg("psittacosaurus",
            () -> EntityType.Builder.of(PsittacosaurusEntity::new, MobCategory.CREATURE).sized(1.0F, 1.0F)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "psittacosaurus").toString()),
            EggSize.SMALL, EggVariant.SPOTS, 1200, 0xa04f2a, 0xe8d49b);

    public static final RegistryObject<EntityType<PterodaustroEntity>> PTERODAUSTRO = registerPrehistoricCreatureWithEgg("pterodaustro",
            () -> EntityType.Builder.of(PterodaustroEntity::new, MobCategory.CREATURE).sized(0.8F, 0.8F)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "pterodaustro").toString()),
            EggSize.SMALL, EggVariant.SPOTS, 1200, 0xc93660, 0xf9e3f9);

    public static final RegistryObject<EntityType<TanystropheusEntity>> TANY = registerPrehistoricCreatureWithEgg("tanystropheus",
            () -> EntityType.Builder.of(TanystropheusEntity::new, MobCategory.CREATURE).sized(1.8F, 0.7F)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "tanystropheus").toString()),
            EggSize.SMALL, EggVariant.SPOTS, 1200, 0x101217, 0x67bce1);

    public static final RegistryObject<EntityType<TriceratopsEntity>> TRICERATOPS = registerPrehistoricCreatureWithEgg("trike",
            () -> EntityType.Builder.of(TriceratopsEntity::new, MobCategory.CREATURE).sized(2.85F, 3.75F)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "trike").toString()),
            EggSize.SMALL, EggVariant.SPOTS, 1200, 0x383323, 0x9c2f2f);

    public static final RegistryObject<EntityType<TyrannosaurusEntity>> TYRANNOSAURUS = registerPrehistoricCreatureWithEgg("rex",
            () -> EntityType.Builder.of(TyrannosaurusEntity::new, MobCategory.CREATURE).sized(3.25F, 4.0F)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "rex").toString()),
            EggSize.SMALL, EggVariant.SPOTS, 1200, 0x2f1519, 0x8f2b46);

    public static final RegistryObject<EntityType<UlughbegsaurusEntity>> ULUG = registerPrehistoricCreatureWithEgg("ulughbegsaurus",
            () -> EntityType.Builder.of(UlughbegsaurusEntity::new, MobCategory.CREATURE).sized(1.95F, 2.8F)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "ulughbegsaurus").toString()),
            EggSize.SMALL, EggVariant.SPOTS, 1200, 0x44302e, 0x65c4ca);

    public static final RegistryObject<EntityType<VelociraptorEntity>> VELOCIRAPTOR = registerPrehistoricCreatureWithEgg("veloci",
            () -> EntityType.Builder.of(VelociraptorEntity::new, MobCategory.CREATURE).sized(0.9F, 0.9F)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "veloci").toString()),
            EggSize.SMALL, EggVariant.SPOTS, 1200, 0x827158, 0x4a4242);

    public static final RegistryObject<EntityType<XiphactinusEntity>> XIPH = ENTITIES.register("xiphactinus",
            () -> EntityType.Builder.of(XiphactinusEntity::new, MobCategory.WATER_CREATURE).sized(2.5F, 1.25F)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "xiphactinus").toString()));

    // Ceno mobs
    public static final RegistryObject<EntityType<BarinasuchusEntity>> BARINASUCHUS = registerPrehistoricCreatureWithEgg("barinasuchus",
            () -> EntityType.Builder.of(BarinasuchusEntity::new, MobCategory.CREATURE).sized(2.4F, 2.2F)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "barinasuchus").toString()),
            EggSize.SMALL, EggVariant.SPOTS, 1200, 0x181606, 0xcebd1e);

    public static final RegistryObject<EntityType<GigantopithicusEntity>> GIGANTOPITHICUS = ENTITIES.register("gigantopithicus",
            () -> EntityType.Builder.of(GigantopithicusEntity::new, MobCategory.CREATURE).sized(2.55F, 3.0F)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "gigantopithicus").toString()));

    public static final RegistryObject<EntityType<MammothEntity>> MAMMOTH = ENTITIES.register("mammoth",
            () -> EntityType.Builder.of(MammothEntity::new, MobCategory.CREATURE).sized(3.75F, 4.5F)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "mammoth").toString()));

    public static final RegistryObject<EntityType<MegalaniaEntity>> MEGALANIA = registerPrehistoricCreatureWithEgg("megalania",
            () -> EntityType.Builder.of(MegalaniaEntity::new, MobCategory.CREATURE).sized(2.5F, 2.0F)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "megalania").toString()),
            EggSize.SMALL, EggVariant.SPOTS, 1200, 0x49412e, 0x33d090);

    public static final RegistryObject<EntityType<MegatheriumEntity>> MEGATHERIUM = ENTITIES.register("megatherium",
            () -> EntityType.Builder.of(MegatheriumEntity::new, MobCategory.CREATURE).sized(2.75F, 3.0F)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "megatherium").toString()));

    public static final RegistryObject<EntityType<OphiodonEntity>> OPHIODON = ENTITIES.register("ophiodon",
            () -> EntityType.Builder.of(OphiodonEntity::new, MobCategory.WATER_CREATURE).sized(1.4f, 0.8f)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "ophiodon").toString()));

    public static final RegistryObject<EntityType<OtarocyonEntity>> OTAROCYON = ENTITIES.register("otarocyon",
            () -> EntityType.Builder.of(OtarocyonEntity::new, MobCategory.CREATURE).sized(1.0F, 1.0F)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "otarocyon").toString()));

    public static final RegistryObject<EntityType<PalaeophisEntity>> PALAEOPHIS = ENTITIES.register("palaeophis",
            () -> EntityType.Builder.of(PalaeophisEntity::new, MobCategory.WATER_CREATURE).sized(0.8F, 0.8F)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "palaeophis").toString()));

    public static final RegistryObject<EntityType<PalaeophisPartEntity>> PALAEOPHIS_PART = ENTITIES.register("palaeophis_part",
            () -> EntityType.Builder.<PalaeophisPartEntity>of(PalaeophisPartEntity::new, MobCategory.MISC)
                    .sized(0.8F, 0.8F).setShouldReceiveVelocityUpdates(true)
                    .setUpdateInterval(1)
                    .noSummon()
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "palaeophis_part").toString()));

    public static final RegistryObject<EntityType<PalaeolophisHatchlingEntity>> BABY_PALAEO = ENTITIES.register("palaeophis_hatchling",
            () -> EntityType.Builder.of(PalaeolophisHatchlingEntity::new, MobCategory.WATER_AMBIENT).sized(0.75f, 0.3f)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "palaeophis_hatchling").toString()));

    public static final RegistryObject<EntityType<ParaceratheriumEntity>> PARACERATHERIUM = ENTITIES.register("paraceratherium",
            () -> EntityType.Builder.of(ParaceratheriumEntity::new, MobCategory.CREATURE).sized(4.25F, 7.5F)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "paraceratherium").toString()));

    public static final RegistryObject<EntityType<PsilopterusEntity>> PSILOPTERUS = registerPrehistoricCreatureWithEgg("psilopterus",
            () -> EntityType.Builder.of(PsilopterusEntity::new, MobCategory.CREATURE).sized(1.1F, 1.5F)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "psilopterus").toString()),
            EggSize.SMALL, EggVariant.SPOTS, 1200, 0x281d17, 0xc27d28);

    public static final RegistryObject<EntityType<SmilodonEntity>> SMILODON = ENTITIES.register("smilodon",
            () -> EntityType.Builder.of(SmilodonEntity::new, MobCategory.CREATURE).sized(1F, 1.6F)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "smilodon").toString()));

    public static final RegistryObject<EntityType<TalpanasEntity>> TALPANAS = registerPrehistoricCreatureWithEgg("talpanas",
            () -> EntityType.Builder.of(TalpanasEntity::new, MobCategory.CREATURE).sized(0.8F, 0.8F)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "talpanas").toString()),
            EggSize.SMALL, EggVariant.SPOTS, 1200, 0x2b211c, 0x8ca6ad);

    public static final RegistryObject<EntityType<TelecrexEntity>> TELECREX = registerPrehistoricCreatureWithEgg("telecrex",
            () -> EntityType.Builder.of(TelecrexEntity::new, MobCategory.CREATURE).sized(0.8F, 1.1F)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "telecrex").toString()),
            EggSize.SMALL, EggVariant.SPOTS, 1200, 0x423025, 0xbcab39);


    // Misc mobs
    public static final RegistryObject<EntityType<EncrustedEntity>> ENCRUSTED = registerPrehistoricCreatureWithEgg("encrusted",
            () -> EntityType.Builder.of(EncrustedEntity::new, MobCategory.CREATURE).sized(1.5F, 1.95F)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "encrusted").toString()),
            EggSize.SMALL, EggVariant.SPOTS, 1200, 0x6e3a01, 0xffa204);

    public static final RegistryObject<EntityType<SludgeEntity>> SLUDGE = ENTITIES.register("sludge",
            () -> EntityType.Builder.of(SludgeEntity::new, MobCategory.MONSTER).sized(2.0f, 2.0f)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "sludge").toString()));

    // Non-living mobs
    public static final RegistryObject<EntityType<AmberShotEntity>> AMBER_SHOT = ENTITIES.register(
            "amber_shot", () -> EntityType.Builder.<AmberShotEntity>of(AmberShotEntity::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F).clientTrackingRange(9)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "amber_shot").toString()));

    public static final RegistryObject<EntityType<HwachavenatorSpikeEntity>> HWACHA_SPIKE = ENTITIES.register(
            "hwacha_spike", () -> EntityType.Builder.<HwachavenatorSpikeEntity>of(HwachavenatorSpikeEntity::new, MobCategory.MISC)
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

    public static final RegistryObject<EntityType<OpalescentPearlEntity>> OPALESCENT_PEARL = ENTITIES.register("opalescent_pearl",
            () ->  EntityType.Builder.<OpalescentPearlEntity>of(OpalescentPearlEntity::new, MobCategory.MISC)
                    .sized(0.25F, 0.25F)
                    .clientTrackingRange(4)
                    .updateInterval(10)
                    .build(prefix("opalescent_pearl").toString()));

    public static final RegistryObject<EntityType<OpalescentShurikenEntity>> OPALESCENT_SHURIKEN = ENTITIES.register("opalescent_shuriken",
            () ->  EntityType.Builder.<OpalescentShurikenEntity>of(OpalescentShurikenEntity::new, MobCategory.MISC)
                    .sized(0.25F, 0.25F)
                    .clientTrackingRange(4)
                    .updateInterval(10)
                    .build(prefix("opalescent_shuriken").toString()));

    public static final RegistryObject<EntityType<JarateEntity>> JARATE = ENTITIES.register("jarate",
        () ->  EntityType.Builder.<JarateEntity>of(JarateEntity::new, MobCategory.MISC)
                .sized(0.25F, 0.25F)
                .clientTrackingRange(4)
                .updateInterval(10)
                .build(prefix("jarate").toString()));

    public static final RegistryObject<EntityType<PalaeophisBookEntity>> BOOK_PALAEO = ENTITIES.register("palaeo_book_palaeo",
            () -> EntityType.Builder.<PalaeophisBookEntity>of(PalaeophisBookEntity::new, MobCategory.MISC)
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
                    .noSummon()
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

    public static final RegistryObject<EntityType<PsittaccoArrow>> PSITTACCO_ARROW = ENTITIES.register("psittacco_arrow",
            () -> EntityType.Builder.<PsittaccoArrow>of(PsittaccoArrow::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(20).setCustomClientFactory(PsittaccoArrow::new)
                    .build(prefix("psittacco_arrow").toString()));

    public static final RegistryObject<EntityType<ThrowableFallingBlockEntity>> THROWABLE_FALLING_BLOCK = ENTITIES.register("throwable_falling_block",
            () -> EntityType.Builder.of(ThrowableFallingBlockEntity::new, MobCategory.MISC)
                    .sized(0.98F, 0.98F)
                    .clientTrackingRange(10)
                    .updateInterval(20)
                    .build(prefix("throwable_falling_block").toString()));

    public static final RegistryObject<EntityType<PrehistoricEggEntity>> PREHISTORIC_EGG = ENTITIES.register("prehistoric_egg",
            () -> EntityType.Builder.<PrehistoricEggEntity>of(PrehistoricEggEntity::new, MobCategory.MISC)
                    .sized(1.0F, 1.0F)
                    .build(prefix("prehistoric_egg").toString()));

    private static <T extends EntityType<?>> RegistryObject<T> registerPrehistoricCreatureWithEgg(String name, Supplier<? extends T> entity, EggSize eggSize, EggVariant variant,  int hatchTime ,int eggBaseColor, int eggSpotColor) {
        RegistryObject<T> prehistoric = ENTITIES.register(name, entity);
        UPItems.ITEMS.register(name+"_egg", () -> new PrehistoricEggItem(prehistoric, eggSize, variant, hatchTime, eggBaseColor, eggSpotColor));
        prehistorics.add(prehistoric);
        return prehistoric;
    }
}
