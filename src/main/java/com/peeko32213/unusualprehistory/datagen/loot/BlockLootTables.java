package com.peeko32213.unusualprehistory.datagen.loot;

import com.peeko32213.unusualprehistory.common.block.custom.DinosaurLandEggBlock;
import com.peeko32213.unusualprehistory.common.block.custom.DinosaurWaterEggBlock;
import com.peeko32213.unusualprehistory.core.registry.UPBlocks;
import com.peeko32213.unusualprehistory.core.registry.UPItems;
import net.minecraft.advancements.critereon.EnchantmentPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

public class BlockLootTables extends BlockLootSubProvider {
    private final Set<Block> knownBlocks = new HashSet<>();
    private static final LootItemCondition.Builder HAS_SILK_TOUCH = MatchTool.toolMatches(ItemPredicate.Builder.item().hasEnchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.Ints.atLeast(1))));
    private static final LootItemCondition.Builder HAS_NO_SILK_TOUCH = HAS_SILK_TOUCH.invert();
    public BlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void add(@NotNull Block pBlock, LootTable.@NotNull Builder pBuilder) {
        super.add(pBlock, pBuilder);
        knownBlocks.add(pBlock);
    }

    @Override
    protected void generate() {
        dropSelf(UPBlocks.PETRIFIED_WOOD.get());
        dropSelf(UPBlocks.PETRIFIED_WOOD_LOG.get());
        dropSelf(UPBlocks.PETRIFIED_WOOD_SIGN.get());
        dropSelf(UPBlocks.PETRIFIED_WOOD_WALL_SIGN.get());
        dropSelf(UPBlocks.PETRIFIED_WOOD_FENCE_GATE.get());
        dropSelf(UPBlocks.PETRIFIED_WOOD_BUTTON.get());
        dropSelf(UPBlocks.PETRIFIED_WOOD_FENCE.get());
        dropSelf(UPBlocks.PETRIFIED_WOOD_SLAB.get());
        dropSelf(UPBlocks.PETRIFIED_WOOD_TRAPDOOR.get());
        dropSelf(UPBlocks.PETRIFIED_WOOD_PRESSURE_PLATE.get());
        dropSelf(UPBlocks.POLISHED_PETRIFIED_WOOD.get());
        dropSelf(UPBlocks.POLISHED_PETRIFIED_WOOD_SLAB.get());
        dropSelf(UPBlocks.POLISHED_PETRIFIED_WOOD_STAIRS.get());
        dropSelf(UPBlocks.STRIPPED_PETRIFIED_WOOD.get());
        dropSelf(UPBlocks.STRIPPED_PETRIFIED_WOOD_LOG.get());
        dropSelf(UPBlocks.PETRIFIED_WOOD_PLANKS.get());
        dropSelf(UPBlocks.PETRIFIED_WOOD_STAIRS.get());

        dropSelf(UPBlocks.FOXXI_WOOD.get());
        dropSelf(UPBlocks.FOXXI_LOG.get());
        dropSelf(UPBlocks.FOXXI_FENCE_GATE.get());
        dropSelf(UPBlocks.FOXXI_BUTTON.get());
        dropSelf(UPBlocks.FOXXI_FENCE.get());
        dropSelf(UPBlocks.FOXXI_SLAB.get());
        dropSelf(UPBlocks.FOXXI_PRESSURE_PLATE.get());
        dropSelf(UPBlocks.STRIPPED_FOXXI_WOOD.get());
        dropSelf(UPBlocks.STRIPPED_FOXXI_LOG.get());
        dropSelf(UPBlocks.FOXXI_PLANKS.get());
        dropSelf(UPBlocks.FOXXI_STAIRS.get());
        dropSelf(UPBlocks.FOXXI_TRAPDOOR.get());
        dropSelf(UPBlocks.FOXII_SIGN.get());
        dropSelf(UPBlocks.FOXII_WALL_SIGN.get());
        dropSelf(UPBlocks.FOXII_SAPLING.get());

        dropSelf(UPBlocks.OPAL_BLOCK.get());
        dropSelf(UPBlocks.DRYO_WOOD.get());
        dropSelf(UPBlocks.DRYO_LOG.get());
        dropSelf(UPBlocks.DRYO_FENCE_GATE.get());
        dropSelf(UPBlocks.DRYO_BUTTON.get());
        dropSelf(UPBlocks.DRYO_FENCE.get());
        dropSelf(UPBlocks.DRYO_SLAB.get());
        dropSelf(UPBlocks.DRYO_PRESSURE_PLATE.get());
        dropSelf(UPBlocks.STRIPPED_DRYO_WOOD.get());
        dropSelf(UPBlocks.STRIPPED_DRYO_LOG.get());
        dropSelf(UPBlocks.DRYO_PLANKS.get());
        dropSelf(UPBlocks.DRYO_STAIRS.get());
        dropSelf(UPBlocks.DRYO_TRAPDOOR.get());
        dropSelf(UPBlocks.DRYO_SIGN.get());
        dropSelf(UPBlocks.DRYO_WALL_SIGN.get());
        dropSelf(UPBlocks.DRYO_SAPLING.get());
        dropSelf(UPBlocks.GINKGO_SAPLING.get());


        dropSelf(UPBlocks.HWACHA_FOSSIL.get());
        dropSelf(UPBlocks.ANTARCTO_FOSSIL.get());
        dropSelf(UPBlocks.KENTRO_FOSSIL.get());
        dropSelf(UPBlocks.ULUGH_FOSSIL.get());
        dropSelf(UPBlocks.AUSTRO_FOSSIL.get());
        dropSelf(UPBlocks.ANURO_FOSSIL.get());
        dropSelf(UPBlocks.COTY_FOSSIL.get());
        dropSelf(UPBlocks.STETHA_FOSSIL.get());
        dropSelf(UPBlocks.SCAU_FOSSIL.get());
        dropSelf(UPBlocks.BEELZE_FOSSIL.get());
        dropSelf(UPBlocks.DUNK_FOSSIL.get());
        dropSelf(UPBlocks.BRACHI_FOSSIL.get());
        dropSelf(UPBlocks.MAJUNGA_FOSSIL.get());
        dropSelf(UPBlocks.PACHY_FOSSIL.get());
        dropSelf(UPBlocks.VELOCI_FOSSIL.get());
        dropSelf(UPBlocks.ERYON_FOSSIL.get());

        dropSelf(UPBlocks.ZULOAGAE.get());
        dropOther(UPBlocks.ZULOAGAE_SAPLING.get(), UPBlocks.ZULOAGAE.get());
        dropSelf(UPBlocks.PERMAFROST.get());
        createFortuneDrops(UPBlocks.PERMAFROST_FOSSIL.get(), UPItems.FROZEN_FOSSIL.get(),1,3);

        dropSelf(UPBlocks.ZULOAGAE_BLOCK.get());
        dropSelf(UPBlocks.ZULOAGAE_FENCE_GATE.get());
        dropSelf(UPBlocks.ZULOAGAE_BUTTON.get());
        dropSelf(UPBlocks.ZULOAGAE_FENCE.get());
        dropSelf(UPBlocks.ZULOAGAE_SLAB.get());
        dropSelf(UPBlocks.ZULOAGAE_PRESSURE_PLATE.get());
        dropSelf(UPBlocks.STRIPPED_ZULOAGAE_BLOCK.get());
        dropSelf(UPBlocks.ZULOAGAE_PLANKS.get());
        dropSelf(UPBlocks.ZULOAGAE_STAIRS.get());
        dropSelf(UPBlocks.ZULOAGAE_TRAPDOOR.get());
        dropSelf(UPBlocks.ZULOAGAE_DOOR.get());
        dropSelf(UPBlocks.AMBER_BLOCK.get());
        dropSelf(UPBlocks.OPAL_BLOCK.get());
        createPotFlowerItemTable(UPBlocks.POTTED_ARCHAEOSIGILARIA.get(),UPBlocks.ARCHAEOSIGILARIA.get());
        createPotFlowerItemTable(UPBlocks.POTTED_BENNETTITALES.get(),UPBlocks.BENNETTITALES.get());
        createPotFlowerItemTable(UPBlocks.POTTED_HORSETAIL.get(),UPBlocks.HORSETAIL.get());
        createPotFlowerItemTable(UPBlocks.POTTED_LEEFRUCTUS.get(),UPBlocks.LEEFRUCTUS.get());
        createPotFlowerItemTable(UPBlocks.POTTED_SARACENIA.get(),UPBlocks.SARACENIA.get());
        createPotFlowerItemTable(UPBlocks.POTTED_GINKGO_SAPLING.get(),UPBlocks.GINKGO_SAPLING.get());
        createPotFlowerItemTable(UPBlocks.POTTED_PETRIFIED_BUSH.get(),UPBlocks.PETRIFIED_BUSH.get());
        //createPotFlowerItemTable(UPBlocks.POTTED_FOXXI.get(),UPBlocks.FOXII_SAPLING.get());
        createPotFlowerItemTable(UPBlocks.POTTED_ZULOGAE.get(),UPBlocks.ZULOAGAE_SAPLING.get());
        createPotFlowerItemTable(UPBlocks.POTTED_DRYO.get(),UPBlocks.DRYO_SAPLING.get());

        for(RegistryObject<Block> blockRegistryObject : UPBlocks.BLOCKS.getEntries()) {
            if(blockRegistryObject.get() instanceof DinosaurLandEggBlock || blockRegistryObject.get() instanceof DinosaurWaterEggBlock) {
                createSingleItemTableSilkTouch(blockRegistryObject.get(), Items.AIR);
            }
        }
     }
    public void createSingleItemTableSilkTouch(Block pBlock, ItemLike pItem) {
        add(pBlock, createSilkTouchDispatchTable(pBlock, this.applyExplosionCondition(pBlock, LootItem.lootTableItem(pItem))));
    }

    protected void createPotFlowerItemTable(Block flowerpotBlock, ItemLike pItem) {
        add(flowerpotBlock ,LootTable.lootTable()
                .withPool(this.applyExplosionCondition(Blocks.FLOWER_POT, LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1.0F))
                .add(LootItem.lootTableItem(Blocks.FLOWER_POT))))
                .withPool(this.applyExplosionCondition(pItem, LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1.0F))
                        .add(LootItem.lootTableItem(pItem)))));
    }

    protected LootTable.Builder createCopperLikeOreDrops(Block pBlock, Item item) {
        return createSilkTouchDispatchTable(pBlock,
                this.applyExplosionDecay(pBlock,
                        LootItem.lootTableItem(item)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 5.0F)))
                                .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE))));
    }

    protected void  createFortuneDrops(Block pBlock, Item item, int minCount, int maxCount) {
        add(pBlock, createSilkTouchDispatchTable(pBlock,
                this.applyExplosionDecay(pBlock,
                        LootItem.lootTableItem(item)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(minCount, maxCount)))
                                .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE)))));
    }

    protected LootTable.Builder createMultipleDrops(Block pBlock, Item item1, Item item2) {
        return createSilkTouchDispatchTable(pBlock,
                this.applyExplosionDecay(pBlock,
                        LootItem.lootTableItem(item1)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 5.0F)))
                                .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE)))
                        .then(LootItem.lootTableItem(item2)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))
                                .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE))));
    }



    @Override
    protected @NotNull Iterable<Block> getKnownBlocks() {
        return knownBlocks;
    }
}
