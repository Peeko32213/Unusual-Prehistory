package com.peeko32213.unusualprehistory.common.entity.custom.base;

import com.google.common.collect.ImmutableList;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.gameevent.GameEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import javax.annotation.Nullable;

public class SkeletonEntity extends LivingEntity implements GeoEntity, GeoAnimatable {

    private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);
    public static final Logger LOGGER = LogManager.getLogger();

    private static final EntityDataAccessor<Boolean> NATURAL = SynchedEntityData.defineId(SkeletonEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> WAXED = SynchedEntityData.defineId(SkeletonEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> LOCKED = SynchedEntityData.defineId(SkeletonEntity.class, EntityDataSerializers.BOOLEAN);

    public long lastHit;

    protected SkeletonEntity(EntityType<? extends LivingEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public void refreshDimensions() {
        double d0 = this.getX();
        double d1 = this.getY();
        double d2 = this.getZ();
        super.refreshDimensions();
        this.setPos(d0, d1, d2);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 4.0D);
    }

    public boolean canBreatheUnderwater() {
        return true;
    }

    public static float yawToYRot(double yaw) {
        return (float) Mth.wrapDegrees(yaw - 90);
    }

    @Override
    public InteractionResult interact(Player pPlayer, InteractionHand pHand) {
        ItemStack itemStack = pPlayer.getItemInHand(pHand);
        if (!this.isLocked()) {
            if (!this.isNatural()) {
                if (!this.isWaxed()) {
                    if (itemStack.isEmpty() && pHand == InteractionHand.MAIN_HAND) {
                        if (!pPlayer.isShiftKeyDown()) {
                            double d0 = pPlayer.getX() - this.getX();
                            double d2 = pPlayer.getZ() - this.getZ();
                            setYRot(yawToYRot(Mth.atan2(d2, d0) * Mth.RAD_TO_DEG));
                        }
                        return InteractionResult.SUCCESS;
                    }
                }
                if (itemStack.getItem() == Items.HONEYCOMB && !this.isWaxed()) {
                    this.setWaxed(true);
                    this.spawnWaxParticles(ParticleTypes.WAX_ON);
                    this.level().playSound(pPlayer, this.getX(), this.getY(), this.getZ(), SoundEvents.HONEYCOMB_WAX_ON, SoundSource.NEUTRAL, 1.0F, 1.0F);
                    itemStack.shrink(1);
                    return InteractionResult.SUCCESS;
                }
                if (itemStack.getItem() instanceof AxeItem && this.isWaxed()) {
                    this.setWaxed(false);
                    this.spawnWaxParticles(ParticleTypes.WAX_OFF);
                    this.level().playSound(pPlayer, this.getX(), this.getY(), this.getZ(), SoundEvents.AXE_WAX_OFF, SoundSource.NEUTRAL, 1.0F, 1.0F);
                    return InteractionResult.SUCCESS;
                }
            }
            if (itemStack.getItem() == Items.DEBUG_STICK && pHand == InteractionHand.MAIN_HAND) {
                this.setNatural(!this.isNatural());
                pPlayer.displayClientMessage(Component.translatable("entity.unusualprehistory.skeleton.natural_" + this.isNatural(), this.getName()), true);
                return InteractionResult.SUCCESS;
            }
        }
        return super.interact(pPlayer, pHand);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(NATURAL, false);
        this.entityData.define(WAXED, false);
        this.entityData.define(LOCKED, false);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setNatural(compound.getBoolean("Natural"));
        this.setWaxed(compound.getBoolean("Waxed"));
        this.setLocked(compound.getBoolean("Locked"));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("Natural", this.isNatural());
        compound.putBoolean("Waxed", this.isWaxed());
        compound.putBoolean("Locked", this.isLocked());
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    public boolean hurt(DamageSource pSource, float pAmount) {
        if (!this.level().isClientSide && !this.isRemoved()) {
            if (pSource.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
                this.kill();
                return false;
            }
            else if (!this.isInvulnerableTo(pSource) && !this.isLocked()) {
                if (pSource.is(DamageTypeTags.IS_EXPLOSION)) {
                    this.broken(pSource);
                    this.kill();
                    return false;
                }
                else {
                    boolean flag = pSource.getDirectEntity() instanceof AbstractArrow;
                    boolean flag1 = flag && ((AbstractArrow)pSource.getDirectEntity()).getPierceLevel() > 0;
                    boolean flag2 = "player".equals(pSource.getMsgId());
                    if (!flag2 && !flag) {
                        return false;
                    }
                    else {
                        Entity entity = pSource.getEntity();
                        if (entity instanceof Player) {
                            Player player = (Player)entity;
                            if (!player.getAbilities().mayBuild) {
                                return false;
                            }
                        }

                        if (pSource.isCreativePlayer()) {
                            this.playBrokenSound();
                            this.showBreakingParticles();
                            this.kill();
                            return flag1;
                        }
                        else {
                            long i = this.level().getGameTime();
                            if (i - this.lastHit > 5L && !flag) {
                                this.level().broadcastEntityEvent(this, (byte)32);
                                this.gameEvent(GameEvent.ENTITY_DAMAGE, pSource.getEntity());
                                this.lastHit = i;
                            }
                            else {
                                this.broken(pSource);
                                this.showBreakingParticles();
                                this.kill();
                            }
                            return true;
                        }
                    }
                }
            }
            else {
                return false;
            }
        }
        else {
            return false;
        }
    }

    public void handleEntityEvent(byte pId) {
        if (pId == 32) {
            if (this.level().isClientSide) {
                this.level().playLocalSound(this.getX(), this.getY(), this.getZ(), SoundEvents.SKELETON_HURT, this.getSoundSource(), 0.3F, 1.0F, false);
                this.lastHit = this.level().getGameTime();
            }
        } else {
            super.handleEntityEvent(pId);
        }
    }

    public void broken(DamageSource pDamageSource) {
        this.playBrokenSound();
        this.dropAllDeathLoot(pDamageSource);
    }

    private void showBreakingParticles() {
        if (this.level() instanceof ServerLevel) {
            ((ServerLevel)this.level()).sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, Blocks.BONE_BLOCK.defaultBlockState()), this.getX(), this.getY(0.6666666666666666), this.getZ(), 10, (double)(this.getBbWidth() / 4.0F), (double)(this.getBbHeight() / 4.0F), (double)(this.getBbWidth() / 4.0F), 0.05);
        }
    }

    private void spawnWaxParticles(ParticleOptions particle) {
        if (this.level().isClientSide) {
            for (int i = 0; i < 7; ++i) {
                double d0 = Mth.nextDouble(this.getRandom(), -1.0D, 1.0D);
                double d1 = Mth.nextDouble(this.getRandom(), -1.0D, 1.0D);
                double d2 = Mth.nextDouble(this.getRandom(), -1.0D, 1.0D);
                this.level().addParticle(particle, this.getRandomX(1.0D), this.getRandomY(), this.getRandomZ(1.0D), d0, d1, d2);
            }
        }
    }

    private void playBrokenSound() {
        this.level().playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.SKELETON_HURT, this.getSoundSource(), 1.0F, 1.0F);
    }

    public LivingEntity.Fallsounds getFallSounds() {
        return new LivingEntity.Fallsounds(SoundEvents.SKELETON_STEP, SoundEvents.SKELETON_STEP);
    }

    @Nullable
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return SoundEvents.SKELETON_HURT;
    }

    @Nullable
    protected SoundEvent getDeathSound() {
        return SoundEvents.SKELETON_HURT;
    }

    public void thunderHit(ServerLevel pLevel, LightningBolt pLightning) {
    }

    public boolean isAffectedByPotions() {
        return false;
    }

    public boolean attackable() {
        return false;
    }

    @Override
    public boolean isPickable() {
        return true;
    }

    public boolean skipAttackInteraction(Entity pEntity) {
        return pEntity instanceof Player && !this.level().mayInteract((Player)pEntity, this.blockPosition());
    }

    public void kill() {
        this.remove(RemovalReason.KILLED);
        this.gameEvent(GameEvent.ENTITY_DIE);
    }

    // Natural
    public void setNatural(boolean natural) {
        this.entityData.set(NATURAL, natural);
    }
    public boolean isNatural() {
        return this.entityData.get(NATURAL);
    }

    // Waxed
    public void setWaxed(boolean waxed) {
        this.entityData.set(WAXED, waxed);
    }
    public boolean isWaxed() {
        return this.entityData.get(WAXED);
    }

    // Locked
    public void setLocked(boolean locked) {
        this.entityData.set(LOCKED, locked);
    }
    public boolean isLocked() {
        return this.entityData.get(LOCKED);
    }

    @Override
    public Iterable<ItemStack> getArmorSlots() {
        return ImmutableList.of();
    }

    @Override
    public ItemStack getItemBySlot(EquipmentSlot p_21127_) {
        return ItemStack.EMPTY;
    }

    @Override
    public void setItemSlot(EquipmentSlot equipmentSlot, ItemStack itemStack) {}

    @Override
    public HumanoidArm getMainArm() {
        return HumanoidArm.RIGHT;
    }

    @Override
    public void registerControllers(final AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "Normal", 5, this::predicate));
    }

    private <E extends SkeletonEntity> PlayState predicate(AnimationState<E> event) {
        return PlayState.CONTINUE;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return geoCache;
    }

    @Override
    public double getTick(Object o) {
        return tickCount;
    }
}
