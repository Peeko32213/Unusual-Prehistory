package com.peeko32213.unusualprehistory.common.entity.msc.util.goal;

import com.peeko32213.unusualprehistory.common.entity.msc.util.dino.BaseDinosaurAnimalEntity;
import com.peeko32213.unusualprehistory.common.entity.msc.util.interfaces.PackHunter;

public class PackHunterGoal extends PackHunterTargetingGoal {

    public PackHunter packAnimal;
    public int packSizeMandatory;

    public PackHunterGoal(BaseDinosaurAnimalEntity mob, Class aClass, int chance, boolean sight, int packSizeMandatory) {
        super(mob, aClass, chance, sight, false, null);
        packAnimal = (PackHunter) mob;
        this.packSizeMandatory = packSizeMandatory;
    }

    public boolean canUse() {
        if (super.canUse()) {
            return packAnimal.getPackSize() >= packSizeMandatory;
        }
        return false;
    }
}