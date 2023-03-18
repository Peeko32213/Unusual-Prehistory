package com.peeko32213.unusualprehistory.common.entity.msc.util;

import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.PanicGoal;

public class BabyPanicGoal extends PanicGoal {
    public BabyPanicGoal(PathfinderMob pMob, double pSpeedModifier) {
        super(pMob, pSpeedModifier);
    }

    @Override
    protected boolean shouldPanic() {
        return mob.getLastHurtByMob() != null && mob.isBaby() || mob.isOnFire();
    }
}
