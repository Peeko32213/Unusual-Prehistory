package com.peeko32213.unusualprehistory.core.registry;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.*;
import com.peeko32213.unusualprehistory.common.entity.baby.EntityBabyDunk;
import com.peeko32213.unusualprehistory.common.entity.baby.EntityBeelzebufoTadpole;
import com.peeko32213.unusualprehistory.common.entity.EntityDunkleosteus;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = UnusualPrehistory.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class UPEntities {

    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES,
            UnusualPrehistory.MODID);

    public static final RegistryObject<EntityType<EntityStethacanthus>> STETHACANTHUS = ENTITIES.register("stethacanthus",
            () -> EntityType.Builder.of(EntityStethacanthus::new, MobCategory.WATER_CREATURE).sized(0.8f, 0.8f)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "stethacanthus").toString()));

    public static final RegistryObject<EntityType<EntityMajungasaurus>> MAJUNGA = ENTITIES.register("majunga",
            () -> EntityType.Builder.of(EntityMajungasaurus::new, MobCategory.CREATURE).sized(2.0f, 2.0f)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "majunga").toString()));

    public static final RegistryObject<EntityType<EntityAnurognathus>> ANURO = ENTITIES.register("anuro",
            () -> EntityType.Builder.of(EntityAnurognathus::new, MobCategory.CREATURE).sized(0.8f, 0.8f)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "anuro").toString()));

    public static final RegistryObject<EntityType<EntityBeelzebufo>> BEELZ = ENTITIES.register("beelz",
            () -> EntityType.Builder.of(EntityBeelzebufo::new, MobCategory.CREATURE).sized(1.5f, 0.8f)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "beelz").toString()));

    public static final RegistryObject<EntityType<EntityAmmonite>> AMMON = ENTITIES.register("ammon",
            () -> EntityType.Builder.of(EntityAmmonite::new, MobCategory.WATER_CREATURE).sized(0.7f, 0.7f)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "ammon").toString()));

    public static final RegistryObject<EntityType<EntityDunkleosteus>> DUNK = ENTITIES.register("dunk",
            () -> EntityType.Builder.of(EntityDunkleosteus::new, MobCategory.WATER_CREATURE).sized(2.8f, 2.8f)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "dunk").toString()));

    public static final RegistryObject<EntityType<EntityCotylorhynchus>> COTY = ENTITIES.register("coty",
            () -> EntityType.Builder.of(EntityCotylorhynchus::new, MobCategory.CREATURE).sized(1.9f, 1.2f)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "coty").toString()));

    public static final RegistryObject<EntityType<EntityBeelzebufoTadpole>> BEELZE_TADPOLE = ENTITIES.register("beelz_tadpole",
            () -> EntityType.Builder.of(EntityBeelzebufoTadpole::new, MobCategory.WATER_AMBIENT).sized(0.4f, 0.3f)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "beelz_tadpole").toString()));

    public static final RegistryObject<EntityType<EntityBabyDunk>> BABY_DUNK = ENTITIES.register("baby_dunk",
            () -> EntityType.Builder.of(EntityBabyDunk::new, MobCategory.WATER_AMBIENT).sized(0.4f, 0.3f)
                    .build(new ResourceLocation(UnusualPrehistory.MODID, "baby_dunk").toString()));

}
