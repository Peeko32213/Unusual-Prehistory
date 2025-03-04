package com.peeko32213.unusualprehistory.common.entity.util.goal;

import com.peeko32213.unusualprehistory.common.entity.base.PrehistoricEntity;
import com.peeko32213.unusualprehistory.common.entity.util.interfaces.IPackHunter;

public class PackHunterGoal extends PackHunterTargetingGoal {

    public IPackHunter packAnimal;
    public int packSizeMandatory;

    public PackHunterGoal(PrehistoricEntity mob, Class aClass, int chance, boolean sight, int packSizeMandatory) {
        super(mob, aClass, chance, sight, false, null);
        packAnimal = (IPackHunter) mob;
        this.packSizeMandatory = packSizeMandatory;
    }

    public boolean canUse() {
        if (super.canUse()) {
            return packAnimal.getPackSize() >= packSizeMandatory;
        }
        return false;
    }
}