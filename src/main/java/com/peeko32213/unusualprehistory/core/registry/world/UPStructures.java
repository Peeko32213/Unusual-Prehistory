package com.peeko32213.unusualprehistory.core.registry.world;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.world.structure.UndergroundDigSiteStructure;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class UPStructures {
    public static final DeferredRegister<StructureType<?>> STRUCTURE_TYPES = DeferredRegister.create(Registries.STRUCTURE_TYPE, UnusualPrehistory.MODID);

    public static final RegistryObject<StructureType<UndergroundDigSiteStructure>> UNDERGROUND_DIG_SITE = STRUCTURE_TYPES.register("underground_dig_site", () -> () -> (UndergroundDigSiteStructure.CODEC));

}
