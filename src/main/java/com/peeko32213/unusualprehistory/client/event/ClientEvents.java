package com.peeko32213.unusualprehistory.client.event;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.client.model.DefaultModel;
import com.peeko32213.unusualprehistory.client.model.ModelLocations;
import com.peeko32213.unusualprehistory.client.model.entity.egg.PrehistoricEggModel;
import com.peeko32213.unusualprehistory.client.model.entity.skeleton.TriceratopsSkeletonModel;
import com.peeko32213.unusualprehistory.client.model.entity.skeleton.TyrannosaurusSkeletonModel;
import com.peeko32213.unusualprehistory.client.model.entity.prehistoric.*;
import com.peeko32213.unusualprehistory.client.model.entity.prehistoric.aquatic.*;
import com.peeko32213.unusualprehistory.client.model.entity.prehistoric.semi_aquatic.*;
import com.peeko32213.unusualprehistory.client.model.entity.iceberg.IcebergMammothModel;
import com.peeko32213.unusualprehistory.client.model.entity.iceberg.IcebergSmilodonModel;
import com.peeko32213.unusualprehistory.client.model.entity.skeleton.UnicornSkeletonModel;
import com.peeko32213.unusualprehistory.client.model.plant.PlantModel;
import com.peeko32213.unusualprehistory.client.overlay.AmberProtectionOverlay;
import com.peeko32213.unusualprehistory.client.particles.ElectricAttackParticle;
import com.peeko32213.unusualprehistory.client.particles.ElectricOrbitParticle;
import com.peeko32213.unusualprehistory.client.particles.TarBubbleParticle;
import com.peeko32213.unusualprehistory.client.render.block.CultivatorBlockEntityRenderer;
import com.peeko32213.unusualprehistory.client.render.block.IncubatorBlockEntityRenderer;
import com.peeko32213.unusualprehistory.client.render.block.PlantEntityRenderer;
import com.peeko32213.unusualprehistory.client.render.egg.PrehistoricEggRenderer;
import com.peeko32213.unusualprehistory.client.render.prehistoric.*;
import com.peeko32213.unusualprehistory.client.render.projectile.*;
import com.peeko32213.unusualprehistory.client.render.projectile.OpalescentShurikenRenderer;
import com.peeko32213.unusualprehistory.client.screen.AnalyzerScreen;
import com.peeko32213.unusualprehistory.client.screen.CultivatorScreen;
import com.peeko32213.unusualprehistory.client.screen.DNAFridgeScreen;
import com.peeko32213.unusualprehistory.common.block.entity.FruitLootBoxEntity;
import com.peeko32213.unusualprehistory.core.registry.*;
import com.peeko32213.unusualprehistory.core.registry.blocks.UPBlockEntities;
import com.peeko32213.unusualprehistory.core.registry.blocks.UPBlocks;
import com.peeko32213.unusualprehistory.core.registry.entities.UPEntities;
import com.peeko32213.unusualprehistory.core.registry.items.UPItemProperties;
import com.peeko32213.unusualprehistory.core.registry.items.UPItems;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.DisplayRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.*;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.awt.event.KeyEvent;
import java.util.Objects;

