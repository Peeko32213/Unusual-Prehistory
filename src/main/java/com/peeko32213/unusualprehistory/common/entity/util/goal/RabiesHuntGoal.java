package com.peeko32213.unusualprehistory.common.entity.util.goal;

import com.peeko32213.unusualprehistory.core.registry.UPEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.Iterator;
import java.util.List;

public class RabiesHuntGoal extends Goal {
    //literally copy paste of the find water goal
    private final PathfinderMob mob;
    private LivingEntity target;


    public RabiesHuntGoal(PathfinderMob pMob) {
        this.mob = pMob;
    }

    public boolean canUse() {
        return this.mob.onGround() && !this.mob.level().getFluidState(this.mob.blockPosition()).is(FluidTags.WATER);
    }

    public void start() {

        this.mob.setPathfindingMalus(BlockPathTypes.WATER, 1000000000);
        //avoid water ong

        //if (pLivingEntity instanceof PathfinderMob && !(this.mob instanceof Player)) {

            double range = Math.max(this.mob.getAttribute(Attributes.FOLLOW_RANGE).getValue(), 15);
            Vec3 loc = this.mob.position();

            AABB hitbox = this.mob.getBoundingBox().inflate(range, range, range);
            List<LivingEntity> victimsList = this.mob.level().getEntitiesOfClass(LivingEntity.class, hitbox);
            //scanbox creation

            double minRange = 100000;
            LivingEntity finalizedTarget = null;
            //closest target

            if (!victimsList.isEmpty()) {
                for (int i = 0; i < victimsList.size(); i++) {
                    LivingEntity victim = victimsList.get(i);

                    if (this.mob.hasLineOfSight(victim) && this.mob.distanceTo(victim) <= minRange && this.mob != victim) {
                        minRange = this.mob.distanceTo(victim);
                        finalizedTarget = victim;
                    }

                }
            }//checks for the closest target within range that is visible

            if (this.mob.getTarget() != finalizedTarget) {
                this.mob.setTarget(finalizedTarget);
                if (finalizedTarget != null) {
                    this.mob.getNavigation().moveTo(this.mob.getTarget(), 4);
                    this.target = finalizedTarget;
                }
            }//sets the target of the entity to the selected target if it isn't targeting it already

    }

    public void tick() {

        double range = Math.max(this.mob.getAttribute(Attributes.FOLLOW_RANGE).getValue(), 15);
        Vec3 loc = this.mob.position();

        AABB hitbox = this.mob.getBoundingBox().inflate(range, range, range);
        List<LivingEntity> victimsList = this.mob.level().getEntitiesOfClass(LivingEntity.class, hitbox);
        //scanbox creation

        double minRange = 100000;
        LivingEntity finalizedTarget = null;
        //closest target

        if (!victimsList.isEmpty()) {
            for (int i = 0; i < victimsList.size(); i++) {
                LivingEntity victim = victimsList.get(i);

                if (this.mob.hasLineOfSight(victim) && this.mob.distanceTo(victim) <= minRange && this.mob != victim) {
                    minRange = this.mob.distanceTo(victim);
                    finalizedTarget = victim;
                }

            }
        }//checks for the closest target within range that is visible

        if (this.mob.getTarget() != finalizedTarget) {
            //if the closest target is not the current target
            this.mob.setTarget(finalizedTarget);
            this.target = finalizedTarget;
        }
        if (this.mob.getTarget() != null) {
            this.mob.getNavigation().moveTo(this.mob.getTarget(), 1.5);
            //constantly track target
        }

    }



}