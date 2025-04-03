package com.peeko32213.unusualprehistory.core.registry.entities;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.custom.base.PalaeophisBookEntity;
import com.peeko32213.unusualprehistory.common.entity.custom.eggs.EggSize;
import com.peeko32213.unusualprehistory.common.entity.custom.eggs.EggVariant;
import com.peeko32213.unusualprehistory.common.entity.custom.eggs.PrehistoricEggEntity;
import com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.flying.TelecrexEntity;
import com.peeko32213.unusualprehistory.common.entity.custom.skeleton.TriceratopsSkeleton;
import com.peeko32213.unusualprehistory.common.entity.custom.skeleton.TyrannosaurusSkeleton;
import com.peeko32213.unusualprehistory.common.entity.custom.iceberg.IcebergMammoth;
import com.peeko32213.unusualprehistory.common.entity.custom.iceberg.IcebergSmilodon;
import com.peeko32213.unusualprehistory.common.entity.custom.part.PalaeophisPartEntity;
import com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.*;
import com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.aquatic.*;
import com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.flying.AnurognathusEntity;
import com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.flying.KimmeridgebrachypteraeschnidiumEntity;
import com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.flying.PterodaustroEntity;
import com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.monster.EncrustedEntity;
import com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.monster.SludgeEntity;
import com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.semi_aquatic.*;
import com.peeko32213.unusualprehistory.common.entity.custom.skeleton.UnicornSkeleton;
import com.peeko32213.unusualprehistory.common.entity.plants.EntityPlant;
import com.peeko32213.unusualprehistory.common.entity.projectile.*;
import com.peeko32213.unusualprehistory.common.item.PrehistoricEggItem;
import com.peeko32213.unusualprehistory.core.registry.items.UPItems;
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

import static com.peeko32213.unusualprehistory.UnusualPrehistory.modPrefix;

