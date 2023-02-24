package com.peeko32213.unusualprehistory.core.registry;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.effect.EffectBrachisProtection;
import com.peeko32213.unusualprehistory.common.effect.EffectPachysMight;
import com.peeko32213.unusualprehistory.common.effect.EffectScreenShake;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.lang.reflect.Field;

@Mod.EventBusSubscriber(modid = UnusualPrehistory.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class UPEffects {
    public static final DeferredRegister<MobEffect> EFFECT_DEF_REG = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, UnusualPrehistory.MODID);

    public static final RegistryObject<MobEffect> BRACHI_PROTECTION = EFFECT_DEF_REG.register("brachi_protection", ()-> new EffectBrachisProtection());
    public static final RegistryObject<MobEffect> PACHYS_MIGHT = EFFECT_DEF_REG.register("pachys_might", ()-> new EffectPachysMight());
    public static final RegistryObject<MobEffect> SCREEN_SHAKE = EFFECT_DEF_REG.register("screen_shake", ()-> new EffectScreenShake());

}
