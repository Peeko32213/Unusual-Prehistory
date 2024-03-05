package com.peeko32213.unusualprehistory.common.entity.msc.util.goal;

import com.peeko32213.unusualprehistory.common.entity.EntityTanystropheus;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;
import java.util.List;

public class TanyFishingGoal extends Goal {

    private final EntityTanystropheus tany;
    private BlockPos waterPos = null;
    private BlockPos targetPos = null;
    private int executionChance = 0;
    private final Direction[] HORIZONTALS = new Direction[]{Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST};
    private int idleTime = 0;
    private int navigateTime = 0;

    public TanyFishingGoal(EntityTanystropheus tany) {
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        this.tany = tany;
    }

    public void stop() {
        targetPos = null;
        waterPos = null;
        idleTime = 0;
        navigateTime = 0;
        this.tany.getNavigation().stop();
    }

    public void tick() {
        if (targetPos != null && waterPos != null) {
            double dist = tany.distanceToSqr(Vec3.atCenterOf(waterPos));
            if (dist <= 1F) {
                navigateTime = 0;
                double d0 = waterPos.getX() + 0.5D - tany.getX();
                double d2 = waterPos.getZ() + 0.5D - tany.getZ();
                float yaw = (float)(Mth.atan2(d2, d0) * (double)Mth.RAD_TO_DEG) - 90.0F;
                tany.setYRot(yaw);
                tany.yHeadRot = yaw;
                tany.yBodyRot = yaw;
                tany.getNavigation().stop();
                idleTime++;
                if(idleTime > 45){
                    this.tany.gameEvent(GameEvent.ITEM_INTERACT_START);
                    this.tany.playSound(SoundEvents.GENERIC_SPLASH, 0.7F, 0.5F + tany.getRandom().nextFloat());
                    this.tany.resetFishingCooldown();
                    this.spawnFishingLoot();
                    this.stop();
                }
            }else{
                navigateTime++;
                tany.getNavigation().moveTo(waterPos.getX(), waterPos.getY(), waterPos.getZ(), 1.2D);
            }
            if(navigateTime > 3600){
                this.stop();
            }
        }
    }

    public boolean canContinueToUse() {
        return targetPos != null && tany.fishingCooldown == 0;
    }

    public void spawnFishingLoot() {
        LootParams.Builder lootcontext$builder = new LootParams.Builder((ServerLevel) this.tany.level());
        LootContextParamSet.Builder lootparameterset$builder = new LootContextParamSet.Builder();
        LootTable loottable = tany.level().getServer().getLootData().getLootTable(BuiltInLootTables.FISHING);
        List<ItemStack> result = loottable.getRandomItems(lootcontext$builder.create(lootparameterset$builder.build()));
        for (ItemStack itemstack : result) {
            ItemEntity item = new ItemEntity(this.tany.level(), this.tany.getX() + 0.5F, this.tany.getY(), this.tany.getZ(), itemstack);
            if (!this.tany.level().isClientSide) {
                this.tany.level().addFreshEntity(item);
            }
        }
    }


    @Override
    public boolean canUse() {
        if(tany.fishingCooldown == 0 && tany.getRandom().nextInt(30) == 0){
            if(tany.isInWater()){
                waterPos = tany.blockPosition();
                targetPos = waterPos;
                return true;
            }else{
                waterPos = generateTarget();
                if (waterPos != null) {
                    targetPos = getLandPos(waterPos);
                    return targetPos != null;
                }
            }

        }
        return false;
    }

    public BlockPos generateTarget() {
        BlockPos blockpos = null;
        final RandomSource random = this.tany.getRandom();
        int range = 32;
        for (int i = 0; i < 15; i++) {
            BlockPos blockpos1 = this.tany.blockPosition().offset(random.nextInt(range) - range / 2, 3, random.nextInt(range) - range / 2);
            while (this.tany.level().isEmptyBlock(blockpos1) && blockpos1.getY() > 1) {
                blockpos1 = blockpos1.below();
            }
            if (isConnectedToLand(blockpos1)) {
                blockpos = blockpos1;
            }
        }
        return blockpos;
    }

    public boolean isConnectedToLand(BlockPos pos) {
        if (this.tany.level().getFluidState(pos).is(FluidTags.WATER)) {
            for (Direction dir : HORIZONTALS) {
                BlockPos offsetPos = pos.relative(dir);
                if (this.tany.level().getFluidState(offsetPos).isEmpty() && this.tany.level().getFluidState(offsetPos.above()).isEmpty()) {
                    return true;
                }
            }
        }
        return false;
    }

    public BlockPos getLandPos(BlockPos pos) {
        if (this.tany.level().getFluidState(pos).is(FluidTags.WATER)) {
            for (Direction dir : HORIZONTALS) {
                BlockPos offsetPos = pos.relative(dir);
                if (this.tany.level().getFluidState(offsetPos).isEmpty() && this.tany.level().getFluidState(offsetPos.above()).isEmpty()) {
                    return offsetPos;
                }
            }
        }
        return null;
    }
}
