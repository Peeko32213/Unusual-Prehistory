package com.peeko32213.unusualprehistory.core.registry;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
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
            .icon(() -> new ItemStack(UPItems.AMMONITE_SHELL_ICON.get()))
            .displayItems((d, entries) ->{

                for(RegistryObject<Item> item : UPItems.ITEMS.getEntries()){
                    if(!(item.get() instanceof ForgeSpawnEggItem)) {
                        d.holders().lookup(Registries.INSTRUMENT).ifPresent((p_270036_) -> {
                            generateInstrumentTypes(entries, p_270036_, UPItems.BARINA_WHISTLE.get(), UPTags.OCARINA_WHISTLE, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                        });
                        if(!item.get().getDefaultInstance().is(UPItems.BARINA_WHISTLE.get()) && !item.get().getDefaultInstance().is(UPBlocks.FRUIT_LOOT_BOX.get().asItem())) {
                            entries.accept(item.get());
                        }

                        addTagToLootFruit(entries, UPBlocks.FRUIT_LOOT_BOX.get().asItem(),CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                    }
                }
            })
            .build();

    private static final CreativeModeTab UP_EGGS = new CreativeModeTab.Builder(CreativeModeTab.Row.TOP, 9)
            .withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
            .title(Component.translatable("itemGroup.unusual_prehistory_eggs"))
            .icon(() -> new ItemStack(UPItems.MAJUNGA_EGG.get()))
            .displayItems((d, entries) ->{
                UPItems.ITEMS.getEntries().forEach(i ->{
                    if((i.get() instanceof ForgeSpawnEggItem)) {
                        entries.accept(i.get());
                    }
                });

            })
            .build();

    public static final RegistryObject<CreativeModeTab> UP_TAB = TABS.register("unusual_prehistory", () -> UP);
    public static final RegistryObject<CreativeModeTab> UP_EGG_TAB = TABS.register("unusual_prehistory_egg", () -> UP_EGGS);


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
