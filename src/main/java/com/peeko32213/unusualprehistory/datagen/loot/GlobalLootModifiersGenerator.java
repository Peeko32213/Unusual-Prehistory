package com.peeko32213.unusualprehistory.datagen.loot;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.loot.AddItemModifier;
import com.peeko32213.unusualprehistory.core.registry.UPItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.minecraftforge.common.loot.LootTableIdCondition;

public class GlobalLootModifiersGenerator extends GlobalLootModifierProvider {
    public GlobalLootModifiersGenerator(PackOutput output){
        super(output, UnusualPrehistory.MODID);
    }

    @Override
    protected void start() {
        add("add_encyclopedia_bonus_chest", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("chests/spawn_bonus_chest")).build() }, UPItems.ENCYLOPEDIA.get(), 1));
    }
}
