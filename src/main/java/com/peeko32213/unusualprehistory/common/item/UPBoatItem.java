package com.peeko32213.unusualprehistory.common.item;

import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import com.peeko32213.unusualprehistory.common.entity.UPBoatEntity;
import com.peeko32213.unusualprehistory.common.entity.UPChestBoatEntity;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Predicate;

public class UPBoatItem extends Item {
	private static final Predicate<Entity> RIDERS = EntitySelector.NO_SPECTATORS.and(Entity::isPickable);
	private final UPBoatEntity.BoatType type;
	private final boolean chest;

	public UPBoatItem(boolean chest, UPBoatEntity.BoatType type, Item.Properties settings) {
		super(settings);
		this.chest = chest;
		this.type = type;
	}

	@Override
	public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level world, Player user, @NotNull InteractionHand hand) {
		ItemStack itemStack = user.getItemInHand(hand);
		HitResult hitResult = getPlayerPOVHitResult(world, user, ClipContext.Fluid.ANY);
		if (hitResult.getType() == HitResult.Type.MISS) {
			return InteractionResultHolder.pass(itemStack);
		}
		Vec3 vec3d = user.getViewVector(1.0f);
		List<Entity> list = world.getEntities(user, user.getBoundingBox().expandTowards(vec3d.scale(5.0)).inflate(1.0), RIDERS);
		if (!list.isEmpty()) {
			Vec3 vec3d2 = user.getEyePosition();
			for (Entity entity : list) {
				AABB box = entity.getBoundingBox().inflate(entity.getPickRadius());
				if (!box.contains(vec3d2)) continue;
				return InteractionResultHolder.pass(itemStack);
			}
		}
		if (hitResult.getType() == HitResult.Type.BLOCK) {
			UPBoatEntity boatEntity = this.createEntity(world, hitResult);
			boatEntity.setUPBoatType(this.type);
			boatEntity.setYRot(user.getYRot());
			if (!world.noCollision(boatEntity, boatEntity.getBoundingBox())) {
				return InteractionResultHolder.fail(itemStack);
			}
			if (!world.isClientSide()) {
				world.addFreshEntity(boatEntity);
				world.gameEvent(user, GameEvent.ENTITY_PLACE, hitResult.getLocation());
				if (!user.getAbilities().instabuild) {
					itemStack.shrink(1);
				}
			}
			user.awardStat(Stats.ITEM_USED.get(this));
			return InteractionResultHolder.sidedSuccess(itemStack, world.isClientSide());
		}
		return InteractionResultHolder.pass(itemStack);
	}

	private UPBoatEntity createEntity(Level world, HitResult hitResult) {
		if (this.chest) {
			return new UPChestBoatEntity(world, hitResult.getLocation().x, hitResult.getLocation().y, hitResult.getLocation().z);
		}
		return new UPBoatEntity(world, hitResult.getLocation().x, hitResult.getLocation().y, hitResult.getLocation().z);
	}

}