package com.peeko32213.unusualprehistory.core.registry;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.world.feature.tree.trunkplacer.GiantTrunkPlacerWithRoots;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class UPTrunkPlacerType {
    public static final DeferredRegister<TrunkPlacerType<?>> TRUNK_PLACER_TYPES = DeferredRegister.create(Registries.TRUNK_PLACER_TYPE, UnusualPrehistory.MODID);
    public static final RegistryObject<TrunkPlacerType<GiantTrunkPlacerWithRoots>> GIANT_TRUNK_PLACER_WITH_ROOTS = TRUNK_PLACER_TYPES.register("giant_trunk_placer_with_roots", ()-> new TrunkPlacerType<>(GiantTrunkPlacerWithRoots.CODEC));


}
