package com.peeko32213.unusualprehistory.core.registry;

import com.peeko32213.unusualprehistory.core.registry.util.BarinaTameTrigger;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.resources.ResourceLocation;

import static com.peeko32213.unusualprehistory.UnusualPrehistory.prefix;

public class UPAdvancementTriggerRegistry {

    public static BarinaTameTrigger BARINA_TRIGGER = new BarinaTameTrigger(new ResourceLocation("barina_trigger"));

    public static void init(){
        CriteriaTriggers.register(BARINA_TRIGGER);
    }

}