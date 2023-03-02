package com.peeko32213.unusualprehistory.core.registry;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.block.*;
import com.peeko32213.unusualprehistory.common.block.decorations.*;
import com.peeko32213.unusualprehistory.common.block.plant.DoubleHeadBlock;
import com.peeko32213.unusualprehistory.common.block.plant.DoubleHeadPlantBlock;
import com.peeko32213.unusualprehistory.common.block.plant.WaterLilyUpdate;
import com.peeko32213.unusualprehistory.common.world.feature.tree.GinkgoTreeGrower;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.PlaceOnWaterBlockItem;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Function;
import java.util.function.Supplier;

public class UPBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS,
            UnusualPrehistory.MODID);

    public static final RegistryObject<Block> STONE_FOSSIL = registerBlock("stone_fossil",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.of(Material.STONE).strength(3.0F, 3.0F).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> DEEPSLATE_FOSSIL = registerBlock("deepslate_fossil",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.of(Material.STONE).strength(4.5F, 3.0F).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> DEEPSLATE_PLANT_FOSSIL = registerBlock("deepslate_plant_fossil",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.of(Material.STONE).strength(4.5F, 3.0F).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> PLANT_FOSSIL = registerBlock("plant_fossil",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.of(Material.STONE).strength(3.0F, 3.0F).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> STONE_AMBER_FOSSIL = registerBlock("stone_amber_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.of(Material.STONE).strength(3.0F, 3.0F).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> DEEPSLATE_AMBER_FOSSIL = registerBlock("deepslate_amber_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.of(Material.STONE).strength(4.5F, 3.0F).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> ANALYZER = registerBlock("analyzer",
            () -> new BlockAnalyzer(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion().requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> CULTIVATOR = registerBlock("cultivator",
            () -> new BlockCultivator(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion().requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> AMBER_GLASS = registerBlock("amber_glass",
            () -> new GlassBlock(BlockBehaviour.Properties.of(Material.GLASS).strength(3.0F, 10.0F).requiresCorrectToolForDrops().noOcclusion()));

    public static final RegistryObject<Block> REX_HEAD = registerBlock("rex_head", () -> new BlockRexHead());

    public static final RegistryObject<Block> DNA_FRIDGE = registerBlock("dna_fridge",
            () -> new BlockDNAFridge(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion().requiresCorrectToolForDrops()));
    public static final Supplier<Block> STETHA_EGGS = create("stetha_eggs",
            () -> new BlockStethaEggs(BlockBehaviour.Properties.of(Material.BUILDABLE_GLASS).instabreak().noOcclusion().noCollission()),
            entry -> new PlaceOnWaterBlockItem(entry.get(), new Item.Properties().tab(UnusualPrehistory.DINO_TAB)));

    public static final Supplier<Block> BEELZE_EGGS = create("beelze_eggs",
            () -> new BlockBeelzeEggs(BlockBehaviour.Properties.of(Material.BUILDABLE_GLASS).instabreak().noOcclusion().noCollission()),
            entry -> new PlaceOnWaterBlockItem(entry.get(), new Item.Properties().tab(UnusualPrehistory.DINO_TAB)));

    public static final Supplier<Block> AMON_EGGS = create("ammon_eggs",
            () -> new BlockAmmoniteEggs(BlockBehaviour.Properties.of(Material.BUILDABLE_GLASS).instabreak().noOcclusion().noCollission()),
            entry -> new PlaceOnWaterBlockItem(entry.get(), new Item.Properties().tab(UnusualPrehistory.DINO_TAB)));

    public static final Supplier<Block> DUNK_EGGS = create("dunk_eggs",
            () -> new BlockDunkEggs(BlockBehaviour.Properties.of(Material.BUILDABLE_GLASS).instabreak().noOcclusion().noCollission()),
            entry -> new PlaceOnWaterBlockItem(entry.get(), new Item.Properties().tab(UnusualPrehistory.DINO_TAB)));

    public static final Supplier<Block> SCAU_EGGS = create("scau_eggs",
            () -> new BlockScauEggs(BlockBehaviour.Properties.of(Material.BUILDABLE_GLASS).instabreak().noOcclusion().noCollission()),
            entry -> new PlaceOnWaterBlockItem(entry.get(), new Item.Properties().tab(UnusualPrehistory.DINO_TAB)));

    public static final RegistryObject<Block> ANURO_EGG = registerBlock("anuro_eggs",
            () -> new BlockAnuroEggs());

    public static final RegistryObject<Block> MAJUNGA_EGG = registerBlock("majunga_eggs",
            () -> new BlockMajungaEggs());

    public static final RegistryObject<Block> COTY_EGG = registerBlock("coty_eggs",
            () -> new BlockCotyEggs());

    public static final RegistryObject<Block> BRACHI_EGG = registerBlock("brachi_egg",
            () -> new BlockBrachiEggs());

    public static final RegistryObject<Block> REX_EGG = registerBlock("rex_eggs",
            () -> new BlockRexEggs());

    public static final RegistryObject<Block> TRIKE_EGG = registerBlock("trike_eggs",
            () -> new BlockTrikeEggs());

    public static final RegistryObject<Block> RAPTOR_EGG = registerBlock("raptor_eggs",
            () -> new BlockRaptorEggs());

    public static final RegistryObject<Block> PACHY_EGG = registerBlock("pachy_eggs",
            () -> new BlockPachyEggs());

    public static final RegistryObject<Block> ENCRUSTED_SACK = registerBlock("encrusted_sack",
            () -> new BlockEncrustedSack());

    public static final RegistryObject<Block> ERYON_EGGS = registerBlock("eryon_eggs",
            () -> new BlockEryonEggs());

    public static final RegistryObject<Block> AMMONITE_SHELL = registerBlock("ammonite_shell",
            () -> new BlockAmmoniteShell(BlockBehaviour.Properties.of(Material.EGG).strength(0.5F).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> HORSETAIL = registerBlock("horsetail",
            () -> new BlockHorsetail(BlockBehaviour.Properties.copy(Blocks.GRASS).noOcclusion()));
    public static final RegistryObject<Block> POTTED_HORSETAIL = registerBlockWithoutBlockItem("potted_horsetail",
            () -> new FlowerPotBlock(null, UPBlocks.HORSETAIL, BlockBehaviour.Properties.copy(Blocks.POTTED_DANDELION).noOcclusion()));
    public static final RegistryObject<Block> LEEFRUCTUS = registerBlock("leefructus",
            () -> new FlowerBlock(MobEffects.ABSORPTION, 8, BlockBehaviour.Properties.copy(Blocks.DANDELION).noOcclusion()));

    public static final RegistryObject<Block> POTTED_LEEFRUCTUS = registerBlockWithoutBlockItem("potted_leefructus",
            () -> new FlowerPotBlock(null, UPBlocks.HORSETAIL, BlockBehaviour.Properties.copy(Blocks.POTTED_DANDELION).noOcclusion()));

    public static final RegistryObject<Block> TALL_HORSETAIL = registerBlock("tall_horsetail",
            () -> new BlockUPTallPlant(BlockBehaviour.Properties.copy(Blocks.TALL_GRASS)));

    public static final RegistryObject<Block> BENNETTITALES = registerBlock("bennett",
            () -> new FlowerBlock(MobEffects.DIG_SPEED, 8, BlockBehaviour.Properties.copy(Blocks.DANDELION).noOcclusion()));

    public static final RegistryObject<Block> ARCHAEOSIGILARIA  = registerBlock("archaeos",
            () -> new FlowerBlock(MobEffects.CONFUSION, 8, BlockBehaviour.Properties.copy(Blocks.DANDELION).noOcclusion()));

    public static final RegistryObject<Block> SARACENIA = registerBlock("sarracenia",
            () -> new BlockSaracenia(BlockBehaviour.Properties.copy(Blocks.GRASS).noOcclusion()));

    public static final RegistryObject<Block> TALL_SARACENIA = registerBlock("tall_sarracenia",
            () -> new BlockUPTallPlant(BlockBehaviour.Properties.copy(Blocks.TALL_GRASS)));

    public static final RegistryObject<Block> GINKGO_PLANKS = registerBlock("ginkgo_planks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));

    public static final RegistryObject<Block> GINKGO_LEAVES = registerBlock("ginkgo_leaves",
            () -> new LeavesBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LEAVES)){

                @Override
                public boolean isFlammable(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
                    return true;
                }

                @Override
                public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
                    return 60;
                }

                @Override
                public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
                    return 30;
                }

            });

    public static final RegistryObject<RotatedPillarBlock> GINKGO_LOG = registerBlock("ginkgo_log",
            () -> new BlockGinkgoWood(BlockBehaviour.Properties.copy(Blocks.OAK_LOG)));

    public static final RegistryObject<RotatedPillarBlock> GINKGO_WOOD = registerBlock("ginkgo_wood",
            () -> new BlockGinkgoWood(BlockBehaviour.Properties.copy(Blocks.OAK_LOG)));

    public static final RegistryObject<RotatedPillarBlock> STRIPPED_GINKGO_WOOD = registerBlock("stripped_ginkgo_wood",
            () -> new BlockGinkgoWood(BlockBehaviour.Properties.copy(Blocks.OAK_LOG)));

    public static final RegistryObject<RotatedPillarBlock> STRIPPED_GINKGO_LOG = registerBlock("stripped_ginkgo_log",
            () -> new BlockGinkgoWood(BlockBehaviour.Properties.copy(Blocks.OAK_LOG)));

    public static final RegistryObject<StairBlock> GINKGO_STAIRS = registerBlock("ginkgo_stairs",
            () -> new StairBlock(() -> GINKGO_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(GINKGO_PLANKS.get())));

    public static final RegistryObject<SlabBlock> GINKGO_SLAB = registerBlock("ginkgo_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(GINKGO_PLANKS.get())));

    public static final RegistryObject<FenceBlock> GINKGO_FENCE = registerBlock("ginkgo_fence",
            () -> new FenceBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE)));

    public static final RegistryObject<FenceGateBlock> GINKGO_FENCE_GATE = registerBlock("ginkgo_fence_gate",
            () -> new FenceGateBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE_GATE)));
    public static final RegistryObject<DoorBlock> GINKGO_DOOR = registerBlock("ginkgo_door",
            () -> new DoorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_DOOR)));
    public static final RegistryObject<TrapDoorBlock> GINKGO_TRAPDOOR = registerBlock("ginkgo_trapdoor",
            () -> new TrapDoorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_TRAPDOOR)));

    public static final RegistryObject<WoodButtonBlock> GINKGO_BUTTON = registerBlock("ginkgo_button",
            () -> new WoodButtonBlock(BlockBehaviour.Properties.copy(Blocks.OAK_BUTTON)));

    public static final RegistryObject<WoodButtonBlock> AMBER_BUTTON = registerBlock("amber_button",
            () -> new WoodButtonBlock(BlockBehaviour.Properties.copy(Blocks.OAK_BUTTON)));

    public static final RegistryObject<PressurePlateBlock> GINKGO_PRESSURE_PLATE = registerBlock("ginkgo_pressure_plate",
            () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.copy(Blocks.OAK_PRESSURE_PLATE)));

    public static final RegistryObject<Block> GINKGO_SAPLING = registerBlock("ginkgo_sapling",
            () -> new SaplingBlock(new GinkgoTreeGrower(), BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING)));

    public static final RegistryObject<Block> AMBER_BLOCK = registerBlock("amber_block",
            () -> new Block(BlockBehaviour.Properties.of(Material.SAND, MaterialColor.COLOR_YELLOW).strength(0.5F).speedFactor(0.4F).sound(SoundType.HONEY_BLOCK)));

    public static final RegistryObject<Block> COTY_FOSSIL = registerBlock("coty_fossil",
            () -> new BlockCotyFossil(BlockBehaviour.Properties.of(Material.STONE).strength(1.5F).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> STETHA_FOSSIL = registerBlock("stetha_fossil",
            () -> new BlockStethaFossil(BlockBehaviour.Properties.of(Material.STONE).strength(1.5F).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> ANURO_FOSSIL = registerBlock("anuro_fossil",
            () -> new BlockAnuroFossil(BlockBehaviour.Properties.of(Material.STONE).strength(1.5F).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> SCAU_FOSSIL = registerBlock("scau_fossil",
            () -> new BlockScauFossil(BlockBehaviour.Properties.of(Material.STONE).strength(1.5F).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> BEELZE_FOSSIL = registerBlock("beelze_fossil",
            () -> new BlockBeelzeFossil(BlockBehaviour.Properties.of(Material.STONE).strength(1.5F).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> BRACHI_FOSSIL = registerBlock("brachi_fossil",
            () -> new BlockBrachiFossil(BlockBehaviour.Properties.of(Material.STONE).strength(1.5F).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> DUNK_FOSSIL = registerBlock("dunk_fossil",
            () -> new BlockDunkFossil(BlockBehaviour.Properties.of(Material.STONE).strength(1.5F).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> MAJUNGA_FOSSIL = registerBlock("majunga_fossil",
            () -> new BlockMajungaFossil(BlockBehaviour.Properties.of(Material.STONE).strength(1.5F).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> PACHY_FOSSIL = registerBlock("pachy_fossil",
            () -> new BlockPachyFossil(BlockBehaviour.Properties.of(Material.STONE).strength(1.5F).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> VELOCI_FOSSIL = registerBlock("veloci_fossil",
            () -> new BlockVelociFossil(BlockBehaviour.Properties.of(Material.STONE).strength(1.5F).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> ERYON_FOSSIL = registerBlock("eryon_fossil",
            () -> new BlockEryonFossil(BlockBehaviour.Properties.of(Material.STONE).strength(1.5F).requiresCorrectToolForDrops()));


    public static final RegistryObject<Block> DEAD_CLATHRODICTYON_BLOCK = registerBlock("dead_clathrodictyon_block", () ->
            new Block(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_GRAY).requiresCorrectToolForDrops().strength(1.5F, 6.0F)));


    public static final RegistryObject<Block> CLATHRODICTYON_BLOCK = registerBlock("clathrodictyon_block", () ->
            new CoralBlock(DEAD_CLATHRODICTYON_BLOCK.get(), BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_CYAN).requiresCorrectToolForDrops().strength(1.5F, 6.0F).sound(SoundType.CORAL_BLOCK)));


    public static final RegistryObject<Block> DEAD_CLATHRODICTYON = registerBlock("dead_clathrodictyon", () ->
            new BaseCoralPlantBlock(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_GRAY).requiresCorrectToolForDrops().noCollission().instabreak()));

    public static final RegistryObject<Block> CLATHRODICTYON = registerBlock("clathrodictyon", () ->
            new CoralPlantBlock(DEAD_CLATHRODICTYON.get(), BlockBehaviour.Properties.of(Material.WATER_PLANT, MaterialColor.COLOR_CYAN).noCollission().instabreak().sound(SoundType.WET_GRASS)));

    public static final RegistryObject<Block> ANOSTYLOSTROMA_BLOCK = registerBlock("anostylostroma_block", () ->
            new Block(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_YELLOW).requiresCorrectToolForDrops().strength(1.5F, 6.0F).sound(SoundType.GRASS)));

    public static final RegistryObject<Block> DEAD_CLATHRODICTYON_FAN = BLOCKS.register("dead_clathrodictyon_fan", () ->
            new BaseCoralFanBlock(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_GRAY).requiresCorrectToolForDrops().noCollission().instabreak()));

    public static final RegistryObject<Block> DEAD_CLATHRODICTYON_WALL_FAN = BLOCKS.register("dead_clathrodictyon_wall_fan", () ->
            new BaseCoralWallFanBlock(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_GRAY).requiresCorrectToolForDrops().noCollission().instabreak().dropsLike(DEAD_CLATHRODICTYON_FAN.get())));

    public static final RegistryObject<Block> CLATHRODICTYON_FAN = BLOCKS.register("clathrodictyon_fan", () ->
            new CoralFanBlock(DEAD_CLATHRODICTYON_FAN.get(), BlockBehaviour.Properties.of(Material.WATER_PLANT, MaterialColor.COLOR_CYAN).noCollission().instabreak().sound(SoundType.WET_GRASS)));

    public static final RegistryObject<Block> CLATHRODICTYON_WALL_FAN = BLOCKS.register("clathrodictyon_wall_fan", () ->
            new CoralWallFanBlock(DEAD_CLATHRODICTYON_WALL_FAN.get(), BlockBehaviour.Properties.of(Material.WATER_PLANT, MaterialColor.COLOR_CYAN).noCollission().instabreak().sound(SoundType.WET_GRASS).dropsLike(CLATHRODICTYON_FAN.get())));

    public static final RegistryObject<Block> ARCHAEFRUCTUS = registerBlock("archaefructus", () ->
            new BlockArchaefructus(BlockBehaviour.Properties.of(Material.REPLACEABLE_WATER_PLANT).noCollission().instabreak().sound(SoundType.WET_GRASS)));

    public static final RegistryObject<Block> GINKGO_SIGN = BLOCKS.register("ginkgo_sign", ()
            -> new BlockUPStandingSign(BlockBehaviour.Properties.of(Material.WOOD, GINKGO_LOG.get().defaultMaterialColor()).noCollission().strength(1.0F).sound(SoundType.WOOD), UPSignTypes.GINKGO));
    public static final RegistryObject<Block> GINKGO_WALL_SIGN = BLOCKS.register("ginkgo_wall_sign", ()
            -> new BlockUPWallSign(BlockBehaviour.Properties.of(Material.WOOD, GINKGO_LOG.get().defaultMaterialColor()).noCollission().strength(1.0F).sound(SoundType.WOOD).dropsLike(GINKGO_SIGN.get()), UPSignTypes.GINKGO));

    public static final Supplier<Block> NELUMBITES = create("nelumbites",
            () -> new WaterlilyBlock(BlockBehaviour.Properties.of(Material.BUILDABLE_GLASS).instabreak().noOcclusion()),
            entry -> new PlaceOnWaterBlockItem(entry.get(), new Item.Properties().tab(UnusualPrehistory.DINO_TAB)));
    public static final RegistryObject<Block> QUEREUXIA_PLANT  = registerBlockWithoutBlockItem("quereuxia_plant",
            () -> new DoubleHeadPlantBlock(BlockBehaviour.Properties.of(Material.WATER_PLANT).noOcclusion().noCollission().randomTicks().instabreak().sound(SoundType.WET_GRASS)));
    public static final RegistryObject<Block> QUEREUXIA  = registerBlock("quereuxia",
            () -> new DoubleHeadBlock(BlockBehaviour.Properties.of(Material.WATER_PLANT).noCollission().noOcclusion().randomTicks().instabreak().sound(SoundType.WET_GRASS)));
    public static final Supplier<Block> QUEREUXIA_TOP  = create("quereuxia_top",
            () -> new WaterLilyUpdate(BlockBehaviour.Properties.of(Material.WATER_PLANT).instabreak().noOcclusion().noCollission()),
            entry -> new PlaceOnWaterBlockItem(entry.get(), new Item.Properties().tab(UnusualPrehistory.DINO_TAB)));


    public static <B extends Block> RegistryObject<B> registerBlock(String name, Supplier<? extends B> supplier) {
        RegistryObject<B> block = BLOCKS.register(name, supplier);
        UPItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(UnusualPrehistory.DINO_TAB)));
        return block;
    }

    private static <T extends Block> RegistryObject<T> registerBlockWithoutBlockItem(String name, Supplier<T> block) {
        return BLOCKS.register(name, block);
    }

    private static <T extends Block> Supplier<T> create(String key, Supplier<T> block, CreativeModeTab tab) {
        return create(key, block, entry -> new BlockItem(entry.get(), new Item.Properties().tab(UnusualPrehistory.DINO_TAB)));
    }

    private static <T extends Block> Supplier<T> create(String key, Supplier<T> block, Function<Supplier<T>, Item> item) {
        Supplier<T> entry = create(key, block);
        UPItems.ITEMS.register(key, () -> item.apply(entry));
        return entry;
    }

    private static <T extends Block> Supplier<T> create(String key, Supplier<T> block) {
        return BLOCKS.register(key, block);
    }


    }
