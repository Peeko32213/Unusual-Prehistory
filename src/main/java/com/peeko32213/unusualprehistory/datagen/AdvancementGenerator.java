package com.peeko32213.unusualprehistory.datagen;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.core.registry.UPBlocks;
import com.peeko32213.unusualprehistory.core.registry.UPEntities;
import com.peeko32213.unusualprehistory.core.registry.UPItems;
import com.peeko32213.unusualprehistory.core.registry.util.BarinaTameTrigger;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.*;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.advancements.AdvancementProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.function.Consumer;

import static com.peeko32213.unusualprehistory.UnusualPrehistory.getTranslation;
import static com.peeko32213.unusualprehistory.UnusualPrehistory.prefix;

public class AdvancementGenerator extends AdvancementProvider {
    public AdvancementGenerator(DataGenerator generatorIn, ExistingFileHelper fileHelperIn) {
        super(generatorIn, fileHelperIn);
    }

    @Override
    protected void registerAdvancements(Consumer<Advancement> consumer, ExistingFileHelper fileHelper) {
        Advancement unusualprehistory = Advancement.Builder.advancement().display(UPItems.ENCYLOPEDIA.get(), getTranslation("advancement.root", new Object[0]), getTranslation("advancement.root.desc", new Object[0]), prefix("textures/block/ginkgo_log_side.png"), FrameType.TASK, false, true, false).addCriterion("acquired_encyclopedia", InventoryChangeTrigger.TriggerInstance.hasItems((ItemLike)UPItems.ENCYLOPEDIA.get())).save(consumer, this.getNameId("main/root"));
        Advancement fossil = getAdvancement(unusualprehistory, (ItemLike)UPItems.MEZO_FOSSIL.get(), "acquire_fossil", FrameType.TASK, true, true, false)
                .addCriterion("mezo_fossil", InventoryChangeTrigger.TriggerInstance.hasItems((ItemLike)UPItems.MEZO_FOSSIL.get()))
                .addCriterion("paleo_fossil", InventoryChangeTrigger.TriggerInstance.hasItems((ItemLike)UPItems.PALEO_FOSSIL.get()))
                .addCriterion("plant_fossil", InventoryChangeTrigger.TriggerInstance.hasItems((ItemLike)UPItems.PLANT_FOSSIL.get()))
                .addCriterion("amber_fossil", InventoryChangeTrigger.TriggerInstance.hasItems((ItemLike)UPItems.AMBER_FOSSIL.get()))
                .addCriterion("frozen_fossil", InventoryChangeTrigger.TriggerInstance.hasItems((ItemLike)UPItems.FROZEN_FOSSIL.get()))
                .addCriterion("tar_fossil", InventoryChangeTrigger.TriggerInstance.hasItems((ItemLike)UPItems.TAR_FOSSIL.get()))
                .requirements(RequirementsStrategy.OR).save(consumer, this.getNameId("main/fossil"));

        Advancement birthingPod = getAdvancement(fossil, (ItemLike)UPBlocks.INCUBATOR.get(), "craft_incubator", FrameType.TASK, true, true, false)
                .addCriterion("incubator", InventoryChangeTrigger.TriggerInstance.hasItems((ItemLike)UPBlocks.INCUBATOR.get()))
                .save(consumer, this.getNameId("main/incubator"));

        Advancement amberFossil = getAdvancement(fossil, (ItemLike)UPItems.AMBER_FOSSIL.get(), "acquire_amber_fossil", FrameType.CHALLENGE, true, true, false)
                .addCriterion("amber_fossil", InventoryChangeTrigger.TriggerInstance.hasItems((ItemLike)UPItems.AMBER_FOSSIL.get()))
                .save(consumer, this.getNameId("main/amber_fossil"));

        Advancement amberStaff = getAdvancement(amberFossil, (ItemLike)UPItems.ADORNED_STAFF.get(), "acquire_adorned_staff", FrameType.TASK, true, true, false)
                .addCriterion("adorned_staff", InventoryChangeTrigger.TriggerInstance.hasItems((ItemLike)UPItems.ADORNED_STAFF.get()))
                .save(consumer, this.getNameId("main/adorned_staff"));

        Advancement analyzer = getAdvancement(fossil, (ItemLike) UPBlocks.AMBER_BLOCK.get(), "craft_analyzer", FrameType.TASK, true, true, false)
                .addCriterion("analyzer", InventoryChangeTrigger.TriggerInstance.hasItems((ItemLike)UPBlocks.ANALYZER.get()))
                .save(consumer, this.getNameId("main/analyzer"));

        Advancement cultivator = getAdvancement(analyzer, (ItemLike) UPBlocks.CULTIVATOR.get(), "craft_cultivator", FrameType.TASK, true, true, false)
                .addCriterion("cultivator", InventoryChangeTrigger.TriggerInstance.hasItems((ItemLike)UPBlocks.CULTIVATOR.get()))
                .save(consumer, this.getNameId("main/cultivator"));

        Advancement eggs = getAdvancement(analyzer, (ItemLike) UPBlocks.COTY_EGG.get(), "obtain_egg", FrameType.TASK, true, true, false)
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
                .addCriterion("talapanas_egg", InventoryChangeTrigger.TriggerInstance.hasItems((ItemLike)UPBlocks.TALAPANAS_EGG.get()))
                .addCriterion("trike_egg", InventoryChangeTrigger.TriggerInstance.hasItems((ItemLike)UPBlocks.TRIKE_EGG.get()))
                .addCriterion("ulugh_egg", InventoryChangeTrigger.TriggerInstance.hasItems((ItemLike)UPBlocks.ULUGH_EGG.get()))
                .addCriterion("eryon_egg", InventoryChangeTrigger.TriggerInstance.hasItems((ItemLike)UPBlocks.ERYON_EGGS.get()))
                .addCriterion("scau_egg", InventoryChangeTrigger.TriggerInstance.hasItems((ItemLike)UPBlocks.SCAU_EGGS.get()))
                .addCriterion("ammon_egg", InventoryChangeTrigger.TriggerInstance.hasItems((ItemLike)UPBlocks.AMON_EGGS.get()))
                .addCriterion("beelze_egg", InventoryChangeTrigger.TriggerInstance.hasItems((ItemLike)UPBlocks.BEELZE_EGGS.get()))
                .addCriterion("stetha_egg", InventoryChangeTrigger.TriggerInstance.hasItems((ItemLike)UPBlocks.STETHA_EGGS.get()))
                .addCriterion("dunk_egg", InventoryChangeTrigger.TriggerInstance.hasItems((ItemLike)UPBlocks.DUNK_EGGS.get()))
                .requirements(RequirementsStrategy.OR)
                .save(consumer, this.getNameId("main/egg"));

        Advancement ammon = getAdvancement(analyzer, (ItemLike)UPItems.AMMONITE_FLASK.get(), "interact_ammonite", FrameType.TASK, true, true, false)
                .addCriterion("damage_ammon", PlayerHurtEntityTrigger.TriggerInstance.playerHurtEntity(DamagePredicate.Builder.damageInstance().type(DamageSourcePredicate.Builder.damageType().direct(EntityPredicate.Builder.entity().of(UPEntities.AMMON.get())))))
                .addCriterion("interact_ammon", PlayerInteractTrigger.TriggerInstance.itemUsedOnEntity(ItemPredicate.Builder.item(), EntityPredicate.Composite.wrap(EntityPredicate.Builder.entity().of(UPEntities.AMMON.get()).build())))
                .addCriterion("killed_ammon", KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(UPEntities.AMMON.get())))
                .save(consumer, this.getNameId("main/ammon"));

