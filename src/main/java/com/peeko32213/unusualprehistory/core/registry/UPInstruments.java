package com.peeko32213.unusualprehistory.core.registry;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Instrument;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class UPInstruments
{
    public static final DeferredRegister<Instrument> INSTRUMENT = DeferredRegister.create(Registries.INSTRUMENT, UnusualPrehistory.MODID);
    public static final RegistryObject<Instrument> OCARINA_WHISTLE = INSTRUMENT.register("ocarina", () -> new Instrument(UPSounds.CROCARINA.getHolder().get(), 200, 50));
    public static final ResourceKey<Instrument> OCARINA_WHISTLE_RK = registerInstrumentRK("ocarina");
    private static ResourceKey<Instrument> registerInstrumentRK(String name) {
        return ResourceKey.create(Registries.INSTRUMENT, new ResourceLocation(UnusualPrehistory.MODID, name));
    }
}
