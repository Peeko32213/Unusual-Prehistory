package com.peeko32213.unusualprehistory.data.server.entitydata;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.data.codec.MobEffectInstanceCodec;
import com.peeko32213.unusualprehistory.common.data.entity.*;
import com.peeko32213.unusualprehistory.common.data.entity.attribute.AttributesModifier;
import com.peeko32213.unusualprehistory.common.data.entity.generic.*;
import com.peeko32213.unusualprehistory.core.registry.entities.UPEntities;
import com.peeko32213.unusualprehistory.core.registry.UPSounds;
import com.scouter.goalsmith.GoalSmith;
import com.scouter.goalsmith.data.goalcodec.*;
import com.scouter.goalsmith.data.goalcodec.targetgoalcodec.HurtByTargetGoalCodec;
import com.scouter.goalsmith.data.predicates.TruePredicate;
import com.scouter.goalsmith.util.GSTags;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attributes;

import java.util.List;
import java.util.function.Consumer;

import static com.peeko32213.unusualprehistory.UnusualPrehistory.modPrefix;

public class UPEntityDataGenerator extends UPEntityDataProvider {
    public UPEntityDataGenerator(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildEntityData(Consumer<UPEntityDataConsumer> pWriter) {
    }


    private static TagKey<EntityType<?>> getGoalSmithEntityTag(String name) {
        return TagKey.create(Registries.ENTITY_TYPE, GoalSmith.prefix(name));
    }

    private static TagKey<EntityType<?>> getUPEntityTag(String name) {
        return TagKey.create(Registries.ENTITY_TYPE, modPrefix(name));
    }
}
