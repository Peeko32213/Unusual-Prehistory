package com.peeko32213.unusualprehistory.core.registry;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Instrument;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class UPInstruments
{
    public static final DeferredRegister<Instrument> INSTRUMENT = DeferredRegister.create(Registry.INSTRUMENT_REGISTRY, UnusualPrehistory.MODID);
    public static final RegistryObject<Instrument> OCARINA_WHISTLE = INSTRUMENT.register("ocarina", () -> new Instrument(UPSounds.CROCARINA.get(), 200, 50));

    private static ResourceKey<Instrument> registerInstrumentRK(String name) {
        return ResourceKey.create(Registry.INSTRUMENT_REGISTRY, new ResourceLocation(UnusualPrehistory.MODID, name));
    }
}
