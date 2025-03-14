package com.peeko32213.unusualprehistory.common.entity.util.goal;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.phys.Vec3;

public class PrehistoricPanicGoal extends PanicGoal {

    public PrehistoricPanicGoal(PathfinderMob mob, double speedModifier) {
        super(mob, speedModifier);
    }

    protected boolean findRandomPosition() {
        Vec3 vec3 = DefaultRandomPos.getPos(this.mob, 12, 4);
        if (vec3 == null) {
            return false;
        } else {
            this.posX = vec3.x;
            this.posY = vec3.y;
            this.posZ = vec3.z;
            return true;
        }
    }

    @Override
    protected BlockPos lookForWater(BlockGetter level, Entity entity, int range) {
        BlockPos entityPos = entity.blockPosition();
        if (!level.getBlockState(entityPos).getCollisionShape(level, entityPos).isEmpty()) {
            return null;
        }
        return BlockPos.findClosestMatch(entityPos, range + 5, 2, (pos) -> level.getFluidState(pos).is(FluidTags.WATER)).orElse(null);
    }
}

