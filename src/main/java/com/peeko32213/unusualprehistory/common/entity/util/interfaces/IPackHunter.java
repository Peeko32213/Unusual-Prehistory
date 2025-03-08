package com.peeko32213.unusualprehistory.common.entity.util.interfaces;

import net.minecraft.world.entity.LivingEntity;

public interface IPackHunter {

    default boolean isPackFollower() {
        return this.getPriorPackMember() != null;
    }

    default boolean hasPackFollower() {
        return this.getAfterPackMember() != null;
    }

    default IPackHunter getPackLeader() {
        IPackHunter leader = this;
        while (leader.getPriorPackMember() != null && leader.getPriorPackMember() != this) {
            leader = leader.getPriorPackMember();
        }
        return leader;
    }

    default int getPackSize() {
        IPackHunter leader = getPackLeader();
        int i = 1;
        while (leader.getAfterPackMember() != null) {
            leader = leader.getAfterPackMember();
            i++;
        }
        return i;
    }

    default boolean isInPack(IPackHunter packAnimal) {
        IPackHunter leader = getPackLeader();
        while (leader.getAfterPackMember() != null) {
            if (packAnimal.equals(leader)) {
                return true;
            }
            leader = leader.getAfterPackMember();
        }
        return false;
    }

    default boolean isValidLeader(IPackHunter packLeader) {
        return !packLeader.isPackFollower() && ((LivingEntity) packLeader).isAlive();
    }

    IPackHunter getPriorPackMember();

    IPackHunter getAfterPackMember();

    void setPriorPackMember(IPackHunter animal);

    void setAfterPackMember(IPackHunter animal);

    default void joinPackOf(IPackHunter caravanHeadIn) {
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
