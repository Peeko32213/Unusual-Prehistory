package com.peeko32213.unusualprehistory.core.registry.blocks;

import com.teamabnormals.blueprint.core.util.registry.BlockSubRegistryHelper;
import com.teamabnormals.blueprint.core.util.registry.RegistryHelper;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

public class UPBlockSubRegistryHelper extends BlockSubRegistryHelper {

    public UPBlockSubRegistryHelper(RegistryHelper parent) {
        super(parent, parent.getItemSubHelper().getDeferredRegister(), parent.getBlockSubHelper().getDeferredRegister());
    }
}
