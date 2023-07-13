package com.peeko32213.unusualprehistory.client.event;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.client.overlay.AmberProtectionOverlay;
import com.peeko32213.unusualprehistory.client.render.armor.*;
import com.peeko32213.unusualprehistory.client.render.block.CultivatorBlockEntityRenderer;
import com.peeko32213.unusualprehistory.client.render.dinosaur_renders.*;
import com.peeko32213.unusualprehistory.client.render.trail.EntityTrailRenderer;
import com.peeko32213.unusualprehistory.client.screen.AnalyzerScreen;
import com.peeko32213.unusualprehistory.client.screen.CultivatorScreen;
import com.peeko32213.unusualprehistory.client.screen.DNAFridgeScreen;
import com.peeko32213.unusualprehistory.common.block.entity.FruitLootBoxEntity;
import com.peeko32213.unusualprehistory.common.config.UnusualPrehistoryConfig;
import com.peeko32213.unusualprehistory.common.item.armor.ItemAustroBoots;
import com.peeko32213.unusualprehistory.common.item.armor.ItemMajungaHelmet;
import com.peeko32213.unusualprehistory.common.item.armor.ItemTyrantsCrown;
import com.peeko32213.unusualprehistory.common.item.armor.shedscale.ItemShedscaleBoots;
import com.peeko32213.unusualprehistory.common.item.armor.shedscale.ItemShedscaleChestplate;
import com.peeko32213.unusualprehistory.common.item.armor.shedscale.ItemShedscaleHelmet;
import com.peeko32213.unusualprehistory.common.item.armor.shedscale.ItemShedscaleLeggings;
import com.peeko32213.unusualprehistory.core.registry.*;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

