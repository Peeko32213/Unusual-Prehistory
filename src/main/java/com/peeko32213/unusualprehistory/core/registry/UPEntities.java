package com.peeko32213.unusualprehistory.core.registry;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.*;
import com.peeko32213.unusualprehistory.common.entity.iceberg.IcebergMammoth;
import com.peeko32213.unusualprehistory.common.entity.iceberg.IcebergSmilodon;
import com.peeko32213.unusualprehistory.common.entity.msc.baby.*;
import com.peeko32213.unusualprehistory.common.entity.msc.projectile.EntityAmberShot;
import com.peeko32213.unusualprehistory.common.entity.msc.projectile.EntityHwachaSpike;
import com.peeko32213.unusualprehistory.common.entity.msc.trail.EntityTrail;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = UnusualPrehistory.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class UPEntities {

    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES,
            UnusualPrehistory.MODID);

    public static final RegistryObject<EntityType<EntityStethacanthus>> STETHACANTHUS = ENTITIES.register("stethacanthus",
            () -> EntityType.Builder.of(EntityStethacanthus::new, MobCategory.WATER_CREATURE).sized(0.6f, 0.6f)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "stethacanthus").toString()));

    public static final RegistryObject<EntityType<EntityMajungasaurus>> MAJUNGA = ENTITIES.register("majunga",
            () -> EntityType.Builder.of(EntityMajungasaurus::new, MobCategory.CREATURE).sized(1.95F, 2.2F)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "majunga").toString()));

    public static final RegistryObject<EntityType<EntityAnurognathus>> ANURO = ENTITIES.register("anuro",
            () -> EntityType.Builder.of(EntityAnurognathus::new, MobCategory.CREATURE).sized(0.8f, 0.8f)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "anuro").toString()));

    public static final RegistryObject<EntityType<EntityBeelzebufo>> BEELZ = ENTITIES.register("beelz",
            () -> EntityType.Builder.of(EntityBeelzebufo::new, MobCategory.CREATURE).sized(1.2f, 0.8f)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "beelz").toString()));

    public static final RegistryObject<EntityType<EntityAmmonite>> AMMON = ENTITIES.register("ammon",
            () -> EntityType.Builder.of(EntityAmmonite::new, MobCategory.WATER_CREATURE).sized(0.7f, 0.7f)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "ammon").toString()));

    public static final RegistryObject<EntityType<EntityDunkleosteus>> DUNK = ENTITIES.register("dunk",
            () -> EntityType.Builder.of(EntityDunkleosteus::new, MobCategory.WATER_CREATURE).sized(2.5F, 1.75F)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "dunk").toString()));

    public static final RegistryObject<EntityType<EntityCotylorhynchus>> COTY = ENTITIES.register("coty",
            () -> EntityType.Builder.of(EntityCotylorhynchus::new, MobCategory.CREATURE).sized(1.9f, 1.4f)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "coty").toString()));

    public static final RegistryObject<EntityType<EntityBeelzebufoTadpole>> BEELZE_TADPOLE = ENTITIES.register("beelz_tadpole",
            () -> EntityType.Builder.of(EntityBeelzebufoTadpole::new, MobCategory.WATER_AMBIENT).sized(1.1f, 0.6f)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "beelz_tadpole").toString()));

    public static final RegistryObject<EntityType<EntityBabyDunk>> BABY_DUNK = ENTITIES.register("baby_dunk",
            () -> EntityType.Builder.of(EntityBabyDunk::new, MobCategory.WATER_AMBIENT).sized(1.1f, 0.6f)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "baby_dunk").toString()));

    public static final RegistryObject<EntityType<EntityScaumenacia>> SCAU = ENTITIES.register("scau",
            () -> EntityType.Builder.of(EntityScaumenacia::new, MobCategory.WATER_AMBIENT).sized(1.1f, 0.6f)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "scau").toString()));

    public static final RegistryObject<EntityType<EntityTriceratops>> TRIKE = ENTITIES.register("trike",
            () -> EntityType.Builder.of(EntityTriceratops::new, MobCategory.CREATURE).sized(4.4F, 4.4F)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "trike").toString()));

    public static final RegistryObject<EntityType<EntityPachycephalosaurus>> PACHY = ENTITIES.register("pachy",
            () -> EntityType.Builder.of(EntityPachycephalosaurus::new, MobCategory.CREATURE).sized(1.8F, 2.1F)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "pachy").toString()));

    public static final RegistryObject<EntityType<EntityBrachiosaurusTeen>> BRACHI_TEEN = ENTITIES.register("brachi_teen",
            () -> EntityType.Builder.of(EntityBrachiosaurusTeen::new, MobCategory.CREATURE).sized(5.2F, 5.4F).setShouldReceiveVelocityUpdates(true).setUpdateInterval(1)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "brachi").toString()));

    public static final RegistryObject<EntityType<EntityBrachiosaurus>> BRACHI = ENTITIES.register("brachi",
            () -> EntityType.Builder.of(EntityBrachiosaurus::new, MobCategory.CREATURE).sized(7.2F, 8.4F).setShouldReceiveVelocityUpdates(true).setUpdateInterval(1)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "brachi").toString()));

    public static final RegistryObject<EntityType<EntityVelociraptor>> VELOCI = ENTITIES.register("veloci",
            () -> EntityType.Builder.of(EntityVelociraptor::new, MobCategory.CREATURE).sized(0.9F, 0.9F)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "veloci").toString()));

    public static final RegistryObject<EntityType<EntityTyrannosaurusRex>> REX = ENTITIES.register("rex",
            () -> EntityType.Builder.of(EntityTyrannosaurusRex::new, MobCategory.CREATURE).sized(3.4F, 5.0F)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "rex").toString()));

    public static final RegistryObject<EntityType<EntityEncrusted>> ENCRUSTED = ENTITIES.register("encrusted",
            () -> EntityType.Builder.of(EntityEncrusted::new, MobCategory.MONSTER).sized(2.3F, 1.95F)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "encrusted").toString()));

    public static final RegistryObject<EntityType<EntityAmberShot>> AMBER_SHOT = ENTITIES.register(
            "amber_shot", () -> EntityType.Builder.<EntityAmberShot>of(EntityAmberShot::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F).clientTrackingRange(9)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "amber_shot").toString()));

    public static final RegistryObject<EntityType<EntityHwachaSpike>> HWACHA_SPIKE = ENTITIES.register(
            "hwacha_spike", () -> EntityType.Builder.<EntityHwachaSpike>of(EntityHwachaSpike::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F).clientTrackingRange(9).noSummon()
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "hwacha_spike").toString()));
    public static final RegistryObject<EntityType<EntityBabyRex>> BABY_REX = ENTITIES.register("baby_rex",
            () -> EntityType.Builder.of(EntityBabyRex::new, MobCategory.CREATURE).sized(1.1f, 0.6f)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "baby_rex").toString()));

    public static final RegistryObject<EntityType<EntityBabyBrachi>> BABY_BRACHI = ENTITIES.register("baby_brachi",
            () -> EntityType.Builder.of(EntityBabyBrachi::new, MobCategory.CREATURE).sized(1.3f, 2.1f)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "baby_brachi").toString()));

    public static final RegistryObject<EntityType<EntityEryon>> ERYON = ENTITIES.register("eryon",
            () -> EntityType.Builder.of(EntityEryon::new, MobCategory.CREATURE).sized(0.8f, 0.8f)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "eryon").toString()));

    public static final RegistryObject<EntityType<EntityAustroraptor>> AUSTRO = ENTITIES.register("austroraptor",
            () -> EntityType.Builder.of(EntityAustroraptor::new, MobCategory.CREATURE).sized(1.95F, 2.2F)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "austroraptor").toString()));

    public static final RegistryObject<EntityType<EntityAntarctopelta>> ANTARCO = ENTITIES.register("antarctopelta",
            () -> EntityType.Builder.of(EntityAntarctopelta::new, MobCategory.CREATURE).sized(2.2F, 1.9F)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "antarctopelta").toString()));

    public static final RegistryObject<EntityType<EntityUlughbegsaurus>> ULUG = ENTITIES.register("ulughbegsaurus",
            () -> EntityType.Builder.of(EntityUlughbegsaurus::new, MobCategory.CREATURE).sized(1.95F, 2.8F)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "ulughbegsaurus").toString()));

    public static final RegistryObject<EntityType<EntityKentrosaurus>> KENTRO = ENTITIES.register("kentrosaurus",
            () -> EntityType.Builder.of(EntityKentrosaurus::new, MobCategory.CREATURE).sized(2.2F, 1.9F)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "kentrosaurus").toString()));

    public static final RegistryObject<EntityType<EntityHwachavenator>> HWACHA = ENTITIES.register("hwachavenator",
            () -> EntityType.Builder.of(EntityHwachavenator::new, MobCategory.CREATURE).sized(1.95F, 2.8F)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "hwachavenator").toString()));

    public static final RegistryObject<EntityType<EntityTalapanas>> TALAPANAS = ENTITIES.register("talapanas",
            () -> EntityType.Builder.of(EntityTalapanas::new, MobCategory.CREATURE).sized(0.8F, 0.8F)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "talapanas").toString()));

    public static final RegistryObject<EntityType<EntityGigantopithicus>> GIGANTOPITHICUS = ENTITIES.register("gigantopithicus",
            () -> EntityType.Builder.of(EntityGigantopithicus::new, MobCategory.CREATURE).sized(4.0F, 4.0F)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "gigantopithicus").toString()));

    public static final RegistryObject<EntityType<EntityBarinasuchus>> BARINASUCHUS = ENTITIES.register("barinasuchus",
            () -> EntityType.Builder.of(EntityBarinasuchus::new, MobCategory.CREATURE).sized(3.1F, 2.0F)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "barinasuchus").toString()));

    public static final RegistryObject<EntityType<EntityMegatherium>> MEGATHERIUM = ENTITIES.register("megatherium",
            () -> EntityType.Builder.of(EntityMegatherium::new, MobCategory.CREATURE).sized(2.9F, 3.0F)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "megatherium").toString()));

    public static final RegistryObject<EntityType<EntitySmilodon>> SMILODON = ENTITIES.register("smilodon",
            () -> EntityType.Builder.of(EntitySmilodon::new, MobCategory.CREATURE).sized(2.0F, 2.0F)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "smilodon").toString()));

    public static final RegistryObject<EntityType<EntityParaceratherium>> PARACERATHERIUM = ENTITIES.register("paraceratherium",
            () -> EntityType.Builder.of(EntityParaceratherium::new, MobCategory.CREATURE).sized(5.0F, 8.0F)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "paraceratherium").toString()));

    public static final RegistryObject<EntityType<EntityMammoth>> MAMMOTH = ENTITIES.register("mammoth",
            () -> EntityType.Builder.of(EntityMammoth::new, MobCategory.CREATURE).sized(5.0F, 5.0F)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "mammoth").toString()));

    public static final RegistryObject<EntityType<EntityPalaeophis>> PALAEOPHIS = ENTITIES.register("palaeophis",
            () -> EntityType.Builder.of(EntityPalaeophis::new, MobCategory.WATER_CREATURE).sized(5.5F, 1.5F)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "palaophis").toString()));

    public static final RegistryObject<EntityType<EntityMegalania>> MEGALANIA = ENTITIES.register("megalania",
            () -> EntityType.Builder.of(EntityMegalania::new, MobCategory.CREATURE).sized(3.0F, 2.0F)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "megalania").toString()));
    public static final RegistryObject<EntityType<EntityTrail>> ENTITY_TRAIL = ENTITIES.register("entity_trail",
            () -> EntityType.Builder.<EntityTrail>of(EntityTrail::new, MobCategory.MISC)
                    .fireImmune().sized(1, 1).noSummon()
                    .clientTrackingRange(4)
                    .updateInterval(5)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "entity_trail").toString()));

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


    private static final EntityType registerEntity(EntityType.Builder builder, String entityName) {
        return (EntityType) builder.build(entityName);
    }

    public static Predicate<LivingEntity> buildPredicateFromTag(TagKey<EntityType<?>> entityTag){
        if(entityTag == null){
            return Predicates.alwaysFalse();
        }else{
            return (com.google.common.base.Predicate<LivingEntity>) e -> e.isAlive() && e.getType().is(entityTag);
        }
    }

}
