package com.peeko32213.unusualprehistory.client.event;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.core.registry.UPMessages;
import com.peeko32213.unusualprehistory.core.registry.util.*;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = UnusualPrehistory.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class InputEvents {
    @SubscribeEvent
    public static void onKeyPress(InputEvent.Key event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null) return;
        onInput(mc, event.getKey(), event.getAction());
    }

    @SubscribeEvent
    public static void onMouseClick(InputEvent.MouseButton event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null) return;
        onInput(mc, event.getButton(), event.getAction());
    }

    private static void onInput(Minecraft mc, int key, int action) {
        if (mc.screen == null && ClientEvents.roarKey.isDown()) {
            UPMessages.sendToServer(new HwachaKeyInputMessage(key));
            UPMessages.sendToServer(new TrikeKeyInputMessage(key));
            UPMessages.sendToServer(new UlughKeyInputMessage(key));
            UPMessages.sendToServer(new MegatheriumKeyInputMessage(key));
        } else if(mc.screen == null && !ClientEvents.roarKey.isDown()){
            UPMessages.sendToServer(new HwachaKeyOutputMessage(key));
            UPMessages.sendToServer(new TrikeKeyOutputMessage(key));
            UPMessages.sendToServer(new UlughKeyOutputMessage(key));
            UPMessages.sendToServer(new MegatheriumKeyOutputMessage(key));
        }

    }
}
