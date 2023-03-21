package com.peeko32213.unusualprehistory.common.entity;

import com.peeko32213.unusualprehistory.common.entity.msc.projectile.EntityHwachaSpike;
import com.peeko32213.unusualprehistory.common.entity.msc.util.*;
import com.peeko32213.unusualprehistory.common.entity.msc.util.dino.EntityRangedBaseDinosaurAnimal;
import com.peeko32213.unusualprehistory.common.entity.msc.util.dino.EntityTameableRangedBaseDinosaurAnimal;
import com.peeko32213.unusualprehistory.core.registry.UPTags;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;

public class EntityHwachavenator extends EntityTameableRangedBaseDinosaurAnimal implements RangedAttackMob, CustomFollower {
    private static final EntityDataAccessor<Boolean> SHOOTING = SynchedEntityData.defineId(EntityHwachavenator.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> COMMAND = SynchedEntityData.defineId(EntityHwachavenator.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> SADDLED = SynchedEntityData.defineId(EntityHwachavenator.class, EntityDataSerializers.BOOLEAN);

    public static final Logger LOGGER = LogManager.getLogger();
    public float shootProgress;
    public float sitProgress;

    public EntityHwachavenator(EntityType<? extends TamableAnimal> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 30D)
                .add(Attributes.FOLLOW_RANGE, 50D)
                .add(Attributes.ARMOR, 5.0D)
                .add(Attributes.ATTACK_DAMAGE, 2.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.3D)
                .add(Attributes.MOVEMENT_SPEED, 0.17F);

    }

    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(3, new BabyPanicGoal(this, 2.0D));
        this.goalSelector.addGoal(3, new RangedAttackGoal(this, 1.25D, 40, 20.0F));
        this.goalSelector.addGoal(3, new CustomRandomStrollGoal(this, 30, 1.0D, 100, 34));
        this.goalSelector.addGoal(5, new FollowParentGoal(this, 1.1D));
        this.goalSelector.addGoal(1, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(1, new CustomRideGoal(this, 3D));
        this.goalSelector.addGoal(3, new TameableFollowOwner(this, 1.2D, 6.0F, 3.0F, false));
        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
        this.goalSelector.addGoal(1, new SitWhenOrderedToGoal(this));
        this.targetSelector.addGoal(8, (new HurtByTargetGoal(this){

            public boolean canUse() {
                return !isTame();
            }

        }));
    }


    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(SHOOTING, false);
        this.entityData.define(COMMAND, 0);
        this.entityData.define(SADDLED, Boolean.valueOf(false));
    }


    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putBoolean("isShooting", this.isShooting());
        pCompound.putBoolean("Saddle", this.isSaddled());
        pCompound.putInt("HwachaCommand", this.getCommand());
    }

    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.setIsShooting(pCompound.getBoolean("isShooting"));
        this.setSaddled(pCompound.getBoolean("Saddle"));
        this.setCommand(pCompound.getInt("HwachaCommand"));
    }

    @javax.annotation.Nullable
    public Entity getControllingPassenger() {
        for (Entity passenger : this.getPassengers()) {
            if (passenger instanceof Player) {
                Player player = (Player) passenger;
                return player;
            }
        }
        return null;
    }

    public void positionRider(Entity passenger) {
        float ySin = Mth.sin(this.yBodyRot * ((float)Math.PI / 180F));
        float yCos = Mth.cos(this.yBodyRot * ((float)Math.PI / 180F));
        passenger.setPos(this.getX() + (double)(0.5F * ySin), this.getY() + this.getPassengersRidingOffset() + passenger.getMyRidingOffset() + 0.4F, this.getZ() - (double)(0.5F * yCos));
    }

    public double getPassengersRidingOffset() {
        float f = Math.min(0.25F, this.animationSpeed);
        float f1 = this.animationPosition;
        return (double)this.getBbHeight() - 0.2D + (double)(0.12F * Mth.cos(f1 * 0.7F) * 0.7F * f);
    }


    public boolean isAlliedTo(Entity entityIn) {
        if (this.isTame()) {
            LivingEntity livingentity = this.getOwner();
            if (entityIn == livingentity) {
                return true;
            }
            if (entityIn instanceof TamableAnimal) {
                return ((TamableAnimal) entityIn).isOwnedBy(livingentity);
            }
            if (livingentity != null) {
                return livingentity.isAlliedTo(entityIn);
            }
        }

        return super.isAlliedTo(entityIn);
    }

    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        InteractionResult type = super.mobInteract(player, hand);
        Item item = itemstack.getItem();
        if (isFood(itemstack) && this.getHealth() < this.getMaxHealth()) {
            if (!player.isCreative()) {
                itemstack.shrink(1);
            }
            this.heal(6);
            return InteractionResult.SUCCESS;
        }
        if (isFood(itemstack) && !isTame()) {
            int size = itemstack.getCount();
            this.tame(player);
            itemstack.shrink(size);
            return InteractionResult.SUCCESS;
        }
        InteractionResult interactionresult = itemstack.interactLivingEntity(player, this, hand);
        if (interactionresult != InteractionResult.SUCCESS && type != InteractionResult.SUCCESS && isTame() && isOwnedBy(player)) {
            if (isFood(itemstack)) {
                this.usePlayerItem(player, hand, itemstack);
                return InteractionResult.SUCCESS;
            } else if (itemstack.getItem() == Items.SADDLE && !this.isSaddled()) {
                this.usePlayerItem(player, hand, itemstack);
                this.setSaddled(true);
                return InteractionResult.SUCCESS;
            } else if (itemstack.getItem() == Items.SHEARS && this.isSaddled()) {
                this.setSaddled(false);
                this.spawnAtLocation(Items.SADDLE);
                return InteractionResult.SUCCESS;
            } else {
                if (!player.isShiftKeyDown() && !this.isBaby() && this.isSaddled()) {
                    player.startRiding(this);
                    return InteractionResult.SUCCESS;
                } else {
                    this.setCommand((this.getCommand() + 1) % 3);

                    if (this.getCommand() == 3) {
                        this.setCommand(0);
                    }
                    player.displayClientMessage(Component.translatable("entity.unusualprehistory.all.command_" + this.getCommand(), this.getName()), true);
                    boolean sit = this.getCommand() == 2;
                    if (sit) {
                        this.setOrderedToSit(true);
                        return InteractionResult.SUCCESS;
                    } else {
                        this.setOrderedToSit(false);
                        return InteractionResult.SUCCESS;
                    }
                }
            }

        }
        return type;
    }

    public boolean isFood(ItemStack stack) {
        return stack.is(UPTags.HWACHA_FOOD);
    }

    public boolean isSaddled() {
        return this.entityData.get(SADDLED).booleanValue();
    }

    public void setSaddled(boolean saddled) {
        this.entityData.set(SADDLED, Boolean.valueOf(saddled));
    }

    public int getCommand() {
        return this.entityData.get(COMMAND).intValue();
    }

    public void setCommand(int command) {
        this.entityData.set(COMMAND, Integer.valueOf(command));
    }

    public boolean isShooting() {
        return this.entityData.get(SHOOTING);
    }

    public void setIsShooting(boolean shooting) {
        this.entityData.set(SHOOTING, shooting);
    }

    public void tick() {
        super.tick();

        if (this.isShooting() && shootProgress < 50) {
            this.spit(this.getTarget());
            shootProgress += 1;
            return;
        }

        shootProgress = 0;
        this.setIsShooting(false);

        if (this.isSaddled() && this.isShooting() && shootProgress < 50) {
            this.spit(this.getTarget());
            shootProgress += 1;
            return;
        }

        shootProgress = 0;
        this.setIsShooting(false);

        if (this.isOrderedToSit() && sitProgress < 5F) {
            sitProgress++;
        }
        if (!this.isOrderedToSit() && sitProgress > 0F) {
            sitProgress--;
        }
        if(this.getCommand() == 2 && !this.isVehicle()){
            this.setOrderedToSit(true);
        }else{
            this.setOrderedToSit(false);
        }
    }

    public boolean hurt(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        } else {
            Entity entity = source.getEntity();
            this.setOrderedToSit(false);
            if (entity != null && this.isTame() && !(entity instanceof Player) && !(entity instanceof AbstractArrow)) {
                amount = (amount + 1.0F) / 3.0F;
            }
            return super.hurt(source, amount);
        }
    }

    @Override
    public void travel(Vec3 pos) {
        if (this.isAlive()) {
            LivingEntity livingentity = (LivingEntity) this.getControllingPassenger();
            if (this.isVehicle() && livingentity !=null) {

                this.setYRot(livingentity.getYRot());
                this.yRotO = this.getYRot();
                this.setXRot(livingentity.getXRot() * 0.5F);
                this.setRot(this.getYRot(), this.getXRot());
                this.yBodyRot = this.getYRot();
                this.yHeadRot = this.yBodyRot;
                float f = livingentity.xxa * 0.5F;
                float f1 = livingentity.zza;
                if (f1 <= 0.0F) {
                    f1 *= 0.25F;
                }

                this.setSpeed(0.3F);
                super.travel(new Vec3((double) f, pos.y, (double) f1));
            }else{
                super.travel(pos);
            }
        }
    }

    private void setupShooting() {
        this.entityData.set(SHOOTING, true);
    }


    public void spit(LivingEntity target) {
        if(target == null){
            return;
        }
        this.lookAt(target, 100, 100);
        for (int i = 0; i < 2 + random.nextInt(2); i++) {
            EntityHwachaSpike llamaspitentity = new EntityHwachaSpike(this.level, this);
            double d0 = target.getX() - this.getX();
            double d1 = target.getY() - llamaspitentity.getY();
            double d2 = target.getZ() - this.getZ();
            float f = Mth.sqrt((float) (d0 * d0 + d2 * d2)) * 0.2F;
            llamaspitentity.shoot(d0, d1 + (double) f, d2, 2.0F, 4.0F);
            this.level.addFreshEntity(llamaspitentity);
        }
    }


    @Override
    protected SoundEvent getAttackSound() {
        return null;
    }

    @Override
    protected int getKillHealAmount() {
        return 5;
    }

    @Override
    protected boolean canGetHungry() {
        return false;
    }

    @Override
    protected boolean hasTargets() {
        return true;
    }

    @Override
    protected boolean hasAvoidEntity() {
        return false;
    }

    @Override
    protected boolean hasCustomNavigation() {
        return false;
    }

    @Override
    protected boolean hasMakeStuckInBlock() {
        return false;
    }

    @Override
    protected boolean customMakeStuckInBlockCheck(BlockState blockState) {
        return false;
    }

    @Override
    protected TagKey<EntityType<?>> getTargetTag() {
        return UPTags.RAPTOR_TARGETS;
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel p_146743_, AgeableMob p_146744_) {
        return null;
    }

    public void performRangedAttack(LivingEntity p_30762_, float p_30763_) {
        this.setIsShooting(true);
    }


    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {

        if (this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6 && !isShooting()) {
            {
                event.getController().setAnimation(new AnimationBuilder().loop("animation.hwacha.walk"));
            }
        } else if (!isShooting()) {
            event.getController().setAnimation(new AnimationBuilder().loop("animation.hwacha.idle"));
            event.getController().setAnimationSpeed(1.0D);
        } else if (isShooting()) {
            event.getController().setAnimation(new AnimationBuilder().loop("animation.hwacha.turret_firing"));
            return PlayState.CONTINUE;
        }
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.setResetSpeedInTicks(5);
        AnimationController<EntityHwachavenator> controller = new AnimationController<>(this, "controller", 5, this::predicate);
        data.addAnimationController(controller);
    }

    @Override
    public boolean shouldFollow() {
        return this.getCommand() == 1;
    }
}
