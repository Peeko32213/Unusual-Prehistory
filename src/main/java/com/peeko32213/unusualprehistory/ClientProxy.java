package com.peeko32213.unusualprehistory;

import com.peeko32213.unusualprehistory.client.screen.util.BookScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.common.Mod;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = UnusualPrehistory.MODID, value = Dist.CLIENT)
public class ClientProxy extends CommonProxy  {

    public Player getClientSidePlayer() {
        return Minecraft.getInstance().player;
    }
    public void openBookGUI(ResourceLocation resourceLocation) {
        Minecraft.getInstance().setScreen(new BookScreen(resourceLocation, 0));
    }
}



