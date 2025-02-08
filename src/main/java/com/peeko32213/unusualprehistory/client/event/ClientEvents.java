package com.peeko32213.unusualprehistory.client.event;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.client.model.*;
import com.peeko32213.unusualprehistory.client.model.iceberg.IcebergMammothModel;
import com.peeko32213.unusualprehistory.client.model.iceberg.IcebergSmilodonModel;
import com.peeko32213.unusualprehistory.client.model.plant.PlantModel;
import com.peeko32213.unusualprehistory.client.overlay.AmberProtectionOverlay;
import com.peeko32213.unusualprehistory.client.overlay.TarOverlay;
import com.peeko32213.unusualprehistory.client.particles.ElectricAttackParticle;
import com.peeko32213.unusualprehistory.client.particles.ElectricOrbitParticle;
import com.peeko32213.unusualprehistory.client.particles.TarBubbleParticle;
import com.peeko32213.unusualprehistory.client.render.UPRenderUtils;
//import com.peeko32213.unusualprehistory.client.render.arrow.PsittaccoArrowRenderer;
import com.peeko32213.unusualprehistory.client.render.block.CultivatorBlockEntityRenderer;
import com.peeko32213.unusualprehistory.client.render.block.IncubatorBlockEntityRenderer;
import com.peeko32213.unusualprehistory.client.render.block.ThrowableFallingBlockRenderer;
import com.peeko32213.unusualprehistory.client.render.dinosaur_renders.*;
import com.peeko32213.unusualprehistory.client.render.tool.FlatMovingThrownItemRenderer;
import com.peeko32213.unusualprehistory.client.screen.AnalyzerScreen;
import com.peeko32213.unusualprehistory.client.screen.CultivatorScreen;
import com.peeko32213.unusualprehistory.client.screen.DNAFridgeScreen;
import com.peeko32213.unusualprehistory.common.block.entity.FruitLootBoxEntity;
import com.peeko32213.unusualprehistory.common.entity.EntityAnurognathus;
import com.peeko32213.unusualprehistory.common.entity.EntityScaumenacia;
import com.peeko32213.unusualprehistory.common.entity.EntityStethacanthus;
import com.peeko32213.unusualprehistory.core.registry.*;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.*;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.awt.event.KeyEvent;

import static com.peeko32213.unusualprehistory.core.registry.UPWoodTypes.*;

