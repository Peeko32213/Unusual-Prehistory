package com.peeko32213.unusualprehistory.common.entity.custom.ai.goal;

import com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.flying.TelecrexEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

public class TelecrexFlightGoal extends Goal {

    protected final TelecrexEntity telecrex;
    protected double x;
    protected double y;
    protected double z;
    private boolean flightTarget = false;

    public TelecrexFlightGoal(TelecrexEntity telecrex) {
        super();
        this.setFlags(EnumSet.of(Flag.MOVE));
        this.telecrex = telecrex;
    }

    @Override
    public boolean canUse() {
        if (telecrex.getTarget() != null) {
            return false;
        } else {
            if (this.telecrex.getRandom().nextInt(30) != 0 && !telecrex.isFlying()) {
                return false;
            }
            if (this.telecrex.onGround()) {
                this.flightTarget = this.telecrex.getRandom().nextBoolean();
            } else {
                this.flightTarget = this.telecrex.getRandom().nextInt(5) > 0 && telecrex.timeFlying < 200;
            }
            Vec3 lvt_1_1_ = this.getPosition();
            if (lvt_1_1_ == null) {
                return false;
            } else {
                this.x = lvt_1_1_.x;
                this.y = lvt_1_1_.y;
                this.z = lvt_1_1_.z;
                return true;
            }
        }
    }

    public void tick() {
        if (flightTarget) {
            telecrex.getMoveControl().setWantedPosition(x, y, z, 1F);
        } else {
            this.telecrex.getNavigation().moveTo(this.x, this.y, this.z, 1F);

            if (telecrex.isFlying() && telecrex.onGround()) {
                telecrex.setFlying(false);
            }
        }

        if (telecrex.isFlying() && telecrex.onGround() && telecrex.timeFlying > 10) {
            telecrex.setFlying(false);
        }
    }

    @Nullable
    protected Vec3 getPosition() {
        final Vec3 vector3d = telecrex.position();
        if (telecrex.isOverWater()) {
            flightTarget = true;
        }
        if (flightTarget) {
            if (telecrex.timeFlying < 50 || telecrex.isOverWater()) {
                return telecrex.getBlockInViewAway(vector3d, 0);
            } else {
                return telecrex.getBlockGrounding(vector3d);
            }
        } else {
            return LandRandomPos.getPos(this.telecrex, 10, 7);
        }
    }

    public boolean canContinueToUse() {
        if (flightTarget) {
            return telecrex.isFlying() && telecrex.distanceToSqr(x, y, z) > 2F;
        } else {
            return (!this.telecrex.getNavigation().isDone()) && !this.telecrex.isVehicle();
        }
    }

    public void start() {
        if (flightTarget) {
            telecrex.setFlying(true);
            telecrex.getMoveControl().setWantedPosition(x, y, z, 1F);
        } else {
            this.telecrex.getNavigation().moveTo(this.x, this.y, this.z, 1F);
        }
    }

    public void stop() {
        this.telecrex.getNavigation().stop();
        super.stop();
    }
}
