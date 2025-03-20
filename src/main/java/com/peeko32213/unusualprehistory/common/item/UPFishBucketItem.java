package com.peeko32213.unusualprehistory.common.item;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.MobBucketItem;
import net.minecraft.world.level.material.Fluid;

import java.util.function.Supplier;

public class UPFishBucketItem extends MobBucketItem {

	public UPFishBucketItem(Supplier<? extends EntityType<?>> entityType, Supplier<? extends Fluid> fluid, Item item, boolean hasTooltip, Properties builder) {
		super(entityType, fluid, () -> SoundEvents.BUCKET_EMPTY_FISH, builder);
	}

}