@Mod.EventBusSubscriber(modid = UnusualPrehistory.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class ClientEvents {


    public static void init(FMLClientSetupEvent event) {
        UPItemProperties.addItemProperties();
    }


    @SubscribeEvent
    public static void onClientSetup(final FMLClientSetupEvent event) {
        UnusualPrehistory.checkForGeckoLib();
        ItemBlockRenderTypes.setRenderLayer(UPBlocks.STETHA_EGGS.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(UPBlocks.BEELZE_EGGS.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(UPBlocks.AMON_EGGS.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(UPBlocks.DUNK_EGGS.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(UPBlocks.SCAU_EGGS.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(UPBlocks.KIMMER_EGGS.get(), RenderType.cutout());
//        ItemBlockRenderTypes.setRenderLayer(UPBlocks.FURCACAUDA_EGGS.get(), RenderType.cutout());


        ItemBlockRenderTypes.setRenderLayer(UPBlocks.CULTIVATOR.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(UPBlocks.GINKGO_DOOR.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(UPBlocks.GINKGO_TRAPDOOR.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(UPBlocks.PETRIFIED_WOOD_DOOR.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(UPBlocks.PETRIFIED_WOOD_TRAPDOOR.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(UPBlocks.ERYON_EGGS.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(UPBlocks.FOXXI_DOOR.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(UPBlocks.FOXXI_TRAPDOOR.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(UPBlocks.DRYO_DOOR.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(UPBlocks.DRYO_TRAPDOOR.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(UPBlocks.ZULOAGAE_DOOR.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(UPBlocks.ZULOAGAE_TRAPDOOR.get(), RenderType.cutout());

        ItemBlockRenderTypes.setRenderLayer(UPBlocks.HORSETAIL.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(UPBlocks.LEEFRUCTUS.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(UPBlocks.TALL_HORSETAIL.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(UPBlocks.BENNETTITALES.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(UPBlocks.ARCHAEOSIGILARIA.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(UPBlocks.SARACENIA.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(UPBlocks.TALL_SARACENIA.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(UPBlocks.REX_HEAD.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(UPBlocks.GINKGO_SAPLING.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(UPBlocks.COTY_FOSSIL.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(UPBlocks.STETHA_FOSSIL.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(UPBlocks.ANURO_FOSSIL.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(UPBlocks.SCAU_FOSSIL.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(UPBlocks.BEELZE_FOSSIL.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(UPBlocks.BRACHI_FOSSIL.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(UPBlocks.DUNK_FOSSIL.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(UPBlocks.MAJUNGA_FOSSIL.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(UPBlocks.PACHY_FOSSIL.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(UPBlocks.VELOCI_FOSSIL.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(UPBlocks.ERYON_FOSSIL.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(UPBlocks.CLATHRODICTYON.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(UPBlocks.CLATHRODICTYON_FAN.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(UPBlocks.CLATHRODICTYON_WALL_FAN.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(UPBlocks.DEAD_CLATHRODICTYON.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(UPBlocks.DEAD_CLATHRODICTYON_FAN.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(UPBlocks.DEAD_CLATHRODICTYON_WALL_FAN.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(UPBlocks.ARCHAEFRUCTUS.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(UPBlocks.NELUMBITES.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(UPBlocks.QUEREUXIA.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(UPBlocks.QUEREUXIA_PLANT.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(UPBlocks.QUEREUXIA_TOP.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(UPBlocks.PETRIFIED_BUSH.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(UPBlocks.AUSTRO_FOSSIL.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(UPBlocks.ULUGH_FOSSIL.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(UPBlocks.KENTRO_FOSSIL.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(UPBlocks.ANTARCTO_FOSSIL.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(UPBlocks.HWACHA_FOSSIL.get(), RenderType.cutout());

        ItemBlockRenderTypes.setRenderLayer(UPBlocks.POTTED_HORSETAIL.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(UPBlocks.POTTED_LEEFRUCTUS.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(UPBlocks.POTTED_ARCHAEOSIGILARIA.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(UPBlocks.POTTED_BENNETTITALES.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(UPBlocks.POTTED_SARACENIA.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(UPBlocks.POTTED_GINKGO_SAPLING.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(UPBlocks.POTTED_PETRIFIED_BUSH.get(), RenderType.cutout());

        ItemBlockRenderTypes.setRenderLayer(UPBlocks.AMBER_GLASS.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(UPBlocks.CULTIVATOR.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(UPBlocks.DRYO_SAPLING.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(UPBlocks.FOXII_SAPLING.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(UPBlocks.INCUBATOR.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(UPBlocks.ZULOAGAE_SAPLING.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(UPBlocks.ZULOAGAE.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(UPBlocks.RAIGUENRAYUN.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(UPBlocks.SPLATTERED_TAR.get(), RenderType.translucent());

//        ItemBlockRenderTypes.setRenderLayer(UPBlocks.FURCACAUDA_EGGS.get(), RenderType.cutout());

        // Unused 1.6 stuff

//        ItemBlockRenderTypes.setRenderLayer(UPBlocks.OPHIDION_EGGS.get(), RenderType.cutout());
//        ItemBlockRenderTypes.setRenderLayer(UPBlocks.DIPLOCAULUS_EGGS.get(), RenderType.cutout());
//        ItemBlockRenderTypes.setRenderLayer(UPBlocks.HYNERIA_EGGS.get(), RenderType.cutout());
//        ItemBlockRenderTypes.setRenderLayer(UPBlocks.TARTUO_EGGS.get(), RenderType.cutout());

        MenuScreens.register(UPMenuTypes.ANALYZER_MENU.get(), AnalyzerScreen::new);
        MenuScreens.register(UPMenuTypes.CULTIVATOR_MENU.get(), CultivatorScreen::new);
        MenuScreens.register(UPMenuTypes.DNA_FRIDGE_MENU.get(), DNAFridgeScreen::new);


        WoodType.register(UPWoodTypes.GINKGO);
        WoodType.register(UPWoodTypes.PETRIFIED);
        WoodType.register(UPWoodTypes.FOXXI);
        WoodType.register(UPWoodTypes.DRYO);

        Sheets.addWoodType(GINKGO);
        Sheets.addWoodType(DRYO);
        Sheets.addWoodType(FOXXI);
        Sheets.addWoodType(PETRIFIED);

       BlockEntityRenderers.register(UPBlockEntities.UP_SIGN.get(), SignRenderer::new);


    }

    @SubscribeEvent
    public static void registerParticleTypes(RegisterParticleProvidersEvent event){
        event.registerSpriteSet(UPParticles.TAR_BUBBLE.get(), TarBubbleParticle.Provider::new);
        event.registerSpecial(UPParticles.ELECTRIC_ORBIT.get(), new ElectricOrbitParticle.PillarFactory());
        event.registerSpecial(UPParticles.ELECTRIC_ATTACK.get(), new ElectricAttackParticle.ElectricAttackFactory());
    }
    private static final ResourceLocation TRICERATOPS_SADDLE_OVERLAY = new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/triceratops_saddle.png");
    private static final ResourceLocation TRICERATOPS_MODEL = new ResourceLocation(UnusualPrehistory.MODID, "geo/trike.geo.json");
    private static final ResourceLocation ULUGH_JEB_OVERLAY = new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/ulughbegsaurus_jeb.png");
    private static final ResourceLocation ULUGH_SADDLE_OVERLAY = new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/ulughbegsaurus_saddled.png");
    private static final ResourceLocation ULUGH_MODEL = new ResourceLocation(UnusualPrehistory.MODID, "geo/ulughbegsaurus.geo.json");
    private static final ResourceLocation HWACHA_SADDLE_OVERLAY = new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/hwachavenator_saddled.png");
    private static final ResourceLocation HWACHA_MODEL = new ResourceLocation(UnusualPrehistory.MODID, "geo/hwachavenator.geo.json");
    private static final ResourceLocation MEGATHERIUM_SADDLE_OVERLAY = new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/megatherium_saddled.png");
    private static final ResourceLocation MEGATHERIUM_MODEL = new ResourceLocation(UnusualPrehistory.MODID, "geo/megatherium.geo.json");

    private static final ResourceLocation BARINASUCHUS_MODEL = new ResourceLocation(UnusualPrehistory.MODID, "geo/barinasuchus.geo.json");
    private static final ResourceLocation BEELZE_SADDLE_OVERLAY = new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/beelzebufo_saddle.png");
    private static final ResourceLocation BEELZE_MODEL = new ResourceLocation(UnusualPrehistory.MODID, "geo/beelzebufo.geo.json");

    // Unused 1.6 stuff
//    private static final ResourceLocation OTAROCYCON_MODEL = new ResourceLocation(UnusualPrehistory.MODID, "geo/otarocyon.geo.json");
//    private static final ResourceLocation KAPROSUCHUS_MODEL = new ResourceLocation(UnusualPrehistory.MODID, "geo/kaprosuchus.geo.json");
//    private static final ResourceLocation ARCHELON_MODEL = new ResourceLocation(UnusualPrehistory.MODID, "geo/archelon.geo.json");
//    private static final ResourceLocation ARCHELON_SADDLE_EMPTY_OVERLAY = new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/archelon_saddle_empty.png");
//    private static final ResourceLocation ARCHELON_SADDLE_HEART_OVERLAY = new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/archelon_saddle_heart.png");
//    private static final ResourceLocation BALAUR_MODEL = new ResourceLocation(UnusualPrehistory.MODID, "geo/balaur.geo.json");

    //private static final ResourceLocation BRACHI_SADDLE_OVERLAY = new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/brachiosaurus_saddle.png");
    //private static final ResourceLocation BRACHI_MODEL = new ResourceLocation(UnusualPrehistory.MODID, "geo/brachi.geo.json");
    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(UPEntities.STETHACANTHUS.get(), e -> new LivingEntityFishRenderer<>(e, new StethacanthusModel()));
        event.registerEntityRenderer(UPEntities.MAJUNGA.get(), e -> new DinosaurRenderer<>(e, new MajungasaurusModel()));
        event.registerEntityRenderer(UPEntities.ANURO.get(), e -> new AgeableMobRenderer<>(e, new DefaultModel<EntityAnurognathus>(ModelLocations.ANURO)));
        event.registerEntityRenderer(UPEntities.BEELZ.get(), e ->
                UPRenderUtils.createDinosaurRenderer(e, new DefaultModel<>(ModelLocations.BEELZEBUFO))
                        .withLayers(BEELZE_MODEL)
                        .withSaddleLayer(BEELZE_SADDLE_OVERLAY).build());

        event.registerEntityRenderer(UPEntities.AMMON.get(), e -> new LivingCutoutNoCullEntityRenderer<>(e, new DefaultModel<>(ModelLocations.AMMONITE)));
        event.registerEntityRenderer(UPEntities.DUNK.get(), e -> new LivingCutoutNoCullEntityRenderer<>(e, new DunkleosteusModel()));
        event.registerEntityRenderer(UPEntities.COTY.get(), e -> new DinosaurRenderer<>(e, new CotylorhynchusModel()));
        event.registerEntityRenderer(UPEntities.BEELZE_TADPOLE.get(), e -> new LivingCutoutNoCullEntityRenderer<>(e, new DefaultModel<>(ModelLocations.BABY_BEELZEBUFO)));
        event.registerEntityRenderer(UPEntities.BABY_DUNK.get(), e -> new LivingCutoutNoCullEntityRenderer<>(e, new DefaultModel<>(ModelLocations.BABY_DUNK)));
        event.registerEntityRenderer(UPEntities.SCAU.get(), e -> new LivingEntityFishRenderer<>(e, new ScaumenaciaModel()));

        event.registerEntityRenderer(UPEntities.TRIKE.get(), e ->
                UPRenderUtils.createTamableDinosaurRenderer(e, new TriceratopsModel())
                        .withLayers(TRICERATOPS_MODEL)
                        .withSaddleLayer(TRICERATOPS_SADDLE_OVERLAY).build());

        event.registerEntityRenderer(UPEntities.PACHY.get(), e -> new DinosaurRenderer<>(e, new PachycephalosaurusModel()));
        event.registerEntityRenderer(UPEntities.BRACHI.get(), BrachiosaurusRenderer::new);
        event.registerEntityRenderer(UPEntities.BRACHI_TEEN.get(), BrachiosaurusTeenRenderer::new);
        event.registerEntityRenderer(UPEntities.VELOCI.get(), e -> new DinosaurRenderer<>(e, new VelociraptorModel()));
        event.registerEntityRenderer(UPEntities.REX.get(), TyrannosaurusRexRenderer::new);
        event.registerEntityRenderer(UPEntities.ENCRUSTED.get(), e -> new LivingCutoutNoCullEntityRenderer<>(e, new DefaultModel<>(ModelLocations.ENCRUSTED)));
        event.registerEntityRenderer(UPEntities.AMBER_SHOT.get(), AmberShotRenderer::new);
        event.registerEntityRenderer(UPEntities.HWACHA_SPIKE.get(), HwachaSpikeRenderer::new);
        event.registerEntityRenderer(UPEntities.BABY_BRACHI.get(), e -> new LivingCutoutNoCullEntityRenderer<>(e, new DefaultModel<>(ModelLocations.BABY_BRACHI)));
        event.registerEntityRenderer(UPEntities.BABY_REX.get(), e -> new LivingCutoutNoCullEntityRenderer<>(e, new DefaultModel<>(ModelLocations.BABY_REX)));
        event.registerEntityRenderer(UPEntities.BABY_MEGATHERIUM.get(), e -> new LivingCutoutNoCullEntityRenderer<>(e, new DefaultModel<>(ModelLocations.BABY_MEGATHERIUM)));
        event.registerEntityRenderer(UPEntities.BABY_GIGANTO.get(), e -> new LivingCutoutNoCullEntityRenderer<>(e, new DefaultModel<>(ModelLocations.BABY_GIGAN)));
        event.registerEntityRenderer(UPEntities.BABY_PARACER.get(), e -> new LivingCutoutNoCullEntityRenderer<>(e, new DefaultModel<>(ModelLocations.BABY_PARACERATHERIUM)));
        event.registerEntityRenderer(UPEntities.BABY_MEGALANIA.get(), e -> new LivingCutoutNoCullEntityRenderer<>(e, new DefaultModel<>(ModelLocations.BABY_MEGALANIA)));
        event.registerEntityRenderer(UPEntities.BABY_PALAEO.get(), e -> new LivingCutoutNoCullEntityRenderer<>(e, new VariantModel<>(ModelLocations.BABY_PALEOLOPHIS)));
        event.registerEntityRenderer(UPEntities.BABY_BARINA.get(), e -> new LivingCutoutNoCullEntityRenderer<>(e, new DefaultModel<>(ModelLocations.BABY_BARINA)));
        event.registerEntityRenderer(UPEntities.BABY_SMILODON.get(), e -> new LivingCutoutNoCullEntityRenderer<>(e, new VariantModel<>(ModelLocations.BABY_SMILODON)));
//        event.registerEntityRenderer(UPEntities.BABY_MAMMOTH.get(), e -> new LivingCutoutNoCullEntityRenderer<>(e,  new DefaultModel<>(ModelLocations.BABY_MAMMOTH)));

        event.registerEntityRenderer(UPEntities.ERYON.get(), e -> new DinosaurCutoutNoCullRenderer<>(e, new VariantModel<>(ModelLocations.ERYON)));
        event.registerEntityRenderer(UPEntities.AUSTRO.get(), e -> new DinosaurCutoutNoCullRenderer<>(e, new CustomAnimationsModel<>(ModelLocations.AUSTRORAPTOR)));
        event.registerEntityRenderer(UPEntities.ANTARCO.get(), e -> new DinosaurRenderer<>(e, new CustomAnimationsModel<>(ModelLocations.ANTARCTOPELTA)));
        event.registerEntityRenderer(UPEntities.ULUG.get(), e ->
                UPRenderUtils.createTamableDinosaurRenderer(e, new UlughbegsaurusModel())
                        .withLayers(ULUGH_MODEL)
                        .withSaddleLayer(ULUGH_SADDLE_OVERLAY)
                        .withJebLayer(ULUGH_JEB_OVERLAY)
                        .build());
        event.registerEntityRenderer(UPEntities.KENTRO.get(), e -> new DinosaurRenderer<>(e, new KentrosaurusModel()));
        event.registerEntityRenderer(UPEntities.HWACHA.get(), e ->
                UPRenderUtils.createTamableDinosaurRenderer(e, new HwachavenatorModel())
                        .withLayers(HWACHA_MODEL)
                        .withSaddleLayer(HWACHA_SADDLE_OVERLAY)
                        .build());
        event.registerEntityRenderer(UPEntities.TALPANAS.get(), e -> new DinosaurRenderer<>(e, new DefaultModel<>(ModelLocations.TALPANAS)));
        event.registerEntityRenderer(UPEntities.GIGANTOPITHICUS.get(), e ->
                UPRenderUtils.createDinosaurRenderer(e, new DefaultModel<>(ModelLocations.GIGANTOPITHICUS))
                        .withItemHoldingLayer()
                        .build());

        event.registerEntityRenderer(UPEntities.BARINASUCHUS.get(), e ->
                UPRenderUtils.createTamableDinosaurRenderer(e, new DefaultModel<>(ModelLocations.BARINASUCHUS))
                        .withLayers(BARINASUCHUS_MODEL)
                        .build());

        event.registerEntityRenderer(UPEntities.PARACERATHERIUM.get(), e -> new DinosaurRenderer<>(e, new DefaultModel<>(ModelLocations.PARACERATHERIUM)));
        event.registerEntityRenderer(UPEntities.MEGATHERIUM.get(), e ->
                UPRenderUtils.createTamableDinosaurRenderer(e, new DefaultModel<>(ModelLocations.MEGATHERIUM))
                        .withLayers(MEGATHERIUM_MODEL)
                        .withSaddleLayer(MEGATHERIUM_SADDLE_OVERLAY)
                        .build());

        event.registerEntityRenderer(UPEntities.SMILODON.get(), e -> new DinosaurRenderer<>(e, new VariantModel<>(ModelLocations.SMILODON)));
        event.registerEntityRenderer(UPEntities.MAMMOTH.get(), e ->
                UPRenderUtils.createDinosaurRenderer(e, new DefaultModel<>(ModelLocations.MAMMOTH))
                        .withItemHoldingLayer()
                        .build());

        event.registerEntityRenderer(UPEntities.MEGALANIA.get(), MegalaniaRenderer::new);
        event.registerEntityRenderer(UPEntities.PALAEOPHIS.get(), e -> new  AquaticRenderer(e, new DefaultModel(ModelLocations.PALAEOPHIS_HEAD)));
        event.registerEntityRenderer(UPEntities.PALAEOPHIS_PART.get(), PalaeophisPartRender::new);
        event.registerEntityRenderer(UPEntities.SLUDGE.get(), e -> new LivingCutoutNoCullEntityRenderer<>(e, new DefaultModel<>(ModelLocations.SLUDGE)));


        event.registerEntityRenderer(UPEntities.ICEBERG_MAMMOTH.get(), e -> new LivingEntityRenderer<>(e, new IcebergMammothModel()));
        event.registerEntityRenderer(UPEntities.ICEBERG_SMILODON.get(), e -> new LivingEntityRenderer<>(e, new IcebergSmilodonModel()));

        event.registerEntityRenderer(UPEntities.BOOK_PALAEO.get(), e -> new LivingCutoutNoCullEntityRenderer<>(e, new DefaultModel<>(ModelLocations.BOOK_SNAKE)));

        event.registerEntityRenderer(UPEntities.KIMMER.get(), KimmeridgebrachypteraeschnidiumRenderer::new);

        // Unused 1.6 stuff
//        event.registerEntityRenderer(UPEntities.JAWLESS_FISH.get(), e -> new LivingCutoutNoCullEntityRenderer<>(e, new JawlessFishModel()));
//        event.registerEntityRenderer(UPEntities.OTAROCYON.get(), e ->
//                UPRenderUtils.createTamableDinosaurRenderer(e, new OtarocyonModel())
//                        .withLayers(OTAROCYCON_MODEL)
//                        .build());
//        event.registerEntityRenderer(UPEntities.LONGISQUAMA.get(),
//                e -> new TameableDinosaurCutoutNoCullRenderer<>(e, new LongisquamaModel()));
//        event.registerEntityRenderer(UPEntities.TARTUOSTEUS.get(), e -> new LivingCutoutNoCullEntityRenderer<>(e, new DefaultModel<>(ModelLocations.TARTUOSTEUS)));
//        event.registerEntityRenderer(UPEntities.PSITTACO.get(), e -> new LivingCutoutNoCullEntityRenderer<>(e, new PsittacosaurusModel()));
//        event.registerEntityRenderer(UPEntities.TANY.get(), e -> new LivingCutoutNoCullEntityRenderer<>(e, new TanystropheusModel()));
//        event.registerEntityRenderer(UPEntities.KAPROSUCHUS.get(), e ->
//                UPRenderUtils.createTamableDinosaurRenderer(e, new KaprosuchusModel())
//                        .withLayers(KAPROSUCHUS_MODEL)
//                        .build());
//        event.registerEntityRenderer(UPEntities.PSILOPTERUS.get(), e -> new LivingCutoutNoCullEntityRenderer<>(e, new PsilopterusModel()));
//        event.registerEntityRenderer(UPEntities.DIPLOCAULUS.get(), e -> new LivingCutoutNoCullEntityRenderer<>(e, new DefaultModel<>(ModelLocations.DIPLOCAULUS)));
//        event.registerEntityRenderer(UPEntities.HYNERPETON.get(), e -> new NoOverlayRenderer<>(e, new DefaultModel<>(ModelLocations.HYPERNETON)));
//        event.registerEntityRenderer(UPEntities.BALAUR.get(), e ->
//                UPRenderUtils.createTamableDinosaurRenderer(e, new BalaurModel())
//                        .withLayers(BALAUR_MODEL)
//                        .build());
//        event.registerEntityRenderer(UPEntities.OPHIODON.get(), e -> new LivingCutoutNoCullEntityRenderer<>(e, new OphiodonModel()));
//        event.registerEntityRenderer(UPEntities.PROTOSPHYRAENA.get(), e -> new LivingCutoutNoCullEntityRenderer<>(e, new ProtosphyraenaModel()));
//        event.registerEntityRenderer(UPEntities.ARCHELON.get(), e ->
//                UPRenderUtils.createTamableDinosaurRenderer(e, new ArchelonModel())
//                        .withLayers(ARCHELON_MODEL)
//                        .withSaddleLayer(ARCHELON_SADDLE_EMPTY_OVERLAY)
//                        .build());
//        event.registerEntityRenderer(UPEntities.LEEDSICHTHYS.get(), LeedsichthysRenderer::new);
//        event.registerEntityRenderer(UPEntities.LEEDS_PART.get(), LeedsichthysPartRender::new);
//        event.registerEntityRenderer(UPEntities.PTERODAUSTRO.get(), e -> new AgeableMobRenderer<>(e, new DefaultModel<EntityPterodaustro>(ModelLocations.PTERODAUSTRO)));
//        event.registerEntityRenderer(UPEntities.HYNERIA.get(), e -> new LivingCutoutNoCullEntityRenderer<>(e, new HyneriaModel()));

        //Plants
        event.registerEntityRenderer(UPEntities.FOXXI_SAPLING.get(), e -> new PlantEntityRenderer<>(e, new PlantModel("tall_plant", "plants/foxxi_sapling.png"), 1));
        event.registerEntityRenderer(UPEntities.HORSETAIL.get(), e -> new PlantEntityRenderer<>(e, new PlantModel("plant", "plants/horsetail.png"), 1));
        event.registerEntityRenderer(UPEntities.TALL_HORSETAIL.get(), e -> new PlantEntityRenderer<>(e, new PlantModel("tall_plant", "plants/tall_horsetail.png"), 1));
        event.registerEntityRenderer(UPEntities.LEEFRUCTUS.get(), e -> new PlantEntityRenderer<>(e, new PlantModel("plant", "plants/leefructus.png"), 1));
        event.registerEntityRenderer(UPEntities.BENNETTITALES.get(), e -> new PlantEntityRenderer<>(e, new PlantModel("plant", "plants/bennett.png"), 1));
        event.registerEntityRenderer(UPEntities.ARCHAEOSIGILARIA.get(), e -> new PlantEntityRenderer<>(e, new PlantModel("plant", "plants/archaeos.png"), 1));
        event.registerEntityRenderer(UPEntities.SARACENIA.get(), e -> new PlantEntityRenderer<>(e, new PlantModel("plant", "plants/sarracenia.png"), 1));
        event.registerEntityRenderer(UPEntities.TALL_SARACENIA.get(), e -> new PlantEntityRenderer<>(e, new PlantModel("tall_plant", "plants/tall_sarracenia.png"), 1));
        event.registerEntityRenderer(UPEntities.GINKGO_SAPLING.get(), e -> new PlantEntityRenderer<>(e, new PlantModel("plant", "plants/ginkgo_sapling.png"), 1));
        event.registerEntityRenderer(UPEntities.DRYO_SAPLING.get(), e -> new PlantEntityRenderer<>(e, new PlantModel("plant", "plants/dryo_sapling.png"), 1));
        event.registerEntityRenderer(UPEntities.CLATHRODICTYON.get(), e -> new PlantEntityRenderer<>(e, new PlantModel("plant", "plants/clathrodictyon.png"), 1));
        event.registerEntityRenderer(UPEntities.ARCHAEFRUCTUS.get(), e -> new PlantEntityRenderer<>(e, new PlantModel("plant", "plants/archaefructus.png"), 1));
        event.registerEntityRenderer(UPEntities.NELUMBITES.get(), e -> new PlantEntityRenderer<>(e, new PlantModel("flat_plant", "plants/nelumbites.png"), 1));
        event.registerEntityRenderer(UPEntities.QUEREUXIA.get(), e -> new PlantEntityRenderer<>(e, new PlantModel("quereuxia", "plants/quereuxia.png"), 0.3F));
        event.registerEntityRenderer(UPEntities.RAIGUENRAYUN.get(), e -> new PlantEntityRenderer<>(e, new PlantModel("tall_plant", "plants/raiguenrayun.png"), 0.8F));


        event.registerEntityRenderer(UPEntities.OPALESCENT_PEARL.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(UPEntities.OPALESCENT_SHURIKEN.get(), FlatMovingThrownItemRenderer::new);
//        event.registerEntityRenderer(UPEntities.T_JARATE.get(), TyrannosaurineJarateRenderer::new);
        event.registerEntityRenderer(UPEntities.THROWABLE_FALLING_BLOCK.get(), ThrowableFallingBlockRenderer::new);

        event.registerBlockEntityRenderer(UPBlockEntities.CULTIVATOR_BLOCK_ENTITY.get(), CultivatorBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(UPBlockEntities.INCUBATOR_BLOCK_ENTITY.get(), IncubatorBlockEntityRenderer::new);
//        EntityRenderers.register(UPEntities.PSITTACCO_ARROW.get(), PsittaccoArrowRenderer::new);
//        event.registerEntityRenderer(UPEntities.DINO_LAND_EGG.get(), DinosaurLandEggRenderer::new);


        try {
            ItemProperties.register(UPItems.TRIKE_SHIELD.get(), new ResourceLocation("blocking"), (stack, p_239421_1_, p_239421_2_, j) -> p_239421_2_ != null && p_239421_2_.isUsingItem() && p_239421_2_.getUseItem() == stack ? 1.0F : 0.0F);
            ItemProperties.register(UPItems.VELOCI_SHIELD.get(), new ResourceLocation("blocking"), (stack, p_239421_1_, p_239421_2_, j) -> p_239421_2_ != null && p_239421_2_.isUsingItem() && p_239421_2_.getUseItem() == stack ? 1.0F : 0.0F);
        } catch (Exception e) {
            UnusualPrehistory.LOGGER.warn("Could not load item models for weapons");
        }
    }


    @SubscribeEvent
    public static void registerBlockColor(RegisterColorHandlersEvent.Block event) {
        event.register((pState, pLevel, pPos, pTintIndex) -> {
            if (pLevel != null && pPos != null) {
                if (((FruitLootBoxEntity) pLevel.getBlockEntity(pPos)) != null) {
                    return ((FruitLootBoxEntity) pLevel.getBlockEntity(pPos)).getColor();
                }
            }
            return 111111;
        }, UPBlocks.FRUIT_LOOT_BOX.get());
    }

    @SubscribeEvent
    public static void registerItemColor(RegisterColorHandlersEvent.Item event) {
        event.register((pStack, pTintIndex) -> pStack.getOrCreateTag().getInt("color"), UPBlocks.FRUIT_LOOT_BOX.get());
    }


    @SubscribeEvent
    public static void registerGuiOverlays(RegisterGuiOverlaysEvent event) {
        event.registerAboveAll("amber_protection", AmberProtectionOverlay.HUD_AMBER_PROTECTION);
        event.registerAboveAll("tar_overlay", TarOverlay.TAR_OVERLAY);
    }


    public static KeyMapping roarKey;

    @SubscribeEvent
    public static void register(final RegisterKeyMappingsEvent event) {
        roarKey = create("attack_key", KeyEvent.VK_G);
        event.register(roarKey);
    }

    private static KeyMapping create(String name, int key) {
        return new KeyMapping("key." + UnusualPrehistory.MODID + "." + name, key, "key.category." + UnusualPrehistory.MODID);
    }

}
