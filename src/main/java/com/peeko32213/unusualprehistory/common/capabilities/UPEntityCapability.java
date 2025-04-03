package com.peeko32213.unusualprehistory.common.capabilities;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UPEntityCapability implements INBTSerializable<CompoundTag> {
    public static final Logger LOGGER = LogManager.getLogger();
    public boolean itemEntityTarbloodInfectious = false;

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();

        nbt.putBoolean("itemEntityTarbloodInfectious", this.itemEntityTarbloodInfectious);

        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {

        this.itemEntityTarbloodInfectious = nbt.getBoolean("itemEntityTarbloodInfectious");

    }

}

