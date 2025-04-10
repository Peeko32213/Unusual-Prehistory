package com.peeko32213.unusualprehistory.common.entity.util.helper;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HitboxAttacks {


    private static final double d = 0.6f;
    private static final double angleVar = Math.PI / 12;

    public static void largeAttack(DamageSource damageSource, float damage, float knockback, PathfinderMob entityIn, Vec3 pos0, double radius, double angleFirst, double angleLast, double hInf, double hSup, boolean disableShield) {
        largeAttack(damageSource, damage, knockback, entityIn, pos0, radius, angleFirst, angleLast, hInf, hSup, disableShield, false);
    }

    public static void largeAttack(DamageSource source, float damage, float knockback, PathfinderMob entityIn, Vec3 pos0, double radius, double angleFirst, double angleLast, double hInf, double hSup, boolean disableShield, boolean hitBoxOutline) {

        Vec2 knockVec = MathHelpers.OrizontalAimVector(
                MathHelpers.AimVector(new Vec3(-entityIn.position().x, -entityIn.position().y, -entityIn.position().z),
                        new Vec3(-Objects.requireNonNull(entityIn.getTarget()).position().x, -entityIn.getTarget().position().y, -entityIn.getTarget().position().z)
                ));

        Vec2 aim = MathHelpers.OrizontalAimVector(entityIn.getLookAngle());
        Level worldIn = entityIn.level();

        for (int i = 0; i <= radius / d; ++i) {

            for (int j = 0; j <= (angleLast - angleFirst) / angleVar; ++j) {

                double angle = angleFirst + angleVar * j;

                double x = pos0.x + i * d * (aim.x * Math.cos(angle) - aim.y * Math.sin(angle));
                double z = pos0.z + i * d * (aim.y * Math.cos(angle) + aim.x * Math.sin(angle));

                for (int k = 0; k <= (hSup - hInf) / d; ++k) {

                    double y = pos0.y + hInf + k * d;
                    AABB scanAbove = new AABB(x - d, y - 4 * d, z - d, x + d, y + 2 * d, z + d);
                    if (hitBoxOutline) {
                        if(worldIn instanceof ServerLevel level) {
                            hitboxOutline(scanAbove, level);
                        }
                    }
                    List<LivingEntity> entities = new ArrayList<>(worldIn.getEntitiesOfClass(LivingEntity.class, scanAbove));

                    if (!entities.isEmpty()) {
                        for (LivingEntity target : entities) {

                            if (target != entityIn) {
                                if (target.invulnerableTime == 0) {
                                    if (target instanceof Player && disableShield) {
                                        disableShield((Player) target, target.getMainHandItem(), target.getOffhandItem(), entityIn);
                                    }
                                    target.hurt(source, damage);
                                    target.setLastHurtByMob(entityIn);
                                    target.knockback(knockback, knockVec.x, knockVec.y);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    public static void largeAttackWithTargetCheck(DamageSource damageSource, float damage, float knockback, PathfinderMob entityIn, Vec3 pos0, double radius, double angleFirst, double angleLast, double hInf, double hSup, boolean disableShield) {
        largeAttackWithTargetCheck(damageSource, damage, knockback, entityIn, pos0, radius, angleFirst, angleLast, hInf, hSup, disableShield, false);
    }

    public static void largeAttackWithTargetCheck(DamageSource damageSource, float damage, float knockback, PathfinderMob entityIn, Vec3 pos0, double radius, double angleFirst, double angleLast, double hInf, double hSup, boolean disableShield, boolean hitBoxOutline) {

        Vec2 knockVec = MathHelpers.OrizontalAimVector(
                MathHelpers.AimVector(new Vec3(-entityIn.position().x, -entityIn.position().y, -entityIn.position().z),
                        new Vec3(-entityIn.getTarget().position().x, -entityIn.getTarget().position().y, -entityIn.getTarget().position().z)
                ));

        Vec2 aim = MathHelpers.OrizontalAimVector(entityIn.getLookAngle());
        Level worldIn = entityIn.level();

        for(int i = 0; i<=radius/d; ++i) {

            for(int j = 0; j<=(angleLast-angleFirst)/angleVar; ++j) {

                double angle = angleFirst + angleVar*j;

                double x = pos0.x + i*d*(aim.x*Math.cos(angle) - aim.y * Math.sin(angle));
                double z = pos0.z + i*d*(aim.y*Math.cos(angle) + aim.x * Math.sin(angle));

                for(int k = 0; k<=(hSup-hInf)/d; ++k) {

                    double y = pos0.y + hInf + k*d;
                    AABB scanAbove = new AABB(x-d, y - 4d, z- d, x+ d, y + 2d, z+ d);
                    List<LivingEntity> entities = new ArrayList<>(worldIn.getEntitiesOfClass(LivingEntity.class, scanAbove));
                    if (hitBoxOutline) {
                        if(worldIn instanceof ServerLevel level) {
                            hitboxOutline(scanAbove, level);
                        }
                    }

                    if(!entities.isEmpty()) {
                        for (LivingEntity target : entities) {

                            if (target == entityIn.getTarget()) {
                                if (target.invulnerableTime == 0) {
                                    if (target instanceof Player && disableShield) {
                                        disableShield((Player) target, target.getMainHandItem(), target.getOffhandItem(), entityIn);
                                    }
                                    target.hurt(damageSource, damage);
                                    target.setLastHurtByMob(entityIn);
                                    target.knockback(knockback, knockVec.x, knockVec.y);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public static void pivotedPolyHitCheck(PathfinderMob entityIn, LivingEntity source, Vec3 boxOffset, double attackWidth, double attackHeight, double attackLength, ServerLevel world, float damage, DamageSource damageSource, float knockback, boolean disableShield, boolean checkTarget) {
        pivotedPolyHitCheck(entityIn, source, boxOffset, attackWidth, attackHeight, attackLength, world, damage, damageSource, knockback, disableShield, checkTarget, false);
    }

    public static void pivotedPolyHitCheck(PathfinderMob entityIn, LivingEntity source, Vec3 boxOffset, double attackWidth, double attackHeight, double attackLength, ServerLevel world, float damage, DamageSource damageSource, float knockback, boolean disableShield, boolean checkTarget, boolean hitBoxOutline) {
        //attackRadius is in blocks
        Vec3 sourcePos = source.position();
        double entityAngle = (source.getYRot());
        Vec3 truePos = sourcePos.add(boxOffset);
        double[] trueXZ = {truePos.x, truePos.z};

        AffineTransform.getRotateInstance(Math.toRadians(entityAngle), sourcePos.x, sourcePos.z).transform(trueXZ, 0, trueXZ, 0, 1);
        Vec3 rotatedPos = new Vec3(trueXZ[0], truePos.y, trueXZ[1]);
        BlockPos finalPos = BlockPos.containing(rotatedPos.x, rotatedPos.y, rotatedPos.z);
        AABB Hitbox = new AABB(finalPos).inflate(attackWidth, attackHeight, attackLength);

        if (hitBoxOutline) {
            hitboxOutline(Hitbox, world);
        }
        List<LivingEntity> entities = new ArrayList<>(world.getEntitiesOfClass(LivingEntity.class, Hitbox));

        for (LivingEntity target : entities) {
            if(checkTarget) {
                if (target != source && target == entityIn.getTarget()) {
                    if (target instanceof Player && disableShield) {
                        disableShield((Player) target, target.getMainHandItem(), target.getOffhandItem(), source);
                    }

                    Vec2 knockVec = MathHelpers.OrizontalAimVector(
                            MathHelpers.AimVector(new Vec3(-source.position().x, -source.position().y, -source.position().z),
                                    new Vec3(-target.position().x, -target.position().y, -target.position().z)
                            ));

                    target.hurt(damageSource, damage);
                    target.setLastHurtByMob(source);
                    target.knockback(knockback, knockVec.x, knockVec.y);
                }
            }
            else {
                if (target != source) {
                    if (target instanceof Player && disableShield) {
                        disableShield((Player) target, target.getMainHandItem(), target.getOffhandItem(), source);
                    }

                    Vec2 knockVec = MathHelpers.OrizontalAimVector(
                            MathHelpers.AimVector(new Vec3(-source.position().x, -source.position().y, -source.position().z),
                                    new Vec3(-target.position().x, -target.position().y, -target.position().z)
                            ));

                    target.hurt(damageSource, damage);
                    target.setLastHurtByMob(source);
                    target.knockback(knockback, knockVec.x, knockVec.y);
                }
            }
        }
    }

    public static void disableShield(Player pPlayer, ItemStack mainHand, ItemStack offHand, Entity source) {
        if (!mainHand.isEmpty() && mainHand.is(Items.SHIELD) && pPlayer.isBlocking() && pPlayer.invulnerableTime == 0) {
            pPlayer.getCooldowns().addCooldown(Items.SHIELD, 100);
            source.level().broadcastEntityEvent(pPlayer, (byte) 30);

        } else if (!offHand.isEmpty() && offHand.is(Items.SHIELD) && pPlayer.isBlocking() && pPlayer.invulnerableTime == 0) {
            pPlayer.getCooldowns().addCooldown(Items.SHIELD, 100);
            source.level().broadcastEntityEvent(pPlayer, (byte) 30);
        }

    }

    public static void hitboxOutline(AABB box, ServerLevel world) {
        world.sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE, (box.maxX), (box.maxY), (box.maxZ), 1, 0.0D, 0.0D, 0.0D, 0.0D);
        world.sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE, (box.maxX), (box.minY), (box.minZ), 1, 0.0D, 0.0D, 0.0D, 0.0D);
        world.sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE, (box.maxX), (box.minY), (box.maxZ), 1, 0.0D, 0.0D, 0.0D, 0.0D);
        world.sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE, (box.maxX), (box.maxY), (box.minZ), 1, 0.0D, 0.0D, 0.0D, 0.0D);

        world.sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE, (box.minX), (box.maxY), (box.maxZ), 1, 0.0D, 0.0D, 0.0D, 0.0D);
        world.sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE, (box.minX), (box.minY), (box.minZ), 1, 0.0D, 0.0D, 0.0D, 0.0D);
        world.sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE, (box.minX), (box.minY), (box.maxZ), 1, 0.0D, 0.0D, 0.0D, 0.0D);
        world.sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE, (box.minX), (box.maxY), (box.minZ), 1, 0.0D, 0.0D, 0.0D, 0.0D);
    }
}