@Mod.EventBusSubscriber(modid = UnusualPrehistory.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class UPEntities {

    public static final List<RegistryObject<?>> prehistorics = new ArrayList<>();
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, UnusualPrehistory.MODID);

    // Fossils
    public static final RegistryObject<EntityType<TyrannosaurusSkeleton>> TYRANNO_SKELETON = ENTITIES.register("tyrannosaurus_skeleton",
            () -> EntityType.Builder.<TyrannosaurusSkeleton>of(TyrannosaurusSkeleton::new, MobCategory.MISC)
                    .fireImmune()
                    .sized(3.25F, 4F)
                    .setTrackingRange(10)
                    .build(modPrefix("tyrannosaurus_skeleton").toString()));

    public static final RegistryObject<EntityType<TriceratopsSkeleton>> TRIKE_SKELETON = ENTITIES.register("triceratops_skeleton",
            () -> EntityType.Builder.<TriceratopsSkeleton>of(TriceratopsSkeleton::new, MobCategory.MISC)
                    .fireImmune()
                    .sized(3.0F, 3.75F)
                    .setTrackingRange(10)
                    .build(modPrefix("triceratops_skeleton").toString()));

    public static final RegistryObject<EntityType<UnicornSkeleton>> UNICORN_SKELETON = ENTITIES.register("unicorn_skeleton",
            () -> EntityType.Builder.<UnicornSkeleton>of(UnicornSkeleton::new, MobCategory.MISC)
                    .fireImmune()
                    .sized(1.2F, 3.0F)
                    .setTrackingRange(10)
                    .build(modPrefix("unicorn_skeleton").toString()));

    // Frozen
    public static final RegistryObject<EntityType<IcebergMammoth>> ICEBERG_MAMMOTH = ENTITIES.register("iceberg_mammoth",
            () -> EntityType.Builder.<IcebergMammoth>of(IcebergMammoth::new, MobCategory.CREATURE)
                    .fireImmune()
                    .sized(5, 5)
                    .build(modPrefix("icberg_mammoth").toString()));

    public static final RegistryObject<EntityType<IcebergSmilodon>> ICEBERG_SMILODON = ENTITIES.register("iceberg_smilodon",
            () -> EntityType.Builder.<IcebergSmilodon>of(IcebergSmilodon::new, MobCategory.CREATURE)
                    .fireImmune()
                    .sized(2, 2)
                    .build(modPrefix("iceberg_smilodon").toString()));

    // Paleo mobs
    public static final RegistryObject<EntityType<AmmoniteEntity>> AMMON = ENTITIES.register("ammon",
            () -> EntityType.Builder.of(AmmoniteEntity::new, MobCategory.WATER_CREATURE).sized(0.7f, 0.7f)
                    .build(modPrefix("ammon").toString()));

    public static final RegistryObject<EntityType<CotylorhynchusEntity>> COTY = registerPrehistoricCreatureWithEgg("coty",
            () -> EntityType.Builder.of(CotylorhynchusEntity::new, MobCategory.CREATURE).sized(2.0F, 1.8F)
                    .build(modPrefix("coty").toString()),
            EggSize.SMALL, EggVariant.SPOTS, 1200, 0x843222, 0xdfd7ad);

    public static final RegistryObject<EntityType<DiplocaulusEntity>> DIPLOCAULUS = ENTITIES.register("diplocaulus",
            () -> EntityType.Builder.of(DiplocaulusEntity::new, MobCategory.CREATURE).sized(0.8F, 0.65F)
                    .build(modPrefix("diplocaulus").toString()));

    public static final RegistryObject<EntityType<DunkleosteusEntity>> DUNK = ENTITIES.register("dunk",
            () -> EntityType.Builder.of(DunkleosteusEntity::new, MobCategory.WATER_CREATURE).sized(0.75F, 0.6F)
                    .build(modPrefix("dunk").toString()));

    public static final RegistryObject<EntityType<EdaphosaurusEntity>> EDAPHOSAURUS = registerPrehistoricCreatureWithEgg("edaphosaurus",
            () -> EntityType.Builder.of(EdaphosaurusEntity::new, MobCategory.CREATURE).sized(1.4F, 1.75F)
                    .build(modPrefix("edaphosaurus").toString()),
            EggSize.SMALL, EggVariant.SPOTS, 1200, 0x632623, 0xf6e8e8);

    public static final RegistryObject<EntityType<EstemmenosuchusEntity>> ESTEMMENOSUCHUS = registerPrehistoricCreatureWithEgg("estemmenosuchus",
            () -> EntityType.Builder.of(EstemmenosuchusEntity::new, MobCategory.CREATURE).sized(3.0F, 3.25F)
                    .build(modPrefix("estemmenosuchus").toString()),
            EggSize.SMALL, EggVariant.SPOTS, 1200, 0x151521, 0xff6cba);

    public static final RegistryObject<EntityType<HyneriaEntity>> HYNERIA = ENTITIES.register("hyneria",
            () -> EntityType.Builder.of(HyneriaEntity::new, MobCategory.WATER_CREATURE).sized(1.45f, 1.0f)
                    .build(modPrefix("hyneria").toString()));

    public static final RegistryObject<EntityType<HynerpetonEntity>> HYNERPETON = registerPrehistoricCreatureWithEgg("hynerpteon",
            () -> EntityType.Builder.of(HynerpetonEntity::new, MobCategory.CREATURE).sized(1.2F, 0.9F)
                    .build(modPrefix("hynerpteon").toString()),
            EggSize.SMALL, EggVariant.SPOTS, 1200, 0x140f0e, 0xc8b523);

    public static final RegistryObject<EntityType<JawlessFishEntity>> JAWLESS_FISH = ENTITIES.register("jawless_fish",
            () -> EntityType.Builder.of(JawlessFishEntity::new, MobCategory.WATER_AMBIENT).sized(0.6f, 0.6f)
                    .build(modPrefix("jawless_fish").toString()));

    public static final RegistryObject<EntityType<PanacanthocarisEntity>> PANACANTHOCARIS = ENTITIES.register("panacanthocaris",
            () -> EntityType.Builder.of(PanacanthocarisEntity::new, MobCategory.WATER_CREATURE).sized(0.9F, 0.45F)
                    .build(modPrefix("panacanthocaris").toString()));

    public static final RegistryObject<EntityType<PterygotusEntity>> PTERYGOTUS = ENTITIES.register("pterygotus",
            () -> EntityType.Builder.of(PterygotusEntity::new, MobCategory.CREATURE).sized(2.75F, 1.0F)
                    .build(modPrefix("pterygotus").toString()));

    public static final RegistryObject<EntityType<ScaumenaciaEntity>> SCAU = ENTITIES.register("scau",
            () -> EntityType.Builder.of(ScaumenaciaEntity::new, MobCategory.WATER_AMBIENT).sized(0.75f, 0.6f)
                    .build(modPrefix("scau").toString()));

    public static final RegistryObject<EntityType<StethacanthusEntity>> STETHACANTHUS = ENTITIES.register("stethacanthus",
            () -> EntityType.Builder.of(StethacanthusEntity::new, MobCategory.WATER_CREATURE).sized(0.6f, 0.6f)
                    .build(modPrefix("stethacanthus").toString()));

    public static final RegistryObject<EntityType<TartuosteusEntity>> TARTUOSTEUS = ENTITIES.register("tartuosteus",
            () -> EntityType.Builder.of(TartuosteusEntity::new, MobCategory.WATER_CREATURE).sized(2.0f, 0.8f)
                    .build(modPrefix("tartuosteus").toString()));

    // Meso mobs

    public static final RegistryObject<EntityType<AntarctopeltaEntity>> ANTARCO = registerPrehistoricCreatureWithEgg("antarctopelta",
            () -> EntityType.Builder.of(AntarctopeltaEntity::new, MobCategory.CREATURE).sized(2.1F, 1.8F)
                    .build(modPrefix("antarctopelta").toString()),
            EggSize.SMALL, EggVariant.SPOTS, 1200, 0x221a19, 0xe6e1d4);

    public static final RegistryObject<EntityType<AnurognathusEntity>> ANURO = registerPrehistoricCreatureWithEgg("anuro",
            () -> EntityType.Builder.of(AnurognathusEntity::new, MobCategory.CREATURE).sized(0.8F, 0.8F)
                    .build(modPrefix("anuro").toString()),
            EggSize.SMALL, EggVariant.SPOTS, 1200, 0x4f4941, 0xfaf15f);

    public static final RegistryObject<EntityType<ArchelonEntity>> ARCHELON = registerPrehistoricCreatureWithEgg("archelon",
            () -> EntityType.Builder.of(ArchelonEntity::new, MobCategory.CREATURE).sized(3.5F, 1.1F)
                    .build(modPrefix("archelon").toString()),
            EggSize.SMALL, EggVariant.SPOTS, 1200, 0x1a1f29, 0x7ca9a5);

    public static final RegistryObject<EntityType<AustroraptorEntity>> AUSTRO = registerPrehistoricCreatureWithEgg("austroraptor",
            () -> EntityType.Builder.of(AustroraptorEntity::new, MobCategory.CREATURE).sized(1.25F, 1.25F)
                    .build(modPrefix("austroraptor").toString()),
            EggSize.SMALL, EggVariant.SPOTS, 1200, 0xefebea, 0xc72727);

    public static final RegistryObject<EntityType<BalaurEntity>> BALAUR = registerPrehistoricCreatureWithEgg("balaur",
            () -> EntityType.Builder.of(BalaurEntity::new, MobCategory.CREATURE).sized(0.9F, 1.75F)
                    .build(modPrefix("balaur").toString()),
            EggSize.SMALL, EggVariant.SPOTS, 1200, 0x1f6731, 0xe5cb36);

    public static final RegistryObject<EntityType<BeelzebufoEntity>> BEELZ = ENTITIES.register("beelz",
            () -> EntityType.Builder.of(BeelzebufoEntity::new, MobCategory.CREATURE).sized(1.2f, 0.8f)
                    .build(modPrefix("beelz").toString()));

    public static final RegistryObject<EntityType<BeelzebufoTadpoleEntity>> BEELZE_TADPOLE = ENTITIES.register("beelz_tadpole",
            () -> EntityType.Builder.of(BeelzebufoTadpoleEntity::new, MobCategory.WATER_AMBIENT).sized(0.4f, 0.4f)
                    .build(modPrefix("beelz_tadpole").toString()));

    public static final RegistryObject<EntityType<BrachiosaurusEntity>> BRACHI = registerPrehistoricCreatureWithEgg("brachi",
            () -> EntityType.Builder.of(BrachiosaurusEntity::new, MobCategory.CREATURE).sized(4.8F, 7.5F).setShouldReceiveVelocityUpdates(true).setUpdateInterval(1)
                    .build(modPrefix("brachi").toString()),
            EggSize.SMALL, EggVariant.SPOTS, 1200, 0x425860, 0x0a0d18);

    public static final RegistryObject<EntityType<EryonEntity>> ERYON = ENTITIES.register("eryon",
            () -> EntityType.Builder.of(EryonEntity::new, MobCategory.CREATURE).sized(0.8f, 0.5f)
                    .build(modPrefix("eryon").toString()));

    public static final RegistryObject<EntityType<GlobidensEntity>> GLOBIDENS = registerPrehistoricCreatureWithEgg("globidens",
            () -> EntityType.Builder.of(GlobidensEntity::new, MobCategory.CREATURE).sized(2.5F, 1.5F)
                    .build(modPrefix("globidens").toString()),
            EggSize.SMALL, EggVariant.SPOTS, 1200, 0x1a2525, 0x867e68);

    public static final RegistryObject<EntityType<HwachavenatorEntity>> HWACHA = registerPrehistoricCreatureWithEgg("hwachavenator",
            () -> EntityType.Builder.of(HwachavenatorEntity::new, MobCategory.CREATURE).sized(1.95F, 2.8F)
                    .build(modPrefix("hwachavenator").toString()),
            EggSize.SMALL, EggVariant.SPOTS, 1200, 0x1a2122, 0xf4f435);

    public static final RegistryObject<EntityType<KaprosuchusEntity>> KAPROSUCHUS = registerPrehistoricCreatureWithEgg("kaprosuchus",
            () -> EntityType.Builder.of(KaprosuchusEntity::new, MobCategory.CREATURE).sized(1.3F, 1.1F)
                    .build(modPrefix("kaprosuchus").toString()),
            EggSize.SMALL, EggVariant.SPOTS, 1200, 0x0e0c0b, 0xe78f3c);

    public static final RegistryObject<EntityType<KentrosaurusEntity>> KENTRO = registerPrehistoricCreatureWithEgg("kentrosaurus",
            () -> EntityType.Builder.of(KentrosaurusEntity::new, MobCategory.CREATURE).sized(1.8F, 1.9F)
                    .build(modPrefix("kentrosaurus").toString()),
            EggSize.SMALL, EggVariant.SPOTS, 1200, 0x273817, 0xdee1c9);

    public static final RegistryObject<EntityType<KimmeridgebrachypteraeschnidiumEntity>> KIMMER = ENTITIES.register("kimmeridgebrachypteraeschnidium",
            () -> EntityType.Builder.of(KimmeridgebrachypteraeschnidiumEntity::new, MobCategory.CREATURE).sized(0.65f, 0.65f)
                    .build(modPrefix("kimmeridgebrachypteraeschnidium").toString()));

    public static final RegistryObject<EntityType<LeedsichthysEntity>> LEEDSICHTHYS = ENTITIES.register("leedsichthys",
            () -> EntityType.Builder.of(LeedsichthysEntity::new, MobCategory.WATER_CREATURE).sized(7.75F, 5.25F)
                    .build(modPrefix("leedsichthys").toString()));

    public static final RegistryObject<EntityType<LongisquamaEntity>> LONGISQUAMA = registerPrehistoricCreatureWithEgg("longisquama",
            () -> EntityType.Builder.of(LongisquamaEntity::new, MobCategory.CREATURE).sized(1.0F, 1.0F)
                    .build(modPrefix("longisquama").toString()),
            EggSize.SMALL, EggVariant.SPOTS, 1200, 0x5e4fa7, 0x7edbdd);

    public static final RegistryObject<EntityType<MajungasaurusEntity>> MAJUNGA = registerPrehistoricCreatureWithEgg("majunga",
            () -> EntityType.Builder.of(MajungasaurusEntity::new, MobCategory.CREATURE).sized(1.35F, 1.8F)
                    .build(modPrefix("majunga").toString()),
            EggSize.SMALL, EggVariant.SPOTS, 1200, 0x1d600e, 0xcad9b0);

    public static final RegistryObject<EntityType<OviraptorEntity>> OVIRAPTOR = registerPrehistoricCreatureWithEgg("oviraptor",
            () -> EntityType.Builder.of(OviraptorEntity::new, MobCategory.CREATURE).sized(0.8F, 1.2F)
                    .build(modPrefix("oviraptor").toString()),
            EggSize.SMALL, EggVariant.SPOTS, 1200, 0x111e27, 0x3e9e67);

    public static final RegistryObject<EntityType<PachycephalosaurusEntity>> PACHY = registerPrehistoricCreatureWithEgg("pachy",
            () -> EntityType.Builder.of(PachycephalosaurusEntity::new, MobCategory.CREATURE).sized(1.2F, 1.9F)
                    .build(modPrefix("pachy").toString()),
            EggSize.SMALL, EggVariant.SPOTS, 1200, 0x70837f, 0x191a2a);

    public static final RegistryObject<EntityType<ProtosphyraenaEntity>> PROTOSPHYRAENA = ENTITIES.register("protosphyraena",
            () -> EntityType.Builder.of(ProtosphyraenaEntity::new, MobCategory.WATER_CREATURE).sized(1.35f, 0.8f)
                    .build(modPrefix("protosphyraena").toString()));

    public static final RegistryObject<EntityType<PsittacosaurusEntity>> PSITTACO = registerPrehistoricCreatureWithEgg("psittacosaurus",
            () -> EntityType.Builder.of(PsittacosaurusEntity::new, MobCategory.CREATURE).sized(1.0F, 1.0F)
                    .build(modPrefix("psittacosaurus").toString()),
            EggSize.SMALL, EggVariant.SPOTS, 1200, 0xa04f2a, 0xe8d49b);

    public static final RegistryObject<EntityType<PterodaustroEntity>> PTERODAUSTRO = registerPrehistoricCreatureWithEgg("pterodaustro",
            () -> EntityType.Builder.of(PterodaustroEntity::new, MobCategory.CREATURE).sized(0.8F, 0.8F)
                    .build(modPrefix("pterodaustro").toString()),
            EggSize.SMALL, EggVariant.SPOTS, 1200, 0xc93660, 0xf9e3f9);

    public static final RegistryObject<EntityType<TanystropheusEntity>> TANY = registerPrehistoricCreatureWithEgg("tanystropheus",
            () -> EntityType.Builder.of(TanystropheusEntity::new, MobCategory.CREATURE).sized(1.8F, 0.7F)
                    .build(modPrefix("tanystropheus").toString()),
            EggSize.SMALL, EggVariant.SPOTS, 1200, 0x101217, 0x67bce1);

    public static final RegistryObject<EntityType<TriceratopsEntity>> TRICERATOPS = registerPrehistoricCreatureWithEgg("trike",
            () -> EntityType.Builder.of(TriceratopsEntity::new, MobCategory.CREATURE).sized(3.0F, 3.75F)
                    .build(modPrefix("trike").toString()),
            EggSize.SMALL, EggVariant.SPOTS, 1200, 0x383323, 0x9c2f2f);

    public static final RegistryObject<EntityType<TyrannosaurusEntity>> TYRANNOSAURUS = registerPrehistoricCreatureWithEgg("rex",
            () -> EntityType.Builder.of(TyrannosaurusEntity::new, MobCategory.CREATURE).sized(3.25F, 4.0F)
                    .build(modPrefix("rex").toString()),
            EggSize.SMALL, EggVariant.SPOTS, 1200, 0x2f1519, 0x8f2b46);

    public static final RegistryObject<EntityType<UlughbegsaurusEntity>> ULUG = registerPrehistoricCreatureWithEgg("ulughbegsaurus",
            () -> EntityType.Builder.of(UlughbegsaurusEntity::new, MobCategory.CREATURE).sized(1.95F, 2.8F)
                    .build(modPrefix("ulughbegsaurus").toString()),
            EggSize.SMALL, EggVariant.SPOTS, 1200, 0x44302e, 0x65c4ca);

    public static final RegistryObject<EntityType<VelociraptorEntity>> VELOCIRAPTOR = registerPrehistoricCreatureWithEgg("veloci",
            () -> EntityType.Builder.of(VelociraptorEntity::new, MobCategory.CREATURE).sized(0.9F, 1.4F)
                    .build(modPrefix("veloci").toString()),
            EggSize.SMALL, EggVariant.SPOTS, 1200, 0x827158, 0x4a4242);

    public static final RegistryObject<EntityType<XiphactinusEntity>> XIPH = ENTITIES.register("xiphactinus",
            () -> EntityType.Builder.of(XiphactinusEntity::new, MobCategory.WATER_CREATURE).sized(2.5F, 1.25F)
                    .build(modPrefix("xiphactinus").toString()));

    // Ceno mobs
    public static final RegistryObject<EntityType<BarinasuchusEntity>> BARINASUCHUS = registerPrehistoricCreatureWithEgg("barinasuchus",
            () -> EntityType.Builder.of(BarinasuchusEntity::new, MobCategory.CREATURE).sized(2.4F, 2.2F)
                    .build(modPrefix("barinasuchus").toString()),
            EggSize.SMALL, EggVariant.SPOTS, 1200, 0x181606, 0xcebd1e);

    public static final RegistryObject<EntityType<GigantopithicusEntity>> GIGANTOPITHICUS = ENTITIES.register("gigantopithicus",
            () -> EntityType.Builder.of(GigantopithicusEntity::new, MobCategory.CREATURE).sized(2.55F, 3.0F)
                    .build(modPrefix("gigantopithicus").toString()));

    public static final RegistryObject<EntityType<MammothEntity>> MAMMOTH = ENTITIES.register("mammoth",
            () -> EntityType.Builder.of(MammothEntity::new, MobCategory.CREATURE).sized(3.75F, 4.5F)
                    .build(modPrefix("mammoth").toString()));

    public static final RegistryObject<EntityType<MegalaniaEntity>> MEGALANIA = registerPrehistoricCreatureWithEgg("megalania",
            () -> EntityType.Builder.of(MegalaniaEntity::new, MobCategory.CREATURE).sized(2.5F, 2.0F)
                    .build(modPrefix("megalania").toString()),
            EggSize.SMALL, EggVariant.SPOTS, 1200, 0x49412e, 0x33d090);

    public static final RegistryObject<EntityType<MegatheriumEntity>> MEGATHERIUM = ENTITIES.register("megatherium",
            () -> EntityType.Builder.of(MegatheriumEntity::new, MobCategory.CREATURE).sized(2.75F, 3.0F)
                    .build(modPrefix("megatherium").toString()));

    public static final RegistryObject<EntityType<OphiodonEntity>> OPHIODON = ENTITIES.register("ophiodon",
            () -> EntityType.Builder.of(OphiodonEntity::new, MobCategory.WATER_CREATURE).sized(1.4f, 0.8f)
                    .build(modPrefix("ophiodon").toString()));

    public static final RegistryObject<EntityType<OtarocyonEntity>> OTAROCYON = ENTITIES.register("otarocyon",
            () -> EntityType.Builder.of(OtarocyonEntity::new, MobCategory.CREATURE).sized(1.0F, 1.0F)
                    .build(modPrefix("otarocyon").toString()));

    public static final RegistryObject<EntityType<PalaeophisEntity>> PALAEOPHIS = ENTITIES.register("palaeophis",
            () -> EntityType.Builder.of(PalaeophisEntity::new, MobCategory.WATER_CREATURE).sized(0.8F, 0.8F)
                    .build(modPrefix("palaeophis").toString()));

    public static final RegistryObject<EntityType<PalaeophisPartEntity>> PALAEOPHIS_PART = ENTITIES.register("palaeophis_part",
            () -> EntityType.Builder.<PalaeophisPartEntity>of(PalaeophisPartEntity::new, MobCategory.MISC)
                    .sized(0.8F, 0.8F).setShouldReceiveVelocityUpdates(true)
                    .setUpdateInterval(1)
                    .noSummon()
                    .build(modPrefix("palaeophis_part").toString()));

    public static final RegistryObject<EntityType<PalaeolophisHatchlingEntity>> BABY_PALAEO = ENTITIES.register("palaeophis_hatchling",
            () -> EntityType.Builder.of(PalaeolophisHatchlingEntity::new, MobCategory.WATER_AMBIENT).sized(0.75f, 0.3f)
                    .build(modPrefix("palaeophis_hatchling").toString()));

    public static final RegistryObject<EntityType<ParaceratheriumEntity>> PARACERATHERIUM = ENTITIES.register("paraceratherium",
            () -> EntityType.Builder.of(ParaceratheriumEntity::new, MobCategory.CREATURE).sized(4.25F, 7.5F)
                    .build(modPrefix("paraceratherium").toString()));

    public static final RegistryObject<EntityType<PsilopterusEntity>> PSILOPTERUS = registerPrehistoricCreatureWithEgg("psilopterus",
            () -> EntityType.Builder.of(PsilopterusEntity::new, MobCategory.CREATURE).sized(1.1F, 1.5F)
                    .build(modPrefix("psilopterus").toString()),
            EggSize.SMALL, EggVariant.SPOTS, 1200, 0x281d17, 0xc27d28);

    public static final RegistryObject<EntityType<SmilodonEntity>> SMILODON = ENTITIES.register("smilodon",
            () -> EntityType.Builder.of(SmilodonEntity::new, MobCategory.CREATURE).sized(1F, 1.6F)
                    .build(modPrefix("smilodon").toString()));

    public static final RegistryObject<EntityType<TalpanasEntity>> TALPANAS = registerPrehistoricCreatureWithEgg("talpanas",
            () -> EntityType.Builder.of(TalpanasEntity::new, MobCategory.CREATURE).sized(0.8F, 0.8F)
                    .build(modPrefix("talpanas").toString()),
            EggSize.SMALL, EggVariant.SPOTS, 1200, 0x2b211c, 0x8ca6ad);

    public static final RegistryObject<EntityType<TelecrexEntity>> TELECREX = ENTITIES.register("telecrex",
            () -> EntityType.Builder.of(TelecrexEntity::new, MobCategory.CREATURE).sized(0.7F, 1.1F)
                    .build(modPrefix("telecrex").toString()));

    public static final RegistryObject<EntityType<UnicornEntity>> UNICORN = ENTITIES.register("unicorn",
            () -> EntityType.Builder.of(UnicornEntity::new, MobCategory.CREATURE).sized(1.2F, 3.0F)
                    .build(modPrefix("unicorn").toString()));

    // Misc mobs
    public static final RegistryObject<EntityType<EncrustedEntity>> ENCRUSTED = registerPrehistoricCreatureWithEgg("encrusted",
            () -> EntityType.Builder.of(EncrustedEntity::new, MobCategory.CREATURE).sized(1.5F, 1.6F)
                    .build(modPrefix("encrusted").toString()),
            EggSize.SMALL, EggVariant.SPOTS, 1200, 0x6e3a01, 0xffa204);

    public static final RegistryObject<EntityType<SludgeEntity>> SLUDGE = ENTITIES.register("sludge",
            () -> EntityType.Builder.of(SludgeEntity::new, MobCategory.MONSTER).sized(2.0f, 2.0f)
                    .build(modPrefix("sludge").toString()));

    // Non-living entities
    public static final RegistryObject<EntityType<AmberShotEntity>> AMBER_SHOT = ENTITIES.register(
            "amber_shot", () -> EntityType.Builder.<AmberShotEntity>of(AmberShotEntity::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F).clientTrackingRange(9)
                    .build(modPrefix("amber_shot").toString()));

    public static final RegistryObject<EntityType<HwachavenatorSpikeEntity>> HWACHA_SPIKE = ENTITIES.register(
            "hwacha_spike", () -> EntityType.Builder.<HwachavenatorSpikeEntity>of(HwachavenatorSpikeEntity::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F).clientTrackingRange(9).noSummon()
                    .build(modPrefix("hwacha_spike").toString()));

    public static final RegistryObject<EntityType<OpalescentPearl>> OPALESCENT_PEARL = ENTITIES.register("opalescent_pearl",
            () ->  EntityType.Builder.<OpalescentPearl>of(OpalescentPearl::new, MobCategory.MISC)
                    .sized(0.25F, 0.25F)
                    .clientTrackingRange(4)
                    .updateInterval(10)
                    .build(modPrefix("opalescent_pearl").toString()));

    public static final RegistryObject<EntityType<OpalescentShuriken>> OPALESCENT_SHURIKEN = ENTITIES.register("opalescent_shuriken",
            () ->  EntityType.Builder.<OpalescentShuriken>of(OpalescentShuriken::new, MobCategory.MISC)
                    .sized(0.25F, 0.25F)
                    .clientTrackingRange(4)
                    .updateInterval(10)
                    .build(modPrefix("opalescent_shuriken").toString()));

    public static final RegistryObject<EntityType<RabiesFlaskEntity>> RABIES_FLASK = ENTITIES.register("rabies_flask",
            () ->  EntityType.Builder.<RabiesFlaskEntity>of(RabiesFlaskEntity::new, MobCategory.MISC)
                    .sized(0.25F, 0.25F)
                    .clientTrackingRange(4)
                    .updateInterval(10)
                    .build(modPrefix("rabies_flask").toString()));

    public static final RegistryObject<EntityType<PalaeophisBookEntity>> BOOK_PALAEO = ENTITIES.register("palaeo_book_palaeo",
            () -> EntityType.Builder.<PalaeophisBookEntity>of(PalaeophisBookEntity::new, MobCategory.MISC)
                    .noSummon()
                    .fireImmune()
                    .sized(2, 2)
                    .build(modPrefix("palaeo_book_palaeo").toString()));

    // Eggs
    public static final RegistryObject<EntityType<TelecrexEgg>> TELECREX_EGG = ENTITIES.register("telecrex_egg", () -> registerEntity(EntityType.Builder.of(TelecrexEgg::new, MobCategory.MISC).sized(0.25F, 0.25F).setCustomClientFactory(TelecrexEgg::new).fireImmune(), "telecrex_egg"));

    // Plants
    public static final RegistryObject<EntityType<EntityPlant>> FOXXI_SAPLING = ENTITIES.register("foxxi_sapling",
            () -> EntityType.Builder.<EntityPlant>of(EntityPlant::new, MobCategory.MISC)
                    .noSummon()
                    .fireImmune()
                    .sized(2, 2)
                    .build(modPrefix("foxxi_sapling").toString()));

    public static final RegistryObject<EntityType<EntityPlant>> HORSETAIL = ENTITIES.register("horsetail",
            () -> EntityType.Builder.<EntityPlant>of(EntityPlant::new, MobCategory.MISC)
                    .noSummon()
                    .fireImmune()
                    .sized(1, 1)
                    .build(modPrefix("horsetail").toString()));

    public static final RegistryObject<EntityType<EntityPlant>> TALL_HORSETAIL = ENTITIES.register("tall_horsetail",
            () -> EntityType.Builder.<EntityPlant>of(EntityPlant::new, MobCategory.MISC)
                    .noSummon()
                    .fireImmune()
                    .sized(2, 2)
                    .build(modPrefix("tall_horsetail").toString()));

    public static final RegistryObject<EntityType<EntityPlant>> LEEFRUCTUS = ENTITIES.register("leefructus",
            () -> EntityType.Builder.<EntityPlant>of(EntityPlant::new, MobCategory.MISC)
                    .noSummon()
                    .fireImmune()
                    .sized(1, 1)
                    .build(modPrefix("leefructus").toString()));

    public static final RegistryObject<EntityType<EntityPlant>> BENNETTITALES = ENTITIES.register("bennett",
            () -> EntityType.Builder.<EntityPlant>of(EntityPlant::new, MobCategory.MISC)
                    .noSummon()
                    .fireImmune()
                    .sized(1, 1)
                    .build(modPrefix("bennett").toString()));

    public static final RegistryObject<EntityType<EntityPlant>> ARCHAEOSIGILARIA = ENTITIES.register("archaeos",
            () -> EntityType.Builder.<EntityPlant>of(EntityPlant::new, MobCategory.MISC)
                    .noSummon()
                    .fireImmune()
                    .sized(1, 1)
                    .build(modPrefix("archaeos").toString()));

    public static final RegistryObject<EntityType<EntityPlant>> SARACENIA = ENTITIES.register("sarracenia",
            () -> EntityType.Builder.<EntityPlant>of(EntityPlant::new, MobCategory.MISC)
                    .noSummon()
                    .fireImmune()
                    .sized(1, 1)
                    .build(modPrefix("sarracenia").toString()));

    public static final RegistryObject<EntityType<EntityPlant>> TALL_SARACENIA = ENTITIES.register("tall_sarracenia",
            () -> EntityType.Builder.<EntityPlant>of(EntityPlant::new, MobCategory.MISC)
                    .noSummon()
                    .fireImmune()
                    .sized(2, 2)
                    .build(modPrefix("tall_sarracenia").toString()));

    public static final RegistryObject<EntityType<EntityPlant>> GINKGO_SAPLING = ENTITIES.register("ginkgo_sapling",
            () -> EntityType.Builder.<EntityPlant>of(EntityPlant::new, MobCategory.MISC)
                    .noSummon()
                    .fireImmune()
                    .sized(1, 1)
                    .build(modPrefix("ginkgo_sapling").toString()));

    public static final RegistryObject<EntityType<EntityPlant>> DRYO_SAPLING = ENTITIES.register("dryo_sapling",
            () -> EntityType.Builder.<EntityPlant>of(EntityPlant::new, MobCategory.MISC)
                    .noSummon()
                    .fireImmune()
                    .sized(1, 1)
                    .build(modPrefix("dryo_sapling").toString()));

    public static final RegistryObject<EntityType<EntityPlant>> CLATHRODICTYON = ENTITIES.register("clathrodictyon",
            () -> EntityType.Builder.<EntityPlant>of(EntityPlant::new, MobCategory.MISC)
                    .noSummon()
                    .fireImmune()
                    .sized(1, 1)
                    .build(modPrefix("clathrodictyon").toString()));

    public static final RegistryObject<EntityType<EntityPlant>> ARCHAEFRUCTUS = ENTITIES.register("archaefructus",
            () -> EntityType.Builder.<EntityPlant>of(EntityPlant::new, MobCategory.MISC)
                    .noSummon()
                    .fireImmune()
                    .sized(1, 1)
                    .build(modPrefix("archaefructus").toString()));

    public static final RegistryObject<EntityType<EntityPlant>> NELUMBITES = ENTITIES.register("nelumbites",
            () -> EntityType.Builder.<EntityPlant>of(EntityPlant::new, MobCategory.MISC)
                    .noSummon()
                    .fireImmune()
                    .sized(1, 1)
                    .build(modPrefix("nelumbites").toString()));

    public static final RegistryObject<EntityType<EntityPlant>> QUEREUXIA = ENTITIES.register("quereuxia",
            () -> EntityType.Builder.<EntityPlant>of(EntityPlant::new, MobCategory.MISC)
                    .noSummon()
                    .fireImmune()
                    .sized(1, 1)
                    .build(modPrefix("quereuxia").toString()));

    public static final RegistryObject<EntityType<EntityPlant>> RAIGUENRAYUN = ENTITIES.register("raiguenrayun",
            () -> EntityType.Builder.<EntityPlant>of(EntityPlant::new, MobCategory.MISC)
                    .noSummon()
                    .fireImmune()
                    .sized(2, 2)
                    .build(modPrefix("raiguenrayun").toString()));

    public static final RegistryObject<EntityType<PsittaccoArrow>> PSITTACCO_ARROW = ENTITIES.register("psittacco_arrow",
            () -> EntityType.Builder.<PsittaccoArrow>of(PsittaccoArrow::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(20).setCustomClientFactory(PsittaccoArrow::new)
                    .build(modPrefix("psittacco_arrow").toString()));

    public static final RegistryObject<EntityType<ThrowableFallingBlockEntity>> THROWABLE_FALLING_BLOCK = ENTITIES.register("throwable_falling_block",
            () -> EntityType.Builder.of(ThrowableFallingBlockEntity::new, MobCategory.MISC)
                    .sized(0.98F, 0.98F)
                    .clientTrackingRange(10)
                    .updateInterval(20)
                    .build(modPrefix("throwable_falling_block").toString()));

    public static final RegistryObject<EntityType<PrehistoricEggEntity>> PREHISTORIC_EGG = ENTITIES.register("prehistoric_egg",
            () -> EntityType.Builder.<PrehistoricEggEntity>of(PrehistoricEggEntity::new, MobCategory.MISC)
                    .sized(1.0F, 1.0F)
                    .build(modPrefix("prehistoric_egg").toString()));

    private static EntityType registerEntity(EntityType.Builder builder, String entityName) {
        return builder.build(entityName);
    }

    private static <T extends EntityType<?>> RegistryObject<T> registerPrehistoricCreatureWithEgg(String name, Supplier<? extends T> entity, EggSize eggSize, EggVariant variant,  int hatchTime ,int eggBaseColor, int eggSpotColor) {
        RegistryObject<T> prehistoric = ENTITIES.register(name, entity);
        UPItems.ITEMS.register(name+"_egg", () -> new PrehistoricEggItem(prehistoric, eggSize, variant, hatchTime, eggBaseColor, eggSpotColor));
        prehistorics.add(prehistoric);
        return prehistoric;
    }
}
