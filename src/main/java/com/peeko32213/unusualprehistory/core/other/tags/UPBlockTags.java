package com.peeko32213.unusualprehistory.core.other.tags;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.teamabnormals.blueprint.core.util.TagUtil;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class UPBlockTags {

    public static final TagKey<Block> VELOCI_BUTTONS = blockTag("velociraptor_buttons");

    public static final TagKey<Block> NONE_BLOCK_TAG = blockTag("none_block_tag");

    public static final TagKey<Block> CLUB_WHITELIST_BLOCKS = blockTag("club_whitelist_blocks");
    public static final TagKey<Block> ZULOAGAE_PLANTABLE_ON = blockTag("zuloagae_plantable_on");
    public static final TagKey<Block> TRIKE_BREAKABLES = blockTag("trike_breakables");
    public static final TagKey<Block> TAR_PIT_REPLACEABLE = blockTag("tar_pit_replaceable");
    public static final TagKey<Block> PASSIVE_BRACHI_BREAKABLES = blockTag("passive_brachi_breakables");
    public static final TagKey<Block> REX_BREAKABLES = blockTag("rex_breakables");
    public static final TagKey<Block> ANGRY_BRACHI_BREAKABLES = blockTag("angry_brachi_breakables");
    public static final TagKey<Block> ERYON_DIGGABLES = blockTag("eryon_diggables");
    public static final TagKey<Block> TALPANAS_DIGGABLES = blockTag("talpanas_diggables");
    public static final TagKey<Block> TYRANNO_BREAKABLES = blockTag("tyrannosaurus_breakables");

    public static final TagKey<Block> DINO_HATCHABLE_BLOCKS = blockTag("dino_hatchable_blocks");

    public static final TagKey<Block> DIPLO_DIGS = blockTag("diplocaulus_burrow_blocks");

    public static final TagKey<Block> TRIKE_GRAZING_BLOCKS = blockTag("triceratops_grazing_blocks");

    public static final TagKey<Block> COTY_GRAZING_BLOCKS = blockTag("cotylorhynchus_grazing_blocks");

    public static final TagKey<Block> MEGATHERIUM_EATABLES = blockTag("megatherium_breakables");
    public static final TagKey<Block> MEGATHERIUM_MINEABLES = blockTag("megatherium_mineables");

    public static final TagKey<Block> DINO_NATURAL_SPAWNABLE = blockTag("dino_natural_spawnable");

    private static TagKey<Block> blockTag(String name) {
        return TagUtil.blockTag(UnusualPrehistory.MODID, name);
    }
}
