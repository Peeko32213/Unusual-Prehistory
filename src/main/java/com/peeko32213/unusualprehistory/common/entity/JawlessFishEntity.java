package com.peeko32213.unusualprehistory.common.entity;

import com.peeko32213.unusualprehistory.common.entity.msc.util.dino.BoidFishEntity;
import com.peeko32213.unusualprehistory.common.entity.msc.util.interfaces.IBookEntity;
import com.peeko32213.unusualprehistory.core.registry.UPItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import javax.annotation.Nonnull;

import static com.peeko32213.unusualprehistory.UnusualPrehistory.prefix;

//TODO LIST
// - Basically Done, nothing else really needed other than chances in the DNA loot pool
public class JawlessFishEntity extends BoidFishEntity implements Bucketable, GeoAnimatable, IBookEntity {

    private static final ResourceLocation TEXTURE_CEPHALAPIS = prefix("textures/entity/cephalaspis.png");
    private static final ResourceLocation TEXTURE_DORYASPIS = prefix("textures/entity/doryaspis.png");
    private static final ResourceLocation TEXTURE_FURCACAUDA = prefix("textures/entity/furcacauda.png");
    private static final ResourceLocation TEXTURE_SACAMAMBASPIS = prefix("textures/entity/sacabambaspis.png");

    private static final ResourceLocation MODEL_CEPHALAPIS = prefix("geo/jawless_fish/cephalaspis.geo.json");
    private static final ResourceLocation MODEL_DORYASPIS = prefix("geo/jawless_fish/doryaspis.geo.json");
    private static final ResourceLocation MODEL_FURCACAUDA = prefix("geo/jawless_fish/furcacauda.geo.json");
    private static final ResourceLocation MODEL_SACAMAMBASPIS = prefix("geo/jawless_fish/sacabambaspis.geo.json");

    private static final EntityDataAccessor<Boolean> FROM_BUCKET = SynchedEntityData.defineId(JawlessFishEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> FROM_BOOK = SynchedEntityData.defineId(JawlessFishEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(JawlessFishEntity.class, EntityDataSerializers.INT);
    private static final RawAnimation JAWLESS_FISH_SWIM = RawAnimation.begin().thenLoop("animation.jawless_fish.swim");
    private static final RawAnimation JAWLESS_FISH_FLOP = RawAnimation.begin().thenLoop("animation.jawless_fish.flop");
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);


    public JawlessFishEntity(EntityType<? extends BoidFishEntity> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new SmoothSwimmingMoveControl(this, 85, 10, 0.02f, 0.1f, true);
        this.lookControl = new SmoothSwimmingLookControl(this, 10);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 2.0);
    }

    @Override
    public int getMaxSchoolSize() {
        return 15;
    }

    protected InteractionResult mobInteract(Player p_27477_, InteractionHand p_27478_) {
        return Bucketable.bucketMobPickup(p_27477_, p_27478_, this).orElse(super.mobInteract(p_27477_, p_27478_));
    }

    @Override
    public ItemStack getBucketItemStack() {
        ItemStack stack = new ItemStack(UPItems.JAWLESS_FISH_BUCKET.get());
        if (this.hasCustomName()) {
            stack.setHoverName(this.getCustomName());
        }
        return stack;
    }

    @Override
    public SoundEvent getPickupSound() {
        return SoundEvents.BUCKET_EMPTY_FISH;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.COD_AMBIENT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.COD_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.COD_HURT;
    }

    @Override
    protected SoundEvent getFlopSound() {
        return SoundEvents.COD_FLOP;
    }

    @Override
    public void setFromBook(boolean fromBook) {
        this.entityData.set(FROM_BOOK, fromBook);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(VARIANT, 0);
        this.entityData.define(FROM_BUCKET, false);
        this.entityData.define(FROM_BOOK, false);
    }

    @Override
    public void saveToBucketTag(@Nonnull ItemStack bucket) {
        if (this.hasCustomName()) {
            bucket.setHoverName(this.getCustomName());
        }
        Bucketable.saveDefaultDataToBucketTag(this, bucket);
        CompoundTag compoundnbt = bucket.getOrCreateTag();
        compoundnbt.putInt("BucketVariantTag", this.getVariant());
    }

    @Override
    public void loadFromBucketTag(@Nonnull CompoundTag compound) {
        Bucketable.loadDefaultDataFromBucketTag(this, compound);
        if (compound.contains("BucketVariantTag", 3)) {
            this.setVariant(compound.getInt("BucketVariantTag"));
        }
    }


    public int getVariant() {
        return this.entityData.get(VARIANT);
    }

    public void setVariant(int variant) {
        this.entityData.set(VARIANT, Integer.valueOf(variant));
    }

    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("Variant", this.getVariant());
        compound.putBoolean("FromBucket", this.fromBucket());
    }

    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setVariant(compound.getInt("Variant"));
        this.setFromBucket(compound.getBoolean("FromBucket"));
    }

    @Override
    public boolean fromBucket() {
        return this.entityData.get(FROM_BUCKET);
    }

    @Override
    public void setFromBucket(boolean p_203706_1_) {
        this.entityData.set(FROM_BUCKET, p_203706_1_);
    }

    public boolean isFromBook() {
        return this.entityData.get(FROM_BOOK).booleanValue();
    }

    public void setIsFromBook(boolean fromBook) {
        this.entityData.set(FROM_BOOK, fromBook);
    }

    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficultyIn, MobSpawnType reason, @Nullable SpawnGroupData spawnDataIn, @Nullable CompoundTag dataTag) {
        float variantChange = this.getRandom().nextFloat();
        if(variantChange <= 0.25F){
            this.setVariant(3);
        }else if(variantChange <= 0.50F){
            this.setVariant(2);
        }else if(variantChange <= 0.75F){
            this.setVariant(1);
        }else{
            this.setVariant(0);
        }
        return super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    public ResourceLocation getVariantTexture() {
        if(getVariant() == 1){
            return TEXTURE_DORYASPIS;
        }
        if(getVariant() == 2)
        {
            return TEXTURE_CEPHALAPIS;
        }
        if(getVariant() == 3)
        {
            return TEXTURE_FURCACAUDA;
        }

        return TEXTURE_SACAMAMBASPIS;
    }

    public ResourceLocation getVariantModel() {
        if(getVariant() == 1){
            return MODEL_DORYASPIS;
        }
        if(getVariant() == 2)
        {
            return MODEL_CEPHALAPIS;
        }
        if(getVariant() == 3)
        {
            return MODEL_FURCACAUDA;
        }

        return MODEL_SACAMAMBASPIS;
    }


    protected <E extends JawlessFishEntity> PlayState Controller(final software.bernie.geckolib.core.animation.AnimationState<E> event) {
        if(!this.isFromBook()) {
            if (!(event.getLimbSwingAmount() > -0.06F && event.getLimbSwingAmount() < 0.06F) && this.isInWater()) {
                event.setAndContinue(JAWLESS_FISH_SWIM);
                return PlayState.CONTINUE;
            }
            if (!this.isInWater()) {
                event.setAndContinue(JAWLESS_FISH_FLOP);
                return PlayState.CONTINUE;
            }
        }
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(final AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "Normal", 5, this::Controller));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    public double getTick(Object o) {
        return tickCount;
    }
}
