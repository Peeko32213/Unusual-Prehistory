package com.peeko32213.unusualprehistory.common.data.entity.generic;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.peeko32213.unusualprehistory.common.data.codec.NullableFieldCodec;
import com.scouter.goalsmith.data.PredicateCodec;
import com.scouter.goalsmith.data.predicates.TruePredicate;
import net.minecraft.world.entity.Entity;


public class GenericEntityData {


    public static final Codec<GenericEntityData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            NullableFieldCodec.makeDefaultableField("passenger_riding_offset",Codec.DOUBLE, 1.0D).forGetter(GenericEntityData::getPassengerRidingOffset),
            NullableFieldCodec.makeDefaultableField("step_height",Codec.FLOAT, 1.0F).forGetter(GenericEntityData::getStepHeight),
            NullableFieldCodec.makeDefaultableField("can_be_collided_with",Codec.BOOL, true).forGetter(GenericEntityData::isCanCollideWith),
            NullableFieldCodec.makeDefaultableField("can_collide_with",Codec.BOOL, true).forGetter(GenericEntityData::isCanBeCollidedWith),
            NullableFieldCodec.makeDefaultableField("spawns_naturally", Codec.BOOL, false).forGetter(GenericEntityData::isSpawnsNaturally),
            NullableFieldCodec.makeDefaultableField("is_attackable", Codec.BOOL, true).forGetter(GenericEntityData::isAttackable),
            NullableFieldCodec.makeDefaultableField("turns_head", Codec.BOOL, true).forGetter(GenericEntityData::isTurnsHead),
            NullableFieldCodec.makeDefaultableField("is_pushable", PredicateCodec.DIRECT_CODEC, new TruePredicate<>()).forGetter(GenericEntityData::getIsPushable),
            NullableFieldCodec.makeDefaultableField("entity_damage_type_definitions", EntityDamageTypeData.CODEC, EntityDamageTypeData.getDefaultInstance()).forGetter(GenericEntityData::getEntityDamageTypeData),
            NullableFieldCodec.makeDefaultableField("screen_shake_definitions", ScreenShakeEntityData.CODEC,ScreenShakeEntityData.getDefaultInstance()).forGetter(GenericEntityData::getScreenShakeEntityData),
            NullableFieldCodec.makeDefaultableField("entity_dimensions", EntityDimensionData.CODEC,EntityDimensionData.getDefaultInstance()).forGetter(GenericEntityData::getEntityDimensionData),
            EntitySoundData.CODEC.fieldOf("entity_sound_definitions").forGetter(GenericEntityData::getEntitySoundData),
            NullableFieldCodec.makeDefaultableField("entity_tag_definitions", EntityTagData.CODEC,EntityTagData.getDefaultInstance()).forGetter(GenericEntityData::getEntityTagData),
            NullableFieldCodec.makeDefaultableField("entity_terrain_break_definitions", EntityTerrainBreakData.CODEC,EntityTerrainBreakData.getDefaultInstance()).forGetter(GenericEntityData::getEntityTerrainBreakData)
    ).apply(instance, (d,f,aBoolean, aBoolean2, aBoolean3, aBoolean4, aBoolean5, predicateCodec, entityDamageTypeData1, screenShakeEntityData1, entityDimensionData1, entitySoundData1, etag, tbreak) -> new GenericEntityData(d,f,aBoolean, aBoolean2, aBoolean3, aBoolean4, aBoolean5, (PredicateCodec<Entity>) predicateCodec, entityDamageTypeData1, screenShakeEntityData1, entityDimensionData1, entitySoundData1, etag,tbreak)));

    private final double passengerRidingOffset;

    private final float stepHeight;

    private final boolean canBeCollidedWith;
    private final boolean canCollideWith;
    private final boolean spawnsNaturally;
    private final boolean isAttackable;
    private final boolean turnsHead;
    private final PredicateCodec<Entity> isPushable;


    private final EntityDamageTypeData entityDamageTypeData;
    private final ScreenShakeEntityData screenShakeEntityData;
    private final EntityDimensionData entityDimensionData;
    private final EntitySoundData entitySoundData;
    private final EntityTagData entityTagData;
    private final EntityTerrainBreakData entityTerrainBreakData;

    public static GenericEntityData getDefaulInstance() {
        return new GenericEntityData(1.0D,1.0F,true, true ,false, true, true,new TruePredicate<>(), EntityDamageTypeData.getDefaultInstance(), ScreenShakeEntityData.getDefaultInstance(), EntityDimensionData.getDefaultInstance(), EntitySoundData.getDefaultInstance(),  EntityTagData.getDefaultInstance(), EntityTerrainBreakData.getDefaultInstance());
    }

    public GenericEntityData(double passengerRidingOffset,float stepHeight,boolean canCollideWith, boolean canBeCollidedWith, boolean spawnsNaturally, boolean isAttackable, boolean turnsHead, PredicateCodec<Entity> isPushable) {
        this(passengerRidingOffset,stepHeight,canCollideWith, canBeCollidedWith ,spawnsNaturally, isAttackable,turnsHead,isPushable, EntityDamageTypeData.getDefaultInstance(), ScreenShakeEntityData.getDefaultInstance(), EntityDimensionData.getDefaultInstance(), EntitySoundData.getDefaultInstance(),  EntityTagData.getDefaultInstance(), EntityTerrainBreakData.getDefaultInstance());
    }

    public GenericEntityData(double passengerRidingOffset,float stepHeight,boolean canCollideWith, boolean canBeCollidedWith, boolean spawnsNaturally, boolean isAttackable, boolean turnsHead, PredicateCodec<Entity> isPushable, EntityTagData entityTagData) {
        this(passengerRidingOffset,stepHeight,canCollideWith, canBeCollidedWith ,spawnsNaturally, isAttackable,turnsHead,isPushable, EntityDamageTypeData.getDefaultInstance(), ScreenShakeEntityData.getDefaultInstance(), EntityDimensionData.getDefaultInstance(), EntitySoundData.getDefaultInstance(),  entityTagData, EntityTerrainBreakData.getDefaultInstance());
    }

    public GenericEntityData(double passengerRidingOffset,float stepHeight,boolean canCollideWith, boolean canBeCollidedWith, boolean spawnsNaturally, boolean isAttackable, boolean turnsHead, PredicateCodec<Entity> isPushable, EntitySoundData entitySoundData) {
        this(passengerRidingOffset,stepHeight,canCollideWith, canBeCollidedWith ,spawnsNaturally, isAttackable,turnsHead,isPushable, EntityDamageTypeData.getDefaultInstance(), ScreenShakeEntityData.getDefaultInstance(), EntityDimensionData.getDefaultInstance(), entitySoundData,  EntityTagData.getDefaultInstance(), EntityTerrainBreakData.getDefaultInstance());
    }
    public GenericEntityData(double passengerRidingOffset,float stepHeight,boolean canCollideWith, boolean canBeCollidedWith, boolean spawnsNaturally, boolean isAttackable, boolean turnsHead, PredicateCodec<Entity> isPushable, EntityDamageTypeData entityDamageTypeData, EntitySoundData entitySoundData, ScreenShakeEntityData screenShakeEntityData) {
        this(passengerRidingOffset,stepHeight,canCollideWith, canBeCollidedWith ,spawnsNaturally, isAttackable,turnsHead,isPushable, entityDamageTypeData, screenShakeEntityData, EntityDimensionData.getDefaultInstance(), entitySoundData,  EntityTagData.getDefaultInstance(), EntityTerrainBreakData.getDefaultInstance());
    }
    public GenericEntityData(double passengerRidingOffset,float stepHeight,boolean canCollideWith, boolean canBeCollidedWith, boolean spawnsNaturally, boolean isAttackable, boolean turnsHead, PredicateCodec<Entity> isPushable, EntityDamageTypeData entityDamageTypeData, EntitySoundData entitySoundData) {
        this(passengerRidingOffset,stepHeight,canCollideWith, canBeCollidedWith ,spawnsNaturally, isAttackable,turnsHead,isPushable, entityDamageTypeData, ScreenShakeEntityData.getDefaultInstance(), EntityDimensionData.getDefaultInstance(), entitySoundData,  EntityTagData.getDefaultInstance(), EntityTerrainBreakData.getDefaultInstance());
    }

    public GenericEntityData(double passengerRidingOffset,float stepHeight,boolean canCollideWith, boolean canBeCollidedWith, boolean spawnsNaturally, boolean isAttackable, boolean turnsHead, PredicateCodec<Entity> isPushable, EntityDimensionData entityDimensionData) {
        this(passengerRidingOffset,stepHeight,canCollideWith, canBeCollidedWith ,spawnsNaturally, isAttackable,turnsHead,isPushable, EntityDamageTypeData.getDefaultInstance(), ScreenShakeEntityData.getDefaultInstance(), entityDimensionData, EntitySoundData.getDefaultInstance(),  EntityTagData.getDefaultInstance(), EntityTerrainBreakData.getDefaultInstance());
    }

    public GenericEntityData(double passengerRidingOffset,float stepHeight,boolean canCollideWith, boolean canBeCollidedWith, boolean spawnsNaturally, boolean isAttackable, boolean turnsHead, PredicateCodec<Entity> isPushable, ScreenShakeEntityData screenShakeEntityData) {
        this(passengerRidingOffset,stepHeight,canCollideWith, canBeCollidedWith ,spawnsNaturally, isAttackable,turnsHead,isPushable, EntityDamageTypeData.getDefaultInstance(), screenShakeEntityData, EntityDimensionData.getDefaultInstance(), EntitySoundData.getDefaultInstance(),  EntityTagData.getDefaultInstance(), EntityTerrainBreakData.getDefaultInstance());
    }

    public GenericEntityData(double passengerRidingOffset,float stepHeight,boolean canCollideWith, boolean canBeCollidedWith, boolean spawnsNaturally, boolean isAttackable, boolean turnsHead, PredicateCodec<Entity> isPushable, EntityDamageTypeData entityDamageTypeData, EntityTagData entityTagData) {
        this(passengerRidingOffset, stepHeight, canCollideWith, canBeCollidedWith ,spawnsNaturally, isAttackable,turnsHead,isPushable, entityDamageTypeData, ScreenShakeEntityData.getDefaultInstance(), EntityDimensionData.getDefaultInstance(), EntitySoundData.getDefaultInstance(), entityTagData, EntityTerrainBreakData.getDefaultInstance());
    }

    public GenericEntityData(double passengerRidingOffset,float stepHeight,boolean canCollideWith, boolean canBeCollidedWith, boolean spawnsNaturally, boolean isAttackable, boolean turnsHead, PredicateCodec<Entity> isPushable, EntityDamageTypeData entityDamageTypeData) {
        this(passengerRidingOffset, stepHeight, canCollideWith, canBeCollidedWith ,spawnsNaturally, isAttackable,turnsHead,isPushable, entityDamageTypeData, ScreenShakeEntityData.getDefaultInstance(), EntityDimensionData.getDefaultInstance(), EntitySoundData.getDefaultInstance(), EntityTagData.getDefaultInstance(), EntityTerrainBreakData.getDefaultInstance());
    }


    public GenericEntityData(double passengerRidingOffset, float stepHeight, boolean canCollideWith, boolean canBeCollidedWith, boolean spawnsNaturally, boolean isAttackable, boolean turnsHead, PredicateCodec<Entity> isPushable, EntityDamageTypeData entityDamageTypeData, ScreenShakeEntityData screenShakeEntityData, EntityDimensionData entityDimensionData, EntitySoundData entitySoundData, EntityTagData entityTagData, EntityTerrainBreakData entityTerrainBreakData) {
        this.passengerRidingOffset = passengerRidingOffset;
        this.stepHeight = stepHeight;
        this.canCollideWith = canCollideWith;
        this.canBeCollidedWith = canBeCollidedWith;
        this.spawnsNaturally = spawnsNaturally;
        this.isAttackable = isAttackable;
        this.turnsHead = turnsHead;
        this.isPushable = isPushable;
        this.entityDamageTypeData = entityDamageTypeData;
        this.screenShakeEntityData = screenShakeEntityData;
        this.entityDimensionData = entityDimensionData;
        this.entitySoundData = entitySoundData;
        this.entityTagData = entityTagData;
        this.entityTerrainBreakData = entityTerrainBreakData;
    }

    public double getPassengerRidingOffset() {
        return passengerRidingOffset;
    }

    public float getStepHeight() {
        return stepHeight;
    }

    public boolean isCanCollideWith() {
        return canCollideWith;
    }

    public boolean isCanBeCollidedWith() {
        return canBeCollidedWith;
    }

    public boolean isSpawnsNaturally() {
        return spawnsNaturally;
    }

    public boolean isAttackable() {
        return isAttackable;
    }

    public boolean isTurnsHead() {
        return turnsHead;
    }

    public EntityDamageTypeData getEntityDamageTypeData() {
        return entityDamageTypeData;
    }

    public ScreenShakeEntityData getScreenShakeEntityData() {
        return screenShakeEntityData;
    }

    public EntityDimensionData getEntityDimensionData() {
        return entityDimensionData;
    }

    public PredicateCodec<Entity> getIsPushable() {
        return isPushable;
    }

    public EntitySoundData getEntitySoundData() {
        return entitySoundData;
    }

    public EntityTagData getEntityTagData() {
        return entityTagData;
    }

    public EntityTerrainBreakData getEntityTerrainBreakData() {
        return entityTerrainBreakData;
    }
}
