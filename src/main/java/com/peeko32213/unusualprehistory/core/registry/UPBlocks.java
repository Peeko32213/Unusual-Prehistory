package com.peeko32213.unusualprehistory.core.registry;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.block.custom.*;
import com.peeko32213.unusualprehistory.common.block.custom.decorations.FossilDecorationBlock;
import com.peeko32213.unusualprehistory.common.block.custom.plant.*;
import com.peeko32213.unusualprehistory.common.world.feature.DryoTreeGrower;
import com.peeko32213.unusualprehistory.common.world.feature.tree.FoxiiTreeGrower;
import com.peeko32213.unusualprehistory.common.world.feature.tree.GinkgoTreeGrower;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.PlaceOnWaterBlockItem;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.minecraft.world.level.block.StandingSignBlock;
import net.minecraft.world.level.block.WallSignBlock;

import java.util.function.Function;
import java.util.function.Supplier;

public class UPBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, UnusualPrehistory.MODID);

    // Book stuff
    public static final RegistryObject<Block> FOSSIL_ORES = registerBlock("fossil_ores", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).strength(0.5F).sound(SoundType.STONE)));

    // Fossil ores
    public static final RegistryObject<Block> STONE_FOSSIL = registerBlock("stone_fossil", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.STONE).strength(3.0F, 3.0F).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> DEEPSLATE_FOSSIL = registerBlock("deepslate_fossil", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.STONE).strength(4.5F, 3.0F).requiresCorrectToolForDrops().sound(SoundType.DEEPSLATE)));

    public static final RegistryObject<Block> PLANT_FOSSIL = registerBlock("plant_fossil", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.STONE).strength(3.0F, 3.0F).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> DEEPSLATE_PLANT_FOSSIL = registerBlock("deepslate_plant_fossil", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.STONE).strength(4.5F, 3.0F).requiresCorrectToolForDrops().sound(SoundType.DEEPSLATE)));

    public static final RegistryObject<Block> STONE_AMBER_FOSSIL = registerBlock("stone_amber_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.STONE).strength(3.0F, 3.0F).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> DEEPSLATE_AMBER_FOSSIL = registerBlock("deepslate_amber_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.STONE).strength(4.5F, 3.0F).requiresCorrectToolForDrops().sound(SoundType.DEEPSLATE)));

    public static final RegistryObject<Block> STONE_OPAL_FOSSIL = registerBlock("stone_opal_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.STONE).strength(3.0F, 3.0F).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> DEEPSLATE_OPAL_FOSSIL = registerBlock("deepslate_opal_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.STONE).strength(4.5F, 3.0F).requiresCorrectToolForDrops().sound(SoundType.DEEPSLATE)));

    public static final RegistryObject<Block> PERMAFROST_FOSSIL = registerBlock("permafrost_fossil", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.FROSTED_ICE).strength(3.0F, 3.0F).requiresCorrectToolForDrops().strength(0.5F).sound(SoundType.GLASS)));
    public static final RegistryObject<Block> PERMAFROST = registerBlock("permafrost", () -> new Block(BlockBehaviour.Properties.copy(Blocks.ICE).strength(0.5F).requiresCorrectToolForDrops().friction(0.98F).strength(0.5F).sound(SoundType.GLASS)));

    public static final RegistryObject<Block> STONE_TAR_FOSSIL = registerBlock("stone_tar_fossil", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.STONE).strength(3.0F, 3.0F).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> DEEPSLATE_TAR_FOSSIL = registerBlock("deepslate_tar_fossil", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.STONE).strength(4.5F, 3.0F).requiresCorrectToolForDrops().sound(SoundType.DEEPSLATE)));

    public static final RegistryObject<Block> OPAL_BLOCK = registerBlock("opal_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.LAPIS_BLOCK).strength(0.5F).sound(SoundType.AMETHYST)));

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
                    UPEntities.AMMON,
                    false
            ),
            entry -> new PlaceOnWaterBlockItem(entry.get(), new Item.Properties()));

    public static final RegistryObject<Block> COTY_EGG = registerBlock("coty_eggs",
            () -> new DinosaurLandEggBlock(
                    BlockBehaviour.Properties.copy(Blocks.TURTLE_EGG).strength(0.5F).sound(SoundType.METAL).randomTicks().noOcclusion(),
                    UPEntities.COTY, 4,
                    Block.box(3.0D, 0.0D, 3.0D, 12.0D, 7.0D, 12.0D),
                    Block.box(1.0D, 0.0D, 1.0D, 15.0D, 7.0D, 15.0D)
            ));

    public static final Supplier<Block> DUNK_EGGS = create("dunk_eggs",
            () -> new DinosaurWaterEggBlock(BlockBehaviour.Properties.copy(Blocks.FROGSPAWN).instabreak().noOcclusion().noCollission().randomTicks(),
                    UPEntities.BABY_DUNK,
                    false),
            entry -> new PlaceOnWaterBlockItem(entry.get(), new Item.Properties()));

    public static final Supplier<Block> SCAU_EGGS = create("scau_eggs",
            () -> new DinosaurWaterEggBlock(BlockBehaviour.Properties.copy(Blocks.FROGSPAWN).instabreak().noOcclusion().noCollission().randomTicks(),
                    UPEntities.SCAU,
                    //Turn this to false if you want it to be placed on water
                    false
            ),
            entry -> new PlaceOnWaterBlockItem(entry.get(), new Item.Properties()));

    public static final Supplier<Block> STETHA_EGGS = create("stetha_eggs",
            () -> new DinosaurWaterEggBlock(BlockBehaviour.Properties.copy(Blocks.FROGSPAWN).instabreak().noOcclusion().noCollission().randomTicks(),
                    UPEntities.STETHACANTHUS, false), entry -> new PlaceOnWaterBlockItem(entry.get(), new Item.Properties()));

    public static final Supplier<Block> DIPLOCAULUS_EGGS = create("diplocaulus_eggs",
            () -> new DinosaurWaterEggBlock(BlockBehaviour.Properties.copy(Blocks.FROGSPAWN).instabreak().noOcclusion().noCollission().randomTicks(),
                    UPEntities.DIPLOCAULUS,
                    false
            ),
            entry -> new PlaceOnWaterBlockItem(entry.get(), new Item.Properties()));

    public static final Supplier<Block> HYNERIA_EGGS = create("hyneria_eggs",
            () -> new DinosaurWaterEggBlock(BlockBehaviour.Properties.copy(Blocks.FROGSPAWN).instabreak().noOcclusion().noCollission().randomTicks(),
                    UPEntities.HYNERIA,
                    false
            ),
            entry -> new PlaceOnWaterBlockItem(entry.get(), new Item.Properties()));

    public static final Supplier<Block> TARTUO_EGGS = create("tartuo_eggs",
            () -> new DinosaurWaterEggBlock(BlockBehaviour.Properties.copy(Blocks.FROGSPAWN).instabreak().noOcclusion().noCollission().randomTicks(),
                    UPEntities.TARTUOSTEUS,
                    false
            ),
            entry -> new PlaceOnWaterBlockItem(entry.get(), new Item.Properties()));

    // Meso eggs
    public static final RegistryObject<Block> ANTARCO_EGG = registerBlock("antarcto_eggs",
            () -> new DinosaurLandEggBlock(
                    BlockBehaviour.Properties.copy(Blocks.TURTLE_EGG).strength(0.5F).sound(SoundType.METAL).randomTicks().noOcclusion(),
                    UPEntities.ANTARCO, 3,
                    Block.box(3.0D, 0.0D, 3.0D, 12.0D, 7.0D, 12.0D),
                    Block.box(1.0D, 0.0D, 1.0D, 15.0D, 7.0D, 15.0D)
            ));

    public static final RegistryObject<Block> ANURO_EGG = registerBlock("anuro_eggs",
            () -> new DinosaurLandEggBlock(
                    BlockBehaviour.Properties.copy(Blocks.TURTLE_EGG).strength(0.5F).sound(SoundType.METAL).randomTicks().noOcclusion(),
                    UPEntities.ANURO, 4,
                    Block.box(3.0D, 0.0D, 3.0D, 12.0D, 7.0D, 12.0D),
                    Block.box(1.0D, 0.0D, 1.0D, 15.0D, 7.0D, 15.0D)
            ));

    public static final RegistryObject<Block> AUSTRO_EGG = registerBlock("austro_eggs",
            () -> new DinosaurLandEggBlock(
                    BlockBehaviour.Properties.copy(Blocks.TURTLE_EGG).strength(0.5F).sound(SoundType.METAL).randomTicks().noOcclusion(),
                    UPEntities.AUSTRO, 3,
                    Block.box(3.0D, 0.0D, 3.0D, 12.0D, 7.0D, 12.0D),
                    Block.box(3.0D, 0.0D, 3.0D, 15.0D, 7.0D, 15.0D)
            ));

    public static final Supplier<Block> BEELZE_EGGS = create("beelze_eggs",
            () -> new DinosaurWaterEggBlock(BlockBehaviour.Properties.copy(Blocks.FROGSPAWN).instabreak().noOcclusion().noCollission().randomTicks(),
                    UPEntities.BEELZE_TADPOLE,
                    false
            ),
            entry -> new PlaceOnWaterBlockItem(entry.get(), new Item.Properties()));

    public static final RegistryObject<Block> BRACHI_EGG = registerBlock("brachi_egg",
            () -> new DinosaurLandEggBlock(
                    BlockBehaviour.Properties.copy(Blocks.TURTLE_EGG).strength(0.5F).sound(SoundType.METAL).randomTicks().noOcclusion(),
                    UPEntities.BRACHI, 1,
                    Block.box(3, 0, 3, 13, 12, 13)
            ));

    public static final RegistryObject<Block> ENCRUSTED_SACK = registerBlock("encrusted_sack",
            () -> new DinosaurLandEggBlock(
                    BlockBehaviour.Properties.copy(Blocks.TURTLE_EGG).strength(0.5F).sound(SoundType.METAL).randomTicks().noOcclusion(),
                    UPEntities.ENCRUSTED, 1,
                    Block.box(4, 0, 4, 12, 8, 12)
            ));

    public static final RegistryObject<Block> ERYON_EGGS = registerBlock("eryon_eggs",
            () -> new DinosaurLandEggBlock(
                    BlockBehaviour.Properties.copy(Blocks.TURTLE_EGG).strength(0.5F).sound(SoundType.HONEY_BLOCK).randomTicks().noOcclusion().noCollission(),
                    UPEntities.ERYON, 1,
                    Block.box(0, 0, 0, 16, 1.5, 16)
            ));

    public static final RegistryObject<Block> HWACHA_EGG = registerBlock("hwacha_eggs",
            () -> new DinosaurLandEggBlock(
                    BlockBehaviour.Properties.copy(Blocks.TURTLE_EGG).strength(0.5F).sound(SoundType.METAL).randomTicks().noOcclusion(),
                    UPEntities.HWACHA, 3,
                    Block.box(3.0D, 0.0D, 3.0D, 12.0D, 7.0D, 12.0D),
                    Block.box(1.0D, 0.0D, 1.0D, 15.0D, 7.0D, 15.0D)
            ));

    public static final RegistryObject<Block> KENTRO_EGG = registerBlock("kentro_eggs",
            () -> new DinosaurLandEggBlock(
                    BlockBehaviour.Properties.copy(Blocks.TURTLE_EGG).strength(0.5F).sound(SoundType.METAL).randomTicks().noOcclusion(),
                    UPEntities.KENTRO, 3,
                    Block.box(3.0D, 0.0D, 3.0D, 12.0D, 7.0D, 12.0D),
                    Block.box(1.0D, 0.0D, 1.0D, 15.0D, 7.0D, 15.0D)
            ));

    public static final Supplier<Block> KIMMER_EGGS = create("kimmer_eggs",
            () -> new DinosaurWaterEggBlock(BlockBehaviour.Properties.copy(Blocks.FROGSPAWN).instabreak().noOcclusion().noCollission().randomTicks(),
                    UPEntities.KIMMER,
                    true
            ),
            entry -> new PlaceOnWaterBlockItem(entry.get(), new Item.Properties()));

    public static final RegistryObject<Block> MAJUNGA_EGG = registerBlock("majunga_eggs",
            () -> new DinosaurLandEggBlock(
                    BlockBehaviour.Properties.copy(Blocks.TURTLE_EGG).strength(0.5F).sound(SoundType.METAL).randomTicks().noOcclusion(),
                    UPEntities.MAJUNGA, 3,
                    Block.box(3.0D, 0.0D, 3.0D, 12.0D, 7.0D, 12.0D),
                    Block.box(1.0D, 0.0D, 1.0D, 15.0D, 7.0D, 15.0D)
            ));

    public static final RegistryObject<Block> PACHY_EGG = registerBlock("pachy_eggs",
            () -> new DinosaurLandEggBlock(
                    BlockBehaviour.Properties.copy(Blocks.TURTLE_EGG).strength(0.5F).sound(SoundType.METAL).randomTicks().noOcclusion(),
                    UPEntities.PACHY, 4,
                    Block.box(3.0D, 0.0D, 3.0D, 12.0D, 7.0D, 12.0D),
                    Block.box(1.0D, 0.0D, 1.0D, 15.0D, 7.0D, 15.0D)
            ));

    public static final RegistryObject<Block> TRIKE_EGG = registerBlock("trike_eggs",
            () -> new DinosaurLandEggBlock(
                    BlockBehaviour.Properties.copy(Blocks.TURTLE_EGG).strength(0.5F).sound(SoundType.METAL).randomTicks().noOcclusion(),
                    UPEntities.TRICERATOPS, 2,
                    Block.box(4, 0, 8, 11, 9, 15),
                    Block.box(2, 0, 1, 14, 10, 15)
            ));

    public static final RegistryObject<Block> REX_EGG = registerBlock("rex_eggs",
            () -> new DinosaurLandEggBlock(
                    BlockBehaviour.Properties.copy(Blocks.TURTLE_EGG).strength(0.5F).sound(SoundType.METAL).randomTicks().noOcclusion(),
                    UPEntities.TYRANNOSAURUS, 2,
                    Block.box(4, 0, 8, 11, 9, 15),
                    Block.box(2, 0, 1, 14, 10, 15)
            ));

    public static final RegistryObject<Block> ULUGH_EGG = registerBlock("ulugh_eggs",
            () -> new DinosaurLandEggBlock(
                    BlockBehaviour.Properties.copy(Blocks.TURTLE_EGG).strength(0.5F).sound(SoundType.METAL).randomTicks().noOcclusion(),
                    UPEntities.ULUG, 3,
                    Block.box(3.0D, 0.0D, 3.0D, 12.0D, 7.0D, 12.0D),
                    Block.box(1.0D, 0.0D, 1.0D, 15.0D, 7.0D, 15.0D)
            ));

    public static final RegistryObject<Block> RAPTOR_EGG = registerBlock("raptor_eggs",
            () -> new DinosaurLandEggBlock(
                    BlockBehaviour.Properties.copy(Blocks.TURTLE_EGG).strength(0.5F).sound(SoundType.METAL).randomTicks().noOcclusion(),
                    UPEntities.VELOCIRAPTOR, 4,
                    Block.box(3.0D, 0.0D, 3.0D, 12.0D, 7.0D, 12.0D),
                    Block.box(1.0D, 0.0D, 1.0D, 15.0D, 7.0D, 15.0D)
            ));

    public static final RegistryObject<Block> BARINA_EGG = registerBlock("barina_eggs",
            () -> new DinosaurLandEggBlock(
                    BlockBehaviour.Properties.copy(Blocks.TURTLE_EGG).strength(0.5F).sound(SoundType.METAL).randomTicks().noOcclusion(),
                    UPEntities.BARINASUCHUS, 4,
                    Block.box(3.0D, 0.0D, 3.0D, 12.0D, 7.0D, 12.0D),
                    Block.box(1.0D, 0.0D, 1.0D, 15.0D, 7.0D, 15.0D)
            ));

    public static final RegistryObject<Block> MEGALA_EGG = registerBlock("megala_eggs",
            () -> new DinosaurLandEggBlock(
                    BlockBehaviour.Properties.copy(Blocks.TURTLE_EGG).strength(0.5F).sound(SoundType.METAL).randomTicks().noOcclusion(),
                    UPEntities.MEGALANIA, 2,
                    Block.box(4, 0, 8, 11, 9, 15),
                    Block.box(2, 0, 1, 14, 10, 15)
            ));

    public static final RegistryObject<Block> TALPANAS_EGG = registerBlock("talpanas_egg",
            () -> new DinosaurLandEggBlock(
                    BlockBehaviour.Properties.copy(Blocks.TURTLE_EGG).strength(0.5F).sound(SoundType.METAL).randomTicks().noOcclusion(),
                    UPEntities.TALPANAS, 1,
                    Block.box(3, 0, 3, 13, 12, 13)
            ));

    public static final Supplier<Block> OPHIDION_EGGS = create("ophiodon_eggs",
            () -> new DinosaurWaterEggBlock(BlockBehaviour.Properties.copy(Blocks.FROGSPAWN).instabreak().noOcclusion().noCollission().randomTicks(),
                    UPEntities.OPHIODON,
                    false
            ),
            entry -> new PlaceOnWaterBlockItem(entry.get(), new Item.Properties()));

    // Fossil mounts
    public static final RegistryObject<Block> AMMONITE_SHELL = registerBlock("ammonite_shell",
            () -> new FossilDecorationBlock(BlockBehaviour.Properties.copy(Blocks.TURTLE_EGG).strength(0.5F).requiresCorrectToolForDrops(),
                    Block.box(5, 0, 2, 11, 12, 14),
                    Block.box(2, 0, 5, 14, 12, 11)
            ));

    public static final RegistryObject<Block> COTY_FOSSIL = registerBlock("coty_fossil",
            () ->  new FossilDecorationBlock(BlockBehaviour.Properties.copy(Blocks.STONE).strength(1.5F).requiresCorrectToolForDrops(),
                    Block.box(-7, 0.08658283817455159, 1.3806023374435663, 23, 30.086582838174543, 5.380602337443565),
                    Block.box(1.3806023374435663, 0.08658283817455159, -7, 5.380602337443565, 30.086582838174543, 23)
            ));

    public static final RegistryObject<Block> STETHA_FOSSIL = registerBlock("stetha_fossil",
            () ->  new FossilDecorationBlock(BlockBehaviour.Properties.copy(Blocks.STONE).strength(1.5F).requiresCorrectToolForDrops(),
                    Block.box(-2, 0, 2, 18, 20, 6),
                    Block.box(2, 0, -2, 6, 20, 18)
            ));

    public static final RegistryObject<Block> ANURO_FOSSIL = registerBlock("anuro_fossil",
            () ->  new FossilDecorationBlock(BlockBehaviour.Properties.copy(Blocks.STONE).strength(1.5F).requiresCorrectToolForDrops(),
                    Block.box(-2, 0, 2, 18, 20, 6),
                    Block.box(2, 0, -2, 6, 20, 18)
            ));

    public static final RegistryObject<Block> SCAU_FOSSIL = registerBlock("scau_fossil",
            () ->  new FossilDecorationBlock(BlockBehaviour.Properties.copy(Blocks.STONE).strength(1.5F).requiresCorrectToolForDrops(),
                    Block.box(-6, 2, 3, 22, 17, 6),
                    Block.box(3, 2, -6, 6, 17, 22)
            ));

    public static final RegistryObject<Block> BEELZE_FOSSIL = registerBlock("beelze_fossil",
            () ->  new FossilDecorationBlock(BlockBehaviour.Properties.copy(Blocks.STONE).strength(1.5F).requiresCorrectToolForDrops(),
                    Block.box(0, 7, 3, 17, 12, 11),
                    Block.box(3, 7, 0, 11, 12, 17)
            ));

    public static final RegistryObject<Block> BRACHI_FOSSIL = registerBlock("brachi_fossil",
            () ->  new FossilDecorationBlock(BlockBehaviour.Properties.copy(Blocks.STONE).strength(1.5F).requiresCorrectToolForDrops(),
                    Block.box(-1, 5, 2, 17, 21, 17),
                    Block.box(2, 5, -1, 17, 21, 17)
            ));

    public static final RegistryObject<Block> DUNK_FOSSIL = registerBlock("dunk_fossil",
            () ->  new FossilDecorationBlock(BlockBehaviour.Properties.copy(Blocks.STONE).strength(1.5F).requiresCorrectToolForDrops(),
                    Block.box(0.5, 1, 6, 15.5, 6, 9),
                    Block.box(6, 1, 0.5, 9, 6, 15.5)
            ));

    public static final RegistryObject<Block> MAJUNGA_FOSSIL = registerBlock("majunga_fossil",
            () ->  new FossilDecorationBlock(BlockBehaviour.Properties.copy(Blocks.STONE).strength(1.5F).requiresCorrectToolForDrops(),
                    Block.box(3.5, 10, 1, 12.5, 17, 13),
                    Block.box(1, 10, 3.5, 13, 17, 12.5)
            ));

    public static final RegistryObject<Block> PACHY_FOSSIL = registerBlock("pachy_fossil",
            () ->  new FossilDecorationBlock(BlockBehaviour.Properties.copy(Blocks.STONE).strength(1.5F).requiresCorrectToolForDrops(),
                    Block.box(2.5, 7, 4, 13.5, 17, 14),
                    Block.box(4, 7, 2.5, 14, 17, 13.5)
            ));

    public static final RegistryObject<Block> VELOCI_FOSSIL = registerBlock("veloci_fossil",
            () -> new FossilDecorationBlock(BlockBehaviour.Properties.copy(Blocks.STONE).strength(1.5F).requiresCorrectToolForDrops(),
                    Block.box(5.5, 7, 7, 10.5, 12, 12),
                    Block.box(7, 7, 5.5, 12, 12, 10.5)
            ));

    public static final RegistryObject<Block> ERYON_FOSSIL = registerBlock("eryon_fossil",
            () -> new FossilDecorationBlock(BlockBehaviour.Properties.copy(Blocks.STONE).strength(1.5F).requiresCorrectToolForDrops(),
                    Block.box(-2, 0, 2, 18, 20, 6),
                    Block.box(2, 0, -2, 6, 20, 18)
            ));

    public static final RegistryObject<Block> AUSTRO_FOSSIL = registerBlock("austro_fossil",
            () ->  new FossilDecorationBlock(BlockBehaviour.Properties.copy(Blocks.STONE).strength(1.5F).requiresCorrectToolForDrops(),
                    Block.box(5.5, 7, 7, 10.5, 12, 12),
                    Block.box(7, 7, 5.5, 12, 12, 10.5)
            ));

    public static final RegistryObject<Block> ULUGH_FOSSIL = registerBlock("ulugh_fossil",
            () ->  new FossilDecorationBlock(BlockBehaviour.Properties.copy(Blocks.STONE).strength(1.5F).requiresCorrectToolForDrops(),
                    Block.box(-1, 5, 2, 17, 21, 17),
                    Block.box(2, 5, -1, 17, 21, 17)
            ));

    public static final RegistryObject<Block> KENTRO_FOSSIL = registerBlock("kentro_fossil",
            () ->  new FossilDecorationBlock(BlockBehaviour.Properties.copy(Blocks.STONE).strength(1.5F).requiresCorrectToolForDrops(),
                    Block.box(0.5, 1, 6, 15.5, 6, 9),
                    Block.box(6, 1, 0.5, 9, 6, 15.5)
            ));

    public static final RegistryObject<Block> ANTARCTO_FOSSIL = registerBlock("antarcto_fossil",
            () -> new FossilDecorationBlock(BlockBehaviour.Properties.copy(Blocks.STONE).strength(1.5F).requiresCorrectToolForDrops(),
                    Block.box(-7.5, 2, 8, 22.5, 4, 25),
                    Block.box(8, 2, -7.5, 25, 4, 22.5)
            ));

    public static final RegistryObject<Block> HWACHA_FOSSIL = registerBlock("hwacha_fossil",
            () -> new FossilDecorationBlock(BlockBehaviour.Properties.copy(Blocks.STONE).strength(1.5F).requiresCorrectToolForDrops(),
                    Block.box(-1, 5, 2, 17, 21, 17),
                    Block.box(2, 5, -1, 17, 21, 17)
            ));

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
    public static final RegistryObject<Block> POTTED_HORSETAIL = registerBlockWithoutBlockItem("potted_horsetail", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, UPBlocks.HORSETAIL, BlockBehaviour.Properties.copy(Blocks.POTTED_DANDELION).noOcclusion()));
    public static final RegistryObject<Block> POTTED_LEEFRUCTUS = registerBlockWithoutBlockItem("potted_leefructus", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, UPBlocks.LEEFRUCTUS, BlockBehaviour.Properties.copy(Blocks.POTTED_DANDELION).noOcclusion()));
    public static final RegistryObject<Block> POTTED_BENNETTITALES = registerBlockWithoutBlockItem("potted_bennett", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, UPBlocks.BENNETTITALES, BlockBehaviour.Properties.copy(Blocks.POTTED_DANDELION).noOcclusion()));
    public static final RegistryObject<Block> POTTED_ARCHAEOSIGILARIA = registerBlockWithoutBlockItem("potted_archaeos", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, UPBlocks.ARCHAEOSIGILARIA, BlockBehaviour.Properties.copy(Blocks.POTTED_DANDELION).noOcclusion()));
    public static final RegistryObject<Block> POTTED_SARACENIA = registerBlockWithoutBlockItem("potted_sarracenia", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, UPBlocks.SARACENIA, BlockBehaviour.Properties.copy(Blocks.POTTED_DANDELION).noOcclusion()));
    public static final RegistryObject<Block> POTTED_GINKGO_SAPLING = registerBlockWithoutBlockItem("potted_ginkgo_sapling", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, UPBlocks.GINKGO_SAPLING, BlockBehaviour.Properties.copy(Blocks.POTTED_DANDELION).noOcclusion()));
    public static final RegistryObject<Block> POTTED_PETRIFIED_BUSH = registerBlockWithoutBlockItem("potted_petrified_bush", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, UPBlocks.PETRIFIED_BUSH, BlockBehaviour.Properties.copy(Blocks.POTTED_DANDELION).noOcclusion()));
    public static final RegistryObject<Block> POTTED_ZULOGAE = registerBlockWithoutBlockItem("potted_zulogae", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, UPBlocks.ZULOAGAE_SAPLING, BlockBehaviour.Properties.copy(Blocks.POTTED_DANDELION).noOcclusion()));
    public static final RegistryObject<Block> POTTED_DRYO = registerBlockWithoutBlockItem("potted_dryo", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, UPBlocks.DRYO_SAPLING, BlockBehaviour.Properties.copy(Blocks.POTTED_DANDELION).noOcclusion()));
    //public static final RegistryObject<Block> POTTED_FOXXI = registerBlockWithoutBlockItem("potted_foxxi", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, UPBlocks.FOXII_SAPLING, BlockBehaviour.Properties.copy(Blocks.POTTED_DANDELION).noOcclusion()));

    // Amber blocks
    public static final RegistryObject<Block> AMBER_BLOCK = registerBlock("amber_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).strength(0.5F).speedFactor(0.4F).sound(SoundType.HONEY_BLOCK)));
    public static final RegistryObject<Block> AMBER_GLASS = registerBlock("amber_glass", () -> new GlassBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).strength(3.0F, 10.0F).requiresCorrectToolForDrops().noOcclusion()));
    public static final RegistryObject<Block> AMBER_GLASS_PANE = registerBlock("amber_glass_pane", () -> new IronBarsBlock(BlockBehaviour.Properties.copy(Blocks.GLASS_PANE).strength(3.0F, 10.0F).requiresCorrectToolForDrops().noOcclusion()));
    public static final RegistryObject<ButtonBlock> AMBER_BUTTON = registerBlock("amber_button", () -> new ButtonBlock(BlockBehaviour.Properties.copy(Blocks.STONE_BUTTON), UPBlockSetType.GINKGO_TYPE, 2, true));

    // Foxii wood
    public static final RegistryObject<RotatedPillarBlock> FOXXI_LOG = registerBlock("foxxi_log", () -> new UPWoodBlocks(BlockBehaviour.Properties.copy(Blocks.OAK_LOG).strength(5f)));
    public static final RegistryObject<RotatedPillarBlock> FOXXI_WOOD = registerBlock("foxxi_wood", () -> new UPWoodBlocks(BlockBehaviour.Properties.copy(Blocks.OAK_LOG)));
    public static final RegistryObject<RotatedPillarBlock> STRIPPED_FOXXI_LOG = registerBlock("stripped_foxxi_log", () -> new UPWoodBlocks(BlockBehaviour.Properties.copy(Blocks.OAK_LOG)));
    public static final RegistryObject<RotatedPillarBlock> STRIPPED_FOXXI_WOOD = registerBlock("stripped_foxxi_wood", () -> new UPWoodBlocks(BlockBehaviour.Properties.copy(Blocks.OAK_LOG)));
    public static final RegistryObject<Block> FOXXI_PLANKS = registerBlock("foxxi_planks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final RegistryObject<StairBlock> FOXXI_STAIRS = registerBlock("foxxi_stairs", () -> new StairBlock(() -> FOXXI_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(FOXXI_PLANKS.get())));
    public static final RegistryObject<SlabBlock> FOXXI_SLAB = registerBlock("foxxi_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(FOXXI_PLANKS.get())));
    public static final RegistryObject<FenceBlock> FOXXI_FENCE = registerBlock("foxxi_fence", () -> new FenceBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE)));
    public static final RegistryObject<FenceGateBlock> FOXXI_FENCE_GATE = registerBlock("foxxi_fence_gate", () -> new FenceGateBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE_GATE), UPBlockSetType.FOXII));
    public static final RegistryObject<DoorBlock> FOXXI_DOOR = registerBlock("foxxi_door", () -> new DoorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_DOOR).noOcclusion(), UPBlockSetType.FOXII_TYPE));
    public static final RegistryObject<TrapDoorBlock> FOXXI_TRAPDOOR = registerBlock("foxxi_trapdoor", () -> new TrapDoorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_TRAPDOOR).noOcclusion(), UPBlockSetType.FOXII_TYPE));
    public static final RegistryObject<PressurePlateBlock> FOXXI_PRESSURE_PLATE = registerBlock("foxxi_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.copy(Blocks.OAK_PRESSURE_PLATE), UPBlockSetType.FOXII_TYPE));
    public static final RegistryObject<ButtonBlock> FOXXI_BUTTON = registerBlock("foxxi_button", () -> new ButtonBlock(BlockBehaviour.Properties.copy(Blocks.OAK_BUTTON), UPBlockSetType.FOXII_TYPE, 30,true));

    public static final RegistryObject<StandingSignBlock> FOXII_SIGN = BLOCKS.register("foxii_sign", () -> new UPStandingSignBlock(BlockBehaviour.Properties.of().mapColor(FOXXI_LOG.get().defaultMapColor()).noCollission().strength(1.0F).sound(SoundType.WOOD), UPBlockSetType.FOXII));
    public static final RegistryObject<WallSignBlock> FOXII_WALL_SIGN = BLOCKS.register("foxii_wall_sign", () -> new UPWallSignBlock(BlockBehaviour.Properties.of().mapColor(FOXXI_LOG.get().defaultMapColor()).noCollission().strength(1.0F).sound(SoundType.WOOD).dropsLike(FOXII_SIGN.get()), UPBlockSetType.FOXII));
    public static final RegistryObject<Block> FOXII_HANGING_SIGN = BLOCKS.register("foxii_hanging_sign", () -> new UPHangingSignBlock(BlockBehaviour.Properties.of().mapColor(FOXXI_LOG.get().defaultMapColor()).forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollission().strength(1.0f).ignitedByLava(), UPBlockSetType.FOXII));
    public static final RegistryObject<Block> FOXII_WALL_HANGING_SIGN = BLOCKS.register("foxii_wall_hanging_sign", () -> new UPWallHangingSignBlock(BlockBehaviour.Properties.of().mapColor(FOXXI_LOG.get().defaultMapColor()).forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollission().strength(1.0f).ignitedByLava().dropsLike(FOXII_HANGING_SIGN.get()), UPBlockSetType.FOXII));

    public static final RegistryObject<Block> FOXXI_LEAVES = registerBlock("foxxi_leaves", () -> new BigLeavesAreaBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).strength(0.2F).randomTicks().sound(SoundType.GRASS).noOcclusion().isValidSpawn(UPBlocks::never).isSuffocating(UPBlocks::never).isViewBlocking(UPBlocks::never).ignitedByLava().pushReaction(PushReaction.DESTROY).isRedstoneConductor(UPBlocks::never), 7, 60, 30 , true));

    public static final RegistryObject<Block> FOXII_SAPLING = registerBlock("foxii_sapling", () -> new DoubleSaplingBlock(new FoxiiTreeGrower(), BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING)));

    // Dryophyllum wood
    public static final RegistryObject<RotatedPillarBlock> DRYO_LOG = registerBlock("dryo_log", () -> new UPWoodBlocks(BlockBehaviour.Properties.copy(Blocks.OAK_LOG)));
    public static final RegistryObject<RotatedPillarBlock> DRYO_WOOD = registerBlock("dryo_wood", () -> new UPWoodBlocks(BlockBehaviour.Properties.copy(Blocks.OAK_LOG)));
    public static final RegistryObject<RotatedPillarBlock> STRIPPED_DRYO_LOG = registerBlock("stripped_dryo_log", () -> new UPWoodBlocks(BlockBehaviour.Properties.copy(Blocks.OAK_LOG)));
    public static final RegistryObject<RotatedPillarBlock> STRIPPED_DRYO_WOOD = registerBlock("stripped_dryo_wood", () -> new UPWoodBlocks(BlockBehaviour.Properties.copy(Blocks.OAK_LOG)));
    public static final RegistryObject<Block> DRYO_PLANKS = registerBlock("dryo_planks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final RegistryObject<StairBlock> DRYO_STAIRS = registerBlock("dryo_stairs", () -> new StairBlock(() -> FOXXI_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(FOXXI_PLANKS.get())));
    public static final RegistryObject<SlabBlock> DRYO_SLAB = registerBlock("dryo_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(FOXXI_PLANKS.get())));
    public static final RegistryObject<FenceBlock> DRYO_FENCE = registerBlock("dryo_fence", () -> new FenceBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE)));
    public static final RegistryObject<FenceGateBlock> DRYO_FENCE_GATE = registerBlock("dryo_fence_gate", () -> new FenceGateBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE_GATE), UPBlockSetType.DRYO));
    public static final RegistryObject<DoorBlock> DRYO_DOOR = registerBlock("dryo_door", () -> new DoorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_DOOR).noOcclusion(), UPBlockSetType.DRYO_TYPE));
    public static final RegistryObject<TrapDoorBlock> DRYO_TRAPDOOR = registerBlock("dryo_trapdoor", () -> new TrapDoorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_TRAPDOOR).noOcclusion(), UPBlockSetType.DRYO_TYPE));
    public static final RegistryObject<PressurePlateBlock> DRYO_PRESSURE_PLATE = registerBlock("dryo_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.copy(Blocks.OAK_PRESSURE_PLATE), UPBlockSetType.DRYO_TYPE));
    public static final RegistryObject<ButtonBlock> DRYO_BUTTON = registerBlock("dryo_button", () -> new ButtonBlock(BlockBehaviour.Properties.copy(Blocks.OAK_BUTTON), UPBlockSetType.DRYO_TYPE, 30, true));

    public static final RegistryObject<StandingSignBlock> DRYO_SIGN = BLOCKS.register("dryo_sign", () -> new UPStandingSignBlock(BlockBehaviour.Properties.of().mapColor(DRYO_LOG.get().defaultMapColor()).noCollission().strength(1.0F).sound(SoundType.WOOD), UPBlockSetType.DRYO));
    public static final RegistryObject<WallSignBlock> DRYO_WALL_SIGN = BLOCKS.register("dryo_wall_sign", () -> new UPWallSignBlock(BlockBehaviour.Properties.of().mapColor(DRYO_LOG.get().defaultMapColor()).noCollission().strength(1.0F).sound(SoundType.WOOD).dropsLike(DRYO_SIGN.get()), UPBlockSetType.DRYO));
    public static final RegistryObject<Block> DRYO_HANGING_SIGN = BLOCKS.register("dryo_hanging_sign", () -> new UPHangingSignBlock(BlockBehaviour.Properties.of().mapColor(DRYO_LOG.get().defaultMapColor()).forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollission().strength(1.0f).ignitedByLava(), UPBlockSetType.DRYO));
    public static final RegistryObject<Block> DRYO_WALL_HANGING_SIGN = BLOCKS.register("dryo_wall_hanging_sign", () -> new UPWallHangingSignBlock(BlockBehaviour.Properties.of().mapColor(DRYO_LOG.get().defaultMapColor()).forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollission().strength(1.0f).ignitedByLava().dropsLike(DRYO_HANGING_SIGN.get()), UPBlockSetType.DRYO));

    public static final RegistryObject<Block> DRYO_LEAVES = registerBlock("dryo_leaves", () -> new BigLeavesAreaBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).strength(0.2F).randomTicks().sound(SoundType.GRASS).noOcclusion().isValidSpawn(UPBlocks::never).isSuffocating(UPBlocks::never).isViewBlocking(UPBlocks::never).ignitedByLava().pushReaction(PushReaction.DESTROY).isRedstoneConductor(UPBlocks::never), 7, 60, 30, true));

    public static final RegistryObject<Block> DRYO_SAPLING = registerBlock("dryo_sapling", () -> new SaplingBlock(new DryoTreeGrower(), BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING)));

    // Ginkgo wood
    public static final RegistryObject<RotatedPillarBlock> GINKGO_LOG = registerBlock("ginkgo_log", () -> new UPWoodBlocks(BlockBehaviour.Properties.copy(Blocks.OAK_LOG)));
    public static final RegistryObject<RotatedPillarBlock> GINKGO_WOOD = registerBlock("ginkgo_wood", () -> new UPWoodBlocks(BlockBehaviour.Properties.copy(Blocks.OAK_LOG)));
    public static final RegistryObject<RotatedPillarBlock> STRIPPED_GINKGO_LOG = registerBlock("stripped_ginkgo_log", () -> new UPWoodBlocks(BlockBehaviour.Properties.copy(Blocks.OAK_LOG)));
    public static final RegistryObject<RotatedPillarBlock> STRIPPED_GINKGO_WOOD = registerBlock("stripped_ginkgo_wood", () -> new UPWoodBlocks(BlockBehaviour.Properties.copy(Blocks.OAK_LOG)));
    public static final RegistryObject<Block> GINKGO_PLANKS = registerBlock("ginkgo_planks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final RegistryObject<StairBlock> GINKGO_STAIRS = registerBlock("ginkgo_stairs", () -> new StairBlock(() -> GINKGO_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(GINKGO_PLANKS.get())));
    public static final RegistryObject<SlabBlock> GINKGO_SLAB = registerBlock("ginkgo_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(GINKGO_PLANKS.get())));
    public static final RegistryObject<FenceBlock> GINKGO_FENCE = registerBlock("ginkgo_fence", () -> new FenceBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE)));
    public static final RegistryObject<FenceGateBlock> GINKGO_FENCE_GATE = registerBlock("ginkgo_fence_gate", () -> new FenceGateBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE_GATE), UPBlockSetType.GINKGO));
    public static final RegistryObject<DoorBlock> GINKGO_DOOR = registerBlock("ginkgo_door", () -> new DoorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_DOOR).noOcclusion(), UPBlockSetType.GINKGO_TYPE));
    public static final RegistryObject<TrapDoorBlock> GINKGO_TRAPDOOR = registerBlock("ginkgo_trapdoor", () -> new TrapDoorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_TRAPDOOR).noOcclusion(), UPBlockSetType.GINKGO_TYPE));
    public static final RegistryObject<PressurePlateBlock> GINKGO_PRESSURE_PLATE = registerBlock("ginkgo_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.copy(Blocks.OAK_PRESSURE_PLATE), UPBlockSetType.GINKGO_TYPE));
    public static final RegistryObject<ButtonBlock> GINKGO_BUTTON = registerBlock("ginkgo_button", () -> new ButtonBlock(BlockBehaviour.Properties.copy(Blocks.OAK_BUTTON), UPBlockSetType.GINKGO_TYPE, 30 ,true));

    public static final RegistryObject<StandingSignBlock> GINKGO_SIGN = BLOCKS.register("ginkgo_sign", () -> new UPStandingSignBlock(BlockBehaviour.Properties.of().mapColor(GINKGO_LOG.get().defaultMapColor()).noCollission().strength(1.0F).sound(SoundType.WOOD), UPBlockSetType.GINKGO));
    public static final RegistryObject<WallSignBlock> GINKGO_WALL_SIGN = BLOCKS.register("ginkgo_wall_sign", () -> new UPWallSignBlock(BlockBehaviour.Properties.of().mapColor(GINKGO_LOG.get().defaultMapColor()).noCollission().strength(1.0F).sound(SoundType.WOOD).dropsLike(GINKGO_SIGN.get()), UPBlockSetType.GINKGO));
    public static final RegistryObject<Block> GINKGO_HANGING_SIGN = BLOCKS.register("ginkgo_hanging_sign", () -> new UPHangingSignBlock(BlockBehaviour.Properties.of().mapColor(GINKGO_LOG.get().defaultMapColor()).forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollission().strength(1.0f).ignitedByLava(), UPBlockSetType.GINKGO));
    public static final RegistryObject<Block> GINKGO_WALL_HANGING_SIGN = BLOCKS.register("ginkgo_wall_hanging_sign", () -> new UPWallHangingSignBlock(BlockBehaviour.Properties.of().mapColor(GINKGO_LOG.get().defaultMapColor()).forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollission().strength(1.0f).ignitedByLava().dropsLike(GINKGO_HANGING_SIGN.get()), UPBlockSetType.GINKGO));

    public static final RegistryObject<Block> GINKGO_LEAVES = registerBlock("ginkgo_leaves", () -> new BigLeavesAreaBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).strength(0.2F).randomTicks().sound(SoundType.GRASS).noOcclusion().isValidSpawn(UPBlocks::never).isSuffocating(UPBlocks::never).isViewBlocking(UPBlocks::never).ignitedByLava().pushReaction(PushReaction.DESTROY).isRedstoneConductor(UPBlocks::never), 7, 60, 30, true));

    public static final RegistryObject<Block> GINKGO_SAPLING = registerBlock("ginkgo_sapling", () -> new SaplingBlock(new GinkgoTreeGrower(), BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING)));

    // Petrified wood
    public static final RegistryObject<RotatedPillarBlock> PETRIFIED_WOOD_LOG = registerBlock("petrified_wood_log", () -> new UPWoodBlocks(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops()));
    public static final Supplier<Block> PETRIFIED_WOOD = registerBlock("petrified_wood", () -> new UPWoodBlocks(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops()));
    public static final RegistryObject<RotatedPillarBlock> STRIPPED_PETRIFIED_WOOD_LOG = registerBlock("stripped_petrified_wood_log", () -> new UPWoodBlocks(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops()));
    public static final RegistryObject<RotatedPillarBlock> STRIPPED_PETRIFIED_WOOD = registerBlock("stripped_petrified_wood", () -> new UPWoodBlocks(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> PETRIFIED_WOOD_PLANKS = registerBlock("petrified_planks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistryObject<StairBlock> PETRIFIED_WOOD_STAIRS = registerBlock("petrified_wood_stairs", () -> new StairBlock(() -> PETRIFIED_WOOD_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(PETRIFIED_WOOD_PLANKS.get())));
    public static final RegistryObject<SlabBlock> PETRIFIED_WOOD_SLAB = registerBlock("petrified_wood_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(PETRIFIED_WOOD_PLANKS.get())));
    public static final RegistryObject<FenceBlock> PETRIFIED_WOOD_FENCE = registerBlock("petrified_wood_fence", () -> new FenceBlock(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS)));
    public static final RegistryObject<FenceGateBlock> PETRIFIED_WOOD_FENCE_GATE = registerBlock("petrified_wood_fence_gate", () -> new FenceGateBlock(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS), UPBlockSetType.PETRIFIED));
    public static final RegistryObject<DoorBlock> PETRIFIED_WOOD_DOOR = registerBlock("petrified_wood_door", () -> new DoorBlock(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS).noOcclusion(), UPBlockSetType.PETRIFIED_TYPE));
    public static final RegistryObject<TrapDoorBlock> PETRIFIED_WOOD_TRAPDOOR = registerBlock("petrified_wood_trapdoor", () -> new TrapDoorBlock(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS).noOcclusion(), UPBlockSetType.PETRIFIED_TYPE));
    public static final RegistryObject<PressurePlateBlock> PETRIFIED_WOOD_PRESSURE_PLATE = registerBlock("petrified_wood_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.copy(Blocks.STONE_PRESSURE_PLATE), UPBlockSetType.PETRIFIED_TYPE));
    public static final RegistryObject<ButtonBlock> PETRIFIED_WOOD_BUTTON = registerBlock("petrified_wood_button", () -> new ButtonBlock(BlockBehaviour.Properties.copy(Blocks.STONE_BUTTON), UPBlockSetType.PETRIFIED_TYPE,30,true));
    public static final RegistryObject<Block> POLISHED_PETRIFIED_WOOD = registerBlock("polished_petrified_wood", () -> new Block(BlockBehaviour.Properties.copy(Blocks.POLISHED_ANDESITE).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> POLISHED_PETRIFIED_WOOD_STAIRS = registerBlock("polished_petrified_wood_stairs", () -> new StairBlock(()-> POLISHED_PETRIFIED_WOOD.get().defaultBlockState(),  BlockBehaviour.Properties.copy(Blocks.POLISHED_ANDESITE).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> POLISHED_PETRIFIED_WOOD_SLAB = registerBlock("polished_petrified_wood_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.POLISHED_ANDESITE).requiresCorrectToolForDrops()));

    public static final RegistryObject<StandingSignBlock> PETRIFIED_WOOD_SIGN = BLOCKS.register("petrified_wood_sign", () -> new UPStandingSignBlock(BlockBehaviour.Properties.of().mapColor(PETRIFIED_WOOD_LOG.get().defaultMapColor()).noCollission().strength(1.0F).sound(SoundType.WOOD), UPBlockSetType.PETRIFIED));
    public static final RegistryObject<WallSignBlock> PETRIFIED_WOOD_WALL_SIGN = BLOCKS.register("petrified_wood_wall_sign", () -> new UPWallSignBlock(BlockBehaviour.Properties.of().mapColor(PETRIFIED_WOOD_LOG.get().defaultMapColor()).noCollission().strength(1.0F).sound(SoundType.WOOD).dropsLike(PETRIFIED_WOOD_SIGN.get()), UPBlockSetType.PETRIFIED));
    public static final RegistryObject<Block> PETRIFIED_WOOD_HANGING_SIGN = BLOCKS.register("petrified_wood_hanging_sign", () -> new UPHangingSignBlock(BlockBehaviour.Properties.of().mapColor(PETRIFIED_WOOD_LOG.get().defaultMapColor()).forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollission().strength(1.0f).ignitedByLava(), UPBlockSetType.PETRIFIED));
    public static final RegistryObject<Block> PETRIFIED_WOOD_WALL_HANGING_SIGN = BLOCKS.register("petrified_wood_wall_hanging_sign", () -> new UPWallHangingSignBlock(BlockBehaviour.Properties.of().mapColor(PETRIFIED_WOOD_LOG.get().defaultMapColor()).forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollission().strength(1.0f).ignitedByLava().dropsLike(PETRIFIED_WOOD_HANGING_SIGN.get()), UPBlockSetType.PETRIFIED));

    public static final RegistryObject<Block> PETRIFIED_BUSH = registerBlock("petrified_bush", () -> new DeadBushBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WOOD).noCollission().instabreak().sound(SoundType.GRASS)));

    // Zuloagae wood
    public static final RegistryObject<RotatedPillarBlock> ZULOAGAE_BLOCK = registerBlock("zuloagae_block", () -> new UPWoodBlocks(BlockBehaviour.Properties.copy(Blocks.BAMBOO_BLOCK).strength(5f)));
    public static final RegistryObject<RotatedPillarBlock> STRIPPED_ZULOAGAE_BLOCK = registerBlock("stripped_zuloagae_block", () -> new UPWoodBlocks(BlockBehaviour.Properties.copy(Blocks.BAMBOO_BLOCK)));
    public static final RegistryObject<Block> ZULOAGAE_PLANKS = registerBlock("zuloagae_planks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.BAMBOO_PLANKS)));
    public static final RegistryObject<StairBlock> ZULOAGAE_STAIRS = registerBlock("zuloagae_stairs", () -> new StairBlock(() -> ZULOAGAE_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(ZULOAGAE_PLANKS.get())));
    public static final RegistryObject<SlabBlock> ZULOAGAE_SLAB = registerBlock("zuloagae_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(ZULOAGAE_PLANKS.get())));
    public static final RegistryObject<FenceBlock> ZULOAGAE_FENCE = registerBlock("zuloagae_fence", () -> new FenceBlock(BlockBehaviour.Properties.copy(Blocks.BAMBOO_FENCE)));
    public static final RegistryObject<FenceGateBlock> ZULOAGAE_FENCE_GATE = registerBlock("zuloagae_fence_gate", () -> new FenceGateBlock(BlockBehaviour.Properties.copy(Blocks.BAMBOO_FENCE_GATE), UPBlockSetType.PETRIFIED));
    public static final RegistryObject<DoorBlock> ZULOAGAE_DOOR = registerBlock("zuloagae_door", () -> new DoorBlock(BlockBehaviour.Properties.copy(Blocks.BAMBOO_DOOR).noOcclusion(), UPBlockSetType.ZULOAGAE_TYPE));
    public static final RegistryObject<TrapDoorBlock> ZULOAGAE_TRAPDOOR = registerBlock("zuloagae_trapdoor", () -> new TrapDoorBlock(BlockBehaviour.Properties.copy(Blocks.BAMBOO_TRAPDOOR).noOcclusion(), UPBlockSetType.ZULOAGAE_TYPE));
    public static final RegistryObject<PressurePlateBlock> ZULOAGAE_PRESSURE_PLATE = registerBlock("zuloagae_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.copy(Blocks.BAMBOO_PRESSURE_PLATE), UPBlockSetType.ZULOAGAE_TYPE));
    public static final RegistryObject<ButtonBlock> ZULOAGAE_BUTTON = registerBlock("zuloagae_button", () -> new ButtonBlock(BlockBehaviour.Properties.copy(Blocks.BAMBOO_BUTTON), UPBlockSetType.ZULOAGAE_TYPE,30,true));

    public static final RegistryObject<StandingSignBlock> ZULOAGAE_SIGN = BLOCKS.register("zuloagae_sign", () -> new UPStandingSignBlock(BlockBehaviour.Properties.of().mapColor(ZULOAGAE_PLANKS.get().defaultMapColor()).noCollission().strength(1.0F).sound(SoundType.BAMBOO), UPBlockSetType.ZULOAGAE));
    public static final RegistryObject<WallSignBlock> ZULOAGAE_WALL_SIGN = BLOCKS.register("zuloagae_wall_sign", () -> new UPWallSignBlock(BlockBehaviour.Properties.of().mapColor(ZULOAGAE_PLANKS.get().defaultMapColor()).noCollission().strength(1.0F).sound(SoundType.BAMBOO).dropsLike(ZULOAGAE_SIGN.get()), UPBlockSetType.ZULOAGAE));
    public static final RegistryObject<Block> ZULOAGAE_HANGING_SIGN = BLOCKS.register("zuloagae_hanging_sign", () -> new UPHangingSignBlock(BlockBehaviour.Properties.of().mapColor(ZULOAGAE_PLANKS.get().defaultMapColor()).forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollission().strength(1.0f).ignitedByLava(), UPBlockSetType.ZULOAGAE));
    public static final RegistryObject<Block> ZULOAGAE_WALL_HANGING_SIGN = BLOCKS.register("zuloagae_wall_hanging_sign", () -> new UPWallHangingSignBlock(BlockBehaviour.Properties.of().mapColor(ZULOAGAE_PLANKS.get().defaultMapColor()).forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollission().strength(1.0f).ignitedByLava().dropsLike(ZULOAGAE_HANGING_SIGN.get()), UPBlockSetType.ZULOAGAE));

    public static final RegistryObject<Block> ZULOAGAE = registerBlock("zuloagae", () -> new ZuloagaeBlock(BlockBehaviour.Properties.copy(Blocks.BAMBOO).randomTicks().instabreak().strength(1.0F).sound(SoundType.BAMBOO).noOcclusion().dynamicShape().offsetType(BlockBehaviour.OffsetType.XZ)));
    public static final RegistryObject<Block> ZULOAGAE_SAPLING = registerBlockWithoutBlockItem("zuloagae_sapling", () -> new ZuloagaeSaplingBlock(BlockBehaviour.Properties.copy(Blocks.BAMBOO_SAPLING).randomTicks().instabreak().noCollission().strength(1.0F).sound(SoundType.BAMBOO_SAPLING).offsetType(BlockBehaviour.OffsetType.XZ)));

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

    private static <T extends Block> Supplier<T> create(String key, Supplier<T> block, CreativeModeTab tab) {
        return create(key, block, entry -> new BlockItem(entry.get(), new Item.Properties()));
    }

    private static <T extends Block> Supplier<T> create(String key, Supplier<T> block) {
        return BLOCKS.register(key, block);
    }

    private static boolean never(BlockState p_50806_, BlockGetter p_50807_, BlockPos p_50808_) {
        return false;
    }

    private static Boolean ocelotOrParrot(BlockState p_50822_, BlockGetter p_50823_, BlockPos p_50824_, EntityType<?> p_50825_) {
        return p_50825_ == EntityType.OCELOT || p_50825_ == EntityType.PARROT;
    }

    private static Boolean never(BlockState p_50779_, BlockGetter p_50780_, BlockPos p_50781_, EntityType<?> p_50782_) {
        return false;
    }
}

//    public static final RegistryObject<Block> ELECTRIC_PILLAR = registerBlock("electric_pillar",
//            () -> new BlockElectricPillar(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_GREEN).strength(1.5F).lightLevel((state -> 9)).emissiveRendering((state, level, pos) -> true).noOcclusion()));
//    public static <B extends Block> RegistryObject<B> registerDino(String name, Supplier<? extends B> supplier) {
//        RegistryObject<B> block = BLOCKS.register(name, supplier);
//        UPItems.ITEMS.register(name, () -> new DinoBlockItem(block.get(), new Item.Properties()));
//        return block;
//    }