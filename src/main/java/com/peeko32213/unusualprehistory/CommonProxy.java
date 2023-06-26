package com.peeko32213.unusualprehistory;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.fml.common.Mod;

import static com.peeko32213.unusualprehistory.UnusualPrehistory.MODID;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonProxy {

    public Player getClientSidePlayer() {
        return null;
    }

    public void openBookGUI(ResourceLocation resourceLocation) {
    }

}
