package com.peeko32213.unusualprehistory;

import com.peeko32213.unusualprehistory.client.screen.EncyclopediaScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.common.Mod;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = UnusualPrehistory.MODID, value = Dist.CLIENT)
public class ClientProxy extends CommonProxy  {

    public void openBookGUI(ItemStack itemStackIn) {
        Minecraft.getInstance().setScreen(new EncyclopediaScreen(itemStackIn));
    }

    public void openBookGUI(ItemStack itemStackIn, String page) {
        Minecraft.getInstance().setScreen(new EncyclopediaScreen(itemStackIn, page));
    }
}



