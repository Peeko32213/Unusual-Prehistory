package com.peeko32213.unusualprehistory.common.data.attack;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.peeko32213.unusualprehistory.common.entity.util.helper.HitboxAttacks;
import com.peeko32213.unusualprehistory.core.registry.UPAttackRegistry;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.phys.Vec3;

import java.util.Objects;

public class PivotedPolyHitCheckAttack implements EntityAttack {


    public static final Codec<PivotedPolyHitCheckAttack> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Vec3.CODEC.fieldOf("attack_offset").forGetter(PivotedPolyHitCheckAttack::getAttackOffset),
            Codec.FLOAT.fieldOf("attack_width").forGetter(PivotedPolyHitCheckAttack::getAttackWidth),
            Codec.FLOAT.fieldOf("attack_height").forGetter(PivotedPolyHitCheckAttack::getAttackHeight),
            Codec.FLOAT.fieldOf("attack_length").forGetter(PivotedPolyHitCheckAttack::getAttackLength),
            Codec.FLOAT.fieldOf("attack_modifier").forGetter(PivotedPolyHitCheckAttack::getAttackModifier),
            Codec.FLOAT.fieldOf("knockback").forGetter(PivotedPolyHitCheckAttack::getKnockback),
            Codec.BOOL.fieldOf("disable_shield").forGetter(PivotedPolyHitCheckAttack::isDisableShield),
            Codec.BOOL.fieldOf("check_target").forGetter(PivotedPolyHitCheckAttack::isCheckTarget),
            Codec.BOOL.optionalFieldOf("hitbox_outline", false).forGetter(PivotedPolyHitCheckAttack::isHitboxOutline)
    ).apply(instance, PivotedPolyHitCheckAttack::new));


    private final Vec3 attackOffset;
    private final float attackWidth;
    private final float attackHeight;
    private final float attackLength;
    private final float attackModifier;
    private final float knockback;
    private final boolean disableShield;
    private final boolean checkTarget;
    private final boolean hitboxOutline;

    public PivotedPolyHitCheckAttack(Vec3 attackOffset, float attackWidth, float attackHeight, float attackLength, float attackModifier, float knockback, boolean disableShield, boolean checkTarget, boolean hitboxOutline) {
        this.attackOffset = attackOffset;
        this.attackModifier = attackModifier;
        this.knockback = knockback;
        this.attackWidth = attackWidth;
        this.attackHeight = attackHeight;
        this.attackLength = attackLength;
        this.disableShield = disableShield;
        this.checkTarget = checkTarget;
        this.hitboxOutline = hitboxOutline;

    }

    @Override
    public void performAttack(PathfinderMob entity) {
        if (entity.level() instanceof ServerLevel level) {
            HitboxAttacks.pivotedPolyHitCheck(entity, entity, attackOffset, attackWidth, attackHeight, attackLength, level, (float) Objects.requireNonNull(entity.getAttribute(Attributes.ATTACK_DAMAGE)).getValue() - attackModifier, entity.damageSources().mobAttack(entity), knockback, disableShield, checkTarget, hitboxOutline);
        }
    }

    @Override
    public Codec<? extends EntityAttack> codec() {
        return UPAttackRegistry.PIVOTED_POLY_HIT_CHECK_ATTACK.get();

    }

    public Vec3 getAttackOffset() {
        return attackOffset;
    }

    public float getAttackModifier() {
        return attackModifier;
    }

    public float getKnockback() {
        return knockback;
    }

    public float getAttackHeight() {
        return attackHeight;
    }

    public float getAttackLength() {
        return attackLength;
    }

    public float getAttackWidth() {
        return attackWidth;
    }

    public boolean isDisableShield() {
        return disableShield;
    }

    public boolean isCheckTarget() {
        return checkTarget;
    }

    public boolean isHitboxOutline() {
        return hitboxOutline;
    }
}
