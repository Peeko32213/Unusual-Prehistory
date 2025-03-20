package com.peeko32213.unusualprehistory.core.registry;

import com.mojang.serialization.Codec;
import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.data.attack.*;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class UPAttackRegistry {
    public static final DeferredRegister<Codec<? extends EntityAttack>> ENTITY_ATTACK = DeferredRegister.create(UPRegistry.Keys.ENTITY_ATTACKS_TYPE_SERIALIZER, UnusualPrehistory.MODID);
    public static final RegistryObject<Codec<? extends NoneAttack>> NONE_ATTACK = ENTITY_ATTACK.register("none_attack", () -> NoneAttack.CODEC);
    public static final RegistryObject<Codec<? extends GenericAttack>> GENERIC_ATTACK = ENTITY_ATTACK.register("generic_attack", () -> GenericAttack.CODEC);
    public static final RegistryObject<Codec<? extends GenericSwingAttack>> GENERIC_SWING_ATTACK = ENTITY_ATTACK.register("generic_swing_attack", () -> GenericSwingAttack.CODEC);
    public static final RegistryObject<Codec<? extends LargeHitBoxAttack>> LARGE_HITBOX_ATTACK = ENTITY_ATTACK.register("large_hitbox_attack", () -> LargeHitBoxAttack.CODEC);
    public static final RegistryObject<Codec<? extends LargeHitBoxAttackWithTargetCheck>> LARGE_HITBOX_ATTACK_WITH_TARGET_CHECK = ENTITY_ATTACK.register("large_hitbox_attack_with_target_check", () -> LargeHitBoxAttackWithTargetCheck.CODEC);

    public static final RegistryObject<Codec<? extends PivotedPolyHitCheckAttack>> PIVOTED_POLY_HIT_CHECK_ATTACK = ENTITY_ATTACK.register("pivoted_poly_hit_check_attack", () -> PivotedPolyHitCheckAttack.CODEC);
    public static final RegistryObject<Codec<? extends StompAttack>> STOMP_ATTACK = ENTITY_ATTACK.register("stomp_attack", () -> StompAttack.CODEC);


}
