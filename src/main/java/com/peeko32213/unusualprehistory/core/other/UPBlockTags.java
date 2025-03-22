package com.peeko32213.unusualprehistory.core.other;

import com.peeko32213.unusualprehistory.core.UnusualPrehistory;
import com.teamabnormals.blueprint.core.util.TagUtil;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class UPBlockTags {

    public static final TagKey<Block> DRYO_LOGS = blockTag("dryophyllum_logs");
    public static final TagKey<Block> FOXII_LOGS = blockTag("foxii_logs");

    private static TagKey<Block> blockTag(String tagName) {
        return TagUtil.blockTag(UnusualPrehistory.MODID, tagName);
    }
}
