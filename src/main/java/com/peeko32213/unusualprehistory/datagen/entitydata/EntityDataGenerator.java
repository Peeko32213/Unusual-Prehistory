package com.peeko32213.unusualprehistory.datagen.entitydata;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.data.attack.NoneAttack;
import com.peeko32213.unusualprehistory.common.data.attack.StompAttack;
import com.peeko32213.unusualprehistory.common.data.codec.MobEffectInstanceCodec;
import com.peeko32213.unusualprehistory.common.data.entity.*;
import com.peeko32213.unusualprehistory.common.data.entity.attribute.AttributesModifier;
import com.peeko32213.unusualprehistory.common.data.entity.generic.*;
import com.peeko32213.unusualprehistory.common.data.entity.goal.*;
import com.peeko32213.unusualprehistory.core.registry.UPEffects;
import com.peeko32213.unusualprehistory.core.registry.UPEntities;
import com.peeko32213.unusualprehistory.core.registry.UPSounds;
import com.peeko32213.unusualprehistory.core.registry.UPTags;
import com.scouter.goalsmith.GoalSmith;
import com.scouter.goalsmith.data.goalcodec.*;
import com.scouter.goalsmith.data.goalcodec.targetgoalcodec.HurtByTargetGoalCodec;
import com.scouter.goalsmith.data.goalcodec.targetgoalcodec.NearestAttackableTargetGoalCodec;
import com.scouter.goalsmith.data.predicates.NoCreativeOrSpectatorPredicate;
import com.scouter.goalsmith.data.predicates.TruePredicate;
import com.scouter.goalsmith.util.GSTags;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attributes;

import java.util.List;
import java.util.function.Consumer;

import static com.peeko32213.unusualprehistory.UnusualPrehistory.prefix;
//import static com.peeko32213.unusualprehistory.common.data.entity.synced.SerializableSynchedDataRegistry.REX_VARIANT;

