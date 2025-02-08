package com.peeko32213.unusualprehistory.core.registry;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.block.BlockDinosaurLandEggs;
import com.peeko32213.unusualprehistory.common.data.ItemWeightedPairCodec;
import com.peeko32213.unusualprehistory.common.data.LootFruitCodec;
import com.peeko32213.unusualprehistory.common.data.LootFruitJsonManager;
import com.peeko32213.unusualprehistory.common.data.RollableItemCodec;
import com.peeko32213.unusualprehistory.common.item.MusicalTameItem;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class UPTabs {

    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, UnusualPrehistory.MODID);

    private static final CreativeModeTab UP = new CreativeModeTab.Builder(CreativeModeTab.Row.TOP, 9)
            .withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
            .title(Component.translatable("itemGroup.unusual_prehistory"))
            .icon(() -> new ItemStack(UPBlocks.AMMONITE_SHELL.get()))
            .displayItems((d, entries) ->{

                for(RegistryObject<Item> item : UPItems.ITEMS.getEntries()){

                        d.holders().lookup(Registries.INSTRUMENT).ifPresent((p_270036_) -> {
                            generateInstrumentTypes(entries, p_270036_, UPItems.BARINA_WHISTLE.get(), UPTags.OCARINA_WHISTLE, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                        });

                        if(!item.get().getDefaultInstance().is(UPItems.BARINA_WHISTLE.get()) && !item.get().getDefaultInstance().is(UPBlocks.FRUIT_LOOT_BOX.get().asItem())) {
                            // Paleo DNA
                            entries.accept(UPItems.AMMONITE_FLASK.get());
                            entries.accept(UPItems.COTY_FLASK.get());
                            entries.accept(UPItems.DUNK_FLASK.get());
                            entries.accept(UPItems.SCAU_FLASK.get());
                            entries.accept(UPItems.STETHA_FLASK.get());

                            // Meso DNA
                            entries.accept(UPItems.ANTARCTO_FLASK.get());
                            entries.accept(UPItems.ANURO_FLASK.get());
                            entries.accept(UPItems.AUSTRO_FLASK.get());
                            entries.accept(UPItems.BEELZ_FLASK.get());
                            entries.accept(UPItems.BRACHI_FLASK.get());
                            entries.accept(UPItems.ENCRUSTED_FLASK.get());
                            entries.accept(UPItems.ERYON_FLASK.get());
                            entries.accept(UPItems.HWACHA_FLASK.get());
                            entries.accept(UPItems.KENTRO_FLASK.get());
                            entries.accept(UPItems.KIMMER_FLASK.get());
                            entries.accept(UPItems.MAJUNGA_FLASK.get());
                            entries.accept(UPItems.PACHY_FLASK.get());
                            entries.accept(UPItems.TRIKE_FLASK.get());
                            entries.accept(UPItems.REX_FLASK.get());
                            entries.accept(UPItems.ULUGH_FLASK.get());
                            entries.accept(UPItems.RAPTOR_FLASK.get());

                            // Ceno DNA
                            entries.accept(UPItems.BARIN_FLASK.get());
                            entries.accept(UPItems.GIGANTO_FLASK.get());
                            entries.accept(UPItems.MAMMOTH_FLASK.get());
                            entries.accept(UPItems.MEGALA_FLASK.get());
                            entries.accept(UPItems.MEGATH_FLASK.get());
                            entries.accept(UPItems.PALAEO_FLASK.get());
                            entries.accept(UPItems.PARACER_FLASK.get());
                            entries.accept(UPItems.SMILO_FLASK.get());
                            entries.accept(UPItems.TALPANAS_FLASK.get());

                            // Paleo DNA
                            entries.accept(UPItems.AMMONITE_FLASK.get());
                            entries.accept(UPItems.COTY_FLASK.get());
                            entries.accept(UPItems.DUNK_FLASK.get());
                            entries.accept(UPItems.SCAU_FLASK.get());
                            entries.accept(UPItems.STETHA_FLASK.get());

                            // Meso DNA
                            entries.accept(UPItems.ANTARCTO_FLASK.get());
                            entries.accept(UPItems.ANURO_FLASK.get());
                            entries.accept(UPItems.AUSTRO_FLASK.get());
                            entries.accept(UPItems.BEELZ_FLASK.get());
                            entries.accept(UPItems.BRACHI_FLASK.get());
                            entries.accept(UPItems.ENCRUSTED_FLASK.get());
                            entries.accept(UPItems.ERYON_FLASK.get());
                            entries.accept(UPItems.HWACHA_FLASK.get());
                            entries.accept(UPItems.KENTRO_FLASK.get());
                            entries.accept(UPItems.KIMMER_FLASK.get());
                            entries.accept(UPItems.MAJUNGA_FLASK.get());
                            entries.accept(UPItems.PACHY_FLASK.get());
                            entries.accept(UPItems.TRIKE_FLASK.get());
                            entries.accept(UPItems.REX_FLASK.get());
                            entries.accept(UPItems.ULUGH_FLASK.get());
                            entries.accept(UPItems.RAPTOR_FLASK.get());

                            // Ceno DNA
                            entries.accept(UPItems.BARIN_FLASK.get());
                            entries.accept(UPItems.GIGANTO_FLASK.get());
                            entries.accept(UPItems.MAMMOTH_FLASK.get());
                            entries.accept(UPItems.MEGALA_FLASK.get());
                            entries.accept(UPItems.MEGATH_FLASK.get());
                            entries.accept(UPItems.PALAEO_FLASK.get());
                            entries.accept(UPItems.PARACER_FLASK.get());
                            entries.accept(UPItems.SMILO_FLASK.get());
                            entries.accept(UPItems.TALPANAS_FLASK.get());

                            // Plant DNA

                            entries.accept(UPItems.ANOSTYLOSTRAMA_FLASK.get());
                            entries.accept(UPItems.ARCHAEFRUCTUS_FLASK.get());
                            entries.accept(UPItems.ARCHAO_FLASK.get());
                            entries.accept(UPItems.BENNET_FLASK.get());
                            entries.accept(UPItems.CLATHRODICTYON_FLASK.get());
                            entries.accept(UPItems.DRYO_FLASK.get());
                            entries.accept(UPItems.FOXXI_FLASK.get());
                            entries.accept(UPItems.GINKGO_FLASK.get());
                            entries.accept(UPItems.HORSETAIL_FLASK.get());
                            entries.accept(UPItems.LEEFRUCTUS_FLASK.get());
                            entries.accept(UPItems.NELUMBITES_FLASK.get());
                            entries.accept(UPItems.QUEREUXIA_FLASK.get());
                            entries.accept(UPItems.RAIGUENRAYUN_FLASK.get());
                            entries.accept(UPItems.SARR_FLASK.get());
                            entries.accept(UPItems.ZULOAGAE_FLASK.get());

                            // Paleo eggs
                            entries.accept(UPBlocks.AMON_EGGS.get());
                            entries.accept(UPBlocks.COTY_EGG.get());
                            entries.accept(UPBlocks.DUNK_EGGS.get());
                            entries.accept(UPBlocks.SCAU_EGGS.get());
                            entries.accept(UPBlocks.STETHA_EGGS.get());

                            // Meso eggs
                            entries.accept(UPBlocks.ANTARCO_EGG.get());
                            entries.accept(UPBlocks.ANURO_EGG.get());
                            entries.accept(UPBlocks.AUSTRO_EGG.get());
                            entries.accept(UPBlocks.BEELZE_EGGS.get());
                            entries.accept(UPBlocks.BRACHI_EGG.get());
                            entries.accept(UPBlocks.ENCRUSTED_SACK.get());
                            entries.accept(UPBlocks.ERYON_EGGS.get());
                            entries.accept(UPBlocks.HWACHA_EGG.get());
                            entries.accept(UPBlocks.KENTRO_EGG.get());
                            entries.accept(UPBlocks.KIMMER_EGGS.get());
                            entries.accept(UPBlocks.MAJUNGA_EGG.get());
                            entries.accept(UPBlocks.PACHY_EGG.get());
                            entries.accept(UPBlocks.TRIKE_EGG.get());
                            entries.accept(UPBlocks.REX_EGG.get());
                            entries.accept(UPBlocks.ULUGH_EGG.get());
                            entries.accept(UPBlocks.RAPTOR_EGG.get());

                            // Ceno eggs & embryos
                            entries.accept(UPBlocks.BARINA_EGG.get());
                            entries.accept(UPItems.GIGANTO_EMBRYO.get());
                            entries.accept(UPItems.MAMMOTH_EMBRYO.get());
                            entries.accept(UPBlocks.MEGALA_EGG.get());
                            entries.accept(UPItems.MEGATH_EMBRYO.get());
                            entries.accept(UPItems.PALAEO_EMBRYO.get());
                            entries.accept(UPItems.PARACER_EMBRYO.get());
                            entries.accept(UPItems.SMILODON_EMBRYO.get());
                            entries.accept(UPBlocks.TALPANAS_EGG.get());

                            // Unsorted stuff
                            entries.accept(item.get());
                        }

                    addTagToLootFruit(entries, UPBlocks.FRUIT_LOOT_BOX.get().asItem(),CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                }
            })
            .build();

    public static final RegistryObject<CreativeModeTab> UP_TAB = TABS.register("unusual_prehistory", () -> UP);


    private static void generateInstrumentTypes(CreativeModeTab.Output pOutput, HolderLookup<Instrument> pInstruments, Item pItem, TagKey<Instrument> pInstrument, CreativeModeTab.TabVisibility pTabVisibility) {
        pInstruments.get(pInstrument).ifPresent((p_270021_) -> {
            p_270021_.stream().map((p_269995_) -> {
                return MusicalTameItem.create(pItem, p_269995_);
            }).forEach((p_270011_) -> {
                pOutput.accept(p_270011_, pTabVisibility);
            });
        });
    }

    private static void generateEnchantsForBoots(CreativeModeTab.Output output, Item item, CreativeModeTab.TabVisibility tabVisibility) {
        ItemStack soulsuckerBoots = new ItemStack(item);
        soulsuckerBoots.enchant(Enchantments.SOUL_SPEED, 3);
        output.accept(soulsuckerBoots, tabVisibility);
    }

    private static void addTagToDinosaurWhistle(CreativeModeTab.Output output, Item item, CreativeModeTab.TabVisibility tabVisibility) {
        ItemStack dinoWhistle = new ItemStack(item);
        CompoundTag compoundTag = new CompoundTag();
        compoundTag.putInt("command", 0);
        dinoWhistle.setTag(compoundTag);
        output.accept(dinoWhistle, tabVisibility);
    }

    private static final LootFruitCodec LOOT_FRUIT = new LootFruitCodec(2, "unusualprehistory.loot_fruit_box.default", Items.BAMBOO, Collections.emptyList(), TextColor.fromRgb(12345), 2);
    private static final List<LootFruitCodec> LOOT_FRUIT_LIST = new ArrayList<>() {{
        add(LOOT_FRUIT);
    }};

    private static void addTagToLootFruit(CreativeModeTab.Output output, Item item, CreativeModeTab.TabVisibility tabVisibility) {
        boolean isEmpty = LootFruitJsonManager.getTierTrades().isEmpty();
        if(!isEmpty) {
            Map<Integer, List<LootFruitCodec>> lootFruitItem= LootFruitJsonManager.getTierTrades();
            for(List<LootFruitCodec> lootFruitCodecs : lootFruitItem.values()){
                for(LootFruitCodec lootFruitCodec : lootFruitCodecs){

                    ItemStack istack = new ItemStack(item);
                    CompoundTag lootFruitTag = istack.getOrCreateTag();
                    int color = lootFruitCodec.getColor().getValue();
                    lootFruitTag.putString("translationKey", lootFruitCodec.getTranslationKey());
                    lootFruitTag.putInt("color", color);
                    lootFruitTag.put("tradeItem", lootFruitCodec.getTradeItem().getDefaultInstance().serializeNBT());
                    lootFruitTag.putInt("CustomModelData", lootFruitCodec.getCustomModelData());
                    istack.setTag(lootFruitTag);
                    output.accept(istack, tabVisibility);
                }
            }


        } else {
            ItemStack istack = new ItemStack(item);
            List<ItemWeightedPairCodec> itemWeightedPairCodecs = new ArrayList<>();
            itemWeightedPairCodecs.add(new ItemWeightedPairCodec(UPItems.PALEO_FOSSIL.get(), 100, 1));
            List<RollableItemCodec> rollableItemCodecs =  new ArrayList<>();
            rollableItemCodecs.add(new RollableItemCodec(1, itemWeightedPairCodecs));
            LootFruitCodec lootFruitCodec = LOOT_FRUIT;
            CompoundTag lootFruitTag = istack.getOrCreateTag();
            if(lootFruitCodec == null) return;
            int color = lootFruitCodec.getColor().getValue();
            lootFruitTag.putString("translationKey", lootFruitCodec.getTranslationKey());
            lootFruitTag.putInt("color", color);
            lootFruitTag.putInt("CustomModelData", lootFruitCodec.getCustomModelData());
            lootFruitTag.put("tradeItem", lootFruitCodec.getTradeItem().getDefaultInstance().serializeNBT());
            istack.setTag(lootFruitTag);
            output.accept(istack, tabVisibility);
        }
    }

}
