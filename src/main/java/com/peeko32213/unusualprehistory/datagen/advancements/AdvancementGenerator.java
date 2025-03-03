package com.peeko32213.unusualprehistory.datagen.advancements;

import com.peeko32213.unusualprehistory.core.registry.UPBlocks;
import com.peeko32213.unusualprehistory.core.registry.UPEntities;
import com.peeko32213.unusualprehistory.core.registry.UPItems;
import com.peeko32213.unusualprehistory.core.registry.util.BarinaTameTrigger;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeAdvancementProvider;

import java.util.function.Consumer;

import static com.peeko32213.unusualprehistory.UnusualPrehistory.*;
import static com.peeko32213.unusualprehistory.UnusualPrehistory.prefixS;

public class AdvancementGenerator implements ForgeAdvancementProvider.AdvancementGenerator{


    @Override
    public void generate(HolderLookup.Provider registries, Consumer<Advancement> consumer, ExistingFileHelper helper) {

        Advancement unusualprehistory = Advancement.Builder.advancement()
                .display(UPItems.ENCYLOPEDIA.get(),
                        getTranslation("advancement.root", new Object[0]),
                        getTranslation("advancement.root.desc", new Object[0]),
                        prefix("textures/block/ginkgo_log_side.png"),
                        FrameType.TASK, false, true, false)
                .addCriterion("acquired_encyclopedia", InventoryChangeTrigger.TriggerInstance.hasItems((ItemLike)UPItems.ENCYLOPEDIA.get()))
                .save(consumer, prefixS("main/root"));

        Advancement fossil = getAdvancement(unusualprehistory, (ItemLike)UPItems.MEZO_FOSSIL.get(), "acquire_fossil", FrameType.TASK, true, true, false)
                .addCriterion("mezo_fossil", InventoryChangeTrigger.TriggerInstance.hasItems((ItemLike)UPItems.MEZO_FOSSIL.get()))
                .addCriterion("paleo_fossil", InventoryChangeTrigger.TriggerInstance.hasItems((ItemLike)UPItems.PALEO_FOSSIL.get()))
                .addCriterion("plant_fossil", InventoryChangeTrigger.TriggerInstance.hasItems((ItemLike)UPItems.PLANT_FOSSIL.get()))
                .addCriterion("amber_fossil", InventoryChangeTrigger.TriggerInstance.hasItems((ItemLike)UPItems.AMBER_FOSSIL.get()))
                .addCriterion("frozen_fossil", InventoryChangeTrigger.TriggerInstance.hasItems((ItemLike)UPItems.FROZEN_FOSSIL.get()))
                .addCriterion("tar_fossil", InventoryChangeTrigger.TriggerInstance.hasItems((ItemLike)UPItems.TAR_FOSSIL.get()))
                .requirements(RequirementsStrategy.OR).save(consumer, prefixS("main/fossil"));

        Advancement analyzer = getAdvancement(fossil, (ItemLike) UPBlocks.ANALYZER.get(), "craft_analyzer", FrameType.TASK, true, true, false)
                .addCriterion("anaylzer", InventoryChangeTrigger.TriggerInstance.hasItems((ItemLike)UPBlocks.ANALYZER.get()))
                .save(consumer, prefixS("main/analyzer"));

        Advancement amberFossil = getAdvancement(fossil, (ItemLike)UPItems.AMBER_FOSSIL.get(), "acquire_amber_fossil", FrameType.CHALLENGE, true, true, true)
                .addCriterion("amber_fossil", InventoryChangeTrigger.TriggerInstance.hasItems((ItemLike)UPItems.AMBER_FOSSIL.get()))
                .save(consumer, prefixS("main/amber_fossil"));

        Advancement amberStaff = getAdvancement(amberFossil, (ItemLike)UPItems.ADORNED_STAFF.get(), "acquire_adorned_staff", FrameType.TASK, true, true, true)
                .addCriterion("adorned_staff", InventoryChangeTrigger.TriggerInstance.hasItems((ItemLike)UPItems.ADORNED_STAFF.get()))
                .save(consumer, prefixS("main/adorned_staff"));

        Advancement cultivator = getAdvancement(analyzer, (ItemLike) UPBlocks.CULTIVATOR.get(), "craft_cultivator", FrameType.TASK, true, true, false)
                .addCriterion("cultivator", InventoryChangeTrigger.TriggerInstance.hasItems((ItemLike)UPBlocks.CULTIVATOR.get()))
                .save(consumer, prefixS("main/cultivator"));

        Advancement eggs = getAdvancement(cultivator, (ItemLike) UPBlocks.COTY_EGG.get(), "obtain_egg", FrameType.TASK, true, true, true)
                .addCriterion("austro_egg", InventoryChangeTrigger.TriggerInstance.hasItems((ItemLike)UPBlocks.AUSTRO_EGG.get()))
                .addCriterion("coty_egg", InventoryChangeTrigger.TriggerInstance.hasItems((ItemLike)UPBlocks.COTY_EGG.get()))
                .addCriterion("hwacha_egg", InventoryChangeTrigger.TriggerInstance.hasItems((ItemLike)UPBlocks.HWACHA_EGG.get()))
                .addCriterion("kentro_egg", InventoryChangeTrigger.TriggerInstance.hasItems((ItemLike)UPBlocks.KENTRO_EGG.get()))
                .addCriterion("antarcto_egg", InventoryChangeTrigger.TriggerInstance.hasItems((ItemLike)UPBlocks.ANTARCO_EGG.get()))
                .addCriterion("anuro_egg", InventoryChangeTrigger.TriggerInstance.hasItems((ItemLike)UPBlocks.ANURO_EGG.get()))
                .addCriterion("brachi_egg", InventoryChangeTrigger.TriggerInstance.hasItems((ItemLike)UPBlocks.BRACHI_EGG.get()))
                .addCriterion("majunga_egg", InventoryChangeTrigger.TriggerInstance.hasItems((ItemLike)UPBlocks.MAJUNGA_EGG.get()))
                .addCriterion("pachy_egg", InventoryChangeTrigger.TriggerInstance.hasItems((ItemLike)UPBlocks.PACHY_EGG.get()))
                .addCriterion("raptor_egg", InventoryChangeTrigger.TriggerInstance.hasItems((ItemLike)UPBlocks.RAPTOR_EGG.get()))
                .addCriterion("rex_egg", InventoryChangeTrigger.TriggerInstance.hasItems((ItemLike)UPBlocks.REX_EGG.get()))
                .addCriterion("barina_egg", InventoryChangeTrigger.TriggerInstance.hasItems((ItemLike)UPBlocks.BARINA_EGG.get()))
                .addCriterion("talapanas_egg", InventoryChangeTrigger.TriggerInstance.hasItems((ItemLike)UPBlocks.TALPANAS_EGG.get()))
                .addCriterion("trike_egg", InventoryChangeTrigger.TriggerInstance.hasItems((ItemLike)UPBlocks.TRIKE_EGG.get()))
                .addCriterion("ulugh_egg", InventoryChangeTrigger.TriggerInstance.hasItems((ItemLike)UPBlocks.ULUGH_EGG.get()))
                .addCriterion("eryon_egg", InventoryChangeTrigger.TriggerInstance.hasItems((ItemLike)UPBlocks.ERYON_EGGS.get()))
                .addCriterion("scau_egg", InventoryChangeTrigger.TriggerInstance.hasItems((ItemLike)UPBlocks.SCAU_EGGS.get()))
                .addCriterion("ammon_egg", InventoryChangeTrigger.TriggerInstance.hasItems((ItemLike)UPBlocks.AMON_EGGS.get()))
                .addCriterion("beelze_egg", InventoryChangeTrigger.TriggerInstance.hasItems((ItemLike)UPBlocks.BEELZE_EGGS.get()))
                .addCriterion("stetha_egg", InventoryChangeTrigger.TriggerInstance.hasItems((ItemLike)UPBlocks.STETHA_EGGS.get()))
                .addCriterion("dunk_egg", InventoryChangeTrigger.TriggerInstance.hasItems((ItemLike)UPBlocks.DUNK_EGGS.get()))
                .requirements(RequirementsStrategy.OR)
                .save(consumer, prefixS("main/egg"));

        Advancement embryo = getAdvancement(cultivator, (ItemLike) UPItems.SMILODON_EMBRYO.get(), "obtain_embryo", FrameType.TASK, true, true, true)
                .addCriterion("smilodon_embryo", InventoryChangeTrigger.TriggerInstance.hasItems((ItemLike)UPItems.SMILODON_EMBRYO.get()))
                .addCriterion("mammoth_embryo", InventoryChangeTrigger.TriggerInstance.hasItems((ItemLike)UPItems.MAMMOTH_EMBRYO.get()))
                .addCriterion("megath_embryo", InventoryChangeTrigger.TriggerInstance.hasItems((ItemLike)UPItems.MEGATH_EMBRYO.get()))
                .addCriterion("giganto_embryo", InventoryChangeTrigger.TriggerInstance.hasItems((ItemLike)UPItems.GIGANTO_EMBRYO.get()))
                .addCriterion("paracer_embryo", InventoryChangeTrigger.TriggerInstance.hasItems((ItemLike)UPItems.PARACER_EMBRYO.get()))
                .addCriterion("palaeo_embryo", InventoryChangeTrigger.TriggerInstance.hasItems((ItemLike)UPItems.PALAEO_EMBRYO.get()))
                .requirements(RequirementsStrategy.OR)
                .save(consumer, prefixS("main/embryo"));

        Advancement ammon = getAdvancement(eggs, (ItemLike)UPItems.AMMONITE_FLASK.get(), "interact_ammonite", FrameType.TASK, true, true, true)
                .addCriterion("damage_ammon", PlayerHurtEntityTrigger.TriggerInstance.playerHurtEntity(DamagePredicate.Builder.damageInstance().type(DamageSourcePredicate.Builder.damageType().direct(EntityPredicate.Builder.entity().of(UPEntities.AMMON.get())))))
                .addCriterion("interact_ammon", PlayerInteractTrigger.TriggerInstance.itemUsedOnEntity(ItemPredicate.Builder.item(), EntityPredicate.wrap(EntityPredicate.Builder.entity().of(UPEntities.AMMON.get()).build())))
                .addCriterion("killed_ammon", KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(UPEntities.AMMON.get())))
                .requirements(RequirementsStrategy.OR)
                .save(consumer, prefixS("main/ammon"));

