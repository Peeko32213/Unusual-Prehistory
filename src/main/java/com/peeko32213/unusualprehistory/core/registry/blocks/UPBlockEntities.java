package com.peeko32213.unusualprehistory.core.registry.blocks;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.block.entity.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;
import java.util.function.Supplier;

public class UPBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, UnusualPrehistory.MODID);

    public static final RegistryObject<BlockEntityType<AnalyzerBlockEntity>> ANALYZER_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("analyzer_block_entity", () ->
                    BlockEntityType.Builder.of(AnalyzerBlockEntity::new,
                            UPBlocks.ANALYZER.get()).build(null));

    public static final RegistryObject<BlockEntityType<CultivatorBlockEntity>> CULTIVATOR_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("cultivator_block_entity", () ->
                    BlockEntityType.Builder.of(CultivatorBlockEntity::new,
                            UPBlocks.CULTIVATOR.get()).build(null));

    public static final RegistryObject<BlockEntityType<IncubatorBlockEntity>> INCUBATOR_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("incubator_block_entity", () ->
                    BlockEntityType.Builder.of(IncubatorBlockEntity::new,
                            UPBlocks.INCUBATOR.get()).build(null));

    public static final RegistryObject<BlockEntityType<DNAFridgeBlockEntity>> DNA_FRIDGE_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("dna_fridge_block_entity", () ->
                    BlockEntityType.Builder.of(DNAFridgeBlockEntity::new,
                            UPBlocks.DNA_FRIDGE.get()).build(null));

    public static final RegistryObject<BlockEntityType<FruitLootBoxEntity>> FRUIT_LOOT_BOX_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("fruit_loot_box_block_entity", () ->
                    BlockEntityType.Builder.of(FruitLootBoxEntity::new,
                            UPBlocks.FRUIT_LOOT_BOX.get()).build(null));



    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }

    public static void expandVanillaDefinitions() {
        ImmutableSet.Builder<Block> validSignBlocks = new ImmutableSet.Builder<>();
        validSignBlocks.addAll(BlockEntityType.SIGN.validBlocks);
        validSignBlocks.add(UPBlocks.DRYO_SIGN.getFirst().get());
        validSignBlocks.add(UPBlocks.DRYO_SIGN.getSecond().get());
        validSignBlocks.add(UPBlocks.FOXII_SIGN.getFirst().get());
        validSignBlocks.add(UPBlocks.FOXII_SIGN.getSecond().get());
        validSignBlocks.add(UPBlocks.GINKGO_SIGN.getFirst().get());
        validSignBlocks.add(UPBlocks.GINKGO_SIGN.getSecond().get());
        validSignBlocks.add(UPBlocks.PETRIFIED_SIGN.getFirst().get());
        validSignBlocks.add(UPBlocks.PETRIFIED_SIGN.getSecond().get());
        validSignBlocks.add(UPBlocks.ZULOAGAE_SIGN.getFirst().get());
        validSignBlocks.add(UPBlocks.ZULOAGAE_SIGN.getSecond().get());
        BlockEntityType.SIGN.validBlocks = validSignBlocks.build();

        ImmutableSet.Builder<Block> validHangingSignBlocks = new ImmutableSet.Builder<>();
        validHangingSignBlocks.addAll(BlockEntityType.HANGING_SIGN.validBlocks);
        validSignBlocks.add(UPBlocks.DRYO_HANGING_SIGN.getFirst().get());
        validSignBlocks.add(UPBlocks.DRYO_HANGING_SIGN.getSecond().get());
        validSignBlocks.add(UPBlocks.FOXII_HANGING_SIGN.getFirst().get());
        validSignBlocks.add(UPBlocks.FOXII_HANGING_SIGN.getSecond().get());
        validSignBlocks.add(UPBlocks.GINKGO_HANGING_SIGN.getFirst().get());
        validSignBlocks.add(UPBlocks.GINKGO_HANGING_SIGN.getSecond().get());
        validSignBlocks.add(UPBlocks.PETRIFIED_HANGING_SIGN.getFirst().get());
        validSignBlocks.add(UPBlocks.PETRIFIED_HANGING_SIGN.getSecond().get());
        validSignBlocks.add(UPBlocks.ZULOAGAE_HANGING_SIGN.getFirst().get());
        validSignBlocks.add(UPBlocks.ZULOAGAE_HANGING_SIGN.getSecond().get());
        BlockEntityType.HANGING_SIGN.validBlocks = validHangingSignBlocks.build();
    }
}
