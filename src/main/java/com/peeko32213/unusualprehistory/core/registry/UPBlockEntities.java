package com.peeko32213.unusualprehistory.core.registry;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.block.BlockUPSignBlockEntity;
import com.peeko32213.unusualprehistory.common.block.entity.*;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

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

    public static final RegistryObject<BlockEntityType<BlockUPSignBlockEntity>> UP_SIGN = BLOCK_ENTITIES.register("sign", () ->
            BlockEntityType.Builder.of(BlockUPSignBlockEntity::new,
                   UPBlocks.GINKGO_SIGN.get(),
                   UPBlocks.GINKGO_WALL_SIGN.get(),
                   UPBlocks.PETRIFIED_WOOD_SIGN.get(),
                   UPBlocks.PETRIFIED_WOOD_WALL_SIGN.get(),
                   UPBlocks.FOXXI_SIGN.get(),
                   UPBlocks.FOXXI_WALL_SIGN.get(),
                   UPBlocks.DRYO_SIGN.get(),
                    UPBlocks.DRYO_WALL_SIGN.get()
           ).build(null));


    public static final RegistryObject<BlockEntityType<FruitLootBoxEntity>> FRUIT_LOOT_BOX_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("fruit_loot_box_block_entity", () ->
                    BlockEntityType.Builder.of(FruitLootBoxEntity::new,
                            UPBlocks.FRUIT_LOOT_BOX.get()).build(null));


    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
