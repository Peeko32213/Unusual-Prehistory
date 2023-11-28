package com.peeko32213.unusualprehistory.common.entity.msc.util.interfaces;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;

public interface  CustomFollower {
    boolean shouldFollow();

    default void followEntity(TamableAnimal tameable, LivingEntity owner, double followSpeed){
        tameable.navigation.moveTo(owner, followSpeed);
    }
}
