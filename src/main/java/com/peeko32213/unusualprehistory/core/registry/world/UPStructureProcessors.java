package com.peeko32213.unusualprehistory.core.registry.world;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.world.structure.processor.UndergroundDigSiteProcessor;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class UPStructureProcessors {

    public static final DeferredRegister<StructureProcessorType<?>> STRUCTURE_PROCESSORS = DeferredRegister.create(Registries.STRUCTURE_PROCESSOR, UnusualPrehistory.MODID);

    public static final RegistryObject<StructureProcessorType<UndergroundDigSiteProcessor>> UNDERGROUND_DIG_SITE = STRUCTURE_PROCESSORS.register("underground_dig_site", () -> () -> UndergroundDigSiteProcessor.CODEC);

}
