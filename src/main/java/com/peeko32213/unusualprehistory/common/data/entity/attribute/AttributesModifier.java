package com.peeko32213.unusualprehistory.common.data.entity.attribute;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.peeko32213.unusualprehistory.core.mixin.AttributeAccessor;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;

import java.util.Collections;
import java.util.List;

public class AttributesModifier implements AttributeModifier {

    private static final Codec<AttributesMap> ATTRIBUTES_CODEC = RecordCodecBuilder.create(instance -> instance.group(
            BuiltInRegistries.ATTRIBUTE.byNameCodec().fieldOf("attribute").forGetter(AttributesMap::getAttribute),
            Codec.DOUBLE.fieldOf("value").forGetter(AttributesMap::getValue)
    ).apply(instance, AttributesMap::new));


    public static final Codec<AttributesModifier> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ATTRIBUTES_CODEC.listOf().fieldOf("attribute_definitions").forGetter(AttributesModifier::getAttributeMap)
    ).apply(instance, AttributesModifier::new));



    private final List<AttributesMap> maps;

    public AttributesModifier(List<AttributesMap> attributes) {
        maps = attributes;
    }

    public List<AttributesMap> getAttributeMap() {
        return maps;
    }

    @Override
    public void performAdditions(PathfinderMob mob) {
        for(AttributesMap attributeMap : getAttributeMap()) {
            if(mob.getAttributes().hasAttribute(attributeMap.attribute)) {
                mob.getAttribute(attributeMap.attribute).setBaseValue(attributeMap.value);
            } else {
                AttributeInstance instance = new AttributeInstance(attributeMap.attribute , e -> onAttributeModified(e, mob));
                instance.setBaseValue(attributeMap.value);
                 ((AttributeAccessor) mob.getAttributes()).unusualprehistory$getAttributes().put(attributeMap.attribute, instance);

            }
        }

    }

    public void onAttributeModified(AttributeInstance attributeInstance, PathfinderMob mob) {
        //if (attributeInstance.getAttribute().isClientSyncable()) {
            ((AttributeAccessor) mob.getAttributes()).unusualprehistory$getDirtyAttributes().add(attributeInstance);
       // }

    }

    public static AttributesModifier getDefaultInstance() {
        return new AttributesModifier(Collections.emptyList());
    }

    public static class AttributesMap {



        private final Attribute attribute;
        private final double value;


        public AttributesMap(Attribute attribute, double value) {
            this.attribute = attribute;
            this.value = value;
        }

        // Getter method for attribute
        public Attribute getAttribute() {
            return attribute;
        }

        // Getter method for value
        public double getValue() {
            return value;
        }

        // Getter method for uuid

    }

}
