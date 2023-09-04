package com.peeko32213.unusualprehistory.core.registry;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = UnusualPrehistory.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class UPSounds {
    public static final DeferredRegister<SoundEvent> DEF_REG = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, UnusualPrehistory.MODID);

    public static final RegistryObject<SoundEvent> MAJUNGA_IDLE = createSoundEvent("majunga_idle");

    public static final RegistryObject<SoundEvent> MAJUNGA_HURT = createSoundEvent("majunga_hurt");

    public static final RegistryObject<SoundEvent> MAJUNGA_ATTACK = createSoundEvent("majunga_attack");

    public static final RegistryObject<SoundEvent> MAJUNGA_DEATH = createSoundEvent("majunga_death");

    public static final RegistryObject<SoundEvent> MAJUNGA_STEP = createSoundEvent("majunga_step");

    public static final RegistryObject<SoundEvent> ANURO_IDLE = createSoundEvent("anuro_idle");

    public static final RegistryObject<SoundEvent> ANURO_HURT = createSoundEvent("anuro_hurt");

    public static final RegistryObject<SoundEvent> ANURO_DEATH = createSoundEvent("anuro_death");

    public static final RegistryObject<SoundEvent> COTY_IDLE = createSoundEvent("coty_idle");

    public static final RegistryObject<SoundEvent> COTY_HURT = createSoundEvent("coty_hurt");

    public static final RegistryObject<SoundEvent> COTY_DEATH = createSoundEvent("coty_death");

    public static final RegistryObject<SoundEvent> DUNK_ATTACK = createSoundEvent("dunk_attack");
    public static final RegistryObject<SoundEvent> DUNK_DEATH = createSoundEvent("dunk_death");
    public static final RegistryObject<SoundEvent> DUNK_HURT = createSoundEvent("dunk_hurt");

    public static final RegistryObject<SoundEvent> BEELZE_IDLE = createSoundEvent("beelze_idle");

    public static final RegistryObject<SoundEvent> BEELZE_HURT = createSoundEvent("beelze_hurt");

    public static final RegistryObject<SoundEvent> BEELZE_ATTACK = createSoundEvent("beelze_attack");

    public static final RegistryObject<SoundEvent> BEELZE_DEATH = createSoundEvent("beelze_death");

    public static final RegistryObject<SoundEvent> REX_BITE = createSoundEvent("rex_bite");
    public static final RegistryObject<SoundEvent> REX_DEATH = createSoundEvent("rex_death");
    public static final RegistryObject<SoundEvent> REX_HURT = createSoundEvent("rex_hurt");
    public static final RegistryObject<SoundEvent> REX_IDLE = createSoundEvent("rex_idle");
    public static final RegistryObject<SoundEvent> REX_STEP = createSoundEvent("rex_step");
    public static final RegistryObject<SoundEvent> REX_STOMP_ATTACK = createSoundEvent("rex_stomp_attack");
    public static final RegistryObject<SoundEvent> REX_TAIL_SWIPE = createSoundEvent("rex_tail_swipe");
    public static final RegistryObject<SoundEvent> TRIKE_DEATH = createSoundEvent("trike_death");
    public static final RegistryObject<SoundEvent> TRIKE_HURT = createSoundEvent("trike_hurt");
    public static final RegistryObject<SoundEvent> TRIKE_IDLE = createSoundEvent("trike_idle");
    public static final RegistryObject<SoundEvent> BRACHI_DEATH = createSoundEvent("brachi_death");
    public static final RegistryObject<SoundEvent> BRACHI_HURT = createSoundEvent("brachi_hurt");
    public static final RegistryObject<SoundEvent> BRACHI_IDLE = createSoundEvent("brachi_idle");
    public static final RegistryObject<SoundEvent> BRACHI_STEP = createSoundEvent("brachi_step");
    public static final RegistryObject<SoundEvent> BRACHI_TOSS = createSoundEvent("brachi_toss");
    public static final RegistryObject<SoundEvent> ENCRUSTED_DEATH = createSoundEvent("encrusted_death");
    public static final RegistryObject<SoundEvent> ENCRUSTED_HURT = createSoundEvent("encrusted_hurt");
    public static final RegistryObject<SoundEvent> ENCRUSTED_IDLE = createSoundEvent("encrusted_idle");
    public static final RegistryObject<SoundEvent> ENCRUSTED_MELEE = createSoundEvent("encrusted_step");
    public static final RegistryObject<SoundEvent> ENCRUSTED_SPIT = createSoundEvent("encrusted_spit");
    public static final RegistryObject<SoundEvent> RAPTOR_ATTACK = createSoundEvent("raptor_attack");
    public static final RegistryObject<SoundEvent> RAPTOR_DEATH = createSoundEvent("raptor_death");
    public static final RegistryObject<SoundEvent> RAPTOR_HURT = createSoundEvent("raptor_hurt");
    public static final RegistryObject<SoundEvent> RAPTOR_IDLE = createSoundEvent("raptor_idle");
    public static final RegistryObject<SoundEvent> RAPTOR_SEARCH = createSoundEvent("raptor_search");
    public static final RegistryObject<SoundEvent> PACHY_DEATH = createSoundEvent("pachy_death");
    public static final RegistryObject<SoundEvent> PACHY_HEADBUTT = createSoundEvent("pachy_headbutt");
    public static final RegistryObject<SoundEvent>PACHY_HURT = createSoundEvent("pachy_hurt");
    public static final RegistryObject<SoundEvent> PACHY_IDLE = createSoundEvent("pachy_idle");
    public static final RegistryObject<SoundEvent> PACHY_KICK = createSoundEvent("pachy_kick");
    public static final RegistryObject<SoundEvent> ERYON_DEATH = createSoundEvent("eryon_death");
    public static final RegistryObject<SoundEvent> ERYON_HURT = createSoundEvent("eryon_hurt");
    public static final RegistryObject<SoundEvent> ERYON_IDLE = createSoundEvent("eryon_idle");
    public static final RegistryObject<SoundEvent> AUSTRO_BITE = createSoundEvent("austro_bite");
    public static final RegistryObject<SoundEvent> AUSTRO_DEATH = createSoundEvent("austro_death");
    public static final RegistryObject<SoundEvent> AUSTRO_HURT = createSoundEvent("austro_hurt");
    public static final RegistryObject<SoundEvent> AUSTRO_IDLE = createSoundEvent("austro_idle");
    public static final RegistryObject<SoundEvent> AUSTRO_PREEN = createSoundEvent("austro_preen");
    public static final RegistryObject<SoundEvent> HWACHA_DEATH = createSoundEvent("hwacha_death");
    public static final RegistryObject<SoundEvent> HWACHA_HURT = createSoundEvent("hwacha_hurt");
    public static final RegistryObject<SoundEvent> HWACHA_IDLE = createSoundEvent("hwacha_idle");
    public static final RegistryObject<SoundEvent> HWACHA_SHOOT = createSoundEvent("hwacha_shoot");
    public static final RegistryObject<SoundEvent> KENTRO_DEATH = createSoundEvent("kentro_death");
    public static final RegistryObject<SoundEvent> KENTRO_HURT = createSoundEvent("kentro_hurt");
    public static final RegistryObject<SoundEvent> KENTRO_IDLE = createSoundEvent("kentro_idle");
    public static final RegistryObject<SoundEvent> TAIL_SWIPE = createSoundEvent("tail_swipe");
    public static final RegistryObject<SoundEvent> ULUGH_BITE = createSoundEvent("ulugh_bite");
    public static final RegistryObject<SoundEvent> ULUGH_DEATH = createSoundEvent("ulugh_death");
    public static final RegistryObject<SoundEvent> ULUGH_HURT = createSoundEvent("ulugh_hurt");
    public static final RegistryObject<SoundEvent> ULUGH_IDLE = createSoundEvent("ulugh_idle");
    public static final RegistryObject<SoundEvent> ULUGH_STEP = createSoundEvent("ulugh_step");
    public static final RegistryObject<SoundEvent> ANTARCTO_DEATH = createSoundEvent("antarcto_death");
    public static final RegistryObject<SoundEvent> ANTARCTO_HURT = createSoundEvent("antarcto_hurt");
    public static final RegistryObject<SoundEvent> ANTARCTO_IDLE = createSoundEvent("antarcto_idle");
    public static final RegistryObject<SoundEvent> REX_BOOMBOX = createSoundEvent("rex_boombox");
    public static final RegistryObject<SoundEvent> GIGANTO_DEATH = createSoundEvent("giganto_death");
    public static final RegistryObject<SoundEvent> GIGANTO_HURT = createSoundEvent("giganto_hurt");
    public static final RegistryObject<SoundEvent> GIGANTO_IDLE = createSoundEvent("giganto_idle");
    public static final RegistryObject<SoundEvent> GIGANTO_TRADE = createSoundEvent("giganto_trade");
    public static final RegistryObject<SoundEvent> MAMMOTH_DEATH = createSoundEvent("mammoth_death");
    public static final RegistryObject<SoundEvent> MAMMOTH_HURT = createSoundEvent("mammoth_hurt");
    public static final RegistryObject<SoundEvent> MAMMOTH_IDLE = createSoundEvent("mammoth_idle");
    public static final RegistryObject<SoundEvent> PARACER_DEATH = createSoundEvent("paracer_death");
    public static final RegistryObject<SoundEvent> PARACER_HURT = createSoundEvent("paracer_hurt");
    public static final RegistryObject<SoundEvent> PARACER_IDLE = createSoundEvent("paracer_idle");
    public static final RegistryObject<SoundEvent> PARACER_STOMP = createSoundEvent("paracer_stomp");
    public static final RegistryObject<SoundEvent> TALAPANAS_DEATH = createSoundEvent("talapanas_death");
    public static final RegistryObject<SoundEvent> TALAPANAS_HURT = createSoundEvent("talapanas_hurt");
    public static final RegistryObject<SoundEvent> TALAPANAS_IDLE = createSoundEvent("talapanas_idle");
    public static final RegistryObject<SoundEvent> TALAPANAS_PANIC = createSoundEvent("talapanas_panic");
    public static final RegistryObject<SoundEvent> CROCARINA = createSoundEvent("crocarina");
    public static final RegistryObject<SoundEvent> BARINA_DEATH = createSoundEvent("barina_death");
    public static final RegistryObject<SoundEvent> BARINA_HURT = createSoundEvent("barina_hurt");
    public static final RegistryObject<SoundEvent> BARINA_IDLE = createSoundEvent("barina_idle");

    public static final RegistryObject<SoundEvent> MEGATHER_DEATH = createSoundEvent("megather_death");
    public static final RegistryObject<SoundEvent> MEGATHER_HURT = createSoundEvent("megather_hurt");
    public static final RegistryObject<SoundEvent> MEGATHER_IDLE = createSoundEvent("megather_idle");
    public static final RegistryObject<SoundEvent> SMILODON_DEATH = createSoundEvent("smilodon_death");
    public static final RegistryObject<SoundEvent> SMILODON_HURT = createSoundEvent("smilodon_hurt");
    public static final RegistryObject<SoundEvent> SMILODON_IDLE = createSoundEvent("smilodon_idle");



    private static RegistryObject<SoundEvent> createSoundEvent(final String soundName) {
        return DEF_REG.register(soundName, () -> new SoundEvent(new ResourceLocation(UnusualPrehistory.MODID, soundName)));
    }

}