package com.peeko32213.unusualprehistory.core.registry.blocks;

import com.mojang.datafixers.util.Pair;
import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.block.custom.*;
import com.peeko32213.unusualprehistory.common.block.custom.decorations.FossilDecorationBlock;
import com.peeko32213.unusualprehistory.common.block.custom.decorations.RexHeadBlock;
import com.peeko32213.unusualprehistory.common.block.custom.plant.*;
import com.peeko32213.unusualprehistory.common.block.sign.UPCeilingHangingSignBlock;
import com.peeko32213.unusualprehistory.common.block.sign.UPStandingSignBlock;
import com.peeko32213.unusualprehistory.common.block.sign.UPWallHangingSignBlock;
import com.peeko32213.unusualprehistory.common.block.sign.UPWallSignBlock;
import com.peeko32213.unusualprehistory.common.world.feature.tree.DryoTreeGrower;
import com.peeko32213.unusualprehistory.common.world.feature.tree.FoxiiTreeGrower;
import com.peeko32213.unusualprehistory.common.world.feature.tree.GinkgoTreeGrower;
import com.peeko32213.unusualprehistory.core.registry.entities.UPEntities;
import com.peeko32213.unusualprehistory.core.registry.items.UPItems;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.flag.FeatureFlag;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class UPBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, UnusualPrehistory.MODID);
    public static List<RegistryObject<? extends Block>> AUTO_TRANSLATE = new ArrayList<>();

    // Properties
    public static final BlockBehaviour.Properties DRYO_LOG_PROPERTIES = BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_PINK).strength(2.0F, 3.0F).sound(SoundType.CHERRY_WOOD).instrument(NoteBlockInstrument.BASS);
    public static final BlockBehaviour.Properties DRYO_PLANKS_PROPERTIES = BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_PINK).strength(2.0F, 3.0F).sound(SoundType.CHERRY_WOOD).instrument(NoteBlockInstrument.BASS);
    public static final BlockBehaviour.Properties FOXII_LOG_PROPERTIES = BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_RED).strength(2.0F, 3.0F).sound(SoundType.CHERRY_WOOD).instrument(NoteBlockInstrument.BASS);
    public static final BlockBehaviour.Properties FOXII_PLANKS_PROPERTIES = BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_RED).strength(2.0F, 3.0F).sound(SoundType.CHERRY_WOOD).instrument(NoteBlockInstrument.BASS);
    public static final BlockBehaviour.Properties GINKGO_LOG_PROPERTIES = BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_YELLOW).strength(2.0F, 5.0F).sound(SoundType.CHERRY_WOOD).instrument(NoteBlockInstrument.BASS);
    public static final BlockBehaviour.Properties GINKGO_PLANKS_PROPERTIES = BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_YELLOW).strength(2.0F, 5.0F).sound(SoundType.CHERRY_WOOD).instrument(NoteBlockInstrument.BASS);
    public static final BlockBehaviour.Properties PETRIFIED_LOG_PROPERTIES = BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_GRAY).strength(3.0F, 6.0F).sound(SoundType.DRIPSTONE_BLOCK).instrument(NoteBlockInstrument.BASS);
    public static final BlockBehaviour.Properties PETRIFIED_PLANKS_PROPERTIES = BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_GRAY).strength(3.0F, 6.0F).sound(SoundType.DRIPSTONE_BLOCK).instrument(NoteBlockInstrument.BASS);

    public static final WoodType DRYO_WOOD_TYPE = WoodType.register(new WoodType("unusualprehistory:dryophyllum", BlockSetType.CHERRY));
    public static final WoodType FOXII_WOOD_TYPE = WoodType.register(new WoodType("unusualprehistory:foxii", BlockSetType.CHERRY));
    public static final WoodType GINKGO_WOOD_TYPE = WoodType.register(new WoodType("unusualprehistory:ginkgo", BlockSetType.CHERRY));

    // Fossil ores
    public static final RegistryObject<Block> STONE_FOSSIL = registerBlock("stone_fossil", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.STONE).strength(3.0F, 3.0F).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> DEEPSLATE_FOSSIL = registerBlock("deepslate_fossil", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.STONE).strength(4.5F, 3.0F).requiresCorrectToolForDrops().sound(SoundType.DEEPSLATE)));

    public static final RegistryObject<Block> PLANT_FOSSIL = registerBlock("stone_plant_fossil", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.STONE).strength(3.0F, 3.0F).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> DEEPSLATE_PLANT_FOSSIL = registerBlock("deepslate_plant_fossil", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.STONE).strength(4.5F, 3.0F).requiresCorrectToolForDrops().sound(SoundType.DEEPSLATE)));

    public static final RegistryObject<Block> STONE_AMBER_FOSSIL = registerBlock("stone_amber_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.STONE).strength(3.0F, 3.0F).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> DEEPSLATE_AMBER_FOSSIL = registerBlock("deepslate_amber_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.STONE).strength(4.5F, 3.0F).requiresCorrectToolForDrops().sound(SoundType.DEEPSLATE)));

    public static final RegistryObject<Block> OPAL_ORE = createBlock("opal_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.STONE).strength(3.0F, 3.0F).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> DEEPSLATE_OPAL_ORE = createBlock("deepslate_opal_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.STONE).strength(4.5F, 3.0F).requiresCorrectToolForDrops().sound(SoundType.DEEPSLATE)));
    public static final RegistryObject<Block> FIRE_OPAL_ORE = createBlock("fire_opal_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.STONE).strength(3.0F, 3.0F).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> DEEPSLATE_FIRE_OPAL_ORE = createBlock("deepslate_fire_opal_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.STONE).strength(4.5F, 3.0F).requiresCorrectToolForDrops().sound(SoundType.DEEPSLATE)));
    public static final RegistryObject<Block> BOULDER_OPAL_ORE = createBlock("boulder_opal_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.STONE).strength(3.0F, 3.0F).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> DEEPSLATE_BOULDER_OPAL_ORE = createBlock("deepslate_boulder_opal_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.STONE).strength(4.5F, 3.0F).requiresCorrectToolForDrops().sound(SoundType.DEEPSLATE)));
    public static final RegistryObject<Block> BLACK_OPAL_ORE = createBlock("black_opal_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.STONE).strength(3.0F, 3.0F).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> DEEPSLATE_BLACK_OPAL_ORE = createBlock("deepslate_black_opal_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.STONE).strength(4.5F, 3.0F).requiresCorrectToolForDrops().sound(SoundType.DEEPSLATE)));

    public static final RegistryObject<Block> PERMAFROST_FOSSIL = registerBlock("permafrost_fossil", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.PACKED_ICE).strength(3.0F, 3.0F).requiresCorrectToolForDrops().strength(0.5F).sound(SoundType.GLASS)));
    public static final RegistryObject<Block> PERMAFROST = registerBlock("permafrost", () -> new Block(BlockBehaviour.Properties.copy(Blocks.PACKED_ICE).strength(0.5F).requiresCorrectToolForDrops().friction(0.98F).strength(0.5F).sound(SoundType.GLASS)));

    public static final RegistryObject<Block> STONE_TAR_FOSSIL = registerBlock("stone_tar_fossil", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.STONE).strength(3.0F, 3.0F).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> DEEPSLATE_TAR_FOSSIL = registerBlock("deepslate_tar_fossil", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.STONE).strength(4.5F, 3.0F).requiresCorrectToolForDrops().sound(SoundType.DEEPSLATE)));

    // Opal blocks
    public static final RegistryObject<Block> OPAL_BLOCK = createBlock("opal_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.LAPIS_BLOCK).strength(3.0F, 4.0F).sound(SoundType.AMETHYST)));
    public static final RegistryObject<Block> FIRE_OPAL_BLOCK = createBlock("fire_opal_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.LAPIS_BLOCK).strength(3.0F, 4.0F).sound(SoundType.AMETHYST)));
    public static final RegistryObject<Block> BOULDER_OPAL_BLOCK = createBlock("boulder_opal_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.LAPIS_BLOCK).strength(3.0F, 4.0F).sound(SoundType.AMETHYST)));
    public static final RegistryObject<Block> BLACK_OPAL_BLOCK = createBlock("black_opal_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.LAPIS_BLOCK).strength(3.0F, 4.0F).sound(SoundType.AMETHYST)));

    // Science doodads
    public static final RegistryObject<Block> ANALYZER = registerBlock("analyzer", () -> new AnalyzerBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion().requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> CULTIVATOR = registerBlock("cultivator", () -> new CultivatorBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion().requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> DNA_FRIDGE = registerBlock("dna_fridge", () -> new DNAFridgeBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion().requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> INCUBATOR = registerBlock("incubator", () -> new IncubatorBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).strength(0.5F).sound(SoundType.METAL).noOcclusion()));

    // Fruit
    public static final RegistryObject<Block> FRUIT_LOOT_BOX = registerBlock("fruit_loot_box", () -> new FruitLootBoxBlock(BlockBehaviour.Properties.copy(Blocks.PUMPKIN).noOcclusion().strength(0.1F)));

    // Rex blocks
    public static final RegistryObject<Block> REX_HEAD = registerBlock("rex_head", RexHeadBlock::new);
    public static final RegistryObject<Block> REX_BOOMBOX = registerBlock("rex_boombox", RexBoomboxBlock::new);

    // Paleo eggs
    public static final Supplier<Block> AMON_EGGS = create("ammon_eggs",
            () -> new DinosaurWaterEggBlock(BlockBehaviour.Properties.copy(Blocks.FROGSPAWN).instabreak().noOcclusion().noCollission().randomTicks(),
                    UPEntities.AMMON, false), entry -> new PlaceOnWaterBlockItem(entry.get(), new Item.Properties()));

    public static final Supplier<Block> DUNK_EGGS = create("dunk_eggs",
            () -> new DinosaurWaterEggBlock(BlockBehaviour.Properties.copy(Blocks.FROGSPAWN).instabreak().noOcclusion().noCollission().randomTicks(),
                    UPEntities.DUNK, false), entry -> new PlaceOnWaterBlockItem(entry.get(), new Item.Properties()));

    public static final Supplier<Block> SCAU_EGGS = create("scau_eggs",
            () -> new DinosaurWaterEggBlock(BlockBehaviour.Properties.copy(Blocks.FROGSPAWN).instabreak().noOcclusion().noCollission().randomTicks(),
                    UPEntities.SCAU, false), entry -> new PlaceOnWaterBlockItem(entry.get(), new Item.Properties()));

    public static final Supplier<Block> STETHA_EGGS = create("stetha_eggs",
            () -> new DinosaurWaterEggBlock(BlockBehaviour.Properties.copy(Blocks.FROGSPAWN).instabreak().noOcclusion().noCollission().randomTicks(),
                    UPEntities.STETHACANTHUS, false), entry -> new PlaceOnWaterBlockItem(entry.get(), new Item.Properties()));

    public static final Supplier<Block> DIPLOCAULUS_EGGS = create("diplocaulus_eggs",
            () -> new DinosaurWaterEggBlock(BlockBehaviour.Properties.copy(Blocks.FROGSPAWN).instabreak().noOcclusion().noCollission().randomTicks(),
                    UPEntities.DIPLOCAULUS, false), entry -> new PlaceOnWaterBlockItem(entry.get(), new Item.Properties()));

    public static final Supplier<Block> HYNERIA_EGGS = create("hyneria_eggs",
            () -> new DinosaurWaterEggBlock(BlockBehaviour.Properties.copy(Blocks.FROGSPAWN).instabreak().noOcclusion().noCollission().randomTicks(),
                    UPEntities.HYNERIA, false), entry -> new PlaceOnWaterBlockItem(entry.get(), new Item.Properties()));

    public static final Supplier<Block> TARTUO_EGGS = create("tartuo_eggs",
            () -> new DinosaurWaterEggBlock(BlockBehaviour.Properties.copy(Blocks.FROGSPAWN).instabreak().noOcclusion().noCollission().randomTicks(),
                    UPEntities.TARTUOSTEUS, false), entry -> new PlaceOnWaterBlockItem(entry.get(), new Item.Properties()));

    // Meso eggs
    public static final Supplier<Block> BEELZE_EGGS = create("beelze_eggs",
            () -> new DinosaurWaterEggBlock(BlockBehaviour.Properties.copy(Blocks.FROGSPAWN).instabreak().noOcclusion().noCollission().randomTicks(),
                    UPEntities.BEELZE_TADPOLE, false), entry -> new PlaceOnWaterBlockItem(entry.get(), new Item.Properties()));

    public static final Supplier<Block> ERYON_EGGS = create("eryon_eggs",
            () -> new DinosaurWaterEggBlock(BlockBehaviour.Properties.copy(Blocks.FROGSPAWN).instabreak().noOcclusion().noCollission().randomTicks(),
                    UPEntities.ERYON, false), entry -> new PlaceOnWaterBlockItem(entry.get(), new Item.Properties()));


    public static final Supplier<Block> KIMMER_EGGS = create("kimmer_eggs",
            () -> new DinosaurWaterEggBlock(BlockBehaviour.Properties.copy(Blocks.FROGSPAWN).instabreak().noOcclusion().noCollission().randomTicks(),
                    UPEntities.KIMMER, true), entry -> new PlaceOnWaterBlockItem(entry.get(), new Item.Properties()));

    // Ceno eggs
    public static final Supplier<Block> OPHIDION_EGGS = create("ophiodon_eggs",
            () -> new DinosaurWaterEggBlock(BlockBehaviour.Properties.copy(Blocks.FROGSPAWN).instabreak().noOcclusion().noCollission().randomTicks(),
                    UPEntities.OPHIODON, false), entry -> new PlaceOnWaterBlockItem(entry.get(), new Item.Properties()));

    // Fossil mounts
    public static final RegistryObject<Block> AMMONITE_SHELL = registerBlock("ammonite_shell", () -> new FossilDecorationBlock(BlockBehaviour.Properties.copy(Blocks.STONE).strength(1.0F).noOcclusion().requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> COTY_FOSSIL = registerBlock("coty_fossil", () ->  new FossilDecorationBlock(BlockBehaviour.Properties.copy(Blocks.STONE).strength(1.0F).noOcclusion().requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> STETHA_FOSSIL = registerBlock("stetha_fossil", () ->  new FossilDecorationBlock(BlockBehaviour.Properties.copy(Blocks.STONE).strength(1.0F).noOcclusion().requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> ANURO_FOSSIL = registerBlock("anuro_fossil", () ->  new FossilDecorationBlock(BlockBehaviour.Properties.copy(Blocks.STONE).strength(1.0F).noOcclusion().requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> SCAU_FOSSIL = registerBlock("scau_fossil", () ->  new FossilDecorationBlock(BlockBehaviour.Properties.copy(Blocks.STONE).strength(1.0F).noOcclusion().requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> BEELZE_FOSSIL = registerBlock("beelze_fossil", () ->  new FossilDecorationBlock(BlockBehaviour.Properties.copy(Blocks.STONE).strength(1.0F).noOcclusion().requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> BRACHI_FOSSIL = registerBlock("brachi_fossil", () ->  new FossilDecorationBlock(BlockBehaviour.Properties.copy(Blocks.STONE).strength(1.0F).noOcclusion().requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> DUNK_FOSSIL = registerBlock("dunk_fossil", () ->  new FossilDecorationBlock(BlockBehaviour.Properties.copy(Blocks.STONE).strength(1.0F).noOcclusion().requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> MAJUNGA_FOSSIL = registerBlock("majunga_fossil", () ->  new FossilDecorationBlock(BlockBehaviour.Properties.copy(Blocks.STONE).strength(1.0F).noOcclusion().requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> PACHY_FOSSIL = registerBlock("pachy_fossil", () ->  new FossilDecorationBlock(BlockBehaviour.Properties.copy(Blocks.STONE).strength(1.0F).noOcclusion().requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> VELOCI_FOSSIL = registerBlock("veloci_fossil", () -> new FossilDecorationBlock(BlockBehaviour.Properties.copy(Blocks.STONE).strength(1.0F).noOcclusion().requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> ERYON_FOSSIL = registerBlock("eryon_fossil", () -> new FossilDecorationBlock(BlockBehaviour.Properties.copy(Blocks.STONE).strength(1.0F).noOcclusion().requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> AUSTRO_FOSSIL = registerBlock("austro_fossil", () ->  new FossilDecorationBlock(BlockBehaviour.Properties.copy(Blocks.STONE).strength(1.0F).noOcclusion().requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> ULUGH_FOSSIL = registerBlock("ulugh_fossil", () ->  new FossilDecorationBlock(BlockBehaviour.Properties.copy(Blocks.STONE).strength(1.0F).noOcclusion().requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> KENTRO_FOSSIL = registerBlock("kentro_fossil", () ->  new FossilDecorationBlock(BlockBehaviour.Properties.copy(Blocks.STONE).strength(1.0F).noOcclusion().requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> ANTARCTO_FOSSIL = registerBlock("antarcto_fossil", () -> new FossilDecorationBlock(BlockBehaviour.Properties.copy(Blocks.STONE).strength(1.0F).noOcclusion().requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> HWACHA_FOSSIL = registerBlock("hwacha_fossil", () -> new FossilDecorationBlock(BlockBehaviour.Properties.copy(Blocks.STONE).strength(1.0F).noOcclusion().requiresCorrectToolForDrops()));

    // Prehistoric plants
    public static final RegistryObject<Block> ARCHAEFRUCTUS = registerBlock("archaefructus", () -> new ArchaefructusBlock(BlockBehaviour.Properties.copy(Blocks.LILY_PAD).noCollission().instabreak().sound(SoundType.WET_GRASS)));
    public static final RegistryObject<Block> ARCHAEOSIGILARIA = registerBlock("archaeos", () -> new FlowerBlock(MobEffects.CONFUSION, 8, BlockBehaviour.Properties.copy(Blocks.DANDELION).noOcclusion()));
    public static final RegistryObject<Block> BENNETTITALES = registerBlock("bennett", () -> new FlowerBlock(MobEffects.DIG_SPEED, 8, BlockBehaviour.Properties.copy(Blocks.DANDELION).noOcclusion()));
    public static final RegistryObject<Block> HORSETAIL = registerBlock("horsetail", () -> new HorsetailBlock(BlockBehaviour.Properties.copy(Blocks.GRASS).noOcclusion()));
    public static final RegistryObject<Block> TALL_HORSETAIL = registerBlock("tall_horsetail", () -> new UPTallPlantBlock(BlockBehaviour.Properties.copy(Blocks.TALL_GRASS)));
    public static final RegistryObject<Block> LEEFRUCTUS = registerBlock("leefructus", () -> new FlowerBlock(MobEffects.ABSORPTION, 8, BlockBehaviour.Properties.copy(Blocks.DANDELION).noOcclusion()));
    public static final Supplier<Block> NELUMBITES = create("nelumbites", () -> new WaterlilyBlock(BlockBehaviour.Properties.copy(Blocks.LILY_PAD).instabreak().noOcclusion().sound(SoundType.WET_GRASS)), entry -> new PlaceOnWaterBlockItem(entry.get(), new Item.Properties()));
    public static final RegistryObject<Block> QUEREUXIA = registerBlock("quereuxia", () -> new DoubleHeadBlock(BlockBehaviour.Properties.copy(Blocks.LILY_PAD).noCollission().noOcclusion().randomTicks().instabreak().sound(SoundType.WET_GRASS)));
    public static final RegistryObject<Block> QUEREUXIA_PLANT = registerBlockWithoutBlockItem("quereuxia_plant", () -> new DoubleHeadPlantBlock(BlockBehaviour.Properties.copy(Blocks.LILY_PAD).noOcclusion().noCollission().randomTicks().instabreak().sound(SoundType.WET_GRASS)));
    public static final Supplier<Block> QUEREUXIA_TOP = create("quereuxia_top", () -> new WaterLilyUpdate(BlockBehaviour.Properties.copy(Blocks.LILY_PAD).instabreak().noOcclusion().noCollission().sound(SoundType.WET_GRASS)), entry -> new PlaceOnWaterBlockItem(entry.get(), new Item.Properties()));
    public static final RegistryObject<Block> RAIGUENRAYUN = registerBlock("raiguenrayun", () -> new UPTallPlantBlock(BlockBehaviour.Properties.copy(Blocks.TALL_GRASS)));
    public static final RegistryObject<Block> SARACENIA = registerBlock("sarracenia", () -> new SaraceniaBlock(BlockBehaviour.Properties.copy(Blocks.GRASS).noOcclusion()));
    public static final RegistryObject<Block> TALL_SARACENIA = registerBlock("tall_sarracenia", () -> new UPTallPlantBlock(BlockBehaviour.Properties.copy(Blocks.TALL_GRASS)));
    public static final RegistryObject<Block> ISOETES_BEESTONII = createBlock("isoetes_beestonii", () -> new IsoetesBeestoniiBlock(BlockBehaviour.Properties.copy(Blocks.GRASS).noOcclusion()));
    public static final RegistryObject<Block> CLADOPHLEBIS = createBlock("cladophlebis", () -> new CladophlebisBlock(BlockBehaviour.Properties.copy(Blocks.GRASS).noOcclusion()));
    public static final RegistryObject<Block> CALAMOPHYTON = createBlock("calamophyton", () -> new CalamophytonBlock(UPProperties.Blocks.CALAMOPHYTON));
    // Prehistoric corals
    public static final RegistryObject<Block> ANOSTYLOSTROMA_BLOCK = registerBlock("anostylostroma_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.BRAIN_CORAL_BLOCK).requiresCorrectToolForDrops().strength(1.5F, 6.0F).sound(SoundType.GRASS)));
    public static final RegistryObject<Block> DEAD_CLATHRODICTYON_BLOCK = registerBlock("dead_clathrodictyon_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(1.5F, 6.0F)));
    public static final RegistryObject<Block> DEAD_CLATHRODICTYON = registerBlock("dead_clathrodictyon", () -> new BaseCoralPlantBlock(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().noCollission().instabreak()));
    public static final RegistryObject<Block> DEAD_CLATHRODICTYON_FAN = BLOCKS.register("dead_clathrodictyon_fan", () -> new BaseCoralFanBlock(BlockBehaviour.Properties.copy(Blocks.DEAD_BRAIN_CORAL_FAN).requiresCorrectToolForDrops().noCollission().instabreak()));
    public static final RegistryObject<Block> DEAD_CLATHRODICTYON_WALL_FAN = BLOCKS.register("dead_clathrodictyon_wall_fan", () -> new BaseCoralWallFanBlock(BlockBehaviour.Properties.copy(Blocks.DEAD_BRAIN_CORAL_WALL_FAN).requiresCorrectToolForDrops().noCollission().instabreak().dropsLike(DEAD_CLATHRODICTYON_FAN.get())));
    public static final RegistryObject<Block> CLATHRODICTYON_BLOCK = registerBlock("clathrodictyon_block", () -> new CoralBlock(DEAD_CLATHRODICTYON_BLOCK.get(), BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(1.5F, 6.0F).sound(SoundType.CORAL_BLOCK)));
    public static final RegistryObject<Block> CLATHRODICTYON = registerBlock("clathrodictyon", () -> new CoralPlantBlock(DEAD_CLATHRODICTYON.get(), BlockBehaviour.Properties.copy(Blocks.LILY_PAD).noCollission().instabreak().sound(SoundType.WET_GRASS)));
    public static final RegistryObject<Block> CLATHRODICTYON_FAN = BLOCKS.register("clathrodictyon_fan", () -> new CoralFanBlock(DEAD_CLATHRODICTYON_FAN.get(), BlockBehaviour.Properties.copy(Blocks.BRAIN_CORAL_FAN).noCollission().instabreak().sound(SoundType.WET_GRASS)));
    public static final RegistryObject<Block> CLATHRODICTYON_WALL_FAN = BLOCKS.register("clathrodictyon_wall_fan", () -> new CoralWallFanBlock(DEAD_CLATHRODICTYON_WALL_FAN.get(), BlockBehaviour.Properties.copy(Blocks.BRAIN_CORAL_WALL_FAN).noCollission().instabreak().sound(SoundType.WET_GRASS).dropsLike(CLATHRODICTYON_FAN.get())));

    // Potted plants
    public static final RegistryObject<Block> POTTED_HORSETAIL = registerBlockWithoutBlockItem("potted_horsetail", () -> new FlowerPotBlock(UPBlocks.HORSETAIL.get(), flowerPot()));
    public static final RegistryObject<Block> POTTED_LEEFRUCTUS = registerBlockWithoutBlockItem("potted_leefructus", () -> new FlowerPotBlock(UPBlocks.LEEFRUCTUS.get(), flowerPot()));
    public static final RegistryObject<Block> POTTED_BENNETTITALES = registerBlockWithoutBlockItem("potted_bennettitales", () -> new FlowerPotBlock(UPBlocks.BENNETTITALES.get(), flowerPot()));
    public static final RegistryObject<Block> POTTED_ARCHAEOSIGILARIA = registerBlockWithoutBlockItem("potted_archaeosigilaria", () -> new FlowerPotBlock(UPBlocks.ARCHAEOSIGILARIA.get(), flowerPot()));
    public static final RegistryObject<Block> POTTED_SARACENIA = registerBlockWithoutBlockItem("potted_sarracenia", () -> new FlowerPotBlock(UPBlocks.SARACENIA.get(), flowerPot()));
    public static final RegistryObject<Block> POTTED_ISOETES_BEESTONII = registerBlockWithoutBlockItem("potted_isoetes_beestonii", () -> new FlowerPotBlock(UPBlocks.ISOETES_BEESTONII.get(), flowerPot()));
    public static final RegistryObject<Block> POTTED_CLADOPHLEBIS = registerBlockWithoutBlockItem("potted_cladophlebis", () -> new FlowerPotBlock(UPBlocks.CLADOPHLEBIS.get(), flowerPot()));

    // Amber blocks
    public static final RegistryObject<Block> AMBER_BLOCK = createBlock("amber_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).strength(0.5F).speedFactor(0.4F).sound(SoundType.HONEY_BLOCK)));
    public static final RegistryObject<Block> AMBER_GLASS = createBlock("amber_glass", () -> new GlassBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).strength(3.0F, 10.0F).requiresCorrectToolForDrops().noOcclusion()));
    public static final RegistryObject<Block> AMBER_GLASS_PANE = createBlock("amber_glass_pane", () -> new IronBarsBlock(BlockBehaviour.Properties.copy(Blocks.GLASS_PANE).strength(3.0F, 10.0F).requiresCorrectToolForDrops().noOcclusion()));
    public static final RegistryObject<ButtonBlock> AMBER_BUTTON = createBlock("amber_button", () -> new ButtonBlock(BlockBehaviour.Properties.copy(Blocks.STONE_BUTTON), BlockSetType.STONE, 8, true));

    // Dryophyllum
    // Uses no lang because the block id doesn't use the full name like it should
    public static final RegistryObject<Block> DRYO_LOG = createBlockNoLang("dryo_log", () -> new UPWoodBlocks(DRYO_LOG_PROPERTIES));
    public static final RegistryObject<Block> DRYO_WOOD = createBlockNoLang("dryo_wood", () -> new UPWoodBlocks(DRYO_LOG_PROPERTIES));
    public static final RegistryObject<Block> STRIPPED_DRYO_LOG = createBlockNoLang("stripped_dryo_log", () -> new RotatedPillarBlock(DRYO_LOG_PROPERTIES));
    public static final RegistryObject<Block> STRIPPED_DRYO_WOOD = createBlockNoLang("stripped_dryo_wood", () -> new RotatedPillarBlock(DRYO_LOG_PROPERTIES));
    public static final RegistryObject<Block> DRYO_PLANKS = createBlockNoLang("dryo_planks", () -> new Block(DRYO_PLANKS_PROPERTIES));
    public static final RegistryObject<Block> DRYO_STAIRS = createBlockNoLang("dryo_stairs", () -> new StairBlock(() -> DRYO_PLANKS.get().defaultBlockState(), DRYO_PLANKS_PROPERTIES));
    public static final RegistryObject<Block> DRYO_SLAB = createBlockNoLang("dryo_slab", () -> new SlabBlock(DRYO_PLANKS_PROPERTIES));
    public static final RegistryObject<Block> DRYO_PRESSURE_PLATE = createBlockNoLang("dryo_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, DRYO_PLANKS_PROPERTIES.noCollission().strength(0.5F).sound(SoundType.CHERRY_WOOD), BlockSetType.CHERRY));
    public static final RegistryObject<Block> DRYO_BUTTON = createBlockNoLang("dryo_button", () -> new ButtonBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).noCollission().strength(0.5F).sound(SoundType.CHERRY_WOOD), BlockSetType.CHERRY, 30, true));

    public static final RegistryObject<Block> DRYO_FENCE = createBlockNoLang("dryo_fence", () -> new FenceBlock(DRYO_PLANKS_PROPERTIES));
    public static final RegistryObject<Block> DRYO_FENCE_GATE = createBlockNoLang("dryo_fence_gate", () -> new FenceGateBlock(DRYO_PLANKS_PROPERTIES.strength(2.0F, 3.0F).sound(SoundType.CHERRY_WOOD).forceSolidOn(), SoundEvents.CHERRY_WOOD_FENCE_GATE_CLOSE, SoundEvents.CHERRY_WOOD_FENCE_GATE_CLOSE));
    public static final RegistryObject<Block> DRYO_DOOR = createBlockNoLang("dryo_door", () -> new DoorBlock(DRYO_PLANKS_PROPERTIES.strength(3.0F).sound(SoundType.CHERRY_WOOD).noOcclusion(), BlockSetType.CHERRY));
    public static final RegistryObject<Block> DRYO_TRAPDOOR = createBlockNoLang("dryo_trapdoor", () -> new TrapDoorBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).strength(3.0F).sound(SoundType.CHERRY_WOOD).noOcclusion(), BlockSetType.CHERRY));

    public static final Pair<RegistryObject<UPStandingSignBlock>, RegistryObject<UPWallSignBlock>> DRYO_SIGN = createSignBlock("dryophyllum", DRYO_WOOD_TYPE, DRYO_PLANKS_PROPERTIES.sound(SoundType.CHERRY_WOOD));
    public static final Pair<RegistryObject<UPCeilingHangingSignBlock>, RegistryObject<UPWallHangingSignBlock>> DRYO_HANGING_SIGN = createHangingSignBlock("dryophyllum", DRYO_WOOD_TYPE, DRYO_PLANKS_PROPERTIES.sound(SoundType.CHERRY_WOOD));

    public static final RegistryObject<Block> DRYO_LEAVES = createBlockNoLang("dryo_leaves", () -> new LeavesBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LEAVES).sound(SoundType.AZALEA_LEAVES)));
    public static final RegistryObject<Block> DRYO_SAPLING = createBlockNoLang("dryo_sapling", () -> new SaplingBlock(new DryoTreeGrower(), BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING)));
    public static final RegistryObject<Block> POTTED_DRYO_SAPLING = createBlockNoItem("potted_dryophyllum_sapling", () -> new FlowerPotBlock(DRYO_SAPLING.get(), flowerPot()));

    // Foxii
    public static final RegistryObject<Block> FOXII_LOG = createBlockNoLang("foxxi_log", () -> new UPWoodBlocks(FOXII_LOG_PROPERTIES));
    public static final RegistryObject<Block> FOXII_WOOD = createBlockNoLang("foxxi_wood", () -> new UPWoodBlocks(FOXII_LOG_PROPERTIES));
    public static final RegistryObject<Block> STRIPPED_FOXII_LOG = createBlockNoLang("stripped_foxxi_log", () -> new RotatedPillarBlock(FOXII_LOG_PROPERTIES));
    public static final RegistryObject<Block> STRIPPED_FOXII_WOOD = createBlockNoLang("stripped_foxxi_wood", () -> new RotatedPillarBlock(FOXII_LOG_PROPERTIES));
    public static final RegistryObject<Block> FOXII_PLANKS = createBlockNoLang("foxxi_planks", () -> new Block(FOXII_PLANKS_PROPERTIES));
    public static final RegistryObject<Block> FOXII_STAIRS = createBlockNoLang("foxxi_stairs", () -> new StairBlock(() -> FOXII_PLANKS.get().defaultBlockState(), FOXII_PLANKS_PROPERTIES));
    public static final RegistryObject<Block> FOXII_SLAB = createBlockNoLang("foxxi_slab", () -> new SlabBlock(FOXII_PLANKS_PROPERTIES));
    public static final RegistryObject<Block> FOXII_PRESSURE_PLATE = createBlockNoLang("foxxi_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, FOXII_PLANKS_PROPERTIES.noCollission().strength(0.5F).sound(SoundType.CHERRY_WOOD), BlockSetType.CHERRY));
    public static final RegistryObject<Block> FOXII_BUTTON = createBlockNoLang("foxxi_button", () -> new ButtonBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).noCollission().strength(0.5F).sound(SoundType.CHERRY_WOOD), BlockSetType.CHERRY, 30, true));

    public static final RegistryObject<Block> FOXII_FENCE = createBlockNoLang("foxxi_fence", () -> new FenceBlock(FOXII_PLANKS_PROPERTIES));
    public static final RegistryObject<Block> FOXII_FENCE_GATE = createBlockNoLang("foxxi_fence_gate", () -> new FenceGateBlock(FOXII_PLANKS_PROPERTIES.strength(2.0F, 3.0F).sound(SoundType.CHERRY_WOOD).forceSolidOn(), SoundEvents.CHERRY_WOOD_FENCE_GATE_CLOSE, SoundEvents.CHERRY_WOOD_FENCE_GATE_CLOSE));
    public static final RegistryObject<Block> FOXII_DOOR = createBlockNoLang("foxxi_door", () -> new DoorBlock(FOXII_PLANKS_PROPERTIES.strength(3.0F).sound(SoundType.CHERRY_WOOD).noOcclusion(), BlockSetType.CHERRY));
    public static final RegistryObject<Block> FOXII_TRAPDOOR = createBlockNoLang("foxxi_trapdoor", () -> new TrapDoorBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).strength(3.0F).sound(SoundType.CHERRY_WOOD).noOcclusion(), BlockSetType.CHERRY));

    public static final Pair<RegistryObject<UPStandingSignBlock>, RegistryObject<UPWallSignBlock>> FOXII_SIGN = createSignBlock("foxii", FOXII_WOOD_TYPE, FOXII_PLANKS_PROPERTIES.sound(SoundType.CHERRY_WOOD));
    public static final Pair<RegistryObject<UPCeilingHangingSignBlock>, RegistryObject<UPWallHangingSignBlock>> FOXII_HANGING_SIGN = createHangingSignBlock("foxii", FOXII_WOOD_TYPE, FOXII_PLANKS_PROPERTIES.sound(SoundType.CHERRY_WOOD));

    public static final RegistryObject<Block> FOXII_LEAVES = createBlockNoLang("foxxi_leaves", () -> new LeavesBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LEAVES).sound(SoundType.AZALEA_LEAVES)));
    public static final RegistryObject<Block> FOXII_SAPLING = createBlockNoLang("foxii_sapling", () -> new DoubleSaplingBlock(new FoxiiTreeGrower(), BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING)));

    // Ginkgo
    public static final RegistryObject<Block> GINKGO_LOG = createBlock("ginkgo_log", () -> new UPWoodBlocks(GINKGO_LOG_PROPERTIES));
    public static final RegistryObject<Block> GINKGO_WOOD = createBlock("ginkgo_wood", () -> new UPWoodBlocks(GINKGO_LOG_PROPERTIES));
    public static final RegistryObject<Block> STRIPPED_GINKGO_LOG = createBlock("stripped_ginkgo_log", () -> new RotatedPillarBlock(GINKGO_LOG_PROPERTIES));
    public static final RegistryObject<Block> STRIPPED_GINKGO_WOOD = createBlock("stripped_ginkgo_wood", () -> new RotatedPillarBlock(GINKGO_LOG_PROPERTIES));
    public static final RegistryObject<Block> GINKGO_PLANKS = createBlock("ginkgo_planks", () -> new Block(GINKGO_PLANKS_PROPERTIES));
    public static final RegistryObject<Block> GINKGO_STAIRS = createBlock("ginkgo_stairs", () -> new StairBlock(() -> GINKGO_PLANKS.get().defaultBlockState(), GINKGO_PLANKS_PROPERTIES));
    public static final RegistryObject<Block> GINKGO_SLAB = createBlock("ginkgo_slab", () -> new SlabBlock(GINKGO_PLANKS_PROPERTIES));
    public static final RegistryObject<Block> GINKGO_PRESSURE_PLATE = createBlock("ginkgo_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, GINKGO_PLANKS_PROPERTIES.noCollission().strength(0.5F).sound(SoundType.CHERRY_WOOD), BlockSetType.CHERRY));
    public static final RegistryObject<Block> GINKGO_BUTTON = createBlock("ginkgo_button", () -> new ButtonBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).noCollission().strength(0.5F).sound(SoundType.CHERRY_WOOD), BlockSetType.CHERRY, 30, true));

    public static final RegistryObject<Block> GINKGO_FENCE = createBlock("ginkgo_fence", () -> new FenceBlock(GINKGO_PLANKS_PROPERTIES));
    public static final RegistryObject<Block> GINKGO_FENCE_GATE = createBlock("ginkgo_fence_gate", () -> new FenceGateBlock(GINKGO_PLANKS_PROPERTIES.strength(2.0F, 3.0F).sound(SoundType.CHERRY_WOOD).forceSolidOn(), SoundEvents.CHERRY_WOOD_FENCE_GATE_CLOSE, SoundEvents.CHERRY_WOOD_FENCE_GATE_CLOSE));
    public static final RegistryObject<Block> GINKGO_DOOR = createBlock("ginkgo_door", () -> new DoorBlock(GINKGO_PLANKS_PROPERTIES.strength(3.0F).sound(SoundType.CHERRY_WOOD).noOcclusion(), BlockSetType.CHERRY));
    public static final RegistryObject<Block> GINKGO_TRAPDOOR = createBlock("ginkgo_trapdoor", () -> new TrapDoorBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).strength(3.0F).sound(SoundType.CHERRY_WOOD).noOcclusion(), BlockSetType.CHERRY));

    public static final Pair<RegistryObject<UPStandingSignBlock>, RegistryObject<UPWallSignBlock>> GINKGO_SIGN = createSignBlock("ginkgo", GINKGO_WOOD_TYPE, GINKGO_PLANKS_PROPERTIES.sound(SoundType.CHERRY_WOOD));
    public static final Pair<RegistryObject<UPCeilingHangingSignBlock>, RegistryObject<UPWallHangingSignBlock>> GINKGO_HANGING_SIGN = createHangingSignBlock("ginkgo", GINKGO_WOOD_TYPE, GINKGO_PLANKS_PROPERTIES.sound(SoundType.CHERRY_WOOD));

    public static final RegistryObject<Block> GINKGO_LEAVES = createBlock("ginkgo_leaves", () -> new LeavesBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LEAVES).sound(SoundType.AZALEA_LEAVES)));
    public static final RegistryObject<Block> GINKGO_SAPLING = createBlock("ginkgo_sapling", () -> new SaplingBlock(new GinkgoTreeGrower(), BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING)));
    public static final RegistryObject<Block> POTTED_GINKGO_SAPLING = createBlockNoItem("potted_ginkgo_sapling", () -> new FlowerPotBlock(GINKGO_SAPLING.get(), flowerPot()));

    // Petrified
    public static final RegistryObject<Block> PETRIFIED_LOG = createBlockNoLang("petrified_wood_log", () -> new UPWoodBlocks(UPProperties.Blocks.petrified()));
    public static final RegistryObject<Block> PETRIFIED_WOOD = createBlock("petrified_wood", () -> new UPWoodBlocks(UPProperties.Blocks.petrified()));
    public static final RegistryObject<Block> STRIPPED_PETRIFIED_LOG = createBlockNoLang("stripped_petrified_wood_log", () -> new UPWoodBlocks(UPProperties.Blocks.petrified()));
    public static final RegistryObject<Block> STRIPPED_PETRIFIED_WOOD = createBlock("stripped_petrified_wood", () -> new UPWoodBlocks(UPProperties.Blocks.petrified()));
    public static final RegistryObject<Block> PETRIFIED_PLANKS = createBlock("petrified_planks", () -> new Block(UPProperties.Blocks.petrified()));
    public static final RegistryObject<Block> PETRIFIED_STAIRS = createBlockNoLang("petrified_wood_stairs", () -> new StairBlock(() -> PETRIFIED_PLANKS.get().defaultBlockState(), UPProperties.Blocks.petrified()));
    public static final RegistryObject<Block> PETRIFIED_SLAB = createBlockNoLang("petrified_wood_slab", () -> new SlabBlock(UPProperties.Blocks.petrified()));
    public static final RegistryObject<Block> PETRIFIED_FENCE = createBlockNoLang("petrified_wood_fence", () -> new FenceBlock(UPProperties.Blocks.petrified()));

    public static final RegistryObject<Block> PETRIFIED_FENCE_GATE = createBlockNoLang("petrified_wood_fence_gate", () -> new FenceGateBlock(UPProperties.Blocks.petrified(), UPBlockSetType.PETRIFIED_WOOD_TYPE.get()));
    public static final RegistryObject<Block> PETRIFIED_DOOR = createBlockNoLang("petrified_wood_door", () -> new DoorBlock(UPProperties.Blocks.petrified().noOcclusion(), UPBlockSetType.PETRIFIED_BLOCKSET.get()));
    public static final RegistryObject<Block> PETRIFIED_TRAPDOOR = createBlockNoLang("petrified_wood_trapdoor", () -> new TrapDoorBlock(UPProperties.Blocks.PETRIFIED_TRAPDOOR, UPBlockSetType.PETRIFIED_BLOCKSET.get()));
    public static final RegistryObject<Block> PETRIFIED_PRESSURE_PLATE = createBlockNoLang("petrified_wood_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, UPProperties.Blocks.PETRIFIED_PRESSURE_PLATE, UPBlockSetType.PETRIFIED_BLOCKSET.get()));
    public static final RegistryObject<Block> PETRIFIED_BUTTON = createBlockNoLang("petrified_wood_button", () -> new ButtonBlock(UPProperties.Blocks.PETRIFIED_BUTTON, UPBlockSetType.PETRIFIED_BLOCKSET.get(), 20, false));

    public static final Pair<RegistryObject<UPStandingSignBlock>, RegistryObject<UPWallSignBlock>> PETRIFIED_SIGN = createSignBlock("petrified", UPBlockSetType.PETRIFIED_WOOD_TYPE.get(), UPProperties.Blocks.PETRIFIED_SIGNS);
    public static final Pair<RegistryObject<UPCeilingHangingSignBlock>, RegistryObject<UPWallHangingSignBlock>> PETRIFIED_HANGING_SIGN = createHangingSignBlock("petrified", UPBlockSetType.PETRIFIED_WOOD_TYPE.get(), UPProperties.Blocks.PETRIFIED_SIGNS);

    public static final RegistryObject<Block> PETRIFIED_BUSH = createBlock("petrified_bush", () -> new DeadBushBlock(UPProperties.Blocks.PETRIFIED_BUSH));
    public static final RegistryObject<Block> POTTED_PETRIFIED_BUSH = createBlockNoItem("potted_petrified_bush", () -> new FlowerPotBlock(PETRIFIED_BUSH.get(), flowerPot()));

    public static final RegistryObject<Block> POLISHED_PETRIFIED_WOOD = createBlock("polished_petrified_wood", () -> new Block(UPProperties.Blocks.petrified()));
    public static final RegistryObject<Block> POLISHED_PETRIFIED_WOOD_STAIRS = createBlock("polished_petrified_wood_stairs", () -> new StairBlock(() -> POLISHED_PETRIFIED_WOOD.get().defaultBlockState(), UPProperties.Blocks.petrified()));
    public static final RegistryObject<Block> POLISHED_PETRIFIED_WOOD_SLAB = createBlock("polished_petrified_wood_slab", () -> new SlabBlock(UPProperties.Blocks.petrified()));
    public static final RegistryObject<Block> POLISHED_PETRIFIED_WOOD_WALL = createBlock("polished_petrified_wood_wall", () -> new WallBlock(UPProperties.Blocks.petrified()));

    // Zuloagae
    public static final RegistryObject<Block> ZULOAGAE_BLOCK = createBlock("zuloagae_block", () -> new UPWoodBlocks(UPProperties.Blocks.zuloagae()));
    public static final RegistryObject<Block> STRIPPED_ZULOAGAE_BLOCK = createBlock("stripped_zuloagae_block", () -> new UPWoodBlocks(UPProperties.Blocks.zuloagae()));
    public static final RegistryObject<Block> ZULOAGAE_PLANKS = createBlock("zuloagae_planks", () -> new Block(UPProperties.Blocks.zuloagae()));
    public static final RegistryObject<Block> ZULOAGAE_STAIRS = createBlock("zuloagae_stairs", () -> new StairBlock(() -> ZULOAGAE_PLANKS.get().defaultBlockState(), UPProperties.Blocks.zuloagae()));
    public static final RegistryObject<Block> ZULOAGAE_SLAB = createBlock("zuloagae_slab", () -> new SlabBlock(UPProperties.Blocks.zuloagae()));
    public static final RegistryObject<Block> ZULOAGAE_FENCE = createBlock("zuloagae_fence", () -> new FenceBlock(UPProperties.Blocks.zuloagae()));

    public static final RegistryObject<Block> ZULOAGAE_FENCE_GATE = createBlock("zuloagae_fence_gate", () -> new FenceGateBlock(UPProperties.Blocks.zuloagae(), UPBlockSetType.ZULOAGAE_WOOD_TYPE.get()));
    public static final RegistryObject<Block> ZULOAGAE_DOOR = createBlock("zuloagae_door", () -> new DoorBlock(UPProperties.Blocks.zuloagae().noOcclusion(), UPBlockSetType.ZULOAGAE_BLOCKSET.get()));
    public static final RegistryObject<Block> ZULOAGAE_TRAPDOOR = createBlock("zuloagae_trapdoor", () -> new TrapDoorBlock(UPProperties.Blocks.ZULOAGAE_TRAPDOOR, UPBlockSetType.ZULOAGAE_BLOCKSET.get()));
    public static final RegistryObject<Block> ZULOAGAE_PRESSURE_PLATE = createBlock("zuloagae_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, UPProperties.Blocks.ZULOAGAE_PRESSURE_PLATE, UPBlockSetType.ZULOAGAE_BLOCKSET.get()));
    public static final RegistryObject<Block> ZULOAGAE_BUTTON = createBlock("zuloagae_button", () -> new ButtonBlock(UPProperties.Blocks.ZULOAGAE_BUTTON, UPBlockSetType.ZULOAGAE_BLOCKSET.get(), 30, true));

    public static final Pair<RegistryObject<UPStandingSignBlock>, RegistryObject<UPWallSignBlock>> ZULOAGAE_SIGN = createSignBlock("zuloagae", UPBlockSetType.ZULOAGAE_WOOD_TYPE.get(), UPProperties.Blocks.ZULOAGAE_SIGNS);
    public static final Pair<RegistryObject<UPCeilingHangingSignBlock>, RegistryObject<UPWallHangingSignBlock>> ZULOAGAE_HANGING_SIGN = createHangingSignBlock("zuloagae", UPBlockSetType.ZULOAGAE_WOOD_TYPE.get(), UPProperties.Blocks.ZULOAGAE_SIGNS);

    public static final RegistryObject<Block> ZULOAGAE_SAPLING = registerBlockWithoutBlockItem("zuloagae_sapling", () -> new ZuloagaeSaplingBlock(BlockBehaviour.Properties.copy(Blocks.BAMBOO_SAPLING).randomTicks().instabreak().noCollission().strength(1.0F).sound(SoundType.BAMBOO_SAPLING).offsetType(BlockBehaviour.OffsetType.XZ)));
    public static final RegistryObject<Block> POTTED_ZULOAGAE_SAPLING = createBlockNoItem("potted_zuloagae_sapling", () -> new FlowerPotBlock(ZULOAGAE_SAPLING.get(), flowerPot()));

    public static final RegistryObject<Block> ZULOAGAE = createBlock("zuloagae", () -> new ZuloagaeBlock(BlockBehaviour.Properties.copy(Blocks.BAMBOO).randomTicks().instabreak().strength(1.0F).sound(SoundType.BAMBOO).noOcclusion().dynamicShape().offsetType(BlockBehaviour.OffsetType.XZ)));

    // Tar stuffs
    public static final RegistryObject<Block> TAR = registerBlockWithoutBlockItem("tar", () -> new TarBlock(BlockBehaviour.Properties.copy(Blocks.POWDER_SNOW).strength(0.25F).sound(SoundType.MUD).dynamicShape().noOcclusion()));
    public static final RegistryObject<Block> SPLATTERED_TAR = registerBlock("splattered_tar", () -> new SplatteredTarBlock(BlockBehaviour.Properties.copy(Blocks.LILY_PAD).noCollission().strength(0.2F).sound(SoundType.HONEY_BLOCK)));
    public static final RegistryObject<Block> ASPHALT = registerBlock("asphalt", () -> new AsphaltBlock(BlockBehaviour.Properties.copy(Blocks.STONE).instabreak().sound(SoundType.STONE).speedFactor(1.15F)));
    public static final RegistryObject<Block> GOLD_ENGRAVED_ASPHALT = registerBlock("gold_engraved_asphalt", () -> new AsphaltBlock(BlockBehaviour.Properties.copy(Blocks.STONE).instabreak().sound(SoundType.STONE).speedFactor(1.15F)));
    public static final RegistryObject<Block> QUARTZ_ENGRAVED_ASPHALT = registerBlock("quartz_engraved_asphalt", () -> new AsphaltBlock(BlockBehaviour.Properties.copy(Blocks.STONE).instabreak().sound(SoundType.STONE).speedFactor(1.15F)));

    public static <B extends Block> RegistryObject<B> registerBlock(String name, Supplier<? extends B> supplier) {
        RegistryObject<B> block = BLOCKS.register(name, supplier);
        UPItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
        return block;
    }

    private static <T extends Block> Supplier<T> create(String key, Supplier<T> block, Function<Supplier<T>, Item> item) {
        Supplier<T> entry = create(key, block);
        UPItems.ITEMS.register(key, () -> item.apply(entry));
        return entry;
    }

    private static <T extends Block> RegistryObject<T> registerBlockWithoutBlockItem(String name, Supplier<T> block) {
        return BLOCKS.register(name, block);
    }

    private static <T extends Block> Supplier<T> create(String key, Supplier<T> block) {
        return BLOCKS.register(key, block);
    }

    private static <B extends Block> RegistryObject<B> createBlockNoItemNoLang(String name, Supplier<? extends B> supplier) {
        return BLOCKS.register(name, supplier);
    }

    private static <B extends Block> RegistryObject<B> createBlockNoItem(String name, Supplier<? extends B> supplier) {
        RegistryObject<B> block = BLOCKS.register(name, supplier);
        System.out.println(block.getId());
        AUTO_TRANSLATE.add(block);
        return block;
    }

    public static Pair<RegistryObject<UPStandingSignBlock>, RegistryObject<UPWallSignBlock>> createSignBlock(String name, WoodType woodType, Block.Properties properties) {
        RegistryObject<UPStandingSignBlock> standing = createBlockNoItem(name + "_sign", () -> new UPStandingSignBlock(properties, woodType));
        RegistryObject<UPWallSignBlock> wall = createBlockNoItemNoLang(name + "_wall_sign", () -> new UPWallSignBlock(properties.lootFrom(standing), woodType));
        UPItems.ITEMS.register(name + "_sign", () -> new SignItem(new Item.Properties(), standing.get(), wall.get()));
        return Pair.of(standing, wall);
    }

    public static Pair<RegistryObject<UPCeilingHangingSignBlock>, RegistryObject<UPWallHangingSignBlock>> createHangingSignBlock(String name, WoodType woodType, Block.Properties properties) {
        RegistryObject<UPCeilingHangingSignBlock> ceiling = createBlockNoItem(name + "_hanging_sign", () -> new UPCeilingHangingSignBlock(properties, woodType));
        RegistryObject<UPWallHangingSignBlock> wall = createBlockNoItemNoLang(name + "_wall_hanging_sign", () -> new UPWallHangingSignBlock(properties.lootFrom(ceiling), woodType));
        UPItems.ITEMS.register(name + "_hanging_sign", () -> new HangingSignItem(ceiling.get(), wall.get(), new Item.Properties()));
        return Pair.of(ceiling, wall);
    }

    public static BlockBehaviour.Properties flowerPot(FeatureFlag... featureFlags) {
        BlockBehaviour.Properties properties = BlockBehaviour.Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY);
        if (featureFlags.length > 0) {
            properties = properties.requiredFeatures(featureFlags);
        }
        return properties;
    }

    private static <B extends Block> RegistryObject<B> createBlockNoLang(String name, Supplier<? extends B> supplier) {
        RegistryObject<B> block = BLOCKS.register(name, supplier);
        UPItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
        return block;
    }

    private static <B extends Block> RegistryObject<B> createBlock(String name, Supplier<? extends B> supplier) {
        RegistryObject<B> block = BLOCKS.register(name, supplier);
        UPItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
        AUTO_TRANSLATE.add(block);
        return block;
    }
}