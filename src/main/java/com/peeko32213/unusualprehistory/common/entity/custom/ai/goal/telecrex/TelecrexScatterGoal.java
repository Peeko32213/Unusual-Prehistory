package com.peeko32213.unusualprehistory.common.entity.custom.ai.goal.telecrex;

import com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.flying.TelecrexEntity;
import com.peeko32213.unusualprehistory.core.other.tags.UPEntityTypeTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import com.google.common.base.Predicate;

import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;

public class TelecrexScatterGoal extends Goal {

    protected final TelecrexEntity telecrex;
    protected final TelecrexScatterGoal.Sorter theNearestAttackableTargetSorter;
    protected final Predicate<? super Entity> targetEntitySelector;
    protected int executionChance = 6;
    protected boolean mustUpdate;
    private Entity targetEntity;
    private Vec3 flightTarget = null;
    private int cooldown = 0;

    public TelecrexScatterGoal(TelecrexEntity telecrex) {
        this.telecrex = telecrex;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        this.theNearestAttackableTargetSorter = new TelecrexScatterGoal.Sorter(this.telecrex);
        this.targetEntitySelector = (Predicate<Entity>) entity -> entity.isAlive() && entity.getType().is(UPEntityTypeTags.SCATTERS_TELECREX) || entity instanceof Player && !((Player) entity).isCreative();
    }

    @Override
    public boolean canUse() {
        if (!this.mustUpdate) {
            final long worldTime = this.telecrex.level().getGameTime() % 10;
            if (worldTime != 0) {
                if (this.telecrex.getNoActionTime() >= 80) {
                    return false;
                }
                if (telecrex.getRandom().nextInt(this.executionChance) != 0) {
                    return false;
                }
            }
        }
        final List<Entity> list = this.telecrex.level().getEntitiesOfClass(Entity.class, this.getTargetableArea(this.getTargetDistance()), this.targetEntitySelector);
        if (list.isEmpty()) {
            return false;
        } else {
            list.sort(this.theNearestAttackableTargetSorter);
            this.targetEntity = list.get(0);
            this.mustUpdate = false;
            return true;
        }
    }

    @Override
    public boolean canContinueToUse() {
        return targetEntity != null;
    }

    public void stop() {
        flightTarget = null;
        this.targetEntity = null;
    }

    @Override
    public void tick() {
        if (cooldown > 0) {
            cooldown--;
        }
        if (flightTarget != null) {
            this.telecrex.setFlying(true);
            this.telecrex.getMoveControl().setWantedPosition(flightTarget.x, flightTarget.y, flightTarget.z, 1.25F);
            if(cooldown == 0 && this.telecrex.isTargetBlocked(flightTarget)){
                cooldown = 30;
                flightTarget = null;
            }
        }

        if (targetEntity != null) {
            if (this.telecrex.onGround() || flightTarget == null || this.telecrex.distanceToSqr(flightTarget) < 3 ) {
                final Vec3 vec = this.telecrex.getBlockInViewAway(targetEntity.position(), 0);
                if (vec != null && vec.y() > this.telecrex.getY()) {
                    flightTarget = vec;
                }
            }
            if (this.telecrex.distanceTo(targetEntity) > 20.0F) {
                this.stop();
            }
        }
    }

    protected double getTargetDistance() {
        return 4D;
    }

    protected AABB getTargetableArea(double targetDistance) {
        final Vec3 renderCenter = new Vec3(this.telecrex.getX(), this.telecrex.getY() + 0.5, this.telecrex.getZ());
        final AABB aabb = new AABB(-2, -2, -2, 2, 2, 2);
        return aabb.move(renderCenter);
    }

    public record Sorter(Entity theEntity) implements Comparator<Entity> {
        public int compare(Entity p_compare_1_, Entity p_compare_2_) {
            final double d0 = this.theEntity.distanceToSqr(p_compare_1_);
            final double d1 = this.theEntity.distanceToSqr(p_compare_2_);
            return Double.compare(d0, d1);
        }
    }
}
