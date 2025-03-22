package com.peeko32213.unusualprehistory.common.data.attack;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.peeko32213.unusualprehistory.common.entity.util.helper.HitboxAttacks;
import com.peeko32213.unusualprehistory.core.registry.UPAttackRegistry;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.Attributes;

import java.util.Objects;

public class LargeHitBoxAttackWithTargetCheck implements EntityAttack{


    public static final Codec<LargeHitBoxAttackWithTargetCheck> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.FLOAT.fieldOf("attack_modifier").forGetter(LargeHitBoxAttackWithTargetCheck::getAttackModifier),
            Codec.FLOAT.fieldOf("knockback").forGetter(LargeHitBoxAttackWithTargetCheck::getKnockback),
            Codec.FLOAT.fieldOf("radius").forGetter(LargeHitBoxAttackWithTargetCheck::getRadius),
            Codec.DOUBLE.optionalFieldOf("angle_first",-Math.PI/2).forGetter(LargeHitBoxAttackWithTargetCheck::getAngleFirst),
            Codec.DOUBLE.optionalFieldOf("angle_last",Math.PI/2).forGetter(LargeHitBoxAttackWithTargetCheck::getAngleLast),
            Codec.BOOL.fieldOf("disable_shield").forGetter(LargeHitBoxAttackWithTargetCheck::isDisableShield),
            Codec.BOOL.optionalFieldOf("hitbox_outline", false).forGetter(LargeHitBoxAttackWithTargetCheck::isHitboxOutline)
    ).apply(instance, LargeHitBoxAttackWithTargetCheck::new));


    private final float attackModifier;
    private final float knockback;
    private final float radius;
    private final double angleFirst;
    private final double angleLast;
    private final boolean disableShield;
    private final boolean hitboxOutline;

    public LargeHitBoxAttackWithTargetCheck(float attackModifier, float knockback, float radius, double angleFirst, double angleLast, boolean disableShield, boolean hitboxOutline) {
        this.attackModifier = attackModifier;
        this.knockback = knockback;
        this.radius = radius;
        this.angleFirst = angleFirst;
        this.angleLast = angleLast;
        this.disableShield = disableShield;
        this.hitboxOutline = hitboxOutline;
    }
    
    @Override
    public void performAttack(PathfinderMob entity) {
        HitboxAttacks.largeAttackWithTargetCheck(entity.damageSources().mobAttack(entity), (float) Objects.requireNonNull(entity.getAttribute(Attributes.ATTACK_DAMAGE)).getValue() - attackModifier, knockback,entity ,entity.position(), radius, angleFirst, angleLast,-1,3,disableShield, hitboxOutline);
    }

    @Override
    public Codec<? extends EntityAttack> codec() {
        return UPAttackRegistry.LARGE_HITBOX_ATTACK_WITH_TARGET_CHECK.get();
    }

    public float getAttackModifier() {
        return attackModifier;
    }

    public float getKnockback() {
        return knockback;
    }

    public float getRadius() {
        return radius;
    }

    public double getAngleFirst() {
        return angleFirst;
    }

    public double getAngleLast() {
        return angleLast;
    }

    public boolean isDisableShield() {
        return disableShield;
    }

    public boolean isHitboxOutline() {
        return hitboxOutline;
    }
}