        Advancement antarcto = getAdvancement(analyzer, (ItemLike)UPItems.ANTARCTO_FLASK.get(), "interact_antarcto", FrameType.TASK, true, true, false)
                .addCriterion("damage_antarcto", PlayerHurtEntityTrigger.TriggerInstance.playerHurtEntity(DamagePredicate.Builder.damageInstance().type(DamageSourcePredicate.Builder.damageType().direct(EntityPredicate.Builder.entity().of(UPEntities.ANTARCO.get())))))
                .addCriterion("interact_antarcto", PlayerInteractTrigger.TriggerInstance.itemUsedOnEntity(ItemPredicate.Builder.item(), EntityPredicate.Composite.wrap(EntityPredicate.Builder.entity().of(UPEntities.ANTARCO.get()).build())))
                .addCriterion("killed_antarcto", KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(UPEntities.ANTARCO.get())))
                .save(consumer, this.getNameId("main/antarcto"));

        Advancement pachy = getAdvancement(analyzer, (ItemLike)UPItems.PACHY_FLASK.get(), "interact_pachy", FrameType.TASK, true, true, false)
                .addCriterion("damage_pachy", PlayerHurtEntityTrigger.TriggerInstance.playerHurtEntity(DamagePredicate.Builder.damageInstance().type(DamageSourcePredicate.Builder.damageType().direct(EntityPredicate.Builder.entity().of(UPEntities.PACHY.get())))))
                .addCriterion("interact_pachy", PlayerInteractTrigger.TriggerInstance.itemUsedOnEntity(ItemPredicate.Builder.item(), EntityPredicate.Composite.wrap(EntityPredicate.Builder.entity().of(UPEntities.PACHY.get()).build())))
                .addCriterion("killed_pachy", KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(UPEntities.PACHY.get())))
                .save(consumer, this.getNameId("main/pachy"));

        Advancement smilodon = getAdvancement(birthingPod, (ItemLike)UPItems.SMILO_FLASK.get(), "interact_smilo", FrameType.TASK, true, true, false)
                .addCriterion("damage_smilodon", PlayerHurtEntityTrigger.TriggerInstance.playerHurtEntity(DamagePredicate.Builder.damageInstance().type(DamageSourcePredicate.Builder.damageType().direct(EntityPredicate.Builder.entity().of(UPEntities.SMILODON.get())))))
                .addCriterion("interact_smilodon", PlayerInteractTrigger.TriggerInstance.itemUsedOnEntity(ItemPredicate.Builder.item(), EntityPredicate.Composite.wrap(EntityPredicate.Builder.entity().of(UPEntities.SMILODON.get()).build())))
                .addCriterion("killed_smilodon", KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(UPEntities.SMILODON.get())))
                .save(consumer, this.getNameId("main/smilodon"));

        Advancement palaeo = getAdvancement(birthingPod, (ItemLike)UPItems.PALAEO_FLASK.get(), "interact_palaeo", FrameType.TASK, true, true, false)
                .addCriterion("damage_palaeo", PlayerHurtEntityTrigger.TriggerInstance.playerHurtEntity(DamagePredicate.Builder.damageInstance().type(DamageSourcePredicate.Builder.damageType().direct(EntityPredicate.Builder.entity().of(UPEntities.PALAEOPHIS.get())))))
                .addCriterion("interact_palaeo", PlayerInteractTrigger.TriggerInstance.itemUsedOnEntity(ItemPredicate.Builder.item(), EntityPredicate.Composite.wrap(EntityPredicate.Builder.entity().of(UPEntities.PALAEOPHIS.get()).build())))
                .addCriterion("killed_palaeo", KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(UPEntities.PALAEOPHIS.get())))
                .save(consumer, this.getNameId("main/palaeophis"));

        Advancement giganto = getAdvancement(birthingPod, (ItemLike)UPItems.GIGANTO_FLASK.get(), "interact_giganto", FrameType.TASK, true, true, false)
                .addCriterion("damage_gigantopithicus", PlayerHurtEntityTrigger.TriggerInstance.playerHurtEntity(DamagePredicate.Builder.damageInstance().type(DamageSourcePredicate.Builder.damageType().direct(EntityPredicate.Builder.entity().of(UPEntities.GIGANTOPITHICUS.get())))))
                .addCriterion("interact_gigantopithicus", PlayerInteractTrigger.TriggerInstance.itemUsedOnEntity(ItemPredicate.Builder.item(), EntityPredicate.Composite.wrap(EntityPredicate.Builder.entity().of(UPEntities.GIGANTOPITHICUS.get()).build())))
                .addCriterion("killed_gigantopithicus", KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(UPEntities.GIGANTOPITHICUS.get())))
                .save(consumer, this.getNameId("main/gigantopithicus"));

        Advancement talapanas = getAdvancement(birthingPod, (ItemLike)UPItems.TALAPANAS_FLASK.get(), "interact_talapanas", FrameType.TASK, true, true, false)
                .addCriterion("damage_talapanas", PlayerHurtEntityTrigger.TriggerInstance.playerHurtEntity(DamagePredicate.Builder.damageInstance().type(DamageSourcePredicate.Builder.damageType().direct(EntityPredicate.Builder.entity().of(UPEntities.TALAPANAS.get())))))
                .addCriterion("interact_talapanas", PlayerInteractTrigger.TriggerInstance.itemUsedOnEntity(ItemPredicate.Builder.item(), EntityPredicate.Composite.wrap(EntityPredicate.Builder.entity().of(UPEntities.TALAPANAS.get()).build())))
                .addCriterion("killed_talapanas", KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(UPEntities.TALAPANAS.get())))
                .save(consumer, this.getNameId("main/talapanas"));

        Advancement mammoth = getAdvancement(birthingPod, (ItemLike)UPItems.MAMMOTH_FLASK.get(), "interact_mammoth", FrameType.TASK, true, true, false)
                .addCriterion("damage_mammoth", PlayerHurtEntityTrigger.TriggerInstance.playerHurtEntity(DamagePredicate.Builder.damageInstance().type(DamageSourcePredicate.Builder.damageType().direct(EntityPredicate.Builder.entity().of(UPEntities.MAMMOTH.get())))))
                .addCriterion("interact_mammoth", PlayerInteractTrigger.TriggerInstance.itemUsedOnEntity(ItemPredicate.Builder.item(), EntityPredicate.Composite.wrap(EntityPredicate.Builder.entity().of(UPEntities.MAMMOTH.get()).build())))
                .addCriterion("killed_mammoth", KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(UPEntities.MAMMOTH.get())))
                .save(consumer, this.getNameId("main/mammoth"));

        Advancement barina = getAdvancement(birthingPod, (ItemLike)UPItems.BARIN_FLASK.get(), "interact_barina", FrameType.TASK, true, true, false)
                .addCriterion("damage_barina", PlayerHurtEntityTrigger.TriggerInstance.playerHurtEntity(DamagePredicate.Builder.damageInstance().type(DamageSourcePredicate.Builder.damageType().direct(EntityPredicate.Builder.entity().of(UPEntities.BARINASUCHUS.get())))))
                .addCriterion("interact_barina", PlayerInteractTrigger.TriggerInstance.itemUsedOnEntity(ItemPredicate.Builder.item(), EntityPredicate.Composite.wrap(EntityPredicate.Builder.entity().of(UPEntities.BARINASUCHUS.get()).build())))
                .addCriterion("killed_barina", KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(UPEntities.BARINASUCHUS.get())))
                .save(consumer, this.getNameId("main/barina"));

        Advancement tameBarina = getAdvancement(barina, (ItemLike)UPItems.BARINA_WHISTLE.get(), "tame_barina", FrameType.TASK, true, true, false)
                .addCriterion("tame_barina", BarinaTameTrigger.TriggerInstance.usedItem())
                .save(consumer, this.getNameId("main/tame_barina"));

        Advancement paracer = getAdvancement(birthingPod, (ItemLike)UPItems.PARACER_FLASK.get(), "interact_paracer", FrameType.TASK, true, true, false)
                .addCriterion("damage_paracer", PlayerHurtEntityTrigger.TriggerInstance.playerHurtEntity(DamagePredicate.Builder.damageInstance().type(DamageSourcePredicate.Builder.damageType().direct(EntityPredicate.Builder.entity().of(UPEntities.PARACERATHERIUM.get())))))
                .addCriterion("interact_paracer", PlayerInteractTrigger.TriggerInstance.itemUsedOnEntity(ItemPredicate.Builder.item(), EntityPredicate.Composite.wrap(EntityPredicate.Builder.entity().of(UPEntities.PARACERATHERIUM.get()).build())))
                .addCriterion("killed_paracer", KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(UPEntities.PARACERATHERIUM.get())))
                .save(consumer, this.getNameId("main/paraceratherium"));

    }

    protected static Advancement.Builder getAdvancement(Advancement parent, ItemLike display, String name, FrameType frame, boolean showToast, boolean announceToChat, boolean hidden) {
        return Advancement.Builder.advancement().parent(parent).display(display, getTranslation("advancement." + name, new Object[0]), getTranslation("advancement." + name + ".desc", new Object[0]), (ResourceLocation)null, frame, showToast, announceToChat, hidden);
    }

    private String getNameId(String id) {
        return "netherdepthsupgrade:" + id;
    }


    public String getName() {
        return UnusualPrehistory.MODID + " Advancements";
    }
}