        Advancement ammon_drop = getAdvancement(ammon, (ItemLike)UPItems.SHELL_SHARD.get(), "obtain_ammon_drop", FrameType.TASK, true, true, true)
                .addCriterion("shell_shard", InventoryChangeTrigger.TriggerInstance.hasItems((ItemLike)UPItems.SHELL_SHARD.get()))
                .save(consumer, prefixS("main/ammon_drop"));

        Advancement ammon_weapon = getAdvancement(ammon_drop, (ItemLike)UPItems.WARPICK.get(), "obtain_ammon_weapon", FrameType.TASK, true, true, true)
                .addCriterion("warpick", InventoryChangeTrigger.TriggerInstance.hasItems((ItemLike)UPItems.WARPICK.get()))
                .save(consumer, prefixS("main/ammon_weapon"));

        Advancement antarcto = getAdvancement(eggs, (ItemLike)UPItems.ANTARCTO_FLASK.get(), "interact_antarcto", FrameType.TASK, true, true, true)
                .addCriterion("damage_antarcto", PlayerHurtEntityTrigger.TriggerInstance.playerHurtEntity(DamagePredicate.Builder.damageInstance().type(DamageSourcePredicate.Builder.damageType().direct(EntityPredicate.Builder.entity().of(UPEntities.ANTARCO.get())))))
                .addCriterion("interact_antarcto", PlayerInteractTrigger.TriggerInstance.itemUsedOnEntity(ItemPredicate.Builder.item(), EntityPredicate.wrap(EntityPredicate.Builder.entity().of(UPEntities.ANTARCO.get()).build())))
                .addCriterion("killed_antarcto", KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(UPEntities.ANTARCO.get())))
                .requirements(RequirementsStrategy.OR)

                .save(consumer, prefixS("main/antarcto"));

        Advancement antarcto_weapon = getAdvancement(antarcto, (ItemLike)UPItems.PRIMAL_MACUAHUITL.get(), "obtain_antarcto_weapon", FrameType.CHALLENGE, true, true, true)
                .addCriterion("primal_macuahuitl", InventoryChangeTrigger.TriggerInstance.hasItems((ItemLike)UPItems.PRIMAL_MACUAHUITL.get()))
                .save(consumer, prefixS("main/antarcto_weapon"));

        Advancement austro = getAdvancement(eggs, (ItemLike)UPItems.AUSTRO_FLASK.get(), "interact_austro", FrameType.TASK, true, true, true)
                .addCriterion("damage_austro", PlayerHurtEntityTrigger.TriggerInstance.playerHurtEntity(DamagePredicate.Builder.damageInstance().type(DamageSourcePredicate.Builder.damageType().direct(EntityPredicate.Builder.entity().of(UPEntities.AUSTRO.get())))))
                .addCriterion("interact_austro", PlayerInteractTrigger.TriggerInstance.itemUsedOnEntity(ItemPredicate.Builder.item(), EntityPredicate.wrap(EntityPredicate.Builder.entity().of(UPEntities.AUSTRO.get()).build())))
                .addCriterion("killed_austro", KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(UPEntities.AUSTRO.get())))
                .requirements(RequirementsStrategy.OR)

                .save(consumer, prefixS("main/austro"));

        Advancement austro_boots = getAdvancement(austro, (ItemLike)UPItems.AUSTRO_BOOTS.get(), "obtain_austro_boots", FrameType.CHALLENGE, true, true, true)
                .addCriterion("primal_macuahuitl", InventoryChangeTrigger.TriggerInstance.hasItems((ItemLike)UPItems.AUSTRO_BOOTS.get()))
                .save(consumer,prefixS("main/austro_boots"));

        Advancement beelze = getAdvancement(eggs, (ItemLike)UPItems.BEELZ_FLASK.get(), "interact_beelze", FrameType.TASK, true, true, true)
                .addCriterion("damage_beelze", PlayerHurtEntityTrigger.TriggerInstance.playerHurtEntity(DamagePredicate.Builder.damageInstance().type(DamageSourcePredicate.Builder.damageType().direct(EntityPredicate.Builder.entity().of(UPEntities.BEELZ.get())))))
                .addCriterion("interact_beelze", PlayerInteractTrigger.TriggerInstance.itemUsedOnEntity(ItemPredicate.Builder.item(), EntityPredicate.wrap(EntityPredicate.Builder.entity().of(UPEntities.BEELZ.get()).build())))
                .addCriterion("killed_beelze", KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(UPEntities.BEELZ.get())))
                .requirements(RequirementsStrategy.OR)

                .save(consumer, prefixS("main/beelze"));

        Advancement beelze_riding = getAdvancement(beelze, (ItemLike)UPItems.MEAT_ON_A_STICK.get(), "obtain_meat_stick", FrameType.CHALLENGE, true, true, true)
                .addCriterion("meat_on_a_stick", InventoryChangeTrigger.TriggerInstance.hasItems((ItemLike)UPItems.MEAT_ON_A_STICK.get()))
                .save(consumer, prefixS("main/meat_stick"));

        Advancement pachy = getAdvancement(eggs, (ItemLike)UPItems.PACHY_FLASK.get(), "interact_pachy", FrameType.TASK, true, true, true)
                .addCriterion("damage_pachy", PlayerHurtEntityTrigger.TriggerInstance.playerHurtEntity(DamagePredicate.Builder.damageInstance().type(DamageSourcePredicate.Builder.damageType().direct(EntityPredicate.Builder.entity().of(UPEntities.PACHY.get())))))
                .addCriterion("interact_pachy", PlayerInteractTrigger.TriggerInstance.itemUsedOnEntity(ItemPredicate.Builder.item(), EntityPredicate.wrap(EntityPredicate.Builder.entity().of(UPEntities.PACHY.get()).build())))
                .addCriterion("killed_pachy", KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(UPEntities.PACHY.get())))
                .requirements(RequirementsStrategy.OR)

                .save(consumer, prefixS("main/pachy"));

        Advancement ulugh = getAdvancement(eggs, (ItemLike)UPItems.ULUGH_FLASK.get(), "interact_ulugh", FrameType.TASK, true, true, true)
                .addCriterion("damage_ulugh", PlayerHurtEntityTrigger.TriggerInstance.playerHurtEntity(DamagePredicate.Builder.damageInstance().type(DamageSourcePredicate.Builder.damageType().direct(EntityPredicate.Builder.entity().of(UPEntities.ULUG.get())))))
                .addCriterion("interact_ulugh", PlayerInteractTrigger.TriggerInstance.itemUsedOnEntity(ItemPredicate.Builder.item(), EntityPredicate.wrap(EntityPredicate.Builder.entity().of(UPEntities.ULUG.get()).build())))
                .addCriterion("killed_ulugh", KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(UPEntities.ULUG.get())))
                .requirements(RequirementsStrategy.OR)

                .save(consumer, prefixS("main/ulugh"));

        Advancement kentro = getAdvancement(eggs, (ItemLike)UPItems.KENTRO_FLASK.get(), "interact_kentro", FrameType.TASK, true, true, true)
                .addCriterion("damage_kentro", PlayerHurtEntityTrigger.TriggerInstance.playerHurtEntity(DamagePredicate.Builder.damageInstance().type(DamageSourcePredicate.Builder.damageType().direct(EntityPredicate.Builder.entity().of(UPEntities.KENTRO.get())))))
                .addCriterion("interact_kentro", PlayerInteractTrigger.TriggerInstance.itemUsedOnEntity(ItemPredicate.Builder.item(), EntityPredicate.wrap(EntityPredicate.Builder.entity().of(UPEntities.KENTRO.get()).build())))
                .addCriterion("killed_kentro", KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(UPEntities.KENTRO.get())))
                .requirements(RequirementsStrategy.OR)

                .save(consumer, prefixS("main/kentro"));

        Advancement stetha = getAdvancement(eggs, (ItemLike)UPItems.STETHA_FLASK.get(), "interact_stetha", FrameType.TASK, true, true, true)
                .addCriterion("damage_stetha", PlayerHurtEntityTrigger.TriggerInstance.playerHurtEntity(DamagePredicate.Builder.damageInstance().type(DamageSourcePredicate.Builder.damageType().direct(EntityPredicate.Builder.entity().of(UPEntities.STETHACANTHUS.get())))))
                .addCriterion("interact_stetha", PlayerInteractTrigger.TriggerInstance.itemUsedOnEntity(ItemPredicate.Builder.item(), EntityPredicate.wrap(EntityPredicate.Builder.entity().of(UPEntities.STETHACANTHUS.get()).build())))
                .addCriterion("killed_stetha", KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(UPEntities.STETHACANTHUS.get())))
                .requirements(RequirementsStrategy.OR)

                .save(consumer, prefixS("main/stetha"));

        Advancement eryon = getAdvancement(eggs, (ItemLike)UPItems.ERYON_FLASK.get(), "interact_eryon", FrameType.TASK, true, true, true)
                .addCriterion("damage_eryon", PlayerHurtEntityTrigger.TriggerInstance.playerHurtEntity(DamagePredicate.Builder.damageInstance().type(DamageSourcePredicate.Builder.damageType().direct(EntityPredicate.Builder.entity().of(UPEntities.ERYON.get())))))
                .addCriterion("interact_eryon", PlayerInteractTrigger.TriggerInstance.itemUsedOnEntity(ItemPredicate.Builder.item(), EntityPredicate.wrap(EntityPredicate.Builder.entity().of(UPEntities.ERYON.get()).build())))
                .addCriterion("killed_eryon", KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(UPEntities.ERYON.get())))
                .requirements(RequirementsStrategy.OR)

                .save(consumer, prefixS("main/eryon"));

        Advancement fossil_stand = getAdvancement(eryon, (ItemLike) UPBlocks.ERYON_FOSSIL.get(), "obtain_fossil_stand", FrameType.TASK, true, true, true)
                .addCriterion("coty_fossil", InventoryChangeTrigger.TriggerInstance.hasItems((ItemLike)UPBlocks.COTY_FOSSIL.get()))
                .addCriterion("stetha_fossil", InventoryChangeTrigger.TriggerInstance.hasItems((ItemLike)UPBlocks.STETHA_FOSSIL.get()))
                .addCriterion("anuro_fossil", InventoryChangeTrigger.TriggerInstance.hasItems((ItemLike)UPBlocks.ANURO_FOSSIL.get()))
                .addCriterion("scau_fossil", InventoryChangeTrigger.TriggerInstance.hasItems((ItemLike)UPBlocks.SCAU_FOSSIL.get()))
                .addCriterion("beelze_fossil", InventoryChangeTrigger.TriggerInstance.hasItems((ItemLike)UPBlocks.BEELZE_FOSSIL.get()))
                .addCriterion("brachi_fossil", InventoryChangeTrigger.TriggerInstance.hasItems((ItemLike)UPBlocks.BRACHI_FOSSIL.get()))
                .addCriterion("dunk_fossil", InventoryChangeTrigger.TriggerInstance.hasItems((ItemLike)UPBlocks.DUNK_FOSSIL.get()))
                .addCriterion("majunga_fossil", InventoryChangeTrigger.TriggerInstance.hasItems((ItemLike)UPBlocks.MAJUNGA_FOSSIL.get()))
                .addCriterion("pachy_fossil", InventoryChangeTrigger.TriggerInstance.hasItems((ItemLike)UPBlocks.PACHY_FOSSIL.get()))
                .addCriterion("veloci_fossil", InventoryChangeTrigger.TriggerInstance.hasItems((ItemLike)UPBlocks.VELOCI_FOSSIL.get()))
                .addCriterion("eryon_fossil", InventoryChangeTrigger.TriggerInstance.hasItems((ItemLike)UPBlocks.ERYON_FOSSIL.get()))
                .addCriterion("austro_fossil", InventoryChangeTrigger.TriggerInstance.hasItems((ItemLike)UPBlocks.AUSTRO_FOSSIL.get()))
                .addCriterion("ulugh_fossil", InventoryChangeTrigger.TriggerInstance.hasItems((ItemLike)UPBlocks.ULUGH_FOSSIL.get()))
                .addCriterion("kentro_fossil", InventoryChangeTrigger.TriggerInstance.hasItems((ItemLike)UPBlocks.KENTRO_FOSSIL.get()))
                .addCriterion("antarcto_fossil", InventoryChangeTrigger.TriggerInstance.hasItems((ItemLike)UPBlocks.ANTARCTO_FOSSIL.get()))
                .addCriterion("hwacha_fossil", InventoryChangeTrigger.TriggerInstance.hasItems((ItemLike)UPBlocks.HWACHA_FOSSIL.get()))
                .requirements(RequirementsStrategy.OR)
                .save(consumer, prefixS("main/fossil_stand"));

        Advancement hwacha = getAdvancement(eggs, (ItemLike)UPItems.HWACHA_FLASK.get(), "interact_hwacha", FrameType.TASK, true, true, true)
                .addCriterion("damage_hwacha", PlayerHurtEntityTrigger.TriggerInstance.playerHurtEntity(DamagePredicate.Builder.damageInstance().type(DamageSourcePredicate.Builder.damageType().direct(EntityPredicate.Builder.entity().of(UPEntities.HWACHA.get())))))
                .addCriterion("interact_hwacha", PlayerInteractTrigger.TriggerInstance.itemUsedOnEntity(ItemPredicate.Builder.item(), EntityPredicate.wrap(EntityPredicate.Builder.entity().of(UPEntities.HWACHA.get()).build())))
                .addCriterion("killed_hwacha", KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(UPEntities.HWACHA.get())))
                .requirements(RequirementsStrategy.OR)

                .save(consumer, prefixS("main/hwacha"));

        Advancement brachi = getAdvancement(eggs, (ItemLike)UPItems.BRACHI_FLASK.get(), "interact_brachi", FrameType.TASK, true, true, true)
                .addCriterion("damage_brachi", PlayerHurtEntityTrigger.TriggerInstance.playerHurtEntity(DamagePredicate.Builder.damageInstance().type(DamageSourcePredicate.Builder.damageType().direct(EntityPredicate.Builder.entity().of(UPEntities.BRACHI.get())))))
                .addCriterion("interact_brachi", PlayerInteractTrigger.TriggerInstance.itemUsedOnEntity(ItemPredicate.Builder.item(), EntityPredicate.wrap(EntityPredicate.Builder.entity().of(UPEntities.BRACHI.get()).build())))
                .addCriterion("killed_brachi", KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(UPEntities.BRACHI.get())))
                .requirements(RequirementsStrategy.OR)

                .save(consumer, prefixS("main/brachi"));

        Advancement scau = getAdvancement(eggs, (ItemLike)UPItems.SCAU_FLASK.get(), "interact_scau", FrameType.TASK, true, true, true)
                .addCriterion("damage_scau", PlayerHurtEntityTrigger.TriggerInstance.playerHurtEntity(DamagePredicate.Builder.damageInstance().type(DamageSourcePredicate.Builder.damageType().direct(EntityPredicate.Builder.entity().of(UPEntities.SCAU.get())))))
                .addCriterion("interact_scau", PlayerInteractTrigger.TriggerInstance.itemUsedOnEntity(ItemPredicate.Builder.item(), EntityPredicate.wrap(EntityPredicate.Builder.entity().of(UPEntities.SCAU.get()).build())))
                .addCriterion("killed_scau", KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(UPEntities.SCAU.get())))
                .requirements(RequirementsStrategy.OR)

                .save(consumer, prefixS("main/scau"));

        Advancement golden_scau = getAdvancement(scau, (ItemLike)UPItems.GOLDEN_SCAU.get(), "obtain_golden_scau", FrameType.CHALLENGE, true, true, true)
                .addCriterion("golden_scau", InventoryChangeTrigger.TriggerInstance.hasItems((ItemLike)UPItems.GOLDEN_SCAU.get()))
                .save(consumer, prefixS("main/golden_scau"));


        Advancement majunga = getAdvancement(eggs, (ItemLike)UPItems.MAJUNGA_FLASK.get(), "interact_majunga", FrameType.TASK, true, true, true)
                .addCriterion("damage_majunga", PlayerHurtEntityTrigger.TriggerInstance.playerHurtEntity(DamagePredicate.Builder.damageInstance().type(DamageSourcePredicate.Builder.damageType().direct(EntityPredicate.Builder.entity().of(UPEntities.MAJUNGA.get())))))
                .addCriterion("interact_majunga", PlayerInteractTrigger.TriggerInstance.itemUsedOnEntity(ItemPredicate.Builder.item(), EntityPredicate.wrap(EntityPredicate.Builder.entity().of(UPEntities.MAJUNGA.get()).build())))
                .addCriterion("killed_scau", KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(UPEntities.MAJUNGA.get())))
                .requirements(RequirementsStrategy.OR)

                .save(consumer, prefixS("main/majunga"));

        Advancement majunga_helmet = getAdvancement(majunga, (ItemLike)UPItems.MAJUNGA_HELMET.get(), "obtain_majunga_helmet", FrameType.TASK, true, true, true)
                .addCriterion("majunga_helmet", InventoryChangeTrigger.TriggerInstance.hasItems((ItemLike)UPItems.MAJUNGA_HELMET.get()))
                .save(consumer, prefixS("main/majunga_helmet"));

        Advancement veloci = getAdvancement(eggs, (ItemLike)UPItems.RAPTOR_FLASK.get(), "interact_veloci", FrameType.TASK, true, true, true)
                .addCriterion("damage_veloci", PlayerHurtEntityTrigger.TriggerInstance.playerHurtEntity(DamagePredicate.Builder.damageInstance().type(DamageSourcePredicate.Builder.damageType().direct(EntityPredicate.Builder.entity().of(UPEntities.VELOCIRAPTOR.get())))))
                .addCriterion("interact_veloci", PlayerInteractTrigger.TriggerInstance.itemUsedOnEntity(ItemPredicate.Builder.item(), EntityPredicate.wrap(EntityPredicate.Builder.entity().of(UPEntities.VELOCIRAPTOR.get()).build())))
                .addCriterion("killed_veloci", KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(UPEntities.VELOCIRAPTOR.get())))
                .requirements(RequirementsStrategy.OR)

                .save(consumer, prefixS("main/veloci"));

        Advancement veloci_shield = getAdvancement(veloci, (ItemLike)UPItems.VELOCI_SHIELD.get(), "obtain_veloci_shield", FrameType.CHALLENGE, true, true, true)
                .addCriterion("veloci_shield", InventoryChangeTrigger.TriggerInstance.hasItems((ItemLike)UPItems.VELOCI_SHIELD.get()))
                .save(consumer, prefixS("main/veloci_shield"));

        Advancement dunk = getAdvancement(eggs, (ItemLike)UPItems.DUNK_FLASK.get(), "interact_dunk", FrameType.TASK, true, true, true)
                .addCriterion("damage_dunk", PlayerHurtEntityTrigger.TriggerInstance.playerHurtEntity(DamagePredicate.Builder.damageInstance().type(DamageSourcePredicate.Builder.damageType().direct(EntityPredicate.Builder.entity().of(UPEntities.DUNK.get())))))
                .addCriterion("interact_dunk", PlayerInteractTrigger.TriggerInstance.itemUsedOnEntity(ItemPredicate.Builder.item(), EntityPredicate.wrap(EntityPredicate.Builder.entity().of(UPEntities.DUNK.get()).build())))
                .addCriterion("killed_dunk", KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(UPEntities.DUNK.get())))
                .requirements(RequirementsStrategy.OR)

                .save(consumer, prefixS("main/dunk"));

        Advancement anuro = getAdvancement(eggs, (ItemLike)UPItems.ANURO_FLASK.get(), "interact_anuro", FrameType.TASK, true, true, true)
                .addCriterion("damage_anuro", PlayerHurtEntityTrigger.TriggerInstance.playerHurtEntity(DamagePredicate.Builder.damageInstance().type(DamageSourcePredicate.Builder.damageType().direct(EntityPredicate.Builder.entity().of(UPEntities.ANURO.get())))))
                .addCriterion("interact_anuro", PlayerInteractTrigger.TriggerInstance.itemUsedOnEntity(ItemPredicate.Builder.item(), EntityPredicate.wrap(EntityPredicate.Builder.entity().of(UPEntities.ANURO.get()).build())))
                .addCriterion("killed_anuro", KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(UPEntities.ANURO.get())))
                .requirements(RequirementsStrategy.OR)

                .save(consumer, prefixS("main/anuro"));

        Advancement trike = getAdvancement(eggs, (ItemLike)UPItems.TRIKE_FLASK.get(), "interact_trike", FrameType.TASK, true, true, true)
                .addCriterion("damage_trike", PlayerHurtEntityTrigger.TriggerInstance.playerHurtEntity(DamagePredicate.Builder.damageInstance().type(DamageSourcePredicate.Builder.damageType().direct(EntityPredicate.Builder.entity().of(UPEntities.TRICERATOPS.get())))))
                .addCriterion("interact_trike", PlayerInteractTrigger.TriggerInstance.itemUsedOnEntity(ItemPredicate.Builder.item(), EntityPredicate.wrap(EntityPredicate.Builder.entity().of(UPEntities.TRICERATOPS.get()).build())))
                .addCriterion("killed_trike", KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(UPEntities.TRICERATOPS.get())))
                .requirements(RequirementsStrategy.OR)

                .save(consumer, prefixS("main/trike"));

        Advancement trike_shield = getAdvancement(trike, (ItemLike)UPItems.TRIKE_SHIELD.get(), "obtain_trike_shield", FrameType.CHALLENGE, true, true, true)
                .addCriterion("trike_shield", InventoryChangeTrigger.TriggerInstance.hasItems((ItemLike)UPItems.TRIKE_SHIELD.get()))
                .save(consumer, prefixS("main/trike_shield"));

        Advancement coty = getAdvancement(eggs, (ItemLike)UPItems.COTY_FLASK.get(), "interact_coty", FrameType.TASK, true, true, true)
                .addCriterion("damage_coty", PlayerHurtEntityTrigger.TriggerInstance.playerHurtEntity(DamagePredicate.Builder.damageInstance().type(DamageSourcePredicate.Builder.damageType().direct(EntityPredicate.Builder.entity().of(UPEntities.COTY.get())))))
                .addCriterion("interact_coty", PlayerInteractTrigger.TriggerInstance.itemUsedOnEntity(ItemPredicate.Builder.item(), EntityPredicate.wrap(EntityPredicate.Builder.entity().of(UPEntities.COTY.get()).build())))
                .addCriterion("killed_coty", KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(UPEntities.COTY.get())))
                .requirements(RequirementsStrategy.OR)

                .save(consumer, prefixS("main/coty"));

        Advancement grog = getAdvancement(coty, (ItemLike)UPItems.GROG.get(), "obtain_grog", FrameType.CHALLENGE, true, true, true)
                .addCriterion("grog", InventoryChangeTrigger.TriggerInstance.hasItems((ItemLike)UPItems.GROG.get()))
                .save(consumer, prefixS("main/grog"));

        Advancement rex = getAdvancement(eggs, (ItemLike)UPItems.REX_FLASK.get(), "interact_rex", FrameType.TASK, true, true, true)
                .addCriterion("damage_rex", PlayerHurtEntityTrigger.TriggerInstance.playerHurtEntity(DamagePredicate.Builder.damageInstance().type(DamageSourcePredicate.Builder.damageType().direct(EntityPredicate.Builder.entity().of(UPEntities.TYRANNOSAURUS.get())))))
                .addCriterion("interact_rex", PlayerInteractTrigger.TriggerInstance.itemUsedOnEntity(ItemPredicate.Builder.item(), EntityPredicate.wrap(EntityPredicate.Builder.entity().of(UPEntities.TYRANNOSAURUS.get()).build())))
                .addCriterion("killed_rex", KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(UPEntities.TYRANNOSAURUS.get())))
                .requirements(RequirementsStrategy.OR)

                .save(consumer, prefixS("main/rex"));

        Advancement rex_defeat = getAdvancement(rex, (ItemLike)UPItems.REX_TOOTH.get(), "rex_passify", FrameType.CHALLENGE, true, true, true)
                .addCriterion("interact_rex", PlayerInteractTrigger.TriggerInstance.itemUsedOnEntity(ItemPredicate.Builder.item(), EntityPredicate.wrap(EntityPredicate.Builder.entity().of(UPEntities.TYRANNOSAURUS.get()).build())))
                .save(consumer, prefixS("main/rex_passify"));

        Advancement smilodon = getAdvancement(cultivator, (ItemLike)UPItems.SMILO_FLASK.get(), "interact_smilo", FrameType.TASK, true, true, false)
                .addCriterion("damage_smilodon", PlayerHurtEntityTrigger.TriggerInstance.playerHurtEntity(DamagePredicate.Builder.damageInstance().type(DamageSourcePredicate.Builder.damageType().direct(EntityPredicate.Builder.entity().of(UPEntities.SMILODON.get())))))
                .addCriterion("interact_smilodon", PlayerInteractTrigger.TriggerInstance.itemUsedOnEntity(ItemPredicate.Builder.item(), EntityPredicate.wrap(EntityPredicate.Builder.entity().of(UPEntities.SMILODON.get()).build())))
                .addCriterion("killed_smilodon", KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(UPEntities.SMILODON.get())))
                .requirements(RequirementsStrategy.OR)

                .save(consumer, prefixS("main/smilodon"));

        Advancement insulator = getAdvancement(smilodon, (ItemLike)UPItems.INSULATOR.get(), "obtain_insulator", FrameType.TASK, true, true, false)
                .addCriterion("insulator", InventoryChangeTrigger.TriggerInstance.hasItems((ItemLike)UPItems.INSULATOR.get()))
                .save(consumer, prefixS("main/insulator"));

        Advancement birthingPod = getAdvancement(insulator, (ItemLike)UPBlocks.INCUBATOR.get(), "craft_incubator", FrameType.TASK, true, true, false)
                .addCriterion("incubator", InventoryChangeTrigger.TriggerInstance.hasItems((ItemLike)UPBlocks.INCUBATOR.get()))
                .save(consumer, prefixS("main/incubator"));

        Advancement palaeo = getAdvancement(birthingPod, (ItemLike)UPItems.PALAEO_FLASK.get(), "interact_palaeo", FrameType.TASK, true, true, true)
                .addCriterion("damage_palaeo", PlayerHurtEntityTrigger.TriggerInstance.playerHurtEntity(DamagePredicate.Builder.damageInstance().type(DamageSourcePredicate.Builder.damageType().direct(EntityPredicate.Builder.entity().of(UPEntities.PALAEOPHIS.get())))))
                .addCriterion("interact_palaeo", PlayerInteractTrigger.TriggerInstance.itemUsedOnEntity(ItemPredicate.Builder.item(), EntityPredicate.wrap(EntityPredicate.Builder.entity().of(UPEntities.PALAEOPHIS.get()).build())))
                .addCriterion("killed_palaeo", KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(UPEntities.PALAEOPHIS.get())))
                .requirements(RequirementsStrategy.OR)

                .save(consumer, prefixS("main/palaeophis"));

        Advancement shedscale = getAdvancement(palaeo, (ItemLike)UPItems.PALAEO_SKIN.get(), "obtain_shedscale", FrameType.CHALLENGE, true, true, true)
                .addCriterion("shedscale_helmet", InventoryChangeTrigger.TriggerInstance.hasItems((ItemLike)UPItems.SHEDSCALE_HELMET.get()))
                .addCriterion("shedscale_chestplate", InventoryChangeTrigger.TriggerInstance.hasItems((ItemLike)UPItems.SHEDSCALE_CHESTPLATE.get()))
                .addCriterion("shedscale_leggings", InventoryChangeTrigger.TriggerInstance.hasItems((ItemLike)UPItems.SHEDSCALE_LEGGINGS.get()))
                .addCriterion("shedscale_boots", InventoryChangeTrigger.TriggerInstance.hasItems((ItemLike)UPItems.SHEDSCALE_BOOTS.get()))
                .save(consumer, prefixS("main/shedscale"));

        Advancement giganto = getAdvancement(birthingPod, (ItemLike)UPItems.GIGANTO_FLASK.get(), "interact_giganto", FrameType.TASK, true, true, true)
                .addCriterion("damage_gigantopithicus", PlayerHurtEntityTrigger.TriggerInstance.playerHurtEntity(DamagePredicate.Builder.damageInstance().type(DamageSourcePredicate.Builder.damageType().direct(EntityPredicate.Builder.entity().of(UPEntities.GIGANTOPITHICUS.get())))))
                .addCriterion("interact_gigantopithicus", PlayerInteractTrigger.TriggerInstance.itemUsedOnEntity(ItemPredicate.Builder.item(), EntityPredicate.wrap(EntityPredicate.Builder.entity().of(UPEntities.GIGANTOPITHICUS.get()).build())))
                .addCriterion("killed_gigantopithicus", KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(UPEntities.GIGANTOPITHICUS.get())))
                .requirements(RequirementsStrategy.OR)

                .save(consumer, prefixS("main/gigantopithicus"));

        Advancement giganto_weapon = getAdvancement(giganto, (ItemLike)UPItems.HANDMADE_SPEAR.get(), "obtain_monkey_weapon", FrameType.CHALLENGE, true, true, true)
                .addCriterion("handmade_spear", InventoryChangeTrigger.TriggerInstance.hasItems((ItemLike)UPItems.HANDMADE_SPEAR.get()))
                .addCriterion("handmade_battleaxe", InventoryChangeTrigger.TriggerInstance.hasItems((ItemLike)UPItems.HANDMADE_BATTLEAXE.get()))
                .addCriterion("handmade_club", InventoryChangeTrigger.TriggerInstance.hasItems((ItemLike)UPItems.HANDMADE_CLUB.get()))
                .save(consumer, prefixS("main/gigantopithicus_weapons"));

        Advancement giganto_fruits = getAdvancement(giganto, (ItemLike)UPItems.RED_FRUIT.get(), "obtain_monkey_fruits", FrameType.CHALLENGE, true, true, true)
                .addCriterion("red_fruit", InventoryChangeTrigger.TriggerInstance.hasItems((ItemLike)UPItems.RED_FRUIT.get()))
                .addCriterion("white_fruit", InventoryChangeTrigger.TriggerInstance.hasItems((ItemLike)UPItems.WHITE_FRUIT.get()))
                .addCriterion("yellow_fruit", InventoryChangeTrigger.TriggerInstance.hasItems((ItemLike)UPItems.YELLOW_FRUIT.get()))
                .addCriterion("blue_fruit", InventoryChangeTrigger.TriggerInstance.hasItems((ItemLike)UPItems.BLUE_FRUIT.get()))

                .save(consumer, prefixS("main/gigantopithicus_fruits"));

        Advancement talapanas = getAdvancement(eggs, (ItemLike)UPItems.TALPANAS_FLASK.get(), "interact_talpanas", FrameType.TASK, true, true, true)
                .addCriterion("damage_talapanas", PlayerHurtEntityTrigger.TriggerInstance.playerHurtEntity(DamagePredicate.Builder.damageInstance().type(DamageSourcePredicate.Builder.damageType().direct(EntityPredicate.Builder.entity().of(UPEntities.TALPANAS.get())))))
                .addCriterion("interact_talapanas", PlayerInteractTrigger.TriggerInstance.itemUsedOnEntity(ItemPredicate.Builder.item(), EntityPredicate.wrap(EntityPredicate.Builder.entity().of(UPEntities.TALPANAS.get()).build())))
                .addCriterion("killed_talapanas", KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(UPEntities.TALPANAS.get())))
                .requirements(RequirementsStrategy.OR)

                .save(consumer, prefixS("main/talapanas"));

        Advancement mammoth = getAdvancement(birthingPod, (ItemLike)UPItems.MAMMOTH_FLASK.get(), "interact_mammoth", FrameType.TASK, true, true, true)
                .addCriterion("damage_mammoth", PlayerHurtEntityTrigger.TriggerInstance.playerHurtEntity(DamagePredicate.Builder.damageInstance().type(DamageSourcePredicate.Builder.damageType().direct(EntityPredicate.Builder.entity().of(UPEntities.MAMMOTH.get())))))
                .addCriterion("interact_mammoth", PlayerInteractTrigger.TriggerInstance.itemUsedOnEntity(ItemPredicate.Builder.item(), EntityPredicate.wrap(EntityPredicate.Builder.entity().of(UPEntities.MAMMOTH.get()).build())))
                .addCriterion("killed_mammoth", KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(UPEntities.MAMMOTH.get())))
                .requirements(RequirementsStrategy.OR)

                .save(consumer, prefixS("main/mammoth"));

        Advancement barina = getAdvancement(eggs, (ItemLike)UPItems.BARIN_FLASK.get(), "interact_barina", FrameType.TASK, true, true, true)
                .addCriterion("damage_barina", PlayerHurtEntityTrigger.TriggerInstance.playerHurtEntity(DamagePredicate.Builder.damageInstance().type(DamageSourcePredicate.Builder.damageType().direct(EntityPredicate.Builder.entity().of(UPEntities.BARINASUCHUS.get())))))
                .addCriterion("interact_barina", PlayerInteractTrigger.TriggerInstance.itemUsedOnEntity(ItemPredicate.Builder.item(), EntityPredicate.wrap(EntityPredicate.Builder.entity().of(UPEntities.BARINASUCHUS.get()).build())))
                .addCriterion("killed_barina", KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(UPEntities.BARINASUCHUS.get())))
                .requirements(RequirementsStrategy.OR)

                .save(consumer, prefixS("main/barina"));

        Advancement tameBarina = getAdvancement(barina, (ItemLike)UPItems.BARINA_WHISTLE.get(), "tame_barina", FrameType.TASK, true, true, true)
                .addCriterion("tame_barina", BarinaTameTrigger.TriggerInstance.placedBlock())
                .save(consumer, prefixS("main/tame_barina"));

        Advancement paracer = getAdvancement(birthingPod, (ItemLike)UPItems.PARACER_FLASK.get(), "interact_paracer", FrameType.TASK, true, true, true)
                .addCriterion("damage_paracer", PlayerHurtEntityTrigger.TriggerInstance.playerHurtEntity(DamagePredicate.Builder.damageInstance().type(DamageSourcePredicate.Builder.damageType().direct(EntityPredicate.Builder.entity().of(UPEntities.PARACERATHERIUM.get())))))
                .addCriterion("interact_paracer", PlayerInteractTrigger.TriggerInstance.itemUsedOnEntity(ItemPredicate.Builder.item(), EntityPredicate.wrap(EntityPredicate.Builder.entity().of(UPEntities.PARACERATHERIUM.get()).build())))
                .addCriterion("killed_paracer", KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(UPEntities.PARACERATHERIUM.get())))
                .requirements(RequirementsStrategy.OR)

                .save(consumer, prefixS("main/paraceratherium"));

        Advancement megalania = getAdvancement(eggs, (ItemLike)UPItems.MEGALA_FLASK.get(), "interact_megala", FrameType.TASK, true, true, true)
                .addCriterion("damage_megala", PlayerHurtEntityTrigger.TriggerInstance.playerHurtEntity(DamagePredicate.Builder.damageInstance().type(DamageSourcePredicate.Builder.damageType().direct(EntityPredicate.Builder.entity().of(UPEntities.MEGALANIA.get())))))
                .addCriterion("interact_megala", PlayerInteractTrigger.TriggerInstance.itemUsedOnEntity(ItemPredicate.Builder.item(), EntityPredicate.wrap(EntityPredicate.Builder.entity().of(UPEntities.MEGALANIA.get()).build())))
                .addCriterion("killed_megala", KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(UPEntities.MEGALANIA.get())))
                .requirements(RequirementsStrategy.OR)

                .save(consumer, prefixS("main/megalania"));

