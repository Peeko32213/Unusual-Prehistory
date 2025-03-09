package com.peeko32213.unusualprehistory.datagen.tags;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.core.registry.UPEntities;
import com.peeko32213.unusualprehistory.core.registry.UPTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class EntityTagsGenerator extends EntityTypeTagsProvider {


    public EntityTagsGenerator(PackOutput p_256095_, CompletableFuture<HolderLookup.Provider> p_256572_, @Nullable ExistingFileHelper existingFileHelper) {
        super(p_256095_, p_256572_, UnusualPrehistory.MODID, existingFileHelper);
    }

    protected void addTags(HolderLookup.@NotNull Provider pProvider) {

        /**Example**/
        //tag(UPTags.ANURO_TARGETS).add(UPEntities.BABY_BRACHI.get());

        tag(UPTags.LAND_MOBS)
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

        tag(UPTags.ANTARCTO_TARGETS)
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

        tag(UPTags.ANURO_TARGETS)
                .add(EntityType.SILVERFISH)
                .add(EntityType.SPIDER)
                .add(EntityType.CAVE_SPIDER)
                .add(EntityType.BEE)
                .add(EntityType.ENDERMITE);

        tag(UPTags.BEELZE_TARGETS)
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

        tag(UPTags.DUNK_TARGETS)
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

        tag(UPTags.XIPH_TARGETS)

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

        tag(UPTags.HYNERPETON_TARGETS)
                .add(EntityType.SQUID)
                .add(EntityType.GLOW_SQUID)
        ;

        tag(UPTags.GLO_TARGETS)
                .add(UPEntities.AMMON.get())
                .add(EntityType.SQUID)
                .add(EntityType.GLOW_SQUID)
                .add(EntityType.GUARDIAN)
        ;

        tag(UPTags.ENCRUSTED_TARGETS)
                .addTag(UPTags.LAND_MOBS);

        tag(UPTags.SMILODON_TARGETS)
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

        tag(UPTags.MAJUNGA_TARGETS)
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

        tag(UPTags.RAPTOR_TARGETS)
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

        tag(UPTags.TYRANNOSAURUS_TARGETS)
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

        tag(UPTags.OPHIODON_TARGETS)
                .add(EntityType.TROPICAL_FISH)
                .add(EntityType.SALMON)
                ;

        tag(UPTags.PROTOSPHYRAENA_TARGETS)
                .add(EntityType.SQUID)
        ;

        tag(UPTags.HYNERIA_TARGETS)
                .add(EntityType.SQUID)
                .add(EntityType.TROPICAL_FISH)
                .add(EntityType.SALMON)
                .add(UPEntities.DIPLOCAULUS.get())
//                .add(UPEntities.HYNERPETON.get())
        ;

        tag(UPTags.MEGALANIA_TARGETS)
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

        tag(UPTags.SMILODON_EMBRYO_ATTACH_TO)
                .add(EntityType.OCELOT)
                .add(UPEntities.SMILODON.get());

        tag(UPTags.MAMMOTH_EMBRYO_ATTACH_TO)
                .add(UPEntities.MAMMOTH.get());

        tag(UPTags.MEGATH_EMBRYO_ATTACH_TO)
                .add(UPEntities.MEGATHERIUM.get());

        tag(UPTags.GIGANTO_EMBRYO_ATTACH_TO)
                .add(UPEntities.GIGANTOPITHICUS.get());

        tag(UPTags.PARACER_EMBRYO_ATTACH_TO)
                .add(UPEntities.PARACERATHERIUM.get());

        tag(UPTags.PALAEO_EMBRYO_ATTACH_TO)
                .add(UPEntities.PALAEOPHIS.get())
                .add(UPEntities.PALAEOPHIS_PART.get());

        tag(UPTags.OTAROCYON_EMBRYO_ATTACH_TO)
                .add(EntityType.OCELOT)
                .add(EntityType.FOX)
                .add(UPEntities.OTAROCYON.get());

        tag(UPTags.TAR_WALKABLE_ON_MOBS)
                .add(EntityType.RABBIT)
                .add(UPEntities.SLUDGE.get());

        tag(UPTags.TAR_WALKABLE_THROUGH_MOBS)
                .add(EntityType.RAVAGER);

        tag(UPTags.HERBIVORES)
                .add(UPEntities.ANTARCO.get())
                .add(UPEntities.BRACHI.get())
                .add(UPEntities.TRICERATOPS.get())
                .add(UPEntities.COTY.get())
                .add(UPEntities.KENTRO.get())
                .add(UPEntities.PACHY.get())
                .add(UPEntities.MEGATHERIUM.get())
                .add(UPEntities.MAMMOTH.get());

        tag(UPTags.CARNIVORES)
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
                .add(UPEntities.OTAROCYON.get())
                .add(UPEntities.LONGISQUAMA.get())
        ;

        tag(UPTags.PISCIVORE_DIET)
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
