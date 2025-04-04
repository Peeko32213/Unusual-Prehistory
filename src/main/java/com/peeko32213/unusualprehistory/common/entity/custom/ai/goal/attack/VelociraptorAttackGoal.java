package com.peeko32213.unusualprehistory.common.entity.custom.ai.goal.attack;

import com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.VelociraptorEntity;
import com.peeko32213.unusualprehistory.common.entity.util.helper.HitboxAttacks;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class VelociraptorAttackGoal extends Goal {

    protected final VelociraptorEntity velociraptor;
    private int animTime = 0;

    Vec3 biteOffSet = new Vec3(0, 0.25, 1.2);

    public VelociraptorAttackGoal(VelociraptorEntity pMob) {
        this.velociraptor = pMob;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    public boolean canUse() {
        return velociraptor.getTarget() != null && velociraptor.getTarget().isAlive();
    }

    public void start() {
        velociraptor.setAggro(!velociraptor.isVehicle());
        velociraptor.setAnimationState(0);
        this.animTime = 0;
    }

    public void stop() {
        velociraptor.setAggro(false);
        velociraptor.setAnimationState(0);
    }

    public void tick() {
        LivingEntity target = velociraptor.getTarget();
        if (target != null) {
            velociraptor.lookAt(EntityAnchorArgument.Anchor.EYES, target.getEyePosition());
            double distance = velociraptor.distanceToSqr(target.getX(), target.getY(), target.getZ());
            int animState = velociraptor.getAnimationState();

            switch (animState) {
                case 21 -> tickBiteAttack();
                case 22 -> tickKickAttack();
                default -> {
                    this.velociraptor.getLookControl().setLookAt(target, 30.0F, 30.0F);
                    this.velociraptor.getNavigation().moveTo(target, 1.0D);
                    this.checkForCloseRangeAttack(distance);
                }
            }
        }
    }

    protected void checkForCloseRangeAttack (double distance){
        int meleeRange = 10;
        if (distance <= meleeRange) {
            int r = (velociraptor.getRandom().nextInt(100) + 1);

            if (r <= 60) {
                velociraptor.setAnimationState(21);
            }
            else {
                velociraptor.setAnimationState(22);
            }
        }
    }

    protected void tickBiteAttack () {
        animTime++;
        LivingEntity target = this.velociraptor.getTarget();
        this.velociraptor.lookAt(target, 100000, 100000);
        this.velociraptor.yBodyRot = this.velociraptor.yHeadRot;

        if(animTime==9) {
            preformBiteAttack();
        }
        if(animTime>=10) {
            animTime=0;
            this.velociraptor.setAnimationState(0);
        }
    }

    protected void tickKickAttack () {
        animTime++;
        LivingEntity target = this.velociraptor.getTarget();
        this.velociraptor.lookAt(target, 100000, 100000);
        this.velociraptor.yBodyRot = this.velociraptor.yHeadRot;

        if(animTime==15) {
            preformKickAttack();
        }
        if(animTime>=16) {
            animTime=0;
            this.velociraptor.setAnimationState(0);
        }
    }

    protected void preformBiteAttack () {
        HitboxAttacks.pivotedPolyHitCheck(velociraptor, velociraptor, this.biteOffSet, 0.5, 1, 0.8, (ServerLevel)velociraptor.level(), (float) velociraptor.getAttribute(Attributes.ATTACK_DAMAGE).getValue(), (velociraptor.damageSources().mobAttack(velociraptor)), 0.1F, false, true, false);
    }

    protected void preformKickAttack () {
        HitboxAttacks.pivotedPolyHitCheck(velociraptor, velociraptor, this.biteOffSet, 0.6, 1, 1.0, (ServerLevel)velociraptor.level(), (float) velociraptor.getAttribute(Attributes.ATTACK_DAMAGE).getValue() * 1.25F, (velociraptor.damageSources().mobAttack(velociraptor)), 0.15F, false, true, false);
    }
}