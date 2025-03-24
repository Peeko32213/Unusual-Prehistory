package com.peeko32213.unusualprehistory.data.server.tags;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

import static com.peeko32213.unusualprehistory.core.registry.UPDamageTypes.SHURIKEN;

public class UPDamageTypeTagsProvider extends TagsProvider<DamageType> {

    public UPDamageTypeTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> future, ExistingFileHelper helper) {
        super(output, Registries.DAMAGE_TYPE, future, UnusualPrehistory.MODID, helper);
    }

    protected void addTags(HolderLookup.Provider provider) {
        this.tag(DamageTypeTags.BYPASSES_ARMOR)
        ;

        this.tag(DamageTypeTags.DAMAGES_HELMET)
        ;

        this.tag(DamageTypeTags.IS_PROJECTILE)
                .add(SHURIKEN)
        ;

        this.tag(DamageTypeTags.IS_FIRE)
        ;

        this.tag(DamageTypeTags.BYPASSES_RESISTANCE)
        ;

        this.tag(DamageTypeTags.ALWAYS_MOST_SIGNIFICANT_FALL)
        ;

        this.tag(DamageTypeTags.IS_FALL)
        ;

        this.tag(DamageTypeTags.NO_ANGER)
        ;

        this.tag(DamageTypeTags.BYPASSES_INVULNERABILITY)
        ;

        this.tag(DamageTypeTags.BYPASSES_ENCHANTMENTS)
        ;

        this.tag(DamageTypeTags.WITCH_RESISTANT_TO)
        ;
    }

    private static TagKey<DamageType> create(String name) {
        return TagKey.create(Registries.DAMAGE_TYPE, UnusualPrehistory.modPrefix(name));
    }
}
