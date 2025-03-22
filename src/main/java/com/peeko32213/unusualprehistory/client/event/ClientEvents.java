package com.peeko32213.unusualprehistory.client.event;

import com.peeko32213.unusualprehistory.core.other.ClientProxy;
import com.peeko32213.unusualprehistory.core.UnusualPrehistory;
import com.peeko32213.unusualprehistory.client.model.CustomAnimationsModel;
import com.peeko32213.unusualprehistory.client.model.DefaultModel;
import com.peeko32213.unusualprehistory.client.model.ModelLocations;
import com.peeko32213.unusualprehistory.client.model.entity.egg.PrehistoricEggModel;
import com.peeko32213.unusualprehistory.client.model.entity.skeleton.TriceratopsSkeletonModel;
import com.peeko32213.unusualprehistory.client.model.entity.skeleton.TyrannosaurusSkeletonModel;
import com.peeko32213.unusualprehistory.client.model.entity.prehistoric.*;
import com.peeko32213.unusualprehistory.client.model.entity.prehistoric.aquatic.*;
import com.peeko32213.unusualprehistory.client.model.entity.prehistoric.flying.TelecrexModel;
import com.peeko32213.unusualprehistory.client.model.entity.prehistoric.semi_aquatic.*;
import com.peeko32213.unusualprehistory.client.model.entity.iceberg.IcebergMammothModel;
import com.peeko32213.unusualprehistory.client.model.entity.iceberg.IcebergSmilodonModel;
import com.peeko32213.unusualprehistory.client.model.plant.PlantModel;
import com.peeko32213.unusualprehistory.client.overlay.AmberProtectionOverlay;
import com.peeko32213.unusualprehistory.client.particles.ElectricAttackParticle;
import com.peeko32213.unusualprehistory.client.particles.ElectricOrbitParticle;
import com.peeko32213.unusualprehistory.client.particles.TarBubbleParticle;
import com.peeko32213.unusualprehistory.client.render.UPRenderUtils;
import com.peeko32213.unusualprehistory.client.render.block.CultivatorBlockEntityRenderer;
import com.peeko32213.unusualprehistory.client.render.block.IncubatorBlockEntityRenderer;
import com.peeko32213.unusualprehistory.client.render.block.PlantEntityRenderer;
import com.peeko32213.unusualprehistory.client.render.egg.PrehistoricEggRenderer;
import com.peeko32213.unusualprehistory.client.render.prehistoric.*;
import com.peeko32213.unusualprehistory.client.render.projectile.*;
import com.peeko32213.unusualprehistory.client.render.projectile.FlatMovingThrownItemRenderer;
import com.peeko32213.unusualprehistory.client.render.tool.UPBoatRenderer;
import com.peeko32213.unusualprehistory.client.screen.AnalyzerScreen;
import com.peeko32213.unusualprehistory.client.screen.CultivatorScreen;
import com.peeko32213.unusualprehistory.client.screen.DNAFridgeScreen;
import com.peeko32213.unusualprehistory.common.block.entity.FruitLootBoxEntity;
import com.peeko32213.unusualprehistory.common.entity.UPBoatEntity;
import com.peeko32213.unusualprehistory.core.registry.*;
import com.peeko32213.unusualprehistory.core.registry.UPBlockEntities;
import com.peeko32213.unusualprehistory.core.registry.UPBlockSetType;
import com.peeko32213.unusualprehistory.core.registry.UPBlocks;
import com.peeko32213.unusualprehistory.core.registry.UPEntities;
import com.peeko32213.unusualprehistory.core.registry.UPItemProperties;
import com.peeko32213.unusualprehistory.core.registry.UPItems;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.ChestBoatModel;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.blockentity.HangingSignRenderer;
import net.minecraft.client.renderer.blockentity.SignRenderer;
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
import java.util.Arrays;
import java.util.Objects;

