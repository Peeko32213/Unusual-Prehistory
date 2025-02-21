package com.peeko32213.unusualprehistory.core.registry;

import com.peeko32213.unusualprehistory.common.block.custom.FruitLootBoxBlock;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class UPItemProperties {
    public static void addItemProperties() {
        makeFruitLootBox(UPBlocks.FRUIT_LOOT_BOX.get().asItem());
    }

    private static void makeFruitLootBox(Item item) {
        ItemProperties.register(item, new ResourceLocation("loot_box"), (itemStack, clientLevel, livingEntity, nr) -> FruitLootBoxBlock.getCustomModelData(itemStack.getOrCreateTag()));
    }

}
