package com.peeko32213.unusualprehistory.common.entity.custom.ai.goal;

import com.peeko32213.unusualprehistory.common.entity.custom.base.old.PrehistoricEntityOld;
import com.peeko32213.unusualprehistory.common.entity.util.interfaces.IPackHunter;

public class PackHunterGoal extends PackHunterTargetingGoal {

    public IPackHunter packAnimal;
    public int packSizeMandatory;

    public PackHunterGoal(PrehistoricEntityOld mob, Class aClass, int chance, boolean sight, int packSizeMandatory) {
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