@Mod.EventBusSubscriber(modid = UnusualPrehistory.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class ClientEvents {

    @SubscribeEvent
    public static void init(final FMLClientSetupEvent event) {
        event.enqueueWork(UPItemProperties::addItemProperties);
        BlockEntityRenderers.register(UPBlockEntities.UP_SIGN.get(), SignRenderer::new);
        BlockEntityRenderers.register(UPBlockEntities.UP_HANGING_SIGN.get(), HangingSignRenderer::new);
        event.enqueueWork(() -> {
            Sheets.addWoodType(UPBlockSetType.DRYO);
            Sheets.addWoodType(UPBlockSetType.FOXII);
            Sheets.addWoodType(UPBlockSetType.GINKGO);
            Sheets.addWoodType(UPBlockSetType.PETRIFIED);
            Sheets.addWoodType(UPBlockSetType.ZULOAGAE);
        });
        ClientProxy.setupBlockRenders();
    }

    @SubscribeEvent
    public static void onClientSetup(final FMLClientSetupEvent event) {
        UnusualPrehistory.checkForGeckoLib();

        MenuScreens.register(UPMenuTypes.ANALYZER_MENU.get(), AnalyzerScreen::new);
        MenuScreens.register(UPMenuTypes.CULTIVATOR_MENU.get(), CultivatorScreen::new);
        MenuScreens.register(UPMenuTypes.DNA_FRIDGE_MENU.get(), DNAFridgeScreen::new);

        WoodType.register(UPBlockSetType.DRYO);
        WoodType.register(UPBlockSetType.FOXII);
        WoodType.register(UPBlockSetType.GINKGO);
        WoodType.register(UPBlockSetType.PETRIFIED);
        WoodType.register(UPBlockSetType.ZULOAGAE);

        Sheets.addWoodType(UPBlockSetType.DRYO);
        Sheets.addWoodType(UPBlockSetType.FOXII);
        Sheets.addWoodType(UPBlockSetType.GINKGO);
        Sheets.addWoodType(UPBlockSetType.PETRIFIED);
        Sheets.addWoodType(UPBlockSetType.ZULOAGAE);
    }

    @SubscribeEvent
    public static void registerBER(EntityRenderersEvent.RegisterRenderers event){
        BlockEntityRenderers.register(UPBlockEntities.UP_SIGN.get(), SignRenderer::new);
        BlockEntityRenderers.register(UPBlockEntities.UP_HANGING_SIGN.get(), HangingSignRenderer::new);
        BlockEntityRenderers.register(UPBlockEntities.CULTIVATOR_BLOCK_ENTITY.get(), CultivatorBlockEntityRenderer::new);
        BlockEntityRenderers.register(UPBlockEntities.INCUBATOR_BLOCK_ENTITY.get(), IncubatorBlockEntityRenderer::new);
    }

    @SubscribeEvent
    public static void registerParticleTypes(RegisterParticleProvidersEvent event){
        event.registerSpriteSet(UPParticles.TAR_BUBBLE.get(), TarBubbleParticle.Provider::new);
        event.registerSpecial(UPParticles.ELECTRIC_ORBIT.get(), new ElectricOrbitParticle.PillarFactory());
        event.registerSpecial(UPParticles.ELECTRIC_ATTACK.get(), new ElectricAttackParticle.ElectricAttackFactory());
    }

    private static final ResourceLocation MEGATHERIUM_SADDLE_OVERLAY = new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/megatherium/megatherium_saddled.png");
    private static final ResourceLocation MEGATHERIUM_MODEL = new ResourceLocation(UnusualPrehistory.MODID, "geo/megatherium/megatherium.geo.json");

    private static final ResourceLocation BEELZE_SADDLE_OVERLAY = new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/beelzebufo_saddle.png");
    private static final ResourceLocation BEELZE_MODEL = new ResourceLocation(UnusualPrehistory.MODID, "geo/beelzebufo.geo.json");

    private static final ResourceLocation KAPROSUCHUS_MODEL = new ResourceLocation(UnusualPrehistory.MODID, "geo/kaprosuchus.geo.json");

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {

        // Paleo mobs
        event.registerEntityRenderer(UPEntities.AMMON.get(), e -> new LivingCutoutNoCullEntityRenderer<>(e, new AmmoniteModel()));
        event.registerEntityRenderer(UPEntities.COTY.get(), e -> new StatedPrehistoricRenderer<>(e, new CotylorhynchusModel()));
        event.registerEntityRenderer(UPEntities.DIPLOCAULUS.get(), e -> new StatedPrehistoricRenderer<>(e, new DiplocaulusModel()));
        event.registerEntityRenderer(UPEntities.DUNK.get(), e -> new StatedPrehistoricAquaticRenderer<>(e, new DunkleosteusModel()));
        event.registerEntityRenderer(UPEntities.EDAPHOSAURUS.get(), e -> new StatedPrehistoricRenderer<>(e, new EdaphosaurusModel()));
        event.registerEntityRenderer(UPEntities.ESTEMMENOSUCHUS.get(), e -> new LivingCutoutNoCullEntityRenderer<>(e, new EstemmenosuchusModel()));
        event.registerEntityRenderer(UPEntities.HYNERIA.get(), e -> new LivingCutoutNoCullEntityRenderer<>(e, new HyneriaModel()));
        event.registerEntityRenderer(UPEntities.HYNERPETON.get(), e -> new PrehistoricRenderer<>(e, new HynerpetonModel()));
        event.registerEntityRenderer(UPEntities.JAWLESS_FISH.get(), e -> new LivingCutoutNoCullEntityRenderer<>(e, new JawlessFishModel()));
        event.registerEntityRenderer(UPEntities.PANACANTHOCARIS.get(), e -> new LivingCutoutNoCullEntityRenderer<>(e, new PanacanthocarisModel()));
        event.registerEntityRenderer(UPEntities.PTERYGOTUS.get(), e -> new LivingCutoutNoCullEntityRenderer<>(e, new PterygotusModel()));
        event.registerEntityRenderer(UPEntities.SCAU.get(), e -> new LivingEntityFishRenderer<>(e, new ScaumenaciaModel()));
        event.registerEntityRenderer(UPEntities.STETHACANTHUS.get(), e -> new LivingEntityFishRenderer<>(e, new StethacanthusModel()));
        event.registerEntityRenderer(UPEntities.TARTUOSTEUS.get(), e -> new LivingCutoutNoCullEntityRenderer<>(e, new DefaultModel<>(ModelLocations.TARTUOSTEUS)));

        // Meso mobs
        event.registerEntityRenderer(UPEntities.ANTARCO.get(), e -> new PrehistoricRenderer<>(e, new CustomAnimationsModel<>(ModelLocations.ANTARCTOPELTA)));
        event.registerEntityRenderer(UPEntities.ANURO.get(), e -> new AgeableMobRenderer<>(e, new DefaultModel<>(ModelLocations.ANURO)));
        event.registerEntityRenderer(UPEntities.ARCHELON.get(), e -> new StatedPrehistoricRenderer<>(e, new ArchelonModel()));
        event.registerEntityRenderer(UPEntities.AUSTRO.get(), e -> new PrehistoricRenderer<>(e, new CustomAnimationsModel<>(ModelLocations.AUSTRORAPTOR)));
        event.registerEntityRenderer(UPEntities.BALAUR.get(), e -> new StatedPrehistoricRenderer<>(e, new BalaurModel()));
        event.registerEntityRenderer(UPEntities.BEELZ.get(), e -> UPRenderUtils.createDinosaurRenderer(e, new DefaultModel<>(ModelLocations.BEELZEBUFO)).withLayers(BEELZE_MODEL).withSaddleLayer(BEELZE_SADDLE_OVERLAY).build());
        event.registerEntityRenderer(UPEntities.BEELZ.get(), e -> new PrehistoricRenderer<>(e, new BeelzebufoModel()));
        event.registerEntityRenderer(UPEntities.BEELZE_TADPOLE.get(), e -> new LivingCutoutNoCullEntityRenderer<>(e, new DefaultModel<>(ModelLocations.BABY_BEELZEBUFO)));
        event.registerEntityRenderer(UPEntities.BRACHI.get(), e -> new LivingCutoutNoCullEntityRenderer<>(e, new BrachiosaurusModel()));
       // event.registerEntityRenderer(UPEntities.ERYON.get(), e -> new PrehistoricRenderer<>(e, new VariantModel<>(ModelLocations.ERYON)));
        event.registerEntityRenderer(UPEntities.GLOBIDENS.get(), e -> new LivingCutoutNoCullEntityRenderer<>(e, new GlobidensModel()));
        event.registerEntityRenderer(UPEntities.GUANLINGSAURUS.get(), e -> new LivingCutoutNoCullEntityRenderer<>(e, new GuanlingsaurusModel()));
        event.registerEntityRenderer(UPEntities.HWACHA.get(), e -> new StatedPrehistoricRenderer<>(e, new HwachavenatorModel()));
        event.registerEntityRenderer(UPEntities.KAPROSUCHUS.get(), e -> UPRenderUtils.createTamableDinosaurRenderer(e, new KaprosuchusModel()).withLayers(KAPROSUCHUS_MODEL).build());
        event.registerEntityRenderer(UPEntities.KENTRO.get(), e -> new PrehistoricRenderer<>(e, new KentrosaurusModel()));
        event.registerEntityRenderer(UPEntities.KIMMER.get(), KimmeridgebrachypteraeschnidiumRenderer::new);
        event.registerEntityRenderer(UPEntities.LEEDSICHTHYS.get(), LeedsichthysRenderer::new);
        event.registerEntityRenderer(UPEntities.LEEDS_PART.get(), LeedsichthysPartRender::new);
        event.registerEntityRenderer(UPEntities.LONGISQUAMA.get(), e -> new TamableCutoutNoCullPrehistoricRenderer<>(e, new LongisquamaModel()));
        event.registerEntityRenderer(UPEntities.MAJUNGA.get(), e -> new PrehistoricRenderer<>(e, new MajungasaurusModel()));
        event.registerEntityRenderer(UPEntities.NYCTORAPTOR.get(), e -> new StatedPrehistoricRenderer<>(e, new NyctoraptorModel()));
        event.registerEntityRenderer(UPEntities.OVIRAPTOR.get(), e -> new StatedPrehistoricRenderer<>(e, new OviraptorModel()));
        event.registerEntityRenderer(UPEntities.PACHY.get(), e -> new PrehistoricRenderer<>(e, new PachycephalosaurusModel()));
        event.registerEntityRenderer(UPEntities.PROSCINETES.get(), e -> new LivingCutoutNoCullEntityRenderer<>(e, new ProscinetesModel()));
        event.registerEntityRenderer(UPEntities.PROTOSPHYRAENA.get(), e -> new LivingCutoutNoCullEntityRenderer<>(e, new ProtosphyraenaModel()));
        event.registerEntityRenderer(UPEntities.PSITTACO.get(), e -> new LivingCutoutNoCullEntityRenderer<>(e, new PsittacosaurusModel()));
        event.registerEntityRenderer(UPEntities.PTERODAUSTRO.get(), e -> new AgeableMobRenderer<>(e, new DefaultModel<>(ModelLocations.PTERODAUSTRO)));
        event.registerEntityRenderer(UPEntities.SCHLUMBERGERITES.get(), e -> new LivingCutoutNoCullEntityRenderer<>(e, new SchlumbergeritesModel()));
        event.registerEntityRenderer(UPEntities.TANY.get(), e -> new LivingCutoutNoCullEntityRenderer<>(e, new TanystropheusModel()));
        event.registerEntityRenderer(UPEntities.TRICERATOPS.get(), e -> new StatedPrehistoricRenderer<>(e, new TriceratopsModel()));
        event.registerEntityRenderer(UPEntities.TYRANNOSAURUS.get(), e -> new LivingCutoutNoCullEntityRenderer<>(e, new TyrannosaurusModel()));
        event.registerEntityRenderer(UPEntities.ULUG.get(), e -> new StatedPrehistoricRenderer<>(e, new UlughbegsaurusModel()));
        event.registerEntityRenderer(UPEntities.VELOCIRAPTOR.get(), e -> new StatedPrehistoricRenderer<>(e, new VelociraptorModel()));
        event.registerEntityRenderer(UPEntities.XIPH.get(), e -> new LivingCutoutNoCullEntityRenderer<>(e, new XiphactinusModel()));

        // Ceno mobs
        event.registerEntityRenderer(UPEntities.BARINASUCHUS.get(), e -> new StatedPrehistoricRenderer<>(e, new BarinasuchusModel()));
        event.registerEntityRenderer(UPEntities.CORONODON.get(), e -> new LivingCutoutNoCullEntityRenderer<>(e, new CoronodonModel()));
        event.registerEntityRenderer(UPEntities.GIGANTOPITHICUS.get(), e -> new PrehistoricRenderer<>(e, new GigantopithicusModel()));
        event.registerEntityRenderer(UPEntities.MAMMOTH.get(), e -> new PrehistoricRenderer<>(e, new MammothModel()));
        event.registerEntityRenderer(UPEntities.MEGALAMPRIS.get(), e -> new StatedPrehistoricRenderer<>(e, new MegalamprisModel()));
        event.registerEntityRenderer(UPEntities.MEGALANIA.get(), e -> new PrehistoricRenderer<>(e, new MegalaniaModel()));
        event.registerEntityRenderer(UPEntities.MEGATHERIUM.get(), e -> UPRenderUtils.createTamableDinosaurRenderer(e, new MegatheriumModel()).withLayers(MEGATHERIUM_MODEL).withSaddleLayer(MEGATHERIUM_SADDLE_OVERLAY).build());
        event.registerEntityRenderer(UPEntities.OPHIODON.get(), e -> new LivingCutoutNoCullEntityRenderer<>(e, new OphiodonModel()));
        event.registerEntityRenderer(UPEntities.OTAROCYON.get(), e -> new LivingCutoutNoCullEntityRenderer<>(e, new OtarocyonModel()));
        event.registerEntityRenderer(UPEntities.PALAEOPHIS.get(), e -> new LivingCutoutNoCullEntityRenderer<>(e, new PalaeophisModel()));
        event.registerEntityRenderer(UPEntities.PALAEOPHIS_PART.get(), PalaeophisPartRender::new);
        event.registerEntityRenderer(UPEntities.BABY_PALAEO.get(), e -> new LivingCutoutNoCullEntityRenderer<>(e, new PalaeophisHatchlingModel()));
        event.registerEntityRenderer(UPEntities.PARACERATHERIUM.get(), e -> new PrehistoricRenderer<>(e, new ParaceratheriumModel()));
        event.registerEntityRenderer(UPEntities.PSILOPTERUS.get(), e -> new LivingCutoutNoCullEntityRenderer<>(e, new PsilopterusModel()));
        event.registerEntityRenderer(UPEntities.SMILODON.get(), e -> new StatedPrehistoricRenderer<>(e, new SmilodonModel()));
        event.registerEntityRenderer(UPEntities.TALPANAS.get(), e -> new PrehistoricRenderer<>(e, new DefaultModel<>(ModelLocations.TALPANAS)));
        event.registerEntityRenderer(UPEntities.TELECREX.get(), e -> new LivingCutoutNoCullEntityRenderer<>(e, new TelecrexModel()));
        event.registerEntityRenderer(UPEntities.TITANONARKE.get(), e -> new StatedPrehistoricRenderer<>(e, new TitanonarkeModel()));

        // Misc mobs
        event.registerEntityRenderer(UPEntities.ENCRUSTED.get(), e -> new LivingCutoutNoCullEntityRenderer<>(e, new DefaultModel<>(ModelLocations.ENCRUSTED)));
        event.registerEntityRenderer(UPEntities.SLUDGE.get(), e -> new LivingCutoutNoCullEntityRenderer<>(e, new DefaultModel<>(ModelLocations.SLUDGE)));

        // Fossils
        event.registerEntityRenderer(UPEntities.TYRANNO_SKELETON.get(), e -> new LivingEntityRenderer<>(e, new TyrannosaurusSkeletonModel()));
        event.registerEntityRenderer(UPEntities.TRIKE_SKELETON.get(), e -> new LivingEntityRenderer<>(e, new TriceratopsSkeletonModel()));

        // Non-living mobs
        event.registerEntityRenderer(UPEntities.ICEBERG_MAMMOTH.get(), e -> new LivingEntityRenderer<>(e, new IcebergMammothModel()));
        event.registerEntityRenderer(UPEntities.ICEBERG_SMILODON.get(), e -> new LivingEntityRenderer<>(e, new IcebergSmilodonModel()));
        event.registerEntityRenderer(UPEntities.BOOK_PALAEO.get(), e -> new LivingCutoutNoCullEntityRenderer<>(e, new DefaultModel<>(ModelLocations.BOOK_SNAKE)));

        // Misc entities
        event.registerEntityRenderer(UPEntities.AMBER_SHOT.get(), AmberShotRenderer::new);
        event.registerEntityRenderer(UPEntities.BOAT.get(), ctx -> new UPBoatRenderer(ctx, false));
        event.registerEntityRenderer(UPEntities.CHEST_BOAT.get(), ctx -> new UPBoatRenderer(ctx, true));
        event.registerEntityRenderer(UPEntities.HWACHA_SPIKE.get(), HwachaSpikeRenderer::new);
        event.registerEntityRenderer(UPEntities.RABIES_FLASK.get(), RabiesFlaskRenderer::new);
        event.registerEntityRenderer(UPEntities.OPALESCENT_PEARL.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(UPEntities.OPALESCENT_SHURIKEN.get(), FlatMovingThrownItemRenderer::new);
        event.registerEntityRenderer(UPEntities.PSITTACCO_ARROW.get(), PsittaccoArrowRenderer::new);
        event.registerEntityRenderer(UPEntities.THROWABLE_FALLING_BLOCK.get(), ThrowableFallingBlockRenderer::new);

        // Eggs
        event.registerEntityRenderer(UPEntities.PREHISTORIC_EGG.get(), e -> new PrehistoricEggRenderer(e, new PrehistoricEggModel()));

        // Plants
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
                if (pLevel.getBlockEntity(pPos) != null) {
                    return ((FruitLootBoxEntity) Objects.requireNonNull(pLevel.getBlockEntity(pPos))).getColor();
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
    }

    public static KeyMapping roarKey;

    @SubscribeEvent
    public static void register(final RegisterKeyMappingsEvent event) {
        roarKey = create();
        event.register(roarKey);
    }

    private static KeyMapping create() {
        return new KeyMapping("key." + UnusualPrehistory.MODID + "." + "attack_key", KeyEvent.VK_G, "key.category." + UnusualPrehistory.MODID);
    }

    @SubscribeEvent
    public static void registerEntityModelLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        Arrays.stream(UPBoatEntity.BoatType.values()).forEach(type -> {
            event.registerLayerDefinition(UPModelLayers.createBoat(type), BoatModel::createBodyModel);
            event.registerLayerDefinition(UPModelLayers.createChestBoat(type), ChestBoatModel::createBodyModel);
        });
    }
}

