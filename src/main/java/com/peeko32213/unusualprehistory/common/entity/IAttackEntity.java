package com.peeko32213.unusualprehistory.common.entity;

public interface IAttackEntity {
    void performAttack();
    void afterAttack();
    int getMaxAttackCooldown();
    int getAttackCooldown();
    void setAttackCooldown(int cooldown);
}
