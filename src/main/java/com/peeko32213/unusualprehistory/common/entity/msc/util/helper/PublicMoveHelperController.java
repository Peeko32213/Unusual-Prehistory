package com.peeko32213.unusualprehistory.common.entity.msc.util.helper;

import com.peeko32213.unusualprehistory.common.entity.EntityMegalampris;
import com.peeko32213.unusualprehistory.common.entity.EntityProtosphyraena;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.animal.WaterAnimal;

public class PublicMoveHelperController extends MoveControl {
    private final WaterAnimal dolphin;

    public PublicMoveHelperController(WaterAnimal dolphinIn) {
        super(dolphinIn);
        this.dolphin = dolphinIn;
    }

    public void tick() {
        if (this.dolphin.isInWater()) {
            this.dolphin.setDeltaMovement(this.dolphin.getDeltaMovement().add(0.0D, 0.005D, 0.0D));
        }

        if (this.operation == Operation.MOVE_TO && !this.dolphin.getNavigation().isDone()) {
            double d0 = this.wantedX - this.dolphin.getX();
            double d1 = this.wantedY - this.dolphin.getY();
            double d2 = this.wantedZ - this.dolphin.getZ();
            double d3 = d0 * d0 + d1 * d1 + d2 * d2;
            if (d3 < (double) 2.5000003E-7F) {
                this.mob.setZza(0.0F);
            } else {
                float f = (float) (Mth.atan2(d2, d0) * (double) (180F / (float) Math.PI)) - 90.0F;
                this.dolphin.setYRot(this.rotlerp(this.dolphin.getYRot(), f, 10.0F));
                this.dolphin.yBodyRot = this.dolphin.getYRot();
                this.dolphin.yHeadRot = this.dolphin.getYRot();
                float f1 = (float) (this.speedModifier * this.dolphin.getAttributeValue(Attributes.MOVEMENT_SPEED));
                if (this.dolphin.isInWater()) {
                    this.dolphin.setSpeed(f1 * 0.02F);
                    float f2 = -((float) (Mth.atan2(d1, Mth.sqrt((float) (d0 * d0 + d2 * d2))) * (double) (180F / (float) Math.PI)));
                    f2 = Mth.clamp(Mth.wrapDegrees(f2), -85.0F, 85.0F);
                    this.dolphin.setXRot(this.rotlerp(this.dolphin.getXRot(), f2, 5.0F));
                    float f3 = Mth.cos(this.dolphin.getXRot() * ((float) Math.PI / 180F));
                    float f4 = Mth.sin(this.dolphin.getXRot() * ((float) Math.PI / 180F));
                    this.dolphin.zza = f3 * f1;
                    this.dolphin.yya = -f4 * f1;
                } else {
                    this.dolphin.setSpeed(f1 * 0.1F);
                }

            }
        } else {
            this.dolphin.setSpeed(0.0F);
            this.dolphin.setXxa(0.0F);
            this.dolphin.setYya(0.0F);
            this.dolphin.setZza(0.0F);
        }
    }
}