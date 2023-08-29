package com.peeko32213.unusualprehistory.core.registry.util;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.level.ItemLike;

public class BarinaTameTrigger extends SimpleCriterionTrigger<BarinaTameTrigger.TriggerInstance> {
    static final ResourceLocation ID = new ResourceLocation("barina_trigger");

    public ResourceLocation getId() {
        return ID;
    }

    public BarinaTameTrigger.TriggerInstance createInstance(JsonObject pJson, EntityPredicate.Composite pEntityPredicate, DeserializationContext pConditionsParser) {
        return new BarinaTameTrigger.TriggerInstance(pEntityPredicate, ItemPredicate.fromJson(pJson.get("item")));
    }

    public void trigger(ServerPlayer pPlayer, ItemStack pItem) {
        this.trigger(pPlayer, (p_23687_) -> {
            return p_23687_.matches(pItem);
        });
    }

    public static class TriggerInstance extends AbstractCriterionTriggerInstance {
        private final ItemPredicate item;

        public TriggerInstance(EntityPredicate.Composite pPlayer, ItemPredicate pItem) {
            super(BarinaTameTrigger.ID, pPlayer);
            this.item = pItem;
        }

        public static BarinaTameTrigger.TriggerInstance usedItem() {
            return new BarinaTameTrigger.TriggerInstance(EntityPredicate.Composite.ANY, ItemPredicate.ANY);
        }

        public static BarinaTameTrigger.TriggerInstance usedItem(ItemPredicate pItem) {
            return new BarinaTameTrigger.TriggerInstance(EntityPredicate.Composite.ANY, pItem);
        }

        public static BarinaTameTrigger.TriggerInstance usedItem(ItemLike pItem) {
            return new BarinaTameTrigger.TriggerInstance(EntityPredicate.Composite.ANY, new ItemPredicate((TagKey<Item>)null, ImmutableSet.of(pItem.asItem()), MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY, EnchantmentPredicate.NONE, EnchantmentPredicate.NONE, (Potion)null, NbtPredicate.ANY));
        }

        public boolean matches(ItemStack pItem) {
            return this.item.matches(pItem);
        }

        public JsonObject serializeToJson(SerializationContext pConditions) {
            JsonObject jsonobject = super.serializeToJson(pConditions);
            jsonobject.add("item", this.item.serializeToJson());
            return jsonobject;
        }
    }
}