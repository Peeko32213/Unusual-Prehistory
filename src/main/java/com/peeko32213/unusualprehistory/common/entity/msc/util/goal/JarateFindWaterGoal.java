package com.peeko32213.unusualprehistory.common.entity.msc.util.goal;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.phys.Vec3;

import java.util.Iterator;

public class JarateFindWaterGoal extends Goal {
    //literally copy paste of the find water goal
    private final PathfinderMob mob;

    private BlockPos endpos;
    private Vec3 randedPos;

    public JarateFindWaterGoal(PathfinderMob pMob) {
        this.mob = pMob;
    }

    public boolean canUse() {
        return this.mob.onGround() && !this.mob.level().getFluidState(this.mob.blockPosition()).is(FluidTags.WATER);
    }

    public void start() {

        this.endpos = null;
        double posdist = 1000000000;

        Iterable<BlockPos> blocksInRange = BlockPos.betweenClosed(Mth.floor(this.mob.getX() - 50), Mth.floor(this.mob.getY() - 50), Mth.floor(this.mob.getZ() - 50), Mth.floor(this.mob.getX() + 50), this.mob.getBlockY(), Mth.floor(this.mob.getZ() + 50));
        Iterator iterableBlocksInRange = blocksInRange.iterator();
        randedPos = DefaultRandomPos.getPos(this.mob, 5, 4);
        //random location to scrawl around if can't find water

        while(iterableBlocksInRange.hasNext()) {
            BlockPos potentialNextBlock = (BlockPos)iterableBlocksInRange.next();
            if (this.mob.level().getFluidState(potentialNextBlock).is(FluidTags.WATER)) {

                double dist = this.mob.distanceToSqr((double)potentialNextBlock.getX(), (double)potentialNextBlock.getY(), (double)potentialNextBlock.getZ());

                if (this.endpos == null) {
                    this.endpos = potentialNextBlock;
                    posdist = dist;

                } else if (dist < posdist) {
                    this.endpos = potentialNextBlock;
                    posdist = dist;
                }
                //picks the closest water possible

            }
        }

        if (this.endpos != null) {
            //if can find water track it
            this.mob.getNavigation().moveTo((double)this.endpos.getX(), (double)this.endpos.getY(), (double)this.endpos.getZ(), 1.5);
        } else {
            //if can't find water run randomly
            this.mob.getNavigation().moveTo((double)this.randedPos.z(), (double)this.randedPos.z(), (double)this.randedPos.z(), 1.5);
        }

    }

    public void tick() {
        if (this.endpos != null) {
            this.mob.getNavigation().moveTo((double)this.endpos.getX(), (double)this.endpos.getY(), (double)this.endpos.getZ(), 1.5);
            //constantly track water
        } else {
            if ((this.mob.tickCount)%20 == 0){
                this.randedPos = DefaultRandomPos.getPos(this.mob, 5, 4);
                this.mob.getNavigation().moveTo((double) this.randedPos.z(), (double) this.randedPos.z(), (double) this.randedPos.z(), 1.5);
                //make a new navigation to panick towards to every second
            }
        }
    }



}