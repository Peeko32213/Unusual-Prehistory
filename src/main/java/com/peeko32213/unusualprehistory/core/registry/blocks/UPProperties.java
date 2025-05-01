package com.peeko32213.unusualprehistory.core.registry.blocks;

import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

public class UPProperties {

    public static final WoodType DRYO_WOOD_TYPE = WoodType.register(new WoodType("unusualprehistory:dryophyllum", BlockSetType.OAK));

    public static final class Blocks {

        public static BlockBehaviour.Properties foxii() {
            return BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.BASEDRUM).strength(2.0F, 1.0F).sound(SoundType.WOOD).mapColor(MapColor.TERRACOTTA_RED).ignitedByLava();
        }

        public static BlockBehaviour.Properties ginkgo() {
            return BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.BANJO).strength(2.0F, 4.0F).sound(SoundType.WOOD).mapColor(MapColor.TERRACOTTA_YELLOW).ignitedByLava();
        }

        public static BlockBehaviour.Properties petrified() {
            return BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.IRON_XYLOPHONE).strength(2.0F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.STONE).mapColor(MapColor.TERRACOTTA_GRAY);
        }

        public static BlockBehaviour.Properties zuloagae() {
            return BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.SNARE).strength(2.0F, 1.0F).sound(SoundType.BAMBOO_WOOD).mapColor(MapColor.TERRACOTTA_GRAY).ignitedByLava();
        }

        public static final BlockBehaviour.Properties FOXII_TRAPDOOR = foxii().noOcclusion().strength(3.0F);
        public static final BlockBehaviour.Properties FOXII_PRESSURE_PLATE = foxii().forceSolidOn().noCollission().pushReaction(PushReaction.DESTROY).strength(0.5F);
        public static final BlockBehaviour.Properties FOXII_BUTTON = foxii().noCollission().strength(0.5F).pushReaction(PushReaction.DESTROY);
        public static final BlockBehaviour.Properties FOXII_SIGNS = foxii().forceSolidOn().noCollission().strength(1.0F);
        public static final BlockBehaviour.Properties FOXII_SAPLING = BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GREEN).noCollission().instabreak().sound(SoundType.GRASS).pushReaction(PushReaction.DESTROY);
        public static final BlockBehaviour.Properties FOXII_LEAVES = BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GREEN).strength(0.2F).randomTicks().sound(SoundType.AZALEA_LEAVES).noOcclusion().isSuffocating((blockState, getter, pos) -> false);

        public static final BlockBehaviour.Properties GINKGO_TRAPDOOR = ginkgo().noOcclusion().strength(3.0F);
        public static final BlockBehaviour.Properties GINKGO_PRESSURE_PLATE = ginkgo().forceSolidOn().noCollission().pushReaction(PushReaction.DESTROY).strength(0.5F);
        public static final BlockBehaviour.Properties GINKGO_BUTTON = ginkgo().noCollission().strength(0.5F).pushReaction(PushReaction.DESTROY);
        public static final BlockBehaviour.Properties GINKGO_SIGNS = ginkgo().forceSolidOn().noCollission().strength(1.0F);
        public static final BlockBehaviour.Properties GINKGO_SAPLING = BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_GREEN).noCollission().instabreak().sound(SoundType.GRASS).pushReaction(PushReaction.DESTROY);
        public static final BlockBehaviour.Properties GINKGO_LEAVES = BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_GREEN).strength(0.2F).randomTicks().sound(SoundType.AZALEA_LEAVES).noOcclusion().isSuffocating((blockState, getter, pos) -> false);

        public static final BlockBehaviour.Properties PETRIFIED_TRAPDOOR = petrified().noOcclusion().strength(3.0F);
        public static final BlockBehaviour.Properties PETRIFIED_PRESSURE_PLATE = petrified().forceSolidOn().noCollission().pushReaction(PushReaction.DESTROY).strength(0.5F);
        public static final BlockBehaviour.Properties PETRIFIED_BUTTON = petrified().noCollission().strength(0.5F).pushReaction(PushReaction.DESTROY);
        public static final BlockBehaviour.Properties PETRIFIED_SIGNS = petrified().forceSolidOn().noCollission().strength(1.0F);
        public static final BlockBehaviour.Properties PETRIFIED_BUSH = BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GRAY).noCollission().instabreak().sound(SoundType.STONE).pushReaction(PushReaction.DESTROY);

        public static final BlockBehaviour.Properties ZULOAGAE_TRAPDOOR = zuloagae().noOcclusion().strength(3.0F);
        public static final BlockBehaviour.Properties ZULOAGAE_PRESSURE_PLATE = zuloagae().forceSolidOn().noCollission().pushReaction(PushReaction.DESTROY).strength(0.5F);
        public static final BlockBehaviour.Properties ZULOAGAE_BUTTON = zuloagae().noCollission().strength(0.5F).pushReaction(PushReaction.DESTROY);
        public static final BlockBehaviour.Properties ZULOAGAE_SIGNS = zuloagae().forceSolidOn().noCollission().strength(1.0F);

        public static final BlockBehaviour.Properties CALAMOPHYTON = BlockBehaviour.Properties.of().noCollission().instabreak().sound(SoundType.GRASS).pushReaction(PushReaction.DESTROY);

    }
}
