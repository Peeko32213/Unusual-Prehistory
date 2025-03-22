package com.peeko32213.unusualprehistory.core.other;

import com.peeko32213.unusualprehistory.core.registry.UPBlocks;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;

public class UPClientCompat {

    public static void registerClientCompat() {
        registerRenderLayers();
        registerBlockColors();
        registerItemProperties();
    }

    private static void registerItemProperties() {
    }

    private static void registerRenderLayers() {
        ItemBlockRenderTypes.setRenderLayer(UPBlocks.DRYO_DOOR.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(UPBlocks.DRYO_TRAPDOOR.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(UPBlocks.DRYO_LEAVES.get(), RenderType.cutoutMipped());
        ItemBlockRenderTypes.setRenderLayer(UPBlocks.DRYO_SAPLING.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(UPBlocks.POTTED_DRYO_SAPLING.get(), RenderType.cutout());

        ItemBlockRenderTypes.setRenderLayer(UPBlocks.FOXII_DOOR.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(UPBlocks.FOXII_TRAPDOOR.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(UPBlocks.FOXII_LEAVES.get(), RenderType.cutoutMipped());
        ItemBlockRenderTypes.setRenderLayer(UPBlocks.FOXII_SAPLING.get(), RenderType.cutout());
    }

    private static void registerBlockColors() {
    }

}
