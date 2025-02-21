package com.peeko32213.unusualprehistory.core.registry;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.effect.*;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = UnusualPrehistory.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class UPEffects {
    public static final DeferredRegister<MobEffect> EFFECT_DEF_REG = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, UnusualPrehistory.MODID);

    public static final RegistryObject<MobEffect> BRACHI_PROTECTION = EFFECT_DEF_REG.register("brachi_protection", BrachisProtectionEffect::new);
    public static final RegistryObject<MobEffect> PACHYS_MIGHT = EFFECT_DEF_REG.register("pachys_might", PachysMightEffect::new);
    public static final RegistryObject<MobEffect> SCREEN_SHAKE = EFFECT_DEF_REG.register("screen_shake", ScreenShakeEffect::new);
    public static final RegistryObject<MobEffect> PREVENT_CLICK = EFFECT_DEF_REG.register("prevent_click", PreventClickEffect::new);
    public static final RegistryObject<MobEffect> HEALTH_REDUCTION = EFFECT_DEF_REG.register("health_reduction",  () ->new HealthReductionEffect());
    public static final RegistryObject<MobEffect> PISSED_UPON = EFFECT_DEF_REG.register("pissed_upon", JarateEffect::new);
    public static final RegistryObject<MobEffect> RABIES = EFFECT_DEF_REG.register("rabies", RampageEffect::new);
    public static final RegistryObject<MobEffect> RABIES_VACCINE = EFFECT_DEF_REG.register("rabies_vaccine", RampageRemedyEffect::new);

}