@Mod.EventBusSubscriber(modid = UnusualPrehistory.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class ClientEvents {

    @SubscribeEvent
    public static void init(final FMLClientSetupEvent event) {
        event.enqueueWork(UPItemProperties::addItemProperties);
    }

    @SubscribeEvent
    public static void onClientSetup(final FMLClientSetupEvent event) {
        MenuScreens.register(UPMenuTypes.ANALYZER_MENU.get(), AnalyzerScreen::new);
        MenuScreens.register(UPMenuTypes.CULTIVATOR_MENU.get(), CultivatorScreen::new);
        MenuScreens.register(UPMenuTypes.DNA_FRIDGE_MENU.get(), DNAFridgeScreen::new);
    }

    @SubscribeEvent
    public static void registerBER(EntityRenderersEvent.RegisterRenderers event){
        BlockEntityRenderers.register(UPBlockEntities.CULTIVATOR_BLOCK_ENTITY.get(), CultivatorBlockEntityRenderer::new);
        BlockEntityRenderers.register(UPBlockEntities.INCUBATOR_BLOCK_ENTITY.get(), IncubatorBlockEntityRenderer::new);
    }

    @SubscribeEvent
    public static void registerParticleTypes(RegisterParticleProvidersEvent event){
        event.registerSpriteSet(UPParticles.TAR_BUBBLE.get(), TarBubbleParticle.Provider::new);
        event.registerSpecial(UPParticles.ELECTRIC_ORBIT.get(), new ElectricOrbitParticle.PillarFactory());
        event.registerSpecial(UPParticles.ELECTRIC_ATTACK.get(), new ElectricAttackParticle.ElectricAttackFactory());
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {

        // Paleo mobs
        event.registerEntityRenderer(UPEntities.AMMON.get(), e -> new LivingCutoutNoCullEntityRenderer<>(e, new AmmoniteModel()));
        event.registerEntityRenderer(UPEntities.COTY.get(), e -> new PrehistoricRenderer<>(e, new CotylorhynchusModel()));
        event.registerEntityRenderer(UPEntities.DIPLOCAULUS.get(), e -> new PrehistoricRenderer<>(e, new DiplocaulusModel()));
        event.registerEntityRenderer(UPEntities.DUNK.get(), e -> new PrehistoricAquaticRenderer<>(e, new DunkleosteusModel()));
        event.registerEntityRenderer(UPEntities.EDAPHOSAURUS.get(), e -> new PrehistoricRenderer<>(e, new EdaphosaurusModel()));
        event.registerEntityRenderer(UPEntities.ESTEMMENOSUCHUS.get(), e -> new LivingCutoutNoCullEntityRenderer<>(e, new EstemmenosuchusModel()));
        event.registerEntityRenderer(UPEntities.HYNERIA.get(), e -> new LivingCutoutNoCullEntityRenderer<>(e, new HyneriaModel()));
        event.registerEntityRenderer(UPEntities.HYNERPETON.get(), e -> new AgeableMobRenderer<>(e, new HynerpetonModel()));
        event.registerEntityRenderer(UPEntities.JAWLESS_FISH.get(), e -> new LivingCutoutNoCullEntityRenderer<>(e, new JawlessFishModel()));
        event.registerEntityRenderer(UPEntities.PANACANTHOCARIS.get(), e -> new LivingCutoutNoCullEntityRenderer<>(e, new PanacanthocarisModel()));
        event.registerEntityRenderer(UPEntities.PTERYGOTUS.get(), e -> new AgeableMobRenderer<>(e, new PterygotusModel()));
        event.registerEntityRenderer(UPEntities.SCAU.get(), e -> new LivingEntityFishRenderer<>(e, new ScaumenaciaModel()));
        event.registerEntityRenderer(UPEntities.STETHACANTHUS.get(), e -> new LivingEntityRenderer<>(e, new StethacanthusModel()));
        event.registerEntityRenderer(UPEntities.TARTUOSTEUS.get(), e -> new AgeableMobRenderer<>(e, new DefaultModel<>(ModelLocations.TARTUOSTEUS)));

        // Meso mobs
        event.registerEntityRenderer(UPEntities.ANTARCO.get(), e -> new AgeableMobRenderer<>(e, new DefaultModel<>(ModelLocations.ANTARCTOPELTA)));
        event.registerEntityRenderer(UPEntities.ANURO.get(), e -> new AgeableMobRenderer<>(e, new DefaultModel<>(ModelLocations.ANURO)));
        event.registerEntityRenderer(UPEntities.ARCHELON.get(), e -> new PrehistoricRenderer<>(e, new ArchelonModel()));
        event.registerEntityRenderer(UPEntities.AUSTRO.get(), e -> new AgeableMobRenderer<>(e, new DefaultModel<>(ModelLocations.AUSTRORAPTOR)));
        event.registerEntityRenderer(UPEntities.BALAUR.get(), e -> new PrehistoricRenderer<>(e, new BalaurModel()));
        event.registerEntityRenderer(UPEntities.BEELZ.get(), e -> new AgeableMobRenderer<>(e, new BeelzebufoModel()));
        event.registerEntityRenderer(UPEntities.BEELZE_TADPOLE.get(), e -> new LivingCutoutNoCullEntityRenderer<>(e, new DefaultModel<>(ModelLocations.BABY_BEELZEBUFO)));
        event.registerEntityRenderer(UPEntities.BRACHI.get(), e -> new LivingCutoutNoCullEntityRenderer<>(e, new BrachiosaurusModel()));
        event.registerEntityRenderer(UPEntities.ERYON.get(), e -> new AgeableMobRenderer<>(e, new DefaultModel<>(ModelLocations.ERYON)));
        event.registerEntityRenderer(UPEntities.GLOBIDENS.get(), e -> new LivingCutoutNoCullEntityRenderer<>(e, new GlobidensModel()));
        event.registerEntityRenderer(UPEntities.HWACHA.get(), e -> new PrehistoricRenderer<>(e, new HwachavenatorModel()));
        event.registerEntityRenderer(UPEntities.KAPROSUCHUS.get(), e -> new AgeableMobRenderer<>(e, new KaprosuchusModel()));
        event.registerEntityRenderer(UPEntities.KENTRO.get(), e -> new AgeableMobRenderer<>(e, new KentrosaurusModel()));
        event.registerEntityRenderer(UPEntities.KIMMER.get(), KimmeridgebrachypteraeschnidiumRenderer::new);
        event.registerEntityRenderer(UPEntities.LEEDSICHTHYS.get(), e -> new PrehistoricRenderer<>(e, new LeedsichthysModel()));
        event.registerEntityRenderer(UPEntities.LONGISQUAMA.get(), e -> new AgeableMobRenderer<>(e, new LongisquamaModel()));
        event.registerEntityRenderer(UPEntities.MAJUNGA.get(), e -> new AgeableMobRenderer<>(e, new MajungasaurusModel()));
        event.registerEntityRenderer(UPEntities.OVIRAPTOR.get(), e -> new PrehistoricRenderer<>(e, new OviraptorModel()));
        event.registerEntityRenderer(UPEntities.PACHY.get(), e -> new AgeableMobRenderer<>(e, new PachycephalosaurusModel()));
        event.registerEntityRenderer(UPEntities.PROTOSPHYRAENA.get(), e -> new LivingCutoutNoCullEntityRenderer<>(e, new ProtosphyraenaModel()));
        event.registerEntityRenderer(UPEntities.PSITTACO.get(), e -> new LivingCutoutNoCullEntityRenderer<>(e, new PsittacosaurusModel()));
        event.registerEntityRenderer(UPEntities.PTERODAUSTRO.get(), e -> new AgeableMobRenderer<>(e, new DefaultModel<>(ModelLocations.PTERODAUSTRO)));
        event.registerEntityRenderer(UPEntities.TANY.get(), e -> new LivingCutoutNoCullEntityRenderer<>(e, new TanystropheusModel()));
        event.registerEntityRenderer(UPEntities.TRICERATOPS.get(), e -> new PrehistoricRenderer<>(e, new TriceratopsModel()));
        event.registerEntityRenderer(UPEntities.TYRANNOSAURUS.get(), e -> new AgeableMobRenderer<>(e, new TyrannosaurusModel()));
        event.registerEntityRenderer(UPEntities.ULUG.get(), e -> new PrehistoricRenderer<>(e, new UlughbegsaurusModel()));
        event.registerEntityRenderer(UPEntities.VELOCIRAPTOR.get(), e -> new PrehistoricRenderer<>(e, new VelociraptorModel()));
        event.registerEntityRenderer(UPEntities.XIPH.get(), e -> new LivingCutoutNoCullEntityRenderer<>(e, new XiphactinusModel()));

        // Ceno mobs
        event.registerEntityRenderer(UPEntities.BARINASUCHUS.get(), e -> new PrehistoricRenderer<>(e, new BarinasuchusModel()));
        event.registerEntityRenderer(UPEntities.GIGANTOPITHICUS.get(), e -> new AgeableMobRenderer<>(e, new GigantopithicusModel()));
        event.registerEntityRenderer(UPEntities.MAMMOTH.get(), e -> new AgeableMobRenderer<>(e, new MammothModel()));
        event.registerEntityRenderer(UPEntities.MEGALANIA.get(), e -> new AgeableMobRenderer<>(e, new MegalaniaModel()));
        event.registerEntityRenderer(UPEntities.MEGATHERIUM.get(), e -> new PrehistoricRenderer<>(e, new MegatheriumModel()));
        event.registerEntityRenderer(UPEntities.OPHIODON.get(), e -> new LivingCutoutNoCullEntityRenderer<>(e, new OphiodonModel()));
        event.registerEntityRenderer(UPEntities.OTAROCYON.get(), e -> new LivingCutoutNoCullEntityRenderer<>(e, new OtarocyonModel()));
        event.registerEntityRenderer(UPEntities.PALAEOPHIS.get(), e -> new LivingCutoutNoCullEntityRenderer<>(e, new PalaeophisModel()));
        event.registerEntityRenderer(UPEntities.PALAEOPHIS_PART.get(), PalaeophisPartRender::new);
        event.registerEntityRenderer(UPEntities.BABY_PALAEO.get(), e -> new LivingCutoutNoCullEntityRenderer<>(e, new PalaeophisHatchlingModel()));
        event.registerEntityRenderer(UPEntities.PARACERATHERIUM.get(), e -> new AgeableMobRenderer<>(e, new ParaceratheriumModel()));
        event.registerEntityRenderer(UPEntities.PSILOPTERUS.get(), e -> new LivingCutoutNoCullEntityRenderer<>(e, new PsilopterusModel()));
        event.registerEntityRenderer(UPEntities.SMILODON.get(), e -> new PrehistoricRenderer<>(e, new SmilodonModel()));
        event.registerEntityRenderer(UPEntities.TALPANAS.get(), e -> new AgeableMobRenderer<>(e, new DefaultModel<>(ModelLocations.TALPANAS)));
        event.registerEntityRenderer(UPEntities.TELECREX.get(), TelecrexRenderer::new);
        event.registerEntityRenderer(UPEntities.UNICORN.get(), e -> new AgeableMobRenderer<>(e, new UnicornModel()));

        // Monsters
        event.registerEntityRenderer(UPEntities.ENCRUSTED.get(), e -> new AgeableMobRenderer<>(e, new DefaultModel<>(ModelLocations.ENCRUSTED)));
        event.registerEntityRenderer(UPEntities.SLUDGE.get(), e -> new LivingCutoutNoCullEntityRenderer<>(e, new DefaultModel<>(ModelLocations.SLUDGE)));

        // Fossils
        event.registerEntityRenderer(UPEntities.TYRANNO_SKELETON.get(), e -> new LivingEntityRenderer<>(e, new TyrannosaurusSkeletonModel()));
        event.registerEntityRenderer(UPEntities.TRIKE_SKELETON.get(), e -> new LivingEntityRenderer<>(e, new TriceratopsSkeletonModel()));
        event.registerEntityRenderer(UPEntities.UNICORN_SKELETON.get(), e -> new LivingEntityRenderer<>(e, new UnicornSkeletonModel()));

        // Non-living mobs
        event.registerEntityRenderer(UPEntities.ICEBERG_MAMMOTH.get(), e -> new LivingEntityRenderer<>(e, new IcebergMammothModel()));
        event.registerEntityRenderer(UPEntities.ICEBERG_SMILODON.get(), e -> new LivingEntityRenderer<>(e, new IcebergSmilodonModel()));
        event.registerEntityRenderer(UPEntities.BOOK_PALAEO.get(), e -> new LivingCutoutNoCullEntityRenderer<>(e, new DefaultModel<>(ModelLocations.BOOK_SNAKE)));

        // Misc entities
        event.registerEntityRenderer(UPEntities.AMBER_SHOT.get(), AmberShotRenderer::new);
        event.registerEntityRenderer(UPEntities.HWACHA_SPIKE.get(), HwachaSpikeRenderer::new);
        event.registerEntityRenderer(UPEntities.OPALESCENT_PEARL.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(UPEntities.OPALESCENT_SHURIKEN.get(), OpalescentShurikenRenderer::new);
        event.registerEntityRenderer(UPEntities.PSITTACCO_ARROW.get(), PsittaccoArrowRenderer::new);
        event.registerEntityRenderer(UPEntities.THROWABLE_FALLING_BLOCK.get(), ThrowableFallingBlockRenderer::new);
        event.registerEntityRenderer(UPEntities.TELECREX_EGG.get(), ThrownItemRenderer::new);

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
}