import java.awt.event.KeyEvent;

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

        MenuScreens.register(UPMenuTypes.ANALYZER_MENU.get(), AnalyzerScreen::new);
        MenuScreens.register(UPMenuTypes.CULTIVATOR_MENU.get(), CultivatorScreen::new);
        MenuScreens.register(UPMenuTypes.DNA_FRIDGE_MENU.get(), DNAFridgeScreen::new);


        WoodType.register(UPSignTypes.GINKGO);
        WoodType.register(UPSignTypes.PETRIFIED);


        BlockEntityRenderers.register(UPBlockEntities.UP_SIGN.get(), SignRenderer::new);


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
        event.registerEntityRenderer(UPEntities.BRACHI_TEEN.get(), BrachiosaurusTeenRenderer::new);
        event.registerEntityRenderer(UPEntities.VELOCI.get(), VelociraptorRenderer::new);
        event.registerEntityRenderer(UPEntities.REX.get(), TyrannosaurusRexRenderer::new);
        event.registerEntityRenderer(UPEntities.ENCRUSTED.get(), EncrustedRenderer::new);
        event.registerEntityRenderer(UPEntities.AMBER_SHOT.get(), AmberShotRenderer::new);
        event.registerEntityRenderer(UPEntities.HWACHA_SPIKE.get(), HwachaSpikeRenderer::new);
        event.registerEntityRenderer(UPEntities.BABY_BRACHI.get(), BabyBrachiRenderer::new);
        event.registerEntityRenderer(UPEntities.BABY_REX.get(), BabyRexRenderer::new);
        event.registerEntityRenderer(UPEntities.ERYON.get(), EryonRenderer::new);
        event.registerEntityRenderer(UPEntities.AUSTRO.get(), AustroraptorRenderer::new);
        event.registerEntityRenderer(UPEntities.ANTARCO.get(), AntarctopeltaRenderer::new);
        event.registerEntityRenderer(UPEntities.ULUG.get(), UlughbegsaurusRenderer::new);
        event.registerEntityRenderer(UPEntities.KENTRO.get(), KentrosaurusRenderer::new);
        event.registerEntityRenderer(UPEntities.HWACHA.get(), HwachavenatorRenderer::new);
        event.registerEntityRenderer(UPEntities.TALAPANAS.get(), TalapanasRenderer::new);
        event.registerEntityRenderer(UPEntities.GIGANTOPITHICUS.get(), GigantopithicusRenderer::new);
        event.registerEntityRenderer(UPEntities.BARINASUCHUS.get(), BarinasuchusRenderer::new);
        event.registerEntityRenderer(UPEntities.PARACERATHERIUM.get(), ParaceratheriumRenderer::new);
        event.registerEntityRenderer(UPEntities.MEGATHERIUM.get(), MegatheriumRenderer::new);
        event.registerEntityRenderer(UPEntities.SMILODON.get(), SmilodonRenderer::new);
        event.registerEntityRenderer(UPEntities.MAMMOTH.get(), MammothRenderer::new);
        event.registerEntityRenderer(UPEntities.MEGALANIA.get(), MegalaniaRenderer::new);
        event.registerEntityRenderer(UPEntities.PALAEOPHIS.get(), PalaeophisRenderer::new);

        event.registerEntityRenderer(UPEntities.ENTITY_TRAIL.get(), EntityTrailRenderer::new);


        event.registerEntityRenderer(UPEntities.WORLD_SPAWNABLE.get(), WorldSpawnableRenderer::new);

        event.registerBlockEntityRenderer(UPBlockEntities.CULTIVATOR_BLOCK_ENTITY.get(), CultivatorBlockEntityRenderer::new);
        //event.registerBlockEntityRenderer(UPBlockEntities.FRUIT_LOOT_BOX_BLOCK_ENTITY.get(), FruitLootBoxRenderer::new);


        MinecraftForge.EVENT_BUS.register(new ClientEvents());
        try {
            ItemProperties.register(UPItems.TRIKE_SHIELD.get(), new ResourceLocation("blocking"), (stack, p_239421_1_, p_239421_2_, j) -> p_239421_2_ != null && p_239421_2_.isUsingItem() && p_239421_2_.getUseItem() == stack ? 1.0F : 0.0F);
            ItemProperties.register(UPItems.VELOCI_SHIELD.get(), new ResourceLocation("blocking"), (stack, p_239421_1_, p_239421_2_, j) -> p_239421_2_ != null && p_239421_2_.isUsingItem() && p_239421_2_.getUseItem() == stack ? 1.0F : 0.0F);
        } catch (Exception e) {
            UnusualPrehistory.LOGGER.warn("Could not load item models for weapons");
        }
    }

    @SubscribeEvent
    public static void registerBlockColor(RegisterColorHandlersEvent.Block event){
        event.register((pState, pLevel, pPos, pTintIndex) -> {
            if( pLevel != null && pPos != null){
                if(((FruitLootBoxEntity)pLevel.getBlockEntity(pPos)) != null){
                    return ((FruitLootBoxEntity)pLevel.getBlockEntity(pPos)).getColor();
                }
            }
            return 111111;
        }, UPBlocks.FRUIT_LOOT_BOX.get());
    }
    @SubscribeEvent
    public static void registerItemC0lor(RegisterColorHandlersEvent.Item event){
        event.register((pStack, pTintIndex) -> pStack.getOrCreateTag().getInt("color")  , UPBlocks.FRUIT_LOOT_BOX.get());
    }

    @SubscribeEvent
    public static void registerRenderers(final EntityRenderersEvent.AddLayers event) {
        GeoArmorRenderer.registerArmorRenderer(ItemMajungaHelmet.class, MajungaHelmetRenderer::new);
        GeoArmorRenderer.registerArmorRenderer(ItemAustroBoots.class, AustroBootsRenderer::new);
        GeoArmorRenderer.registerArmorRenderer(ItemTyrantsCrown.class, TyrantsCrownRenderer::new);


        GeoArmorRenderer.registerArmorRenderer(ItemShedscaleHelmet.class, ShedscaleHelmetRenderer::new);
        GeoArmorRenderer.registerArmorRenderer(ItemShedscaleChestplate.class, ShedscaleChestplateRenderer::new);
        GeoArmorRenderer.registerArmorRenderer(ItemShedscaleLeggings.class, ShedscaleLeggingsRenderer::new);
        GeoArmorRenderer.registerArmorRenderer(ItemShedscaleBoots.class, ShedscaleBootsRenderer::new);
    }

    @SubscribeEvent
    public void onCameraSetup(ViewportEvent.ComputeCameraAngles event) {
        if (Minecraft.getInstance().player.getEffect(UPEffects.SCREEN_SHAKE.get()) != null && !Minecraft.getInstance().isPaused() && UnusualPrehistoryConfig.SCREEN_SHAKE.get()) {
            int duration = Minecraft.getInstance().player.getEffect(UPEffects.SCREEN_SHAKE.get()).getDuration();
            int amplifier = Minecraft.getInstance().player.getEffect(UPEffects.SCREEN_SHAKE.get()).getAmplifier();
            float f = (Math.min(10, duration) + Minecraft.getInstance().getFrameTime()) * 0.1F;
            double intensity = f * Minecraft.getInstance().options.screenEffectScale().get();
            RandomSource rng = Minecraft.getInstance().player.getRandom();
            double totalAmp = (0.1 + 0.1*amplifier);
            event.getCamera().move(rng.nextFloat() * 0.4F * intensity * totalAmp  , rng.nextFloat() * 0.2F * intensity * totalAmp , rng.nextFloat() * 0.4F * intensity * totalAmp );
        }
    }

    @SubscribeEvent
    public static void registerGuiOverlays(RegisterGuiOverlaysEvent event)
    {
        event.registerAboveAll("amber_protection", AmberProtectionOverlay.HUD_AMBER_PROTECTION);
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
