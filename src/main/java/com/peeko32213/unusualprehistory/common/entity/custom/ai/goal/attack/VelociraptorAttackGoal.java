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
    private int pounceCooldown = 0;

    Vec3 biteOffSet = new Vec3(0, 0.5, 1.15);

    public VelociraptorAttackGoal(VelociraptorEntity pMob) {
        this.velociraptor = pMob;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    public boolean canUse() {
        return velociraptor.getTarget() != null && velociraptor.getTarget().isAlive() && velociraptor.getHealth() > velociraptor.getMaxHealth() * 0.25F;
    }

    public void start() {
        velociraptor.setRunning(!velociraptor.isVehicle());
        velociraptor.setAnimationState(0);
        this.animTime = 0;
        this.pounceCooldown = 0;
    }

    public void stop() {
        velociraptor.setRunning(false);
        velociraptor.setAnimationState(0);
    }

    public void tick() {
        LivingEntity target = velociraptor.getTarget();
        if (target != null) {
            velociraptor.lookAt(EntityAnchorArgument.Anchor.EYES, target.getEyePosition());
            double distance = velociraptor.distanceToSqr(target.getX(), target.getY(), target.getZ());
            int animState = velociraptor.getAnimationState();

            switch (animState) {
                case 21 -> {
                    this.velociraptor.getNavigation().moveTo(target, 1.0D);
                    tickBiteAttack();
                }
                case 22 -> tickKickAttack();
                default -> {
                    this.pounceCooldown = Math.max(this.pounceCooldown - 1, 0);
                    this.velociraptor.getLookControl().setLookAt(target, 30.0F, 30.0F);
                    this.velociraptor.getNavigation().moveTo(target, 1.0D);
                    this.checkForCloseRangeAttack(distance);
                }
            }
        }
    }

    protected void checkForCloseRangeAttack (double distance){
        int meleeRange = 10;
        int r = (velociraptor.getRandom().nextInt(100) + 1);
        if (distance <= meleeRange) {
            if (r <= 75) {
                velociraptor.setAnimationState(21);
            }
            else {
                velociraptor.setAnimationState(22);
            }
        }
        else if (distance > meleeRange && this.pounceCooldown <= 0) {
            this.pounce();
        }
    }

    protected void tickBiteAttack () {
        animTime++;
        if(animTime==9) {
            HitboxAttacks.pivotedPolyHitCheck(velociraptor, velociraptor, this.biteOffSet, 0.25, -0.15, 0.2, (ServerLevel)velociraptor.level(), (float) velociraptor.getAttribute(Attributes.ATTACK_DAMAGE).getValue(), (velociraptor.damageSources().mobAttack(velociraptor)), 0.1F, false, true, false);
        }
        if(animTime>=10) {
            animTime=0;
            this.velociraptor.setAnimationState(0);
        }
    }

    protected void tickKickAttack () {
        animTime++;
        if(animTime==15) {
            HitboxAttacks.pivotedPolyHitCheck(velociraptor, velociraptor, this.biteOffSet, 0.3, -0.15, 0.2, (ServerLevel)velociraptor.level(), (float) velociraptor.getAttribute(Attributes.ATTACK_DAMAGE).getValue() * 1.25F, (velociraptor.damageSources().mobAttack(velociraptor)), 0.15F, false, true, false);
        }
        if(animTime>=16) {
            animTime=0;
            this.velociraptor.setAnimationState(0);
        }
    }

    public void pounce() {
        Vec3 diff = new Vec3(velociraptor.getTarget().getX() - velociraptor.getX(), (velociraptor.getTarget().getY() - velociraptor.getY()) + 1.25, velociraptor.getTarget().getZ() -velociraptor.getZ());
        Vec3 vel = diff.multiply(0.5D,0.4D, 0.5D).add(0,0.45,0).normalize();
        velociraptor.setDeltaMovement(vel);
        this.pounceCooldown = velociraptor.getRandom().nextInt(25) + 50;
    }
}