package com.peeko32213.unusualprehistory.common.entity.msc.baby;

import com.peeko32213.unusualprehistory.common.entity.msc.util.BabyPanicGoal;
import com.peeko32213.unusualprehistory.common.entity.msc.util.LandCreaturePathNavigation;
import com.peeko32213.unusualprehistory.core.registry.UPEntities;
import com.peeko32213.unusualprehistory.core.registry.UPItems;
import com.peeko32213.unusualprehistory.core.registry.UPSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.Difficulty;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class EntityBabyBrachi extends PathfinderMob implements IAnimatable {

    public static final int MAX_TADPOLE_AGE = Math.abs(-30000);
    private AnimationFactory factory = GeckoLibUtil.createFactory(this);
    public static final Ingredient FOOD_ITEMS = Ingredient.of(Items.MELON, UPItems.GINKGO_FRUIT.get());
    private int age;

    public EntityBabyBrachi(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
    }


    @Override
    protected PathNavigation createNavigation(Level level) {
        return new LandCreaturePathNavigation(this, level);
    }


    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 70.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.09D);
    }

    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.25D));
        this.goalSelector.addGoal(3, new BabyPanicGoal(this, 2.0D));
        this.goalSelector.addGoal(4, new TemptGoal(this, 1.2D, FOOD_ITEMS, false));
        this.goalSelector.addGoal(1, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 15.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(8, (new HurtByTargetGoal(this)));
    }

    protected SoundEvent getAmbientSound() {
        return UPSounds.BRACHI_IDLE.get();
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return UPSounds.BRACHI_HURT.get();
    }

    protected SoundEvent getDeathSound() {
        return UPSounds.BRACHI_DEATH.get();
    }

    protected void playStepSound(BlockPos p_28301_, BlockState p_28302_) {
        this.playSound(UPSounds.BRACHI_STEP.get(), 0.15F, 1.0F);
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (!this.level.isClientSide) this.setAge(this.age + 1);
        super.aiStep();
    }

    public InteractionResult mobInteract(Player p_218703_, InteractionHand p_218704_) {
        ItemStack itemstack = p_218703_.getItemInHand(p_218704_);
        if (this.isFood(itemstack)) {
            this.eatFood(p_218703_, itemstack);
        }
        return InteractionResult.sidedSuccess(this.level.isClientSide);
    }

    private boolean isFood(ItemStack stack) {
        return EntityBabyBrachi.FOOD_ITEMS.test(stack);
    }

    private void eatFood(Player player, ItemStack stack) {
        this.decrementItem(player, stack);
        this.increaseAge((int)((float)(this.getTicksUntilGrowth() / 20) * 0.1F));
        this.level.addParticle(ParticleTypes.HAPPY_VILLAGER, this.getRandomX(1.0D), this.getRandomY() + 0.5D, this.getRandomZ(1.0D), 0.0D, 0.0D, 0.0D);
    }

    private void decrementItem(Player player, ItemStack stack) {
        if (!player.getAbilities().instabuild) {
            stack.shrink(1);
        }
    }

    public void addAdditionalSaveData(CompoundTag p_218709_) {
        super.addAdditionalSaveData(p_218709_);
        p_218709_.putInt("Age", this.age);
    }

    public void readAdditionalSaveData(CompoundTag p_218698_) {
        super.readAdditionalSaveData(p_218698_);
        this.setAge(p_218698_.getInt("Age"));
    }


    private void increaseAge(int seconds) {
        this.setAge(this.age + seconds * 20);
    }

    public void setAge(int age) {
        this.age = age;
        if (this.age >= MAX_TADPOLE_AGE) this.growUp();
    }

    private void growUp() {
        if (this.level instanceof ServerLevel server) {
            EntityBrachiosaurusTeen frog = UPEntities.BRACHI_TEEN.get().create(this.level);
            if (frog == null) return;

            frog.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), this.getXRot());
            frog.finalizeSpawn(server, this.level.getCurrentDifficultyAt(frog.blockPosition()), MobSpawnType.CONVERSION, null, null);
            frog.setNoAi(this.isNoAi());
            if (this.hasCustomName()) {
                frog.setCustomName(this.getCustomName());
                frog.setCustomNameVisible(this.isCustomNameVisible());
            }

            frog.setPersistenceRequired();
            this.playSound(SoundEvents.PLAYER_LEVELUP, 0.15F, 1.0F);
            server.addFreshEntityWithPassengers(frog);
            this.discard();
        }
    }

    public void checkDespawn() {
        if (this.level.getDifficulty() == Difficulty.PEACEFUL && this.shouldDespawnInPeaceful()) {
            this.discard();
        } else {
            this.noActionTime = 0;
        }
    }

    private int getTicksUntilGrowth() {
        return Math.max(0, MAX_TADPOLE_AGE - this.age);
    }

    @Override
    public boolean shouldDropExperience() {
        return false;
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6) {
            {
                event.getController().setAnimation(new AnimationBuilder().loop("animation.babybrachy.walk"));
            }
        }
        if (this.isInWater()) {
            event.getController().setAnimation(new AnimationBuilder().loop("animation.babybrachy.swim"));
            event.getController().setAnimationSpeed(1.0F);
        }
        else {
            event.getController().setAnimation(new AnimationBuilder().loop("animation.babybrachy.idle"));
        }
        return PlayState.CONTINUE;
    }



    @Override
    public void registerControllers(AnimationData data) {
        data.setResetSpeedInTicks(5);
        AnimationController<EntityBabyBrachi> controller = new AnimationController<>(this, "controller", 5, this::predicate);
        data.addAnimationController(controller);
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

}
