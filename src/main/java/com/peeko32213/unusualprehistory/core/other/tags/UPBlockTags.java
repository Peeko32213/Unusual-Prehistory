package com.peeko32213.unusualprehistory.core.other.tags;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.teamabnormals.blueprint.core.util.TagUtil;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class UPBlockTags {

    public static final TagKey<Block> DRYO_LOGS = blockTag("dryophyllum_logs");
    public static final TagKey<Block> FOXII_LOGS = blockTag("foxii_logs");
    public static final TagKey<Block> GINKGO_LOGS = blockTag("ginkgo_logs");
    public static final TagKey<Block> PETRIFIED_LOGS = blockTag("petrified_logs");

    public static final TagKey<Block> VELOCI_BUTTONS = blockTag("velociraptor_buttons");

    public static final TagKey<Block> CLUB_WHITELIST_BLOCKS = blockTag("club_whitelist_blocks");
    public static final TagKey<Block> TAR_PIT_REPLACEABLE = blockTag("tar_pit_replaceable");

    public static final TagKey<Block> ERYON_DIGGABLES = blockTag("eryon_diggables");
    public static final TagKey<Block> TALPANAS_DIGGABLES = blockTag("talpanas_diggables");

    public static final TagKey<Block> TYRANNO_BREAKABLES = blockTag("tyrannosaurus_breakables");

    public static final TagKey<Block> EGG_ACCELERATORS = blockTag("egg_accelerator_blocks");

    public static final TagKey<Block> DIPLO_BURROWS = blockTag("diplocaulus_burrow_blocks");
    public static final TagKey<Block> TRIKE_GRAZING_BLOCKS = blockTag("triceratops_grazing_blocks");
    public static final TagKey<Block> COTY_GRAZING_BLOCKS = blockTag("cotylorhynchus_grazing_blocks");
    public static final TagKey<Block> PACHY_GRAZING_BLOCKS = blockTag("pachycephalosaurus_grazing_blocks");

    public static final TagKey<Block> MEGATHERIUM_MINEABLES = blockTag("megatherium_mineables");

    public static final TagKey<Block> DINO_NATURAL_SPAWNABLE = blockTag("dino_natural_spawnable");

    // Plant placeable blocks
    public static final TagKey<Block> ZULOAGAE_PLANTABLE_ON = blockTag("zuloagae_plantable_on");
    public static final TagKey<Block> ISOETES_BEESTONII_PLACEABLE = blockTag("isoetes_beestonii_placeable");
    public static final TagKey<Block> CLADOPHLEBIS_PLACEABLE = blockTag("cladophlebis_placeable");
    public static final TagKey<Block> HORSETAIL_PLACEABLE = blockTag("horsetail_placeable");
    public static final TagKey<Block> CALAMOPHYTON_PLACEABLE = blockTag("calamophyton_placeable");

    private static TagKey<Block> blockTag(String name) {
        return TagUtil.blockTag(UnusualPrehistory.MODID, name);
    }
}
