package com.peeko32213.unusualprehistory.common.entity.custom.ai.goal.pterodaustro;

import com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.flying.PterodaustroEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

public class PterodaustroFlightGoal extends Goal {

    protected final PterodaustroEntity pterodaustro;
    protected double x;
    protected double y;
    protected double z;
    private boolean flightTarget = false;
    private int orbitResetCooldown = 0;
    private int maxOrbitTime = 360;
    private int orbitTime = 0;

    public PterodaustroFlightGoal(PterodaustroEntity pterodaustro) {
        super();
        this.setFlags(EnumSet.of(Flag.MOVE));
        this.pterodaustro = pterodaustro;
    }

    @Override
    public boolean canUse() {
        if (orbitResetCooldown < 0) {
            orbitResetCooldown++;
        }
        if ((pterodaustro.getTarget() != null && pterodaustro.getTarget().isAlive() && !this.pterodaustro.isVehicle()) || this.pterodaustro.isPassenger()) {
            return false;
        } else {
            if (this.pterodaustro.getRandom().nextInt(15) != 0 && !pterodaustro.isFlying()) {
                return false;
            }
            if (this.pterodaustro.isBaby()) {
                this.flightTarget = false;
            } else if (this.pterodaustro.isInWaterOrBubble()) {
                this.flightTarget = true;
            } else if (this.pterodaustro.onGround()) {
                this.flightTarget = pterodaustro.getRandom().nextBoolean();
            } else {
                if (orbitResetCooldown == 0 && pterodaustro.getRandom().nextInt(6) == 0) {
                    orbitResetCooldown = 400;
                    pterodaustro.orbitPos = pterodaustro.blockPosition();
                    pterodaustro.orbitDist = 4 + pterodaustro.getRandom().nextInt(5);
                    pterodaustro.orbitClockwise = pterodaustro.getRandom().nextBoolean();
                    orbitTime = 0;
                    maxOrbitTime = (int) (360 + 360 * pterodaustro.getRandom().nextFloat());
                }
                this.flightTarget = pterodaustro.isVehicle() || pterodaustro.getRandom().nextInt(7) > 0 && pterodaustro.timeFlying < 700;
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
        if (orbitResetCooldown > 0) {
            orbitResetCooldown--;
        }
        if (orbitResetCooldown < 0) {
            orbitResetCooldown++;
        }
        if (orbitResetCooldown > 0 && pterodaustro.orbitPos != null) {
            if (orbitTime < maxOrbitTime && !pterodaustro.isInWaterOrBubble()) {
                orbitTime++;
            } else {
                orbitTime = 0;
                pterodaustro.orbitPos = null;
                orbitResetCooldown = -400 - pterodaustro.getRandom().nextInt(400);
            }
        }
        if (pterodaustro.horizontalCollision && !pterodaustro.onGround()) {
            stop();
        }
        if (flightTarget) {
            pterodaustro.getMoveControl().setWantedPosition(x, y, z, 1F);
        } else {
            if (!pterodaustro.onGround() && pterodaustro.isFlying()) {
                if (!pterodaustro.isInWaterOrBubble()) {
                    pterodaustro.setDeltaMovement(pterodaustro.getDeltaMovement().multiply(1.2F, 0.6F, 1.2F));
                }
            } else {
                this.pterodaustro.getNavigation().moveTo(this.x, this.y, this.z, 1F);
            }
        }
        if (!flightTarget && pterodaustro.onGround() && pterodaustro.isFlying()) {
            pterodaustro.setFlying(false);
            orbitTime = 0;
            pterodaustro.orbitPos = null;
            orbitResetCooldown = -400 - pterodaustro.getRandom().nextInt(400);
        }
        if (pterodaustro.timeFlying > 30 && pterodaustro.isFlying() && (!pterodaustro.level().isEmptyBlock(pterodaustro.getBlockPosBelowThatAffectsMyMovement()) || pterodaustro.onGround()) && !pterodaustro.isInWaterOrBubble()) {
            pterodaustro.setFlying(false);
            orbitTime = 0;
            pterodaustro.orbitPos = null;
            orbitResetCooldown = -400 - pterodaustro.getRandom().nextInt(400);
        }
    }

    @Nullable
    protected Vec3 getPosition() {
        Vec3 vector3d = pterodaustro.position();
        if (pterodaustro.isTame() && pterodaustro.getCommand() == 1 && pterodaustro.getOwner() != null) {
            vector3d = pterodaustro.getOwner().position();
            pterodaustro.orbitPos = pterodaustro.getOwner().blockPosition();
        }
        if (orbitResetCooldown > 0 && pterodaustro.orbitPos != null) {
            return pterodaustro.getOrbitVec(vector3d, 4 + pterodaustro.getRandom().nextInt(2));
        }
        if (pterodaustro.isVehicle() || pterodaustro.isOverWaterOrVoid()) {
            flightTarget = true;
        }
        if (flightTarget) {
            if (pterodaustro.timeFlying < 500 || pterodaustro.isVehicle() || pterodaustro.isOverWaterOrVoid()) {
                return pterodaustro.getBlockInViewAway(vector3d, 0);
            } else {
                return pterodaustro.getBlockGrounding(vector3d);
            }
        } else {
            return LandRandomPos.getPos(this.pterodaustro, 10, 7);
        }
    }

    public boolean canContinueToUse() {
        if (flightTarget) {
            return pterodaustro.isFlying() && pterodaustro.distanceToSqr(x, y, z) > 2F;
        } else {
            return (!this.pterodaustro.getNavigation().isDone()) && !this.pterodaustro.isVehicle();
        }
    }

    public void start() {
        if (flightTarget) {
            pterodaustro.setFlying(true);
            pterodaustro.getMoveControl().setWantedPosition(x, y, z, 1F);
        } else {
            this.pterodaustro.getNavigation().moveTo(this.x, this.y, this.z, 1F);
        }
    }

    public void stop() {
        this.pterodaustro.getNavigation().stop();
        super.stop();
    }
}
