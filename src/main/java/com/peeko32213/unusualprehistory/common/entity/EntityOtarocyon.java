package com.peeko32213.unusualprehistory.common.entity;

import com.peeko32213.unusualprehistory.common.entity.msc.util.*;
import com.peeko32213.unusualprehistory.common.entity.msc.util.dino.EntityTameableBaseDinosaurAnimal;
import com.peeko32213.unusualprehistory.core.registry.UPItems;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

import java.util.List;

public class EntityOtarocyon extends EntityTameableBaseDinosaurAnimal implements CustomFollower {

    private static final RawAnimation OTAROCYON_IDLE = RawAnimation.begin().thenLoop("animation.otarocyon.idle");
    private static final RawAnimation OTAROCYON_SIT = RawAnimation.begin().thenLoop("animation.otarocyon.sit");
    private static final RawAnimation OTAROCYON_LOAF = RawAnimation.begin().thenLoop("animation.otarocyon.loaf");
    private static final RawAnimation OTAROCYON_SLEEP = RawAnimation.begin().thenLoop("animation.otarocyon.sleep");
    private static final RawAnimation OTAROCYON_WALK = RawAnimation.begin().thenLoop("animation.otarocyon.walk");
    private static final RawAnimation OTAROCYON_RUN = RawAnimation.begin().thenLoop("animation.otarocyon.run");
    private static final RawAnimation OTAROCYON_SWIM = RawAnimation.begin().thenLoop("animation.otarocyon.swim");
    private static final RawAnimation OTAROCYON_YAWN = RawAnimation.begin().thenLoop("animation.otarocyon.yawn");
    private static final RawAnimation OTAROCYON_DIG = RawAnimation.begin().thenLoop("animation.otarocyon.dig");
    private static final RawAnimation OTAROCYON_SCREAM = RawAnimation.begin().thenLoop("animation.otarocyon.scream");
    private static final RawAnimation OTAROCYON_ATTACK = RawAnimation.begin().thenLoop("animation.otarocyon.attack");
    private static final RawAnimation OTAROCYON_LEAP_START = RawAnimation.begin().thenLoop("animation.otarocyon.leap_start");
    private static final RawAnimation OTAROCYON_LEAP_HOLD = RawAnimation.begin().thenLoop("animation.otarocyon.leap_hold");

