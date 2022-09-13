package com.peeko32213.unusualprehistory.client.event;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.client.render.*;
import com.peeko32213.unusualprehistory.core.registry.UPBlocks;
import com.peeko32213.unusualprehistory.core.registry.UPEntities;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = UnusualPrehistory.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class ClientModEvents {

    private ClientModEvents() {
    }

    private static void render(Supplier<? extends Block> block, RenderType render) {
        ItemBlockRenderTypes.setRenderLayer(block.get(), render);
    }

    public static void registerBlockRenderers() {
        RenderType cutout = RenderType.cutout();
        RenderType mipped = RenderType.cutoutMipped();
        RenderType translucent = RenderType.translucent();

        render(UPBlocks.STETHA_EGGS, translucent);
        render(UPBlocks.BEELZE_EGGS, translucent);
        render(UPBlocks.AMON_EGGS, translucent);
        render(UPBlocks.DUNK_EGGS, translucent);

    }


    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(UPEntities.STETHACANTHUS.get(), StethacanthusRenderer::new);
        event.registerEntityRenderer(UPEntities.MAJUNGA.get(), MajungasaurusRenderer::new);
        event.registerEntityRenderer(UPEntities.ANURO.get(), AnurognathusRenderer::new);
        event.registerEntityRenderer(UPEntities.BEELZ.get(), BeelzebufoRenderer::new);
        event.registerEntityRenderer(UPEntities.AMMON.get(), AmmoniteRenderer::new);
        event.registerEntityRenderer(UPEntities.DUNK.get(), DunkleosteusRenderer::new);
        event.registerEntityRenderer(UPEntities.COTY.get(), CotylorhynchusRenderer::new);
        event.registerEntityRenderer(UPEntities.BEELZE_TADPOLE.get(), BeelzebufoTadpoleRenderer::new);
        event.registerEntityRenderer(UPEntities.BABY_DUNK.get(), BabyDunkRenderer::new);

    }

}