//        Advancement otarocyon = getAdvancement(birthingPod, (ItemLike)UPItems.OTAROCYON_FLASK.get(), "interact_otarocyon", FrameType.TASK, true, true, true)
//                .addCriterion("damage_otarocyon", PlayerHurtEntityTrigger.TriggerInstance.playerHurtEntity(DamagePredicate.Builder.damageInstance().type(DamageSourcePredicate.Builder.damageType().direct(EntityPredicate.Builder.entity().of(UPEntities.TALPANAS.get())))))
//                .addCriterion("interact_otarocyon", PlayerInteractTrigger.TriggerInstance.itemUsedOnEntity(ItemPredicate.Builder.item(), EntityPredicate.wrap(EntityPredicate.Builder.entity().of(UPEntities.TALPANAS.get()).build())))
//                .addCriterion("killed_otarocyon", KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(UPEntities.TALPANAS.get())))
//                .requirements(RequirementsStrategy.OR)
//                .save(consumer, prefixS("main/otarocyon"));

        Advancement petrified = getAdvancement(fossil, (ItemLike) UPBlocks.PETRIFIED_WOOD_LOG.get(), "petrified_wood", FrameType.TASK, true, true, true)
                .addCriterion("petrified_wood", InventoryChangeTrigger.TriggerInstance.hasItems((ItemLike)UPBlocks.PETRIFIED_WOOD_LOG.get()))
                .requirements(RequirementsStrategy.OR)
                .save(consumer, prefixS("main/petrified"));

        Advancement plants = getAdvancement(petrified, (ItemLike)UPItems.HORSETAIL_FLASK.get(), "prehistoric_plants", FrameType.TASK, true, true, true)
                .addCriterion("horsetail_flask", InventoryChangeTrigger.TriggerInstance.hasItems((ItemLike)UPItems.HORSETAIL_FLASK.get()))
                .addCriterion("leefructus_flask", InventoryChangeTrigger.TriggerInstance.hasItems((ItemLike)UPItems.LEEFRUCTUS_FLASK.get()))
                .addCriterion("archao_flask", InventoryChangeTrigger.TriggerInstance.hasItems((ItemLike)UPItems.ARCHAO_FLASK.get()))
                .addCriterion("bennet_flask", InventoryChangeTrigger.TriggerInstance.hasItems((ItemLike)UPItems.BENNET_FLASK.get()))
                .addCriterion("ginkgo_flask", InventoryChangeTrigger.TriggerInstance.hasItems((ItemLike)UPItems.GINKGO_FLASK.get()))
                .addCriterion("sarr_flask", InventoryChangeTrigger.TriggerInstance.hasItems((ItemLike)UPItems.SARR_FLASK.get()))
                .addCriterion("anostylostroma_flask", InventoryChangeTrigger.TriggerInstance.hasItems((ItemLike)UPItems.ANOSTYLOSTRAMA_FLASK.get()))
                .addCriterion("archaefructus_flask", InventoryChangeTrigger.TriggerInstance.hasItems((ItemLike)UPItems.ARCHAEFRUCTUS_FLASK.get()))
                .addCriterion("clathrodictyon_flask", InventoryChangeTrigger.TriggerInstance.hasItems((ItemLike)UPItems.CLATHRODICTYON_FLASK.get()))
                .addCriterion("nelumbites_flask", InventoryChangeTrigger.TriggerInstance.hasItems((ItemLike)UPItems.NELUMBITES_FLASK.get()))
                .addCriterion("quereuxia_flask", InventoryChangeTrigger.TriggerInstance.hasItems((ItemLike)UPItems.QUEREUXIA_FLASK.get()))
                .addCriterion("zuloagae_flask", InventoryChangeTrigger.TriggerInstance.hasItems((ItemLike)UPItems.ZULOAGAE_FLASK.get()))
                .addCriterion("raiguenrayun_flask", InventoryChangeTrigger.TriggerInstance.hasItems((ItemLike)UPItems.RAIGUENRAYUN_FLASK.get()))
                .addCriterion("foxxi_flask", InventoryChangeTrigger.TriggerInstance.hasItems((ItemLike)UPItems.FOXXI_FLASK.get()))
                .addCriterion("dryo_flask", InventoryChangeTrigger.TriggerInstance.hasItems((ItemLike)UPItems.DRYO_FLASK.get()))
                .requirements(RequirementsStrategy.OR)
                .save(consumer, prefixS("main/plants"));

        Advancement ginkgo = getAdvancement(plants, (ItemLike)UPBlocks.GINKGO_SAPLING.get(), "ginkgo", FrameType.TASK, true, true, true)
                .addCriterion("ginkgo_sapling", InventoryChangeTrigger.TriggerInstance.hasItems((ItemLike)UPBlocks.GINKGO_SAPLING.get()))
                .requirements(RequirementsStrategy.OR)
                .save(consumer, prefixS("main/ginkgo"));

        Advancement foxii = getAdvancement(plants, (ItemLike)UPBlocks.FOXII_SAPLING.get(), "foxii", FrameType.TASK, true, true, true)
                .addCriterion("foxii_sapling", InventoryChangeTrigger.TriggerInstance.hasItems((ItemLike)UPBlocks.FOXII_SAPLING.get()))
                .requirements(RequirementsStrategy.OR)
                .save(consumer, prefixS("main/foxii"));

        Advancement dryo = getAdvancement(plants, (ItemLike)UPBlocks.DRYO_SAPLING.get(), "dryo", FrameType.TASK, true, true, true)
                .addCriterion("dryo_sapling", InventoryChangeTrigger.TriggerInstance.hasItems((ItemLike)UPBlocks.DRYO_SAPLING.get()))
                .requirements(RequirementsStrategy.OR)
                .save(consumer, prefixS("main/dryo"));

        Advancement zuloagae = getAdvancement(plants, (ItemLike)UPBlocks.ZULOAGAE.get(), "zuloagae", FrameType.TASK, true, true, true)
                .addCriterion("zuloagae", InventoryChangeTrigger.TriggerInstance.hasItems((ItemLike)UPBlocks.ZULOAGAE.get()))
                .requirements(RequirementsStrategy.OR)
                .save(consumer, prefixS("main/zuloagae"));

        Advancement opal = getAdvancement(fossil, (ItemLike)UPItems.OPAL_FOSSIL.get(), "opal_fossil", FrameType.TASK, true, true, true)
                .addCriterion("opal_fossil", InventoryChangeTrigger.TriggerInstance.hasItems((ItemLike)UPItems.OPAL_FOSSIL.get()))
                .requirements(RequirementsStrategy.OR)
                .save(consumer, prefixS("main/opal_fossil"));

        Advancement shuriken = getAdvancement(opal, (ItemLike)UPItems.OPALESCENT_SHURIKEN.get(), "opal_shuriken", FrameType.TASK, true, true, true)
                .addCriterion("opal_shuriken", InventoryChangeTrigger.TriggerInstance.hasItems((ItemLike)UPItems.OPALESCENT_SHURIKEN.get()))
                .requirements(RequirementsStrategy.OR)
                .save(consumer, prefixS("main/opal_shuriken"));

        Advancement pearl = getAdvancement(opal, (ItemLike)UPItems.OPALESCENT_PEARL.get(), "opal_pearl", FrameType.TASK, true, true, true)
                .addCriterion("opal_pearl", InventoryChangeTrigger.TriggerInstance.hasItems((ItemLike)UPItems.OPALESCENT_PEARL.get()))
                .requirements(RequirementsStrategy.OR)
                .save(consumer, prefixS("main/opal_pearl"));

        Advancement meatball = getAdvancement(mammoth, (ItemLike)UPItems.MAMMOTH_MEATBALL.get(), "mammoth_meatball", FrameType.CHALLENGE, true, true, true)
                .addCriterion("mammoth_meatball", InventoryChangeTrigger.TriggerInstance.hasItems((ItemLike)UPItems.MAMMOTH_MEATBALL.get()))
                .requirements(RequirementsStrategy.OR)
                .save(consumer, prefixS("main/mammoth_meatball"));

        Advancement amber_gummy = getAdvancement(amberFossil, (ItemLike)UPItems.AMBER_GUMMY.get(), "amber_gummy", FrameType.TASK, true, true, true)
                .addCriterion("amber_gummy", InventoryChangeTrigger.TriggerInstance.hasItems((ItemLike)UPItems.AMBER_GUMMY.get()))
                .requirements(RequirementsStrategy.OR)
                .save(consumer, prefixS("main/amber_gummy"));

        Advancement encrusted = getAdvancement(amberFossil, UPItems.ENCRUSTED_FLASK.get(), "interact_encrusted", FrameType.TASK, true, true, true)
                .addCriterion("damage_encrusted", PlayerHurtEntityTrigger.TriggerInstance.playerHurtEntity(DamagePredicate.Builder.damageInstance().type(DamageSourcePredicate.Builder.damageType().direct(EntityPredicate.Builder.entity().of(UPEntities.ENCRUSTED.get())))))
                .addCriterion("interact_encrusted", PlayerInteractTrigger.TriggerInstance.itemUsedOnEntity(ItemPredicate.Builder.item(), EntityPredicate.wrap(EntityPredicate.Builder.entity().of(UPEntities.ENCRUSTED.get()).build())))
                .addCriterion("killed_encrusted", KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(UPEntities.ENCRUSTED.get())))
                .requirements(RequirementsStrategy.OR)
                .save(consumer, prefixS("main/encrusted"));

