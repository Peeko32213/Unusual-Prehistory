package com.peeko32213.unusualprehistory.core.registry;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.*;
import com.peeko32213.unusualprehistory.common.entity.baby.EntityBabyBrachi;
import com.peeko32213.unusualprehistory.common.entity.baby.EntityBabyDunk;
import com.peeko32213.unusualprehistory.common.entity.baby.EntityBabyRex;
import com.peeko32213.unusualprehistory.common.entity.baby.EntityBeelzebufoTadpole;
import com.peeko32213.unusualprehistory.common.entity.EntityDunkleosteus;
import com.peeko32213.unusualprehistory.common.entity.projectile.EntityAmberShot;
import com.peeko32213.unusualprehistory.common.entity.render.BaseEntityRender;
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

    public static final RegistryObject<EntityType<EntityBrachiosaurus>> BRACHI = ENTITIES.register("brachi",
            () -> EntityType.Builder.of(EntityBrachiosaurus::new, MobCategory.CREATURE).sized(5.2F, 5.4F).setShouldReceiveVelocityUpdates(true).setUpdateInterval(1)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "brachi").toString()));

    public static final RegistryObject<EntityType<EntityVelociraptor>> VELOCI = ENTITIES.register("veloci",
            () -> EntityType.Builder.of(EntityVelociraptor::new, MobCategory.CREATURE).sized(0.7F, 0.9F)
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

    public static final RegistryObject<EntityType<EntityBabyRex>> BABY_REX = ENTITIES.register("baby_rex",
            () -> EntityType.Builder.of(EntityBabyRex::new, MobCategory.CREATURE).sized(1.1f, 0.6f)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "baby_rex").toString()));

    public static final RegistryObject<EntityType<EntityBabyBrachi>> BABY_BRACHI = ENTITIES.register("baby_brachi",
            () -> EntityType.Builder.of(EntityBabyBrachi::new, MobCategory.CREATURE).sized(1.3f, 2.1f)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "baby_brachi").toString()));

    public static final RegistryObject<EntityType<EntityEryon>> ERYON = ENTITIES.register("eryon",
            () -> EntityType.Builder.of(EntityEryon::new, MobCategory.CREATURE).sized(0.8f, 0.8f)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "eryon").toString()));

    public static final RegistryObject<EntityType<BaseEntityRender>> AMMON_RENDER = ENTITIES.register("ammon_render",
            () -> EntityType.Builder.of(BaseEntityRender::new, MobCategory.MISC).sized(0.7f, 0.7f)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "ammon_render").toString()));

    public static final RegistryObject<EntityType<BaseEntityRender>> MAJUNGA_RENDER = ENTITIES.register("majunga_render",
            () -> EntityType.Builder.of(BaseEntityRender::new, MobCategory.MISC).sized(0.7f, 0.7f)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "majunga_render").toString()));

    public static final RegistryObject<EntityType<BaseEntityRender>> DUNK_RENDER = ENTITIES.register("dunk_render",
            () -> EntityType.Builder.of(BaseEntityRender::new, MobCategory.MISC).sized(0.7f, 0.7f)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "dunk_render").toString()));

    public static final RegistryObject<EntityType<BaseEntityRender>> COTY_RENDER = ENTITIES.register("coty_render",
            () -> EntityType.Builder.of(BaseEntityRender::new, MobCategory.MISC).sized(0.7f, 0.7f)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "coty_render").toString()));

    public static final RegistryObject<EntityType<BaseEntityRender>> BEELZ_RENDER = ENTITIES.register("beelz_render",
            () -> EntityType.Builder.of(BaseEntityRender::new, MobCategory.MISC).sized(0.7f, 0.7f)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "beelz_render").toString()));

    public static final RegistryObject<EntityType<BaseEntityRender>> ANURO_RENDER = ENTITIES.register("anuro_render",
            () -> EntityType.Builder.of(BaseEntityRender::new, MobCategory.MISC).sized(0.7f, 0.7f)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "anuro_render").toString()));

    public static final RegistryObject<EntityType<BaseEntityRender>> STETHA_RENDER = ENTITIES.register("stetha_render",
            () -> EntityType.Builder.of(BaseEntityRender::new, MobCategory.MISC).sized(0.7f, 0.7f)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "stetha_render").toString()));

    public static final RegistryObject<EntityType<BaseEntityRender>> SCAU_RENDER = ENTITIES.register("scau_render",
            () -> EntityType.Builder.of(BaseEntityRender::new, MobCategory.MISC).sized(0.7f, 0.7f)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "scau_render").toString()));

    public static final RegistryObject<EntityType<BaseEntityRender>> TRIKE_RENDER = ENTITIES.register("trike_render",
            () -> EntityType.Builder.of(BaseEntityRender::new, MobCategory.MISC).sized(0.7f, 0.7f)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "trike_render").toString()));

    public static final RegistryObject<EntityType<BaseEntityRender>> PACHY_RENDER = ENTITIES.register("pachy_render",
            () -> EntityType.Builder.of(BaseEntityRender::new, MobCategory.MISC).sized(0.7f, 0.7f)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "pachy_render").toString()));

    public static final RegistryObject<EntityType<BaseEntityRender>> BRACHI_RENDER = ENTITIES.register("brachi_render",
            () -> EntityType.Builder.of(BaseEntityRender::new, MobCategory.MISC).sized(0.7f, 0.7f)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "brachi_render").toString()));

    public static final RegistryObject<EntityType<BaseEntityRender>> RAPTOR_RENDER = ENTITIES.register("raptor_render",
            () -> EntityType.Builder.of(BaseEntityRender::new, MobCategory.MISC).sized(0.7f, 0.7f)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "raptor_render").toString()));

    public static final RegistryObject<EntityType<BaseEntityRender>> REX_RENDER = ENTITIES.register("rex_render",
            () -> EntityType.Builder.of(BaseEntityRender::new, MobCategory.MISC).sized(0.7f, 0.7f)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "rex_render").toString()));

    public static final RegistryObject<EntityType<BaseEntityRender>> ENCRUSTED_RENDER = ENTITIES.register("encrusted_render",
            () -> EntityType.Builder.of(BaseEntityRender::new, MobCategory.MISC).sized(0.7f, 0.7f)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "encrusted_render").toString()));

    public static final RegistryObject<EntityType<BaseEntityRender>> ERYON_RENDER = ENTITIES.register("eryon_render",
            () -> EntityType.Builder.of(BaseEntityRender::new, MobCategory.MISC).sized(0.7f, 0.7f)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "eryon_render").toString()));


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
