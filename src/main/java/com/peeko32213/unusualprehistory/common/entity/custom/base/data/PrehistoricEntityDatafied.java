package com.peeko32213.unusualprehistory.common.entity.custom.base.data;

import com.peeko32213.unusualprehistory.common.data.PrehistoricEntityJsonDataManager;
import com.peeko32213.unusualprehistory.common.data.entity.*;
import com.peeko32213.unusualprehistory.common.data.entity.generic.*;
import com.peeko32213.unusualprehistory.common.entity.animation.state.IStateAction;
import com.peeko32213.unusualprehistory.core.mixin.AttributeAccessor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public abstract class PrehistoricEntityDatafied extends TamableAnimal implements IStateAction, GeoEntity, GeoAnimatable {
    public static final Logger LOGGER = LogManager.getLogger();
    private static final EntityDataAccessor<String> ANIMATION_LOCATION = SynchedEntityData.defineId(PrehistoricEntityDatafied.class, EntityDataSerializers.STRING);
    private static final EntityDataAccessor<String> MODEL_LOCATION = SynchedEntityData.defineId(PrehistoricEntityDatafied.class, EntityDataSerializers.STRING);
    private static final EntityDataAccessor<String> TEXTURE_LOCATION = SynchedEntityData.defineId(PrehistoricEntityDatafied.class, EntityDataSerializers.STRING);
    private static final EntityDataAccessor<String> RENDER_TYPE_LOCATION = SynchedEntityData.defineId(PrehistoricEntityDatafied.class, EntityDataSerializers.STRING);
    private static final EntityDataAccessor<Float> WIDTH_SCALE = SynchedEntityData.defineId(PrehistoricEntityDatafied.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> HEIGHT_SCALE = SynchedEntityData.defineId(PrehistoricEntityDatafied.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Integer> TOGGLE_FLAGS = SynchedEntityData.defineId(PrehistoricEntityDatafied.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> PERFORMING_ACTION = SynchedEntityData.defineId(PrehistoricEntityDatafied.class, EntityDataSerializers.BOOLEAN);

    //To be saved on the entity
    private static final EntityDataAccessor<Integer> VARIANT_ID = SynchedEntityData.defineId(PrehistoricEntityDatafied.class, EntityDataSerializers.INT);
    private static final String VARIANT_ID_KEY = "variant_id";

    private static final String DEFAULT_LOC = "unusualprehistory:default";

    private ResourceLocation cachedAnimationLocation;
    private ResourceLocation cachedModelLocation;
    private ResourceLocation cachedTextureLocation;
    private String cachedRenderTypeKey;
    private String cachedRenderType;


    private double passengerRidingOffset;
    private float stepHeight;
    private boolean attackable;
    private boolean pushable;
    private boolean canCollideWith;
    private boolean canBeCollidedWith;

    private SoundEvent hurtSound;
    private SoundEvent deathSound;
    private SoundEvent ambientSound;
    private float soundVolume;



    private ScreenShakeEntityData screenShakeEntityData;
    private EntityTerrainBreakData entityTerrainBreakData;

    private TagKey<Item> food;
    private TagKey<EntityType<?>> targets;

    private List<DamageType> invulnerabilityList = new ArrayList<>();
    protected PrehistoricEntityDatafied(EntityType<? extends TamableAnimal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    //just some basic attributes that will get overriden later
    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 10.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.0D)
                .add(Attributes.ARMOR, 0.0D)
                .add(Attributes.ARMOR_TOUGHNESS, 0.0D)
                .add(Attributes.ATTACK_DAMAGE, 0.0D)
                .add(Attributes.ATTACK_KNOCKBACK, 0.0D)
                .add(Attributes.ATTACK_SPEED, 0.0D)
                .add(Attributes.FLYING_SPEED, 0.0D)
                .add(Attributes.FOLLOW_RANGE, 0.0D)
                .add(Attributes.JUMP_STRENGTH, 0.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.0D)
                .add(Attributes.LUCK, 0.0D)
                .add(Attributes.SPAWN_REINFORCEMENTS_CHANCE, 0.0D);
    }


    protected abstract ResourceLocation getEntityDataResourceLocation();

    private PrehistoricEntityData getPrehistoricEntityData() {
        return getPrehistoricEntityData(getEntityDataResourceLocation());
    }

    public static PrehistoricEntityData getPrehistoricEntityData(ResourceLocation resourceLocation) {
        return PrehistoricEntityJsonDataManager.getPrehistoricEntityData(resourceLocation);
    }


    //todo however how should we handle reloaded stuff?

    @Override
    public void onAddedToWorld() {
        super.onAddedToWorld();

        int variantId = getVariantId();
        PrehistoricEntityData prehistoricEntityData = getPrehistoricEntityData();
        VariantData variantData = prehistoricEntityData.getVariantById(variantId);
        GenericEntityData genericEntityData = variantData.getGenericEntityData();
        EntityResourceLocationData resourceLocationData = variantData.getEntityResourceLocationData();
        EntityGoals entityGoals = variantData.getEntityGoals();
        entityGoals.addGoals(this);
        setRenderTypeKey(resourceLocationData.getRenderType());
        setTextureLocation(resourceLocationData.getTextureLocation());
        setModelLocation(resourceLocationData.getModelLocation());
        setAnimationLocation(resourceLocationData.getAnimationLocation());


        EntityDamageTypeData damageTypeData = genericEntityData.getEntityDamageTypeData();
        EntityDimensionData dimensionData = genericEntityData.getEntityDimensionData();
        EntitySoundData soundData = genericEntityData.getEntitySoundData();
        ScreenShakeEntityData screenShakeEntityData = genericEntityData.getScreenShakeEntityData();
        EntityTagData entityTagData = genericEntityData.getEntityTagData();
        setEntityTerrainBreakData(genericEntityData.getEntityTerrainBreakData());
        setScreenShakeEntityData(screenShakeEntityData);

        setIsInvulnerableTo(damageTypeData.getIsInvulnerableTo(this.level()));


        setHeightScale(dimensionData.getHeight());
        setWidthScale(dimensionData.getWidth());

        setHurtSound(soundData.getHurtSound());
        setDeathSound(soundData.getDeathSound());
        setAmbientSound(soundData.getAmbientSound());
        setSoundVolume(soundData.getSoundVolume());

        setPushable(genericEntityData.getIsPushable().getPredicate().test(this));
        setAttackable(genericEntityData.isAttackable());
        setTurnsHead(genericEntityData.isTurnsHead());
        setCanBeCollidedWith(genericEntityData.isCanBeCollidedWith());
        setCanCollideWith(genericEntityData.isCanCollideWith());
        setPassengerRidingOffset(genericEntityData.getPassengerRidingOffset());
        setStepHeight(genericEntityData.getStepHeight());

        setFood(entityTagData.getFood());
        setTargets(entityTagData.getTargets());
    }

    @Override
    public InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
        Map<Attribute, AttributeInstance> attributes =  ((AttributeAccessor) this.getAttributes()).unusualprehistory$getAttributes();
        LOGGER.info("Level " + level());
        for(Attribute attribute : attributes.keySet()) {
           AttributeInstance instance = attributes.get(attribute);

           LOGGER.info("Attribute: " + attribute.getDescriptionId() + " value " + instance.getValue());
        }

        return super.mobInteract(pPlayer, pHand);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(HEIGHT_SCALE, 1F);
        this.entityData.define(WIDTH_SCALE, 1F);
        this.entityData.define(VARIANT_ID, 0);
        this.entityData.define(TOGGLE_FLAGS, 0);
        this.entityData.define(TEXTURE_LOCATION, DEFAULT_LOC);
        this.entityData.define(MODEL_LOCATION, DEFAULT_LOC);
        this.entityData.define(ANIMATION_LOCATION, DEFAULT_LOC);
        this.entityData.define(RENDER_TYPE_LOCATION, "entity_cutout");
        this.entityData.define(PERFORMING_ACTION, false);

    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt(VARIANT_ID_KEY, getVariantId());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        setVariantId(compound.getInt(VARIANT_ID_KEY));
    }

    public void onSyncedDataUpdated(EntityDataAccessor<?> pKey) {
        if (HEIGHT_SCALE.equals(pKey) || WIDTH_SCALE.equals(pKey)) {
            this.refreshDimensions();
        }
        super.onSyncedDataUpdated(pKey);
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {

        PrehistoricEntityData entityData1 = getPrehistoricEntityData();
        Optional<WeightedVariantData> weightedVariantData = entityData1.getVariantDataWeightedRandomList().getRandom(pLevel.getRandom());
        VariantData variantData = weightedVariantData
                .map(WeightedVariantData::getVariantData)
                .orElse(VariantData.getDefaultInstance());


        this.setVariantId(variantData.getVariantId());
        variantData.getAttributesModifiers().performAdditions(this);
        this.heal(this.getMaxHealth());
        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
    }

    //this cant be dont per variant since its still an EntityType and not an entity so we dont have a variant defined yet.
    public static boolean checkSurfaceDinoSpawnRules(EntityType<? extends PrehistoricEntityDatafied> entityType, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource randomSource) {
        ResourceLocation location = BuiltInRegistries.ENTITY_TYPE.getKey(entityType);
        PrehistoricEntityData entityData = getPrehistoricEntityData(location);
        if(entityData == null) return false;
        return entityData.getSpawnData().getSpawnsNaturally() && entityData.getSpawnData().getSpawnPredicate().checkSpawn(level, spawnType, pos, randomSource);
    }



    //this is for saved entity data but booleans if we want to synch them to client we do this in order to prevent getting a lot of SynchedEntityData
    //////////////////FLAGS
    private static final int ROTATE_HEAD_FLAG = 1;
    private static final int GLOWING_LAYER_FLAG = 2;


    public void setTurnsHead(boolean rotate) {
        setEntityFlags(ROTATE_HEAD_FLAG, rotate);
    }


    public boolean getTurnsHead() {
        return (getDataFlag() & ROTATE_HEAD_FLAG)  != 0;
    }



    protected void setEntityFlags(int key, boolean value) {
        int flags = this.entityData.get(TOGGLE_FLAGS);
        if (value) {
            flags |= key;
        } else {
            flags &= ~key;
        }
        this.entityData.set(TOGGLE_FLAGS, flags);
    }

    private int getDataFlag() {
        return this.entityData.get(TOGGLE_FLAGS);
    }



    public float getWidthScale() {
        return this.entityData.get(WIDTH_SCALE);
    }

    public void setWidthScale(float scale) {
        this.entityData.set(WIDTH_SCALE, scale);
    }

    public float getHeightScale() {
        return this.entityData.get(HEIGHT_SCALE);
    }

    public void setHeightScale(float scale) {
        this.entityData.set(HEIGHT_SCALE, scale);
    }

    public int getVariantId() {
        return this.entityData.get(VARIANT_ID);
    }

    public void setVariantId(int id) {
        this.entityData.set(VARIANT_ID, id);
    }


    @Override
    public boolean canCollideWith(Entity entity) {
        return canCollideWith ? super.canCollideWith(entity) : false;
    }

    @Override
    public boolean canBeCollidedWith() {
        return canBeCollidedWith;
    }

    @Override
    public boolean isPushable() {
        return pushable;
    }

    @Override
    public boolean attackable() {
        return attackable;
    }

    @Override
    public boolean isInvulnerableTo(DamageSource pSource) {
        return invulnerabilityList.contains(pSource.type()) || super.isInvulnerableTo(pSource);
    }

    @Override
    public boolean getAction() {
        return getPerformingAction();
    }

    @Override
    public void setAction(boolean action) {
        setPerformingAction(action);
    }

    @Override
    public void aiStep() {
        super.aiStep();

        getEntityTerrainBreakData().breakBlocks(this);

    }

    @Override
    public void travel(Vec3 pTravelVector) {
        if (this.getPerformingAction()) {
            if (this.getNavigation().getPath() != null) {
                this.getNavigation().stop();
            }
            pTravelVector = Vec3.ZERO;
        }
        super.travel(pTravelVector);
    }


    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return hurtSound;
    }

    @Nullable
    @Override
    public SoundEvent getDeathSound() {
        return deathSound;
    }

    @Override
    protected float getSoundVolume() {
        if(this.isBaby()) {
            return soundVolume * 0.4F;
        }
        return soundVolume;
    }

    @Nullable
    @Override
    public SoundEvent getAmbientSound() {
        return ambientSound;
    }

    @Override
    public @NotNull EntityDimensions getDimensions(@NotNull Pose pPose) {
        return super.getDimensions(pPose).scale(getWidthScale(), getHeightScale());
    }

    @Override
    public float getStepHeight() {
        return stepHeight;
    }

    @Override
    public double getPassengersRidingOffset() {
        return passengerRidingOffset;
    }


    @Override
    public boolean isFood(ItemStack pStack) {
        return pStack.is(getFood());
    }

    public TagKey<EntityType<?>> getTargets() {
        return targets;
    }

    public TagKey<Item> getFood() {
        return food;
    }

    public ScreenShakeEntityData getScreenShakeEntityData() {
        return screenShakeEntityData;
    }

    public EntityTerrainBreakData getEntityTerrainBreakData() {
        return entityTerrainBreakData;
    }


    public void setCanCollideWith(boolean collideWith) {
        this.canCollideWith = collideWith;
    }

    public void setCanBeCollidedWith(boolean collideWith) {
        this.canBeCollidedWith = collideWith;
    }

    public void setPushable(boolean pushable) {
        this.pushable = pushable;
    }

    public void setAttackable(boolean attackable) {
        this.attackable = attackable;
    }

    public void setAmbientSound(SoundEvent ambientSound) {
        this.ambientSound = ambientSound;
    }

    public void setHurtSound(SoundEvent hurtSound) {
        this.hurtSound = hurtSound;
    }

    public void setDeathSound(SoundEvent deathSound) {
        this.deathSound = deathSound;
    }

    public void setSoundVolume(float soundVolume) {
        this.soundVolume = soundVolume;
    }


    public void setScreenShakeEntityData(ScreenShakeEntityData screenShakeEntityData) {
        this.screenShakeEntityData = screenShakeEntityData;
    }

    public void setEntityTerrainBreakData(EntityTerrainBreakData entityTerrainBreakData) {
        this.entityTerrainBreakData = entityTerrainBreakData;
    }

    public boolean getPerformingAction() {
        return this.entityData.get(PERFORMING_ACTION);
    }

    public void setPerformingAction(boolean action) {
        this.entityData.set(PERFORMING_ACTION, action);
    }

    public void setStepHeight(float stepHeight) {
        this.stepHeight = stepHeight;
    }

    public void setPassengerRidingOffset(double passengerRidingOffset) {
        this.passengerRidingOffset = passengerRidingOffset;
    }

    public void setFood(TagKey<Item> food) {
        this.food = food;
    }

    public void setTargets(TagKey<EntityType<?>> targets) {
        this.targets = targets;
    }

    public void setIsInvulnerableTo(List<DamageType> damageTypes) {
        this.invulnerabilityList = damageTypes;
    }



    public void setModelLocation(ResourceLocation loc) {
        String locString = loc.toString();
        this.entityData.set(MODEL_LOCATION, locString);
        this.cachedModelLocation = getResourceLocationFromEntityData(MODEL_LOCATION);
    }

    public ResourceLocation getModelLocation() {
        if (cachedModelLocation == null) {
            cachedModelLocation = getResourceLocationFromEntityData(MODEL_LOCATION);
        }
        return cachedModelLocation;
    }


    public void setAnimationLocation(ResourceLocation loc) {
        String locString = loc.toString();
        this.entityData.set(ANIMATION_LOCATION, locString);
        this.cachedAnimationLocation = getResourceLocationFromEntityData(ANIMATION_LOCATION);
    }

    public ResourceLocation getAnimationLocation() {
        if (cachedAnimationLocation == null) {
            cachedAnimationLocation = getResourceLocationFromEntityData(ANIMATION_LOCATION);
        }
        return cachedAnimationLocation;
    }

    public void setTextureLocation(ResourceLocation loc) {
        String locString = loc.toString();
        this.entityData.set(ANIMATION_LOCATION, locString);
        this.cachedTextureLocation = getResourceLocationFromEntityData(ANIMATION_LOCATION);
    }

    public ResourceLocation getTextureLocation() {
        if (cachedTextureLocation == null) {
            cachedTextureLocation = getResourceLocationFromEntityData(TEXTURE_LOCATION);
        }
        return cachedTextureLocation;
    }

    public void setRenderTypeKey(String loc) {
        this.entityData.set(RENDER_TYPE_LOCATION, loc);
        this.cachedRenderTypeKey = getEntityData(RENDER_TYPE_LOCATION);
    }

    public String getRenderTypeKey() {
        if (cachedRenderTypeKey == null) {
            cachedRenderTypeKey = getEntityData(RENDER_TYPE_LOCATION);
        }
        return cachedRenderTypeKey;
    }


    private ResourceLocation getResourceLocationFromEntityData(EntityDataAccessor<String> entityDataAccessor) {
        return ResourceLocation.tryParse(this.entityData.get(entityDataAccessor));
    }

    private String getEntityData(EntityDataAccessor<String> entityDataAccessor) {
        return this.entityData.get(entityDataAccessor);
    }

    public String getRenderType() {
        if(cachedRenderType == null) {
            cachedRenderType = getRenderTypeKey();
        }
        return cachedRenderType;
    }


}
