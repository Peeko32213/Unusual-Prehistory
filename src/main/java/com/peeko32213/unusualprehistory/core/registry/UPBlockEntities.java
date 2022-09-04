package com.peeko32213.unusualprehistory.core.registry;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.block.entity.AnalyzerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class UPBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, UnusualPrehistory.MODID);

    public static final RegistryObject<BlockEntityType<AnalyzerBlockEntity>> ANALYZER_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("analyzer_block_entity", () ->
                    BlockEntityType.Builder.of(AnalyzerBlockEntity::new,
                            UPBlocks.ANALYZER.get()).build(null));


    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
