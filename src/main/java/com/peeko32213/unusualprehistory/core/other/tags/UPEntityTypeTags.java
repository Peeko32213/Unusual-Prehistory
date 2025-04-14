package com.peeko32213.unusualprehistory.core.other.tags;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.teamabnormals.blueprint.core.util.TagUtil;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;

public class UPEntityTypeTags {

    // Target tags
    public static final TagKey<EntityType<?>> ANURO_TARGETS = entityTypeTag("anuro_targets");
    public static final TagKey<EntityType<?>> MAJUNGA_TARGETS = entityTypeTag("majunga_targets");
    public static final TagKey<EntityType<?>> MEGALANIA_TARGETS = entityTypeTag("megalania_targets");
    public static final TagKey<EntityType<?>> BEELZE_TARGETS = entityTypeTag("beelze_targets");
    public static final TagKey<EntityType<?>> TYRANNOSAURUS_TARGETS = entityTypeTag("tyrannosaurus_targets");
    public static final TagKey<EntityType<?>> RAPTOR_TARGETS = entityTypeTag("raptor_targets");
    public static final TagKey<EntityType<?>> ANTARCTO_TARGETS = entityTypeTag("antarcto_targets");
    public static final TagKey<EntityType<?>> ENCRUSTED_TARGETS = entityTypeTag("encrusted_targets");
    public static final TagKey<EntityType<?>> BIG_DUNK_TARGETS = entityTypeTag("large_dunkleosteus_targets");
    public static final TagKey<EntityType<?>> MEDIUM_DUNK_TARGETS = entityTypeTag("medium_dunkleosteus_targets");
    public static final TagKey<EntityType<?>> SMALL_DUNK_TARGETS = entityTypeTag("small_dunkleosteus_targets");
    public static final TagKey<EntityType<?>> HYNERIA_TARGETS = entityTypeTag("hyneria_targets");
    public static final TagKey<EntityType<?>> LAND_MOBS = entityTypeTag("land_mobs");
    public static final TagKey<EntityType<?>> PSITTACO_TARGETS = entityTypeTag("psittaco_targets");
    public static final TagKey<EntityType<?>> SMILODON_TARGETS = entityTypeTag("smilodon_targets");
    public static final TagKey<EntityType<?>> OPHIODON_TARGETS = entityTypeTag("ophiodon_targets");
    public static final TagKey<EntityType<?>> PROTOSPHYRAENA_TARGETS = entityTypeTag("protosphyraena_targets");
    public static final TagKey<EntityType<?>> XIPH_TARGETS = entityTypeTag("xiphactinus_targets");
    public static final TagKey<EntityType<?>> HYNERPETON_TARGETS = entityTypeTag("hynerpeton_targets");
    public static final TagKey<EntityType<?>> GLO_TARGETS = entityTypeTag("globidens_targets");

    public static final TagKey<EntityType<?>> HERBIVORES = entityTypeTag("herbivores");
    public static final TagKey<EntityType<?>> CARNIVORES = entityTypeTag("carnivores");
    public static final TagKey<EntityType<?>> OMNIVORES = entityTypeTag("omnivores");

    public static final TagKey<EntityType<?>> PISCIVORE_DIET = entityTypeTag("piscivore_diet");

    public static final TagKey<EntityType<?>> SMILODON_EMBRYO_ATTACH_TO= entityTypeTag("smilodon_embryo_attach_to");
    public static final TagKey<EntityType<?>> MAMMOTH_EMBRYO_ATTACH_TO= entityTypeTag("mammoth_embryo_attach_to");
    public static final TagKey<EntityType<?>> MEGATH_EMBRYO_ATTACH_TO= entityTypeTag("megath_embryo_attach_to");
    public static final TagKey<EntityType<?>> GIGANTO_EMBRYO_ATTACH_TO= entityTypeTag("giganto_embryo_attach_to");
    public static final TagKey<EntityType<?>> PARACER_EMBRYO_ATTACH_TO= entityTypeTag("paracer_embryo_attach_to");
    public static final TagKey<EntityType<?>> PALAEO_EMBRYO_ATTACH_TO= entityTypeTag("palaeo_embryo_attach_to");

    public static final TagKey<EntityType<?>> OTAROCYON_EMBRYO_ATTACH_TO= entityTypeTag("otarocyon_embryo_attach_to");
    public static final TagKey<EntityType<?>> TAR_WALKABLE_ON_MOBS= entityTypeTag("tar_walkable_on_mobs");
    public static final TagKey<EntityType<?>> TAR_WALKABLE_THROUGH_MOBS= entityTypeTag("tar_walkable_through_mobs");

    public static final TagKey<EntityType<?>> STETHA_TARGETS = entityTypeTag("stethacanthus_targets");

    public static final TagKey<EntityType<?>> PACHY_AVOIDS = entityTypeTag("pachycephalosaurus_avoids");
    public static final TagKey<EntityType<?>> SCATTERS_TELECREX= entityTypeTag("scatters_telecrex");
    public static final TagKey<EntityType<?>> STETHA_AVOIDS = entityTypeTag("stethacanthus_avoids");

    private static TagKey<EntityType<?>> entityTypeTag(String name) {
        return TagUtil.entityTypeTag(UnusualPrehistory.MODID, name);
    }
}
