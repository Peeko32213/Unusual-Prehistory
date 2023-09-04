package com.peeko32213.unusualprehistory.datagen;

import com.peeko32213.unusualprehistory.core.registry.UPBlocks;
import com.peeko32213.unusualprehistory.core.registry.UPItems;
import net.minecraft.advancements.critereon.EnchantmentPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;

import java.util.HashSet;
import java.util.Set;

public class LootTableBlocks extends BlockLoot {

    private final Set<Block> knownBlocks = new HashSet<>();
    private static final LootItemCondition.Builder HAS_SILK_TOUCH = MatchTool.toolMatches(ItemPredicate.Builder.item().hasEnchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.Ints.atLeast(1))));
    private static final LootItemCondition.Builder HAS_NO_SILK_TOUCH = HAS_SILK_TOUCH.invert();
    @Override
    protected void add(Block block, LootTable.Builder builder) {
        super.add(block, builder);
        knownBlocks.add(block);
    }

    @Override
    protected void addTables() {
        /**Example**/

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
        dropSelf(UPBlocks.PETRIFIED_WOOD_DOOR.get());
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
        dropSelf(UPBlocks.FOXXI_DOOR.get());
        dropSelf(UPBlocks.FOXXI_TRAPDOOR.get());
        dropSelf(UPBlocks.FOXXI_SIGN.get());
        dropSelf(UPBlocks.FOXXI_WALL_SIGN.get());


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
        dropSelf(UPBlocks.DRYO_DOOR.get());
        dropSelf(UPBlocks.DRYO_TRAPDOOR.get());
        dropSelf(UPBlocks.DRYO_SIGN.get());
        dropSelf(UPBlocks.DRYO_WALL_SIGN.get());

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
        dropOther(UPBlocks.PERMAFROST_FOSSIL.get(), UPItems.FROZEN_FOSSIL.get());


        //dropSelf(UPBlocks.AMBER_BLOCK.get());
        //add(UPBlocks.QUEREUXIA_PLANT.get(), BlockLoot::createShearsOnlyDrop);


    }
    @Override
    protected Iterable<Block> getKnownBlocks() {
        return knownBlocks;
    }
}
