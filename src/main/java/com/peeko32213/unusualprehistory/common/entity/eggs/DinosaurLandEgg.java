package com.peeko32213.unusualprehistory.common.entity.eggs;

import com.peeko32213.unusualprehistory.common.entity.EntityKimmeridgebrachypteraeschnidium;
import com.peeko32213.unusualprehistory.common.entity.IHatchableEntity;
import com.peeko32213.unusualprehistory.common.entity.msc.anim_goal.AnimationAI;
import com.peeko32213.unusualprehistory.common.entity.msc.anim_goal.AnimationHelper;
import com.peeko32213.unusualprehistory.core.registry.UPEntities;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

import java.util.Optional;
import java.util.function.Supplier;

public class DinosaurLandEgg extends BaseDinosaurEgg {

    //private static final EntityDataAccessor<Integer> EGG_SIZE = SynchedEntityData.defineId(DinosaurLandEgg.class, EntityDataSerializers.INT);
    private int currentHatchTime;
    private int maxHatchTime;
    private LivingEntity dinosaur;
    private final int MAX_HATCH_COUNT = 2;
    private int hatchCount;
    private static final AnimationHelper EGG_HATCH = AnimationHelper.playAnimationWController("egg_hatch", "animation.egg.hatch");

    public DinosaurLandEgg(Level pLevel, double pX, double pY, double pZ, Supplier<? extends EntityType<?>> toSpawn, EggSize eggsize, float eggColor1, float eggColor2, int hatchTime, ItemStack stack) {
        super(UPEntities.DINO_LAND_EGG.get(), pLevel, pX, pY, pZ, stack, eggsize, eggColor1, eggColor2);
        this.maxHatchTime = hatchTime;
        this.dinosaur = (LivingEntity) toSpawn.get().create(pLevel);
    }

    public DinosaurLandEgg(EntityType<DinosaurLandEgg> dinosaurLandEggEntityType, Level level) {
        super(dinosaurLandEggEntityType, level);
    }


    @Override
    public void tick() {
        super.tick();
        if (currentHatchTime >= maxHatchTime) {
            if (hatchCount++ >= MAX_HATCH_COUNT) {
                hatchEgg();
            } else {
                increaseHatchCount();
            }

        }

    }

    public void hatchEgg() {
        level().playSound(null, this.blockPosition(), SoundEvents.TURTLE_EGG_HATCH, SoundSource.BLOCKS, 0.7F, 0.9F + random.nextFloat() * 0.2F);

        LivingEntity dinosaurToSpawn = dinosaur;
        if (dinosaurToSpawn instanceof IHatchableEntity hatchableEntity) {
            hatchableEntity.determineVariant(random.nextInt(100));
        }


        if (dinosaurToSpawn instanceof Animal animal) {
            animal.setAge(-24000);
        }

        if(dinosaurToSpawn instanceof Mob mob) {
            mob.restrictTo(this.blockPosition(), 20);
            mob.moveTo((double) this.blockPosition().getX() + 0.3D, this.blockPosition().getY(), (double) this.blockPosition().getZ() + 0.3D, 0.0F, 0.0F);
        }

        if (!level().isClientSide) {
            level().addFreshEntity(dinosaurToSpawn);
        }

    }

    public void increaseHatchCount() {
        level().playSound(null, this.blockPosition(), SoundEvents.TURTLE_EGG_CRACK, SoundSource.BLOCKS, 0.7F, 0.9F + random.nextFloat() * 0.2F);
        this.triggerAnim(EGG_HATCH.getControllerName(), EGG_HATCH.getAnimName());
    }

    @Override
    public ResourceLocation getTexture() {
        int scale = getModelScale();
        return switch (scale) {
            case 0 -> null;
            case 1 -> null;
            case 2 -> null;
            case 3 -> null;
            default -> null;
        };
    }


    @Override
    public ResourceLocation getModel() {
        int scale = getModelScale();
        return switch (scale) {
            case 0 -> null;
            case 1 -> null;
            case 2 -> null;
            case 3 -> null;
            default -> null;
        };
    }

    @Override
    public int getHatchTime() {
        return maxHatchTime;
    }

    @Override
    public int getCurrentHatchTime() {
        return currentHatchTime;
    }


    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        dinosaur.save(compound);
        compound.putInt("hatchTime", currentHatchTime);
        compound.putInt("maxHatchTime", maxHatchTime);
        compound.putInt("hatchCount", hatchCount);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        Optional<Entity> type = EntityType.create(compound, level());
        this.dinosaur = (LivingEntity) type.orElse(EntityType.PIG.create(level()));
        this.currentHatchTime = compound.getInt("hatchTime");
        this.maxHatchTime = compound.getInt("maxHatchTime");
        this.hatchCount = compound.getInt("hatchCount");
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();

    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, EGG_HATCH.getControllerName(), 0, event -> {
            return PlayState.STOP;
        }).triggerableAnim(EGG_HATCH.getAnimName(), EGG_HATCH.getAnimation()));
    }
}
