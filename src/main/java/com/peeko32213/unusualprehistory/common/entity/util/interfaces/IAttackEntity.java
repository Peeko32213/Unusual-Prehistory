package com.peeko32213.unusualprehistory.common.entity.util.interfaces;

public interface IAttackEntity {
    void performAttack();
    void afterAttack();
    int getMaxAttackCooldown();
    int getAttackCooldown();
    void setAttackCooldown(int cooldown);
}
