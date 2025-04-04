package com.peeko32213.unusualprehistory.common.entity.custom.ai.goal.attack;

import com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.TriceratopsEntity;
import com.peeko32213.unusualprehistory.common.entity.util.helper.HitboxAttacks;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class TriceratopsAttackGoal extends Goal {

    protected final TriceratopsEntity triceratops;
    private int animTime = 0;

    private int chargeCD;
    private Vec3 chargeMotion = new Vec3(0,0,0);

    Vec3 chargeOffSet = new Vec3(0, 1, 2);
    Vec3 strikeOffSet = new Vec3(0, 1.5, 3);

    public TriceratopsAttackGoal(TriceratopsEntity pMob) {
        this.triceratops = pMob;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    public boolean canUse() {
        return triceratops.getTarget() != null && triceratops.getTarget().isAlive();
    }

    public void start() {
        triceratops.setAggro(!triceratops.isVehicle());
        triceratops.setAnimationState(0);
        this.animTime = 0;
        this.chargeCD = 0;
    }

    public void stop() {
        triceratops.setAggro(false);
        triceratops.setAnimationState(0);
    }

    public void tick() {
        LivingEntity target = triceratops.getTarget();
        if (target != null) {
            triceratops.lookAt(EntityAnchorArgument.Anchor.EYES, target.getEyePosition());
            double distance = triceratops.distanceToSqr(target.getX(), target.getY(), target.getZ());
            int animState = triceratops.getAnimationState();

            switch (animState) {
                case 21, 22 -> tickStrikeAttack();
//            case 23 -> tickChargeAttack();
                default -> {
                    this.chargeCD = Math.max(this.chargeCD - 1, 0);
                    this.triceratops.getLookControl().setLookAt(target, 30.0F, 30.0F);
                    this.triceratops.getNavigation().moveTo(target, 1.0D);
                    this.checkForCloseRangeAttack(distance);
                }
            }
        }
    }

    protected void checkForCloseRangeAttack (double distance){
        int meleeRange = 36;
        if (distance <= meleeRange) {
            int r = (this.triceratops.getRandom().nextInt(100) + 1);
            // 21 is strike1, 22 is strike2, 23 is charge

            if (distance <= 36) {
                if (r <= 60) {
                    this.triceratops.setAnimationState(22);
                }
                else if (60 < r && r <= 80) {
                    this.triceratops.setAnimationState(21);
                }
//                else if (r >= 80) {
//                    this.triceratops.setAnimationState(23);
//                }
            } else {
                if (r <= 40) {
                    this.triceratops.setAnimationState(21);
                }
                else if (40 < r && r <= 70) {
                    this.triceratops.setAnimationState(22);
                }
//                else if (r > 70) {
//                    this.triceratops.setAnimationState(23);
//                }
            }
        }
//        else if (this.ticksUntilNextAttack <= 0 && this.chargeCD <= 0 && this.triceratops.onGround()) {
//            this.triceratops.setAnimationState(23);
//        }
    }

    protected void tickStrikeAttack () {
        animTime++;
        LivingEntity target = this.triceratops.getTarget();
        this.triceratops.lookAt(target, 100000, 100000);
        this.triceratops.yBodyRot = this.triceratops.yHeadRot;

        if(animTime==9) {
            //System.out.println("strike");
            preformStrikeAttack();
        }
        if(animTime>=10) {
            animTime=0;
            this.triceratops.setAnimationState(0);
        }
    }

//    protected void tickChargeAttack () {
//        animTime++;
//        this.triceratops.getNavigation().stop();
//        this.triceratops.lookAt(this.triceratops.getTarget(), 100000, 100000);
//        this.triceratops.yBodyRot = this.triceratops.yHeadRot;
//
//        // Find charge direction
//        if (animTime == 1) {
//            this.triceratops.lookAt(this.triceratops.getTarget(), 100000, 100000);
//            this.triceratops.yBodyRot = this.triceratops.yHeadRot;
//            Entity target = this.triceratops.getTarget();
//            Vec3 targetPos = (target.position());
//
//            double x = -((this.triceratops.position().x - targetPos.x));
//            double z = -((this.triceratops.position().z - targetPos.z));
//
//            this.chargeMotion = new Vec3(x, this.triceratops.getDeltaMovement().y, z).normalize();
//        }
//
//        // Only move after charged up
//        if(animTime >= 19 && animTime < 38) {
//            this.triceratops.setDeltaMovement(chargeMotion.x/2, this.triceratops.getDeltaMovement().y, chargeMotion.z/2);
//
//            // Attack while charging
//            HitboxAttacks.pivotedPolyHitCheck(triceratops, this.triceratops, this.chargeOffSet, 1.3, 3, 1.2, (ServerLevel) this.triceratops.level(), (float) triceratops.getAttribute(Attributes.ATTACK_DAMAGE).getValue() / 1.25F, this.triceratops.damageSources().mobAttack(triceratops), 2.5F, true, false, false);
//        }
//
//        if(animTime >= 39) {
//            animTime=0;
//            this.triceratops.setAnimationState(0);
//            this.resetAttackCooldown();
//            this.ticksUntilNextPathRecalculation = 0;
//            this.chargeCD = this.triceratops.getRandom().nextInt(300) + 50;
//        }
//    }

    protected void preformStrikeAttack () {
        HitboxAttacks.pivotedPolyHitCheck(triceratops, this.triceratops, this.strikeOffSet, 1.3, 2, 1.4, (ServerLevel)this.triceratops.level(), (float) triceratops.getAttribute(Attributes.ATTACK_DAMAGE).getValue(), (this.triceratops.damageSources().mobAttack(triceratops)), 1.5F, false, true, false);
    }
}