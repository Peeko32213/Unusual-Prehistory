package com.peeko32213.unusualprehistory.core.registry.blocks;

import com.google.common.base.Suppliers;
import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.teamabnormals.blueprint.core.api.BlockSetTypeRegistryHelper;
import com.teamabnormals.blueprint.core.api.WoodTypeRegistryHelper;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;

import java.util.function.Supplier;

public class UPBlockSetType {

    public static final Supplier<BlockSetType> FOXII_BLOCKSET = Suppliers.memoize(() -> createBlocksetType("foxii", true, SoundType.WOOD, SoundEvents.WOODEN_DOOR_CLOSE, SoundEvents.WOODEN_DOOR_OPEN, SoundEvents.WOODEN_TRAPDOOR_CLOSE, SoundEvents.WOODEN_TRAPDOOR_OPEN, SoundEvents.WOODEN_PRESSURE_PLATE_CLICK_OFF, SoundEvents.WOODEN_PRESSURE_PLATE_CLICK_ON, SoundEvents.WOODEN_BUTTON_CLICK_OFF, SoundEvents.WOODEN_BUTTON_CLICK_ON));
    public static final Supplier<WoodType> FOXII_WOOD_TYPE = Suppliers.memoize(() -> createDefaultWoodType(FOXII_BLOCKSET));

    public static final Supplier<BlockSetType> GINKGO_BLOCKSET = Suppliers.memoize(() -> createBlocksetType("ginkgo", true, SoundType.WOOD, SoundEvents.WOODEN_DOOR_CLOSE, SoundEvents.WOODEN_DOOR_OPEN, SoundEvents.WOODEN_TRAPDOOR_CLOSE, SoundEvents.WOODEN_TRAPDOOR_OPEN, SoundEvents.WOODEN_PRESSURE_PLATE_CLICK_OFF, SoundEvents.WOODEN_PRESSURE_PLATE_CLICK_ON, SoundEvents.WOODEN_BUTTON_CLICK_OFF, SoundEvents.WOODEN_BUTTON_CLICK_ON));
    public static final Supplier<WoodType> GINKGO_WOOD_TYPE = Suppliers.memoize(() -> createDefaultWoodType(GINKGO_BLOCKSET));

    public static final Supplier<BlockSetType> PETRIFIED_BLOCKSET = Suppliers.memoize(() -> createBlocksetType("petrified", true, SoundType.STONE, SoundEvents.IRON_DOOR_CLOSE, SoundEvents.IRON_DOOR_OPEN, SoundEvents.IRON_TRAPDOOR_CLOSE, SoundEvents.IRON_TRAPDOOR_OPEN, SoundEvents.STONE_PRESSURE_PLATE_CLICK_OFF, SoundEvents.STONE_PRESSURE_PLATE_CLICK_ON, SoundEvents.STONE_BUTTON_CLICK_OFF, SoundEvents.STONE_BUTTON_CLICK_ON));
    public static final Supplier<WoodType> PETRIFIED_WOOD_TYPE = Suppliers.memoize(() -> createDefaultWoodType(PETRIFIED_BLOCKSET));

    public static final Supplier<BlockSetType> ZULOAGAE_BLOCKSET = Suppliers.memoize(() -> createBlocksetType("zuloagae", true, SoundType.BAMBOO_WOOD, SoundEvents.BAMBOO_WOOD_DOOR_CLOSE, SoundEvents.BAMBOO_WOOD_DOOR_OPEN, SoundEvents.BAMBOO_WOOD_TRAPDOOR_CLOSE, SoundEvents.BAMBOO_WOOD_TRAPDOOR_OPEN, SoundEvents.BAMBOO_WOOD_PRESSURE_PLATE_CLICK_OFF, SoundEvents.BAMBOO_WOOD_PRESSURE_PLATE_CLICK_ON, SoundEvents.BAMBOO_WOOD_BUTTON_CLICK_OFF, SoundEvents.BAMBOO_WOOD_BUTTON_CLICK_ON));
    public static final Supplier<WoodType> ZULOAGAE_WOOD_TYPE = Suppliers.memoize(() -> createDefaultWoodType(ZULOAGAE_BLOCKSET));

    public static BlockSetType createBlocksetType(String name, boolean canOpenByHand, SoundType soundType, SoundEvent doorClose, SoundEvent doorOpen, SoundEvent trapdoorClose, SoundEvent trapdoorOpen, SoundEvent pressurePlateClickOff, SoundEvent pressurePlateClickOn, SoundEvent buttonClickOff, SoundEvent buttonClickOn) {
        return BlockSetTypeRegistryHelper.register(new BlockSetType(UnusualPrehistory.modPrefix(name).toString(), canOpenByHand, soundType, doorClose, doorOpen, trapdoorClose, trapdoorOpen, pressurePlateClickOff, pressurePlateClickOn, buttonClickOff, buttonClickOn));
    }

    public static WoodType createDefaultWoodType(Supplier<BlockSetType> blockSetType) {
        return createWoodType(blockSetType, SoundType.HANGING_SIGN, SoundEvents.FENCE_GATE_CLOSE, SoundEvents.FENCE_GATE_OPEN);
    }

    public static WoodType createWoodType(Supplier<BlockSetType> blockSetType, SoundType hangingSignSoundType, SoundEvent fenceGateClose, SoundEvent fenceGateOpen) {
        return WoodTypeRegistryHelper.registerWoodType(new WoodType(blockSetType.get().name(), blockSetType.get(), blockSetType.get().soundType(), hangingSignSoundType, fenceGateClose, fenceGateOpen));
    }
}