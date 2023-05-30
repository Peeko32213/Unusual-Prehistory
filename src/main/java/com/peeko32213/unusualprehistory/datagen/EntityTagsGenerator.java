package com.peeko32213.unusualprehistory.datagen;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.core.registry.UPEntities;
import com.peeko32213.unusualprehistory.core.registry.UPTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.common.data.ExistingFileHelper;

public class EntityTagsGenerator extends EntityTypeTagsProvider {


    public EntityTagsGenerator(DataGenerator pGenerator, ExistingFileHelper existingFileHelper) {
        super(pGenerator, UnusualPrehistory.MODID, existingFileHelper);
    }

    protected void addTags() {

        /**Example**/
        //tag(UPTags.ANURO_TARGETS).add(UPEntities.BABY_BRACHI.get());

        tag(UPTags.LAND_MOBS)
                .add(UPEntities.COTY.get())
                .add(UPEntities.PACHY.get())
                .add(UPEntities.MAJUNGA.get())
                .add(UPEntities.ANURO.get())
                .add(UPEntities.BEELZ.get())
                .add(UPEntities.TRIKE.get())
                .add(UPEntities.BRACHI.get())
                .add(UPEntities.VELOCI.get())
                .add(UPEntities.REX.get())
                .add(UPEntities.BABY_REX.get())
                .add(UPEntities.BABY_BRACHI.get())

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
                .add(EntityType.ENDERMITE);

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
                .add(EntityType.ENDERMITE);

        tag(UPTags.DUNK_TARGETS)
                .add(UPEntities.AMMON.get())
                .add(UPEntities.COTY.get())
                .add(UPEntities.MAJUNGA.get())

                .add(EntityType.SQUID)
                .add(EntityType.PIG)
                .add(EntityType.COW)
                .add(EntityType.SHEEP)
                .add(EntityType.HORSE)
                .add(EntityType.CHICKEN)
                .add(EntityType.RABBIT)
                .add(EntityType.LLAMA)

                .add(EntityType.SILVERFISH)
                .add(EntityType.SPIDER)
                .add(EntityType.CAVE_SPIDER)
                .add(EntityType.BEE)
                .add(EntityType.ENDERMITE)

                .add(EntityType.GLOW_SQUID)
                .add(EntityType.AXOLOTL)
                .add(EntityType.TURTLE)
                .add(EntityType.DOLPHIN)

                .add(EntityType.TROPICAL_FISH)
                .add(EntityType.PUFFERFISH)
                .add(EntityType.DROWNED)
                .add(EntityType.GUARDIAN);

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
                .add(EntityType.WANDERING_TRADER);

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
                .add(UPEntities.MAJUNGA.get());

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
                .add(UPEntities.PACHY.get());

        tag(UPTags.REX_TARGETS);
    }

    @Override
    public String getName() {
        return UnusualPrehistory.MODID + " Entity type tags provider";
    }
}
