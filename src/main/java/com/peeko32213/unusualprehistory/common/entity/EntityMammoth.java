package com.peeko32213.unusualprehistory.common.entity;

import com.google.common.collect.Lists;
import com.peeko32213.unusualprehistory.common.entity.msc.util.dino.EntityBaseDinosaurAnimal;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;

import java.util.Optional;

public class EntityMammoth extends EntityBaseDinosaurAnimal {
    private static final EntityDataAccessor<Boolean> IS_TRUNKING = SynchedEntityData.defineId(EntityMammoth.class, EntityDataSerializers.BOOLEAN);
    public float prevFeedProgress;
    public float feedProgress;
    private Ingredient temptationItems;

    public EntityMammoth(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 50.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.15D)
                .add(Attributes.ARMOR, 5.0D)
                .add(Attributes.ARMOR_TOUGHNESS, 3.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 8.5D);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.25D));
        this.goalSelector.addGoal(4, new TemptGoal(this, 1.2D, getTemptationItems(), false));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.targetSelector.addGoal(8, (new HurtByTargetGoal(this)));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(1, new MammothTrunkIdleGoal(this));

    }

    private Ingredient getTemptationItems() {
        if(temptationItems == null)
            temptationItems = Ingredient.merge(Lists.newArrayList(
                    Ingredient.of(ItemTags.LEAVES)
            ));

        return temptationItems;
    }


    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(IS_TRUNKING, false);
    }

    public boolean isTrunking() {
        return this.entityData.get(IS_TRUNKING);
    }

    public void setTrunking(boolean isPecking) {
        this.entityData.set(IS_TRUNKING, isPecking);
    }


    static class MammothTrunkIdleGoal extends Goal {
        private final EntityMammoth mammoth;

        public MammothTrunkIdleGoal(EntityMammoth mammoth) {
            this.mammoth = mammoth;
        }
        @Override
        public boolean canUse() {
            return this.mammoth.isOnGround() && this.mammoth.getRandom().nextInt(100) == 0;
        }

        @Override
        public void tick() {
            this.mammoth.setTrunking(true);
            if(this.mammoth.getRandom().nextInt(100) <= 25 ) {
                this.mammoth.setTrunking(false);
            }
        }

        @Override
        public boolean canContinueToUse() {
            return this.canUse();
        }
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

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6) {
            event.getController().setAnimation(new AnimationBuilder().loop("animation.mammoth.move"));
            event.getController().setAnimationSpeed(1.5D);
            return PlayState.CONTINUE;
        }
        else {
            event.getController().setAnimation(new AnimationBuilder().loop("animation.mammoth.idle"));
            event.getController().setAnimationSpeed(1.0D);
        }
        return PlayState.CONTINUE;
    }

    private <E extends IAnimatable> PlayState trunkPredicate(AnimationEvent<E> event) {
        if (this.isTrunking()) {
            event.getController().setAnimation(new AnimationBuilder().playOnce("animation.mammoth.idle_trunk"));
        }
        event.getController().markNeedsReload();
        return PlayState.STOP;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.setResetSpeedInTicks(5);
        AnimationController<EntityMammoth> controller = new AnimationController<>(this, "controller", 5, this::predicate);
        data.addAnimationController(new AnimationController<>(this, "eatController", 5, this::trunkPredicate));
        data.addAnimationController(controller);
    }
}
