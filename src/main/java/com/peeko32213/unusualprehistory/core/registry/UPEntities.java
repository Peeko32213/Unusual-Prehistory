package com.peeko32213.unusualprehistory.core.registry;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.*;
import com.peeko32213.unusualprehistory.common.entity.projectile.*;
import com.peeko32213.unusualprehistory.common.entity.iceberg.IcebergMammoth;
import com.peeko32213.unusualprehistory.common.entity.iceberg.IcebergSmilodon;
import com.peeko32213.unusualprehistory.common.entity.part.LeedsichthysPartEntity;
import com.peeko32213.unusualprehistory.common.entity.part.PalaeophisPartEntity;
import com.peeko32213.unusualprehistory.common.entity.base.PalaeophisBookEntity;
import com.peeko32213.unusualprehistory.common.entity.plants.EntityPlant;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;

import static com.peeko32213.unusualprehistory.UnusualPrehistory.prefix;

@Mod.EventBusSubscriber(modid = UnusualPrehistory.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class UPEntities {
    public static final List<RegistryObject<?>> dinos = new ArrayList<>();
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, UnusualPrehistory.MODID);

    // Boat.
    public static final RegistryObject<EntityType<UPBoatEntity>> BOAT = ENTITIES.register("boat", () -> EntityType.Builder.<UPBoatEntity>of(UPBoatEntity::new, MobCategory.MISC).sized(1.375F, 0.5625F).clientTrackingRange(10).build(new ResourceLocation(UnusualPrehistory.MODID, "boat").toString()));
    public static final RegistryObject<EntityType<UPChestBoatEntity>> CHEST_BOAT = ENTITIES.register("chest_boat", () -> EntityType.Builder.<UPChestBoatEntity>of(UPChestBoatEntity::new, MobCategory.MISC).sized(1.375f, 0.5625f).clientTrackingRange(10).build(new ResourceLocation(UnusualPrehistory.MODID, "chest_boat").toString()));

    // Paleo mobs
    public static final RegistryObject<EntityType<AmmoniteEntity>> AMMON = ENTITIES.register("ammon",
            () -> EntityType.Builder.of(AmmoniteEntity::new, MobCategory.WATER_CREATURE).sized(0.7f, 0.7f)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "ammon").toString()));

    public static final RegistryObject<EntityType<CotylorhynchusEntity>> COTY = ENTITIES.register("coty",
            () -> EntityType.Builder.of(CotylorhynchusEntity::new, MobCategory.CREATURE).sized(2.0f, 1.8f)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "coty").toString()));

    public static final RegistryObject<EntityType<DiplocaulusEntity>> DIPLOCAULUS = ENTITIES.register("diplocaulus",
            () -> EntityType.Builder.of(DiplocaulusEntity::new, MobCategory.CREATURE).sized(0.8F, 0.65F)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "diplocaulus").toString()));

    public static final RegistryObject<EntityType<DunkleosteusEntity>> DUNK = ENTITIES.register("dunk",
            () -> EntityType.Builder.of(DunkleosteusEntity::new, MobCategory.WATER_CREATURE).sized(2.2F, 1.75F)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "dunk").toString()));

    public static final RegistryObject<EntityType<EntityBabyDunk>> BABY_DUNK = ENTITIES.register("baby_dunk",
            () -> EntityType.Builder.of(EntityBabyDunk::new, MobCategory.WATER_AMBIENT).sized(1.1f, 0.6f)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "baby_dunk").toString()));

    public static final RegistryObject<EntityType<EdaphosaurusEntity>> EDAPHOSAURUS = ENTITIES.register("edaphosaurus",
            () -> EntityType.Builder.of(EdaphosaurusEntity::new, MobCategory.CREATURE).sized(1.4f, 1.75f)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "edaphosaurus").toString()));

    public static final RegistryObject<EntityType<EstemmenosuchusEntity>> ESTEMMENOSUCHUS = ENTITIES.register("estemmenosuchus",
            () -> EntityType.Builder.of(EstemmenosuchusEntity::new, MobCategory.CREATURE).sized(3.0f, 3.25f)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "estemmenosuchus").toString()));

    public static final RegistryObject<EntityType<HyneriaEntity>> HYNERIA = ENTITIES.register("hyneria",
            () -> EntityType.Builder.of(HyneriaEntity::new, MobCategory.WATER_CREATURE).sized(1.45f, 1.0f)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "hyneria").toString()));

    public static final RegistryObject<EntityType<HynerpetonEntity>> HYNERPETON = ENTITIES.register("hynerpteon",
            () -> EntityType.Builder.of(HynerpetonEntity::new, MobCategory.CREATURE).sized(1.1F, 1.5F)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "hynerpteon").toString()));

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
    public static final RegistryObject<EntityType<AntarctopeltaEntity>> ANTARCO = ENTITIES.register("antarctopelta",
            () -> EntityType.Builder.of(AntarctopeltaEntity::new, MobCategory.CREATURE).sized(2.1F, 1.8F)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "antarctopelta").toString()));

    public static final RegistryObject<EntityType<AnurognathusEntity>> ANURO = ENTITIES.register("anuro",
            () -> EntityType.Builder.of(AnurognathusEntity::new, MobCategory.CREATURE).sized(0.8f, 0.8f)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "anuro").toString()));

    public static final RegistryObject<EntityType<ArchelonEntity>> ARCHELON = ENTITIES.register("archelon",
            () -> EntityType.Builder.of(ArchelonEntity::new, MobCategory.WATER_CREATURE).sized(3.5f, 1.1f)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "archelon").toString()));

    public static final RegistryObject<EntityType<AustroraptorEntity>> AUSTRO = ENTITIES.register("austroraptor",
            () -> EntityType.Builder.of(AustroraptorEntity::new, MobCategory.CREATURE).sized(1.25F, 2.4F)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "austroraptor").toString()));

    public static final RegistryObject<EntityType<BalaurEntity>> BALAUR = ENTITIES.register("balaur",
            () -> EntityType.Builder.of(BalaurEntity::new, MobCategory.CREATURE).sized(0.9F, 1.15F)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "balaur").toString()));

    public static final RegistryObject<EntityType<BeelzebufoEntity>> BEELZ = ENTITIES.register("beelz",
            () -> EntityType.Builder.of(BeelzebufoEntity::new, MobCategory.CREATURE).sized(1.2f, 0.8f)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "beelz").toString()));

    public static final RegistryObject<EntityType<BeelzebufoTadpoleEntity>> BEELZE_TADPOLE = ENTITIES.register("beelz_tadpole",
            () -> EntityType.Builder.of(BeelzebufoTadpoleEntity::new, MobCategory.WATER_AMBIENT).sized(0.4f, 0.4f)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "beelz_tadpole").toString()));

    public static final RegistryObject<EntityType<BrachiosaurusEntity>> BRACHI = ENTITIES.register("brachi",
            () -> EntityType.Builder.of(BrachiosaurusEntity::new, MobCategory.CREATURE).sized(5.25F, 8.0F).setShouldReceiveVelocityUpdates(true).setUpdateInterval(1)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "brachi").toString()));

    public static final RegistryObject<EntityType<EryonEntity>> ERYON = ENTITIES.register("eryon",
            () -> EntityType.Builder.of(EryonEntity::new, MobCategory.CREATURE).sized(0.8f, 0.5f)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "eryon").toString()));

    public static final RegistryObject<EntityType<GlobidensEntity>> GLOBIDENS = ENTITIES.register("globidens",
            () -> EntityType.Builder.of(GlobidensEntity::new, MobCategory.CREATURE).sized(2.5F, 1.5F)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "globidens").toString()));

    public static final RegistryObject<EntityType<HwachavenatorEntity>> HWACHA = ENTITIES.register("hwachavenator",
            () -> EntityType.Builder.of(HwachavenatorEntity::new, MobCategory.CREATURE).sized(1.95F, 2.8F)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "hwachavenator").toString()));

    public static final RegistryObject<EntityType<KaprosuchusEntity>> KAPROSUCHUS = ENTITIES.register("kaprosuchus",
            () -> EntityType.Builder.of(KaprosuchusEntity::new, MobCategory.CREATURE).sized(1.3F, 1.1F)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "kaprosuchus").toString()));

    public static final RegistryObject<EntityType<KentrosaurusEntity>> KENTRO = ENTITIES.register("kentrosaurus",
            () -> EntityType.Builder.of(KentrosaurusEntity::new, MobCategory.CREATURE).sized(1.8F, 1.9F)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "kentrosaurus").toString()));

    public static final RegistryObject<EntityType<KimmeridgebrachypteraeschnidiumEntity>> KIMMER = ENTITIES.register("kimmer",
            () -> EntityType.Builder.of(KimmeridgebrachypteraeschnidiumEntity::new, MobCategory.CREATURE).sized(0.65f, 0.65f)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "kimmer").toString()));

    public static final RegistryObject<EntityType<LeedsichthysEntity>> LEEDSICHTHYS = ENTITIES.register("leedsichthys",
            () -> EntityType.Builder.of(LeedsichthysEntity::new, MobCategory.WATER_CREATURE).sized(8.0F, 5.0F)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "leedsichthys").toString()));

    public static final RegistryObject<EntityType<LeedsichthysPartEntity>> LEEDS_PART = ENTITIES.register("leeds_part",
            () -> EntityType.Builder.<LeedsichthysPartEntity>of(LeedsichthysPartEntity::new, MobCategory.MISC)
                    .sized(10.0F, 10.0F).setShouldReceiveVelocityUpdates(true)
                    .setUpdateInterval(1)
                    .noSummon()
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "leeds_part").toString()));

    public static final RegistryObject<EntityType<LongisquamaEntity>> LONGISQUAMA = ENTITIES.register("longisquama",
            () -> EntityType.Builder.of(LongisquamaEntity::new, MobCategory.CREATURE).sized(1.0F, 1.0F)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "longisquama").toString()));

    public static final RegistryObject<EntityType<MajungasaurusEntity>> MAJUNGA = ENTITIES.register("majunga",
            () -> EntityType.Builder.of(MajungasaurusEntity::new, MobCategory.CREATURE).sized(1.35F, 1.8F)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "majunga").toString()));

    public static final RegistryObject<EntityType<OviraptorEntity>> OVIRAPTOR = ENTITIES.register("oviraptor",
            () -> EntityType.Builder.of(OviraptorEntity::new, MobCategory.CREATURE).sized(0.8F, 1.2F)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "oviraptor").toString()));

    public static final RegistryObject<EntityType<PachycephalosaurusEntity>> PACHY = ENTITIES.register("pachy",
            () -> EntityType.Builder.of(PachycephalosaurusEntity::new, MobCategory.CREATURE).sized(1.2F, 1.9F)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "pachy").toString()));

    public static final RegistryObject<EntityType<ProtosphyraenaEntity>> PROTOSPHYRAENA = ENTITIES.register("protosphyraena",
            () -> EntityType.Builder.of(ProtosphyraenaEntity::new, MobCategory.WATER_CREATURE).sized(1.35f, 0.8f)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "protosphyraena").toString()));

    public static final RegistryObject<EntityType<PsittacosaurusEntity>> PSITTACO = ENTITIES.register("psittacosaurus",
            () -> EntityType.Builder.of(PsittacosaurusEntity::new, MobCategory.CREATURE).sized(1.0F, 1.0F)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "psittaco").toString()));

    public static final RegistryObject<EntityType<PterodaustroEntity>> PTERODAUSTRO = ENTITIES.register("pterodaustro",
            () -> EntityType.Builder.of(PterodaustroEntity::new, MobCategory.CREATURE).sized(0.8f, 0.8f)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "pterodaustro").toString()));

    public static final RegistryObject<EntityType<TanystropheusEntity>> TANY = ENTITIES.register("tany",
            () -> EntityType.Builder.of(TanystropheusEntity::new, MobCategory.CREATURE).sized(1.8F, 0.7F)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "tany").toString()));

    public static final RegistryObject<EntityType<TriceratopsEntity>> TRICERATOPS = ENTITIES.register("trike",
            () -> EntityType.Builder.of(TriceratopsEntity::new, MobCategory.CREATURE).sized(2.85F, 3.75F)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "trike").toString()));

    public static final RegistryObject<EntityType<TyrannosaurusEntity>> TYRANNOSAURUS = ENTITIES.register("rex",
            () -> EntityType.Builder.of(TyrannosaurusEntity::new, MobCategory.CREATURE).sized(3.25F, 4.0F)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "rex").toString()));

    public static final RegistryObject<EntityType<UlughbegsaurusEntity>> ULUG = ENTITIES.register("ulughbegsaurus",
            () -> EntityType.Builder.of(UlughbegsaurusEntity::new, MobCategory.CREATURE).sized(1.95F, 2.8F)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "ulughbegsaurus").toString()));

    public static final RegistryObject<EntityType<VelociraptorEntity>> VELOCIRAPTOR = ENTITIES.register("veloci",
            () -> EntityType.Builder.of(VelociraptorEntity::new, MobCategory.CREATURE).sized(0.9F, 0.9F)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "veloci").toString()));

    public static final RegistryObject<EntityType<XiphactinusEntity>> XIPH = ENTITIES.register("xiphactinus",
            () -> EntityType.Builder.of(XiphactinusEntity::new, MobCategory.WATER_CREATURE).sized(2.5F, 1.25F)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "xiphactinus").toString()));

    // Ceno mobs
    public static final RegistryObject<EntityType<BarinasuchusEntity>> BARINASUCHUS = ENTITIES.register("barinasuchus",
            () -> EntityType.Builder.of(BarinasuchusEntity::new, MobCategory.CREATURE).sized(2.4F, 2.2F)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "barinasuchus").toString()));

    public static final RegistryObject<EntityType<GigantopithicusEntity>> GIGANTOPITHICUS = ENTITIES.register("gigantopithicus",
            () -> EntityType.Builder.of(GigantopithicusEntity::new, MobCategory.CREATURE).sized(2.55F, 3.0F)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "gigantopithicus").toString()));

    public static final RegistryObject<EntityType<MammothEntity>> MAMMOTH = ENTITIES.register("mammoth",
            () -> EntityType.Builder.of(MammothEntity::new, MobCategory.CREATURE).sized(3.75F, 4.5F)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "mammoth").toString()));

    public static final RegistryObject<EntityType<MegalaniaEntity>> MEGALANIA = ENTITIES.register("megalania",
            () -> EntityType.Builder.of(MegalaniaEntity::new, MobCategory.CREATURE).sized(2.5F, 2.0F)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "megalania").toString()));

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

    public static final RegistryObject<EntityType<PsilopterusEntity>> PSILOPTERUS = ENTITIES.register("psilopterus",
            () -> EntityType.Builder.of(PsilopterusEntity::new, MobCategory.CREATURE).sized(1.1F, 1.5F)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "psilopterus").toString()));

    public static final RegistryObject<EntityType<SmilodonEntity>> SMILODON = ENTITIES.register("smilodon",
            () -> EntityType.Builder.of(SmilodonEntity::new, MobCategory.CREATURE).sized(1F, 1.6F)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "smilodon").toString()));

    public static final RegistryObject<EntityType<TalpanasEntity>> TALPANAS = ENTITIES.register("talpanas",
            () -> EntityType.Builder.of(TalpanasEntity::new, MobCategory.CREATURE).sized(0.8F, 0.8F)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "talpanas").toString()));

    public static final RegistryObject<EntityType<TelecrexEntity>> TELECREX = ENTITIES.register("telecrex",
            () -> EntityType.Builder.of(TelecrexEntity::new, MobCategory.CREATURE).sized(0.8F, 1.1F)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "telecrex").toString()));

    // Misc mobs
    public static final RegistryObject<EntityType<EncrustedEntity>> ENCRUSTED = ENTITIES.register("encrusted",
            () -> EntityType.Builder.of(EncrustedEntity::new, MobCategory.MONSTER).sized(1.5F, 1.95F)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "encrusted").toString()));

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

    // Unfinished 1.6 stuff

//    public static final RegistryObject<EntityType<DinosaurLandEgg>> DINO_LAND_EGG = ENTITIES.register("dino_land_egg",
//            () -> EntityType.Builder.<DinosaurLandEgg>of(DinosaurLandEgg::new, MobCategory.MISC)
//                    .sized(1, 1)
//                    .build(prefix("dino_land_egg").toString()));





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
