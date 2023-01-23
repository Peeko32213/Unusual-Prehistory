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

    public static final SoundEvent REX_BITE = createSoundEvent("rex_bite");
    public static final SoundEvent REX_DEATH = createSoundEvent("rex_death");
    public static final SoundEvent REX_HURT = createSoundEvent("rex_hurt");
    public static final SoundEvent REX_IDLE = createSoundEvent("rex_idle");
    public static final SoundEvent REX_STEP = createSoundEvent("rex_step");
    public static final SoundEvent REX_STOMP_ATTACK = createSoundEvent("rex_stomp_attack");
    public static final SoundEvent REX_TAIL_SWIPE = createSoundEvent("rex_tail_swipe");
    public static final SoundEvent TRIKE_DEATH = createSoundEvent("trike_death");
    public static final SoundEvent TRIKE_HURT = createSoundEvent("trike_hurt");
    public static final SoundEvent TRIKE_IDLE = createSoundEvent("trike_idle");
    public static final SoundEvent BRACHI_DEATH = createSoundEvent("brachi_death");
    public static final SoundEvent BRACHI_HURT = createSoundEvent("brachi_hurt");
    public static final SoundEvent BRACHI_IDLE = createSoundEvent("brachi_idle");
    public static final SoundEvent BRACHI_STEP = createSoundEvent("brachi_step");
    public static final SoundEvent BRACHI_TOSS = createSoundEvent("brachi_toss");
    public static final SoundEvent ENCRUSTED_DEATH = createSoundEvent("encrusted_death");
    public static final SoundEvent ENCRUSTED_HURT = createSoundEvent("encrusted_hurt");
    public static final SoundEvent ENCRUSTED_IDLE = createSoundEvent("encrusted_idle");
    public static final SoundEvent ENCRUSTED_MELEE = createSoundEvent("encrusted_step");
    public static final SoundEvent ENCRUSTED_SPIT = createSoundEvent("encrusted_spit");
    public static final SoundEvent RAPTOR_ATTACK = createSoundEvent("raptor_attack");
    public static final SoundEvent RAPTOR_DEATH = createSoundEvent("raptor_death");
    public static final SoundEvent RAPTOR_HURT = createSoundEvent("raptor_hurt");
    public static final SoundEvent RAPTOR_IDLE = createSoundEvent("raptor_idle");
    public static final SoundEvent RAPTOR_SEARCH = createSoundEvent("raptor_search");
    public static final SoundEvent PACHY_DEATH = createSoundEvent("pachy_death");
    public static final SoundEvent PACHY_HEADBUTT = createSoundEvent("pachy_headbutt");
    public static final SoundEvent PACHY_HURT = createSoundEvent("pachy_hurt");
    public static final SoundEvent PACHY_IDLE = createSoundEvent("pachy_idle");
    public static final SoundEvent PACHY_KICK = createSoundEvent("pachy_kick");

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