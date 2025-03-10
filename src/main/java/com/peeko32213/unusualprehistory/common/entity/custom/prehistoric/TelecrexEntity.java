package com.peeko32213.unusualprehistory.common.entity.custom.prehistoric;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.data.entity.PrehistoricEntityData;
import com.peeko32213.unusualprehistory.common.data.entity.VariantData;
import com.peeko32213.unusualprehistory.common.data.entity.WeightedVariantData;
import com.peeko32213.unusualprehistory.common.entity.animation.state.StateHelper;
import com.peeko32213.unusualprehistory.common.entity.animation.state.WeightedState;
import com.peeko32213.unusualprehistory.common.entity.custom.base.PrehistoricEntity;
import com.peeko32213.unusualprehistory.common.entity.custom.base.data.PrehistoricEntityDatafied;
import com.peeko32213.unusualprehistory.common.entity.custom.base.old.PrehistoricEntityOld;
import com.peeko32213.unusualprehistory.core.registry.UPSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

import java.util.List;
import java.util.Optional;

public class TelecrexEntity extends PrehistoricEntityDatafied {

    private Ingredient temptationItems;

    private static final RawAnimation TELECREX_WALK = RawAnimation.begin().thenLoop("animation.telecrex.walk");
    private static final RawAnimation TELECREX_IDLE = RawAnimation.begin().thenLoop("animation.telecrex.idle");
    private static final RawAnimation TELECREX_SWIM = RawAnimation.begin().thenLoop("animation.telecrex.hover");

    public TelecrexEntity(EntityType<? extends PrehistoricEntityDatafied> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
            .add(Attributes.MAX_HEALTH, 10.0D)
            .add(Attributes.MOVEMENT_SPEED, 0.15D);
    }

    @Override
    protected void registerGoals() {
//        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.25D));
        this.goalSelector.addGoal(4, new TemptGoal(this, 1.2D, getTemptationItems(), false));
//        this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 1.0D));
//        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.targetSelector.addGoal(8, (new HurtByTargetGoal(this)));
//        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
    }

    private Ingredient getTemptationItems() {
        if (temptationItems == null)
            temptationItems = Ingredient.merge(Lists.newArrayList(
                    Ingredient.of(ItemTags.LEAVES)
            ));

        return temptationItems;
    }
    protected SoundEvent getHurtSound(@NotNull DamageSource damageSourceIn) {
        return UPSounds.TALPANAS_HURT.get();
    }

    protected void playStepSound(@NotNull BlockPos p_28301_, @NotNull BlockState p_28302_) {
        this.playSound(SoundEvents.CHICKEN_STEP, 0.1F, 1.0F);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel pLevel, @NotNull AgeableMob pOtherParent) {
        return null;
    }

    @Override
    protected ResourceLocation getEntityDataResourceLocation() {
        return new ResourceLocation(UnusualPrehistory.MODID, "unusualprehistory/prehistoric_animal/telecrex.json");
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
    }

    public void tick() {
        super.tick();
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        return source.is(DamageTypes.FALL) || source.is(DamageTypes.IN_WALL);
    }

    protected <E extends TelecrexEntity> PlayState Controller(final software.bernie.geckolib.core.animation.AnimationState<E> event) {

        if (this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6 && !this.isPassenger()&& !this.isSwimming()) {
            event.setAndContinue(TELECREX_WALK);
            return PlayState.CONTINUE;
        }

        if (this.isInWater()) {
            event.setAndContinue(TELECREX_SWIM);
            event.getController().setAnimationSpeed(1.0F);
            return PlayState.CONTINUE;
        }
        else {
            event.setAndContinue(TELECREX_IDLE);
        }
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(final AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "Normal", 10, this::Controller));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return null;
    }

    @Override
    public ImmutableMap<String, StateHelper> getStates() {
        return null;
    }

    @Override
    public List<WeightedState<StateHelper>> getWeightedStatesToPerform() {
        return List.of();
    }
}
