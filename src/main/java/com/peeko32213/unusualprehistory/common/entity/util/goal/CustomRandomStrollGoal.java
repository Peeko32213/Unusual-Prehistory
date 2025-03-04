package com.peeko32213.unusualprehistory.common.entity.util.goal;

import com.peeko32213.unusualprehistory.common.entity.base.PrehistoricAquaticEntity;
import com.peeko32213.unusualprehistory.common.entity.base.PrehistoricEntity;
import com.peeko32213.unusualprehistory.common.entity.base.TamablePrehistoricEntity;
import com.peeko32213.unusualprehistory.common.entity.base.TameablePrehistoricNoFloatEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

public class CustomRandomStrollGoal extends WaterAvoidingRandomStrollGoal {
    protected final float probability;
    protected final int xzRange;
    protected final int yRange;

    public CustomRandomStrollGoal(PathfinderMob creature, int chance, double speedIn, int xzRange, int yRange) {
        this(creature, chance, speedIn, 0.001F, xzRange, yRange);
    }

    public boolean canUse() {
        if(this.mob instanceof PrehistoricEntity entityBaseDinosaurAnimal) {
            if(entityBaseDinosaurAnimal.playingAnimation()) {
                return false;
            }
        }
        if(this.mob instanceof PrehistoricAquaticEntity entityBaseDinosaurAnimal) {
            if(entityBaseDinosaurAnimal.playingAnimation()) {
                return false;
            }
        }
        if(this.mob instanceof TamablePrehistoricEntity entityBaseDinosaurAnimal) {
            if(entityBaseDinosaurAnimal.playingAnimation()) {
                return false;
            }
        }
        if(this.mob instanceof TameablePrehistoricNoFloatEntity entityBaseDinosaurAnimal) {
            if(entityBaseDinosaurAnimal.playingAnimation()) {
                return false;
            }
        }
        if (this.mob.isVehicle()) {
            return false;
        } else {
            if (!this.forceTrigger) {
                if ( this.mob.getNoActionTime() >= 100) {
                    return false;
                }

                if (this.mob.getRandom().nextInt(this.interval) != 0) {
                    return false;
                }
            }

            Vec3 lvt_1_1_ = this.getPosition();
            if (lvt_1_1_ == null) {
                return false;
            } else {
                this.wantedX = lvt_1_1_.x;
                this.wantedY = lvt_1_1_.y;
                this.wantedZ = lvt_1_1_.z;
                this.forceTrigger = false;
                return true;
            }
        }
    }

    public CustomRandomStrollGoal(PathfinderMob creature, int chance, double speedIn, float probabilityIn, int xzRange, int yRange) {
        super(creature, speedIn, chance);
        this.probability = probabilityIn;
        this.xzRange = xzRange;
        this.yRange = yRange;
    }

    @Nullable
    protected Vec3 getPosition() {
        if (this.mob.isInWaterOrBubble()) {
            Vec3 vector3d = LandRandomPos.getPos(this.mob, xzRange, yRange);
            return vector3d == null ? super.getPosition() : vector3d;
        } else {
            return this.mob.getRandom().nextFloat() >= this.probability ? LandRandomPos.getPos(this.mob, xzRange, yRange) : super.getPosition();
        }
    }
}