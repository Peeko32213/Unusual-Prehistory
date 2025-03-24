package com.peeko32213.unusualprehistory.core.registry;

import com.peeko32213.unusualprehistory.core.registry.util.BarinaTameTrigger;
import net.minecraft.advancements.CriteriaTriggers;

import static com.peeko32213.unusualprehistory.UnusualPrehistory.modPrefix;

public class UPAdvancementTriggerRegistry {

    public static BarinaTameTrigger BARINA_TRIGGER = new BarinaTameTrigger(modPrefix("barina_trigger"));

    public static void init(){
        CriteriaTriggers.register(BARINA_TRIGGER);
    }

}