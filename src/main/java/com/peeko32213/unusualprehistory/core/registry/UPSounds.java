package com.peeko32213.unusualprehistory.core.registry;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.lang.reflect.Field;

@Mod.EventBusSubscriber(modid = UnusualPrehistory.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class UPSounds {

    public static final SoundEvent MAJUNGA_IDLE = createSoundEvent("majunga_idle");

    public static final SoundEvent MAJUNGA_HURT = createSoundEvent("majunga_hurt");

    public static final SoundEvent MAJUNGA_ATTACK = createSoundEvent("majunga_attack");

    public static final SoundEvent MAJUNGA_DEATH = createSoundEvent("majunga_death");

    public static final SoundEvent MAJUNGA_STEP = createSoundEvent("majunga_step");

    public static final SoundEvent ANURO_IDLE = createSoundEvent("anuro_idle");

    public static final SoundEvent ANURO_HURT = createSoundEvent("anuro_hurt");

    public static final SoundEvent ANURO_DEATH = createSoundEvent("anuro_death");

    public static final SoundEvent COTY_IDLE = createSoundEvent("coty_idle");

    public static final SoundEvent COTY_HURT = createSoundEvent("coty_hurt");

    public static final SoundEvent COTY_DEATH = createSoundEvent("coty_death");

    public static final SoundEvent DUNK_ATTACK = createSoundEvent("dunk_attack");

    public static final SoundEvent BEELZE_IDLE = createSoundEvent("beelze_idle");

    public static final SoundEvent BEELZE_HURT = createSoundEvent("beelze_hurt");

    public static final SoundEvent BEELZE_ATTACK = createSoundEvent("beelze_attack");

    public static final SoundEvent BEELZE_DEATH = createSoundEvent("beelze_death");

    private static SoundEvent createSoundEvent(final String soundName) {
        final ResourceLocation soundID = new ResourceLocation(UnusualPrehistory.MODID, soundName);
        return new SoundEvent(soundID).setRegistryName(soundID);
    }

    @SubscribeEvent
    public static void registerSoundEvents(final RegistryEvent.Register<SoundEvent> event) {
        try {
            for (Field f : UPSounds.class.getDeclaredFields()) {
                Object obj = f.get(null);
                if (obj instanceof SoundEvent) {
                    event.getRegistry().register((SoundEvent) obj);
                } else if (obj instanceof SoundEvent[]) {
                    for (SoundEvent soundEvent : (SoundEvent[]) obj) {
                        event.getRegistry().register(soundEvent);
                    }
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

}