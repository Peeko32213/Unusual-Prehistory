package com.peeko32213.unusualprehistory.core.registry.util;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.peeko32213.unusualprehistory.core.registry.UPAdvancementTriggerRegistry;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LocationCheck;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;

import java.util.Arrays;

public class BarinaTameTrigger extends SimpleCriterionTrigger<BarinaTameTrigger.TriggerInstance> {
    final ResourceLocation id;

    public BarinaTameTrigger(ResourceLocation p_286779_) {
        this.id = p_286779_;
    }

    public ResourceLocation getId() {
        return this.id;
    }

    public BarinaTameTrigger.TriggerInstance createInstance(JsonObject p_286301_, ContextAwarePredicate p_286748_, DeserializationContext p_286322_) {
        //ContextAwarePredicate contextawarepredicate = ContextAwarePredicate.fromElement("location", p_286322_, p_286301_.get("location"), LootContextParamSets.ADVANCEMENT_LOCATION);
        return new BarinaTameTrigger.TriggerInstance(this.id, p_286748_);
    }

    public void trigger(ServerPlayer p_286813_, BlockPos p_286625_, ItemStack p_286620_) {
        ServerLevel serverlevel = p_286813_.serverLevel();
        BlockState blockstate = serverlevel.getBlockState(p_286625_);
        LootParams lootparams = (new LootParams.Builder(serverlevel)).withParameter(LootContextParams.ORIGIN, p_286625_.getCenter()).withParameter(LootContextParams.THIS_ENTITY, p_286813_).withParameter(LootContextParams.BLOCK_STATE, blockstate).withParameter(LootContextParams.TOOL, p_286620_).create(LootContextParamSets.ADVANCEMENT_LOCATION);
        LootContext lootcontext = (new LootContext.Builder(lootparams)).create((ResourceLocation)null);
        this.trigger(p_286813_, (p_286596_) -> {
            return p_286596_.matches();
        });
    }

    public static class TriggerInstance extends AbstractCriterionTriggerInstance {
        //private final ContextAwarePredicate location;

        public TriggerInstance(ResourceLocation p_286265_, ContextAwarePredicate p_286333_) {
            super(p_286265_, p_286333_);
            //this.location = p_286319_;
        }

        public static BarinaTameTrigger.TriggerInstance placedBlock(Block p_286530_) {
            ContextAwarePredicate contextawarepredicate = ContextAwarePredicate.create(LootItemBlockStatePropertyCondition.hasBlockStateProperties(p_286530_).build());
            return new BarinaTameTrigger.TriggerInstance(UPAdvancementTriggerRegistry.BARINA_TRIGGER.id, ContextAwarePredicate.ANY);
        }

        public static BarinaTameTrigger.TriggerInstance placedBlock(LootItemCondition.Builder... p_286365_) {
            ContextAwarePredicate contextawarepredicate = ContextAwarePredicate.create(Arrays.stream(p_286365_).map(LootItemCondition.Builder::build).toArray((p_286827_) -> {
                return new LootItemCondition[p_286827_];
            }));
            return new BarinaTameTrigger.TriggerInstance(UPAdvancementTriggerRegistry.BARINA_TRIGGER.id, ContextAwarePredicate.ANY);
        }

        public static BarinaTameTrigger.TriggerInstance itemUsedOnLocation(LocationPredicate.Builder p_286740_, ItemPredicate.Builder p_286777_, ResourceLocation p_286742_) {
            ContextAwarePredicate contextawarepredicate = ContextAwarePredicate.create(LocationCheck.checkLocation(p_286740_).build(), MatchTool.toolMatches(p_286777_).build());
            return new BarinaTameTrigger.TriggerInstance(p_286742_, ContextAwarePredicate.ANY);
        }

        public static BarinaTameTrigger.TriggerInstance itemUsedOnBlock(LocationPredicate.Builder p_286808_, ItemPredicate.Builder p_286486_) {
            return itemUsedOnLocation(p_286808_, p_286486_, UPAdvancementTriggerRegistry.BARINA_TRIGGER.id);
        }

        public static BarinaTameTrigger.TriggerInstance allayDropItemOnBlock(LocationPredicate.Builder p_286325_, ItemPredicate.Builder p_286531_) {
            return itemUsedOnLocation(p_286325_, p_286531_, UPAdvancementTriggerRegistry.BARINA_TRIGGER.id);
        }

        public boolean matches() {
            return true;
        }

        public JsonObject serializeToJson(SerializationContext p_286870_) {
            JsonObject jsonobject = super.serializeToJson(p_286870_);
            //jsonobject.add("location", this.location.toJson(p_286870_));
            return jsonobject;
        }
    }
}