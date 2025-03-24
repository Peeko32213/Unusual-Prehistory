package com.peeko32213.unusualprehistory.common.data.entity.generic;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.peeko32213.unusualprehistory.core.other.tags.UPItemTags;
import com.peeko32213.unusualprehistory.core.other.tags.UPEntityTypeTags;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;

public class EntityTagData {

    public static final Codec<EntityTagData> CODEC = RecordCodecBuilder.create(instance -> instance
            .group(
                    TagKey.codec(Registries.ITEM).fieldOf("food_tag").forGetter(EntityTagData::getFood),
                    TagKey.codec(Registries.ITEM).fieldOf("tame_food_tag").forGetter(EntityTagData::getFood),
                    TagKey.codec(Registries.ENTITY_TYPE).fieldOf("targets_tag").forGetter(EntityTagData::getTargets)
            ).apply(instance, EntityTagData::new)
    );

    private final TagKey<Item> food;
    private final TagKey<Item> tameFood;
    private final TagKey<EntityType<?>> targets;

    public EntityTagData(TagKey<Item> food, TagKey<Item> tameFood, TagKey<EntityType<?>> targets) {
        this.food = food;
        this.tameFood = tameFood;
        this.targets = targets;
    }

    public TagKey<EntityType<?>> getTargets() {
        return targets;
    }

    public TagKey<Item> getFood() {
        return food;
    }

    public TagKey<Item> getTameFood() {
        return tameFood;
    }

    public static EntityTagData getDefaultInstance() {
        return new EntityTagData(UPItemTags.NONE_ITEM_TAG, UPItemTags.NONE_ITEM_TAG, UPEntityTypeTags.NONE_ENTITY_TAG);
    }
}
