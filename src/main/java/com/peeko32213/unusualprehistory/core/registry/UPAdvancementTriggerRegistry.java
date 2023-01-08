package com.peeko32213.unusualprehistory.core.registry;

import com.peeko32213.unusualprehistory.core.registry.util.UPAdvancementTrigger;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.resources.ResourceLocation;

public class UPAdvancementTriggerRegistry {

    public static UPAdvancementTrigger REX_PASSIFY = new UPAdvancementTrigger(new ResourceLocation("unusualprehistory:rex_passify"));

    public static void init(){
        CriteriaTriggers.register(REX_PASSIFY);
    }

}