//        Advancement sludge = getAdvancement(fossil, UPItems.TAR_FOSSIL.get(), "interact_sludge", FrameType.TASK, true, true, true)
//                .addCriterion("damage_sludge", PlayerHurtEntityTrigger.TriggerInstance.playerHurtEntity(DamagePredicate.Builder.damageInstance().type(DamageSourcePredicate.Builder.damageType().direct(EntityPredicate.Builder.entity().of(UPEntities.SLUDGE.get())))))
//                .addCriterion("interact_sludge", PlayerInteractTrigger.TriggerInstance.itemUsedOnEntity(ItemPredicate.Builder.item(), EntityPredicate.wrap(EntityPredicate.Builder.entity().of(UPEntities.SLUDGE.get()).build())))
//                .addCriterion("killed_sludge", KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(UPEntities.SLUDGE.get())))
//                .requirements(RequirementsStrategy.OR)
//                .save(consumer, prefixS("main/sludge"));


    }
    protected static Advancement.Builder getAdvancement(Advancement parent, ItemLike display, String name, FrameType frame, boolean showToast, boolean announceToChat, boolean hidden) {
        return Advancement.Builder.advancement().parent(parent).display(display, getTranslation("advancement." + name, new Object[0]), getTranslation("advancement." + name + ".desc", new Object[0]), (ResourceLocation)null, frame, showToast, announceToChat, hidden);
    }
}
