package com.peeko32213.unusualprehistory.common.item;

import com.peeko32213.unusualprehistory.common.entity.UPBoat;
import com.peeko32213.unusualprehistory.common.entity.UPChestBoat;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.Stats;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;

public class UPBoatItem extends Item {
    private static final Predicate<Entity> COLLISION_PREDICATE;
    private final boolean hasChest;
    private final ResourceLocation type;

    public UPBoatItem(boolean hasChest, ResourceLocation type, Item.Properties properties) {
        super(properties);
        this.hasChest = hasChest;
        this.type = type;
        DispenserBlock.registerBehavior(this, new UPDispenserBoatBehavior(hasChest, type));
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player playerIn, InteractionHand handIn) {
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        HitResult hitResult = getPlayerPOVHitResult(level, playerIn, Fluid.ANY);
        if (hitResult.getType() == Type.MISS) {
            return new InteractionResultHolder(InteractionResult.PASS, itemstack);
        } else {
            Vec3 vec3d = playerIn.getViewVector(1.0F);
            List<Entity> list = level.getEntities(playerIn, playerIn.getBoundingBox().expandTowards(vec3d.scale(5.0F)).inflate(1.0F), COLLISION_PREDICATE);
            if (!list.isEmpty()) {
                Vec3 vec3d1 = playerIn.getEyePosition();

                for(Entity entity : list) {
                    AABB aabb = entity.getBoundingBox().inflate(entity.getPickRadius());
                    if (aabb.contains(vec3d1)) {
                        return new InteractionResultHolder(InteractionResult.PASS, itemstack);
                    }
                }
            }
            if (hitResult.getType() == Type.BLOCK) {
                Boat boat = this.hasChest ? new UPChestBoat(level, this.type, hitResult.getLocation().x, hitResult.getLocation().y, hitResult.getLocation().z) : new UPBoat(level, this.type, hitResult.getLocation().x, hitResult.getLocation().y, hitResult.getLocation().z);
                boat.setYRot(playerIn.getYRot());
                if (!level.noCollision(boat, boat.getBoundingBox().inflate(-0.1))) {
                    return new InteractionResultHolder(InteractionResult.FAIL, itemstack);
                } else {
                    if (!level.isClientSide) {
                        level.addFreshEntity(boat);
                    }

                    if (!playerIn.getAbilities().instabuild) {
                        itemstack.shrink(1);
                    }

                    playerIn.awardStat(Stats.ITEM_USED.get(this));
                    return new InteractionResultHolder(InteractionResult.SUCCESS, itemstack);
                }
            } else {
                return new InteractionResultHolder(InteractionResult.PASS, itemstack);
            }
        }
    }

    static {
        COLLISION_PREDICATE = EntitySelector.NO_SPECTATORS.and(Entity::isPickable);
    }

    static class UPDispenserBoatBehavior extends DefaultDispenseItemBehavior {
        private final DefaultDispenseItemBehavior defaultDispenseItemBehavior = new DefaultDispenseItemBehavior();
        private final boolean hasChest;
        private final ResourceLocation type;

        public UPDispenserBoatBehavior(boolean hasChest, ResourceLocation type) {
            this.hasChest = hasChest;
            this.type = type;
        }

        public ItemStack execute(BlockSource source, ItemStack stack) {
            Direction direction = source.getBlockState().getValue(DispenserBlock.FACING);
            Level level = source.getLevel();
            double x = source.x() + (double)((float)direction.getStepX() * 1.125F);
            double y = source.y() + (double)((float)direction.getStepY() * 1.125F);
            double z = source.z() + (double)((float)direction.getStepZ() * 1.125F);
            BlockPos pos = source.getPos().relative(direction);
            double adjustY;
            if (level.getFluidState(pos).is(FluidTags.WATER)) {
                adjustY = 1.0F;
            } else {
                if (!level.getBlockState(pos).isAir() || !level.getFluidState(pos.below()).is(FluidTags.WATER)) {
                    return this.defaultDispenseItemBehavior.dispense(source, stack);
                }

                adjustY = 0.0F;
            }

            Boat boat = this.hasChest ? new UPChestBoat(level, this.type, x, y + adjustY, z) : new UPChestBoat(level, this.type, x, y + adjustY, z);
            boat.setYRot(direction.toYRot());
            level.addFreshEntity(boat);
            stack.shrink(1);
            return stack;
        }

        protected void playSound(BlockSource source) {
            source.getLevel().levelEvent(1000, source.getPos(), 0);
        }
    }
}
