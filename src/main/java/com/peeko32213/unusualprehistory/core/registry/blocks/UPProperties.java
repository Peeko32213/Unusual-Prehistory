package com.peeko32213.unusualprehistory.core.registry.blocks;

import com.teamabnormals.blueprint.core.util.PropertyUtil;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

public class UPProperties {

    public static final class Blocks {

        public static BlockBehaviour.Properties dryo() {
            return BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.BIT).strength(2.0F, 1.0F).sound(SoundType.WOOD).mapColor(MapColor.TERRACOTTA_PINK);
        }

        public static BlockBehaviour.Properties foxii() {
            return BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.BASEDRUM).strength(2.0F, 1.0F).sound(SoundType.WOOD).mapColor(MapColor.TERRACOTTA_RED);
        }

        public static BlockBehaviour.Properties ginkgo() {
            return BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.BANJO).strength(2.0F, 1.0F).sound(SoundType.WOOD).mapColor(MapColor.TERRACOTTA_YELLOW);
        }

        public static BlockBehaviour.Properties petrified() {
            return BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.IRON_XYLOPHONE).strength(2.0F, 3.0F).requiresCorrectToolForDrops().sound(SoundType.STONE).mapColor(MapColor.TERRACOTTA_GRAY);
        }

        public static BlockBehaviour.Properties zuloagae() {
            return BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.SNARE).strength(2.0F, 1.0F).requiresCorrectToolForDrops().sound(SoundType.BAMBOO_WOOD).mapColor(MapColor.TERRACOTTA_GRAY);
        }

        public static final BlockBehaviour.Properties DRYO_TRAPDOOR = dryo().noOcclusion().isValidSpawn(PropertyUtil::never).strength(3.0F);
        public static final BlockBehaviour.Properties DRYO_PRESSURE_PLATE = dryo().forceSolidOn().noCollission().pushReaction(PushReaction.DESTROY).strength(0.5F);
        public static final BlockBehaviour.Properties DRYO_BUTTON = dryo().noCollission().strength(0.5F).pushReaction(PushReaction.DESTROY);
        public static final BlockBehaviour.Properties DRYO_SIGNS = dryo().forceSolidOn().noCollission().strength(1.0F);
        public static final BlockBehaviour.Properties DRYO_SAPLING = BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_GREEN).noCollission().instabreak().sound(SoundType.GRASS).pushReaction(PushReaction.DESTROY);
        public static final BlockBehaviour.Properties DRYO_LEAVES = BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_GREEN).strength(0.2F).randomTicks().sound(SoundType.AZALEA_LEAVES).noOcclusion().isSuffocating((blockState, getter, pos) -> false);

        public static final BlockBehaviour.Properties FOXII_TRAPDOOR = foxii().noOcclusion().isValidSpawn(PropertyUtil::never).strength(3.0F);
        public static final BlockBehaviour.Properties FOXII_PRESSURE_PLATE = foxii().forceSolidOn().noCollission().pushReaction(PushReaction.DESTROY).strength(0.5F);
        public static final BlockBehaviour.Properties FOXII_BUTTON = foxii().noCollission().strength(0.5F).pushReaction(PushReaction.DESTROY);
        public static final BlockBehaviour.Properties FOXII_SIGNS = foxii().forceSolidOn().noCollission().strength(1.0F);
        public static final BlockBehaviour.Properties FOXII_SAPLING = BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GREEN).noCollission().instabreak().sound(SoundType.GRASS).pushReaction(PushReaction.DESTROY);
        public static final BlockBehaviour.Properties FOXII_LEAVES = BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GREEN).strength(0.2F).randomTicks().sound(SoundType.AZALEA_LEAVES).noOcclusion().isSuffocating((blockState, getter, pos) -> false);

        public static final BlockBehaviour.Properties GINKGO_TRAPDOOR = ginkgo().noOcclusion().isValidSpawn(PropertyUtil::never).strength(3.0F);
        public static final BlockBehaviour.Properties GINKGO_PRESSURE_PLATE = ginkgo().forceSolidOn().noCollission().pushReaction(PushReaction.DESTROY).strength(0.5F);
        public static final BlockBehaviour.Properties GINKGO_BUTTON = ginkgo().noCollission().strength(0.5F).pushReaction(PushReaction.DESTROY);
        public static final BlockBehaviour.Properties GINKGO_SIGNS = ginkgo().forceSolidOn().noCollission().strength(1.0F);
        public static final BlockBehaviour.Properties GINKGO_SAPLING = BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_GREEN).noCollission().instabreak().sound(SoundType.GRASS).pushReaction(PushReaction.DESTROY);
        public static final BlockBehaviour.Properties GINKGO_LEAVES = BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_GREEN).strength(0.2F).randomTicks().sound(SoundType.AZALEA_LEAVES).noOcclusion().isSuffocating((blockState, getter, pos) -> false);

        public static final BlockBehaviour.Properties PETRIFIED_TRAPDOOR = petrified().noOcclusion().isValidSpawn(PropertyUtil::never).strength(3.0F);
        public static final BlockBehaviour.Properties PETRIFIED_PRESSURE_PLATE = petrified().forceSolidOn().noCollission().pushReaction(PushReaction.DESTROY).strength(0.5F);
        public static final BlockBehaviour.Properties PETRIFIED_BUTTON = petrified().noCollission().strength(0.5F).pushReaction(PushReaction.DESTROY);
        public static final BlockBehaviour.Properties PETRIFIED_SIGNS = petrified().forceSolidOn().noCollission().strength(1.0F);
        public static final BlockBehaviour.Properties PETRIFIED_BUSH = BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GRAY).noCollission().instabreak().sound(SoundType.STONE).pushReaction(PushReaction.DESTROY);

        public static final BlockBehaviour.Properties ZULOAGAE_TRAPDOOR = zuloagae().noOcclusion().isValidSpawn(PropertyUtil::never).strength(3.0F);
        public static final BlockBehaviour.Properties ZULOAGAE_PRESSURE_PLATE = zuloagae().forceSolidOn().noCollission().pushReaction(PushReaction.DESTROY).strength(0.5F);
        public static final BlockBehaviour.Properties ZULOAGAE_BUTTON = zuloagae().noCollission().strength(0.5F).pushReaction(PushReaction.DESTROY);
        public static final BlockBehaviour.Properties ZULOAGAE_SIGNS = zuloagae().forceSolidOn().noCollission().strength(1.0F);
    }
}
