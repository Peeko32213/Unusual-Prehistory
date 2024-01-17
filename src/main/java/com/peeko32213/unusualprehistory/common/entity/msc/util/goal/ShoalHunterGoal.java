package com.peeko32213.unusualprehistory.common.entity.msc.util.goal;

import com.peeko32213.unusualprehistory.common.entity.msc.util.dino.EntityBaseDinosaurAnimal;
import com.peeko32213.unusualprehistory.common.entity.msc.util.interfaces.PackHunter;
import net.minecraft.world.entity.animal.WaterAnimal;

public class ShoalHunterGoal extends ShoalHunterTargetingGoal {

    public PackHunter packAnimal;
    public int packSizeMandatory;

    public ShoalHunterGoal(WaterAnimal mob, Class aClass, int chance, boolean sight, int packSizeMandatory) {
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