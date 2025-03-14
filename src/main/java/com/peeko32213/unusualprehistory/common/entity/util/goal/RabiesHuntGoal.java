package com.peeko32213.unusualprehistory.common.entity.util.goal;

import com.peeko32213.unusualprehistory.core.registry.UPEffects;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class RabiesHuntGoal extends Goal {
    //literally copy paste of the find water goal
    private final PathfinderMob mob;
    private LivingEntity target;


    public RabiesHuntGoal(PathfinderMob pMob) {
        this.mob = pMob;
    }

    public boolean canUse() {
        return this.mob.onGround() && !this.mob.level().getFluidState(this.mob.blockPosition()).is(FluidTags.WATER) && this.mob.hasEffect(UPEffects.YIXIAN_RAMPAGE.get());
    }

    public void start() {

        this.mob.setPathfindingMalus(BlockPathTypes.WATER, 1000000000);
        //avoid water ong
        if (this.mob.getTarget() != null && this.mob.getTarget().hasEffect(UPEffects.YIXIAN_RAMPAGE.get())) {
            this.mob.setTarget(null);
        }

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

                if (this.mob.hasLineOfSight(victim) && this.mob.distanceTo(victim) <= minRange && this.mob != victim && !victim.hasEffect(UPEffects.YIXIAN_RAMPAGE.get())) {
                    minRange = this.mob.distanceTo(victim);
                    finalizedTarget = victim;
                }

            }
        }//checks for the closest target within range that is visible and not rabid

        setTargetToBrainOrNormal(this.mob, finalizedTarget);
        if (finalizedTarget != null && this.mob.getTarget() != null) {
            this.mob.getNavigation().moveTo(this.mob.getTarget(), 4);

        }
        //sets the target of the entity to the selected target if it isn't targeting it already

    }

    @Override
    public boolean canContinueToUse() {
        return this.mob.hasEffect(UPEffects.YIXIAN_RAMPAGE.get());
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

        setTargetToBrainOrNormal(this.mob, finalizedTarget);
        if (this.mob.getTarget() != null) {
            this.mob.getNavigation().moveTo(this.mob.getTarget(), 1.5);
        }

    }


    private void setTargetToBrainOrNormal(LivingEntity targeter, LivingEntity target) {
        if(target == null || targeter == null) return;
        Brain<?> brain = targeter.getBrain();
        if (!brain.hasMemoryValue(MemoryModuleType.ATTACK_TARGET)) {
            brain.setMemory(MemoryModuleType.ATTACK_TARGET, target);
        }
        this.mob.setTarget(target);
        this.target = target;
    }
}