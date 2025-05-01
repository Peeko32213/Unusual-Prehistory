package com.peeko32213.unusualprehistory.core.registry.blocks;

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

    public static final RegistryObject<BlockEntityType<UPSignBlockEntity>> SIGN =
            createSign("sign", UPSignBlockEntity::new,
                    () -> UPSignBlockEntity.VALID_BLOCKS);

    public static final RegistryObject<BlockEntityType<UPHangingSignBlockEntity>> HANGING_SIGN =
            createSign("sign", UPHangingSignBlockEntity::new,
                    () -> UPHangingSignBlockEntity.VALID_BLOCKS);


    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }

    public static <T extends BlockEntity> RegistryObject<BlockEntityType<T>> createSign(String name, BlockEntityType.BlockEntitySupplier<? extends T> blockEntity, Supplier<Set<Block>> validBlocks) {
        return BLOCK_ENTITIES.register(name, () -> new BlockEntityType<>(blockEntity, validBlocks.get(), null));
    }
}
