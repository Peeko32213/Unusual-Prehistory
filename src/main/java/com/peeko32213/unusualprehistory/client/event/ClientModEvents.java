package com.peeko32213.unusualprehistory.client.event;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.client.render.AnurognathusRenderer;
import com.peeko32213.unusualprehistory.client.render.MajungasaurusRenderer;
import com.peeko32213.unusualprehistory.client.render.StethacanthusRenderer;
import com.peeko32213.unusualprehistory.core.registry.UPEntities;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = UnusualPrehistory.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class ClientModEvents {

    private ClientModEvents() {
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(UPEntities.STETHACANTHUS.get(), StethacanthusRenderer::new);
        event.registerEntityRenderer(UPEntities.MAJUNGA.get(), MajungasaurusRenderer::new);
        event.registerEntityRenderer(UPEntities.ANURO.get(), AnurognathusRenderer::new);

    }

}