public class EntityDataGenerator extends EntityDataProvider {
    public EntityDataGenerator(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildEntityData(Consumer<EntityDataConsumer> pWriter) {
//        PrehistoricEntityData trexData = new PrehistoricEntityData(
//                EntitySpawnData.getDefaultInstance(),
//                WeightedRandomList.create(
//                        new WeightedVariantData(
//                                65,
//                                new VariantData(
//                                        1,
//                                        new EntityResourceLocationData(
//                                                new ResourceLocation(UnusualPrehistory.MODID, "geo/tyrannosaurus/tyrannosaurus_rex.geo.json"),
//                                                new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/tyrannosaurus/tyrannosaurus_rex.png"),
//                                                new ResourceLocation(UnusualPrehistory.MODID, "animations/tyrannosaurus.animation.json"),
//                                                UPRenderTypes.getRenderType(ResourceLocation.tryParse("entity_cutout"))
//                                        ),
//                                        new AttributesModifier(List.of(
//                                                new AttributesModifier.AttributesMap(
//                                                        Attributes.MAX_HEALTH,300D
//                                                ),
//                                                new AttributesModifier.AttributesMap(
//                                                        Attributes.MOVEMENT_SPEED,0.2D
//                                                ),
//                                                new AttributesModifier.AttributesMap(
//                                                        Attributes.ATTACK_DAMAGE,16D
//                                                ),
//                                                new AttributesModifier.AttributesMap(
//                                                        Attributes.KNOCKBACK_RESISTANCE,1.5D
//                                                ),
//                                                new AttributesModifier.AttributesMap(
//                                                        Attributes.FOLLOW_RANGE,32D
//                                                )
//                                        )
//                                        ),
//                                        new EntityGoalsBuilder()
//                                                .addGoal(new RandomLookAroundGoalCodec(0))
//                                                .addGoal(new FloatGoalCodec(0))
//                                                .addGoal(new WaterAvoidingRandomStrollGoalCodec(3,1.0D,20))
//                                                .addGoal(new LookAtEntityGoalCodec(6, GSTags.PLAYER, 6,0.02F,false))
//                                                .addGoal(new AvoidEntityGoalCodec(4, getGoalSmithEntityTag("chicken"),new TruePredicate<>(),12F,2D,2D, new NoCreativeOrSpectatorPredicate()))
//                                                .addGoal(new SerializableRandomStateGoalCodec(2,
//                                                            WeightedRandomList.create(
//                                                                    //new WeightedSerializableStateHelper(
//                                                                    //        11,
//                                                                    //        SerializableStateHelper.Builder.state(SerializableSynchedDataRegistry.REX_IDLE_1_AC, "tyrannosaurus_shake")
//                                                                    //                .playTime(90)
//                                                                    //                .stopTime(200)
//                                                                    //                .entityAction(EntityAction.getDefaultInstance())
//                                                                    //                .build()
//                                                                    //)
//                                                            )
//                                                        ))
//
//                                                .addTargetGoal(new NearestAttackableTargetGoalCodec(2, UPTags.TYRANNOSAURUS_TARGETS, 10,false,false, new TruePredicate<>()))
//                                                //TODO: this tag should be the one it should ignore, aka itself so it should be a tag with itself in it so this has to be done!
//                                                .addTargetGoal(new HurtByTargetGoalCodec(9, UPTags.TYRANNOSAURUS_TARGETS))
//                                                .build(),
//                                        GenericEntityData.getDefaulInstance()
//                                )
//                        ),
//                        new WeightedVariantData(
//                                1,
//                                new VariantData(
//                                        2,
//                                        new EntityResourceLocationData(
//                                                new ResourceLocation(UnusualPrehistory.MODID, "geo/tyrannosaurus/tyrannosaurus_mcraeensis.geo.json"),
//                                                new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/tyrannosaurus/tyrannosaurus_mcraeensis.png"),
//                                                new ResourceLocation(UnusualPrehistory.MODID, "animations/tyrannosaurus.animation.json"),
//                                                UPRenderTypes.getRenderType(ResourceLocation.tryParse("entity_cutout_no_cull"))
//                                        ),
//                                        new AttributesModifier(List.of(
//                                                new AttributesModifier.AttributesMap(
//                                                        Attributes.MAX_HEALTH,300D
//                                                ),
//                                                new AttributesModifier.AttributesMap(
//                                                        Attributes.MOVEMENT_SPEED,0.5D
//                                                ),
//                                                new AttributesModifier.AttributesMap(
//                                                        Attributes.ATTACK_DAMAGE,16D
//                                                ),
//                                                new AttributesModifier.AttributesMap(
//                                                        Attributes.KNOCKBACK_RESISTANCE,1.5D
//                                                ),
//                                                new AttributesModifier.AttributesMap(
//                                                        Attributes.FOLLOW_RANGE,32D
//                                                )
//                                        )
//                                        ),                              new EntityGoalsBuilder()
//                                        .addGoal(new RandomLookAroundGoalCodec(0))
//                                        .addGoal(new FloatGoalCodec(0))
//                                        .addGoal(new WaterAvoidingRandomStrollGoalCodec(3,1.0D,20))
//                                        .addGoal(new LookAtEntityGoalCodec(6, GSTags.PLAYER, 6,0.02F,false))
//                                        .addGoal(new AvoidEntityGoalCodec(4, getGoalSmithEntityTag("chicken"),new TruePredicate<>(),12F,2D,2D, new NoCreativeOrSpectatorPredicate()))
//                                        .addTargetGoal(new NearestAttackableTargetGoalCodec(2, UPTags.TYRANNOSAURUS_TARGETS, 10,false,false, new TruePredicate<>()))
//                                        //TODO: this tag should be the one it should ignore, aka itself so it should be a tag with itself in it so this has to be done!
//                                        .addTargetGoal(new HurtByTargetGoalCodec(9, UPTags.TYRANNOSAURUS_TARGETS))
//                                        .build(),
//                                        GenericEntityData.getDefaulInstance()
//                                )
//                        )
//                )

        PrehistoricEntityData telecrexData = new PrehistoricEntityData(
                EntitySpawnData.getDefaultInstance(),
                WeightedRandomList.create(
                        new WeightedVariantData(
                                100,
                                new VariantData(
                                        1,
                                        new EntityResourceLocationData(
                                                new ResourceLocation(UnusualPrehistory.MODID, "geo/telecrex.geo.json"),
                                                new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/telecrex.png"),
                                                new ResourceLocation(UnusualPrehistory.MODID, "animations/telecrex.animation.json"),
                                                "entity_cutout_no_cull"
                                        ),
                                        new AttributesModifier(List.of(
                                                new AttributesModifier.AttributesMap(
                                                        Attributes.MAX_HEALTH, 3000D
                                                ),
                                                new AttributesModifier.AttributesMap(
                                                        Attributes.MOVEMENT_SPEED, 1.0D
                                                ),
                                                new AttributesModifier.AttributesMap(
                                                        Attributes.ATTACK_DAMAGE, 1600D
                                                ),
                                                new AttributesModifier.AttributesMap(
                                                        Attributes.KNOCKBACK_RESISTANCE, 100.0D
                                                ),
                                                new AttributesModifier.AttributesMap(
                                                        Attributes.FOLLOW_RANGE, 16D
                                                )
                                        )
                                        ),
                                        new EntityGoalsBuilder()
                                                .addGoals(
                                                        new RandomLookAroundGoalCodec(0),
                                                        new FloatGoalCodec(0),
                                                        new WaterAvoidingRandomStrollGoalCodec(3, 1.0D, 20),
                                                        new LookAtEntityGoalCodec(6, GSTags.PLAYER, 6, 0.02F, false),
                                                        new PanicGoalCodec(1,1.25D),
                                                        new TemptGoalCodec(4, 1.2D, ItemTags.LEAVES, false),
                                                        new MeleeAttackGoalCodec(1,3.25D, true)
//                                                        new SerializableRandomMeleeAttackGoalCodec(1,
//                                                                WeightedRandomList.create(
//                                                                        new WeightedSerializableMeleeAttackHelper(
//                                                                                10,
//                                                                                SerializableRandomMeleeAttackHelper.Builder
//                                                                                        .state(REX_VARIANT, "rex")
//                                                                                        .meleeEntityAction(new MeleeEntityAction(
//                                                                                        8,1,
//                                                                                        new StompAttack(
//                                                                                                new SoundData(
//                                                                                                        UPSounds.TYRANNO_STOMP_ATTACK.get(),
//                                                                                                        SoundSource.AMBIENT,
//                                                                                                        1,
//                                                                                                        1
//
//                                                                                                ),
//                                                                                                new NoneAttack(),
//                                                                                                new CooldownWideRangeEffectData(
//                                                                                                        100,
//                                                                                                        new WideRangeEffectData(
//                                                                                                                new MobEffectInstanceCodec(UPEffects.SCREEN_SHAKE.get(),
//                                                                                                                        4,10,false,false,false),
//                                                                                                                0.2,UPTags.NONE_ENTITY_TAG
//                                                                                                        )
//                                                                                                )
//                                                                                        )
//                                                                                        )
//                                                                                        )
//                                                                                        .build()
//                                                                        )
//                                                                ),2,false,2)

                                                )
                                                //this one should probably be itself but its a tag of things it should be bothered by when attacked by it
                                                .addTargetGoals(new HurtByTargetGoalCodec(8, GSTags.PLAYER))

                                                .build(),
                                        new GenericEntityData(1.0D,1.0F,true,true,false,true, true, new TruePredicate<>(),
                                                new EntityDamageTypeData(
                                                        List.of(
                                                                DamageTypes.FALL,
                                                                DamageTypes.IN_WALL
                                                        )
                                                ),
                                                new EntitySoundData(
                                                        UPSounds.ANURO_IDLE.get(),
                                                        UPSounds.TALPANAS_HURT.get(),
                                                        UPSounds.TYRANNO_DEATH.get(),
                                                        1,
                                                        1
                                                ),
                                                new ScreenShakeEntityData(true,
                                                        new CooldownWideRangeEffectData(100,
                                                                new WideRangeEffectData(
                                                                        new MobEffectInstanceCodec(MobEffects.ABSORPTION,10,1000,false,false,false),
                                                                        10,
                                                                        GSTags.PLAYER
                                                                )))

                                        )
                                )
                        )
                )
        );

        pWriter.accept(new EntityDataConsumer(UPEntities.TELECREX.get(), telecrexData));
    }


    private static TagKey<EntityType<?>> getGoalSmithEntityTag(String name) {
        return TagKey.create(Registries.ENTITY_TYPE, GoalSmith.prefix(name));
    }

    private static TagKey<EntityType<?>> getUPEntityTag(String name) {
        return TagKey.create(Registries.ENTITY_TYPE, prefix(name));
    }
}
