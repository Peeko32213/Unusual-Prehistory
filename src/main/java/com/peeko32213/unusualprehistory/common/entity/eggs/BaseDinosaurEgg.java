package com.peeko32213.unusualprehistory.common.entity.eggs;

import com.peeko32213.unusualprehistory.common.entity.EntityVelociraptor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.checkerframework.checker.units.qual.C;
import org.joml.Vector3f;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.Collections;

public abstract class BaseDinosaurEgg extends LivingEntity implements GeoAnimatable, GeoEntity, IEggEntity {
    private static final EntityDataAccessor<Integer> SCALE = SynchedEntityData.defineId(BaseDinosaurEgg.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Vector3f> EGG_BASE_COLOR = SynchedEntityData.defineId(BaseDinosaurEgg.class, EntityDataSerializers.VECTOR3);
    private static final EntityDataAccessor<Vector3f> EGG_SPOT_COLOR = SynchedEntityData.defineId(BaseDinosaurEgg.class, EntityDataSerializers.VECTOR3);
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private boolean invisible;
    public long lastHit;
    private ItemStack stack;
    private EggSize eggSize;
    //private EntityType<?> dinosaur;
    //private int
    protected BaseDinosaurEgg(EntityType<? extends LivingEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.refreshDimensions();
    }

    public BaseDinosaurEgg(EntityType<? extends LivingEntity>pEntityType, Level pLevel, double pX, double pY, double pZ, ItemStack stack, EggSize size, float color1, float color2) {
        this(pEntityType, pLevel);
        this.setPos(pX, pY, pZ);
        this.stack = stack.copy();
        this.eggSize = size;
        this.refreshDimensions();
    }

    public void refreshDimensions() {
        double d0 = this.getX();
        double d1 = this.getY();
        double d2 = this.getZ();
        super.refreshDimensions();
        this.setPos(d0, d1, d2);
    }

    private boolean hasPhysics() {
        return !this.isNoGravity();
    }

    /**
     * Returns whether the entity is in a server world
     */
    public boolean isEffectiveAi() {
        return super.isEffectiveAi() && this.hasPhysics();
    }

    @Override
    public Iterable<ItemStack> getArmorSlots() {
        return Collections.emptyList();
    }

    @Override
    public ItemStack getItemBySlot(EquipmentSlot pSlot) {
        return ItemStack.EMPTY;
    }

    @Override
    public void setItemSlot(EquipmentSlot pSlot, ItemStack pStack) {
    }

    @Override
    public HumanoidArm getMainArm() {
        return HumanoidArm.RIGHT;
    }

    protected void doPush(Entity pEntity) {
    }

    public boolean hurt(DamageSource pSource, float pAmount) {
        if (!this.level().isClientSide && !this.isRemoved()) {
            if (pSource.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
                this.discard();
                return false;
            } else if (!this.isInvulnerableTo(pSource) && !this.invisible) {
                if (pSource.is(DamageTypeTags.IS_EXPLOSION)) {
                    this.discard();
                    return false;

                    //todo change to custom tag
                } else if (pSource.is(DamageTypeTags.IGNITES_ARMOR_STANDS)) {
                    if (this.isOnFire()) {
                        this.causeDamage(pSource, 0.15F);
                    } else {
                        this.setSecondsOnFire(5);
                    }

                    return false;

                    //todo change to custom tag
                } else if (pSource.is(DamageTypeTags.BURNS_ARMOR_STANDS) && this.getHealth() > 0.5F) {
                    this.causeDamage(pSource, 4.0F);
                    return false;
                } else {
                    boolean flag = pSource.getDirectEntity() instanceof AbstractArrow;
                    boolean flag1 = flag && ((AbstractArrow)pSource.getDirectEntity()).getPierceLevel() > 0;
                    boolean flag2 = "player".equals(pSource.getMsgId());
                    if (!flag2 && !flag) {
                        return false;
                    } else {
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
                            this.remove(RemovalReason.KILLED);
                            return flag1;
                        } else {
                            long i = this.level().getGameTime();
                            if (i - this.lastHit > 5L && !flag) {
                                this.level().broadcastEntityEvent(this, (byte)32);
                                this.gameEvent(GameEvent.ENTITY_DAMAGE, pSource.getEntity());
                                this.lastHit = i;
                            } else {
                                this.brokenByPlayer(pSource);
                                this.showBreakingParticles();
                                this.kill();
                            }

                            return true;
                        }
                    }
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    protected void updateInvisibilityStatus() {
        this.setInvisible(this.invisible);
    }

    public void setInvisible(boolean pInvisible) {
        this.invisible = pInvisible;
        super.setInvisible(pInvisible);
    }

    private void causeDamage(DamageSource pDamageSource, float pAmount) {
        float f = this.getHealth();
        f -= pAmount;
        if (f <= 0.5F) {
            this.kill();
        } else {
            this.setHealth(f);
            this.gameEvent(GameEvent.ENTITY_DAMAGE, pDamageSource.getEntity());
        }
    }

    private void playBrokenSound() {
        this.level().playSound((Player)null, this.getX(), this.getY(), this.getZ(), SoundEvents.TURTLE_EGG_BREAK, this.getSoundSource(), 1.0F, 1.0F);
    }

    private void showBreakingParticles() {
        if (this.level() instanceof ServerLevel) {
            //((ServerLevel)this.level()).sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, Blocks.OAK_PLANKS.defaultBlockState()), this.getX(), this.getY(0.6666666666666666D), this.getZ(), 10, (double)(this.getBbWidth() / 4.0F), (double)(this.getBbHeight() / 4.0F), (double)(this.getBbWidth() / 4.0F), 0.05D);
            ((ServerLevel)this.level()).sendParticles(ParticleTypes.EGG_CRACK, this.getX(), this.getY(0.6666666666666666D), this.getZ(), 10, (double)(this.getBbWidth() / 4.0F), (double)(this.getBbHeight() / 4.0F), (double)(this.getBbWidth() / 4.0F), 0.05D);
        }
    }

    private void brokenByPlayer(DamageSource pDamageSource) {
        //todo replace this with correct item method
        ItemStack itemstack = getDinosaurEgg();
        if (this.hasCustomName()) {
            itemstack.setHoverName(this.getCustomName());
        }

        Block.popResource(this.level(), this.blockPosition(), itemstack);
    }

    public void handleEntityEvent(byte pId) {
        if (pId == 32) {
            if (this.level().isClientSide) {
                this.level().playLocalSound(this.getX(), this.getY(), this.getZ(), SoundEvents.TURTLE_EGG_CRACK, this.getSoundSource(), 0.3F, 1.0F, false);
                this.lastHit = this.level().getGameTime();
            }
        } else {
            super.handleEntityEvent(pId);
        }

    }

    @Override
    public ItemStack getPickedResult(HitResult target) {
        //todo replace this with correct item method
        return getDinosaurEgg();
    }

    public void travel(Vec3 pTravelVector) {
        if (this.hasPhysics()) {
            super.travel(pTravelVector);
        }
    }

    public ItemStack getDinosaurEgg() {
        int time = getCurrentHatchTime();
        ItemStack eggItem = stack;
        CompoundTag tag = null;
        if(stack.getTag() != null && !stack.getTag().isEmpty()) {
            tag = stack.getTag();
        } else {
            tag = new CompoundTag();
        }

        tag.putInt("time", time);
        eggItem.setTag(tag);
        return eggItem;
        //EntityType<?> type = getDinosaur();
        //float color1 = getEggBaseColor();
        //float color2 = getEggSpotColor();

    }


    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("scale", this.getModelScale());
        //CompoundTag egg = (CompoundTag) EggSize.CODEC.encodeStart(NbtOps.INSTANCE, eggSize).result().orElse(new CompoundTag());
        compound.putString("eggSize", eggSize.name());
        compound.put("eggItem", this.stack.save(new CompoundTag()));
        compound.put("eggBaseColor", writeVector3f(getEggBaseColor()));
        compound.put("eggSpotColor", writeVector3f(getEggSpotColor()));
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setScale(Math.min(compound.getInt("scale"), 0));
        EggSize egg = EggSize.valueOf(compound.getString("eggSize"));
        this.eggSize = egg;
        if (compound.contains("eggItem", 10)) {
            this.stack = ItemStack.of(compound.getCompound("eggItem"));
        }
        setEggBaseColor(readVector3f(compound.getCompound("eggBaseColor")));
        setEggSpotColor(readVector3f(compound.getCompound("eggSpotColor")));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(SCALE, eggSize.getSizeNr());
        this.entityData.define(EGG_BASE_COLOR, new Vector3f(251,62,249));
        this.entityData.define(EGG_SPOT_COLOR, new Vector3f(0,0,0));
    }

    public void onSyncedDataUpdated(EntityDataAccessor<?> pKey) {
        if (SCALE.equals(pKey)) {
            this.refreshDimensions();
        }

        super.onSyncedDataUpdated(pKey);
    }

    public EntityDimensions getDimensions(Pose pPose) {
        return super.getDimensions(pPose).scale(getWidthScale(this.getModelScale()), getHeightScale(this.getModelScale()));
    }

    public float getWidthScale(int scale) {
        return switch (scale) {
            case 0, 1 -> 0.1875F;
            case 2 -> 0.375F;
            case 3 -> 0.625F;
            default -> 0.9F;
        };
    }

    public float getHeightScale(int scale) {
        return switch (scale) {
            case 0 -> 0.1875F;
            case 1 -> 0.3125F;
            case 2 -> 0.4375F;
            case 3 -> 0.625F;
            default -> 0.9F;
        };
    }

    public int getModelScale() {
        return this.entityData.get(SCALE);
    }

    public void setScale(int scale) {
        this.entityData.set(SCALE, scale);
    }

    public Vector3f getEggBaseColor() {
        return this.entityData.get(EGG_BASE_COLOR);
    }

    public void setEggBaseColor(Vector3f color) {
        this.entityData.set(EGG_BASE_COLOR, color);
    }

    public Vector3f getEggSpotColor() {
        return this.entityData.get(EGG_SPOT_COLOR);
    }

    public void setEggSpotColor(Vector3f color) {
        this.entityData.set(EGG_SPOT_COLOR, color);
    }

    public EggSize getEggSize() {
        return eggSize;
    }

    public static Vector3f readVector3f(CompoundTag pTag) {
        return new Vector3f(pTag.getFloat("X"), pTag.getFloat("Y"), pTag.getFloat("Z"));
    }

    public static CompoundTag writeVector3f(Vector3f pPos) {
        CompoundTag compoundtag = new CompoundTag();
        compoundtag.putFloat("X", pPos.x());
        compoundtag.putFloat("Y", pPos.y());
        compoundtag.putFloat("Z", pPos.z());
        return compoundtag;
    }

    public float[] getEggColorFromVector(Vector3f vector3f) {
        float[] colors = new float[3];
        float red = vector3f.x()/255;
        float green = vector3f.y()/255;
        float blue = vector3f.z()/255;

        colors[0] = red;
        colors[1] = green;
        colors[2] = blue;
        return colors;
    }
}
