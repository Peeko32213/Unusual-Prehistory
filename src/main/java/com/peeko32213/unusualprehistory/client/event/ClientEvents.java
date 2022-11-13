package com.peeko32213.unusualprehistory.client.event;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.client.model.render.*;
import com.peeko32213.unusualprehistory.client.render.*;
import com.peeko32213.unusualprehistory.client.render.armor.MajungaHelmetRenderer;
import com.peeko32213.unusualprehistory.client.render.renders.*;
import com.peeko32213.unusualprehistory.client.screen.AnalyzerScreen;
import com.peeko32213.unusualprehistory.client.screen.CultivatorScreen;
import com.peeko32213.unusualprehistory.common.item.armor.ItemMajungaHelmet;
import com.peeko32213.unusualprehistory.common.screen.CultivatorMenu;
import com.peeko32213.unusualprehistory.core.registry.UPBlocks;
import com.peeko32213.unusualprehistory.core.registry.UPEntities;
import com.peeko32213.unusualprehistory.core.registry.UPMenuTypes;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

@Mod.EventBusSubscriber(modid = UnusualPrehistory.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class ClientEvents {


    @SubscribeEvent
    public static void onClientSetup(final FMLClientSetupEvent event) {
        ItemBlockRenderTypes.setRenderLayer(UPBlocks.STETHA_EGGS.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(UPBlocks.BEELZE_EGGS.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(UPBlocks.AMON_EGGS.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(UPBlocks.DUNK_EGGS.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(UPBlocks.SCAU_EGGS.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(UPBlocks.CULTIVATOR.get(), RenderType.translucent());

        ItemBlockRenderTypes.setRenderLayer(UPBlocks.HORSETAIL.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(UPBlocks.POTTED_HORSETAIL.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(UPBlocks.TALL_HORSETAIL.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(UPBlocks.BENNETTITALES.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(UPBlocks.ARCHAEOSIGILARIA.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(UPBlocks.SARACENIA.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(UPBlocks.TALL_SARACENIA.get(), RenderType.cutout());

        ItemBlockRenderTypes.setRenderLayer(UPBlocks.LEEFRUCTUS.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(UPBlocks.POTTED_LEEFRUCTUS.get(), RenderType.cutout());


        MenuScreens.register(UPMenuTypes.ANALYZER_MENU.get(), AnalyzerScreen::new);
        MenuScreens.register(UPMenuTypes.CULTIVATOR_MENU.get(), CultivatorScreen::new);

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
        event.registerEntityRenderer(UPEntities.SCAU.get(), ScaumenaciaRenderer::new);
        event.registerEntityRenderer(UPEntities.TRIKE.get(), TriceratopsRenderer::new);
        event.registerEntityRenderer(UPEntities.PACHY.get(), PachycephalosaurusRenderer::new);
        event.registerEntityRenderer(UPEntities.BRACHI.get(), BrachiosaurusRenderer::new);
        event.registerEntityRenderer(UPEntities.VELOCI.get(), VelociraptorRenderer::new);

        event.registerEntityRenderer(UPEntities.AMMON_RENDER.get(), AmmoniteRenderRenderer::new);
        event.registerEntityRenderer(UPEntities.MAJUNGA_RENDER.get(), MajungaRenderRenderer::new);
        event.registerEntityRenderer(UPEntities.ANURO_RENDER.get(), AnuroRenderRenderer::new);
        event.registerEntityRenderer(UPEntities.BEELZ_RENDER.get(), BeelzRenderRenderer::new);
        event.registerEntityRenderer(UPEntities.COTY_RENDER.get(), CotyRenderRenderer::new);
        event.registerEntityRenderer(UPEntities.DUNK_RENDER.get(), DunkRenderRenderer::new);
        event.registerEntityRenderer(UPEntities.STETHA_RENDER.get(), StethaRenderRenderer::new);
        event.registerEntityRenderer(UPEntities.SCAU_RENDER.get(), ScaumenaciaRenderRenderer::new);

    }

    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(AmmoniteRenderModel.LAYER_LOCATION, AmmoniteRenderModel::createBodyLayer);
        event.registerLayerDefinition(MajungaRenderModel.LAYER_LOCATION, MajungaRenderModel::createBodyLayer);
        event.registerLayerDefinition(AnuroRenderModel.LAYER_LOCATION, AnuroRenderModel::createBodyLayer);
        event.registerLayerDefinition(BeelzRenderModel.LAYER_LOCATION, BeelzRenderModel::createBodyLayer);
        event.registerLayerDefinition(CotyRenderModel.LAYER_LOCATION, CotyRenderModel::createBodyLayer);
        event.registerLayerDefinition(DunkRenderModel.LAYER_LOCATION, DunkRenderModel::createBodyLayer);
        event.registerLayerDefinition(StethaRenderModel.LAYER_LOCATION, StethaRenderModel::createBodyLayer);
        event.registerLayerDefinition(ScaumenaciaRenderModel.LAYER_LOCATION, ScaumenaciaRenderModel::createBodyLayer);

    }

    @SubscribeEvent
    public static void registerArmorRenderers(final EntityRenderersEvent.AddLayers event) {
        GeoArmorRenderer.registerArmorRenderer(ItemMajungaHelmet.class, new MajungaHelmetRenderer());
    }

}