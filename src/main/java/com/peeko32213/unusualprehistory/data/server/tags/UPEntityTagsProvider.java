package com.peeko32213.unusualprehistory.data.server.tags;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.core.registry.entities.UPEntities;
import com.peeko32213.unusualprehistory.core.other.tags.UPEntityTypeTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class UPEntityTagsProvider extends EntityTypeTagsProvider {


    public UPEntityTagsProvider(PackOutput p_256095_, CompletableFuture<HolderLookup.Provider> p_256572_, @Nullable ExistingFileHelper existingFileHelper) {
        super(p_256095_, p_256572_, UnusualPrehistory.MODID, existingFileHelper);
    }

    protected void addTags(HolderLookup.@NotNull Provider pProvider) {

        tag(UPEntityTypeTags.LAND_MOBS)
                .add(UPEntities.COTY.get())
                .add(UPEntities.PACHY.get())
                .add(UPEntities.MAJUNGA.get())
                .add(UPEntities.ANURO.get())
                .add(UPEntities.BEELZ.get())
                .add(UPEntities.TRICERATOPS.get())
                .add(UPEntities.BRACHI.get())
                .add(UPEntities.VELOCIRAPTOR.get())
                .add(UPEntities.TYRANNOSAURUS.get())
                //TODO the rest of up mobs need to be added!!
                .add(EntityType.CAT)
                .add(EntityType.CHICKEN)
                .add(EntityType.COW)
                .add(EntityType.DONKEY)
                .add(EntityType.FOX)
                .add(EntityType.HORSE)
                .add(EntityType.LLAMA)
                .add(EntityType.MULE)
                .add(EntityType.MOOSHROOM)
                .add(EntityType.OCELOT)
                .add(EntityType.PANDA)
                .add(EntityType.PARROT)
                .add(EntityType.PIG)
                .add(EntityType.RABBIT)
                .add(EntityType.SHEEP)
                .add(EntityType.STRIDER)
                .add(EntityType.TRADER_LLAMA)
                .add(EntityType.WOLF)
                .add(EntityType.POLAR_BEAR)
                .add(EntityType.GOAT)
                .add(EntityType.VILLAGER)
                .add(EntityType.WANDERING_TRADER);

        tag(UPEntityTypeTags.ANTARCTO_TARGETS)
                .add(EntityType.SILVERFISH)
                .add(EntityType.SPIDER)
                .add(EntityType.CAVE_SPIDER)
                .add(EntityType.HUSK)
                .add(EntityType.ENDERMITE)
                .addOptional(new ResourceLocation("alexsmobs:rocky_roller"))
                .addOptional(new ResourceLocation("alexsmobs:anaconda"))
                .addOptional(new ResourceLocation("alexsmobs:underminer"))
                .addOptional(new ResourceLocation("alexsmobs:platypus"))
                .addOptional(new ResourceLocation("alexsmobs:dropbear"))
                .addOptional(new ResourceLocation("alexsmobs:kangaroo"))
                .addOptional(new ResourceLocation("alexsmobs:warped_mosco"))
                .addOptional(new ResourceLocation("alexsmobs:centipede_head"))
                .addOptional(new ResourceLocation("alexsmobs:centipede_body"))
                .addOptional(new ResourceLocation("alexsmobs:centipede_tail"))
                .addOptional(new ResourceLocation("alexsmobs:gazelle"))
                .addOptional(new ResourceLocation("alexsmobs:rattlesnake"))
                .addOptional(new ResourceLocation("alexsmobs:alligator_snapping_turtle"))
                .addOptional(new ResourceLocation("alexsmobs:mungus"))
                .addOptional(new ResourceLocation("alexsmobs:straddler"))
                .addOptional(new ResourceLocation("alexsmobs:tusklin"))
                .addOptional(new ResourceLocation("naturalist:snake"))
                .addOptional(new ResourceLocation("naturalist:rattlesnake"))
                .addOptional(new ResourceLocation("naturalist:deer"))
                .addOptional(new ResourceLocation("naturalist:boar"))
                .addOptional(new ResourceLocation("naturalist:tortoise"))
        ;

        tag(UPEntityTypeTags.ANURO_TARGETS)
                .add(EntityType.SILVERFISH)
                .add(EntityType.SPIDER)
                .add(EntityType.CAVE_SPIDER)
                .add(EntityType.BEE)
                .add(EntityType.ENDERMITE);

        tag(UPEntityTypeTags.BEELZE_TARGETS)
                .add(EntityType.CHICKEN)
                .add(EntityType.RABBIT)
                .add(EntityType.SILVERFISH)
                .add(EntityType.SPIDER)
                .add(EntityType.CAVE_SPIDER)
                .add(EntityType.BEE)
                .add(EntityType.ENDERMITE)
                .addOptional(new ResourceLocation("alexsmobs:rattlesnake"))
                .addOptional(new ResourceLocation("naturalist:snake"))
                .addOptional(new ResourceLocation("naturalist:rattlesnake"))
        ;

        tag(UPEntityTypeTags.BIG_DUNK_TARGETS)
                .add(UPEntities.AMMON.get())
                .add(EntityType.SQUID)
                .add(EntityType.GLOW_SQUID)
                .add(EntityType.AXOLOTL)
                .add(EntityType.TURTLE)
                .add(EntityType.DOLPHIN)
                .add(EntityType.TROPICAL_FISH)
                .add(EntityType.PUFFERFISH)
                .add(EntityType.DROWNED)
                .add(EntityType.GUARDIAN)
        ;

        tag(UPEntityTypeTags.MEDIUM_DUNK_TARGETS)
                .add(EntityType.SQUID)
                .add(EntityType.GLOW_SQUID)
                .add(EntityType.AXOLOTL)
                .add(EntityType.DOLPHIN)
                .add(EntityType.TROPICAL_FISH)
                .add(EntityType.PUFFERFISH)
                .add(EntityType.DROWNED)
        ;

        tag(UPEntityTypeTags.SMALL_DUNK_TARGETS)
                .add(EntityType.AXOLOTL)
                .add(EntityType.TROPICAL_FISH)
        ;

        tag(UPEntityTypeTags.XIPH_TARGETS)

                .add(EntityType.SQUID)
                .add(EntityType.PIG)
                .add(EntityType.COW)
                .add(EntityType.SHEEP)
                .add(EntityType.HORSE)
                .add(EntityType.CHICKEN)
                .add(EntityType.RABBIT)
                .add(EntityType.LLAMA)

                .add(EntityType.GLOW_SQUID)
                .add(EntityType.AXOLOTL)
                .add(EntityType.DOLPHIN)

                .add(EntityType.COD)
                .add(EntityType.SALMON)
                .add(EntityType.TROPICAL_FISH)
                .add(EntityType.PUFFERFISH)
                .add(EntityType.DROWNED)
                .add(EntityType.GUARDIAN)
        ;

        tag(UPEntityTypeTags.HYNERPETON_TARGETS)
                .add(EntityType.SQUID)
                .add(EntityType.GLOW_SQUID)
        ;

        tag(UPEntityTypeTags.GLO_TARGETS)
                .add(UPEntities.AMMON.get())
                .add(EntityType.SQUID)
                .add(EntityType.GLOW_SQUID)
                .add(EntityType.GUARDIAN)
        ;

        tag(UPEntityTypeTags.ENCRUSTED_TARGETS)
                .addTag(UPEntityTypeTags.LAND_MOBS);

        tag(UPEntityTypeTags.SMILODON_TARGETS)
                .add(EntityType.PILLAGER)
                .add(EntityType.EVOKER)
                .add(EntityType.VINDICATOR)
                .add(EntityType.WITCH)
                .add(EntityType.PIGLIN)
                .add(EntityType.PIGLIN_BRUTE)
                .add(EntityType.VILLAGER)
                .add(EntityType.WANDERING_TRADER)
                .addOptional(new ResourceLocation("alexsmobs:rocky_roller"))
                .addOptional(new ResourceLocation("alexsmobs:anaconda"))
                .addOptional(new ResourceLocation("alexsmobs:platypus"))
                .addOptional(new ResourceLocation("alexsmobs:dropbear"))
                .addOptional(new ResourceLocation("alexsmobs:kangaroo"))
                .addOptional(new ResourceLocation("alexsmobs:warped_mosco"))
                .addOptional(new ResourceLocation("alexsmobs:centipede_head"))
                .addOptional(new ResourceLocation("alexsmobs:centipede_body"))
                .addOptional(new ResourceLocation("alexsmobs:centipede_tail"))
                .addOptional(new ResourceLocation("alexsmobs:gazelle"))
                .addOptional(new ResourceLocation("alexsmobs:rattlesnake"))
                .addOptional(new ResourceLocation("alexsmobs:alligator_snapping_turtle"))
                .addOptional(new ResourceLocation("alexsmobs:mungus"))
                .addOptional(new ResourceLocation("alexsmobs:straddler"))
                .addOptional(new ResourceLocation("alexsmobs:tusklin"))
                .addOptional(new ResourceLocation("naturalist:snake"))
                .addOptional(new ResourceLocation("naturalist:rattlesnake"))
                .addOptional(new ResourceLocation("naturalist:deer"))
                .addOptional(new ResourceLocation("naturalist:boar"))
                .addOptional(new ResourceLocation("naturalist:tortoise"))
        ;

        tag(UPEntityTypeTags.MAJUNGA_TARGETS)
                .add(EntityType.PIG)
                .add(EntityType.COW)
                .add(EntityType.SHEEP)
                .add(EntityType.HORSE)
                .add(EntityType.CHICKEN)
                .add(EntityType.RABBIT)
                .add(EntityType.LLAMA)
                .add(EntityType.TURTLE)
                .add(EntityType.GOAT)
                .add(UPEntities.COTY.get())
                .add(UPEntities.PACHY.get())
                .add(UPEntities.MAJUNGA.get())
                .addOptional(new ResourceLocation("alexsmobs:rocky_roller"))
                .addOptional(new ResourceLocation("alexsmobs:anaconda"))
                .addOptional(new ResourceLocation("alexsmobs:bunfungus"))
                .addOptional(new ResourceLocation("alexsmobs:platypus"))
                .addOptional(new ResourceLocation("alexsmobs:dropbear"))
                .addOptional(new ResourceLocation("alexsmobs:kangaroo"))
                .addOptional(new ResourceLocation("alexsmobs:warped_mosco"))
                .addOptional(new ResourceLocation("alexsmobs:elephant"))
                .addOptional(new ResourceLocation("alexsmobs:bison"))
                .addOptional(new ResourceLocation("alexsmobs:centipede_head"))
                .addOptional(new ResourceLocation("alexsmobs:centipede_body"))
                .addOptional(new ResourceLocation("alexsmobs:centipede_tail"))
                .addOptional(new ResourceLocation("alexsmobs:warped_toad"))
                .addOptional(new ResourceLocation("alexsmobs:gorilla"))
                .addOptional(new ResourceLocation("alexsmobs:crocodile"))
                .addOptional(new ResourceLocation("alexsmobs:gazelle"))
                .addOptional(new ResourceLocation("alexsmobs:rattlesnake"))
                .addOptional(new ResourceLocation("alexsmobs:moose"))
                .addOptional(new ResourceLocation("alexsmobs:alligator_snapping_turtle"))
                .addOptional(new ResourceLocation("alexsmobs:mungus"))
                .addOptional(new ResourceLocation("alexsmobs:straddler"))
                .addOptional(new ResourceLocation("alexsmobs:tusklin"))
                .addOptional(new ResourceLocation("naturalist:bear"))
                .addOptional(new ResourceLocation("naturalist:snake"))
                .addOptional(new ResourceLocation("naturalist:rattlesnake"))
                .addOptional(new ResourceLocation("naturalist:deer"))
                .addOptional(new ResourceLocation("naturalist:rhino"))
                .addOptional(new ResourceLocation("naturalist:lion"))
                .addOptional(new ResourceLocation("naturalist:elephant"))
                .addOptional(new ResourceLocation("naturalist:zebra"))
                .addOptional(new ResourceLocation("naturalist:giraffe"))
                .addOptional(new ResourceLocation("naturalist:hippo"))
                .addOptional(new ResourceLocation("naturalist:boar"))
                .addOptional(new ResourceLocation("naturalist:alligator"))
                .addOptional(new ResourceLocation("naturalist:tortoise"))
        ;

        tag(UPEntityTypeTags.RAPTOR_TARGETS)
                .add(EntityType.PIG)
                .add(EntityType.COW)
                .add(EntityType.SHEEP)
                .add(EntityType.HORSE)
                .add(EntityType.CHICKEN)
                .add(EntityType.RABBIT)
                .add(EntityType.LLAMA)
                .add(EntityType.TURTLE)
                .add(EntityType.GOAT)
                .add(UPEntities.COTY.get())
                .add(UPEntities.PACHY.get())
                .addOptional(new ResourceLocation("alexsmobs:rocky_roller"))
                .addOptional(new ResourceLocation("alexsmobs:anaconda"))
                .addOptional(new ResourceLocation("alexsmobs:platypus"))
                .addOptional(new ResourceLocation("alexsmobs:dropbear"))
                .addOptional(new ResourceLocation("alexsmobs:kangaroo"))
                .addOptional(new ResourceLocation("alexsmobs:warped_mosco"))
                .addOptional(new ResourceLocation("alexsmobs:centipede_head"))
                .addOptional(new ResourceLocation("alexsmobs:centipede_body"))
                .addOptional(new ResourceLocation("alexsmobs:centipede_tail"))
                .addOptional(new ResourceLocation("alexsmobs:gazelle"))
                .addOptional(new ResourceLocation("alexsmobs:rattlesnake"))
                .addOptional(new ResourceLocation("alexsmobs:alligator_snapping_turtle"))
                .addOptional(new ResourceLocation("alexsmobs:mungus"))
                .addOptional(new ResourceLocation("alexsmobs:straddler"))
                .addOptional(new ResourceLocation("alexsmobs:tusklin"))
                .addOptional(new ResourceLocation("naturalist:snake"))
                .addOptional(new ResourceLocation("naturalist:rattlesnake"))
                .addOptional(new ResourceLocation("naturalist:deer"))
                .addOptional(new ResourceLocation("naturalist:boar"))
                .addOptional(new ResourceLocation("naturalist:tortoise"))
        ;

        tag(UPEntityTypeTags.TYRANNOSAURUS_TARGETS)
                .add(EntityType.PIG)
                .add(EntityType.COW)
                .add(EntityType.SHEEP)
                .add(EntityType.HORSE)
                .add(EntityType.LLAMA)
                .add(EntityType.TURTLE)
                .add(EntityType.GOAT)
                .add(UPEntities.COTY.get())
                .add(UPEntities.TRICERATOPS.get())
        ;

//        tag(UPTags.PSITTACO_TARGETS)
//                .add(UPEntities.BEELZ.get())
//                .add(UPEntities.VELOCIRAPTOR.get())
//                .add(UPEntities.MAJUNGA.get())
//                .add(UPEntities.SMILODON.get())
//        ;
//

        tag(UPEntityTypeTags.OPHIODON_TARGETS)
                .add(EntityType.TROPICAL_FISH)
                .add(EntityType.SALMON)
                ;

        tag(UPEntityTypeTags.PROTOSPHYRAENA_TARGETS)
                .add(EntityType.SQUID)
        ;

        tag(UPEntityTypeTags.HYNERIA_TARGETS)
                .add(EntityType.SQUID)
                .add(EntityType.TROPICAL_FISH)
                .add(EntityType.SALMON)
                .add(UPEntities.DIPLOCAULUS.get())
//                .add(UPEntities.HYNERPETON.get())
        ;

        tag(UPEntityTypeTags.MEGALANIA_TARGETS)
                .add(EntityType.PIG)
                .add(EntityType.COW)
                .add(EntityType.SHEEP)
                .add(EntityType.HORSE)
                .add(EntityType.CHICKEN)
                .add(EntityType.RABBIT)
                .add(EntityType.LLAMA)
                .add(EntityType.TURTLE)
                .add(EntityType.GOAT)
                .add(UPEntities.COTY.get())
                .add(UPEntities.PACHY.get())
                .add(UPEntities.MAJUNGA.get())
                .addOptional(new ResourceLocation("alexsmobs:rocky_roller"))
                .addOptional(new ResourceLocation("alexsmobs:anaconda"))
                .addOptional(new ResourceLocation("alexsmobs:bunfungus"))
                .addOptional(new ResourceLocation("alexsmobs:platypus"))
                .addOptional(new ResourceLocation("alexsmobs:dropbear"))
                .addOptional(new ResourceLocation("alexsmobs:kangaroo"))
                .addOptional(new ResourceLocation("alexsmobs:warped_mosco"))
                .addOptional(new ResourceLocation("alexsmobs:elephant"))
                .addOptional(new ResourceLocation("alexsmobs:centipede_head"))
                .addOptional(new ResourceLocation("alexsmobs:centipede_body"))
                .addOptional(new ResourceLocation("alexsmobs:centipede_tail"))
                .addOptional(new ResourceLocation("alexsmobs:warped_toad"))
                .addOptional(new ResourceLocation("alexsmobs:gorilla"))
                .addOptional(new ResourceLocation("alexsmobs:crocodile"))
                .addOptional(new ResourceLocation("alexsmobs:gazelle"))
                .addOptional(new ResourceLocation("alexsmobs:rattlesnake"))
                .addOptional(new ResourceLocation("alexsmobs:moose"))
                .addOptional(new ResourceLocation("alexsmobs:alligator_snapping_turtle"))
                .addOptional(new ResourceLocation("alexsmobs:mungus"))
                .addOptional(new ResourceLocation("alexsmobs:straddler"))
                .addOptional(new ResourceLocation("alexsmobs:tusklin"))
                .addOptional(new ResourceLocation("naturalist:bear"))
                .addOptional(new ResourceLocation("naturalist:snake"))
                .addOptional(new ResourceLocation("naturalist:rattlesnake"))
                .addOptional(new ResourceLocation("naturalist:deer"))
                .addOptional(new ResourceLocation("naturalist:rhino"))
                .addOptional(new ResourceLocation("naturalist:lion"))
                .addOptional(new ResourceLocation("naturalist:elephant"))
                .addOptional(new ResourceLocation("naturalist:zebra"))
                .addOptional(new ResourceLocation("naturalist:giraffe"))
                .addOptional(new ResourceLocation("naturalist:hippo"))
                .addOptional(new ResourceLocation("naturalist:boar"))
                .addOptional(new ResourceLocation("naturalist:alligator"))
                .addOptional(new ResourceLocation("naturalist:tortoise"))

        ;

        tag(UPEntityTypeTags.SMILODON_EMBRYO_ATTACH_TO)
                .add(EntityType.OCELOT)
                .add(UPEntities.SMILODON.get());

        tag(UPEntityTypeTags.MAMMOTH_EMBRYO_ATTACH_TO)
                .add(UPEntities.MAMMOTH.get());

        tag(UPEntityTypeTags.MEGATH_EMBRYO_ATTACH_TO)
                .add(UPEntities.MEGATHERIUM.get());

        tag(UPEntityTypeTags.GIGANTO_EMBRYO_ATTACH_TO)
                .add(UPEntities.GIGANTOPITHICUS.get());

        tag(UPEntityTypeTags.PARACER_EMBRYO_ATTACH_TO)
                .add(UPEntities.PARACERATHERIUM.get());

        tag(UPEntityTypeTags.PALAEO_EMBRYO_ATTACH_TO)
                .add(UPEntities.PALAEOPHIS.get())
                .add(UPEntities.PALAEOPHIS_PART.get());

        tag(UPEntityTypeTags.OTAROCYON_EMBRYO_ATTACH_TO)
                .add(EntityType.OCELOT)
                .add(EntityType.FOX)
        ;

        tag(UPEntityTypeTags.TAR_WALKABLE_ON_MOBS)
                .add(EntityType.RABBIT)
                .add(UPEntities.SLUDGE.get());

        tag(UPEntityTypeTags.TAR_WALKABLE_THROUGH_MOBS)
                .add(EntityType.RAVAGER);

        tag(UPEntityTypeTags.SCATTERS_TELECREX);

        tag(UPEntityTypeTags.PACHY_AVOIDS).add(
                UPEntities.MAJUNGA.get(),
                UPEntities.TYRANNOSAURUS.get(),
                UPEntities.ULUG.get(),
                UPEntities.BARINASUCHUS.get(),
                UPEntities.HWACHA.get()
        );

        tag(UPEntityTypeTags.STETHA_AVOIDS).add(
                UPEntities.DUNK.get(),
                UPEntities.LEEDSICHTHYS.get(),
                UPEntities.KAPROSUCHUS.get()
        );

        tag(UPEntityTypeTags.STETHA_TARGETS).add(
                UPEntities.SCAU.get(),
                UPEntities.JAWLESS_FISH.get(),
                EntityType.SALMON,
                EntityType.COD,
                EntityType.TROPICAL_FISH
        );

        tag(UPEntityTypeTags.HERBIVORES)
                .add(UPEntities.ANTARCO.get())
                .add(UPEntities.BRACHI.get())
                .add(UPEntities.TRICERATOPS.get())
                .add(UPEntities.COTY.get())
                .add(UPEntities.KENTRO.get())
                .add(UPEntities.PACHY.get())
                .add(UPEntities.MEGATHERIUM.get())
                .add(UPEntities.MAMMOTH.get());

        tag(UPEntityTypeTags.CARNIVORES)
                .add(UPEntities.ANURO.get())
                .add(UPEntities.AUSTRO.get())
                .add(UPEntities.BARINASUCHUS.get())
                .add(UPEntities.BEELZ.get())
                .add(UPEntities.DUNK.get())
                .add(UPEntities.TYRANNOSAURUS.get())
                .add(UPEntities.HWACHA.get())
                .add(UPEntities.SMILODON.get())
                .add(UPEntities.ULUG.get())
                .add(UPEntities.VELOCIRAPTOR.get())
                .add(UPEntities.MAJUNGA.get())
                .add(UPEntities.SLUDGE.get())
        ;

        tag(UPEntityTypeTags.PISCIVORE_DIET)
                .add(EntityType.TROPICAL_FISH)
                .add(EntityType.COD)
                .add(EntityType.SALMON)
        ;
    }

    @Override
    public @NotNull String getName() {
        return UnusualPrehistory.MODID + " Entity type tags provider";
    }
}