    private static final EntityDataAccessor<Integer> COMMAND = SynchedEntityData.defineId(EntityMegatherium.class, EntityDataSerializers.INT);
    public float sitProgress;
    private int spookMobsTime = 0;

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 15.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.2D)
                .add(Attributes.ARMOR, 0.0D)
                .add(Attributes.ARMOR_TOUGHNESS, 0.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.0D);
    }

    public EntityOtarocyon(EntityType<? extends EntityTameableBaseDinosaurAnimal> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new NocturnalSleepingGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.25D));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(0, new SitWhenOrderedToGoal(this));
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)));
        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
        this.goalSelector.addGoal(3, new TameableFollowOwner(this, 1.2D, 5.0F, 2.0F, false));
        this.goalSelector.addGoal(3, new CustomRandomStrollGoal(this, 30, 1.0D, 100, 34));
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (itemstack.is(UPItems.ENCYLOPEDIA.get())) {
            InteractionResult interactionresult = itemstack.interactLivingEntity(player, this, hand);
        }
        if (itemstack.is(UPItems.RAW_AUSTRO.get())) {
            if (!isTame()) {
                this.usePlayerItem(player, hand, itemstack);
                if (getRandom().nextInt(3) == 0) {
                    this.tame(player);
                    this.level().broadcastEntityEvent(this, (byte) 7);
                } else {
                    this.level().broadcastEntityEvent(this, (byte) 6);
                }
            }
        }
        return InteractionResult.SUCCESS;
    }


    public void tick() {
        super.tick();

        if(attackCooldown > 0){
            attackCooldown--;
        }

        if (this.isOrderedToSit() && sitProgress < 5F) {
            sitProgress++;
        }
        if (!this.isOrderedToSit() && sitProgress > 0F) {
            sitProgress--;
        }
        if (this.getCommand() == 2) {
            this.setOrderedToSit(true);
        } else {
            this.setOrderedToSit(false);
        }
        if(isStillEnough() && random.nextInt(1000) == 0 && !this.isInSittingPose() && !this.isSwimming()){
            float rand = random.nextFloat();
            if (rand < 0.2F) {
                spookMobsTime = 40;
            }
        }
        if(spookMobsTime > 0) {
            List<Monster> list = this.level().getEntitiesOfClass(Monster.class, this.getBoundingBox().inflate(16, 8, 16));
            for (Monster e : list) {
                e.setTarget(null);
                e.setLastHurtByMob(null);
                if(spookMobsTime % 5 == 0){
                    Vec3 vec = LandRandomPos.getPosAway(e, 20, 7, this.position());
                    if(vec != null){
                        e.getNavigation().moveTo(vec.x, vec.y, vec.z, 1.5D);
                    }
                }

            }
            spookMobsTime--;
        }
    }

    public int getCommand() {
        return this.entityData.get(COMMAND).intValue();
    }

    public void setCommand(int command) {
        this.entityData.set(COMMAND, Integer.valueOf(command));
    }

    @Override
    public boolean shouldFollow() {
        return this.getCommand() == 1;
    }

    @Override
    protected void performAttack() {

    }

    @Override
    protected SoundEvent getAttackSound() {
        return null;
    }

    @Override
    protected int getKillHealAmount() {
        return 0;
    }

    @Override
    protected boolean canGetHungry() {
        return false;
    }

    @Override
    protected boolean hasTargets() {
        return false;
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
        return null;
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        return null;
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

        return entityIn.is(this);
    }

    private boolean isStillEnough() {
        return this.getDeltaMovement().horizontalDistance() < 0.05;
    }

    protected <E extends EntityOtarocyon> PlayState Controller(final software.bernie.geckolib.core.animation.AnimationState<E> event) {

        if (this.isFromBook()) {
            return PlayState.CONTINUE;
        }
        if (this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6 && !this.isInSittingPose() && !this.isInWater()) {
            if (this.isSprinting() || !this.getPassengers().isEmpty()) {
                event.setAndContinue(OTAROCYON_RUN);
                event.getController().setAnimationSpeed(2.0D);
                return PlayState.CONTINUE;
            } else if (event.isMoving()) {
                event.setAndContinue(OTAROCYON_WALK);
                event.getController().setAnimationSpeed(1.0D);
                return PlayState.CONTINUE;
            }
        }
        if (this.isInSittingPose() && !this.isInWater() && !this.isSwimming()) {
            event.setAndContinue(OTAROCYON_SIT);
            return PlayState.CONTINUE;
        }
        if (this.isInWater()) {
            event.setAndContinue(OTAROCYON_SWIM);
            event.getController().setAnimationSpeed(1.0F);
            return PlayState.CONTINUE;
        }
        if (spookMobsTime > 0){
            event.setAndContinue(OTAROCYON_SCREAM);
            event.getController().setAnimationSpeed(1.0F);
            return PlayState.CONTINUE;
        }
        if (isStillEnough() && random.nextInt(100) == 0 && !this.isInSittingPose() && !this.isSwimming()) {
            float rand = random.nextFloat();
            if (rand < 0.2F) {
                event.setAndContinue(OTAROCYON_LOAF);
            }
            if (rand < 0.45F) {
                event.setAndContinue(OTAROCYON_DIG);
            }
            else if (rand < 0.9F) {
                event.setAndContinue(OTAROCYON_YAWN);
            } else {
                event.setAndContinue(OTAROCYON_IDLE);
            }
        }
        return PlayState.CONTINUE;
    }

    protected <E extends EntityOtarocyon> PlayState attackController(final software.bernie.geckolib.core.animation.AnimationState<E> event) {
        if (this.swinging && event.getController().getAnimationState().equals(AnimationController.State.PAUSED)) {
            return event.setAndContinue(OTAROCYON_ATTACK);
        }
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(final AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "Normal", 10, this::Controller));
        controllers.add(new AnimationController<>(this, "Attack", 0, this::attackController));
    }

}
