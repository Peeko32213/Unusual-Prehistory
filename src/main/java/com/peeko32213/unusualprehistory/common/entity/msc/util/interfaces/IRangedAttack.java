package com.peeko32213.unusualprehistory.common.entity.msc.util.interfaces;

import com.peeko32213.unusualprehistory.common.entity.msc.util.ranged.AttackSound;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;

public interface IRangedAttack {

    public Projectile getProjectile(Level world, double d2, double d3, double d4);

    public AttackSound getDefaultAttackSound();

}
