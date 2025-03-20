package com.peeko32213.unusualprehistory.core.registry;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.block.custom.FruitLootBoxBlock;
import com.peeko32213.unusualprehistory.common.item.CaptureFlaskItem;
import com.peeko32213.unusualprehistory.common.item.UPFishBucketItem;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;

public class UPItemProperties {
    public static void addItemProperties() {
        makeFruitLootBox(UPBlocks.FRUIT_LOOT_BOX.get().asItem());

        for (RegistryObject<Item> item : UPItems.ITEMS.getEntries()) {
            if (item.get() instanceof CaptureFlaskItem || item.get() instanceof UPFishBucketItem) {
                registerVariantProperties(item.get());
            }
        }

    }

    private static void makeFruitLootBox(Item item) {
        ItemProperties.register(item, new ResourceLocation("loot_box"), (itemStack, clientLevel, livingEntity, nr) -> FruitLootBoxBlock.getCustomModelData(itemStack.getOrCreateTag()));
    }


    private static void registerVariantProperties(Item item) {
        ItemProperties.register(item, new ResourceLocation(UnusualPrehistory.MODID, "variant"), (stack, world, player, i) -> stack.hasTag() ? stack.getTag().getInt("Variant") : 0);
    }
}
