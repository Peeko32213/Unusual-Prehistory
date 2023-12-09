package com.peeko32213.unusualprehistory.common.entity.msc.util.interfaces;

import net.minecraft.world.entity.LivingEntity;

public interface PackHunter {

    default boolean isPackFollower() {
        return this.getPriorPackMember() != null;
    }

    default boolean hasPackFollower() {
        return this.getAfterPackMember() != null;
    }

    default PackHunter getPackLeader() {
        PackHunter leader = this;
        while (leader.getPriorPackMember() != null && leader.getPriorPackMember() != this) {
            leader = leader.getPriorPackMember();
        }
        return leader;
    }

    default int getPackSize() {
        PackHunter leader = getPackLeader();
        int i = 1;
        while (leader.getAfterPackMember() != null) {
            leader = leader.getAfterPackMember();
            i++;
        }
        return i;
    }

    default boolean isInPack(PackHunter packAnimal) {
        PackHunter leader = getPackLeader();
        while (leader.getAfterPackMember() != null) {
            if (packAnimal.equals(leader)) {
                return true;
            }
            leader = leader.getAfterPackMember();
        }
        return false;
    }

    default boolean isValidLeader(PackHunter packLeader) {
        return !packLeader.isPackFollower() && ((LivingEntity) packLeader).isAlive();
    }

    PackHunter getPriorPackMember();

    PackHunter getAfterPackMember();

    void setPriorPackMember(PackHunter animal);

    void setAfterPackMember(PackHunter animal);

    default void joinPackOf(PackHunter caravanHeadIn) {
        setPriorPackMember(caravanHeadIn);
        caravanHeadIn.setAfterPackMember(this);
        resetPackFlags();
    }

    default void leavePack() {
        if (this.getPriorPackMember() != null) {
            this.getPriorPackMember().setAfterPackMember(null);
        }
        this.setPriorPackMember(null);
        resetPackFlags();
    }

    default void resetPackFlags() {
    }
}
