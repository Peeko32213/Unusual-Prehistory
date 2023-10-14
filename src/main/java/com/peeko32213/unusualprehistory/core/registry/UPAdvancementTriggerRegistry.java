package com.peeko32213.unusualprehistory.core.registry;

import com.peeko32213.unusualprehistory.core.registry.util.BarinaTameTrigger;
import net.minecraft.advancements.CriteriaTriggers;

public class UPAdvancementTriggerRegistry {

    public static BarinaTameTrigger BARINA_TRIGGER = new BarinaTameTrigger();

    public static void init(){
        CriteriaTriggers.register(BARINA_TRIGGER);
    }

}