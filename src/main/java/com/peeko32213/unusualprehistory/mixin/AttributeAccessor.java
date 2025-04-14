package com.peeko32213.unusualprehistory.mixin;

import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;
import java.util.Set;

@Mixin(AttributeMap.class)
public interface AttributeAccessor {
    @Accessor("attributes")
    Map<Attribute, AttributeInstance> unusualprehistory$getAttributes();

    @Accessor("dirtyAttributes")
    Set<AttributeInstance> unusualprehistory$getDirtyAttributes();
}
