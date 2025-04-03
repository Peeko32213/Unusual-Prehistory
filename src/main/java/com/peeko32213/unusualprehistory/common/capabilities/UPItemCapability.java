package com.peeko32213.unusualprehistory.common.capabilities;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.event.entity.item.ItemEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UPItemCapability implements INBTSerializable<CompoundTag> {
    public static final Logger LOGGER = LogManager.getLogger();
    public boolean itemTarbloodInfectious = false;


    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();

        nbt.putBoolean("itemTarbloodInfectious", this.itemTarbloodInfectious);

        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {

        this.itemTarbloodInfectious = nbt.getBoolean("itemTarbloodInfectious");
    }
}