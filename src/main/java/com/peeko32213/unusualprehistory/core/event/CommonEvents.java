package com.peeko32213.unusualprehistory.core.event;

import com.google.common.base.Predicates;
import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.*;
import com.peeko32213.unusualprehistory.common.entity.baby.EntityBabyDunk;
import com.peeko32213.unusualprehistory.common.entity.baby.EntityBeelzebufoTadpole;
import com.peeko32213.unusualprehistory.common.entity.EntityDunkleosteus;
import com.peeko32213.unusualprehistory.common.entity.render.BaseEntityRender;
import com.peeko32213.unusualprehistory.common.item.tool.ItemVelociShield;
import com.peeko32213.unusualprehistory.core.registry.UPEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import java.util.List;
import java.util.function.Predicate;

@Mod.EventBusSubscriber(modid = UnusualPrehistory.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonEvents {

    @SubscribeEvent
    public static void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
        });
    }

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
        event.put(UPEntities.BRACHI.get(), EntityBrachiosaurus.createAttributes().build());
        event.put(UPEntities.VELOCI.get(), EntityVelociraptor.createAttributes().build());
        event.put(UPEntities.REX.get(), EntityTyrannosaurusRex.createAttributes().build());

        event.put(UPEntities.AMMON_RENDER.get(), BaseEntityRender.createAttributes().build());
        event.put(UPEntities.MAJUNGA_RENDER.get(), BaseEntityRender.createAttributes().build());
        event.put(UPEntities.DUNK_RENDER.get(), BaseEntityRender.createAttributes().build());
        event.put(UPEntities.COTY_RENDER.get(), BaseEntityRender.createAttributes().build());
        event.put(UPEntities.BEELZ_RENDER.get(), BaseEntityRender.createAttributes().build());
        event.put(UPEntities.ANURO_RENDER.get(), BaseEntityRender.createAttributes().build());
        event.put(UPEntities.STETHA_RENDER.get(), BaseEntityRender.createAttributes().build());
        event.put(UPEntities.SCAU_RENDER.get(), BaseEntityRender.createAttributes().build());

    }

    public static Predicate<LivingEntity> buildPredicateFromTag(TagKey<EntityType<?>> entityTag) {
        if (entityTag == null) {
            return Predicates.alwaysFalse();
        } else {
            return (com.google.common.base.Predicate<LivingEntity>) e -> e.isAlive() && e.getType().is(entityTag);
        }
    }
}